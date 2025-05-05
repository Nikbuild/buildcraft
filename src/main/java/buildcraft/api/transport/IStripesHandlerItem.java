package buildcraft.api.transport;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IStripesHandlerItem {
  boolean handle(World paramWorld, BlockPos paramBlockPos, EnumFacing paramEnumFacing, ItemStack paramItemStack, EntityPlayer paramEntityPlayer, IStripesActivator paramIStripesActivator);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\IStripesHandlerItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */