/*    */ package buildcraft.robotics.boards;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.api.core.BuildCraftAPI;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BoardRobotMiner
/*    */   extends BoardRobotGenericBreakBlock
/*    */ {
/*    */   private static final int MAX_HARVEST_LEVEL = 3;
/* 22 */   private int harvestLevel = 0;
/*    */   
/*    */   public BoardRobotMiner(EntityRobotBase iRobot) {
/* 25 */     super(iRobot);
/* 26 */     detectHarvestLevel();
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 31 */     super.delegateAIEnded(ai);
/*    */     
/* 33 */     if (ai instanceof buildcraft.robotics.ai.AIRobotFetchAndEquipItemStack && 
/* 34 */       ai.success()) {
/* 35 */       detectHarvestLevel();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private void detectHarvestLevel() {
/* 41 */     ItemStack stack = this.robot.func_70694_bm();
/*    */     
/* 43 */     if (stack != null && stack.func_77973_b() != null && stack.func_77973_b().getToolClasses(stack).contains("pickaxe")) {
/* 44 */       this.harvestLevel = stack.func_77973_b().getHarvestLevel(stack, "pickaxe");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardRobotNBT getNBTHandler() {
/* 50 */     return BCBoardNBT.REGISTRY.get("miner");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExpectedTool(ItemStack stack) {
/* 55 */     return (stack != null && stack.func_77973_b().getToolClasses(stack).contains("pickaxe"));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExpectedBlock(World world, int x, int y, int z) {
/* 60 */     return BuildCraftAPI.getWorldProperty("ore@hardness=" + Math.min(3, this.harvestLevel))
/* 61 */       .get(world, x, y, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotMiner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */