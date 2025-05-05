/*     */ package buildcraft;
/*     */ import buildcraft.api.blueprints.BuilderAPI;
/*     */ import buildcraft.api.blueprints.ISchematicHelper;
/*     */ import buildcraft.api.blueprints.ISchematicRegistry;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.core.BuildCraftAPI;
/*     */ import buildcraft.api.core.EnumColor;
/*     */ import buildcraft.api.core.ICoreProxy;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.core.IWorldProperty;
/*     */ import buildcraft.api.crops.CropManager;
/*     */ import buildcraft.api.crops.ICropHandler;
/*     */ import buildcraft.api.filler.FillerManager;
/*     */ import buildcraft.api.filler.IFillerPattern;
/*     */ import buildcraft.api.filler.IFillerRegistry;
/*     */ import buildcraft.api.lists.ListMatchHandler;
/*     */ import buildcraft.api.lists.ListRegistry;
/*     */ import buildcraft.api.recipes.BuildcraftRecipeRegistry;
/*     */ import buildcraft.api.recipes.IAssemblyRecipeManager;
/*     */ import buildcraft.api.recipes.IIntegrationRecipeManager;
/*     */ import buildcraft.api.recipes.IProgrammingRecipeManager;
/*     */ import buildcraft.api.recipes.IRefineryRecipeManager;
/*     */ import buildcraft.api.statements.IActionExternal;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.IActionProvider;
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.ITriggerExternal;
/*     */ import buildcraft.api.statements.ITriggerInternal;
/*     */ import buildcraft.api.statements.ITriggerProvider;
/*     */ import buildcraft.api.statements.StatementManager;
/*     */ import buildcraft.api.statements.StatementParameterItemStack;
/*     */ import buildcraft.api.tablet.TabletAPI;
/*     */ import buildcraft.api.tablet.TabletProgramFactory;
/*     */ import buildcraft.api.tiles.IControllable;
/*     */ import buildcraft.core.AchievementManager;
/*     */ import buildcraft.core.BCCreativeTab;
/*     */ import buildcraft.core.BCRegistry;
/*     */ import buildcraft.core.BlockBuildTool;
/*     */ import buildcraft.core.BlockEngine;
/*     */ import buildcraft.core.BlockMarker;
/*     */ import buildcraft.core.BlockPathMarker;
/*     */ import buildcraft.core.BlockSpring;
/*     */ import buildcraft.core.CompatHooks;
/*     */ import buildcraft.core.CoreGuiHandler;
/*     */ import buildcraft.core.CoreIconProvider;
/*     */ import buildcraft.core.CoreSiliconRecipes;
/*     */ import buildcraft.core.InterModComms;
/*     */ import buildcraft.core.ItemGear;
/*     */ import buildcraft.core.ItemList;
/*     */ import buildcraft.core.ItemMapLocation;
/*     */ import buildcraft.core.ItemPaintbrush;
/*     */ import buildcraft.core.ItemSpring;
/*     */ import buildcraft.core.ItemWrench;
/*     */ import buildcraft.core.SchematicEngine;
/*     */ import buildcraft.core.SpringPopulate;
/*     */ import buildcraft.core.TickHandlerCore;
/*     */ import buildcraft.core.TileEngineWood;
/*     */ import buildcraft.core.TilePathMarker;
/*     */ import buildcraft.core.Version;
/*     */ import buildcraft.core.blueprints.BuildingSlotMapIterator;
/*     */ import buildcraft.core.blueprints.SchematicHelper;
/*     */ import buildcraft.core.blueprints.SchematicRegistry;
/*     */ import buildcraft.core.builders.patterns.FillerPattern;
/*     */ import buildcraft.core.builders.patterns.FillerRegistry;
/*     */ import buildcraft.core.builders.patterns.PatternBox;
/*     */ import buildcraft.core.builders.patterns.PatternClear;
/*     */ import buildcraft.core.builders.patterns.PatternCylinder;
/*     */ import buildcraft.core.builders.patterns.PatternFill;
/*     */ import buildcraft.core.builders.patterns.PatternFlatten;
/*     */ import buildcraft.core.builders.patterns.PatternFrame;
/*     */ import buildcraft.core.builders.patterns.PatternHorizon;
/*     */ import buildcraft.core.builders.patterns.PatternParameterCenter;
/*     */ import buildcraft.core.builders.patterns.PatternParameterHollow;
/*     */ import buildcraft.core.builders.patterns.PatternParameterXZDir;
/*     */ import buildcraft.core.builders.patterns.PatternParameterYDir;
/*     */ import buildcraft.core.builders.patterns.PatternPyramid;
/*     */ import buildcraft.core.builders.patterns.PatternStairs;
/*     */ import buildcraft.core.builders.schematics.SchematicIgnore;
/*     */ import buildcraft.core.command.SubCommandChangelog;
/*     */ import buildcraft.core.command.SubCommandDeop;
/*     */ import buildcraft.core.command.SubCommandOp;
/*     */ import buildcraft.core.command.SubCommandVersion;
/*     */ import buildcraft.core.config.BuildCraftConfiguration;
/*     */ import buildcraft.core.config.ConfigManager;
/*     */ import buildcraft.core.crops.CropHandlerPlantable;
/*     */ import buildcraft.core.crops.CropHandlerReeds;
/*     */ import buildcraft.core.lib.commands.RootCommand;
/*     */ import buildcraft.core.lib.commands.SubCommand;
/*     */ import buildcraft.core.lib.engines.ItemEngine;
/*     */ import buildcraft.core.lib.network.ChannelHandler;
/*     */ import buildcraft.core.lib.render.FluidRenderer;
/*     */ import buildcraft.core.lib.utils.ColorUtils;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import buildcraft.core.lib.utils.XorShift128Random;
/*     */ import buildcraft.core.list.ListMatchHandlerArmor;
/*     */ import buildcraft.core.list.ListMatchHandlerClass;
/*     */ import buildcraft.core.list.ListMatchHandlerFluid;
/*     */ import buildcraft.core.list.ListMatchHandlerOreDictionary;
/*     */ import buildcraft.core.list.ListMatchHandlerTools;
/*     */ import buildcraft.core.list.ListOreDictionaryCache;
/*     */ import buildcraft.core.list.ListTooltipHandler;
/*     */ import buildcraft.core.network.PacketHandlerCore;
/*     */ import buildcraft.core.properties.WorldPropertyIsDirt;
/*     */ import buildcraft.core.properties.WorldPropertyIsFarmland;
/*     */ import buildcraft.core.properties.WorldPropertyIsFluidSource;
/*     */ import buildcraft.core.properties.WorldPropertyIsHarvestable;
/*     */ import buildcraft.core.properties.WorldPropertyIsLeaf;
/*     */ import buildcraft.core.properties.WorldPropertyIsOre;
/*     */ import buildcraft.core.properties.WorldPropertyIsReplaceable;
/*     */ import buildcraft.core.properties.WorldPropertyIsShoveled;
/*     */ import buildcraft.core.properties.WorldPropertyIsSoft;
/*     */ import buildcraft.core.properties.WorldPropertyIsWood;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import buildcraft.core.recipes.AssemblyRecipeManager;
/*     */ import buildcraft.core.recipes.IntegrationRecipeManager;
/*     */ import buildcraft.core.recipes.ProgrammingRecipeManager;
/*     */ import buildcraft.core.recipes.RefineryRecipeManager;
/*     */ import buildcraft.core.render.BlockHighlightHandler;
/*     */ import buildcraft.core.render.RenderLEDTile;
/*     */ import buildcraft.core.render.RenderLaser;
/*     */ import buildcraft.core.statements.ActionMachineControl;
/*     */ import buildcraft.core.statements.ActionRedstoneOutput;
/*     */ import buildcraft.core.statements.DefaultActionProvider;
/*     */ import buildcraft.core.statements.DefaultTriggerProvider;
/*     */ import buildcraft.core.statements.StatementParameterDirection;
/*     */ import buildcraft.core.statements.StatementParameterItemStackExact;
/*     */ import buildcraft.core.statements.StatementParameterRedstoneGateSideOnly;
/*     */ import buildcraft.core.statements.TriggerEnergy;
/*     */ import buildcraft.core.statements.TriggerFluidContainer;
/*     */ import buildcraft.core.statements.TriggerFluidContainerLevel;
/*     */ import buildcraft.core.statements.TriggerInventory;
/*     */ import buildcraft.core.statements.TriggerInventoryLevel;
/*     */ import buildcraft.core.statements.TriggerMachine;
/*     */ import buildcraft.core.statements.TriggerRedstoneInput;
/*     */ import buildcraft.core.tablet.ItemTablet;
/*     */ import buildcraft.core.tablet.PacketTabletMessage;
/*     */ import buildcraft.core.tablet.manager.TabletManagerClient;
/*     */ import buildcraft.core.tablet.manager.TabletManagerServer;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import cpw.mods.fml.client.event.ConfigChangedEvent;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.Loader;
/*     */ import cpw.mods.fml.common.Mod;
/*     */ import cpw.mods.fml.common.Mod.EventHandler;
/*     */ import cpw.mods.fml.common.Mod.Instance;
/*     */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLInterModComms;
/*     */ import cpw.mods.fml.common.event.FMLPostInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLServerStartingEvent;
/*     */ import cpw.mods.fml.common.event.FMLServerStoppingEvent;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.network.IGuiHandler;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import java.io.File;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraftforge.client.event.TextureStitchEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.config.Configuration;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ @Mod(name = "BuildCraft", version = "7.1.26", useMetadata = false, modid = "BuildCraft|Core", acceptedMinecraftVersions = "[1.7.10,1.8)", dependencies = "required-after:Forge@[10.13.2.1236,)", guiFactory = "buildcraft.core.config.ConfigManager")
/*     */ public class BuildCraftCore extends BuildCraftMod {
/*     */   @Instance("BuildCraft|Core")
/*     */   public static BuildCraftCore instance;
/*     */   public static final boolean DEVELOPER_MODE = false;
/*     */   
/*     */   public enum RenderMode {
/* 185 */     Full, NoDynamic;
/*     */   }
/*     */   
/* 188 */   public static RootCommand commandBuildcraft = new RootCommand("buildcraft");
/* 189 */   public static XorShift128Random random = new XorShift128Random();
/* 190 */   public static RenderMode render = RenderMode.Full;
/*     */   public static boolean debugWorldgen = false;
/*     */   public static boolean modifyWorld = false;
/*     */   public static boolean colorBlindMode = false;
/*     */   public static boolean hidePowerNumbers = false;
/*     */   public static boolean hideFluidNumbers = false;
/*     */   public static boolean canEnginesExplode = false;
/*     */   public static boolean useServerDataOnClient = true;
/*     */   public static boolean alphaPassBugPresent = true;
/* 199 */   public static int itemLifespan = 1200;
/* 200 */   public static int updateFactor = 10;
/* 201 */   public static int builderMaxPerItemFactor = 1024;
/* 202 */   public static long longUpdateFactor = 40L;
/*     */   
/*     */   public static BuildCraftConfiguration mainConfiguration;
/*     */   public static ConfigManager mainConfigManager;
/*     */   public static BlockEngine engineBlock;
/*     */   public static BlockMarker markerBlock;
/*     */   public static BlockPathMarker pathMarkerBlock;
/*     */   public static Block springBlock;
/*     */   public static BlockBuildTool buildToolBlock;
/*     */   public static Item woodenGearItem;
/*     */   public static Item stoneGearItem;
/*     */   public static Item ironGearItem;
/*     */   public static Item goldGearItem;
/*     */   public static Item diamondGearItem;
/*     */   public static Item wrenchItem;
/*     */   public static Item mapLocationItem;
/*     */   public static Item debuggerItem;
/*     */   public static Item paintbrushItem;
/*     */   public static ItemList listItem;
/*     */   public static ItemTablet tabletItem;
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static IIcon redLaserTexture;
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static IIcon blueLaserTexture;
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static IIcon stripesLaserTexture;
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static IIcon transparentTexture;
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static IIconProvider iconProvider;
/*     */   public static int blockByEntityModel;
/*     */   public static int complexBlockModel;
/*     */   public static int markerModel;
/* 235 */   public static ITriggerExternal triggerMachineActive = (ITriggerExternal)new TriggerMachine(true);
/* 236 */   public static ITriggerExternal triggerMachineInactive = (ITriggerExternal)new TriggerMachine(false);
/* 237 */   public static IStatement triggerEnergyHigh = (IStatement)new TriggerEnergy(true);
/* 238 */   public static IStatement triggerEnergyLow = (IStatement)new TriggerEnergy(false);
/* 239 */   public static ITriggerExternal triggerEmptyInventory = (ITriggerExternal)new TriggerInventory(TriggerInventory.State.Empty);
/* 240 */   public static ITriggerExternal triggerContainsInventory = (ITriggerExternal)new TriggerInventory(TriggerInventory.State.Contains);
/* 241 */   public static ITriggerExternal triggerSpaceInventory = (ITriggerExternal)new TriggerInventory(TriggerInventory.State.Space);
/* 242 */   public static ITriggerExternal triggerFullInventory = (ITriggerExternal)new TriggerInventory(TriggerInventory.State.Full);
/* 243 */   public static ITriggerExternal triggerEmptyFluid = (ITriggerExternal)new TriggerFluidContainer(TriggerFluidContainer.State.Empty);
/* 244 */   public static ITriggerExternal triggerContainsFluid = (ITriggerExternal)new TriggerFluidContainer(TriggerFluidContainer.State.Contains);
/* 245 */   public static ITriggerExternal triggerSpaceFluid = (ITriggerExternal)new TriggerFluidContainer(TriggerFluidContainer.State.Space);
/* 246 */   public static ITriggerExternal triggerFullFluid = (ITriggerExternal)new TriggerFluidContainer(TriggerFluidContainer.State.Full);
/* 247 */   public static ITriggerInternal triggerRedstoneActive = (ITriggerInternal)new TriggerRedstoneInput(true);
/* 248 */   public static ITriggerInternal triggerRedstoneInactive = (ITriggerInternal)new TriggerRedstoneInput(false);
/* 249 */   public static ITriggerExternal triggerInventoryBelow25 = (ITriggerExternal)new TriggerInventoryLevel(TriggerInventoryLevel.TriggerType.BELOW25);
/* 250 */   public static ITriggerExternal triggerInventoryBelow50 = (ITriggerExternal)new TriggerInventoryLevel(TriggerInventoryLevel.TriggerType.BELOW50);
/* 251 */   public static ITriggerExternal triggerInventoryBelow75 = (ITriggerExternal)new TriggerInventoryLevel(TriggerInventoryLevel.TriggerType.BELOW75);
/* 252 */   public static ITriggerExternal triggerFluidContainerBelow25 = (ITriggerExternal)new TriggerFluidContainerLevel(TriggerFluidContainerLevel.TriggerType.BELOW25);
/* 253 */   public static ITriggerExternal triggerFluidContainerBelow50 = (ITriggerExternal)new TriggerFluidContainerLevel(TriggerFluidContainerLevel.TriggerType.BELOW50);
/* 254 */   public static ITriggerExternal triggerFluidContainerBelow75 = (ITriggerExternal)new TriggerFluidContainerLevel(TriggerFluidContainerLevel.TriggerType.BELOW75);
/* 255 */   public static IActionInternal actionRedstone = (IActionInternal)new ActionRedstoneOutput();
/*     */   
/*     */   public static IActionExternal[] actionControl;
/*     */   
/*     */   public static boolean loadDefaultRecipes = true;
/*     */   
/*     */   public static boolean consumeWaterSources = false;
/*     */   
/*     */   public static boolean miningAllowPlayerProtectedBlocks = false;
/*     */   public static float miningMultiplier;
/*     */   public static AchievementManager achievementManager;
/*     */   public static Achievement woodenGearAchievement;
/*     */   public static Achievement stoneGearAchievement;
/*     */   public static Achievement ironGearAchievement;
/*     */   public static Achievement goldGearAchievement;
/*     */   public static Achievement diamondGearAchievement;
/*     */   public static Achievement wrenchAchievement;
/*     */   public static Achievement engineRedstoneAchievement;
/* 273 */   public static GameProfile gameProfile = new GameProfile(UUID.nameUUIDFromBytes("buildcraft.core".getBytes()), "[BuildCraft]");
/*     */   
/*     */   @EventHandler
/*     */   public void loadConfiguration(FMLPreInitializationEvent evt) {
/* 277 */     BCLog.logger.info("Starting BuildCraft " + Version.getVersion());
/* 278 */     BCLog.logger.info("Copyright (c) the BuildCraft team, 2011-2017");
/* 279 */     BCLog.logger.info("http://www.mod-buildcraft.com");
/*     */     
/* 281 */     commandBuildcraft.addAlias("bc");
/* 282 */     commandBuildcraft.addChildCommand((SubCommand)new SubCommandVersion());
/* 283 */     commandBuildcraft.addChildCommand((SubCommand)new SubCommandChangelog());
/* 284 */     commandBuildcraft.addChildCommand((SubCommand)new SubCommandDeop());
/* 285 */     commandBuildcraft.addChildCommand((SubCommand)new SubCommandOp());
/*     */     
/* 287 */     BuildcraftRecipeRegistry.assemblyTable = (IAssemblyRecipeManager)AssemblyRecipeManager.INSTANCE;
/* 288 */     BuildcraftRecipeRegistry.integrationTable = (IIntegrationRecipeManager)IntegrationRecipeManager.INSTANCE;
/* 289 */     BuildcraftRecipeRegistry.refinery = (IRefineryRecipeManager)RefineryRecipeManager.INSTANCE;
/* 290 */     BuildcraftRecipeRegistry.programmingTable = (IProgrammingRecipeManager)ProgrammingRecipeManager.INSTANCE;
/*     */     
/* 292 */     BuilderAPI.schematicHelper = (ISchematicHelper)SchematicHelper.INSTANCE;
/* 293 */     BuilderAPI.schematicRegistry = (ISchematicRegistry)SchematicRegistry.INSTANCE;
/*     */     
/* 295 */     BCRegistry.INSTANCE.setRegistryConfig(new File(evt.getModConfigurationDirectory(), "buildcraft/objects.cfg"));
/*     */     
/* 297 */     mainConfiguration = new BuildCraftConfiguration(new File(evt.getModConfigurationDirectory(), "buildcraft/main.cfg"));
/* 298 */     mainConfigManager = new ConfigManager((Configuration)mainConfiguration);
/* 299 */     mainConfiguration.load();
/*     */     
/* 301 */     mainConfigManager.getCat("debug").setShowInGui(false);
/* 302 */     mainConfigManager.getCat("vars").setShowInGui(false);
/*     */     
/* 304 */     mainConfigManager.register("general.useServerDataOnClient", Boolean.valueOf(useServerDataOnClient), "Allows BuildCraft to use the integrated server's data on the client on singleplayer worlds. Disable if you're getting the odd crash caused by it.", ConfigManager.RestartRequirement.NONE);
/* 305 */     mainConfigManager.register("general.builderMaxIterationsPerItemFactor", Integer.valueOf(builderMaxPerItemFactor), "Lower this number if BuildCraft builders/fillers are causing TPS lag. Raise it if you think they are being too slow.", ConfigManager.RestartRequirement.NONE);
/*     */     
/* 307 */     mainConfigManager.register("general.miningBreaksPlayerProtectedBlocks", Boolean.valueOf(false), "Should BuildCraft miners be allowed to break blocks using player-specific protection?", ConfigManager.RestartRequirement.NONE);
/* 308 */     mainConfigManager.register("general.updateCheck", Boolean.valueOf(true), "Should I check the BuildCraft version on startup?", ConfigManager.RestartRequirement.NONE);
/* 309 */     mainConfigManager.register("display.hidePowerValues", Boolean.valueOf(false), "Should all power values (RF, RF/t) be hidden?", ConfigManager.RestartRequirement.NONE);
/* 310 */     mainConfigManager.register("display.hideFluidValues", Boolean.valueOf(false), "Should all fluid values (mB, mB/t) be hidden?", ConfigManager.RestartRequirement.NONE);
/* 311 */     mainConfigManager.register("general.itemLifespan", Integer.valueOf(60), "How long, in seconds, should items stay on the ground? (Vanilla = 300, default = 60)", ConfigManager.RestartRequirement.NONE)
/* 312 */       .setMinValue(5);
/* 313 */     mainConfigManager.register("network.updateFactor", Integer.valueOf(10), "How often, in ticks, should network update packets be sent? Increasing this might help network performance.", ConfigManager.RestartRequirement.GAME)
/* 314 */       .setMinValue(1);
/* 315 */     mainConfigManager.register("network.longUpdateFactor", Integer.valueOf(40), "How often, in ticks, should full network sync packets be sent? Increasing this might help network performance.", ConfigManager.RestartRequirement.GAME)
/* 316 */       .setMinValue(1);
/* 317 */     mainConfigManager.register("general.canEnginesExplode", Boolean.valueOf(false), "Should engines explode upon overheat?", ConfigManager.RestartRequirement.NONE);
/* 318 */     mainConfigManager.register("worldgen.enable", Boolean.valueOf(true), "Should BuildCraft generate anything in the world?", ConfigManager.RestartRequirement.GAME);
/* 319 */     mainConfigManager.register("general.pumpsConsumeWater", Boolean.valueOf(false), "Should pumps consume water? Enabling this might cause performance issues!", ConfigManager.RestartRequirement.NONE);
/* 320 */     mainConfigManager.register("power.miningUsageMultiplier", Double.valueOf(1.0D), "What should the multiplier of all mining-related power usage be?", ConfigManager.RestartRequirement.NONE);
/* 321 */     mainConfigManager.register("display.colorBlindMode", Boolean.valueOf(false), "Should I enable colorblind mode?", ConfigManager.RestartRequirement.GAME);
/* 322 */     mainConfigManager.register("worldgen.generateWaterSprings", Boolean.valueOf(true), "Should BuildCraft generate water springs?", ConfigManager.RestartRequirement.GAME);
/*     */     
/* 324 */     reloadConfig(ConfigManager.RestartRequirement.GAME);
/*     */     
/* 326 */     wrenchItem = (new ItemWrench()).func_77655_b("wrenchItem");
/* 327 */     BCRegistry.INSTANCE.registerItem(wrenchItem, false);
/*     */     
/* 329 */     mapLocationItem = (new ItemMapLocation()).func_77655_b("mapLocation");
/* 330 */     BCRegistry.INSTANCE.registerItem(mapLocationItem, false);
/*     */     
/* 332 */     listItem = (ItemList)(new ItemList()).func_77655_b("list");
/* 333 */     BCRegistry.INSTANCE.registerItem((Item)listItem, false);
/*     */     
/* 335 */     debuggerItem = (new ItemDebugger()).func_77655_b("debugger");
/* 336 */     BCRegistry.INSTANCE.registerItem(debuggerItem, false);
/*     */     
/* 338 */     if (modifyWorld) {
/* 339 */       BlockSpring.EnumSpring.WATER.canGen = mainConfigManager.get("worldgen.generateWaterSprings").getBoolean();
/* 340 */       springBlock = (new BlockSpring()).func_149663_c("eternalSpring");
/* 341 */       BCRegistry.INSTANCE.registerBlock(springBlock, ItemSpring.class, false);
/*     */     } 
/*     */     
/* 344 */     woodenGearItem = (new ItemGear()).func_77655_b("woodenGearItem");
/* 345 */     if (BCRegistry.INSTANCE.registerItem(woodenGearItem, false)) {
/* 346 */       OreDictionary.registerOre("gearWood", new ItemStack(woodenGearItem));
/*     */     }
/*     */     
/* 349 */     stoneGearItem = (new ItemGear()).func_77655_b("stoneGearItem");
/* 350 */     if (BCRegistry.INSTANCE.registerItem(stoneGearItem, false)) {
/* 351 */       OreDictionary.registerOre("gearStone", new ItemStack(stoneGearItem));
/*     */     }
/*     */     
/* 354 */     ironGearItem = (new ItemGear()).func_77655_b("ironGearItem");
/* 355 */     if (BCRegistry.INSTANCE.registerItem(ironGearItem, false)) {
/* 356 */       OreDictionary.registerOre("gearIron", new ItemStack(ironGearItem));
/*     */     }
/*     */     
/* 359 */     goldGearItem = (new ItemGear()).func_77655_b("goldGearItem");
/* 360 */     if (BCRegistry.INSTANCE.registerItem(goldGearItem, false)) {
/* 361 */       OreDictionary.registerOre("gearGold", new ItemStack(goldGearItem));
/*     */     }
/*     */     
/* 364 */     diamondGearItem = (new ItemGear()).func_77655_b("diamondGearItem");
/* 365 */     if (BCRegistry.INSTANCE.registerItem(diamondGearItem, false)) {
/* 366 */       OreDictionary.registerOre("gearDiamond", new ItemStack(diamondGearItem));
/*     */     }
/*     */     
/* 369 */     paintbrushItem = (new ItemPaintbrush()).func_77655_b("paintbrush");
/* 370 */     BCRegistry.INSTANCE.registerItem(paintbrushItem, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     buildToolBlock = new BlockBuildTool();
/* 379 */     buildToolBlock.func_149663_c("buildToolBlock");
/* 380 */     BCRegistry.INSTANCE.registerBlock((Block)buildToolBlock, true);
/*     */     
/* 382 */     engineBlock = (BlockEngine)CompatHooks.INSTANCE.getBlock(BlockEngine.class);
/* 383 */     BCRegistry.INSTANCE.registerBlock((Block)engineBlock, ItemEngine.class, true);
/* 384 */     engineBlock.registerTile(CompatHooks.INSTANCE.getTile(TileEngineWood.class), 0, "tile.engineWood", "buildcraftcore:engineWood");
/* 385 */     BCRegistry.INSTANCE.registerTileEntity(TileEngineWood.class, "net.minecraft.src.buildcraft.energy.TileEngineWood");
/*     */     
/* 387 */     markerBlock = (BlockMarker)CompatHooks.INSTANCE.getBlock(BlockMarker.class);
/* 388 */     BCRegistry.INSTANCE.registerBlock(markerBlock.func_149663_c("markerBlock"), false);
/*     */     
/* 390 */     pathMarkerBlock = (BlockPathMarker)CompatHooks.INSTANCE.getBlock(BlockPathMarker.class);
/* 391 */     BCRegistry.INSTANCE.registerBlock(pathMarkerBlock.func_149663_c("pathMarkerBlock"), false);
/*     */     
/* 393 */     FMLCommonHandler.instance().bus().register(this);
/* 394 */     MinecraftForge.EVENT_BUS.register(this);
/* 395 */     MinecraftForge.EVENT_BUS.register(new BlockHighlightHandler());
/* 396 */     MinecraftForge.EVENT_BUS.register(new ListTooltipHandler());
/*     */     
/* 398 */     OreDictionary.registerOre("chestWood", (Block)Blocks.field_150486_ae);
/* 399 */     OreDictionary.registerOre("craftingTableWood", Blocks.field_150462_ai);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void init(FMLInitializationEvent evt) {
/* 404 */     BuildCraftAPI.proxy = (ICoreProxy)CoreProxy.proxy;
/*     */     
/* 406 */     ChannelHandler coreChannelHandler = new ChannelHandler();
/* 407 */     coreChannelHandler.registerPacketType(PacketTabletMessage.class);
/*     */     
/* 409 */     this
/* 410 */       .channels = NetworkRegistry.INSTANCE.newChannel("BC-CORE", new ChannelHandler[] { (ChannelHandler)coreChannelHandler, (ChannelHandler)new PacketHandlerCore() });
/*     */     
/* 412 */     achievementManager = new AchievementManager("BuildCraft");
/* 413 */     FMLCommonHandler.instance().bus().register(achievementManager);
/*     */     
/* 415 */     woodenGearAchievement = achievementManager.registerAchievement(new Achievement("achievement.woodenGear", "woodenGearAchievement", 0, 0, woodenGearItem, null));
/* 416 */     stoneGearAchievement = achievementManager.registerAchievement(new Achievement("achievement.stoneGear", "stoneGearAchievement", 2, 0, stoneGearItem, woodenGearAchievement));
/* 417 */     ironGearAchievement = achievementManager.registerAchievement(new Achievement("achievement.ironGear", "ironGearAchievement", 4, 0, ironGearItem, stoneGearAchievement));
/* 418 */     goldGearAchievement = achievementManager.registerAchievement(new Achievement("achievement.goldGear", "goldGearAchievement", 6, 0, goldGearItem, ironGearAchievement));
/* 419 */     diamondGearAchievement = achievementManager.registerAchievement(new Achievement("achievement.diamondGear", "diamondGearAchievement", 8, 0, diamondGearItem, goldGearAchievement));
/* 420 */     wrenchAchievement = achievementManager.registerAchievement(new Achievement("achievement.wrench", "wrenchAchievement", 3, 2, wrenchItem, stoneGearAchievement));
/* 421 */     engineRedstoneAchievement = achievementManager.registerAchievement(new Achievement("achievement.redstoneEngine", "engineAchievement1", 1, -2, new ItemStack((Block)engineBlock, 1, 0), woodenGearAchievement));
/*     */ 
/*     */     
/* 424 */     StatementManager.registerParameterClass("buildcraft:stackTrigger", StatementParameterItemStack.class);
/* 425 */     StatementManager.registerParameterClass("buildcraft:stackAction", StatementParameterItemStack.class);
/*     */     
/* 427 */     StatementManager.registerParameterClass(StatementParameterItemStack.class);
/* 428 */     StatementManager.registerParameterClass(StatementParameterItemStackExact.class);
/* 429 */     StatementManager.registerParameterClass(StatementParameterDirection.class);
/* 430 */     StatementManager.registerParameterClass(StatementParameterRedstoneGateSideOnly.class);
/* 431 */     StatementManager.registerTriggerProvider((ITriggerProvider)new DefaultTriggerProvider());
/* 432 */     StatementManager.registerActionProvider((IActionProvider)new DefaultActionProvider());
/*     */     
/* 434 */     if (modifyWorld) {
/* 435 */       MinecraftForge.EVENT_BUS.register(new SpringPopulate());
/*     */     }
/*     */     
/* 438 */     if (mainConfiguration.hasChanged()) {
/* 439 */       mainConfiguration.save();
/*     */     }
/*     */     
/* 442 */     if (loadDefaultRecipes) {
/* 443 */       loadRecipes();
/*     */     }
/*     */     
/* 446 */     if (BCCreativeTab.isPresent("main")) {
/* 447 */       BCCreativeTab.get("main").setIcon(new ItemStack(wrenchItem, 1));
/*     */     }
/*     */     
/* 450 */     EntityList.field_75625_b.remove("BuildCraft|Core.bcLaser");
/* 451 */     EntityList.field_75625_b.remove("BuildCraft|Core.bcEnergyLaser");
/*     */     
/* 453 */     BuilderAPI.schematicRegistry.registerSchematicBlock((Block)engineBlock, SchematicEngine.class, new Object[0]);
/*     */     
/* 455 */     CoreProxy.proxy.initializeRendering();
/* 456 */     CoreProxy.proxy.initializeEntityRendering();
/*     */     
/* 458 */     NetworkRegistry.INSTANCE.registerGuiHandler(instance, (IGuiHandler)new CoreGuiHandler());
/*     */     
/* 460 */     FMLCommonHandler.instance().bus().register(TabletManagerClient.INSTANCE);
/* 461 */     FMLCommonHandler.instance().bus().register(TabletManagerServer.INSTANCE);
/* 462 */     MinecraftForge.EVENT_BUS.register(TabletManagerClient.INSTANCE);
/* 463 */     MinecraftForge.EVENT_BUS.register(TabletManagerServer.INSTANCE);
/*     */     
/* 465 */     TabletAPI.registerProgram((TabletProgramFactory)new TabletProgramMenuFactory());
/*     */ 
/*     */     
/*     */     try {
/* 469 */       FillerManager.registry = (IFillerRegistry)new FillerRegistry();
/*     */ 
/*     */       
/* 472 */       FillerManager.registry.addPattern((IFillerPattern)PatternFill.INSTANCE);
/* 473 */       FillerManager.registry.addPattern((IFillerPattern)new PatternFlatten());
/* 474 */       FillerManager.registry.addPattern((IFillerPattern)new PatternHorizon());
/* 475 */       FillerManager.registry.addPattern((IFillerPattern)new PatternClear());
/* 476 */       FillerManager.registry.addPattern((IFillerPattern)new PatternBox());
/* 477 */       FillerManager.registry.addPattern((IFillerPattern)new PatternPyramid());
/* 478 */       FillerManager.registry.addPattern((IFillerPattern)new PatternStairs());
/* 479 */       FillerManager.registry.addPattern((IFillerPattern)new PatternCylinder());
/* 480 */       FillerManager.registry.addPattern((IFillerPattern)new PatternFrame());
/* 481 */     } catch (Error error) {
/* 482 */       BCLog.logErrorAPI(error, IFillerPattern.class);
/* 483 */       throw error;
/*     */     } 
/*     */     
/* 486 */     StatementManager.registerParameterClass(PatternParameterYDir.class);
/* 487 */     StatementManager.registerParameterClass(PatternParameterXZDir.class);
/* 488 */     StatementManager.registerParameterClass(PatternParameterCenter.class);
/* 489 */     StatementManager.registerParameterClass(PatternParameterHollow.class);
/*     */     
/* 491 */     ListRegistry.registerHandler((ListMatchHandler)new ListMatchHandlerClass());
/* 492 */     ListRegistry.registerHandler((ListMatchHandler)new ListMatchHandlerFluid());
/* 493 */     ListRegistry.registerHandler((ListMatchHandler)new ListMatchHandlerTools());
/* 494 */     ListRegistry.registerHandler((ListMatchHandler)new ListMatchHandlerArmor());
/* 495 */     ListRegistry.itemClassAsType.add(ItemFood.class);
/*     */     
/* 497 */     if (Loader.isModLoaded("foamfix")) {
/* 498 */       alphaPassBugPresent = false;
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void postInit(FMLPostInitializationEvent event) {
/* 504 */     BCLog.logger.info("BuildCraft's fake player: UUID = " + gameProfile.getId().toString() + ", name = '" + gameProfile.getName() + "'!");
/*     */     
/* 506 */     BCRegistry.INSTANCE.save();
/*     */     
/* 508 */     for (Object o : Block.field_149771_c) {
/* 509 */       Block block = (Block)o;
/*     */       
/* 511 */       if (block instanceof net.minecraftforge.fluids.BlockFluidBase || block instanceof net.minecraft.block.BlockLiquid || block instanceof net.minecraftforge.common.IPlantable) {
/* 512 */         BuildCraftAPI.softBlocks.add(block);
/*     */       }
/*     */     } 
/*     */     
/* 516 */     BuildCraftAPI.softBlocks.add(Blocks.field_150433_aE);
/* 517 */     BuildCraftAPI.softBlocks.add(Blocks.field_150395_bd);
/* 518 */     BuildCraftAPI.softBlocks.add(Blocks.field_150480_ab);
/*     */     
/* 520 */     FMLCommonHandler.instance().bus().register(new TickHandlerCore());
/*     */     
/* 522 */     CropManager.setDefaultHandler((ICropHandler)new CropHandlerPlantable());
/* 523 */     CropManager.registerHandler((ICropHandler)new CropHandlerReeds());
/*     */     
/* 525 */     CropHandlerPlantable.forbidBlock(Blocks.field_150436_aH);
/*     */     
/* 527 */     BuildCraftAPI.registerWorldProperty("replaceable", (IWorldProperty)new WorldPropertyIsReplaceable());
/* 528 */     BuildCraftAPI.registerWorldProperty("soft", (IWorldProperty)new WorldPropertyIsSoft());
/* 529 */     BuildCraftAPI.registerWorldProperty("wood", (IWorldProperty)new WorldPropertyIsWood());
/* 530 */     BuildCraftAPI.registerWorldProperty("leaves", (IWorldProperty)new WorldPropertyIsLeaf());
/* 531 */     for (int i = 0; i < 4; i++) {
/* 532 */       BuildCraftAPI.registerWorldProperty("ore@hardness=" + i, (IWorldProperty)new WorldPropertyIsOre(i));
/*     */     }
/* 534 */     BuildCraftAPI.registerWorldProperty("harvestable", (IWorldProperty)new WorldPropertyIsHarvestable());
/* 535 */     BuildCraftAPI.registerWorldProperty("farmland", (IWorldProperty)new WorldPropertyIsFarmland());
/* 536 */     BuildCraftAPI.registerWorldProperty("shoveled", (IWorldProperty)new WorldPropertyIsShoveled());
/* 537 */     BuildCraftAPI.registerWorldProperty("dirt", (IWorldProperty)new WorldPropertyIsDirt());
/* 538 */     BuildCraftAPI.registerWorldProperty("fluidSource", (IWorldProperty)new WorldPropertyIsFluidSource());
/*     */ 
/*     */     
/* 541 */     SchematicRegistry.INSTANCE.registerSchematicBlock((Block)markerBlock, SchematicIgnore.class, new Object[0]);
/* 542 */     SchematicRegistry.INSTANCE.registerSchematicBlock((Block)pathMarkerBlock, SchematicIgnore.class, new Object[0]);
/*     */     
/* 544 */     ColorUtils.initialize();
/*     */     
/* 546 */     actionControl = new IActionExternal[(IControllable.Mode.values()).length];
/* 547 */     for (IControllable.Mode mode : IControllable.Mode.values()) {
/* 548 */       if (mode != IControllable.Mode.Unknown && mode != IControllable.Mode.Mode) {
/* 549 */         actionControl[mode.ordinal()] = (IActionExternal)new ActionMachineControl(mode);
/*     */       }
/*     */     } 
/*     */     
/* 553 */     MinecraftForge.EVENT_BUS.register(ListOreDictionaryCache.INSTANCE);
/* 554 */     for (String s : OreDictionary.getOreNames()) {
/* 555 */       ListOreDictionaryCache.INSTANCE.registerName(s);
/*     */     }
/*     */     
/* 558 */     ListRegistry.registerHandler((ListMatchHandler)new ListMatchHandlerOreDictionary());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void serverStarting(FMLServerStartingEvent event) {
/* 563 */     event.registerServerCommand((ICommand)commandBuildcraft);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 568 */     if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
/* 569 */       BuildingSlotMapIterator.MAX_PER_ITEM = builderMaxPerItemFactor * 4;
/*     */     } else {
/* 571 */       BuildingSlotMapIterator.MAX_PER_ITEM = builderMaxPerItemFactor;
/*     */     } 
/*     */     
/* 574 */     if (Utils.CAULDRON_DETECTED) {
/* 575 */       BCLog.logger.warn("############################################");
/* 576 */       BCLog.logger.warn("#                                          #");
/* 577 */       BCLog.logger.warn("#  Cauldron has been detected! Please keep #");
/* 578 */       BCLog.logger.warn("#  in mind that BuildCraft may NOT provide #");
/* 579 */       BCLog.logger.warn("# support to Cauldron users, as the mod is #");
/* 580 */       BCLog.logger.warn("# primarily tested without Bukkit/Spigot's #");
/* 581 */       BCLog.logger.warn("#    changes to the Minecraft internals.   #");
/* 582 */       BCLog.logger.warn("#                                          #");
/* 583 */       BCLog.logger.warn("#  Any lag caused by BuildCraft on top of  #");
/* 584 */       BCLog.logger.warn("#  Cauldron likely arises from workarounds #");
/* 585 */       BCLog.logger.warn("#  which we apply to make sure BuildCraft  #");
/* 586 */       BCLog.logger.warn("#  works properly with Cauldron installed. #");
/* 587 */       BCLog.logger.warn("#                                          #");
/* 588 */       BCLog.logger.warn("#     Thanks for your attention! ~ BC devs #");
/* 589 */       BCLog.logger.warn("#                                          #");
/* 590 */       BCLog.logger.warn("############################################");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void serverStopping(FMLServerStoppingEvent event) {
/* 601 */     TabletManagerClient.INSTANCE.onServerStopping();
/* 602 */     TabletManagerServer.INSTANCE.onServerStopping();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void textureHook(TextureStitchEvent.Pre event) {
/* 608 */     for (FillerPattern pattern : FillerPattern.patterns.values()) {
/* 609 */       pattern.registerIcons((IIconRegister)event.map);
/*     */     }
/*     */     
/* 612 */     if (event.map.func_130086_a() == 1) {
/* 613 */       iconProvider = (IIconProvider)new CoreIconProvider();
/* 614 */       iconProvider.registerIcons((IIconRegister)event.map);
/*     */       
/* 616 */       StatementManager.registerIcons((IIconRegister)event.map);
/* 617 */     } else if (event.map.func_130086_a() == 0) {
/* 618 */       redLaserTexture = event.map.func_94245_a("buildcraftcore:laserBox/blockRedLaser");
/* 619 */       blueLaserTexture = event.map.func_94245_a("buildcraftcore:laserBox/blockBlueLaser");
/* 620 */       stripesLaserTexture = event.map.func_94245_a("buildcraftcore:laserBox/blockStripesLaser");
/* 621 */       transparentTexture = event.map.func_94245_a("buildcraftcore:misc/transparent");
/* 622 */       RenderLEDTile.registerBlockIcons((IIconRegister)event.map);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void textureHook(TextureStitchEvent.Post event) {
/* 629 */     FluidRenderer.onTextureReload();
/* 630 */     RenderLaser.onTextureReload();
/*     */   }
/*     */   
/*     */   public void reloadConfig(ConfigManager.RestartRequirement restartType) {
/* 634 */     if (restartType == ConfigManager.RestartRequirement.GAME) {
/* 635 */       modifyWorld = mainConfigManager.get("worldgen.enable").getBoolean();
/* 636 */       updateFactor = mainConfigManager.get("network.updateFactor").getInt();
/* 637 */       longUpdateFactor = mainConfigManager.get("network.longUpdateFactor").getInt();
/* 638 */       colorBlindMode = mainConfigManager.get("display.colorBlindMode").getBoolean();
/*     */       
/* 640 */       reloadConfig(ConfigManager.RestartRequirement.WORLD);
/* 641 */     } else if (restartType == ConfigManager.RestartRequirement.WORLD) {
/* 642 */       reloadConfig(ConfigManager.RestartRequirement.NONE);
/*     */     } else {
/* 644 */       useServerDataOnClient = mainConfigManager.get("general.useServerDataOnClient").getBoolean(true);
/* 645 */       builderMaxPerItemFactor = mainConfigManager.get("general.builderMaxIterationsPerItemFactor").getInt();
/* 646 */       hideFluidNumbers = mainConfigManager.get("display.hideFluidValues").getBoolean();
/* 647 */       hidePowerNumbers = mainConfigManager.get("display.hidePowerValues").getBoolean();
/* 648 */       itemLifespan = mainConfigManager.get("general.itemLifespan").getInt();
/* 649 */       canEnginesExplode = mainConfigManager.get("general.canEnginesExplode").getBoolean();
/* 650 */       consumeWaterSources = mainConfigManager.get("general.pumpsConsumeWater").getBoolean();
/* 651 */       miningMultiplier = (float)mainConfigManager.get("power.miningUsageMultiplier").getDouble();
/* 652 */       miningAllowPlayerProtectedBlocks = mainConfigManager.get("general.miningBreaksPlayerProtectedBlocks").getBoolean();
/*     */       
/* 654 */       BuildingSlotMapIterator.MAX_PER_ITEM = builderMaxPerItemFactor;
/*     */       
/* 656 */       if (miningMultiplier <= 0.0F) {
/* 657 */         throw new RuntimeException("Please do not set the miningMultiplier to values <= 0.0!");
/*     */       }
/*     */       
/* 660 */       if (mainConfigManager.get("general.updateCheck").getBoolean(true)) {
/* 661 */         Version.check();
/*     */       }
/*     */       
/* 664 */       if (mainConfiguration.hasChanged()) {
/* 665 */         mainConfiguration.save();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
/* 672 */     if ("BuildCraft|Core".equals(event.modID)) {
/* 673 */       reloadConfig(event.isWorldRunning ? ConfigManager.RestartRequirement.NONE : ConfigManager.RestartRequirement.WORLD);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadRecipes() {
/* 678 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack(wrenchItem), new Object[] { "I I", " G ", " I ", Character.valueOf('I'), "ingotIron", Character.valueOf('G'), "gearStone" });
/* 679 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack(woodenGearItem), new Object[] { " S ", "S S", " S ", 
/* 680 */           Character.valueOf('S'), "stickWood" });
/*     */     
/* 682 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack(stoneGearItem), new Object[] { " I ", "IGI", " I ", 
/* 683 */           Character.valueOf('I'), "cobblestone", 
/* 684 */           Character.valueOf('G'), "gearWood" });
/*     */     
/* 686 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack(ironGearItem), new Object[] { " I ", "IGI", " I ", 
/* 687 */           Character.valueOf('I'), "ingotIron", 
/* 688 */           Character.valueOf('G'), "gearStone" });
/* 689 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack(goldGearItem), new Object[] { " I ", "IGI", " I ", 
/* 690 */           Character.valueOf('I'), "ingotGold", 
/* 691 */           Character.valueOf('G'), "gearIron" });
/* 692 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack(diamondGearItem), new Object[] { " I ", "IGI", " I ", 
/* 693 */           Character.valueOf('I'), "gemDiamond", Character.valueOf('G'), "gearGold" });
/* 694 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack(mapLocationItem), new Object[] { "ppp", "pYp", "ppp", Character.valueOf('p'), Items.field_151121_aF, Character.valueOf('Y'), "dyeYellow" });
/*     */     
/* 696 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)engineBlock, 1, 0), new Object[] { "www", " g ", "GpG", 
/* 697 */           Character.valueOf('w'), "plankWood", Character.valueOf('g'), "blockGlass", Character.valueOf('G'), "gearWood", 
/* 698 */           Character.valueOf('p'), Blocks.field_150331_J });
/*     */     
/* 700 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)markerBlock, 1), new Object[] { "l ", "r ", Character.valueOf('l'), new ItemStack(Items.field_151100_aR, 1, 4), 
/* 701 */           Character.valueOf('r'), Blocks.field_150429_aA });
/*     */     
/* 703 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)pathMarkerBlock, 1), new Object[] { "l ", "r ", Character.valueOf('l'), "dyeGreen", 
/* 704 */           Character.valueOf('r'), Blocks.field_150429_aA });
/*     */     
/* 706 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack(paintbrushItem), new Object[] { " iw", " gi", "s  ", 
/* 707 */           Character.valueOf('s'), "stickWood", Character.valueOf('g'), "gearWood", Character.valueOf('w'), new ItemStack(Blocks.field_150325_L, 1, 0), Character.valueOf('i'), Items.field_151007_F });
/*     */     
/* 709 */     ItemStack anyPaintbrush = new ItemStack(paintbrushItem, 1, 32767);
/*     */     
/* 711 */     for (int i = 0; i < 16; i++) {
/* 712 */       ItemStack outputStack = new ItemStack(paintbrushItem);
/* 713 */       NBTUtils.getItemData(outputStack).func_74774_a("color", (byte)i);
/* 714 */       BCRegistry.INSTANCE.addShapelessRecipe(outputStack, new Object[] { anyPaintbrush, EnumColor.fromId(i).getDye() });
/*     */     } 
/*     */ 
/*     */     
/* 718 */     BCRegistry.INSTANCE.addShapelessRecipe(new ItemStack((Item)listItem, 1, 1), new Object[] { new ItemStack((Item)listItem, 1, 0) });
/*     */     
/* 720 */     if (Loader.isModLoaded("BuildCraft|Silicon")) {
/* 721 */       CoreSiliconRecipes.loadSiliconRecipes();
/*     */     } else {
/* 723 */       BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Item)listItem, 1, 1), new Object[] { "ppp", "pYp", "ppp", Character.valueOf('p'), Items.field_151121_aF, Character.valueOf('Y'), "dyeGreen" });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void processIMCRequests(FMLInterModComms.IMCEvent event) {
/* 730 */     InterModComms.processIMC(event);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void cleanRegistries(WorldEvent.Unload event) {
/* 735 */     for (IWorldProperty property : BuildCraftAPI.worldProperties.values()) {
/* 736 */       property.clear();
/*     */     }
/* 738 */     if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
/* 739 */       TilePathMarker.clearAvailableMarkersList(event.world); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\BuildCraftCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */