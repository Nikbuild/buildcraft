package buildcraft.api.recipes;

public interface IIntegrationRecipeRegistry extends IIntegrationRecipeProvider {
  void addRecipe(IntegrationRecipe paramIntegrationRecipe);
  
  void addRecipeProvider(IIntegrationRecipeProvider paramIIntegrationRecipeProvider);
  
  Iterable<IntegrationRecipe> getAllRecipes();
  
  Iterable<IIntegrationRecipeProvider> getAllRecipeProviders();
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\recipes\IIntegrationRecipeRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */