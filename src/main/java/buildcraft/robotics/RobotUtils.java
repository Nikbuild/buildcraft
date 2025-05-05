/*    */ package buildcraft.robotics;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRegistry;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.robots.IDockingStationProvider;
/*    */ import buildcraft.api.transport.IPipeTile;
/*    */ import com.google.common.collect.Iterables;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class RobotUtils
/*    */ {
/*    */   public static List<DockingStation> getStations(Object tile) {
/* 27 */     ArrayList<DockingStation> stations = new ArrayList<DockingStation>();
/*    */     
/* 29 */     if (tile instanceof IDockingStationProvider) {
/* 30 */       DockingStation station = ((IDockingStationProvider)tile).getStation();
/* 31 */       if (station != null) {
/* 32 */         stations.add(station);
/*    */       }
/*    */     } 
/*    */     
/* 36 */     if (tile instanceof IPipeTile) {
/* 37 */       IPipeTile pipeTile = (IPipeTile)tile;
/* 38 */       for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
/* 39 */         if (pipeTile.getPipePluggable(d) instanceof IDockingStationProvider) {
/* 40 */           IDockingStationProvider pluggable = (IDockingStationProvider)pipeTile.getPipePluggable(d);
/* 41 */           DockingStation station = pluggable.getStation();
/*    */           
/* 43 */           if (station != null) {
/* 44 */             stations.add(station);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 50 */     return stations;
/*    */   }
/*    */ 
/*    */   
/*    */   public static RedstoneBoardRobotNBT getNextBoard(ItemStack stack, boolean reverse) {
/* 55 */     Collection<RedstoneBoardNBT<?>> boards = RedstoneBoardRegistry.instance.getAllBoardNBTs();
/* 56 */     if (stack == null || !(stack.func_77973_b() instanceof ItemRobot)) {
/* 57 */       if (!reverse) {
/* 58 */         return (RedstoneBoardRobotNBT)Iterables.getFirst(boards, null);
/*    */       }
/* 60 */       return (RedstoneBoardRobotNBT)Iterables.getLast(boards, null);
/*    */     } 
/*    */     
/* 63 */     if (reverse) {
/* 64 */       boards = Lists.reverse((List)boards);
/*    */     }
/* 66 */     boolean found = false;
/* 67 */     for (RedstoneBoardNBT<?> boardNBT : boards) {
/* 68 */       if (found)
/* 69 */         return (RedstoneBoardRobotNBT)boardNBT; 
/* 70 */       if (ItemRobot.getRobotNBT(stack) == boardNBT) {
/* 71 */         found = true;
/*    */       }
/*    */     } 
/* 74 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\RobotUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */