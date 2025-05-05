/*    */ package buildcraft.core.lib.engines;
/*    */ 
/*    */ import buildcraft.core.BCCreativeTab;
/*    */ import buildcraft.core.lib.items.ItemBlockBuildCraft;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemEngine
/*    */   extends ItemBlockBuildCraft
/*    */ {
/*    */   private final BlockEngineBase engineBlock;
/*    */   
/*    */   public ItemEngine(Block block) {
/* 22 */     super(block);
/* 23 */     this.engineBlock = (BlockEngineBase)block;
/* 24 */     func_77637_a((CreativeTabs)BCCreativeTab.get("main"));
/* 25 */     func_77656_e(0);
/* 26 */     func_77627_a(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77647_b(int i) {
/* 31 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack itemstack) {
/* 36 */     return this.engineBlock.getUnlocalizedName(itemstack.func_77960_j());
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon func_77650_f(ItemStack stack) {
/* 41 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\engines\ItemEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */