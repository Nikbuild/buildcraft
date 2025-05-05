package buildcraft.api.recipes;

import java.util.Collection;

public interface IProgrammingRecipeManager {
  void addRecipe(IProgrammingRecipe paramIProgrammingRecipe);
  
  void removeRecipe(String paramString);
  
  void removeRecipe(IProgrammingRecipe paramIProgrammingRecipe);
  
  IProgrammingRecipe getRecipe(String paramString);
  
  Collection<IProgrammingRecipe> getRecipes();
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\recipes\IProgrammingRecipeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */