/*    */ package buildcraft.core.lib.gui.buttons;
/*    */ 
/*    */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum LockButtonState
/*    */   implements IMultiButtonState
/*    */ {
/* 15 */   UNLOCKED(new ButtonTextureSet(224, 0, 16, 16)), LOCKED(new ButtonTextureSet(240, 0, 16, 16)); private final IButtonTextureSet texture; static {
/* 16 */     VALUES = values();
/*    */   }
/*    */   public static final LockButtonState[] VALUES;
/*    */   LockButtonState(IButtonTextureSet texture) {
/* 20 */     this.texture = texture;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLabel() {
/* 25 */     return "";
/*    */   }
/*    */ 
/*    */   
/*    */   public IButtonTextureSet getTextureSet() {
/* 30 */     return this.texture;
/*    */   }
/*    */ 
/*    */   
/*    */   public ToolTip getToolTip() {
/* 35 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\buttons\LockButtonState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */