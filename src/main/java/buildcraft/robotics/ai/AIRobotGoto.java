/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AIRobotGoto
/*    */   extends AIRobot
/*    */ {
/*    */   protected float nextX;
/*    */   protected float nextY;
/*    */   protected float nextZ;
/*    */   protected double dirX;
/*    */   protected double dirY;
/*    */   protected double dirZ;
/*    */   
/*    */   public AIRobotGoto(EntityRobotBase iRobot) {
/* 20 */     super(iRobot);
/*    */   }
/*    */   
/*    */   protected void setDestination(EntityRobotBase robot, float x, float y, float z) {
/* 24 */     this.nextX = x;
/* 25 */     this.nextY = y;
/* 26 */     this.nextZ = z;
/*    */     
/* 28 */     this.dirX = this.nextX - robot.field_70165_t;
/* 29 */     this.dirY = this.nextY - robot.field_70163_u;
/* 30 */     this.dirZ = this.nextZ - robot.field_70161_v;
/*    */     
/* 32 */     double magnitude = Math.sqrt(this.dirX * this.dirX + this.dirY * this.dirY + this.dirZ * this.dirZ);
/*    */     
/* 34 */     if (magnitude != 0.0D) {
/* 35 */       this.dirX /= magnitude;
/* 36 */       this.dirY /= magnitude;
/* 37 */       this.dirZ /= magnitude;
/*    */     } else {
/* 39 */       this.dirX = 0.0D;
/* 40 */       this.dirY = 0.0D;
/* 41 */       this.dirZ = 0.0D;
/*    */     } 
/*    */     
/* 44 */     robot.field_70159_w = this.dirX / 10.0D;
/* 45 */     robot.field_70181_x = this.dirY / 10.0D;
/* 46 */     robot.field_70179_y = this.dirZ / 10.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyCost() {
/* 51 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotGoto.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */