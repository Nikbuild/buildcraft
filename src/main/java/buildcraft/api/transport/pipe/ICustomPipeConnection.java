package buildcraft.api.transport.pipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICustomPipeConnection {
  float getExtension(World paramWorld, BlockPos paramBlockPos, EnumFacing paramEnumFacing, IBlockState paramIBlockState);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\ICustomPipeConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */