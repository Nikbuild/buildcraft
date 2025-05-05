/*    */ package buildcraft.core.tablet;
/*    */ 
/*    */ import buildcraft.api.tablet.TabletBitmap;
/*    */ import buildcraft.api.tablet.TabletTicker;
/*    */ 
/*    */ class TabletRenderer {
/*    */   private TabletBitmap currDisplay;
/*  8 */   private final TabletTicker refreshRate = new TabletTicker(0.035F); private TabletBitmap newDisplay;
/*    */   private boolean changed = false;
/*    */   private boolean isTicking = false;
/* 11 */   private int tickLocation = 7;
/*    */   
/*    */   public TabletRenderer(TabletBitmap display) {
/* 14 */     this.currDisplay = display;
/*    */   }
/*    */   
/*    */   public TabletBitmap get() {
/* 18 */     return this.currDisplay;
/*    */   }
/*    */   
/*    */   public boolean shouldChange() {
/* 22 */     boolean oldChanged = this.changed;
/* 23 */     this.changed = false;
/* 24 */     return oldChanged;
/*    */   }
/*    */   
/*    */   public void update(TabletBitmap display) {
/* 28 */     synchronized (this.refreshRate) {
/* 29 */       this.newDisplay = display;
/* 30 */       this.isTicking = true;
/* 31 */       this.tickLocation = 7;
/* 32 */       this.refreshRate.reset();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean tick(float tick) {
/* 37 */     if (this.isTicking) {
/* 38 */       synchronized (this.refreshRate) {
/* 39 */         this.refreshRate.add(tick);
/* 40 */         this.changed = false;
/* 41 */         for (int times = 0; times < this.refreshRate.getTicks(); times++) {
/* 42 */           for (int j = 0; j < this.currDisplay.height; j++) {
/* 43 */             for (int i = 0; i < this.currDisplay.width; i++) {
/* 44 */               int oldI = this.currDisplay.get(i, j);
/* 45 */               int newI = this.newDisplay.get(i, j);
/* 46 */               if (Math.abs(oldI - newI) == this.tickLocation) {
/* 47 */                 if (oldI < newI) {
/* 48 */                   this.changed = true;
/* 49 */                   this.currDisplay.set(i, j, oldI + 1);
/* 50 */                 } else if (oldI > newI) {
/* 51 */                   this.changed = true;
/* 52 */                   this.currDisplay.set(i, j, oldI - 1);
/*    */                 } 
/*    */               }
/*    */             } 
/*    */           } 
/*    */           
/* 58 */           this.tickLocation--;
/*    */           
/* 60 */           if (!this.changed || this.tickLocation == 0) {
/* 61 */             this.isTicking = false;
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/* 66 */         this.refreshRate.tick();
/*    */       } 
/*    */       
/* 69 */       return true;
/*    */     } 
/* 71 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\tablet\TabletRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */