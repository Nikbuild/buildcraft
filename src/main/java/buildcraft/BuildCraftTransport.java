/*     */ package buildcraft;
/*     */ 
/*     */ import buildcraft.api.blueprints.BuilderAPI;
/*     */ import buildcraft.api.blueprints.SchematicTile;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.core.EnumColor;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.facades.FacadeAPI;
/*     */ import buildcraft.api.facades.IFacadeItem;
/*     */ import buildcraft.api.gates.GateExpansions;
/*     */ import buildcraft.api.gates.IGateExpansion;
/*     */ import buildcraft.api.lists.ListRegistry;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.IActionProvider;
/*     */ import buildcraft.api.statements.ITriggerInternal;
/*     */ import buildcraft.api.statements.ITriggerProvider;
/*     */ import buildcraft.api.statements.StatementManager;
/*     */ import buildcraft.api.transport.IStripesHandler;
/*     */ import buildcraft.api.transport.PipeManager;
/*     */ import buildcraft.api.transport.PipeWire;
/*     */ import buildcraft.core.BCCreativeTab;
/*     */ import buildcraft.core.BCRegistry;
/*     */ import buildcraft.core.CompatHooks;
/*     */ import buildcraft.core.DefaultProps;
/*     */ import buildcraft.core.IMCHandler;
/*     */ import buildcraft.core.InterModComms;
/*     */ import buildcraft.core.PowerMode;
/*     */ import buildcraft.core.config.ConfigManager;
/*     */ import buildcraft.core.lib.items.ItemBuildCraft;
/*     */ import buildcraft.core.lib.network.ChannelHandler;
/*     */ import buildcraft.core.lib.network.PacketSide;
/*     */ import buildcraft.core.lib.utils.ColorUtils;
/*     */ import buildcraft.transport.BlockFilteredBuffer;
/*     */ import buildcraft.transport.BlockGenericPipe;
/*     */ import buildcraft.transport.FacadePluggable;
/*     */ import buildcraft.transport.IDiamondPipe;
/*     */ import buildcraft.transport.IMCHandlerTransport;
/*     */ import buildcraft.transport.ItemFacade;
/*     */ import buildcraft.transport.ItemGateCopier;
/*     */ import buildcraft.transport.ItemPipe;
/*     */ import buildcraft.transport.ItemPipeWire;
/*     */ import buildcraft.transport.LensFilterHandler;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeActionProvider;
/*     */ import buildcraft.transport.PipeColoringRecipe;
/*     */ import buildcraft.transport.PipeEventBus;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeTriggerProvider;
/*     */ import buildcraft.transport.TileFilteredBuffer;
/*     */ import buildcraft.transport.TileGenericPipe;
/*     */ import buildcraft.transport.TransportGuiHandler;
/*     */ import buildcraft.transport.TransportProxy;
/*     */ import buildcraft.transport.TransportSiliconRecipes;
/*     */ import buildcraft.transport.WireIconProvider;
/*     */ import buildcraft.transport.gates.GateDefinition;
/*     */ import buildcraft.transport.gates.GateExpansionLightSensor;
/*     */ import buildcraft.transport.gates.GateExpansionPulsar;
/*     */ import buildcraft.transport.gates.GateExpansionRedstoneFader;
/*     */ import buildcraft.transport.gates.GateExpansionTimer;
/*     */ import buildcraft.transport.gates.GatePluggable;
/*     */ import buildcraft.transport.gates.ItemGate;
/*     */ import buildcraft.transport.network.PacketFluidUpdate;
/*     */ import buildcraft.transport.network.PacketHandlerTransport;
/*     */ import buildcraft.transport.network.PacketPipeTransportItemStack;
/*     */ import buildcraft.transport.network.PacketPipeTransportItemStackRequest;
/*     */ import buildcraft.transport.network.PacketPipeTransportTraveler;
/*     */ import buildcraft.transport.network.PacketPowerUpdate;
/*     */ import buildcraft.transport.pipes.PipeFluidsClay;
/*     */ import buildcraft.transport.pipes.PipeFluidsCobblestone;
/*     */ import buildcraft.transport.pipes.PipeFluidsDiamond;
/*     */ import buildcraft.transport.pipes.PipeFluidsEmerald;
/*     */ import buildcraft.transport.pipes.PipeFluidsGold;
/*     */ import buildcraft.transport.pipes.PipeFluidsIron;
/*     */ import buildcraft.transport.pipes.PipeFluidsQuartz;
/*     */ import buildcraft.transport.pipes.PipeFluidsSandstone;
/*     */ import buildcraft.transport.pipes.PipeFluidsStone;
/*     */ import buildcraft.transport.pipes.PipeFluidsVoid;
/*     */ import buildcraft.transport.pipes.PipeFluidsWood;
/*     */ import buildcraft.transport.pipes.PipeItemsClay;
/*     */ import buildcraft.transport.pipes.PipeItemsCobblestone;
/*     */ import buildcraft.transport.pipes.PipeItemsDaizuli;
/*     */ import buildcraft.transport.pipes.PipeItemsDiamond;
/*     */ import buildcraft.transport.pipes.PipeItemsEmerald;
/*     */ import buildcraft.transport.pipes.PipeItemsEmzuli;
/*     */ import buildcraft.transport.pipes.PipeItemsGold;
/*     */ import buildcraft.transport.pipes.PipeItemsIron;
/*     */ import buildcraft.transport.pipes.PipeItemsLapis;
/*     */ import buildcraft.transport.pipes.PipeItemsObsidian;
/*     */ import buildcraft.transport.pipes.PipeItemsQuartz;
/*     */ import buildcraft.transport.pipes.PipeItemsSandstone;
/*     */ import buildcraft.transport.pipes.PipeItemsStone;
/*     */ import buildcraft.transport.pipes.PipeItemsStripes;
/*     */ import buildcraft.transport.pipes.PipeItemsVoid;
/*     */ import buildcraft.transport.pipes.PipeItemsWood;
/*     */ import buildcraft.transport.pipes.PipePowerCobblestone;
/*     */ import buildcraft.transport.pipes.PipePowerDiamond;
/*     */ import buildcraft.transport.pipes.PipePowerEmerald;
/*     */ import buildcraft.transport.pipes.PipePowerGold;
/*     */ import buildcraft.transport.pipes.PipePowerIron;
/*     */ import buildcraft.transport.pipes.PipePowerQuartz;
/*     */ import buildcraft.transport.pipes.PipePowerSandstone;
/*     */ import buildcraft.transport.pipes.PipePowerStone;
/*     */ import buildcraft.transport.pipes.PipePowerWood;
/*     */ import buildcraft.transport.pipes.PipeStructureCobblestone;
/*     */ import buildcraft.transport.pluggable.ItemLens;
/*     */ import buildcraft.transport.pluggable.ItemPlug;
/*     */ import buildcraft.transport.pluggable.ItemPowerAdapter;
/*     */ import buildcraft.transport.pluggable.LensPluggable;
/*     */ import buildcraft.transport.pluggable.PlugPluggable;
/*     */ import buildcraft.transport.pluggable.PowerAdapterPluggable;
/*     */ import buildcraft.transport.render.PipeRendererTESR;
/*     */ import buildcraft.transport.schematics.BptPipeFiltered;
/*     */ import buildcraft.transport.schematics.BptPipeRotatable;
/*     */ import buildcraft.transport.schematics.SchematicPipe;
/*     */ import buildcraft.transport.statements.ActionEnergyPulsar;
/*     */ import buildcraft.transport.statements.ActionExtractionPreset;
/*     */ import buildcraft.transport.statements.ActionParameterSignal;
/*     */ import buildcraft.transport.statements.ActionPipeColor;
/*     */ import buildcraft.transport.statements.ActionPipeDirection;
/*     */ import buildcraft.transport.statements.ActionPowerLimiter;
/*     */ import buildcraft.transport.statements.ActionRedstoneFaderOutput;
/*     */ import buildcraft.transport.statements.ActionSignalOutput;
/*     */ import buildcraft.transport.statements.ActionSingleEnergyPulse;
/*     */ import buildcraft.transport.statements.ActionValve;
/*     */ import buildcraft.transport.statements.TriggerClockTimer;
/*     */ import buildcraft.transport.statements.TriggerLightSensor;
/*     */ import buildcraft.transport.statements.TriggerParameterSignal;
/*     */ import buildcraft.transport.statements.TriggerPipeContents;
/*     */ import buildcraft.transport.statements.TriggerPipeSignal;
/*     */ import buildcraft.transport.statements.TriggerRedstoneFaderInput;
/*     */ import buildcraft.transport.stripes.PipeExtensionListener;
/*     */ import buildcraft.transport.stripes.StripesHandlerArrow;
/*     */ import buildcraft.transport.stripes.StripesHandlerBucket;
/*     */ import buildcraft.transport.stripes.StripesHandlerDispenser;
/*     */ import buildcraft.transport.stripes.StripesHandlerEntityInteract;
/*     */ import buildcraft.transport.stripes.StripesHandlerHoe;
/*     */ import buildcraft.transport.stripes.StripesHandlerMinecartDestroy;
/*     */ import buildcraft.transport.stripes.StripesHandlerPipeWires;
/*     */ import buildcraft.transport.stripes.StripesHandlerPipes;
/*     */ import buildcraft.transport.stripes.StripesHandlerPlaceBlock;
/*     */ import buildcraft.transport.stripes.StripesHandlerPlant;
/*     */ import buildcraft.transport.stripes.StripesHandlerRightClick;
/*     */ import buildcraft.transport.stripes.StripesHandlerShears;
/*     */ import buildcraft.transport.stripes.StripesHandlerUse;
/*     */ import cpw.mods.fml.client.event.ConfigChangedEvent;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.Loader;
/*     */ import cpw.mods.fml.common.Mod;
/*     */ import cpw.mods.fml.common.Mod.EventHandler;
/*     */ import cpw.mods.fml.common.Mod.Instance;
/*     */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLInterModComms;
/*     */ import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
/*     */ import cpw.mods.fml.common.event.FMLPostInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLServerStartingEvent;
/*     */ import cpw.mods.fml.common.event.FMLServerStoppingEvent;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.gameevent.TickEvent;
/*     */ import cpw.mods.fml.common.network.IGuiHandler;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemMinecart;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.client.event.TextureStitchEvent;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.oredict.RecipeSorter;
/*     */ import net.minecraftforge.oredict.ShapedOreRecipe;
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
/*     */ @Mod(version = "7.1.26", modid = "BuildCraft|Transport", name = "Buildcraft Transport", dependencies = "required-after:BuildCraft|Core@7.1.26")
/*     */ public class BuildCraftTransport
/*     */   extends BuildCraftMod
/*     */ {
/*     */   @Instance("BuildCraft|Transport")
/*     */   public static BuildCraftTransport instance;
/*     */   public static float pipeDurability;
/*     */   public static int pipeFluidsBaseFlowRate;
/*     */   public static boolean facadeTreatBlacklistAsWhitelist;
/*     */   public static boolean additionalWaterproofingRecipe;
/*     */   public static boolean facadeForceNonLaserRecipe;
/*     */   public static boolean showAllFacadesCreative;
/*     */   public static BlockGenericPipe genericPipeBlock;
/*     */   public static BlockFilteredBuffer filteredBufferBlock;
/*     */   public static Item pipeWaterproof;
/*     */   public static Item pipeGate;
/*     */   public static Item pipeWire;
/*     */   public static Item plugItem;
/*     */   public static Item lensItem;
/*     */   public static Item powerAdapterItem;
/*     */   public static Item pipeStructureCobblestone;
/*     */   public static Item gateCopier;
/*     */   public static ItemFacade facadeItem;
/*     */   public static Item pipeItemsWood;
/*     */   public static Item pipeItemsEmerald;
/*     */   public static Item pipeItemsStone;
/*     */   public static Item pipeItemsCobblestone;
/*     */   public static Item pipeItemsIron;
/*     */   public static Item pipeItemsQuartz;
/*     */   public static Item pipeItemsGold;
/*     */   public static Item pipeItemsDiamond;
/*     */   public static Item pipeItemsObsidian;
/*     */   public static Item pipeItemsLapis;
/*     */   public static Item pipeItemsDaizuli;
/*     */   public static Item pipeItemsVoid;
/*     */   public static Item pipeItemsSandstone;
/*     */   public static Item pipeItemsEmzuli;
/*     */   public static Item pipeItemsStripes;
/*     */   public static Item pipeItemsClay;
/*     */   public static Item pipeFluidsWood;
/*     */   public static Item pipeFluidsCobblestone;
/*     */   public static Item pipeFluidsStone;
/*     */   public static Item pipeFluidsQuartz;
/*     */   public static Item pipeFluidsIron;
/*     */   public static Item pipeFluidsGold;
/*     */   public static Item pipeFluidsVoid;
/*     */   public static Item pipeFluidsSandstone;
/*     */   public static Item pipeFluidsEmerald;
/*     */   public static Item pipeFluidsDiamond;
/*     */   public static Item pipeFluidsClay;
/*     */   public static Item pipePowerWood;
/*     */   public static Item pipePowerCobblestone;
/*     */   public static Item pipePowerStone;
/*     */   public static Item pipePowerQuartz;
/*     */   public static Item pipePowerIron;
/*     */   public static Item pipePowerGold;
/*     */   public static Item pipePowerDiamond;
/*     */   public static Item pipePowerEmerald;
/*     */   public static Item pipePowerSandstone;
/*     */   public static String[] facadeBlacklist;
/*     */   public static ITriggerInternal triggerLightSensorBright;
/*     */   public static ITriggerInternal triggerLightSensorDark;
/* 257 */   public static ITriggerInternal[] triggerPipe = new ITriggerInternal[(TriggerPipeContents.PipeContents.values()).length];
/* 258 */   public static ITriggerInternal[] triggerPipeWireActive = new ITriggerInternal[(PipeWire.values()).length];
/* 259 */   public static ITriggerInternal[] triggerPipeWireInactive = new ITriggerInternal[(PipeWire.values()).length];
/* 260 */   public static ITriggerInternal[] triggerTimer = new ITriggerInternal[TriggerClockTimer.Time.VALUES.length];
/* 261 */   public static ITriggerInternal[] triggerRedstoneLevel = new ITriggerInternal[15];
/* 262 */   public static IActionInternal[] actionPipeWire = (IActionInternal[])new ActionSignalOutput[(PipeWire.values()).length];
/* 263 */   public static IActionInternal actionEnergyPulser = (IActionInternal)new ActionEnergyPulsar();
/* 264 */   public static IActionInternal actionSingleEnergyPulse = (IActionInternal)new ActionSingleEnergyPulse();
/* 265 */   public static IActionInternal[] actionPipeColor = new IActionInternal[16];
/* 266 */   public static IActionInternal[] actionPipeDirection = new IActionInternal[16];
/* 267 */   public static IActionInternal[] actionPowerLimiter = new IActionInternal[7];
/* 268 */   public static IActionInternal[] actionRedstoneLevel = new IActionInternal[15];
/* 269 */   public static IActionInternal actionExtractionPresetRed = (IActionInternal)new ActionExtractionPreset(EnumColor.RED);
/* 270 */   public static IActionInternal actionExtractionPresetBlue = (IActionInternal)new ActionExtractionPreset(EnumColor.BLUE);
/* 271 */   public static IActionInternal actionExtractionPresetGreen = (IActionInternal)new ActionExtractionPreset(EnumColor.GREEN);
/* 272 */   public static IActionInternal actionExtractionPresetYellow = (IActionInternal)new ActionExtractionPreset(EnumColor.YELLOW);
/* 273 */   public static IActionInternal[] actionValve = new IActionInternal[4];
/*     */   
/*     */   public static boolean debugPrintFacadeList = false;
/*     */   
/*     */   public static boolean usePipeLoss = false;
/* 278 */   public static float gateCostMultiplier = 1.0F;
/*     */   
/*     */   public static PipeExtensionListener pipeExtensionListener;
/*     */   
/* 282 */   private static LinkedList<PipeRecipe> pipeRecipes = new LinkedList<PipeRecipe>();
/*     */   
/*     */   private static ChannelHandler transportChannelHandler;
/* 285 */   public IIconProvider pipeIconProvider = (IIconProvider)new PipeIconProvider();
/* 286 */   public IIconProvider wireIconProvider = (IIconProvider)new WireIconProvider();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void preInit(FMLPreInitializationEvent evt) {
/*     */     try {
/* 297 */       BuildCraftCore.mainConfigManager.register("experimental.kinesisPowerLossOnTravel", Boolean.valueOf(false), "Should kinesis pipes lose power over distance (think IC2 or BC pre-3.7)?", ConfigManager.RestartRequirement.WORLD);
/*     */       
/* 299 */       BuildCraftCore.mainConfigManager.register("general.pipes.hardness", Double.valueOf(DefaultProps.PIPES_DURABILITY), "How hard to break should a pipe be?", ConfigManager.RestartRequirement.NONE);
/* 300 */       BuildCraftCore.mainConfigManager.register("general.pipes.baseFluidRate", Integer.valueOf(DefaultProps.PIPES_FLUIDS_BASE_FLOW_RATE), "What should the base flow rate of a fluid pipe be?", ConfigManager.RestartRequirement.GAME)
/* 301 */         .setMinValue(1).setMaxValue(40);
/* 302 */       BuildCraftCore.mainConfigManager.register("debug.printFacadeList", Boolean.valueOf(false), "Print a list of all registered facades.", ConfigManager.RestartRequirement.GAME);
/* 303 */       BuildCraftCore.mainConfigManager.register("general.pipes.facadeShowAllInCreative", Boolean.valueOf(true), "Should all BC facades be shown in Creative/NEI, or just a few carefully chosen ones?", ConfigManager.RestartRequirement.GAME);
/* 304 */       BuildCraftCore.mainConfigManager.register("general.pipes.slimeballWaterproofRecipe", Boolean.valueOf(false), "Should I enable an alternate Waterproof recipe, based on slimeballs?", ConfigManager.RestartRequirement.GAME);
/* 305 */       BuildCraftCore.mainConfigManager.register("power.gateCostMultiplier", Double.valueOf(1.0D), "What should be the multiplier of all gate power costs?", ConfigManager.RestartRequirement.GAME);
/* 306 */       BuildCraftCore.mainConfigManager.register("general.pipes.facadeBlacklist", new String[] { Block.field_149771_c
/* 307 */             .func_148750_c(Blocks.field_150378_br), Block.field_149771_c
/* 308 */             .func_148750_c(Blocks.field_150349_c), Block.field_149771_c
/* 309 */             .func_148750_c(Blocks.field_150362_t), Block.field_149771_c
/* 310 */             .func_148750_c(Blocks.field_150361_u), Block.field_149771_c
/* 311 */             .func_148750_c(Blocks.field_150428_aP), Block.field_149771_c
/* 312 */             .func_148750_c(Blocks.field_150374_bv), Block.field_149771_c
/* 313 */             .func_148750_c(Blocks.field_150474_ac), Block.field_149771_c
/* 314 */             .func_148750_c(Blocks.field_150418_aU), Block.field_149771_c
/* 315 */             .func_148750_c(Blocks.field_150379_bu), Block.field_149771_c
/* 316 */             .func_148750_c(Blocks.field_150334_T), Block.field_149771_c
/* 317 */             .func_148750_c(Blocks.field_150373_bw), Block.field_149771_c
/* 318 */             .func_148750_c(Blocks.field_150360_v) }, "What block types should be blacklisted from being a facade?", ConfigManager.RestartRequirement.GAME);
/*     */       
/* 320 */       BuildCraftCore.mainConfigManager.register("general.pipes.facadeBlacklistAsWhitelist", Boolean.valueOf(false), "Should the blacklist be treated as a whitelist instead?", ConfigManager.RestartRequirement.GAME);
/* 321 */       BuildCraftCore.mainConfigManager.register("general.pipes.facadeNoLaserRecipe", Boolean.valueOf(false), "Should non-laser (crafting table) facade recipes be forced?", ConfigManager.RestartRequirement.GAME);
/*     */       
/* 323 */       reloadConfig(ConfigManager.RestartRequirement.GAME);
/*     */       
/* 325 */       if (showAllFacadesCreative) {
/* 326 */         new BCCreativeTab("facades");
/*     */       }
/*     */       
/* 329 */       filteredBufferBlock = new BlockFilteredBuffer();
/* 330 */       BCRegistry.INSTANCE.registerBlock(filteredBufferBlock.func_149663_c("filteredBufferBlock"), false);
/*     */       
/* 332 */       pipeWaterproof = (Item)new ItemBuildCraft();
/* 333 */       pipeWaterproof.func_77655_b("pipeWaterproof");
/* 334 */       BCRegistry.INSTANCE.registerItem(pipeWaterproof, false);
/*     */       
/* 336 */       genericPipeBlock = (BlockGenericPipe)CompatHooks.INSTANCE.getBlock(BlockGenericPipe.class);
/* 337 */       BCRegistry.INSTANCE.registerBlock(genericPipeBlock.func_149663_c("pipeBlock"), ItemBlock.class, true);
/*     */       
/* 339 */       pipeItemsWood = buildPipe((Class)PipeItemsWood.class, new Object[] { "plankWood", "blockGlassColorless", "plankWood" });
/* 340 */       pipeItemsEmerald = buildPipe((Class)PipeItemsEmerald.class, new Object[] { "gemEmerald", "blockGlassColorless", "gemEmerald" });
/* 341 */       pipeItemsCobblestone = buildPipe((Class)PipeItemsCobblestone.class, new Object[] { "cobblestone", "blockGlassColorless", "cobblestone" });
/* 342 */       pipeItemsStone = buildPipe((Class)PipeItemsStone.class, new Object[] { "stone", "blockGlassColorless", "stone" });
/* 343 */       pipeItemsQuartz = buildPipe((Class)PipeItemsQuartz.class, new Object[] { "blockQuartz", "blockGlassColorless", "blockQuartz" });
/* 344 */       pipeItemsIron = buildPipe((Class)PipeItemsIron.class, new Object[] { "ingotIron", "blockGlassColorless", "ingotIron" });
/* 345 */       pipeItemsGold = buildPipe((Class)PipeItemsGold.class, new Object[] { "ingotGold", "blockGlassColorless", "ingotGold" });
/* 346 */       pipeItemsDiamond = buildPipe((Class)PipeItemsDiamond.class, new Object[] { "gemDiamond", "blockGlassColorless", "gemDiamond" });
/* 347 */       pipeItemsObsidian = buildPipe((Class)PipeItemsObsidian.class, new Object[] { Blocks.field_150343_Z, "blockGlassColorless", Blocks.field_150343_Z });
/* 348 */       pipeItemsLapis = buildPipe((Class)PipeItemsLapis.class, new Object[] { "blockLapis", "blockGlassColorless", "blockLapis" });
/* 349 */       pipeItemsDaizuli = buildPipe((Class)PipeItemsDaizuli.class, new Object[] { "blockLapis", "blockGlassColorless", "gemDiamond" });
/* 350 */       pipeItemsSandstone = buildPipe((Class)PipeItemsSandstone.class, new Object[] { Blocks.field_150322_A, "blockGlassColorless", Blocks.field_150322_A });
/* 351 */       pipeItemsVoid = buildPipe((Class)PipeItemsVoid.class, new Object[] { "dyeBlack", "blockGlassColorless", "dustRedstone" });
/* 352 */       pipeItemsEmzuli = buildPipe((Class)PipeItemsEmzuli.class, new Object[] { "blockLapis", "blockGlassColorless", "gemEmerald" });
/* 353 */       pipeItemsStripes = buildPipe((Class)PipeItemsStripes.class, new Object[] { "gearGold", "blockGlassColorless", "gearGold" });
/* 354 */       pipeItemsClay = buildPipe((Class)PipeItemsClay.class, new Object[] { Blocks.field_150435_aG, "blockGlassColorless", Blocks.field_150435_aG });
/*     */       
/* 356 */       pipeFluidsWood = buildPipe((Class)PipeFluidsWood.class, new Object[] { pipeWaterproof, pipeItemsWood });
/* 357 */       pipeFluidsCobblestone = buildPipe((Class)PipeFluidsCobblestone.class, new Object[] { pipeWaterproof, pipeItemsCobblestone });
/* 358 */       pipeFluidsStone = buildPipe((Class)PipeFluidsStone.class, new Object[] { pipeWaterproof, pipeItemsStone });
/* 359 */       pipeFluidsQuartz = buildPipe((Class)PipeFluidsQuartz.class, new Object[] { pipeWaterproof, pipeItemsQuartz });
/* 360 */       pipeFluidsIron = buildPipe((Class)PipeFluidsIron.class, new Object[] { pipeWaterproof, pipeItemsIron });
/* 361 */       pipeFluidsGold = buildPipe((Class)PipeFluidsGold.class, new Object[] { pipeWaterproof, pipeItemsGold });
/* 362 */       pipeFluidsEmerald = buildPipe((Class)PipeFluidsEmerald.class, new Object[] { pipeWaterproof, pipeItemsEmerald });
/* 363 */       pipeFluidsDiamond = buildPipe((Class)PipeFluidsDiamond.class, new Object[] { pipeWaterproof, pipeItemsDiamond });
/* 364 */       pipeFluidsSandstone = buildPipe((Class)PipeFluidsSandstone.class, new Object[] { pipeWaterproof, pipeItemsSandstone });
/* 365 */       pipeFluidsVoid = buildPipe((Class)PipeFluidsVoid.class, new Object[] { pipeWaterproof, pipeItemsVoid });
/* 366 */       pipeFluidsClay = buildPipe((Class)PipeFluidsClay.class, new Object[] { pipeWaterproof, pipeItemsClay });
/*     */       
/* 368 */       pipePowerWood = buildPipe((Class)PipePowerWood.class, new Object[] { "dustRedstone", pipeItemsWood });
/* 369 */       pipePowerCobblestone = buildPipe((Class)PipePowerCobblestone.class, new Object[] { "dustRedstone", pipeItemsCobblestone });
/* 370 */       pipePowerStone = buildPipe((Class)PipePowerStone.class, new Object[] { "dustRedstone", pipeItemsStone });
/* 371 */       pipePowerQuartz = buildPipe((Class)PipePowerQuartz.class, new Object[] { "dustRedstone", pipeItemsQuartz });
/* 372 */       pipePowerIron = buildPipe((Class)PipePowerIron.class, new Object[] { "dustRedstone", pipeItemsIron });
/* 373 */       pipePowerGold = buildPipe((Class)PipePowerGold.class, new Object[] { "dustRedstone", pipeItemsGold });
/* 374 */       pipePowerDiamond = buildPipe((Class)PipePowerDiamond.class, new Object[] { "dustRedstone", pipeItemsDiamond });
/* 375 */       pipePowerEmerald = buildPipe((Class)PipePowerEmerald.class, new Object[] { "dustRedstone", pipeItemsEmerald });
/* 376 */       pipePowerSandstone = buildPipe((Class)PipePowerSandstone.class, new Object[] { "dustRedstone", pipeItemsSandstone });
/*     */       
/* 378 */       pipeStructureCobblestone = buildPipe((Class)PipeStructureCobblestone.class, new Object[] { Blocks.field_150347_e, Blocks.field_150351_n, Blocks.field_150347_e });
/*     */       
/* 380 */       pipeWire = (Item)new ItemPipeWire();
/* 381 */       BCRegistry.INSTANCE.registerItem(pipeWire, false);
/* 382 */       PipeWire.item = pipeWire;
/*     */       
/* 384 */       pipeGate = (Item)new ItemGate();
/* 385 */       pipeGate.func_77655_b("pipeGate");
/* 386 */       if (Loader.isModLoaded("BuildCraft|Silicon") && BCRegistry.INSTANCE.isItemEnabled(pipeGate)) {
/* 387 */         new BCCreativeTab("gates");
/*     */       }
/* 389 */       BCRegistry.INSTANCE.registerItem(pipeGate, false);
/*     */       
/* 391 */       facadeItem = new ItemFacade();
/* 392 */       facadeItem.func_77655_b("pipeFacade");
/* 393 */       BCRegistry.INSTANCE.registerItem((Item)facadeItem, false);
/* 394 */       FacadeAPI.facadeItem = (IFacadeItem)facadeItem;
/*     */       
/* 396 */       plugItem = (Item)new ItemPlug();
/* 397 */       plugItem.func_77655_b("pipePlug");
/* 398 */       BCRegistry.INSTANCE.registerItem(plugItem, false);
/*     */       
/* 400 */       lensItem = (Item)new ItemLens();
/* 401 */       lensItem.func_77655_b("pipeLens");
/* 402 */       BCRegistry.INSTANCE.registerItem(lensItem, false);
/*     */       
/* 404 */       powerAdapterItem = (Item)new ItemPowerAdapter();
/* 405 */       powerAdapterItem.func_77655_b("pipePowerAdapter");
/* 406 */       BCRegistry.INSTANCE.registerItem(powerAdapterItem, false);
/*     */       
/* 408 */       gateCopier = (Item)new ItemGateCopier();
/* 409 */       BCRegistry.INSTANCE.registerItem(gateCopier, false);
/*     */       
/* 411 */       for (TriggerPipeContents.PipeContents kind : TriggerPipeContents.PipeContents.values()) {
/* 412 */         triggerPipe[kind.ordinal()] = (ITriggerInternal)new TriggerPipeContents(kind);
/*     */       }
/*     */       
/* 415 */       for (PipeWire wire : PipeWire.values()) {
/* 416 */         triggerPipeWireActive[wire.ordinal()] = (ITriggerInternal)new TriggerPipeSignal(true, wire);
/* 417 */         triggerPipeWireInactive[wire.ordinal()] = (ITriggerInternal)new TriggerPipeSignal(false, wire);
/* 418 */         actionPipeWire[wire.ordinal()] = (IActionInternal)new ActionSignalOutput(wire);
/*     */       } 
/*     */       
/* 421 */       for (TriggerClockTimer.Time time : TriggerClockTimer.Time.VALUES) {
/* 422 */         triggerTimer[time.ordinal()] = (ITriggerInternal)new TriggerClockTimer(time);
/*     */       }
/*     */       
/* 425 */       for (int level = 0; level < triggerRedstoneLevel.length; level++) {
/* 426 */         triggerRedstoneLevel[level] = (ITriggerInternal)new TriggerRedstoneFaderInput(level + 1);
/* 427 */         actionRedstoneLevel[level] = (IActionInternal)new ActionRedstoneFaderOutput(level + 1);
/*     */       } 
/*     */       
/* 430 */       for (EnumColor color : EnumColor.VALUES) {
/* 431 */         actionPipeColor[color.ordinal()] = (IActionInternal)new ActionPipeColor(color);
/*     */       }
/*     */       
/* 434 */       for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
/* 435 */         actionPipeDirection[direction.ordinal()] = (IActionInternal)new ActionPipeDirection(direction);
/*     */       }
/*     */       
/* 438 */       for (ActionValve.ValveState state : ActionValve.ValveState.VALUES) {
/* 439 */         actionValve[state.ordinal()] = (IActionInternal)new ActionValve(state);
/*     */       }
/*     */       
/* 442 */       for (PowerMode limit : PowerMode.VALUES) {
/* 443 */         actionPowerLimiter[limit.ordinal()] = (IActionInternal)new ActionPowerLimiter(limit);
/*     */       }
/*     */       
/* 446 */       triggerLightSensorBright = (ITriggerInternal)new TriggerLightSensor(true);
/* 447 */       triggerLightSensorDark = (ITriggerInternal)new TriggerLightSensor(false);
/*     */     } finally {
/* 449 */       BuildCraftCore.mainConfiguration.save();
/*     */     } 
/*     */     
/* 452 */     InterModComms.registerHandler((IMCHandler)new IMCHandlerTransport());
/*     */   } private static class PipeRecipe {
/*     */     private PipeRecipe() {} boolean isShapeless = false; ItemStack result; Object[] input; }
/*     */   @EventHandler
/*     */   public void init(FMLInitializationEvent evt) {
/* 457 */     transportChannelHandler = new ChannelHandler();
/* 458 */     MinecraftForge.EVENT_BUS.register(this);
/*     */     
/* 460 */     transportChannelHandler.registerPacketType(PacketFluidUpdate.class, PacketSide.CLIENT_ONLY);
/* 461 */     transportChannelHandler.registerPacketType(PacketPipeTransportItemStack.class, PacketSide.CLIENT_ONLY);
/* 462 */     transportChannelHandler.registerPacketType(PacketPipeTransportItemStackRequest.class, PacketSide.SERVER_ONLY);
/* 463 */     transportChannelHandler.registerPacketType(PacketPipeTransportTraveler.class, PacketSide.CLIENT_ONLY);
/* 464 */     transportChannelHandler.registerPacketType(PacketPowerUpdate.class, PacketSide.CLIENT_ONLY);
/*     */     
/* 466 */     this
/* 467 */       .channels = NetworkRegistry.INSTANCE.newChannel("BC-TRANSPORT", new ChannelHandler[] { (ChannelHandler)transportChannelHandler, (ChannelHandler)new PacketHandlerTransport() });
/*     */     
/* 469 */     TransportProxy.proxy.registerTileEntities();
/*     */     
/* 471 */     BuilderAPI.schematicRegistry.registerSchematicBlock((Block)genericPipeBlock, SchematicPipe.class, new Object[0]);
/* 472 */     BuilderAPI.schematicRegistry.registerSchematicBlock((Block)filteredBufferBlock, SchematicTile.class, new Object[0]);
/*     */     
/* 474 */     new BptPipeRotatable(pipeItemsWood);
/* 475 */     new BptPipeRotatable(pipeFluidsWood);
/* 476 */     new BptPipeRotatable(pipeItemsIron);
/* 477 */     new BptPipeRotatable(pipeFluidsIron);
/* 478 */     new BptPipeRotatable(pipeItemsEmerald);
/* 479 */     new BptPipeRotatable(pipeFluidsEmerald);
/*     */     
/* 481 */     new BptPipeRotatable(pipeItemsDaizuli);
/* 482 */     new BptPipeRotatable(pipeItemsEmzuli);
/*     */     
/* 484 */     for (Item itemPipe : BlockGenericPipe.pipes.keySet()) {
/* 485 */       Class<? extends Pipe<?>> klazz = (Class<? extends Pipe<?>>)BlockGenericPipe.pipes.get(itemPipe);
/*     */       
/* 487 */       if (IDiamondPipe.class.isAssignableFrom(klazz)) {
/* 488 */         new BptPipeFiltered(itemPipe);
/*     */       }
/*     */     } 
/*     */     
/* 492 */     PipeEventBus.registerGlobalHandler(new LensFilterHandler());
/*     */     
/* 494 */     if (BCCreativeTab.isPresent("pipes")) {
/* 495 */       BCCreativeTab.get("pipes").setIcon(new ItemStack(pipeItemsDiamond, 1));
/*     */     }
/* 497 */     if (BCCreativeTab.isPresent("facades")) {
/* 498 */       BCCreativeTab.get("facades").setIcon(facadeItem.getFacadeForBlock(Blocks.field_150336_V, 0));
/*     */     }
/* 500 */     if (BCCreativeTab.isPresent("gates")) {
/* 501 */       BCCreativeTab.get("gates").setIcon(ItemGate.makeGateItem(GateDefinition.GateMaterial.DIAMOND, GateDefinition.GateLogic.AND));
/*     */     }
/*     */     
/* 504 */     StatementManager.registerParameterClass(TriggerParameterSignal.class);
/* 505 */     StatementManager.registerParameterClass(ActionParameterSignal.class);
/* 506 */     StatementManager.registerTriggerProvider((ITriggerProvider)new PipeTriggerProvider());
/* 507 */     StatementManager.registerActionProvider((IActionProvider)new PipeActionProvider());
/*     */ 
/*     */     
/* 510 */     PipeManager.registerStripesHandler((IStripesHandler)new StripesHandlerRightClick(), -32768);
/* 511 */     PipeManager.registerStripesHandler((IStripesHandler)new StripesHandlerDispenser(), -49152);
/* 512 */     PipeManager.registerStripesHandler((IStripesHandler)new StripesHandlerPlant(), 0);
/* 513 */     PipeManager.registerStripesHandler((IStripesHandler)new StripesHandlerBucket(), 0);
/* 514 */     PipeManager.registerStripesHandler((IStripesHandler)new StripesHandlerArrow(), 0);
/* 515 */     PipeManager.registerStripesHandler((IStripesHandler)new StripesHandlerShears(), 0);
/* 516 */     PipeManager.registerStripesHandler((IStripesHandler)new StripesHandlerPipes(), 0);
/* 517 */     PipeManager.registerStripesHandler((IStripesHandler)new StripesHandlerPipeWires(), 0);
/* 518 */     PipeManager.registerStripesHandler((IStripesHandler)new StripesHandlerEntityInteract(), 0);
/* 519 */     PipeManager.registerStripesHandler((IStripesHandler)new StripesHandlerPlaceBlock(), -65536);
/* 520 */     PipeManager.registerStripesHandler((IStripesHandler)new StripesHandlerUse(), -131072);
/* 521 */     PipeManager.registerStripesHandler((IStripesHandler)new StripesHandlerHoe(), 0);
/*     */     
/* 523 */     StripesHandlerDispenser.items.add(ItemMinecart.class);
/* 524 */     StripesHandlerRightClick.items.add(Items.field_151110_aK);
/* 525 */     StripesHandlerRightClick.items.add(Items.field_151126_ay);
/* 526 */     StripesHandlerRightClick.items.add(Items.field_151062_by);
/* 527 */     StripesHandlerUse.items.add(Items.field_151152_bP);
/*     */ 
/*     */     
/* 530 */     PipeManager.registerStripesHandler((IStripesHandler)new StripesHandlerMinecartDestroy(), 0);
/*     */     
/* 532 */     PipeManager.registerPipePluggable(FacadePluggable.class, "facade");
/* 533 */     PipeManager.registerPipePluggable(GatePluggable.class, "gate");
/* 534 */     PipeManager.registerPipePluggable(LensPluggable.class, "lens");
/* 535 */     PipeManager.registerPipePluggable(PlugPluggable.class, "plug");
/* 536 */     PipeManager.registerPipePluggable(PowerAdapterPluggable.class, "powerAdapter");
/*     */     
/* 538 */     GateExpansions.registerExpansion((IGateExpansion)GateExpansionPulsar.INSTANCE);
/* 539 */     GateExpansions.registerExpansion((IGateExpansion)GateExpansionTimer.INSTANCE);
/* 540 */     GateExpansions.registerExpansion((IGateExpansion)GateExpansionRedstoneFader.INSTANCE);
/* 541 */     GateExpansions.registerExpansion((IGateExpansion)GateExpansionLightSensor.INSTANCE, new ItemStack((Block)Blocks.field_150453_bW));
/*     */     
/* 543 */     if (BuildCraftCore.loadDefaultRecipes) {
/* 544 */       loadRecipes();
/*     */     }
/*     */     
/* 547 */     TransportProxy.proxy.registerRenderers();
/* 548 */     NetworkRegistry.INSTANCE.registerGuiHandler(instance, (IGuiHandler)new TransportGuiHandler());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void postInit(FMLPostInitializationEvent evt) {
/* 553 */     facadeItem.initialize();
/*     */     
/* 555 */     if (debugPrintFacadeList) {
/*     */       try {
/* 557 */         PrintWriter writer = new PrintWriter("FacadeDebug.txt", "UTF-8");
/* 558 */         writer.println("*** REGISTERED FACADES ***");
/* 559 */         for (ItemStack stack : ItemFacade.allFacades) {
/* 560 */           if ((facadeItem.getBlocksForFacade(stack)).length > 0) {
/* 561 */             writer.println(Block.field_149771_c.func_148750_c(facadeItem.getBlocksForFacade(stack)[0]) + ":" + facadeItem.getMetaValuesForFacade(stack)[0]);
/*     */           }
/*     */         } 
/* 564 */         writer.close();
/* 565 */       } catch (Exception e) {
/* 566 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 570 */     ListRegistry.itemClassAsType.add(ItemPipe.class);
/* 571 */     ListRegistry.itemClassAsType.add(ItemGate.class);
/* 572 */     ListRegistry.itemClassAsType.add(ItemFacade.class);
/* 573 */     ListRegistry.itemClassAsType.add(ItemPipeWire.class);
/*     */   }
/*     */   
/*     */   public void reloadConfig(ConfigManager.RestartRequirement restartType) {
/* 577 */     if (restartType == ConfigManager.RestartRequirement.GAME) {
/* 578 */       facadeTreatBlacklistAsWhitelist = BuildCraftCore.mainConfigManager.get("general.pipes.facadeBlacklistAsWhitelist").getBoolean();
/* 579 */       facadeBlacklist = BuildCraftCore.mainConfigManager.get("general.pipes.facadeBlacklist").getStringList();
/* 580 */       gateCostMultiplier = (float)BuildCraftCore.mainConfigManager.get("power.gateCostMultiplier").getDouble();
/* 581 */       additionalWaterproofingRecipe = BuildCraftCore.mainConfigManager.get("general.pipes.slimeballWaterproofRecipe").getBoolean();
/* 582 */       debugPrintFacadeList = BuildCraftCore.mainConfigManager.get("debug.printFacadeList").getBoolean();
/* 583 */       pipeFluidsBaseFlowRate = BuildCraftCore.mainConfigManager.get("general.pipes.baseFluidRate").getInt();
/* 584 */       facadeForceNonLaserRecipe = BuildCraftCore.mainConfigManager.get("general.pipes.facadeNoLaserRecipe").getBoolean();
/* 585 */       showAllFacadesCreative = BuildCraftCore.mainConfigManager.get("general.pipes.facadeShowAllInCreative").getBoolean();
/*     */       
/* 587 */       reloadConfig(ConfigManager.RestartRequirement.WORLD);
/* 588 */     } else if (restartType == ConfigManager.RestartRequirement.WORLD) {
/* 589 */       usePipeLoss = BuildCraftCore.mainConfigManager.get("experimental.kinesisPowerLossOnTravel").getBoolean();
/*     */       
/* 591 */       reloadConfig(ConfigManager.RestartRequirement.NONE);
/*     */     } else {
/* 593 */       pipeDurability = (float)BuildCraftCore.mainConfigManager.get("general.pipes.hardness").getDouble();
/*     */       
/* 595 */       if (BuildCraftCore.mainConfiguration.hasChanged()) {
/* 596 */         BuildCraftCore.mainConfiguration.save();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
/* 603 */     if ("BuildCraft|Core".equals(event.modID)) {
/* 604 */       reloadConfig(event.isWorldRunning ? ConfigManager.RestartRequirement.NONE : ConfigManager.RestartRequirement.WORLD);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void textureHook(TextureStitchEvent.Pre event) {
/* 611 */     if (event.map.func_130086_a() == 0) {
/* 612 */       for (Item i : BlockGenericPipe.pipes.keySet()) {
/* 613 */         Pipe<?> dummyPipe = BlockGenericPipe.createPipe(i);
/* 614 */         if (dummyPipe != null) {
/* 615 */           dummyPipe.getIconProvider().registerIcons((IIconRegister)event.map);
/*     */         }
/*     */       } 
/*     */       
/* 619 */       this.wireIconProvider.registerIcons((IIconRegister)event.map);
/*     */       
/* 621 */       for (GateDefinition.GateMaterial material : GateDefinition.GateMaterial.VALUES) {
/* 622 */         material.registerBlockIcon((IIconRegister)event.map);
/*     */       }
/*     */       
/* 625 */       for (GateDefinition.GateLogic logic : GateDefinition.GateLogic.VALUES) {
/* 626 */         logic.registerBlockIcon((IIconRegister)event.map);
/*     */       }
/*     */       
/* 629 */       for (IGateExpansion expansion : GateExpansions.getExpansions()) {
/* 630 */         expansion.registerBlockOverlay((IIconRegister)event.map);
/*     */       }
/*     */       
/* 633 */       PipeRendererTESR.INSTANCE.onTextureReload();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void serverLoading(FMLServerStartingEvent event) {
/* 639 */     pipeExtensionListener = new PipeExtensionListener();
/* 640 */     FMLCommonHandler.instance().bus().register(pipeExtensionListener);
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void serverUnloading(FMLServerStoppingEvent event) {
/* 646 */     for (WorldServer w : DimensionManager.getWorlds()) {
/* 647 */       pipeExtensionListener.tick(new TickEvent.WorldTickEvent(Side.SERVER, TickEvent.Phase.END, (World)w));
/*     */     }
/* 649 */     FMLCommonHandler.instance().bus().unregister(pipeExtensionListener);
/* 650 */     pipeExtensionListener = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadRecipes() {
/* 655 */     GameRegistry.addShapelessRecipe(new ItemStack(pipeWaterproof, 1), new Object[] { new ItemStack(Items.field_151100_aR, 1, 2) });
/* 656 */     if (additionalWaterproofingRecipe) {
/* 657 */       GameRegistry.addShapelessRecipe(new ItemStack(pipeWaterproof, 1), new Object[] { new ItemStack(Items.field_151123_aH) });
/*     */     }
/*     */ 
/*     */     
/* 661 */     for (PipeRecipe pipe : pipeRecipes) {
/* 662 */       if (pipe.isShapeless) {
/* 663 */         BCRegistry.INSTANCE.addShapelessRecipe(pipe.result, pipe.input); continue;
/*     */       } 
/* 665 */       BCRegistry.INSTANCE.addCraftingRecipe(pipe.result, pipe.input);
/*     */     } 
/*     */ 
/*     */     
/* 669 */     GameRegistry.addRecipe((IRecipe)new PipeColoringRecipe());
/* 670 */     RecipeSorter.register("buildcraft:pipecoloring", PipeColoringRecipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
/*     */     
/* 672 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)filteredBufferBlock, 1), new Object[] { "wdw", "wcw", "wpw", 
/* 673 */           Character.valueOf('w'), "plankWood", Character.valueOf('d'), pipeItemsDiamond, 
/* 674 */           Character.valueOf('c'), "chestWood", Character.valueOf('p'), Blocks.field_150331_J });
/*     */ 
/*     */ 
/*     */     
/* 678 */     facadeItem.getClass(); GameRegistry.addRecipe((IRecipe)new ItemFacade.FacadeRecipe(facadeItem));
/* 679 */     RecipeSorter.register("facadeTurningHelper", ItemFacade.FacadeRecipe.class, RecipeSorter.Category.SHAPELESS, "");
/*     */ 
/*     */     
/* 682 */     GameRegistry.addShapelessRecipe(new ItemStack(plugItem, 4), new Object[] { new ItemStack(pipeStructureCobblestone) });
/* 683 */     GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(powerAdapterItem, 4), new Object[] { "scs", "sbs", "sas", Character.valueOf('s'), pipeStructureCobblestone, Character.valueOf('a'), Items.field_151137_ax, Character.valueOf('b'), "gearStone", Character.valueOf('c'), "ingotGold" }));
/*     */     
/* 685 */     if (Loader.isModLoaded("BuildCraft|Silicon")) {
/* 686 */       TransportSiliconRecipes.loadSiliconRecipes();
/*     */     } else {
/* 688 */       BCLog.logger.warn("**********************************************");
/* 689 */       BCLog.logger.warn("*   You are using the BuildCraft Transport   *");
/* 690 */       BCLog.logger.warn("*  module WITHOUT the Silicon module. Gates  *");
/* 691 */       BCLog.logger.warn("*           will not be available.           *");
/* 692 */       BCLog.logger.warn("**********************************************");
/*     */ 
/*     */ 
/*     */       
/* 696 */       for (int i = 0; i < 16; i++) {
/* 697 */         String dye = ColorUtils.getOreDictionaryName(15 - i);
/* 698 */         GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(lensItem, 8, i), new Object[] { "OSO", "SGS", "OSO", 
/*     */                 
/* 700 */                 Character.valueOf('O'), "ingotIron", Character.valueOf('S'), dye, Character.valueOf('G'), "blockGlass" }));
/*     */         
/* 702 */         GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(lensItem, 8, i + 16), new Object[] { "OSO", "SGS", "OSO", 
/*     */                 
/* 704 */                 Character.valueOf('O'), Blocks.field_150411_aY, Character.valueOf('S'), dye, Character.valueOf('G'), "blockGlass" }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void processIMCRequests(FMLInterModComms.IMCEvent event) {
/* 712 */     InterModComms.processIMC(event);
/*     */   }
/*     */   
/*     */   public static Item buildPipe(Class<? extends Pipe<?>> clas, Object... ingredients) {
/* 716 */     return buildPipe(clas, BCCreativeTab.get("pipes"), ingredients);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Item buildPipe(Class<? extends Pipe<?>> clas, String descr, BCCreativeTab creativeTab, Object... ingredients) {
/* 723 */     return buildPipe(clas, creativeTab, ingredients);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Item buildPipe(Class<? extends Pipe<?>> clas, BCCreativeTab creativeTab, Object... ingredients) {
/* 728 */     if (!BCRegistry.INSTANCE.isEnabled("pipes", clas.getSimpleName())) {
/* 729 */       return null;
/*     */     }
/*     */     
/* 732 */     ItemPipe res = BlockGenericPipe.registerPipe(clas, creativeTab);
/* 733 */     res.func_77655_b(clas.getSimpleName());
/*     */     
/* 735 */     for (Object o : ingredients) {
/* 736 */       if (o == null) {
/* 737 */         return (Item)res;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 742 */     if (ingredients.length == 3) {
/* 743 */       for (int i = 0; i < 17; i++) {
/* 744 */         Object glass; PipeRecipe recipe = new PipeRecipe();
/*     */ 
/*     */         
/* 747 */         if (i == 0) {
/* 748 */           glass = ingredients[1];
/*     */         } else {
/* 750 */           glass = "blockGlass" + EnumColor.fromId(15 - i - 1).getName();
/*     */         } 
/*     */         
/* 753 */         recipe.result = new ItemStack((Item)res, 8, i);
/* 754 */         recipe.input = new Object[] { "ABC", Character.valueOf('A'), ingredients[0], Character.valueOf('B'), glass, Character.valueOf('C'), ingredients[2] };
/*     */         
/* 756 */         pipeRecipes.add(recipe);
/*     */       } 
/* 758 */     } else if (ingredients.length == 2) {
/* 759 */       for (int i = 0; i < 17; i++) {
/* 760 */         PipeRecipe recipe = new PipeRecipe();
/*     */         
/* 762 */         Object left = ingredients[0];
/* 763 */         Object right = ingredients[1];
/*     */         
/* 765 */         if (ingredients[1] instanceof ItemPipe) {
/* 766 */           right = new ItemStack((Item)right, 1, i);
/*     */         }
/*     */         
/* 769 */         recipe.isShapeless = true;
/* 770 */         recipe.result = new ItemStack((Item)res, 1, i);
/* 771 */         recipe.input = new Object[] { left, right };
/*     */         
/* 773 */         pipeRecipes.add(recipe);
/*     */         
/* 775 */         if (ingredients[1] instanceof ItemPipe && clas != PipeStructureCobblestone.class) {
/* 776 */           PipeRecipe uncraft = new PipeRecipe();
/* 777 */           uncraft.isShapeless = true;
/* 778 */           uncraft.input = new Object[] { recipe.result };
/* 779 */           uncraft.result = (ItemStack)right;
/* 780 */           pipeRecipes.add(uncraft);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 785 */     return (Item)res;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void whiteListAppliedEnergetics(FMLInitializationEvent event) {
/* 790 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileGenericPipe.class
/* 791 */         .getCanonicalName());
/* 792 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileFilteredBuffer.class
/* 793 */         .getCanonicalName());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void remap(FMLMissingMappingsEvent event) {
/* 798 */     for (FMLMissingMappingsEvent.MissingMapping mapping : event.get()) {
/* 799 */       if (mapping.type == GameRegistry.Type.ITEM && 
/* 800 */         mapping.name.equals("BuildCraft|Transport:robotStation"))
/* 801 */         mapping.remap((Item)Item.field_150901_e.func_82594_a("BuildCraft|Robotics:robotStation")); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\BuildCraftTransport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */