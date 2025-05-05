/*    */ package buildcraft.core.list;
/*    */ 
/*    */ import buildcraft.api.lists.ListMatchHandler;
/*    */ import java.util.Set;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class ListMatchHandlerTools
/*    */   extends ListMatchHandler
/*    */ {
/*    */   public boolean matches(ListMatchHandler.Type type, ItemStack stack, ItemStack target, boolean precise) {
/* 12 */     if (type == ListMatchHandler.Type.TYPE) {
/* 13 */       Set<String> toolClassesSource = stack.func_77973_b().getToolClasses(stack);
/* 14 */       Set<String> toolClassesTarget = target.func_77973_b().getToolClasses(stack);
/* 15 */       if (toolClassesSource.size() > 0 && toolClassesTarget.size() > 0) {
/* 16 */         if (precise && 
/* 17 */           toolClassesSource.size() != toolClassesTarget.size()) {
/* 18 */           return false;
/*    */         }
/*    */         
/* 21 */         for (String s : toolClassesSource) {
/* 22 */           if (!toolClassesTarget.contains(s)) {
/* 23 */             return false;
/*    */           }
/*    */         } 
/* 26 */         return true;
/*    */       } 
/*    */     } 
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidSource(ListMatchHandler.Type type, ItemStack stack) {
/* 34 */     return (stack.func_77973_b().getToolClasses(stack).size() > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\list\ListMatchHandlerTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */