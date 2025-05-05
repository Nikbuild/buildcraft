package buildcraft.api.boards;

import java.util.Collection;
import net.minecraft.nbt.NBTTagCompound;

public abstract class RedstoneBoardRegistry {
  public static RedstoneBoardRegistry instance;
  
  public abstract void registerBoardType(RedstoneBoardNBT<?> paramRedstoneBoardNBT, long paramLong);
  
  public abstract void setEmptyRobotBoard(RedstoneBoardRobotNBT paramRedstoneBoardRobotNBT);
  
  public abstract RedstoneBoardRobotNBT getEmptyRobotBoard();
  
  public abstract RedstoneBoardNBT<?> getRedstoneBoard(NBTTagCompound paramNBTTagCompound);
  
  public abstract RedstoneBoardNBT<?> getRedstoneBoard(String paramString);
  
  public abstract Collection<RedstoneBoardNBT<?>> getAllBoardNBTs();
  
  public abstract long getPowerCost(RedstoneBoardNBT<?> paramRedstoneBoardNBT);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\boards\RedstoneBoardRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */