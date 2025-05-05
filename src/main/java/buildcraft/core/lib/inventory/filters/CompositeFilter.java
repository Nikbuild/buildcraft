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
/*    */ public class CompositeFilter
/*    */   implements IStackFilter
/*    */ {
/*    */   private final IStackFilter[] filters;
/*    */   
/*    */   public CompositeFilter(IStackFilter... iFilters) {
/* 21 */     this.filters = iFilters;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack stack) {
/* 26 */     for (IStackFilter f : this.filters) {
/* 27 */       if (f.matches(stack)) {
/* 28 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 32 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\filters\CompositeFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */