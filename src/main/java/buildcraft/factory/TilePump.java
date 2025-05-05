/*     */ package buildcraft.factory;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.BuildCraftFactory;
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.SafeTimeTracker;
/*     */ import buildcraft.api.power.IRedstoneEngineReceiver;
/*     */ import buildcraft.api.tiles.IHasWork;
/*     */ import buildcraft.core.internal.ILEDProvider;
/*     */ import buildcraft.core.lib.EntityBlock;
/*     */ import buildcraft.core.lib.RFBattery;
/*     */ import buildcraft.core.lib.TileBuffer;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.fluids.SingleUseTank;
/*     */ import buildcraft.core.lib.fluids.TankUtils;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Deque;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTankInfo;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
/*     */ import net.minecraftforge.fluids.IFluidTank;
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
/*     */ public class TilePump
/*     */   extends TileBuildCraft
/*     */   implements IHasWork, IFluidHandler, IRedstoneEngineReceiver, ILEDProvider
/*     */ {
/*     */   public static final int REBUID_DELAY = 512;
/*  52 */   public static int MAX_LIQUID = 16000;
/*  53 */   public SingleUseTank tank = new SingleUseTank("tank", MAX_LIQUID, (TileEntity)this);
/*     */   
/*     */   private EntityBlock tube;
/*  56 */   private TreeMap<Integer, Deque<BlockIndex>> pumpLayerQueues = new TreeMap<Integer, Deque<BlockIndex>>();
/*  57 */   private double tubeY = Double.NaN;
/*  58 */   private int aimY = 0;
/*     */   
/*  60 */   private SafeTimeTracker timer = new SafeTimeTracker(512L);
/*  61 */   private int tick = Utils.RANDOM.nextInt(32);
/*  62 */   private int tickPumped = this.tick - 20;
/*  63 */   private int numFluidBlocksFound = 0;
/*     */   
/*     */   private boolean powered = false;
/*     */   
/*     */   private int ledState;
/*  68 */   private SafeTimeTracker updateTracker = new SafeTimeTracker(Math.max(16, BuildCraftCore.updateFactor));
/*     */ 
/*     */   
/*     */   public TilePump() {
/*  72 */     setBattery(new RFBattery(1000, 150, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  77 */     super.func_145845_h();
/*     */     
/*  79 */     if (this.powered) {
/*  80 */       this.pumpLayerQueues.clear();
/*  81 */       destroyTube();
/*     */     } else {
/*  83 */       createTube();
/*     */     } 
/*     */     
/*  86 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  90 */     if (this.updateTracker.markTimeIfDelay(this.field_145850_b)) {
/*  91 */       sendNetworkUpdate();
/*     */     }
/*     */     
/*  94 */     pushToConsumers();
/*     */     
/*  96 */     if (this.powered) {
/*     */       return;
/*     */     }
/*     */     
/* 100 */     if (this.tube == null) {
/*     */       return;
/*     */     }
/*     */     
/* 104 */     if (this.tube.field_70163_u - this.aimY > 0.01D) {
/* 105 */       this.tubeY = this.tube.field_70163_u - 0.01D;
/* 106 */       setTubePosition();
/* 107 */       sendNetworkUpdate();
/*     */       
/*     */       return;
/*     */     } 
/* 111 */     this.tick++;
/*     */     
/* 113 */     if (this.tick % 16 != 0) {
/*     */       return;
/*     */     }
/*     */     
/* 117 */     BlockIndex index = getNextIndexToPump(false);
/*     */     
/* 119 */     FluidStack fluidToPump = (index != null) ? BlockUtils.drainBlock(this.field_145850_b, index.x, index.y, index.z, false) : null;
/* 120 */     if (fluidToPump != null) {
/* 121 */       if (isFluidAllowed(fluidToPump.getFluid()) && this.tank.fill(fluidToPump, false) == fluidToPump.amount && 
/* 122 */         getBattery().useEnergy(100, 100, false) > 0) {
/* 123 */         if (fluidToPump.getFluid() != FluidRegistry.WATER || BuildCraftCore.consumeWaterSources || this.numFluidBlocksFound < 9) {
/* 124 */           index = getNextIndexToPump(true);
/* 125 */           BlockUtils.drainBlock(this.field_145850_b, index.x, index.y, index.z, true);
/*     */         } 
/*     */         
/* 128 */         this.tank.fill(fluidToPump, true);
/* 129 */         this.tickPumped = this.tick;
/*     */       }
/*     */     
/*     */     }
/* 133 */     else if (this.tick % 128 == 0) {
/*     */       
/* 135 */       rebuildQueue();
/*     */       
/* 137 */       if (getNextIndexToPump(false) == null) {
/* 138 */         for (int y = this.field_145848_d - 1; y > 0; y--) {
/* 139 */           if (isPumpableFluid(this.field_145851_c, y, this.field_145849_e)) {
/* 140 */             this.aimY = y; return;
/*     */           } 
/* 142 */           if (isBlocked(this.field_145851_c, y, this.field_145849_e)) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(Block block) {
/* 152 */     boolean p = this.field_145850_b.func_72864_z(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     
/* 154 */     if (this.powered != p) {
/* 155 */       this.powered = p;
/*     */       
/* 157 */       if (!this.field_145850_b.field_72995_K) {
/* 158 */         sendNetworkUpdate();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isBlocked(int x, int y, int z) {
/* 164 */     Material mat = BlockUtils.getBlock(this.field_145850_b, x, y, z).func_149688_o();
/*     */     
/* 166 */     return mat.func_76230_c();
/*     */   }
/*     */   
/*     */   private void pushToConsumers() {
/* 170 */     if (this.cache == null) {
/* 171 */       this.cache = TileBuffer.makeBuffer(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, false);
/*     */     }
/*     */     
/* 174 */     TankUtils.pushFluidToConsumers((IFluidTank)this.tank, 400, this.cache);
/*     */   }
/*     */   
/*     */   private void createTube() {
/* 178 */     if (this.tube == null) {
/* 179 */       this.tube = FactoryProxy.proxy.newPumpTube(this.field_145850_b);
/*     */       
/* 181 */       if (!Double.isNaN(this.tubeY)) {
/* 182 */         this.tube.field_70163_u = this.tubeY;
/*     */       } else {
/* 184 */         this.tube.field_70163_u = this.field_145848_d;
/*     */       } 
/*     */       
/* 187 */       this.tubeY = this.tube.field_70163_u;
/*     */       
/* 189 */       if (this.aimY == 0) {
/* 190 */         this.aimY = this.field_145848_d;
/*     */       }
/*     */       
/* 193 */       setTubePosition();
/*     */       
/* 195 */       this.field_145850_b.func_72838_d((Entity)this.tube);
/*     */       
/* 197 */       if (!this.field_145850_b.field_72995_K) {
/* 198 */         sendNetworkUpdate();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void destroyTube() {
/* 204 */     if (this.tube != null) {
/* 205 */       CoreProxy.proxy.removeEntity((Entity)this.tube);
/* 206 */       this.tube = null;
/* 207 */       this.tubeY = Double.NaN;
/* 208 */       this.aimY = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private BlockIndex getNextIndexToPump(boolean remove) {
/* 213 */     if (this.pumpLayerQueues.isEmpty()) {
/* 214 */       if (this.timer.markTimeIfDelay(this.field_145850_b)) {
/* 215 */         rebuildQueue();
/*     */       }
/*     */       
/* 218 */       return null;
/*     */     } 
/*     */     
/* 221 */     Deque<BlockIndex> topLayer = (Deque<BlockIndex>)this.pumpLayerQueues.lastEntry().getValue();
/*     */     
/* 223 */     if (topLayer != null) {
/* 224 */       if (topLayer.isEmpty()) {
/* 225 */         this.pumpLayerQueues.pollLastEntry();
/*     */       }
/*     */       
/* 228 */       if (remove) {
/* 229 */         BlockIndex index = topLayer.pollLast();
/* 230 */         return index;
/*     */       } 
/* 232 */       return topLayer.peekLast();
/*     */     } 
/*     */     
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private Deque<BlockIndex> getLayerQueue(int layer) {
/* 240 */     Deque<BlockIndex> pumpQueue = this.pumpLayerQueues.get(Integer.valueOf(layer));
/*     */     
/* 242 */     if (pumpQueue == null) {
/* 243 */       pumpQueue = new LinkedList<BlockIndex>();
/* 244 */       this.pumpLayerQueues.put(Integer.valueOf(layer), pumpQueue);
/*     */     } 
/*     */     
/* 247 */     return pumpQueue;
/*     */   }
/*     */   
/*     */   public void rebuildQueue() {
/* 251 */     this.numFluidBlocksFound = 0;
/* 252 */     this.pumpLayerQueues.clear();
/* 253 */     int x = this.field_145851_c;
/* 254 */     int y = this.aimY;
/* 255 */     int z = this.field_145849_e;
/* 256 */     Fluid pumpingFluid = BlockUtils.getFluid(BlockUtils.getBlock(this.field_145850_b, x, y, z));
/*     */     
/* 258 */     if (pumpingFluid == null) {
/*     */       return;
/*     */     }
/*     */     
/* 262 */     if (pumpingFluid != this.tank.getAcceptedFluid() && this.tank.getAcceptedFluid() != null) {
/*     */       return;
/*     */     }
/*     */     
/* 266 */     Set<BlockIndex> visitedBlocks = new HashSet<BlockIndex>();
/* 267 */     Deque<BlockIndex> fluidsFound = new LinkedList<BlockIndex>();
/*     */     
/* 269 */     queueForPumping(x, y, z, visitedBlocks, fluidsFound, pumpingFluid);
/*     */ 
/*     */ 
/*     */     
/* 273 */     while (!fluidsFound.isEmpty()) {
/* 274 */       Deque<BlockIndex> fluidsToExpand = fluidsFound;
/* 275 */       fluidsFound = new LinkedList<BlockIndex>();
/*     */       
/* 277 */       for (BlockIndex index : fluidsToExpand) {
/* 278 */         queueForPumping(index.x, index.y + 1, index.z, visitedBlocks, fluidsFound, pumpingFluid);
/* 279 */         queueForPumping(index.x + 1, index.y, index.z, visitedBlocks, fluidsFound, pumpingFluid);
/* 280 */         queueForPumping(index.x - 1, index.y, index.z, visitedBlocks, fluidsFound, pumpingFluid);
/* 281 */         queueForPumping(index.x, index.y, index.z + 1, visitedBlocks, fluidsFound, pumpingFluid);
/* 282 */         queueForPumping(index.x, index.y, index.z - 1, visitedBlocks, fluidsFound, pumpingFluid);
/*     */         
/* 284 */         if (pumpingFluid == FluidRegistry.WATER && !BuildCraftCore.consumeWaterSources && this.numFluidBlocksFound >= 9) {
/*     */           return;
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
/*     */   public void queueForPumping(int x, int y, int z, Set<BlockIndex> visitedBlocks, Deque<BlockIndex> fluidsFound, Fluid pumpingFluid) {
/* 297 */     BlockIndex index = new BlockIndex(x, y, z);
/* 298 */     if (visitedBlocks.add(index)) {
/* 299 */       if ((x - this.field_145851_c) * (x - this.field_145851_c) + (z - this.field_145849_e) * (z - this.field_145849_e) > 4096) {
/*     */         return;
/*     */       }
/*     */       
/* 303 */       Block block = BlockUtils.getBlock(this.field_145850_b, x, y, z);
/*     */       
/* 305 */       if (BlockUtils.getFluid(block) == pumpingFluid) {
/* 306 */         fluidsFound.add(index);
/*     */       }
/*     */       
/* 309 */       if (canDrainBlock(block, x, y, z, pumpingFluid)) {
/* 310 */         getLayerQueue(y).add(index);
/* 311 */         this.numFluidBlocksFound++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isPumpableFluid(int x, int y, int z) {
/* 317 */     Fluid fluid = BlockUtils.getFluid(BlockUtils.getBlock(this.field_145850_b, x, y, z));
/*     */     
/* 319 */     if (fluid == null)
/* 320 */       return false; 
/* 321 */     if (!isFluidAllowed(fluid)) {
/* 322 */       return false;
/*     */     }
/* 324 */     return (this.tank.getAcceptedFluid() == null || this.tank.getAcceptedFluid() == fluid);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canDrainBlock(Block block, int x, int y, int z, Fluid fluid) {
/* 329 */     if (!isFluidAllowed(fluid)) {
/* 330 */       return false;
/*     */     }
/*     */     
/* 333 */     FluidStack fluidStack = BlockUtils.drainBlock(block, this.field_145850_b, x, y, z, false);
/*     */     
/* 335 */     if (fluidStack == null || fluidStack.amount <= 0) {
/* 336 */       return false;
/*     */     }
/* 338 */     return (fluidStack.getFluid() == fluid);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isFluidAllowed(Fluid fluid) {
/* 343 */     return BuildCraftFactory.pumpDimensionList.isFluidAllowed(fluid, this.field_145850_b.field_73011_w.field_76574_g);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound data) {
/* 348 */     super.func_145839_a(data);
/*     */     
/* 350 */     this.tank.readFromNBT(data);
/*     */     
/* 352 */     this.powered = data.func_74767_n("powered");
/*     */     
/* 354 */     this.aimY = data.func_74762_e("aimY");
/* 355 */     this.tubeY = data.func_74760_g("tubeY");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound data) {
/* 360 */     super.func_145841_b(data);
/*     */     
/* 362 */     this.tank.writeToNBT(data);
/*     */     
/* 364 */     data.func_74757_a("powered", this.powered);
/*     */     
/* 366 */     data.func_74768_a("aimY", this.aimY);
/*     */     
/* 368 */     if (this.tube != null) {
/* 369 */       data.func_74776_a("tubeY", (float)this.tube.field_70163_u);
/*     */     } else {
/* 371 */       data.func_74776_a("tubeY", this.field_145848_d);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWork() {
/* 377 */     BlockIndex next = getNextIndexToPump(false);
/*     */     
/* 379 */     if (next != null) {
/* 380 */       return isPumpableFluid(next.x, next.y, next.z);
/*     */     }
/* 382 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf buf) {
/* 388 */     buf.writeShort(this.aimY);
/* 389 */     buf.writeFloat((float)this.tubeY);
/* 390 */     buf.writeBoolean(this.powered);
/* 391 */     this.ledState = ((this.tick - this.tickPumped < 48) ? 16 : 0) | getBattery().getEnergyStored() * 15 / getBattery().getMaxEnergyStored();
/* 392 */     buf.writeByte(this.ledState);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/* 397 */     this.aimY = data.readShort();
/* 398 */     this.tubeY = data.readFloat();
/* 399 */     this.powered = data.readBoolean();
/*     */     
/* 401 */     int newLedState = data.readUnsignedByte();
/* 402 */     if (newLedState != this.ledState) {
/* 403 */       this.ledState = newLedState;
/* 404 */       this.field_145850_b.func_147458_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     } 
/*     */     
/* 407 */     setTubePosition();
/*     */   }
/*     */   
/*     */   private void setTubePosition() {
/* 411 */     if (this.tube != null) {
/* 412 */       this.tube.iSize = 0.5D;
/* 413 */       this.tube.kSize = 0.5D;
/* 414 */       this.tube.jSize = this.field_145848_d - this.tube.field_70163_u;
/*     */       
/* 416 */       this.tube.func_70107_b((this.field_145851_c + 0.25F), this.tubeY, (this.field_145849_e + 0.25F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 422 */     super.func_145843_s();
/* 423 */     destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onChunkUnload() {
/* 428 */     super.onChunkUnload();
/*     */     
/* 430 */     if (this.tube != null) {
/*     */       
/* 432 */       CoreProxy.proxy.removeEntity((Entity)this.tube);
/* 433 */       this.tube = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145829_t() {
/* 439 */     super.func_145829_t();
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 444 */     this.pumpLayerQueues.clear();
/* 445 */     destroyTube();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/* 452 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/* 457 */     return this.tank.drain(maxDrain, doDrain);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/* 462 */     if (resource == null)
/* 463 */       return null; 
/* 464 */     if (!resource.isFluidEqual(this.tank.getFluid())) {
/* 465 */       return null;
/*     */     }
/* 467 */     return drain(from, resource.amount, doDrain);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/* 473 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 478 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/* 483 */     return new FluidTankInfo[] { this.tank.getInfo() };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstoneEngine(ForgeDirection side) {
/* 488 */     return !BuildCraftFactory.pumpsNeedRealPower;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLEDLevel(int led) {
/* 493 */     if (led == 0) {
/* 494 */       return this.ledState & 0xF;
/*     */     }
/* 496 */     return (this.ledState >> 4 > 0) ? 15 : 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\TilePump.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */