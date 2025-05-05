/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import java.util.Date;
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
/*    */ public class IterableAlgorithmRunner
/*    */   extends Thread
/*    */ {
/*    */   private IIterableAlgorithm pathFinding;
/*    */   private boolean stop = false;
/*    */   private int maxIterations;
/*    */   private boolean done = false;
/*    */   
/*    */   public IterableAlgorithmRunner(IIterableAlgorithm iPathFinding, int iMaxIterations) {
/* 23 */     super("Path Finding");
/* 24 */     this.pathFinding = iPathFinding;
/* 25 */     this.maxIterations = iMaxIterations;
/*    */   }
/*    */   
/*    */   public IterableAlgorithmRunner(IIterableAlgorithm iPathFinding) {
/* 29 */     this(iPathFinding, 1000);
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 35 */       for (int i = 0; i < this.maxIterations && 
/* 36 */         !isTerminated() && !this.pathFinding.isDone(); i++) {
/*    */ 
/*    */ 
/*    */         
/* 40 */         long startTime = (new Date()).getTime();
/*    */         
/* 42 */         this.pathFinding.iterate();
/*    */         
/* 44 */         long elapsedTime = (new Date()).getTime() - startTime;
/* 45 */         int timeToWait = MathUtils.clamp((int)Math.ceil(elapsedTime * 1.5D), 1, 500);
/* 46 */         sleep(timeToWait);
/*    */       } 
/* 48 */     } catch (Throwable t) {
/* 49 */       t.printStackTrace();
/*    */     } finally {
/* 51 */       this.done = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public synchronized void terminate() {
/* 56 */     this.stop = true;
/*    */   }
/*    */   
/*    */   public synchronized boolean isTerminated() {
/* 60 */     return this.stop;
/*    */   }
/*    */   
/*    */   public synchronized boolean isDone() {
/* 64 */     return this.done;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\IterableAlgorithmRunner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */