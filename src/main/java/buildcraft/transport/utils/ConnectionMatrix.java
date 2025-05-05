/*    */ package buildcraft.transport.utils;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ public class ConnectionMatrix
/*    */ {
/* 16 */   private int mask = 0;
/*    */ 
/*    */   
/*    */   private boolean dirty = false;
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isConnected(ForgeDirection direction) {
/* 24 */     return ((this.mask & 1 << direction.ordinal()) != 0);
/*    */   }
/*    */   
/*    */   public void setConnected(ForgeDirection direction, boolean value) {
/* 28 */     if (isConnected(direction) != value) {
/*    */       
/* 30 */       this.mask ^= 1 << direction.ordinal();
/* 31 */       this.dirty = true;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMask() {
/* 41 */     return this.mask;
/*    */   }
/*    */   
/*    */   public boolean isDirty() {
/* 45 */     return this.dirty;
/*    */   }
/*    */   
/*    */   public void clean() {
/* 49 */     this.dirty = false;
/*    */   }
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 53 */     data.writeByte(this.mask);
/*    */   }
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 57 */     byte newMask = data.readByte();
/*    */     
/* 59 */     if (newMask != this.mask) {
/* 60 */       this.mask = newMask;
/* 61 */       this.dirty = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transpor\\utils\ConnectionMatrix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */