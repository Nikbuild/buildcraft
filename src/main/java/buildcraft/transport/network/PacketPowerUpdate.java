/*    */ package buildcraft.transport.network;
/*    */ 
/*    */ import buildcraft.core.lib.network.PacketCoordinates;
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
/*    */ 
/*    */ public class PacketPowerUpdate
/*    */   extends PacketCoordinates
/*    */ {
/*    */   public boolean overload;
/*    */   public short[] displayPower;
/*    */   
/*    */   public PacketPowerUpdate() {}
/*    */   
/*    */   public PacketPowerUpdate(int x, int y, int z) {
/* 25 */     super(4, x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 30 */     super.readData(data);
/* 31 */     this.displayPower = new short[] { 0, 0, 0, 0, 0, 0 };
/* 32 */     this.overload = data.readBoolean();
/* 33 */     for (int i = 0; i < this.displayPower.length; i++) {
/* 34 */       this.displayPower[i] = data.readShort();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 40 */     super.writeData(data);
/* 41 */     data.writeBoolean(this.overload);
/* 42 */     for (short element : this.displayPower)
/* 43 */       data.writeShort(element); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\network\PacketPowerUpdate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */