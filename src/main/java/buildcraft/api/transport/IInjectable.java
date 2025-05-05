package buildcraft.api.transport;

import javax.annotation.Nonnull;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface IInjectable {
  boolean canInjectItems(EnumFacing paramEnumFacing);
  
  @Nonnull
  ItemStack injectItem(@Nonnull ItemStack paramItemStack, boolean paramBoolean, EnumFacing paramEnumFacing, EnumDyeColor paramEnumDyeColor, double paramDouble);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\IInjectable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */