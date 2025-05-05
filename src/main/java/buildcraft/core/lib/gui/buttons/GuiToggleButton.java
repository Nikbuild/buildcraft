/*    */ package buildcraft.core.lib.gui.buttons;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiToggleButton
/*    */   extends GuiBetterButton
/*    */ {
/*    */   public boolean active;
/*    */   
/*    */   public GuiToggleButton(int id, int x, int y, String label, boolean active) {
/* 16 */     this(id, x, y, 200, StandardButtonTextureSets.LARGE_BUTTON, label, active);
/*    */   }
/*    */   
/*    */   public GuiToggleButton(int id, int x, int y, int width, String s, boolean active) {
/* 20 */     super(id, x, y, width, StandardButtonTextureSets.LARGE_BUTTON, s);
/* 21 */     this.active = active;
/*    */   }
/*    */   
/*    */   public GuiToggleButton(int id, int x, int y, int width, IButtonTextureSet texture, String s, boolean active) {
/* 25 */     super(id, x, y, width, texture, s);
/* 26 */     this.active = active;
/*    */   }
/*    */   
/*    */   public void toggle() {
/* 30 */     this.active = !this.active;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_146114_a(boolean mouseOver) {
/* 35 */     int state = 1;
/* 36 */     if (!this.field_146124_l) {
/* 37 */       state = 0;
/* 38 */     } else if (mouseOver) {
/* 39 */       state = 2;
/* 40 */     } else if (!this.active) {
/* 41 */       state = 3;
/*    */     } 
/* 43 */     return state;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTextColor(boolean mouseOver) {
/* 48 */     if (!this.field_146124_l)
/* 49 */       return -6250336; 
/* 50 */     if (mouseOver)
/* 51 */       return 16777120; 
/* 52 */     if (!this.active) {
/* 53 */       return 7829367;
/*    */     }
/* 55 */     return 14737632;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\buttons\GuiToggleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */