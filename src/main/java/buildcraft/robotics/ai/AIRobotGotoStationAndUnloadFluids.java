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
/*    */ public class AIRobotGotoStationAndUnloadFluids
/*    */   extends AIRobot
/*    */ {
/*    */   public AIRobotGotoStationAndUnloadFluids(EntityRobotBase iRobot) {
/* 17 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 22 */     startDelegateAI(new AIRobotGotoStationToUnloadFluids(this.robot));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 27 */     if (ai instanceof AIRobotGotoStationToUnloadFluids)
/* 28 */       if (ai.success()) {
/* 29 */         startDelegateAI(new AIRobotUnloadFluids(this.robot));
/*    */       } else {
/* 31 */         setSuccess(false);
/* 32 */         terminate();
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotGotoStationAndUnloadFluids.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */