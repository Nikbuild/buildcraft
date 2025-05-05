/*    */ package buildcraft.api.tablet;
/*    */ 
/*    */ public class TabletTicker
/*    */ {
/*    */   private final float tickTime;
/*  6 */   private float time = 0.0F;
/*  7 */   private int ticked = 0;
/*    */   
/*    */   public TabletTicker(float tickTime) {
/* 10 */     this.tickTime = tickTime;
/*    */   }
/*    */   
/*    */   public void add(float time) {
/* 14 */     this.time += time;
/*    */     
/* 16 */     while (this.time >= this.tickTime) {
/* 17 */       this.time -= this.tickTime;
/* 18 */       this.ticked++;
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getTicks() {
/* 23 */     return this.ticked;
/*    */   }
/*    */   
/*    */   public boolean tick() {
/* 27 */     boolean oldTicked = (this.ticked > 0);
/* 28 */     this.ticked = 0;
/* 29 */     return oldTicked;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 33 */     this.ticked = 0;
/* 34 */     this.time = 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\tablet\TabletTicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */