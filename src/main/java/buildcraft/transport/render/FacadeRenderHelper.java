/*     */ package buildcraft.transport.render;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.render.ITextureStates;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.api.transport.pluggable.IFacadePluggable;
/*     */ import buildcraft.core.lib.render.FakeBlock;
/*     */ import buildcraft.core.lib.render.TextureStateManager;
/*     */ import buildcraft.core.lib.utils.MatrixTranformations;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.util.IIcon;
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
/*     */ public final class FacadeRenderHelper
/*     */ {
/*     */   private static final float zFightOffset = 2.4414062E-4F;
/*  32 */   private static final float[][] zeroStateFacade = new float[3][2];
/*  33 */   private static final float[][] zeroStateSupport = new float[3][2];
/*  34 */   private static final float[] xOffsets = new float[6];
/*  35 */   private static final float[] yOffsets = new float[6];
/*  36 */   private static final float[] zOffsets = new float[6];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  47 */     zeroStateFacade[0][0] = 0.0F;
/*  48 */     zeroStateFacade[0][1] = 1.0F;
/*     */     
/*  50 */     zeroStateFacade[1][0] = 0.0F;
/*  51 */     zeroStateFacade[1][1] = 0.125F;
/*     */     
/*  53 */     zeroStateFacade[2][0] = 0.0F;
/*  54 */     zeroStateFacade[2][1] = 1.0F;
/*     */ 
/*     */     
/*  57 */     zeroStateSupport[0][0] = 0.25F;
/*  58 */     zeroStateSupport[0][1] = 0.75F;
/*     */     
/*  60 */     zeroStateSupport[1][0] = 0.125F;
/*  61 */     zeroStateSupport[1][1] = 0.25F;
/*     */     
/*  63 */     zeroStateSupport[2][0] = 0.25F;
/*  64 */     zeroStateSupport[2][1] = 0.75F;
/*     */     
/*  66 */     xOffsets[0] = 2.4414062E-4F;
/*  67 */     xOffsets[1] = 2.4414062E-4F;
/*  68 */     xOffsets[2] = 0.0F;
/*  69 */     xOffsets[3] = 0.0F;
/*  70 */     xOffsets[4] = 0.0F;
/*  71 */     xOffsets[5] = 0.0F;
/*     */     
/*  73 */     yOffsets[0] = 0.0F;
/*  74 */     yOffsets[1] = 0.0F;
/*  75 */     yOffsets[2] = 2.4414062E-4F;
/*  76 */     yOffsets[3] = 2.4414062E-4F;
/*  77 */     yOffsets[4] = 0.0F;
/*  78 */     yOffsets[5] = 0.0F;
/*     */     
/*  80 */     zOffsets[0] = 2.4414062E-4F;
/*  81 */     zOffsets[1] = 2.4414062E-4F;
/*  82 */     zOffsets[2] = 0.0F;
/*  83 */     zOffsets[3] = 0.0F;
/*  84 */     zOffsets[4] = 0.0F;
/*  85 */     zOffsets[5] = 0.0F;
/*     */   }
/*     */   
/*     */   private static void setRenderBounds(RenderBlocks renderblocks, float[][] rotated, ForgeDirection side) {
/*  89 */     renderblocks.func_147782_a((rotated[0][0] + xOffsets[side
/*  90 */           .ordinal()]), (rotated[1][0] + yOffsets[side
/*  91 */           .ordinal()]), (rotated[2][0] + zOffsets[side
/*  92 */           .ordinal()]), (rotated[0][1] - xOffsets[side
/*  93 */           .ordinal()]), (rotated[1][1] - yOffsets[side
/*  94 */           .ordinal()]), (rotated[2][1] - zOffsets[side
/*  95 */           .ordinal()]));
/*     */   }
/*     */   
/*     */   public static void pipeFacadeRenderer(RenderBlocks renderblocks, ITextureStates blockStateMachine, IPipeTile tile, int renderPass, int x, int y, int z, ForgeDirection direction, IFacadePluggable pluggable) {
/*  99 */     ITextureStates textureManager = blockStateMachine;
/* 100 */     IIcon[] textures = ((TextureStateManager)textureManager.getTextureState()).popArray();
/*     */     
/* 102 */     Block renderBlock = pluggable.getCurrentBlock();
/*     */     
/* 104 */     if (renderBlock != null && tile != null) {
/* 105 */       IBlockAccess facadeBlockAccess = new FacadeBlockAccess((IBlockAccess)tile.getWorld(), direction);
/*     */ 
/*     */       
/* 108 */       if (renderBlock.canRenderInPass(renderPass)) {
/* 109 */         int renderMeta = pluggable.getCurrentMetadata();
/*     */         
/* 111 */         for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 112 */           textures[side.ordinal()] = renderBlock.func_149673_e(facadeBlockAccess, tile
/* 113 */               .x(), tile.y(), tile.z(), side.ordinal());
/*     */           
/* 115 */           if (textures[side.ordinal()] == null) {
/* 116 */             textures[side.ordinal()] = renderBlock.func_149691_a(side.ordinal(), renderMeta);
/*     */           }
/* 118 */           if (side == direction || side == direction.getOpposite()) {
/* 119 */             blockStateMachine.setRenderSide(side, true);
/*     */           }
/* 121 */           else if (!(tile.getPipePluggable(side) instanceof IFacadePluggable)) {
/* 122 */             blockStateMachine.setRenderSide(side, true);
/*     */           } else {
/* 124 */             IFacadePluggable pluggable2 = (IFacadePluggable)tile.getPipePluggable(side);
/* 125 */             blockStateMachine.setRenderSide(side, (pluggable2.getCurrentBlock() == null));
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 130 */         if (renderBlock.func_149645_b() == 31) {
/* 131 */           if ((renderMeta & 0xC) == 4) {
/* 132 */             renderblocks.field_147875_q = 1;
/* 133 */             renderblocks.field_147873_r = 1;
/* 134 */             renderblocks.field_147867_u = 1;
/* 135 */             renderblocks.field_147865_v = 1;
/* 136 */           } else if ((renderMeta & 0xC) == 8) {
/* 137 */             renderblocks.field_147871_s = 1;
/* 138 */             renderblocks.field_147869_t = 1;
/*     */           } 
/*     */         }
/*     */         
/* 142 */         ((FakeBlock)blockStateMachine.getBlock()).setColor(renderBlock.func_149741_i(renderMeta));
/*     */         
/* 144 */         if (pluggable.isHollow()) {
/* 145 */           renderblocks.field_152631_f = true;
/* 146 */           float[][] rotated = MatrixTranformations.deepClone(zeroStateFacade);
/* 147 */           rotated[0][0] = 0.24902344F;
/* 148 */           rotated[0][1] = 0.75097656F;
/* 149 */           rotated[2][0] = 0.0F;
/* 150 */           rotated[2][1] = 0.24951172F;
/* 151 */           MatrixTranformations.transform(rotated, direction);
/* 152 */           setRenderBounds(renderblocks, rotated, direction);
/* 153 */           renderblocks.func_147736_d(blockStateMachine.getBlock(), x, y, z, 1.0F, 1.0F, 1.0F);
/*     */           
/* 155 */           rotated = MatrixTranformations.deepClone(zeroStateFacade);
/* 156 */           rotated[0][0] = 0.24902344F;
/* 157 */           rotated[0][1] = 0.75097656F;
/* 158 */           rotated[2][0] = 0.7504883F;
/* 159 */           MatrixTranformations.transform(rotated, direction);
/* 160 */           setRenderBounds(renderblocks, rotated, direction);
/* 161 */           renderblocks.func_147736_d(blockStateMachine.getBlock(), x, y, z, 1.0F, 1.0F, 1.0F);
/*     */           
/* 163 */           rotated = MatrixTranformations.deepClone(zeroStateFacade);
/* 164 */           rotated[0][0] = 0.0F;
/* 165 */           rotated[0][1] = 0.24951172F;
/* 166 */           MatrixTranformations.transform(rotated, direction);
/* 167 */           setRenderBounds(renderblocks, rotated, direction);
/* 168 */           renderblocks.func_147736_d(blockStateMachine.getBlock(), x, y, z, 1.0F, 1.0F, 1.0F);
/*     */           
/* 170 */           rotated = MatrixTranformations.deepClone(zeroStateFacade);
/* 171 */           rotated[0][0] = 0.7504883F;
/* 172 */           rotated[0][1] = 1.0F;
/* 173 */           MatrixTranformations.transform(rotated, direction);
/* 174 */           setRenderBounds(renderblocks, rotated, direction);
/* 175 */           renderblocks.func_147736_d(blockStateMachine.getBlock(), x, y, z, 1.0F, 1.0F, 1.0F);
/* 176 */           renderblocks.field_152631_f = false;
/*     */         } else {
/* 178 */           float[][] rotated = MatrixTranformations.deepClone(zeroStateFacade);
/* 179 */           MatrixTranformations.transform(rotated, direction);
/* 180 */           setRenderBounds(renderblocks, rotated, direction);
/* 181 */           renderblocks.func_147784_q(blockStateMachine.getBlock(), x, y, z);
/*     */         } 
/* 183 */         ((FakeBlock)blockStateMachine.getBlock()).setColor(16777215);
/*     */         
/* 185 */         if (renderBlock.func_149645_b() == 31) {
/* 186 */           renderblocks.field_147871_s = 0;
/* 187 */           renderblocks.field_147875_q = 0;
/* 188 */           renderblocks.field_147873_r = 0;
/* 189 */           renderblocks.field_147869_t = 0;
/* 190 */           renderblocks.field_147867_u = 0;
/* 191 */           renderblocks.field_147865_v = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 196 */     ((FakeBlock)blockStateMachine.getBlock()).setColor(16777215);
/*     */     
/* 198 */     ((TextureStateManager)textureManager.getTextureState()).pushArray();
/* 199 */     blockStateMachine.setRenderAllSides();
/*     */     
/* 201 */     textureManager.getTextureState().set(BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.PipeStructureCobblestone.ordinal()));
/*     */ 
/*     */     
/* 204 */     if (renderPass == 0 && !pluggable.isHollow() && renderBlock != null && renderBlock.func_149688_o().func_76218_k()) {
/* 205 */       float[][] rotated = MatrixTranformations.deepClone(zeroStateSupport);
/* 206 */       MatrixTranformations.transform(rotated, direction);
/*     */       
/* 208 */       renderblocks.func_147782_a(rotated[0][0], rotated[1][0], rotated[2][0], rotated[0][1], rotated[1][1], rotated[2][1]);
/* 209 */       renderblocks.func_147784_q(blockStateMachine.getBlock(), x, y, z);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\render\FacadeRenderHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */