package buildcraft.api.transport;

import buildcraft.api.transport.pipe.IPipeHolder;
import buildcraft.api.transport.pluggable.PipePluggable;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

public interface IItemPluggable {
  PipePluggable onPlace(@Nonnull ItemStack paramItemStack, IPipeHolder paramIPipeHolder, EnumFacing paramEnumFacing, EntityPlayer paramEntityPlayer, EnumHand paramEnumHand);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\IItemPluggable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */