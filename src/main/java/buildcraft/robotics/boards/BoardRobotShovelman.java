/*    */ package buildcraft.robotics.boards;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.api.core.BuildCraftAPI;
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
/*    */ public class BoardRobotShovelman
/*    */   extends BoardRobotGenericBreakBlock
/*    */ {
/*    */   public BoardRobotShovelman(EntityRobotBase iRobot) {
/* 21 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardRobotNBT getNBTHandler() {
/* 26 */     return BCBoardNBT.REGISTRY.get("shovelman");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExpectedTool(ItemStack stack) {
/* 31 */     return (stack != null && stack.func_77973_b().getToolClasses(stack).contains("shovel"));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExpectedBlock(World world, int x, int y, int z) {
/* 36 */     return BuildCraftAPI.getWorldProperty("shoveled").get(world, x, y, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotShovelman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */