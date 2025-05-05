package buildcraft.api.core;

import com.mojang.authlib.GameProfile;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

public interface IFakePlayerProvider {
  @Deprecated
  FakePlayer getBuildCraftPlayer(WorldServer paramWorldServer);
  
  FakePlayer getFakePlayer(WorldServer paramWorldServer, GameProfile paramGameProfile);
  
  FakePlayer getFakePlayer(WorldServer paramWorldServer, GameProfile paramGameProfile, BlockPos paramBlockPos);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\IFakePlayerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */