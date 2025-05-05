package buildcraft.core.builders;

import java.util.Collection;

public interface IBuildingItemsProvider {
  Collection<BuildingItem> getBuilders();
  
  void addAndLaunchBuildingItem(BuildingItem paramBuildingItem);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\IBuildingItemsProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */