package buildcraft.api.statements;

import javax.annotation.Nullable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IStatementContainer {
  TileEntity getTile();
  
  @Nullable
  TileEntity getNeighbourTile(EnumFacing paramEnumFacing);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\IStatementContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */