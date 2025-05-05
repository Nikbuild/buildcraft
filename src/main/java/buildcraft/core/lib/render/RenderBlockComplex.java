/*    */ package buildcraft.core.lib.render;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import buildcraft.core.render.BCSimpleBlockRenderingHandler;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.RenderBlocks;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderBlockComplex
/*    */   extends BCSimpleBlockRenderingHandler
/*    */ {
/* 17 */   private static final int[] Y_ROTATE = new int[] { 3, 0, 1, 2 };
/*    */ 
/*    */   
/*    */   public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderer) {
/* 21 */     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 22 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 23 */     BlockBuildCraft bcBlock = (BlockBuildCraft)block;
/* 24 */     int pass = 0;
/* 25 */     while (bcBlock.canRenderInPassBC(pass)) {
/* 26 */       renderPassInventory(pass, bcBlock, meta, renderer);
/* 27 */       pass++;
/*    */     } 
/* 29 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*    */   }
/*    */   
/*    */   private void renderPassInventory(int pass, BlockBuildCraft block, int meta, RenderBlocks renderer) {
/* 33 */     if (block.isRotatable()) {
/* 34 */       renderer.field_147867_u = Y_ROTATE[block.getFrontSide(meta) - 2];
/* 35 */       renderer.field_147865_v = Y_ROTATE[block.getFrontSide(meta) - 2];
/*    */     } 
/*    */     
/* 38 */     RenderUtils.drawBlockItem(renderer, Tessellator.field_78398_a, (Block)block, meta);
/*    */     
/* 40 */     renderer.field_147867_u = 0;
/* 41 */     renderer.field_147865_v = 0;
/*    */   }
/*    */   
/*    */   private void renderPassWorld(int pass, BlockBuildCraft block, int meta, RenderBlocks renderer, IBlockAccess world, int x, int y, int z) {
/* 45 */     if (block.isRotatable()) {
/* 46 */       renderer.field_147867_u = Y_ROTATE[block.getFrontSide(meta) - 2];
/* 47 */       renderer.field_147865_v = Y_ROTATE[block.getFrontSide(meta) - 2];
/*    */     } 
/*    */     
/* 50 */     double pDouble = ((pass > 0) ? true : false) / 512.0D;
/* 51 */     renderer.func_147782_a(block.func_149704_x() - pDouble, block
/* 52 */         .func_149665_z() - pDouble, block
/* 53 */         .func_149706_B() - pDouble, block
/* 54 */         .func_149753_y() + pDouble, block
/* 55 */         .func_149669_A() + pDouble, block
/* 56 */         .func_149693_C() + pDouble);
/*    */     
/* 58 */     renderer.func_147784_q((Block)block, x, y, z);
/*    */     
/* 60 */     renderer.field_147867_u = 0;
/* 61 */     renderer.field_147865_v = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
/* 66 */     BlockBuildCraft bcBlock = (BlockBuildCraft)block;
/* 67 */     int meta = world.func_72805_g(x, y, z);
/*    */     
/* 69 */     int pass = bcBlock.getCurrentRenderPass();
/* 70 */     while (bcBlock.canRenderInPassBC(pass)) {
/* 71 */       renderPassWorld(pass, bcBlock, meta, renderer, world, x, y, z);
/* 72 */       pass++;
/*    */     } 
/* 74 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldRender3DInInventory(int modelId) {
/* 79 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRenderId() {
/* 84 */     return BuildCraftCore.complexBlockModel;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\render\RenderBlockComplex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */