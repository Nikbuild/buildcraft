/*    */ package buildcraft.core.properties;
/*    */ 
/*    */ import buildcraft.api.core.BuildCraftAPI;
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
/*    */ public class WorldPropertyIsSoft
/*    */   extends WorldProperty
/*    */ {
/*    */   public boolean get(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
/* 19 */     return (block == null || block
/* 20 */       .isAir(blockAccess, x, y, z) || BuildCraftAPI.softBlocks
/* 21 */       .contains(block) || block
/* 22 */       .isReplaceable(blockAccess, x, y, z));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\properties\WorldPropertyIsSoft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */