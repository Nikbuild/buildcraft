package buildcraft.core.lib;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public interface ITileBufferHolder {
  void blockRemoved(ForgeDirection paramForgeDirection);
  
  void blockCreated(ForgeDirection paramForgeDirection, Block paramBlock, TileEntity paramTileEntity);
  
  Block getBlock(ForgeDirection paramForgeDirection);
  
  TileEntity getTile(ForgeDirection paramForgeDirection);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\ITileBufferHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */