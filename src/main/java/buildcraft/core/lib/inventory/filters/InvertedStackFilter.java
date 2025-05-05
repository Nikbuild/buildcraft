/*    */ package buildcraft.core.lib.inventory.filters;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvertedStackFilter
/*    */   implements IStackFilter
/*    */ {
/*    */   private final IStackFilter filter;
/*    */   
/*    */   public InvertedStackFilter(IStackFilter filter) {
/* 18 */     this.filter = filter;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack stack) {
/* 23 */     if (stack == null) {
/* 24 */       return false;
/*    */     }
/* 26 */     return !this.filter.matches(stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\filters\InvertedStackFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */