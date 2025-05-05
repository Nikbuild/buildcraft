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
/*    */ 
/*    */ public class WorldPropertyIsDirt
/*    */   extends WorldProperty
/*    */ {
/*    */   public boolean get(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
/* 20 */     return (block instanceof net.minecraft.block.BlockDirt || block instanceof net.minecraft.block.BlockGrass);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\properties\WorldPropertyIsDirt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */