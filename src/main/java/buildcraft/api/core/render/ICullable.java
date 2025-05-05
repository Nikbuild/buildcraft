package buildcraft.api.core.render;

import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public interface ICullable {
  void setRenderSide(ForgeDirection paramForgeDirection, boolean paramBoolean);
  
  void setRenderAllSides();
  
  boolean shouldSideBeRendered(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void setRenderMask(int paramInt);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\core\render\ICullable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */