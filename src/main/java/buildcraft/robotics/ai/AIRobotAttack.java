/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.robotics.EntityRobot;
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
/*    */ 
/*    */ public class AIRobotAttack
/*    */   extends AIRobot
/*    */ {
/*    */   private Entity target;
/* 22 */   private int delay = 10;
/*    */   
/*    */   public AIRobotAttack(EntityRobotBase iRobot) {
/* 25 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotAttack(EntityRobotBase iRobot, Entity iTarget) {
/* 29 */     this(iRobot);
/*    */     
/* 31 */     this.target = iTarget;
/*    */   }
/*    */ 
/*    */   
/*    */   public void preempt(AIRobot ai) {
/* 36 */     if (ai instanceof AIRobotGotoBlock)
/*    */     {
/*    */       
/* 39 */       if (this.target != null && this.robot.func_70032_d(this.target) <= 2.0D) {
/* 40 */         abortDelegateAI();
/* 41 */         this.robot.setItemActive(true);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 48 */     if (this.target == null || this.target.field_70128_L) {
/* 49 */       terminate();
/*    */       
/*    */       return;
/*    */     } 
/* 53 */     if (this.robot.func_70032_d(this.target) > 2.0D) {
/* 54 */       startDelegateAI(new AIRobotGotoBlock(this.robot, (int)Math.floor(this.target.field_70165_t), 
/* 55 */             (int)Math.floor(this.target.field_70163_u), (int)Math.floor(this.target.field_70161_v)));
/* 56 */       this.robot.setItemActive(false);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 61 */     this.delay++;
/*    */     
/* 63 */     if (this.delay > 20) {
/* 64 */       this.delay = 0;
/* 65 */       ((EntityRobot)this.robot).attackTargetEntityWithCurrentItem(this.target);
/* 66 */       this.robot.aimItemAt((int)Math.floor(this.target.field_70165_t), (int)Math.floor(this.target.field_70163_u), 
/* 67 */           (int)Math.floor(this.target.field_70161_v));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void end() {
/* 73 */     this.robot.setItemActive(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 78 */     if (ai instanceof AIRobotGotoBlock) {
/* 79 */       if (!ai.success()) {
/* 80 */         this.robot.unreachableEntityDetected(this.target);
/*    */       }
/* 82 */       terminate();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyCost() {
/* 88 */     return 16;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotAttack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */