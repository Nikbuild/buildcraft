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
/*    */ public class ArrayStackFilter
/*    */   implements IStackFilter
/*    */ {
/*    */   protected ItemStack[] stacks;
/*    */   
/*    */   public ArrayStackFilter(ItemStack... stacks) {
/* 23 */     this.stacks = stacks;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack stack) {
/* 28 */     if (this.stacks.length == 0 || !hasFilter()) {
/* 29 */       return true;
/*    */     }
/* 31 */     for (ItemStack s : this.stacks) {
/* 32 */       if (StackHelper.isMatchingItem(s, stack)) {
/* 33 */         return true;
/*    */       }
/*    */     } 
/* 36 */     return false;
/*    */   }
/*    */   
/*    */   public boolean matches(IStackFilter filter2) {
/* 40 */     for (ItemStack s : this.stacks) {
/* 41 */       if (filter2.matches(s)) {
/* 42 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 46 */     return false;
/*    */   }
/*    */   
/*    */   public ItemStack[] getStacks() {
/* 50 */     return this.stacks;
/*    */   }
/*    */   
/*    */   public boolean hasFilter() {
/* 54 */     for (ItemStack filter : this.stacks) {
/* 55 */       if (filter != null) {
/* 56 */         return true;
/*    */       }
/*    */     } 
/* 59 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\filters\ArrayStackFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */