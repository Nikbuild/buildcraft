/*    */ package buildcraft.api.core;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnumHandlerPriority
/*    */   implements Comparable<EnumHandlerPriority>
/*    */ {
/* 10 */   HIGHEST,
/* 11 */   HIGH,
/* 12 */   NORMAL,
/* 13 */   LOW,
/* 14 */   LOWEST;
/*    */   static {
/* 16 */     VALUES = values();
/*    */   }
/*    */   
/*    */   public static final EnumHandlerPriority[] VALUES;
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\EnumHandlerPriority.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */