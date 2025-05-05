package buildcraft.api.core;

import net.minecraft.util.math.BlockPos;

public interface IAreaProvider {
  BlockPos min();
  
  BlockPos max();
  
  void removeFromWorld();
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\IAreaProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */