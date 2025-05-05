/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.StatementParameterItemStack;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionStationAcceptFluids
/*    */   extends BCStatement
/*    */   implements IActionInternal
/*    */ {
/*    */   public ActionStationAcceptFluids() {
/* 23 */     super(new String[] { "buildcraft:station.accept_fluids" });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 28 */     return StringUtils.localize("gate.action.station.accept_fluids");
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 33 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:triggers/action_station_accept_fluids");
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 38 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 43 */     return (IStatementParameter)new StatementParameterItemStack();
/*    */   }
/*    */   
/*    */   public void actionActivate(IStatementContainer source, IStatementParameter[] parameters) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\ActionStationAcceptFluids.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */