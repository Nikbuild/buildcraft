/*    */ package buildcraft.api.mj;
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
/*    */ 
/*    */ public interface IMjReceiver
/*    */   extends IMjConnector
/*    */ {
/*    */   long getPowerRequested();
/*    */   
/*    */   long receivePower(long paramLong, boolean paramBoolean);
/*    */   
/*    */   default boolean canReceive() {
/* 28 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\mj\IMjReceiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */