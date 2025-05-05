package buildcraft.builders.blueprints;

import net.minecraft.inventory.IInventory;

public interface IBlueprintBuilderAgent {
  boolean breakBlock(int paramInt1, int paramInt2, int paramInt3);
  
  IInventory getInventory();
  
  boolean buildBlock(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\blueprints\IBlueprintBuilderAgent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */