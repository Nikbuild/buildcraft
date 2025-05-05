/*    */ package buildcraft.core.properties;
/*    */ 
/*    */ import buildcraft.api.crops.CropManager;
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
/*    */ public class WorldPropertyIsHarvestable
/*    */   extends WorldProperty
/*    */ {
/*    */   public boolean get(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
/* 20 */     return CropManager.isMature(blockAccess, block, meta, x, y, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\properties\WorldPropertyIsHarvestable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */