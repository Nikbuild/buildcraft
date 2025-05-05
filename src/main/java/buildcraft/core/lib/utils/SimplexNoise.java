/*     */ package buildcraft.core.lib.utils;
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
/*     */ public final class SimplexNoise
/*     */ {
/*  28 */   private static Grad[] grad3 = new Grad[] { new Grad(1.0D, 1.0D, 0.0D), new Grad(-1.0D, 1.0D, 0.0D), new Grad(1.0D, -1.0D, 0.0D), new Grad(-1.0D, -1.0D, 0.0D), new Grad(1.0D, 0.0D, 1.0D), new Grad(-1.0D, 0.0D, 1.0D), new Grad(1.0D, 0.0D, -1.0D), new Grad(-1.0D, 0.0D, -1.0D), new Grad(0.0D, 1.0D, 1.0D), new Grad(0.0D, -1.0D, 1.0D), new Grad(0.0D, 1.0D, -1.0D), new Grad(0.0D, -1.0D, -1.0D) };
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
/*  41 */   private static short[] p = new short[] { 151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244, 102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196, 135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42, 223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9, 129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180 };
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
/*  52 */   private static short[] perm = new short[512];
/*  53 */   private static short[] permMod12 = new short[512];
/*     */   
/*     */   static {
/*  56 */     for (int i = 0; i < 512; i++) {
/*  57 */       perm[i] = p[i & 0xFF];
/*  58 */       permMod12[i] = (short)(perm[i] % 12);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  63 */   private static final double F2 = 0.5D * (Math.sqrt(3.0D) - 1.0D);
/*  64 */   private static final double G2 = (3.0D - Math.sqrt(3.0D)) / 6.0D;
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
/*     */   private static int fastfloor(double x) {
/*  79 */     int xi = (int)x;
/*  80 */     return (x < xi) ? (xi - 1) : xi;
/*     */   }
/*     */   
/*     */   private static double dot(Grad g, double x, double y) {
/*  84 */     return g.x * x + g.y * y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double noise(double xin, double yin) {
/*     */     double n0, n1, n2;
/*     */     int i1, j1;
/*  97 */     double s = (xin + yin) * F2;
/*  98 */     int i = fastfloor(xin + s);
/*  99 */     int j = fastfloor(yin + s);
/* 100 */     double t = (i + j) * G2;
/* 101 */     double originX0 = i - t;
/* 102 */     double originY0 = j - t;
/* 103 */     double x0 = xin - originX0;
/* 104 */     double y0 = yin - originY0;
/*     */ 
/*     */ 
/*     */     
/* 108 */     if (x0 > y0) {
/* 109 */       i1 = 1;
/* 110 */       j1 = 0;
/*     */     } else {
/*     */       
/* 113 */       i1 = 0;
/* 114 */       j1 = 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 119 */     double x1 = x0 - i1 + G2;
/* 120 */     double y1 = y0 - j1 + G2;
/* 121 */     double x2 = x0 - 1.0D + 2.0D * G2;
/* 122 */     double y2 = y0 - 1.0D + 2.0D * G2;
/*     */     
/* 124 */     int ii = i & 0xFF;
/* 125 */     int jj = j & 0xFF;
/* 126 */     int gi0 = permMod12[ii + perm[jj]];
/* 127 */     int gi1 = permMod12[ii + i1 + perm[jj + j1]];
/* 128 */     int gi2 = permMod12[ii + 1 + perm[jj + 1]];
/*     */     
/* 130 */     double t0 = 0.5D - x0 * x0 - y0 * y0;
/* 131 */     if (t0 < 0.0D) {
/* 132 */       n0 = 0.0D;
/*     */     } else {
/* 134 */       t0 *= t0;
/* 135 */       n0 = t0 * t0 * dot(grad3[gi0], x0, y0);
/*     */     } 
/* 137 */     double t1 = 0.5D - x1 * x1 - y1 * y1;
/* 138 */     if (t1 < 0.0D) {
/* 139 */       n1 = 0.0D;
/*     */     } else {
/* 141 */       t1 *= t1;
/* 142 */       n1 = t1 * t1 * dot(grad3[gi1], x1, y1);
/*     */     } 
/* 144 */     double t2 = 0.5D - x2 * x2 - y2 * y2;
/* 145 */     if (t2 < 0.0D) {
/* 146 */       n2 = 0.0D;
/*     */     } else {
/* 148 */       t2 *= t2;
/* 149 */       n2 = t2 * t2 * dot(grad3[gi2], x2, y2);
/*     */     } 
/*     */ 
/*     */     
/* 153 */     return 70.0D * (n0 + n1 + n2);
/*     */   }
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
/*     */   private static class Grad
/*     */   {
/*     */     double x;
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
/*     */     double y;
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
/*     */     double z;
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
/*     */     double w;
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
/*     */     Grad(double x, double y, double z) {
/* 219 */       this.x = x;
/* 220 */       this.y = y;
/* 221 */       this.z = z;
/*     */     }
/*     */     
/*     */     Grad(double x, double y, double z, double w) {
/* 225 */       this.x = x;
/* 226 */       this.y = y;
/* 227 */       this.z = z;
/* 228 */       this.w = w;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\SimplexNoise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */