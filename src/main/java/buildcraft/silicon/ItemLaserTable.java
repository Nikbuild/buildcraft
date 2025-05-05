/*    */ package buildcraft.silicon;
/*    */ 
/*    */ import buildcraft.core.lib.items.ItemBlockBuildCraft;
/*    */ import net.minecraft.block.Block;
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
/*    */ public class ItemLaserTable
/*    */   extends ItemBlockBuildCraft
/*    */ {
/*    */   public ItemLaserTable(Block block) {
/* 19 */     super(block);
/* 20 */     func_77656_e(0);
/* 21 */     func_77627_a(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack stack) {
/* 26 */     switch (stack.func_77960_j()) {
/*    */       case 0:
/* 28 */         return "tile.assemblyTableBlock";
/*    */       case 1:
/* 30 */         return "tile.assemblyWorkbenchBlock";
/*    */       case 2:
/* 32 */         return "tile.integrationTableBlock";
/*    */       case 3:
/* 34 */         return "tile.chargingTableBlock";
/*    */       case 4:
/* 36 */         return "tile.programmingTableBlock";
/*    */       case 5:
/* 38 */         return "tile.stampingTableBlock";
/*    */     } 
/* 40 */     return func_77658_a();
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77647_b(int meta) {
/* 45 */     return (meta < 6) ? meta : 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\ItemLaserTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */