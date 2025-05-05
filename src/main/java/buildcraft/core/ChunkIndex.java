/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.api.core.ISerializable;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.nbt.NBTTagCompound;
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
/*    */ public class ChunkIndex
/*    */   implements ISerializable
/*    */ {
/*    */   public int x;
/*    */   public int z;
/*    */   
/*    */   public ChunkIndex() {}
/*    */   
/*    */   public ChunkIndex(int iX, int iZ) {
/* 25 */     this.x = iX;
/* 26 */     this.z = iZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 31 */     if (obj instanceof ChunkIndex) {
/* 32 */       ChunkIndex c = (ChunkIndex)obj;
/*    */       
/* 34 */       return (c.x == this.x && c.z == this.z);
/*    */     } 
/*    */     
/* 37 */     return super.equals(obj);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 43 */     return this.x * 37 + this.z;
/*    */   }
/*    */   
/*    */   public void writeToNBT(NBTTagCompound nbt) {
/* 47 */     nbt.func_74768_a("x", this.x);
/* 48 */     nbt.func_74768_a("z", this.z);
/*    */   }
/*    */   
/*    */   public void readFromNBT(NBTTagCompound nbt) {
/* 52 */     this.x = nbt.func_74762_e("x");
/* 53 */     this.z = nbt.func_74762_e("z");
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf stream) {
/* 58 */     this.x = stream.readInt();
/* 59 */     this.z = stream.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf stream) {
/* 64 */     stream.writeInt(this.x);
/* 65 */     stream.writeInt(this.z);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\ChunkIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */