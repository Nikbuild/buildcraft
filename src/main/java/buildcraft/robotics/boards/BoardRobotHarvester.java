/*    */ package buildcraft.robotics.boards;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.api.core.BuildCraftAPI;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.robotics.ai.AIRobotHarvest;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BoardRobotHarvester
/*    */   extends BoardRobotGenericSearchBlock
/*    */ {
/*    */   public BoardRobotHarvester(EntityRobotBase iRobot) {
/* 23 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public BoardRobotHarvester(EntityRobotBase iRobot, NBTTagCompound nbt) {
/* 27 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardRobotNBT getNBTHandler() {
/* 32 */     return BCBoardNBT.REGISTRY.get("harvester");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExpectedBlock(World world, int x, int y, int z) {
/* 37 */     return BuildCraftAPI.getWorldProperty("harvestable").get(world, x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 42 */     if (blockFound() != null) {
/* 43 */       startDelegateAI((AIRobot)new AIRobotHarvest(this.robot, blockFound()));
/*    */     } else {
/* 45 */       super.update();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 51 */     if (ai instanceof AIRobotHarvest) {
/* 52 */       releaseBlockFound(ai.success());
/*    */     }
/* 54 */     super.delegateAIEnded(ai);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotHarvester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */