/*    */ package buildcraft.core.lib.utils;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MathUtils
/*    */ {
/*    */   public static int clamp(int value, int min, int max) {
/* 20 */     return (value < min) ? min : ((value > max) ? max : value);
/*    */   }
/*    */   
/*    */   public static float clamp(float value, float min, float max) {
/* 24 */     return (value < min) ? min : ((value > max) ? max : value);
/*    */   }
/*    */   
/*    */   public static double clamp(double value, double min, double max) {
/* 28 */     return (value < min) ? min : ((value > max) ? max : value);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\MathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */