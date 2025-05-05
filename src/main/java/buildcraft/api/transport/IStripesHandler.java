/*    */ package buildcraft.api.transport;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ public interface IStripesHandler
/*    */ {
/*    */   StripesHandlerType getType();
/*    */   
/*    */   boolean shouldHandle(ItemStack paramItemStack);
/*    */   
/*    */   boolean handle(World paramWorld, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection, ItemStack paramItemStack, EntityPlayer paramEntityPlayer, IStripesActivator paramIStripesActivator);
/*    */   
/*    */   public enum StripesHandlerType
/*    */   {
/* 18 */     ITEM_USE,
/* 19 */     BLOCK_BREAK;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\transport\IStripesHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */