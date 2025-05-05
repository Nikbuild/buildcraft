/*    */ package buildcraft.api.tablet;
/*    */ 
/*    */ public class TabletBitmap
/*    */ {
/*    */   public final int width;
/*    */   
/*    */   public TabletBitmap(int width, int height) {
/*  8 */     this.width = width;
/*  9 */     this.height = height;
/* 10 */     this.data = new int[width * height];
/*    */   }
/*    */   public final int height; protected int[] data;
/*    */   public TabletBitmap(ITablet tablet) {
/* 14 */     this(tablet.getScreenWidth(), tablet.getScreenHeight());
/*    */   }
/*    */   
/*    */   public int[] getData() {
/* 18 */     return this.data;
/*    */   }
/*    */   
/*    */   public int get(int x, int y) {
/* 22 */     if (x < 0 || y < 0 || x >= this.width || y >= this.height) {
/* 23 */       return 0;
/*    */     }
/* 25 */     return this.data[y * this.width + x];
/*    */   }
/*    */   
/*    */   public void set(int x, int y, int shade) {
/* 29 */     if (x < 0 || y < 0 || x >= this.width || y >= this.height) {
/*    */       return;
/*    */     }
/* 32 */     this.data[y * this.width + x] = shade;
/*    */   }
/*    */   
/*    */   public void set(int x, int y, TabletBitmap bitmap) {
/* 36 */     for (int i = 0; i < bitmap.height && 
/* 37 */       i < this.height; i++) {
/*    */ 
/*    */       
/* 40 */       for (int h = 0; h < bitmap.width && 
/* 41 */         h < this.width; h++)
/*    */       {
/*    */ 
/*    */         
/* 45 */         set(x + h, y + i, bitmap.get(h, i));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public TabletBitmap duplicate() {
/* 51 */     TabletBitmap cloned = new TabletBitmap(this.width, this.height);
/* 52 */     cloned.data = (int[])this.data.clone();
/* 53 */     return cloned;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\tablet\TabletBitmap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */