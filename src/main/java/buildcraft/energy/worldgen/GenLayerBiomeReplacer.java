/*    */ package buildcraft.energy.worldgen;
/*    */ 
/*    */ import buildcraft.core.lib.utils.SimplexNoise;
/*    */ import java.util.Random;
/*    */ import net.minecraft.world.gen.layer.GenLayer;
/*    */ import net.minecraft.world.gen.layer.IntCache;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GenLayerBiomeReplacer
/*    */   extends GenLayer
/*    */ {
/*    */   public static final int OFFSET_RANGE = 500000;
/*    */   protected final double xOffset;
/*    */   protected final double zOffset;
/*    */   protected final double noiseScale;
/*    */   protected final double noiseThreshold;
/*    */   protected final int newBiomeId;
/*    */   
/*    */   public GenLayerBiomeReplacer(long worldSeed, long seed, GenLayer parent, double noiseScale, double noiseThreshold, int newBiomeId) {
/* 39 */     super(seed);
/* 40 */     this.field_75909_a = parent;
/* 41 */     this.noiseScale = noiseScale;
/* 42 */     this.noiseThreshold = noiseThreshold;
/* 43 */     this.newBiomeId = newBiomeId;
/* 44 */     Random rand = new Random(worldSeed);
/* 45 */     this.xOffset = (rand.nextInt(500000) - 250000);
/* 46 */     this.zOffset = (rand.nextInt(500000) - 250000);
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract boolean canReplaceBiome(int paramInt);
/*    */   
/*    */   public int[] func_75904_a(int x, int z, int width, int length) {
/* 53 */     int[] inputBiomeIDs = this.field_75909_a.func_75904_a(x - 1, z - 1, width + 2, length + 2);
/* 54 */     int[] outputBiomeIDs = IntCache.func_76445_a(width * length);
/* 55 */     for (int xIter = 0; xIter < width; xIter++) {
/* 56 */       for (int zIter = 0; zIter < length; zIter++) {
/* 57 */         func_75903_a((xIter + x), (zIter + z));
/* 58 */         int currentBiomeId = inputBiomeIDs[xIter + 1 + (zIter + 1) * (width + 2)];
/* 59 */         if (canReplaceBiome(currentBiomeId) && SimplexNoise.noise(((xIter + x) + this.xOffset) * this.noiseScale, ((zIter + z) + this.zOffset) * this.noiseScale) > this.noiseThreshold) {
/* 60 */           outputBiomeIDs[xIter + zIter * width] = this.newBiomeId;
/*    */         } else {
/*    */           
/* 63 */           outputBiomeIDs[xIter + zIter * width] = currentBiomeId;
/*    */         } 
/*    */       } 
/*    */     } 
/* 67 */     return outputBiomeIDs;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\worldgen\GenLayerBiomeReplacer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */