package buildcraft.api.filler;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;

public interface IFillerRegistry {
  void addPattern(IFillerPattern paramIFillerPattern);
  
  @Nullable
  IFillerPattern getPattern(String paramString);
  
  Collection<IFillerPattern> getPatterns();
  
  IFilledTemplate createFilledTemplate(BlockPos paramBlockPos1, BlockPos paramBlockPos2);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\filler\IFillerRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */