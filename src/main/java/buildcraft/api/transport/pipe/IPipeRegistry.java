package buildcraft.api.transport.pipe;

import net.minecraft.util.ResourceLocation;

public interface IPipeRegistry {
  PipeDefinition getDefinition(ResourceLocation paramResourceLocation);
  
  IItemPipe registerPipeAndItem(PipeDefinition paramPipeDefinition);
  
  void registerPipe(PipeDefinition paramPipeDefinition);
  
  void setItemForPipe(PipeDefinition paramPipeDefinition, IItemPipe paramIItemPipe);
  
  IItemPipe getItemForPipe(PipeDefinition paramPipeDefinition);
  
  Iterable<PipeDefinition> getAllRegisteredPipes();
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\IPipeRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */