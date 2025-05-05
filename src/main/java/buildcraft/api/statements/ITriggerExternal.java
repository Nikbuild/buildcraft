package buildcraft.api.statements;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface ITriggerExternal extends ITrigger {
  boolean isTriggerActive(TileEntity paramTileEntity, EnumFacing paramEnumFacing, IStatementContainer paramIStatementContainer, IStatementParameter[] paramArrayOfIStatementParameter);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\ITriggerExternal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */