package buildcraft.api.tiles;

import java.util.List;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IDebuggable {
  void getDebugInfo(List<String> paramList1, List<String> paramList2, EnumFacing paramEnumFacing);
  
  @SideOnly(Side.CLIENT)
  default void getClientDebugInfo(List<String> left, List<String> right, EnumFacing side) {}
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\tiles\IDebuggable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */