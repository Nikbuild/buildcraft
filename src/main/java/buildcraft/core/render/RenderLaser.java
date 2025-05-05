/*     */ package buildcraft.core.render;
/*     */ 
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.core.EntityLaser;
/*     */ import buildcraft.core.LaserData;
/*     */ import buildcraft.core.lib.render.RenderEntityBlock;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ public class RenderLaser
/*     */   extends Render
/*     */ {
/*     */   public static final float STEP = 0.04F;
/*  32 */   protected static ModelBase model = new ModelBase()
/*     */     {
/*     */     
/*     */     };
/*     */   
/*     */   private static ModelRenderer[] box;
/*     */   
/*     */   private static int[][][] scaledBoxes;
/*     */   
/*     */   public static void onTextureReload() {
/*  42 */     scaledBoxes = (int[][][])null;
/*     */   }
/*     */   
/*     */   private static ModelRenderer getBox(int index) {
/*  46 */     if (box == null) {
/*  47 */       box = new ModelRenderer[40];
/*     */       
/*  49 */       for (int j = 0; j < box.length; j++) {
/*  50 */         box[j] = new ModelRenderer(model, box.length - j, 0);
/*  51 */         box[j].func_78789_a(0.0F, -0.5F, -0.5F, 16, 1, 1);
/*  52 */         (box[j]).field_78800_c = 0.0F;
/*  53 */         (box[j]).field_78797_d = 0.0F;
/*  54 */         (box[j]).field_78798_e = 0.0F;
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     return box[index];
/*     */   }
/*     */   
/*     */   private static void initScaledBoxes() {
/*  62 */     if (scaledBoxes == null) {
/*  63 */       scaledBoxes = new int[2][100][20];
/*     */       
/*  65 */       for (int flags = 0; flags < 2; flags++) {
/*  66 */         for (int size = 0; size < 100; size++) {
/*  67 */           for (int i = 0; i < 20; i++) {
/*  68 */             scaledBoxes[flags][size][i] = GLAllocation.func_74526_a(1);
/*  69 */             GL11.glNewList(scaledBoxes[flags][size][i], 4864);
/*     */             
/*  71 */             RenderEntityBlock.RenderInfo block = new RenderEntityBlock.RenderInfo();
/*     */             
/*  73 */             float minSize = 0.2F * size / 100.0F;
/*  74 */             float maxSize = 0.4F * size / 100.0F;
/*     */ 
/*     */ 
/*     */             
/*  78 */             float range = maxSize - minSize;
/*     */             
/*  80 */             float diff = MathHelper.func_76134_b(i / 20.0F * 2.0F * 3.1415927F) * range / 2.0F;
/*     */ 
/*     */             
/*  83 */             block.minX = 0.0D;
/*  84 */             block.minY = (-maxSize / 2.0F + diff);
/*  85 */             block.minZ = (-maxSize / 2.0F + diff);
/*     */             
/*  87 */             block.maxX = 0.03999999910593033D;
/*  88 */             block.maxY = (maxSize / 2.0F - diff);
/*  89 */             block.maxZ = (maxSize / 2.0F - diff);
/*     */             
/*  91 */             if (flags == 1) {
/*  92 */               block.brightness = 15;
/*     */             }
/*     */             
/*  95 */             RenderEntityBlock.INSTANCE.renderBlock(block);
/*     */             
/*  97 */             GL11.glEndList();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_76986_a(Entity entity, double x, double y, double z, float f, float f1) {
/* 106 */     doRender((EntityLaser)entity, x, y, z, f, f1);
/*     */   }
/*     */ 
/*     */   
/*     */   private void doRender(EntityLaser laser, double x, double y, double z, float f, float f1) {
/* 111 */     if (!laser.isVisible() || laser.getTexture() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 115 */     GL11.glPushMatrix();
/* 116 */     GL11.glPushAttrib(8192);
/* 117 */     GL11.glDisable(2896);
/*     */     
/* 119 */     Position offset = laser.renderOffset();
/* 120 */     GL11.glTranslated(x + offset.x, y + offset.y, z + offset.z);
/*     */ 
/*     */ 
/*     */     
/* 124 */     doRenderLaser(this.field_76990_c.field_78724_e, laser.data, laser.getTexture());
/*     */     
/* 126 */     GL11.glPopAttrib();
/* 127 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void doRenderLaserWave(TextureManager textureManager, LaserData laser, ResourceLocation texture) {
/* 131 */     if (!laser.isVisible || texture == null) {
/*     */       return;
/*     */     }
/*     */     
/* 135 */     GL11.glPushMatrix();
/*     */     
/* 137 */     GL11.glTranslated(laser.head.x, laser.head.y, laser.head.z);
/* 138 */     laser.update();
/*     */     
/* 140 */     GL11.glRotatef((float)laser.angleZ, 0.0F, 1.0F, 0.0F);
/* 141 */     GL11.glRotatef((float)laser.angleY, 0.0F, 0.0F, 1.0F);
/*     */     
/* 143 */     textureManager.func_110577_a(texture);
/*     */     
/* 145 */     int indexList = 0;
/*     */     
/* 147 */     initScaledBoxes();
/*     */     
/* 149 */     double x1 = laser.wavePosition;
/* 150 */     double x2 = x1 + ((scaledBoxes[0][0]).length * 0.04F);
/* 151 */     double x3 = laser.renderSize;
/*     */     
/* 153 */     doRenderLaserLine(x1, laser.laserTexAnimation);
/*     */     double i;
/* 155 */     for (i = x1; i <= x2 && i <= laser.renderSize; i += 0.03999999910593033D) {
/* 156 */       GL11.glCallList(scaledBoxes[laser.isGlowing ? 1 : 0][(int)(laser.waveSize * 99.0F)][indexList]);
/* 157 */       indexList = (indexList + 1) % (scaledBoxes[0][0]).length;
/* 158 */       GL11.glTranslated(0.03999999910593033D, 0.0D, 0.0D);
/*     */     } 
/*     */     
/* 161 */     if (x2 < x3) {
/* 162 */       doRenderLaserLine(x3 - x2, laser.laserTexAnimation);
/*     */     }
/*     */     
/* 165 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void doRenderLaser(TextureManager textureManager, LaserData laser, ResourceLocation texture) {
/* 170 */     if (!laser.isVisible || texture == null) {
/*     */       return;
/*     */     }
/*     */     
/* 174 */     GL11.glPushMatrix();
/*     */     
/* 176 */     GL11.glTranslated(laser.head.x, laser.head.y, laser.head.z);
/* 177 */     laser.update();
/*     */     
/* 179 */     GL11.glRotatef((float)laser.angleZ, 0.0F, 1.0F, 0.0F);
/* 180 */     GL11.glRotatef((float)laser.angleY, 0.0F, 0.0F, 1.0F);
/*     */     
/* 182 */     textureManager.func_110577_a(texture);
/*     */     
/* 184 */     initScaledBoxes();
/*     */     
/* 186 */     if (laser.isGlowing) {
/* 187 */       float lastBrightnessX = OpenGlHelper.lastBrightnessX;
/* 188 */       float lastBrightnessY = OpenGlHelper.lastBrightnessY;
/*     */       
/* 190 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/* 191 */       GL11.glDisable(2896);
/*     */       
/* 193 */       doRenderLaserLine(laser.renderSize, laser.laserTexAnimation);
/*     */       
/* 195 */       GL11.glEnable(2896);
/* 196 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, lastBrightnessX, lastBrightnessY);
/*     */     } else {
/* 198 */       doRenderLaserLine(laser.renderSize, laser.laserTexAnimation);
/*     */     } 
/*     */     
/* 201 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private static void doRenderLaserLine(double len, int texId) {
/* 205 */     float lasti = 0.0F;
/*     */     
/* 207 */     if (len - 1.0D > 0.0D) {
/* 208 */       float i; for (i = 0.0F; i <= len - 1.0D; i++) {
/* 209 */         getBox(texId).func_78785_a(0.0625F);
/* 210 */         GL11.glTranslated(1.0D, 0.0D, 0.0D);
/* 211 */         lasti = i;
/*     */       } 
/* 213 */       lasti++;
/*     */     } 
/*     */     
/* 216 */     GL11.glPushMatrix();
/* 217 */     GL11.glScalef((float)len - lasti, 1.0F, 1.0F);
/* 218 */     getBox(texId).func_78785_a(0.0625F);
/* 219 */     GL11.glPopMatrix();
/*     */     
/* 221 */     GL11.glTranslated((float)(len - lasti), 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation func_110775_a(Entity entity) {
/* 226 */     return ((EntityLaser)entity).getTexture();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\render\RenderLaser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */