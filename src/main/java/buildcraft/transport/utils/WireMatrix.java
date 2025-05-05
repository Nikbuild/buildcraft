/*    */ package buildcraft.transport.utils;
/*    */ 
/*    */ import buildcraft.api.transport.PipeWire;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.BitSet;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
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
/*    */ public class WireMatrix
/*    */ {
/* 20 */   private final BitSet hasWire = new BitSet((PipeWire.values()).length);
/* 21 */   private final BitSetCodec bitSetCodec = new BitSetCodec();
/*    */   
/* 23 */   private final ConnectionMatrix[] wires = new ConnectionMatrix[(PipeWire.values()).length];
/* 24 */   private final int[] wireIconIndex = new int[(PipeWire.values()).length];
/*    */   
/*    */   private boolean dirty = false;
/*    */   
/*    */   public WireMatrix() {
/* 29 */     for (int i = 0; i < (PipeWire.values()).length; i++) {
/* 30 */       this.wires[i] = new ConnectionMatrix();
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean hasWire(PipeWire color) {
/* 35 */     return this.hasWire.get(color.ordinal());
/*    */   }
/*    */   
/*    */   public void setWire(PipeWire color, boolean value) {
/* 39 */     if (this.hasWire.get(color.ordinal()) != value) {
/* 40 */       this.hasWire.set(color.ordinal(), value);
/* 41 */       this.dirty = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isWireConnected(PipeWire color, ForgeDirection direction) {
/* 46 */     return this.wires[color.ordinal()].isConnected(direction);
/*    */   }
/*    */   
/*    */   public void setWireConnected(PipeWire color, ForgeDirection direction, boolean value) {
/* 50 */     this.wires[color.ordinal()].setConnected(direction, value);
/*    */   }
/*    */   
/*    */   public int getWireIconIndex(PipeWire color) {
/* 54 */     return this.wireIconIndex[color.ordinal()];
/*    */   }
/*    */   
/*    */   public void setWireIndex(PipeWire color, int value) {
/* 58 */     if (this.wireIconIndex[color.ordinal()] != value) {
/* 59 */       this.wireIconIndex[color.ordinal()] = value;
/* 60 */       this.dirty = true;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDirty() {
/* 66 */     for (int i = 0; i < (PipeWire.values()).length; i++) {
/* 67 */       if (this.wires[i].isDirty()) {
/* 68 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 72 */     return this.dirty;
/*    */   }
/*    */   
/*    */   public void clean() {
/* 76 */     for (int i = 0; i < (PipeWire.values()).length; i++) {
/* 77 */       this.wires[i].clean();
/*    */     }
/* 79 */     this.dirty = false;
/*    */   }
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 83 */     data.writeByte(this.bitSetCodec.encode(this.hasWire));
/*    */     
/* 85 */     for (int i = 0; i < (PipeWire.values()).length; i++) {
/* 86 */       this.wires[i].writeData(data);
/* 87 */       data.writeByte(this.wireIconIndex[i]);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 92 */     this.bitSetCodec.decode(data.readByte(), this.hasWire);
/* 93 */     for (int i = 0; i < (PipeWire.values()).length; i++) {
/* 94 */       this.wires[i].readData(data);
/* 95 */       this.wireIconIndex[i] = data.readUnsignedByte();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transpor\\utils\WireMatrix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */