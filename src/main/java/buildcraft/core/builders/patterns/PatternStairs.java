/*     */ package buildcraft.core.builders.patterns;
/*     */ 
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.core.Box;
/*     */ import buildcraft.core.blueprints.Template;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PatternStairs
/*     */   extends FillerPattern
/*     */ {
/*     */   public PatternStairs() {
/*  20 */     super("stairs");
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  25 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int minParameters() {
/*  30 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int index) {
/*  35 */     return (index == 1) ? new PatternParameterXZDir(0) : new PatternParameterYDir(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Template getTemplate(Box box, World world, IStatementParameter[] parameters) {
/*  40 */     int height, heightStep, xMin = 0;
/*  41 */     int yMin = 0;
/*  42 */     int zMin = 0;
/*     */     
/*  44 */     int xMax = box.sizeX() - 1;
/*  45 */     int yMax = box.sizeY() - 1;
/*  46 */     int zMax = box.sizeZ() - 1;
/*     */     
/*  48 */     int sizeX = xMax - xMin + 1;
/*  49 */     int sizeZ = zMax - zMin + 1;
/*     */     
/*  51 */     Template template = new Template(box.sizeX(), box.sizeY(), box.sizeZ());
/*     */ 
/*     */ 
/*     */     
/*  55 */     if (parameters.length >= 1 && parameters[0] != null && !((PatternParameterYDir)parameters[0]).up) {
/*  56 */       height = Math.max(yMin, yMax - Math.max(xMax, zMax));
/*  57 */       heightStep = 1;
/*     */     } else {
/*  59 */       height = Math.min(yMax, Math.max(xMax, zMax));
/*  60 */       heightStep = -1;
/*     */     } 
/*     */     
/*  63 */     int param2 = 0;
/*  64 */     if (parameters.length >= 2 && parameters[1] != null) {
/*  65 */       param2 = ((PatternParameterXZDir)parameters[1]).getDirection();
/*     */     }
/*     */     
/*  68 */     int[] steps = { 0, 0, 0, 0 };
/*     */     
/*  70 */     if (param2 == 0) {
/*  71 */       steps[0] = 1;
/*  72 */     } else if (param2 == 1) {
/*  73 */       steps[1] = 1;
/*  74 */     } else if (param2 == 2) {
/*  75 */       steps[2] = 1;
/*  76 */     } else if (param2 == 3) {
/*  77 */       steps[3] = 1;
/*     */     } 
/*     */     
/*  80 */     int x1 = xMin, x2 = xMax, z1 = zMin, z2 = zMax;
/*     */     
/*  82 */     if (steps[0] == 1) {
/*  83 */       x1 = xMax - sizeX + 1;
/*  84 */       x2 = x1;
/*     */     } 
/*     */     
/*  87 */     if (steps[1] == 1) {
/*  88 */       x2 = xMin + sizeX - 1;
/*  89 */       x1 = x2;
/*     */     } 
/*     */     
/*  92 */     if (steps[2] == 1) {
/*  93 */       z1 = zMax - sizeZ + 1;
/*  94 */       z2 = z1;
/*     */     } 
/*     */     
/*  97 */     if (steps[3] == 1) {
/*  98 */       z2 = zMin + sizeZ - 1;
/*  99 */       z1 = z2;
/*     */     } 
/*     */     
/* 102 */     while (x2 - x1 + 1 > 0 && z2 - z1 + 1 > 0 && x2 - x1 < sizeX && z2 - z1 < sizeZ && height >= yMin && height <= yMax) {
/* 103 */       fill(x1, height, z1, x2, height, z2, template);
/*     */       
/* 105 */       x2 += steps[0];
/* 106 */       x1 -= steps[1];
/* 107 */       z2 += steps[2];
/* 108 */       z1 -= steps[3];
/*     */       
/* 110 */       height += heightStep;
/*     */     } 
/*     */     
/* 113 */     return template;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\PatternStairs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */