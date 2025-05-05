/*    */ package buildcraft.robotics.boards;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.api.core.BuildCraftAPI;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ public class BoardRobotLumberjack
/*    */   extends BoardRobotGenericBreakBlock
/*    */ {
/*    */   public BoardRobotLumberjack(EntityRobotBase iRobot) {
/* 22 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public BoardRobotLumberjack(EntityRobotBase iRobot, NBTTagCompound nbt) {
/* 26 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardRobotNBT getNBTHandler() {
/* 31 */     return BCBoardNBT.REGISTRY.get("lumberjack");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExpectedTool(ItemStack stack) {
/* 36 */     return (stack != null && stack.func_77973_b().getToolClasses(stack).contains("axe"));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExpectedBlock(World world, int x, int y, int z) {
/* 41 */     return BuildCraftAPI.getWorldProperty("wood").get(world, x, y, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotLumberjack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */