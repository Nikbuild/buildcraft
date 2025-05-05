/*     */ package buildcraft;
/*     */ 
/*     */ import buildcraft.api.blueprints.BlueprintDeployer;
/*     */ import buildcraft.api.blueprints.BuilderAPI;
/*     */ import buildcraft.api.blueprints.ISchematicRegistry;
/*     */ import buildcraft.api.blueprints.SchematicBlock;
/*     */ import buildcraft.api.blueprints.SchematicEntity;
/*     */ import buildcraft.api.blueprints.SchematicFactory;
/*     */ import buildcraft.api.blueprints.SchematicMask;
/*     */ import buildcraft.api.blueprints.SchematicTile;
/*     */ import buildcraft.api.core.JavaTools;
/*     */ import buildcraft.api.library.LibraryAPI;
/*     */ import buildcraft.api.library.LibraryTypeHandler;
/*     */ import buildcraft.api.statements.IActionProvider;
/*     */ import buildcraft.api.statements.StatementManager;
/*     */ import buildcraft.builders.BlockArchitect;
/*     */ import buildcraft.builders.BlockBlueprintLibrary;
/*     */ import buildcraft.builders.BlockBuilder;
/*     */ import buildcraft.builders.BlockConstructionMarker;
/*     */ import buildcraft.builders.BlockFiller;
/*     */ import buildcraft.builders.BlockFrame;
/*     */ import buildcraft.builders.BlockQuarry;
/*     */ import buildcraft.builders.BlueprintServerDatabase;
/*     */ import buildcraft.builders.BuilderProxy;
/*     */ import buildcraft.builders.BuilderProxyClient;
/*     */ import buildcraft.builders.BuilderTooltipHandler;
/*     */ import buildcraft.builders.BuildersGuiHandler;
/*     */ import buildcraft.builders.HeuristicBlockDetection;
/*     */ import buildcraft.builders.ItemBlueprintStandard;
/*     */ import buildcraft.builders.ItemBlueprintTemplate;
/*     */ import buildcraft.builders.ItemConstructionMarker;
/*     */ import buildcraft.builders.LibraryBlueprintTypeHandler;
/*     */ import buildcraft.builders.LibraryBookTypeHandler;
/*     */ import buildcraft.builders.LibraryDatabase;
/*     */ import buildcraft.builders.TileArchitect;
/*     */ import buildcraft.builders.TileBlueprintLibrary;
/*     */ import buildcraft.builders.TileBuilder;
/*     */ import buildcraft.builders.TileConstructionMarker;
/*     */ import buildcraft.builders.TileFiller;
/*     */ import buildcraft.builders.TileQuarry;
/*     */ import buildcraft.builders.blueprints.RealBlueprintDeployer;
/*     */ import buildcraft.builders.schematics.SchematicAir;
/*     */ import buildcraft.builders.schematics.SchematicBed;
/*     */ import buildcraft.builders.schematics.SchematicBrewingStand;
/*     */ import buildcraft.builders.schematics.SchematicBuilderLike;
/*     */ import buildcraft.builders.schematics.SchematicCactus;
/*     */ import buildcraft.builders.schematics.SchematicCustomStack;
/*     */ import buildcraft.builders.schematics.SchematicDirt;
/*     */ import buildcraft.builders.schematics.SchematicDoor;
/*     */ import buildcraft.builders.schematics.SchematicEnderChest;
/*     */ import buildcraft.builders.schematics.SchematicFactoryBlock;
/*     */ import buildcraft.builders.schematics.SchematicFactoryEntity;
/*     */ import buildcraft.builders.schematics.SchematicFactoryMask;
/*     */ import buildcraft.builders.schematics.SchematicFarmland;
/*     */ import buildcraft.builders.schematics.SchematicFire;
/*     */ import buildcraft.builders.schematics.SchematicGlassPane;
/*     */ import buildcraft.builders.schematics.SchematicGravel;
/*     */ import buildcraft.builders.schematics.SchematicHanging;
/*     */ import buildcraft.builders.schematics.SchematicJukebox;
/*     */ import buildcraft.builders.schematics.SchematicMetadataMask;
/*     */ import buildcraft.builders.schematics.SchematicMinecart;
/*     */ import buildcraft.builders.schematics.SchematicPiston;
/*     */ import buildcraft.builders.schematics.SchematicPortal;
/*     */ import buildcraft.builders.schematics.SchematicPumpkin;
/*     */ import buildcraft.builders.schematics.SchematicRail;
/*     */ import buildcraft.builders.schematics.SchematicRedstoneDiode;
/*     */ import buildcraft.builders.schematics.SchematicRedstoneLamp;
/*     */ import buildcraft.builders.schematics.SchematicRedstoneWire;
/*     */ import buildcraft.builders.schematics.SchematicSeeds;
/*     */ import buildcraft.builders.schematics.SchematicSign;
/*     */ import buildcraft.builders.schematics.SchematicSilverfish;
/*     */ import buildcraft.builders.schematics.SchematicSkull;
/*     */ import buildcraft.builders.schematics.SchematicStone;
/*     */ import buildcraft.builders.schematics.SchematicTripWireHook;
/*     */ import buildcraft.builders.schematics.SchematicTripwire;
/*     */ import buildcraft.builders.statements.BuildersActionProvider;
/*     */ import buildcraft.core.BCRegistry;
/*     */ import buildcraft.core.CompatHooks;
/*     */ import buildcraft.core.DefaultProps;
/*     */ import buildcraft.core.InterModComms;
/*     */ import buildcraft.core.TileMarker;
/*     */ import buildcraft.core.TilePathMarker;
/*     */ import buildcraft.core.blueprints.SchematicRegistry;
/*     */ import buildcraft.core.builders.schematics.SchematicBlockCreative;
/*     */ import buildcraft.core.builders.schematics.SchematicFree;
/*     */ import buildcraft.core.builders.schematics.SchematicIgnore;
/*     */ import buildcraft.core.builders.schematics.SchematicRotateMeta;
/*     */ import buildcraft.core.builders.schematics.SchematicRotateMetaSupported;
/*     */ import buildcraft.core.builders.schematics.SchematicTileCreative;
/*     */ import buildcraft.core.config.ConfigManager;
/*     */ import com.google.common.collect.Lists;
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
/*     */ import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
/*     */ import cpw.mods.fml.common.event.FMLServerStoppingEvent;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.network.IGuiHandler;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecartChest;
/*     */ import net.minecraft.entity.item.EntityMinecartEmpty;
/*     */ import net.minecraft.entity.item.EntityMinecartFurnace;
/*     */ import net.minecraft.entity.item.EntityMinecartHopper;
/*     */ import net.minecraft.entity.item.EntityMinecartTNT;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.launchwrapper.Launch;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.TextureStitchEvent;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ import net.minecraftforge.common.ForgeChunkManager;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.config.Configuration;
/*     */ import net.minecraftforge.common.config.Property;
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
/*     */ @Mod(name = "BuildCraft Builders", version = "7.1.26", useMetadata = false, modid = "BuildCraft|Builders", dependencies = "required-after:BuildCraft|Core@7.1.26")
/*     */ public class BuildCraftBuilders
/*     */   extends BuildCraftMod
/*     */ {
/*     */   @Instance("BuildCraft|Builders")
/*     */   public static BuildCraftBuilders instance;
/*     */   public static BlockConstructionMarker constructionMarkerBlock;
/*     */   public static BlockFiller fillerBlock;
/*     */   public static BlockBuilder builderBlock;
/*     */   public static BlockArchitect architectBlock;
/*     */   public static BlockBlueprintLibrary libraryBlock;
/*     */   public static BlockQuarry quarryBlock;
/*     */   public static BlockFrame frameBlock;
/*     */   public static ItemBlueprintTemplate templateItem;
/*     */   public static ItemBlueprintStandard blueprintItem;
/*     */   public static Achievement architectAchievement;
/*     */   public static Achievement libraryAchievement;
/*     */   public static Achievement blueprintAchievement;
/*     */   public static Achievement builderAchievement;
/*     */   public static Achievement templateAchievement;
/*     */   public static Achievement chunkDestroyerAchievement;
/*     */   public static BlueprintServerDatabase serverDB;
/*     */   public static LibraryDatabase clientDB;
/*     */   public static boolean debugPrintSchematicList = false;
/*     */   public static boolean dropBrokenBlocks = false;
/*     */   public static boolean quarryLoadsChunks = true;
/*     */   public static boolean quarryOneTimeUse = false;
/*     */   private String oldBlueprintServerDir;
/*     */   private String blueprintClientDir;
/*     */   
/*     */   public class QuarryChunkloadCallback
/*     */     implements ForgeChunkManager.OrderedLoadingCallback
/*     */   {
/*     */     public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
/* 182 */       for (ForgeChunkManager.Ticket ticket : tickets) {
/* 183 */         int quarryX = ticket.getModData().func_74762_e("quarryX");
/* 184 */         int quarryY = ticket.getModData().func_74762_e("quarryY");
/* 185 */         int quarryZ = ticket.getModData().func_74762_e("quarryZ");
/*     */         
/* 187 */         Block block = world.func_147439_a(quarryX, quarryY, quarryZ);
/* 188 */         if (block == BuildCraftBuilders.quarryBlock) {
/* 189 */           TileQuarry tq = (TileQuarry)world.func_147438_o(quarryX, quarryY, quarryZ);
/* 190 */           tq.forceChunkLoading(ticket);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public List<ForgeChunkManager.Ticket> ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world, int maxTicketCount) {
/* 197 */       List<ForgeChunkManager.Ticket> validTickets = Lists.newArrayList();
/* 198 */       for (ForgeChunkManager.Ticket ticket : tickets) {
/* 199 */         int quarryX = ticket.getModData().func_74762_e("quarryX");
/* 200 */         int quarryY = ticket.getModData().func_74762_e("quarryY");
/* 201 */         int quarryZ = ticket.getModData().func_74762_e("quarryZ");
/*     */         
/* 203 */         Block block = world.func_147439_a(quarryX, quarryY, quarryZ);
/* 204 */         if (block == BuildCraftBuilders.quarryBlock) {
/* 205 */           validTickets.add(ticket);
/*     */         }
/*     */       } 
/* 208 */       return validTickets;
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void loadConfiguration(FMLPreInitializationEvent evt) {
/* 214 */     BuildCraftCore.mainConfigManager.register("blueprints.serverDatabaseDirectory", "\"$MINECRAFT" + File.separator + "config" + File.separator + "buildcraft" + File.separator + "blueprints" + File.separator + "server\"", "DEPRECATED - USED ONLY FOR COMPATIBILITY", ConfigManager.RestartRequirement.GAME);
/*     */ 
/*     */ 
/*     */     
/* 218 */     BuildCraftCore.mainConfigManager.register("blueprints.clientDatabaseDirectory", "\"$MINECRAFT" + File.separator + "blueprints\"", "Location for the client blueprint database (used by the Electronic Library).", ConfigManager.RestartRequirement.NONE);
/*     */ 
/*     */ 
/*     */     
/* 222 */     BuildCraftCore.mainConfigManager.register("general.markerRange", Integer.valueOf(64), "Set the maximum marker range.", ConfigManager.RestartRequirement.NONE);
/* 223 */     BuildCraftCore.mainConfigManager.register("general.quarry.oneTimeUse", Boolean.valueOf(false), "Should the quarry only be usable once after placing?", ConfigManager.RestartRequirement.NONE);
/* 224 */     BuildCraftCore.mainConfigManager.register("general.quarry.doChunkLoading", Boolean.valueOf(true), "Should the quarry keep the chunks it is working on loaded?", ConfigManager.RestartRequirement.NONE);
/*     */     
/* 226 */     BuildCraftCore.mainConfigManager.register("builders.dropBrokenBlocks", Boolean.valueOf(false), "Should the builder and filler drop the cleared blocks?", ConfigManager.RestartRequirement.NONE);
/*     */     
/* 228 */     BuildCraftCore.mainConfigManager.get("blueprints.serverDatabaseDirectory").setShowInGui(false);
/* 229 */     BuildCraftCore.mainConfigManager.get("general.markerRange").setMinValue(8).setMaxValue(64);
/*     */     
/* 231 */     serverDB = new BlueprintServerDatabase();
/* 232 */     clientDB = new LibraryDatabase();
/*     */     
/* 234 */     reloadConfig(ConfigManager.RestartRequirement.GAME);
/*     */     
/* 236 */     Property printSchematicList = BuildCraftCore.mainConfiguration.get("debug", "printBlueprintSchematicList", false);
/* 237 */     debugPrintSchematicList = printSchematicList.getBoolean();
/*     */   }
/*     */   
/*     */   public void reloadConfig(ConfigManager.RestartRequirement restartType) {
/* 241 */     if (restartType == ConfigManager.RestartRequirement.GAME) {
/* 242 */       reloadConfig(ConfigManager.RestartRequirement.WORLD);
/* 243 */     } else if (restartType == ConfigManager.RestartRequirement.WORLD) {
/* 244 */       this.oldBlueprintServerDir = BuildCraftCore.mainConfigManager.get("blueprints.serverDatabaseDirectory").getString();
/* 245 */       this.oldBlueprintServerDir = JavaTools.stripSurroundingQuotes(replacePathVariables(this.oldBlueprintServerDir));
/*     */       
/* 247 */       reloadConfig(ConfigManager.RestartRequirement.NONE);
/*     */     } else {
/* 249 */       quarryOneTimeUse = BuildCraftCore.mainConfigManager.get("general.quarry.oneTimeUse").getBoolean();
/* 250 */       quarryLoadsChunks = BuildCraftCore.mainConfigManager.get("general.quarry.doChunkLoading").getBoolean();
/*     */       
/* 252 */       this.blueprintClientDir = BuildCraftCore.mainConfigManager.get("blueprints.clientDatabaseDirectory").getString();
/* 253 */       this.blueprintClientDir = JavaTools.stripSurroundingQuotes(replacePathVariables(this.blueprintClientDir));
/* 254 */       clientDB.init(new String[] { this.blueprintClientDir, 
/*     */             
/* 256 */             getDownloadsDir() }, this.blueprintClientDir);
/*     */ 
/*     */       
/* 259 */       DefaultProps.MARKER_RANGE = BuildCraftCore.mainConfigManager.get("general.markerRange").getInt();
/*     */       
/* 261 */       dropBrokenBlocks = BuildCraftCore.mainConfigManager.get("builders.dropBrokenBlocks").getBoolean();
/*     */       
/* 263 */       if (BuildCraftCore.mainConfiguration.hasChanged()) {
/* 264 */         BuildCraftCore.mainConfiguration.save();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConfigChanged(ConfigChangedEvent.PostConfigChangedEvent event) {
/* 271 */     if ("BuildCraft|Core".equals(event.modID)) {
/* 272 */       reloadConfig(event.isWorldRunning ? ConfigManager.RestartRequirement.NONE : ConfigManager.RestartRequirement.WORLD);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String getDownloadsDir() {
/* 277 */     String os = System.getProperty("os.name").toLowerCase();
/*     */     
/* 279 */     if (os.contains("nix") || os.contains("lin") || os.contains("mac")) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 284 */         Process process = Runtime.getRuntime().exec(new String[] { "xdg-user-dir", "DOWNLOAD" });
/* 285 */         BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
/* 286 */         process.waitFor();
/* 287 */         String line = reader.readLine().trim();
/* 288 */         reader.close();
/*     */         
/* 290 */         if (line.length() > 0) {
/* 291 */           return line;
/*     */         }
/* 293 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     return "$HOME" + File.separator + "Downloads";
/*     */   }
/*     */   
/*     */   private String replacePathVariables(String path) {
/* 303 */     String result = path.replace("$DOWNLOADS", getDownloadsDir());
/* 304 */     result = result.replace("$HOME", System.getProperty("user.home"));
/*     */     
/* 306 */     if (Launch.minecraftHome == null) {
/* 307 */       result = result.replace("$MINECRAFT", (new File(".")).getAbsolutePath());
/*     */     } else {
/* 309 */       result = result.replace("$MINECRAFT", Launch.minecraftHome.getAbsolutePath());
/*     */     } 
/*     */     
/* 312 */     if ("/".equals(File.separator)) {
/* 313 */       result = result.replaceAll("\\\\", "/");
/*     */     } else {
/* 315 */       result = result.replaceAll("/", "\\\\");
/*     */     } 
/*     */     
/* 318 */     return result;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void postInit(FMLPostInitializationEvent evt) {
/* 323 */     HeuristicBlockDetection.start();
/* 324 */     ForgeChunkManager.setForcedChunkLoadingCallback(instance, (ForgeChunkManager.LoadingCallback)new QuarryChunkloadCallback());
/*     */     
/* 326 */     if (debugPrintSchematicList) {
/*     */       try {
/* 328 */         PrintWriter writer = new PrintWriter("SchematicDebug.txt", "UTF-8");
/* 329 */         writer.println("*** REGISTERED SCHEMATICS ***");
/* 330 */         SchematicRegistry reg = (SchematicRegistry)BuilderAPI.schematicRegistry;
/* 331 */         for (String s : reg.schematicBlocks.keySet()) {
/* 332 */           writer.println(s + " -> " + ((SchematicRegistry.SchematicConstructor)reg.schematicBlocks.get(s)).clazz.getCanonicalName());
/*     */         }
/* 334 */         writer.close();
/* 335 */       } catch (Exception e) {
/* 336 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 342 */     clientDB.refresh();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void init(FMLInitializationEvent evt) {
/* 347 */     NetworkRegistry.INSTANCE.registerGuiHandler(instance, (IGuiHandler)new BuildersGuiHandler());
/*     */     
/* 349 */     MinecraftForge.EVENT_BUS.register(new BuilderTooltipHandler());
/*     */ 
/*     */     
/* 352 */     ISchematicRegistry schemes = BuilderAPI.schematicRegistry;
/* 353 */     schemes.registerSchematicBlock(Blocks.field_150350_a, SchematicAir.class, new Object[0]);
/*     */     
/* 355 */     schemes.registerSchematicBlock(Blocks.field_150433_aE, SchematicIgnore.class, new Object[0]);
/* 356 */     schemes.registerSchematicBlock((Block)Blocks.field_150329_H, SchematicIgnore.class, new Object[0]);
/* 357 */     schemes.registerSchematicBlock((Block)Blocks.field_150398_cm, SchematicIgnore.class, new Object[0]);
/* 358 */     schemes.registerSchematicBlock(Blocks.field_150432_aD, SchematicIgnore.class, new Object[0]);
/* 359 */     schemes.registerSchematicBlock((Block)Blocks.field_150332_K, SchematicIgnore.class, new Object[0]);
/*     */     
/* 361 */     schemes.registerSchematicBlock(Blocks.field_150346_d, SchematicDirt.class, new Object[0]);
/* 362 */     schemes.registerSchematicBlock((Block)Blocks.field_150349_c, SchematicDirt.class, new Object[0]);
/*     */     
/* 364 */     schemes.registerSchematicBlock(Blocks.field_150434_aF, SchematicCactus.class, new Object[0]);
/*     */     
/* 366 */     schemes.registerSchematicBlock(Blocks.field_150458_ak, SchematicFarmland.class, new Object[0]);
/* 367 */     schemes.registerSchematicBlock(Blocks.field_150464_aj, SchematicSeeds.class, new Object[] { Items.field_151014_N });
/* 368 */     schemes.registerSchematicBlock(Blocks.field_150393_bb, SchematicSeeds.class, new Object[] { Items.field_151080_bb });
/* 369 */     schemes.registerSchematicBlock(Blocks.field_150394_bc, SchematicSeeds.class, new Object[] { Items.field_151081_bc });
/* 370 */     schemes.registerSchematicBlock(Blocks.field_150388_bm, SchematicSeeds.class, new Object[] { Items.field_151075_bm });
/*     */     
/* 372 */     schemes.registerSchematicBlock(Blocks.field_150457_bL, SchematicTile.class, new Object[0]);
/*     */     
/* 374 */     schemes.registerSchematicBlock(Blocks.field_150473_bD, SchematicTripwire.class, new Object[0]);
/* 375 */     schemes.registerSchematicBlock((Block)Blocks.field_150479_bC, SchematicTripWireHook.class, new Object[0]);
/*     */     
/* 377 */     schemes.registerSchematicBlock(Blocks.field_150465_bP, SchematicSkull.class, new Object[0]);
/*     */     
/* 379 */     schemes.registerSchematicBlock(Blocks.field_150468_ap, SchematicRotateMetaSupported.class, new Object[] { { 2, 5, 3, 4 }, Boolean.valueOf(true) });
/* 380 */     schemes.registerSchematicBlock(Blocks.field_150396_be, SchematicRotateMeta.class, new Object[] { { 0, 1, 2, 3 }, Boolean.valueOf(true) });
/* 381 */     schemes.registerSchematicBlock(Blocks.field_150364_r, SchematicRotateMeta.class, new Object[] { { 8, 4, 8, 4 }, Boolean.valueOf(true) });
/* 382 */     schemes.registerSchematicBlock(Blocks.field_150363_s, SchematicRotateMeta.class, new Object[] { { 8, 4, 8, 4 }, Boolean.valueOf(true) });
/* 383 */     schemes.registerSchematicBlock(Blocks.field_150407_cf, SchematicRotateMeta.class, new Object[] { { 8, 4, 8, 4 }, Boolean.valueOf(true) });
/* 384 */     schemes.registerSchematicBlock(Blocks.field_150371_ca, SchematicRotateMeta.class, new Object[] { { 4, 3, 4, 3 }, Boolean.valueOf(true) });
/* 385 */     schemes.registerSchematicBlock((Block)Blocks.field_150438_bZ, SchematicRotateMeta.class, new Object[] { { 2, 5, 3, 4 }, Boolean.valueOf(true) });
/* 386 */     schemes.registerSchematicBlock(Blocks.field_150467_bQ, SchematicRotateMeta.class, new Object[] { { 0, 1, 2, 3 }, Boolean.valueOf(true) });
/*     */     
/* 388 */     schemes.registerSchematicBlock(Blocks.field_150460_al, SchematicRotateMeta.class, new Object[] { { 2, 5, 3, 4 }, Boolean.valueOf(true) });
/* 389 */     schemes.registerSchematicBlock(Blocks.field_150470_am, SchematicRotateMeta.class, new Object[] { { 2, 5, 3, 4 }, Boolean.valueOf(true) });
/* 390 */     schemes.registerSchematicBlock((Block)Blocks.field_150486_ae, SchematicRotateMeta.class, new Object[] { { 2, 5, 3, 4 }, Boolean.valueOf(true) });
/* 391 */     schemes.registerSchematicBlock(Blocks.field_150447_bR, SchematicRotateMeta.class, new Object[] { { 2, 5, 3, 4 }, Boolean.valueOf(true) });
/* 392 */     schemes.registerSchematicBlock(Blocks.field_150367_z, SchematicRotateMeta.class, new Object[] { { 2, 5, 3, 4 }, Boolean.valueOf(true) });
/* 393 */     schemes.registerSchematicBlock(Blocks.field_150409_cd, SchematicRotateMeta.class, new Object[] { { 2, 5, 3, 4 }, Boolean.valueOf(true) });
/*     */     
/* 395 */     schemes.registerSchematicBlock(Blocks.field_150477_bB, SchematicEnderChest.class, new Object[0]);
/*     */     
/* 397 */     schemes.registerSchematicBlock(Blocks.field_150395_bd, SchematicRotateMeta.class, new Object[] { { 1, 4, 8, 2 }, Boolean.valueOf(false) });
/* 398 */     schemes.registerSchematicBlock(Blocks.field_150415_aT, SchematicRotateMeta.class, new Object[] { { 0, 1, 2, 3 }, Boolean.valueOf(false) });
/*     */     
/* 400 */     schemes.registerSchematicBlock(Blocks.field_150348_b, SchematicStone.class, new Object[0]);
/* 401 */     schemes.registerSchematicBlock(Blocks.field_150365_q, SchematicStone.class, new Object[0]);
/* 402 */     schemes.registerSchematicBlock(Blocks.field_150369_x, SchematicStone.class, new Object[0]);
/* 403 */     schemes.registerSchematicBlock(Blocks.field_150482_ag, SchematicStone.class, new Object[0]);
/* 404 */     schemes.registerSchematicBlock(Blocks.field_150450_ax, SchematicStone.class, new Object[0]);
/* 405 */     schemes.registerSchematicBlock(Blocks.field_150439_ay, SchematicStone.class, new Object[0]);
/* 406 */     schemes.registerSchematicBlock(Blocks.field_150412_bA, SchematicStone.class, new Object[0]);
/*     */     
/* 408 */     schemes.registerSchematicBlock((Block)Blocks.field_150362_t, SchematicMetadataMask.class, new Object[] { Integer.valueOf(3) });
/* 409 */     schemes.registerSchematicBlock((Block)Blocks.field_150361_u, SchematicMetadataMask.class, new Object[] { Integer.valueOf(3) });
/* 410 */     schemes.registerSchematicBlock(Blocks.field_150345_g, SchematicMetadataMask.class, new Object[] { Integer.valueOf(7) });
/*     */     
/* 412 */     schemes.registerSchematicBlock(Blocks.field_150418_aU, SchematicSilverfish.class, new Object[0]);
/*     */     
/* 414 */     schemes.registerSchematicBlock(Blocks.field_150351_n, SchematicGravel.class, new Object[0]);
/*     */     
/* 416 */     schemes.registerSchematicBlock((Block)Blocks.field_150488_af, SchematicRedstoneWire.class, new Object[] { new ItemStack(Items.field_151137_ax) });
/* 417 */     schemes.registerSchematicBlock(Blocks.field_150414_aQ, SchematicCustomStack.class, new Object[] { new ItemStack(Items.field_151105_aU) });
/* 418 */     schemes.registerSchematicBlock(Blocks.field_150426_aN, SchematicCustomStack.class, new Object[] { new ItemStack(Blocks.field_150426_aN) });
/*     */     
/* 420 */     schemes.registerSchematicBlock((Block)Blocks.field_150416_aS, SchematicRedstoneDiode.class, new Object[] { Items.field_151107_aW });
/* 421 */     schemes.registerSchematicBlock((Block)Blocks.field_150413_aR, SchematicRedstoneDiode.class, new Object[] { Items.field_151107_aW });
/* 422 */     schemes.registerSchematicBlock((Block)Blocks.field_150455_bV, SchematicRedstoneDiode.class, new Object[] { Items.field_151132_bS });
/* 423 */     schemes.registerSchematicBlock((Block)Blocks.field_150441_bU, SchematicRedstoneDiode.class, new Object[] { Items.field_151132_bS });
/*     */     
/* 425 */     schemes.registerSchematicBlock((Block)Blocks.field_150453_bW, SchematicTile.class, new Object[0]);
/* 426 */     schemes.registerSchematicBlock(Blocks.field_150421_aI, SchematicJukebox.class, new Object[0]);
/* 427 */     schemes.registerSchematicBlock(Blocks.field_150323_B, SchematicTile.class, new Object[0]);
/*     */     
/* 429 */     schemes.registerSchematicBlock(Blocks.field_150379_bu, SchematicRedstoneLamp.class, new Object[0]);
/* 430 */     schemes.registerSchematicBlock(Blocks.field_150374_bv, SchematicRedstoneLamp.class, new Object[0]);
/*     */     
/* 432 */     schemes.registerSchematicBlock(Blocks.field_150410_aZ, SchematicGlassPane.class, new Object[0]);
/* 433 */     schemes.registerSchematicBlock((Block)Blocks.field_150397_co, SchematicGlassPane.class, new Object[0]);
/*     */     
/* 435 */     schemes.registerSchematicBlock((Block)Blocks.field_150331_J, SchematicPiston.class, new Object[0]);
/* 436 */     schemes.registerSchematicBlock((Block)Blocks.field_150326_M, SchematicPiston.class, new Object[0]);
/* 437 */     schemes.registerSchematicBlock((Block)Blocks.field_150320_F, SchematicPiston.class, new Object[0]);
/*     */     
/* 439 */     schemes.registerSchematicBlock(Blocks.field_150428_aP, SchematicPumpkin.class, new Object[0]);
/*     */     
/* 441 */     schemes.registerSchematicBlock(Blocks.field_150466_ao, SchematicDoor.class, new Object[] { new ItemStack(Items.field_151135_aq) });
/* 442 */     schemes.registerSchematicBlock(Blocks.field_150454_av, SchematicDoor.class, new Object[] { new ItemStack(Items.field_151139_aw) });
/*     */     
/* 444 */     schemes.registerSchematicBlock(Blocks.field_150324_C, SchematicBed.class, new Object[0]);
/*     */     
/* 446 */     schemes.registerSchematicBlock(Blocks.field_150444_as, SchematicSign.class, new Object[] { Boolean.valueOf(true) });
/* 447 */     schemes.registerSchematicBlock(Blocks.field_150472_an, SchematicSign.class, new Object[] { Boolean.valueOf(false) });
/*     */     
/* 449 */     schemes.registerSchematicBlock((Block)Blocks.field_150427_aO, SchematicPortal.class, new Object[0]);
/*     */     
/* 451 */     schemes.registerSchematicBlock(Blocks.field_150448_aq, SchematicRail.class, new Object[0]);
/* 452 */     schemes.registerSchematicBlock(Blocks.field_150408_cc, SchematicRail.class, new Object[0]);
/* 453 */     schemes.registerSchematicBlock(Blocks.field_150319_E, SchematicRail.class, new Object[0]);
/* 454 */     schemes.registerSchematicBlock(Blocks.field_150318_D, SchematicRail.class, new Object[0]);
/*     */     
/* 456 */     schemes.registerSchematicBlock((Block)Blocks.field_150461_bJ, SchematicTile.class, new Object[0]);
/* 457 */     schemes.registerSchematicBlock(Blocks.field_150382_bo, SchematicBrewingStand.class, new Object[0]);
/* 458 */     schemes.registerSchematicBlock(Blocks.field_150381_bn, SchematicTile.class, new Object[0]);
/*     */     
/* 460 */     schemes.registerSchematicBlock((Block)Blocks.field_150480_ab, SchematicFire.class, new Object[0]);
/*     */     
/* 462 */     schemes.registerSchematicBlock(Blocks.field_150357_h, SchematicBlockCreative.class, new Object[0]);
/*     */     
/* 464 */     schemes.registerSchematicBlock(Blocks.field_150483_bI, SchematicTileCreative.class, new Object[0]);
/* 465 */     schemes.registerSchematicBlock(Blocks.field_150474_ac, SchematicTileCreative.class, new Object[0]);
/*     */ 
/*     */ 
/*     */     
/* 469 */     schemes.registerSchematicEntity(EntityMinecartEmpty.class, SchematicMinecart.class, new Object[] { Items.field_151143_au });
/* 470 */     schemes.registerSchematicEntity(EntityMinecartFurnace.class, SchematicMinecart.class, new Object[] { Items.field_151109_aJ });
/* 471 */     schemes.registerSchematicEntity(EntityMinecartTNT.class, SchematicMinecart.class, new Object[] { Items.field_151142_bV });
/* 472 */     schemes.registerSchematicEntity(EntityMinecartChest.class, SchematicMinecart.class, new Object[] { Items.field_151108_aI });
/* 473 */     schemes.registerSchematicEntity(EntityMinecartHopper.class, SchematicMinecart.class, new Object[] { Items.field_151140_bW });
/*     */     
/* 475 */     schemes.registerSchematicEntity(EntityPainting.class, SchematicHanging.class, new Object[] { Items.field_151159_an });
/* 476 */     schemes.registerSchematicEntity(EntityItemFrame.class, SchematicHanging.class, new Object[] { Items.field_151160_bD });
/*     */ 
/*     */     
/* 479 */     schemes.registerSchematicBlock((Block)architectBlock, SchematicRotateMeta.class, new Object[] { { 2, 5, 3, 4 }, Boolean.valueOf(true) });
/* 480 */     schemes.registerSchematicBlock((Block)builderBlock, SchematicBuilderLike.class, new Object[0]);
/* 481 */     schemes.registerSchematicBlock((Block)fillerBlock, SchematicBuilderLike.class, new Object[0]);
/* 482 */     schemes.registerSchematicBlock((Block)libraryBlock, SchematicRotateMeta.class, new Object[] { { 2, 5, 3, 4 }, Boolean.valueOf(true) });
/* 483 */     schemes.registerSchematicBlock((Block)quarryBlock, SchematicBuilderLike.class, new Object[0]);
/*     */     
/* 485 */     if (constructionMarkerBlock != null) {
/* 486 */       schemes.registerSchematicBlock((Block)constructionMarkerBlock, SchematicIgnore.class, new Object[0]);
/*     */     }
/*     */     
/* 489 */     schemes.registerSchematicBlock((Block)frameBlock, SchematicFree.class, new Object[0]);
/*     */ 
/*     */ 
/*     */     
/* 493 */     SchematicFactory.registerSchematicFactory(SchematicBlock.class, (SchematicFactory)new SchematicFactoryBlock());
/* 494 */     SchematicFactory.registerSchematicFactory(SchematicMask.class, (SchematicFactory)new SchematicFactoryMask());
/* 495 */     SchematicFactory.registerSchematicFactory(SchematicEntity.class, (SchematicFactory)new SchematicFactoryEntity());
/*     */     
/* 497 */     LibraryAPI.registerHandler((LibraryTypeHandler)new LibraryBlueprintTypeHandler(false));
/* 498 */     LibraryAPI.registerHandler((LibraryTypeHandler)new LibraryBlueprintTypeHandler(true));
/* 499 */     LibraryAPI.registerHandler((LibraryTypeHandler)new LibraryBookTypeHandler());
/*     */     
/* 501 */     BlueprintDeployer.instance = (BlueprintDeployer)new RealBlueprintDeployer();
/*     */     
/* 503 */     architectAchievement = BuildCraftCore.achievementManager.registerAchievement(new Achievement("achievement.architect", "architectAchievement", 11, 2, (Block)architectBlock, BuildCraftCore.goldGearAchievement));
/* 504 */     builderAchievement = BuildCraftCore.achievementManager.registerAchievement(new Achievement("achievement.builder", "builderAchievement", 13, 2, (Block)builderBlock, architectAchievement));
/* 505 */     blueprintAchievement = BuildCraftCore.achievementManager.registerAchievement(new Achievement("achievement.blueprint", "blueprintAchievement", 11, 4, (Item)blueprintItem, architectAchievement));
/* 506 */     templateAchievement = BuildCraftCore.achievementManager.registerAchievement(new Achievement("achievement.template", "templateAchievement", 13, 4, (Item)templateItem, blueprintAchievement));
/* 507 */     libraryAchievement = BuildCraftCore.achievementManager.registerAchievement(new Achievement("achievement.blueprintLibrary", "blueprintLibraryAchievement", 15, 2, (Block)libraryBlock, builderAchievement));
/* 508 */     chunkDestroyerAchievement = BuildCraftCore.achievementManager.registerAchievement(new Achievement("achievement.chunkDestroyer", "chunkDestroyerAchievement", 9, 2, (Block)quarryBlock, BuildCraftCore.diamondGearAchievement));
/*     */     
/* 510 */     if (BuildCraftCore.loadDefaultRecipes) {
/* 511 */       loadRecipes();
/*     */     }
/*     */     
/* 514 */     BuilderProxy.proxy.registerBlockRenderers();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void preInit(FMLPreInitializationEvent evt) {
/* 519 */     templateItem = new ItemBlueprintTemplate();
/* 520 */     templateItem.func_77655_b("templateItem");
/* 521 */     BCRegistry.INSTANCE.registerItem((Item)templateItem, false);
/*     */     
/* 523 */     blueprintItem = new ItemBlueprintStandard();
/* 524 */     blueprintItem.func_77655_b("blueprintItem");
/* 525 */     BCRegistry.INSTANCE.registerItem((Item)blueprintItem, false);
/*     */     
/* 527 */     quarryBlock = (BlockQuarry)CompatHooks.INSTANCE.getBlock(BlockQuarry.class);
/* 528 */     BCRegistry.INSTANCE.registerBlock(quarryBlock.func_149663_c("machineBlock"), false);
/*     */     
/* 530 */     fillerBlock = (BlockFiller)CompatHooks.INSTANCE.getBlock(BlockFiller.class);
/* 531 */     BCRegistry.INSTANCE.registerBlock(fillerBlock.func_149663_c("fillerBlock"), false);
/*     */     
/* 533 */     frameBlock = new BlockFrame();
/* 534 */     BCRegistry.INSTANCE.registerBlock(frameBlock.func_149663_c("frameBlock"), true);
/*     */     
/* 536 */     builderBlock = (BlockBuilder)CompatHooks.INSTANCE.getBlock(BlockBuilder.class);
/* 537 */     BCRegistry.INSTANCE.registerBlock(builderBlock.func_149663_c("builderBlock"), false);
/*     */     
/* 539 */     architectBlock = (BlockArchitect)CompatHooks.INSTANCE.getBlock(BlockArchitect.class);
/* 540 */     BCRegistry.INSTANCE.registerBlock(architectBlock.func_149663_c("architectBlock"), false);
/*     */     
/* 542 */     libraryBlock = (BlockBlueprintLibrary)CompatHooks.INSTANCE.getBlock(BlockBlueprintLibrary.class);
/* 543 */     BCRegistry.INSTANCE.registerBlock(libraryBlock.func_149663_c("libraryBlock"), false);
/*     */     
/* 545 */     BCRegistry.INSTANCE.registerTileEntity(TileQuarry.class, "Machine");
/* 546 */     BCRegistry.INSTANCE.registerTileEntity(TileMarker.class, "Marker");
/* 547 */     BCRegistry.INSTANCE.registerTileEntity(TileFiller.class, "Filler");
/* 548 */     BCRegistry.INSTANCE.registerTileEntity(TileBuilder.class, "net.minecraft.src.builders.TileBuilder");
/* 549 */     BCRegistry.INSTANCE.registerTileEntity(TileArchitect.class, "net.minecraft.src.builders.TileTemplate");
/* 550 */     BCRegistry.INSTANCE.registerTileEntity(TilePathMarker.class, "net.minecraft.src.builders.TilePathMarker");
/* 551 */     BCRegistry.INSTANCE.registerTileEntity(TileBlueprintLibrary.class, "net.minecraft.src.builders.TileBlueprintLibrary");
/*     */     
/* 553 */     constructionMarkerBlock = (BlockConstructionMarker)CompatHooks.INSTANCE.getBlock(BlockConstructionMarker.class);
/* 554 */     BCRegistry.INSTANCE.registerBlock(constructionMarkerBlock.func_149663_c("constructionMarkerBlock"), ItemConstructionMarker.class, false);
/*     */ 
/*     */     
/* 557 */     BCRegistry.INSTANCE.registerTileEntity(TileConstructionMarker.class, "net.minecraft.src.builders.TileConstructionMarker");
/*     */     
/* 559 */     SchematicRegistry.INSTANCE.readConfiguration((Configuration)BuildCraftCore.mainConfiguration);
/*     */     
/* 561 */     if (BuildCraftCore.mainConfiguration.hasChanged()) {
/* 562 */       BuildCraftCore.mainConfiguration.save();
/*     */     }
/*     */     
/* 565 */     MinecraftForge.EVENT_BUS.register(this);
/* 566 */     FMLCommonHandler.instance().bus().register(this);
/*     */     
/* 568 */     StatementManager.registerActionProvider((IActionProvider)new BuildersActionProvider());
/*     */   }
/*     */   
/*     */   public static void loadRecipes() {
/* 572 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)quarryBlock), new Object[] { "ipi", "gig", "dDd", 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 577 */           Character.valueOf('i'), "gearIron", 
/* 578 */           Character.valueOf('p'), "dustRedstone", 
/* 579 */           Character.valueOf('g'), "gearGold", 
/* 580 */           Character.valueOf('d'), "gearDiamond", 
/* 581 */           Character.valueOf('D'), Items.field_151046_w });
/*     */     
/* 583 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Item)templateItem, 1), new Object[] { "ppp", "pip", "ppp", Character.valueOf('i'), "dyeBlack", 
/* 584 */           Character.valueOf('p'), Items.field_151121_aF });
/*     */     
/* 586 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Item)blueprintItem, 1), new Object[] { "ppp", "pip", "ppp", Character.valueOf('i'), "gemLapis", 
/* 587 */           Character.valueOf('p'), Items.field_151121_aF });
/*     */     
/* 589 */     if (constructionMarkerBlock != null) {
/* 590 */       BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)constructionMarkerBlock, 1), new Object[] { "l ", "r ", Character.valueOf('l'), "gearGold", 
/* 591 */             Character.valueOf('r'), Blocks.field_150429_aA });
/*     */     }
/*     */     
/* 594 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)fillerBlock, 1), new Object[] { "btb", "ycy", "gCg", Character.valueOf('b'), "dyeBlack", 
/* 595 */           Character.valueOf('t'), BuildCraftCore.markerBlock, Character.valueOf('y'), "dyeYellow", 
/* 596 */           Character.valueOf('c'), Blocks.field_150462_ai, Character.valueOf('g'), "gearGold", Character.valueOf('C'), Blocks.field_150486_ae });
/*     */     
/* 598 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)builderBlock, 1), new Object[] { "btb", "ycy", "gCg", Character.valueOf('b'), "dyeBlack", 
/* 599 */           Character.valueOf('t'), BuildCraftCore.markerBlock, Character.valueOf('y'), "dyeYellow", 
/* 600 */           Character.valueOf('c'), Blocks.field_150462_ai, Character.valueOf('g'), "gearDiamond", Character.valueOf('C'), Blocks.field_150486_ae });
/*     */     
/* 602 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)architectBlock, 1), new Object[] { "btb", "ycy", "gCg", Character.valueOf('b'), "dyeBlack", 
/* 603 */           Character.valueOf('t'), BuildCraftCore.markerBlock, Character.valueOf('y'), "dyeYellow", 
/* 604 */           Character.valueOf('c'), Blocks.field_150462_ai, Character.valueOf('g'), "gearDiamond", Character.valueOf('C'), new ItemStack((Item)blueprintItem, 1) });
/*     */ 
/*     */     
/* 607 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)libraryBlock, 1), new Object[] { "igi", "bBb", "iri", Character.valueOf('B'), new ItemStack((Item)blueprintItem), 
/* 608 */           Character.valueOf('b'), Blocks.field_150342_X, Character.valueOf('i'), "ingotIron", Character.valueOf('g'), "gearIron", Character.valueOf('r'), Items.field_151137_ax });
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void processIMCRequests(FMLInterModComms.IMCEvent event) {
/* 613 */     InterModComms.processIMC(event);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void serverStop(FMLServerStoppingEvent event) {
/* 618 */     TilePathMarker.clearAvailableMarkersList();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void serverAboutToStart(FMLServerAboutToStartEvent event) {
/* 623 */     String blueprintPath = (new File(DimensionManager.getCurrentSaveRootDirectory(), "buildcraft" + File.separator + "blueprints")).getPath();
/* 624 */     serverDB.init(new String[] { this.oldBlueprintServerDir, blueprintPath }, blueprintPath);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void loadTextures(TextureStitchEvent.Pre evt) {
/* 630 */     if (evt.map.func_130086_a() == 0) {
/* 631 */       TextureMap terrainTextures = evt.map;
/* 632 */       BuilderProxyClient.drillTexture = terrainTextures.func_94245_a("buildcraftbuilders:machineBlock/drill");
/* 633 */       BuilderProxyClient.drillSideTexture = terrainTextures.func_94245_a("buildcraftbuilders:machineBlock/drill_xz");
/* 634 */       BuilderProxyClient.drillHeadTexture = terrainTextures.func_94245_a("buildcraftbuilders:machineBlock/drill_head");
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void whiteListAppliedEnergetics(FMLInitializationEvent event) {
/* 640 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileBlueprintLibrary.class
/* 641 */         .getCanonicalName());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void remap(FMLMissingMappingsEvent event) {
/* 646 */     for (FMLMissingMappingsEvent.MissingMapping mapping : event.get()) {
/* 647 */       if (mapping.name.equals("BuildCraft|Builders:buildToolBlock") || mapping.name
/* 648 */         .equals("BuildCraft|Builders:null")) {
/* 649 */         if (mapping.type == GameRegistry.Type.ITEM) {
/* 650 */           mapping.remap(Item.func_150898_a((Block)BuildCraftCore.buildToolBlock)); continue;
/*     */         } 
/* 652 */         mapping.remap((Block)BuildCraftCore.buildToolBlock); continue;
/*     */       } 
/* 654 */       if (mapping.name.equals("BuildCraft|Builders:markerBlock")) {
/* 655 */         if (mapping.type == GameRegistry.Type.ITEM) {
/* 656 */           mapping.remap(Item.func_150898_a((Block)BuildCraftCore.markerBlock)); continue;
/*     */         } 
/* 658 */         mapping.remap((Block)BuildCraftCore.markerBlock); continue;
/*     */       } 
/* 660 */       if (mapping.name.equals("BuildCraft|Builders:pathMarkerBlock")) {
/* 661 */         if (mapping.type == GameRegistry.Type.ITEM) {
/* 662 */           mapping.remap(Item.func_150898_a((Block)BuildCraftCore.pathMarkerBlock)); continue;
/*     */         } 
/* 664 */         mapping.remap((Block)BuildCraftCore.pathMarkerBlock);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\BuildCraftBuilders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */