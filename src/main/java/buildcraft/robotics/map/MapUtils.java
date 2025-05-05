/*    */ package buildcraft.robotics.map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MapUtils
/*    */ {
/*    */   public static long getIDFromCoords(int x, int z) {
/*  9 */     return ((x & 0xFFFFFF) << 24 | z & 0xFFFFFF);
/*    */   }
/*    */   
/*    */   public static int getXFromID(long id) {
/* 13 */     return (int)(id >> 24L);
/*    */   }
/*    */   
/*    */   public static int getZFromID(long id) {
/* 17 */     int z = (int)(id & 0xFFFFFFL);
/* 18 */     if (z >= 8388608) {
/* 19 */       return -(z ^ 0xFFFFFF);
/*    */     }
/* 21 */     return z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\map\MapUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */