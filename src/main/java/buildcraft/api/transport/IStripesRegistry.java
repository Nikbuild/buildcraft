/*    */ package buildcraft.api.transport;
/*    */ 
/*    */ import buildcraft.api.core.EnumHandlerPriority;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public interface IStripesRegistry
/*    */ {
/*    */   default void addHandler(IStripesHandlerItem handler) {
/* 14 */     addHandler(handler, EnumHandlerPriority.NORMAL);
/*    */   }
/*    */ 
/*    */   
/*    */   void addHandler(IStripesHandlerItem paramIStripesHandlerItem, EnumHandlerPriority paramEnumHandlerPriority);
/*    */   
/*    */   default void addHandler(IStripesHandlerBlock handler) {
/* 21 */     addHandler(handler, EnumHandlerPriority.NORMAL);
/*    */   }
/*    */   
/*    */   void addHandler(IStripesHandlerBlock paramIStripesHandlerBlock, EnumHandlerPriority paramEnumHandlerPriority);
/*    */   
/*    */   boolean handleItem(World paramWorld, BlockPos paramBlockPos, EnumFacing paramEnumFacing, ItemStack paramItemStack, EntityPlayer paramEntityPlayer, IStripesActivator paramIStripesActivator);
/*    */   
/*    */   boolean handleBlock(World paramWorld, BlockPos paramBlockPos, EnumFacing paramEnumFacing, EntityPlayer paramEntityPlayer, IStripesActivator paramIStripesActivator);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\IStripesRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */