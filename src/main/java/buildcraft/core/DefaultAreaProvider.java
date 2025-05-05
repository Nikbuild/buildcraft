/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.api.core.IAreaProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultAreaProvider
/*    */   implements IAreaProvider
/*    */ {
/*    */   int xMin;
/*    */   int yMin;
/*    */   int zMin;
/*    */   int xMax;
/*    */   int yMax;
/*    */   int zMax;
/*    */   
/*    */   public DefaultAreaProvider(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
/* 19 */     this.xMin = xMin;
/* 20 */     this.xMax = xMax;
/* 21 */     this.yMin = yMin;
/* 22 */     this.yMax = yMax;
/* 23 */     this.zMin = zMin;
/* 24 */     this.zMax = zMax;
/*    */   }
/*    */ 
/*    */   
/*    */   public int xMin() {
/* 29 */     return this.xMin;
/*    */   }
/*    */ 
/*    */   
/*    */   public int yMin() {
/* 34 */     return this.yMin;
/*    */   }
/*    */ 
/*    */   
/*    */   public int zMin() {
/* 39 */     return this.zMin;
/*    */   }
/*    */ 
/*    */   
/*    */   public int xMax() {
/* 44 */     return this.xMax;
/*    */   }
/*    */ 
/*    */   
/*    */   public int yMax() {
/* 49 */     return this.yMax;
/*    */   }
/*    */ 
/*    */   
/*    */   public int zMax() {
/* 54 */     return this.zMax;
/*    */   }
/*    */   
/*    */   public void removeFromWorld() {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\DefaultAreaProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */