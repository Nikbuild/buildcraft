package buildcraft.api.recipes;

import java.util.List;
import net.minecraft.item.ItemStack;

public interface IProgrammingRecipe {
  String getId();
  
  List<ItemStack> getOptions(int paramInt1, int paramInt2);
  
  int getEnergyCost(ItemStack paramItemStack);
  
  boolean canCraft(ItemStack paramItemStack);
  
  ItemStack craft(ItemStack paramItemStack1, ItemStack paramItemStack2);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\recipes\IProgrammingRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */