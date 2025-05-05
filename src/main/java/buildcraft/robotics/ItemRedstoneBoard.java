/*    */ package buildcraft.robotics;
/*    */ 
/*    */ import buildcraft.BuildCraftRobotics;
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRegistry;
/*    */ import buildcraft.core.BCCreativeTab;
/*    */ import buildcraft.core.lib.items.ItemBuildCraft;
/*    */ import buildcraft.core.lib.utils.NBTUtils;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.IIcon;
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
/*    */ public class ItemRedstoneBoard
/*    */   extends ItemBuildCraft
/*    */ {
/*    */   public ItemRedstoneBoard() {
/* 32 */     super((CreativeTabs)BCCreativeTab.get("boards"));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getItemStackLimit(ItemStack stack) {
/* 37 */     return (getBoardNBT(stack) != RedstoneBoardRegistry.instance.getEmptyRobotBoard()) ? 1 : 16;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_77624_a(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
/* 42 */     RedstoneBoardNBT<?> board = getBoardNBT(stack);
/* 43 */     board.addInformation(stack, player, list, advanced);
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon func_77650_f(ItemStack stack) {
/* 48 */     NBTTagCompound cpt = getNBT(stack);
/* 49 */     return getBoardNBT(cpt).getIcon(cpt);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public String[] getIconNames() {
/* 55 */     return new String[] { "board/clean" };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_150895_a(Item item, CreativeTabs par2CreativeTabs, List<ItemStack> itemList) {
/* 62 */     itemList.add(createStack((RedstoneBoardNBT<?>)RedstoneBoardRegistry.instance.getEmptyRobotBoard()));
/* 63 */     for (RedstoneBoardNBT<?> boardNBT : (Iterable<RedstoneBoardNBT<?>>)RedstoneBoardRegistry.instance.getAllBoardNBTs()) {
/* 64 */       itemList.add(createStack(boardNBT));
/*    */     }
/*    */   }
/*    */   
/*    */   public static ItemStack createStack(RedstoneBoardNBT<?> boardNBT) {
/* 69 */     ItemStack stack = new ItemStack((Item)BuildCraftRobotics.redstoneBoard);
/* 70 */     NBTTagCompound nbtData = NBTUtils.getItemData(stack);
/* 71 */     boardNBT.createBoard(nbtData);
/* 72 */     return stack;
/*    */   }
/*    */   
/*    */   public static RedstoneBoardNBT<?> getBoardNBT(ItemStack stack) {
/* 76 */     return getBoardNBT(getNBT(stack));
/*    */   }
/*    */   
/*    */   private static NBTTagCompound getNBT(ItemStack stack) {
/* 80 */     NBTTagCompound cpt = NBTUtils.getItemData(stack);
/* 81 */     if (!cpt.func_74764_b("id")) {
/* 82 */       RedstoneBoardRegistry.instance.getEmptyRobotBoard().createBoard(cpt);
/*    */     }
/* 84 */     return cpt;
/*    */   }
/*    */   
/*    */   private static RedstoneBoardNBT<?> getBoardNBT(NBTTagCompound cpt) {
/* 88 */     return RedstoneBoardRegistry.instance.getRedstoneBoard(cpt);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ItemRedstoneBoard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */