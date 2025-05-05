/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.core.IZone;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.robotics.IStationFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotSearchAndGotoStation
/*    */   extends AIRobot
/*    */ {
/*    */   private IStationFilter filter;
/*    */   private IZone zone;
/*    */   
/*    */   public AIRobotSearchAndGotoStation(EntityRobotBase iRobot) {
/* 22 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotSearchAndGotoStation(EntityRobotBase iRobot, IStationFilter iFilter, IZone iZone) {
/* 26 */     this(iRobot);
/*    */     
/* 28 */     this.filter = iFilter;
/* 29 */     this.zone = iZone;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 34 */     startDelegateAI(new AIRobotSearchStation(this.robot, this.filter, this.zone));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 39 */     if (ai instanceof AIRobotSearchStation) {
/* 40 */       if (ai.success()) {
/* 41 */         startDelegateAI(new AIRobotGotoStation(this.robot, ((AIRobotSearchStation)ai).targetStation));
/*    */       } else {
/* 43 */         setSuccess(false);
/* 44 */         terminate();
/*    */       } 
/* 46 */     } else if (ai instanceof AIRobotGotoStation) {
/* 47 */       setSuccess(ai.success());
/* 48 */       terminate();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotSearchAndGotoStation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */