/*    */ package buildcraft.core.lib.gui.slots;
/*    */ 
/*    */ import buildcraft.core.lib.gui.tooltips.IToolTipProvider;
/*    */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotBase
/*    */   extends Slot
/*    */   implements IToolTipProvider
/*    */ {
/*    */   private ToolTip toolTips;
/*    */   
/*    */   public SlotBase(IInventory iinventory, int slotIndex, int posX, int posY) {
/* 22 */     super(iinventory, slotIndex, posX, posY);
/*    */   }
/*    */   
/*    */   public boolean canShift() {
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToolTip getToolTip() {
/* 34 */     return this.toolTips;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setToolTips(ToolTip toolTips) {
/* 41 */     this.toolTips = toolTips;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isToolTipVisible() {
/* 46 */     return (func_75211_c() == null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMouseOver(int mouseX, int mouseY) {
/* 51 */     return (mouseX >= this.field_75223_e && mouseX <= this.field_75223_e + 16 && mouseY >= this.field_75221_f && mouseY <= this.field_75221_f + 16);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\slots\SlotBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */