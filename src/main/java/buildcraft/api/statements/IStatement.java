/*    */ package buildcraft.api.statements;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IStatement
/*    */   extends IGuiSlot
/*    */ {
/*    */   int maxParameters();
/*    */   
/*    */   int minParameters();
/*    */   
/*    */   IStatementParameter createParameter(int paramInt);
/*    */   
/*    */   default IStatementParameter createParameter(IStatementParameter old, int index) {
/* 29 */     IStatementParameter _new = createParameter(index);
/* 30 */     if (old == null || _new == null)
/* 31 */       return _new; 
/* 32 */     if (old.getClass() == _new.getClass()) {
/* 33 */       return old;
/*    */     }
/* 35 */     return _new;
/*    */   }
/*    */ 
/*    */   
/*    */   IStatement rotateLeft();
/*    */ 
/*    */   
/*    */   IStatement[] getPossible();
/*    */ 
/*    */   
/*    */   default boolean isPossibleOrdered() {
/* 46 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\IStatement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */