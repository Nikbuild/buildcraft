/*     */ package buildcraft.transport.network;
/*     */ 
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.PacketHandler;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeTransportItems;
/*     */ import buildcraft.transport.PipeTransportPower;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Sharable
/*     */ public class PacketHandlerTransport
/*     */   extends PacketHandler
/*     */ {
/*     */   protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
/*  43 */     super.channelRead0(ctx, packet);
/*     */     try {
/*  45 */       INetHandler netHandler = (INetHandler)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
/*  46 */       EntityPlayer player = CoreProxy.proxy.getPlayerFromNetHandler(netHandler);
/*     */       
/*  48 */       int packetID = packet.getID();
/*     */       
/*  50 */       switch (packetID) {
/*     */         case 4:
/*  52 */           onPacketPower(player, (PacketPowerUpdate)packet);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/*  58 */           onPipeTravelerUpdate(player, (PacketPipeTransportTraveler)packet);
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 5:
/*  70 */           ((PacketPipeTransportItemStackRequest)packet).sendDataToPlayer(player);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*  75 */     } catch (Exception ex) {
/*  76 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onPipeTravelerUpdate(EntityPlayer player, PacketPipeTransportTraveler packet) {
/*  86 */     World world = player.field_70170_p;
/*     */     
/*  88 */     if (!world.func_72899_e(packet.posX, packet.posY, packet.posZ)) {
/*     */       return;
/*     */     }
/*     */     
/*  92 */     TileEntity entity = world.func_147438_o(packet.posX, packet.posY, packet.posZ);
/*  93 */     if (!(entity instanceof IPipeTile)) {
/*     */       return;
/*     */     }
/*     */     
/*  97 */     IPipeTile pipe = (IPipeTile)entity;
/*  98 */     if (pipe.getPipe() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 102 */     if (!(((Pipe)pipe.getPipe()).transport instanceof PipeTransportItems)) {
/*     */       return;
/*     */     }
/*     */     
/* 106 */     ((PipeTransportItems)((Pipe)pipe.getPipe()).transport).handleTravelerPacket(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onPacketPower(EntityPlayer player, PacketPowerUpdate packetPower) {
/* 115 */     World world = player.field_70170_p;
/* 116 */     if (!world.func_72899_e(packetPower.posX, packetPower.posY, packetPower.posZ)) {
/*     */       return;
/*     */     }
/*     */     
/* 120 */     TileEntity entity = world.func_147438_o(packetPower.posX, packetPower.posY, packetPower.posZ);
/* 121 */     if (!(entity instanceof IPipeTile)) {
/*     */       return;
/*     */     }
/*     */     
/* 125 */     IPipeTile pipe = (IPipeTile)entity;
/* 126 */     if (pipe.getPipe() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 130 */     if (!(((Pipe)pipe.getPipe()).transport instanceof PipeTransportPower)) {
/*     */       return;
/*     */     }
/*     */     
/* 134 */     ((PipeTransportPower)((Pipe)pipe.getPipe()).transport).handlePowerPacket(packetPower);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\network\PacketHandlerTransport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */