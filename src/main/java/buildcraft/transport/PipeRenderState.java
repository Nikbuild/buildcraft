/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.api.core.ISerializable;
/*    */ import buildcraft.transport.utils.ConnectionMatrix;
/*    */ import buildcraft.transport.utils.TextureMatrix;
/*    */ import buildcraft.transport.utils.WireMatrix;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PipeRenderState
/*    */   implements ISerializable
/*    */ {
/* 19 */   public final ConnectionMatrix pipeConnectionMatrix = new ConnectionMatrix();
/* 20 */   public final TextureMatrix textureMatrix = new TextureMatrix();
/* 21 */   public final WireMatrix wireMatrix = new WireMatrix();
/*    */   private boolean glassColorDirty = false;
/* 23 */   private byte glassColor = -127;
/*    */   
/*    */   private boolean dirty = true;
/*    */   
/*    */   public void clean() {
/* 28 */     this.dirty = false;
/* 29 */     this.glassColorDirty = false;
/* 30 */     this.pipeConnectionMatrix.clean();
/* 31 */     this.textureMatrix.clean();
/* 32 */     this.wireMatrix.clean();
/*    */   }
/*    */   
/*    */   public byte getGlassColor() {
/* 36 */     return this.glassColor;
/*    */   }
/*    */   
/*    */   public void setGlassColor(byte color) {
/* 40 */     if (this.glassColor != color) {
/* 41 */       this.glassColor = color;
/* 42 */       this.glassColorDirty = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isDirty() {
/* 47 */     return (this.dirty || this.pipeConnectionMatrix.isDirty() || this.glassColorDirty || this.textureMatrix
/* 48 */       .isDirty() || this.wireMatrix.isDirty());
/*    */   }
/*    */   
/*    */   public boolean needsRenderUpdate() {
/* 52 */     return (this.glassColorDirty || this.pipeConnectionMatrix.isDirty() || this.textureMatrix.isDirty());
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 57 */     data.writeByte((this.glassColor < -1) ? -1 : this.glassColor);
/* 58 */     this.pipeConnectionMatrix.writeData(data);
/* 59 */     this.textureMatrix.writeData(data);
/* 60 */     this.wireMatrix.writeData(data);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 65 */     byte g = data.readByte();
/* 66 */     if (g != this.glassColor) {
/* 67 */       this.glassColor = g;
/* 68 */       this.glassColorDirty = true;
/*    */     } 
/* 70 */     this.pipeConnectionMatrix.readData(data);
/* 71 */     this.textureMatrix.readData(data);
/* 72 */     this.wireMatrix.readData(data);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipeRenderState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */