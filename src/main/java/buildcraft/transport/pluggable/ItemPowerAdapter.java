/*    */ package buildcraft.transport.pluggable;
/*    */ 
/*    */ import buildcraft.api.transport.IPipe;
/*    */ import buildcraft.api.transport.pluggable.IPipePluggableItem;
/*    */ import buildcraft.api.transport.pluggable.PipePluggable;
/*    */ import buildcraft.core.lib.items.ItemBuildCraft;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemPowerAdapter
/*    */   extends ItemBuildCraft
/*    */   implements IPipePluggableItem
/*    */ {
/*    */   public String func_77667_c(ItemStack itemstack) {
/* 29 */     return "item.PipePowerAdapter";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public PipePluggable createPipePluggable(IPipe pipe, ForgeDirection side, ItemStack stack) {
/* 39 */     return new PowerAdapterPluggable();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pluggable\ItemPowerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */