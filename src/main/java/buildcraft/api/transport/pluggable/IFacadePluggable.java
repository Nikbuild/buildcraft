package buildcraft.api.transport.pluggable;

import net.minecraft.block.Block;

public interface IFacadePluggable {
  Block getCurrentBlock();
  
  int getCurrentMetadata();
  
  boolean isTransparent();
  
  boolean isHollow();
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\transport\pluggable\IFacadePluggable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */