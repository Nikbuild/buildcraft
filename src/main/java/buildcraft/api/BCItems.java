/*     */ package buildcraft.api;
/*     */ 
/*     */ import buildcraft.api.core.BCDebugging;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.LoaderState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BCItems
/*     */ {
/*  19 */   private static final boolean DEBUG = BCDebugging.shouldDebugLog("api.items");
/*     */   
/*     */   public static final Item LIB_GUIDE;
/*     */   
/*     */   public static final Item LIB_DEBUGGER;
/*     */   
/*     */   public static final Item CORE_WRENCH;
/*     */   
/*     */   public static final Item CORE_DIAMOND_SHARD;
/*     */   
/*     */   public static final Item CORE_LIST;
/*     */   
/*     */   public static final Item CORE_MAP_LOCATION;
/*     */   
/*     */   public static final Item CORE_PAINTBRUSH;
/*     */   
/*     */   public static final Item CORE_GEAR_WOOD;
/*     */   
/*     */   public static final Item CORE_GEAR_STONE;
/*     */   
/*     */   public static final Item CORE_GEAR_IRON;
/*     */   
/*     */   public static final Item CORE_GEAR_GOLD;
/*     */   
/*     */   public static final Item CORE_GEAR_DIAMOND;
/*     */   
/*     */   public static final Item CORE_MARKER_CONNECTOR;
/*     */   
/*     */   public static final Item CORE_GOGGLES;
/*     */   
/*     */   public static final Item BUILDERS_SINGLE_SCHEMATIC;
/*     */   
/*     */   public static final Item BUILDERS_SNAPSHOT;
/*     */   
/*     */   public static final Item ENERGY_GLOB_OF_OIL;
/*     */   
/*     */   public static final Item FACTORY_PLASTIC_SHEET;
/*     */   
/*     */   public static final Item ROBOTICS_REDSTONE_BOARD;
/*     */   
/*     */   public static final Item ROBOTICS_ROBOT;
/*     */   
/*     */   public static final Item ROBOTICS_ROBOT_GOGGLES;
/*     */   
/*     */   public static final Item ROBOTICS_PLUGGABLE_ROBOT_STATION;
/*     */   public static final Item SILICON_REDSTONE_CHIPSET;
/*     */   public static final Item TRANSPORT_WATERPROOF;
/*     */   public static final Item TRANSPORT_GATE_COPIER;
/*     */   public static final Item TRANSPORT_PLUGGABLE_GATE;
/*     */   public static final Item TRANSPORT_PLUGGABLE_WIRE;
/*     */   public static final Item TRANSPORT_PLUGGABLE_BLOCKER;
/*     */   public static final Item TRANSPORT_PLUGGABLE_LENS;
/*     */   public static final Item TRANSPORT_PLUGGABLE_POWER_ADAPTOR;
/*     */   public static final Item TRANSPORT_PLUGGABLE_FACADE;
/*     */   public static final Item TRANSPORT_PIPE_WOOD_ITEM;
/*     */   public static final Item TRANSPORT_PIPE_WOOD_FLUID;
/*     */   public static final Item TRANSPORT_PIPE_WOOD_POWER;
/*     */   public static final Item TRANSPORT_PIPE_DIAMOND_ITEM;
/*  77 */   private static final Set<String> SCANNED = DEBUG ? new HashSet<>() : null;
/*     */   
/*     */   static {
/*  80 */     if (!Loader.instance().hasReachedState(LoaderState.INITIALIZATION)) {
/*  81 */       throw new RuntimeException("Accessed BC items too early! You can only use them from init onwards!");
/*     */     }
/*  83 */     String lib = "lib";
/*  84 */     LIB_GUIDE = getRegisteredItem("lib", "guide");
/*  85 */     LIB_DEBUGGER = getRegisteredItem("lib", "debugger");
/*     */     
/*  87 */     String core = "core";
/*  88 */     CORE_WRENCH = getRegisteredItem("core", "wrench");
/*  89 */     CORE_DIAMOND_SHARD = getRegisteredItem("core", "diamond_shard");
/*  90 */     CORE_LIST = getRegisteredItem("core", "list");
/*  91 */     CORE_MAP_LOCATION = getRegisteredItem("core", "map_location");
/*  92 */     CORE_PAINTBRUSH = getRegisteredItem("core", "paintbrush");
/*  93 */     CORE_GEAR_WOOD = getRegisteredItem("core", "gear_wood");
/*  94 */     CORE_GEAR_STONE = getRegisteredItem("core", "gear_stone");
/*  95 */     CORE_GEAR_IRON = getRegisteredItem("core", "gear_iron");
/*  96 */     CORE_GEAR_GOLD = getRegisteredItem("core", "gear_gold");
/*  97 */     CORE_GEAR_DIAMOND = getRegisteredItem("core", "gear_diamond");
/*  98 */     CORE_MARKER_CONNECTOR = getRegisteredItem("core", "marker_connector");
/*  99 */     CORE_GOGGLES = getRegisteredItem("core", "goggles");
/*     */     
/* 101 */     String builders = "builders";
/* 102 */     BUILDERS_SINGLE_SCHEMATIC = getRegisteredItem("builders", "single_schematic");
/* 103 */     BUILDERS_SNAPSHOT = getRegisteredItem("builders", "snapshot");
/*     */     
/* 105 */     String energy = "energy";
/* 106 */     ENERGY_GLOB_OF_OIL = getRegisteredItem("energy", "glob_oil");
/*     */     
/* 108 */     String factory = "factory";
/* 109 */     FACTORY_PLASTIC_SHEET = getRegisteredItem("factory", "plastic_sheet");
/*     */     
/* 111 */     String robotics = "robotics";
/* 112 */     ROBOTICS_REDSTONE_BOARD = getRegisteredItem("robotics", "redstone_board");
/* 113 */     ROBOTICS_ROBOT = getRegisteredItem("robotics", "robot");
/* 114 */     ROBOTICS_PLUGGABLE_ROBOT_STATION = getRegisteredItem("robotics", "robot_station");
/* 115 */     ROBOTICS_ROBOT_GOGGLES = getRegisteredItem("robotics", "robot_goggles");
/*     */     
/* 117 */     String silicon = "silicon";
/* 118 */     SILICON_REDSTONE_CHIPSET = getRegisteredItem("silicon", "redstone_chipset");
/*     */     
/* 120 */     String transport = "transport";
/* 121 */     TRANSPORT_WATERPROOF = getRegisteredItem("transport", "waterproof");
/* 122 */     TRANSPORT_GATE_COPIER = getRegisteredItem("transport", "gate_copier");
/* 123 */     TRANSPORT_PLUGGABLE_GATE = getRegisteredItem("transport", "plug_gate");
/* 124 */     TRANSPORT_PLUGGABLE_WIRE = getRegisteredItem("transport", "plug_wire");
/* 125 */     TRANSPORT_PLUGGABLE_BLOCKER = getRegisteredItem("transport", "plug_blocker");
/* 126 */     TRANSPORT_PLUGGABLE_LENS = getRegisteredItem("transport", "plug_lens");
/* 127 */     TRANSPORT_PLUGGABLE_FACADE = getRegisteredItem("transport", "plug_facade");
/* 128 */     TRANSPORT_PLUGGABLE_POWER_ADAPTOR = getRegisteredItem("transport", "plug_power_adapter");
/* 129 */     TRANSPORT_PIPE_WOOD_ITEM = getRegisteredItem("transport", "pipe_wood_item");
/* 130 */     TRANSPORT_PIPE_WOOD_FLUID = getRegisteredItem("transport", "pipe_wood_fluid");
/* 131 */     TRANSPORT_PIPE_WOOD_POWER = getRegisteredItem("transport", "pipe_wood_power");
/*     */     
/* 133 */     TRANSPORT_PIPE_DIAMOND_ITEM = getRegisteredItem("transport", "pipe_diamond_item");
/*     */     
/* 135 */     if (DEBUG) {
/* 136 */       for (Item item : Item.field_150901_e) {
/* 137 */         ResourceLocation id = item.getRegistryName();
/* 138 */         if (id.func_110624_b().startsWith("buildcraft") && 
/* 139 */           !SCANNED.contains(id.toString())) {
/* 140 */           BCLog.logger.warn("[api.items] Found an item " + id.toString() + " that was not registered with the API! Is this a bug?");
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static Item getRegisteredItem(String module, String regName) {
/* 148 */     String modid = "buildcraft" + module;
/* 149 */     Item item = (Item)Item.field_150901_e.func_82594_a(new ResourceLocation(modid, regName));
/* 150 */     if (item != null) {
/* 151 */       if (DEBUG) {
/* 152 */         BCLog.logger.info("[api.items] Found the item " + regName + " from the module " + module);
/* 153 */         SCANNED.add(modid + ":" + regName);
/*     */       } 
/* 155 */       return item;
/*     */     } 
/* 157 */     if (DEBUG) {
/* 158 */       if (Loader.isModLoaded(modid)) {
/* 159 */         BCLog.logger.info("[api.items] Did not find the item " + regName + " despite the appropriate mod being loaded (" + modid + ")");
/*     */       } else {
/* 161 */         BCLog.logger.info("[api.items] Did not find the item " + regName + " probably because the mod is not loaded (" + modid + ")");
/*     */       } 
/*     */     }
/*     */     
/* 165 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\BCItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */