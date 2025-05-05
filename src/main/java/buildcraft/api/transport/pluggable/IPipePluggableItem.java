package buildcraft.api.transport.pluggable;

import buildcraft.api.transport.IPipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public interface IPipePluggableItem {
  PipePluggable createPipePluggable(IPipe paramIPipe, ForgeDirection paramForgeDirection, ItemStack paramItemStack);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\transport\pluggable\IPipePluggableItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */