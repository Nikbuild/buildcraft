/*    */ package buildcraft.core.lib.network;
/*    */ 
/*    */ import buildcraft.api.core.ISerializable;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import java.io.IOException;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
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
/*    */ public class PacketTileState
/*    */   extends PacketCoordinates
/*    */ {
/*    */   private ByteBuf state;
/*    */   
/*    */   private class StateWithId
/*    */   {
/*    */     public byte stateId;
/*    */     public ISerializable state;
/*    */     
/*    */     public StateWithId(byte stateId, ISerializable state) {
/* 30 */       this.stateId = stateId;
/* 31 */       this.state = state;
/*    */     }
/*    */   }
/*    */   
/* 35 */   private List<StateWithId> stateList = new LinkedList<StateWithId>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PacketTileState() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PacketTileState(int x, int y, int z) {
/* 49 */     super(100, x, y, z);
/* 50 */     this.isChunkDataPacket = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getID() {
/* 55 */     return 100;
/*    */   }
/*    */   
/*    */   public void applyStates(ISyncedTile tile) throws IOException {
/* 59 */     byte stateCount = this.state.readByte();
/* 60 */     for (int i = 0; i < stateCount; i++) {
/* 61 */       byte stateId = this.state.readByte();
/* 62 */       tile.getStateInstance(stateId).readData(this.state);
/* 63 */       tile.afterStateUpdated(stateId);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void addStateForSerialization(byte stateId, ISerializable state) {
/* 68 */     this.stateList.add(new StateWithId(stateId, state));
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 73 */     super.writeData(data);
/*    */     
/* 75 */     ByteBuf tmpState = Unpooled.buffer();
/*    */     
/* 77 */     tmpState.writeByte(this.stateList.size());
/* 78 */     for (StateWithId stateWithId : this.stateList) {
/* 79 */       tmpState.writeByte(stateWithId.stateId);
/* 80 */       stateWithId.state.writeData(tmpState);
/*    */     } 
/*    */     
/* 83 */     data.writeShort((short)tmpState.readableBytes());
/* 84 */     data.writeBytes(tmpState.readBytes(tmpState.readableBytes()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 89 */     super.readData(data);
/*    */     
/* 91 */     this.state = Unpooled.buffer();
/* 92 */     int length = data.readUnsignedShort();
/* 93 */     this.state.writeBytes(data.readBytes(length));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\PacketTileState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */