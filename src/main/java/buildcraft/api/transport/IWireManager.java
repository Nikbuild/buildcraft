package buildcraft.api.transport;

import buildcraft.api.transport.pipe.IPipeHolder;
import net.minecraft.item.EnumDyeColor;

public interface IWireManager {
  IPipeHolder getHolder();
  
  void updateBetweens(boolean paramBoolean);
  
  EnumDyeColor getColorOfPart(EnumWirePart paramEnumWirePart);
  
  EnumDyeColor removePart(EnumWirePart paramEnumWirePart);
  
  boolean addPart(EnumWirePart paramEnumWirePart, EnumDyeColor paramEnumDyeColor);
  
  boolean hasPartOfColor(EnumDyeColor paramEnumDyeColor);
  
  boolean isPowered(EnumWirePart paramEnumWirePart);
  
  boolean isAnyPowered(EnumDyeColor paramEnumDyeColor);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\IWireManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */