/*    */ package buildcraft.core.tablet.utils;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TabletTextUtils
/*    */ {
/*    */   public static String[] split(String text, TabletFont font, int width, boolean justify) {
/* 12 */     List<String> lines = new ArrayList<String>();
/*    */     
/* 14 */     int x = 0;
/* 15 */     String line = "";
/* 16 */     boolean first = true;
/*    */     
/* 18 */     for (String s : text.split(" ")) {
/* 19 */       String ts = first ? s : (" " + s);
/* 20 */       int w = font.getStringWidth(ts);
/* 21 */       if (x + w > width) {
/* 22 */         x = 0;
/* 23 */         ts = s;
/* 24 */         lines.add(line);
/* 25 */         line = "";
/*    */       } 
/* 27 */       x += w;
/* 28 */       line = line + ts;
/* 29 */       first = false;
/*    */     } 
/* 31 */     if (line.length() > 0) {
/* 32 */       lines.add(line);
/*    */     }
/* 34 */     return lines.<String>toArray(new String[lines.size()]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\table\\utils\TabletTextUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */