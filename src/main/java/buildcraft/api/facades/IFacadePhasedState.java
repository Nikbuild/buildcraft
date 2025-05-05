package buildcraft.api.facades;

import javax.annotation.Nullable;
import net.minecraft.item.EnumDyeColor;

public interface IFacadePhasedState {
  IFacadeState getState();
  
  boolean isHollow();
  
  @Nullable
  EnumDyeColor getActiveColor();
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\facades\IFacadePhasedState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */