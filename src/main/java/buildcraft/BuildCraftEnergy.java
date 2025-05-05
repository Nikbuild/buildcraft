/*     */ package buildcraft;
/*     */ 
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.core.JavaTools;
/*     */ import buildcraft.api.core.StackKey;
/*     */ import buildcraft.api.fuels.BuildcraftFuelRegistry;
/*     */ import buildcraft.api.fuels.ICoolantManager;
/*     */ import buildcraft.api.fuels.IFuelManager;
/*     */ import buildcraft.api.recipes.BuildcraftRecipeRegistry;
/*     */ import buildcraft.api.statements.ITriggerExternal;
/*     */ import buildcraft.api.statements.ITriggerProvider;
/*     */ import buildcraft.api.statements.StatementManager;
/*     */ import buildcraft.core.BCRegistry;
/*     */ import buildcraft.core.BlockSpring;
/*     */ import buildcraft.core.DefaultProps;
/*     */ import buildcraft.core.IMCHandler;
/*     */ import buildcraft.core.InterModComms;
/*     */ import buildcraft.core.config.ConfigManager;
/*     */ import buildcraft.core.lib.block.BlockBuildCraftFluid;
/*     */ import buildcraft.core.lib.engines.TileEngineBase;
/*     */ import buildcraft.core.lib.network.ChannelHandler;
/*     */ import buildcraft.core.lib.network.PacketHandler;
/*     */ import buildcraft.energy.BucketHandler;
/*     */ import buildcraft.energy.EnergyGuiHandler;
/*     */ import buildcraft.energy.EnergyProxy;
/*     */ import buildcraft.energy.IMCHandlerEnergy;
/*     */ import buildcraft.energy.ItemBucketBuildcraft;
/*     */ import buildcraft.energy.TileEngineCreative;
/*     */ import buildcraft.energy.TileEngineIron;
/*     */ import buildcraft.energy.TileEngineStone;
/*     */ import buildcraft.energy.fuels.CoolantManager;
/*     */ import buildcraft.energy.fuels.FuelManager;
/*     */ import buildcraft.energy.statements.EnergyStatementProvider;
/*     */ import buildcraft.energy.statements.TriggerCoolantBelowThreshold;
/*     */ import buildcraft.energy.statements.TriggerEngineHeat;
/*     */ import buildcraft.energy.statements.TriggerFuelBelowThreshold;
/*     */ import buildcraft.energy.worldgen.BiomeGenOilDesert;
/*     */ import buildcraft.energy.worldgen.BiomeGenOilOcean;
/*     */ import buildcraft.energy.worldgen.BiomeInitializer;
/*     */ import buildcraft.energy.worldgen.OilPopulate;
/*     */ import cpw.mods.fml.client.event.ConfigChangedEvent;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.Mod;
/*     */ import cpw.mods.fml.common.Mod.EventHandler;
/*     */ import cpw.mods.fml.common.Mod.Instance;
/*     */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLInterModComms;
/*     */ import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
/*     */ import cpw.mods.fml.common.event.FMLPostInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.network.IGuiHandler;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraftforge.client.event.TextureStitchEvent;
/*     */ import net.minecraftforge.common.BiomeDictionary;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.config.Property;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import org.apache.logging.log4j.Level;
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
/*     */ @Mod(name = "BuildCraft Energy", version = "7.1.26", useMetadata = false, modid = "BuildCraft|Energy", dependencies = "required-after:BuildCraft|Core@7.1.26")
/*     */ public class BuildCraftEnergy
/*     */   extends BuildCraftMod
/*     */ {
/*     */   @Instance("BuildCraft|Energy")
/*     */   public static BuildCraftEnergy instance;
/*     */   public static boolean spawnOilSprings = true;
/*     */   public static BiomeGenOilDesert biomeOilDesert;
/*     */   public static BiomeGenOilOcean biomeOilOcean;
/*     */   public static Fluid fluidOil;
/*     */   public static Fluid fluidFuel;
/*     */   public static Fluid fluidRedPlasma;
/*     */   public static Block blockOil;
/*     */   public static Block blockFuel;
/*     */   public static Block blockRedPlasma;
/*     */   public static Item bucketOil;
/*     */   public static Item bucketFuel;
/*     */   public static Item bucketRedPlasma;
/*     */   public static Item fuel;
/*     */   public static Achievement engineAchievement2;
/*     */   public static Achievement engineAchievement3;
/*     */   public static boolean canOilBurn;
/*     */   public static boolean isOilDense;
/* 110 */   public static double oilWellScalar = 1.0D;
/*     */   
/* 112 */   public static ITriggerExternal triggerBlueEngineHeat = (ITriggerExternal)new TriggerEngineHeat(TileEngineBase.EnergyStage.BLUE);
/* 113 */   public static ITriggerExternal triggerGreenEngineHeat = (ITriggerExternal)new TriggerEngineHeat(TileEngineBase.EnergyStage.GREEN);
/* 114 */   public static ITriggerExternal triggerYellowEngineHeat = (ITriggerExternal)new TriggerEngineHeat(TileEngineBase.EnergyStage.YELLOW);
/* 115 */   public static ITriggerExternal triggerRedEngineHeat = (ITriggerExternal)new TriggerEngineHeat(TileEngineBase.EnergyStage.RED);
/* 116 */   public static ITriggerExternal triggerEngineOverheat = (ITriggerExternal)new TriggerEngineHeat(TileEngineBase.EnergyStage.OVERHEAT);
/*     */   
/* 118 */   public static ITriggerExternal triggerFuelBelow25 = (ITriggerExternal)new TriggerFuelBelowThreshold(0.25F);
/* 119 */   public static ITriggerExternal triggerFuelBelow50 = (ITriggerExternal)new TriggerFuelBelowThreshold(0.5F);
/*     */   
/* 121 */   public static ITriggerExternal triggerCoolantBelow25 = (ITriggerExternal)new TriggerCoolantBelowThreshold(0.25F);
/* 122 */   public static ITriggerExternal triggerCoolantBelow50 = (ITriggerExternal)new TriggerCoolantBelowThreshold(0.5F);
/*     */   
/*     */   private static Fluid buildcraftFluidOil;
/*     */   private static Fluid buildcraftFluidFuel;
/*     */   private static Fluid buildcraftFluidRedPlasma;
/*     */   
/*     */   @EventHandler
/*     */   public void preInit(FMLPreInitializationEvent evt) {
/* 130 */     BuildcraftFuelRegistry.fuel = (IFuelManager)FuelManager.INSTANCE;
/* 131 */     BuildcraftFuelRegistry.coolant = (ICoolantManager)CoolantManager.INSTANCE;
/*     */ 
/*     */ 
/*     */     
/* 135 */     int oilDesertBiomeId = BuildCraftCore.mainConfigManager.register("worldgen.biomes", "biomeOilDesert", Integer.valueOf(DefaultProps.BIOME_OIL_DESERT), "The id for the Oil Desert biome", ConfigManager.RestartRequirement.GAME).getInt();
/*     */ 
/*     */     
/* 138 */     int oilOceanBiomeId = BuildCraftCore.mainConfigManager.register("worldgen.biomes", "biomeOilOcean", Integer.valueOf(DefaultProps.BIOME_OIL_OCEAN), "The id for the Oil Ocean biome", ConfigManager.RestartRequirement.GAME).getInt();
/*     */     
/* 140 */     BuildCraftCore.mainConfigManager.register("worldgen.spawnOilSprings", Boolean.valueOf(true), "Should I spawn oil springs?", ConfigManager.RestartRequirement.GAME);
/* 141 */     BuildCraftCore.mainConfigManager.register("worldgen.oilWellGenerationRate", Double.valueOf(1.0D), "How high should be the probability of an oil well generating?", ConfigManager.RestartRequirement.NONE);
/*     */     
/* 143 */     setBiomeList(OilPopulate.INSTANCE.surfaceDepositBiomes, BuildCraftCore.mainConfigManager
/*     */ 
/*     */         
/* 146 */         .register("worldgen.biomes", "increasedOilIDs", new String[] {
/* 147 */             BiomeDictionary.Type.SANDY.toString(), BiomeGenBase.field_76768_g.field_76791_y }, "IDs or Biome Types (e.g. SANDY,OCEAN) of biomes that should have increased oil generation rates.", ConfigManager.RestartRequirement.GAME));
/*     */ 
/*     */     
/* 150 */     setBiomeList(OilPopulate.INSTANCE.excessiveBiomes, BuildCraftCore.mainConfigManager
/*     */ 
/*     */         
/* 153 */         .register("worldgen.biomes", "excessiveOilIDs", new String[0], "IDs or Biome Types (e.g. SANDY,OCEAN) of biomes that should have GREATLY increased oil generation rates.", ConfigManager.RestartRequirement.GAME));
/*     */ 
/*     */ 
/*     */     
/* 157 */     setBiomeList(OilPopulate.INSTANCE.excludedBiomes, BuildCraftCore.mainConfigManager
/*     */         
/* 159 */         .register("worldgen.biomes", "excludeOilIDs", new String[] { BiomeGenBase.field_76779_k.field_76791_y, BiomeGenBase.field_76778_j.field_76791_y }, "IDs or Biome Types (e.g. SANDY,OCEAN) of biomes that are excluded from generating oil.", ConfigManager.RestartRequirement.GAME));
/*     */ 
/*     */ 
/*     */     
/* 163 */     double fuelOilMultiplier = BuildCraftCore.mainConfigManager.register("general", "fuel.oil.combustion", Float.valueOf(1.0F), "adjust energy value of Oil in Combustion Engines", ConfigManager.RestartRequirement.GAME).getDouble();
/* 164 */     double fuelFuelMultiplier = BuildCraftCore.mainConfigManager.register("general", "fuel.fuel.combustion", Float.valueOf(1.0F), "adjust energy value of Fuel in Combustion Engines", ConfigManager.RestartRequirement.GAME).getDouble();
/*     */     
/* 166 */     int fuelOilEnergyOutput = BuildCraftCore.mainConfigManager.register("general", "fuel.oil.combustion.energyOutput", Integer.valueOf(30), "adjust output energy by Oil in Combustion Engines", ConfigManager.RestartRequirement.GAME).getInt();
/* 167 */     int fuelFuelEnergyOutput = BuildCraftCore.mainConfigManager.register("general", "fuel.fuel.combustion.energyOutput", Integer.valueOf(60), "adjust output energy by Fuel in Combustion Engines", ConfigManager.RestartRequirement.GAME).getInt();
/*     */     
/* 169 */     BuildCraftCore.mainConfiguration.save();
/*     */     
/* 171 */     BiomeGenBase[] biomeGenArray = BiomeGenBase.func_150565_n();
/*     */     
/* 173 */     if (oilDesertBiomeId > 0) {
/* 174 */       if (oilDesertBiomeId >= biomeGenArray.length || biomeGenArray[oilDesertBiomeId] != null) {
/* 175 */         oilDesertBiomeId = findUnusedBiomeID("oilDesert");
/*     */         
/* 177 */         BuildCraftCore.mainConfiguration.get("worldgen.biomes", "biomeOilDesert", oilDesertBiomeId).set(oilDesertBiomeId);
/* 178 */         BuildCraftCore.mainConfiguration.save();
/*     */       } 
/* 180 */       biomeOilDesert = BiomeGenOilDesert.makeBiome(oilDesertBiomeId);
/*     */     } 
/*     */     
/* 183 */     if (oilOceanBiomeId > 0) {
/* 184 */       if (oilOceanBiomeId >= biomeGenArray.length || biomeGenArray[oilOceanBiomeId] != null) {
/* 185 */         oilOceanBiomeId = findUnusedBiomeID("oilOcean");
/*     */         
/* 187 */         BuildCraftCore.mainConfiguration.get("worldgen.biomes", "biomeOilOcean", oilOceanBiomeId).set(oilOceanBiomeId);
/* 188 */         BuildCraftCore.mainConfiguration.save();
/*     */       } 
/* 190 */       biomeOilOcean = BiomeGenOilOcean.makeBiome(oilOceanBiomeId);
/*     */     } 
/*     */ 
/*     */     
/* 194 */     if (!FluidRegistry.isFluidRegistered("oil")) {
/* 195 */       buildcraftFluidOil = (new Fluid("oil")).setDensity(800).setViscosity(10000);
/* 196 */       FluidRegistry.registerFluid(buildcraftFluidOil);
/*     */     } else {
/* 198 */       BCLog.logger.warn("Not using BuildCraft oil - issues might occur!");
/*     */     } 
/* 200 */     fluidOil = FluidRegistry.getFluid("oil");
/*     */     
/* 202 */     if (!FluidRegistry.isFluidRegistered("fuel")) {
/* 203 */       buildcraftFluidFuel = new Fluid("fuel");
/* 204 */       FluidRegistry.registerFluid(buildcraftFluidFuel);
/*     */     } else {
/* 206 */       BCLog.logger.warn("Not using BuildCraft fuel - issues might occur!");
/*     */     } 
/* 208 */     fluidFuel = FluidRegistry.getFluid("fuel");
/*     */     
/* 210 */     if (!FluidRegistry.isFluidRegistered("redplasma")) {
/* 211 */       buildcraftFluidRedPlasma = (new Fluid("redplasma")).setDensity(10000).setViscosity(10000).setLuminosity(30);
/* 212 */       FluidRegistry.registerFluid(buildcraftFluidRedPlasma);
/*     */     } else {
/* 214 */       BCLog.logger.warn("Not using BuildCraft red plasma - issues might occur!");
/*     */     } 
/* 216 */     fluidRedPlasma = FluidRegistry.getFluid("redplasma");
/*     */     
/* 218 */     if (fluidOil.getBlock() == null) {
/* 219 */       blockOil = (Block)(new BlockBuildCraftFluid(fluidOil, Material.field_151586_h, MapColor.field_151646_E)).setFlammability(0);
/* 220 */       blockOil.func_149663_c("blockOil").func_149713_g(8);
/* 221 */       BCRegistry.INSTANCE.registerBlock(blockOil, true);
/* 222 */       fluidOil.setBlock(blockOil);
/*     */       
/* 224 */       BuildCraftCore.mainConfigManager.register("general.oilCanBurn", Boolean.valueOf(true), "Should oil burn when lit on fire?", ConfigManager.RestartRequirement.NONE);
/* 225 */       BuildCraftCore.mainConfigManager.register("general.oilIsDense", Boolean.valueOf(true), "Should oil be dense and drag entities down?", ConfigManager.RestartRequirement.NONE);
/*     */     } else {
/* 227 */       blockOil = fluidOil.getBlock();
/*     */     } 
/*     */     
/* 230 */     reloadConfig(ConfigManager.RestartRequirement.GAME);
/*     */     
/* 232 */     if (blockOil != null) {
/* 233 */       spawnOilSprings = BuildCraftCore.mainConfigManager.get("worldgen.spawnOilSprings").getBoolean(true);
/* 234 */       BlockSpring.EnumSpring.OIL.canGen = spawnOilSprings;
/* 235 */       BlockSpring.EnumSpring.OIL.liquidBlock = blockOil;
/*     */     } 
/*     */     
/* 238 */     if (fluidFuel.getBlock() == null) {
/* 239 */       blockFuel = (Block)(new BlockBuildCraftFluid(fluidFuel, Material.field_151586_h, MapColor.field_151673_t)).setFlammable(true).setFlammability(5).setParticleColor(0.7F, 0.7F, 0.0F);
/* 240 */       blockFuel.func_149663_c("blockFuel").func_149713_g(3);
/* 241 */       BCRegistry.INSTANCE.registerBlock(blockFuel, true);
/* 242 */       fluidFuel.setBlock(blockFuel);
/*     */     } else {
/* 244 */       blockFuel = fluidFuel.getBlock();
/*     */     } 
/*     */     
/* 247 */     if (fluidRedPlasma.getBlock() == null) {
/*     */       
/* 249 */       blockRedPlasma = (Block)(new BlockBuildCraftFluid(fluidRedPlasma, Material.field_151586_h, MapColor.field_151645_D)).setFlammable(false).setParticleColor(0.9F, 0.0F, 0.0F);
/* 250 */       blockRedPlasma.func_149663_c("blockRedPlasma");
/* 251 */       BCRegistry.INSTANCE.registerBlock(blockRedPlasma, true);
/* 252 */       fluidRedPlasma.setBlock(blockRedPlasma);
/*     */     } else {
/* 254 */       blockRedPlasma = fluidRedPlasma.getBlock();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 259 */     if (blockOil != null) {
/* 260 */       bucketOil = (Item)new ItemBucketBuildcraft(blockOil);
/* 261 */       bucketOil.func_77655_b("bucketOil").func_77642_a(Items.field_151133_ar);
/* 262 */       BCRegistry.INSTANCE.registerItem(bucketOil, true);
/* 263 */       FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("oil", 1000), new ItemStack(bucketOil), new ItemStack(Items.field_151133_ar));
/*     */     } 
/*     */     
/* 266 */     if (blockFuel != null) {
/* 267 */       bucketFuel = (Item)new ItemBucketBuildcraft(blockFuel);
/* 268 */       bucketFuel.func_77655_b("bucketFuel").func_77642_a(Items.field_151133_ar);
/* 269 */       BCRegistry.INSTANCE.registerItem(bucketFuel, true);
/* 270 */       FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fuel", 1000), new ItemStack(bucketFuel), new ItemStack(Items.field_151133_ar));
/*     */     } 
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
/* 283 */     if (blockOil != null) {
/* 284 */       BucketHandler.INSTANCE.buckets.put(blockOil, bucketOil);
/*     */     }
/* 286 */     if (blockFuel != null) {
/* 287 */       BucketHandler.INSTANCE.buckets.put(blockFuel, bucketFuel);
/*     */     }
/* 289 */     MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
/*     */     
/* 291 */     BuildcraftRecipeRegistry.refinery.addRecipe("buildcraft:fuel", new FluidStack(fluidOil, 1), new FluidStack(fluidFuel, 1), 120, 1);
/*     */ 
/*     */     
/* 294 */     BuildcraftFuelRegistry.fuel.addFuel(fluidOil, fuelOilEnergyOutput, (int)(5000.0D * fuelOilMultiplier));
/* 295 */     BuildcraftFuelRegistry.fuel.addFuel(fluidFuel, fuelFuelEnergyOutput, (int)(25000.0D * fuelFuelMultiplier));
/*     */     
/* 297 */     BuildcraftFuelRegistry.coolant.addCoolant(FluidRegistry.WATER, 0.0023F);
/* 298 */     BuildcraftFuelRegistry.coolant.addSolidCoolant(StackKey.stack(Blocks.field_150432_aD), StackKey.fluid(FluidRegistry.WATER), 1.5F);
/* 299 */     BuildcraftFuelRegistry.coolant.addSolidCoolant(StackKey.stack(Blocks.field_150403_cj), StackKey.fluid(FluidRegistry.WATER), 2.0F);
/*     */     
/* 301 */     BuildCraftCore.engineBlock.registerTile(TileEngineStone.class, 1, "tile.engineStone", "buildcraftenergy:engineStone");
/* 302 */     BuildCraftCore.engineBlock.registerTile(TileEngineIron.class, 2, "tile.engineIron", "buildcraftenergy:engineIron");
/* 303 */     BuildCraftCore.engineBlock.registerTile(TileEngineCreative.class, 3, "tile.engineCreative", "buildcraftenergy:engineCreative");
/*     */     
/* 305 */     InterModComms.registerHandler((IMCHandler)new IMCHandlerEnergy());
/*     */     
/* 307 */     FMLCommonHandler.instance().bus().register(this);
/* 308 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   public void reloadConfig(ConfigManager.RestartRequirement restartType) {
/* 312 */     if (restartType == ConfigManager.RestartRequirement.GAME) {
/* 313 */       reloadConfig(ConfigManager.RestartRequirement.WORLD);
/* 314 */     } else if (restartType == ConfigManager.RestartRequirement.WORLD) {
/* 315 */       reloadConfig(ConfigManager.RestartRequirement.NONE);
/*     */     } else {
/* 317 */       oilWellScalar = BuildCraftCore.mainConfigManager.get("worldgen.oilWellGenerationRate").getDouble();
/*     */       
/* 319 */       if (blockOil instanceof BlockBuildCraftFluid) {
/* 320 */         canOilBurn = BuildCraftCore.mainConfigManager.get("general.oilCanBurn").getBoolean();
/* 321 */         isOilDense = BuildCraftCore.mainConfigManager.get("general.oilIsDense").getBoolean();
/* 322 */         ((BlockBuildCraftFluid)blockOil).setFlammable(canOilBurn).setDense(isOilDense);
/*     */       } 
/*     */       
/* 325 */       if (BuildCraftCore.mainConfiguration.hasChanged()) {
/* 326 */         BuildCraftCore.mainConfiguration.save();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConfigChanged(ConfigChangedEvent.PostConfigChangedEvent event) {
/* 333 */     if ("BuildCraft|Core".equals(event.modID)) {
/* 334 */       reloadConfig(event.isWorldRunning ? ConfigManager.RestartRequirement.NONE : ConfigManager.RestartRequirement.WORLD);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setBiomeList(Set<Integer> list, Property configuration) {
/* 339 */     for (String id : configuration.getStringList()) {
/* 340 */       String strippedId = JavaTools.stripSurroundingQuotes(id.trim());
/*     */       
/* 342 */       if (strippedId.length() > 0) {
/* 343 */         if (strippedId.matches("-?\\d+(\\.\\d+)?")) {
/*     */           try {
/* 345 */             list.add(Integer.valueOf(Integer.parseInt(strippedId)));
/* 346 */           } catch (NumberFormatException ex) {
/* 347 */             BCLog.logger
/* 348 */               .log(Level.WARN, configuration
/* 349 */                 .getName() + ": Could not find biome id: " + strippedId + " ; Skipping!");
/*     */           } 
/*     */         } else {
/*     */           
/* 353 */           boolean found = false;
/* 354 */           String biomeName = strippedId.toUpperCase();
/*     */           
/* 356 */           for (BiomeDictionary.Type t : BiomeDictionary.Type.values()) {
/* 357 */             String biomeType = t.name().toUpperCase();
/*     */             
/* 359 */             for (BiomeGenBase b : BiomeDictionary.getBiomesForType(t)) {
/* 360 */               if (b.field_76791_y.toUpperCase().equals(biomeName) || biomeType
/* 361 */                 .toUpperCase().equals(biomeName)) {
/* 362 */                 list.add(Integer.valueOf(b.field_76756_M));
/* 363 */                 found = true;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 369 */           if (!found) {
/* 370 */             BCLog.logger
/* 371 */               .log(Level.WARN, configuration
/* 372 */                 .getName() + ": Could not find biome id: " + strippedId + " ; Skipping!");
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void init(FMLInitializationEvent evt) {
/* 382 */     this
/* 383 */       .channels = NetworkRegistry.INSTANCE.newChannel("BC-ENERGY", new ChannelHandler[] { (ChannelHandler)new ChannelHandler(), (ChannelHandler)new PacketHandler() });
/*     */     
/* 385 */     NetworkRegistry.INSTANCE.registerGuiHandler(instance, (IGuiHandler)new EnergyGuiHandler());
/*     */     
/* 387 */     StatementManager.registerTriggerProvider((ITriggerProvider)new EnergyStatementProvider());
/*     */     
/* 389 */     if (BuildCraftCore.loadDefaultRecipes) {
/* 390 */       loadRecipes();
/*     */     }
/*     */     
/* 393 */     EnergyProxy.proxy.registerBlockRenderers();
/* 394 */     EnergyProxy.proxy.registerTileEntities();
/*     */     
/* 396 */     engineAchievement2 = BuildCraftCore.achievementManager.registerAchievement(new Achievement("achievement.stirlingEngine", "engineAchievement2", 3, -2, new ItemStack((Block)BuildCraftCore.engineBlock, 1, 1), BuildCraftCore.engineRedstoneAchievement));
/* 397 */     engineAchievement3 = BuildCraftCore.achievementManager.registerAchievement(new Achievement("achievement.combustionEngine", "engineAchievement3", 5, -2, new ItemStack((Block)BuildCraftCore.engineBlock, 1, 2), engineAchievement2));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void postInit(FMLPostInitializationEvent evt) {
/* 402 */     if (BuildCraftCore.modifyWorld) {
/* 403 */       MinecraftForge.EVENT_BUS.register(OilPopulate.INSTANCE);
/* 404 */       MinecraftForge.TERRAIN_GEN_BUS.register(new BiomeInitializer());
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void textureHook(TextureStitchEvent.Post event) {
/* 411 */     if (event.map.func_130086_a() == 0) {
/* 412 */       if (buildcraftFluidOil != null) {
/* 413 */         buildcraftFluidOil.setIcons(blockOil.func_149733_h(1), blockOil.func_149733_h(2));
/*     */       }
/* 415 */       if (buildcraftFluidFuel != null) {
/* 416 */         buildcraftFluidFuel.setIcons(blockFuel.func_149733_h(1), blockFuel.func_149733_h(2));
/*     */       }
/* 418 */       if (buildcraftFluidRedPlasma != null) {
/* 419 */         buildcraftFluidRedPlasma.setIcons(blockRedPlasma.func_149733_h(1), blockRedPlasma.func_149733_h(2));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void loadRecipes() {
/* 425 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)BuildCraftCore.engineBlock, 1, 1), new Object[] { "www", " g ", "GpG", 
/* 426 */           Character.valueOf('w'), "cobblestone", 
/* 427 */           Character.valueOf('g'), "blockGlass", Character.valueOf('G'), "gearStone", Character.valueOf('p'), Blocks.field_150331_J });
/* 428 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)BuildCraftCore.engineBlock, 1, 2), new Object[] { "www", " g ", "GpG", 
/* 429 */           Character.valueOf('w'), "ingotIron", 
/* 430 */           Character.valueOf('g'), "blockGlass", Character.valueOf('G'), "gearIron", Character.valueOf('p'), Blocks.field_150331_J });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findUnusedBiomeID(String biomeName) {
/* 436 */     for (int i = 1; i < 256; i++) {
/* 437 */       if (BiomeGenBase.func_150565_n()[i] == null) {
/* 438 */         int freeBiomeID = i;
/* 439 */         return freeBiomeID;
/*     */       } 
/*     */     } 
/*     */     class BiomeIdLimitException
/*     */       extends RuntimeException {
/*     */       private static final long serialVersionUID = 1L;
/*     */       
/*     */       public BiomeIdLimitException(String biome) {
/* 447 */         super(String.format("You have run out of free Biome ID spaces for %s - free more Biome IDs or disable the biome by setting the ID to 0!", new Object[] { biome }));
/*     */       }
/*     */     };
/*     */     
/* 451 */     throw new BiomeIdLimitException(biomeName);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void processIMCRequests(FMLInterModComms.IMCEvent event) {
/* 456 */     InterModComms.processIMC(event);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void whiteListAppliedEnergetics(FMLInitializationEvent event) {
/* 461 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileEngineBase.class
/* 462 */         .getCanonicalName());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void remap(FMLMissingMappingsEvent event) {
/* 467 */     for (FMLMissingMappingsEvent.MissingMapping mapping : event.get()) {
/* 468 */       if (mapping.name.equals("BuildCraft|Energy:engineBlock")) {
/* 469 */         if (mapping.type == GameRegistry.Type.BLOCK) {
/* 470 */           mapping.remap((Block)BuildCraftCore.engineBlock); continue;
/* 471 */         }  if (mapping.type == GameRegistry.Type.ITEM)
/* 472 */           mapping.remap(Item.func_150898_a((Block)BuildCraftCore.engineBlock)); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\BuildCraftEnergy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */