/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.StatementParameterItemStackExact;
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
/*    */ 
/*    */ public class ActionStationRequestItems
/*    */   extends ActionStationInputItems
/*    */ {
/*    */   public ActionStationRequestItems() {
/* 21 */     super("buildcraft:station.request_items");
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 26 */     return StringUtils.localize("gate.action.station.request_items");
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 31 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:triggers/action_station_request_items");
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 36 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public int minParameters() {
/* 41 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 46 */     return (IStatementParameter)new StatementParameterItemStackExact(4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\ActionStationRequestItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */