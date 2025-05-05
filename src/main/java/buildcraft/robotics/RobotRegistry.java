/*     */ package buildcraft.robotics;
/*     */ 
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.robots.DockingStation;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.api.robots.IRobotRegistry;
/*     */ import buildcraft.api.robots.ResourceId;
/*     */ import buildcraft.api.robots.RobotManager;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import java.security.InvalidParameterException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSavedData;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.event.world.ChunkEvent;
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
/*     */ public class RobotRegistry
/*     */   extends WorldSavedData
/*     */   implements IRobotRegistry
/*     */ {
/*     */   protected World world;
/*  39 */   protected final HashMap<StationIndex, DockingStation> stations = new HashMap<StationIndex, DockingStation>();
/*     */   
/*  41 */   private long nextRobotID = Long.MIN_VALUE;
/*     */   
/*  43 */   private final LongHashMap robotsLoaded = new LongHashMap();
/*  44 */   private final HashSet<EntityRobot> robotsLoadedSet = new HashSet<EntityRobot>();
/*  45 */   private final HashMap<ResourceId, Long> resourcesTaken = new HashMap<ResourceId, Long>();
/*  46 */   private final LongHashMap resourcesTakenByRobot = new LongHashMap();
/*  47 */   private final LongHashMap stationsTakenByRobot = new LongHashMap();
/*     */   
/*     */   public RobotRegistry(String id) {
/*  50 */     super(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getNextRobotId() {
/*  55 */     long result = this.nextRobotID;
/*     */     
/*  57 */     this.nextRobotID++;
/*     */     
/*  59 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerRobot(EntityRobotBase robot) {
/*  64 */     func_76185_a();
/*     */     
/*  66 */     if (robot.getRobotId() == Long.MAX_VALUE) {
/*  67 */       ((EntityRobot)robot).setUniqueRobotId(getNextRobotId());
/*     */     }
/*  69 */     if (this.robotsLoaded.func_76161_b(robot.getRobotId())) {
/*  70 */       BCLog.logger.warn("Robot with id %d was not unregistered properly", new Object[] { Long.valueOf(robot.getRobotId()) });
/*     */     }
/*     */     
/*  73 */     addRobotLoaded((EntityRobot)robot);
/*     */   }
/*     */   
/*     */   private HashSet<ResourceId> getResourcesTakenByRobot(long robotId) {
/*  77 */     return (HashSet<ResourceId>)this.resourcesTakenByRobot.func_76164_a(robotId);
/*     */   }
/*     */   
/*     */   private HashSet<StationIndex> getStationsTakenByRobot(long robotId) {
/*  81 */     return (HashSet<StationIndex>)this.stationsTakenByRobot.func_76164_a(robotId);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addRobotLoaded(EntityRobot robot) {
/*  86 */     this.robotsLoaded.func_76163_a(robot.getRobotId(), robot);
/*  87 */     this.robotsLoadedSet.add(robot);
/*     */   }
/*     */   
/*     */   private void removeRobotLoaded(EntityRobot robot) {
/*  91 */     this.robotsLoaded.func_76159_d(robot.getRobotId());
/*  92 */     this.robotsLoadedSet.remove(robot);
/*     */   }
/*     */ 
/*     */   
/*     */   public void killRobot(EntityRobotBase robot) {
/*  97 */     func_76185_a();
/*     */     
/*  99 */     releaseResources(robot, true);
/* 100 */     removeRobotLoaded((EntityRobot)robot);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloadRobot(EntityRobotBase robot) {
/* 105 */     func_76185_a();
/*     */     
/* 107 */     releaseResources(robot, false, true);
/* 108 */     removeRobotLoaded((EntityRobot)robot);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityRobot getLoadedRobot(long id) {
/* 113 */     if (this.robotsLoaded.func_76161_b(id)) {
/* 114 */       return (EntityRobot)this.robotsLoaded.func_76164_a(id);
/*     */     }
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isTaken(ResourceId resourceId) {
/* 122 */     return (robotIdTaking(resourceId) != Long.MAX_VALUE);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized long robotIdTaking(ResourceId resourceId) {
/* 127 */     if (!this.resourcesTaken.containsKey(resourceId)) {
/* 128 */       return Long.MAX_VALUE;
/*     */     }
/*     */     
/* 131 */     long robotId = ((Long)this.resourcesTaken.get(resourceId)).longValue();
/*     */     
/* 133 */     if (this.robotsLoaded.func_76161_b(robotId) && !((EntityRobot)this.robotsLoaded.func_76164_a(robotId)).field_70128_L) {
/* 134 */       return robotId;
/*     */     }
/*     */ 
/*     */     
/* 138 */     release(resourceId);
/* 139 */     return Long.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized EntityRobot robotTaking(ResourceId resourceId) {
/* 145 */     long robotId = robotIdTaking(resourceId);
/*     */     
/* 147 */     if (robotId == Long.MAX_VALUE || !this.robotsLoaded.func_76161_b(robotId)) {
/* 148 */       return null;
/*     */     }
/* 150 */     return (EntityRobot)this.robotsLoaded.func_76164_a(robotId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean take(ResourceId resourceId, EntityRobotBase robot) {
/* 156 */     func_76185_a();
/*     */     
/* 158 */     return take(resourceId, robot.getRobotId());
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean take(ResourceId resourceId, long robotId) {
/* 163 */     if (resourceId == null) {
/* 164 */       return false;
/*     */     }
/*     */     
/* 167 */     func_76185_a();
/*     */     
/* 169 */     if (!this.resourcesTaken.containsKey(resourceId)) {
/* 170 */       this.resourcesTaken.put(resourceId, Long.valueOf(robotId));
/*     */       
/* 172 */       if (!this.resourcesTakenByRobot.func_76161_b(robotId)) {
/* 173 */         this.resourcesTakenByRobot.func_76163_a(robotId, new HashSet());
/*     */       }
/*     */       
/* 176 */       getResourcesTakenByRobot(robotId).add(resourceId);
/*     */       
/* 178 */       return true;
/*     */     } 
/* 180 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void release(ResourceId resourceId) {
/* 186 */     if (resourceId == null) {
/*     */       return;
/*     */     }
/*     */     
/* 190 */     func_76185_a();
/*     */     
/* 192 */     if (this.resourcesTaken.containsKey(resourceId)) {
/* 193 */       long robotId = ((Long)this.resourcesTaken.get(resourceId)).longValue();
/*     */       
/* 195 */       getResourcesTakenByRobot(robotId).remove(resourceId);
/* 196 */       this.resourcesTaken.remove(resourceId);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void releaseResources(EntityRobotBase robot) {
/* 202 */     releaseResources(robot, false);
/*     */   }
/*     */   
/*     */   private synchronized void releaseResources(EntityRobotBase robot, boolean forceAll) {
/* 206 */     releaseResources(robot, forceAll, false);
/*     */   }
/*     */   
/*     */   private synchronized void releaseResources(EntityRobotBase robot, boolean forceAll, boolean resetEntities) {
/* 210 */     func_76185_a();
/*     */     
/* 212 */     if (this.resourcesTakenByRobot.func_76161_b(robot.getRobotId())) {
/*     */       
/* 214 */       HashSet<ResourceId> resourceSet = (HashSet<ResourceId>)getResourcesTakenByRobot(robot.getRobotId()).clone();
/*     */       
/* 216 */       for (ResourceId id : resourceSet) {
/* 217 */         release(id);
/*     */       }
/*     */       
/* 220 */       this.resourcesTakenByRobot.func_76159_d(robot.getRobotId());
/*     */     } 
/*     */     
/* 223 */     if (this.stationsTakenByRobot.func_76161_b(robot.getRobotId())) {
/*     */       
/* 225 */       HashSet<StationIndex> stationSet = (HashSet<StationIndex>)getStationsTakenByRobot(robot.getRobotId()).clone();
/*     */       
/* 227 */       for (StationIndex s : stationSet) {
/* 228 */         DockingStation d = this.stations.get(s);
/*     */         
/* 230 */         if (d != null) {
/* 231 */           if (!d.canRelease()) {
/* 232 */             if (forceAll) {
/* 233 */               d.unsafeRelease(robot); continue;
/* 234 */             }  if (resetEntities && d.robotIdTaking() == robot.getRobotId())
/* 235 */               d.invalidateRobotTakingEntity(); 
/*     */             continue;
/*     */           } 
/* 238 */           d.unsafeRelease(robot);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 243 */       if (forceAll) {
/* 244 */         this.stationsTakenByRobot.func_76159_d(robot.getRobotId());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized DockingStation getStation(int x, int y, int z, ForgeDirection side) {
/* 251 */     StationIndex index = new StationIndex(side, x, y, z);
/*     */     
/* 253 */     if (this.stations.containsKey(index)) {
/* 254 */       return this.stations.get(index);
/*     */     }
/* 256 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Collection<DockingStation> getStations() {
/* 262 */     return this.stations.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void registerStation(DockingStation station) {
/* 267 */     func_76185_a();
/*     */     
/* 269 */     StationIndex index = new StationIndex(station);
/*     */     
/* 271 */     if (this.stations.containsKey(index)) {
/* 272 */       throw new InvalidParameterException("Station " + index + " already registered");
/*     */     }
/* 274 */     this.stations.put(index, station);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeStation(DockingStation station) {
/* 280 */     func_76185_a();
/*     */     
/* 282 */     StationIndex index = new StationIndex(station);
/*     */     
/* 284 */     if (this.stations.containsKey(index)) {
/* 285 */       if (station.robotTaking() != null) {
/* 286 */         if (!station.isMainStation()) {
/* 287 */           station.robotTaking().undock();
/*     */         } else {
/* 289 */           station.robotTaking().setMainStation(null);
/*     */         } 
/* 291 */       } else if (station.robotIdTaking() != Long.MAX_VALUE && 
/* 292 */         this.stationsTakenByRobot.func_76161_b(station.robotIdTaking())) {
/* 293 */         getStationsTakenByRobot(station.robotIdTaking()).remove(index);
/*     */       } 
/*     */ 
/*     */       
/* 297 */       this.stations.remove(index);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void take(DockingStation station, long robotId) {
/* 303 */     if (!this.stationsTakenByRobot.func_76161_b(robotId)) {
/* 304 */       this.stationsTakenByRobot.func_76163_a(robotId, new HashSet());
/*     */     }
/*     */     
/* 307 */     getStationsTakenByRobot(robotId).add(new StationIndex(station));
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void release(DockingStation station, long robotId) {
/* 312 */     if (this.stationsTakenByRobot.func_76161_b(robotId)) {
/* 313 */       getStationsTakenByRobot(robotId).remove(new StationIndex(station));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void func_76187_b(NBTTagCompound nbt) {
/* 319 */     nbt.func_74772_a("nextRobotID", this.nextRobotID);
/*     */     
/* 321 */     NBTTagList resourceList = new NBTTagList();
/*     */     
/* 323 */     for (Map.Entry<ResourceId, Long> e : this.resourcesTaken.entrySet()) {
/* 324 */       NBTTagCompound cpt = new NBTTagCompound();
/* 325 */       NBTTagCompound resourceId = new NBTTagCompound();
/* 326 */       ((ResourceId)e.getKey()).writeToNBT(resourceId);
/* 327 */       cpt.func_74782_a("resourceId", (NBTBase)resourceId);
/* 328 */       cpt.func_74772_a("robotId", ((Long)e.getValue()).longValue());
/*     */       
/* 330 */       resourceList.func_74742_a((NBTBase)cpt);
/*     */     } 
/*     */     
/* 333 */     nbt.func_74782_a("resourceList", (NBTBase)resourceList);
/*     */     
/* 335 */     NBTTagList stationList = new NBTTagList();
/*     */     
/* 337 */     for (Map.Entry<StationIndex, DockingStation> e : this.stations.entrySet()) {
/* 338 */       NBTTagCompound cpt = new NBTTagCompound();
/* 339 */       ((DockingStation)e.getValue()).writeToNBT(cpt);
/* 340 */       cpt.func_74778_a("stationType", RobotManager.getDockingStationName(((DockingStation)e.getValue()).getClass()));
/* 341 */       stationList.func_74742_a((NBTBase)cpt);
/*     */     } 
/*     */     
/* 344 */     nbt.func_74782_a("stationList", (NBTBase)stationList);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void func_76184_a(NBTTagCompound nbt) {
/* 349 */     this.nextRobotID = nbt.func_74763_f("nextRobotID");
/*     */     
/* 351 */     NBTTagList resourceList = nbt.func_150295_c("resourceList", 10);
/*     */     
/* 353 */     for (int i = 0; i < resourceList.func_74745_c(); i++) {
/* 354 */       NBTTagCompound cpt = resourceList.func_150305_b(i);
/* 355 */       ResourceId resourceId = ResourceId.load(cpt.func_74775_l("resourceId"));
/* 356 */       long robotId = cpt.func_74763_f("robotId");
/*     */       
/* 358 */       take(resourceId, robotId);
/*     */     } 
/*     */     
/* 361 */     NBTTagList stationList = nbt.func_150295_c("stationList", 10);
/*     */     
/* 363 */     for (int j = 0; j < stationList.func_74745_c(); j++) {
/* 364 */       Class<? extends DockingStation> cls; NBTTagCompound cpt = stationList.func_150305_b(j);
/*     */ 
/*     */ 
/*     */       
/* 368 */       if (!cpt.func_74764_b("stationType")) {
/* 369 */         Class<DockingStationPipe> clazz = DockingStationPipe.class;
/*     */       } else {
/* 371 */         cls = RobotManager.getDockingStationByName(cpt.func_74779_i("stationType"));
/* 372 */         if (cls == null) {
/* 373 */           BCLog.logger.error("Could not load docking station of type " + nbt
/* 374 */               .func_74779_i("stationType"));
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */       try {
/* 380 */         DockingStation station = cls.newInstance();
/* 381 */         station.readFromNBT(cpt);
/*     */         
/* 383 */         registerStation(station);
/*     */         
/* 385 */         if (station.linkedId() != Long.MAX_VALUE) {
/* 386 */           take(station, station.linkedId());
/*     */         }
/* 388 */       } catch (Exception e) {
/* 389 */         BCLog.logger.error("Could not load docking station", e);
/*     */       } 
/*     */       continue;
/*     */     } 
/*     */   }
/*     */   @SubscribeEvent
/*     */   public void onChunkUnload(ChunkEvent.Unload e) {
/* 396 */     if (e.world == this.world) {
/* 397 */       for (EntityRobot robot : new ArrayList(this.robotsLoadedSet)) {
/* 398 */         if (!e.world.field_72996_f.contains(robot)) {
/* 399 */           robot.onChunkUnload();
/*     */         }
/*     */       } 
/* 402 */       for (DockingStation station : new ArrayList(this.stations.values())) {
/* 403 */         if (!this.world.func_72899_e(station.x(), station.y(), station.z())) {
/* 404 */           station.onChunkUnload();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registryMarkDirty() {
/* 416 */     func_76185_a();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\RobotRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */