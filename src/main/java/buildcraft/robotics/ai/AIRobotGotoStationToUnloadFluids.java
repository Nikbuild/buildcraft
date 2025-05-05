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
/*    */ public class AIRobotGotoStationToUnloadFluids
/*    */   extends AIRobot
/*    */ {
/*    */   public AIRobotGotoStationToUnloadFluids(EntityRobotBase iRobot) {
/* 19 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 24 */     startDelegateAI(new AIRobotSearchAndGotoStation(this.robot, new StationFilter(), this.robot
/* 25 */           .getZoneToLoadUnload()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 30 */     if (ai instanceof AIRobotSearchAndGotoStation) {
/* 31 */       setSuccess(ai.success());
/* 32 */       terminate();
/*    */     } 
/*    */   }
/*    */   
/*    */   private class StationFilter implements IStationFilter {
/*    */     private StationFilter() {}
/*    */     
/*    */     public boolean matches(DockingStation station) {
/* 40 */       return (AIRobotUnloadFluids.unload(AIRobotGotoStationToUnloadFluids.this.robot, station, false) > 0);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotGotoStationToUnloadFluids.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */