/*    */ package buildcraft.api.core;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SafeTimeTracker
/*    */ {
/* 12 */   private long lastMark = Long.MIN_VALUE;
/* 13 */   private long duration = -1L;
/* 14 */   private long randomRange = 0L;
/* 15 */   private long lastRandomDelay = 0L;
/* 16 */   private long internalDelay = 1L;
/*    */ 
/*    */ 
/*    */   
/*    */   public SafeTimeTracker() {}
/*    */ 
/*    */   
/*    */   public SafeTimeTracker(long delay) {
/* 24 */     this.internalDelay = delay;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SafeTimeTracker(long delay, long random) {
/* 32 */     this.internalDelay = delay;
/* 33 */     this.randomRange = random;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean markTimeIfDelay(World world) {
/* 38 */     return markTimeIfDelay(world, this.internalDelay);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean markTimeIfDelay(World world, long delay) {
/* 43 */     if (world == null) {
/* 44 */       return false;
/*    */     }
/*    */     
/* 47 */     long currentTime = world.func_82737_E();
/*    */     
/* 49 */     if (currentTime < this.lastMark) {
/* 50 */       this.lastMark = currentTime;
/* 51 */       return false;
/* 52 */     }  if (this.lastMark + delay + this.lastRandomDelay <= currentTime) {
/* 53 */       this.duration = currentTime - this.lastMark;
/* 54 */       this.lastMark = currentTime;
/* 55 */       this.lastRandomDelay = (int)(Math.random() * this.randomRange);
/*    */       
/* 57 */       return true;
/*    */     } 
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public long durationOfLastDelay() {
/* 64 */     return (this.duration > 0L) ? this.duration : 0L;
/*    */   }
/*    */   
/*    */   public void markTime(World world) {
/* 68 */     this.lastMark = world.func_82737_E();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\SafeTimeTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */