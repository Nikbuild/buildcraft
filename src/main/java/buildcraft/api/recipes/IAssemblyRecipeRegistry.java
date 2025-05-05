package buildcraft.api.recipes;

import javax.annotation.Nonnull;

public interface IAssemblyRecipeRegistry extends IAssemblyRecipeProvider {
  void addRecipe(@Nonnull AssemblyRecipe paramAssemblyRecipe);
  
  void addRecipeProvider(@Nonnull IAssemblyRecipeProvider paramIAssemblyRecipeProvider);
  
  Iterable<AssemblyRecipe> getAllRecipes();
  
  Iterable<IAssemblyRecipeProvider> getAllRecipeProviders();
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\recipes\IAssemblyRecipeRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */