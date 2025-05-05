/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.SafeTimeTracker;
/*     */ import buildcraft.api.tiles.IDebuggable;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.DefaultProps;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.utils.MathUtils;
/*     */ import buildcraft.transport.network.PacketFluidUpdate;
/*     */ import buildcraft.transport.pipes.PipeFluidsClay;
/*     */ import buildcraft.transport.pipes.PipeFluidsCobblestone;
/*     */ import buildcraft.transport.pipes.PipeFluidsDiamond;
/*     */ import buildcraft.transport.pipes.PipeFluidsEmerald;
/*     */ import buildcraft.transport.pipes.PipeFluidsGold;
/*     */ import buildcraft.transport.pipes.PipeFluidsIron;
/*     */ import buildcraft.transport.pipes.PipeFluidsQuartz;
/*     */ import buildcraft.transport.pipes.PipeFluidsSandstone;
/*     */ import buildcraft.transport.pipes.PipeFluidsStone;
/*     */ import buildcraft.transport.pipes.PipeFluidsVoid;
/*     */ import buildcraft.transport.pipes.PipeFluidsWood;
/*     */ import buildcraft.transport.pipes.events.PipeEvent;
/*     */ import buildcraft.transport.pipes.events.PipeEventFluid;
/*     */ import buildcraft.transport.utils.FluidRenderData;
/*     */ import com.google.common.collect.EnumMultiset;
/*     */ import com.google.common.collect.Multiset;
/*     */ import java.util.Arrays;
/*     */ import java.util.BitSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidEvent;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTankInfo;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
/*     */ 
/*     */ public class PipeTransportFluids
/*     */   extends PipeTransport implements IFluidHandler, IDebuggable {
/*  47 */   public static final Map<Class<? extends Pipe<?>>, Integer> fluidCapacities = new HashMap<Class<? extends Pipe<?>>, Integer>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public static int MAX_TRAVEL_DELAY = 12;
/*  54 */   public static short INPUT_TTL = 60;
/*  55 */   public static short OUTPUT_TTL = 80;
/*  56 */   public static short OUTPUT_COOLDOWN = 30;
/*     */   
/*  58 */   private static int NETWORK_SYNC_TICKS = BuildCraftCore.updateFactor / 2;
/*  59 */   private static final ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
/*  60 */   private static final ForgeDirection[] orientations = ForgeDirection.values();
/*     */   
/*     */   public class PipeSection
/*     */   {
/*     */     public int amount;
/*  65 */     private short currentTime = 0;
/*  66 */     private short[] incoming = new short[PipeTransportFluids.MAX_TRAVEL_DELAY];
/*     */     
/*     */     public int fill(int maxFill, boolean doFill) {
/*  69 */       int amountToFill = Math.min(getMaxFillRate(), maxFill);
/*  70 */       if (amountToFill <= 0) {
/*  71 */         return 0;
/*     */       }
/*     */       
/*  74 */       if (doFill) {
/*  75 */         this.incoming[this.currentTime] = (short)(this.incoming[this.currentTime] + amountToFill);
/*  76 */         this.amount += amountToFill;
/*     */       } 
/*  78 */       return amountToFill;
/*     */     }
/*     */     
/*     */     public int drain(int maxDrain, boolean doDrain) {
/*  82 */       int maxToDrain = getAvailable();
/*  83 */       if (maxToDrain > maxDrain) {
/*  84 */         maxToDrain = maxDrain;
/*     */       }
/*  86 */       if (maxToDrain > PipeTransportFluids.this.flowRate) {
/*  87 */         maxToDrain = PipeTransportFluids.this.flowRate;
/*     */       }
/*  89 */       if (maxToDrain <= 0) {
/*  90 */         return 0;
/*     */       }
/*  92 */       if (doDrain) {
/*  93 */         this.amount -= maxToDrain;
/*     */       }
/*  95 */       return maxToDrain;
/*     */     }
/*     */ 
/*     */     
/*     */     public void moveFluids() {
/* 100 */       this.incoming[this.currentTime] = 0;
/*     */     }
/*     */     
/*     */     public void setTime(short newTime) {
/* 104 */       this.currentTime = newTime;
/*     */     }
/*     */     
/*     */     public void reset() {
/* 108 */       this.amount = 0;
/* 109 */       this.incoming = new short[PipeTransportFluids.MAX_TRAVEL_DELAY];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getAvailable() {
/* 119 */       int all = this.amount;
/* 120 */       for (short slot : this.incoming) {
/* 121 */         all -= slot;
/*     */       }
/* 123 */       return all;
/*     */     }
/*     */     
/*     */     public int getMaxFillRate() {
/* 127 */       return Math.min(PipeTransportFluids.this.getCapacity() - this.amount, PipeTransportFluids.this.flowRate - this.incoming[this.currentTime]);
/*     */     }
/*     */     
/*     */     public void readFromNBT(NBTTagCompound compoundTag) {
/* 131 */       this.amount = compoundTag.func_74765_d("capacity");
/*     */       
/* 133 */       for (int i = 0; i < PipeTransportFluids.this.travelDelay; i++) {
/* 134 */         this.incoming[i] = compoundTag.func_74765_d("in[" + i + "]");
/*     */       }
/*     */     }
/*     */     
/*     */     public void writeToNBT(NBTTagCompound subTag) {
/* 139 */       subTag.func_74777_a("capacity", (short)this.amount);
/*     */       
/* 141 */       for (int i = 0; i < PipeTransportFluids.this.travelDelay; i++) {
/* 142 */         subTag.func_74777_a("in[" + i + "]", this.incoming[i]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 147 */   public PipeSection[] sections = new PipeSection[7];
/*     */   
/*     */   public FluidStack fluidType;
/* 150 */   public FluidRenderData renderCache = new FluidRenderData();
/*     */   
/* 152 */   private final SafeTimeTracker networkSyncTracker = new SafeTimeTracker(NETWORK_SYNC_TICKS);
/* 153 */   private final TransferState[] transferState = new TransferState[directions.length];
/* 154 */   private final int[] inputPerTick = new int[directions.length];
/* 155 */   private final short[] inputTTL = new short[] { 0, 0, 0, 0, 0, 0 };
/* 156 */   private final short[] outputTTL = new short[] { OUTPUT_TTL, OUTPUT_TTL, OUTPUT_TTL, OUTPUT_TTL, OUTPUT_TTL, OUTPUT_TTL };
/* 157 */   private final short[] outputCooldown = new short[] { 0, 0, 0, 0, 0, 0 };
/* 158 */   private final boolean[] canReceiveCache = new boolean[6];
/* 159 */   private int clientSyncCounter = 0;
/*     */   private int capacity;
/* 161 */   private int travelDelay = MAX_TRAVEL_DELAY;
/*     */   private int flowRate;
/*     */   
/* 164 */   public enum TransferState { None, Input, Output; }
/*     */ 
/*     */   
/*     */   public PipeTransportFluids() {
/* 168 */     for (ForgeDirection direction : directions) {
/* 169 */       this.sections[direction.ordinal()] = new PipeSection();
/* 170 */       this.transferState[direction.ordinal()] = TransferState.None;
/*     */     } 
/* 172 */     this.sections[6] = new PipeSection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCapacity() {
/* 181 */     return this.capacity;
/*     */   }
/*     */   
/*     */   public int getFlowRate() {
/* 185 */     return this.flowRate;
/*     */   }
/*     */   
/*     */   public void initFromPipe(Class<? extends Pipe<?>> pipeClass) {
/* 189 */     this.capacity = 25 * Math.min(1000, BuildCraftTransport.pipeFluidsBaseFlowRate);
/* 190 */     this.flowRate = ((Integer)fluidCapacities.get(pipeClass)).intValue();
/* 191 */     this.travelDelay = MathUtils.clamp(Math.round(16.0F / (this.flowRate / BuildCraftTransport.pipeFluidsBaseFlowRate)), 1, MAX_TRAVEL_DELAY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/* 196 */     super.initialize();
/*     */     
/* 198 */     for (ForgeDirection d : directions) {
/* 199 */       this.canReceiveCache[d.ordinal()] = canReceiveFluid(d);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public IPipeTile.PipeType getPipeType() {
/* 205 */     return IPipeTile.PipeType.FLUID;
/*     */   }
/*     */   
/*     */   private boolean canReceiveFluid(ForgeDirection o) {
/* 209 */     TileEntity tile = this.container.getTile(o);
/*     */     
/* 211 */     if (!this.container.isPipeConnected(o)) {
/* 212 */       return false;
/*     */     }
/*     */     
/* 215 */     if (tile instanceof IPipeTile) {
/* 216 */       Pipe<?> pipe = (Pipe)((IPipeTile)tile).getPipe();
/*     */       
/* 218 */       if (pipe == null || !inputOpen(o.getOpposite())) {
/* 219 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 223 */     if (tile instanceof IFluidHandler) {
/* 224 */       return true;
/*     */     }
/*     */     
/* 227 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEntity() {
/* 232 */     if ((this.container.func_145831_w()).field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 236 */     moveFluids();
/*     */     
/* 238 */     if (this.networkSyncTracker.markTimeIfDelay(this.container.func_145831_w())) {
/* 239 */       boolean init = false;
/* 240 */       if (++this.clientSyncCounter > BuildCraftCore.longUpdateFactor * 2L) {
/* 241 */         this.clientSyncCounter = 0;
/* 242 */         init = true;
/*     */       } 
/* 244 */       PacketFluidUpdate packet = computeFluidUpdate(init, true);
/*     */       
/* 246 */       if (packet != null) {
/* 247 */         BuildCraftTransport.instance.sendToPlayers((Packet)packet, this.container.func_145831_w(), this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e, DefaultProps.PIPE_CONTENTS_RENDER_DIST);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void moveFluids() {
/* 253 */     if (this.fluidType != null) {
/* 254 */       short newTimeSlot = (short)(int)(this.container.func_145831_w().func_82737_E() % this.travelDelay);
/* 255 */       int outputCount = computeCurrentConnectionStatesAndTickFlows((newTimeSlot > 0 && newTimeSlot < this.travelDelay) ? newTimeSlot : 0);
/*     */       
/* 257 */       if (this.fluidType != null) {
/* 258 */         moveFromPipe(outputCount);
/* 259 */         moveFromCenter();
/* 260 */         moveToCenter();
/*     */       } 
/*     */     } else {
/* 263 */       computeTTLs();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void moveFromPipe(int outputCount) {
/* 269 */     if (outputCount > 0) {
/* 270 */       for (ForgeDirection o : directions) {
/* 271 */         if (this.transferState[o.ordinal()] == TransferState.Output) {
/* 272 */           TileEntity target = this.container.getTile(o);
/* 273 */           if (target instanceof IFluidHandler) {
/*     */ 
/*     */ 
/*     */             
/* 277 */             PipeSection section = this.sections[o.ordinal()];
/* 278 */             FluidStack liquidToPush = new FluidStack(this.fluidType, section.drain(this.flowRate, false));
/*     */             
/* 280 */             if (liquidToPush.amount > 0) {
/* 281 */               int filled = ((IFluidHandler)target).fill(o.getOpposite(), liquidToPush, true);
/* 282 */               if (filled <= 0) {
/* 283 */                 this.outputTTL[o.ordinal()] = (short)(this.outputTTL[o.ordinal()] - 1);
/*     */               } else {
/* 285 */                 section.drain(filled, true);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void moveFromCenter() {
/* 295 */     int pushAmount = (this.sections[6]).amount;
/* 296 */     int totalAvailable = this.sections[6].getAvailable();
/* 297 */     if (totalAvailable < 1 || pushAmount < 1) {
/*     */       return;
/*     */     }
/*     */     
/* 301 */     int testAmount = this.flowRate;
/*     */     
/* 303 */     EnumMultiset enumMultiset = EnumMultiset.create(ForgeDirection.class);
/* 304 */     for (ForgeDirection direction : directions) {
/* 305 */       if (this.transferState[direction.ordinal()] == TransferState.Output) {
/* 306 */         enumMultiset.add(direction);
/*     */       }
/*     */     } 
/*     */     
/* 310 */     if (enumMultiset.size() > 0) {
/* 311 */       this.container.pipe.eventBus.handleEvent((Class)PipeEventFluid.FindDest.class, (PipeEvent)new PipeEventFluid.FindDest(this.container.pipe, new FluidStack(this.fluidType, pushAmount), (Multiset)enumMultiset));
/* 312 */       float min = Math.min(this.flowRate * enumMultiset.size(), totalAvailable) / this.flowRate / enumMultiset.size();
/*     */       
/* 314 */       for (ForgeDirection direction : enumMultiset.elementSet()) {
/* 315 */         int available = this.sections[direction.ordinal()].fill(testAmount, false);
/* 316 */         int amountToPush = (int)(available * min * enumMultiset.count(direction));
/* 317 */         if (amountToPush < 1) {
/* 318 */           amountToPush++;
/*     */         }
/*     */         
/* 321 */         amountToPush = this.sections[6].drain(amountToPush, false);
/* 322 */         if (amountToPush > 0) {
/* 323 */           int filled = this.sections[direction.ordinal()].fill(amountToPush, true);
/* 324 */           this.sections[6].drain(filled, true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void moveToCenter() {
/* 331 */     int transferInCount = 0;
/* 332 */     int spaceAvailable = getCapacity() - (this.sections[6]).amount;
/*     */     
/* 334 */     for (ForgeDirection dir : directions) {
/* 335 */       this.inputPerTick[dir.ordinal()] = 0;
/* 336 */       if (this.transferState[dir.ordinal()] != TransferState.Output) {
/* 337 */         this.inputPerTick[dir.ordinal()] = this.sections[dir.ordinal()].drain(this.flowRate, false);
/* 338 */         if (this.inputPerTick[dir.ordinal()] > 0) {
/* 339 */           transferInCount++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 344 */     float min = Math.min(this.flowRate * transferInCount, spaceAvailable) / this.flowRate / transferInCount;
/*     */     
/* 346 */     for (ForgeDirection dir : directions) {
/*     */       
/* 348 */       if (this.inputPerTick[dir.ordinal()] > 0) {
/* 349 */         int amountToDrain = (int)(this.inputPerTick[dir.ordinal()] * min);
/* 350 */         if (amountToDrain < 1) {
/* 351 */           amountToDrain++;
/*     */         }
/*     */         
/* 354 */         int amountToPush = this.sections[dir.ordinal()].drain(amountToDrain, false);
/* 355 */         if (amountToPush > 0) {
/* 356 */           int filled = this.sections[6].fill(amountToPush, true);
/* 357 */           this.sections[dir.ordinal()].drain(filled, true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void computeTTLs() {
/* 364 */     for (int i = 0; i < 6; i++) {
/* 365 */       if (this.transferState[i] == TransferState.Input) {
/* 366 */         if (this.inputTTL[i] > 0) {
/* 367 */           this.inputTTL[i] = (short)(this.inputTTL[i] - 1);
/*     */         } else {
/* 369 */           this.transferState[i] = TransferState.None;
/*     */         } 
/*     */       }
/*     */       
/* 373 */       if (this.outputCooldown[i] > 0) {
/* 374 */         this.outputCooldown[i] = (short)(this.outputCooldown[i] - 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private int computeCurrentConnectionStatesAndTickFlows(short newTimeSlot) {
/* 380 */     int outputCount = 0;
/* 381 */     int fluidAmount = 0;
/*     */ 
/*     */     
/* 384 */     for (ForgeDirection direction : orientations) {
/* 385 */       int dirI = direction.ordinal();
/* 386 */       PipeSection section = this.sections[dirI];
/*     */       
/* 388 */       fluidAmount += section.amount;
/* 389 */       section.setTime(newTimeSlot);
/* 390 */       section.moveFluids();
/*     */ 
/*     */       
/* 393 */       if (direction != ForgeDirection.UNKNOWN)
/*     */       {
/*     */         
/* 396 */         if (this.transferState[dirI] == TransferState.Input) {
/* 397 */           this.inputTTL[dirI] = (short)(this.inputTTL[dirI] - 1);
/* 398 */           if (this.inputTTL[dirI] <= 0) {
/* 399 */             this.transferState[dirI] = TransferState.None;
/*     */           
/*     */           }
/*     */         }
/* 403 */         else if (!this.container.pipe.outputOpen(direction)) {
/* 404 */           this.transferState[dirI] = TransferState.None;
/*     */         
/*     */         }
/* 407 */         else if (this.outputCooldown[dirI] > 0) {
/* 408 */           this.outputCooldown[dirI] = (short)(this.outputCooldown[dirI] - 1);
/*     */         
/*     */         }
/* 411 */         else if (this.outputTTL[dirI] <= 0) {
/* 412 */           this.transferState[dirI] = TransferState.None;
/* 413 */           this.outputCooldown[dirI] = OUTPUT_COOLDOWN;
/* 414 */           this.outputTTL[dirI] = OUTPUT_TTL;
/*     */         
/*     */         }
/* 417 */         else if (this.canReceiveCache[dirI] && this.container.pipe.outputOpen(direction)) {
/* 418 */           this.transferState[dirI] = TransferState.Output;
/* 419 */           outputCount++;
/*     */         } 
/*     */       }
/*     */     } 
/* 423 */     if (fluidAmount == 0) {
/* 424 */       setFluidType((FluidStack)null);
/*     */     }
/*     */     
/* 427 */     return outputCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PacketFluidUpdate computeFluidUpdate(boolean initPacket, boolean persistChange) {
/* 438 */     boolean changed = false;
/* 439 */     BitSet delta = new BitSet(8);
/*     */     
/* 441 */     FluidRenderData renderCacheCopy = this.renderCache;
/*     */     
/* 443 */     if (initPacket || (this.fluidType == null && renderCacheCopy.fluidID != 0) || (this.fluidType != null && renderCacheCopy.fluidID != this.fluidType
/* 444 */       .getFluid().getID())) {
/* 445 */       changed = true;
/* 446 */       this.renderCache.fluidID = (this.fluidType != null) ? this.fluidType.getFluid().getID() : 0;
/* 447 */       this.renderCache.color = (this.fluidType != null) ? this.fluidType.getFluid().getColor(this.fluidType) : 0;
/* 448 */       this.renderCache.flags = FluidRenderData.getFlags(this.fluidType);
/* 449 */       delta.set(0);
/*     */     } 
/*     */     
/* 452 */     for (ForgeDirection dir : orientations) {
/* 453 */       int pamount = this.renderCache.amount[dir.ordinal()];
/* 454 */       int camount = (this.sections[dir.ordinal()]).amount;
/* 455 */       int displayQty = (pamount * 4 + camount) / 5;
/* 456 */       if ((displayQty == 0 && camount > 0) || initPacket) {
/* 457 */         displayQty = camount;
/*     */       }
/* 459 */       displayQty = Math.min(getCapacity(), displayQty);
/*     */       
/* 461 */       if (pamount != displayQty || initPacket) {
/* 462 */         changed = true;
/* 463 */         this.renderCache.amount[dir.ordinal()] = displayQty;
/* 464 */         delta.set(dir.ordinal() + 1);
/*     */       } 
/*     */     } 
/*     */     
/* 468 */     if (persistChange) {
/* 469 */       this.renderCache = renderCacheCopy;
/*     */     }
/*     */     
/* 472 */     if (changed || initPacket) {
/* 473 */       PacketFluidUpdate packet = new PacketFluidUpdate(this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e, initPacket, (getCapacity() > 255));
/* 474 */       packet.renderCache = renderCacheCopy;
/* 475 */       packet.delta = delta;
/* 476 */       return packet;
/*     */     } 
/*     */     
/* 479 */     return null;
/*     */   }
/*     */   
/*     */   private void setFluidType(FluidStack type) {
/* 483 */     this.fluidType = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendDescriptionPacket() {
/* 491 */     super.sendDescriptionPacket();
/*     */     
/* 493 */     PacketFluidUpdate update = computeFluidUpdate(true, true);
/* 494 */     BuildCraftTransport.instance.sendToPlayers((Packet)update, this.container.func_145831_w(), this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e, DefaultProps.PIPE_CONTENTS_RENDER_DIST);
/*     */   }
/*     */   
/*     */   public FluidStack getStack(ForgeDirection direction) {
/* 498 */     if (this.fluidType == null) {
/* 499 */       return null;
/*     */     }
/* 501 */     return new FluidStack(this.fluidType, (this.sections[direction.ordinal()]).amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropContents() {
/* 507 */     if (this.fluidType != null) {
/* 508 */       int totalAmount = 0;
/* 509 */       for (int i = 0; i < 7; i++) {
/* 510 */         totalAmount += (this.sections[i]).amount;
/*     */       }
/* 512 */       if (totalAmount > 0) {
/* 513 */         FluidEvent.fireEvent((FluidEvent)new FluidEvent.FluidSpilledEvent(new FluidStack(this.fluidType, totalAmount), 
/*     */               
/* 515 */               getWorld(), this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound) {
/* 523 */     super.readFromNBT(nbttagcompound);
/*     */     
/* 525 */     if (nbttagcompound.func_74764_b("fluid")) {
/* 526 */       setFluidType(FluidStack.loadFluidStackFromNBT(nbttagcompound.func_74775_l("fluid")));
/*     */     } else {
/* 528 */       setFluidType((FluidStack)null);
/*     */     } 
/*     */     
/* 531 */     for (ForgeDirection direction : orientations) {
/* 532 */       if (nbttagcompound.func_74764_b("tank[" + direction.ordinal() + "]")) {
/* 533 */         NBTTagCompound compound = nbttagcompound.func_74775_l("tank[" + direction.ordinal() + "]");
/* 534 */         if (compound.func_74764_b("FluidType")) {
/* 535 */           FluidStack stack = FluidStack.loadFluidStackFromNBT(compound);
/* 536 */           if (this.fluidType == null) {
/* 537 */             setFluidType(stack);
/*     */           }
/* 539 */           if (stack.isFluidEqual(this.fluidType)) {
/* 540 */             this.sections[direction.ordinal()].readFromNBT(compound);
/*     */           }
/*     */         } else {
/* 543 */           this.sections[direction.ordinal()].readFromNBT(compound);
/*     */         } 
/*     */       } 
/* 546 */       if (direction != ForgeDirection.UNKNOWN) {
/* 547 */         this.transferState[direction.ordinal()] = TransferState.values()[nbttagcompound.func_74765_d("transferState[" + direction.ordinal() + "]")];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound) {
/* 554 */     super.writeToNBT(nbttagcompound);
/*     */     
/* 556 */     if (this.fluidType != null) {
/* 557 */       NBTTagCompound fluidTag = new NBTTagCompound();
/* 558 */       this.fluidType.writeToNBT(fluidTag);
/* 559 */       nbttagcompound.func_74782_a("fluid", (NBTBase)fluidTag);
/*     */       
/* 561 */       for (ForgeDirection direction : orientations) {
/* 562 */         NBTTagCompound subTag = new NBTTagCompound();
/* 563 */         this.sections[direction.ordinal()].writeToNBT(subTag);
/* 564 */         nbttagcompound.func_74782_a("tank[" + direction.ordinal() + "]", (NBTBase)subTag);
/* 565 */         if (direction != ForgeDirection.UNKNOWN) {
/* 566 */           nbttagcompound.func_74777_a("transferState[" + direction.ordinal() + "]", (short)this.transferState[direction.ordinal()].ordinal());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/*     */     int filled;
/* 574 */     if (from != ForgeDirection.UNKNOWN && !inputOpen(from)) {
/* 575 */       return 0;
/*     */     }
/*     */     
/* 578 */     if (resource == null || (this.fluidType != null && !resource.isFluidEqual(this.fluidType))) {
/* 579 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 584 */     if (this.container.pipe instanceof IPipeTransportFluidsHook) {
/* 585 */       filled = ((IPipeTransportFluidsHook)this.container.pipe).fill(from, resource, doFill);
/*     */     } else {
/* 587 */       filled = this.sections[from.ordinal()].fill(resource.amount, doFill);
/*     */     } 
/*     */     
/* 590 */     if (doFill && filled > 0) {
/* 591 */       if (this.fluidType == null) {
/* 592 */         setFluidType(new FluidStack(resource, 0));
/*     */       }
/* 594 */       if (from != ForgeDirection.UNKNOWN) {
/* 595 */         this.transferState[from.ordinal()] = TransferState.Input;
/* 596 */         this.inputTTL[from.ordinal()] = INPUT_TTL;
/*     */       } 
/*     */     } 
/*     */     
/* 600 */     return filled;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/* 605 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/* 610 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/* 615 */     return inputOpen(from);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 620 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/* 625 */     return new FluidTankInfo[] { new FluidTankInfo(this.fluidType, (this.sections[from.ordinal()]).amount) };
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNeighborChange(ForgeDirection direction) {
/* 630 */     super.onNeighborChange(direction);
/*     */     
/* 632 */     if (!this.container.isPipeConnected(direction)) {
/* 633 */       this.sections[direction.ordinal()].reset();
/* 634 */       this.transferState[direction.ordinal()] = TransferState.None;
/* 635 */       this.renderCache.amount[direction.ordinal()] = 0;
/* 636 */       this.canReceiveCache[direction.ordinal()] = false;
/*     */     } else {
/* 638 */       this.canReceiveCache[direction.ordinal()] = canReceiveFluid(direction);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPipeConnect(TileEntity tile, ForgeDirection side) {
/* 644 */     if (tile instanceof IPipeTile) {
/* 645 */       Pipe<?> pipe2 = (Pipe)((IPipeTile)tile).getPipe();
/* 646 */       if (BlockGenericPipe.isValid(pipe2) && !(pipe2.transport instanceof PipeTransportFluids)) {
/* 647 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 651 */     if (tile instanceof IFluidHandler) {
/* 652 */       return true;
/*     */     }
/*     */     
/* 655 */     return tile instanceof IPipeTile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getDebugInfo(List<String> info, ForgeDirection side, ItemStack debugger, EntityPlayer player) {
/* 660 */     int[] amount = new int[7];
/* 661 */     for (int i = 0; i < 7; i++) {
/* 662 */       if (this.sections[i] != null) {
/* 663 */         amount[i] = (this.sections[i]).amount;
/*     */       }
/*     */     } 
/* 666 */     info.add(String.format("PipeTransportFluids (%s, %d mB, %d mB/t)", new Object[] { (this.fluidType != null) ? this.fluidType.getLocalizedName() : "Empty", Integer.valueOf(this.capacity), Integer.valueOf(this.flowRate) }));
/* 667 */     info.add("- Stored: " + Arrays.toString(amount));
/*     */   }
/*     */   
/*     */   static {
/* 671 */     fluidCapacities.put(PipeFluidsVoid.class, Integer.valueOf(1 * BuildCraftTransport.pipeFluidsBaseFlowRate));
/*     */     
/* 673 */     fluidCapacities.put(PipeFluidsWood.class, Integer.valueOf(1 * BuildCraftTransport.pipeFluidsBaseFlowRate));
/* 674 */     fluidCapacities.put(PipeFluidsCobblestone.class, Integer.valueOf(1 * BuildCraftTransport.pipeFluidsBaseFlowRate));
/*     */     
/* 676 */     fluidCapacities.put(PipeFluidsSandstone.class, Integer.valueOf(2 * BuildCraftTransport.pipeFluidsBaseFlowRate));
/* 677 */     fluidCapacities.put(PipeFluidsStone.class, Integer.valueOf(2 * BuildCraftTransport.pipeFluidsBaseFlowRate));
/*     */     
/* 679 */     fluidCapacities.put(PipeFluidsClay.class, Integer.valueOf(4 * BuildCraftTransport.pipeFluidsBaseFlowRate));
/* 680 */     fluidCapacities.put(PipeFluidsEmerald.class, Integer.valueOf(4 * BuildCraftTransport.pipeFluidsBaseFlowRate));
/* 681 */     fluidCapacities.put(PipeFluidsIron.class, Integer.valueOf(4 * BuildCraftTransport.pipeFluidsBaseFlowRate));
/* 682 */     fluidCapacities.put(PipeFluidsQuartz.class, Integer.valueOf(4 * BuildCraftTransport.pipeFluidsBaseFlowRate));
/*     */     
/* 684 */     fluidCapacities.put(PipeFluidsDiamond.class, Integer.valueOf(8 * BuildCraftTransport.pipeFluidsBaseFlowRate));
/* 685 */     fluidCapacities.put(PipeFluidsGold.class, Integer.valueOf(8 * BuildCraftTransport.pipeFluidsBaseFlowRate));
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipeTransportFluids.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */