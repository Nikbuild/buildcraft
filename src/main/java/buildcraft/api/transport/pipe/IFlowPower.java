package buildcraft.api.transport.pipe;

import net.minecraft.util.EnumFacing;

public interface IFlowPower {
  void reconfigure();
  
  long tryExtractPower(long paramLong, EnumFacing paramEnumFacing);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\IFlowPower.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */