/*    */ package buildcraft.core.lib.inventory.filters;
/*    */ 
/*    */ import buildcraft.core.lib.inventory.StackHelper;
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
/*    */ 
/*    */ 
/*    */ public class ArrayStackOrListFilter
/*    */   extends ArrayStackFilter
/*    */ {
/*    */   public ArrayStackOrListFilter(ItemStack... stacks) {
/* 22 */     super(stacks);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack stack) {
/* 27 */     if (this.stacks.length == 0 || !hasFilter()) {
/* 28 */       return true;
/*    */     }
/*    */     
/* 31 */     for (ItemStack s : this.stacks) {
/* 32 */       if (StackHelper.isMatchingItemOrList(s, stack)) {
/* 33 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 37 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\filters\ArrayStackOrListFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */