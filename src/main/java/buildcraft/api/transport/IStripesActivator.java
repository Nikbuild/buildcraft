package buildcraft.api.transport;

import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface IStripesActivator {
  boolean sendItem(@Nonnull ItemStack paramItemStack, EnumFacing paramEnumFacing);
  
  void dropItem(@Nonnull ItemStack paramItemStack, EnumFacing paramEnumFacing);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\IStripesActivator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */