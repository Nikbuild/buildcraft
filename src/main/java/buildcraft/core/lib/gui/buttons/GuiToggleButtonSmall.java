/*    */ package buildcraft.core.lib.gui.buttons;
/*    */ 
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
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiToggleButtonSmall
/*    */   extends GuiToggleButton
/*    */ {
/*    */   public GuiToggleButtonSmall(int i, int j, int k, String s, boolean active) {
/* 18 */     this(i, j, k, 200, s, active);
/*    */   }
/*    */   
/*    */   public GuiToggleButtonSmall(int i, int x, int y, int w, String s, boolean active) {
/* 22 */     super(i, x, y, w, StandardButtonTextureSets.SMALL_BUTTON, s, active);
/* 23 */     this.active = active;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\buttons\GuiToggleButtonSmall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */