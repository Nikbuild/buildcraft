/*    */ package buildcraft.core.lib.network;
/*    */ 
/*    */ import buildcraft.api.core.BCLog;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.nbt.CompressedStreamTools;
/*    */ import net.minecraft.nbt.NBTSizeTracker;
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
/*    */ 
/*    */ 
/*    */ public class PacketNBT
/*    */   extends PacketCoordinates
/*    */ {
/*    */   private NBTTagCompound nbttagcompound;
/*    */   
/*    */   public PacketNBT() {}
/*    */   
/*    */   public PacketNBT(int id, NBTTagCompound nbttagcompound, int xCoord, int yCoord, int zCoord) {
/* 29 */     super(id, xCoord, yCoord, zCoord);
/* 30 */     this.nbttagcompound = nbttagcompound;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 35 */     super.writeData(data);
/*    */     
/*    */     try {
/* 38 */       byte[] compressed = CompressedStreamTools.func_74798_a(this.nbttagcompound);
/* 39 */       if (compressed.length > 65535) {
/* 40 */         BCLog.logger.error("NBT data is too large (" + compressed.length + " > 65535)! Please report!");
/*    */       }
/* 42 */       data.writeShort(compressed.length);
/* 43 */       data.writeBytes(compressed);
/* 44 */     } catch (IOException e) {
/* 45 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 51 */     super.readData(data);
/*    */     
/* 53 */     int length = data.readUnsignedShort();
/* 54 */     byte[] compressed = new byte[length];
/* 55 */     data.readBytes(compressed);
/*    */     
/*    */     try {
/* 58 */       this.nbttagcompound = CompressedStreamTools.func_152457_a(compressed, NBTSizeTracker.field_152451_a);
/* 59 */     } catch (IOException e) {
/* 60 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public NBTTagCompound getTagCompound() {
/* 65 */     return this.nbttagcompound;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\PacketNBT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */