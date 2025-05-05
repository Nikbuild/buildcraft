/*     */ package buildcraft;
/*     */ 
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.core.DefaultProps;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.utils.ThreadSafeUtils;
/*     */ import cpw.mods.fml.common.network.FMLEmbeddedChannel;
/*     */ import cpw.mods.fml.common.network.FMLOutboundHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
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
/*     */ public class BuildCraftMod
/*     */ {
/*  39 */   private static PacketSender sender = new PacketSender();
/*  40 */   private static Thread senderThread = new Thread(sender);
/*     */   public EnumMap<Side, FMLEmbeddedChannel> channels;
/*     */   
/*     */   static abstract class SendRequest
/*     */   {
/*     */     final Packet packet;
/*     */     final BuildCraftMod source;
/*     */     
/*     */     SendRequest(BuildCraftMod source, Packet packet) {
/*  49 */       this.packet = packet;
/*  50 */       this.source = source;
/*     */     }
/*     */     
/*     */     abstract boolean isValid(EntityPlayer param1EntityPlayer);
/*     */   }
/*     */   
/*     */   class PlayerSendRequest extends SendRequest {
/*     */     EntityPlayer player;
/*     */     
/*     */     PlayerSendRequest(BuildCraftMod source, Packet packet, EntityPlayer player) {
/*  60 */       super(packet);
/*  61 */       this.player = player;
/*     */     }
/*     */     
/*     */     boolean isValid(EntityPlayer player) {
/*  65 */       return this.player.equals(player);
/*     */     }
/*     */   }
/*     */   
/*     */   class EntitySendRequest extends SendRequest {
/*     */     Entity entity;
/*     */     
/*     */     EntitySendRequest(BuildCraftMod source, Packet packet, Entity entity) {
/*  73 */       super(packet);
/*  74 */       this.entity = entity;
/*     */     }
/*     */     
/*     */     boolean isValid(EntityPlayer player) {
/*  78 */       if (player.field_70170_p.equals(this.entity.field_70170_p)) {
/*  79 */         if (player.field_70170_p instanceof WorldServer) {
/*  80 */           return ((WorldServer)player.field_70170_p).func_73039_n().getTrackingPlayers(this.entity).contains(player);
/*     */         }
/*  82 */         return true;
/*     */       } 
/*     */       
/*  85 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   class WorldSendRequest
/*     */     extends SendRequest {
/*     */     final int dimensionId;
/*     */     
/*     */     WorldSendRequest(BuildCraftMod source, Packet packet, int dimensionId) {
/*  94 */       super(packet);
/*  95 */       this.dimensionId = dimensionId;
/*     */     }
/*     */     
/*     */     boolean isValid(EntityPlayer player) {
/*  99 */       return (player.field_70170_p.field_73011_w.field_76574_g == this.dimensionId);
/*     */     }
/*     */   }
/*     */   
/*     */   class LocationSendRequest extends SendRequest {
/*     */     final int dimensionId;
/*     */     final int x;
/*     */     
/*     */     LocationSendRequest(BuildCraftMod source, Packet packet, int dimensionId, int x, int y, int z, int md) {
/* 108 */       super(packet);
/* 109 */       this.dimensionId = dimensionId;
/* 110 */       this.x = x;
/* 111 */       this.y = y;
/* 112 */       this.z = z;
/* 113 */       this.md = md * md;
/*     */     }
/*     */     final int y; final int z; final int md;
/*     */     boolean isValid(EntityPlayer player) {
/* 117 */       return (this.dimensionId == player.field_70170_p.field_73011_w.field_76574_g && player
/* 118 */         .func_70092_e(this.x, this.y, this.z) <= this.md);
/*     */     }
/*     */   }
/*     */   
/*     */   static class PacketSender implements Runnable {
/* 123 */     private BlockingQueue<BuildCraftMod.SendRequest> packets = new LinkedBlockingQueue<BuildCraftMod.SendRequest>();
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       while (true) {
/*     */         try {
/* 129 */           BuildCraftMod.SendRequest r = this.packets.take();
/* 130 */           Packet p = ThreadSafeUtils.generatePacketFrom(r.packet, r.source.channels.get(Side.SERVER));
/* 131 */           List<EntityPlayerMP> playerList = (MinecraftServer.func_71276_C().func_71203_ab()).field_72404_b;
/* 132 */           for (EntityPlayerMP player : (EntityPlayerMP[])playerList.<EntityPlayerMP>toArray(new EntityPlayerMP[playerList.size()])) {
/* 133 */             if (r.isValid((EntityPlayer)player)) {
/* 134 */               NetHandlerPlayServer handler = player.field_71135_a;
/* 135 */               if (handler != null)
/*     */               
/*     */               { 
/*     */                 
/* 139 */                 NetworkManager manager = handler.field_147371_a;
/* 140 */                 if (manager != null && manager.func_150724_d())
/*     */                 {
/*     */ 
/*     */                   
/* 144 */                   manager.func_150725_a(p, new io.netty.util.concurrent.GenericFutureListener[0]); }  } 
/*     */             } 
/*     */           } 
/* 147 */         } catch (Exception e) {
/* 148 */           BCLog.logger.error("The BuildCraft packet sender thread raised an exception! Please report to GitHub.");
/* 149 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean add(BuildCraftMod.SendRequest r) {
/* 155 */       return this.packets.offer(r);
/*     */     }
/*     */   }
/*     */   
/*     */   static {
/* 160 */     senderThread.setDaemon(true);
/* 161 */     senderThread.setName("BuildCraft packet sender thread");
/* 162 */     senderThread.start();
/*     */   }
/*     */   
/*     */   public void sendToPlayers(Packet packet, World world, int x, int y, int z, int maxDistance) {
/* 166 */     sender.add(new LocationSendRequest(this, packet, world.field_73011_w.field_76574_g, x, y, z, maxDistance));
/*     */   }
/*     */   
/*     */   public void sendToPlayersNear(Packet packet, TileEntity tileEntity, int maxDistance) {
/* 170 */     sender.add(new LocationSendRequest(this, packet, (tileEntity.func_145831_w()).field_73011_w.field_76574_g, tileEntity.field_145851_c, tileEntity.field_145848_d, tileEntity.field_145849_e, maxDistance));
/*     */   }
/*     */   
/*     */   public void sendToPlayersNear(Packet packet, TileEntity tileEntity) {
/* 174 */     sendToPlayersNear(packet, tileEntity, DefaultProps.NETWORK_UPDATE_RANGE);
/*     */   }
/*     */   
/*     */   public void sendToWorld(Packet packet, World world) {
/* 178 */     sender.add(new WorldSendRequest(this, packet, world.field_73011_w.field_76574_g));
/*     */   }
/*     */   
/*     */   public void sendToEntity(Packet packet, Entity entity) {
/* 182 */     sender.add(new EntitySendRequest(this, packet, entity));
/*     */   }
/*     */   
/*     */   public void sendToPlayer(EntityPlayer entityplayer, Packet packet) {
/* 186 */     sender.add(new PlayerSendRequest(this, packet, entityplayer));
/*     */   }
/*     */   
/*     */   public void sendToServer(Packet packet) {
/*     */     try {
/* 191 */       ((FMLEmbeddedChannel)this.channels.get(Side.CLIENT)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
/* 192 */       ((FMLEmbeddedChannel)this.channels.get(Side.CLIENT)).writeOutbound(new Object[] { packet });
/* 193 */     } catch (Exception e) {
/* 194 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\BuildCraftMod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */