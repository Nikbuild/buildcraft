/*     */ package buildcraft;
/*     */ 
/*     */ import buildcraft.api.blueprints.BuilderAPI;
/*     */ import buildcraft.api.blueprints.SchematicTile;
/*     */ import buildcraft.api.boards.RedstoneBoardNBT;
/*     */ import buildcraft.api.boards.RedstoneBoardRegistry;
/*     */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*     */ import buildcraft.api.lists.ListRegistry;
/*     */ import buildcraft.api.recipes.BuildcraftRecipeRegistry;
/*     */ import buildcraft.api.recipes.IIntegrationRecipe;
/*     */ import buildcraft.api.recipes.IProgrammingRecipe;
/*     */ import buildcraft.api.robots.IRobotRegistryProvider;
/*     */ import buildcraft.api.robots.RobotManager;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.IActionProvider;
/*     */ import buildcraft.api.statements.ITriggerInternal;
/*     */ import buildcraft.api.statements.ITriggerProvider;
/*     */ import buildcraft.api.statements.StatementManager;
/*     */ import buildcraft.api.transport.PipeManager;
/*     */ import buildcraft.core.BCCreativeTab;
/*     */ import buildcraft.core.BCRegistry;
/*     */ import buildcraft.core.CompatHooks;
/*     */ import buildcraft.core.InterModComms;
/*     */ import buildcraft.core.config.ConfigManager;
/*     */ import buildcraft.robotics.BlockRequester;
/*     */ import buildcraft.robotics.BlockZonePlan;
/*     */ import buildcraft.robotics.BoardProgrammingRecipe;
/*     */ import buildcraft.robotics.DockingStationPipe;
/*     */ import buildcraft.robotics.EntityRobot;
/*     */ import buildcraft.robotics.ImplRedstoneBoardRegistry;
/*     */ import buildcraft.robotics.ItemRedstoneBoard;
/*     */ import buildcraft.robotics.ItemRobot;
/*     */ import buildcraft.robotics.ItemRobotStation;
/*     */ import buildcraft.robotics.RobotIntegrationRecipe;
/*     */ import buildcraft.robotics.RobotRegistryProvider;
/*     */ import buildcraft.robotics.RobotStationPluggable;
/*     */ import buildcraft.robotics.RoboticsGuiHandler;
/*     */ import buildcraft.robotics.RoboticsProxy;
/*     */ import buildcraft.robotics.TileRequester;
/*     */ import buildcraft.robotics.TileZonePlan;
/*     */ import buildcraft.robotics.ai.AIRobotAttack;
/*     */ import buildcraft.robotics.ai.AIRobotBreak;
/*     */ import buildcraft.robotics.ai.AIRobotDeliverRequested;
/*     */ import buildcraft.robotics.ai.AIRobotDisposeItems;
/*     */ import buildcraft.robotics.ai.AIRobotFetchAndEquipItemStack;
/*     */ import buildcraft.robotics.ai.AIRobotFetchItem;
/*     */ import buildcraft.robotics.ai.AIRobotGoAndLinkToDock;
/*     */ import buildcraft.robotics.ai.AIRobotGoto;
/*     */ import buildcraft.robotics.ai.AIRobotGotoBlock;
/*     */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*     */ import buildcraft.robotics.ai.AIRobotGotoStation;
/*     */ import buildcraft.robotics.ai.AIRobotGotoStationAndLoad;
/*     */ import buildcraft.robotics.ai.AIRobotGotoStationAndLoadFluids;
/*     */ import buildcraft.robotics.ai.AIRobotGotoStationAndUnload;
/*     */ import buildcraft.robotics.ai.AIRobotGotoStationToLoad;
/*     */ import buildcraft.robotics.ai.AIRobotGotoStationToLoadFluids;
/*     */ import buildcraft.robotics.ai.AIRobotGotoStationToUnload;
/*     */ import buildcraft.robotics.ai.AIRobotGotoStationToUnloadFluids;
/*     */ import buildcraft.robotics.ai.AIRobotHarvest;
/*     */ import buildcraft.robotics.ai.AIRobotLoad;
/*     */ import buildcraft.robotics.ai.AIRobotLoadFluids;
/*     */ import buildcraft.robotics.ai.AIRobotMain;
/*     */ import buildcraft.robotics.ai.AIRobotPlant;
/*     */ import buildcraft.robotics.ai.AIRobotPumpBlock;
/*     */ import buildcraft.robotics.ai.AIRobotRecharge;
/*     */ import buildcraft.robotics.ai.AIRobotSearchAndGotoBlock;
/*     */ import buildcraft.robotics.ai.AIRobotSearchAndGotoStation;
/*     */ import buildcraft.robotics.ai.AIRobotSearchBlock;
/*     */ import buildcraft.robotics.ai.AIRobotSearchEntity;
/*     */ import buildcraft.robotics.ai.AIRobotSearchRandomGroundBlock;
/*     */ import buildcraft.robotics.ai.AIRobotSearchStackRequest;
/*     */ import buildcraft.robotics.ai.AIRobotSearchStation;
/*     */ import buildcraft.robotics.ai.AIRobotShutdown;
/*     */ import buildcraft.robotics.ai.AIRobotSleep;
/*     */ import buildcraft.robotics.ai.AIRobotStraightMoveTo;
/*     */ import buildcraft.robotics.ai.AIRobotUnload;
/*     */ import buildcraft.robotics.ai.AIRobotUnloadFluids;
/*     */ import buildcraft.robotics.ai.AIRobotUseToolOnBlock;
/*     */ import buildcraft.robotics.boards.BCBoardNBT;
/*     */ import buildcraft.robotics.boards.BoardRobotBomber;
/*     */ import buildcraft.robotics.boards.BoardRobotBuilder;
/*     */ import buildcraft.robotics.boards.BoardRobotButcher;
/*     */ import buildcraft.robotics.boards.BoardRobotCarrier;
/*     */ import buildcraft.robotics.boards.BoardRobotDelivery;
/*     */ import buildcraft.robotics.boards.BoardRobotEmpty;
/*     */ import buildcraft.robotics.boards.BoardRobotFarmer;
/*     */ import buildcraft.robotics.boards.BoardRobotFluidCarrier;
/*     */ import buildcraft.robotics.boards.BoardRobotHarvester;
/*     */ import buildcraft.robotics.boards.BoardRobotKnight;
/*     */ import buildcraft.robotics.boards.BoardRobotLeaveCutter;
/*     */ import buildcraft.robotics.boards.BoardRobotLumberjack;
/*     */ import buildcraft.robotics.boards.BoardRobotMiner;
/*     */ import buildcraft.robotics.boards.BoardRobotPicker;
/*     */ import buildcraft.robotics.boards.BoardRobotPlanter;
/*     */ import buildcraft.robotics.boards.BoardRobotPump;
/*     */ import buildcraft.robotics.boards.BoardRobotShovelman;
/*     */ import buildcraft.robotics.boards.BoardRobotStripes;
/*     */ import buildcraft.robotics.boards.RedstoneBoardRobotEmptyNBT;
/*     */ import buildcraft.robotics.map.MapManager;
/*     */ import buildcraft.robotics.statements.ActionRobotFilter;
/*     */ import buildcraft.robotics.statements.ActionRobotFilterTool;
/*     */ import buildcraft.robotics.statements.ActionRobotGotoStation;
/*     */ import buildcraft.robotics.statements.ActionRobotWakeUp;
/*     */ import buildcraft.robotics.statements.ActionRobotWorkInArea;
/*     */ import buildcraft.robotics.statements.ActionStationAcceptFluids;
/*     */ import buildcraft.robotics.statements.ActionStationAcceptItems;
/*     */ import buildcraft.robotics.statements.ActionStationForbidRobot;
/*     */ import buildcraft.robotics.statements.ActionStationProvideFluids;
/*     */ import buildcraft.robotics.statements.ActionStationProvideItems;
/*     */ import buildcraft.robotics.statements.ActionStationRequestItems;
/*     */ import buildcraft.robotics.statements.ActionStationRequestItemsMachine;
/*     */ import buildcraft.robotics.statements.RobotsActionProvider;
/*     */ import buildcraft.robotics.statements.RobotsTriggerProvider;
/*     */ import buildcraft.robotics.statements.StatementParameterMapLocation;
/*     */ import buildcraft.robotics.statements.StatementParameterRobot;
/*     */ import buildcraft.robotics.statements.TriggerRobotInStation;
/*     */ import buildcraft.robotics.statements.TriggerRobotLinked;
/*     */ import buildcraft.robotics.statements.TriggerRobotSleep;
/*     */ import buildcraft.silicon.ItemRedstoneChipset;
/*     */ import cpw.mods.fml.client.event.ConfigChangedEvent;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.Loader;
/*     */ import cpw.mods.fml.common.Mod;
/*     */ import cpw.mods.fml.common.Mod.EventHandler;
/*     */ import cpw.mods.fml.common.Mod.Instance;
/*     */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLInterModComms;
/*     */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLServerStartedEvent;
/*     */ import cpw.mods.fml.common.event.FMLServerStartingEvent;
/*     */ import cpw.mods.fml.common.event.FMLServerStoppingEvent;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.network.IGuiHandler;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import cpw.mods.fml.common.registry.EntityRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.client.event.TextureStitchEvent;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mod(name = "BuildCraft Robotics", version = "7.1.26", useMetadata = false, modid = "BuildCraft|Robotics", dependencies = "required-after:BuildCraft|Core@7.1.26")
/*     */ public class BuildCraftRobotics
/*     */   extends BuildCraftMod
/*     */ {
/*     */   @Instance("BuildCraft|Robotics")
/*     */   public static BuildCraftRobotics instance;
/*     */   public static BlockZonePlan zonePlanBlock;
/*     */   public static BlockRequester requesterBlock;
/*     */   public static ItemRedstoneBoard redstoneBoard;
/*     */   public static Item robotItem;
/*     */   public static Item robotStationItem;
/* 167 */   public static ITriggerInternal triggerRobotSleep = (ITriggerInternal)new TriggerRobotSleep();
/* 168 */   public static ITriggerInternal triggerRobotInStation = (ITriggerInternal)new TriggerRobotInStation();
/* 169 */   public static ITriggerInternal triggerRobotLinked = (ITriggerInternal)new TriggerRobotLinked(false);
/* 170 */   public static ITriggerInternal triggerRobotReserved = (ITriggerInternal)new TriggerRobotLinked(true);
/*     */   
/* 172 */   public static IActionInternal actionRobotGotoStation = (IActionInternal)new ActionRobotGotoStation();
/* 173 */   public static IActionInternal actionRobotWakeUp = (IActionInternal)new ActionRobotWakeUp();
/* 174 */   public static IActionInternal actionRobotWorkInArea = (IActionInternal)new ActionRobotWorkInArea(ActionRobotWorkInArea.AreaType.WORK);
/* 175 */   public static IActionInternal actionRobotLoadUnloadArea = (IActionInternal)new ActionRobotWorkInArea(ActionRobotWorkInArea.AreaType.LOAD_UNLOAD);
/* 176 */   public static IActionInternal actionRobotFilter = (IActionInternal)new ActionRobotFilter();
/* 177 */   public static IActionInternal actionRobotFilterTool = (IActionInternal)new ActionRobotFilterTool();
/* 178 */   public static IActionInternal actionStationRequestItems = (IActionInternal)new ActionStationRequestItems();
/* 179 */   public static IActionInternal actionStationProvideItems = (IActionInternal)new ActionStationProvideItems();
/* 180 */   public static IActionInternal actionStationAcceptFluids = (IActionInternal)new ActionStationAcceptFluids();
/* 181 */   public static IActionInternal actionStationProvideFluids = (IActionInternal)new ActionStationProvideFluids();
/* 182 */   public static IActionInternal actionStationForceRobot = (IActionInternal)new ActionStationForbidRobot(true);
/* 183 */   public static IActionInternal actionStationForbidRobot = (IActionInternal)new ActionStationForbidRobot(false);
/* 184 */   public static IActionInternal actionStationAcceptItems = (IActionInternal)new ActionStationAcceptItems();
/* 185 */   public static IActionInternal actionStationMachineRequestItems = (IActionInternal)new ActionStationRequestItemsMachine();
/*     */   
/*     */   public static List<String> blacklistedRobots;
/*     */   
/*     */   public static MapManager manager;
/*     */   private static Thread managerThread;
/*     */   
/*     */   @EventHandler
/*     */   public void preInit(FMLPreInitializationEvent evt) {
/* 194 */     BuildCraftCore.mainConfigManager.register("general", "boards.blacklist", new String[0], "Blacklisted robots boards", ConfigManager.RestartRequirement.GAME);
/*     */     
/* 196 */     reloadConfig(ConfigManager.RestartRequirement.GAME);
/*     */     
/* 198 */     robotItem = (new ItemRobot()).func_77655_b("robot");
/* 199 */     BCRegistry.INSTANCE.registerItem(robotItem, false);
/*     */     
/* 201 */     robotStationItem = (new ItemRobotStation()).func_77655_b("robotStation");
/* 202 */     BCRegistry.INSTANCE.registerItem(robotStationItem, false);
/*     */     
/* 204 */     redstoneBoard = new ItemRedstoneBoard();
/* 205 */     redstoneBoard.func_77655_b("redstone_board");
/* 206 */     BCRegistry.INSTANCE.registerItem((Item)redstoneBoard, false);
/*     */     
/* 208 */     zonePlanBlock = (BlockZonePlan)CompatHooks.INSTANCE.getBlock(BlockZonePlan.class);
/* 209 */     zonePlanBlock.func_149663_c("zonePlan");
/* 210 */     BCRegistry.INSTANCE.registerBlock((Block)zonePlanBlock, false);
/*     */     
/* 212 */     requesterBlock = (BlockRequester)CompatHooks.INSTANCE.getBlock(BlockRequester.class);
/* 213 */     requesterBlock.func_149663_c("requester");
/* 214 */     BCRegistry.INSTANCE.registerBlock((Block)requesterBlock, false);
/*     */     
/* 216 */     RedstoneBoardRegistry.instance = (RedstoneBoardRegistry)new ImplRedstoneBoardRegistry();
/*     */     
/* 218 */     RedstoneBoardRegistry.instance.setEmptyRobotBoard((RedstoneBoardRobotNBT)RedstoneBoardRobotEmptyNBT.instance);
/*     */ 
/*     */ 
/*     */     
/* 222 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotPicker", "picker", BoardRobotPicker.class, "green"), 8000);
/* 223 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotCarrier", "carrier", BoardRobotCarrier.class, "green"), 8000);
/* 224 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotFluidCarrier", "fluidCarrier", BoardRobotFluidCarrier.class, "green"), 8000);
/*     */ 
/*     */ 
/*     */     
/* 228 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotLumberjack", "lumberjack", BoardRobotLumberjack.class, "blue"), 32000);
/* 229 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotHarvester", "harvester", BoardRobotHarvester.class, "blue"), 32000);
/* 230 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:miner", "miner", BoardRobotMiner.class, "blue"), 32000);
/* 231 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotPlanter", "planter", BoardRobotPlanter.class, "blue"), 32000);
/* 232 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotFarmer", "farmer", BoardRobotFarmer.class, "blue"), 32000);
/* 233 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:leave_cutter", "leaveCutter", BoardRobotLeaveCutter.class, "blue"), 32000);
/* 234 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotButcher", "butcher", BoardRobotButcher.class, "blue"), 32000);
/* 235 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:shovelman", "shovelman", BoardRobotShovelman.class, "blue"), 32000);
/* 236 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotPump", "pump", BoardRobotPump.class, "blue"), 32000);
/*     */ 
/*     */ 
/*     */     
/* 240 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotDelivery", "delivery", BoardRobotDelivery.class, "green"), 128000);
/* 241 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotKnight", "knight", BoardRobotKnight.class, "red"), 128000);
/* 242 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotBomber", "bomber", BoardRobotBomber.class, "red"), 128000);
/* 243 */     RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotStripes", "stripes", BoardRobotStripes.class, "yellow"), 128000);
/*     */ 
/*     */ 
/*     */     
/* 247 */     if (Loader.isModLoaded("BuildCraft|Builders")) {
/* 248 */       RedstoneBoardRegistry.instance.registerBoardType((RedstoneBoardNBT)new BCBoardNBT("buildcraft:boardRobotBuilder", "builder", BoardRobotBuilder.class, "yellow"), 512000);
/*     */     }
/*     */     
/* 251 */     StatementManager.registerParameterClass(StatementParameterRobot.class);
/* 252 */     StatementManager.registerParameterClass(StatementParameterMapLocation.class);
/* 253 */     StatementManager.registerActionProvider((IActionProvider)new RobotsActionProvider());
/* 254 */     StatementManager.registerTriggerProvider((ITriggerProvider)new RobotsTriggerProvider());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void init(FMLInitializationEvent evt) {
/* 259 */     NetworkRegistry.INSTANCE.registerGuiHandler(instance, (IGuiHandler)new RoboticsGuiHandler());
/* 260 */     MinecraftForge.EVENT_BUS.register(this);
/*     */     
/* 262 */     if (BuildCraftCore.loadDefaultRecipes && Loader.isModLoaded("BuildCraft|Silicon")) {
/* 263 */       loadRecipes();
/*     */     }
/*     */     
/* 266 */     if (BCCreativeTab.isPresent("boards")) {
/* 267 */       BCCreativeTab.get("boards").setIcon(new ItemStack((Item)redstoneBoard, 1));
/*     */     }
/*     */     
/* 270 */     BuilderAPI.schematicRegistry.registerSchematicBlock((Block)requesterBlock, SchematicTile.class, new Object[0]);
/*     */     
/* 272 */     PipeManager.registerPipePluggable(RobotStationPluggable.class, "robotStation");
/* 273 */     EntityRegistry.registerModEntity(EntityRobot.class, "bcRobot", 10, instance, 50, 1, true);
/*     */     
/* 275 */     BCRegistry.INSTANCE.registerTileEntity(TileZonePlan.class, "net.minecraft.src.buildcraft.commander.TileZonePlan");
/* 276 */     BCRegistry.INSTANCE.registerTileEntity(TileRequester.class, "net.minecraft.src.buildcraft.commander.TileRequester");
/*     */     
/* 278 */     RobotManager.registryProvider = (IRobotRegistryProvider)new RobotRegistryProvider();
/*     */     
/* 280 */     RobotManager.registerAIRobot(AIRobotMain.class, "aiRobotMain", "buildcraft.core.robots.AIRobotMain");
/* 281 */     RobotManager.registerAIRobot(BoardRobotEmpty.class, "boardRobotEmpty");
/* 282 */     RobotManager.registerAIRobot(BoardRobotBomber.class, "boardRobotBomber", "buildcraft.core.robots.boards.BoardRobotBomber");
/* 283 */     if (Loader.isModLoaded("BuildCraft|Builders")) {
/* 284 */       RobotManager.registerAIRobot(BoardRobotBuilder.class, "boardRobotBuilder", "buildcraft.core.robots.boards.BoardRobotBuilder");
/*     */     }
/* 286 */     RobotManager.registerAIRobot(BoardRobotButcher.class, "boardRobotButcher", "buildcraft.core.robots.boards.BoardRobotButcher");
/* 287 */     RobotManager.registerAIRobot(BoardRobotCarrier.class, "boardRobotCarrier", "buildcraft.core.robots.boards.BoardRobotCarrier");
/* 288 */     RobotManager.registerAIRobot(BoardRobotDelivery.class, "boardRobotDelivery", "buildcraft.core.robots.boards.BoardRobotDelivery");
/* 289 */     RobotManager.registerAIRobot(BoardRobotFarmer.class, "boardRobotFarmer", "buildcraft.core.robots.boards.BoardRobotFarmer");
/* 290 */     RobotManager.registerAIRobot(BoardRobotFluidCarrier.class, "boardRobotFluidCarrier", "buildcraft.core.robots.boards.BoardRobotFluidCarrier");
/* 291 */     RobotManager.registerAIRobot(BoardRobotHarvester.class, "boardRobotHarvester", "buildcraft.core.robots.boards.BoardRobotHarvester");
/* 292 */     RobotManager.registerAIRobot(BoardRobotKnight.class, "boardRobotKnight", "buildcraft.core.robots.boards.BoardRobotKnight");
/* 293 */     RobotManager.registerAIRobot(BoardRobotLeaveCutter.class, "boardRobotLeaveCutter", "buildcraft.core.robots.boards.BoardRobotLeaveCutter");
/* 294 */     RobotManager.registerAIRobot(BoardRobotLumberjack.class, "boardRobotLumberjack", "buildcraft.core.robots.boards.BoardRobotLumberjack");
/* 295 */     RobotManager.registerAIRobot(BoardRobotMiner.class, "boardRobotMiner", "buildcraft.core.robots.boards.BoardRobotMiner");
/* 296 */     RobotManager.registerAIRobot(BoardRobotPicker.class, "boardRobotPicker", "buildcraft.core.robots.boards.BoardRobotPicker");
/* 297 */     RobotManager.registerAIRobot(BoardRobotPlanter.class, "boardRobotPlanter", "buildcraft.core.robots.boards.BoardRobotPlanter");
/* 298 */     RobotManager.registerAIRobot(BoardRobotPump.class, "boardRobotPump", "buildcraft.core.robots.boards.BoardRobotPump");
/* 299 */     RobotManager.registerAIRobot(BoardRobotShovelman.class, "boardRobotShovelman", "buildcraft.core.robots.boards.BoardRobotShovelman");
/* 300 */     RobotManager.registerAIRobot(BoardRobotStripes.class, "boardRobotStripes", "buildcraft.core.robots.boards.BoardRobotStripes");
/* 301 */     RobotManager.registerAIRobot(AIRobotAttack.class, "aiRobotAttack", "buildcraft.core.robots.AIRobotAttack");
/* 302 */     RobotManager.registerAIRobot(AIRobotBreak.class, "aiRobotBreak", "buildcraft.core.robots.AIRobotBreak");
/* 303 */     RobotManager.registerAIRobot(AIRobotDeliverRequested.class, "aiRobotDeliverRequested", "buildcraft.core.robots.AIRobotDeliverRequested");
/* 304 */     RobotManager.registerAIRobot(AIRobotDisposeItems.class, "aiRobotDisposeItems", "buildcraft.core.robots.AIRobotDisposeItems");
/* 305 */     RobotManager.registerAIRobot(AIRobotFetchAndEquipItemStack.class, "aiRobotFetchAndEquipItemStack", "buildcraft.core.robots.AIRobotFetchAndEquipItemStack");
/* 306 */     RobotManager.registerAIRobot(AIRobotFetchItem.class, "aiRobotFetchItem", "buildcraft.core.robots.AIRobotFetchItem");
/* 307 */     RobotManager.registerAIRobot(AIRobotGoAndLinkToDock.class, "aiRobotGoAndLinkToDock", "buildcraft.core.robots.AIRobotGoAndLinkToDock");
/* 308 */     RobotManager.registerAIRobot(AIRobotGoto.class, "aiRobotGoto", "buildcraft.core.robots.AIRobotGoto");
/* 309 */     RobotManager.registerAIRobot(AIRobotGotoBlock.class, "aiRobotGotoBlock", "buildcraft.core.robots.AIRobotGotoBlock");
/* 310 */     RobotManager.registerAIRobot(AIRobotGotoSleep.class, "aiRobotGotoSleep", "buildcraft.core.robots.AIRobotGotoSleep");
/* 311 */     RobotManager.registerAIRobot(AIRobotGotoStation.class, "aiRobotGotoStation", "buildcraft.core.robots.AIRobotGotoStation");
/* 312 */     RobotManager.registerAIRobot(AIRobotGotoStationAndLoad.class, "aiRobotGotoStationAndLoad", "buildcraft.core.robots.AIRobotGotoStationAndLoad");
/* 313 */     RobotManager.registerAIRobot(AIRobotGotoStationAndLoadFluids.class, "aiRobotGotoStationAndLoadFluids", "buildcraft.core.robots.AIRobotGotoStationAndLoadFluids");
/* 314 */     RobotManager.registerAIRobot(AIRobotGotoStationAndUnload.class, "aiRobotGotoStationAndUnload", "buildcraft.core.robots.AIRobotGotoStationAndUnload");
/* 315 */     RobotManager.registerAIRobot(AIRobotGotoStationToLoad.class, "aiRobotGotoStationToLoad", "buildcraft.core.robots.AIRobotGotoStationToLoad");
/* 316 */     RobotManager.registerAIRobot(AIRobotGotoStationToLoadFluids.class, "aiRobotGotoStationToLoadFluids", "buildcraft.core.robots.AIRobotGotoStationToLoadFluids");
/* 317 */     RobotManager.registerAIRobot(AIRobotGotoStationToUnload.class, "aiRobotGotoStationToUnload", "buildcraft.core.robots.AIRobotGotoStationToUnload");
/* 318 */     RobotManager.registerAIRobot(AIRobotGotoStationToUnloadFluids.class, "aiRobotGotoStationToUnloadFluids", "buildcraft.core.robots.AIRobotGotoStationToUnloadFluids");
/* 319 */     RobotManager.registerAIRobot(AIRobotHarvest.class, "aiRobotHarvest");
/* 320 */     RobotManager.registerAIRobot(AIRobotLoad.class, "aiRobotLoad", "buildcraft.core.robots.AIRobotLoad");
/* 321 */     RobotManager.registerAIRobot(AIRobotLoadFluids.class, "aiRobotLoadFluids", "buildcraft.core.robots.AIRobotLoadFluids");
/* 322 */     RobotManager.registerAIRobot(AIRobotPlant.class, "aiRobotPlant");
/* 323 */     RobotManager.registerAIRobot(AIRobotPumpBlock.class, "aiRobotPumpBlock", "buildcraft.core.robots.AIRobotPumpBlock");
/* 324 */     RobotManager.registerAIRobot(AIRobotRecharge.class, "aiRobotRecharge", "buildcraft.core.robots.AIRobotRecharge");
/* 325 */     RobotManager.registerAIRobot(AIRobotSearchAndGotoBlock.class, "aiRobotSearchAndGoToBlock");
/* 326 */     RobotManager.registerAIRobot(AIRobotSearchAndGotoStation.class, "aiRobotSearchAndGotoStation", "buildcraft.core.robots.AIRobotSearchAndGotoStation");
/* 327 */     RobotManager.registerAIRobot(AIRobotSearchBlock.class, "aiRobotSearchBlock", "buildcraft.core.robots.AIRobotSearchBlock");
/* 328 */     RobotManager.registerAIRobot(AIRobotSearchEntity.class, "aiRobotSearchEntity", "buildcraft.core.robots.AIRobotSearchEntity");
/* 329 */     RobotManager.registerAIRobot(AIRobotSearchRandomGroundBlock.class, "aiRobotSearchRandomGroundBlock", "buildcraft.core.robots.AIRobotSearchRandomGroundBlock");
/* 330 */     RobotManager.registerAIRobot(AIRobotSearchStackRequest.class, "aiRobotSearchStackRequest", "buildcraft.core.robots.AIRobotSearchStackRequest");
/* 331 */     RobotManager.registerAIRobot(AIRobotSearchStation.class, "aiRobotSearchStation", "buildcraft.core.robots.AIRobotSearchStation");
/* 332 */     RobotManager.registerAIRobot(AIRobotShutdown.class, "aiRobotShutdown");
/* 333 */     RobotManager.registerAIRobot(AIRobotSleep.class, "aiRobotSleep", "buildcraft.core.robots.AIRobotSleep");
/* 334 */     RobotManager.registerAIRobot(AIRobotStraightMoveTo.class, "aiRobotStraightMoveTo", "buildcraft.core.robots.AIRobotStraightMoveTo");
/* 335 */     RobotManager.registerAIRobot(AIRobotUnload.class, "aiRobotUnload", "buildcraft.core.robots.AIRobotUnload");
/* 336 */     RobotManager.registerAIRobot(AIRobotUnloadFluids.class, "aiRobotUnloadFluids", "buildcraft.core.robots.AIRobotUnloadFluids");
/* 337 */     RobotManager.registerAIRobot(AIRobotUseToolOnBlock.class, "aiRobotUseToolOnBlock", "buildcraft.core.robots.AIRobotUseToolOnBlock");
/*     */     
/* 339 */     RobotManager.registerDockingStation(DockingStationPipe.class, "dockingStationPipe");
/*     */     
/* 341 */     RoboticsProxy.proxy.registerRenderers();
/*     */     
/* 343 */     ListRegistry.itemClassAsType.add(ItemRobot.class);
/*     */   }
/*     */   
/*     */   public static void loadRecipes() {
/* 347 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack(robotItem), new Object[] { "PPP", "PRP", "C C", 
/*     */ 
/*     */ 
/*     */           
/* 351 */           Character.valueOf('P'), "ingotIron", 
/* 352 */           Character.valueOf('R'), "crystalRedstone", 
/* 353 */           Character.valueOf('C'), ItemRedstoneChipset.Chipset.DIAMOND.getStack() });
/*     */     
/* 355 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Item)redstoneBoard), new Object[] { "PPP", "PRP", "PPP", 
/*     */ 
/*     */ 
/*     */           
/* 359 */           Character.valueOf('R'), "dustRedstone", 
/* 360 */           Character.valueOf('P'), Items.field_151121_aF });
/*     */     
/* 362 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)zonePlanBlock, 1, 0), new Object[] { "IRI", "GMG", "IDI", 
/*     */ 
/*     */ 
/*     */           
/* 366 */           Character.valueOf('M'), Items.field_151148_bJ, 
/* 367 */           Character.valueOf('R'), "dustRedstone", 
/* 368 */           Character.valueOf('G'), "gearGold", 
/* 369 */           Character.valueOf('D'), "gearDiamond", 
/* 370 */           Character.valueOf('I'), "ingotIron" });
/*     */     
/* 372 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)requesterBlock, 1, 0), new Object[] { "IPI", "GCG", "IRI", 
/*     */ 
/*     */ 
/*     */           
/* 376 */           Character.valueOf('C'), "chestWood", 
/* 377 */           Character.valueOf('R'), "dustRedstone", 
/* 378 */           Character.valueOf('P'), Blocks.field_150331_J, 
/* 379 */           Character.valueOf('G'), "gearIron", 
/* 380 */           Character.valueOf('I'), "ingotIron" });
/*     */     
/* 382 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack(robotStationItem), new Object[] { "   ", " I ", "ICI", 
/* 383 */           Character.valueOf('I'), "ingotIron", 
/* 384 */           Character.valueOf('C'), ItemRedstoneChipset.Chipset.GOLD.getStack() });
/*     */     
/* 386 */     BuildcraftRecipeRegistry.programmingTable.addRecipe((IProgrammingRecipe)new BoardProgrammingRecipe());
/* 387 */     BuildcraftRecipeRegistry.integrationTable.addRecipe((IIntegrationRecipe)new RobotIntegrationRecipe());
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void textureHook(TextureStitchEvent.Pre event) {
/* 393 */     if (event.map.func_130086_a() == 1) {
/* 394 */       RedstoneBoardRegistry.instance.registerIcons((IIconRegister)event.map);
/*     */     }
/*     */   }
/*     */   
/*     */   private void stopMapManager() {
/* 399 */     if (manager != null) {
/* 400 */       manager.stop();
/* 401 */       MinecraftForge.EVENT_BUS.unregister(manager);
/* 402 */       FMLCommonHandler.instance().bus().unregister(manager);
/*     */     } 
/*     */     
/* 405 */     if (managerThread != null) {
/* 406 */       managerThread.interrupt();
/*     */     }
/*     */     
/* 409 */     managerThread = null;
/* 410 */     manager = null;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void serverUnload(FMLServerStoppingEvent event) {
/* 415 */     stopMapManager();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void serverLoad(FMLServerStartingEvent event) {
/* 420 */     File f = new File(DimensionManager.getCurrentSaveRootDirectory(), "buildcraft/zonemap");
/*     */     
/*     */     try {
/* 423 */       f.mkdirs();
/* 424 */     } catch (Exception e) {
/* 425 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 428 */     stopMapManager();
/*     */     
/* 430 */     manager = new MapManager(f);
/* 431 */     managerThread = new Thread((Runnable)manager);
/* 432 */     managerThread.start();
/*     */     
/* 434 */     BoardRobotPicker.onServerStart();
/*     */     
/* 436 */     MinecraftForge.EVENT_BUS.register(manager);
/* 437 */     FMLCommonHandler.instance().bus().register(manager);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void serverLoadFinish(FMLServerStartedEvent event) {
/* 442 */     manager.initialize();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void processRequests(FMLInterModComms.IMCEvent event) {
/* 447 */     InterModComms.processIMC(event);
/*     */   }
/*     */   
/*     */   public void reloadConfig(ConfigManager.RestartRequirement restartType) {
/* 451 */     if (restartType == ConfigManager.RestartRequirement.GAME) {
/*     */       
/* 453 */       blacklistedRobots = new ArrayList<String>();
/* 454 */       blacklistedRobots.addAll(Arrays.asList(BuildCraftCore.mainConfigManager.get("general", "boards.blacklist")
/* 455 */             .getStringList()));
/* 456 */       reloadConfig(ConfigManager.RestartRequirement.WORLD);
/* 457 */     } else if (restartType == ConfigManager.RestartRequirement.WORLD) {
/* 458 */       reloadConfig(ConfigManager.RestartRequirement.NONE);
/*     */     }
/* 460 */     else if (BuildCraftCore.mainConfiguration.hasChanged()) {
/* 461 */       BuildCraftCore.mainConfiguration.save();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
/* 468 */     if ("BuildCraft|Core".equals(event.modID))
/* 469 */       reloadConfig(event.isWorldRunning ? ConfigManager.RestartRequirement.NONE : ConfigManager.RestartRequirement.WORLD); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\BuildCraftRobotics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */