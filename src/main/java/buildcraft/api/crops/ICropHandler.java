package buildcraft.api.crops;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface ICropHandler {
  boolean isSeed(ItemStack paramItemStack);
  
  boolean canSustainPlant(World paramWorld, ItemStack paramItemStack, BlockPos paramBlockPos);
  
  boolean plantCrop(World paramWorld, EntityPlayer paramEntityPlayer, ItemStack paramItemStack, BlockPos paramBlockPos);
  
  boolean isMature(IBlockAccess paramIBlockAccess, IBlockState paramIBlockState, BlockPos paramBlockPos);
  
  boolean harvestCrop(World paramWorld, BlockPos paramBlockPos, NonNullList<ItemStack> paramNonNullList);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\crops\ICropHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */