package buildcraft.api.transport.pluggable;

import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IPluggableStaticBaker<K extends PluggableModelKey> {
  List<BakedQuad> bake(K paramK);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pluggable\IPluggableStaticBaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */