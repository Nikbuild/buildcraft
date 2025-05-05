/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotGotoStationAndUnload
/*    */   extends AIRobot
/*    */ {
/*    */   private DockingStation station;
/*    */   
/*    */   public AIRobotGotoStationAndUnload(EntityRobotBase iRobot) {
/* 20 */     super(iRobot);
/*    */     
/* 22 */     this.station = null;
/*    */   }
/*    */   
/*    */   public AIRobotGotoStationAndUnload(EntityRobotBase iRobot, DockingStation iStation) {
/* 26 */     super(iRobot);
/*    */     
/* 28 */     this.station = iStation;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 33 */     if (this.station == null) {
/* 34 */       startDelegateAI(new AIRobotGotoStationToUnload(this.robot));
/*    */     } else {
/* 36 */       startDelegateAI(new AIRobotGotoStation(this.robot, this.station));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 42 */     if (ai instanceof AIRobotGotoStationToUnload) {
/* 43 */       if (ai.success()) {
/* 44 */         startDelegateAI(new AIRobotUnload(this.robot));
/*    */       } else {
/* 46 */         setSuccess(false);
/* 47 */         terminate();
/*    */       } 
/* 49 */     } else if (ai instanceof AIRobotGotoStation) {
/* 50 */       if (ai.success()) {
/* 51 */         startDelegateAI(new AIRobotUnload(this.robot));
/*    */       } else {
/* 53 */         setSuccess(false);
/* 54 */         terminate();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotGotoStationAndUnload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */