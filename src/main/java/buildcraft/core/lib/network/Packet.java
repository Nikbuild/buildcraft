/*    */ package buildcraft.core.lib.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ public abstract class Packet {
/*    */   protected boolean isChunkDataPacket = false;
/*    */   
/*    */   public abstract int getID();
/*    */   
/*    */   public abstract void readData(ByteBuf paramByteBuf);
/*    */   
/*    */   public abstract void writeData(ByteBuf paramByteBuf);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\Packet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */