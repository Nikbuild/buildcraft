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
/*    */ 
/*    */ public class BoardRobotLeaveCutter
/*    */   extends BoardRobotGenericBreakBlock
/*    */ {
/*    */   public BoardRobotLeaveCutter(EntityRobotBase iRobot) {
/* 22 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardRobotNBT getNBTHandler() {
/* 27 */     return BCBoardNBT.REGISTRY.get("leaveCutter");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExpectedTool(ItemStack stack) {
/* 32 */     return (stack != null && stack.func_77973_b() instanceof net.minecraft.item.ItemShears);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExpectedBlock(World world, int x, int y, int z) {
/* 37 */     return BuildCraftAPI.getWorldProperty("leaves").get(world, x, y, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotLeaveCutter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */