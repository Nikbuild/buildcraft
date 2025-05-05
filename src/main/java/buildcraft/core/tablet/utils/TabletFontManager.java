/*    */ package buildcraft.core.tablet.utils;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class TabletFontManager {
/*  7 */   public static final TabletFontManager INSTANCE = new TabletFontManager();
/*    */   
/*  9 */   public HashMap<String, TabletFont> fonts = new HashMap<String, TabletFont>();
/*    */   
/*    */   public TabletFont register(String name, InputStream stream) {
/*    */     try {
/* 13 */       this.fonts.put(name, new TabletFont(stream));
/* 14 */     } catch (Exception e) {
/* 15 */       e.printStackTrace();
/*    */     } 
/* 17 */     return get(name);
/*    */   }
/*    */   
/*    */   public TabletFont get(String font) {
/* 21 */     return this.fonts.get(font);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\table\\utils\TabletFontManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */