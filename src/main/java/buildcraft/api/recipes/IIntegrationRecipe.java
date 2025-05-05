package buildcraft.api.recipes;

import java.util.List;
import net.minecraft.item.ItemStack;

public interface IIntegrationRecipe {
  int getEnergyCost();
  
  List<ItemStack> getExampleInput();
  
  List<List<ItemStack>> getExampleExpansions();
  
  List<ItemStack> getExampleOutput();
  
  boolean isValidInput(ItemStack paramItemStack);
  
  boolean isValidExpansion(ItemStack paramItemStack1, ItemStack paramItemStack2);
  
  ItemStack craft(ItemStack paramItemStack, List<ItemStack> paramList, boolean paramBoolean);
  
  int getMaximumExpansionCount(ItemStack paramItemStack);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\recipes\IIntegrationRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */