package buildcraft.api.recipes;

import java.util.Collection;

public interface IFlexibleRecipeViewable {
  Object getOutput();
  
  Collection<Object> getInputs();
  
  long getCraftingTime();
  
  int getEnergyCost();
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\recipes\IFlexibleRecipeViewable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */