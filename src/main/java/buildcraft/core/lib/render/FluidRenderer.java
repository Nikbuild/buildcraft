/*     */ package buildcraft.core.lib.render;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ public final class FluidRenderer
/*     */ {
/*     */   public static final int DISPLAY_STAGES = 100;
/*  31 */   private static Map<Fluid, int[]> flowingRenderCache = (Map)new HashMap<Fluid, int>();
/*  32 */   private static Map<Fluid, int[]> stillRenderCache = (Map)new HashMap<Fluid, int>();
/*  33 */   private static final RenderEntityBlock.RenderInfo liquidBlock = new RenderEntityBlock.RenderInfo();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onTextureReload() {
/*  43 */     for (int[] ia : flowingRenderCache.values()) {
/*  44 */       for (int i : ia) {
/*  45 */         GL11.glDeleteLists(i, 1);
/*     */       }
/*     */     } 
/*  48 */     flowingRenderCache.clear();
/*     */     
/*  50 */     for (int[] ia : stillRenderCache.values()) {
/*  51 */       for (int i : ia) {
/*  52 */         GL11.glDeleteLists(i, 1);
/*     */       }
/*     */     } 
/*  55 */     stillRenderCache.clear();
/*     */   }
/*     */   
/*     */   public static IIcon getFluidTexture(FluidStack fluidStack, boolean flowing) {
/*  59 */     if (fluidStack == null) {
/*  60 */       return null;
/*     */     }
/*  62 */     return getFluidTexture(fluidStack.getFluid(), flowing);
/*     */   }
/*     */   public static IIcon getFluidTexture(Fluid fluid, boolean flowing) {
/*     */     TextureAtlasSprite textureAtlasSprite;
/*  66 */     if (fluid == null) {
/*  67 */       return null;
/*     */     }
/*  69 */     IIcon icon = flowing ? fluid.getFlowingIcon() : fluid.getStillIcon();
/*  70 */     if (icon == null) {
/*  71 */       textureAtlasSprite = ((TextureMap)Minecraft.func_71410_x().func_110434_K().func_110581_b(TextureMap.field_110575_b)).func_110572_b("missingno");
/*     */     }
/*  73 */     return (IIcon)textureAtlasSprite;
/*     */   }
/*     */   
/*     */   public static void setColorForFluidStack(FluidStack fluidstack) {
/*  77 */     if (fluidstack == null) {
/*     */       return;
/*     */     }
/*     */     
/*  81 */     int color = fluidstack.getFluid().getColor(fluidstack);
/*  82 */     RenderUtils.setGLColorFromInt(color);
/*     */   }
/*     */   
/*     */   public static int[] getFluidDisplayLists(FluidStack fluidStack, World world, boolean flowing) {
/*  86 */     if (fluidStack == null) {
/*  87 */       return null;
/*     */     }
/*  89 */     Fluid fluid = fluidStack.getFluid();
/*  90 */     if (fluid == null) {
/*  91 */       return null;
/*     */     }
/*  93 */     Map<Fluid, int[]> cache = flowing ? flowingRenderCache : stillRenderCache;
/*  94 */     int[] diplayLists = cache.get(fluid);
/*  95 */     if (diplayLists != null) {
/*  96 */       return diplayLists;
/*     */     }
/*     */     
/*  99 */     diplayLists = new int[100];
/*     */     
/* 101 */     if (fluid.getBlock() != null) {
/* 102 */       liquidBlock.baseBlock = fluid.getBlock();
/* 103 */       liquidBlock.texture = getFluidTexture(fluidStack, flowing);
/*     */     } else {
/* 105 */       liquidBlock.baseBlock = Blocks.field_150355_j;
/* 106 */       liquidBlock.texture = getFluidTexture(fluidStack, flowing);
/*     */     } 
/*     */     
/* 109 */     cache.put(fluid, diplayLists);
/*     */     
/* 111 */     GL11.glDisable(2896);
/* 112 */     GL11.glDisable(3042);
/* 113 */     GL11.glDisable(2884);
/*     */     
/* 115 */     for (int s = 0; s < 100; s++) {
/* 116 */       diplayLists[s] = GLAllocation.func_74526_a(1);
/* 117 */       GL11.glNewList(diplayLists[s], 4864);
/*     */       
/* 119 */       liquidBlock.minX = 0.009999999776482582D;
/* 120 */       liquidBlock.minY = 0.0D;
/* 121 */       liquidBlock.minZ = 0.009999999776482582D;
/*     */       
/* 123 */       liquidBlock.maxX = 0.9900000095367432D;
/* 124 */       liquidBlock.maxY = (Math.max(s, 1) / 100.0F);
/* 125 */       liquidBlock.maxZ = 0.9900000095367432D;
/*     */       
/* 127 */       RenderEntityBlock.INSTANCE.renderBlock(liquidBlock);
/*     */       
/* 129 */       GL11.glEndList();
/*     */     } 
/*     */     
/* 132 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 133 */     GL11.glEnable(2884);
/* 134 */     GL11.glEnable(3042);
/* 135 */     GL11.glEnable(2896);
/*     */     
/* 137 */     return diplayLists;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\render\FluidRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */