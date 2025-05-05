/*     */ package buildcraft.core;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.IAreaProvider;
/*     */ import buildcraft.api.core.IBox;
/*     */ import buildcraft.api.core.ISerializable;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.core.lib.utils.LaserUtils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Box
/*     */   implements IBox, ISerializable
/*     */ {
/*     */   public enum Kind
/*     */   {
/*  31 */     LASER_RED,
/*  32 */     LASER_YELLOW,
/*  33 */     LASER_GREEN,
/*  34 */     LASER_BLUE,
/*  35 */     STRIPES,
/*  36 */     BLUE_STRIPES;
/*     */   }
/*     */   
/*  39 */   public Kind kind = Kind.LASER_RED;
/*     */   
/*     */   public int xMin;
/*     */   public int yMin;
/*     */   public int zMin;
/*     */   public int xMax;
/*     */   
/*     */   public Box() {
/*  47 */     reset();
/*     */   }
/*     */   public int yMax; public int zMax; public boolean initialized; public boolean isVisible = true; public LaserData[] lasersData;
/*     */   public Box(TileEntity e) {
/*  51 */     initialize(e.field_145851_c, e.field_145848_d, e.field_145849_e, e.field_145851_c + 1, e.field_145848_d + 1, e.field_145849_e + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Box(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
/*  56 */     this();
/*  57 */     initialize(xMin, yMin, zMin, xMax, yMax, zMax);
/*     */   }
/*     */   
/*     */   public void reset() {
/*  61 */     this.initialized = false;
/*  62 */     this.xMin = Integer.MAX_VALUE;
/*  63 */     this.yMin = Integer.MAX_VALUE;
/*  64 */     this.zMin = Integer.MAX_VALUE;
/*  65 */     this.xMax = Integer.MAX_VALUE;
/*  66 */     this.yMax = Integer.MAX_VALUE;
/*  67 */     this.zMax = Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public boolean isInitialized() {
/*  71 */     return this.initialized;
/*     */   }
/*     */   
/*     */   public void initialize(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
/*  75 */     if (xMin < xMax) {
/*  76 */       this.xMin = xMin;
/*  77 */       this.xMax = xMax;
/*     */     } else {
/*  79 */       this.xMin = xMax;
/*  80 */       this.xMax = xMin;
/*     */     } 
/*     */     
/*  83 */     if (yMin < yMax) {
/*  84 */       this.yMin = yMin;
/*  85 */       this.yMax = yMax;
/*     */     } else {
/*  87 */       this.yMin = yMax;
/*  88 */       this.yMax = yMin;
/*     */     } 
/*     */     
/*  91 */     if (zMin < zMax) {
/*  92 */       this.zMin = zMin;
/*  93 */       this.zMax = zMax;
/*     */     } else {
/*  95 */       this.zMin = zMax;
/*  96 */       this.zMax = zMin;
/*     */     } 
/*     */     
/*  99 */     this.initialized = (xMin != Integer.MAX_VALUE && yMin != Integer.MAX_VALUE && zMin != Integer.MAX_VALUE && xMax != Integer.MAX_VALUE && yMax != Integer.MAX_VALUE && zMax != Integer.MAX_VALUE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize(Box box) {
/* 104 */     initialize(box.xMin, box.yMin, box.zMin, box.xMax, box.yMax, box.zMax);
/*     */   }
/*     */   
/*     */   public void initialize(IAreaProvider a) {
/* 108 */     initialize(a.xMin(), a.yMin(), a.zMin(), a.xMax(), a.yMax(), a.zMax());
/*     */   }
/*     */   
/*     */   public void initialize(NBTTagCompound nbttagcompound) {
/* 112 */     this.kind = Kind.values()[nbttagcompound.func_74765_d("kind")];
/*     */     
/* 114 */     initialize(nbttagcompound.func_74762_e("xMin"), nbttagcompound
/* 115 */         .func_74762_e("yMin"), nbttagcompound
/* 116 */         .func_74762_e("zMin"), nbttagcompound
/* 117 */         .func_74762_e("xMax"), nbttagcompound
/* 118 */         .func_74762_e("yMax"), nbttagcompound
/* 119 */         .func_74762_e("zMax"));
/*     */   }
/*     */   
/*     */   public void initialize(int centerX, int centerY, int centerZ, int size) {
/* 123 */     initialize(centerX - size, centerY - size, centerZ - size, centerX + size, centerY + size, centerZ + size);
/*     */   }
/*     */   
/*     */   public List<BlockIndex> getBlocksInArea() {
/* 127 */     List<BlockIndex> blocks = new ArrayList<BlockIndex>();
/*     */     
/* 129 */     for (float x = this.xMin; x <= this.xMax; x++) {
/* 130 */       for (float y = this.yMin; y <= this.yMax; y++) {
/* 131 */         float z; for (z = this.zMin; z <= this.zMax; z++) {
/* 132 */           blocks.add(new BlockIndex((int)x, (int)y, (int)z));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     return blocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public Box expand(int amount) {
/* 142 */     this.xMin -= amount;
/* 143 */     this.yMin -= amount;
/* 144 */     this.zMin -= amount;
/* 145 */     this.xMax += amount;
/* 146 */     this.yMax += amount;
/* 147 */     this.zMax += amount;
/*     */     
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBox contract(int amount) {
/* 154 */     return expand(-amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(double x, double y, double z) {
/* 159 */     return contains(MathHelper.func_76128_c(x), MathHelper.func_76128_c(y), MathHelper.func_76128_c(z));
/*     */   }
/*     */   
/*     */   public boolean contains(int x, int y, int z) {
/* 163 */     return (x >= this.xMin && x <= this.xMax && y >= this.yMin && y <= this.yMax && z >= this.zMin && z <= this.zMax);
/*     */   }
/*     */   
/*     */   public boolean contains(Position p) {
/* 167 */     return contains((int)p.x, (int)p.y, (int)p.z);
/*     */   }
/*     */   
/*     */   public boolean contains(BlockIndex i) {
/* 171 */     return contains(i.x, i.y, i.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public Position pMin() {
/* 176 */     return new Position(this.xMin, this.yMin, this.zMin);
/*     */   }
/*     */ 
/*     */   
/*     */   public Position pMax() {
/* 181 */     return new Position(this.xMax, this.yMax, this.zMax);
/*     */   }
/*     */   
/*     */   public int sizeX() {
/* 185 */     return this.xMax - this.xMin + 1;
/*     */   }
/*     */   
/*     */   public int sizeY() {
/* 189 */     return this.yMax - this.yMin + 1;
/*     */   }
/*     */   
/*     */   public int sizeZ() {
/* 193 */     return this.zMax - this.zMin + 1;
/*     */   }
/*     */   
/*     */   public double centerX() {
/* 197 */     return this.xMin + sizeX() / 2.0D;
/*     */   }
/*     */   
/*     */   public double centerY() {
/* 201 */     return this.yMin + sizeY() / 2.0D;
/*     */   }
/*     */   
/*     */   public double centerZ() {
/* 205 */     return this.zMin + sizeZ() / 2.0D;
/*     */   }
/*     */   
/*     */   public Box rotateLeft() {
/* 209 */     Box nBox = new Box();
/* 210 */     nBox.xMin = sizeZ() - 1 - this.zMin;
/* 211 */     nBox.yMin = this.yMin;
/* 212 */     nBox.zMin = this.xMin;
/*     */     
/* 214 */     nBox.xMax = sizeZ() - 1 - this.zMax;
/* 215 */     nBox.yMax = this.yMax;
/* 216 */     nBox.zMax = this.xMax;
/*     */     
/* 218 */     nBox.reorder();
/*     */     
/* 220 */     return nBox;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reorder() {
/* 226 */     if (this.xMin > this.xMax) {
/* 227 */       int tmp = this.xMin;
/* 228 */       this.xMin = this.xMax;
/* 229 */       this.xMax = tmp;
/*     */     } 
/*     */     
/* 232 */     if (this.yMin > this.yMax) {
/* 233 */       int tmp = this.yMin;
/* 234 */       this.yMin = this.yMax;
/* 235 */       this.yMax = tmp;
/*     */     } 
/*     */     
/* 238 */     if (this.zMin > this.zMax) {
/* 239 */       int tmp = this.zMin;
/* 240 */       this.zMin = this.zMax;
/* 241 */       this.zMax = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void createLaserData() {
/* 247 */     this.lasersData = LaserUtils.createLaserDataBox(this.xMin, this.yMin, this.zMin, this.xMax, this.yMax, this.zMax);
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound) {
/* 251 */     nbttagcompound.func_74774_a("kind", (byte)this.kind.ordinal());
/*     */     
/* 253 */     nbttagcompound.func_74768_a("xMin", this.xMin);
/* 254 */     nbttagcompound.func_74768_a("yMin", this.yMin);
/* 255 */     nbttagcompound.func_74768_a("zMin", this.zMin);
/*     */     
/* 257 */     nbttagcompound.func_74768_a("xMax", this.xMax);
/* 258 */     nbttagcompound.func_74768_a("yMax", this.yMax);
/* 259 */     nbttagcompound.func_74768_a("zMax", this.zMax);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 264 */     return "{" + this.xMin + ", " + this.xMax + "}, {" + this.yMin + ", " + this.yMax + "}, {" + this.zMin + ", " + this.zMax + "}";
/*     */   }
/*     */   
/*     */   public Box extendToEncompass(Box toBeContained) {
/* 268 */     if (toBeContained == null || !toBeContained.initialized) {
/* 269 */       return this;
/*     */     }
/*     */     
/* 272 */     if (toBeContained.xMin < this.xMin) {
/* 273 */       this.xMin = toBeContained.xMin;
/*     */     }
/*     */     
/* 276 */     if (toBeContained.yMin < this.yMin) {
/* 277 */       this.yMin = toBeContained.yMin;
/*     */     }
/*     */     
/* 280 */     if (toBeContained.zMin < this.zMin) {
/* 281 */       this.zMin = toBeContained.zMin;
/*     */     }
/*     */     
/* 284 */     if (toBeContained.xMax > this.xMax) {
/* 285 */       this.xMax = toBeContained.xMax;
/*     */     }
/*     */     
/* 288 */     if (toBeContained.yMax > this.yMax) {
/* 289 */       this.yMax = toBeContained.yMax;
/*     */     }
/*     */     
/* 292 */     if (toBeContained.zMax > this.zMax) {
/* 293 */       this.zMax = toBeContained.zMax;
/*     */     }
/*     */     
/* 296 */     return this;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getBoundingBox() {
/* 300 */     return AxisAlignedBB.func_72330_a(this.xMin, this.yMin, this.zMin, this.xMax, this.yMax, this.zMax);
/*     */   }
/*     */ 
/*     */   
/*     */   public Box extendToEncompass(Position toBeContained) {
/* 305 */     if (toBeContained.x < this.xMin) {
/* 306 */       this.xMin = (int)toBeContained.x - 1;
/*     */     }
/*     */     
/* 309 */     if (toBeContained.y < this.yMin) {
/* 310 */       this.yMin = (int)toBeContained.y - 1;
/*     */     }
/*     */     
/* 313 */     if (toBeContained.z < this.zMin) {
/* 314 */       this.zMin = (int)toBeContained.z - 1;
/*     */     }
/*     */     
/* 317 */     if (toBeContained.x > this.xMax) {
/* 318 */       this.xMax = (int)toBeContained.x + 1;
/*     */     }
/*     */     
/* 321 */     if (toBeContained.y > this.yMax) {
/* 322 */       this.yMax = (int)toBeContained.y + 1;
/*     */     }
/*     */     
/* 325 */     if (toBeContained.z > this.zMax) {
/* 326 */       this.zMax = (int)toBeContained.z + 1;
/*     */     }
/*     */     
/* 329 */     return this;
/*     */   }
/*     */   
/*     */   public Box extendToEncompass(BlockIndex toBeContained) {
/* 333 */     if (toBeContained.x < this.xMin) {
/* 334 */       this.xMin = toBeContained.x - 1;
/*     */     }
/*     */     
/* 337 */     if (toBeContained.y < this.yMin) {
/* 338 */       this.yMin = toBeContained.y - 1;
/*     */     }
/*     */     
/* 341 */     if (toBeContained.z < this.zMin) {
/* 342 */       this.zMin = toBeContained.z - 1;
/*     */     }
/*     */     
/* 345 */     if (toBeContained.x > this.xMax) {
/* 346 */       this.xMax = toBeContained.x + 1;
/*     */     }
/*     */     
/* 349 */     if (toBeContained.y > this.yMax) {
/* 350 */       this.yMax = toBeContained.y + 1;
/*     */     }
/*     */     
/* 353 */     if (toBeContained.z > this.zMax) {
/* 354 */       this.zMax = toBeContained.z + 1;
/*     */     }
/*     */     
/* 357 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public double distanceTo(BlockIndex index) {
/* 362 */     return Math.sqrt(distanceToSquared(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public double distanceToSquared(BlockIndex index) {
/* 367 */     int dx = index.x - this.xMin + this.xMax - this.xMin + 1;
/* 368 */     int dy = index.y - this.yMin + this.yMax - this.yMin + 1;
/* 369 */     int dz = index.z - this.zMin + this.zMax - this.zMin + 1;
/*     */     
/* 371 */     return (dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockIndex getRandomBlockIndex(Random rand) {
/* 376 */     int x = (this.xMax > this.xMin) ? (this.xMin + rand.nextInt(this.xMax - this.xMin + 1)) : this.xMin;
/* 377 */     int y = (this.yMax > this.yMin) ? (this.yMin + rand.nextInt(this.yMax - this.yMin + 1)) : this.yMin;
/* 378 */     int z = (this.zMax > this.zMin) ? (this.zMin + rand.nextInt(this.zMax - this.zMin + 1)) : this.zMin;
/*     */     
/* 380 */     return new BlockIndex(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 386 */     byte flags = stream.readByte();
/* 387 */     this.xMin = stream.readInt();
/* 388 */     this.yMin = stream.readShort();
/* 389 */     this.zMin = stream.readInt();
/* 390 */     this.xMax = stream.readInt();
/* 391 */     this.yMax = stream.readShort();
/* 392 */     this.zMax = stream.readInt();
/*     */     
/* 394 */     this.kind = Kind.values()[flags & 0x1F];
/* 395 */     this.initialized = ((flags & 0x40) != 0);
/* 396 */     this.isVisible = ((flags & 0x20) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 401 */     stream.writeByte((this.initialized ? 64 : 0) | (this.isVisible ? 32 : 0) | this.kind.ordinal());
/* 402 */     stream.writeInt(this.xMin);
/* 403 */     stream.writeShort(this.yMin);
/* 404 */     stream.writeInt(this.zMin);
/* 405 */     stream.writeInt(this.xMax);
/* 406 */     stream.writeShort(this.yMax);
/* 407 */     stream.writeInt(this.zMax);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\Box.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */