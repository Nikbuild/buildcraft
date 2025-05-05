/*    */ package buildcraft.core.tablet.utils;
/*    */ 
/*    */ import buildcraft.api.tablet.TabletBitmap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TabletDrawUtils
/*    */ {
/*    */   public static void drawRect(TabletBitmap bitmap, int x, int y, int w, int h, int shade) {
/* 11 */     int x2 = x + w - 1;
/* 12 */     int y2 = y + h - 1; int i;
/* 13 */     for (i = 0; i < w; i++) {
/* 14 */       bitmap.set(x + i, y, shade);
/* 15 */       bitmap.set(x + i, y2, shade);
/*    */     } 
/* 17 */     for (i = 1; i < h - 1; i++) {
/* 18 */       bitmap.set(x, y + i, shade);
/* 19 */       bitmap.set(x2, y + i, shade);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void drawFilledRect(TabletBitmap bitmap, int x, int y, int w, int h, int shade) {
/* 24 */     for (int j = 0; j < h; j++) {
/* 25 */       for (int i = 0; i < w; i++)
/* 26 */         bitmap.set(x + i, y + j, bitmap); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\table\\utils\TabletDrawUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */