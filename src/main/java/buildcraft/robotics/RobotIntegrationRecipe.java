/*    */ package buildcraft.robotics;
/*    */ 
/*    */ import buildcraft.BuildCraftRobotics;
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRegistry;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.core.lib.utils.NBTUtils;
/*    */ import buildcraft.core.recipes.IntegrationRecipeBC;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RobotIntegrationRecipe
/*    */   extends IntegrationRecipeBC
/*    */ {
/*    */   public RobotIntegrationRecipe() {
/* 26 */     super(50000, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> generateExampleInput() {
/* 31 */     ArrayList<ItemStack> example = new ArrayList<ItemStack>();
/* 32 */     example.add(ItemRobot.createRobotStack(RedstoneBoardRegistry.instance.getEmptyRobotBoard(), 0));
/* 33 */     return example;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<List<ItemStack>> generateExampleExpansions() {
/* 38 */     ArrayList<List<ItemStack>> list = new ArrayList<List<ItemStack>>();
/* 39 */     ArrayList<ItemStack> example = new ArrayList<ItemStack>();
/* 40 */     for (RedstoneBoardNBT<?> nbt : (Iterable<RedstoneBoardNBT<?>>)RedstoneBoardRegistry.instance.getAllBoardNBTs()) {
/* 41 */       ItemStack stack = new ItemStack((Item)BuildCraftRobotics.redstoneBoard);
/* 42 */       nbt.createBoard(NBTUtils.getItemData(stack));
/* 43 */       example.add(stack);
/*    */     } 
/* 45 */     list.add(example);
/* 46 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> generateExampleOutput() {
/* 51 */     ArrayList<ItemStack> example = new ArrayList<ItemStack>();
/* 52 */     for (RedstoneBoardNBT<?> nbt : (Iterable<RedstoneBoardNBT<?>>)RedstoneBoardRegistry.instance.getAllBoardNBTs()) {
/* 53 */       example.add(ItemRobot.createRobotStack((RedstoneBoardRobotNBT)nbt, 0));
/*    */     }
/* 55 */     return example;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidInput(ItemStack input) {
/* 60 */     return input.func_77973_b() instanceof ItemRobot;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidExpansion(ItemStack input, ItemStack expansion) {
/* 65 */     return expansion.func_77973_b() instanceof ItemRedstoneBoard;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack craft(ItemStack input, List<ItemStack> expansions, boolean preview) {
/* 70 */     if (!preview) {
/* 71 */       ((ItemStack)expansions.get(0)).field_77994_a--;
/*    */     }
/* 73 */     RedstoneBoardRobotNBT boardNBT = (RedstoneBoardRobotNBT)ItemRedstoneBoard.getBoardNBT(expansions.get(0));
/*    */     
/* 75 */     int energy = ItemRobot.getEnergy(input);
/* 76 */     if (energy == 0) {
/* 77 */       energy = 20000;
/*    */     }
/* 79 */     return ItemRobot.createRobotStack(boardNBT, energy);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\RobotIntegrationRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */