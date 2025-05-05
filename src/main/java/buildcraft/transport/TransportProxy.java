/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.core.CompatHooks;
/*    */ import cpw.mods.fml.common.SidedProxy;
/*    */ import cpw.mods.fml.common.registry.GameRegistry;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
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
/*    */ public class TransportProxy
/*    */ {
/*    */   @SidedProxy(clientSide = "buildcraft.transport.TransportProxyClient", serverSide = "buildcraft.transport.TransportProxy")
/*    */   public static TransportProxy proxy;
/* 24 */   public static int pipeModel = -1;
/*    */ 
/*    */   
/*    */   public void registerTileEntities() {
/* 28 */     GameRegistry.registerTileEntityWithAlternatives(CompatHooks.INSTANCE.getTile(TileGenericPipe.class), "net.minecraft.src.buildcraft.transport.GenericPipe", new String[] { "net.minecraft.src.buildcraft.GenericPipe", "net.minecraft.src.buildcraft.transport.TileGenericPipe" });
/* 29 */     GameRegistry.registerTileEntity(CompatHooks.INSTANCE.getTile(TileFilteredBuffer.class), "net.minecraft.src.buildcraft.transport.TileFilteredBuffer");
/*    */   }
/*    */   
/*    */   public void registerRenderers() {}
/*    */   
/*    */   public void setIconProviderFromPipe(ItemPipe item, Pipe<?> dummyPipe) {}
/*    */   
/*    */   public void obsidianPipePickup(World world, EntityItem item, TileEntity tile) {}
/*    */   
/*    */   public void clearDisplayList(int displayList) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\TransportProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */