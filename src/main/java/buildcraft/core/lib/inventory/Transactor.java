/*    */ package buildcraft.core.lib.inventory;
/*    */ 
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
/*    */ 
/*    */ public abstract class Transactor
/*    */   implements ITransactor
/*    */ {
/*    */   public ItemStack add(ItemStack stack, ForgeDirection orientation, boolean doAdd) {
/* 21 */     ItemStack added = stack.func_77946_l();
/* 22 */     added.field_77994_a = inject(stack, orientation, doAdd);
/* 23 */     return added;
/*    */   }
/*    */   
/*    */   public abstract int inject(ItemStack paramItemStack, ForgeDirection paramForgeDirection, boolean paramBoolean);
/*    */   
/*    */   public static ITransactor getTransactorFor(Object object) {
/* 29 */     if (object instanceof net.minecraft.inventory.ISidedInventory)
/* 30 */       return new TransactorSimple((IInventory)object); 
/* 31 */     if (object instanceof IInventory) {
/* 32 */       return new TransactorSimple(InvUtils.getInventory((IInventory)object));
/*    */     }
/*    */     
/* 35 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\Transactor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */