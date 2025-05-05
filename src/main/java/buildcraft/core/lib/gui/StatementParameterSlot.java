/*    */ package buildcraft.core.lib.gui;
/*    */ 
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class StatementParameterSlot
/*    */   extends AdvancedSlot
/*    */ {
/*    */   public int slot;
/*    */   public StatementSlot statementSlot;
/*    */   
/*    */   public StatementParameterSlot(GuiAdvancedInterface gui, int x, int y, int slot, StatementSlot iStatementSlot) {
/* 17 */     super(gui, x, y);
/*    */     
/* 19 */     this.slot = slot;
/* 20 */     this.statementSlot = iStatementSlot;
/* 21 */     this.statementSlot.parameters.add(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDefined() {
/* 26 */     return (getParameter() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 31 */     IStatementParameter parameter = getParameter();
/*    */ 
/*    */     
/* 34 */     if (parameter != null && !(parameter instanceof buildcraft.api.statements.StatementParameterItemStack)) {
/* 35 */       return (parameter.getDescription() != null) ? parameter.getDescription() : "";
/*    */     }
/* 37 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 43 */     IStatementParameter parameter = getParameter();
/*    */     
/* 45 */     if (parameter != null) {
/* 46 */       return parameter.getItemStack();
/*    */     }
/* 48 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getIcon() {
/* 54 */     IStatementParameter parameter = getParameter();
/*    */     
/* 56 */     if (parameter != null) {
/* 57 */       return parameter.getIcon();
/*    */     }
/* 59 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract IStatementParameter getParameter();
/*    */   
/*    */   public boolean isAllowed() {
/* 66 */     return (this.statementSlot.getStatement() != null && this.slot < this.statementSlot.getStatement().maxParameters());
/*    */   }
/*    */   
/*    */   public boolean isRequired() {
/* 70 */     return (this.statementSlot.getStatement() != null && this.slot < this.statementSlot.getStatement().minParameters());
/*    */   }
/*    */   
/*    */   public abstract void setParameter(IStatementParameter paramIStatementParameter, boolean paramBoolean);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\StatementParameterSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */