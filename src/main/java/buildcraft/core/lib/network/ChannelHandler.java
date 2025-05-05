/*     */ package buildcraft.core.lib.network;
/*     */ 
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import cpw.mods.fml.common.FMLLog;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import cpw.mods.fml.common.network.internal.FMLProxyPacket;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import gnu.trove.map.hash.TByteObjectHashMap;
/*     */ import gnu.trove.map.hash.TObjectByteHashMap;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageCodec;
/*     */ import io.netty.util.AttributeKey;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.MarkerManager;
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
/*     */ public final class ChannelHandler
/*     */   extends MessageToMessageCodec<FMLProxyPacket, Packet>
/*     */ {
/*     */   private static final class DiscriminatorData
/*     */   {
/*     */     private final Class<? extends Packet> discriminator;
/*     */     private final PacketSide side;
/*     */     
/*     */     DiscriminatorData(Class<? extends Packet> discriminator, PacketSide side) {
/*  48 */       this.discriminator = discriminator;
/*  49 */       this.side = side;
/*     */     }
/*     */     
/*     */     public Class<? extends Packet> getDiscriminator() {
/*  53 */       return this.discriminator;
/*     */     }
/*     */     
/*     */     public PacketSide getSide() {
/*  57 */       return this.side;
/*     */     }
/*     */   }
/*     */   
/*  61 */   public static final Marker SUSPICIOUS_PACKETS = MarkerManager.getMarker("SuspiciousPackets");
/*  62 */   public static final AttributeKey<ThreadLocal<WeakReference<FMLProxyPacket>>> INBOUNDPACKETTRACKER = new AttributeKey("bc:inboundpacket");
/*  63 */   private TByteObjectHashMap<DiscriminatorData> discriminators = new TByteObjectHashMap();
/*  64 */   private TObjectByteHashMap<Class<? extends Packet>> types = new TObjectByteHashMap();
/*     */   
/*     */   private int maxDiscriminator;
/*     */   
/*     */   public ChannelHandler() {
/*  69 */     addDiscriminator(0, (Class)PacketTileUpdate.class, PacketSide.CLIENT_ONLY);
/*  70 */     addDiscriminator(1, (Class)PacketTileState.class, PacketSide.CLIENT_ONLY);
/*  71 */     addDiscriminator(2, (Class)PacketNBT.class, PacketSide.BOTH_SIDES);
/*  72 */     addDiscriminator(4, (Class)PacketGuiReturn.class, PacketSide.SERVER_ONLY);
/*  73 */     addDiscriminator(5, (Class)PacketGuiWidget.class, PacketSide.CLIENT_ONLY);
/*  74 */     addDiscriminator(7, (Class)PacketCommand.class, PacketSide.BOTH_SIDES);
/*  75 */     addDiscriminator(8, (Class)PacketEntityUpdate.class, PacketSide.CLIENT_ONLY);
/*  76 */     this.maxDiscriminator = 9;
/*     */   }
/*     */   
/*     */   public byte getDiscriminator(Class<? extends Packet> clazz) {
/*  80 */     return this.types.get(clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/*  85 */     super.handlerAdded(ctx);
/*  86 */     ctx.attr(INBOUNDPACKETTRACKER).set(new ThreadLocal());
/*     */   }
/*     */   
/*     */   public ChannelHandler addDiscriminator(int discriminator, Class<? extends Packet> type) {
/*  90 */     return addDiscriminator(discriminator, type, PacketSide.BOTH_SIDES);
/*     */   }
/*     */   
/*     */   public ChannelHandler addDiscriminator(int discriminator, Class<? extends Packet> packetType, PacketSide packetSide) {
/*  94 */     this.discriminators.put((byte)discriminator, new DiscriminatorData(packetType, packetSide));
/*  95 */     this.types.put(packetType, (byte)discriminator);
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   protected void logSuspiciousPacketWrongSide(EntityPlayer player, String packetType) {
/* 100 */     BCLog.logger.info(SUSPICIOUS_PACKETS, "Player {} tried to send packet of type {} to invalid side {}. This could be a false warning due to custom mod/addon interference, or an indicator of hacking/cheating activity.", new Object[] { player
/*     */ 
/*     */           
/* 103 */           .func_146103_bH(), packetType, (player instanceof net.minecraft.entity.player.EntityPlayerMP) ? Side.SERVER : Side.CLIENT });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void logSuspiciousPacketWrongDiscriminator(EntityPlayer player, int typeId) {
/* 109 */     BCLog.logger.info(SUSPICIOUS_PACKETS, "Player {} tried to send packet of invalid type {}. This could be a false warning due to custom mod/addon interference, or an indicator of hacking/cheating activity.", new Object[] { player
/*     */ 
/*     */           
/* 112 */           .func_146103_bH(), 
/* 113 */           Integer.valueOf(typeId) });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
/* 118 */     ByteBuf buffer = Unpooled.buffer();
/* 119 */     Class<? extends Packet> clazz = (Class)msg.getClass();
/* 120 */     byte discriminator = this.types.get(clazz);
/* 121 */     buffer.writeByte(discriminator);
/* 122 */     msg.writeData(buffer);
/* 123 */     FMLProxyPacket proxy = new FMLProxyPacket(buffer.copy(), (String)ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
/* 124 */     WeakReference<FMLProxyPacket> ref = ((ThreadLocal<WeakReference<FMLProxyPacket>>)ctx.attr(INBOUNDPACKETTRACKER).get()).get();
/* 125 */     FMLProxyPacket old = (ref == null) ? null : ref.get();
/* 126 */     if (old != null) {
/* 127 */       proxy.setDispatcher(old.getDispatcher());
/*     */     }
/* 129 */     out.add(proxy);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception {
/* 134 */     testMessageValidity(msg);
/* 135 */     ByteBuf payload = msg.payload();
/* 136 */     byte discriminator = payload.readByte();
/* 137 */     DiscriminatorData data = (DiscriminatorData)this.discriminators.get(discriminator);
/* 138 */     if (data == null) {
/* 139 */       INetHandler handler = (INetHandler)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
/* 140 */       EntityPlayer player = CoreProxy.proxy.getPlayerFromNetHandler(handler);
/* 141 */       logSuspiciousPacketWrongDiscriminator(player, discriminator);
/*     */       return;
/*     */     } 
/* 144 */     PacketSide expectedSide = (ctx.channel().attr(NetworkRegistry.CHANNEL_SOURCE).get() == Side.CLIENT) ? PacketSide.CLIENT_ONLY : PacketSide.SERVER_ONLY;
/*     */ 
/*     */     
/* 147 */     if (!data.getSide().contains(expectedSide)) {
/* 148 */       INetHandler handler = (INetHandler)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
/* 149 */       EntityPlayer player = CoreProxy.proxy.getPlayerFromNetHandler(handler);
/* 150 */       logSuspiciousPacketWrongSide(player, data.getDiscriminator().getSimpleName());
/*     */     } else {
/* 152 */       Packet newMsg = data.getDiscriminator().newInstance();
/* 153 */       ((ThreadLocal)ctx.attr(INBOUNDPACKETTRACKER).get()).set(new WeakReference<FMLProxyPacket>(msg));
/* 154 */       newMsg.readData(payload.slice());
/* 155 */       out.add(newMsg);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void testMessageValidity(FMLProxyPacket msg) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 169 */     FMLLog.log(Level.ERROR, cause, "BC ChannelHandler exception caught", new Object[0]);
/* 170 */     super.exceptionCaught(ctx, cause);
/*     */   }
/*     */   
/*     */   public void registerPacketType(Class<? extends Packet> packetType) {
/* 174 */     registerPacketType(packetType, PacketSide.BOTH_SIDES);
/*     */   }
/*     */   
/*     */   public void registerPacketType(Class<? extends Packet> packetType, PacketSide packetSide) {
/* 178 */     addDiscriminator(this.maxDiscriminator++, packetType, packetSide);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\ChannelHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */