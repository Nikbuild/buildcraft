package buildcraft.api.core;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTStoreable {
  void readFromNBT(NBTTagCompound paramNBTTagCompound);
  
  void writeToNBT(NBTTagCompound paramNBTTagCompound);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\core\INBTStoreable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */