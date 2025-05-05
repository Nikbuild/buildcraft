/*    */ package buildcraft.core.lib.inventory.filters;
/*    */ 
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import java.util.ArrayList;
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
/*    */ public class StatementParameterStackFilter
/*    */   extends ArrayStackOrListFilter
/*    */ {
/*    */   public StatementParameterStackFilter(IStatementParameter... parameters) {
/* 23 */     super(new ItemStack[0]);
/* 24 */     ArrayList<ItemStack> tmp = new ArrayList<ItemStack>();
/*    */     
/* 26 */     for (IStatementParameter s : parameters) {
/* 27 */       if (s != null && 
/* 28 */         s instanceof buildcraft.api.statements.StatementParameterItemStack) {
/* 29 */         tmp.add(s.getItemStack());
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 34 */     this.stacks = tmp.<ItemStack>toArray(new ItemStack[tmp.size()]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\filters\StatementParameterStackFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */