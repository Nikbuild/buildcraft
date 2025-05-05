/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.slots.SlotPhantom;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotPackager
/*    */   extends SlotPhantom
/*    */ {
/*    */   public SlotPackager(IInventory iinventory, int slotIndex, int posX, int posY) {
/* 18 */     super(iinventory, slotIndex, posX, posY);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 23 */     return (stack != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canShift() {
/* 28 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_75219_a() {
/* 33 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\SlotPackager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */