/*     */ package buildcraft.api;
/*     */ 
/*     */ import buildcraft.api.core.BCDebugging;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.LoaderState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BCBlocks
/*     */ {
/*  19 */   private static final boolean DEBUG = BCDebugging.shouldDebugLog("api.blocks");
/*     */   
/*     */   public static final Block CORE_DECORATED;
/*     */   
/*     */   public static final Block CORE_ENGINE;
/*     */   
/*     */   public static final Block CORE_MARKER_VOLUME;
/*     */   
/*     */   public static final Block CORE_MARKER_PATH;
/*     */   
/*     */   public static final Block FACTORY_TANK;
/*     */   
/*     */   public static final Block FACTORY_PUMP;
/*     */   
/*     */   public static final Block FACTORY_CHUTE;
/*     */   
/*     */   public static final Block FACTORY_FLOOD_GATE;
/*     */   
/*     */   public static final Block FACTORY_MINING_WELL;
/*     */   
/*     */   public static final Block FACTORY_AUTOWORKBENCH_ITEM;
/*     */   
/*     */   public static final Block FACTORY_DISTILLER;
/*     */   
/*     */   public static final Block FACTORY_HEAT_EXCHANGE_START;
/*     */   
/*     */   public static final Block FACTORY_HEAT_EXCHANGE_MIDDLE;
/*     */   
/*     */   public static final Block FACTORY_HEAT_EXCHANGE_END;
/*     */   
/*     */   public static final Block SILICON_LASER;
/*     */   
/*     */   public static final Block SILICON_TABLE_ASSEMBLY;
/*     */   public static final Block SILICON_TABLE_INTEGRATION;
/*     */   public static final Block SILICON_TABLE_ADV_CRAFT;
/*     */   public static final Block SILICON_TABLE_CHARGING;
/*     */   public static final Block SILICON_TABLE_PROGRAMMING;
/*  56 */   private static final Set<String> SCANNED = DEBUG ? new HashSet<>() : null;
/*     */   
/*     */   static {
/*  59 */     if (!Loader.instance().hasReachedState(LoaderState.INITIALIZATION)) {
/*  60 */       throw new RuntimeException("Accessed BC blocks too early! You can only use them from init onwards!");
/*     */     }
/*  62 */     String core = "core";
/*  63 */     CORE_DECORATED = getRegisteredBlock(core, "decorated");
/*  64 */     CORE_ENGINE = getRegisteredBlock(core, "engine");
/*  65 */     CORE_MARKER_VOLUME = getRegisteredBlock(core, "marker_volume");
/*  66 */     CORE_MARKER_PATH = getRegisteredBlock(core, "marker_path");
/*     */     
/*  68 */     String energy = "energy";
/*     */     
/*  70 */     String factory = "factory";
/*  71 */     FACTORY_TANK = getRegisteredBlock(factory, "tank");
/*  72 */     FACTORY_PUMP = getRegisteredBlock(factory, "pump");
/*  73 */     FACTORY_CHUTE = getRegisteredBlock(factory, "chute");
/*  74 */     FACTORY_FLOOD_GATE = getRegisteredBlock(factory, "flood_gate");
/*  75 */     FACTORY_MINING_WELL = getRegisteredBlock(factory, "mining_well");
/*  76 */     FACTORY_AUTOWORKBENCH_ITEM = getRegisteredBlock(factory, "autoworkbench_item");
/*  77 */     FACTORY_DISTILLER = getRegisteredBlock(factory, "distiller");
/*  78 */     FACTORY_HEAT_EXCHANGE_START = getRegisteredBlock(factory, "heat_exchange_start");
/*  79 */     FACTORY_HEAT_EXCHANGE_MIDDLE = getRegisteredBlock(factory, "heat_exchange_middle");
/*  80 */     FACTORY_HEAT_EXCHANGE_END = getRegisteredBlock(factory, "heat_exchange_end");
/*     */     
/*  82 */     String silicon = "silicon";
/*  83 */     SILICON_LASER = getRegisteredBlock(silicon, "laser");
/*  84 */     SILICON_TABLE_ASSEMBLY = getRegisteredBlock(silicon, "assembly_table");
/*  85 */     SILICON_TABLE_INTEGRATION = getRegisteredBlock(silicon, "integration_table");
/*  86 */     SILICON_TABLE_ADV_CRAFT = getRegisteredBlock(silicon, "advanced_crafting_table");
/*  87 */     SILICON_TABLE_CHARGING = getRegisteredBlock(silicon, "charging_table");
/*  88 */     SILICON_TABLE_PROGRAMMING = getRegisteredBlock(silicon, "programming_table");
/*     */     
/*  90 */     if (DEBUG) {
/*  91 */       for (Block block : Block.field_149771_c) {
/*  92 */         ResourceLocation id = block.getRegistryName();
/*  93 */         if (id.func_110624_b().startsWith("buildcraft") && 
/*  94 */           !SCANNED.contains(id.toString())) {
/*  95 */           BCLog.logger.warn("[api.blocks] Found a block " + id.toString() + " that was not registered with the API! Is this a bug?");
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static Block getRegisteredBlock(String module, String regName) {
/* 103 */     String modid = "buildcraft" + module;
/* 104 */     Block block = (Block)Block.field_149771_c.func_82594_a(new ResourceLocation(modid, regName));
/*     */     
/* 106 */     if (block != Blocks.field_150350_a) {
/* 107 */       if (DEBUG) {
/* 108 */         BCLog.logger.info("[api.blocks] Found the block " + regName + " from the module " + module);
/* 109 */         SCANNED.add(modid + ":" + regName);
/*     */       } 
/* 111 */       return block;
/*     */     } 
/* 113 */     if (DEBUG) {
/* 114 */       if (Loader.isModLoaded(modid)) {
/* 115 */         BCLog.logger.info("[api.blocks] Did not find the block " + regName + " despite the appropriate mod being loaded (" + modid + ")");
/*     */       } else {
/* 117 */         BCLog.logger.info("[api.blocks] Did not find the block " + regName + " probably because the mod is not loaded (" + modid + ")");
/*     */       } 
/*     */     }
/* 120 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\BCBlocks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */