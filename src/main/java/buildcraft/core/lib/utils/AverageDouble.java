/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AverageDouble
/*    */ {
/*    */   private double[] data;
/*    */   private int pos;
/*    */   private int precise;
/*    */   private double averageRaw;
/*    */   private double tickValue;
/*    */   
/*    */   public AverageDouble(int precise) {
/* 17 */     this.precise = precise;
/* 18 */     this.data = new double[precise];
/* 19 */     this.pos = 0;
/*    */   }
/*    */   
/*    */   public double getAverage() {
/* 23 */     return this.averageRaw / this.precise;
/*    */   }
/*    */   
/*    */   public void tick(double value) {
/* 27 */     internalTick(this.tickValue + value);
/* 28 */     this.tickValue = 0.0D;
/*    */   }
/*    */   
/*    */   public void tick() {
/* 32 */     internalTick(this.tickValue);
/* 33 */     this.tickValue = 0.0D;
/*    */   }
/*    */   
/*    */   private void internalTick(double value) {
/* 37 */     this.pos = ++this.pos % this.precise;
/* 38 */     double oldValue = this.data[this.pos];
/* 39 */     this.data[this.pos] = value;
/* 40 */     if (this.pos == 0) {
/* 41 */       this.averageRaw = 0.0D;
/* 42 */       for (double iValue : this.data) {
/* 43 */         this.averageRaw += iValue;
/*    */       }
/*    */     } else {
/* 46 */       this.averageRaw = this.averageRaw - oldValue + value;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void push(double value) {
/* 51 */     this.tickValue += value;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\AverageDouble.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */