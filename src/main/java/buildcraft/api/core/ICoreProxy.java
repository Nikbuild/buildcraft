package buildcraft.api.core;

import java.lang.ref.WeakReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;

public interface ICoreProxy {
  WeakReference<EntityPlayer> getBuildCraftPlayer(WorldServer paramWorldServer);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\core\ICoreProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */