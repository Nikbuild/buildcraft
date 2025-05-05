/*    */ package buildcraft.robotics.map;
/*    */ 
/*    */ import buildcraft.api.core.INBTStoreable;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.IntHashMap;
/*    */ 
/*    */ public class MapRegion implements INBTStoreable {
/*  9 */   private final IntHashMap chunks = new IntHashMap();
/*    */   private final int x;
/*    */   
/*    */   public MapRegion(int x, int z) {
/* 13 */     this.x = x;
/* 14 */     this.z = z;
/*    */   }
/*    */   private final int z;
/*    */   public int getX() {
/* 18 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getZ() {
/* 22 */     return this.z;
/*    */   }
/*    */   
/*    */   public boolean hasChunk(int x, int z) {
/* 26 */     return this.chunks.func_76037_b(z << 4 | x);
/*    */   }
/*    */   
/*    */   public MapChunk getChunk(int x, int z) {
/* 30 */     int id = z << 4 | x;
/* 31 */     MapChunk chunk = (MapChunk)this.chunks.func_76041_a(id);
/* 32 */     if (chunk == null) {
/* 33 */       chunk = new MapChunk(x, z);
/* 34 */       this.chunks.func_76038_a(id, chunk);
/*    */     } 
/* 36 */     return chunk;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound tag) {
/* 41 */     this.chunks.func_76046_c();
/*    */     
/* 43 */     if (tag != null) {
/* 44 */       for (int i = 0; i < 256; i++) {
/* 45 */         if (tag.func_74764_b("r" + i)) {
/* 46 */           MapChunk chunk = new MapChunk(tag.func_74775_l("r" + i));
/* 47 */           this.chunks.func_76038_a(i, chunk);
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNBT(NBTTagCompound tag) {
/* 55 */     for (int i = 0; i < 256; i++) {
/* 56 */       MapChunk chunk = (MapChunk)this.chunks.func_76041_a(i);
/* 57 */       if (chunk != null) {
/* 58 */         NBTTagCompound chunkNBT = new NBTTagCompound();
/* 59 */         synchronized (chunk) {
/* 60 */           chunk.writeToNBT(chunkNBT);
/*    */         } 
/* 62 */         tag.func_74782_a("r" + i, (NBTBase)chunkNBT);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\map\MapRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */