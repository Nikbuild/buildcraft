package buildcraft.api.transport.pluggable;

import net.minecraft.client.renderer.VertexBuffer;

public interface IPlugDynamicRenderer<P extends PipePluggable> {
  void render(P paramP, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, VertexBuffer paramVertexBuffer);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pluggable\IPlugDynamicRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */