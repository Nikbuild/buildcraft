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
/*    */ public class CraftingFilter
/*    */   implements IStackFilter
/*    */ {
/*    */   private final ItemStack[] stacks;
/*    */   
/*    */   public CraftingFilter(ItemStack... stacks) {
/* 24 */     this.stacks = stacks;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack stack) {
/* 29 */     if (this.stacks.length == 0 || !hasFilter()) {
/* 30 */       return true;
/*    */     }
/* 32 */     for (ItemStack s : this.stacks) {
/* 33 */       if (StackHelper.isCraftingEquivalent(s, stack, true)) {
/* 34 */         return true;
/*    */       }
/*    */     } 
/* 37 */     return false;
/*    */   }
/*    */   
/*    */   public ItemStack[] getStacks() {
/* 41 */     return this.stacks;
/*    */   }
/*    */   
/*    */   public boolean hasFilter() {
/* 45 */     for (ItemStack filter : this.stacks) {
/* 46 */       if (filter != null) {
/* 47 */         return true;
/*    */       }
/*    */     } 
/* 50 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\filters\CraftingFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */