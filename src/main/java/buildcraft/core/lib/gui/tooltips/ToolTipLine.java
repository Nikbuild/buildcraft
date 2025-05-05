/*    */ package buildcraft.core.lib.gui.tooltips;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ToolTipLine
/*    */ {
/*    */   public String text;
/*    */   public final int color;
/*    */   public int spacing;
/*    */   
/*    */   public ToolTipLine(String text, int color) {
/* 18 */     this.text = text;
/* 19 */     this.color = color;
/*    */   }
/*    */   
/*    */   public ToolTipLine(String text) {
/* 23 */     this(text, -1);
/*    */   }
/*    */   
/*    */   public ToolTipLine() {
/* 27 */     this("", -1);
/*    */   }
/*    */   
/*    */   public void setSpacing(int spacing) {
/* 31 */     this.spacing = spacing;
/*    */   }
/*    */   
/*    */   public int getSpacing() {
/* 35 */     return this.spacing;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\tooltips\ToolTipLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */