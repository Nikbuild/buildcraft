/*    */ package buildcraft.factory.gui;
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
/*    */ public class SlotWorkbench
/*    */   extends SlotPhantom
/*    */ {
/*    */   public SlotWorkbench(IInventory iinventory, int slotIndex, int posX, int posY) {
/* 18 */     super(iinventory, slotIndex, posX, posY);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 23 */     return (stack != null && !stack.func_77973_b().hasContainerItem(stack));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canShift() {
/* 28 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\gui\SlotWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */