package buildcraft.api.robots;

import net.minecraft.item.ItemStack;

public interface IRequestProvider {
  int getRequestsCount();
  
  ItemStack getRequest(int paramInt);
  
  ItemStack offerItem(int paramInt, ItemStack paramItemStack);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\robots\IRequestProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */