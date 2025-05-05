/*    */ package buildcraft.core.properties;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.world.IBlockAccess;
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
/*    */ public class WorldPropertyIsFarmland
/*    */   extends WorldProperty
/*    */ {
/*    */   public boolean get(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
/* 19 */     return block instanceof net.minecraft.block.BlockFarmland;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\properties\WorldPropertyIsFarmland.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */