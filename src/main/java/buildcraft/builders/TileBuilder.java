/*     */ package buildcraft.builders;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.IAreaProvider;
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import buildcraft.api.core.IPathProvider;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.api.core.SafeTimeTracker;
/*     */ import buildcraft.api.robots.IRequestProvider;
/*     */ import buildcraft.api.tiles.IControllable;
/*     */ import buildcraft.api.tiles.IHasWork;
/*     */ import buildcraft.builders.blueprints.RecursiveBlueprintBuilder;
/*     */ import buildcraft.core.Box;
/*     */ import buildcraft.core.LaserData;
/*     */ import buildcraft.core.blueprints.Blueprint;
/*     */ import buildcraft.core.blueprints.BlueprintBase;
/*     */ import buildcraft.core.blueprints.BptBuilderBase;
/*     */ import buildcraft.core.blueprints.BptBuilderBlueprint;
/*     */ import buildcraft.core.blueprints.BptBuilderTemplate;
/*     */ import buildcraft.core.blueprints.RequirementItemStack;
/*     */ import buildcraft.core.builders.IBuildingItemsProvider;
/*     */ import buildcraft.core.builders.TileAbstractBuilder;
/*     */ import buildcraft.core.lib.fluids.Tank;
/*     */ import buildcraft.core.lib.fluids.TankManager;
/*     */ import buildcraft.core.lib.inventory.ITransactor;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.lib.inventory.InventoryIterator;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.inventory.Transactor;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTankInfo;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
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
/*     */ public class TileBuilder
/*     */   extends TileAbstractBuilder
/*     */   implements IHasWork, IFluidHandler, IRequestProvider, IControllable
/*     */ {
/*  72 */   private static int POWER_ACTIVATION = 500;
/*     */   
/*  74 */   public Box box = new Box();
/*     */   public PathIterator currentPathIterator;
/*  76 */   public Tank[] fluidTanks = new Tank[] { new Tank("fluid1", 8000, (TileEntity)this), new Tank("fluid2", 8000, (TileEntity)this), new Tank("fluid3", 8000, (TileEntity)this), new Tank("fluid4", 8000, (TileEntity)this) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public TankManager<Tank> fluidTank = new TankManager(this.fluidTanks);
/*     */   
/*  84 */   private SafeTimeTracker networkUpdateTracker = new SafeTimeTracker((BuildCraftCore.updateFactor / 2));
/*     */   
/*     */   private boolean shouldUpdateRequirements;
/*  87 */   private SimpleInventory inv = new SimpleInventory(28, "Builder", 64);
/*     */   private BptBuilderBase currentBuilder;
/*     */   private RecursiveBlueprintBuilder recursiveBuilder;
/*     */   private List<BlockIndex> path;
/*     */   private ArrayList<RequirementItemStack> requiredToBuild;
/*  92 */   private NBTTagCompound initNBT = null;
/*     */   
/*     */   private boolean done = true;
/*     */   private boolean isBuilding = false;
/*     */   
/*     */   private class PathIterator
/*     */   {
/*     */     public Iterator<BlockIndex> currentIterator;
/*     */     public double cx;
/*     */     public double cy;
/*     */     public double cz;
/* 103 */     AxisAlignedBB oldBoundingBox = null; public float ix; public float iy; public float iz; public BlockIndex to; public double lastDistance;
/* 104 */     ForgeDirection o = null;
/*     */     
/*     */     public PathIterator(BlockIndex from, Iterator<BlockIndex> it, ForgeDirection initialDir) {
/* 107 */       this.to = it.next();
/*     */       
/* 109 */       this.currentIterator = it;
/*     */       
/* 111 */       double dx = (this.to.x - from.x);
/* 112 */       double dy = (this.to.y - from.y);
/* 113 */       double dz = (this.to.z - from.z);
/*     */       
/* 115 */       double size = Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */       
/* 117 */       this.cx = dx / size / 10.0D;
/* 118 */       this.cy = dy / size / 10.0D;
/* 119 */       this.cz = dz / size / 10.0D;
/*     */       
/* 121 */       this.ix = from.x;
/* 122 */       this.iy = from.y;
/* 123 */       this.iz = from.z;
/*     */       
/* 125 */       this.lastDistance = ((this.ix - this.to.x) * (this.ix - this.to.x) + (this.iy - this.to.y) * (this.iy - this.to.y) + (this.iz - this.to.z) * (this.iz - this.to.z));
/*     */ 
/*     */       
/* 128 */       if (dx == 0.0D && dz == 0.0D) {
/* 129 */         this.o = initialDir;
/* 130 */       } else if (Math.abs(dx) > Math.abs(dz)) {
/* 131 */         if (dx > 0.0D) {
/* 132 */           this.o = ForgeDirection.EAST;
/*     */         } else {
/* 134 */           this.o = ForgeDirection.WEST;
/*     */         }
/*     */       
/* 137 */       } else if (dz > 0.0D) {
/* 138 */         this.o = ForgeDirection.SOUTH;
/*     */       } else {
/* 140 */         this.o = ForgeDirection.NORTH;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BptBuilderBase next() {
/*     */       while (true) {
/* 152 */         int newX = Math.round(this.ix);
/* 153 */         int newY = Math.round(this.iy);
/* 154 */         int newZ = Math.round(this.iz);
/*     */         
/* 156 */         BptBuilderBase bpt = TileBuilder.this.instanciateBluePrintBuilder(newX, newY, newZ, this.o);
/*     */         
/* 158 */         if (bpt == null) {
/* 159 */           return null;
/*     */         }
/*     */         
/* 162 */         AxisAlignedBB boundingBox = bpt.getBoundingBox();
/*     */         
/* 164 */         if (this.oldBoundingBox == null || !collision(this.oldBoundingBox, boundingBox)) {
/* 165 */           this.oldBoundingBox = boundingBox;
/* 166 */           return bpt;
/*     */         } 
/*     */         
/* 169 */         this.ix = (float)(this.ix + this.cx);
/* 170 */         this.iy = (float)(this.iy + this.cy);
/* 171 */         this.iz = (float)(this.iz + this.cz);
/*     */         
/* 173 */         double distance = ((this.ix - this.to.x) * (this.ix - this.to.x) + (this.iy - this.to.y) * (this.iy - this.to.y) + (this.iz - this.to.z) * (this.iz - this.to.z));
/*     */ 
/*     */         
/* 176 */         if (distance > this.lastDistance) {
/* 177 */           return null;
/*     */         }
/* 179 */         this.lastDistance = distance;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public PathIterator iterate() {
/* 185 */       if (this.currentIterator.hasNext()) {
/* 186 */         PathIterator next = new PathIterator(this.to, this.currentIterator, this.o);
/* 187 */         next.oldBoundingBox = this.oldBoundingBox;
/*     */         
/* 189 */         return next;
/*     */       } 
/* 191 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean collision(AxisAlignedBB left, AxisAlignedBB right) {
/* 196 */       if (left.field_72336_d < right.field_72340_a || left.field_72340_a > right.field_72336_d) {
/* 197 */         return false;
/*     */       }
/* 199 */       if (left.field_72337_e < right.field_72338_b || left.field_72338_b > right.field_72337_e) {
/* 200 */         return false;
/*     */       }
/* 202 */       if (left.field_72334_f < right.field_72339_c || left.field_72339_c > right.field_72334_f) {
/* 203 */         return false;
/*     */       }
/* 205 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileBuilder() {
/* 212 */     this.box.kind = Box.Kind.STRIPES;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/* 217 */     super.initialize();
/*     */     
/* 219 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 223 */     if (this.initNBT != null) {
/* 224 */       iterateBpt(true);
/*     */       
/* 226 */       if (this.initNBT.func_74764_b("iterator")) {
/* 227 */         BlockIndex expectedTo = new BlockIndex(this.initNBT.func_74775_l("iterator"));
/*     */         
/* 229 */         while (!this.done && this.currentBuilder != null && this.currentPathIterator != null) {
/* 230 */           BlockIndex bi = new BlockIndex((int)this.currentPathIterator.ix, (int)this.currentPathIterator.iy, (int)this.currentPathIterator.iz);
/*     */ 
/*     */           
/* 233 */           if (bi.equals(expectedTo)) {
/*     */             break;
/*     */           }
/*     */           
/* 237 */           iterateBpt(true);
/*     */         } 
/*     */       } 
/*     */       
/* 241 */       if (this.currentBuilder != null) {
/* 242 */         this.currentBuilder.loadBuildStateToNBT(this.initNBT
/* 243 */             .func_74775_l("builderState"), (IBuildingItemsProvider)this);
/*     */       }
/*     */       
/* 246 */       this.initNBT = null;
/*     */     } 
/*     */     
/* 249 */     this.box.kind = Box.Kind.STRIPES;
/*     */     
/* 251 */     for (int x = this.field_145851_c - 1; x <= this.field_145851_c + 1; x++) {
/* 252 */       for (int y = this.field_145848_d - 1; y <= this.field_145848_d + 1; y++) {
/* 253 */         for (int z = this.field_145849_e - 1; z <= this.field_145849_e + 1; z++) {
/* 254 */           TileEntity tile = this.field_145850_b.func_147438_o(x, y, z);
/*     */           
/* 256 */           if (tile instanceof IPathProvider) {
/* 257 */             this.path = ((IPathProvider)tile).getPath();
/* 258 */             ((IPathProvider)tile).removeFromWorld();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 266 */     if (this.path != null && this.pathLasers.size() == 0) {
/* 267 */       createLasersForPath();
/* 268 */       sendNetworkUpdate();
/*     */     } 
/*     */     
/* 271 */     iterateBpt(false);
/*     */   }
/*     */   
/*     */   public void createLasersForPath() {
/* 275 */     this.pathLasers = new LinkedList();
/* 276 */     BlockIndex previous = null;
/*     */     
/* 278 */     for (BlockIndex b : this.path) {
/* 279 */       if (previous != null) {
/* 280 */         LaserData laser = new LaserData(new Position(previous.x + 0.5D, previous.y + 0.5D, previous.z + 0.5D), new Position(b.x + 0.5D, b.y + 0.5D, b.z + 0.5D));
/*     */ 
/*     */ 
/*     */         
/* 284 */         this.pathLasers.add(laser);
/*     */       } 
/*     */       
/* 287 */       previous = b;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlueprintBase instanciateBlueprint() {
/*     */     BlueprintBase bpt;
/*     */     try {
/* 295 */       bpt = ItemBlueprint.loadBlueprint(func_70301_a(0));
/* 296 */     } catch (Throwable t) {
/* 297 */       t.printStackTrace();
/* 298 */       return null;
/*     */     } 
/*     */     
/* 301 */     return bpt;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public BptBuilderBase instanciateBluePrintBuilder(int x, int y, int z, ForgeDirection o) {
/* 306 */     BlueprintBase bpt = instanciateBlueprint();
/* 307 */     if (bpt == null) {
/* 308 */       return null;
/*     */     }
/*     */     
/* 311 */     bpt = bpt.adjustToWorld(this.field_145850_b, x, y, z, o);
/*     */     
/* 313 */     if (bpt != null) {
/* 314 */       if (func_70301_a(0).func_77973_b() instanceof ItemBlueprintStandard)
/* 315 */         return (BptBuilderBase)new BptBuilderBlueprint((Blueprint)bpt, this.field_145850_b, x, y, z); 
/* 316 */       if (func_70301_a(0).func_77973_b() instanceof ItemBlueprintTemplate) {
/* 317 */         return (BptBuilderBase)new BptBuilderTemplate(bpt, this.field_145850_b, x, y, z);
/*     */       }
/*     */     } 
/* 320 */     return null;
/*     */   }
/*     */   
/*     */   public void iterateBpt(boolean forceIterate) {
/* 324 */     if ((func_70301_a(0) == null || !(func_70301_a(0).func_77973_b() instanceof ItemBlueprint)) && 
/* 325 */       this.box.isInitialized()) {
/* 326 */       if (this.currentBuilder != null) {
/* 327 */         this.currentBuilder = null;
/*     */       }
/*     */       
/* 330 */       if (this.box.isInitialized()) {
/* 331 */         this.box.reset();
/*     */       }
/*     */       
/* 334 */       if (this.currentPathIterator != null) {
/* 335 */         this.currentPathIterator = null;
/*     */       }
/*     */       
/* 338 */       scheduleRequirementUpdate();
/*     */       
/* 340 */       sendNetworkUpdate();
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 346 */     if (this.currentBuilder == null || this.currentBuilder.isDone((IBuildingItemsProvider)this) || forceIterate) {
/* 347 */       if (this.path != null && this.path.size() > 1) {
/* 348 */         if (this.currentPathIterator == null) {
/* 349 */           Iterator<BlockIndex> it = this.path.iterator();
/* 350 */           BlockIndex start = it.next();
/* 351 */           this
/*     */             
/* 353 */             .currentPathIterator = new PathIterator(start, it, ForgeDirection.values()[this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, this.field_145849_e)].getOpposite());
/*     */         } 
/*     */         
/* 356 */         if (this.currentBuilder != null && this.currentBuilder.isDone((IBuildingItemsProvider)this)) {
/* 357 */           this.currentBuilder.postProcessing(this.field_145850_b);
/*     */         }
/*     */         
/* 360 */         this.currentBuilder = this.currentPathIterator.next();
/*     */         
/* 362 */         if (this.currentBuilder != null) {
/* 363 */           this.box.reset();
/* 364 */           this.box.initialize((IAreaProvider)this.currentBuilder);
/* 365 */           sendNetworkUpdate();
/*     */         } 
/*     */         
/* 368 */         if (this.currentBuilder == null) {
/* 369 */           this.currentPathIterator = this.currentPathIterator.iterate();
/*     */         }
/*     */         
/* 372 */         if (this.currentPathIterator == null) {
/* 373 */           this.done = true;
/*     */         } else {
/* 375 */           this.done = false;
/*     */         } 
/*     */         
/* 378 */         scheduleRequirementUpdate();
/*     */       } else {
/* 380 */         if (this.currentBuilder != null && this.currentBuilder.isDone((IBuildingItemsProvider)this)) {
/* 381 */           this.currentBuilder.postProcessing(this.field_145850_b);
/* 382 */           this.currentBuilder = this.recursiveBuilder.nextBuilder();
/*     */           
/* 384 */           scheduleRequirementUpdate();
/*     */         } else {
/* 386 */           BlueprintBase bpt = instanciateBlueprint();
/*     */           
/* 388 */           if (bpt != null) {
/* 389 */             this
/* 390 */               .recursiveBuilder = new RecursiveBlueprintBuilder(bpt, this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, ForgeDirection.values()[this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, this.field_145849_e)].getOpposite());
/*     */             
/* 392 */             this.currentBuilder = this.recursiveBuilder.nextBuilder();
/*     */             
/* 394 */             scheduleRequirementUpdate();
/*     */           } 
/*     */         } 
/*     */         
/* 398 */         if (this.currentBuilder == null) {
/* 399 */           this.done = true;
/*     */         } else {
/* 401 */           this.box.initialize((IAreaProvider)this.currentBuilder);
/* 402 */           sendNetworkUpdate();
/* 403 */           this.done = false;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 408 */     if (this.done && func_70301_a(0) != null) {
/* 409 */       boolean dropBlueprint = true;
/* 410 */       for (int i = 1; i < func_70302_i_(); i++) {
/* 411 */         if (func_70301_a(i) == null) {
/* 412 */           func_70299_a(i, func_70301_a(0));
/* 413 */           dropBlueprint = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 417 */       if (dropBlueprint) {
/* 418 */         InvUtils.dropItems(func_145831_w(), func_70301_a(0), this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */       }
/*     */       
/* 421 */       func_70299_a(0, (ItemStack)null);
/* 422 */       this.box.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 428 */     return this.inv.func_70302_i_();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/* 433 */     return this.inv.func_70301_a(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int j) {
/* 438 */     ItemStack result = this.inv.func_70298_a(i, j);
/*     */     
/* 440 */     if (!this.field_145850_b.field_72995_K && 
/* 441 */       i == 0) {
/* 442 */       BuildCraftCore.instance.sendToWorld((Packet)new PacketCommand(this, "clearItemRequirements", null), this.field_145850_b);
/* 443 */       iterateBpt(false);
/*     */     } 
/*     */ 
/*     */     
/* 447 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/* 452 */     this.inv.func_70299_a(i, itemstack);
/*     */     
/* 454 */     if (!this.field_145850_b.field_72995_K && 
/* 455 */       i == 0) {
/* 456 */       iterateBpt(false);
/* 457 */       this.done = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slot) {
/* 464 */     return this.inv.func_70304_b(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 469 */     return "Builder";
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 474 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/* 479 */     return (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 484 */     super.func_145839_a(nbttagcompound);
/*     */     
/* 486 */     this.inv.readFromNBT(nbttagcompound);
/*     */     
/* 488 */     if (nbttagcompound.func_74764_b("box")) {
/* 489 */       this.box.initialize(nbttagcompound.func_74775_l("box"));
/*     */     }
/*     */     
/* 492 */     if (nbttagcompound.func_74764_b("path")) {
/* 493 */       this.path = new LinkedList<BlockIndex>();
/* 494 */       NBTTagList list = nbttagcompound.func_150295_c("path", 10);
/*     */ 
/*     */       
/* 497 */       for (int i = 0; i < list.func_74745_c(); i++) {
/* 498 */         this.path.add(new BlockIndex(list.func_150305_b(i)));
/*     */       }
/*     */     } 
/*     */     
/* 502 */     this.done = nbttagcompound.func_74767_n("done");
/* 503 */     this.fluidTank.readFromNBT(nbttagcompound);
/*     */ 
/*     */     
/* 506 */     this.initNBT = (NBTTagCompound)nbttagcompound.func_74775_l("bptBuilder").func_74737_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 511 */     super.func_145841_b(nbttagcompound);
/*     */     
/* 513 */     this.inv.writeToNBT(nbttagcompound);
/*     */     
/* 515 */     if (this.box.isInitialized()) {
/* 516 */       NBTTagCompound boxStore = new NBTTagCompound();
/* 517 */       this.box.writeToNBT(boxStore);
/* 518 */       nbttagcompound.func_74782_a("box", (NBTBase)boxStore);
/*     */     } 
/*     */     
/* 521 */     if (this.path != null) {
/* 522 */       NBTTagList list = new NBTTagList();
/*     */       
/* 524 */       for (BlockIndex i : this.path) {
/* 525 */         NBTTagCompound c = new NBTTagCompound();
/* 526 */         i.writeTo(c);
/* 527 */         list.func_74742_a((NBTBase)c);
/*     */       } 
/*     */       
/* 530 */       nbttagcompound.func_74782_a("path", (NBTBase)list);
/*     */     } 
/*     */     
/* 533 */     nbttagcompound.func_74757_a("done", this.done);
/* 534 */     this.fluidTank.writeToNBT(nbttagcompound);
/*     */     
/* 536 */     NBTTagCompound bptNBT = new NBTTagCompound();
/*     */     
/* 538 */     if (this.currentBuilder != null) {
/* 539 */       NBTTagCompound builderCpt = new NBTTagCompound();
/* 540 */       this.currentBuilder.saveBuildStateToNBT(builderCpt, (IBuildingItemsProvider)this);
/* 541 */       bptNBT.func_74782_a("builderState", (NBTBase)builderCpt);
/*     */     } 
/*     */     
/* 544 */     if (this.currentPathIterator != null) {
/* 545 */       NBTTagCompound iteratorNBT = new NBTTagCompound();
/* 546 */       (new BlockIndex((int)this.currentPathIterator.ix, (int)this.currentPathIterator.iy, (int)this.currentPathIterator.iz))
/*     */         
/* 548 */         .writeTo(iteratorNBT);
/* 549 */       bptNBT.func_74782_a("iterator", (NBTBase)iteratorNBT);
/*     */     } 
/*     */     
/* 552 */     nbttagcompound.func_74782_a("bptBuilder", (NBTBase)bptNBT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 557 */     super.func_145843_s();
/* 558 */     destroy();
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
/*     */   public void func_145845_h() {
/* 571 */     super.func_145845_h();
/*     */     
/* 573 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 577 */     if (this.shouldUpdateRequirements && this.networkUpdateTracker.markTimeIfDelay(this.field_145850_b)) {
/* 578 */       updateRequirements();
/* 579 */       this.shouldUpdateRequirements = false;
/*     */     } 
/*     */     
/* 582 */     if ((this.currentBuilder == null || this.currentBuilder.isDone((IBuildingItemsProvider)this)) && this.box
/* 583 */       .isInitialized()) {
/* 584 */       this.box.reset();
/*     */       
/* 586 */       sendNetworkUpdate();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 591 */     iterateBpt(false);
/*     */     
/* 593 */     if (this.mode != IControllable.Mode.Off && (
/* 594 */       func_145831_w().func_72912_H().func_76077_q() == WorldSettings.GameType.CREATIVE || 
/* 595 */       getBattery().getEnergyStored() > POWER_ACTIVATION)) {
/* 596 */       build();
/*     */     }
/*     */ 
/*     */     
/* 600 */     if (!this.isBuilding && isBuildingBlueprint()) {
/* 601 */       scheduleRequirementUpdate();
/*     */     }
/* 603 */     this.isBuilding = isBuildingBlueprint();
/*     */     
/* 605 */     if (this.done)
/*     */       return; 
/* 607 */     if (getBattery().getEnergyStored() < 25) {
/*     */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWork() {
/* 614 */     return !this.done;
/*     */   }
/*     */   
/*     */   public boolean isBuildingBlueprint() {
/* 618 */     return (func_70301_a(0) != null && func_70301_a(0).func_77973_b() instanceof ItemBlueprint);
/*     */   }
/*     */   
/*     */   public List<RequirementItemStack> getNeededItems() {
/* 622 */     return this.field_145850_b.field_72995_K ? this.requiredToBuild : ((this.currentBuilder instanceof BptBuilderBlueprint) ? ((BptBuilderBlueprint)this.currentBuilder).getNeededItems() : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/* 627 */     super.receiveCommand(command, side, sender, stream);
/* 628 */     if (side.isClient()) {
/* 629 */       if ("clearItemRequirements".equals(command)) {
/* 630 */         this.requiredToBuild = null;
/* 631 */       } else if ("setItemRequirements".equals(command)) {
/* 632 */         int size = stream.readUnsignedMedium();
/* 633 */         this.requiredToBuild = new ArrayList<RequirementItemStack>();
/* 634 */         for (int i = 0; i < size; i++) {
/* 635 */           int itemId = stream.readUnsignedShort();
/* 636 */           int itemDamage = stream.readShort();
/* 637 */           int stackSize = stream.readUnsignedMedium();
/* 638 */           boolean hasCompound = (stackSize >= 8388608);
/*     */           
/* 640 */           ItemStack stack = new ItemStack(Item.func_150899_d(itemId), 1, itemDamage);
/* 641 */           if (hasCompound) {
/* 642 */             stack.func_77982_d(NetworkUtils.readNBT(stream));
/*     */           }
/*     */           
/* 645 */           if (stack.func_77973_b() != null) {
/* 646 */             this.requiredToBuild.add(new RequirementItemStack(stack, stackSize & 0x7FFFFF));
/*     */           } else {
/* 648 */             BCLog.logger.error("Corrupt ItemStack in TileBuilder.receiveCommand! This should not happen! (ID " + itemId + ", damage " + itemDamage + ")");
/*     */           } 
/*     */         } 
/*     */       } 
/* 652 */     } else if (side.isServer()) {
/* 653 */       EntityPlayer player = (EntityPlayer)sender;
/* 654 */       if ("eraseFluidTank".equals(command)) {
/* 655 */         int id = stream.readInt();
/* 656 */         if (id < 0 || id >= this.fluidTanks.length) {
/*     */           return;
/*     */         }
/* 659 */         if (func_70300_a(player) && player.func_70092_e(this.field_145851_c, this.field_145848_d, this.field_145849_e) <= 64.0D) {
/* 660 */           this.fluidTanks[id].setFluid(null);
/* 661 */           sendNetworkUpdate();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Packet getItemRequirementsPacket(List<RequirementItemStack> itemsIn) {
/* 668 */     if (itemsIn != null) {
/* 669 */       final List<RequirementItemStack> items = new ArrayList<RequirementItemStack>();
/* 670 */       items.addAll(itemsIn);
/*     */       
/* 672 */       return (Packet)new PacketCommand(this, "setItemRequirements", new CommandWriter() {
/*     */             public void write(ByteBuf data) {
/* 674 */               data.writeMedium(items.size());
/* 675 */               for (RequirementItemStack rb : items) {
/* 676 */                 data.writeShort(Item.func_150891_b(rb.stack.func_77973_b()));
/* 677 */                 data.writeShort(rb.stack.func_77960_j());
/* 678 */                 data.writeMedium((rb.stack.func_77942_o() ? 8388608 : 0) | Math.min(8388607, rb.size));
/* 679 */                 if (rb.stack.func_77942_o()) {
/* 680 */                   NetworkUtils.writeNBT(data, rb.stack.func_77978_p());
/*     */                 }
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/* 686 */     return (Packet)new PacketCommand(this, "clearItemRequirements", null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBuildingMaterialSlot(int i) {
/* 692 */     return (i != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 697 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int slot, ItemStack stack) {
/* 702 */     if (slot == 0) {
/* 703 */       return stack.func_77973_b() instanceof ItemBlueprint;
/*     */     }
/* 705 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Box getBox() {
/* 711 */     return this.box;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getRenderBoundingBox() {
/* 716 */     Box renderBox = (new Box((TileEntity)this)).extendToEncompass(this.box);
/*     */     
/* 718 */     for (LaserData l : this.pathLasers) {
/* 719 */       renderBox = renderBox.extendToEncompass(l.head);
/* 720 */       renderBox = renderBox.extendToEncompass(l.tail);
/*     */     } 
/*     */     
/* 723 */     return renderBox.expand(50).getBoundingBox();
/*     */   }
/*     */   
/*     */   public void build() {
/* 727 */     if (this.currentBuilder != null && 
/* 728 */       this.currentBuilder.buildNextSlot(this.field_145850_b, this, this.field_145851_c, this.field_145848_d, this.field_145849_e)) {
/* 729 */       scheduleRequirementUpdate();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateRequirements() {
/* 735 */     List<RequirementItemStack> reqCopy = null;
/* 736 */     if (this.currentBuilder instanceof BptBuilderBlueprint) {
/* 737 */       this.currentBuilder.initialize();
/* 738 */       reqCopy = ((BptBuilderBlueprint)this.currentBuilder).getNeededItems();
/*     */     } 
/*     */     
/* 741 */     for (EntityPlayer p : this.guiWatchers) {
/* 742 */       BuildCraftCore.instance.sendToPlayer(p, getItemRequirementsPacket(reqCopy));
/*     */     }
/*     */   }
/*     */   
/*     */   public void scheduleRequirementUpdate() {
/* 747 */     this.shouldUpdateRequirements = true;
/*     */   }
/*     */   
/*     */   public void updateRequirementsOnGuiOpen(EntityPlayer caller) {
/* 751 */     List<RequirementItemStack> reqCopy = null;
/* 752 */     if (this.currentBuilder instanceof BptBuilderBlueprint) {
/* 753 */       this.currentBuilder.initialize();
/* 754 */       reqCopy = ((BptBuilderBlueprint)this.currentBuilder).getNeededItems();
/*     */     } 
/*     */     
/* 757 */     BuildCraftCore.instance.sendToPlayer(caller, getItemRequirementsPacket(reqCopy));
/*     */   }
/*     */   
/*     */   public BptBuilderBase getBlueprint() {
/* 761 */     if (this.currentBuilder != null) {
/* 762 */       return this.currentBuilder;
/*     */     }
/* 764 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 770 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/* 775 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/* 780 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean drainBuild(FluidStack fluidStack, boolean realDrain) {
/* 785 */     for (Tank tank : this.fluidTanks) {
/* 786 */       if (tank.getFluidType() == fluidStack.getFluid()) {
/* 787 */         return (tank.getFluidAmount() >= fluidStack.amount && (tank.drain(fluidStack.amount, realDrain)).amount > 0);
/*     */       }
/*     */     } 
/* 790 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/* 795 */     Fluid fluid = resource.getFluid();
/* 796 */     Tank emptyTank = null;
/* 797 */     for (Tank tank : this.fluidTanks) {
/* 798 */       Fluid type = tank.getFluidType();
/* 799 */       if (type == fluid) {
/* 800 */         int used = tank.fill(resource, doFill);
/* 801 */         if (used > 0 && doFill) {
/* 802 */           sendNetworkUpdate();
/*     */         }
/* 804 */         return used;
/* 805 */       }  if (emptyTank == null && tank.isEmpty()) {
/* 806 */         emptyTank = tank;
/*     */       }
/*     */     } 
/* 809 */     if (emptyTank != null) {
/* 810 */       int used = emptyTank.fill(resource, doFill);
/* 811 */       if (used > 0 && doFill) {
/* 812 */         sendNetworkUpdate();
/*     */       }
/* 814 */       return used;
/*     */     } 
/* 816 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/* 821 */     boolean emptyAvailable = false;
/* 822 */     for (Tank tank : this.fluidTanks) {
/* 823 */       Fluid type = tank.getFluidType();
/* 824 */       if (type == fluid)
/* 825 */         return !tank.isFull(); 
/* 826 */       if (!emptyAvailable) {
/* 827 */         emptyAvailable = tank.isEmpty();
/*     */       }
/*     */     } 
/* 830 */     return emptyAvailable;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/* 835 */     return this.fluidTank.getTankInfo(from);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRequestsCount() {
/* 840 */     if (this.currentBuilder == null)
/* 841 */       return 0; 
/* 842 */     if (!(this.currentBuilder instanceof BptBuilderBlueprint)) {
/* 843 */       return 0;
/*     */     }
/* 845 */     BptBuilderBlueprint bpt = (BptBuilderBlueprint)this.currentBuilder;
/*     */     
/* 847 */     return bpt.getNeededItems().size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getRequest(int slot) {
/* 853 */     if (this.currentBuilder == null)
/* 854 */       return null; 
/* 855 */     if (!(this.currentBuilder instanceof BptBuilderBlueprint)) {
/* 856 */       return null;
/*     */     }
/* 858 */     BptBuilderBlueprint bpt = (BptBuilderBlueprint)this.currentBuilder;
/* 859 */     List<RequirementItemStack> neededItems = bpt.getNeededItems();
/*     */     
/* 861 */     if (neededItems.size() <= slot) {
/* 862 */       return null;
/*     */     }
/*     */     
/* 865 */     RequirementItemStack requirement = neededItems.get(slot);
/*     */     
/* 867 */     int qty = quantityMissing(requirement.stack, requirement.size);
/*     */     
/* 869 */     if (qty <= 0) {
/* 870 */       return null;
/*     */     }
/* 872 */     ItemStack requestStack = requirement.stack.func_77946_l();
/* 873 */     requestStack.field_77994_a = qty;
/* 874 */     return requestStack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack offerItem(int slot, ItemStack stack) {
/* 880 */     if (this.currentBuilder == null)
/* 881 */       return stack; 
/* 882 */     if (!(this.currentBuilder instanceof BptBuilderBlueprint)) {
/* 883 */       return stack;
/*     */     }
/* 885 */     BptBuilderBlueprint bpt = (BptBuilderBlueprint)this.currentBuilder;
/* 886 */     List<RequirementItemStack> neededItems = bpt.getNeededItems();
/*     */     
/* 888 */     if (neededItems.size() <= slot) {
/* 889 */       return stack;
/*     */     }
/*     */     
/* 892 */     RequirementItemStack requirement = neededItems.get(slot);
/*     */     
/* 894 */     int qty = quantityMissing(requirement.stack, requirement.size);
/*     */     
/* 896 */     if (qty <= 0) {
/* 897 */       return stack;
/*     */     }
/*     */     
/* 900 */     ItemStack toAdd = stack.func_77946_l();
/*     */     
/* 902 */     if (qty < toAdd.field_77994_a) {
/* 903 */       toAdd.field_77994_a = qty;
/*     */     }
/*     */     
/* 906 */     ITransactor t = Transactor.getTransactorFor(this);
/* 907 */     ItemStack added = t.add(toAdd, ForgeDirection.UNKNOWN, true);
/*     */     
/* 909 */     if (added.field_77994_a >= stack.field_77994_a) {
/* 910 */       return null;
/*     */     }
/* 912 */     stack.field_77994_a -= added.field_77994_a;
/* 913 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int quantityMissing(ItemStack requirement, int amount) {
/* 919 */     int left = (amount <= 0) ? requirement.field_77994_a : amount;
/*     */     
/* 921 */     for (IInvSlot slot : InventoryIterator.getIterable((IInventory)this)) {
/* 922 */       if (slot.getStackInSlot() != null)
/*     */       {
/* 924 */         if (StackHelper.isEqualItem(requirement, slot.getStackInSlot())) {
/* 925 */           if ((slot.getStackInSlot()).field_77994_a >= left) {
/* 926 */             return 0;
/*     */           }
/* 928 */           left -= (slot.getStackInSlot()).field_77994_a;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 934 */     return left;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsControlMode(IControllable.Mode mode) {
/* 939 */     return (mode == IControllable.Mode.Off || mode == IControllable.Mode.On);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 944 */     super.writeData(stream);
/* 945 */     this.box.writeData(stream);
/* 946 */     this.fluidTank.writeData(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 951 */     super.readData(stream);
/* 952 */     this.box.readData(stream);
/* 953 */     this.fluidTank.readData(stream);
/*     */   }
/*     */   
/*     */   public Tank[] getFluidTanks() {
/* 957 */     return this.fluidTanks;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\TileBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */