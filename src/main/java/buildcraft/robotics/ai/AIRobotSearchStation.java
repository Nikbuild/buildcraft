/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.core.IZone;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.robotics.IStationFilter;
/*    */ import buildcraft.robotics.statements.ActionStationForbidRobot;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotSearchStation
/*    */   extends AIRobot
/*    */ {
/*    */   public DockingStation targetStation;
/*    */   private IStationFilter filter;
/*    */   private IZone zone;
/*    */   
/*    */   public AIRobotSearchStation(EntityRobotBase iRobot) {
/* 25 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotSearchStation(EntityRobotBase iRobot, IStationFilter iFilter, IZone iZone) {
/* 29 */     this(iRobot);
/*    */     
/* 31 */     this.filter = iFilter;
/* 32 */     this.zone = iZone;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 37 */     if (this.robot.getDockingStation() != null && this.filter
/* 38 */       .matches(this.robot.getDockingStation())) {
/* 39 */       this.targetStation = this.robot.getDockingStation();
/* 40 */       terminate();
/*    */       
/*    */       return;
/*    */     } 
/* 44 */     double potentialStationDistance = 3.4028234663852886E38D;
/* 45 */     DockingStation potentialStation = null;
/*    */     
/* 47 */     for (DockingStation station : this.robot.getRegistry().getStations()) {
/* 48 */       if (!station.isInitialized()) {
/*    */         continue;
/*    */       }
/*    */       
/* 52 */       if (station.isTaken() && station.robotIdTaking() != this.robot.getRobotId()) {
/*    */         continue;
/*    */       }
/*    */       
/* 56 */       if (this.zone != null && !this.zone.contains(station.x(), station.y(), station.z())) {
/*    */         continue;
/*    */       }
/*    */       
/* 60 */       if (!this.filter.matches(station) || 
/* 61 */         ActionStationForbidRobot.isForbidden(station, this.robot)) {
/*    */         continue;
/*    */       }
/*    */       
/* 65 */       double dx = this.robot.field_70165_t - station.x();
/* 66 */       double dy = this.robot.field_70163_u - station.y();
/* 67 */       double dz = this.robot.field_70161_v - station.z();
/* 68 */       double distance = dx * dx + dy * dy + dz * dz;
/*    */       
/* 70 */       if (potentialStation == null || distance < potentialStationDistance) {
/* 71 */         potentialStation = station;
/* 72 */         potentialStationDistance = distance;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 77 */     if (potentialStation != null) {
/* 78 */       this.targetStation = potentialStation;
/*    */     }
/*    */     
/* 81 */     terminate();
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 86 */     terminate();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean success() {
/* 91 */     return (this.targetStation != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotSearchStation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */