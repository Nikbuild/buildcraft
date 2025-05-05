package buildcraft.api.power;

import net.minecraft.util.EnumFacing;

public interface IEngine {
  boolean canReceiveFromEngine(EnumFacing paramEnumFacing);
  
  boolean receivePower(long paramLong, boolean paramBoolean);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\power\IEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */