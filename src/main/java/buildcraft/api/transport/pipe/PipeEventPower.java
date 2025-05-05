/*    */ package buildcraft.api.transport.pipe;
/*    */ 
/*    */ import buildcraft.api.mj.MjAPI;
/*    */ 
/*    */ public abstract class PipeEventPower extends PipeEvent {
/*    */   public final IFlowPower flow;
/*    */   
/*    */   protected PipeEventPower(IPipeHolder holder, IFlowPower flow) {
/*  9 */     super(holder);
/* 10 */     this.flow = flow;
/*    */   }
/*    */   
/*    */   protected PipeEventPower(boolean canBeCancelled, IPipeHolder holder, IFlowPower flow) {
/* 14 */     super(canBeCancelled, holder);
/* 15 */     this.flow = flow;
/*    */   }
/*    */   
/*    */   public static class Configure extends PipeEventPower {
/* 19 */     private long maxPower = 10L * MjAPI.MJ;
/*    */ 
/*    */     
/* 22 */     private long powerResistance = -1L;
/*    */ 
/*    */     
/* 25 */     private long powerLoss = -1L;
/*    */     private boolean receiver = false;
/*    */     
/*    */     public Configure(IPipeHolder holder, IFlowPower flow) {
/* 29 */       super(holder, flow);
/*    */     }
/*    */     
/*    */     public long getMaxPower() {
/* 33 */       return this.maxPower;
/*    */     }
/*    */     
/*    */     public void setMaxPower(long maxPower) {
/* 37 */       this.maxPower = maxPower;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public long getPowerLoss() {
/* 43 */       return this.powerLoss;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void setPowerLoss(long powerLoss) {
/* 49 */       this.powerLoss = powerLoss;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public long getPowerResistance() {
/* 55 */       return this.powerResistance;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void setPowerResistance(long powerResistance) {
/* 61 */       this.powerResistance = powerResistance;
/*    */     }
/*    */     
/*    */     public boolean isReceiver() {
/* 65 */       return this.receiver;
/*    */     }
/*    */ 
/*    */     
/*    */     public void setReceiver(boolean receiver) {
/* 70 */       this.receiver = receiver;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeEventPower.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */