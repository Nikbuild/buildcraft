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
/*    */ 
/*    */ public class PassThroughStackFilter
/*    */   implements IStackFilter
/*    */ {
/*    */   public boolean matches(ItemStack stack) {
/* 20 */     return (stack != null && stack.field_77994_a > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\filters\PassThroughStackFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */