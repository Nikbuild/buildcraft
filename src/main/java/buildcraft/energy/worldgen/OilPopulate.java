/*     */ package buildcraft.energy.worldgen;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.BuildCraftEnergy;
/*     */ import buildcraft.core.lib.block.BlockBuildCraftFluid;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import java.util.HashSet;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraftforge.common.util.EnumHelper;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.event.terraingen.PopulateChunkEvent;
/*     */ import net.minecraftforge.event.terraingen.TerrainGen;
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
/*     */ 
/*     */ 
/*     */ public final class OilPopulate
/*     */ {
/*  40 */   public static final OilPopulate INSTANCE = new OilPopulate();
/*  41 */   public static final PopulateChunkEvent.Populate.EventType EVENT_TYPE = (PopulateChunkEvent.Populate.EventType)EnumHelper.addEnum(PopulateChunkEvent.Populate.EventType.class, "BUILDCRAFT_OIL", new Class[0], new Object[0]);
/*     */   private static final byte LARGE_WELL_HEIGHT = 16;
/*     */   private static final byte MEDIUM_WELL_HEIGHT = 6;
/*  44 */   public final Set<Integer> excessiveBiomes = new HashSet<Integer>();
/*  45 */   public final Set<Integer> surfaceDepositBiomes = new HashSet<Integer>();
/*  46 */   public final Set<Integer> excludedBiomes = new HashSet<Integer>();
/*     */   
/*     */   private enum GenType {
/*  49 */     LARGE, MEDIUM, LAKE, NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void populate(PopulateChunkEvent.Pre event) {
/*  58 */     boolean doGen = TerrainGen.populate(event.chunkProvider, event.world, event.rand, event.chunkX, event.chunkZ, event.hasVillageGenerated, EVENT_TYPE);
/*     */     
/*  60 */     if (!doGen) {
/*  61 */       event.setResult(Event.Result.ALLOW);
/*     */       
/*     */       return;
/*     */     } 
/*  65 */     generateOil(event.world, event.rand, event.chunkX, event.chunkZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateOil(World world, Random rand, int chunkX, int chunkZ) {
/*  70 */     int x = chunkX * 16 + 8 + rand.nextInt(16);
/*  71 */     int z = chunkZ * 16 + 8 + rand.nextInt(16);
/*     */     
/*  73 */     BiomeGenBase biome = world.func_72807_a(x, z);
/*     */ 
/*     */     
/*  76 */     if (this.excludedBiomes.contains(Integer.valueOf(biome.field_76756_M)) || BlockBuildCraftFluid.isFluidExplosive(world, x, z)) {
/*     */       return;
/*     */     }
/*     */     
/*  80 */     boolean oilBiome = this.surfaceDepositBiomes.contains(Integer.valueOf(biome.field_76756_M));
/*     */     
/*  82 */     double bonus = oilBiome ? 3.0D : 1.0D;
/*  83 */     bonus *= BuildCraftEnergy.oilWellScalar;
/*  84 */     if (this.excessiveBiomes.contains(Integer.valueOf(biome.field_76756_M))) {
/*  85 */       bonus *= 30.0D;
/*  86 */     } else if (BuildCraftCore.debugWorldgen) {
/*  87 */       bonus *= 20.0D;
/*     */     } 
/*  89 */     GenType type = GenType.NONE;
/*  90 */     if (rand.nextDouble() <= 4.0E-4D * bonus) {
/*     */       
/*  92 */       type = GenType.LARGE;
/*  93 */     } else if (rand.nextDouble() <= 0.001D * bonus) {
/*     */       
/*  95 */       type = GenType.MEDIUM;
/*  96 */     } else if (oilBiome && rand.nextDouble() <= 0.02D * bonus) {
/*     */       
/*  98 */       type = GenType.LAKE;
/*     */     } 
/*     */     
/* 101 */     if (type == GenType.NONE) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 107 */     int groundLevel = getTopBlock(world, x, z);
/* 108 */     if (groundLevel < 5) {
/*     */       return;
/*     */     }
/*     */     
/* 112 */     double deviation = surfaceDeviation(world, x, groundLevel, z, 8);
/* 113 */     if (deviation > 0.45D) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 118 */     if (type == GenType.LARGE || type == GenType.MEDIUM) {
/* 119 */       int radius, lakeRadius, baseY, wellX = x;
/* 120 */       int wellZ = z;
/*     */       
/* 122 */       int wellHeight = 6;
/* 123 */       if (type == GenType.LARGE) {
/* 124 */         wellHeight = 16;
/*     */       }
/* 126 */       int maxHeight = groundLevel + wellHeight;
/* 127 */       if (maxHeight >= world.func_72800_K() - 1) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 132 */       int wellY = 20 + rand.nextInt(10);
/*     */       
/* 134 */       if (wellY > groundLevel) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 139 */       if (type == GenType.LARGE) {
/* 140 */         radius = 8 + rand.nextInt(9);
/*     */       } else {
/* 142 */         radius = 4 + rand.nextInt(4);
/*     */       } 
/*     */       
/* 145 */       int radiusSq = radius * radius;
/*     */       
/* 147 */       for (int poolX = -radius; poolX <= radius; poolX++) {
/* 148 */         for (int poolY = -radius; poolY <= radius; poolY++) {
/* 149 */           for (int poolZ = -radius; poolZ <= radius; poolZ++) {
/* 150 */             int distance = poolX * poolX + poolY * poolY + poolZ * poolZ;
/*     */             
/* 152 */             if (distance <= radiusSq) {
/* 153 */               world.func_147465_d(poolX + wellX, poolY + wellY, poolZ + wellZ, BuildCraftEnergy.blockOil, 0, (distance == radiusSq) ? 3 : 2);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 161 */       if (type == GenType.LARGE) {
/* 162 */         lakeRadius = 25 + rand.nextInt(20);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 167 */         lakeRadius = 5 + rand.nextInt(10);
/*     */       } 
/* 169 */       generateSurfaceDeposit(world, rand, biome, wellX, groundLevel, wellZ, lakeRadius);
/*     */       
/* 171 */       boolean makeSpring = (type == GenType.LARGE && BuildCraftEnergy.spawnOilSprings && BuildCraftCore.springBlock != null && (BuildCraftCore.debugWorldgen || rand.nextDouble() <= 0.25D));
/*     */ 
/*     */ 
/*     */       
/* 175 */       if (makeSpring) {
/* 176 */         baseY = 0;
/*     */       } else {
/* 178 */         baseY = wellY;
/*     */       } 
/*     */       
/* 181 */       if (makeSpring && world.func_147439_a(wellX, baseY, wellZ) == Blocks.field_150357_h)
/* 182 */         world.func_147465_d(wellX, baseY, wellZ, BuildCraftCore.springBlock, 1, 3); 
/*     */       int y;
/* 184 */       for (y = baseY + 1; y <= maxHeight; y++) {
/* 185 */         world.func_147465_d(wellX, y, wellZ, BuildCraftEnergy.blockOil, 0, 3);
/*     */       }
/*     */       
/* 188 */       if (type == GenType.LARGE) {
/* 189 */         for (y = wellY; y <= maxHeight - wellHeight / 2; y++) {
/* 190 */           world.func_147465_d(wellX + 1, y, wellZ, BuildCraftEnergy.blockOil, 0, 3);
/* 191 */           world.func_147465_d(wellX - 1, y, wellZ, BuildCraftEnergy.blockOil, 0, 3);
/* 192 */           world.func_147465_d(wellX, y, wellZ + 1, BuildCraftEnergy.blockOil, 0, 3);
/* 193 */           world.func_147465_d(wellX, y, wellZ - 1, BuildCraftEnergy.blockOil, 0, 3);
/*     */         }
/*     */       
/*     */       }
/* 197 */     } else if (type == GenType.LAKE) {
/*     */       
/* 199 */       int lakeX = x;
/* 200 */       int lakeZ = z;
/* 201 */       int lakeY = groundLevel;
/*     */       
/* 203 */       Block block = world.func_147439_a(lakeX, lakeY, lakeZ);
/* 204 */       if (block == biome.field_76752_A) {
/* 205 */         generateSurfaceDeposit(world, rand, biome, lakeX, lakeY, lakeZ, 5 + rand.nextInt(10));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void generateSurfaceDeposit(World world, Random rand, int x, int y, int z, int radius) {
/* 211 */     BiomeGenBase biome = world.func_72807_a(x, z);
/* 212 */     generateSurfaceDeposit(world, rand, biome, x, y, z, radius);
/*     */   }
/*     */   
/*     */   private void generateSurfaceDeposit(World world, Random rand, BiomeGenBase biome, int x, int y, int z, int radius) {
/* 216 */     int depth = (rand.nextDouble() < 0.5D) ? 1 : 2;
/*     */ 
/*     */     
/* 219 */     setOilColumnForLake(world, biome, x, y, z, depth, 2);
/*     */ 
/*     */     
/* 222 */     for (int w = 1; w <= radius; w++) {
/* 223 */       float proba = (radius - w + 4) / (radius + 4);
/*     */       
/* 225 */       setOilWithProba(world, biome, rand, proba, x, y, z + w, depth);
/* 226 */       setOilWithProba(world, biome, rand, proba, x, y, z - w, depth);
/* 227 */       setOilWithProba(world, biome, rand, proba, x + w, y, z, depth);
/* 228 */       setOilWithProba(world, biome, rand, proba, x - w, y, z, depth);
/*     */       
/* 230 */       for (int i = 1; i <= w; i++) {
/* 231 */         setOilWithProba(world, biome, rand, proba, x + i, y, z + w, depth);
/* 232 */         setOilWithProba(world, biome, rand, proba, x + i, y, z - w, depth);
/* 233 */         setOilWithProba(world, biome, rand, proba, x + w, y, z + i, depth);
/* 234 */         setOilWithProba(world, biome, rand, proba, x - w, y, z + i, depth);
/*     */         
/* 236 */         setOilWithProba(world, biome, rand, proba, x - i, y, z + w, depth);
/* 237 */         setOilWithProba(world, biome, rand, proba, x - i, y, z - w, depth);
/* 238 */         setOilWithProba(world, biome, rand, proba, x + w, y, z - i, depth);
/* 239 */         setOilWithProba(world, biome, rand, proba, x - w, y, z - i, depth);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 244 */     for (int dx = x - radius; dx <= x + radius; dx++) {
/* 245 */       for (int dz = z - radius; dz <= z + radius; dz++) {
/* 246 */         if (!isOil(world, dx, y, dz))
/*     */         {
/*     */           
/* 249 */           if (isOilSurrounded(world, dx, y, dz))
/* 250 */             setOilColumnForLake(world, biome, dx, y, dz, depth, 2); 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isReplaceableFluid(World world, int x, int y, int z) {
/* 257 */     Block block = world.func_147439_a(x, y, z);
/* 258 */     return ((block instanceof net.minecraft.block.BlockStaticLiquid || block instanceof net.minecraftforge.fluids.BlockFluidBase || block instanceof net.minecraftforge.fluids.IFluidBlock) && block.func_149688_o() != Material.field_151587_i);
/*     */   }
/*     */   
/*     */   private boolean isOil(World world, int x, int y, int z) {
/* 262 */     Block block = world.func_147439_a(x, y, z);
/* 263 */     return (block == BuildCraftEnergy.blockOil);
/*     */   }
/*     */   
/*     */   private boolean isReplaceableForLake(World world, BiomeGenBase biome, int x, int y, int z) {
/* 267 */     if (world.func_147437_c(x, y, z)) {
/* 268 */       return true;
/*     */     }
/*     */     
/* 271 */     Block block = world.func_147439_a(x, y, z);
/*     */     
/* 273 */     if (block == biome.field_76753_B || block == biome.field_76752_A) {
/* 274 */       return true;
/*     */     }
/*     */     
/* 277 */     if (!block.func_149688_o().func_76230_c()) {
/* 278 */       return true;
/*     */     }
/*     */     
/* 281 */     if (block.isReplaceableOreGen(world, x, y, z, Blocks.field_150348_b)) {
/* 282 */       return true;
/*     */     }
/*     */     
/* 285 */     if (block instanceof net.minecraft.block.BlockFlower) {
/* 286 */       return true;
/*     */     }
/*     */     
/* 289 */     if (!block.func_149662_c()) {
/* 290 */       return true;
/*     */     }
/*     */     
/* 293 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isOilAdjacent(World world, int x, int y, int z) {
/* 297 */     return (isOil(world, x + 1, y, z) || 
/* 298 */       isOil(world, x - 1, y, z) || 
/* 299 */       isOil(world, x, y, z + 1) || 
/* 300 */       isOil(world, x, y, z - 1));
/*     */   }
/*     */   
/*     */   private boolean isOilSurrounded(World world, int x, int y, int z) {
/* 304 */     return (isOil(world, x + 1, y, z) && 
/* 305 */       isOil(world, x - 1, y, z) && 
/* 306 */       isOil(world, x, y, z + 1) && 
/* 307 */       isOil(world, x, y, z - 1));
/*     */   }
/*     */   
/*     */   private void setOilWithProba(World world, BiomeGenBase biome, Random rand, float proba, int x, int y, int z, int depth) {
/* 311 */     if (rand.nextFloat() <= proba && !world.func_147437_c(x, y - depth - 1, z) && 
/* 312 */       isOilAdjacent(world, x, y, z)) {
/* 313 */       setOilColumnForLake(world, biome, x, y, z, depth, 3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void setOilColumnForLake(World world, BiomeGenBase biome, int x, int y, int z, int depth, int update) {
/* 319 */     if (isReplaceableForLake(world, biome, x, y + 1, z)) {
/* 320 */       if (!world.func_147437_c(x, y + 2, z)) {
/*     */         return;
/*     */       }
/* 323 */       if (isReplaceableFluid(world, x, y, z) || world.isSideSolid(x, y - 1, z, ForgeDirection.UP)) {
/* 324 */         world.func_147465_d(x, y, z, BuildCraftEnergy.blockOil, 0, update);
/*     */       } else {
/*     */         return;
/*     */       } 
/* 328 */       if (!world.func_147437_c(x, y + 1, z)) {
/* 329 */         world.func_147465_d(x, y + 1, z, Blocks.field_150350_a, 0, update);
/*     */       }
/*     */       
/* 332 */       for (int d = 1; d <= depth - 1; d++) {
/* 333 */         if (isReplaceableFluid(world, x, y - d, z) || !world.isSideSolid(x, y - d - 1, z, ForgeDirection.UP)) {
/*     */           return;
/*     */         }
/* 336 */         world.func_147465_d(x, y - d, z, BuildCraftEnergy.blockOil, 0, 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getTopBlock(World world, int x, int z) {
/* 342 */     Chunk chunk = world.func_72938_d(x, z);
/* 343 */     int y = chunk.func_76625_h() + 15;
/*     */     
/* 345 */     int trimmedX = x & 0xF;
/* 346 */     int trimmedZ = z & 0xF;
/*     */     
/* 348 */     for (; y > 0; y--) {
/* 349 */       Block block = chunk.func_150810_a(trimmedX, y, trimmedZ);
/*     */       
/* 351 */       if (!block.isAir((IBlockAccess)world, x, y, z)) {
/*     */ 
/*     */ 
/*     */         
/* 355 */         if (block instanceof net.minecraft.block.BlockStaticLiquid) {
/* 356 */           return y;
/*     */         }
/*     */         
/* 359 */         if (block instanceof net.minecraftforge.fluids.BlockFluidBase) {
/* 360 */           return y;
/*     */         }
/*     */         
/* 363 */         if (block instanceof net.minecraftforge.fluids.IFluidBlock) {
/* 364 */           return y;
/*     */         }
/*     */         
/* 367 */         if (block.func_149688_o().func_76230_c())
/*     */         {
/*     */ 
/*     */           
/* 371 */           if (!(block instanceof net.minecraft.block.BlockFlower))
/*     */           {
/*     */ 
/*     */             
/* 375 */             return y - 1; }  } 
/*     */       } 
/*     */     } 
/* 378 */     return -1;
/*     */   }
/*     */   
/*     */   private double surfaceDeviation(World world, int x, int y, int z, int radius) {
/* 382 */     int diameter = radius * 2;
/* 383 */     double centralTendancy = y;
/* 384 */     double deviation = 0.0D;
/* 385 */     for (int i = 0; i < diameter; i++) {
/* 386 */       for (int k = 0; k < diameter; k++) {
/* 387 */         deviation += getTopBlock(world, x - radius + i, z - radius + k) - centralTendancy;
/*     */       }
/*     */     } 
/* 390 */     return Math.abs(deviation / centralTendancy);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\worldgen\OilPopulate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */