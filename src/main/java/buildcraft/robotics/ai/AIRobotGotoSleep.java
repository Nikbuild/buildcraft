/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotGotoSleep
/*    */   extends AIRobot
/*    */ {
/*    */   public AIRobotGotoSleep(EntityRobotBase iRobot) {
/* 17 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 22 */     this.robot.getRegistry().releaseResources(this.robot);
/* 23 */     startDelegateAI(new AIRobotGotoStation(this.robot, this.robot.getLinkedStation()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 28 */     if (ai instanceof AIRobotGotoStation) {
/* 29 */       startDelegateAI(new AIRobotSleep(this.robot));
/* 30 */     } else if (ai instanceof AIRobotSleep) {
/* 31 */       terminate();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotGotoSleep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */