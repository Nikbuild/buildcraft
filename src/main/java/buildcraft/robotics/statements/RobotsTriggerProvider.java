/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.BuildCraftRobotics;
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.ITriggerExternal;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.api.statements.ITriggerProvider;
/*    */ import buildcraft.robotics.RobotUtils;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ 
/*    */ public class RobotsTriggerProvider
/*    */   implements ITriggerProvider
/*    */ {
/*    */   public Collection<ITriggerInternal> getInternalTriggers(IStatementContainer container) {
/* 30 */     LinkedList<ITriggerInternal> result = new LinkedList<ITriggerInternal>();
/* 31 */     List<DockingStation> stations = RobotUtils.getStations(container.getTile());
/*    */     
/* 33 */     if (stations.size() > 0) {
/* 34 */       result.add(BuildCraftRobotics.triggerRobotSleep);
/* 35 */       result.add(BuildCraftRobotics.triggerRobotInStation);
/* 36 */       result.add(BuildCraftRobotics.triggerRobotLinked);
/* 37 */       result.add(BuildCraftRobotics.triggerRobotReserved);
/*    */     } 
/*    */     
/* 40 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<ITriggerExternal> getExternalTriggers(ForgeDirection side, TileEntity tile) {
/* 45 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\RobotsTriggerProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */