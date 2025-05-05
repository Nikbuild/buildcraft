/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AverageInt
/*    */ {
/*    */   private int[] data;
/*    */   private int pos;
/*    */   private int precise;
/*    */   private int averageRaw;
/*    */   private int tickValue;
/*    */   
/*    */   public AverageInt(int precise) {
/* 17 */     this.precise = precise;
/* 18 */     clear();
/*    */   }
/*    */   
/*    */   public void clear() {
/* 22 */     this.data = new int[this.precise];
/* 23 */     this.pos = 0;
/*    */   }
/*    */   
/*    */   public double getAverage() {
/* 27 */     return this.averageRaw / this.precise;
/*    */   }
/*    */   
/*    */   public void tick(int value) {
/* 31 */     internalTick(this.tickValue + value);
/* 32 */     this.tickValue = 0;
/*    */   }
/*    */   
/*    */   public void tick() {
/* 36 */     internalTick(this.tickValue);
/* 37 */     this.tickValue = 0;
/*    */   }
/*    */   
/*    */   private void internalTick(int value) {
/* 41 */     this.pos = ++this.pos % this.precise;
/* 42 */     int oldValue = this.data[this.pos];
/* 43 */     this.data[this.pos] = value;
/* 44 */     if (this.pos == 0) {
/* 45 */       this.averageRaw = 0;
/* 46 */       for (int iValue : this.data) {
/* 47 */         this.averageRaw += iValue;
/*    */       }
/*    */     } else {
/* 50 */       this.averageRaw = this.averageRaw - oldValue + value;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void push(int value) {
/* 55 */     this.tickValue += value;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\AverageInt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */