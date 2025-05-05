/*     */ package buildcraft.core.blueprints;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.blueprints.IBuilderContext;
/*     */ import buildcraft.api.blueprints.MappingNotFoundException;
/*     */ import buildcraft.api.blueprints.SchematicBlock;
/*     */ import buildcraft.api.blueprints.SchematicBlockBase;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.IAreaProvider;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.core.Box;
/*     */ import buildcraft.core.builders.BuildingItem;
/*     */ import buildcraft.core.builders.BuildingSlot;
/*     */ import buildcraft.core.builders.BuildingSlotBlock;
/*     */ import buildcraft.core.builders.IBuildingItemsProvider;
/*     */ import buildcraft.core.builders.TileAbstractBuilder;
/*     */ import buildcraft.core.lib.utils.BitSetUtils;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import java.util.BitSet;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.BlockSnapshot;
/*     */ import net.minecraftforge.event.world.BlockEvent;
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
/*     */ 
/*     */ public abstract class BptBuilderBase
/*     */   implements IAreaProvider
/*     */ {
/*     */   public BlueprintBase blueprint;
/*     */   public BptContext context;
/*     */   protected BitSet usedLocations;
/*     */   protected boolean done;
/*     */   protected int x;
/*     */   protected int y;
/*     */   protected int z;
/*     */   protected boolean initialized = false;
/*  59 */   private long nextBuildDate = 0L;
/*     */   
/*     */   public BptBuilderBase(BlueprintBase bluePrint, World world, int x, int y, int z) {
/*  62 */     this.blueprint = bluePrint;
/*  63 */     this.x = x;
/*  64 */     this.y = y;
/*  65 */     this.z = z;
/*  66 */     this.usedLocations = new BitSet(bluePrint.sizeX * bluePrint.sizeY * bluePrint.sizeZ);
/*  67 */     this.done = false;
/*     */     
/*  69 */     Box box = new Box();
/*  70 */     box.initialize(this);
/*     */     
/*  72 */     this.context = bluePrint.getContext(world, box);
/*     */   }
/*     */   
/*     */   protected boolean isLocationUsed(int i, int j, int k) {
/*  76 */     int xCoord = i - this.x + this.blueprint.anchorX;
/*  77 */     int yCoord = j - this.y + this.blueprint.anchorY;
/*  78 */     int zCoord = k - this.z + this.blueprint.anchorZ;
/*  79 */     return this.usedLocations.get((zCoord * this.blueprint.sizeY + yCoord) * this.blueprint.sizeX + xCoord);
/*     */   }
/*     */   
/*     */   protected void markLocationUsed(int i, int j, int k) {
/*  83 */     int xCoord = i - this.x + this.blueprint.anchorX;
/*  84 */     int yCoord = j - this.y + this.blueprint.anchorY;
/*  85 */     int zCoord = k - this.z + this.blueprint.anchorZ;
/*  86 */     this.usedLocations.set((zCoord * this.blueprint.sizeY + yCoord) * this.blueprint.sizeX + xCoord, true);
/*     */   }
/*     */   
/*     */   public void initialize() {
/*  90 */     if (!this.initialized) {
/*  91 */       internalInit();
/*  92 */       this.initialized = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void internalInit();
/*     */   
/*     */   protected abstract BuildingSlot reserveNextBlock(World paramWorld);
/*     */   
/*     */   protected abstract BuildingSlot getNextBlock(World paramWorld, TileAbstractBuilder paramTileAbstractBuilder);
/*     */   
/*     */   public boolean buildNextSlot(World world, TileAbstractBuilder builder, double x, double y, double z) {
/* 103 */     initialize();
/*     */     
/* 105 */     if (world.func_82737_E() < this.nextBuildDate) {
/* 106 */       return false;
/*     */     }
/*     */     
/* 109 */     BuildingSlot slot = getNextBlock(world, builder);
/*     */     
/* 111 */     if (buildSlot(world, (IBuildingItemsProvider)builder, slot, x + 0.5D, y + 0.5D, z + 0.5D)) {
/* 112 */       this.nextBuildDate = world.func_82737_E() + slot.buildTime();
/* 113 */       return true;
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean buildSlot(World world, IBuildingItemsProvider builder, BuildingSlot slot, double x, double y, double z) {
/* 121 */     initialize();
/*     */     
/* 123 */     if (slot != null) {
/* 124 */       slot.built = true;
/* 125 */       BuildingItem i = new BuildingItem();
/* 126 */       i.origin = new Position(x, y, z);
/* 127 */       i.destination = slot.getDestination();
/* 128 */       i.slotToBuild = slot;
/* 129 */       i.context = getContext();
/* 130 */       i.setStacksToDisplay(slot.getStacksToDisplay());
/* 131 */       builder.addAndLaunchBuildingItem(i);
/*     */       
/* 133 */       return true;
/*     */     } 
/*     */     
/* 136 */     return false;
/*     */   }
/*     */   
/*     */   public BuildingSlot reserveNextSlot(World world) {
/* 140 */     initialize();
/*     */     
/* 142 */     return reserveNextBlock(world);
/*     */   }
/*     */ 
/*     */   
/*     */   public int xMin() {
/* 147 */     return this.x - this.blueprint.anchorX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int yMin() {
/* 152 */     return this.y - this.blueprint.anchorY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int zMin() {
/* 157 */     return this.z - this.blueprint.anchorZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int xMax() {
/* 162 */     return this.x + this.blueprint.sizeX - this.blueprint.anchorX - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int yMax() {
/* 167 */     return this.y + this.blueprint.sizeY - this.blueprint.anchorY - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int zMax() {
/* 172 */     return this.z + this.blueprint.sizeZ - this.blueprint.anchorZ - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFromWorld() {}
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox() {
/* 181 */     return AxisAlignedBB.func_72330_a(xMin(), yMin(), zMin(), xMax(), yMax(), zMax());
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcessing(World world) {}
/*     */ 
/*     */   
/*     */   public BptContext getContext() {
/* 189 */     return this.context;
/*     */   }
/*     */   
/*     */   public boolean isDone(IBuildingItemsProvider builder) {
/* 193 */     return (this.done && builder.getBuilders().size() == 0);
/*     */   }
/*     */   
/*     */   private int getBlockBreakEnergy(BuildingSlotBlock slot) {
/* 197 */     return BlockUtils.computeBlockBreakEnergy(this.context.world(), slot.x, slot.y, slot.z);
/*     */   }
/*     */   
/*     */   protected final boolean canDestroy(TileAbstractBuilder builder, IBuilderContext context, BuildingSlotBlock slot) {
/* 201 */     return (builder.energyAvailable() >= getBlockBreakEnergy(slot));
/*     */   }
/*     */   
/*     */   public void consumeEnergyToDestroy(TileAbstractBuilder builder, BuildingSlotBlock slot) {
/* 205 */     builder.consumeEnergy(getBlockBreakEnergy(slot));
/*     */   }
/*     */   
/*     */   public void createDestroyItems(BuildingSlotBlock slot) {
/* 209 */     int hardness = (int)Math.ceil(getBlockBreakEnergy(slot) / 160.0D);
/*     */     
/* 211 */     for (int i = 0; i < hardness; i++) {
/* 212 */       slot.addStackConsumed(new ItemStack((Block)BuildCraftCore.buildToolBlock));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void useRequirements(IInventory inv, BuildingSlot slot) {}
/*     */ 
/*     */   
/*     */   public void saveBuildStateToNBT(NBTTagCompound nbt, IBuildingItemsProvider builder) {
/* 221 */     nbt.func_74773_a("usedLocationList", BitSetUtils.toByteArray(this.usedLocations));
/*     */     
/* 223 */     NBTTagList buildingList = new NBTTagList();
/*     */     
/* 225 */     for (BuildingItem item : builder.getBuilders()) {
/* 226 */       NBTTagCompound sub = new NBTTagCompound();
/* 227 */       item.writeToNBT(sub);
/* 228 */       buildingList.func_74742_a((NBTBase)sub);
/*     */     } 
/*     */     
/* 231 */     nbt.func_74782_a("buildersInAction", (NBTBase)buildingList);
/*     */   }
/*     */   
/*     */   public void loadBuildStateToNBT(NBTTagCompound nbt, IBuildingItemsProvider builder) {
/* 235 */     if (nbt.func_74764_b("usedLocationList")) {
/* 236 */       this.usedLocations = BitSetUtils.fromByteArray(nbt.func_74770_j("usedLocationList"));
/*     */     }
/*     */ 
/*     */     
/* 240 */     NBTTagList buildingList = nbt.func_150295_c("buildersInAction", 10);
/*     */ 
/*     */     
/* 243 */     for (int i = 0; i < buildingList.func_74745_c(); i++) {
/* 244 */       BuildingItem item = new BuildingItem();
/*     */       
/*     */       try {
/* 247 */         item.readFromNBT(buildingList.func_150305_b(i));
/* 248 */         item.context = getContext();
/* 249 */         builder.getBuilders().add(item);
/* 250 */       } catch (MappingNotFoundException e) {
/* 251 */         BCLog.logger.log(Level.WARN, "can't load building item", (Throwable)e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 257 */     if (nbt.func_74764_b("clearList")) {
/* 258 */       NBTTagList clearList = nbt.func_150295_c("clearList", 10);
/*     */       
/* 260 */       for (int j = 0; j < clearList.func_74745_c(); j++) {
/* 261 */         NBTTagCompound cpt = clearList.func_150305_b(j);
/* 262 */         BlockIndex o = new BlockIndex(cpt);
/* 263 */         markLocationUsed(o.x, o.y, o.z);
/*     */       } 
/*     */     } 
/*     */     
/* 267 */     if (nbt.func_74764_b("builtList")) {
/* 268 */       NBTTagList builtList = nbt.func_150295_c("builtList", 10);
/*     */       
/* 270 */       for (int j = 0; j < builtList.func_74745_c(); j++) {
/* 271 */         NBTTagCompound cpt = builtList.func_150305_b(j);
/* 272 */         BlockIndex o = new BlockIndex(cpt);
/* 273 */         markLocationUsed(o.x, o.y, o.z);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isBlockBreakCanceled(World world, int x, int y, int z) {
/* 279 */     if (!world.func_147437_c(x, y, z)) {
/*     */ 
/*     */       
/* 282 */       BlockEvent.BreakEvent breakEvent = new BlockEvent.BreakEvent(x, y, z, world, world.func_147439_a(x, y, z), world.func_72805_g(x, y, z), CoreProxy.proxy.getBuildCraftPlayer((WorldServer)world, this.x, this.y, this.z).get());
/* 283 */       MinecraftForge.EVENT_BUS.post((Event)breakEvent);
/* 284 */       return breakEvent.isCanceled();
/*     */     } 
/* 286 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean isBlockPlaceCanceled(World world, int x, int y, int z, SchematicBlockBase schematic) {
/* 290 */     Block block = (schematic instanceof SchematicBlock) ? ((SchematicBlock)schematic).block : Blocks.field_150348_b;
/* 291 */     int meta = (schematic instanceof SchematicBlock) ? ((SchematicBlock)schematic).meta : 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 296 */     BlockEvent.PlaceEvent placeEvent = new BlockEvent.PlaceEvent(new BlockSnapshot(world, x, y, z, block, meta), Blocks.field_150350_a, CoreProxy.proxy.getBuildCraftPlayer((WorldServer)world, this.x, this.y, this.z).get());
/*     */ 
/*     */     
/* 299 */     MinecraftForge.EVENT_BUS.post((Event)placeEvent);
/* 300 */     return placeEvent.isCanceled();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\BptBuilderBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */