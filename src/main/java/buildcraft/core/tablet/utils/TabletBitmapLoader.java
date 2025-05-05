/*    */ package buildcraft.core.tablet.utils;
/*    */ 
/*    */ import buildcraft.api.tablet.TabletBitmap;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TabletBitmapLoader
/*    */ {
/*    */   public static TabletBitmap createFromGray(InputStream stream, int width, int height) {
/*    */     try {
/* 17 */       byte[] data = new byte[stream.available()];
/* 18 */       stream.read(data);
/* 19 */       stream.close();
/*    */       
/* 21 */       TabletBitmap bitmap = new TabletBitmap(width, height);
/* 22 */       for (int i = 0; i < width * height; i++) {
/* 23 */         bitmap.set(i % width, i / width, (data[i * 2] >>> 5 ^ 0xFFFFFFFF) & 0x7);
/*    */       }
/* 25 */       return bitmap;
/* 26 */     } catch (Exception e) {
/* 27 */       e.printStackTrace();
/* 28 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\table\\utils\TabletBitmapLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */