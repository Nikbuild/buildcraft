/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardRobot;
/*    */ import buildcraft.api.core.BCLog;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotMain
/*    */   extends AIRobot
/*    */ {
/*    */   private AIRobot overridingAI;
/*    */   private int rechargeCooldown;
/*    */   
/*    */   public AIRobotMain(EntityRobotBase iRobot) {
/* 21 */     super(iRobot);
/* 22 */     this.rechargeCooldown = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyCost() {
/* 27 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void preempt(AIRobot ai) {
/* 32 */     if (this.robot.getEnergy() <= 0 && (this.robot
/* 33 */       .getDockingStation() == null || !this.robot.getDockingStation().providesPower())) {
/* 34 */       if (!(ai instanceof AIRobotShutdown)) {
/* 35 */         BCLog.logger.info("Shutting down robot " + this.robot.toString() + " - no power");
/* 36 */         startDelegateAI(new AIRobotShutdown(this.robot));
/*    */       } 
/* 38 */     } else if (this.robot.getEnergy() < 20000) {
/* 39 */       if (!(ai instanceof AIRobotRecharge) && !(ai instanceof AIRobotShutdown) && 
/* 40 */         this.rechargeCooldown-- <= 0) {
/* 41 */         startDelegateAI(new AIRobotRecharge(this.robot));
/*    */       }
/*    */     }
/* 44 */     else if (!(ai instanceof AIRobotRecharge) && 
/* 45 */       this.overridingAI != null && ai != this.overridingAI) {
/* 46 */       startDelegateAI(this.overridingAI);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 53 */     RedstoneBoardRobot redstoneBoardRobot = this.robot.getBoard();
/*    */     
/* 55 */     if (redstoneBoardRobot != null) {
/* 56 */       startDelegateAI((AIRobot)redstoneBoardRobot);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 62 */     if (ai instanceof AIRobotRecharge && 
/* 63 */       !ai.success()) {
/* 64 */       this.rechargeCooldown = 120;
/*    */     }
/*    */     
/* 67 */     if (ai == this.overridingAI) {
/* 68 */       this.overridingAI = null;
/*    */     }
/*    */   }
/*    */   
/*    */   public void setOverridingAI(AIRobot ai) {
/* 73 */     if (this.overridingAI == null) {
/* 74 */       this.overridingAI = ai;
/*    */     }
/*    */   }
/*    */   
/*    */   public AIRobot getOverridingAI() {
/* 79 */     return this.overridingAI;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canLoadFromNBT() {
/* 84 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotMain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */