package buildcraft.api.tiles;

public interface IHeatable {
  double getMinHeatValue();
  
  double getIdealHeatValue();
  
  double getMaxHeatValue();
  
  double getCurrentHeatValue();
  
  double setHeatValue(double paramDouble);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\tiles\IHeatable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */