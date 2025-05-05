/*    */ package buildcraft.core.lib.inventory;
/*    */ 
/*    */ import buildcraft.core.lib.utils.Utils;
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
/*    */ 
/*    */ public class InventoryWrapperSimple
/*    */   extends InventoryWrapper
/*    */ {
/*    */   private final int[] slots;
/*    */   
/*    */   public InventoryWrapperSimple(IInventory inventory) {
/* 21 */     super(inventory);
/* 22 */     this.slots = Utils.createSlotArray(0, inventory.func_70302_i_());
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] func_94128_d(int var1) {
/* 27 */     return this.slots;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_102007_a(int slotIndex, ItemStack itemstack, int side) {
/* 32 */     return func_94041_b(slotIndex, itemstack);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_102008_b(int slotIndex, ItemStack itemstack, int side) {
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\InventoryWrapperSimple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */