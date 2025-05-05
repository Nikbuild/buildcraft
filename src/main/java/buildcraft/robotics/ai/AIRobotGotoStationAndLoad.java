/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotGotoStationAndLoad
/*    */   extends AIRobot
/*    */ {
/*    */   private IStackFilter filter;
/*    */   private int quantity;
/*    */   
/*    */   public AIRobotGotoStationAndLoad(EntityRobotBase iRobot) {
/* 21 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotGotoStationAndLoad(EntityRobotBase iRobot, IStackFilter iFilter, int iQuantity) {
/* 25 */     this(iRobot);
/*    */     
/* 27 */     this.filter = iFilter;
/* 28 */     this.quantity = iQuantity;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 33 */     startDelegateAI(new AIRobotGotoStationToLoad(this.robot, this.filter, this.quantity));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 38 */     if (ai instanceof AIRobotGotoStationToLoad) {
/* 39 */       if (this.filter != null && ai.success()) {
/* 40 */         startDelegateAI(new AIRobotLoad(this.robot, this.filter, this.quantity));
/*    */       } else {
/* 42 */         setSuccess(false);
/* 43 */         terminate();
/*    */       } 
/* 45 */     } else if (ai instanceof AIRobotLoad) {
/* 46 */       setSuccess(ai.success());
/* 47 */       terminate();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotGotoStationAndLoad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */