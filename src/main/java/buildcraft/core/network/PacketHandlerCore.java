/*    */ package buildcraft.core.network;
/*    */ 
/*    */ import buildcraft.core.lib.network.Packet;
/*    */ import buildcraft.core.lib.network.PacketHandler;
/*    */ import buildcraft.core.proxy.CoreProxy;
/*    */ import buildcraft.core.tablet.PacketTabletMessage;
/*    */ import buildcraft.core.tablet.TabletBase;
/*    */ import buildcraft.core.tablet.TabletServer;
/*    */ import buildcraft.core.tablet.manager.TabletManagerClient;
/*    */ import buildcraft.core.tablet.manager.TabletManagerServer;
/*    */ import cpw.mods.fml.common.network.NetworkRegistry;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.INetHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketHandlerCore
/*    */   extends PacketHandler
/*    */ {
/*    */   protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
/* 24 */     super.channelRead0(ctx, packet);
/* 25 */     INetHandler netHandler = (INetHandler)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
/* 26 */     EntityPlayer player = CoreProxy.proxy.getPlayerFromNetHandler(netHandler);
/*    */     
/* 28 */     switch (packet.getID()) {
/*    */       case 40:
/* 30 */         if (netHandler instanceof net.minecraft.network.NetHandlerPlayServer) {
/* 31 */           handleTabletServer(player, (PacketTabletMessage)packet); break;
/*    */         } 
/* 33 */         handleTabletClient((PacketTabletMessage)packet);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void handleTabletClient(PacketTabletMessage packet) {
/* 41 */     TabletBase tablet = TabletManagerClient.INSTANCE.get().getTablet();
/* 42 */     tablet.receiveMessage(packet.getTag());
/*    */   }
/*    */   
/*    */   private void handleTabletServer(EntityPlayer player, PacketTabletMessage packet) {
/* 46 */     TabletServer tabletServer = TabletManagerServer.INSTANCE.get(player);
/* 47 */     tabletServer.receiveMessage(packet.getTag());
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\network\PacketHandlerCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */