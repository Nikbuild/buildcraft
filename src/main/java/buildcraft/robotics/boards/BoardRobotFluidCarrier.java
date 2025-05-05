/*    */ package buildcraft.robotics.boards;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRobot;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.inventory.filters.IFluidFilter;
/*    */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*    */ import buildcraft.robotics.ai.AIRobotGotoStationAndLoadFluids;
/*    */ import buildcraft.robotics.ai.AIRobotGotoStationAndUnloadFluids;
/*    */ import buildcraft.robotics.statements.ActionRobotFilter;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BoardRobotFluidCarrier
/*    */   extends RedstoneBoardRobot
/*    */ {
/*    */   public BoardRobotFluidCarrier(EntityRobotBase iRobot) {
/* 27 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardRobotNBT getNBTHandler() {
/* 32 */     return BCBoardNBT.REGISTRY.get("fluidCarrier");
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 37 */     if (!robotHasFluid()) {
/* 38 */       IFluidFilter filter = ActionRobotFilter.getGateFluidFilter(this.robot.getLinkedStation());
/* 39 */       startDelegateAI((AIRobot)new AIRobotGotoStationAndLoadFluids(this.robot, filter));
/*    */     } else {
/* 41 */       startDelegateAI((AIRobot)new AIRobotGotoStationAndUnloadFluids(this.robot));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 47 */     if (ai instanceof AIRobotGotoStationAndLoadFluids) {
/* 48 */       if (!ai.success()) {
/* 49 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*    */       }
/* 51 */     } else if (ai instanceof AIRobotGotoStationAndUnloadFluids && 
/* 52 */       !ai.success()) {
/* 53 */       startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean robotHasFluid() {
/* 59 */     FluidStack tank = (this.robot.getTankInfo(ForgeDirection.UNKNOWN)[0]).fluid;
/* 60 */     return (tank != null && tank.amount > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotFluidCarrier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */