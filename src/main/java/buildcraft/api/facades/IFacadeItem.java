/*    */ package buildcraft.api.facades;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IFacadeItem
/*    */ {
/*    */   @Nullable
/*    */   default FacadeType getFacadeType(@Nonnull ItemStack stack) {
/* 17 */     IFacade facade = getFacade(stack);
/* 18 */     if (facade == null) {
/* 19 */       return null;
/*    */     }
/* 21 */     return facade.getType();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   ItemStack getFacadeForBlock(IBlockState paramIBlockState);
/*    */   
/*    */   ItemStack createFacadeStack(IFacade paramIFacade);
/*    */   
/*    */   @Nullable
/*    */   IFacade getFacade(@Nonnull ItemStack paramItemStack);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\facades\IFacadeItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */