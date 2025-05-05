package buildcraft.api.library;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface ILibraryTypeHandler {
  boolean isHandler(ItemStack paramItemStack, boolean paramBoolean);
  
  String getFileExtension();
  
  int getTextColor();
  
  String getName(ItemStack paramItemStack);
  
  ItemStack load(ItemStack paramItemStack, NBTTagCompound paramNBTTagCompound);
  
  boolean store(ItemStack paramItemStack, NBTTagCompound paramNBTTagCompound);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\library\ILibraryTypeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */