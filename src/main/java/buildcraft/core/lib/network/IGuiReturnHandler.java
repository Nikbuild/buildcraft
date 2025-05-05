package buildcraft.core.lib.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IGuiReturnHandler {
  World getWorld();
  
  void writeGuiData(ByteBuf paramByteBuf);
  
  void readGuiData(ByteBuf paramByteBuf, EntityPlayer paramEntityPlayer);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\IGuiReturnHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */