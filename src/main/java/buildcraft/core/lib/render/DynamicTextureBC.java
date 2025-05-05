/*     */ package buildcraft.core.lib.render;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ 
/*     */ public class DynamicTextureBC
/*     */ {
/*     */   public final int width;
/*     */   public final int height;
/*     */   public int[] colorMap;
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected DynamicTexture dynamicTexture;
/*     */   
/*     */   public DynamicTextureBC(int iWidth, int iHeight) {
/*  18 */     this.width = iWidth;
/*  19 */     this.height = iHeight;
/*  20 */     if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
/*  21 */       createDynamicTexture();
/*     */     } else {
/*  23 */       this.colorMap = new int[iWidth * iHeight];
/*     */     } 
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private void createDynamicTexture() {
/*  29 */     this.dynamicTexture = new DynamicTexture(this.width, this.height);
/*  30 */     this.colorMap = this.dynamicTexture.func_110565_c();
/*     */   }
/*     */   
/*     */   public void setColord(int index, double r, double g, double b, double a) {
/*  34 */     int i = (int)(a * 255.0D);
/*  35 */     int j = (int)(r * 255.0D);
/*  36 */     int k = (int)(g * 255.0D);
/*  37 */     int l = (int)(b * 255.0D);
/*  38 */     this.colorMap[index] = i << 24 | j << 16 | k << 8 | l;
/*     */   }
/*     */   
/*     */   public void setColord(int x, int y, double r, double g, double b, double a) {
/*  42 */     setColord(x + y * this.width, r, g, b, a);
/*     */   }
/*     */   
/*     */   public void setColori(int index, int r, int g, int b, int a) {
/*  46 */     this.colorMap[index] = (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
/*     */   }
/*     */   
/*     */   public void setColori(int x, int y, int r, int g, int b, int a) {
/*  50 */     setColori(x + y * this.width, r, g, b, a);
/*     */   }
/*     */   
/*     */   public void setColor(int x, int y, int color) {
/*  54 */     this.colorMap[x + y * this.width] = color;
/*     */   }
/*     */   
/*     */   public void setColor(int x, int y, int color, float alpha) {
/*  58 */     int a = (int)(alpha * 255.0F);
/*     */     
/*  60 */     this.colorMap[x + y * this.width] = a << 24 | color;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void updateTexture() {
/*  65 */     this.dynamicTexture.func_110564_a();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void draw(int screenX, int screenY, float zLevel) {
/*  71 */     draw(screenX, screenY, zLevel, 0, 0, this.width, this.height);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void draw(int screenX, int screenY, float zLevel, int clipX, int clipY, int clipWidth, int clipHeight) {
/*  76 */     updateTexture();
/*     */     
/*  78 */     float f = 1.0F / this.width;
/*  79 */     float f1 = 1.0F / this.height;
/*  80 */     Tessellator tessellator = Tessellator.field_78398_a;
/*  81 */     tessellator.func_78382_b();
/*  82 */     tessellator.func_78374_a((screenX + 0), (screenY + clipHeight), zLevel, ((clipX + 0) * f), ((clipY + clipHeight) * f1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     tessellator.func_78374_a((screenX + clipWidth), (screenY + clipHeight), zLevel, ((clipX + clipWidth) * f), ((clipY + clipHeight) * f1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     tessellator.func_78374_a((screenX + clipWidth), (screenY + 0), zLevel, ((clipX + clipWidth) * f), ((clipY + 0) * f1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     tessellator.func_78374_a((screenX + 0), (screenY + 0), zLevel, ((clipX + 0) * f), ((clipY + 0) * f1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     tessellator.func_78381_a();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\render\DynamicTextureBC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */