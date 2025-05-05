/*    */ package buildcraft.core.lib.network;
/*    */ 
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
/*    */ public abstract class PacketCoordinates
/*    */   extends Packet
/*    */ {
/*    */   public int posX;
/*    */   public int posY;
/*    */   public int posZ;
/*    */   private int id;
/*    */   
/*    */   public PacketCoordinates() {}
/*    */   
/*    */   public PacketCoordinates(int id, int x, int y, int z) {
/* 25 */     this.id = id;
/* 26 */     this.posX = x;
/* 27 */     this.posY = y;
/* 28 */     this.posZ = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 33 */     data.writeByte(this.id);
/* 34 */     data.writeInt(this.posX);
/* 35 */     data.writeShort(this.posY);
/* 36 */     data.writeInt(this.posZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 41 */     this.id = data.readByte();
/* 42 */     this.posX = data.readInt();
/* 43 */     this.posY = data.readShort();
/* 44 */     this.posZ = data.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getID() {
/* 49 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\PacketCoordinates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */