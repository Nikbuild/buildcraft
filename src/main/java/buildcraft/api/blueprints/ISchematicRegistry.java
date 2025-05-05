package buildcraft.api.blueprints;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;

public interface ISchematicRegistry {
  void registerSchematicBlock(Block paramBlock, Class<? extends Schematic> paramClass, Object... paramVarArgs);
  
  void registerSchematicBlock(Block paramBlock, int paramInt, Class<? extends Schematic> paramClass, Object... paramVarArgs);
  
  void registerSchematicEntity(Class<? extends Entity> paramClass, Class<? extends SchematicEntity> paramClass1, Object... paramVarArgs);
  
  boolean isSupported(Block paramBlock, int paramInt);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\blueprints\ISchematicRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */