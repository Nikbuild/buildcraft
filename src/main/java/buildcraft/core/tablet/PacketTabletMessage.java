/*    */ package buildcraft.core.tablet;
/*    */ 
/*    */ import buildcraft.api.core.BCLog;
/*    */ import buildcraft.core.lib.network.Packet;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.nbt.CompressedStreamTools;
/*    */ import net.minecraft.nbt.NBTSizeTracker;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketTabletMessage
/*    */   extends Packet
/*    */ {
/*    */   private NBTTagCompound tag;
/*    */   
/*    */   public PacketTabletMessage() {
/* 19 */     this.tag = new NBTTagCompound();
/*    */   }
/*    */   
/*    */   public PacketTabletMessage(NBTTagCompound tag) {
/* 23 */     this.tag = tag;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getID() {
/* 28 */     return 40;
/*    */   }
/*    */   
/*    */   public NBTTagCompound getTag() {
/* 32 */     return this.tag;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 37 */     int length = data.readUnsignedShort();
/* 38 */     byte[] compressed = new byte[length];
/* 39 */     data.readBytes(compressed);
/*    */     
/*    */     try {
/* 42 */       this.tag = CompressedStreamTools.func_152457_a(compressed, NBTSizeTracker.field_152451_a);
/* 43 */     } catch (IOException e) {
/* 44 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf data) {
/*    */     try {
/* 51 */       byte[] compressed = CompressedStreamTools.func_74798_a(this.tag);
/* 52 */       if (compressed.length > 65535) {
/* 53 */         BCLog.logger.error("NBT data is too large (" + compressed.length + " > 65535)! Please report!");
/*    */       }
/* 55 */       data.writeShort(compressed.length);
/* 56 */       data.writeBytes(compressed);
/* 57 */     } catch (IOException e) {
/* 58 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\tablet\PacketTabletMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */