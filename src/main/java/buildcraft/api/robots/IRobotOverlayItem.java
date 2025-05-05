package buildcraft.api.robots;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IRobotOverlayItem {
  boolean isValidRobotOverlay(ItemStack paramItemStack);
  
  @SideOnly(Side.CLIENT)
  void renderRobotOverlay(ItemStack paramItemStack, TextureManager paramTextureManager);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\robots\IRobotOverlayItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */