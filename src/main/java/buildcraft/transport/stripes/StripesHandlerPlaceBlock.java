/*    */ package buildcraft.transport.stripes;
/*    */ 
/*    */ import buildcraft.api.transport.IStripesActivator;
/*    */ import buildcraft.api.transport.IStripesHandler;
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
/*    */ public class StripesHandlerPlaceBlock
/*    */   implements IStripesHandler
/*    */ {
/*    */   public IStripesHandler.StripesHandlerType getType() {
/* 25 */     return IStripesHandler.StripesHandlerType.ITEM_USE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldHandle(ItemStack stack) {
/* 30 */     return stack.func_77973_b() instanceof net.minecraft.item.ItemBlock;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handle(World world, int x, int y, int z, ForgeDirection direction, ItemStack stack, EntityPlayer player, IStripesActivator activator) {
/* 37 */     if (!world.func_147437_c(x, y, z)) {
/* 38 */       return false;
/*    */     }
/* 40 */     if (stack.func_77943_a(player, world, x, y, z, direction.ordinal(), 0.0F, 0.0F, 0.0F)) {
/*    */       
/* 42 */       if (stack.field_77994_a > 0) {
/* 43 */         activator.sendItem(stack, direction.getOpposite());
/*    */       }
/*    */       
/* 46 */       return true;
/*    */     } 
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\stripes\StripesHandlerPlaceBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */