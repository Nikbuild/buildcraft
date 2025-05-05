package buildcraft.api.core;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWorldProperty {
  boolean get(World paramWorld, BlockPos paramBlockPos);
  
  void clear();
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\IWorldProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */