/*    */ package buildcraft.factory;
/*    */ 
/*    */ import buildcraft.BuildCraftFactory;
/*    */ import buildcraft.core.BlockHatched;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockMiningWell
/*    */   extends BlockHatched
/*    */ {
/*    */   public BlockMiningWell() {
/* 21 */     super(Material.field_151578_c);
/*    */     
/* 23 */     func_149711_c(5.0F);
/* 24 */     func_149752_b(10.0F);
/* 25 */     func_149672_a(field_149769_e);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_149749_a(World world, int x, int y, int z, Block block, int meta) {
/* 30 */     super.func_149749_a(world, x, y, z, block, meta);
/* 31 */     removePipes(world, x, y, z);
/*    */   }
/*    */   
/*    */   public void removePipes(World world, int x, int y, int z) {
/* 35 */     for (int depth = y - 1; depth > 0; depth--) {
/* 36 */       Block pipe = world.func_147439_a(x, depth, z);
/* 37 */       if (pipe != BuildCraftFactory.plainPipeBlock) {
/*    */         break;
/*    */       }
/* 40 */       world.func_147468_f(x, depth, z);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int metadata) {
/* 46 */     return (TileEntity)new TileMiningWell();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\BlockMiningWell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */