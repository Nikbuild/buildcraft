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
/*     */ public class PatternCylinder
/*     */   extends FillerPattern
/*     */ {
/*     */   public PatternCylinder() {
/*  20 */     super("cylinder");
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  25 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int minParameters() {
/*  30 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int index) {
/*  35 */     return new PatternParameterHollow(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Template getTemplate(Box box, World world, IStatementParameter[] parameters) {
/*  40 */     Template result = new Template(box.sizeX(), box.sizeY(), box.sizeZ());
/*  41 */     boolean filled = (parameters.length > 0 && ((PatternParameterHollow)parameters[0]).filled);
/*     */     
/*  43 */     int xMin = 0;
/*  44 */     int yMin = 0;
/*  45 */     int zMin = 0;
/*     */     
/*  47 */     int xMax = box.sizeX() - 1;
/*  48 */     int yMax = box.sizeY() - 1;
/*  49 */     int zMax = box.sizeZ() - 1;
/*     */     
/*  51 */     int xFix = (xMax - xMin) % 2;
/*  52 */     int zFix = (zMax - zMin) % 2;
/*     */     
/*  54 */     int xCenter = (xMax + xMin) / 2 + ((xMax + xMin < 0 && xFix == 1) ? -1 : 0);
/*     */     
/*  56 */     int zCenter = (zMax + zMin) / 2 + ((zMax + zMin < 0 && zFix == 1) ? -1 : 0);
/*     */ 
/*     */     
/*  59 */     int xRadius = (xMax - xMin) / 2;
/*  60 */     int zRadius = (zMax - zMin) / 2;
/*     */     
/*  62 */     if (xRadius == 0 || zRadius == 0) {
/*  63 */       fill(xMin, yMin, zMin, xMax, yMax, zMax, result);
/*  64 */       return result;
/*     */     } 
/*     */     
/*  67 */     int dx = xRadius, dz = 0;
/*  68 */     int xChange = zRadius * zRadius * (1 - 2 * xRadius);
/*  69 */     int zChange = xRadius * xRadius;
/*  70 */     int ellipseError = 0;
/*  71 */     int twoASquare = 2 * xRadius * xRadius;
/*  72 */     int twoBSquare = 2 * zRadius * zRadius;
/*  73 */     int stoppingX = twoBSquare * xRadius;
/*  74 */     int stoppingZ = 0;
/*     */     
/*  76 */     if (twoASquare > 0) {
/*  77 */       while (stoppingX >= stoppingZ) {
/*  78 */         if (filled) {
/*  79 */           fillSquare(xCenter, zCenter, dx, dz, xFix, zFix, yMin, yMax, result);
/*     */         } else {
/*     */           
/*  82 */           fillFourColumns(xCenter, zCenter, dx, dz, xFix, zFix, yMin, yMax, result);
/*     */         } 
/*     */ 
/*     */         
/*  86 */         dz++;
/*  87 */         stoppingZ += twoASquare;
/*  88 */         ellipseError += zChange;
/*  89 */         zChange += twoASquare;
/*  90 */         if (2 * ellipseError + xChange > 0) {
/*  91 */           dx--;
/*  92 */           stoppingX -= twoBSquare;
/*  93 */           ellipseError += xChange;
/*  94 */           xChange += twoBSquare;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  99 */     dx = 0;
/* 100 */     dz = zRadius;
/* 101 */     xChange = zRadius * zRadius;
/* 102 */     zChange = xRadius * xRadius * (1 - 2 * zRadius);
/* 103 */     ellipseError = 0;
/* 104 */     stoppingX = 0;
/* 105 */     stoppingZ = twoASquare * zRadius;
/*     */     
/* 107 */     if (twoBSquare > 0) {
/* 108 */       while (stoppingX <= stoppingZ) {
/* 109 */         if (filled) {
/* 110 */           fillSquare(xCenter, zCenter, dx, dz, xFix, zFix, yMin, yMax, result);
/*     */         } else {
/*     */           
/* 113 */           fillFourColumns(xCenter, zCenter, dx, dz, xFix, zFix, yMin, yMax, result);
/*     */         } 
/*     */ 
/*     */         
/* 117 */         dx++;
/* 118 */         stoppingX += twoBSquare;
/* 119 */         ellipseError += xChange;
/* 120 */         xChange += twoBSquare;
/* 121 */         if (2 * ellipseError + zChange > 0) {
/* 122 */           dz--;
/* 123 */           stoppingZ -= twoASquare;
/* 124 */           ellipseError += zChange;
/* 125 */           zChange += twoASquare;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 130 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean fillSquare(int xCenter, int zCenter, int dx, int dz, int xFix, int zFix, int yMin, int yMax, Template template) {
/* 137 */     int x1 = xCenter + dx + xFix;
/* 138 */     int z1 = zCenter + dz + zFix;
/*     */     
/* 140 */     int x2 = xCenter - dx;
/* 141 */     int z2 = zCenter + dz + zFix;
/*     */     
/* 143 */     fill(x2, yMin, z2, x1, yMax, z1, template);
/*     */     
/* 145 */     x1 = xCenter - dx;
/* 146 */     z1 = zCenter - dz;
/*     */     
/* 148 */     fill(x1, yMin, z1, x2, yMax, z2, template);
/*     */     
/* 150 */     x2 = xCenter + dx + xFix;
/* 151 */     z2 = zCenter - dz;
/*     */     
/* 153 */     fill(x1, yMin, z1, x2, yMax, z2, template);
/*     */     
/* 155 */     x1 = xCenter + dx + xFix;
/* 156 */     z1 = zCenter + dz + zFix;
/*     */     
/* 158 */     fill(x2, yMin, z2, x1, yMax, z1, template);
/*     */     
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean fillFourColumns(int xCenter, int zCenter, int dx, int dz, int xFix, int zFix, int yMin, int yMax, Template template) {
/* 167 */     int x = xCenter + dx + xFix;
/* 168 */     int z = zCenter + dz + zFix;
/* 169 */     fill(x, yMin, z, x, yMax, z, template);
/*     */     
/* 171 */     x = xCenter - dx;
/* 172 */     z = zCenter + dz + zFix;
/* 173 */     fill(x, yMin, z, x, yMax, z, template);
/*     */     
/* 175 */     x = xCenter - dx;
/* 176 */     z = zCenter - dz;
/* 177 */     fill(x, yMin, z, x, yMax, z, template);
/*     */     
/* 179 */     x = xCenter + dx + xFix;
/* 180 */     z = zCenter - dz;
/* 181 */     fill(x, yMin, z, x, yMax, z, template);
/*     */     
/* 183 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\PatternCylinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */