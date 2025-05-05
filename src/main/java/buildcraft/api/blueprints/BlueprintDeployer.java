package buildcraft.api.blueprints;

import java.io.File;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlueprintDeployer {
  public static BlueprintDeployer instance;
  
  public abstract void deployBlueprint(World paramWorld, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection, File paramFile);
  
  public abstract void deployBlueprintFromFileStream(World paramWorld, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection, byte[] paramArrayOfbyte);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\blueprints\BlueprintDeployer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */