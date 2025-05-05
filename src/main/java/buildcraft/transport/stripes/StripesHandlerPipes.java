/*    */ package buildcraft.transport.stripes;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.core.Position;
/*    */ import buildcraft.api.transport.IStripesActivator;
/*    */ import buildcraft.api.transport.IStripesHandler;
/*    */ import buildcraft.core.proxy.CoreProxy;
/*    */ import buildcraft.transport.BlockGenericPipe;
/*    */ import buildcraft.transport.Pipe;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldServer;
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
/*    */ public class StripesHandlerPipes
/*    */   implements IStripesHandler
/*    */ {
/*    */   public IStripesHandler.StripesHandlerType getType() {
/* 32 */     return IStripesHandler.StripesHandlerType.ITEM_USE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldHandle(ItemStack stack) {
/* 37 */     return stack.func_77973_b() instanceof buildcraft.transport.ItemPipe;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handle(World world, int x, int y, int z, ForgeDirection direction, ItemStack stack, EntityPlayer player, IStripesActivator activator) {
/* 45 */     if (!(stack.func_77973_b() instanceof buildcraft.transport.ItemPipe) || stack.func_77973_b() == BuildCraftTransport.pipeItemsStripes) {
/* 46 */       return false;
/*    */     }
/*    */     
/* 49 */     Position p = new Position(x, y, z, direction);
/* 50 */     p.moveBackwards(1.0D);
/*    */     
/* 52 */     Pipe<?> pipe = BlockGenericPipe.createPipe(stack.func_77973_b());
/*    */     
/* 54 */     if (pipe.transport instanceof buildcraft.transport.PipeTransportItems) {
/*    */       
/* 56 */       BuildCraftTransport.pipeExtensionListener.requestPipeExtension(stack, world, (int)p.x, (int)p.y, (int)p.z, direction, activator);
/*    */     } else {
/*    */       
/* 59 */       stack.func_77973_b().func_77648_a(stack, CoreProxy.proxy
/* 60 */           .getBuildCraftPlayer((WorldServer)world, (int)p.x, (int)p.y, (int)p.z).get(), world, x, y, z, 1, 0.0F, 0.0F, 0.0F);
/*    */     } 
/*    */     
/* 63 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\stripes\StripesHandlerPipes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */