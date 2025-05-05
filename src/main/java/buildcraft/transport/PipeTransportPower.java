/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.SafeTimeTracker;
/*     */ import buildcraft.api.tiles.IDebuggable;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.CompatHooks;
/*     */ import buildcraft.core.DefaultProps;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.utils.AverageInt;
/*     */ import buildcraft.transport.network.PacketPowerUpdate;
/*     */ import buildcraft.transport.pipes.PipePowerCobblestone;
/*     */ import buildcraft.transport.pipes.PipePowerDiamond;
/*     */ import buildcraft.transport.pipes.PipePowerEmerald;
/*     */ import buildcraft.transport.pipes.PipePowerGold;
/*     */ import buildcraft.transport.pipes.PipePowerIron;
/*     */ import buildcraft.transport.pipes.PipePowerQuartz;
/*     */ import buildcraft.transport.pipes.PipePowerSandstone;
/*     */ import buildcraft.transport.pipes.PipePowerStone;
/*     */ import buildcraft.transport.pipes.PipePowerWood;
/*     */ import cofh.api.energy.IEnergyConnection;
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import cofh.api.energy.IEnergyReceiver;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ 
/*     */ public class PipeTransportPower
/*     */   extends PipeTransport
/*     */   implements IDebuggable
/*     */ {
/*  49 */   public static final Map<Class<? extends Pipe<?>>, Integer> powerCapacities = new HashMap<Class<? extends Pipe<?>>, Integer>();
/*  50 */   public static final Map<Class<? extends Pipe<?>>, Float> powerResistances = new HashMap<Class<? extends Pipe<?>>, Float>();
/*     */   
/*     */   private static final int OVERLOAD_TICKS = 60;
/*     */   
/*  54 */   public short[] displayPower = new short[6];
/*  55 */   public int[] nextPowerQuery = new int[6];
/*  56 */   public double[] internalNextPower = new double[6];
/*     */   public int overload;
/*  58 */   public int maxPower = 80;
/*     */   
/*     */   public float powerResistance;
/*  61 */   public int[] dbgEnergyInput = new int[6];
/*  62 */   public int[] dbgEnergyOutput = new int[6];
/*  63 */   public int[] dbgEnergyOffered = new int[6];
/*     */   
/*  65 */   private final AverageInt[] powerAverage = new AverageInt[6];
/*  66 */   private final TileEntity[] tiles = new TileEntity[6];
/*  67 */   private final Object[] providers = new Object[6];
/*     */   
/*     */   private boolean needsInit = true;
/*     */   
/*  71 */   private int[] powerQuery = new int[6];
/*     */   
/*     */   private long currentDate;
/*  74 */   private double[] internalPower = new double[6];
/*     */   
/*  76 */   private SafeTimeTracker tracker = new SafeTimeTracker((2 * BuildCraftCore.updateFactor));
/*     */   
/*     */   public PipeTransportPower() {
/*  79 */     for (int i = 0; i < 6; i++) {
/*  80 */       this.powerQuery[i] = 0;
/*  81 */       this.powerAverage[i] = new AverageInt(10);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IPipeTile.PipeType getPipeType() {
/*  87 */     return IPipeTile.PipeType.POWER;
/*     */   }
/*     */   
/*     */   public void initFromPipe(Class<? extends Pipe<?>> pipeClass) {
/*  91 */     if (BuildCraftTransport.usePipeLoss) {
/*  92 */       this.maxPower = 10240;
/*  93 */       this.powerResistance = ((Float)powerResistances.get(pipeClass)).floatValue();
/*     */     } else {
/*  95 */       this.maxPower = ((Integer)powerCapacities.get(pipeClass)).intValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPipeConnect(TileEntity tile, ForgeDirection side) {
/* 101 */     if (tile instanceof IPipeTile) {
/* 102 */       Pipe<?> pipe2 = (Pipe)((IPipeTile)tile).getPipe();
/* 103 */       if (BlockGenericPipe.isValid(pipe2) && !(pipe2.transport instanceof PipeTransportPower)) {
/* 104 */         return false;
/*     */       }
/* 106 */       return true;
/*     */     } 
/*     */     
/* 109 */     if (this.container.pipe instanceof PipePowerWood) {
/* 110 */       return isPowerSource(tile, side);
/*     */     }
/* 112 */     if (tile instanceof buildcraft.api.power.IEngine)
/*     */     {
/* 114 */       return false;
/*     */     }
/*     */     
/* 117 */     Object provider = CompatHooks.INSTANCE.getEnergyProvider(tile);
/*     */     
/* 119 */     if (provider instanceof IEnergyHandler || provider instanceof IEnergyReceiver) {
/* 120 */       IEnergyConnection handler = (IEnergyConnection)provider;
/* 121 */       if (handler.canConnectEnergy(side.getOpposite())) {
/* 122 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 127 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isPowerSource(TileEntity tile, ForgeDirection side) {
/* 131 */     if (tile instanceof buildcraft.core.lib.block.TileBuildCraft && !(tile instanceof buildcraft.api.power.IEngine))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 136 */       return false;
/*     */     }
/*     */     
/* 139 */     if (tile instanceof buildcraft.api.power.IRedstoneEngine)
/*     */     {
/*     */       
/* 142 */       return false;
/*     */     }
/*     */     
/* 145 */     return (tile instanceof IEnergyConnection && ((IEnergyConnection)tile).canConnectEnergy(side.getOpposite()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNeighborChange(ForgeDirection side) {
/* 150 */     super.onNeighborChange(side);
/* 151 */     updateTile(side);
/*     */   }
/*     */   
/*     */   private void updateTile(ForgeDirection side) {
/* 155 */     int o = side.ordinal();
/* 156 */     TileEntity tile = this.container.getTile(side);
/* 157 */     if (tile != null && this.container.isPipeConnected(side)) {
/* 158 */       this.tiles[o] = tile;
/*     */     } else {
/* 160 */       this.tiles[o] = null;
/* 161 */       this.internalPower[o] = 0.0D;
/* 162 */       this.internalNextPower[o] = 0.0D;
/* 163 */       this.powerAverage[o].clear();
/*     */     } 
/* 165 */     this.providers[o] = getEnergyProvider(o);
/*     */   }
/*     */   
/*     */   private void init() {
/* 169 */     if (this.needsInit) {
/* 170 */       this.needsInit = false;
/* 171 */       for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 172 */         updateTile(side);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private Object getEnergyProvider(int side) {
/* 178 */     ForgeDirection fs = ForgeDirection.getOrientation(side);
/* 179 */     if (this.container.hasPipePluggable(fs)) {
/* 180 */       Object pp = this.container.getPipePluggable(fs);
/* 181 */       if (pp instanceof IEnergyReceiver) {
/* 182 */         return pp;
/*     */       }
/*     */     } 
/* 185 */     return CompatHooks.INSTANCE.getEnergyProvider(this.tiles[side]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEntity() {
/* 190 */     if ((this.container.func_145831_w()).field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 194 */     step();
/*     */     
/* 196 */     init();
/*     */     
/* 198 */     for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 199 */       if (this.tiles[side.ordinal()] != null && this.tiles[side.ordinal()].func_145837_r()) {
/* 200 */         updateTile(side);
/*     */       }
/*     */     } 
/*     */     
/* 204 */     for (int i = 0; i < 6; i++) {
/* 205 */       if (this.internalPower[i] > 0.0D) {
/* 206 */         int totalPowerQuery = 0;
/* 207 */         for (int m = 0; m < 6; m++) {
/* 208 */           if (m != i && this.powerQuery[m] > 0) {
/* 209 */             Object ep = this.providers[m];
/* 210 */             if (ep instanceof IPipeTile || ep instanceof IEnergyReceiver || ep instanceof IEnergyHandler) {
/* 211 */               totalPowerQuery += this.powerQuery[m];
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 216 */         if (totalPowerQuery > 0) {
/* 217 */           int unusedPowerQuery = totalPowerQuery;
/* 218 */           for (int n = 0; n < 6; n++) {
/* 219 */             if (n != i && this.powerQuery[n] > 0) {
/* 220 */               Object ep = this.providers[n];
/* 221 */               double watts = Math.min(this.internalPower[i] * this.powerQuery[n] / unusedPowerQuery, this.internalPower[i]);
/* 222 */               unusedPowerQuery -= this.powerQuery[n];
/*     */               
/* 224 */               if (ep instanceof IPipeTile && ((IPipeTile)ep).getPipeType() == IPipeTile.PipeType.POWER) {
/* 225 */                 Pipe<?> nearbyPipe = (Pipe)((IPipeTile)ep).getPipe();
/* 226 */                 PipeTransportPower nearbyTransport = (PipeTransportPower)nearbyPipe.transport;
/* 227 */                 watts = nearbyTransport.receiveEnergy(ForgeDirection.VALID_DIRECTIONS[n]
/* 228 */                     .getOpposite(), watts);
/*     */                 
/* 230 */                 this.internalPower[i] = this.internalPower[i] - watts;
/* 231 */                 this.dbgEnergyOutput[n] = (int)(this.dbgEnergyOutput[n] + watts);
/*     */                 
/* 233 */                 this.powerAverage[n].push((int)Math.ceil(watts));
/* 234 */                 this.powerAverage[i].push((int)Math.ceil(watts));
/*     */               } else {
/* 236 */                 int iWatts = (int)watts;
/* 237 */                 if (ep instanceof IEnergyHandler) {
/* 238 */                   IEnergyHandler handler = (IEnergyHandler)ep;
/* 239 */                   if (handler.canConnectEnergy(ForgeDirection.VALID_DIRECTIONS[n].getOpposite())) {
/* 240 */                     iWatts = handler.receiveEnergy(ForgeDirection.VALID_DIRECTIONS[n].getOpposite(), iWatts, false);
/*     */                   }
/*     */                 }
/* 243 */                 else if (ep instanceof IEnergyReceiver) {
/* 244 */                   IEnergyReceiver handler = (IEnergyReceiver)ep;
/* 245 */                   if (handler.canConnectEnergy(ForgeDirection.VALID_DIRECTIONS[n].getOpposite())) {
/* 246 */                     iWatts = handler.receiveEnergy(ForgeDirection.VALID_DIRECTIONS[n].getOpposite(), iWatts, false);
/*     */                   }
/*     */                 } 
/*     */ 
/*     */                 
/* 251 */                 this.internalPower[i] = this.internalPower[i] - iWatts;
/* 252 */                 this.dbgEnergyOutput[n] = this.dbgEnergyOutput[n] + iWatts;
/*     */                 
/* 254 */                 this.powerAverage[n].push(iWatts);
/* 255 */                 this.powerAverage[i].push(iWatts);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 263 */     short highestPower = 0;
/* 264 */     for (int j = 0; j < 6; j++) {
/* 265 */       this.powerAverage[j].tick();
/* 266 */       this.displayPower[j] = (short)(int)Math.round(this.powerAverage[j].getAverage());
/* 267 */       if (this.displayPower[j] > highestPower) {
/* 268 */         highestPower = this.displayPower[j];
/*     */       }
/*     */     } 
/*     */     
/* 272 */     this.overload += (highestPower > this.maxPower * 0.95F) ? 1 : -1;
/* 273 */     if (this.overload < 0) {
/* 274 */       this.overload = 0;
/*     */     }
/* 276 */     if (this.overload > 60) {
/* 277 */       this.overload = 60;
/*     */     }
/*     */ 
/*     */     
/* 281 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/* 282 */       if (outputOpen(dir)) {
/*     */ 
/*     */ 
/*     */         
/* 286 */         Object tile = this.providers[dir.ordinal()];
/*     */         
/* 288 */         if (!(tile instanceof IPipeTile) || ((IPipeTile)tile)
/* 289 */           .getPipe() == null || 
/* 290 */           !(((Pipe)((IPipeTile)tile).getPipe()).transport instanceof PipeTransportPower))
/*     */         {
/*     */           
/* 293 */           if (tile instanceof IEnergyHandler) {
/* 294 */             IEnergyHandler handler = (IEnergyHandler)tile;
/* 295 */             if (handler.canConnectEnergy(dir.getOpposite())) {
/* 296 */               int request = handler.receiveEnergy(dir.getOpposite(), this.maxPower, true);
/* 297 */               if (request > 0) {
/* 298 */                 requestEnergy(dir, request);
/*     */               }
/*     */             } 
/* 301 */           } else if (tile instanceof IEnergyReceiver) {
/* 302 */             IEnergyReceiver handler = (IEnergyReceiver)tile;
/* 303 */             if (handler.canConnectEnergy(dir.getOpposite())) {
/* 304 */               int request = handler.receiveEnergy(dir.getOpposite(), this.maxPower, true);
/* 305 */               if (request > 0) {
/* 306 */                 requestEnergy(dir, request);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 313 */     int[] transferQuery = new int[6]; int k;
/* 314 */     for (k = 0; k < 6; k++) {
/* 315 */       transferQuery[k] = 0;
/* 316 */       if (inputOpen(ForgeDirection.getOrientation(k))) {
/*     */ 
/*     */         
/* 319 */         for (int m = 0; m < 6; m++) {
/* 320 */           if (m != k) {
/* 321 */             transferQuery[k] = transferQuery[k] + this.powerQuery[m];
/*     */           }
/*     */         } 
/* 324 */         transferQuery[k] = Math.min(transferQuery[k], this.maxPower);
/*     */       } 
/*     */     } 
/*     */     
/* 328 */     for (k = 0; k < 6; k++) {
/* 329 */       if (transferQuery[k] != 0 && this.tiles[k] != null) {
/* 330 */         TileEntity entity = this.tiles[k];
/* 331 */         if (entity instanceof IPipeTile && ((IPipeTile)entity).getPipeType() == IPipeTile.PipeType.POWER) {
/* 332 */           IPipeTile nearbyTile = (IPipeTile)entity;
/* 333 */           if (nearbyTile.getPipe() != null && nearbyTile.getPipeType() == IPipeTile.PipeType.POWER) {
/*     */ 
/*     */             
/* 336 */             PipeTransportPower nearbyTransport = (PipeTransportPower)((Pipe)nearbyTile.getPipe()).transport;
/* 337 */             nearbyTransport.requestEnergy(ForgeDirection.VALID_DIRECTIONS[k].getOpposite(), transferQuery[k]);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 343 */     if (this.tracker.markTimeIfDelay(this.container.func_145831_w())) {
/* 344 */       PacketPowerUpdate packet = new PacketPowerUpdate(this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e);
/*     */       
/* 346 */       packet.displayPower = this.displayPower;
/* 347 */       packet.overload = isOverloaded();
/* 348 */       BuildCraftTransport.instance.sendToPlayers((Packet)packet, this.container.func_145831_w(), this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e, DefaultProps.PIPE_CONTENTS_RENDER_DIST);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isOverloaded() {
/* 353 */     return (this.overload >= 60);
/*     */   }
/*     */   
/*     */   private void step() {
/* 357 */     if (this.container != null && this.container.func_145831_w() != null && this.currentDate != this.container
/* 358 */       .func_145831_w().func_82737_E()) {
/* 359 */       this.currentDate = this.container.func_145831_w().func_82737_E();
/*     */       
/* 361 */       Arrays.fill(this.dbgEnergyInput, 0);
/* 362 */       Arrays.fill(this.dbgEnergyOffered, 0);
/* 363 */       Arrays.fill(this.dbgEnergyOutput, 0);
/*     */       
/* 365 */       this.powerQuery = this.nextPowerQuery;
/* 366 */       this.nextPowerQuery = new int[6];
/*     */       
/* 368 */       double[] next = this.internalPower;
/* 369 */       this.internalPower = this.internalNextPower;
/* 370 */       this.internalNextPower = next;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double receiveEnergy(ForgeDirection from, double tVal) {
/* 380 */     int side = from.ordinal();
/* 381 */     double val = tVal;
/*     */     
/* 383 */     step();
/*     */     
/* 385 */     this.dbgEnergyOffered[side] = (int)(this.dbgEnergyOffered[side] + val);
/*     */     
/* 387 */     if (this.container.pipe instanceof IPipeTransportPowerHook) {
/* 388 */       double ret = ((IPipeTransportPowerHook)this.container.pipe).receiveEnergy(from, (int)val);
/* 389 */       if (ret >= 0.0D) {
/* 390 */         return ret;
/*     */       }
/*     */     } 
/*     */     
/* 394 */     if (this.internalNextPower[side] > this.maxPower) {
/* 395 */       return 0.0D;
/*     */     }
/*     */     
/* 398 */     if (BuildCraftTransport.usePipeLoss) {
/* 399 */       this.internalNextPower[side] = this.internalNextPower[side] + val * (1.0F - this.powerResistance);
/*     */     } else {
/* 401 */       this.internalNextPower[side] = this.internalNextPower[side] + val;
/*     */     } 
/*     */     
/* 404 */     if (this.internalNextPower[side] > this.maxPower) {
/* 405 */       val -= this.internalNextPower[side] - this.maxPower;
/* 406 */       this.internalNextPower[side] = this.maxPower;
/* 407 */       if (val < 0.0D) {
/* 408 */         val = 0.0D;
/*     */       }
/*     */     } 
/*     */     
/* 412 */     this.dbgEnergyInput[side] = (int)(this.dbgEnergyInput[side] + val);
/*     */     
/* 414 */     return val;
/*     */   }
/*     */   
/*     */   public void requestEnergy(ForgeDirection from, int amount) {
/* 418 */     step();
/*     */     
/* 420 */     if (this.container.pipe instanceof IPipeTransportPowerHook) {
/* 421 */       this.nextPowerQuery[from.ordinal()] = this.nextPowerQuery[from.ordinal()] + ((IPipeTransportPowerHook)this.container.pipe).requestEnergy(from, amount);
/*     */     } else {
/* 423 */       this.nextPowerQuery[from.ordinal()] = this.nextPowerQuery[from.ordinal()] + amount;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/* 429 */     this.currentDate = this.container.func_145831_w().func_82737_E();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound) {
/* 434 */     super.readFromNBT(nbttagcompound);
/*     */     
/* 436 */     for (int i = 0; i < 6; i++) {
/* 437 */       this.powerQuery[i] = nbttagcompound.func_74762_e("powerQuery[" + i + "]");
/* 438 */       this.nextPowerQuery[i] = nbttagcompound.func_74762_e("nextPowerQuery[" + i + "]");
/* 439 */       this.internalPower[i] = nbttagcompound.func_74762_e("internalPower[" + i + "]");
/* 440 */       this.internalNextPower[i] = nbttagcompound.func_74762_e("internalNextPower[" + i + "]");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound) {
/* 447 */     super.writeToNBT(nbttagcompound);
/*     */     
/* 449 */     for (int i = 0; i < 6; i++) {
/* 450 */       nbttagcompound.func_74768_a("powerQuery[" + i + "]", this.powerQuery[i]);
/* 451 */       nbttagcompound.func_74768_a("nextPowerQuery[" + i + "]", this.nextPowerQuery[i]);
/* 452 */       nbttagcompound.func_74780_a("internalPower[" + i + "]", this.internalPower[i]);
/* 453 */       nbttagcompound.func_74780_a("internalNextPower[" + i + "]", this.internalNextPower[i]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlePowerPacket(PacketPowerUpdate packetPower) {
/* 463 */     this.displayPower = packetPower.displayPower;
/* 464 */     this.overload = packetPower.overload ? 60 : 0;
/*     */   }
/*     */   
/*     */   public boolean isQueryingPower() {
/* 468 */     for (int d : this.powerQuery) {
/* 469 */       if (d > 0) {
/* 470 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 474 */     return false;
/*     */   }
/*     */   
/*     */   static {
/* 478 */     powerCapacities.put(PipePowerCobblestone.class, Integer.valueOf(80));
/* 479 */     powerCapacities.put(PipePowerStone.class, Integer.valueOf(160));
/* 480 */     powerCapacities.put(PipePowerWood.class, Integer.valueOf(320));
/* 481 */     powerCapacities.put(PipePowerSandstone.class, Integer.valueOf(320));
/* 482 */     powerCapacities.put(PipePowerQuartz.class, Integer.valueOf(640));
/* 483 */     powerCapacities.put(PipePowerIron.class, Integer.valueOf(1280));
/* 484 */     powerCapacities.put(PipePowerGold.class, Integer.valueOf(2560));
/* 485 */     powerCapacities.put(PipePowerEmerald.class, Integer.valueOf(2560));
/* 486 */     powerCapacities.put(PipePowerDiamond.class, Integer.valueOf(10240));
/*     */     
/* 488 */     powerResistances.put(PipePowerCobblestone.class, Float.valueOf(0.05F));
/* 489 */     powerResistances.put(PipePowerStone.class, Float.valueOf(0.025F));
/* 490 */     powerResistances.put(PipePowerWood.class, Float.valueOf(0.0F));
/* 491 */     powerResistances.put(PipePowerSandstone.class, Float.valueOf(0.0125F));
/* 492 */     powerResistances.put(PipePowerQuartz.class, Float.valueOf(0.0125F));
/* 493 */     powerResistances.put(PipePowerIron.class, Float.valueOf(0.0125F));
/* 494 */     powerResistances.put(PipePowerGold.class, Float.valueOf(0.003125F));
/* 495 */     powerResistances.put(PipePowerEmerald.class, Float.valueOf(0.0F));
/* 496 */     powerResistances.put(PipePowerDiamond.class, Float.valueOf(0.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public void getDebugInfo(List<String> info, ForgeDirection side, ItemStack debugger, EntityPlayer player) {
/* 501 */     info.add("PipeTransportPower (" + this.maxPower + " RF/t)");
/* 502 */     info.add("- internalPower: " + Arrays.toString(this.internalPower) + " <- " + Arrays.toString(this.internalNextPower));
/* 503 */     info.add("- powerQuery: " + Arrays.toString(this.powerQuery) + " <- " + Arrays.toString(this.nextPowerQuery));
/* 504 */     info.add("- energy: IN " + Arrays.toString(this.dbgEnergyInput) + ", OUT " + Arrays.toString(this.dbgEnergyOutput));
/* 505 */     info.add("- energy: OFFERED " + Arrays.toString(this.dbgEnergyOffered));
/*     */     
/* 507 */     int[] totalPowerQuery = new int[6];
/* 508 */     for (int i = 0; i < 6; i++) {
/* 509 */       if (this.internalPower[i] > 0.0D) {
/* 510 */         for (int j = 0; j < 6; j++) {
/* 511 */           if (j != i && this.powerQuery[j] > 0) {
/* 512 */             Object ep = this.providers[j];
/* 513 */             if (ep instanceof IPipeTile || ep instanceof IEnergyReceiver || ep instanceof IEnergyHandler) {
/* 514 */               totalPowerQuery[i] = totalPowerQuery[i] + this.powerQuery[j];
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 521 */     info.add("- totalPowerQuery: " + Arrays.toString(totalPowerQuery));
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipeTransportPower.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */