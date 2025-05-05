/*    */ package buildcraft.core.lib.inventory;
/*    */ 
/*    */ import buildcraft.api.core.IInvSlot;
/*    */ import net.minecraft.inventory.IInventory;
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
/*    */ public class TransactorRoundRobin
/*    */   extends TransactorSimple
/*    */ {
/*    */   public TransactorRoundRobin(IInventory inventory) {
/* 21 */     super(inventory);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int inject(ItemStack stack, ForgeDirection orientation, boolean doAdd) {
/* 27 */     int added = 0;
/*    */     
/* 29 */     for (int itemLoop = 0; itemLoop < stack.field_77994_a; ) {
/*    */       
/* 31 */       int smallestStackSize = Integer.MAX_VALUE;
/* 32 */       IInvSlot minSlot = null;
/*    */       
/* 34 */       for (IInvSlot slot : InventoryIterator.getIterable(this.inventory, orientation)) {
/* 35 */         ItemStack stackInInventory = slot.getStackInSlot();
/*    */         
/* 37 */         if (stackInInventory == null) {
/*    */           continue;
/*    */         }
/*    */         
/* 41 */         if (stackInInventory.field_77994_a >= stackInInventory.func_77976_d()) {
/*    */           continue;
/*    */         }
/*    */         
/* 45 */         if (stackInInventory.field_77994_a >= this.inventory.func_70297_j_()) {
/*    */           continue;
/*    */         }
/*    */         
/* 49 */         if (StackHelper.canStacksMerge(stack, stackInInventory) && stackInInventory.field_77994_a < smallestStackSize) {
/* 50 */           smallestStackSize = stackInInventory.field_77994_a;
/* 51 */           minSlot = slot;
/*    */         } 
/* 53 */         if (smallestStackSize <= 1) {
/*    */           break;
/*    */         }
/*    */       } 
/*    */       
/* 58 */       if (minSlot != null) {
/* 59 */         added += addToSlot(minSlot, stack, stack.field_77994_a - 1, doAdd);
/*    */ 
/*    */         
/*    */         itemLoop++;
/*    */       } 
/*    */     } 
/*    */     
/* 66 */     return added;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\TransactorRoundRobin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */