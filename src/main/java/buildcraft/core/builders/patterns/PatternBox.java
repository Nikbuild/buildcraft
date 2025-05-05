/*    */ package buildcraft.core.builders.patterns;
/*    */ 
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.core.Box;
/*    */ import buildcraft.core.blueprints.Template;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PatternBox
/*    */   extends FillerPattern
/*    */ {
/*    */   public PatternBox() {
/* 21 */     super("box");
/*    */   }
/*    */ 
/*    */   
/*    */   public Template getTemplate(Box box, World world, IStatementParameter[] parameters) {
/* 26 */     Template result = new Template(box.sizeX(), box.sizeY(), box.sizeZ());
/*    */     
/* 28 */     int xMin = 0;
/* 29 */     int yMin = 0;
/* 30 */     int zMin = 0;
/*    */     
/* 32 */     int xMax = box.sizeX() - 1;
/* 33 */     int yMax = box.sizeY() - 1;
/* 34 */     int zMax = box.sizeZ() - 1;
/*    */     
/* 36 */     fill(xMin, yMin, zMin, xMax, yMin, zMax, result);
/* 37 */     fill(xMin, yMin, zMin, xMin, yMax, zMax, result);
/* 38 */     fill(xMin, yMin, zMin, xMax, yMax, zMin, result);
/* 39 */     fill(xMax, yMin, zMin, xMax, yMax, zMax, result);
/* 40 */     fill(xMin, yMin, zMax, xMax, yMax, zMax, result);
/* 41 */     fill(xMin, yMax, zMin, xMax, yMax, zMax, result);
/*    */     
/* 43 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\PatternBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */