package buildcraft.transport;

import net.minecraftforge.common.util.ForgeDirection;

public interface IPipeTransportPowerHook {
  int receiveEnergy(ForgeDirection paramForgeDirection, int paramInt);
  
  int requestEnergy(ForgeDirection paramForgeDirection, int paramInt);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\IPipeTransportPowerHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */