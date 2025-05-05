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
/*    */ public final class PatternFill
/*    */   extends FillerPattern
/*    */ {
/* 19 */   public static final PatternFill INSTANCE = new PatternFill();
/*    */   
/*    */   private PatternFill() {
/* 22 */     super("fill");
/*    */   }
/*    */ 
/*    */   
/*    */   public Template getTemplate(Box box, World world, IStatementParameter[] parameters) {
/* 27 */     Template bpt = new Template(box.sizeX(), box.sizeY(), box.sizeZ());
/*    */     
/* 29 */     fill(0, 0, 0, box.sizeX() - 1, box.sizeY() - 1, box.sizeZ() - 1, bpt);
/*    */     
/* 31 */     return bpt;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\PatternFill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */