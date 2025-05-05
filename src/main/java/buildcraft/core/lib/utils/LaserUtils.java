/*     */ package buildcraft.core.lib.utils;
/*     */ 
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.core.LaserData;
/*     */ import buildcraft.core.LaserKind;
/*     */ import buildcraft.core.lib.EntityBlock;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LaserUtils
/*     */ {
/*     */   public static EntityBlock createLaser(World world, Position p1, Position p2, LaserKind kind) {
/*  17 */     if (p1.equals(p2)) {
/*  18 */       return null;
/*     */     }
/*     */     
/*  21 */     double iSize = p2.x - p1.x;
/*  22 */     double jSize = p2.y - p1.y;
/*  23 */     double kSize = p2.z - p1.z;
/*     */     
/*  25 */     double i = p1.x;
/*  26 */     double j = p1.y;
/*  27 */     double k = p1.z;
/*     */     
/*  29 */     if (iSize != 0.0D) {
/*  30 */       i += 0.5D;
/*  31 */       j += 0.45D;
/*  32 */       k += 0.45D;
/*     */       
/*  34 */       jSize = 0.1D;
/*  35 */       kSize = 0.1D;
/*  36 */     } else if (jSize != 0.0D) {
/*  37 */       i += 0.45D;
/*  38 */       j += 0.5D;
/*  39 */       k += 0.45D;
/*     */       
/*  41 */       iSize = 0.1D;
/*  42 */       kSize = 0.1D;
/*  43 */     } else if (kSize != 0.0D) {
/*  44 */       i += 0.45D;
/*  45 */       j += 0.45D;
/*  46 */       k += 0.5D;
/*     */       
/*  48 */       iSize = 0.1D;
/*  49 */       jSize = 0.1D;
/*     */     } 
/*     */     
/*  52 */     EntityBlock block = CoreProxy.proxy.newEntityBlock(world, i, j, k, iSize, jSize, kSize, kind);
/*  53 */     block.setBrightness(210);
/*     */     
/*  55 */     world.func_72838_d((Entity)block);
/*     */     
/*  57 */     return block;
/*     */   }
/*     */   
/*     */   public static EntityBlock[] createLaserBox(World world, double xMin, double yMin, double zMin, double xMax, double yMax, double zMax, LaserKind kind) {
/*  61 */     EntityBlock[] lasers = new EntityBlock[12];
/*  62 */     Position[] p = new Position[8];
/*     */     
/*  64 */     p[0] = new Position(xMin, yMin, zMin);
/*  65 */     p[1] = new Position(xMax, yMin, zMin);
/*  66 */     p[2] = new Position(xMin, yMax, zMin);
/*  67 */     p[3] = new Position(xMax, yMax, zMin);
/*  68 */     p[4] = new Position(xMin, yMin, zMax);
/*  69 */     p[5] = new Position(xMax, yMin, zMax);
/*  70 */     p[6] = new Position(xMin, yMax, zMax);
/*  71 */     p[7] = new Position(xMax, yMax, zMax);
/*     */     
/*  73 */     lasers[0] = createLaser(world, p[0], p[1], kind);
/*  74 */     lasers[1] = createLaser(world, p[0], p[2], kind);
/*  75 */     lasers[2] = createLaser(world, p[2], p[3], kind);
/*  76 */     lasers[3] = createLaser(world, p[1], p[3], kind);
/*  77 */     lasers[4] = createLaser(world, p[4], p[5], kind);
/*  78 */     lasers[5] = createLaser(world, p[4], p[6], kind);
/*  79 */     lasers[6] = createLaser(world, p[5], p[7], kind);
/*  80 */     lasers[7] = createLaser(world, p[6], p[7], kind);
/*  81 */     lasers[8] = createLaser(world, p[0], p[4], kind);
/*  82 */     lasers[9] = createLaser(world, p[1], p[5], kind);
/*  83 */     lasers[10] = createLaser(world, p[2], p[6], kind);
/*  84 */     lasers[11] = createLaser(world, p[3], p[7], kind);
/*     */     
/*  86 */     return lasers;
/*     */   }
/*     */   
/*     */   public static LaserData[] createLaserDataBox(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
/*  90 */     LaserData[] lasers = new LaserData[12];
/*  91 */     Position[] p = new Position[8];
/*     */     
/*  93 */     p[0] = new Position(xMin, yMin, zMin);
/*  94 */     p[1] = new Position(xMax, yMin, zMin);
/*  95 */     p[2] = new Position(xMin, yMax, zMin);
/*  96 */     p[3] = new Position(xMax, yMax, zMin);
/*  97 */     p[4] = new Position(xMin, yMin, zMax);
/*  98 */     p[5] = new Position(xMax, yMin, zMax);
/*  99 */     p[6] = new Position(xMin, yMax, zMax);
/* 100 */     p[7] = new Position(xMax, yMax, zMax);
/*     */     
/* 102 */     lasers[0] = new LaserData(p[0], p[1]);
/* 103 */     lasers[1] = new LaserData(p[0], p[2]);
/* 104 */     lasers[2] = new LaserData(p[2], p[3]);
/* 105 */     lasers[3] = new LaserData(p[1], p[3]);
/* 106 */     lasers[4] = new LaserData(p[4], p[5]);
/* 107 */     lasers[5] = new LaserData(p[4], p[6]);
/* 108 */     lasers[6] = new LaserData(p[5], p[7]);
/* 109 */     lasers[7] = new LaserData(p[6], p[7]);
/* 110 */     lasers[8] = new LaserData(p[0], p[4]);
/* 111 */     lasers[9] = new LaserData(p[1], p[5]);
/* 112 */     lasers[10] = new LaserData(p[2], p[6]);
/* 113 */     lasers[11] = new LaserData(p[3], p[7]);
/*     */     
/* 115 */     return lasers;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\LaserUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */