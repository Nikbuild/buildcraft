/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*    */ import buildcraft.robotics.IStationFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotGotoStationToLoad
/*    */   extends AIRobot
/*    */ {
/*    */   private IStackFilter filter;
/*    */   private int quantity;
/*    */   
/*    */   public AIRobotGotoStationToLoad(EntityRobotBase iRobot) {
/* 23 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotGotoStationToLoad(EntityRobotBase iRobot, IStackFilter iFilter, int iQuantity) {
/* 27 */     this(iRobot);
/*    */     
/* 29 */     this.filter = iFilter;
/* 30 */     this.quantity = iQuantity;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 35 */     startDelegateAI(new AIRobotSearchAndGotoStation(this.robot, new StationFilter(), this.robot.getZoneToLoadUnload()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 40 */     if (ai instanceof AIRobotSearchAndGotoStation) {
/* 41 */       setSuccess(ai.success());
/* 42 */       terminate();
/*    */     } 
/*    */   }
/*    */   
/*    */   private class StationFilter implements IStationFilter {
/*    */     private StationFilter() {}
/*    */     
/*    */     public boolean matches(DockingStation station) {
/* 50 */       return AIRobotLoad.load(AIRobotGotoStationToLoad.this.robot, station, AIRobotGotoStationToLoad.this.filter, AIRobotGotoStationToLoad.this.quantity, false);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotGotoStationToLoad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */