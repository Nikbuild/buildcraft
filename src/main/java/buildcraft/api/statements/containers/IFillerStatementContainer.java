package buildcraft.api.statements.containers;

import buildcraft.api.core.IBox;
import buildcraft.api.filler.IFillerPattern;
import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.IStatementParameter;
import javax.annotation.Nullable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IFillerStatementContainer extends IStatementContainer {
  @Nullable
  TileEntity getTile();
  
  World getFillerWorld();
  
  boolean hasBox();
  
  IBox getBox() throws IllegalStateException;
  
  void setPattern(IFillerPattern paramIFillerPattern, IStatementParameter[] paramArrayOfIStatementParameter);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\containers\IFillerStatementContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */