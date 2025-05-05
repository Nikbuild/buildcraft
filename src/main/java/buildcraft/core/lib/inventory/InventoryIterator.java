/*    */ package buildcraft.core.lib.inventory;
/*    */ 
/*    */ import buildcraft.api.core.IInvSlot;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.ISidedInventory;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class InventoryIterator
/*    */ {
/*    */   public static Iterable<IInvSlot> getIterable(IInventory inv) {
/* 27 */     return getIterable(inv, ForgeDirection.UNKNOWN);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Iterable<IInvSlot> getIterable(IInventory inv, ForgeDirection side) {
/* 38 */     if (inv instanceof ISidedInventory) {
/* 39 */       return new InventoryIteratorSided((ISidedInventory)inv, side);
/*    */     }
/*    */     
/* 42 */     return new InventoryIteratorSimple(inv);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\InventoryIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */