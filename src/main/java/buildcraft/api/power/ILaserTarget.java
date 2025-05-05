package buildcraft.api.power;

public interface ILaserTarget {
  boolean requiresLaserEnergy();
  
  void receiveLaserEnergy(int paramInt);
  
  boolean isInvalidTarget();
  
  double getXCoord();
  
  double getYCoord();
  
  double getZCoord();
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\power\ILaserTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */