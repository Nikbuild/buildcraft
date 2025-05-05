/*     */ package buildcraft.robotics.map;
/*     */ 
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.gameevent.TickEvent;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import java.io.File;
/*     */ import java.util.Date;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.gen.ChunkProviderServer;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ import net.minecraftforge.event.world.BlockEvent;
/*     */ import net.minecraftforge.event.world.ChunkEvent;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapManager
/*     */   implements Runnable
/*     */ {
/*     */   private static final int UPDATE_DELAY = 60000;
/*  26 */   private final HashBiMap<World, MapWorld> worldMap = HashBiMap.create();
/*     */   private final File location;
/*     */   private boolean stop = false;
/*     */   private long lastSaveTime;
/*     */   
/*     */   public MapManager(File location) {
/*  32 */     this.location = location;
/*     */   }
/*     */   
/*     */   public void stop() {
/*  36 */     this.stop = true;
/*  37 */     saveAllWorlds();
/*     */   }
/*     */   
/*     */   public MapWorld getWorld(World world) {
/*  41 */     if (world == null || world.field_72995_K) {
/*  42 */       return null;
/*     */     }
/*     */     
/*  45 */     if (!this.worldMap.containsKey(world)) {
/*  46 */       synchronized (this.worldMap) {
/*  47 */         this.worldMap.put(world, new MapWorld(world, this.location));
/*     */       } 
/*     */     }
/*  50 */     return (MapWorld)this.worldMap.get(world);
/*     */   }
/*     */   
/*     */   private boolean doUpdate(MapWorld world, Chunk chunk) {
/*  54 */     int x = chunk.field_76635_g;
/*  55 */     int z = chunk.field_76647_h;
/*  56 */     long updateTime = (new Date()).getTime() - 60000L;
/*  57 */     return (world.getUpdateTime(x, z) < updateTime || !world.hasChunk(x, z));
/*     */   }
/*     */   
/*     */   private void updateChunk(World rworld, Chunk chunk, boolean force) {
/*  61 */     MapWorld world = getWorld(rworld);
/*  62 */     if (world != null && (force || doUpdate(world, chunk))) {
/*  63 */       world.updateChunk(chunk);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateChunkDelayed(World rworld, Chunk chunk, boolean force, byte time) {
/*  68 */     MapWorld world = getWorld(rworld);
/*  69 */     if (world != null && (force || doUpdate(world, chunk))) {
/*  70 */       world.updateChunkDelayed(chunk, time);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void tickDelayedWorlds(TickEvent.WorldTickEvent event) {
/*  76 */     if (event.phase == TickEvent.Phase.END && event.side == Side.SERVER) {
/*  77 */       MapWorld w = (MapWorld)this.worldMap.get(event.world);
/*  78 */       if (w != null) {
/*  79 */         w.tick();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void worldUnloaded(WorldEvent.Unload event) {
/*  86 */     if (this.worldMap.containsKey(event.world)) {
/*  87 */       ((MapWorld)this.worldMap.get(event.world)).save();
/*  88 */       synchronized (this.worldMap) {
/*  89 */         this.worldMap.remove(event.world);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void chunkLoaded(ChunkEvent.Load event) {
/*  96 */     updateChunkDelayed(event.world, event.getChunk(), false, (byte)(40 + Utils.RANDOM.nextInt(20)));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void chunkUnloaded(ChunkEvent.Unload event) {
/* 101 */     updateChunk(event.world, event.getChunk(), false);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void blockPlaced(BlockEvent.PlaceEvent placeEvent) {
/* 106 */     Chunk chunk = placeEvent.world.func_72938_d(placeEvent.x, placeEvent.z);
/* 107 */     MapWorld world = getWorld(placeEvent.world);
/* 108 */     if (world != null && doUpdate(world, chunk)) {
/* 109 */       int hv = placeEvent.world.func_72976_f(placeEvent.x, placeEvent.z);
/* 110 */       if (placeEvent.y >= hv - 3) {
/* 111 */         world.updateChunk(chunk);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void blockBroken(BlockEvent.BreakEvent placeEvent) {
/* 118 */     Chunk chunk = placeEvent.world.func_72938_d(placeEvent.x, placeEvent.z);
/* 119 */     MapWorld world = getWorld(placeEvent.world);
/* 120 */     if (world != null && doUpdate(world, chunk)) {
/* 121 */       int hv = placeEvent.world.func_72976_f(placeEvent.x, placeEvent.z);
/* 122 */       if (placeEvent.y >= hv - 3) {
/* 123 */         world.updateChunk(chunk);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveAllWorlds() {
/* 129 */     synchronized (this.worldMap) {
/* 130 */       for (MapWorld world : this.worldMap.values()) {
/* 131 */         world.save();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 138 */     this.lastSaveTime = (new Date()).getTime();
/*     */     
/* 140 */     while (!this.stop) {
/* 141 */       long now = (new Date()).getTime();
/*     */       
/* 143 */       if (now - this.lastSaveTime > 120000L) {
/* 144 */         saveAllWorlds();
/* 145 */         this.lastSaveTime = now;
/*     */       } 
/*     */       
/*     */       try {
/* 149 */         Thread.sleep(4000L);
/* 150 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {
/* 157 */     for (WorldServer ws : DimensionManager.getWorlds()) {
/* 158 */       MapWorld mw = getWorld((World)ws);
/* 159 */       IChunkProvider provider = ws.func_72863_F();
/* 160 */       if (provider instanceof ChunkProviderServer)
/* 161 */         for (Object o : ((ChunkProviderServer)provider).func_152380_a()) {
/* 162 */           if (o != null && o instanceof Chunk) {
/* 163 */             Chunk c = (Chunk)o;
/* 164 */             if (!mw.hasChunk(c.field_76635_g, c.field_76647_h))
/* 165 */               mw.updateChunkDelayed(c, (byte)(40 + Utils.RANDOM.nextInt(20))); 
/*     */           } 
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\map\MapManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */