/*     */ package buildcraft.core.lib.render;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.util.IIcon;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RenderUtils
/*     */ {
/*     */   public static void setGLColorFromInt(int color) {
/*  27 */     float red = (color >> 16 & 0xFF) / 255.0F;
/*  28 */     float green = (color >> 8 & 0xFF) / 255.0F;
/*  29 */     float blue = (color & 0xFF) / 255.0F;
/*  30 */     GL11.glColor4f(red, green, blue, 1.0F);
/*     */   }
/*     */   
/*     */   public static void drawBlockItem(RenderBlocks render, Tessellator tessellator, Block block, IIcon icon) {
/*  34 */     if (icon == null) {
/*     */       return;
/*     */     }
/*  37 */     tessellator.func_78382_b();
/*  38 */     tessellator.func_78375_b(0.0F, -1.0F, 0.0F);
/*  39 */     render.func_147768_a(block, 0.0D, 0.0D, 0.0D, icon);
/*  40 */     tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
/*  41 */     render.func_147806_b(block, 0.0D, 0.0D, 0.0D, icon);
/*  42 */     tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
/*  43 */     render.func_147761_c(block, 0.0D, 0.0D, 0.0D, icon);
/*  44 */     tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/*  45 */     render.func_147734_d(block, 0.0D, 0.0D, 0.0D, icon);
/*  46 */     tessellator.func_78375_b(-1.0F, 0.0F, 0.0F);
/*  47 */     render.func_147798_e(block, 0.0D, 0.0D, 0.0D, icon);
/*  48 */     tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
/*  49 */     render.func_147764_f(block, 0.0D, 0.0D, 0.0D, icon);
/*  50 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public static void drawBlockItem(RenderBlocks render, Tessellator tessellator, Block block, int decodedMeta) {
/*  54 */     tessellator.func_78382_b();
/*  55 */     IIcon icon = tryGetBlockIcon(block, 0, decodedMeta);
/*  56 */     if (icon != null) {
/*  57 */       tessellator.func_78375_b(0.0F, -1.0F, 0.0F);
/*  58 */       render.func_147768_a(block, 0.0D, 0.0D, 0.0D, icon);
/*     */     } 
/*  60 */     icon = tryGetBlockIcon(block, 1, decodedMeta);
/*  61 */     if (icon != null) {
/*  62 */       tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
/*  63 */       render.func_147806_b(block, 0.0D, 0.0D, 0.0D, icon);
/*     */     } 
/*  65 */     icon = tryGetBlockIcon(block, 2, decodedMeta);
/*  66 */     if (icon != null) {
/*  67 */       tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
/*  68 */       render.func_147761_c(block, 0.0D, 0.0D, 0.0D, icon);
/*     */     } 
/*  70 */     icon = tryGetBlockIcon(block, 3, decodedMeta);
/*  71 */     if (icon != null) {
/*  72 */       tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/*  73 */       render.func_147734_d(block, 0.0D, 0.0D, 0.0D, icon);
/*     */     } 
/*  75 */     icon = tryGetBlockIcon(block, 4, decodedMeta);
/*  76 */     if (icon != null) {
/*  77 */       tessellator.func_78375_b(-1.0F, 0.0F, 0.0F);
/*  78 */       render.func_147798_e(block, 0.0D, 0.0D, 0.0D, icon);
/*     */     } 
/*  80 */     icon = tryGetBlockIcon(block, 5, decodedMeta);
/*  81 */     if (icon != null) {
/*  82 */       tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
/*  83 */       render.func_147764_f(block, 0.0D, 0.0D, 0.0D, icon);
/*     */     } 
/*  85 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public static IIcon tryGetBlockIcon(Block block, int side, int decodedMeta) {
/*  89 */     IIcon icon = null;
/*     */     
/*     */     try {
/*  92 */       icon = block.func_149691_a(side, decodedMeta);
/*  93 */     } catch (Throwable t) {
/*     */       try {
/*  95 */         icon = block.func_149733_h(side);
/*  96 */       } catch (Throwable throwable) {}
/*     */     } 
/*     */ 
/*     */     
/* 100 */     return icon;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\render\RenderUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */