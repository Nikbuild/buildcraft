package buildcraft.api.statements.containers;

import net.minecraft.util.EnumFacing;

public interface IRedstoneStatementContainer {
  int getRedstoneInput(EnumFacing paramEnumFacing);
  
  boolean setRedstoneOutput(EnumFacing paramEnumFacing, int paramInt);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\containers\IRedstoneStatementContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */