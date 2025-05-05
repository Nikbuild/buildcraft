/*     */ package buildcraft.core.builders.patterns;
/*     */ 
/*     */ import buildcraft.api.blueprints.SchematicBlockBase;
/*     */ import buildcraft.api.blueprints.SchematicMask;
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
/*     */ public class PatternPyramid
/*     */   extends FillerPattern
/*     */ {
/*  19 */   private static final int[] MODIFIERS = new int[] { 257, 4353, 4097, 273, 4369, 4113, 272, 4368, 4112 };
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
/*     */   public PatternPyramid() {
/*  32 */     super("pyramid");
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  37 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int minParameters() {
/*  42 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int index) {
/*  47 */     return (index == 1) ? new PatternParameterCenter(4) : new PatternParameterYDir(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Template getTemplate(Box box, World world, IStatementParameter[] parameters) {
/*  52 */     int height, stepY, xMin = (int)(box.pMin()).x;
/*  53 */     int yMin = (int)(box.pMin()).y;
/*  54 */     int zMin = (int)(box.pMin()).z;
/*     */     
/*  56 */     int xMax = (int)(box.pMax()).x;
/*  57 */     int yMax = (int)(box.pMax()).y;
/*  58 */     int zMax = (int)(box.pMax()).z;
/*     */     
/*  60 */     Template bpt = new Template(xMax - xMin + 1, yMax - yMin + 1, zMax - zMin + 1);
/*     */     
/*  62 */     int[] modifiers = new int[4];
/*     */ 
/*     */ 
/*     */     
/*  66 */     if (parameters.length >= 1 && parameters[0] != null && !((PatternParameterYDir)parameters[0]).up) {
/*  67 */       stepY = -1;
/*     */     } else {
/*  69 */       stepY = 1;
/*     */     } 
/*     */     
/*  72 */     int center = 4;
/*  73 */     if (parameters.length >= 2 && parameters[1] != null) {
/*  74 */       center = ((PatternParameterCenter)parameters[1]).getDirection();
/*     */     }
/*     */     
/*  77 */     modifiers[0] = MODIFIERS[center] >> 12 & 0x1;
/*  78 */     modifiers[1] = MODIFIERS[center] >> 8 & 0x1;
/*  79 */     modifiers[2] = MODIFIERS[center] >> 4 & 0x1;
/*  80 */     modifiers[3] = MODIFIERS[center] & 0x1;
/*     */     
/*  82 */     if (stepY == 1) {
/*  83 */       height = yMin;
/*     */     } else {
/*  85 */       height = yMax;
/*     */     } 
/*     */     
/*  88 */     int x1 = xMin;
/*  89 */     int x2 = xMax;
/*  90 */     int z1 = zMin;
/*  91 */     int z2 = zMax;
/*     */     
/*  93 */     while (height >= yMin && height <= yMax) {
/*  94 */       for (int x = x1; x <= x2; x++) {
/*  95 */         for (int z = z1; z <= z2; z++) {
/*  96 */           bpt.put(x - xMin, height - yMin, z - zMin, (SchematicBlockBase)new SchematicMask(true));
/*     */         }
/*     */       } 
/*     */       
/* 100 */       x1 += modifiers[0];
/* 101 */       x2 -= modifiers[1];
/* 102 */       z1 += modifiers[2];
/* 103 */       z2 -= modifiers[3];
/* 104 */       height += stepY;
/*     */       
/* 106 */       if (x1 > x2 || z1 > z2) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 111 */     return bpt;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\PatternPyramid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */