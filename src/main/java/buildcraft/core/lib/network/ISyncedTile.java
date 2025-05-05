package buildcraft.core.lib.network;

import buildcraft.api.core.ISerializable;

public interface ISyncedTile {
  ISerializable getStateInstance(byte paramByte);
  
  void afterStateUpdated(byte paramByte);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\ISyncedTile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */