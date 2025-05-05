/*    */ package buildcraft.core.lib.utils;
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
/*    */ public final class SessionVars
/*    */ {
/*    */   private static Class openedLedger;
/*    */   
/*    */   public static void setOpenedLedger(Class ledgerClass) {
/* 24 */     openedLedger = ledgerClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Class getOpenedLedger() {
/* 29 */     return openedLedger;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\SessionVars.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */