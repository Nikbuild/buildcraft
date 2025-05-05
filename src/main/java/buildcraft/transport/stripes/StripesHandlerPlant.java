/*    */ package buildcraft.transport.stripes;
/*    */ 
/*    */ import buildcraft.api.crops.CropManager;
/*    */ import buildcraft.api.transport.IStripesActivator;
/*    */ import buildcraft.api.transport.IStripesHandler;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ public class StripesHandlerPlant
/*    */   implements IStripesHandler
/*    */ {
/*    */   public IStripesHandler.StripesHandlerType getType() {
/* 16 */     return IStripesHandler.StripesHandlerType.ITEM_USE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldHandle(ItemStack stack) {
/* 21 */     return CropManager.isSeed(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handle(World world, int x, int y, int z, ForgeDirection direction, ItemStack stack, EntityPlayer player, IStripesActivator activator) {
/* 27 */     if (CropManager.canSustainPlant(world, stack, x, y - 1, z) && 
/* 28 */       CropManager.plantCrop(world, player, stack, x, y - 1, z)) {
/* 29 */       if (stack.field_77994_a > 0) {
/* 30 */         activator.sendItem(stack, direction.getOpposite());
/*    */       }
/* 32 */       return true;
/*    */     } 
/*    */     
/* 35 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\stripes\StripesHandlerPlant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */