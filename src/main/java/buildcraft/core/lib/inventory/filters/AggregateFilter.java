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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AggregateFilter
/*    */   implements IStackFilter
/*    */ {
/*    */   private final IStackFilter[] filters;
/*    */   
/*    */   public AggregateFilter(IStackFilter... iFilters) {
/* 21 */     this.filters = iFilters;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack stack) {
/* 26 */     for (IStackFilter f : this.filters) {
/* 27 */       if (!f.matches(stack)) {
/* 28 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\filters\AggregateFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */