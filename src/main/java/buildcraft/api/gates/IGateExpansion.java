package buildcraft.api.gates;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public interface IGateExpansion {
  String getUniqueIdentifier();
  
  String getDisplayName();
  
  GateExpansionController makeController(TileEntity paramTileEntity);
  
  void registerBlockOverlay(IIconRegister paramIIconRegister);
  
  void registerItemOverlay(IIconRegister paramIIconRegister);
  
  IIcon getOverlayBlock();
  
  IIcon getOverlayItem();
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\gates\IGateExpansion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */