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
/*    */ public class WorldPropertyIsReplaceable
/*    */   extends WorldProperty
/*    */ {
/*    */   public boolean get(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
/* 17 */     return (block == null || block
/* 18 */       .isAir(blockAccess, x, y, z) || block
/* 19 */       .isReplaceable(blockAccess, x, y, z));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\properties\WorldPropertyIsReplaceable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */