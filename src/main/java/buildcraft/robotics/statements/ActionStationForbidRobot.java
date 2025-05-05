/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.StatementSlot;
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
/*    */ public class ActionStationForbidRobot
/*    */   extends BCStatement
/*    */   implements IActionInternal
/*    */ {
/*    */   private final boolean invert;
/*    */   
/*    */   public ActionStationForbidRobot(boolean invert) {
/* 26 */     super(new String[] { "buildcraft:station." + (invert ? "force" : "forbid") + "_robot" });
/* 27 */     this.invert = invert;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 32 */     return StringUtils.localize("gate.action.station." + (this.invert ? "force" : "forbid") + "_robot");
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 37 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:triggers/action_station_robot_" + (this.invert ? "mandatory" : "forbidden"));
/*    */   }
/*    */ 
/*    */   
/*    */   public int minParameters() {
/* 42 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 47 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 52 */     return (IStatementParameter)new StatementParameterRobot();
/*    */   }
/*    */   
/*    */   public static boolean isForbidden(DockingStation station, EntityRobotBase robot) {
/* 56 */     for (StatementSlot s : station.getActiveActions()) {
/* 57 */       if (s.statement instanceof ActionStationForbidRobot && (
/* 58 */         ((ActionStationForbidRobot)s.statement).invert ^ isForbidden(s, robot)) != 0) {
/* 59 */         return true;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 64 */     return false;
/*    */   }
/*    */   
/*    */   public static boolean isForbidden(StatementSlot slot, EntityRobotBase robot) {
/* 68 */     for (IStatementParameter p : slot.parameters) {
/* 69 */       if (p != null && StatementParameterRobot.matches(p, robot)) {
/* 70 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 74 */     return false;
/*    */   }
/*    */   
/*    */   public void actionActivate(IStatementContainer source, IStatementParameter[] parameters) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\ActionStationForbidRobot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */