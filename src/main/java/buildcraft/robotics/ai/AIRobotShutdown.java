/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotShutdown
/*    */   extends AIRobot
/*    */ {
/*    */   private int skip;
/*    */   private double motionX;
/*    */   private double motionZ;
/*    */   
/*    */   public AIRobotShutdown(EntityRobotBase iRobot) {
/* 20 */     super(iRobot);
/* 21 */     this.skip = 0;
/* 22 */     this.motionX = this.robot.field_70159_w;
/* 23 */     this.motionZ = this.robot.field_70179_y;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 28 */     this.robot.undock();
/* 29 */     this.robot.field_70159_w = this.motionX;
/* 30 */     this.robot.field_70181_x = -0.07500000298023224D;
/* 31 */     this.robot.field_70179_y = this.motionZ;
/*    */   }
/*    */   
/*    */   private boolean isBlocked(float yOffset) {
/* 35 */     return 
/* 36 */       (this.robot.field_70170_p.func_72945_a((Entity)this.robot, this.robot.func_70046_E().func_72321_a(this.robot.field_70159_w, yOffset, this.robot.field_70179_y)).size() > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 41 */     if (this.skip == 0) {
/* 42 */       if (!isBlocked(-0.075F)) {
/* 43 */         this.robot.field_70181_x = -0.07500000298023224D;
/*    */       } else {
/* 45 */         while (isBlocked(0.0F)) {
/* 46 */           this.robot.field_70163_u += 0.07500000298023224D;
/*    */         }
/* 48 */         this.robot.field_70181_x = 0.0D;
/* 49 */         if (this.robot.field_70159_w != 0.0D || this.robot.field_70179_y != 0.0D) {
/* 50 */           this.robot.field_70159_w = 0.0D;
/* 51 */           this.robot.field_70179_y = 0.0D;
/* 52 */           this.skip = 0;
/*    */         } else {
/* 54 */           this.skip = 20;
/*    */         } 
/*    */       } 
/*    */     } else {
/* 58 */       this.skip--;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyCost() {
/* 64 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotShutdown.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */