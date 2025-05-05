/*    */ package buildcraft.energy.worldgen;
/*    */ 
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import net.minecraft.world.biome.BiomeGenDesert;
/*    */ import net.minecraftforge.common.BiomeDictionary;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BiomeGenOilDesert
/*    */   extends BiomeGenDesert
/*    */ {
/* 18 */   protected static final BiomeGenBase.Height height_OilDesert = new BiomeGenBase.Height(0.1F, 0.2F);
/*    */   
/*    */   private BiomeGenOilDesert(int id) {
/* 21 */     super(id);
/* 22 */     func_76739_b(16421912);
/* 23 */     func_76735_a("Desert Oil Field");
/* 24 */     func_76745_m();
/* 25 */     func_76732_a(2.0F, 0.0F);
/* 26 */     func_150570_a(height_OilDesert);
/*    */   }
/*    */   
/*    */   public static BiomeGenOilDesert makeBiome(int id) {
/* 30 */     BiomeGenOilDesert biome = new BiomeGenOilDesert(id);
/* 31 */     BiomeDictionary.registerBiomeType((BiomeGenBase)biome, new BiomeDictionary.Type[] { BiomeDictionary.Type.SANDY });
/* 32 */     OilPopulate.INSTANCE.excessiveBiomes.add(Integer.valueOf(biome.field_76756_M));
/* 33 */     OilPopulate.INSTANCE.surfaceDepositBiomes.add(Integer.valueOf(biome.field_76756_M));
/* 34 */     return biome;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\worldgen\BiomeGenOilDesert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */