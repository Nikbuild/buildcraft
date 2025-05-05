package buildcraft.api.items;

import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

public interface IItemFluidShard {
  void addFluidDrops(NonNullList<ItemStack> paramNonNullList, @Nullable FluidStack paramFluidStack);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\items\IItemFluidShard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */