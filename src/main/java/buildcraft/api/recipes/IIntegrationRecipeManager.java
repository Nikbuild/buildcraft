package buildcraft.api.recipes;

import java.util.List;

public interface IIntegrationRecipeManager {
  void addRecipe(IIntegrationRecipe paramIIntegrationRecipe);
  
  List<? extends IIntegrationRecipe> getRecipes();
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\recipes\IIntegrationRecipeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */