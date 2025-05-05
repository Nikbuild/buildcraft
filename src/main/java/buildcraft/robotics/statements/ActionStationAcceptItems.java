/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.StatementManager;
/*    */ import buildcraft.api.statements.StatementParameterItemStack;
/*    */ import buildcraft.core.lib.utils.StringUtils;
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
/*    */ public class ActionStationAcceptItems
/*    */   extends ActionStationInputItems
/*    */ {
/*    */   public ActionStationAcceptItems() {
/* 21 */     super("buildcraft:station.accept_items");
/* 22 */     StatementManager.statements.put("buildcraft:station.drop_in_pipe", this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 27 */     return StringUtils.localize("gate.action.station.accept_items");
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 32 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:triggers/action_station_accept_items");
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 37 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 42 */     return (IStatementParameter)new StatementParameterItemStack();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\ActionStationAcceptItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */