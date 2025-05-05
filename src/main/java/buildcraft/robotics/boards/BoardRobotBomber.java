/*     */ package buildcraft.robotics.boards;
/*     */ 
/*     */ import buildcraft.api.boards.RedstoneBoardNBT;
/*     */ import buildcraft.api.boards.RedstoneBoardRobot;
/*     */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.core.lib.inventory.ITransactor;
/*     */ import buildcraft.core.lib.inventory.Transactor;
/*     */ import buildcraft.core.lib.inventory.filters.ArrayStackFilter;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.core.lib.utils.IBlockFilter;
/*     */ import buildcraft.robotics.ai.AIRobotGotoBlock;
/*     */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*     */ import buildcraft.robotics.ai.AIRobotGotoStationAndLoad;
/*     */ import buildcraft.robotics.ai.AIRobotSearchRandomGroundBlock;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BoardRobotBomber
/*     */   extends RedstoneBoardRobot
/*     */ {
/*  35 */   private static final IStackFilter TNT_FILTER = (IStackFilter)new ArrayStackFilter(new ItemStack[] { new ItemStack(Blocks.field_150335_W) });
/*     */   
/*  37 */   private int flyingHeight = 20;
/*     */   
/*     */   public BoardRobotBomber(EntityRobotBase iRobot) {
/*  40 */     super(iRobot);
/*     */   }
/*     */ 
/*     */   
/*     */   public RedstoneBoardRobotNBT getNBTHandler() {
/*  45 */     return BCBoardNBT.REGISTRY.get("bomber");
/*     */   }
/*     */ 
/*     */   
/*     */   public final void update() {
/*  50 */     boolean containItems = false;
/*     */     
/*  52 */     for (int i = 0; i < this.robot.func_70302_i_(); i++) {
/*  53 */       if (this.robot.func_70301_a(i) != null) {
/*  54 */         containItems = true;
/*     */       }
/*     */     } 
/*     */     
/*  58 */     if (!containItems) {
/*  59 */       startDelegateAI((AIRobot)new AIRobotGotoStationAndLoad(this.robot, TNT_FILTER, -1));
/*     */     } else {
/*  61 */       startDelegateAI((AIRobot)new AIRobotSearchRandomGroundBlock(this.robot, 100, new IBlockFilter()
/*     */             {
/*     */               public boolean matches(World world, int x, int y, int z) {
/*  64 */                 return (y < world.func_72800_K() - BoardRobotBomber.this.flyingHeight && !world.func_147437_c(x, y, z));
/*     */               }
/*  66 */             }this.robot.getZoneToWork()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {
/*  72 */     if (ai instanceof AIRobotGotoStationAndLoad) {
/*  73 */       if (!ai.success()) {
/*  74 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */       }
/*  76 */     } else if (ai instanceof AIRobotSearchRandomGroundBlock) {
/*  77 */       if (ai.success()) {
/*  78 */         AIRobotSearchRandomGroundBlock aiFind = (AIRobotSearchRandomGroundBlock)ai;
/*     */         
/*  80 */         startDelegateAI((AIRobot)new AIRobotGotoBlock(this.robot, aiFind.blockFound.x, aiFind.blockFound.y + this.flyingHeight, aiFind.blockFound.z));
/*     */       }
/*     */       else {
/*     */         
/*  84 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */       } 
/*  86 */     } else if (ai instanceof AIRobotGotoBlock) {
/*  87 */       if (ai.success()) {
/*  88 */         ITransactor t = Transactor.getTransactorFor(this.robot);
/*  89 */         ItemStack stack = t.remove(TNT_FILTER, ForgeDirection.UNKNOWN, true);
/*     */         
/*  91 */         if (stack != null && stack.field_77994_a > 0) {
/*  92 */           EntityTNTPrimed tnt = new EntityTNTPrimed(this.robot.field_70170_p, this.robot.field_70165_t + 0.25D, this.robot.field_70163_u - 1.0D, this.robot.field_70161_v + 0.25D, (EntityLivingBase)this.robot);
/*     */ 
/*     */ 
/*     */           
/*  96 */           tnt.field_70516_a = 37;
/*  97 */           this.robot.field_70170_p.func_72838_d((Entity)tnt);
/*  98 */           this.robot.field_70170_p.func_72956_a((Entity)tnt, "game.tnt.primed", 1.0F, 1.0F);
/*     */         } 
/*     */       } else {
/* 101 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotBomber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */