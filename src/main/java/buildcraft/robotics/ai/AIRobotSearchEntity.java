/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.core.IZone;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.utils.IEntityFilter;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotSearchEntity
/*    */   extends AIRobot
/*    */ {
/*    */   public Entity target;
/*    */   private float maxRange;
/*    */   private IZone zone;
/*    */   private IEntityFilter filter;
/*    */   
/*    */   public AIRobotSearchEntity(EntityRobotBase iRobot) {
/* 27 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotSearchEntity(EntityRobotBase iRobot, IEntityFilter iFilter, float iMaxRange, IZone iZone) {
/* 31 */     this(iRobot);
/*    */     
/* 33 */     this.maxRange = iMaxRange;
/* 34 */     this.zone = iZone;
/* 35 */     this.filter = iFilter;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 40 */     double previousDistance = Double.MAX_VALUE;
/*    */     
/* 42 */     for (Object o : this.robot.field_70170_p.field_72996_f) {
/* 43 */       Entity e = (Entity)o;
/*    */       
/* 45 */       if (!e.field_70128_L && this.filter
/* 46 */         .matches(e) && (this.zone == null || this.zone
/* 47 */         .contains(e.field_70165_t, e.field_70163_u, e.field_70161_v)) && 
/* 48 */         !this.robot.isKnownUnreachable(e)) {
/* 49 */         double dx = e.field_70165_t - this.robot.field_70165_t;
/* 50 */         double dy = e.field_70163_u - this.robot.field_70163_u;
/* 51 */         double dz = e.field_70161_v - this.robot.field_70161_v;
/*    */         
/* 53 */         double sqrDistance = dx * dx + dy * dy + dz * dz;
/* 54 */         double maxDistance = (this.maxRange * this.maxRange);
/*    */         
/* 56 */         if (sqrDistance >= maxDistance) {
/*    */           continue;
/*    */         }
/* 59 */         if (this.target == null) {
/* 60 */           previousDistance = sqrDistance;
/* 61 */           this.target = e; continue;
/*    */         } 
/* 63 */         if (sqrDistance < previousDistance) {
/* 64 */           previousDistance = sqrDistance;
/* 65 */           this.target = e;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 72 */     terminate();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean success() {
/* 77 */     return (this.target != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyCost() {
/* 82 */     return 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotSearchEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */