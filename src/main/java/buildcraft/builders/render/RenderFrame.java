/*     */ package buildcraft.builders.render;
/*     */ 
/*     */ import buildcraft.builders.BuilderProxy;
/*     */ import buildcraft.core.lib.render.FakeBlock;
/*     */ import buildcraft.core.lib.render.RenderUtils;
/*     */ import buildcraft.core.render.BCSimpleBlockRenderingHandler;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderFrame
/*     */   extends BCSimpleBlockRenderingHandler
/*     */ {
/*     */   private void renderTwoWayBlock(RenderBlocks renderblocks, FakeBlock stateHost, int x, int y, int z, float[] dim, int mask) {
/*  20 */     assert mask != 0;
/*     */     
/*  22 */     stateHost.setRenderMask(mask);
/*  23 */     renderblocks.func_147782_a(dim[2], dim[0], dim[1], dim[5], dim[3], dim[4]);
/*  24 */     renderblocks.func_147736_d((Block)stateHost, x, y, z, 1.0F, 1.0F, 1.0F);
/*     */     
/*  26 */     stateHost.setRenderMask((mask & 0x15) << 1 | (mask & 0x2A) >> 1);
/*  27 */     renderblocks.func_147782_a(dim[5], dim[3], dim[4], dim[2], dim[0], dim[1]);
/*  28 */     renderblocks.func_147736_d((Block)stateHost, x, y, z, 0.8F, 0.8F, 0.8F);
/*     */     
/*  30 */     stateHost.setRenderAllSides();
/*     */   }
/*     */   
/*     */   private void resetToCenterDimensions(float[] dim) {
/*  34 */     for (int i = 0; i < 3; i++) {
/*  35 */       dim[i] = 0.25F;
/*  36 */       dim[i + 3] = 0.75F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
/*  42 */     renderer.func_147782_a(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
/*  43 */     RenderUtils.drawBlockItem(renderer, Tessellator.field_78398_a, block, metadata);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
/*  48 */     FakeBlock fakeBlock = FakeBlock.INSTANCE;
/*  49 */     fakeBlock.getTextureState().set(block.func_149691_a(0, 0));
/*     */     
/*  51 */     int connectivity = 0;
/*  52 */     int connections = 0;
/*     */     
/*  54 */     float[] dim = new float[6];
/*  55 */     resetToCenterDimensions(dim);
/*     */     
/*  57 */     for (int i = 0; i < 6; i++) {
/*  58 */       ForgeDirection d = ForgeDirection.getOrientation(i);
/*  59 */       if (world.func_147439_a(x + d.offsetX, y + d.offsetY, z + d.offsetZ) == block) {
/*  60 */         connectivity |= 1 << i;
/*  61 */         connections++;
/*     */       } 
/*     */     } 
/*     */     
/*  65 */     if (connections != 2) {
/*  66 */       renderTwoWayBlock(renderer, fakeBlock, x, y, z, dim, 63);
/*     */     } else {
/*  68 */       renderTwoWayBlock(renderer, fakeBlock, x, y, z, dim, connectivity ^ 0x3F);
/*     */     } 
/*     */ 
/*     */     
/*  72 */     for (int dir = 0; dir < 6; dir++) {
/*  73 */       int mask = 1 << dir;
/*     */       
/*  75 */       if ((connectivity & mask) != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  80 */         resetToCenterDimensions(dim);
/*     */ 
/*     */         
/*  83 */         dim[dir / 2] = (dir % 2 == 0) ? 0.0F : 0.75F;
/*  84 */         dim[dir / 2 + 3] = (dir % 2 == 0) ? 0.25F : 1.0F;
/*     */ 
/*     */         
/*  87 */         int renderMask = 3 << (dir & 0x6) ^ 0x3F;
/*  88 */         renderTwoWayBlock(renderer, fakeBlock, x, y, z, dim, renderMask);
/*     */       } 
/*     */     } 
/*  91 */     renderer.func_147782_a(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender3DInInventory(int modelId) {
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderId() {
/* 102 */     return BuilderProxy.frameRenderId;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\render\RenderFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */