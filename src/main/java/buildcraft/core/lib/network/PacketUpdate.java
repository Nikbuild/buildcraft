/*    */ package buildcraft.core.lib.network;
/*    */ 
/*    */ import buildcraft.api.core.ISerializable;
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ public abstract class PacketUpdate
/*    */   extends Packet
/*    */ {
/*    */   public ByteBuf stream;
/*    */   public ISerializable payload;
/*    */   private int packetId;
/*    */   
/*    */   public PacketUpdate() {}
/*    */   
/*    */   public PacketUpdate(int packetId, ISerializable payload) {
/* 25 */     this(packetId);
/*    */     
/* 27 */     this.payload = payload;
/*    */   }
/*    */   
/*    */   public PacketUpdate(int packetId) {
/* 31 */     this.packetId = packetId;
/* 32 */     this.isChunkDataPacket = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 37 */     data.writeByte(this.packetId);
/* 38 */     writeIdentificationData(data);
/*    */     
/* 40 */     if (this.payload != null) {
/* 41 */       this.payload.writeData(data);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void writeIdentificationData(ByteBuf paramByteBuf);
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 49 */     this.packetId = data.readByte();
/* 50 */     readIdentificationData(data);
/*    */     
/* 52 */     this.stream = data;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void readIdentificationData(ByteBuf paramByteBuf);
/*    */   
/*    */   public int getID() {
/* 59 */     return this.packetId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\PacketUpdate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */