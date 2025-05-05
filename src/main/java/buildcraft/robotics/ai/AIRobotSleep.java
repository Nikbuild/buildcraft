/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.api.statements.StatementSlot;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotSleep
/*    */   extends AIRobot
/*    */ {
/*    */   private static final int SLEEPING_TIME = 1200;
/* 19 */   private int sleptTime = 0;
/*    */   
/*    */   public AIRobotSleep(EntityRobotBase iRobot) {
/* 22 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public void preempt(AIRobot ai) {
/* 27 */     for (StatementSlot s : this.robot.getLinkedStation().getActiveActions()) {
/* 28 */       if (s.statement instanceof buildcraft.robotics.statements.ActionRobotWakeUp) {
/* 29 */         terminate();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 36 */     this.sleptTime++;
/*    */     
/* 38 */     if (this.sleptTime > 1200) {
/* 39 */       terminate();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEnergyCost() {
/* 46 */     return (this.sleptTime % 10 == 0) ? 1 : 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotSleep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */