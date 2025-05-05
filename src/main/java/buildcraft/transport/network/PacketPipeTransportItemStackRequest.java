/*    */ package buildcraft.transport.network;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.core.lib.network.Packet;
/*    */ import buildcraft.transport.TravelingItem;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
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
/*    */ public class PacketPipeTransportItemStackRequest
/*    */   extends Packet
/*    */ {
/*    */   public int travelerID;
/*    */   TravelingItem item;
/*    */   
/*    */   public PacketPipeTransportItemStackRequest() {}
/*    */   
/*    */   public PacketPipeTransportItemStackRequest(int travelerID) {
/* 30 */     this.travelerID = travelerID;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 35 */     data.writeShort(this.travelerID);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 40 */     this.travelerID = data.readShort();
/* 41 */     TravelingItem.TravelingItemCache cache = TravelingItem.serverCache;
/* 42 */     this.item = cache.get(this.travelerID);
/*    */   }
/*    */   
/*    */   public void sendDataToPlayer(EntityPlayer player) {
/* 46 */     if (this.item != null) {
/* 47 */       BuildCraftTransport.instance.sendToPlayer(player, new PacketPipeTransportItemStack(this.travelerID, this.item
/*    */ 
/*    */             
/* 50 */             .getItemStack()));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getID() {
/* 56 */     return 5;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\network\PacketPipeTransportItemStackRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */