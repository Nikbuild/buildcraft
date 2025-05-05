/*    */ package buildcraft.core.tablet.manager;
/*    */ 
/*    */ import buildcraft.core.tablet.TabletBase;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class TabletThread
/*    */   implements Runnable
/*    */ {
/*    */   private final TabletBase tablet;
/*    */   private long begunTickDate;
/*    */   private long lastTickReceivedDate;
/* 12 */   private float ticksLeft = 0.0F;
/*    */   private boolean isRunning = false;
/*    */   
/*    */   public TabletThread(TabletBase tablet) {
/* 16 */     this.tablet = tablet;
/* 17 */     this.lastTickReceivedDate = this.begunTickDate = (new Date()).getTime();
/*    */   }
/*    */   
/*    */   public TabletBase getTablet() {
/* 21 */     return this.tablet;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 26 */     this.isRunning = true;
/* 27 */     while (this.isRunning) {
/* 28 */       if (this.ticksLeft > 0.0F) {
/* 29 */         this.begunTickDate = (new Date()).getTime();
/* 30 */         this.tablet.tick(this.ticksLeft);
/* 31 */         float timeElapsed = (float)(this.lastTickReceivedDate - this.begunTickDate) / 1000.0F;
/* 32 */         if (timeElapsed > 0.0F)
/* 33 */           this.ticksLeft -= timeElapsed; 
/*    */         continue;
/*    */       } 
/*    */       try {
/* 37 */         Thread.sleep(1L);
/* 38 */       } catch (Exception exception) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void stop() {
/* 45 */     this.isRunning = false;
/*    */   }
/*    */   
/*    */   public void tick(float time) {
/* 49 */     this.ticksLeft += time;
/* 50 */     this.lastTickReceivedDate = (new Date()).getTime();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\tablet\manager\TabletThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */