package buildcraft.api.recipes;

import net.minecraft.item.ItemStack;

public interface IFlexibleRecipe<T> {
  boolean canBeCrafted(IFlexibleCrafter paramIFlexibleCrafter);
  
  CraftingResult<T> craft(IFlexibleCrafter paramIFlexibleCrafter, boolean paramBoolean);
  
  CraftingResult<T> canCraft(ItemStack paramItemStack);
  
  String getId();
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\recipes\IFlexibleRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */