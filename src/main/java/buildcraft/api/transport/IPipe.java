package buildcraft.api.transport;

import buildcraft.api.gates.IGate;
import net.minecraftforge.common.util.ForgeDirection;

public interface IPipe {
  IPipeTile getTile();
  
  IGate getGate(ForgeDirection paramForgeDirection);
  
  boolean hasGate(ForgeDirection paramForgeDirection);
  
  boolean isWired(PipeWire paramPipeWire);
  
  boolean isWireActive(PipeWire paramPipeWire);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\transport\IPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */