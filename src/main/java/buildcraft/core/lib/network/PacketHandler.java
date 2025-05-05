/*     */ package buildcraft.core.lib.network;
/*     */ 
/*     */ import buildcraft.api.core.ISerializable;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.SimpleChannelInboundHandler;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.entity.Entity;
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
/*     */ @Sharable
/*     */ public class PacketHandler
/*     */   extends SimpleChannelInboundHandler<Packet>
/*     */ {
/*     */   private void onTileUpdate(EntityPlayer player, PacketTileUpdate packet) throws IOException {
/*  37 */     World world = player.field_70170_p;
/*     */     
/*  39 */     if (!packet.targetExists(world)) {
/*     */       return;
/*     */     }
/*     */     
/*  43 */     TileEntity entity = packet.getTarget(world);
/*     */     
/*  45 */     if (!(entity instanceof ISerializable)) {
/*     */       return;
/*     */     }
/*     */     
/*  49 */     ISerializable tile = (ISerializable)entity;
/*  50 */     tile.readData(packet.stream);
/*     */   }
/*     */   
/*     */   private void onEntityUpdate(EntityPlayer player, PacketEntityUpdate packet) throws IOException {
/*  54 */     World world = player.field_70170_p;
/*     */     
/*  56 */     if (!packet.targetExists(world)) {
/*     */       return;
/*     */     }
/*     */     
/*  60 */     Entity entity = packet.getTarget(world);
/*     */     
/*  62 */     if (!(entity instanceof ISerializable)) {
/*     */       return;
/*     */     }
/*     */     
/*  66 */     ISerializable payload = (ISerializable)entity;
/*  67 */     payload.readData(packet.stream);
/*     */   } protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
/*     */     try {
/*     */       PacketTileState pkt;
/*     */       World world;
/*     */       TileEntity tile;
/*  73 */       INetHandler netHandler = (INetHandler)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
/*  74 */       EntityPlayer player = CoreProxy.proxy.getPlayerFromNetHandler(netHandler);
/*     */       
/*  76 */       int packetID = packet.getID();
/*     */       
/*  78 */       switch (packetID) {
/*     */         case 0:
/*  80 */           onTileUpdate(player, (PacketTileUpdate)packet);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 7:
/*  85 */           onEntityUpdate(player, (PacketEntityUpdate)packet);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 1:
/*  90 */           ((PacketCommand)packet).handle(player);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 100:
/*  95 */           pkt = (PacketTileState)packet;
/*  96 */           world = player.field_70170_p;
/*     */           
/*  98 */           tile = world.func_147438_o(pkt.posX, pkt.posY, pkt.posZ);
/*     */           
/* 100 */           if (tile instanceof ISyncedTile) {
/* 101 */             pkt.applyStates((ISyncedTile)tile);
/*     */           }
/*     */           break;
/*     */       } 
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
/* 117 */     } catch (Exception ex) {
/* 118 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\PacketHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */