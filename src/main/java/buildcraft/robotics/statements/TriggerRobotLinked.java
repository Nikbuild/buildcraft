/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
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
/*    */ public class TriggerRobotLinked
/*    */   extends BCStatement
/*    */   implements ITriggerInternal
/*    */ {
/*    */   private final boolean reserved;
/*    */   
/*    */   public TriggerRobotLinked(boolean reserved) {
/* 27 */     super(new String[] { "buildcraft:robot." + (reserved ? "reserved" : "linked") });
/* 28 */     this.reserved = reserved;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 33 */     return StringUtils.localize("gate.trigger.robot." + (this.reserved ? "reserved" : "linked"));
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 38 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:triggers/trigger_robot_" + (this.reserved ? "reserved" : "linked"));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTriggerActive(IStatementContainer container, IStatementParameter[] parameters) {
/* 43 */     List<DockingStation> stations = RobotUtils.getStations(container.getTile());
/*    */     
/* 45 */     for (DockingStation station : stations) {
/* 46 */       if (station.isTaken() && (this.reserved || station.isMainStation())) {
/* 47 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 51 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\TriggerRobotLinked.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */