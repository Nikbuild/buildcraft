/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import buildcraft.robotics.EntityRobot;
/*    */ import buildcraft.robotics.RobotUtils;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TriggerRobotInStation
/*    */   extends BCStatement
/*    */   implements ITriggerInternal
/*    */ {
/*    */   public TriggerRobotInStation() {
/* 27 */     super(new String[] { "buildcraft:robot.in.station" });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 32 */     return StringUtils.localize("gate.trigger.robot.in.station");
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 37 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:triggers/trigger_robot_in_station");
/*    */   }
/*    */ 
/*    */   
/*    */   public int minParameters() {
/* 42 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 49 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 54 */     return (IStatementParameter)new StatementParameterRobot();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTriggerActive(IStatementContainer container, IStatementParameter[] parameters) {
/* 59 */     List<DockingStation> stations = RobotUtils.getStations(container.getTile());
/*    */     
/* 61 */     for (DockingStation station : stations) {
/* 62 */       if (station.robotTaking() != null) {
/* 63 */         EntityRobot robot = (EntityRobot)station.robotTaking();
/*    */         
/* 65 */         if (robot.getDockingStation() == station) {
/* 66 */           if (parameters.length > 0 && parameters[0] != null && parameters[0].getItemStack() != null) {
/* 67 */             if (StatementParameterRobot.matches(parameters[0], (EntityRobotBase)robot))
/* 68 */               return true; 
/*    */             continue;
/*    */           } 
/* 71 */           return true;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 77 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\TriggerRobotInStation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */