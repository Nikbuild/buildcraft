/*    */ package buildcraft.core.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.containers.IRedstoneStatementContainer;
/*    */ import buildcraft.api.statements.containers.ISidedStatementContainer;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
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
/*    */ 
/*    */ public class ActionRedstoneOutput
/*    */   extends BCStatement
/*    */   implements IActionInternal
/*    */ {
/*    */   public ActionRedstoneOutput(String s) {
/* 28 */     super(new String[] { s });
/*    */   }
/*    */   
/*    */   public ActionRedstoneOutput() {
/* 32 */     super(new String[] { "buildcraft:redstone.output", "buildcraft.redstone.output" });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 37 */     return StringUtils.localize("gate.action.redstone.signal");
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 42 */     IStatementParameter param = null;
/*    */     
/* 44 */     if (index == 0) {
/* 45 */       param = new StatementParameterRedstoneGateSideOnly();
/*    */     }
/*    */     
/* 48 */     return param;
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 53 */     return 1;
/*    */   }
/*    */   
/*    */   protected boolean isSideOnly(IStatementParameter[] parameters) {
/* 57 */     if (parameters != null && parameters.length >= 1 && parameters[0] instanceof StatementParameterRedstoneGateSideOnly) {
/* 58 */       return ((StatementParameterRedstoneGateSideOnly)parameters[0]).isOn;
/*    */     }
/*    */     
/* 61 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void actionActivate(IStatementContainer source, IStatementParameter[] parameters) {
/* 67 */     if (source instanceof IRedstoneStatementContainer) {
/* 68 */       ForgeDirection side = ForgeDirection.UNKNOWN;
/* 69 */       if (source instanceof ISidedStatementContainer && isSideOnly(parameters)) {
/* 70 */         side = ((ISidedStatementContainer)source).getSide();
/*    */       }
/* 72 */       ((IRedstoneStatementContainer)source).setRedstoneOutput(side, getSignalLevel());
/*    */     } 
/*    */   }
/*    */   
/*    */   protected int getSignalLevel() {
/* 77 */     return 15;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IIconRegister register) {
/* 83 */     this.icon = register.func_94245_a("buildcraftcore:triggers/action_redstoneoutput");
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\ActionRedstoneOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */