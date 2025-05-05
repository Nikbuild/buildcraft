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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldPropertyIsShoveled
/*    */   extends WorldProperty
/*    */ {
/*    */   public boolean get(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
/* 25 */     return (block instanceof net.minecraft.block.BlockDirt || block instanceof net.minecraft.block.BlockSand || block instanceof net.minecraft.block.BlockClay || block instanceof net.minecraft.block.BlockGravel || block instanceof net.minecraft.block.BlockFarmland || block instanceof net.minecraft.block.BlockGrass || block instanceof net.minecraft.block.BlockSnow);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\properties\WorldPropertyIsShoveled.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */