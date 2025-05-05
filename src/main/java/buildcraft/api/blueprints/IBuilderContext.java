package buildcraft.api.blueprints;

import buildcraft.api.core.IBox;
import buildcraft.api.core.Position;
import net.minecraft.world.World;

public interface IBuilderContext {
  Position rotatePositionLeft(Position paramPosition);
  
  IBox surroundingBox();
  
  World world();
  
  MappingRegistry getMappingRegistry();
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\blueprints\IBuilderContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */