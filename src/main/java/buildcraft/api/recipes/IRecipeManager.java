package buildcraft.api.recipes;

import java.util.Collection;

public interface IRecipeManager<T> {
  void addRecipe(String paramString, int paramInt, T paramT, Object... paramVarArgs);
  
  void addRecipe(String paramString, int paramInt1, int paramInt2, T paramT, Object... paramVarArgs);
  
  void addRecipe(IFlexibleRecipe<T> paramIFlexibleRecipe);
  
  void removeRecipe(String paramString);
  
  void removeRecipe(IFlexibleRecipe<T> paramIFlexibleRecipe);
  
  Collection<IFlexibleRecipe<T>> getRecipes();
  
  IFlexibleRecipe<T> getRecipe(String paramString);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\recipes\IRecipeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */