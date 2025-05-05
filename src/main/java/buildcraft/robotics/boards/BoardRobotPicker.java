/*    */ package buildcraft.robotics.boards;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRobot;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.robotics.ai.AIRobotFetchItem;
/*    */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*    */ import buildcraft.robotics.ai.AIRobotGotoStationAndUnload;
/*    */ import buildcraft.robotics.statements.ActionRobotFilter;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BoardRobotPicker
/*    */   extends RedstoneBoardRobot
/*    */ {
/* 24 */   public static Set<Integer> targettedItems = new HashSet<Integer>();
/*    */   
/*    */   public BoardRobotPicker(EntityRobotBase iRobot) {
/* 27 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public static void onServerStart() {
/* 31 */     targettedItems.clear();
/*    */   }
/*    */   
/*    */   private void fetchNewItem() {
/* 35 */     startDelegateAI((AIRobot)new AIRobotFetchItem(this.robot, 250.0F, ActionRobotFilter.getGateFilter(this.robot
/* 36 */             .getLinkedStation()), this.robot.getZoneToWork()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 41 */     fetchNewItem();
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 46 */     if (ai instanceof AIRobotFetchItem) {
/* 47 */       if (ai.success()) {
/*    */ 
/*    */         
/* 50 */         fetchNewItem();
/* 51 */       } else if (this.robot.containsItems()) {
/* 52 */         startDelegateAI((AIRobot)new AIRobotGotoStationAndUnload(this.robot));
/*    */       } else {
/* 54 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*    */       } 
/* 56 */     } else if (ai instanceof AIRobotGotoStationAndUnload && 
/* 57 */       !ai.success()) {
/* 58 */       startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public RedstoneBoardRobotNBT getNBTHandler() {
/* 65 */     return BCBoardNBT.REGISTRY.get("picker");
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotPicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */