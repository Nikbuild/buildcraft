package buildcraft.api.robots;

import java.util.Collection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IRobotRegistry {
  long getNextRobotId();
  
  void registerRobot(EntityRobotBase paramEntityRobotBase);
  
  void killRobot(EntityRobotBase paramEntityRobotBase);
  
  void unloadRobot(EntityRobotBase paramEntityRobotBase);
  
  EntityRobotBase getLoadedRobot(long paramLong);
  
  boolean isTaken(ResourceId paramResourceId);
  
  long robotIdTaking(ResourceId paramResourceId);
  
  EntityRobotBase robotTaking(ResourceId paramResourceId);
  
  boolean take(ResourceId paramResourceId, EntityRobotBase paramEntityRobotBase);
  
  boolean take(ResourceId paramResourceId, long paramLong);
  
  void release(ResourceId paramResourceId);
  
  void releaseResources(EntityRobotBase paramEntityRobotBase);
  
  DockingStation getStation(BlockPos paramBlockPos, EnumFacing paramEnumFacing);
  
  Collection<DockingStation> getStations();
  
  void registerStation(DockingStation paramDockingStation);
  
  void removeStation(DockingStation paramDockingStation);
  
  void take(DockingStation paramDockingStation, long paramLong);
  
  void release(DockingStation paramDockingStation, long paramLong);
  
  void writeToNBT(NBTTagCompound paramNBTTagCompound);
  
  void readFromNBT(NBTTagCompound paramNBTTagCompound);
  
  void registryMarkDirty();
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\robots\IRobotRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */