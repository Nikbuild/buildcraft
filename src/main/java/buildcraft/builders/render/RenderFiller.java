/*    */ package buildcraft.builders.render;
/*    */ 
/*    */ import buildcraft.BuildCraftBuilders;
/*    */ import buildcraft.builders.TileFiller;
/*    */ import buildcraft.core.lib.render.RenderEntityBlock;
/*    */ import buildcraft.core.render.RenderBuilder;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class RenderFiller
/*    */   extends RenderBuilder
/*    */ {
/*    */   private static final float Z_OFFSET = 1.0004883F;
/*    */   
/*    */   public void func_147500_a(TileEntity tileentity, double x, double y, double z, float f) {
/* 18 */     super.func_147500_a(tileentity, x, y, z, f);
/*    */     
/* 20 */     func_147499_a(TextureMap.field_110575_b);
/* 21 */     RenderEntityBlock.RenderInfo renderBox = new RenderEntityBlock.RenderInfo();
/*    */     
/* 23 */     GL11.glPushMatrix();
/*    */     
/* 25 */     GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
/* 26 */     GL11.glScalef(1.0004883F, 1.0004883F, 1.0004883F);
/* 27 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/*    */     
/* 29 */     renderBox.setRenderSingleSide(BuildCraftBuilders.fillerBlock.getFrontSide(tileentity.func_145832_p()));
/* 30 */     renderBox.texture = ((TileFiller)tileentity).currentPattern.getBlockOverlay();
/* 31 */     RenderEntityBlock.INSTANCE.renderBlock(renderBox);
/*    */     
/* 33 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\render\RenderFiller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */