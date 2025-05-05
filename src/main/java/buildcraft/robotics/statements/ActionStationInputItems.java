/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ActionStationInputItems
/*    */   extends BCStatement
/*    */   implements IActionInternal
/*    */ {
/*    */   public ActionStationInputItems(String name) {
/* 18 */     super(new String[] { name });
/*    */   }
/*    */   
/*    */   public void actionActivate(IStatementContainer source, IStatementParameter[] parameters) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\ActionStationInputItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */