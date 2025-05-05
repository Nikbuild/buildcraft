/*    */ package buildcraft.api.statements;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class StatementMouseClick
/*    */ {
/*    */   private int button;
/*    */   private boolean shift;
/*    */   
/*    */   public StatementMouseClick(int button, boolean shift) {
/* 12 */     this.button = button;
/* 13 */     this.shift = shift;
/*    */   }
/*    */   
/*    */   public boolean isShift() {
/* 17 */     return this.shift;
/*    */   }
/*    */   
/*    */   public int getButton() {
/* 21 */     return this.button;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\StatementMouseClick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */