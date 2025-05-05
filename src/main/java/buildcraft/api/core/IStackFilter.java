/*    */ package buildcraft.api.core;
/*    */ 
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ public interface IStackFilter
/*    */ {
/*    */   default IStackFilter and(IStackFilter filter) {
/* 23 */     IStackFilter before = this;
/* 24 */     return stack -> (before.matches(stack) && filter.matches(stack));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default NonNullList<ItemStack> getExamples() {
/* 31 */     return NonNullList.func_191197_a(0, ItemStack.field_190927_a);
/*    */   }
/*    */   
/*    */   boolean matches(@Nonnull ItemStack paramItemStack);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\IStackFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */