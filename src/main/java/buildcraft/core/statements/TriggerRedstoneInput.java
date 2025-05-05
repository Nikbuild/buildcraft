/*    */ package buildcraft.core.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.api.statements.containers.IRedstoneStatementContainer;
/*    */ import buildcraft.api.statements.containers.ISidedStatementContainer;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TriggerRedstoneInput
/*    */   extends BCStatement
/*    */   implements ITriggerInternal
/*    */ {
/*    */   boolean active;
/*    */   
/*    */   public TriggerRedstoneInput(boolean active) {
/* 27 */     super(new String[] { "buildcraft:redstone.input." + (active ? "active" : "inactive"), active ? "buildcraft.redtone.input.active" : "buildcraft.redtone.input.inactive" });
/* 28 */     this.active = active;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 33 */     return StringUtils.localize("gate.trigger.redstone.input." + (this.active ? "active" : "inactive"));
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 38 */     IStatementParameter param = null;
/*    */     
/* 40 */     if (index == 0) {
/* 41 */       param = new StatementParameterRedstoneGateSideOnly();
/*    */     }
/*    */     
/* 44 */     return param;
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 49 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTriggerActive(IStatementContainer container, IStatementParameter[] parameters) {
/* 54 */     if (container instanceof IRedstoneStatementContainer) {
/* 55 */       int level = ((IRedstoneStatementContainer)container).getRedstoneInput(ForgeDirection.UNKNOWN);
/* 56 */       if (parameters.length > 0 && parameters[0] instanceof StatementParameterRedstoneGateSideOnly && ((StatementParameterRedstoneGateSideOnly)parameters[0]).isOn && container instanceof ISidedStatementContainer)
/*    */       {
/*    */         
/* 59 */         level = ((IRedstoneStatementContainer)container).getRedstoneInput(((ISidedStatementContainer)container).getSide());
/*    */       }
/*    */       
/* 62 */       return this.active ? ((level > 0)) : ((level == 0));
/*    */     } 
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister register) {
/* 70 */     this.icon = register.func_94245_a("buildcraftcore:triggers/trigger_redstoneinput_" + (this.active ? "active" : "inactive"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\TriggerRedstoneInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */