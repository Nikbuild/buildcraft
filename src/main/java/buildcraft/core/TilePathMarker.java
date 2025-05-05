/*     */ package buildcraft.core;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.IPathProvider;
/*     */ import buildcraft.api.core.Position;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
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
/*     */ public class TilePathMarker
/*     */   extends TileMarker
/*     */   implements IPathProvider
/*     */ {
/*  30 */   private static ArrayList<TilePathMarker> availableMarkers = new ArrayList<TilePathMarker>();
/*     */   
/*     */   public int x0;
/*     */   public int y0;
/*     */   public int z0;
/*     */   public int x1;
/*  36 */   public LaserData[] lasers = new LaserData[2]; public int y1; public int z1; public boolean loadLink0 = false;
/*     */   public boolean loadLink1 = false;
/*     */   public boolean tryingToConnect = false;
/*  39 */   public TilePathMarker[] links = new TilePathMarker[2];
/*     */   
/*     */   public boolean isFullyConnected() {
/*  42 */     return (this.lasers[0] != null && this.lasers[1] != null);
/*     */   }
/*     */   
/*     */   public boolean isLinkedTo(TilePathMarker pathMarker) {
/*  46 */     return (this.links[0] == pathMarker || this.links[1] == pathMarker);
/*     */   }
/*     */   
/*     */   public void connect(TilePathMarker marker, LaserData laser) {
/*  50 */     if (this.lasers[0] == null) {
/*  51 */       this.lasers[0] = laser;
/*  52 */       this.links[0] = marker;
/*  53 */     } else if (this.lasers[1] == null) {
/*  54 */       this.lasers[1] = laser;
/*  55 */       this.links[1] = marker;
/*     */     } 
/*     */     
/*  58 */     if (isFullyConnected()) {
/*  59 */       availableMarkers.remove(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void createLaserAndConnect(TilePathMarker pathMarker) {
/*  64 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  68 */     LaserData laser = new LaserData(new Position(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.5D), new Position(pathMarker.field_145851_c + 0.5D, pathMarker.field_145848_d + 0.5D, pathMarker.field_145849_e + 0.5D));
/*     */ 
/*     */ 
/*     */     
/*  72 */     LaserData laser2 = new LaserData(laser.head, laser.tail);
/*  73 */     laser2.isVisible = false;
/*     */     
/*  75 */     connect(pathMarker, laser);
/*  76 */     pathMarker.connect(this, laser2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TilePathMarker findNearestAvailablePathMarker() {
/*  84 */     TilePathMarker nearestAvailable = null;
/*  85 */     double nearestDistance = 0.0D;
/*     */     
/*  87 */     for (TilePathMarker t : availableMarkers) {
/*  88 */       if (t == this || t == this.links[0] || t == this.links[1] || (t.func_145831_w()).field_73011_w.field_76574_g != (func_145831_w()).field_73011_w.field_76574_g) {
/*     */         continue;
/*     */       }
/*     */       
/*  92 */       int dx = this.field_145851_c - t.field_145851_c;
/*  93 */       int dy = this.field_145848_d - t.field_145848_d;
/*  94 */       int dz = this.field_145849_e - t.field_145849_e;
/*  95 */       double distance = (dx * dx + dy * dy + dz * dz);
/*     */       
/*  97 */       if (distance > (DefaultProps.MARKER_RANGE * DefaultProps.MARKER_RANGE)) {
/*     */         continue;
/*     */       }
/*     */       
/* 101 */       if (nearestAvailable == null || distance < nearestDistance) {
/* 102 */         nearestAvailable = t;
/* 103 */         nearestDistance = distance;
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     return nearestAvailable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tryConnection() {
/* 113 */     if (this.field_145850_b.field_72995_K || isFullyConnected()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 118 */     this.tryingToConnect = !this.tryingToConnect;
/*     */     
/* 120 */     sendNetworkUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 125 */     super.func_145845_h();
/*     */     
/* 127 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 131 */     if (this.tryingToConnect) {
/* 132 */       TilePathMarker nearestPathMarker = findNearestAvailablePathMarker();
/*     */       
/* 134 */       if (nearestPathMarker != null) {
/* 135 */         createLaserAndConnect(nearestPathMarker);
/*     */       }
/*     */       
/* 138 */       this.tryingToConnect = false;
/*     */       
/* 140 */       sendNetworkUpdate();
/* 141 */       func_145831_w().func_147458_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<BlockIndex> getPath() {
/* 148 */     HashSet<BlockIndex> visitedPaths = new HashSet<BlockIndex>();
/* 149 */     ArrayList<BlockIndex> res = new ArrayList<BlockIndex>();
/*     */     
/* 151 */     TilePathMarker nextTile = this;
/*     */     
/* 153 */     while (nextTile != null) {
/* 154 */       BlockIndex b = new BlockIndex(nextTile.field_145851_c, nextTile.field_145848_d, nextTile.field_145849_e);
/*     */       
/* 156 */       visitedPaths.add(b);
/* 157 */       res.add(b);
/*     */       
/* 159 */       if (nextTile.links[0] != null && 
/* 160 */         !visitedPaths.contains(new BlockIndex((nextTile.links[0]).field_145851_c, (nextTile.links[0]).field_145848_d, (nextTile.links[0]).field_145849_e))) {
/* 161 */         nextTile = nextTile.links[0]; continue;
/* 162 */       }  if (nextTile.links[1] != null && 
/* 163 */         !visitedPaths.contains(new BlockIndex((nextTile.links[1]).field_145851_c, (nextTile.links[1]).field_145848_d, (nextTile.links[1]).field_145849_e))) {
/* 164 */         nextTile = nextTile.links[1]; continue;
/*     */       } 
/* 166 */       nextTile = null;
/*     */     } 
/*     */ 
/*     */     
/* 170 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 176 */     super.func_145843_s();
/*     */     
/* 178 */     if (this.links[0] != null) {
/* 179 */       this.links[0].unlink(this);
/*     */     }
/*     */     
/* 182 */     if (this.links[1] != null) {
/* 183 */       this.links[1].unlink(this);
/*     */     }
/*     */     
/* 186 */     availableMarkers.remove(this);
/* 187 */     this.tryingToConnect = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/* 192 */     super.initialize();
/*     */     
/* 194 */     if (!this.field_145850_b.field_72995_K && !isFullyConnected()) {
/* 195 */       availableMarkers.add(this);
/*     */     }
/*     */     
/* 198 */     if (this.loadLink0) {
/* 199 */       TileEntity e0 = this.field_145850_b.func_147438_o(this.x0, this.y0, this.z0);
/*     */       
/* 201 */       if (this.links[0] != e0 && this.links[1] != e0 && e0 instanceof TilePathMarker) {
/* 202 */         createLaserAndConnect((TilePathMarker)e0);
/*     */       }
/*     */       
/* 205 */       this.loadLink0 = false;
/*     */     } 
/*     */     
/* 208 */     if (this.loadLink1) {
/* 209 */       TileEntity e1 = this.field_145850_b.func_147438_o(this.x1, this.y1, this.z1);
/*     */       
/* 211 */       if (this.links[0] != e1 && this.links[1] != e1 && e1 instanceof TilePathMarker) {
/* 212 */         createLaserAndConnect((TilePathMarker)e1);
/*     */       }
/*     */       
/* 215 */       this.loadLink1 = false;
/*     */     } 
/*     */     
/* 218 */     sendNetworkUpdate();
/*     */   }
/*     */   
/*     */   private void unlink(TilePathMarker tile) {
/* 222 */     if (this.links[0] == tile) {
/* 223 */       this.lasers[0] = null;
/* 224 */       this.links[0] = null;
/*     */     } 
/*     */     
/* 227 */     if (this.links[1] == tile) {
/* 228 */       this.lasers[1] = null;
/* 229 */       this.links[1] = null;
/*     */     } 
/*     */     
/* 232 */     if (!isFullyConnected() && !availableMarkers.contains(this) && !this.field_145850_b.field_72995_K) {
/* 233 */       availableMarkers.add(this);
/*     */     }
/*     */     
/* 236 */     sendNetworkUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 241 */     super.func_145839_a(nbttagcompound);
/*     */     
/* 243 */     if (nbttagcompound.func_74764_b("x0")) {
/* 244 */       this.x0 = nbttagcompound.func_74762_e("x0");
/* 245 */       this.y0 = nbttagcompound.func_74762_e("y0");
/* 246 */       this.z0 = nbttagcompound.func_74762_e("z0");
/*     */       
/* 248 */       this.loadLink0 = true;
/*     */     } 
/*     */     
/* 251 */     if (nbttagcompound.func_74764_b("x1")) {
/* 252 */       this.x1 = nbttagcompound.func_74762_e("x1");
/* 253 */       this.y1 = nbttagcompound.func_74762_e("y1");
/* 254 */       this.z1 = nbttagcompound.func_74762_e("z1");
/*     */       
/* 256 */       this.loadLink1 = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 262 */     super.func_145841_b(nbttagcompound);
/*     */     
/* 264 */     if (this.links[0] != null) {
/* 265 */       nbttagcompound.func_74768_a("x0", (this.links[0]).field_145851_c);
/* 266 */       nbttagcompound.func_74768_a("y0", (this.links[0]).field_145848_d);
/* 267 */       nbttagcompound.func_74768_a("z0", (this.links[0]).field_145849_e);
/*     */     } 
/*     */     
/* 270 */     if (this.links[1] != null) {
/* 271 */       nbttagcompound.func_74768_a("x1", (this.links[1]).field_145851_c);
/* 272 */       nbttagcompound.func_74768_a("y1", (this.links[1]).field_145848_d);
/* 273 */       nbttagcompound.func_74768_a("z1", (this.links[1]).field_145849_e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onChunkUnload() {
/* 279 */     super.onChunkUnload();
/* 280 */     availableMarkers.remove(this);
/*     */   }
/*     */   
/*     */   public static void clearAvailableMarkersList() {
/* 284 */     availableMarkers.clear();
/*     */   }
/*     */   
/*     */   public static void clearAvailableMarkersList(World w) {
/* 288 */     for (Iterator<TilePathMarker> it = availableMarkers.iterator(); it.hasNext(); ) {
/* 289 */       TilePathMarker t = it.next();
/* 290 */       if ((t.func_145831_w()).field_73011_w.field_76574_g == w.field_73011_w.field_76574_g) {
/* 291 */         it.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/* 298 */     boolean previousState = this.tryingToConnect;
/*     */     
/* 300 */     int flags = data.readUnsignedByte();
/* 301 */     if ((flags & 0x1) != 0) {
/* 302 */       this.lasers[0] = new LaserData();
/* 303 */       this.lasers[0].readData(data);
/*     */     } else {
/* 305 */       this.lasers[0] = null;
/*     */     } 
/* 307 */     if ((flags & 0x2) != 0) {
/* 308 */       this.lasers[1] = new LaserData();
/* 309 */       this.lasers[1].readData(data);
/*     */     } else {
/* 311 */       this.lasers[1] = null;
/*     */     } 
/* 313 */     this.tryingToConnect = ((flags & 0x4) != 0);
/*     */     
/* 315 */     if (previousState != this.tryingToConnect) {
/* 316 */       this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/* 322 */     int flags = ((this.lasers[0] != null) ? 1 : 0) | ((this.lasers[1] != null) ? 2 : 0) | (this.tryingToConnect ? 4 : 0);
/* 323 */     data.writeByte(flags);
/* 324 */     if (this.lasers[0] != null) {
/* 325 */       this.lasers[0].writeData(data);
/*     */     }
/* 327 */     if (this.lasers[1] != null) {
/* 328 */       this.lasers[1].writeData(data);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFromWorld() {
/* 334 */     List<BlockIndex> path = getPath();
/* 335 */     for (BlockIndex b : path) {
/* 336 */       BuildCraftCore.pathMarkerBlock.func_149697_b(this.field_145850_b, b.x, b.y, b.z, 0, 0);
/*     */ 
/*     */ 
/*     */       
/* 340 */       this.field_145850_b.func_147468_f(b.x, b.y, b.z);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\TilePathMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */