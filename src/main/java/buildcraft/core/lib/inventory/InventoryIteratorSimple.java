/*    */ package buildcraft.core.lib.inventory;
/*    */ 
/*    */ import buildcraft.api.core.IInvSlot;
/*    */ import java.util.Iterator;
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
/*    */ 
/*    */ class InventoryIteratorSimple
/*    */   implements Iterable<IInvSlot>
/*    */ {
/*    */   private final IInventory inv;
/*    */   
/*    */   InventoryIteratorSimple(IInventory inv) {
/* 23 */     this.inv = InvUtils.getInventory(inv);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<IInvSlot> iterator() {
/* 28 */     return new Iterator<IInvSlot>() {
/* 29 */         int slot = 0;
/*    */ 
/*    */         
/*    */         public boolean hasNext() {
/* 33 */           return (this.slot < InventoryIteratorSimple.this.inv.func_70302_i_());
/*    */         }
/*    */ 
/*    */         
/*    */         public IInvSlot next() {
/* 38 */           return new InventoryIteratorSimple.InvSlot(this.slot++);
/*    */         }
/*    */ 
/*    */         
/*    */         public void remove() {
/* 43 */           throw new UnsupportedOperationException("Remove not supported.");
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   private class InvSlot
/*    */     implements IInvSlot {
/*    */     private int slot;
/*    */     
/*    */     public InvSlot(int slot) {
/* 53 */       this.slot = slot;
/*    */     }
/*    */ 
/*    */     
/*    */     public ItemStack getStackInSlot() {
/* 58 */       return InventoryIteratorSimple.this.inv.func_70301_a(this.slot);
/*    */     }
/*    */ 
/*    */     
/*    */     public void setStackInSlot(ItemStack stack) {
/* 63 */       InventoryIteratorSimple.this.inv.func_70299_a(this.slot, stack);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean canPutStackInSlot(ItemStack stack) {
/* 68 */       return InventoryIteratorSimple.this.inv.func_94041_b(this.slot, stack);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean canTakeStackFromSlot(ItemStack stack) {
/* 73 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isItemValidForSlot(ItemStack stack) {
/* 78 */       return InventoryIteratorSimple.this.inv.func_94041_b(this.slot, stack);
/*    */     }
/*    */ 
/*    */     
/*    */     public ItemStack decreaseStackInSlot(int amount) {
/* 83 */       return InventoryIteratorSimple.this.inv.func_70298_a(this.slot, amount);
/*    */     }
/*    */ 
/*    */     
/*    */     public int getIndex() {
/* 88 */       return this.slot;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\InventoryIteratorSimple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */