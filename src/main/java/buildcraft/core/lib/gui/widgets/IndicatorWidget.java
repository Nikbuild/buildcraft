/*    */ package buildcraft.core.lib.gui.widgets;
/*    */ 
/*    */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*    */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IndicatorWidget
/*    */   extends Widget
/*    */ {
/*    */   public final IIndicatorController controller;
/*    */   
/*    */   public IndicatorWidget(IIndicatorController controller, int x, int y, int u, int v, int w, int h) {
/* 22 */     super(x, y, u, v, w, h);
/* 23 */     this.controller = controller;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void draw(GuiBuildCraft gui, int guiX, int guiY, int mouseX, int mouseY) {
/* 29 */     int scale = this.controller.getScaledLevel(this.h);
/* 30 */     gui.func_73729_b(guiX + this.x, guiY + this.y + this.h - scale, this.u, this.v + this.h - scale, this.w, scale);
/*    */   }
/*    */ 
/*    */   
/*    */   public ToolTip getToolTip() {
/* 35 */     return this.controller.getToolTip();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\widgets\IndicatorWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */