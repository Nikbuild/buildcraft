package buildcraft.core.lib.render;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public interface ICustomHighlight {
  AxisAlignedBB[] getBoxes(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer);
  
  double getExpansion();
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\render\ICustomHighlight.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */