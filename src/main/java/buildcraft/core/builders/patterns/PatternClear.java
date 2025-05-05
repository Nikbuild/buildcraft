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
/*    */ public class PatternClear
/*    */   extends FillerPattern
/*    */ {
/*    */   public PatternClear() {
/* 20 */     super("clear");
/*    */   }
/*    */ 
/*    */   
/*    */   public Template getTemplate(Box box, World world, IStatementParameter[] parameters) {
/* 25 */     int xMin = (int)(box.pMin()).x;
/* 26 */     int yMin = (int)(box.pMin()).y;
/* 27 */     int zMin = (int)(box.pMin()).z;
/*    */     
/* 29 */     int xMax = (int)(box.pMax()).x;
/* 30 */     int yMax = (int)(box.pMax()).y;
/* 31 */     int zMax = (int)(box.pMax()).z;
/*    */     
/* 33 */     Template bpt = new Template(xMax - xMin + 1, yMax - yMin + 1, zMax - zMin + 1);
/*    */     
/* 35 */     return bpt;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\PatternClear.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */