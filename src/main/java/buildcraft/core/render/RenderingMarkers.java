/*     */ package buildcraft.core.render;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
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
/*     */ public class RenderingMarkers
/*     */   extends BCSimpleBlockRenderingHandler
/*     */ {
/*  24 */   public static final double[][][] frontX = new double[6][3][4];
/*  25 */   public static final double[][][] frontZ = new double[6][3][4];
/*  26 */   public static final double[][][] frontY = new double[6][3][4];
/*  27 */   public static final int[] metaToOld = new int[6];
/*     */   
/*     */   public RenderingMarkers() {
/*  30 */     initializeMarkerMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {}
/*     */ 
/*     */   
/*     */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
/*  39 */     Tessellator tessellator = Tessellator.field_78398_a;
/*  40 */     float f = block.func_149677_c(world, x, y, z);
/*     */     
/*  42 */     if (block.func_149750_m() > 0) {
/*  43 */       f = 1.0F;
/*     */     }
/*     */     
/*  46 */     tessellator.func_78386_a(f, f, f);
/*  47 */     renderMarkerWithMeta(world, block, x, y, z, world.func_72805_g(x, y, z));
/*     */     
/*  49 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender3DInInventory(int modelId) {
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderId() {
/*  59 */     return BuildCraftCore.markerModel;
/*     */   }
/*     */   
/*     */   public static double[][] safeClone(double[][] d) {
/*  63 */     double[][] ret = new double[d.length][(d[0]).length];
/*     */     
/*  65 */     for (int i = 0; i < d.length; i++) {
/*  66 */       System.arraycopy(d[i], 0, ret[i], 0, (d[0]).length);
/*     */     }
/*     */     
/*  69 */     return ret;
/*     */   }
/*     */   
/*     */   public static void initializeMarkerMatrix() {
/*  73 */     metaToOld[0] = 0;
/*  74 */     metaToOld[1] = 5;
/*  75 */     metaToOld[2] = 4;
/*  76 */     metaToOld[3] = 3;
/*  77 */     metaToOld[4] = 2;
/*  78 */     metaToOld[5] = 1;
/*     */     
/*  80 */     double[][] frontXBase = { { -0.0625D, -0.0625D, -0.0625D, -0.0625D }, { 1.0D, 0.0D, 0.0D, 1.0D }, { -0.5D, -0.5D, 0.5D, 0.5D } };
/*     */     
/*  82 */     frontX[3] = safeClone(frontXBase);
/*  83 */     rotateFace(frontX[3]);
/*  84 */     rotateFace(frontX[3]);
/*  85 */     rotateFace(frontX[3]);
/*     */     
/*  87 */     frontX[4] = safeClone(frontXBase);
/*  88 */     rotateFace(frontX[4]);
/*     */     
/*  90 */     frontX[5] = safeClone(frontXBase);
/*     */     
/*  92 */     frontX[0] = safeClone(frontXBase);
/*  93 */     rotateFace(frontX[0]);
/*  94 */     rotateFace(frontX[0]);
/*     */     
/*  96 */     double[][] frontZBase = { { -0.5D, -0.5D, 0.5D, 0.5D }, { 1.0D, 0.0D, 0.0D, 1.0D }, { 0.0625D, 0.0625D, 0.0625D, 0.0625D } };
/*     */     
/*  98 */     frontZ[5] = safeClone(frontZBase);
/*     */     
/* 100 */     frontZ[1] = safeClone(frontZBase);
/* 101 */     rotateFace(frontZ[1]);
/* 102 */     rotateFace(frontZ[1]);
/* 103 */     rotateFace(frontZ[1]);
/*     */     
/* 105 */     frontZ[2] = safeClone(frontZBase);
/* 106 */     rotateFace(frontZ[2]);
/*     */     
/* 108 */     frontZ[0] = safeClone(frontZBase);
/* 109 */     rotateFace(frontZ[0]);
/* 110 */     rotateFace(frontZ[0]);
/*     */     
/* 112 */     double[][] frontYBase = { { -0.5D, -0.5D, 0.5D, 0.5D }, { -0.0625D, -0.0625D, -0.0625D, -0.0625D }, { 0.5D, -0.5D, -0.5D, 0.5D } };
/*     */     
/* 114 */     frontY[4] = safeClone(frontYBase);
/* 115 */     rotateFace(frontY[4]);
/* 116 */     rotateFace(frontY[4]);
/*     */     
/* 118 */     frontY[3] = safeClone(frontYBase);
/*     */     
/* 120 */     frontY[2] = safeClone(frontYBase);
/* 121 */     rotateFace(frontY[2]);
/*     */     
/* 123 */     frontY[1] = safeClone(frontYBase);
/* 124 */     rotateFace(frontY[1]);
/* 125 */     rotateFace(frontY[1]);
/* 126 */     rotateFace(frontY[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderMarkerWithMeta(IBlockAccess iblockaccess, Block block, double xi, double yi, double zi, int meta) {
/* 131 */     Tessellator tessellator = Tessellator.field_78398_a;
/* 132 */     int metadata = meta;
/*     */     
/* 134 */     if (metadata > 5 || metadata < 0) {
/* 135 */       metadata = 1;
/*     */     }
/*     */     
/* 138 */     double x = xi;
/* 139 */     double y = yi;
/* 140 */     double z = zi;
/*     */     
/* 142 */     int xCoord = (int)x;
/* 143 */     int yCoord = (int)y;
/* 144 */     int zCoord = (int)z;
/*     */     
/* 146 */     IIcon i = block.func_149673_e(iblockaccess, xCoord, yCoord, zCoord, 1);
/*     */     
/* 148 */     int m = metaToOld[metadata];
/* 149 */     x += 0.5D;
/* 150 */     z += 0.5D;
/*     */     
/* 152 */     double minU = i.func_94214_a(7.0D);
/* 153 */     double minV = i.func_94207_b(7.0D);
/* 154 */     double maxU = i.func_94214_a(9.0D);
/* 155 */     double maxV = i.func_94207_b(9.0D);
/*     */     
/* 157 */     tessellator.func_78380_c(block.func_149677_c(iblockaccess, xCoord, yCoord, zCoord));
/*     */     
/* 159 */     double s = 0.0625D;
/*     */     
/* 161 */     if (m == 5) {
/* 162 */       tessellator.func_78374_a(x - s, y + 0.5D + s, z - s, minU, minV);
/* 163 */       tessellator.func_78374_a(x - s, y + 0.5D + s, z + s, minU, maxV);
/* 164 */       tessellator.func_78374_a(x + s, y + 0.5D + s, z + s, maxU, maxV);
/* 165 */       tessellator.func_78374_a(x + s, y + 0.5D + s, z - s, maxU, minV);
/* 166 */     } else if (m == 0) {
/* 167 */       tessellator.func_78374_a(x + s, y + 0.5D - s, z - s, maxU, minV);
/* 168 */       tessellator.func_78374_a(x + s, y + 0.5D - s, z + s, maxU, maxV);
/* 169 */       tessellator.func_78374_a(x - s, y + 0.5D - s, z + s, minU, maxV);
/* 170 */       tessellator.func_78374_a(x - s, y + 0.5D - s, z - s, minU, minV);
/* 171 */     } else if (m == 2) {
/* 172 */       tessellator.func_78374_a(x - s, y + 0.5D - s, z - s, minU, minV);
/* 173 */       tessellator.func_78374_a(x - s, y + 0.5D - s, z + s, minU, maxV);
/* 174 */       tessellator.func_78374_a(x - s, y + 0.5D + s, z + s, maxU, maxV);
/* 175 */       tessellator.func_78374_a(x - s, y + 0.5D + s, z - s, maxU, minV);
/* 176 */     } else if (m == 1) {
/* 177 */       tessellator.func_78374_a(x + s, y + 0.5D + s, z - s, maxU, minV);
/* 178 */       tessellator.func_78374_a(x + s, y + 0.5D + s, z + s, maxU, maxV);
/* 179 */       tessellator.func_78374_a(x + s, y + 0.5D - s, z + s, minU, maxV);
/* 180 */       tessellator.func_78374_a(x + s, y + 0.5D - s, z - s, minU, minV);
/* 181 */     } else if (m == 3) {
/* 182 */       tessellator.func_78374_a(x - s, y + 0.5D - s, z + s, minU, minV);
/* 183 */       tessellator.func_78374_a(x + s, y + 0.5D - s, z + s, minU, maxV);
/* 184 */       tessellator.func_78374_a(x + s, y + 0.5D + s, z + s, maxU, maxV);
/* 185 */       tessellator.func_78374_a(x - s, y + 0.5D + s, z + s, maxU, minV);
/* 186 */     } else if (m == 4) {
/* 187 */       tessellator.func_78374_a(x - s, y + 0.5D + s, z - s, maxU, minV);
/* 188 */       tessellator.func_78374_a(x + s, y + 0.5D + s, z - s, maxU, maxV);
/* 189 */       tessellator.func_78374_a(x + s, y + 0.5D - s, z - s, minU, maxV);
/* 190 */       tessellator.func_78374_a(x - s, y + 0.5D - s, z - s, minU, minV);
/*     */     } 
/*     */     
/* 193 */     i = block.func_149673_e(iblockaccess, xCoord, yCoord, zCoord, 0);
/*     */     
/* 195 */     minU = i.func_94209_e();
/* 196 */     maxU = i.func_94212_f();
/* 197 */     minV = i.func_94206_g();
/* 198 */     maxV = i.func_94210_h();
/*     */     
/* 200 */     if (m == 5 || m == 4 || m == 3 || m == 0) {
/* 201 */       tessellator.func_78374_a(x + frontX[m][0][0], y + frontX[m][1][0], z + frontX[m][2][0], minU, minV);
/* 202 */       tessellator.func_78374_a(x + frontX[m][0][1], y + frontX[m][1][1], z + frontX[m][2][1], minU, maxV);
/* 203 */       tessellator.func_78374_a(x + frontX[m][0][2], y + frontX[m][1][2], z + frontX[m][2][2], maxU, maxV);
/* 204 */       tessellator.func_78374_a(x + frontX[m][0][3], y + frontX[m][1][3], z + frontX[m][2][3], maxU, minV);
/*     */       
/* 206 */       tessellator.func_78374_a(x - frontX[m][0][3], y + frontX[m][1][3], z + frontX[m][2][3], maxU, minV);
/* 207 */       tessellator.func_78374_a(x - frontX[m][0][2], y + frontX[m][1][2], z + frontX[m][2][2], maxU, maxV);
/* 208 */       tessellator.func_78374_a(x - frontX[m][0][1], y + frontX[m][1][1], z + frontX[m][2][1], minU, maxV);
/* 209 */       tessellator.func_78374_a(x - frontX[m][0][0], y + frontX[m][1][0], z + frontX[m][2][0], minU, minV);
/*     */     } 
/*     */     
/* 212 */     if (m == 5 || m == 2 || m == 1 || m == 0) {
/* 213 */       tessellator.func_78374_a(x + frontZ[m][0][0], y + frontZ[m][1][0], z + frontZ[m][2][0], minU, minV);
/* 214 */       tessellator.func_78374_a(x + frontZ[m][0][1], y + frontZ[m][1][1], z + frontZ[m][2][1], minU, maxV);
/* 215 */       tessellator.func_78374_a(x + frontZ[m][0][2], y + frontZ[m][1][2], z + frontZ[m][2][2], maxU, maxV);
/* 216 */       tessellator.func_78374_a(x + frontZ[m][0][3], y + frontZ[m][1][3], z + frontZ[m][2][3], maxU, minV);
/*     */       
/* 218 */       tessellator.func_78374_a(x + frontZ[m][0][3], y + frontZ[m][1][3], z - frontZ[m][2][3], maxU, minV);
/* 219 */       tessellator.func_78374_a(x + frontZ[m][0][2], y + frontZ[m][1][2], z - frontZ[m][2][2], maxU, maxV);
/* 220 */       tessellator.func_78374_a(x + frontZ[m][0][1], y + frontZ[m][1][1], z - frontZ[m][2][1], minU, maxV);
/* 221 */       tessellator.func_78374_a(x + frontZ[m][0][0], y + frontZ[m][1][0], z - frontZ[m][2][0], minU, minV);
/*     */     } 
/*     */     
/* 224 */     if (m == 4 || m == 3 || m == 2 || m == 1) {
/* 225 */       tessellator.func_78374_a(x + frontY[m][0][0], y + 0.5D + frontY[m][1][0], z + frontY[m][2][0], minU, minV);
/* 226 */       tessellator.func_78374_a(x + frontY[m][0][1], y + 0.5D + frontY[m][1][1], z + frontY[m][2][1], minU, maxV);
/* 227 */       tessellator.func_78374_a(x + frontY[m][0][2], y + 0.5D + frontY[m][1][2], z + frontY[m][2][2], maxU, maxV);
/* 228 */       tessellator.func_78374_a(x + frontY[m][0][3], y + 0.5D + frontY[m][1][3], z + frontY[m][2][3], maxU, minV);
/*     */       
/* 230 */       tessellator.func_78374_a(x + frontY[m][0][3], y + 0.5D - frontY[m][1][3], z + frontY[m][2][3], maxU, minV);
/* 231 */       tessellator.func_78374_a(x + frontY[m][0][2], y + 0.5D - frontY[m][1][2], z + frontY[m][2][2], maxU, maxV);
/* 232 */       tessellator.func_78374_a(x + frontY[m][0][1], y + 0.5D - frontY[m][1][1], z + frontY[m][2][1], minU, maxV);
/* 233 */       tessellator.func_78374_a(x + frontY[m][0][0], y + 0.5D - frontY[m][1][0], z + frontY[m][2][0], minU, minV);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void rotateFace(double[][] face) {
/* 238 */     for (int j = 0; j < 3; j++) {
/* 239 */       double tmp = face[j][0];
/* 240 */       face[j][0] = face[j][1];
/* 241 */       face[j][1] = face[j][2];
/* 242 */       face[j][2] = face[j][3];
/* 243 */       face[j][3] = tmp;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\render\RenderingMarkers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */