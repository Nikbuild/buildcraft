/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.core.lib.items.ItemBlockBuildCraft;
/*    */ import java.util.Locale;
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
/*    */ 
/*    */ public class ItemSpring
/*    */   extends ItemBlockBuildCraft
/*    */ {
/*    */   public ItemSpring(Block block) {
/* 21 */     super(block);
/* 22 */     func_77656_e(0);
/* 23 */     func_77627_a(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack stack) {
/* 28 */     return "tile.spring." + BlockSpring.EnumSpring.fromMeta(stack.func_77960_j()).name().toLowerCase(Locale.ENGLISH);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\ItemSpring.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */