package buildcraft.api.transport.pluggable;

import buildcraft.api.core.render.ITextureStates;
import buildcraft.api.transport.IPipe;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraftforge.common.util.ForgeDirection;

public interface IPipePluggableRenderer {
  void renderPluggable(RenderBlocks paramRenderBlocks, IPipe paramIPipe, ForgeDirection paramForgeDirection, PipePluggable paramPipePluggable, ITextureStates paramITextureStates, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\transport\pluggable\IPipePluggableRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */