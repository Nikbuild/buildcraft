/*    */ package buildcraft.api.core;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class JavaTools
/*    */ {
/*    */   public static <T> T[] concat(T[] first, T[] second) {
/* 15 */     T[] result = Arrays.copyOf(first, first.length + second.length);
/* 16 */     System.arraycopy(second, 0, result, first.length, second.length);
/* 17 */     return result;
/*    */   }
/*    */   
/*    */   public static int[] concat(int[] first, int[] second) {
/* 21 */     int[] result = Arrays.copyOf(first, first.length + second.length);
/* 22 */     System.arraycopy(second, 0, result, first.length, second.length);
/* 23 */     return result;
/*    */   }
/*    */   
/*    */   public static float[] concat(float[] first, float[] second) {
/* 27 */     float[] result = Arrays.copyOf(first, first.length + second.length);
/* 28 */     System.arraycopy(second, 0, result, first.length, second.length);
/* 29 */     return result;
/*    */   }
/*    */   
/*    */   public static String surroundWithQuotes(String stringToSurroundWithQuotes) {
/* 33 */     return String.format("\"%s\"", new Object[] { stringToSurroundWithQuotes });
/*    */   }
/*    */   
/*    */   public static String stripSurroundingQuotes(String stringToStripQuotes) {
/* 37 */     return stringToStripQuotes.replaceAll("^\"|\"$", "");
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\JavaTools.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */