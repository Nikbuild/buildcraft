/*     */ package buildcraft.transport.render;
/*     */ 
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.core.render.ITextureStates;
/*     */ import buildcraft.api.transport.pluggable.IPipePluggableRenderer;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import buildcraft.core.lib.render.FakeBlock;
/*     */ import buildcraft.core.lib.utils.ColorUtils;
/*     */ import buildcraft.core.render.BCSimpleBlockRenderingHandler;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeRenderState;
/*     */ import buildcraft.transport.TileGenericPipe;
/*     */ import buildcraft.transport.TransportProxy;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PipeRendererWorld
/*     */   extends BCSimpleBlockRenderingHandler
/*     */ {
/*  39 */   public static int renderPass = -1;
/*  40 */   public static float zFightOffset = 2.4414062E-4F;
/*  41 */   private static final double[] CHEST_BB = new double[] { 0.0D, 0.0625D, 0.0625D, 0.875D, 0.9375D, 0.9375D };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renderPipe(RenderBlocks renderblocks, IBlockAccess iblockaccess, TileGenericPipe tile, int x, int y, int z) {
/*  47 */     PipeRenderState state = tile.renderState;
/*  48 */     IIconProvider icons = tile.getPipeIcons();
/*  49 */     FakeBlock fakeBlock = FakeBlock.INSTANCE;
/*  50 */     int glassColor = tile.getPipeColor();
/*     */     
/*  52 */     if (icons == null) {
/*  53 */       return false;
/*     */     }
/*     */     
/*  56 */     boolean rendered = false;
/*     */     
/*  58 */     if (renderPass == 0 || glassColor >= 0) {
/*     */       
/*  60 */       int connectivity = state.pipeConnectionMatrix.getMask();
/*  61 */       float[] dim = new float[6];
/*     */       
/*  63 */       if (renderPass == 1) {
/*  64 */         fakeBlock.setColor(ColorUtils.getRGBColor(glassColor));
/*  65 */       } else if (glassColor >= 0 && tile.getPipe() instanceof buildcraft.transport.pipes.PipeStructureCobblestone) {
/*  66 */         if (glassColor == 0) {
/*  67 */           fakeBlock.setColor(14671839);
/*     */         } else {
/*  69 */           fakeBlock.setColor(ColorUtils.getRGBColor(glassColor));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  75 */       if (connectivity != 63) {
/*  76 */         resetToCenterDimensions(dim);
/*     */         
/*  78 */         if (renderPass == 0) {
/*  79 */           fakeBlock.getTextureState().set(icons.getIcon(state.textureMatrix.getTextureIndex(ForgeDirection.UNKNOWN)));
/*     */         } else {
/*  81 */           fakeBlock.getTextureState().set(PipeIconProvider.TYPE.PipeStainedOverlay.getIcon());
/*     */         } 
/*     */         
/*  84 */         fixForRenderPass(dim);
/*     */         
/*  86 */         renderTwoWayBlock(renderblocks, fakeBlock, x, y, z, dim, connectivity ^ 0x3F);
/*  87 */         rendered = true;
/*     */       } 
/*     */ 
/*     */       
/*  91 */       for (int dir = 0; dir < 6; dir++) {
/*  92 */         int mask = 1 << dir;
/*     */         
/*  94 */         if ((connectivity & mask) != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  99 */           resetToCenterDimensions(dim);
/*     */ 
/*     */           
/* 102 */           dim[dir / 2] = (dir % 2 == 0) ? 0.0F : 0.75F;
/* 103 */           dim[dir / 2 + 3] = (dir % 2 == 0) ? 0.25F : 1.0F;
/*     */ 
/*     */           
/* 106 */           int renderMask = 3 << (dir & 0x6) ^ 0x3F;
/*     */           
/* 108 */           fixForRenderPass(dim);
/*     */ 
/*     */           
/* 111 */           if (renderPass == 0) {
/* 112 */             fakeBlock.getTextureState().set(icons.getIcon(state.textureMatrix.getTextureIndex(ForgeDirection.VALID_DIRECTIONS[dir])));
/*     */           } else {
/* 114 */             fakeBlock.getTextureState().set(PipeIconProvider.TYPE.PipeStainedOverlay.getIcon());
/*     */           } 
/*     */           
/* 117 */           renderTwoWayBlock(renderblocks, fakeBlock, x, y, z, dim, renderMask);
/* 118 */           rendered = true;
/*     */ 
/*     */           
/* 121 */           if ((Minecraft.func_71410_x()).field_71474_y.field_74347_j) {
/* 122 */             ForgeDirection side = ForgeDirection.getOrientation(dir);
/* 123 */             int px = x + side.offsetX;
/* 124 */             int py = y + side.offsetY;
/* 125 */             int pz = z + side.offsetZ;
/* 126 */             Block block = iblockaccess.func_147439_a(px, py, pz);
/* 127 */             if (!(block instanceof buildcraft.transport.BlockGenericPipe) && !block.func_149662_c()) {
/*     */               double[] blockBB;
/*     */               
/* 130 */               if (block instanceof net.minecraft.block.BlockChest) {
/*     */                 
/* 132 */                 blockBB = CHEST_BB;
/*     */               } else {
/* 134 */                 block.func_149719_a(iblockaccess, px, py, pz);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 142 */                 blockBB = new double[] { block.func_149665_z(), block.func_149704_x(), block.func_149706_B(), block.func_149669_A(), block.func_149753_y(), block.func_149693_C() };
/*     */               } 
/*     */ 
/*     */               
/* 146 */               if ((dir % 2 == 1 && blockBB[dir / 2] != 0.0D) || (dir % 2 == 0 && blockBB[dir / 2 + 3] != 1.0D)) {
/* 147 */                 resetToCenterDimensions(dim);
/*     */                 
/* 149 */                 if (dir % 2 == 1) {
/* 150 */                   dim[dir / 2] = 0.0F;
/* 151 */                   dim[dir / 2 + 3] = (float)blockBB[dir / 2];
/*     */                 } else {
/* 153 */                   dim[dir / 2] = (float)blockBB[dir / 2 + 3];
/* 154 */                   dim[dir / 2 + 3] = 1.0F;
/*     */                 } 
/*     */                 
/* 157 */                 fixForRenderPass(dim);
/*     */                 
/* 159 */                 renderTwoWayBlock(renderblocks, fakeBlock, x + side.offsetX, y + side.offsetY, z + side.offsetZ, dim, renderMask);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 167 */       fakeBlock.setColor(16777215);
/*     */     } 
/*     */     
/* 170 */     renderblocks.func_147782_a(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*     */     
/* 172 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/* 173 */       if (tile.hasPipePluggable(dir)) {
/* 174 */         PipePluggable p = tile.getPipePluggable(dir);
/* 175 */         IPipePluggableRenderer r = p.getRenderer();
/* 176 */         if (r != null) {
/* 177 */           r.renderPluggable(renderblocks, tile.getPipe(), dir, p, (ITextureStates)fakeBlock, renderPass, x, y, z);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 182 */     return rendered;
/*     */   }
/*     */   
/*     */   private void fixForRenderPass(float[] dim) {
/* 186 */     if (renderPass == 1) {
/* 187 */       int i; for (i = 0; i < 3; i++) {
/* 188 */         dim[i] = dim[i] + zFightOffset;
/*     */       }
/*     */       
/* 191 */       for (i = 3; i < 6; i++) {
/* 192 */         dim[i] = dim[i] - zFightOffset;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resetToCenterDimensions(float[] dim) {
/* 198 */     for (int i = 0; i < 3; i++) {
/* 199 */       dim[i] = 0.25F;
/* 200 */       dim[i + 3] = 0.75F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderTwoWayBlock(RenderBlocks renderblocks, FakeBlock stateHost, int x, int y, int z, float[] dim, int mask) {
/* 209 */     assert mask != 0;
/*     */     
/* 211 */     int c = stateHost.func_149635_D();
/* 212 */     float r = ((c & 0xFF0000) >> 16) / 255.0F;
/* 213 */     float g = ((c & 0xFF00) >> 8) / 255.0F;
/* 214 */     float b = (c & 0xFF) / 255.0F;
/*     */     
/* 216 */     stateHost.setRenderMask(mask);
/* 217 */     renderblocks.func_147782_a(dim[2], dim[0], dim[1], dim[5], dim[3], dim[4]);
/* 218 */     renderblocks.func_147736_d((Block)stateHost, x, y, z, r, g, b);
/*     */     
/* 220 */     stateHost.setRenderMask((mask & 0x15) << 1 | (mask & 0x2A) >> 1);
/* 221 */     renderblocks.func_147782_a(dim[5], dim[3], dim[4], dim[2], dim[0], dim[1]);
/* 222 */     renderblocks.func_147736_d((Block)stateHost, x, y, z, r * 0.67F, g * 0.67F, b * 0.67F);
/*     */     
/* 224 */     stateHost.setRenderAllSides();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
/* 234 */     boolean rendered = false;
/* 235 */     TileEntity tile = world.func_147438_o(x, y, z);
/*     */     
/* 237 */     if (tile instanceof TileGenericPipe) {
/* 238 */       TileGenericPipe pipeTile = (TileGenericPipe)tile;
/* 239 */       rendered = renderPipe(renderer, world, pipeTile, x, y, z);
/*     */     } 
/*     */     
/* 242 */     if (!rendered) {
/* 243 */       fixEmptyAlphaPass(x, y, z);
/*     */     }
/*     */     
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender3DInInventory(int modelId) {
/* 251 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderId() {
/* 256 */     return TransportProxy.pipeModel;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\render\PipeRendererWorld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */