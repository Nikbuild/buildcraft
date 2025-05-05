package buildcraft.api.facades;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public interface IFacadeState {
  boolean isTransparent();
  
  IBlockState getBlockState();
  
  ItemStack getRequiredStack();
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\facades\IFacadeState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */