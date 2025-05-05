/*     */ package buildcraft.robotics;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.ISerializable;
/*     */ import buildcraft.core.lib.utils.BitSetUtils;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.BitSet;
/*     */ import java.util.Random;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZoneChunk
/*     */   implements ISerializable
/*     */ {
/*     */   public BitSet property;
/*     */   private boolean fullSet = false;
/*     */   
/*     */   public boolean get(int xChunk, int zChunk) {
/*  32 */     if (this.fullSet)
/*  33 */       return true; 
/*  34 */     if (this.property == null) {
/*  35 */       return false;
/*     */     }
/*  37 */     return this.property.get(xChunk + zChunk * 16);
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int xChunk, int zChunk, boolean value) {
/*  42 */     if (value) {
/*  43 */       if (this.fullSet) {
/*     */         return;
/*     */       }
/*     */       
/*  47 */       if (this.property == null) {
/*  48 */         this.property = new BitSet(256);
/*     */       }
/*     */       
/*  51 */       this.property.set(xChunk + zChunk * 16, value);
/*     */       
/*  53 */       if (this.property.cardinality() >= 256) {
/*  54 */         this.property = null;
/*  55 */         this.fullSet = true;
/*     */       } 
/*     */     } else {
/*  58 */       if (this.fullSet) {
/*  59 */         this.property = new BitSet(256);
/*  60 */         this.property.flip(0, 255);
/*  61 */         this.fullSet = false;
/*  62 */       } else if (this.property == null) {
/*     */         
/*  64 */         this.property = new BitSet(256);
/*     */       } 
/*     */       
/*  67 */       this.property.set(xChunk + zChunk * 16, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*  72 */     nbt.func_74757_a("fullSet", this.fullSet);
/*     */     
/*  74 */     if (this.property != null) {
/*  75 */       nbt.func_74773_a("bits", BitSetUtils.toByteArray(this.property));
/*     */     }
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  80 */     this.fullSet = nbt.func_74767_n("fullSet");
/*     */     
/*  82 */     if (nbt.func_74764_b("bits")) {
/*  83 */       this.property = BitSetUtils.fromByteArray(nbt.func_74770_j("bits"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockIndex getRandomBlockIndex(Random rand) {
/*     */     int x, z;
/*  90 */     if (this.fullSet) {
/*  91 */       x = rand.nextInt(16);
/*  92 */       z = rand.nextInt(16);
/*     */     } else {
/*  94 */       int bitId = rand.nextInt(this.property.cardinality());
/*  95 */       int bitPosition = this.property.nextSetBit(0);
/*     */       
/*  97 */       while (bitId > 0) {
/*  98 */         bitId--;
/*     */         
/* 100 */         bitPosition = this.property.nextSetBit(bitPosition + 1);
/*     */       } 
/*     */       
/* 103 */       z = bitPosition / 16;
/* 104 */       x = bitPosition - 16 * z;
/*     */     } 
/* 106 */     int y = rand.nextInt(255);
/*     */     
/* 108 */     return new BlockIndex(x, y, z);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 112 */     return (!this.fullSet && this.property.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 117 */     int flags = stream.readUnsignedByte();
/* 118 */     if ((flags & 0x1) != 0) {
/* 119 */       this.property = BitSetUtils.fromByteArray(NetworkUtils.readByteArray(stream));
/*     */     }
/* 121 */     this.fullSet = ((flags & 0x2) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 126 */     int flags = (this.fullSet ? 2 : 0) | ((this.property != null) ? 1 : 0);
/* 127 */     stream.writeByte(flags);
/* 128 */     if (this.property != null)
/* 129 */       NetworkUtils.writeByteArray(stream, BitSetUtils.toByteArray(this.property)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ZoneChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */