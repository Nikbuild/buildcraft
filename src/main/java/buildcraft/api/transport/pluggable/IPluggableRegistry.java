package buildcraft.api.transport.pluggable;

import net.minecraft.util.ResourceLocation;

public interface IPluggableRegistry {
  void register(PluggableDefinition paramPluggableDefinition);
  
  PluggableDefinition getDefinition(ResourceLocation paramResourceLocation);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pluggable\IPluggableRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */