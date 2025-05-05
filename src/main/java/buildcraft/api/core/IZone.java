package buildcraft.api.core;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public interface IZone {
  double distanceTo(BlockPos paramBlockPos);
  
  double distanceToSquared(BlockPos paramBlockPos);
  
  boolean contains(Vec3d paramVec3d);
  
  BlockPos getRandomBlockPos(Random paramRandom);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\IZone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */