package buildcraft.api.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICustomRotationHandler {
  EnumActionResult attemptRotation(World paramWorld, BlockPos paramBlockPos, IBlockState paramIBlockState, EnumFacing paramEnumFacing);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\blocks\ICustomRotationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */