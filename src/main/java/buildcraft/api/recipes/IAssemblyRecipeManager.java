package buildcraft.api.recipes;

import java.util.Collection;
import net.minecraft.item.ItemStack;

public interface IAssemblyRecipeManager {
  void addRecipe(String paramString, int paramInt, ItemStack paramItemStack, Object... paramVarArgs);
  
  void addRecipe(IFlexibleRecipe<ItemStack> paramIFlexibleRecipe);
  
  void removeRecipe(String paramString);
  
  void removeRecipe(IFlexibleRecipe<ItemStack> paramIFlexibleRecipe);
  
  Collection<IFlexibleRecipe<ItemStack>> getRecipes();
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\recipes\IAssemblyRecipeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */