/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.inventory.filters.IFluidFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotGotoStationAndLoadFluids
/*    */   extends AIRobot
/*    */ {
/*    */   private IFluidFilter filter;
/*    */   
/*    */   public AIRobotGotoStationAndLoadFluids(EntityRobotBase iRobot) {
/* 20 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotGotoStationAndLoadFluids(EntityRobotBase iRobot, IFluidFilter iFilter) {
/* 24 */     this(iRobot);
/*    */     
/* 26 */     this.filter = iFilter;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 31 */     startDelegateAI(new AIRobotGotoStationToLoadFluids(this.robot, this.filter));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 36 */     if (ai instanceof AIRobotGotoStationToLoadFluids)
/* 37 */       if (this.filter != null && ai.success()) {
/* 38 */         startDelegateAI(new AIRobotLoadFluids(this.robot, this.filter));
/*    */       } else {
/* 40 */         setSuccess(false);
/* 41 */         terminate();
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotGotoStationAndLoadFluids.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */