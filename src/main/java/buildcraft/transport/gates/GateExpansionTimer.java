/*    */ package buildcraft.transport.gates;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.gates.GateExpansionController;
/*    */ import buildcraft.api.gates.IGate;
/*    */ import buildcraft.api.gates.IGateExpansion;
/*    */ import buildcraft.api.statements.IStatement;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.transport.statements.TriggerClockTimer;
/*    */ import java.util.List;
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ public final class GateExpansionTimer
/*    */   extends GateExpansionBuildcraft
/*    */   implements IGateExpansion
/*    */ {
/* 27 */   public static GateExpansionTimer INSTANCE = new GateExpansionTimer();
/*    */   
/*    */   private GateExpansionTimer() {
/* 30 */     super("timer");
/*    */   }
/*    */ 
/*    */   
/*    */   public GateExpansionController makeController(TileEntity pipeTile) {
/* 35 */     return new GateExpansionControllerTimer(pipeTile);
/*    */   }
/*    */   
/*    */   private class GateExpansionControllerTimer
/*    */     extends GateExpansionController
/*    */   {
/*    */     private class Timer {
/*    */       private static final int ACTIVE_TIME = 5;
/*    */       private final TriggerClockTimer.Time time;
/*    */       private int clock;
/*    */       
/*    */       public Timer(TriggerClockTimer.Time time) {
/* 47 */         this.time = time;
/*    */       }
/*    */       
/*    */       public void tick() {
/* 51 */         if (this.clock > -5) {
/* 52 */           this.clock--;
/*    */         } else {
/* 54 */           this.clock = this.time.delay * 20 + 5;
/*    */         } 
/*    */       }
/*    */       
/*    */       public boolean isActive() {
/* 59 */         return (this.clock < 0);
/*    */       }
/*    */     }
/*    */     
/* 63 */     private final Timer[] timers = new Timer[TriggerClockTimer.Time.VALUES.length];
/*    */     
/*    */     public GateExpansionControllerTimer(TileEntity pipeTile) {
/* 66 */       super(GateExpansionTimer.this, pipeTile);
/* 67 */       for (TriggerClockTimer.Time time : TriggerClockTimer.Time.VALUES) {
/* 68 */         this.timers[time.ordinal()] = new Timer(time);
/*    */       }
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isTriggerActive(IStatement trigger, IStatementParameter[] parameters) {
/* 74 */       if (trigger instanceof TriggerClockTimer) {
/* 75 */         TriggerClockTimer timerTrigger = (TriggerClockTimer)trigger;
/* 76 */         return this.timers[timerTrigger.time.ordinal()].isActive();
/*    */       } 
/* 78 */       return super.isTriggerActive(trigger, parameters);
/*    */     }
/*    */ 
/*    */     
/*    */     public void addTriggers(List<ITriggerInternal> list) {
/* 83 */       super.addTriggers(list);
/* 84 */       for (TriggerClockTimer.Time time : TriggerClockTimer.Time.VALUES) {
/* 85 */         list.add(BuildCraftTransport.triggerTimer[time.ordinal()]);
/*    */       }
/*    */     }
/*    */ 
/*    */     
/*    */     public void tick(IGate gate) {
/* 91 */       for (Timer timer : this.timers)
/* 92 */         timer.tick(); 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gates\GateExpansionTimer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */