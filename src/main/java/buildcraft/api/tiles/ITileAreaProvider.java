package buildcraft.api.tiles;

import buildcraft.api.core.IAreaProvider;
import net.minecraft.util.math.BlockPos;

public interface ITileAreaProvider extends IAreaProvider {
  boolean isValidFromLocation(BlockPos paramBlockPos);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\tiles\ITileAreaProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */