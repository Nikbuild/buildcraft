/*     */ package buildcraft;
/*     */ 
/*     */ import buildcraft.api.blueprints.BuilderAPI;
/*     */ import buildcraft.api.recipes.BuildcraftRecipeRegistry;
/*     */ import buildcraft.core.BCRegistry;
/*     */ import buildcraft.core.CompatHooks;
/*     */ import buildcraft.core.InterModComms;
/*     */ import buildcraft.core.builders.schematics.SchematicRotateMeta;
/*     */ import buildcraft.core.config.ConfigManager;
/*     */ import buildcraft.core.lib.items.ItemBuildCraft;
/*     */ import buildcraft.core.lib.network.ChannelHandler;
/*     */ import buildcraft.core.lib.network.PacketHandler;
/*     */ import buildcraft.silicon.BlockLaser;
/*     */ import buildcraft.silicon.BlockLaserTable;
/*     */ import buildcraft.silicon.BlockPackager;
/*     */ import buildcraft.silicon.EntityPackage;
/*     */ import buildcraft.silicon.ItemLaserTable;
/*     */ import buildcraft.silicon.ItemPackage;
/*     */ import buildcraft.silicon.ItemRedstoneChipset;
/*     */ import buildcraft.silicon.SiliconGuiHandler;
/*     */ import buildcraft.silicon.SiliconProxy;
/*     */ import buildcraft.silicon.TileAdvancedCraftingTable;
/*     */ import buildcraft.silicon.TileAssemblyTable;
/*     */ import buildcraft.silicon.TileChargingTable;
/*     */ import buildcraft.silicon.TileIntegrationTable;
/*     */ import buildcraft.silicon.TileLaser;
/*     */ import buildcraft.silicon.TilePackager;
/*     */ import buildcraft.silicon.TileProgrammingTable;
/*     */ import buildcraft.silicon.TileStampingTable;
/*     */ import buildcraft.transport.stripes.StripesHandlerDispenser;
/*     */ import cpw.mods.fml.common.Loader;
/*     */ import cpw.mods.fml.common.Mod;
/*     */ import cpw.mods.fml.common.Mod.EventHandler;
/*     */ import cpw.mods.fml.common.Mod.Instance;
/*     */ import cpw.mods.fml.common.Optional.Method;
/*     */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLInterModComms;
/*     */ import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
/*     */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*     */ import cpw.mods.fml.common.network.IGuiHandler;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import cpw.mods.fml.common.registry.EntityRegistry;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraftforge.oredict.OreDictionary;
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
/*     */ @Mod(name = "BuildCraft Silicon", version = "7.1.26", useMetadata = false, modid = "BuildCraft|Silicon", dependencies = "required-after:BuildCraft|Core@7.1.26")
/*     */ public class BuildCraftSilicon
/*     */   extends BuildCraftMod
/*     */ {
/*     */   @Instance("BuildCraft|Silicon")
/*     */   public static BuildCraftSilicon instance;
/*     */   public static ItemRedstoneChipset redstoneChipset;
/*     */   public static ItemPackage packageItem;
/*     */   public static BlockLaser laserBlock;
/*     */   public static BlockLaserTable assemblyTableBlock;
/*     */   public static BlockPackager packagerBlock;
/*     */   public static Item redstoneCrystal;
/*     */   public static Achievement timeForSomeLogicAchievement;
/*     */   public static Achievement tinglyLaserAchievement;
/*  79 */   public static float chipsetCostMultiplier = 1.0F;
/*     */   
/*     */   @EventHandler
/*     */   public void preInit(FMLPreInitializationEvent evt) {
/*  83 */     BuildCraftCore.mainConfigManager.register("power.chipsetCostMultiplier", Double.valueOf(1.0D), "The cost multiplier for Chipsets", ConfigManager.RestartRequirement.GAME);
/*  84 */     BuildCraftCore.mainConfiguration.save();
/*  85 */     chipsetCostMultiplier = (float)BuildCraftCore.mainConfigManager.get("power.chipsetCostMultiplier").getDouble();
/*     */     
/*  87 */     if (BuildCraftCore.mainConfiguration.hasChanged()) {
/*  88 */       BuildCraftCore.mainConfiguration.save();
/*     */     }
/*     */     
/*  91 */     laserBlock = (BlockLaser)CompatHooks.INSTANCE.getBlock(BlockLaser.class);
/*  92 */     laserBlock.func_149663_c("laserBlock");
/*  93 */     BCRegistry.INSTANCE.registerBlock((Block)laserBlock, false);
/*     */     
/*  95 */     assemblyTableBlock = (BlockLaserTable)CompatHooks.INSTANCE.getBlock(BlockLaserTable.class);
/*  96 */     assemblyTableBlock.func_149663_c("laserTableBlock");
/*  97 */     BCRegistry.INSTANCE.registerBlock((Block)assemblyTableBlock, ItemLaserTable.class, false);
/*     */     
/*  99 */     packagerBlock = (BlockPackager)CompatHooks.INSTANCE.getBlock(BlockPackager.class);
/* 100 */     packagerBlock.func_149663_c("packagerBlock");
/* 101 */     BCRegistry.INSTANCE.registerBlock((Block)packagerBlock, false);
/*     */     
/* 103 */     redstoneChipset = new ItemRedstoneChipset();
/* 104 */     redstoneChipset.func_77655_b("redstoneChipset");
/* 105 */     BCRegistry.INSTANCE.registerItem((Item)redstoneChipset, false);
/* 106 */     redstoneChipset.registerItemStacks();
/*     */     
/* 108 */     packageItem = new ItemPackage();
/* 109 */     packageItem.func_77655_b("package");
/* 110 */     BCRegistry.INSTANCE.registerItem((Item)packageItem, false);
/*     */     
/* 112 */     redstoneCrystal = (new ItemBuildCraft()).func_77655_b("redstoneCrystal");
/* 113 */     if (BCRegistry.INSTANCE.registerItem(redstoneCrystal, false)) {
/* 114 */       OreDictionary.registerOre("redstoneCrystal", new ItemStack(redstoneCrystal));
/* 115 */       OreDictionary.registerOre("crystalRedstone", new ItemStack(redstoneCrystal));
/*     */     } 
/*     */     
/* 118 */     EntityRegistry.registerModEntity(EntityPackage.class, "bcPackageThrowable", 11, instance, 48, 10, true);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void init(FMLInitializationEvent evt) {
/* 123 */     this
/*     */       
/* 125 */       .channels = NetworkRegistry.INSTANCE.newChannel("BC-SILICON", new ChannelHandler[] { (ChannelHandler)new ChannelHandler(), (ChannelHandler)new PacketHandler() });
/*     */     
/* 127 */     NetworkRegistry.INSTANCE.registerGuiHandler(instance, (IGuiHandler)new SiliconGuiHandler());
/* 128 */     BCRegistry.INSTANCE.registerTileEntity(TileLaser.class, "net.minecraft.src.buildcraft.factory.TileLaser");
/* 129 */     BCRegistry.INSTANCE.registerTileEntity(TileAssemblyTable.class, "net.minecraft.src.buildcraft.factory.TileAssemblyTable");
/*     */     
/* 131 */     BCRegistry.INSTANCE.registerTileEntity(TileAdvancedCraftingTable.class, "net.minecraft.src.buildcraft.factory.TileAssemblyAdvancedWorkbench");
/*     */     
/* 133 */     BCRegistry.INSTANCE.registerTileEntity(TileIntegrationTable.class, "net.minecraft.src.buildcraft.factory.TileIntegrationTable");
/*     */     
/* 135 */     BCRegistry.INSTANCE.registerTileEntity(TileChargingTable.class, "net.minecraft.src.buildcraft.factory.TileChargingTable");
/*     */     
/* 137 */     BCRegistry.INSTANCE.registerTileEntity(TileProgrammingTable.class, "net.minecraft.src.buildcraft.factory.TileProgrammingTable");
/*     */     
/* 139 */     BCRegistry.INSTANCE.registerTileEntity(TilePackager.class, "buildcraft.TilePackager");
/* 140 */     BCRegistry.INSTANCE.registerTileEntity(TileStampingTable.class, "buildcraft.TileStampingTable");
/*     */     
/* 142 */     BuilderAPI.schematicRegistry.registerSchematicBlock((Block)laserBlock, SchematicRotateMeta.class, new Object[] { { 2, 5, 3, 4 }, Boolean.valueOf(true) });
/*     */     
/* 144 */     timeForSomeLogicAchievement = BuildCraftCore.achievementManager.registerAchievement(new Achievement("achievement.timeForSomeLogic", "timeForSomeLogicAchievement", 9, -2, (Block)assemblyTableBlock, BuildCraftCore.diamondGearAchievement));
/* 145 */     tinglyLaserAchievement = BuildCraftCore.achievementManager.registerAchievement(new Achievement("achievement.tinglyLaser", "tinglyLaserAchievement", 11, -2, (Block)laserBlock, timeForSomeLogicAchievement));
/*     */     
/* 147 */     if (BuildCraftCore.loadDefaultRecipes) {
/* 148 */       loadRecipes();
/*     */     }
/*     */     
/* 151 */     BlockDispenser.field_149943_a.func_82595_a(packageItem, new ItemPackage.DispenseBehaviour());
/* 152 */     if (Loader.isModLoaded("BuildCraft|Transport")) {
/* 153 */       initTransport();
/*     */     }
/*     */     
/* 156 */     SiliconProxy.proxy.registerRenderers();
/*     */   }
/*     */   
/*     */   @Method(modid = "BuildCraft|Transport")
/*     */   private void initTransport() {
/* 161 */     StripesHandlerDispenser.items.add(packageItem);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadRecipes() {
/* 167 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)laserBlock), new Object[] { "ORR", "DDR", "ORR", 
/*     */ 
/*     */ 
/*     */           
/* 171 */           Character.valueOf('O'), Blocks.field_150343_Z, 
/* 172 */           Character.valueOf('R'), "dustRedstone", 
/* 173 */           Character.valueOf('D'), "gemDiamond" });
/*     */     
/* 175 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)laserBlock), new Object[] { "RRO", "RDD", "RRO", 
/*     */ 
/*     */ 
/*     */           
/* 179 */           Character.valueOf('O'), Blocks.field_150343_Z, 
/* 180 */           Character.valueOf('R'), "dustRedstone", 
/* 181 */           Character.valueOf('D'), "gemDiamond" });
/*     */     
/* 183 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)laserBlock), new Object[] { "RRR", "RDR", "ODO", 
/*     */ 
/*     */ 
/*     */           
/* 187 */           Character.valueOf('O'), Blocks.field_150343_Z, 
/* 188 */           Character.valueOf('R'), "dustRedstone", 
/* 189 */           Character.valueOf('D'), "gemDiamond" });
/*     */     
/* 191 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)laserBlock), new Object[] { "ODO", "RDR", "RRR", 
/*     */ 
/*     */ 
/*     */           
/* 195 */           Character.valueOf('O'), Blocks.field_150343_Z, 
/* 196 */           Character.valueOf('R'), "dustRedstone", 
/* 197 */           Character.valueOf('D'), "gemDiamond" });
/*     */     
/* 199 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)assemblyTableBlock, 1, 0), new Object[] { "ODO", "ORO", "OGO", 
/*     */ 
/*     */ 
/*     */           
/* 203 */           Character.valueOf('O'), Blocks.field_150343_Z, 
/* 204 */           Character.valueOf('R'), "dustRedstone", 
/* 205 */           Character.valueOf('D'), "gemDiamond", 
/* 206 */           Character.valueOf('G'), "gearDiamond" });
/*     */     
/* 208 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)assemblyTableBlock, 1, 1), new Object[] { "OWO", "OCO", "ORO", 
/*     */ 
/*     */ 
/*     */           
/* 212 */           Character.valueOf('O'), Blocks.field_150343_Z, 
/* 213 */           Character.valueOf('W'), Blocks.field_150462_ai, 
/* 214 */           Character.valueOf('C'), Blocks.field_150486_ae, 
/* 215 */           Character.valueOf('R'), new ItemStack((Item)redstoneChipset, 1, 0) });
/*     */     
/* 217 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)assemblyTableBlock, 1, 2), new Object[] { "OIO", "OCO", "OGO", 
/*     */ 
/*     */ 
/*     */           
/* 221 */           Character.valueOf('O'), Blocks.field_150343_Z, 
/* 222 */           Character.valueOf('I'), "ingotGold", 
/* 223 */           Character.valueOf('C'), new ItemStack((Item)redstoneChipset, 1, 0), 
/* 224 */           Character.valueOf('G'), "gearDiamond" });
/*     */     
/* 226 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)assemblyTableBlock, 1, 3), new Object[] { "OIO", "OCO", "OGO", 
/*     */ 
/*     */ 
/*     */           
/* 230 */           Character.valueOf('O'), Blocks.field_150343_Z, 
/* 231 */           Character.valueOf('I'), "dustRedstone", 
/* 232 */           Character.valueOf('C'), new ItemStack((Item)redstoneChipset, 1, 0), 
/* 233 */           Character.valueOf('G'), "gearGold" });
/*     */     
/* 235 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)assemblyTableBlock, 1, 4), new Object[] { "OCO", "ORO", "OGO", 
/*     */ 
/*     */ 
/*     */           
/* 239 */           Character.valueOf('O'), Blocks.field_150343_Z, 
/* 240 */           Character.valueOf('R'), new ItemStack((Item)redstoneChipset, 1, 0), 
/* 241 */           Character.valueOf('C'), "gemEmerald", 
/* 242 */           Character.valueOf('G'), "gearDiamond" });
/*     */     
/* 244 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)assemblyTableBlock, 1, 5), new Object[] { "OWO", "ORO", "OGO", 
/*     */ 
/*     */ 
/*     */           
/* 248 */           Character.valueOf('O'), Blocks.field_150343_Z, 
/* 249 */           Character.valueOf('W'), "craftingTableWood", 
/* 250 */           Character.valueOf('G'), "gearGold", 
/* 251 */           Character.valueOf('R'), new ItemStack((Item)redstoneChipset, 1, 0) });
/*     */     
/* 253 */     BCRegistry.INSTANCE.addCraftingRecipe(new ItemStack((Block)packagerBlock, 1, 0), new Object[] { " I ", "ICI", " P ", 
/*     */ 
/*     */ 
/*     */           
/* 257 */           Character.valueOf('I'), "ingotIron", 
/* 258 */           Character.valueOf('C'), "craftingTableWood", 
/* 259 */           Character.valueOf('P'), Blocks.field_150331_J });
/*     */ 
/*     */     
/* 262 */     BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:redstoneChipset", Math.round(100000.0F * chipsetCostMultiplier), ItemRedstoneChipset.Chipset.RED.getStack(), new Object[] { "dustRedstone" });
/*     */     
/* 264 */     BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:ironChipset", Math.round(200000.0F * chipsetCostMultiplier), ItemRedstoneChipset.Chipset.IRON.getStack(), new Object[] { "dustRedstone", "ingotIron" });
/*     */     
/* 266 */     BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:goldChipset", Math.round(400000.0F * chipsetCostMultiplier), ItemRedstoneChipset.Chipset.GOLD.getStack(), new Object[] { "dustRedstone", "ingotGold" });
/*     */     
/* 268 */     BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:diamondChipset", Math.round(800000.0F * chipsetCostMultiplier), ItemRedstoneChipset.Chipset.DIAMOND
/* 269 */         .getStack(), new Object[] { "dustRedstone", "gemDiamond" });
/* 270 */     BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:emeraldChipset", Math.round(1200000.0F * chipsetCostMultiplier), ItemRedstoneChipset.Chipset.EMERALD
/* 271 */         .getStack(), new Object[] { "dustRedstone", "gemEmerald" });
/* 272 */     BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:pulsatingChipset", Math.round(400000.0F * chipsetCostMultiplier), ItemRedstoneChipset.Chipset.PULSATING
/* 273 */         .getStack(2), new Object[] { "dustRedstone", Items.field_151079_bi });
/* 274 */     BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:quartzChipset", Math.round(600000.0F * chipsetCostMultiplier), ItemRedstoneChipset.Chipset.QUARTZ.getStack(), new Object[] { "dustRedstone", "gemQuartz" });
/*     */     
/* 276 */     BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:compChipset", Math.round(600000.0F * chipsetCostMultiplier), ItemRedstoneChipset.Chipset.COMP.getStack(), new Object[] { "dustRedstone", Items.field_151132_bS });
/*     */ 
/*     */ 
/*     */     
/* 280 */     BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:redstoneCrystal", 10000000, new ItemStack(redstoneCrystal), new Object[] { new ItemStack(Blocks.field_150451_bX) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void processIMCRequests(FMLInterModComms.IMCEvent event) {
/* 287 */     InterModComms.processIMC(event);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void whiteListAppliedEnergetics(FMLInitializationEvent event) {
/* 292 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileLaser.class
/* 293 */         .getCanonicalName());
/* 294 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileAssemblyTable.class
/* 295 */         .getCanonicalName());
/* 296 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileAdvancedCraftingTable.class
/* 297 */         .getCanonicalName());
/* 298 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileIntegrationTable.class
/* 299 */         .getCanonicalName());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void remap(FMLMissingMappingsEvent event) {
/* 304 */     for (FMLMissingMappingsEvent.MissingMapping mapping : event.get()) {
/* 305 */       if (mapping.name.equals("BuildCraft|Silicon:null")) {
/* 306 */         if (mapping.type == GameRegistry.Type.ITEM) {
/* 307 */           mapping.remap(Item.func_150898_a((Block)assemblyTableBlock));
/*     */         } else {
/* 309 */           mapping.remap((Block)assemblyTableBlock);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 314 */       if (mapping.type == GameRegistry.Type.ITEM) {
/* 315 */         if (mapping.name.equals("BuildCraft|Silicon:robot")) {
/* 316 */           mapping.remap((Item)Item.field_150901_e.func_82594_a("BuildCraft|Robotics:robot")); continue;
/* 317 */         }  if (mapping.name.equals("BuildCraft|Silicon:redstone_board")) {
/* 318 */           mapping.remap((Item)Item.field_150901_e.func_82594_a("BuildCraft|Robotics:redstone_board")); continue;
/* 319 */         }  if (mapping.name.equals("BuildCraft|Silicon:requester")) {
/* 320 */           mapping.remap((Item)Item.field_150901_e.func_82594_a("BuildCraft|Robotics:requester")); continue;
/* 321 */         }  if (mapping.name.equals("BuildCraft|Silicon:zonePlan"))
/* 322 */           mapping.remap((Item)Item.field_150901_e.func_82594_a("BuildCraft|Robotics:zonePlan"));  continue;
/*     */       } 
/* 324 */       if (mapping.type == GameRegistry.Type.BLOCK) {
/* 325 */         if (mapping.name.equals("BuildCraft|Silicon:requester")) {
/* 326 */           mapping.remap(Block.func_149684_b("BuildCraft|Robotics:requester")); continue;
/* 327 */         }  if (mapping.name.equals("BuildCraft|Silicon:zonePlan"))
/* 328 */           mapping.remap(Block.func_149684_b("BuildCraft|Robotics:zonePlan")); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\BuildCraftSilicon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */