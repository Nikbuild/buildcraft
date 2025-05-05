/*    */ package buildcraft.core.lib.gui.slots;
/*    */ 
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotValidated
/*    */   extends Slot
/*    */ {
/*    */   public SlotValidated(IInventory inv, int id, int x, int y) {
/* 18 */     super(inv, id, x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack itemStack) {
/* 23 */     return this.field_75224_c.func_94041_b(getSlotIndex(), itemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\slots\SlotValidated.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */