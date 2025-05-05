/*    */ package buildcraft.core.blueprints;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class RequirementItemStack {
/*    */   public final ItemStack stack;
/*    */   public final int size;
/*    */   
/*    */   public RequirementItemStack(ItemStack stack, int size) {
/* 10 */     this.stack = stack;
/* 11 */     this.size = size;
/* 12 */     stack.field_77994_a = 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 17 */     return this.stack.hashCode() * 13 + this.size;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\RequirementItemStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */