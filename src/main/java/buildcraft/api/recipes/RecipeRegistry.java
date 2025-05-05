package buildcraft.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public final class RecipeRegistry {
  public static IRecipeManager<ItemStack> assemblyTable;
  
  public static IRecipeManager<ItemStack> integrationTable;
  
  public static IRecipeManager<FluidStack> refinery;
  
  public static IProgrammingRecipeManager programmingTable;
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\recipes\RecipeRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */