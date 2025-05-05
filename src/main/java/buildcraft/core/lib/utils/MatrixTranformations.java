/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import net.minecraftforge.common.util.ForgeDirection;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MatrixTranformations
/*    */ {
/*    */   public static void mirrorY(float[][] targetArray) {
/* 27 */     float temp = targetArray[1][0];
/* 28 */     targetArray[1][0] = (targetArray[1][1] - 0.5F) * -1.0F + 0.5F;
/* 29 */     targetArray[1][1] = (temp - 0.5F) * -1.0F + 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void rotate(float[][] targetArray) {
/* 40 */     for (int i = 0; i < 2; i++) {
/* 41 */       float temp = targetArray[2][i];
/* 42 */       targetArray[2][i] = targetArray[1][i];
/* 43 */       targetArray[1][i] = targetArray[0][i];
/* 44 */       targetArray[0][i] = temp;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void transform(float[][] targetArray, ForgeDirection direction) {
/* 53 */     if ((direction.ordinal() & 0x1) == 1) {
/* 54 */       mirrorY(targetArray);
/*    */     }
/*    */     
/* 57 */     for (int i = 0; i < direction.ordinal() >> 1; i++) {
/* 58 */       rotate(targetArray);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float[][] deepClone(float[][] source) {
/* 68 */     float[][] target = (float[][])source.clone();
/* 69 */     for (int i = 0; i < target.length; i++) {
/* 70 */       target[i] = (float[])source[i].clone();
/*    */     }
/* 72 */     return target;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\MatrixTranformations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */