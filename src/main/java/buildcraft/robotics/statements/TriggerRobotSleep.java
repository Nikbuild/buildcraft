/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.robots.DockingStation;
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
/*    */ 
/*    */ public class TriggerRobotSleep
/*    */   extends BCStatement
/*    */   implements ITriggerInternal
/*    */ {
/*    */   public TriggerRobotSleep() {
/* 27 */     super(new String[] { "buildcraft:robot.sleep" });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 32 */     return StringUtils.localize("gate.trigger.robot.sleep");
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 37 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:triggers/trigger_robot_sleep");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTriggerActive(IStatementContainer container, IStatementParameter[] parameters) {
/* 42 */     List<DockingStation> stations = RobotUtils.getStations(container.getTile());
/*    */     
/* 44 */     for (DockingStation station : stations) {
/* 45 */       if (station.robotTaking() != null) {
/* 46 */         EntityRobot robot = (EntityRobot)station.robotTaking();
/*    */         
/* 48 */         if (robot.isActive()) {
/* 49 */           return true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 54 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\TriggerRobotSleep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */