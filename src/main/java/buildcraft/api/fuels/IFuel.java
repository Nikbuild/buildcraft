package buildcraft.api.fuels;

import net.minecraftforge.fluids.FluidStack;

public interface IFuel {
  FluidStack getFluid();
  
  int getTotalBurningTime();
  
  long getPowerPerCycle();
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\fuels\IFuel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */