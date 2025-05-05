/*    */ package buildcraft.robotics;
/*    */ 
/*    */ import buildcraft.BuildCraftRobotics;
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRegistry;
/*    */ import buildcraft.api.recipes.IProgrammingRecipe;
/*    */ import buildcraft.core.lib.utils.NBTUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class BoardProgrammingRecipe
/*    */   implements IProgrammingRecipe {
/*    */   private class BoardSorter implements Comparator<ItemStack> {
/*    */     private BoardProgrammingRecipe recipe;
/*    */     
/*    */     public BoardSorter(BoardProgrammingRecipe recipe) {
/* 21 */       this.recipe = recipe;
/*    */     }
/*    */ 
/*    */     
/*    */     public int compare(ItemStack o1, ItemStack o2) {
/* 26 */       int i = (this.recipe.getEnergyCost(o1) - this.recipe.getEnergyCost(o2)) * 200;
/* 27 */       return (i != 0) ? i : ItemRedstoneBoard.getBoardNBT(o1).getID().compareTo(ItemRedstoneBoard.getBoardNBT(o2).getID());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 33 */     return "buildcraft:redstone_board";
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getOptions(int width, int height) {
/* 38 */     List<ItemStack> options = new ArrayList<ItemStack>(width * height);
/* 39 */     for (RedstoneBoardNBT<?> nbt : (Iterable<RedstoneBoardNBT<?>>)RedstoneBoardRegistry.instance.getAllBoardNBTs()) {
/* 40 */       ItemStack stack = new ItemStack((Item)BuildCraftRobotics.redstoneBoard);
/* 41 */       nbt.createBoard(NBTUtils.getItemData(stack));
/* 42 */       options.add(stack);
/*    */     } 
/* 44 */     Collections.sort(options, new BoardSorter(this));
/* 45 */     return options;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyCost(ItemStack option) {
/* 50 */     return RedstoneBoardRegistry.instance.getEnergyCost(RedstoneBoardRegistry.instance
/* 51 */         .getRedstoneBoard(option.func_77978_p().func_74779_i("id")));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canCraft(ItemStack input) {
/* 57 */     return input.func_77973_b() instanceof ItemRedstoneBoard;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack craft(ItemStack input, ItemStack option) {
/* 62 */     return option.func_77946_l();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\BoardProgrammingRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */