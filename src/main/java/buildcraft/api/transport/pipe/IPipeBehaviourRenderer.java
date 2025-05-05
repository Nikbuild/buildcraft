package buildcraft.api.transport.pipe;

import net.minecraft.client.renderer.VertexBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IPipeBehaviourRenderer<B extends PipeBehaviour> {
  void render(B paramB, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, VertexBuffer paramVertexBuffer);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\IPipeBehaviourRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */