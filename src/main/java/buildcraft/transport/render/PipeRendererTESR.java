/*     */ package buildcraft.transport.render;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.render.ITextureStates;
/*     */ import buildcraft.api.gates.IGateExpansion;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.api.transport.PipeWire;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import buildcraft.core.lib.render.RenderEntityBlock;
/*     */ import buildcraft.core.lib.utils.MatrixTranformations;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeRenderState;
/*     */ import buildcraft.transport.TileGenericPipe;
/*     */ import buildcraft.transport.gates.GatePluggable;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public class PipeRendererTESR
/*     */   extends TileEntitySpecialRenderer
/*     */ {
/*  42 */   public static final PipeRendererTESR INSTANCE = new PipeRendererTESR();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTextureReload() {
/*  48 */     PipeTransportPowerRenderer.clear();
/*  49 */     PipeTransportFluidsRenderer.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_147500_a(TileEntity tileentity, double x, double y, double z, float f) {
/*  54 */     if (BuildCraftCore.render == BuildCraftCore.RenderMode.NoDynamic) {
/*     */       return;
/*     */     }
/*     */     
/*  58 */     TileGenericPipe pipe = (TileGenericPipe)tileentity;
/*     */     
/*  60 */     if (pipe.pipe == null) {
/*     */       return;
/*     */     }
/*     */     
/*  64 */     renderGatesWires(pipe, x, y, z);
/*  65 */     renderPluggables(pipe, x, y, z);
/*     */     
/*  67 */     PipeTransportRenderer renderer = PipeTransportRenderer.RENDERER_MAP.get(pipe.pipe.transport.getClass());
/*  68 */     if (renderer != null) {
/*  69 */       renderer.render(renderer.useServerTileIfPresent() ? (Pipe)((IPipeTile)CoreProxy.proxy.getServerTile((TileEntity)pipe)).getPipe() : pipe.pipe, x, y, z, f);
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderGatesWires(TileGenericPipe pipe, double x, double y, double z) {
/*  74 */     PipeRenderState state = pipe.renderState;
/*     */     
/*  76 */     if (state.wireMatrix.hasWire(PipeWire.RED)) {
/*  77 */       pipeWireRender(pipe, 0.25F, 0.75F, 0.25F, PipeWire.RED, x, y, z);
/*     */     }
/*     */     
/*  80 */     if (state.wireMatrix.hasWire(PipeWire.BLUE)) {
/*  81 */       pipeWireRender(pipe, 0.75F, 0.75F, 0.75F, PipeWire.BLUE, x, y, z);
/*     */     }
/*     */     
/*  84 */     if (state.wireMatrix.hasWire(PipeWire.GREEN)) {
/*  85 */       pipeWireRender(pipe, 0.75F, 0.25F, 0.25F, PipeWire.GREEN, x, y, z);
/*     */     }
/*     */     
/*  88 */     if (state.wireMatrix.hasWire(PipeWire.YELLOW)) {
/*  89 */       pipeWireRender(pipe, 0.25F, 0.25F, 0.75F, PipeWire.YELLOW, x, y, z);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void pipeWireRender(TileGenericPipe pipe, float cx, float cy, float cz, PipeWire color, double x, double y, double z) {
/*  95 */     PipeRenderState state = pipe.renderState;
/*     */     
/*  97 */     float minX = 0.25F;
/*  98 */     float minY = 0.25F;
/*  99 */     float minZ = 0.25F;
/*     */     
/* 101 */     float maxX = 0.75F;
/* 102 */     float maxY = 0.75F;
/* 103 */     float maxZ = 0.75F;
/*     */     
/* 105 */     boolean foundX = false, foundY = false, foundZ = false;
/*     */     
/* 107 */     if (state.wireMatrix.isWireConnected(color, ForgeDirection.WEST)) {
/* 108 */       minX = 0.0F;
/* 109 */       foundX = true;
/*     */     } 
/*     */     
/* 112 */     if (state.wireMatrix.isWireConnected(color, ForgeDirection.EAST)) {
/* 113 */       maxX = 1.0F;
/* 114 */       foundX = true;
/*     */     } 
/*     */     
/* 117 */     if (state.wireMatrix.isWireConnected(color, ForgeDirection.DOWN)) {
/* 118 */       minY = 0.0F;
/* 119 */       foundY = true;
/*     */     } 
/*     */     
/* 122 */     if (state.wireMatrix.isWireConnected(color, ForgeDirection.UP)) {
/* 123 */       maxY = 1.0F;
/* 124 */       foundY = true;
/*     */     } 
/*     */     
/* 127 */     if (state.wireMatrix.isWireConnected(color, ForgeDirection.NORTH)) {
/* 128 */       minZ = 0.0F;
/* 129 */       foundZ = true;
/*     */     } 
/*     */     
/* 132 */     if (state.wireMatrix.isWireConnected(color, ForgeDirection.SOUTH)) {
/* 133 */       maxZ = 1.0F;
/* 134 */       foundZ = true;
/*     */     } 
/*     */     
/* 137 */     boolean center = false;
/*     */     
/* 139 */     if (minX == 0.0F && maxX != 1.0F && (foundY || foundZ)) {
/* 140 */       if (cx == 0.25F) {
/* 141 */         maxX = 0.25F;
/*     */       } else {
/* 143 */         center = true;
/*     */       } 
/*     */     }
/*     */     
/* 147 */     if (minX != 0.0F && maxX == 1.0F && (foundY || foundZ)) {
/* 148 */       if (cx == 0.75F) {
/* 149 */         minX = 0.75F;
/*     */       } else {
/* 151 */         center = true;
/*     */       } 
/*     */     }
/*     */     
/* 155 */     if (minY == 0.0F && maxY != 1.0F && (foundX || foundZ)) {
/* 156 */       if (cy == 0.25F) {
/* 157 */         maxY = 0.25F;
/*     */       } else {
/* 159 */         center = true;
/*     */       } 
/*     */     }
/*     */     
/* 163 */     if (minY != 0.0F && maxY == 1.0F && (foundX || foundZ)) {
/* 164 */       if (cy == 0.75F) {
/* 165 */         minY = 0.75F;
/*     */       } else {
/* 167 */         center = true;
/*     */       } 
/*     */     }
/*     */     
/* 171 */     if (minZ == 0.0F && maxZ != 1.0F && (foundX || foundY)) {
/* 172 */       if (cz == 0.25F) {
/* 173 */         maxZ = 0.25F;
/*     */       } else {
/* 175 */         center = true;
/*     */       } 
/*     */     }
/*     */     
/* 179 */     if (minZ != 0.0F && maxZ == 1.0F && (foundX || foundY)) {
/* 180 */       if (cz == 0.75F) {
/* 181 */         minZ = 0.75F;
/*     */       } else {
/* 183 */         center = true;
/*     */       } 
/*     */     }
/*     */     
/* 187 */     boolean found = (foundX || foundY || foundZ);
/*     */     
/* 189 */     GL11.glPushMatrix();
/* 190 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 191 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/*     */     
/* 193 */     float scale = 1.001F;
/* 194 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 195 */     GL11.glScalef(scale, scale, scale);
/* 196 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/*     */ 
/*     */     
/* 199 */     func_147499_a(TextureMap.field_110575_b);
/*     */     
/* 201 */     RenderEntityBlock.RenderInfo renderBox = new RenderEntityBlock.RenderInfo();
/* 202 */     renderBox.texture = BuildCraftTransport.instance.wireIconProvider.getIcon(state.wireMatrix.getWireIconIndex(color));
/* 203 */     boolean isLit = ((state.wireMatrix.getWireIconIndex(color) & 0x1) > 0);
/*     */ 
/*     */ 
/*     */     
/* 207 */     if (minZ != 0.25F || maxZ != 0.75F || !found) {
/* 208 */       renderBox.setBounds((cx == 0.25F) ? (cx - 0.05F) : cx, (cy == 0.25F) ? (cy - 0.05F) : cy, minZ, (cx == 0.25F) ? cx : (cx + 0.05F), (cy == 0.25F) ? cy : (cy + 0.05F), maxZ);
/*     */       
/* 210 */       renderLitBox(renderBox, isLit);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 215 */     if (minX != 0.25F || maxX != 0.75F || !found) {
/* 216 */       renderBox.setBounds(minX, (cy == 0.25F) ? (cy - 0.05F) : cy, (cz == 0.25F) ? (cz - 0.05F) : cz, maxX, (cy == 0.25F) ? cy : (cy + 0.05F), (cz == 0.25F) ? cz : (cz + 0.05F));
/*     */       
/* 218 */       renderLitBox(renderBox, isLit);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 223 */     if (minY != 0.25F || maxY != 0.75F || !found) {
/* 224 */       renderBox.setBounds((cx == 0.25F) ? (cx - 0.05F) : cx, minY, (cz == 0.25F) ? (cz - 0.05F) : cz, (cx == 0.25F) ? cx : (cx + 0.05F), maxY, (cz == 0.25F) ? cz : (cz + 0.05F));
/*     */       
/* 226 */       renderLitBox(renderBox, isLit);
/*     */     } 
/*     */     
/* 229 */     if (center || !found) {
/* 230 */       renderBox.setBounds((cx == 0.25F) ? (cx - 0.05F) : cx, (cy == 0.25F) ? (cy - 0.05F) : cy, (cz == 0.25F) ? (cz - 0.05F) : cz, (cx == 0.25F) ? cx : (cx + 0.05F), (cy == 0.25F) ? cy : (cy + 0.05F), (cz == 0.25F) ? cz : (cz + 0.05F));
/*     */       
/* 232 */       renderLitBox(renderBox, isLit);
/*     */     } 
/*     */     
/* 235 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void renderPluggables(TileGenericPipe pipe, double x, double y, double z) {
/* 239 */     TileEntityRendererDispatcher.field_147556_a.field_147553_e.func_110577_a(TextureMap.field_110575_b);
/*     */     
/* 241 */     for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
/* 242 */       PipePluggable pluggable = pipe.getPipePluggable(direction);
/* 243 */       if (pluggable != null && pluggable.getDynamicRenderer() != null) {
/* 244 */         pluggable.getDynamicRenderer().renderPluggable(pipe.getPipe(), direction, pluggable, x, y, z);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void renderGateStatic(RenderBlocks renderblocks, ForgeDirection direction, GatePluggable gate, ITextureStates blockStateMachine, int x, int y, int z) {
/* 250 */     blockStateMachine.getTextureState().set(gate.getLogic().getGateIcon());
/*     */     
/* 252 */     float trim = 0.1F;
/* 253 */     float[][] zeroState = new float[3][2];
/* 254 */     float min = 0.25F + trim / 2.0F;
/* 255 */     float max = 0.75F - trim / 2.0F;
/*     */ 
/*     */     
/* 258 */     zeroState[0][0] = min;
/* 259 */     zeroState[0][1] = max;
/*     */     
/* 261 */     zeroState[1][0] = 0.15F;
/* 262 */     zeroState[1][1] = 0.251F;
/*     */     
/* 264 */     zeroState[2][0] = min;
/* 265 */     zeroState[2][1] = max;
/*     */     
/* 267 */     float[][] rotated = MatrixTranformations.deepClone(zeroState);
/* 268 */     MatrixTranformations.transform(rotated, direction);
/*     */     
/* 270 */     blockStateMachine.setRenderAllSides();
/* 271 */     renderblocks.func_147782_a(rotated[0][0], rotated[1][0], rotated[2][0], rotated[0][1], rotated[1][1], rotated[2][1]);
/* 272 */     renderblocks.func_147784_q(blockStateMachine.getBlock(), x, y, z);
/*     */   }
/*     */   public static void renderGate(double x, double y, double z, GatePluggable gate, ForgeDirection direction) {
/*     */     IIcon lightIcon;
/* 276 */     GL11.glPushMatrix();
/* 277 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 278 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/*     */ 
/*     */     
/* 281 */     if (gate.isLit) {
/* 282 */       lightIcon = gate.getLogic().getIconLit();
/*     */     } else {
/* 284 */       lightIcon = gate.getLogic().getIconDark();
/*     */     } 
/*     */     
/* 287 */     float translateCenter = 0.0F;
/*     */     
/* 289 */     renderGate(lightIcon, 0, 0.1F, 0.0F, 0.0F, direction, gate.isLit, 1);
/*     */     
/* 291 */     float pulseStage = gate.getPulseStage() * 2.0F;
/*     */     
/* 293 */     if (gate.isPulsing || pulseStage != 0.0F) {
/* 294 */       IIcon gateIcon = gate.getLogic().getGateIcon();
/*     */ 
/*     */       
/* 297 */       float amplitude = 0.1F;
/* 298 */       float start = 0.01F;
/*     */       
/* 300 */       if (pulseStage < 1.0F) {
/* 301 */         translateCenter = pulseStage * amplitude + start;
/*     */       } else {
/* 303 */         translateCenter = amplitude - (pulseStage - 1.0F) * amplitude + start;
/*     */       } 
/*     */       
/* 306 */       renderGate(gateIcon, 0, 0.13F, translateCenter, translateCenter, direction, false, 2);
/* 307 */       renderGate(lightIcon, 0, 0.13F, translateCenter, translateCenter, direction, gate.isLit, 0);
/*     */     } 
/*     */     
/* 310 */     IIcon materialIcon = gate.getMaterial().getIconBlock();
/* 311 */     if (materialIcon != null) {
/* 312 */       renderGate(materialIcon, 1, 0.13F, translateCenter, translateCenter, direction, false, 1);
/*     */     }
/*     */     
/* 315 */     for (IGateExpansion expansion : gate.getExpansions()) {
/* 316 */       renderGate(expansion.getOverlayBlock(), 2, 0.13F, translateCenter, translateCenter, direction, false, 0);
/*     */     }
/*     */     
/* 319 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private static void renderGate(IIcon icon, int layer, float trim, float translateCenter, float extraDepth, ForgeDirection direction, boolean isLit, int sideRenderingMode) {
/* 323 */     RenderEntityBlock.RenderInfo renderBox = new RenderEntityBlock.RenderInfo();
/* 324 */     renderBox.texture = icon;
/*     */     
/* 326 */     float[][] zeroState = new float[3][2];
/* 327 */     float min = 0.25F + trim / 2.0F;
/* 328 */     float max = 0.75F - trim / 2.0F;
/*     */ 
/*     */     
/* 331 */     zeroState[0][0] = min;
/* 332 */     zeroState[0][1] = max;
/*     */     
/* 334 */     zeroState[1][0] = 0.15F - 0.001F * layer;
/* 335 */     zeroState[1][1] = 0.251F + 0.01F * layer + extraDepth;
/*     */     
/* 337 */     zeroState[2][0] = min;
/* 338 */     zeroState[2][1] = max;
/*     */ 
/*     */     
/* 341 */     if (translateCenter != 0.0F) {
/* 342 */       GL11.glPushMatrix();
/* 343 */       float xt = direction.offsetX * translateCenter, yt = direction.offsetY * translateCenter, zt = direction.offsetZ * translateCenter;
/*     */ 
/*     */       
/* 346 */       GL11.glTranslatef(xt, yt, zt);
/*     */     } 
/*     */     
/* 349 */     float[][] rotated = MatrixTranformations.deepClone(zeroState);
/* 350 */     MatrixTranformations.transform(rotated, direction);
/*     */     
/* 352 */     switch (sideRenderingMode) {
/*     */       case 0:
/* 354 */         renderBox.setRenderSingleSide(direction.ordinal());
/*     */         break;
/*     */       case 1:
/* 357 */         renderBox.setRenderSingleSide(direction.ordinal());
/* 358 */         renderBox.renderSide[direction.ordinal() ^ 0x1] = true;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 364 */     renderBox.setBounds(rotated[0][0], rotated[1][0], rotated[2][0], rotated[0][1], rotated[1][1], rotated[2][1]);
/* 365 */     renderLitBox(renderBox, isLit);
/* 366 */     if (translateCenter != 0.0F) {
/* 367 */       GL11.glPopMatrix();
/*     */     }
/*     */   }
/*     */   
/*     */   private static void renderLitBox(RenderEntityBlock.RenderInfo info, boolean isLit) {
/* 372 */     RenderEntityBlock.INSTANCE.renderBlock(info);
/*     */     
/* 374 */     float lastX = OpenGlHelper.lastBrightnessX;
/* 375 */     float lastY = OpenGlHelper.lastBrightnessY;
/* 376 */     if (isLit) {
/* 377 */       GL11.glPushMatrix();
/* 378 */       GL11.glEnable(3042);
/* 379 */       GL11.glDisable(2896);
/* 380 */       GL11.glBlendFunc(1, 1);
/* 381 */       GL11.glDepthMask(true);
/* 382 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 61680.0F, 0.0F);
/* 383 */       RenderEntityBlock.INSTANCE.renderBlock(info);
/* 384 */       GL11.glDisable(3042);
/* 385 */       GL11.glEnable(2896);
/* 386 */       GL11.glPopMatrix();
/*     */     } 
/* 388 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, lastX, lastY);
/*     */   }
/*     */   
/*     */   public boolean isOpenOrientation(PipeRenderState state, ForgeDirection direction) {
/* 392 */     int connections = 0;
/*     */     
/* 394 */     ForgeDirection targetOrientation = ForgeDirection.UNKNOWN;
/*     */     
/* 396 */     for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {
/* 397 */       if (state.pipeConnectionMatrix.isConnected(o)) {
/*     */         
/* 399 */         connections++;
/*     */         
/* 401 */         if (connections == 1) {
/* 402 */           targetOrientation = o;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 407 */     if (connections > 1 || connections == 0) {
/* 408 */       return false;
/*     */     }
/*     */     
/* 411 */     return (targetOrientation.getOpposite() == direction);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\render\PipeRendererTESR.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */