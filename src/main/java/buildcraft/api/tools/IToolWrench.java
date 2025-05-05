package buildcraft.api.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;

public interface IToolWrench {
  boolean canWrench(EntityPlayer paramEntityPlayer, EnumHand paramEnumHand, ItemStack paramItemStack, RayTraceResult paramRayTraceResult);
  
  void wrenchUsed(EntityPlayer paramEntityPlayer, EnumHand paramEnumHand, ItemStack paramItemStack, RayTraceResult paramRayTraceResult);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\tools\IToolWrench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */