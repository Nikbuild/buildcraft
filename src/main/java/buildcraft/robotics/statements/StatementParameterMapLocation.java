/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IStatement;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.StatementMouseClick;
/*    */ import buildcraft.api.statements.StatementParameterItemStack;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class StatementParameterMapLocation
/*    */   extends StatementParameterItemStack
/*    */ {
/*    */   public String getUniqueTag() {
/* 14 */     return "buildcraft:maplocation";
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(IStatementContainer source, IStatement stmt, ItemStack stackIn, StatementMouseClick mouse) {
/* 19 */     ItemStack stack = stackIn;
/* 20 */     if (stack != null && !(stack.func_77973_b() instanceof buildcraft.api.items.IMapLocation)) {
/* 21 */       stack = null;
/*    */     }
/* 23 */     super.onClick(source, stmt, stack, mouse);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\StatementParameterMapLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */