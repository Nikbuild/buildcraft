/*     */ package buildcraft;
/*     */ 
/*     */ import buildcraft.api.blueprints.BuilderAPI;
/*     */ import buildcraft.api.blueprints.SchematicTile;
/*     */ import buildcraft.core.BCRegistry;
/*     */ import buildcraft.core.CompatHooks;
/*     */ import buildcraft.core.DefaultProps;
/*     */ import buildcraft.core.InterModComms;
/*     */ import buildcraft.core.builders.schematics.SchematicFree;
/*     */ import buildcraft.core.builders.schematics.SchematicRotateMeta;
/*     */ import buildcraft.core.config.ConfigManager;
/*     */ import buildcraft.core.lib.network.ChannelHandler;
/*     */ import buildcraft.core.lib.network.PacketHandler;
/*     */ import buildcraft.factory.BlockAutoWorkbench;
/*     */ import buildcraft.factory.BlockFloodGate;
/*     */ import buildcraft.factory.BlockHopper;
/*     */ import buildcraft.factory.BlockMiningWell;
/*     */ import buildcraft.factory.BlockPlainPipe;
/*     */ import buildcraft.factory.BlockPump;
/*     */ import buildcraft.factory.BlockRefinery;
/*     */ import buildcraft.factory.BlockTank;
/*     */ import buildcraft.factory.FactoryGuiHandler;
/*     */ import buildcraft.factory.FactoryProxy;
/*     */ import buildcraft.factory.FactoryProxyClient;
/*     */ import buildcraft.factory.PumpDimensionList;
/*     */ import buildcraft.factory.TileAutoWorkbench;
/*     */ import buildcraft.factory.TileFloodGate;
/*     */ import buildcraft.factory.TileHopper;
/*     */ import buildcraft.factory.TileMiningWell;
/*     */ import buildcraft.factory.TilePump;
/*     */ import buildcraft.factory.TileRefinery;
/*     */ import buildcraft.factory.TileTank;
/*     */ import buildcraft.factory.schematics.SchematicAutoWorkbench;
/*     */ import buildcraft.factory.schematics.SchematicPump;
/*     */ import buildcraft.factory.schematics.SchematicRefinery;
/*     */ import buildcraft.factory.schematics.SchematicTileIgnoreState;
/*     */ import cpw.mods.fml.client.event.ConfigChangedEvent;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.Loader;
/*     */ import cpw.mods.fml.common.Mod;
/*     */ import cpw.mods.fml.common.Mod.EventHandler;
/*     */ import cpw.mods.fml.common.Mod.Instance;
/*     */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLInterModComms;
/*     */ import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
/*     */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.network.IGuiHandler;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraftforge.client.event.TextureStitchEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
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
/*     */ @Mod(name = "BuildCraft Factory", version = "7.1.26", useMetadata = false, modid = "BuildCraft|Factory", dependencies = "required-after:BuildCraft|Core@7.1.26")
/*     */ public class BuildCraftFactory
/*     */   extends BuildCraftMod
/*     */ {
/*     */   @Instance("BuildCraft|Factory")
/*     */   public static BuildCraftFactory instance;
/*     */   public static BlockMiningWell miningWellBlock;
/*     */   public static BlockAutoWorkbench autoWorkbenchBlock;
/*     */   public static BlockPlainPipe plainPipeBlock;
/*     */   public static BlockPump pumpBlock;
/*     */   public static BlockFloodGate floodGateBlock;
/*     */   public static BlockTank tankBlock;
/*     */   public static BlockRefinery refineryBlock;
/*     */   public static BlockHopper hopperBlock;
/*     */   public static Achievement aLotOfCraftingAchievement;
/*     */   public static Achievement straightDownAchievement;
/*     */   public static Achievement refineAndRedefineAchievement;
/*  90 */   public static int miningDepth = 256;
/*     */   public static boolean pumpsNeedRealPower = false;
/*     */   public static PumpDimensionList pumpDimensionList;
/*     */   
/*     */   @EventHandler
/*     */   public void load(FMLInitializationEvent evt) {
/*  96 */     NetworkRegistry.INSTANCE.registerGuiHandler(instance, (IGuiHandler)new FactoryGuiHandler());
/*     */     
/*  98 */     BCRegistry.INSTANCE.registerTileEntity(TileMiningWell.class, "MiningWell");
/*  99 */     BCRegistry.INSTANCE.registerTileEntity(TileAutoWorkbench.class, "AutoWorkbench");
/* 100 */     BCRegistry.INSTANCE.registerTileEntity(TilePump.class, "net.minecraft.src.buildcraft.factory.TilePump");
/* 101 */     BCRegistry.INSTANCE.registerTileEntity(TileFloodGate.class, "net.minecraft.src.buildcraft.factory.TileFloodGate");
/* 102 */     BCRegistry.INSTANCE.registerTileEntity(TileTank.class, "net.minecraft.src.buildcraft.factory.TileTank");
/* 103 */     BCRegistry.INSTANCE.registerTileEntity(TileRefinery.class, "net.minecraft.src.buildcraft.factory.Refinery");
/* 104 */     BCRegistry.INSTANCE.registerTileEntity(TileHopper.class, "net.minecraft.src.buildcraft.factory.TileHopper");
/*     */     
/* 106 */     FactoryProxy.proxy.initializeTileEntities();
/*     */     
/* 108 */     BuilderAPI.schematicRegistry.registerSchematicBlock((Block)refineryBlock, SchematicRefinery.class, new Object[0]);
/* 109 */     BuilderAPI.schematicRegistry.registerSchematicBlock((Block)tankBlock, SchematicTileIgnoreState.class, new Object[0]);
/* 110 */     BuilderAPI.schematicRegistry.registerSchematicBlock((Block)pumpBlock, SchematicPump.class, new Object[0]);
/* 111 */     BuilderAPI.schematicRegistry.registerSchematicBlock((Block)miningWellBlock, SchematicRotateMeta.class, new Object[] { { 2, 5, 3, 4 }, Boolean.valueOf(true) });
/* 112 */     BuilderAPI.schematicRegistry.registerSchematicBlock((Block)floodGateBlock, SchematicTileIgnoreState.class, new Object[0]);
/* 113 */     BuilderAPI.schematicRegistry.registerSchematicBlock((Block)autoWorkbenchBlock, SchematicAutoWorkbench.class, new Object[0]);
/* 114 */     BuilderAPI.schematicRegistry.registerSchematicBlock((Block)hopperBlock, SchematicTile.class, new Object[0]);
/* 115 */     BuilderAPI.schematicRegistry.registerSchematicBlock((Block)plainPipeBlock, SchematicFree.class, new Object[0]);
/*     */     
/* 117 */     aLotOfCraftingAchievement = BuildCraftCore.achievementManager.registerAchievement(new Achievement("achievement.aLotOfCrafting", "aLotOfCraftingAchievement", 1, 2, (Block)autoWorkbenchBlock, BuildCraftCore.woodenGearAchievement));
/* 118 */     straightDownAchievement = BuildCraftCore.achievementManager.registerAchievement(new Achievement("achievement.straightDown", "straightDownAchievement", 5, 2, (Block)miningWellBlock, BuildCraftCore.ironGearAchievement));
/* 119 */     refineAndRedefineAchievement = BuildCraftCore.achievementManager.registerAchievement(new Achievement("achievement.refineAndRedefine", "refineAndRedefineAchievement", 10, 0, (Block)refineryBlock, BuildCraftCore.diamondGearAchievement));
/*     */     
/* 121 */     if (BuildCraftCore.loadDefaultRecipes) {
/* 122 */       loadRecipes();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void initialize(FMLPreInitializationEvent evt) {
/* 128 */     this
/* 129 */       .channels = NetworkRegistry.INSTANCE.newChannel("BC-FACTORY", new ChannelHandler[] { (ChannelHandler)new ChannelHandler(), (ChannelHandler)new PacketHandler() });
/*     */     
/* 131 */     String plc = "Allows admins to whitelist or blacklist pumping of specific fluids in specific dimensions.\nEg. \"-/-1/Lava\" will disable lava in the nether. \"-/*/Lava\" will disable lava in any dimension. \"+/0/*\" will enable any fluid in the overworld.\nEntries are comma seperated, banned fluids have precedence over allowed ones.Default is \"+/*/*,+/-1/Lava\" - the second redundant entry (\"+/-1/lava\") is there to show the format.";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     BuildCraftCore.mainConfigManager.register("general.miningDepth", Integer.valueOf(256), "Should the mining well only be usable once after placing?", ConfigManager.RestartRequirement.NONE);
/*     */     
/* 138 */     BuildCraftCore.mainConfigManager.get("general.miningDepth").setMinValue(2).setMaxValue(256);
/* 139 */     BuildCraftCore.mainConfigManager.register("general.pumpDimensionControl", DefaultProps.PUMP_DIMENSION_LIST, plc, ConfigManager.RestartRequirement.NONE);
/* 140 */     BuildCraftCore.mainConfigManager.register("general.pumpsNeedRealPower", Boolean.valueOf(false), "Do pumps need real (non-redstone) power?", ConfigManager.RestartRequirement.WORLD);
/*     */     
/* 142 */     reloadConfig(ConfigManager.RestartRequirement.GAME);
/*     */     
/* 144 */     miningWellBlock = (BlockMiningWell)CompatHooks.INSTANCE.getBlock(BlockMiningWell.class);
/* 145 */     if (BCRegistry.INSTANCE.registerBlock(miningWellBlock.func_149663_c("miningWellBlock"), false)) {
/* 146 */       plainPipeBlock = new BlockPlainPipe();
/* 147 */       BCRegistry.INSTANCE.registerBlock(plainPipeBlock.func_149663_c("plainPipeBlock"), true);
/*     */     } 
/*     */     
/* 150 */     autoWorkbenchBlock = (BlockAutoWorkbench)CompatHooks.INSTANCE.getBlock(BlockAutoWorkbench.class);
/* 151 */     BCRegistry.INSTANCE.registerBlock(autoWorkbenchBlock.func_149663_c("autoWorkbenchBlock"), false);
/*     */     
/* 153 */     tankBlock = (BlockTank)CompatHooks.INSTANCE.getBlock(BlockTank.class);
/* 154 */     BCRegistry.INSTANCE.registerBlock(tankBlock.func_149663_c("tankBlock"), false);
/*     */     
/* 156 */     pumpBlock = (BlockPump)CompatHooks.INSTANCE.getBlock(BlockPump.class);
/* 157 */     BCRegistry.INSTANCE.registerBlock(pumpBlock.func_149663_c("pumpBlock"), false);
/*     */     
/* 159 */     floodGateBlock = (BlockFloodGate)CompatHooks.INSTANCE.getBlock(BlockFloodGate.class);
/* 160 */     BCRegistry.INSTANCE.registerBlock(floodGateBlock.func_149663_c("floodGateBlock"), false);
/*     */     
/* 162 */     refineryBlock = (BlockRefinery)CompatHooks.INSTANCE.getBlock(BlockRefinery.class);
/* 163 */     BCRegistry.INSTANCE.registerBlock(refineryBlock.func_149663_c("refineryBlock"), false);
/*     */     
/* 165 */     hopperBlock = (BlockHopper)CompatHooks.INSTANCE.getBlock(BlockHopper.class);
/* 166 */     BCRegistry.INSTANCE.registerBlock(hopperBlock.func_149663_c("blockHopper"), false);
/*     */     
/* 168 */     FactoryProxy.proxy.initializeEntityRenders();
/*     */     
/* 170 */     FMLCommonHandler.instance().bus().register(this);
/* 171 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   public static void loadRecipes() {
/* 175 */     if (miningWellBlock != null) {
/* 176 */       BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)miningWellBlock, 1), new Object[] { "ipi", "igi", "iPi", 
/*     */ 
/*     */ 
/*     */             
/* 180 */             Character.valueOf('p'), "dustRedstone", 
/* 181 */             Character.valueOf('i'), "ingotIron", 
/* 182 */             Character.valueOf('g'), "gearIron", 
/* 183 */             Character.valueOf('P'), Items.field_151035_b });
/*     */     }
/*     */     
/* 186 */     if (pumpBlock != null) {
/* 187 */       BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)pumpBlock), new Object[] { "ipi", "igi", "TBT", 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 192 */             Character.valueOf('p'), "dustRedstone", 
/* 193 */             Character.valueOf('i'), "ingotIron", 
/* 194 */             Character.valueOf('T'), tankBlock, 
/* 195 */             Character.valueOf('g'), "gearIron", 
/* 196 */             Character.valueOf('B'), Items.field_151133_ar });
/*     */     }
/*     */     
/* 199 */     if (autoWorkbenchBlock != null) {
/* 200 */       BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)autoWorkbenchBlock), new Object[] { "gwg", 
/*     */             
/* 202 */             Character.valueOf('w'), "craftingTableWood", 
/* 203 */             Character.valueOf('g'), "gearStone" });
/*     */       
/* 205 */       BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)autoWorkbenchBlock), new Object[] { "g", "w", "g", 
/*     */ 
/*     */ 
/*     */             
/* 209 */             Character.valueOf('w'), "craftingTableWood", 
/* 210 */             Character.valueOf('g'), "gearStone" });
/*     */     } 
/*     */ 
/*     */     
/* 214 */     if (tankBlock != null) {
/* 215 */       BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)tankBlock), new Object[] { "ggg", "g g", "ggg", 
/*     */ 
/*     */ 
/*     */             
/* 219 */             Character.valueOf('g'), "blockGlass" });
/*     */     }
/*     */     
/* 222 */     if (refineryBlock != null) {
/* 223 */       BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)refineryBlock), new Object[] { "RTR", "TGT", 
/*     */ 
/*     */             
/* 226 */             Character.valueOf('T'), (tankBlock != null) ? tankBlock : "blockGlass", 
/* 227 */             Character.valueOf('G'), "gearDiamond", 
/* 228 */             Character.valueOf('R'), Blocks.field_150429_aA });
/*     */     }
/*     */     
/* 231 */     if (hopperBlock != null) {
/* 232 */       BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)hopperBlock), new Object[] { "ICI", " G ", 
/*     */ 
/*     */             
/* 235 */             Character.valueOf('I'), "ingotIron", 
/* 236 */             Character.valueOf('C'), "chestWood", 
/* 237 */             Character.valueOf('G'), "gearStone" });
/*     */       
/* 239 */       BCRegistry.INSTANCE.addShapelessRecipe(new ItemStack((Block)hopperBlock), new Object[] { Blocks.field_150438_bZ, "gearStone" });
/*     */     } 
/*     */     
/* 242 */     if (floodGateBlock != null) {
/* 243 */       BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)floodGateBlock), new Object[] { "IGI", "FTF", "IFI", 
/*     */ 
/*     */ 
/*     */             
/* 247 */             Character.valueOf('I'), "ingotIron", 
/* 248 */             Character.valueOf('T'), (tankBlock != null) ? tankBlock : "blockGlass", 
/* 249 */             Character.valueOf('G'), "gearIron", 
/* 250 */             Character.valueOf('F'), new ItemStack(Blocks.field_150411_aY) });
/*     */     }
/*     */   }
/*     */   
/*     */   public void reloadConfig(ConfigManager.RestartRequirement restartType) {
/* 255 */     if (restartType == ConfigManager.RestartRequirement.GAME) {
/* 256 */       reloadConfig(ConfigManager.RestartRequirement.WORLD);
/* 257 */     } else if (restartType == ConfigManager.RestartRequirement.WORLD) {
/* 258 */       reloadConfig(ConfigManager.RestartRequirement.NONE);
/*     */     } else {
/* 260 */       miningDepth = BuildCraftCore.mainConfigManager.get("general.miningDepth").getInt();
/* 261 */       pumpsNeedRealPower = BuildCraftCore.mainConfigManager.get("general.pumpsNeedRealPower").getBoolean();
/* 262 */       pumpDimensionList = new PumpDimensionList(BuildCraftCore.mainConfigManager.get("general.pumpDimensionControl").getString());
/*     */       
/* 264 */       if (BuildCraftCore.mainConfiguration.hasChanged()) {
/* 265 */         BuildCraftCore.mainConfiguration.save();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
/* 272 */     if ("BuildCraft|Core".equals(event.modID)) {
/* 273 */       reloadConfig(event.isWorldRunning ? ConfigManager.RestartRequirement.NONE : ConfigManager.RestartRequirement.WORLD);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void processIMCRequests(FMLInterModComms.IMCEvent event) {
/* 279 */     InterModComms.processIMC(event);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void loadTextures(TextureStitchEvent.Pre evt) {
/* 285 */     if (evt.map.func_130086_a() == 0) {
/* 286 */       TextureMap terrainTextures = evt.map;
/* 287 */       FactoryProxyClient.pumpTexture = terrainTextures.func_94245_a("buildcraftfactory:pumpBlock/tube");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void whiteListAppliedEnergetics(FMLInitializationEvent event) {
/* 295 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileAutoWorkbench.class
/* 296 */         .getCanonicalName());
/*     */ 
/*     */     
/* 299 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileFloodGate.class
/* 300 */         .getCanonicalName());
/* 301 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileTank.class
/* 302 */         .getCanonicalName());
/* 303 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileRefinery.class
/* 304 */         .getCanonicalName());
/* 305 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileHopper.class
/* 306 */         .getCanonicalName());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void remap(FMLMissingMappingsEvent event) {
/* 311 */     for (FMLMissingMappingsEvent.MissingMapping mapping : event.get()) {
/* 312 */       if (mapping.name.equals("BuildCraft|Factory:machineBlock")) {
/* 313 */         if (Loader.isModLoaded("BuildCraft|Builders")) {
/* 314 */           if (mapping.type == GameRegistry.Type.BLOCK) {
/* 315 */             mapping.remap(Block.func_149684_b("BuildCraft|Builders:machineBlock")); continue;
/* 316 */           }  if (mapping.type == GameRegistry.Type.ITEM)
/* 317 */             mapping.remap(Item.func_150898_a(Block.func_149684_b("BuildCraft|Builders:machineBlock"))); 
/*     */           continue;
/*     */         } 
/* 320 */         mapping.warn(); continue;
/*     */       } 
/* 322 */       if (mapping.name.equals("BuildCraft|Factory:frameBlock")) {
/* 323 */         if (Loader.isModLoaded("BuildCraft|Builders")) {
/* 324 */           if (mapping.type == GameRegistry.Type.BLOCK) {
/* 325 */             mapping.remap(Block.func_149684_b("BuildCraft|Builders:frameBlock")); continue;
/* 326 */           }  if (mapping.type == GameRegistry.Type.ITEM)
/* 327 */             mapping.remap(Item.func_150898_a(Block.func_149684_b("BuildCraft|Builders:frameBlock"))); 
/*     */           continue;
/*     */         } 
/* 330 */         mapping.ignore();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\BuildCraftFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */