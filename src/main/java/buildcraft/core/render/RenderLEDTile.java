/*    */ package buildcraft.core.render;
/*    */ 
/*    */ import buildcraft.core.internal.ICustomLEDBlock;
/*    */ import buildcraft.core.internal.ILEDProvider;
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import buildcraft.core.lib.render.RenderEntityBlock;
/*    */ import buildcraft.core.lib.utils.ResourceUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.IIcon;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class RenderLEDTile
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/* 24 */   private static final Map<Block, IIcon[]> iconMap = (Map)new HashMap<Block, IIcon>();
/*    */   private static final float Z_OFFSET = 1.0004883F;
/*    */   private final Block block;
/*    */   
/*    */   public RenderLEDTile(Block block) {
/* 29 */     iconMap.put(block, null);
/* 30 */     this.block = block;
/*    */   }
/*    */   
/*    */   public static void registerBlockIcons(IIconRegister register) {
/* 34 */     for (Block b : (Block[])iconMap.keySet().toArray((Object[])new Block[iconMap.keySet().size()])) {
/* 35 */       String base = ResourceUtils.getObjectPrefix(Block.field_149771_c.func_148750_c(b));
/* 36 */       if (base != null) {
/* 37 */         List<IIcon> icons = new ArrayList<IIcon>();
/* 38 */         if (b instanceof ICustomLEDBlock) {
/* 39 */           for (String s : ((ICustomLEDBlock)b).getLEDSuffixes()) {
/* 40 */             icons.add(register.func_94245_a(base + "/" + s));
/*    */           }
/*    */         } else {
/* 43 */           icons.add(register.func_94245_a(base + "/led_red"));
/* 44 */           icons.add(register.func_94245_a(base + "/led_green"));
/*    */         } 
/*    */         
/* 47 */         iconMap.put(b, icons.toArray(new IIcon[icons.size()]));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_147500_a(TileEntity tile, double x, double y, double z, float f) {
/* 54 */     if (!(tile instanceof ILEDProvider)) {
/*    */       return;
/*    */     }
/*    */     
/* 58 */     func_147499_a(TextureMap.field_110575_b);
/* 59 */     RenderEntityBlock.RenderInfo renderBox = new RenderEntityBlock.RenderInfo();
/*    */     
/* 61 */     ILEDProvider provider = (ILEDProvider)tile;
/*    */     
/* 63 */     GL11.glPushMatrix();
/* 64 */     GL11.glPushAttrib(24576);
/*    */     
/* 66 */     GL11.glDisable(2896);
/* 67 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 68 */     GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
/* 69 */     GL11.glScalef(1.0004883F, 1.0004883F, 1.0004883F);
/* 70 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/*    */     
/* 72 */     IIcon[] icons = iconMap.get(this.block);
/*    */     
/* 74 */     for (int i = 0; i < icons.length; i++) {
/* 75 */       renderBox.light = provider.getLEDLevel(i);
/* 76 */       if (renderBox.light != 0) {
/* 77 */         renderBox.texture = icons[i];
/*    */         
/* 79 */         if (((BlockBuildCraft)this.block).isRotatable()) {
/* 80 */           renderBox.setRenderSingleSide(((BlockBuildCraft)this.block).getFrontSide(tile.func_145832_p()));
/*    */         } else {
/* 82 */           renderBox.renderSide[0] = false;
/* 83 */           renderBox.renderSide[1] = false;
/* 84 */           renderBox.renderSide[2] = true;
/* 85 */           renderBox.renderSide[3] = true;
/* 86 */           renderBox.renderSide[4] = true;
/* 87 */           renderBox.renderSide[5] = true;
/*    */         } 
/* 89 */         RenderEntityBlock.INSTANCE.renderBlock(renderBox);
/*    */       } 
/*    */     } 
/*    */     
/* 93 */     GL11.glPopAttrib();
/* 94 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\render\RenderLEDTile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */