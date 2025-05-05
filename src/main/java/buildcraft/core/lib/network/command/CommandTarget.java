package buildcraft.core.lib.network.command;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class CommandTarget {
  public abstract Class<?> getHandledClass();
  
  public abstract ICommandReceiver handle(EntityPlayer paramEntityPlayer, ByteBuf paramByteBuf, World paramWorld);
  
  public abstract void write(ByteBuf paramByteBuf, Object paramObject);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\command\CommandTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */