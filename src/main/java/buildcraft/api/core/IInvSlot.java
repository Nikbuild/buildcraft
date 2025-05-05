package buildcraft.api.core;

import net.minecraft.item.ItemStack;

public interface IInvSlot {
  int getIndex();
  
  boolean canPutStackInSlot(ItemStack paramItemStack);
  
  boolean canTakeStackFromSlot(ItemStack paramItemStack);
  
  boolean isItemValidForSlot(ItemStack paramItemStack);
  
  ItemStack decreaseStackInSlot(int paramInt);
  
  ItemStack getStackInSlot();
  
  void setStackInSlot(ItemStack paramItemStack);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\IInvSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */