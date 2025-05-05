/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import buildcraft.core.lib.network.ChannelHandler;
/*    */ import buildcraft.core.lib.network.Packet;
/*    */ import cpw.mods.fml.common.network.FMLEmbeddedChannel;
/*    */ import cpw.mods.fml.common.network.NetworkRegistry;
/*    */ import cpw.mods.fml.common.network.internal.FMLProxyPacket;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.world.ChunkCoordIntPair;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ import net.minecraft.world.chunk.IChunkProvider;
/*    */ import net.minecraft.world.gen.ChunkProviderServer;
/*    */ 
/*    */ public final class ThreadSafeUtils
/*    */ {
/* 20 */   private static final ThreadLocal<Chunk> lastChunk = new ThreadLocal<Chunk>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Chunk getChunk(World world, int x, int z) {
/* 28 */     Chunk chunk = lastChunk.get();
/*    */     
/* 30 */     if (chunk != null) {
/* 31 */       if (chunk.field_76636_d) {
/* 32 */         if (chunk.field_76637_e == world && chunk.field_76635_g == x && chunk.field_76647_h == z) {
/* 33 */           return chunk;
/*    */         }
/*    */       } else {
/* 36 */         lastChunk.set(null);
/*    */       } 
/*    */     }
/*    */     
/* 40 */     IChunkProvider provider = world.func_72863_F();
/*    */     
/* 42 */     if (!Utils.CAULDRON_DETECTED && provider instanceof ChunkProviderServer) {
/*    */       
/* 44 */       chunk = (Chunk)((ChunkProviderServer)provider).field_73244_f.func_76164_a(ChunkCoordIntPair.func_77272_a(x, z));
/*    */     } else {
/* 46 */       chunk = provider.func_73149_a(x, z) ? provider.func_73154_d(x, z) : null;
/*    */     } 
/*    */     
/* 49 */     if (chunk != null) {
/* 50 */       lastChunk.set(chunk);
/*    */     }
/* 52 */     return chunk;
/*    */   }
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
/*    */   public static Packet generatePacketFrom(Packet packet, FMLEmbeddedChannel channel) {
/* 65 */     ByteBuf data = Unpooled.buffer();
/* 66 */     for (ChannelHandler h : channel.pipeline().toMap().values()) {
/* 67 */       if (h instanceof ChannelHandler) {
/* 68 */         data.writeByte(((ChannelHandler)h).getDiscriminator(packet.getClass()));
/*    */         break;
/*    */       } 
/*    */     } 
/* 72 */     packet.writeData(data);
/* 73 */     return (Packet)new FMLProxyPacket(data.copy(), (String)channel.attr(NetworkRegistry.FML_CHANNEL).get());
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\ThreadSafeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */