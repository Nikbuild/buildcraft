package buildcraft.api.core;

import javax.annotation.Nullable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IFluidHandlerAdv extends IFluidHandler {
  @Nullable
  FluidStack drain(IFluidFilter paramIFluidFilter, int paramInt, boolean paramBoolean);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\IFluidHandlerAdv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */