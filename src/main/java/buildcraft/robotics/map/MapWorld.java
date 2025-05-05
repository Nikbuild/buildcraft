/*     */ package buildcraft.robotics.map;
/*     */ 
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import gnu.trove.map.hash.TLongLongHashMap;
/*     */ import gnu.trove.set.hash.TLongHashSet;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapWorld
/*     */ {
/*     */   private final LongHashMap regionMap;
/*  24 */   private final HashMap<Chunk, Integer> timeToUpdate = new HashMap<Chunk, Integer>();
/*     */   private final TLongLongHashMap regionUpdateTime;
/*     */   private final TLongHashSet updatedChunks;
/*     */   private final File location;
/*     */   
/*     */   public MapWorld(World world, File location) {
/*  30 */     this.regionMap = new LongHashMap();
/*  31 */     this.regionUpdateTime = new TLongLongHashMap();
/*  32 */     this.updatedChunks = new TLongHashSet();
/*     */     
/*  34 */     String saveFolder = world.field_73011_w.getSaveFolder();
/*  35 */     if (saveFolder == null) {
/*  36 */       saveFolder = "world";
/*     */     }
/*  38 */     this.location = new File(location, saveFolder);
/*     */     try {
/*  40 */       this.location.mkdirs();
/*  41 */     } catch (Exception e) {
/*  42 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private MapRegion getRegion(int x, int z) {
/*  47 */     long id = MapUtils.getIDFromCoords(x, z);
/*  48 */     MapRegion region = (MapRegion)this.regionMap.func_76164_a(id);
/*  49 */     if (region == null) {
/*  50 */       region = new MapRegion(x, z);
/*     */ 
/*     */       
/*  53 */       File target = new File(this.location, "r" + x + "," + z + ".nbt");
/*  54 */       if (target.exists()) {
/*     */         try {
/*  56 */           FileInputStream f = new FileInputStream(target);
/*  57 */           byte[] data = new byte[(int)target.length()];
/*  58 */           f.read(data);
/*  59 */           f.close();
/*     */           
/*  61 */           NBTTagCompound nbt = NBTUtils.load(data);
/*  62 */           if (nbt != null) {
/*  63 */             region.readFromNBT(nbt);
/*     */           }
/*  65 */         } catch (Exception e) {
/*  66 */           e.printStackTrace();
/*     */         } 
/*     */       }
/*     */       
/*  70 */       this.regionMap.func_76163_a(id, region);
/*     */     } 
/*  72 */     return region;
/*     */   }
/*     */   
/*     */   private MapChunk getChunk(int x, int z) {
/*  76 */     MapRegion region = getRegion(x >> 4, z >> 4);
/*  77 */     return region.getChunk(x & 0xF, z & 0xF);
/*     */   }
/*     */   
/*     */   public boolean hasChunk(int x, int z) {
/*  81 */     MapRegion region = getRegion(x >> 4, z >> 4);
/*  82 */     return region.hasChunk(x & 0xF, z & 0xF);
/*     */   }
/*     */   
/*     */   public void save() {
/*     */     long[] chunkList;
/*  87 */     synchronized (this.updatedChunks) {
/*  88 */       chunkList = this.updatedChunks.toArray();
/*  89 */       this.updatedChunks.clear();
/*     */     } 
/*     */     
/*  92 */     for (long id : chunkList) {
/*  93 */       MapRegion region = (MapRegion)this.regionMap.func_76164_a(id);
/*  94 */       if (region != null) {
/*     */ 
/*     */ 
/*     */         
/*  98 */         NBTTagCompound output = new NBTTagCompound();
/*  99 */         region.writeToNBT(output);
/* 100 */         byte[] data = NBTUtils.save(output);
/* 101 */         File file = new File(this.location, "r" + MapUtils.getXFromID(id) + "," + MapUtils.getZFromID(id) + ".nbt");
/*     */         
/*     */         try {
/* 104 */           FileOutputStream f = new FileOutputStream(file);
/* 105 */           f.write(data);
/* 106 */           f.close();
/* 107 */         } catch (IOException e) {
/* 108 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public int getColor(int x, int z) {
/* 114 */     MapChunk chunk = getChunk(x >> 4, z >> 4);
/* 115 */     return chunk.getColor(x & 0xF, z & 0xF);
/*     */   }
/*     */   
/*     */   public void tick() {
/* 119 */     if (this.timeToUpdate.size() > 0) {
/* 120 */       synchronized (this.timeToUpdate) {
/* 121 */         Set<Chunk> chunks = new HashSet<Chunk>();
/* 122 */         chunks.addAll(this.timeToUpdate.keySet());
/* 123 */         for (Chunk c : chunks) {
/* 124 */           int v = ((Integer)this.timeToUpdate.get(c)).intValue();
/* 125 */           if (v > 1) {
/* 126 */             this.timeToUpdate.put(c, Integer.valueOf(v - 1)); continue;
/*     */           } 
/*     */           try {
/* 129 */             updateChunk(c);
/* 130 */           } catch (Exception e) {
/* 131 */             e.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateChunk(Chunk rchunk) {
/* 140 */     long id = MapUtils.getIDFromCoords(rchunk.field_76635_g, rchunk.field_76647_h);
/* 141 */     MapChunk chunk = getChunk(rchunk.field_76635_g, rchunk.field_76647_h);
/* 142 */     chunk.update(rchunk);
/* 143 */     synchronized (this.updatedChunks) {
/* 144 */       this.updatedChunks.add(id);
/*     */     } 
/* 146 */     synchronized (this.timeToUpdate) {
/* 147 */       this.timeToUpdate.remove(rchunk);
/*     */     } 
/* 149 */     this.regionUpdateTime.put(id, (new Date()).getTime());
/*     */   }
/*     */   
/*     */   public long getUpdateTime(int x, int z) {
/* 153 */     return this.regionUpdateTime.get(MapUtils.getIDFromCoords(x, z));
/*     */   }
/*     */   
/*     */   public void updateChunkDelayed(Chunk chunk, byte time) {
/* 157 */     synchronized (this.timeToUpdate) {
/* 158 */       this.timeToUpdate.put(chunk, Integer.valueOf(time));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\map\MapWorld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */