/*    */ package buildcraft.robotics.boards;
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRobot;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*    */ 
/*    */ public class BoardRobotEmpty extends RedstoneBoardRobot {
/*    */   public BoardRobotEmpty(EntityRobotBase iRobot) {
/* 11 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardRobotNBT getNBTHandler() {
/* 16 */     return RedstoneBoardRobotEmptyNBT.instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 21 */     startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotEmpty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */