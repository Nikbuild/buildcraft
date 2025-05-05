/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.inventory.filters.IFluidFilter;
/*    */ import buildcraft.robotics.IStationFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotGotoStationToLoadFluids
/*    */   extends AIRobot
/*    */ {
/*    */   private IFluidFilter filter;
/*    */   
/*    */   public AIRobotGotoStationToLoadFluids(EntityRobotBase iRobot) {
/* 22 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotGotoStationToLoadFluids(EntityRobotBase iRobot, IFluidFilter iFiler) {
/* 26 */     this(iRobot);
/*    */     
/* 28 */     this.filter = iFiler;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 33 */     startDelegateAI(new AIRobotSearchAndGotoStation(this.robot, new StationFilter(), this.robot
/* 34 */           .getZoneToLoadUnload()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 39 */     if (ai instanceof AIRobotSearchAndGotoStation) {
/* 40 */       setSuccess(ai.success());
/* 41 */       terminate();
/*    */     } 
/*    */   }
/*    */   
/*    */   private class StationFilter implements IStationFilter {
/*    */     private StationFilter() {}
/*    */     
/*    */     public boolean matches(DockingStation station) {
/* 49 */       return (AIRobotLoadFluids.load(AIRobotGotoStationToLoadFluids.this.robot, station, AIRobotGotoStationToLoadFluids.this.filter, false) > 0);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotGotoStationToLoadFluids.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */