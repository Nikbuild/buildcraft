package buildcraft.api.statements;

import java.util.Collection;
import javax.annotation.Nonnull;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IActionProvider {
  void addInternalActions(Collection<IActionInternal> paramCollection, IStatementContainer paramIStatementContainer);
  
  void addInternalSidedActions(Collection<IActionInternalSided> paramCollection, IStatementContainer paramIStatementContainer, @Nonnull EnumFacing paramEnumFacing);
  
  void addExternalActions(Collection<IActionExternal> paramCollection, @Nonnull EnumFacing paramEnumFacing, TileEntity paramTileEntity);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\IActionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */