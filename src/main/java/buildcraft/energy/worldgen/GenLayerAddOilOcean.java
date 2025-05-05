/*    */ package buildcraft.energy.worldgen;
/*    */ 
/*    */ import buildcraft.BuildCraftEnergy;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import net.minecraft.world.gen.layer.GenLayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GenLayerAddOilOcean
/*    */   extends GenLayerBiomeReplacer
/*    */ {
/*    */   public static final double NOISE_FIELD_SCALE = 5.0E-4D;
/*    */   public static final double NOISE_FIELD_THRESHOLD = 0.9D;
/*    */   
/*    */   public GenLayerAddOilOcean(long worldSeed, long seed, GenLayer parent) {
/* 22 */     super(worldSeed, seed, parent, 5.0E-4D, 0.9D, BuildCraftEnergy.biomeOilOcean.field_76756_M);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canReplaceBiome(int biomeId) {
/* 27 */     return (biomeId == BiomeGenBase.field_76771_b.field_76756_M);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\worldgen\GenLayerAddOilOcean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */