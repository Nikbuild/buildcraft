/*    */ package buildcraft.core.lib.gui.widgets;
/*    */ 
/*    */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ButtonWidget
/*    */   extends Widget
/*    */ {
/*    */   private boolean pressed;
/*    */   private int buttonPressed;
/*    */   
/*    */   public ButtonWidget(int x, int y, int u, int v, int w, int h) {
/* 19 */     super(x, y, u, v, w, h);
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(GuiBuildCraft gui, int guiX, int guiY, int mouseX, int mouseY) {
/* 24 */     int vv = this.pressed ? (this.v + this.h) : this.v;
/* 25 */     gui.func_73729_b(guiX + this.x, guiY + this.y, this.u, vv, this.w, this.h);
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean handleMouseClick(int mouseX, int mouseY, int mouseButton) {
/* 30 */     this.pressed = true;
/* 31 */     this.buttonPressed = mouseButton;
/* 32 */     onPress(this.buttonPressed);
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void handleMouseRelease(int mouseX, int mouseY, int eventType) {
/* 38 */     if (this.pressed) {
/* 39 */       this.pressed = false;
/* 40 */       onRelease(this.buttonPressed);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public final void handleMouseMove(int mouseX, int mouseY, int mouseButton, long time) {
/* 46 */     if (this.pressed && !isMouseOver(mouseX, mouseY)) {
/* 47 */       this.pressed = false;
/* 48 */       onRelease(this.buttonPressed);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onPress(int mouseButton) {}
/*    */   
/*    */   public void onRelease(int mouseButton) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\widgets\ButtonWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */