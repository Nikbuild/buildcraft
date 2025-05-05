/*     */ package buildcraft.robotics;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.ISerializable;
/*     */ import buildcraft.api.core.IZone;
/*     */ import buildcraft.core.ChunkIndex;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
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
/*     */ public class ZonePlan
/*     */   implements IZone, ISerializable
/*     */ {
/*  28 */   private final HashMap<ChunkIndex, ZoneChunk> chunkMapping = new HashMap<ChunkIndex, ZoneChunk>();
/*     */   
/*     */   public boolean get(int x, int z) {
/*  31 */     int xChunk = x >> 4;
/*  32 */     int zChunk = z >> 4;
/*  33 */     ChunkIndex chunkId = new ChunkIndex(xChunk, zChunk);
/*     */ 
/*     */     
/*  36 */     if (!this.chunkMapping.containsKey(chunkId)) {
/*  37 */       return false;
/*     */     }
/*  39 */     ZoneChunk property = this.chunkMapping.get(chunkId);
/*  40 */     return property.get(x & 0xF, z & 0xF);
/*     */   }
/*     */   
/*     */   public void set(int x, int z, boolean val) {
/*     */     ZoneChunk property;
/*  45 */     int xChunk = x >> 4;
/*  46 */     int zChunk = z >> 4;
/*  47 */     ChunkIndex chunkId = new ChunkIndex(xChunk, zChunk);
/*     */ 
/*     */     
/*  50 */     if (!this.chunkMapping.containsKey(chunkId)) {
/*  51 */       if (val) {
/*  52 */         property = new ZoneChunk();
/*  53 */         this.chunkMapping.put(chunkId, property);
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */     } else {
/*  58 */       property = this.chunkMapping.get(chunkId);
/*     */     } 
/*     */     
/*  61 */     property.set(x & 0xF, z & 0xF, val);
/*     */     
/*  63 */     if (property.isEmpty()) {
/*  64 */       this.chunkMapping.remove(chunkId);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*  69 */     NBTTagList list = new NBTTagList();
/*     */     
/*  71 */     for (Map.Entry<ChunkIndex, ZoneChunk> e : this.chunkMapping.entrySet()) {
/*  72 */       NBTTagCompound subNBT = new NBTTagCompound();
/*  73 */       ((ChunkIndex)e.getKey()).writeToNBT(subNBT);
/*  74 */       ((ZoneChunk)e.getValue()).writeToNBT(subNBT);
/*  75 */       list.func_74742_a((NBTBase)subNBT);
/*     */     } 
/*     */     
/*  78 */     nbt.func_74782_a("chunkMapping", (NBTBase)list);
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  82 */     NBTTagList list = nbt.func_150295_c("chunkMapping", 10);
/*     */     
/*  84 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*  85 */       NBTTagCompound subNBT = list.func_150305_b(i);
/*     */       
/*  87 */       ChunkIndex id = new ChunkIndex();
/*  88 */       id.readFromNBT(subNBT);
/*     */       
/*  90 */       ZoneChunk chunk = new ZoneChunk();
/*  91 */       chunk.readFromNBT(subNBT);
/*     */       
/*  93 */       this.chunkMapping.put(id, chunk);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double distanceTo(BlockIndex index) {
/*  99 */     return Math.sqrt(distanceToSquared(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public double distanceToSquared(BlockIndex index) {
/* 104 */     double maxSqrDistance = Double.MAX_VALUE;
/*     */     
/* 106 */     for (Map.Entry<ChunkIndex, ZoneChunk> e : this.chunkMapping.entrySet()) {
/* 107 */       double dx = ((((ChunkIndex)e.getKey()).x << 12) - index.x);
/* 108 */       double dz = ((((ChunkIndex)e.getKey()).x << 12) - index.z);
/*     */       
/* 110 */       double sqrDistance = dx * dx + dz * dz;
/*     */       
/* 112 */       if (sqrDistance < maxSqrDistance) {
/* 113 */         maxSqrDistance = sqrDistance;
/*     */       }
/*     */     } 
/*     */     
/* 117 */     return maxSqrDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(double x, double y, double z) {
/* 122 */     int xBlock = (int)Math.floor(x);
/* 123 */     int zBlock = (int)Math.floor(z);
/*     */     
/* 125 */     return get(xBlock, zBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockIndex getRandomBlockIndex(Random rand) {
/* 130 */     if (this.chunkMapping.size() == 0) {
/* 131 */       return null;
/*     */     }
/*     */     
/* 134 */     int chunkId = rand.nextInt(this.chunkMapping.size());
/*     */     
/* 136 */     for (Map.Entry<ChunkIndex, ZoneChunk> e : this.chunkMapping.entrySet()) {
/* 137 */       if (chunkId == 0) {
/* 138 */         BlockIndex i = ((ZoneChunk)e.getValue()).getRandomBlockIndex(rand);
/* 139 */         i.x = (((ChunkIndex)e.getKey()).x << 4) + i.x;
/* 140 */         i.z = (((ChunkIndex)e.getKey()).z << 4) + i.z;
/*     */         
/* 142 */         return i;
/*     */       } 
/*     */       
/* 145 */       chunkId--;
/*     */     } 
/*     */     
/* 148 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 153 */     this.chunkMapping.clear();
/* 154 */     int size = stream.readInt();
/* 155 */     for (int i = 0; i < size; i++) {
/* 156 */       ChunkIndex key = new ChunkIndex();
/* 157 */       ZoneChunk value = new ZoneChunk();
/* 158 */       key.readData(stream);
/* 159 */       value.readData(stream);
/* 160 */       this.chunkMapping.put(key, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 166 */     stream.writeInt(this.chunkMapping.size());
/* 167 */     for (Map.Entry<ChunkIndex, ZoneChunk> e : this.chunkMapping.entrySet()) {
/* 168 */       ((ChunkIndex)e.getKey()).writeData(stream);
/* 169 */       ((ZoneChunk)e.getValue()).writeData(stream);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ZonePlan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */