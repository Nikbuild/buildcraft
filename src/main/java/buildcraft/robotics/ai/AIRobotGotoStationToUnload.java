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
/*    */ public class AIRobotGotoStationToUnload
/*    */   extends AIRobot
/*    */ {
/*    */   public AIRobotGotoStationToUnload(EntityRobotBase iRobot) {
/* 19 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 24 */     startDelegateAI(new AIRobotSearchAndGotoStation(this.robot, new StationInventory(), this.robot.getZoneToLoadUnload()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 29 */     if (ai instanceof AIRobotSearchAndGotoStation) {
/* 30 */       setSuccess(ai.success());
/* 31 */       terminate();
/*    */     } 
/*    */   }
/*    */   
/*    */   private class StationInventory
/*    */     implements IStationFilter {
/*    */     public boolean matches(DockingStation station) {
/* 38 */       return AIRobotUnload.unload(AIRobotGotoStationToUnload.this.robot, station, false);
/*    */     }
/*    */     
/*    */     private StationInventory() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotGotoStationToUnload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */