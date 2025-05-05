/*    */ package buildcraft.energy.worldgen;
/*    */ 
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import net.minecraft.world.biome.BiomeGenOcean;
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
/*    */ public final class BiomeGenOilOcean
/*    */   extends BiomeGenOcean
/*    */ {
/* 18 */   protected static final BiomeGenBase.Height height_OilOcean = new BiomeGenBase.Height(0.1F, 0.2F);
/*    */   
/*    */   private BiomeGenOilOcean(int id) {
/* 21 */     super(id);
/* 22 */     func_76735_a("Ocean Oil Field");
/* 23 */     func_76739_b(112);
/* 24 */     func_150570_a(field_150595_c);
/*    */   }
/*    */   
/*    */   public static BiomeGenOilOcean makeBiome(int id) {
/* 28 */     BiomeGenOilOcean biome = new BiomeGenOilOcean(id);
/* 29 */     BiomeDictionary.registerBiomeType((BiomeGenBase)biome, new BiomeDictionary.Type[] { BiomeDictionary.Type.WATER });
/* 30 */     OilPopulate.INSTANCE.excessiveBiomes.add(Integer.valueOf(biome.field_76756_M));
/* 31 */     OilPopulate.INSTANCE.surfaceDepositBiomes.add(Integer.valueOf(biome.field_76756_M));
/* 32 */     return biome;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\worldgen\BiomeGenOilOcean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */