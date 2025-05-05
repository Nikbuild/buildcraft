package buildcraft.api.statements;

import java.util.Collection;
import javax.annotation.Nonnull;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface ITriggerProvider {
  void addInternalTriggers(Collection<ITriggerInternal> paramCollection, IStatementContainer paramIStatementContainer);
  
  void addInternalSidedTriggers(Collection<ITriggerInternalSided> paramCollection, IStatementContainer paramIStatementContainer, @Nonnull EnumFacing paramEnumFacing);
  
  void addExternalTriggers(Collection<ITriggerExternal> paramCollection, @Nonnull EnumFacing paramEnumFacing, TileEntity paramTileEntity);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\ITriggerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */