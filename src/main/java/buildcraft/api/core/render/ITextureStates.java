package buildcraft.api.core.render;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;

public interface ITextureStates extends ICullable {
  ITextureStateManager getTextureState();
  
  IIcon getIcon(int paramInt1, int paramInt2);
  
  Block getBlock();
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\core\render\ITextureStates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */