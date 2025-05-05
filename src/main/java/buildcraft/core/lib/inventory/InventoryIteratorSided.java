/*    */ package buildcraft.core.lib.inventory;
/*    */ 
/*    */ import buildcraft.api.core.IInvSlot;
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.inventory.ISidedInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
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
/*    */ 
/*    */ class InventoryIteratorSided
/*    */   implements Iterable<IInvSlot>
/*    */ {
/*    */   private final ISidedInventory inv;
/*    */   private final int side;
/*    */   
/*    */   InventoryIteratorSided(ISidedInventory inv, ForgeDirection side) {
/* 26 */     this.inv = inv;
/* 27 */     this.side = side.ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<IInvSlot> iterator() {
/* 32 */     return new Iterator<IInvSlot>() {
/* 33 */         int[] slots = InventoryIteratorSided.this.inv.func_94128_d(InventoryIteratorSided.this.side);
/* 34 */         int index = 0;
/*    */ 
/*    */         
/*    */         public boolean hasNext() {
/* 38 */           return (this.slots != null) ? ((this.index < this.slots.length)) : false;
/*    */         }
/*    */ 
/*    */         
/*    */         public IInvSlot next() {
/* 43 */           return (this.slots != null) ? new InventoryIteratorSided.InvSlot(this.slots[this.index++]) : null;
/*    */         }
/*    */ 
/*    */         
/*    */         public void remove() {
/* 48 */           throw new UnsupportedOperationException("Remove not supported.");
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   private class InvSlot
/*    */     implements IInvSlot
/*    */   {
/*    */     private int slot;
/*    */     
/*    */     public InvSlot(int slot) {
/* 59 */       this.slot = slot;
/*    */     }
/*    */ 
/*    */     
/*    */     public ItemStack getStackInSlot() {
/* 64 */       return InventoryIteratorSided.this.inv.func_70301_a(this.slot);
/*    */     }
/*    */ 
/*    */     
/*    */     public void setStackInSlot(ItemStack stack) {
/* 69 */       InventoryIteratorSided.this.inv.func_70299_a(this.slot, stack);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean canPutStackInSlot(ItemStack stack) {
/* 74 */       return (InventoryIteratorSided.this.inv.func_102007_a(this.slot, stack, InventoryIteratorSided.this.side) && InventoryIteratorSided.this.inv.func_94041_b(this.slot, stack));
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean canTakeStackFromSlot(ItemStack stack) {
/* 79 */       return InventoryIteratorSided.this.inv.func_102008_b(this.slot, stack, InventoryIteratorSided.this.side);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isItemValidForSlot(ItemStack stack) {
/* 84 */       return InventoryIteratorSided.this.inv.func_94041_b(this.slot, stack);
/*    */     }
/*    */ 
/*    */     
/*    */     public ItemStack decreaseStackInSlot(int amount) {
/* 89 */       return InventoryIteratorSided.this.inv.func_70298_a(this.slot, amount);
/*    */     }
/*    */ 
/*    */     
/*    */     public int getIndex() {
/* 94 */       return this.slot;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\InventoryIteratorSided.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */