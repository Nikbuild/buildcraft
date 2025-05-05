package buildcraft.api.tablet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

public interface ITablet {
  Side getSide();
  
  void refreshScreen(TabletBitmap paramTabletBitmap);
  
  int getScreenWidth();
  
  int getScreenHeight();
  
  void launchProgram(String paramString);
  
  void sendMessage(NBTTagCompound paramNBTTagCompound);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\tablet\ITablet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */