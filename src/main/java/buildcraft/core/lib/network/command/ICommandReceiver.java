package buildcraft.core.lib.network.command;

import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;

public interface ICommandReceiver {
  void receiveCommand(String paramString, Side paramSide, Object paramObject, ByteBuf paramByteBuf);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\command\ICommandReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */