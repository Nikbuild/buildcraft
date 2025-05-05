/*    */ package buildcraft.core.blueprints;
/*    */ 
/*    */ import buildcraft.api.core.BCLog;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BptError
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 3579188081467555542L;
/*    */   
/*    */   public BptError(String str) {
/* 17 */     super(str);
/*    */     
/* 19 */     BCLog.logger.debug("BLUEPRINT ERROR:" + str);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\BptError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */