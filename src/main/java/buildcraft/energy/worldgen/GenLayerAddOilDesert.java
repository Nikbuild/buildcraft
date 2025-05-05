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
/*    */ public class GenLayerAddOilDesert
/*    */   extends GenLayerBiomeReplacer
/*    */ {
/*    */   protected static final double NOISE_FIELD_SCALE = 0.001D;
/*    */   protected static final double NOISE_FIELD_THRESHOLD = 0.7D;
/*    */   
/*    */   public GenLayerAddOilDesert(long worldSeed, long seed, GenLayer parent) {
/* 22 */     super(worldSeed, seed, parent, 0.001D, 0.7D, BuildCraftEnergy.biomeOilDesert.field_76756_M);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canReplaceBiome(int biomeId) {
/* 27 */     return (biomeId == BiomeGenBase.field_76769_d.field_76756_M);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\worldgen\GenLayerAddOilDesert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */