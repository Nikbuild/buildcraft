/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.DockingStation;
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
/*    */ public class AIRobotRecharge
/*    */   extends AIRobot
/*    */ {
/*    */   public AIRobotRecharge(EntityRobotBase iRobot) {
/* 19 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 24 */     this.robot.getRegistry().releaseResources(this.robot);
/* 25 */     this.robot.field_70159_w = 0.0D;
/* 26 */     this.robot.field_70181_x = 0.0D;
/* 27 */     this.robot.field_70179_y = 0.0D;
/*    */     
/* 29 */     startDelegateAI(new AIRobotSearchAndGotoStation(this.robot, new IStationFilter()
/*    */           {
/*    */             public boolean matches(DockingStation station) {
/* 32 */               return station.providesPower();
/*    */             }
/*    */           },  null));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyCost() {
/* 39 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 44 */     if (this.robot.getEnergy() >= 99500) {
/* 45 */       terminate();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 51 */     if (ai instanceof AIRobotSearchAndGotoStation && 
/* 52 */       !ai.success()) {
/* 53 */       setSuccess(false);
/* 54 */       terminate();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotRecharge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */