package buildcraft.core.lib.inventory;

import buildcraft.core.lib.inventory.filters.IStackFilter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public interface ITransactor {
  ItemStack add(ItemStack paramItemStack, ForgeDirection paramForgeDirection, boolean paramBoolean);
  
  ItemStack remove(IStackFilter paramIStackFilter, ForgeDirection paramForgeDirection, boolean paramBoolean);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\ITransactor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */