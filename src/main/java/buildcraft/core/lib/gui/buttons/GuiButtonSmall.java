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
/*    */ public class GuiButtonSmall
/*    */   extends GuiBetterButton
/*    */ {
/*    */   public GuiButtonSmall(int i, int x, int y, String s) {
/* 18 */     this(i, x, y, 200, s);
/*    */   }
/*    */   
/*    */   public GuiButtonSmall(int i, int x, int y, int w, String s) {
/* 22 */     super(i, x, y, w, StandardButtonTextureSets.SMALL_BUTTON, s);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\buttons\GuiButtonSmall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */