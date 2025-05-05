package buildcraft.api.blocks;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface ICustomPaintHandler {
  EnumActionResult attemptPaint(World paramWorld, BlockPos paramBlockPos, IBlockState paramIBlockState, Vec3d paramVec3d, @Nullable EnumFacing paramEnumFacing, @Nullable EnumDyeColor paramEnumDyeColor);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\blocks\ICustomPaintHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */