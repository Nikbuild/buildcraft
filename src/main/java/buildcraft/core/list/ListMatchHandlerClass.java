/*    */ package buildcraft.core.list;
/*    */ 
/*    */ import buildcraft.api.lists.ListMatchHandler;
/*    */ import buildcraft.api.lists.ListRegistry;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ListMatchHandlerClass
/*    */   extends ListMatchHandler
/*    */ {
/*    */   public boolean matches(ListMatchHandler.Type type, ItemStack stack, ItemStack target, boolean precise) {
/* 11 */     if (type == ListMatchHandler.Type.TYPE) {
/* 12 */       Class<?> kl = stack.func_77973_b().getClass();
/* 13 */       return (ListRegistry.itemClassAsType.contains(kl) && kl.equals(target.getClass()));
/*    */     } 
/* 15 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidSource(ListMatchHandler.Type type, ItemStack stack) {
/* 20 */     if (type == ListMatchHandler.Type.TYPE) {
/* 21 */       Class<?> kl = stack.func_77973_b().getClass();
/* 22 */       return ListRegistry.itemClassAsType.contains(kl);
/*    */     } 
/* 24 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\list\ListMatchHandlerClass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */