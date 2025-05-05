/*    */ package buildcraft.core.properties;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraftforge.oredict.OreDictionary;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldPropertyIsLeaf
/*    */   extends WorldProperty
/*    */ {
/* 19 */   private int leavesId = 0;
/*    */   
/*    */   public WorldPropertyIsLeaf() {
/* 22 */     this.leavesId = OreDictionary.getOreID("treeLeaves");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean get(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
/* 27 */     if (block == null) {
/* 28 */       return false;
/*    */     }
/* 30 */     ItemStack stack = new ItemStack(block, 1, meta);
/*    */     
/* 32 */     if (stack.func_77973_b() != null) {
/* 33 */       for (int id : OreDictionary.getOreIDs(stack)) {
/* 34 */         if (id == this.leavesId) {
/* 35 */           return true;
/*    */         }
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/* 41 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\properties\WorldPropertyIsLeaf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */