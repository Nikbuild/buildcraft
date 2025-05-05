package buildcraft.api.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public interface IIconProvider {
  @SideOnly(Side.CLIENT)
  IIcon getIcon(int paramInt);
  
  @SideOnly(Side.CLIENT)
  void registerIcons(IIconRegister paramIIconRegister);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\core\IIconProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */