/*     */ package buildcraft.core;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.ISerializable;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.api.tiles.ITileAreaProvider;
/*     */ import buildcraft.core.lib.EntityBlock;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.utils.LaserUtils;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileMarker
/*     */   extends TileBuildCraft
/*     */   implements ITileAreaProvider
/*     */ {
/*     */   public static class TileWrapper
/*     */     implements ISerializable
/*     */   {
/*     */     public int x;
/*     */     public int y;
/*     */     public int z;
/*     */     private TileMarker marker;
/*     */     
/*     */     public TileWrapper() {
/*  34 */       this.x = Integer.MAX_VALUE;
/*  35 */       this.y = Integer.MAX_VALUE;
/*  36 */       this.z = Integer.MAX_VALUE;
/*     */     }
/*     */     
/*     */     public TileWrapper(int x, int y, int z) {
/*  40 */       this.x = x;
/*  41 */       this.y = y;
/*  42 */       this.z = z;
/*     */     }
/*     */     
/*     */     public boolean isSet() {
/*  46 */       return (this.x != Integer.MAX_VALUE);
/*     */     }
/*     */     
/*     */     public TileMarker getMarker(World world) {
/*  50 */       if (!isSet()) {
/*  51 */         return null;
/*     */       }
/*     */       
/*  54 */       if (this.marker == null) {
/*  55 */         TileEntity tile = world.func_147438_o(this.x, this.y, this.z);
/*  56 */         if (tile instanceof TileMarker) {
/*  57 */           this.marker = (TileMarker)tile;
/*     */         }
/*     */       } 
/*     */       
/*  61 */       return this.marker;
/*     */     }
/*     */     
/*     */     public void reset() {
/*  65 */       this.x = Integer.MAX_VALUE;
/*  66 */       this.y = Integer.MAX_VALUE;
/*  67 */       this.z = Integer.MAX_VALUE;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readData(ByteBuf stream) {
/*  72 */       this.x = stream.readInt();
/*  73 */       if (isSet()) {
/*  74 */         this.y = stream.readShort();
/*  75 */         this.z = stream.readInt();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeData(ByteBuf stream) {
/*  81 */       stream.writeInt(this.x);
/*  82 */       if (isSet()) {
/*     */         
/*  84 */         stream.writeShort(this.y);
/*  85 */         stream.writeInt(this.z);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Origin implements ISerializable {
/*  91 */     public TileMarker.TileWrapper vectO = new TileMarker.TileWrapper();
/*  92 */     public TileMarker.TileWrapper[] vect = new TileMarker.TileWrapper[] { new TileMarker.TileWrapper(), new TileMarker.TileWrapper(), new TileMarker.TileWrapper() }; public int xMin; public int yMin;
/*     */     public int zMin;
/*     */     
/*     */     public boolean isSet() {
/*  96 */       return this.vectO.isSet();
/*     */     }
/*     */     public int xMax; public int yMax; public int zMax;
/*     */     
/*     */     public void writeData(ByteBuf stream) {
/* 101 */       this.vectO.writeData(stream);
/* 102 */       for (TileMarker.TileWrapper tw : this.vect) {
/* 103 */         tw.writeData(stream);
/*     */       }
/* 105 */       stream.writeInt(this.xMin);
/* 106 */       stream.writeShort(this.yMin);
/* 107 */       stream.writeInt(this.zMin);
/* 108 */       stream.writeInt(this.xMax);
/* 109 */       stream.writeShort(this.yMax);
/* 110 */       stream.writeInt(this.zMax);
/*     */     }
/*     */ 
/*     */     
/*     */     public void readData(ByteBuf stream) {
/* 115 */       this.vectO.readData(stream);
/* 116 */       for (TileMarker.TileWrapper tw : this.vect) {
/* 117 */         tw.readData(stream);
/*     */       }
/* 119 */       this.xMin = stream.readInt();
/* 120 */       this.yMin = stream.readShort();
/* 121 */       this.zMin = stream.readInt();
/* 122 */       this.xMax = stream.readInt();
/* 123 */       this.yMax = stream.readShort();
/* 124 */       this.zMax = stream.readInt();
/*     */     }
/*     */   }
/*     */   
/* 128 */   public Origin origin = new Origin();
/*     */   
/*     */   public boolean showSignals = false;
/*     */   private Position initVectO;
/*     */   private Position[] initVect;
/*     */   private EntityBlock[] lasers;
/*     */   private EntityBlock[] signals;
/*     */   
/*     */   public void updateSignals() {
/* 137 */     if (!this.field_145850_b.field_72995_K) {
/* 138 */       this.showSignals = this.field_145850_b.func_72864_z(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 139 */       sendNetworkUpdate();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void switchSignals() {
/* 144 */     if (this.signals != null) {
/* 145 */       for (EntityBlock b : this.signals) {
/* 146 */         if (b != null) {
/* 147 */           CoreProxy.proxy.removeEntity((Entity)b);
/*     */         }
/*     */       } 
/* 150 */       this.signals = null;
/*     */     } 
/* 152 */     if (this.showSignals) {
/* 153 */       this.signals = new EntityBlock[6];
/* 154 */       if (!this.origin.isSet() || !this.origin.vect[0].isSet()) {
/* 155 */         this.signals[0] = LaserUtils.createLaser(this.field_145850_b, new Position(this.field_145851_c, this.field_145848_d, this.field_145849_e), new Position((this.field_145851_c + DefaultProps.MARKER_RANGE - 1), this.field_145848_d, this.field_145849_e), LaserKind.Blue);
/*     */         
/* 157 */         this.signals[1] = LaserUtils.createLaser(this.field_145850_b, new Position((this.field_145851_c - DefaultProps.MARKER_RANGE + 1), this.field_145848_d, this.field_145849_e), new Position(this.field_145851_c, this.field_145848_d, this.field_145849_e), LaserKind.Blue);
/*     */       } 
/*     */ 
/*     */       
/* 161 */       if (!this.origin.isSet() || !this.origin.vect[1].isSet()) {
/* 162 */         this.signals[2] = LaserUtils.createLaser(this.field_145850_b, new Position(this.field_145851_c, this.field_145848_d, this.field_145849_e), new Position(this.field_145851_c, (this.field_145848_d + DefaultProps.MARKER_RANGE - 1), this.field_145849_e), LaserKind.Blue);
/*     */         
/* 164 */         this.signals[3] = LaserUtils.createLaser(this.field_145850_b, new Position(this.field_145851_c, (this.field_145848_d - DefaultProps.MARKER_RANGE + 1), this.field_145849_e), new Position(this.field_145851_c, this.field_145848_d, this.field_145849_e), LaserKind.Blue);
/*     */       } 
/*     */ 
/*     */       
/* 168 */       if (!this.origin.isSet() || !this.origin.vect[2].isSet()) {
/* 169 */         this.signals[4] = LaserUtils.createLaser(this.field_145850_b, new Position(this.field_145851_c, this.field_145848_d, this.field_145849_e), new Position(this.field_145851_c, this.field_145848_d, (this.field_145849_e + DefaultProps.MARKER_RANGE - 1)), LaserKind.Blue);
/*     */         
/* 171 */         this.signals[5] = LaserUtils.createLaser(this.field_145850_b, new Position(this.field_145851_c, this.field_145848_d, (this.field_145849_e - DefaultProps.MARKER_RANGE + 1)), new Position(this.field_145851_c, this.field_145848_d, this.field_145849_e), LaserKind.Blue);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {
/* 179 */     super.initialize();
/*     */     
/* 181 */     updateSignals();
/*     */     
/* 183 */     if (this.initVectO != null) {
/* 184 */       this.origin = new Origin();
/*     */       
/* 186 */       this.origin.vectO = new TileWrapper((int)this.initVectO.x, (int)this.initVectO.y, (int)this.initVectO.z);
/*     */       
/* 188 */       for (int i = 0; i < 3; i++) {
/* 189 */         if (this.initVect[i] != null) {
/* 190 */           linkTo((TileMarker)this.field_145850_b.func_147438_o((int)(this.initVect[i]).x, (int)(this.initVect[i]).y, (int)(this.initVect[i]).z), i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tryConnection() {
/* 197 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 201 */     for (int j = 0; j < 3; j++) {
/* 202 */       if (!this.origin.isSet() || !this.origin.vect[j].isSet()) {
/* 203 */         setVect(j);
/*     */       }
/*     */     } 
/*     */     
/* 207 */     sendNetworkUpdate();
/*     */   }
/*     */   
/*     */   void setVect(int n) {
/* 211 */     int[] coords = new int[3];
/*     */     
/* 213 */     coords[0] = this.field_145851_c;
/* 214 */     coords[1] = this.field_145848_d;
/* 215 */     coords[2] = this.field_145849_e;
/*     */     
/* 217 */     if (!this.origin.isSet() || !this.origin.vect[n].isSet()) {
/* 218 */       for (int j = 1; j < DefaultProps.MARKER_RANGE; j++) {
/* 219 */         coords[n] = coords[n] + j;
/*     */         
/* 221 */         Block block = this.field_145850_b.func_147439_a(coords[0], coords[1], coords[2]);
/*     */         
/* 223 */         if (block == BuildCraftCore.markerBlock) {
/* 224 */           TileMarker marker = (TileMarker)this.field_145850_b.func_147438_o(coords[0], coords[1], coords[2]);
/*     */           
/* 226 */           if (linkTo(marker, n)) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */         
/* 231 */         coords[n] = coords[n] - j;
/* 232 */         coords[n] = coords[n] - j;
/*     */         
/* 234 */         block = this.field_145850_b.func_147439_a(coords[0], coords[1], coords[2]);
/*     */         
/* 236 */         if (block == BuildCraftCore.markerBlock) {
/* 237 */           TileMarker marker = (TileMarker)this.field_145850_b.func_147438_o(coords[0], coords[1], coords[2]);
/*     */           
/* 239 */           if (linkTo(marker, n)) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */         
/* 244 */         coords[n] = coords[n] + j;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean linkTo(TileMarker marker, int n) {
/* 250 */     if (marker == null) {
/* 251 */       return false;
/*     */     }
/*     */     
/* 254 */     if (this.origin.isSet() && marker.origin.isSet()) {
/* 255 */       return false;
/*     */     }
/*     */     
/* 258 */     if (!this.origin.isSet() && !marker.origin.isSet()) {
/* 259 */       this.origin = new Origin();
/* 260 */       marker.origin = this.origin;
/* 261 */       this.origin.vectO = new TileWrapper(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 262 */       this.origin.vect[n] = new TileWrapper(marker.field_145851_c, marker.field_145848_d, marker.field_145849_e);
/* 263 */     } else if (!this.origin.isSet()) {
/* 264 */       this.origin = marker.origin;
/* 265 */       this.origin.vect[n] = new TileWrapper(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     } else {
/* 267 */       marker.origin = this.origin;
/* 268 */       this.origin.vect[n] = new TileWrapper(marker.field_145851_c, marker.field_145848_d, marker.field_145849_e);
/*     */     } 
/*     */     
/* 271 */     this.origin.vectO.getMarker(this.field_145850_b).createLasers();
/* 272 */     updateSignals();
/* 273 */     marker.updateSignals();
/*     */     
/* 275 */     return true;
/*     */   }
/*     */   
/*     */   private void createLasers() {
/* 279 */     if (this.lasers != null) {
/* 280 */       for (EntityBlock entity : this.lasers) {
/* 281 */         if (entity != null) {
/* 282 */           CoreProxy.proxy.removeEntity((Entity)entity);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 287 */     this.lasers = new EntityBlock[12];
/* 288 */     Origin o = this.origin;
/*     */     
/* 290 */     if (!this.origin.vect[0].isSet()) {
/* 291 */       o.xMin = this.origin.vectO.x;
/* 292 */       o.xMax = this.origin.vectO.x;
/* 293 */     } else if ((this.origin.vect[0]).x < this.field_145851_c) {
/* 294 */       o.xMin = (this.origin.vect[0]).x;
/* 295 */       o.xMax = this.field_145851_c;
/*     */     } else {
/* 297 */       o.xMin = this.field_145851_c;
/* 298 */       o.xMax = (this.origin.vect[0]).x;
/*     */     } 
/*     */     
/* 301 */     if (!this.origin.vect[1].isSet()) {
/* 302 */       o.yMin = this.origin.vectO.y;
/* 303 */       o.yMax = this.origin.vectO.y;
/* 304 */     } else if ((this.origin.vect[1]).y < this.field_145848_d) {
/* 305 */       o.yMin = (this.origin.vect[1]).y;
/* 306 */       o.yMax = this.field_145848_d;
/*     */     } else {
/* 308 */       o.yMin = this.field_145848_d;
/* 309 */       o.yMax = (this.origin.vect[1]).y;
/*     */     } 
/*     */     
/* 312 */     if (!this.origin.vect[2].isSet()) {
/* 313 */       o.zMin = this.origin.vectO.z;
/* 314 */       o.zMax = this.origin.vectO.z;
/* 315 */     } else if ((this.origin.vect[2]).z < this.field_145849_e) {
/* 316 */       o.zMin = (this.origin.vect[2]).z;
/* 317 */       o.zMax = this.field_145849_e;
/*     */     } else {
/* 319 */       o.zMin = this.field_145849_e;
/* 320 */       o.zMax = (this.origin.vect[2]).z;
/*     */     } 
/*     */     
/* 323 */     this.lasers = LaserUtils.createLaserBox(this.field_145850_b, o.xMin, o.yMin, o.zMin, o.xMax, o.yMax, o.zMax, LaserKind.Red);
/*     */   }
/*     */ 
/*     */   
/*     */   public int xMin() {
/* 328 */     if (this.origin.isSet()) {
/* 329 */       return this.origin.xMin;
/*     */     }
/* 331 */     return this.field_145851_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public int yMin() {
/* 336 */     if (this.origin.isSet()) {
/* 337 */       return this.origin.yMin;
/*     */     }
/* 339 */     return this.field_145848_d;
/*     */   }
/*     */ 
/*     */   
/*     */   public int zMin() {
/* 344 */     if (this.origin.isSet()) {
/* 345 */       return this.origin.zMin;
/*     */     }
/* 347 */     return this.field_145849_e;
/*     */   }
/*     */ 
/*     */   
/*     */   public int xMax() {
/* 352 */     if (this.origin.isSet()) {
/* 353 */       return this.origin.xMax;
/*     */     }
/* 355 */     return this.field_145851_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public int yMax() {
/* 360 */     if (this.origin.isSet()) {
/* 361 */       return this.origin.yMax;
/*     */     }
/* 363 */     return this.field_145848_d;
/*     */   }
/*     */ 
/*     */   
/*     */   public int zMax() {
/* 368 */     if (this.origin.isSet()) {
/* 369 */       return this.origin.zMax;
/*     */     }
/* 371 */     return this.field_145849_e;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 376 */     super.func_145843_s();
/* 377 */     destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 382 */     TileMarker markerOrigin = null;
/*     */     
/* 384 */     if (this.origin.isSet()) {
/* 385 */       markerOrigin = this.origin.vectO.getMarker(this.field_145850_b);
/*     */       
/* 387 */       Origin o = this.origin;
/*     */       
/* 389 */       if (markerOrigin != null && markerOrigin.lasers != null) {
/* 390 */         for (EntityBlock entity : markerOrigin.lasers) {
/* 391 */           if (entity != null) {
/* 392 */             entity.func_70106_y();
/*     */           }
/*     */         } 
/* 395 */         markerOrigin.lasers = null;
/*     */       } 
/*     */       
/* 398 */       for (TileWrapper m : o.vect) {
/* 399 */         TileMarker mark = m.getMarker(this.field_145850_b);
/*     */         
/* 401 */         if (mark != null) {
/* 402 */           if (mark.lasers != null) {
/* 403 */             for (EntityBlock entity : mark.lasers) {
/* 404 */               if (entity != null) {
/* 405 */                 entity.func_70106_y();
/*     */               }
/*     */             } 
/* 408 */             mark.lasers = null;
/*     */           } 
/*     */           
/* 411 */           if (mark != this) {
/* 412 */             mark.origin = new Origin();
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 417 */       if (markerOrigin != this && markerOrigin != null) {
/* 418 */         markerOrigin.origin = new Origin();
/*     */       }
/*     */       
/* 421 */       for (TileWrapper wrapper : o.vect) {
/* 422 */         TileMarker mark = wrapper.getMarker(this.field_145850_b);
/*     */         
/* 424 */         if (mark != null) {
/* 425 */           mark.updateSignals();
/*     */         }
/*     */       } 
/* 428 */       if (markerOrigin != null) {
/* 429 */         markerOrigin.updateSignals();
/*     */       }
/*     */     } 
/*     */     
/* 433 */     if (this.signals != null) {
/* 434 */       for (EntityBlock block : this.signals) {
/* 435 */         if (block != null) {
/* 436 */           block.func_70106_y();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 441 */     this.signals = null;
/*     */     
/* 443 */     if (!this.field_145850_b.field_72995_K && markerOrigin != null && markerOrigin != this) {
/* 444 */       markerOrigin.sendNetworkUpdate();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFromWorld() {
/* 450 */     if (!this.origin.isSet()) {
/*     */       return;
/*     */     }
/*     */     
/* 454 */     Origin o = this.origin;
/*     */     
/* 456 */     for (TileWrapper m : (TileWrapper[])o.vect.clone()) {
/* 457 */       if (m.isSet()) {
/* 458 */         this.field_145850_b.func_147468_f(m.x, m.y, m.z);
/*     */         
/* 460 */         BuildCraftCore.markerBlock.func_149697_b(this.field_145850_b, m.x, m.y, m.z, 0, 0);
/*     */       } 
/*     */     } 
/*     */     
/* 464 */     this.field_145850_b.func_147468_f(o.vectO.x, o.vectO.y, o.vectO.z);
/*     */     
/* 466 */     BuildCraftCore.markerBlock.func_149697_b(this.field_145850_b, o.vectO.x, o.vectO.y, o.vectO.z, 0, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 471 */     super.func_145839_a(nbttagcompound);
/*     */     
/* 473 */     if (nbttagcompound.func_74764_b("vectO")) {
/* 474 */       this.initVectO = new Position(nbttagcompound.func_74775_l("vectO"));
/* 475 */       this.initVect = new Position[3];
/*     */       
/* 477 */       for (int i = 0; i < 3; i++) {
/* 478 */         if (nbttagcompound.func_74764_b("vect" + i)) {
/* 479 */           this.initVect[i] = new Position(nbttagcompound.func_74775_l("vect" + i));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 487 */     super.func_145841_b(nbttagcompound);
/*     */     
/* 489 */     if (this.origin.isSet() && this.origin.vectO.getMarker(this.field_145850_b) == this) {
/* 490 */       NBTTagCompound vectO = new NBTTagCompound();
/*     */       
/* 492 */       (new Position((TileEntity)this.origin.vectO.getMarker(this.field_145850_b))).writeToNBT(vectO);
/* 493 */       nbttagcompound.func_74782_a("vectO", (NBTBase)vectO);
/*     */       
/* 495 */       for (int i = 0; i < 3; i++) {
/* 496 */         if (this.origin.vect[i].isSet()) {
/* 497 */           NBTTagCompound vect = new NBTTagCompound();
/* 498 */           (new Position((this.origin.vect[i]).x, (this.origin.vect[i]).y, (this.origin.vect[i]).z)).writeToNBT(vect);
/* 499 */           nbttagcompound.func_74782_a("vect" + i, (NBTBase)vect);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 508 */     this.origin.writeData(stream);
/* 509 */     stream.writeBoolean(this.showSignals);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 514 */     this.origin.readData(stream);
/* 515 */     this.showSignals = stream.readBoolean();
/*     */     
/* 517 */     switchSignals();
/*     */     
/* 519 */     if (this.origin.vectO.isSet() && this.origin.vectO.getMarker(this.field_145850_b) != null) {
/* 520 */       this.origin.vectO.getMarker(this.field_145850_b).updateSignals();
/*     */       
/* 522 */       for (TileWrapper w : this.origin.vect) {
/* 523 */         TileMarker m = w.getMarker(this.field_145850_b);
/*     */         
/* 525 */         if (m != null) {
/* 526 */           m.updateSignals();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 531 */     createLasers();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidFromLocation(int x, int y, int z) {
/* 541 */     int equal = ((x == this.field_145851_c) ? 1 : 0) + ((y == this.field_145848_d) ? 1 : 0) + ((z == this.field_145849_e) ? 1 : 0);
/* 542 */     int touching = 0;
/*     */     
/* 544 */     if (equal == 0 || equal == 3) {
/* 545 */       return false;
/*     */     }
/*     */     
/* 548 */     if (x < xMin() - 1 || x > xMax() + 1 || y < yMin() - 1 || y > yMax() + 1 || z < 
/* 549 */       zMin() - 1 || z > zMax() + 1) {
/* 550 */       return false;
/*     */     }
/*     */     
/* 553 */     if (x >= xMin() && x <= xMax() && y >= yMin() && y <= yMax() && z >= zMin() && z <= zMax()) {
/* 554 */       return false;
/*     */     }
/*     */     
/* 557 */     if (xMin() - x == 1 || x - xMax() == 1) {
/* 558 */       touching++;
/*     */     }
/*     */     
/* 561 */     if (yMin() - y == 1 || y - yMax() == 1) {
/* 562 */       touching++;
/*     */     }
/*     */     
/* 565 */     if (zMin() - z == 1 || z - zMax() == 1) {
/* 566 */       touching++;
/*     */     }
/*     */     
/* 569 */     return (touching == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\TileMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */