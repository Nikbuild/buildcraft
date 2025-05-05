package buildcraft.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFlexibleCrafter {
  int getCraftingItemStackSize();
  
  ItemStack getCraftingItemStack(int paramInt);
  
  ItemStack decrCraftingItemStack(int paramInt1, int paramInt2);
  
  FluidStack getCraftingFluidStack(int paramInt);
  
  FluidStack decrCraftingFluidStack(int paramInt1, int paramInt2);
  
  int getCraftingFluidStackSize();
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\recipes\IFlexibleCrafter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */