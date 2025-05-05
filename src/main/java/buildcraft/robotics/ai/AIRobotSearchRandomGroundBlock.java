/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.core.BlockIndex;
/*    */ import buildcraft.api.core.IZone;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.utils.IBlockFilter;
/*    */ import net.minecraft.util.MathHelper;
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
/*    */ public class AIRobotSearchRandomGroundBlock
/*    */   extends AIRobot
/*    */ {
/*    */   private static final int MAX_ATTEMPTS = 4096;
/*    */   public BlockIndex blockFound;
/*    */   private int range;
/*    */   private IBlockFilter filter;
/*    */   private IZone zone;
/* 28 */   private int attempts = 0;
/*    */   
/*    */   public AIRobotSearchRandomGroundBlock(EntityRobotBase iRobot) {
/* 31 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotSearchRandomGroundBlock(EntityRobotBase iRobot, int iRange, IBlockFilter iFilter, IZone iZone) {
/* 35 */     this(iRobot);
/*    */     
/* 37 */     this.range = iRange;
/* 38 */     this.filter = iFilter;
/* 39 */     this.zone = iZone;
/*    */   }
/*    */   
/*    */   public void update() {
/*    */     int x, z;
/* 44 */     if (this.filter == null) {
/* 45 */       terminate();
/*    */     }
/*    */     
/* 48 */     this.attempts++;
/*    */     
/* 50 */     if (this.attempts > 4096) {
/* 51 */       terminate();
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 56 */     if (this.zone == null) {
/* 57 */       double r = (this.robot.field_70170_p.field_73012_v.nextFloat() * this.range);
/* 58 */       float a = this.robot.field_70170_p.field_73012_v.nextFloat() * 2.0F * 3.1415927F;
/*    */       
/* 60 */       x = (int)(MathHelper.func_76134_b(a) * r + Math.floor(this.robot.field_70165_t));
/* 61 */       z = (int)(MathHelper.func_76126_a(a) * r + Math.floor(this.robot.field_70161_v));
/*    */     } else {
/* 63 */       BlockIndex b = this.zone.getRandomBlockIndex(this.robot.field_70170_p.field_73012_v);
/* 64 */       x = b.x;
/* 65 */       z = b.z;
/*    */     } 
/*    */     
/* 68 */     for (int y = this.robot.field_70170_p.func_72800_K(); y >= 0; y--) {
/* 69 */       if (this.filter.matches(this.robot.field_70170_p, x, y, z)) {
/* 70 */         this.blockFound = new BlockIndex(x, y, z);
/* 71 */         terminate(); return;
/*    */       } 
/* 73 */       if (!this.robot.field_70170_p.func_147437_c(x, y, z)) {
/*    */         return;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean success() {
/* 81 */     return (this.blockFound != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyCost() {
/* 86 */     return 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotSearchRandomGroundBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */