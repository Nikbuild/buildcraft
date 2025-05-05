/*    */ package buildcraft.api.facades;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum FacadeType
/*    */ {
/*  8 */   Basic,
/*  9 */   Phased;
/*    */   
/*    */   public static FacadeType fromOrdinal(int ordinal) {
/* 12 */     return (ordinal == 1) ? Phased : Basic;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\facades\FacadeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */