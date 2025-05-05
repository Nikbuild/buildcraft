package buildcraft.api.mj;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface IMjEffectManager {
  void createPowerLossEffect(World paramWorld, Vec3d paramVec3d, long paramLong);
  
  void createPowerLossEffect(World paramWorld, Vec3d paramVec3d, EnumFacing paramEnumFacing, long paramLong);
  
  void createPowerLossEffect(World paramWorld, Vec3d paramVec3d1, Vec3d paramVec3d2, long paramLong);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\mj\IMjEffectManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */