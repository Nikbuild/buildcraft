/*    */ package buildcraft.transport.statements;
/*    */ 
/*    */ import buildcraft.api.gates.IGate;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import buildcraft.core.statements.StatementParameterRedstoneGateSideOnly;
/*    */ import buildcraft.transport.TileGenericPipe;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TriggerRedstoneFaderInput
/*    */   extends BCStatement
/*    */   implements ITriggerInternal
/*    */ {
/*    */   public final int level;
/*    */   
/*    */   public TriggerRedstoneFaderInput(int level) {
/* 30 */     super(new String[] { String.format("buildcraft:redtone.input.%02d", new Object[] { Integer.valueOf(level) }) });
/*    */     
/* 32 */     this.level = level;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 37 */     return String.format(StringUtils.localize("gate.trigger.redstone.input.level"), new Object[] { Integer.valueOf(this.level) });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTriggerActive(IStatementContainer container, IStatementParameter[] parameters) {
/* 42 */     if (!(container instanceof IGate)) {
/* 43 */       return false;
/*    */     }
/*    */     
/* 46 */     IGate gate = (IGate)container;
/* 47 */     TileGenericPipe tile = (TileGenericPipe)gate.getPipe().getTile();
/* 48 */     int inputLevel = tile.redstoneInput;
/* 49 */     if (parameters.length > 0 && parameters[0] instanceof StatementParameterRedstoneGateSideOnly && ((StatementParameterRedstoneGateSideOnly)parameters[0]).isOn)
/*    */     {
/* 51 */       inputLevel = tile.redstoneInputSide[gate.getSide().ordinal()];
/*    */     }
/*    */     
/* 54 */     return (inputLevel == this.level);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 60 */     this.icon = iconRegister.func_94245_a(String.format("buildcrafttransport:triggers/redstone_%02d", new Object[] { Integer.valueOf(this.level) }));
/*    */   }
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/*    */     StatementParameterRedstoneGateSideOnly statementParameterRedstoneGateSideOnly;
/* 65 */     IStatementParameter param = null;
/*    */     
/* 67 */     if (index == 0) {
/* 68 */       statementParameterRedstoneGateSideOnly = new StatementParameterRedstoneGateSideOnly();
/*    */     }
/*    */     
/* 71 */     return (IStatementParameter)statementParameterRedstoneGateSideOnly;
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 76 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\TriggerRedstoneFaderInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */