/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.BuildCraftRobotics;
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.statements.IActionExternal;
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IActionProvider;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.transport.IPipeTile;
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
/*    */ 
/*    */ 
/*    */ public class RobotsActionProvider
/*    */   implements IActionProvider
/*    */ {
/*    */   public Collection<IActionInternal> getInternalActions(IStatementContainer container) {
/* 33 */     LinkedList<IActionInternal> result = new LinkedList<IActionInternal>();
/* 34 */     TileEntity tile = container.getTile();
/*    */     
/* 36 */     if (!(tile instanceof IPipeTile)) {
/* 37 */       return result;
/*    */     }
/*    */     
/* 40 */     IPipeTile pipeTile = (IPipeTile)tile;
/*    */     
/* 42 */     List<DockingStation> stations = RobotUtils.getStations(pipeTile);
/*    */     
/* 44 */     if (stations.size() == 0) {
/* 45 */       return result;
/*    */     }
/*    */     
/* 48 */     result.add(BuildCraftRobotics.actionRobotGotoStation);
/* 49 */     result.add(BuildCraftRobotics.actionRobotWorkInArea);
/* 50 */     result.add(BuildCraftRobotics.actionRobotLoadUnloadArea);
/* 51 */     result.add(BuildCraftRobotics.actionRobotWakeUp);
/* 52 */     result.add(BuildCraftRobotics.actionRobotFilter);
/* 53 */     result.add(BuildCraftRobotics.actionRobotFilterTool);
/* 54 */     result.add(BuildCraftRobotics.actionStationForbidRobot);
/* 55 */     result.add(BuildCraftRobotics.actionStationForceRobot);
/*    */     
/* 57 */     if (pipeTile.getPipeType() == IPipeTile.PipeType.ITEM) {
/* 58 */       result.add(BuildCraftRobotics.actionStationRequestItems);
/* 59 */       result.add(BuildCraftRobotics.actionStationAcceptItems);
/*    */     } 
/*    */     
/* 62 */     if (pipeTile.getPipeType() == IPipeTile.PipeType.FLUID) {
/* 63 */       result.add(BuildCraftRobotics.actionStationAcceptFluids);
/*    */     }
/*    */     
/* 66 */     for (DockingStation station : stations) {
/* 67 */       if (station.getItemInput() != null) {
/* 68 */         result.add(BuildCraftRobotics.actionStationProvideItems);
/*    */       }
/*    */       
/* 71 */       if (station.getFluidInput() != null) {
/* 72 */         result.add(BuildCraftRobotics.actionStationProvideFluids);
/*    */       }
/*    */       
/* 75 */       if (station.getRequestProvider() != null) {
/* 76 */         result.add(BuildCraftRobotics.actionStationMachineRequestItems);
/*    */       }
/*    */     } 
/*    */     
/* 80 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<IActionExternal> getExternalActions(ForgeDirection side, TileEntity tile) {
/* 85 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\RobotsActionProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */