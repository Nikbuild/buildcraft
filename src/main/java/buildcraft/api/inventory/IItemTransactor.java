/*    */ package buildcraft.api.inventory;
/*    */ 
/*    */ import buildcraft.api.core.IStackFilter;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.NonNullList;
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
/*    */ public interface IItemTransactor
/*    */ {
/*    */   @Nonnull
/*    */   ItemStack insert(@Nonnull ItemStack paramItemStack, boolean paramBoolean1, boolean paramBoolean2);
/*    */   
/*    */   default NonNullList<ItemStack> insert(NonNullList<ItemStack> stacks, boolean simulate) {
/* 27 */     NonNullList<ItemStack> leftOver = NonNullList.func_191196_a();
/* 28 */     for (ItemStack stack : stacks) {
/* 29 */       ItemStack leftOverStack = insert(stack, false, simulate);
/* 30 */       if (!leftOverStack.func_190926_b()) {
/* 31 */         leftOver.add(leftOverStack);
/*    */       }
/*    */     } 
/* 34 */     return leftOver;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   ItemStack extract(@Nullable IStackFilter paramIStackFilter, int paramInt1, int paramInt2, boolean paramBoolean);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean canFullyAccept(@Nonnull ItemStack stack) {
/* 48 */     return insert(stack, true, true).func_190926_b();
/*    */   }
/*    */   
/*    */   default boolean canPartiallyAccept(@Nonnull ItemStack stack) {
/* 52 */     return (insert(stack, false, true).func_190916_E() < stack.func_190916_E());
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface IItemInsertable
/*    */     extends IItemTransactor {
/*    */     @Nonnull
/*    */     default ItemStack extract(IStackFilter filter, int min, int max, boolean simulate) {
/* 60 */       return ItemStack.field_190927_a;
/*    */     }
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface IItemExtractable
/*    */     extends IItemTransactor {
/*    */     @Nonnull
/*    */     default ItemStack insert(@Nonnull ItemStack stack, boolean allOrNone, boolean simulate) {
/* 69 */       return stack;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\inventory\IItemTransactor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */