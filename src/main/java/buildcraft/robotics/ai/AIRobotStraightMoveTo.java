/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotStraightMoveTo
/*    */   extends AIRobotGoto
/*    */ {
/* 17 */   private double prevDistance = Double.MAX_VALUE;
/*    */   
/*    */   private float x;
/*    */   
/*    */   public AIRobotStraightMoveTo(EntityRobotBase iRobot) {
/* 22 */     super(iRobot);
/*    */   }
/*    */   private float y; private float z;
/*    */   public AIRobotStraightMoveTo(EntityRobotBase iRobot, float ix, float iy, float iz) {
/* 26 */     this(iRobot);
/* 27 */     this.x = ix;
/* 28 */     this.y = iy;
/* 29 */     this.z = iz;
/* 30 */     this.robot.aimItemAt((int)Math.floor(this.x), (int)Math.floor(this.y), (int)Math.floor(this.z));
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 35 */     this.robot.undock();
/* 36 */     setDestination(this.robot, this.x, this.y, this.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 41 */     double distance = this.robot.func_70011_f(this.nextX, this.nextY, this.nextZ);
/*    */     
/* 43 */     if (distance < this.prevDistance) {
/* 44 */       this.prevDistance = distance;
/*    */     } else {
/* 46 */       this.robot.field_70159_w = 0.0D;
/* 47 */       this.robot.field_70181_x = 0.0D;
/* 48 */       this.robot.field_70179_y = 0.0D;
/*    */       
/* 50 */       this.robot.field_70165_t = this.x;
/* 51 */       this.robot.field_70163_u = this.y;
/* 52 */       this.robot.field_70161_v = this.z;
/*    */       
/* 54 */       terminate();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canLoadFromNBT() {
/* 60 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeSelfToNBT(NBTTagCompound nbt) {
/* 65 */     super.writeSelfToNBT(nbt);
/*    */     
/* 67 */     nbt.func_74776_a("x", this.x);
/* 68 */     nbt.func_74776_a("y", this.y);
/* 69 */     nbt.func_74776_a("z", this.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 74 */     super.loadSelfFromNBT(nbt);
/*    */     
/* 76 */     if (nbt.func_74764_b("x")) {
/* 77 */       this.x = nbt.func_74760_g("x");
/* 78 */       this.y = nbt.func_74760_g("y");
/* 79 */       this.z = nbt.func_74760_g("z");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotStraightMoveTo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */