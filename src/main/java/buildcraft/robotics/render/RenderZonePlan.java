/*    */ package buildcraft.robotics.render;
/*    */ 
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import buildcraft.core.lib.render.DynamicTextureBC;
/*    */ import buildcraft.core.lib.render.FakeIcon;
/*    */ import buildcraft.core.lib.render.RenderEntityBlock;
/*    */ import buildcraft.robotics.TileZonePlan;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.IIcon;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderZonePlan
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/*    */   private static final float Z_OFFSET = 1.0004883F;
/* 19 */   private static final HashMap<TileZonePlan, DynamicTextureBC> TEXTURES = new HashMap<TileZonePlan, DynamicTextureBC>();
/*    */ 
/*    */   
/*    */   public void func_147500_a(TileEntity tile, double tx, double ty, double tz, float partialTicks) {
/* 23 */     boolean rendered = true;
/* 24 */     TileZonePlan zonePlan = (TileZonePlan)tile;
/*    */     
/* 26 */     if (!TEXTURES.containsKey(zonePlan)) {
/* 27 */       DynamicTextureBC dynamicTextureBC = new DynamicTextureBC(16, 16);
/* 28 */       TEXTURES.put(zonePlan, dynamicTextureBC);
/* 29 */       rendered = false;
/*    */     } 
/* 31 */     DynamicTextureBC textureBC = TEXTURES.get(zonePlan);
/* 32 */     FakeIcon fakeIcon = new FakeIcon(0.0F, 1.0F, 0.0F, 1.0F, 16, 16);
/*    */     
/* 34 */     byte[] previewColors = zonePlan.getPreviewTexture(!rendered);
/*    */     
/* 36 */     if (previewColors != null) {
/* 37 */       for (int y = 0; y < 8; y++) {
/* 38 */         for (int x = 0; x < 10; x++) {
/* 39 */           int col = (MapColor.field_76281_a[previewColors[y * 10 + x]]).field_76291_p;
/* 40 */           if ((x & 0x1) != (y & 0x1)) {
/* 41 */             int ocol = col;
/* 42 */             col = (ocol & 0xFF) * 15 / 16 | ((ocol & 0xFF00) >> 8) * 15 / 16 << 8 | ((ocol & 0xFF0000) >> 16) * 15 / 16 << 16;
/*    */           } 
/*    */ 
/*    */           
/* 46 */           textureBC.setColor(x + 3, y + 3, 0xFF000000 | col);
/*    */         } 
/*    */       } 
/*    */     }
/*    */     
/* 51 */     GL11.glPushMatrix();
/* 52 */     GL11.glPushAttrib(16384);
/*    */     
/* 54 */     GL11.glTranslatef((float)tx + 0.5F, (float)ty + 0.5F, (float)tz + 0.5F);
/* 55 */     GL11.glScalef(1.0004883F, 1.0004883F, 1.0004883F);
/* 56 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/*    */     
/* 58 */     GL11.glEnable(3042);
/* 59 */     GL11.glBlendFunc(770, 1);
/*    */     
/* 61 */     textureBC.updateTexture();
/*    */     
/* 63 */     RenderEntityBlock.RenderInfo renderBox = new RenderEntityBlock.RenderInfo();
/* 64 */     renderBox.setRenderSingleSide(((BlockBuildCraft)zonePlan.func_145838_q()).getFrontSide(zonePlan.func_145832_p()));
/* 65 */     renderBox.texture = (IIcon)fakeIcon;
/* 66 */     renderBox.light = 15;
/* 67 */     RenderEntityBlock.INSTANCE.renderBlock(renderBox);
/*    */     
/* 69 */     GL11.glPopAttrib();
/* 70 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\render\RenderZonePlan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */