/*    */ package buildcraft.core.lib.gui.widgets;
/*    */ 
/*    */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*    */ import buildcraft.core.lib.utils.MathUtils;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class ScrollbarWidget extends Widget {
/*    */   private static final int HEIGHT = 14;
/*    */   private int pos;
/*    */   private int len;
/*    */   private boolean isClicking;
/*    */   
/*    */   public ScrollbarWidget(int x, int y, int u, int v, int h) {
/* 15 */     super(x, y, u, v, 6, h);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void draw(GuiBuildCraft gui, int guiX, int guiY, int mouseX, int mouseY) {
/* 21 */     gui.func_73729_b(guiX + this.x, guiY + this.y, this.u, this.v, this.w, this.h);
/* 22 */     int posPx = this.pos * (this.h - 14 + 2) / this.len;
/* 23 */     gui.func_73729_b(guiX + this.x, guiY + this.y + posPx, this.u + 6, this.v, this.w, 14);
/*    */   }
/*    */   
/*    */   private void updateLength(int mouseY) {
/* 27 */     setPosition(((mouseY - this.y) * this.len + this.h / 2) / this.h);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public boolean handleMouseClick(int mouseX, int mouseY, int mouseButton) {
/* 33 */     if (mouseButton == 0) {
/* 34 */       this.isClicking = true;
/* 35 */       updateLength(mouseY);
/* 36 */       return true;
/*    */     } 
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void handleMouseMove(int mouseX, int mouseY, int mouseButton, long time) {
/* 44 */     if (this.isClicking && mouseButton == 0) {
/* 45 */       updateLength(mouseY);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void handleMouseRelease(int mouseX, int mouseY, int eventType) {
/* 52 */     if (this.isClicking && eventType == 0) {
/* 53 */       updateLength(mouseY);
/* 54 */       this.isClicking = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getPosition() {
/* 59 */     return this.pos;
/*    */   }
/*    */   
/*    */   public void setPosition(int pos) {
/* 63 */     this.pos = MathUtils.clamp(pos, 0, this.len);
/*    */   }
/*    */   
/*    */   public void setLength(int len) {
/* 67 */     this.len = len;
/* 68 */     setPosition(this.pos);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\widgets\ScrollbarWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */