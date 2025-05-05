package buildcraft.api.items;

import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;

public interface INamedItem {
  String getName(@Nonnull ItemStack paramItemStack);
  
  boolean setName(@Nonnull ItemStack paramItemStack, String paramString);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\items\INamedItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */