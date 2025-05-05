/*    */ package buildcraft.robotics.boards;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRobot;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*    */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*    */ import buildcraft.robotics.ai.AIRobotGotoStationAndLoad;
/*    */ import buildcraft.robotics.ai.AIRobotGotoStationAndUnload;
/*    */ import buildcraft.robotics.statements.ActionRobotFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BoardRobotCarrier
/*    */   extends RedstoneBoardRobot
/*    */ {
/*    */   public BoardRobotCarrier(EntityRobotBase iRobot) {
/* 25 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardRobotNBT getNBTHandler() {
/* 30 */     return BCBoardNBT.REGISTRY.get("carrier");
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 35 */     if (!this.robot.containsItems()) {
/* 36 */       IStackFilter filter = ActionRobotFilter.getGateFilter(this.robot.getLinkedStation());
/* 37 */       startDelegateAI((AIRobot)new AIRobotGotoStationAndLoad(this.robot, filter, -1));
/*    */     } else {
/* 39 */       startDelegateAI((AIRobot)new AIRobotGotoStationAndUnload(this.robot));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 45 */     if (ai instanceof AIRobotGotoStationAndLoad) {
/* 46 */       if (!ai.success()) {
/* 47 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*    */       }
/* 49 */     } else if (ai instanceof AIRobotGotoStationAndUnload && 
/* 50 */       !ai.success()) {
/* 51 */       startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotCarrier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */