/*     */ package buildcraft.core.properties;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.IWorldAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DimensionProperty
/*     */   implements IWorldAccess
/*     */ {
/*  22 */   private final LongHashMap chunkMapping = new LongHashMap();
/*     */   private final World world;
/*     */   private final int worldHeight;
/*     */   private final WorldProperty worldProperty;
/*     */   
/*     */   public DimensionProperty(World iWorld, WorldProperty iProp) {
/*  28 */     this.world = iWorld;
/*  29 */     this.worldHeight = iWorld.func_72800_K();
/*  30 */     this.world.func_72954_a(this);
/*  31 */     this.worldProperty = iProp;
/*     */   }
/*     */   
/*     */   public synchronized boolean get(int x, int y, int z) {
/*  35 */     int xChunk = x >> 4;
/*  36 */     int zChunk = z >> 4;
/*     */     
/*  38 */     if (this.world.func_72863_F().func_73149_a(xChunk, zChunk)) {
/*  39 */       ChunkProperty property; long chunkId = ChunkCoordIntPair.func_77272_a(xChunk, zChunk);
/*     */       
/*  41 */       if (!this.chunkMapping.func_76161_b(chunkId)) {
/*  42 */         property = new ChunkProperty((IBlockAccess)this.world, this.world.func_72800_K(), xChunk, zChunk);
/*  43 */         this.chunkMapping.func_76163_a(chunkId, property);
/*  44 */         load(this.world.func_72964_e(xChunk, zChunk), property);
/*     */       } else {
/*  46 */         property = (ChunkProperty)this.chunkMapping.func_76164_a(chunkId);
/*     */       } 
/*     */       
/*  49 */       return property.get(x & 0xF, y, z & 0xF);
/*     */     } 
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void load(Chunk chunk, ChunkProperty property) {
/*  56 */     synchronized (this.world) {
/*  57 */       for (int x = 0; x < 16; x++) {
/*  58 */         for (int y = 0; y < this.worldHeight; y++) {
/*  59 */           for (int z = 0; z < 16; z++) {
/*  60 */             Block block = chunk.func_150810_a(x, y, z);
/*  61 */             int meta = chunk.func_76628_c(x, y, z);
/*     */ 
/*     */             
/*  64 */             boolean prop = this.worldProperty.get((IBlockAccess)this.world, block, meta, chunk.field_76635_g * 16 + x, y, chunk.field_76647_h * 16 + z);
/*  65 */             property.set(x, y, z, prop);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void func_147586_a(int x, int y, int z) {
/*  74 */     int xChunk = x >> 4;
/*  75 */     int zChunk = z >> 4;
/*  76 */     if (this.world.func_72863_F().func_73149_a(xChunk, zChunk)) {
/*  77 */       long chunkId = ChunkCoordIntPair.func_77272_a(xChunk, zChunk);
/*     */       
/*  79 */       if (this.chunkMapping.func_76161_b(chunkId)) {
/*  80 */         ChunkProperty property = (ChunkProperty)this.chunkMapping.func_76164_a(chunkId);
/*     */         
/*  82 */         Block block = this.world.func_147439_a(x, y, z);
/*  83 */         int meta = this.world.func_72805_g(x, y, z);
/*     */         
/*  85 */         boolean prop = this.worldProperty.get((IBlockAccess)this.world, block, meta, x, y, z);
/*  86 */         property.set(x & 0xF, y, z & 0xF, prop);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_147588_b(int var1, int var2, int var3) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_147585_a(int x1, int y1, int z1, int x2, int y2, int z2) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_72704_a(String var1, double var2, double var4, double var6, float var8, float var9) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_85102_a(EntityPlayer var1, String var2, double var3, double var5, double var7, float var9, float var10) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_72708_a(String var1, double var2, double var4, double var6, double var8, double var10, double var12) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_72703_a(Entity var1) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_72709_b(Entity var1) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_72702_a(String var1, int var2, int var3, int var4) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_82746_a(int var1, int var2, int var3, int var4, int var5) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_72706_a(EntityPlayer var1, int var2, int var3, int var4, int var5, int var6) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_147587_b(int var1, int var2, int var3, int var4, int var5) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_147584_b() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 142 */     this.world.func_72848_b(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\properties\DimensionProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */