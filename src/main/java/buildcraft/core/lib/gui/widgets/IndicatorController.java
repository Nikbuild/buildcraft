/*    */ package buildcraft.core.lib.gui.widgets;
/*    */ 
/*    */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*    */ import buildcraft.core.lib.gui.tooltips.ToolTipLine;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class IndicatorController
/*    */   implements IIndicatorController
/*    */ {
/* 16 */   protected ToolTipLine tip = new ToolTipLine();
/*    */   
/* 18 */   private final ToolTip tips = new ToolTip(new ToolTipLine[0])
/*    */     {
/*    */       public void refresh() {
/* 21 */         IndicatorController.this.refreshToolTip();
/*    */       }
/*    */     };
/*    */   
/*    */   public IndicatorController() {
/* 26 */     this.tips.add(this.tip);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void refreshToolTip() {}
/*    */ 
/*    */   
/*    */   public final ToolTip getToolTip() {
/* 34 */     return this.tips;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\widgets\IndicatorController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */