package buildcraft.api.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IItemCustomPipeRender {
  float getPipeRenderScale(ItemStack paramItemStack);
  
  @SideOnly(Side.CLIENT)
  boolean renderItemInPipe(ItemStack paramItemStack, double paramDouble1, double paramDouble2, double paramDouble3);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\items\IItemCustomPipeRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */