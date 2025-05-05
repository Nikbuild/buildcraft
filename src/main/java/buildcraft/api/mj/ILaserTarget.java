package buildcraft.api.mj;

public interface ILaserTarget {
  long getRequiredLaserPower();
  
  long receiveLaserPower(long paramLong);
  
  boolean isInvalidTarget();
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\mj\ILaserTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */