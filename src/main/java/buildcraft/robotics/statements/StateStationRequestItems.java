/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.statements.ActionState;
/*    */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*    */ import java.util.LinkedList;
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
/*    */ public class StateStationRequestItems
/*    */   extends ActionState
/*    */ {
/*    */   LinkedList<ItemStack> items;
/*    */   
/*    */   public StateStationRequestItems(LinkedList<ItemStack> filter) {
/* 23 */     this.items = filter;
/*    */   }
/*    */   
/*    */   public boolean matches(IStackFilter filter) {
/* 27 */     if (this.items.size() == 0) {
/* 28 */       return true;
/*    */     }
/* 30 */     for (ItemStack stack : this.items) {
/* 31 */       if (filter.matches(stack)) {
/* 32 */         return true;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 37 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\StateStationRequestItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */