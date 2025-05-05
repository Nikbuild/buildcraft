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
/*    */ 
/*    */ public class TextureMatrix
/*    */ {
/* 17 */   private final int[] iconIndexes = new int[7];
/*    */   private boolean dirty = false;
/*    */   
/*    */   public int getTextureIndex(ForgeDirection direction) {
/* 21 */     return this.iconIndexes[direction.ordinal()];
/*    */   }
/*    */   
/*    */   public void setIconIndex(ForgeDirection direction, int value) {
/* 25 */     if (this.iconIndexes[direction.ordinal()] != value) {
/* 26 */       this.iconIndexes[direction.ordinal()] = value;
/* 27 */       this.dirty = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isDirty() {
/* 32 */     return this.dirty;
/*    */   }
/*    */   
/*    */   public void clean() {
/* 36 */     this.dirty = false;
/*    */   }
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 40 */     for (int iconIndex : this.iconIndexes) {
/* 41 */       data.writeByte(iconIndex);
/*    */     }
/*    */   }
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 46 */     for (int i = 0; i < this.iconIndexes.length; i++) {
/* 47 */       int icon = data.readUnsignedByte();
/* 48 */       if (this.iconIndexes[i] != icon) {
/* 49 */         this.iconIndexes[i] = icon;
/* 50 */         this.dirty = true;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transpor\\utils\TextureMatrix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */