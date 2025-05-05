/*    */ package buildcraft.core.crops;
/*    */ 
/*    */ import buildcraft.api.crops.CropManager;
/*    */ import buildcraft.api.crops.ICropHandler;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.IPlantable;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropHandlerReeds
/*    */   implements ICropHandler
/*    */ {
/*    */   public boolean isSeed(ItemStack stack) {
/* 23 */     return (stack.func_77973_b() == Items.field_151120_aE);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSustainPlant(World world, ItemStack seed, int x, int y, int z) {
/* 28 */     Block block = world.func_147439_a(x, y, z);
/* 29 */     return (block.canSustainPlant((IBlockAccess)world, x, y, z, ForgeDirection.UP, (IPlantable)Blocks.field_150436_aH) && block != Blocks.field_150436_aH && world
/*    */       
/* 31 */       .func_147437_c(x, y + 1, z));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean plantCrop(World world, EntityPlayer player, ItemStack seed, int x, int y, int z) {
/* 36 */     return CropManager.getDefaultHandler().plantCrop(world, player, seed, x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMature(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean harvestCrop(World world, int x, int y, int z, List<ItemStack> drops) {
/* 46 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\crops\CropHandlerReeds.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */