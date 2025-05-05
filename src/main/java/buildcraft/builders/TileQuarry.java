/*     */ package buildcraft.builders;
/*     */ 
/*     */ import buildcraft.BuildCraftBuilders;
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.BuildCraftAPI;
/*     */ import buildcraft.api.core.IAreaProvider;
/*     */ import buildcraft.api.core.SafeTimeTracker;
/*     */ import buildcraft.api.filler.FillerManager;
/*     */ import buildcraft.api.tiles.IControllable;
/*     */ import buildcraft.api.tiles.IHasWork;
/*     */ import buildcraft.api.transport.IPipeConnection;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.Box;
/*     */ import buildcraft.core.DefaultAreaProvider;
/*     */ import buildcraft.core.DefaultProps;
/*     */ import buildcraft.core.blueprints.Blueprint;
/*     */ import buildcraft.core.blueprints.BptBuilderBase;
/*     */ import buildcraft.core.blueprints.BptBuilderBlueprint;
/*     */ import buildcraft.core.builders.IBuildingItemsProvider;
/*     */ import buildcraft.core.builders.TileAbstractBuilder;
/*     */ import buildcraft.core.builders.patterns.FillerPattern;
/*     */ import buildcraft.core.internal.IDropControlInventory;
/*     */ import buildcraft.core.internal.ILEDProvider;
/*     */ import buildcraft.core.lib.RFBattery;
/*     */ import buildcraft.core.lib.utils.BlockMiner;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraftforge.common.ForgeChunkManager;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
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
/*     */ public class TileQuarry
/*     */   extends TileAbstractBuilder
/*     */   implements IHasWork, ISidedInventory, IDropControlInventory, IPipeConnection, IControllable, ILEDProvider
/*     */ {
/*     */   public EntityMechanicalArm arm;
/*     */   public EntityPlayer placedBy;
/*     */   
/*     */   private enum Stage
/*     */   {
/*  70 */     BUILDING,
/*  71 */     DIGGING,
/*  72 */     MOVING,
/*  73 */     IDLE,
/*  74 */     DONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   protected Box box = new Box(); private int targetX;
/*     */   private int targetY;
/*     */   private int targetZ;
/*  83 */   private double speed = 0.03D; private double headPosX; private double headPosY; private double headPosZ;
/*  84 */   private Stage stage = Stage.BUILDING;
/*     */   
/*     */   private boolean movingHorizontally;
/*     */   private boolean movingVertically;
/*     */   private float headTrajectory;
/*  89 */   private SafeTimeTracker updateTracker = new SafeTimeTracker(BuildCraftCore.updateFactor);
/*     */   
/*     */   private BptBuilderBase builder;
/*     */   
/*  93 */   private final LinkedList<int[]> visitList = Lists.newLinkedList();
/*     */   
/*     */   private boolean loadDefaultBoundaries = false;
/*     */   
/*     */   private ForgeChunkManager.Ticket chunkTicket;
/*     */   
/*     */   private boolean frameProducer = true;
/* 100 */   private NBTTagCompound initNBT = null;
/*     */   
/*     */   private BlockMiner miner;
/*     */   private int ledState;
/*     */   
/*     */   public TileQuarry() {
/* 106 */     this.box.kind = Box.Kind.STRIPES;
/* 107 */     setBattery(new RFBattery((int)(20480.0F * BuildCraftCore.miningMultiplier), (int)(1000.0F * BuildCraftCore.miningMultiplier), 0));
/*     */   }
/*     */   
/*     */   public void createUtilsIfNeeded() {
/* 111 */     if (!this.field_145850_b.field_72995_K && 
/* 112 */       this.builder == null) {
/* 113 */       if (!this.box.isInitialized()) {
/* 114 */         setBoundaries(this.loadDefaultBoundaries);
/*     */       }
/*     */       
/* 117 */       initializeBlueprintBuilder();
/*     */     } 
/*     */ 
/*     */     
/* 121 */     if (this.stage != Stage.BUILDING) {
/* 122 */       this.box.isVisible = false;
/*     */       
/* 124 */       if (this.arm == null) {
/* 125 */         createArm();
/*     */       }
/*     */       
/* 128 */       if (findTarget(false) && (
/* 129 */         this.headPosX < this.box.xMin || this.headPosX > this.box.xMax || this.headPosZ < this.box.zMin || this.headPosZ > this.box.zMax)) {
/* 130 */         setHead((this.box.xMin + 1), (this.field_145848_d + 2), (this.box.zMin + 1));
/*     */       }
/*     */     } else {
/*     */       
/* 134 */       this.box.isVisible = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createArm() {
/* 139 */     this.field_145850_b
/* 140 */       .func_72838_d(new EntityMechanicalArm(this.field_145850_b, (this.box.xMin + 0.75F), ((this.field_145848_d + this.box
/*     */           
/* 142 */           .sizeY() - 1) + 0.25F), (this.box.zMin + 0.75F), ((this.box
/*     */           
/* 144 */           .sizeX() - 2) + 0.5F), ((this.box
/* 145 */           .sizeZ() - 2) + 0.5F), this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setArm(EntityMechanicalArm arm) {
/* 151 */     this.arm = arm;
/*     */   }
/*     */   
/*     */   public boolean areChunksLoaded() {
/* 155 */     if (!BuildCraftBuilders.quarryLoadsChunks)
/*     */     {
/* 157 */       for (int chunkX = this.box.xMin >> 4; chunkX <= this.box.xMax >> 4; chunkX++) {
/* 158 */         for (int chunkZ = this.box.zMin >> 4; chunkZ <= this.box.zMax >> 4; chunkZ++) {
/* 159 */           if (!this.field_145850_b.func_72899_e(chunkX << 4, this.box.yMax, chunkZ << 4)) {
/* 160 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 171 */     super.func_145845_h();
/*     */     
/* 173 */     if (this.field_145850_b.field_72995_K) {
/* 174 */       if (this.stage != Stage.DONE) {
/* 175 */         moveHead(this.speed);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 181 */     if (this.stage == Stage.DONE) {
/* 182 */       if (this.mode == IControllable.Mode.Loop) {
/* 183 */         this.stage = Stage.IDLE;
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */     }
/*     */     
/* 189 */     if (!areChunksLoaded()) {
/*     */       return;
/*     */     }
/*     */     
/* 193 */     if (this.mode == IControllable.Mode.Off && this.stage != Stage.MOVING) {
/*     */       return;
/*     */     }
/*     */     
/* 197 */     createUtilsIfNeeded();
/*     */     
/* 199 */     if (this.stage == Stage.BUILDING)
/* 200 */     { if (this.builder != null && !this.builder.isDone((IBuildingItemsProvider)this)) {
/* 201 */         this.builder.buildNextSlot(this.field_145850_b, this, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */       } else {
/* 203 */         this.stage = Stage.IDLE;
/*     */       }  }
/* 205 */     else if (this.stage == Stage.DIGGING)
/* 206 */     { dig(); }
/* 207 */     else { if (this.stage == Stage.IDLE) {
/* 208 */         idling();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 213 */       if (this.stage == Stage.MOVING) {
/* 214 */         int energyUsed = getBattery().useEnergy(20, (int)Math.ceil(20.0D + getBattery().getEnergyStored() / 10.0D), false);
/*     */         
/* 216 */         if (energyUsed >= 20) {
/*     */           
/* 218 */           this.speed = 0.1D + (energyUsed / 2000.0F);
/*     */ 
/*     */           
/* 221 */           if (this.field_145850_b.func_72896_J()) {
/* 222 */             int headBPX = (int)this.headPosX;
/* 223 */             int headBPY = (int)this.headPosY;
/* 224 */             int headBPZ = (int)this.headPosZ;
/* 225 */             if (this.field_145850_b.func_72976_f(headBPX, headBPZ) < headBPY) {
/* 226 */               this.speed *= 0.7D;
/*     */             }
/*     */           } 
/*     */           
/* 230 */           moveHead(this.speed);
/*     */         } else {
/* 232 */           this.speed = 0.0D;
/*     */         } 
/*     */       }  }
/*     */     
/* 236 */     if (this.updateTracker.markTimeIfDelay(this.field_145850_b)) {
/* 237 */       sendNetworkUpdate();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void dig() {
/* 242 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 246 */     if (this.miner == null) {
/*     */       
/* 248 */       this.stage = Stage.IDLE;
/*     */       
/*     */       return;
/*     */     } 
/* 252 */     int rfTaken = this.miner.acceptEnergy(getBattery().getEnergyStored());
/* 253 */     getBattery().useEnergy(rfTaken, rfTaken, false);
/*     */     
/* 255 */     if (this.miner.hasMined()) {
/*     */       
/* 257 */       double[] head = getHead();
/* 258 */       AxisAlignedBB axis = AxisAlignedBB.func_72330_a(head[0] - 2.0D, head[1] - 2.0D, head[2] - 2.0D, head[0] + 3.0D, head[1] + 3.0D, head[2] + 3.0D);
/* 259 */       List<EntityItem> result = this.field_145850_b.func_72872_a(EntityItem.class, axis);
/* 260 */       for (EntityItem entity : result) {
/* 261 */         if (entity.field_70128_L) {
/*     */           continue;
/*     */         }
/*     */         
/* 265 */         ItemStack mineable = entity.func_92059_d();
/* 266 */         if (mineable.field_77994_a <= 0) {
/*     */           continue;
/*     */         }
/* 269 */         CoreProxy.proxy.removeEntity((Entity)entity);
/* 270 */         this.miner.mineStack(mineable);
/*     */       } 
/*     */     } 
/*     */     
/* 274 */     if (this.miner.hasMined() || this.miner.hasFailed()) {
/* 275 */       this.miner = null;
/*     */       
/* 277 */       if (!findFrame()) {
/* 278 */         initializeBlueprintBuilder();
/* 279 */         this.stage = Stage.BUILDING;
/*     */       } else {
/* 281 */         this.stage = Stage.IDLE;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean findFrame() {
/* 287 */     for (int i = 2; i < 6; i++) {
/* 288 */       ForgeDirection o = ForgeDirection.getOrientation(i);
/* 289 */       if (this.box.contains(this.field_145851_c + o.offsetX, this.field_145848_d + o.offsetY, this.field_145849_e + o.offsetZ)) {
/* 290 */         return (this.field_145850_b.func_147439_a(this.field_145851_c + o.offsetX, this.field_145848_d + o.offsetY, this.field_145849_e + o.offsetZ) == BuildCraftBuilders.frameBlock);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 296 */     return true;
/*     */   }
/*     */   
/*     */   protected void idling() {
/* 300 */     if (!findTarget(true)) {
/*     */       
/* 302 */       if (this.arm != null && this.box != null) {
/* 303 */         setTarget(this.box.xMin + 1, this.field_145848_d + 2, this.box.zMin + 1);
/*     */       }
/*     */       
/* 306 */       this.stage = Stage.DONE;
/*     */     } else {
/* 308 */       this.stage = Stage.MOVING;
/*     */     } 
/*     */     
/* 311 */     this.movingHorizontally = true;
/* 312 */     this.movingVertically = true;
/* 313 */     double[] head = getHead();
/* 314 */     int[] target = getTarget();
/* 315 */     this.headTrajectory = (float)Math.atan2(target[2] - head[2], target[0] - head[0]);
/* 316 */     sendNetworkUpdate();
/*     */   }
/*     */   
/*     */   public boolean findTarget(boolean doSet) {
/* 320 */     if (this.field_145850_b.field_72995_K) {
/* 321 */       return false;
/*     */     }
/*     */     
/* 324 */     boolean columnVisitListIsUpdated = false;
/*     */     
/* 326 */     if (this.visitList.isEmpty()) {
/* 327 */       createColumnVisitList();
/* 328 */       columnVisitListIsUpdated = true;
/*     */     } 
/*     */     
/* 331 */     if (!doSet) {
/* 332 */       return !this.visitList.isEmpty();
/*     */     }
/*     */     
/* 335 */     if (this.visitList.isEmpty()) {
/* 336 */       return false;
/*     */     }
/*     */     
/* 339 */     int[] nextTarget = this.visitList.removeFirst();
/*     */     
/* 341 */     if (!columnVisitListIsUpdated) {
/* 342 */       for (int y = nextTarget[1] + 1; y < this.field_145848_d + 3; y++) {
/* 343 */         if (isQuarriableBlock(nextTarget[0], y, nextTarget[2])) {
/* 344 */           createColumnVisitList();
/* 345 */           columnVisitListIsUpdated = true;
/* 346 */           nextTarget = null;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 352 */     if (columnVisitListIsUpdated && nextTarget == null && !this.visitList.isEmpty()) {
/* 353 */       nextTarget = this.visitList.removeFirst();
/* 354 */     } else if (columnVisitListIsUpdated && nextTarget == null) {
/* 355 */       return false;
/*     */     } 
/*     */     
/* 358 */     setTarget(nextTarget[0], nextTarget[1] + 1, nextTarget[2]);
/*     */     
/* 360 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createColumnVisitList() {
/* 367 */     this.visitList.clear();
/* 368 */     boolean[][] blockedColumns = new boolean[this.builder.blueprint.sizeX - 2][this.builder.blueprint.sizeZ - 2];
/*     */     
/* 370 */     for (int searchY = this.field_145848_d + 3; searchY >= 1; searchY--) {
/*     */       int startX; int endX;
/*     */       int incX;
/* 373 */       if (searchY % 2 == 0) {
/* 374 */         startX = 0;
/* 375 */         endX = this.builder.blueprint.sizeX - 2;
/* 376 */         incX = 1;
/*     */       } else {
/* 378 */         startX = this.builder.blueprint.sizeX - 3;
/* 379 */         endX = -1;
/* 380 */         incX = -1;
/*     */       } 
/*     */       int searchX;
/* 383 */       for (searchX = startX; searchX != endX; searchX += incX) {
/*     */         int startZ; int endZ;
/*     */         int incZ;
/* 386 */         if (searchX % 2 == searchY % 2) {
/* 387 */           startZ = 0;
/* 388 */           endZ = this.builder.blueprint.sizeZ - 2;
/* 389 */           incZ = 1;
/*     */         } else {
/* 391 */           startZ = this.builder.blueprint.sizeZ - 3;
/* 392 */           endZ = -1;
/* 393 */           incZ = -1;
/*     */         } 
/*     */         int searchZ;
/* 396 */         for (searchZ = startZ; searchZ != endZ; searchZ += incZ) {
/* 397 */           if (!blockedColumns[searchX][searchZ]) {
/* 398 */             int bx = this.box.xMin + searchX + 1, by = searchY, bz = this.box.zMin + searchZ + 1;
/*     */             
/* 400 */             Block block = this.field_145850_b.func_147439_a(bx, by, bz);
/*     */             
/* 402 */             if (!BlockUtils.canChangeBlock(block, this.field_145850_b, bx, by, bz)) {
/* 403 */               blockedColumns[searchX][searchZ] = true;
/* 404 */             } else if (!BuildCraftAPI.isSoftBlock(this.field_145850_b, bx, by, bz) && !(block instanceof net.minecraft.block.BlockLiquid) && !(block instanceof net.minecraftforge.fluids.IFluidBlock)) {
/* 405 */               this.visitList.add(new int[] { bx, by, bz });
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 410 */             if (this.visitList.size() > this.builder.blueprint.sizeZ * this.builder.blueprint.sizeX * 2) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 421 */     super.func_145839_a(nbttagcompound);
/*     */     
/* 423 */     if (nbttagcompound.func_74764_b("box")) {
/* 424 */       this.box.initialize(nbttagcompound.func_74775_l("box"));
/*     */       
/* 426 */       this.loadDefaultBoundaries = false;
/* 427 */     } else if (nbttagcompound.func_74764_b("xSize")) {
/*     */ 
/*     */       
/* 430 */       int xMin = nbttagcompound.func_74762_e("xMin");
/* 431 */       int zMin = nbttagcompound.func_74762_e("zMin");
/*     */       
/* 433 */       int xSize = nbttagcompound.func_74762_e("xSize");
/* 434 */       int ySize = nbttagcompound.func_74762_e("ySize");
/* 435 */       int zSize = nbttagcompound.func_74762_e("zSize");
/*     */       
/* 437 */       this.box.initialize(xMin, this.field_145848_d, zMin, xMin + xSize - 1, this.field_145848_d + ySize - 1, zMin + zSize - 1);
/*     */       
/* 439 */       this.loadDefaultBoundaries = false;
/*     */     }
/*     */     else {
/*     */       
/* 443 */       this.loadDefaultBoundaries = true;
/*     */     } 
/*     */     
/* 446 */     this.targetX = nbttagcompound.func_74762_e("targetX");
/* 447 */     this.targetY = nbttagcompound.func_74762_e("targetY");
/* 448 */     this.targetZ = nbttagcompound.func_74762_e("targetZ");
/* 449 */     this.headPosX = nbttagcompound.func_74769_h("headPosX");
/* 450 */     this.headPosY = nbttagcompound.func_74769_h("headPosY");
/* 451 */     this.headPosZ = nbttagcompound.func_74769_h("headPosZ");
/*     */ 
/*     */     
/* 454 */     this.initNBT = (NBTTagCompound)nbttagcompound.func_74775_l("bpt").func_74737_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 459 */     super.func_145841_b(nbttagcompound);
/*     */     
/* 461 */     nbttagcompound.func_74768_a("targetX", this.targetX);
/* 462 */     nbttagcompound.func_74768_a("targetY", this.targetY);
/* 463 */     nbttagcompound.func_74768_a("targetZ", this.targetZ);
/* 464 */     nbttagcompound.func_74780_a("headPosX", this.headPosX);
/* 465 */     nbttagcompound.func_74780_a("headPosY", this.headPosY);
/* 466 */     nbttagcompound.func_74780_a("headPosZ", this.headPosZ);
/*     */     
/* 468 */     NBTTagCompound boxTag = new NBTTagCompound();
/* 469 */     this.box.writeToNBT(boxTag);
/* 470 */     nbttagcompound.func_74782_a("box", (NBTBase)boxTag);
/*     */     
/* 472 */     NBTTagCompound bptNBT = new NBTTagCompound();
/*     */     
/* 474 */     if (this.builder != null) {
/* 475 */       NBTTagCompound builderCpt = new NBTTagCompound();
/* 476 */       this.builder.saveBuildStateToNBT(builderCpt, (IBuildingItemsProvider)this);
/* 477 */       bptNBT.func_74782_a("builderState", (NBTBase)builderCpt);
/*     */     } 
/*     */     
/* 480 */     nbttagcompound.func_74782_a("bpt", (NBTBase)bptNBT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void positionReached() {
/* 485 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 489 */     if (isQuarriableBlock(this.targetX, this.targetY - 1, this.targetZ)) {
/* 490 */       this.miner = new BlockMiner(this.field_145850_b, (TileEntity)this, this.targetX, this.targetY - 1, this.targetZ);
/* 491 */       this.stage = Stage.DIGGING;
/*     */     } else {
/* 493 */       this.stage = Stage.IDLE;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isQuarriableBlock(int bx, int by, int bz) {
/* 498 */     Block block = this.field_145850_b.func_147439_a(bx, by, bz);
/* 499 */     return (BlockUtils.canChangeBlock(block, this.field_145850_b, bx, by, bz) && 
/* 500 */       !BuildCraftAPI.isSoftBlock(this.field_145850_b, bx, by, bz) && !(block instanceof net.minecraft.block.BlockLiquid) && !(block instanceof net.minecraftforge.fluids.IFluidBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getNetworkUpdateRange() {
/* 506 */     return DefaultProps.NETWORK_UPDATE_RANGE + (int)Math.ceil(Math.sqrt((this.field_145848_d * this.field_145848_d + this.box.sizeX() * this.box.sizeX() + this.box.sizeZ() * this.box.sizeZ())));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 511 */     if (this.chunkTicket != null) {
/* 512 */       ForgeChunkManager.releaseTicket(this.chunkTicket);
/*     */     }
/*     */     
/* 515 */     super.func_145843_s();
/* 516 */     destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onChunkUnload() {
/* 521 */     destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 526 */     if (this.arm != null) {
/* 527 */       this.arm.func_70106_y();
/*     */     }
/*     */     
/* 530 */     this.arm = null;
/*     */     
/* 532 */     this.frameProducer = false;
/*     */     
/* 534 */     if (this.miner != null) {
/* 535 */       this.miner.invalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWork() {
/* 541 */     return (this.stage != Stage.DONE);
/*     */   }
/*     */   private void setBoundaries(boolean useDefaultI) {
/*     */     DefaultAreaProvider defaultAreaProvider;
/* 545 */     boolean useDefault = useDefaultI;
/*     */     
/* 547 */     if (BuildCraftBuilders.quarryLoadsChunks && this.chunkTicket == null) {
/* 548 */       this.chunkTicket = ForgeChunkManager.requestTicket(BuildCraftBuilders.instance, this.field_145850_b, ForgeChunkManager.Type.NORMAL);
/*     */     }
/*     */     
/* 551 */     if (this.chunkTicket != null) {
/* 552 */       this.chunkTicket.getModData().func_74768_a("quarryX", this.field_145851_c);
/* 553 */       this.chunkTicket.getModData().func_74768_a("quarryY", this.field_145848_d);
/* 554 */       this.chunkTicket.getModData().func_74768_a("quarryZ", this.field_145849_e);
/*     */     } 
/*     */     
/* 557 */     IAreaProvider a = null;
/*     */     
/* 559 */     if (!useDefault) {
/* 560 */       a = Utils.getNearbyAreaProvider(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/*     */     
/* 563 */     if (a == null) {
/* 564 */       defaultAreaProvider = new DefaultAreaProvider(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c + 10, this.field_145848_d + 4, this.field_145849_e + 10);
/*     */       
/* 566 */       useDefault = true;
/*     */     } 
/*     */     
/* 569 */     int xSize = defaultAreaProvider.xMax() - defaultAreaProvider.xMin() + 1;
/* 570 */     int zSize = defaultAreaProvider.zMax() - defaultAreaProvider.zMin() + 1;
/*     */     
/* 572 */     if (xSize < 3 || zSize < 3 || (this.chunkTicket != null && xSize * zSize >> 8 >= this.chunkTicket.getMaxChunkListDepth())) {
/* 573 */       if (this.placedBy != null) {
/* 574 */         this.placedBy.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.buildcraft.quarry.tooSmall", new Object[] {
/* 575 */                 Integer.valueOf(xSize), Integer.valueOf(zSize), 
/* 576 */                 Integer.valueOf((this.chunkTicket != null) ? this.chunkTicket.getMaxChunkListDepth() : 0)
/*     */               }));
/*     */       }
/* 579 */       defaultAreaProvider = new DefaultAreaProvider(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c + 10, this.field_145848_d + 4, this.field_145849_e + 10);
/* 580 */       useDefault = true;
/*     */     } 
/*     */     
/* 583 */     xSize = defaultAreaProvider.xMax() - defaultAreaProvider.xMin() + 1;
/* 584 */     int ySize = defaultAreaProvider.yMax() - defaultAreaProvider.yMin() + 1;
/* 585 */     zSize = defaultAreaProvider.zMax() - defaultAreaProvider.zMin() + 1;
/*     */     
/* 587 */     this.box.initialize((IAreaProvider)defaultAreaProvider);
/*     */     
/* 589 */     if (ySize < 5) {
/* 590 */       ySize = 5;
/* 591 */       this.box.yMax = this.box.yMin + ySize - 1;
/*     */     } 
/*     */     
/* 594 */     if (useDefault) {
/*     */ 
/*     */       
/* 597 */       int xMin, zMin, dir = this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 598 */       ForgeDirection o = ForgeDirection.getOrientation((dir > 6) ? 6 : dir).getOpposite();
/*     */       
/* 600 */       switch (o) {
/*     */         case EAST:
/* 602 */           xMin = this.field_145851_c + 1;
/* 603 */           zMin = this.field_145849_e - 4 - 1;
/*     */           break;
/*     */         case WEST:
/* 606 */           xMin = this.field_145851_c - 9 - 2;
/* 607 */           zMin = this.field_145849_e - 4 - 1;
/*     */           break;
/*     */         case SOUTH:
/* 610 */           xMin = this.field_145851_c - 4 - 1;
/* 611 */           zMin = this.field_145849_e + 1;
/*     */           break;
/*     */         
/*     */         default:
/* 615 */           xMin = this.field_145851_c - 4 - 1;
/* 616 */           zMin = this.field_145849_e - 9 - 2;
/*     */           break;
/*     */       } 
/*     */       
/* 620 */       this.box.initialize(xMin, this.field_145848_d, zMin, xMin + xSize - 1, this.field_145848_d + ySize - 1, zMin + zSize - 1);
/*     */     } 
/*     */     
/* 623 */     defaultAreaProvider.removeFromWorld();
/* 624 */     if (this.chunkTicket != null) {
/* 625 */       forceChunkLoading(this.chunkTicket);
/*     */     }
/*     */     
/* 628 */     sendNetworkUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initializeBlueprintBuilder() {
/* 633 */     Blueprint bpt = ((FillerPattern)FillerManager.registry.getPattern("buildcraft:frame")).getBlueprint(this.box, this.field_145850_b, new buildcraft.api.statements.IStatementParameter[0], BuildCraftBuilders.frameBlock, 0);
/*     */     
/* 635 */     if (bpt != null) {
/* 636 */       this.builder = (BptBuilderBase)new BptBuilderBlueprint(bpt, this.field_145850_b, this.box.xMin, this.field_145848_d, this.box.zMin);
/* 637 */       this.speed = 0.0D;
/* 638 */       this.stage = Stage.BUILDING;
/* 639 */       sendNetworkUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 645 */     super.writeData(stream);
/* 646 */     this.box.writeData(stream);
/* 647 */     stream.writeInt(this.targetX);
/* 648 */     stream.writeShort(this.targetY);
/* 649 */     stream.writeInt(this.targetZ);
/* 650 */     stream.writeDouble(this.headPosX);
/* 651 */     stream.writeDouble(this.headPosY);
/* 652 */     stream.writeDouble(this.headPosZ);
/* 653 */     stream.writeFloat((float)this.speed);
/* 654 */     stream.writeFloat(this.headTrajectory);
/* 655 */     int flags = this.stage.ordinal();
/* 656 */     flags |= this.movingHorizontally ? 16 : 0;
/* 657 */     flags |= this.movingVertically ? 32 : 0;
/* 658 */     stream.writeByte(flags);
/*     */     
/* 660 */     this.ledState = ((hasWork() && this.mode != IControllable.Mode.Off && getTicksSinceEnergyReceived() < 12) ? 16 : 0) | getBattery().getEnergyStored() * 15 / getBattery().getMaxEnergyStored();
/* 661 */     stream.writeByte(this.ledState);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 666 */     super.readData(stream);
/* 667 */     this.box.readData(stream);
/* 668 */     this.targetX = stream.readInt();
/* 669 */     this.targetY = stream.readUnsignedShort();
/* 670 */     this.targetZ = stream.readInt();
/* 671 */     this.headPosX = stream.readDouble();
/* 672 */     this.headPosY = stream.readDouble();
/* 673 */     this.headPosZ = stream.readDouble();
/* 674 */     this.speed = stream.readFloat();
/* 675 */     this.headTrajectory = stream.readFloat();
/* 676 */     int flags = stream.readUnsignedByte();
/* 677 */     this.stage = Stage.values()[flags & 0x7];
/* 678 */     this.movingHorizontally = ((flags & 0x10) != 0);
/* 679 */     this.movingVertically = ((flags & 0x20) != 0);
/* 680 */     int newLedState = stream.readUnsignedByte();
/* 681 */     if (newLedState != this.ledState) {
/* 682 */       this.ledState = newLedState;
/* 683 */       this.field_145850_b.func_147458_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     } 
/*     */     
/* 686 */     createUtilsIfNeeded();
/*     */     
/* 688 */     if (this.arm != null) {
/* 689 */       this.arm.setHead(this.headPosX, this.headPosY, this.headPosZ);
/* 690 */       this.arm.updatePosition();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/* 696 */     super.initialize();
/*     */     
/* 698 */     if (!(func_145831_w()).field_72995_K && !this.box.initialized) {
/* 699 */       setBoundaries(false);
/*     */     }
/*     */     
/* 702 */     createUtilsIfNeeded();
/*     */     
/* 704 */     if (this.initNBT != null && this.builder != null) {
/* 705 */       this.builder.loadBuildStateToNBT(this.initNBT
/* 706 */           .func_74775_l("builderState"), (IBuildingItemsProvider)this);
/*     */     }
/*     */     
/* 709 */     this.initNBT = null;
/*     */     
/* 711 */     sendNetworkUpdate();
/*     */   }
/*     */   
/*     */   public void reinitalize() {
/* 715 */     initializeBlueprintBuilder();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 720 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/* 725 */     if (this.frameProducer) {
/* 726 */       return new ItemStack(BuildCraftBuilders.frameBlock);
/*     */     }
/* 728 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int j) {
/* 734 */     if (this.frameProducer) {
/* 735 */       return new ItemStack(BuildCraftBuilders.frameBlock, j);
/*     */     }
/* 737 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slot) {
/* 747 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 752 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 757 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 762 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/* 767 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70305_f() {}
/*     */ 
/*     */   
/*     */   public boolean isBuildingMaterialSlot(int i) {
/* 780 */     return true;
/*     */   }
/*     */   
/*     */   public void moveHead(double instantSpeed) {
/* 784 */     int[] target = getTarget();
/* 785 */     double[] head = getHead();
/*     */     
/* 787 */     if (this.movingHorizontally) {
/* 788 */       if (Math.abs(target[0] - head[0]) < instantSpeed * 2.0D && Math.abs(target[2] - head[2]) < instantSpeed * 2.0D) {
/* 789 */         head[0] = target[0];
/* 790 */         head[2] = target[2];
/*     */         
/* 792 */         this.movingHorizontally = false;
/*     */         
/* 794 */         if (!this.movingVertically) {
/* 795 */           positionReached();
/* 796 */           head[1] = target[1];
/*     */         } 
/*     */       } else {
/* 799 */         head[0] = head[0] + MathHelper.func_76134_b(this.headTrajectory) * instantSpeed;
/* 800 */         head[2] = head[2] + MathHelper.func_76126_a(this.headTrajectory) * instantSpeed;
/*     */       } 
/* 802 */       setHead(head[0], head[1], head[2]);
/*     */     } 
/*     */     
/* 805 */     if (this.movingVertically) {
/* 806 */       if (Math.abs(target[1] - head[1]) < instantSpeed * 2.0D) {
/* 807 */         head[1] = target[1];
/*     */         
/* 809 */         this.movingVertically = false;
/* 810 */         if (!this.movingHorizontally) {
/* 811 */           positionReached();
/* 812 */           head[0] = target[0];
/* 813 */           head[2] = target[2];
/*     */         }
/*     */       
/* 816 */       } else if (target[1] > head[1]) {
/* 817 */         head[1] = head[1] + instantSpeed;
/*     */       } else {
/* 819 */         head[1] = head[1] - instantSpeed;
/*     */       } 
/*     */       
/* 822 */       setHead(head[0], head[1], head[2]);
/*     */     } 
/*     */     
/* 825 */     updatePosition();
/*     */   }
/*     */   
/*     */   private void updatePosition() {
/* 829 */     if (this.arm != null && this.field_145850_b.field_72995_K) {
/* 830 */       this.arm.setHead(this.headPosX, this.headPosY, this.headPosZ);
/* 831 */       this.arm.updatePosition();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setHead(double x, double y, double z) {
/* 836 */     this.headPosX = x;
/* 837 */     this.headPosY = y;
/* 838 */     this.headPosZ = z;
/*     */   }
/*     */   
/*     */   private double[] getHead() {
/* 842 */     return new double[] { this.headPosX, this.headPosY, this.headPosZ };
/*     */   }
/*     */   
/*     */   private int[] getTarget() {
/* 846 */     return new int[] { this.targetX, this.targetY, this.targetZ };
/*     */   }
/*     */   
/*     */   private void setTarget(int x, int y, int z) {
/* 850 */     this.targetX = x;
/* 851 */     this.targetY = y;
/* 852 */     this.targetZ = z;
/*     */   }
/*     */   
/*     */   public void forceChunkLoading(ForgeChunkManager.Ticket ticket) {
/* 856 */     if (this.chunkTicket == null) {
/* 857 */       this.chunkTicket = ticket;
/*     */     }
/*     */     
/* 860 */     Set<ChunkCoordIntPair> chunks = Sets.newHashSet();
/* 861 */     ChunkCoordIntPair quarryChunk = new ChunkCoordIntPair(this.field_145851_c >> 4, this.field_145849_e >> 4);
/* 862 */     chunks.add(quarryChunk);
/* 863 */     ForgeChunkManager.forceChunk(ticket, quarryChunk);
/*     */     
/* 865 */     for (int chunkX = this.box.xMin >> 4; chunkX <= this.box.xMax >> 4; chunkX++) {
/* 866 */       for (int chunkZ = this.box.zMin >> 4; chunkZ <= this.box.zMax >> 4; chunkZ++) {
/* 867 */         ChunkCoordIntPair chunk = new ChunkCoordIntPair(chunkX, chunkZ);
/* 868 */         ForgeChunkManager.forceChunk(ticket, chunk);
/* 869 */         chunks.add(chunk);
/*     */       } 
/*     */     } 
/*     */     
/* 873 */     if (this.placedBy != null && !(this.placedBy instanceof net.minecraftforge.common.util.FakePlayer)) {
/* 874 */       this.placedBy.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.buildcraft.quarry.chunkloadInfo", new Object[] {
/*     */               
/* 876 */               Integer.valueOf(this.field_145851_c), Integer.valueOf(this.field_145848_d), Integer.valueOf(this.field_145849_e), Integer.valueOf(chunks.size())
/*     */             }));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean func_145818_k_() {
/* 882 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getRenderBoundingBox() {
/* 887 */     return (new Box((TileEntity)this)).extendToEncompass(this.box).expand(50).getBoundingBox();
/*     */   }
/*     */ 
/*     */   
/*     */   public Box getBox() {
/* 892 */     return this.box;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int side) {
/* 897 */     return new int[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int p1, ItemStack p2, int p3) {
/* 902 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int p1, ItemStack p2, int p3) {
/* 907 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsControlMode(IControllable.Mode mode) {
/* 912 */     return (mode == IControllable.Mode.Off || mode == IControllable.Mode.On || mode == IControllable.Mode.Loop);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doDrop() {
/* 917 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public IPipeConnection.ConnectOverride overridePipeConnection(IPipeTile.PipeType type, ForgeDirection with) {
/* 922 */     return (type == IPipeTile.PipeType.ITEM) ? IPipeConnection.ConnectOverride.CONNECT : IPipeConnection.ConnectOverride.DEFAULT;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLEDLevel(int led) {
/* 927 */     if (led == 0) {
/* 928 */       return this.ledState & 0xF;
/*     */     }
/* 930 */     return (this.ledState >> 4 > 0) ? 15 : 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\TileQuarry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */