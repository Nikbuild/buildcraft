/*     */ package buildcraft.core.lib.render;
/*     */ 
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.model.PositionTextureVertex;
/*     */ import net.minecraft.client.model.TexturedQuad;
/*     */ import net.minecraft.client.renderer.Tessellator;
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
/*     */ public class ModelFrustum
/*     */ {
/*     */   public final float posX1;
/*     */   public final float posY1;
/*     */   public final float posZ1;
/*     */   public final float posX2;
/*     */   public final float posY2;
/*     */   public final float posZ2;
/*     */   private PositionTextureVertex[] vertexPositions;
/*     */   private TexturedQuad[] quadList;
/*     */   
/*     */   public ModelFrustum(ModelRenderer par1ModelRenderer, int textureOffsetX, int textureOffsetY, float originXI, float originYI, float originZI, int bottomWidth, int bottomDepth, int topWidth, int topDepth, int height, float scaleFactor) {
/*  49 */     float originX = originXI;
/*  50 */     float originY = originYI;
/*  51 */     float originZ = originZI;
/*     */     
/*  53 */     this.posX1 = originX;
/*  54 */     this.posY1 = originY;
/*  55 */     this.posZ1 = originZ;
/*     */     
/*  57 */     this.vertexPositions = new PositionTextureVertex[8];
/*  58 */     this.quadList = new TexturedQuad[6];
/*     */     
/*  60 */     float bottomDeltaX = (bottomWidth > topWidth) ? 0.0F : ((topWidth - bottomWidth) / 2.0F);
/*  61 */     float topDeltaX = (bottomWidth > topWidth) ? ((bottomWidth - topWidth) / 2.0F) : 0.0F;
/*     */     
/*  63 */     float bottomDeltaZ = (bottomDepth > topDepth) ? 0.0F : ((topDepth - bottomDepth) / 2.0F);
/*  64 */     float topDeltaZ = (bottomDepth > topDepth) ? ((bottomDepth - topDepth) / 2.0F) : 0.0F;
/*     */     
/*  66 */     float targetX = originX + Math.max(bottomWidth, topWidth);
/*  67 */     float targetY = originY + height;
/*  68 */     float targetZ = originZ + Math.max(bottomDepth, topDepth);
/*     */     
/*  70 */     this.posX2 = targetX;
/*  71 */     this.posY2 = targetY;
/*  72 */     this.posZ2 = targetZ;
/*     */     
/*  74 */     originX -= scaleFactor;
/*  75 */     originY -= scaleFactor;
/*  76 */     originZ -= scaleFactor;
/*  77 */     targetX += scaleFactor;
/*  78 */     targetY += scaleFactor;
/*  79 */     targetZ += scaleFactor;
/*     */     
/*  81 */     if (par1ModelRenderer.field_78809_i) {
/*  82 */       float var14 = targetX;
/*  83 */       targetX = originX;
/*  84 */       originX = var14;
/*     */     } 
/*     */     
/*  87 */     PositionTextureVertex var23 = new PositionTextureVertex(originX + bottomDeltaX, originY, originZ + bottomDeltaZ, 0.0F, 0.0F);
/*  88 */     PositionTextureVertex var15 = new PositionTextureVertex(targetX - bottomDeltaX, originY, originZ + bottomDeltaZ, 0.0F, 8.0F);
/*  89 */     PositionTextureVertex var16 = new PositionTextureVertex(targetX - topDeltaX, targetY, originZ + topDeltaZ, 8.0F, 8.0F);
/*  90 */     PositionTextureVertex var17 = new PositionTextureVertex(originX + topDeltaX, targetY, originZ + topDeltaZ, 8.0F, 0.0F);
/*     */     
/*  92 */     PositionTextureVertex var18 = new PositionTextureVertex(originX + bottomDeltaX, originY, targetZ - bottomDeltaZ, 0.0F, 0.0F);
/*  93 */     PositionTextureVertex var19 = new PositionTextureVertex(targetX - bottomDeltaX, originY, targetZ - bottomDeltaZ, 0.0F, 8.0F);
/*  94 */     PositionTextureVertex var20 = new PositionTextureVertex(targetX - topDeltaX, targetY, targetZ - topDeltaZ, 8.0F, 8.0F);
/*  95 */     PositionTextureVertex var21 = new PositionTextureVertex(originX + topDeltaX, targetY, targetZ - topDeltaZ, 8.0F, 0.0F);
/*  96 */     this.vertexPositions[0] = var23;
/*  97 */     this.vertexPositions[1] = var15;
/*  98 */     this.vertexPositions[2] = var16;
/*  99 */     this.vertexPositions[3] = var17;
/* 100 */     this.vertexPositions[4] = var18;
/* 101 */     this.vertexPositions[5] = var19;
/* 102 */     this.vertexPositions[6] = var20;
/* 103 */     this.vertexPositions[7] = var21;
/*     */     
/* 105 */     int depth = Math.max(bottomDepth, topDepth);
/* 106 */     int width = Math.max(bottomWidth, topWidth);
/*     */     
/* 108 */     this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] { var19, var15, var16, var20 }, textureOffsetX + depth + width, textureOffsetY + depth, textureOffsetX + depth + width + depth, textureOffsetY + depth + height, par1ModelRenderer.field_78801_a, par1ModelRenderer.field_78799_b);
/*     */ 
/*     */ 
/*     */     
/* 112 */     this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] { var23, var18, var21, var17 }, textureOffsetX, textureOffsetY + depth, textureOffsetX + depth, textureOffsetY + depth + height, par1ModelRenderer.field_78801_a, par1ModelRenderer.field_78799_b);
/*     */ 
/*     */ 
/*     */     
/* 116 */     this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] { var19, var18, var23, var15 }, textureOffsetX + depth, textureOffsetY, textureOffsetX + depth + width, textureOffsetY + depth, par1ModelRenderer.field_78801_a, par1ModelRenderer.field_78799_b);
/*     */ 
/*     */ 
/*     */     
/* 120 */     this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] { var16, var17, var21, var20 }, textureOffsetX + depth + width, textureOffsetY + depth, textureOffsetX + depth + width + width, textureOffsetY, par1ModelRenderer.field_78801_a, par1ModelRenderer.field_78799_b);
/*     */ 
/*     */ 
/*     */     
/* 124 */     this.quadList[4] = new TexturedQuad(new PositionTextureVertex[] { var15, var23, var17, var16 }, textureOffsetX + depth, textureOffsetY + depth, textureOffsetX + depth + width, textureOffsetY + depth + height, par1ModelRenderer.field_78801_a, par1ModelRenderer.field_78799_b);
/*     */ 
/*     */ 
/*     */     
/* 128 */     this.quadList[5] = new TexturedQuad(new PositionTextureVertex[] { var18, var19, var20, var21 }, textureOffsetX + depth + width + depth, textureOffsetY + depth, textureOffsetX + depth + width + depth + width, textureOffsetY + depth + height, par1ModelRenderer.field_78801_a, par1ModelRenderer.field_78799_b);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     if (par1ModelRenderer.field_78809_i) {
/* 135 */       for (TexturedQuad element : this.quadList) {
/* 136 */         element.func_78235_a();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Tessellator par1Tessellator, float par2) {
/* 145 */     for (TexturedQuad element : this.quadList)
/* 146 */       element.func_78236_a(par1Tessellator, par2); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\render\ModelFrustum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */