package buildcraft.api.core;

import io.netty.buffer.ByteBuf;

public interface ISerializable {
  void writeData(ByteBuf paramByteBuf);
  
  void readData(ByteBuf paramByteBuf);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\core\ISerializable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */