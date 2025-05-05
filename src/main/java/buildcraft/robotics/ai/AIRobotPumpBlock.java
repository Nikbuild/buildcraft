/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.core.BlockIndex;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.utils.BlockUtils;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotPumpBlock
/*    */   extends AIRobot
/*    */ {
/*    */   private BlockIndex blockToPump;
/* 22 */   private long waited = 0L;
/* 23 */   private int pumped = 0;
/*    */   
/*    */   public AIRobotPumpBlock(EntityRobotBase iRobot) {
/* 26 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotPumpBlock(EntityRobotBase iRobot, BlockIndex iBlockToPump) {
/* 30 */     this(iRobot);
/*    */     
/* 32 */     this.blockToPump = iBlockToPump;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 37 */     this.robot.aimItemAt(this.blockToPump.x, this.blockToPump.y, this.blockToPump.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void preempt(AIRobot ai) {
/* 42 */     super.preempt(ai);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 47 */     if (this.waited < 40L) {
/* 48 */       this.waited++;
/*    */     } else {
/* 50 */       FluidStack fluidStack = BlockUtils.drainBlock(this.robot.field_70170_p, this.blockToPump.x, this.blockToPump.y, this.blockToPump.z, false);
/* 51 */       if (fluidStack != null && 
/* 52 */         this.robot.fill(ForgeDirection.UNKNOWN, fluidStack, true) > 0) {
/* 53 */         BlockUtils.drainBlock(this.robot.field_70170_p, this.blockToPump.x, this.blockToPump.y, this.blockToPump.z, true);
/*    */       }
/*    */ 
/*    */       
/* 57 */       terminate();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEnergyCost() {
/* 64 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean success() {
/* 69 */     return (this.pumped > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotPumpBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */