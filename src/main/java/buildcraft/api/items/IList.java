package buildcraft.api.items;

import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;

public interface IList extends INamedItem {
  boolean matches(@Nonnull ItemStack paramItemStack1, @Nonnull ItemStack paramItemStack2);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\items\IList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */