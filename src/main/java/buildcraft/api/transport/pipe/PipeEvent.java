/*    */ package buildcraft.api.transport.pipe;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PipeEvent
/*    */ {
/*    */   public final boolean canBeCancelled;
/*    */   public final IPipeHolder holder;
/*    */   private boolean canceled = false;
/*    */   
/*    */   public PipeEvent(IPipeHolder holder) {
/* 12 */     this(false, holder);
/*    */   }
/*    */   
/*    */   protected PipeEvent(boolean canBeCancelled, IPipeHolder holder) {
/* 16 */     this.canBeCancelled = canBeCancelled;
/* 17 */     this.holder = holder;
/*    */   }
/*    */   
/*    */   public void cancel() {
/* 21 */     if (this.canBeCancelled) {
/* 22 */       this.canceled = true;
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isCanceled() {
/* 27 */     return this.canceled;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String checkStateForErrors() {
/* 36 */     if ((this.canceled & (!this.canBeCancelled ? 1 : 0)) != 0) {
/* 37 */       return "Somehow cancelled an event that isn't marked as such!";
/*    */     }
/* 39 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */