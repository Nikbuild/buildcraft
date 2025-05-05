/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.gates.IGate;
/*     */ import buildcraft.api.statements.ActionState;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.api.transport.IPipe;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.api.transport.PipeWire;
/*     */ import buildcraft.core.internal.IDropControlInventory;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import buildcraft.transport.gates.GateFactory;
/*     */ import buildcraft.transport.statements.ActionValve;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Pipe<T extends PipeTransport>
/*     */   implements IDropControlInventory, IPipe
/*     */ {
/*  47 */   public int[] wireSignalStrength = new int[] { 0, 0, 0, 0 };
/*  48 */   public boolean[] wireSet = new boolean[] { false, false, false, false };
/*     */   
/*     */   public TileGenericPipe container;
/*     */   public final T transport;
/*     */   public final Item item;
/*  53 */   public final Gate[] gates = new Gate[ForgeDirection.VALID_DIRECTIONS.length];
/*  54 */   public PipeEventBus eventBus = new PipeEventBus();
/*     */   
/*     */   private boolean initialized = false;
/*     */   
/*     */   private boolean scheduleWireUpdate;
/*  59 */   private ArrayList<ActionState> actionStates = new ArrayList<ActionState>();
/*     */   
/*     */   public Pipe(T transport, Item item) {
/*  62 */     this.transport = transport;
/*  63 */     this.item = item;
/*     */     
/*  65 */     this.eventBus.registerHandler(this);
/*     */   }
/*     */   
/*     */   public void setTile(TileEntity tile) {
/*  69 */     this.container = (TileGenericPipe)tile;
/*  70 */     this.transport.setTile((TileGenericPipe)tile);
/*     */   }
/*     */   
/*     */   public void resolveActions() {
/*  74 */     for (Gate gate : this.gates) {
/*  75 */       if (gate != null) {
/*  76 */         gate.resolveActions();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean blockActivated(EntityPlayer player, ForgeDirection side) {
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   public void onBlockPlaced() {
/*  86 */     this.transport.onBlockPlaced();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(EntityLivingBase placer) {}
/*     */   
/*     */   public void onNeighborBlockChange(int blockId) {
/*  93 */     for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
/*  94 */       this.transport.onNeighborChange(d);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPipeConnect(TileEntity tile, ForgeDirection side) {
/* 101 */     if (tile instanceof IPipeTile) {
/* 102 */       Pipe<?> otherPipe = (Pipe)((IPipeTile)tile).getPipe();
/* 103 */       if (!BlockGenericPipe.isFullyDefined(otherPipe)) {
/* 104 */         return false;
/*     */       }
/*     */       
/* 107 */       if (!PipeConnectionBans.canPipesConnect((Class)getClass(), (Class)otherPipe.getClass())) {
/* 108 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 112 */     return this.transport.canPipeConnect(tile, side);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconIndexForItem() {
/* 121 */     return getIconIndex(ForgeDirection.UNKNOWN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public abstract IIconProvider getIconProvider();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getIconIndex(ForgeDirection paramForgeDirection);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateEntity() {
/* 144 */     this.transport.updateEntity();
/*     */     
/* 146 */     this.actionStates.clear();
/*     */ 
/*     */     
/* 149 */     if (!(this.container.func_145831_w()).field_72995_K) {
/* 150 */       for (Gate gate : this.gates) {
/* 151 */         if (gate != null) {
/* 152 */           gate.resolveActions();
/* 153 */           gate.tick();
/*     */         } 
/*     */       } 
/*     */       
/* 157 */       if (this.scheduleWireUpdate) {
/* 158 */         updateSignalState();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound data) {
/* 164 */     this.transport.writeToNBT(data);
/*     */     
/*     */     int i;
/* 167 */     for (i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/* 168 */       String key = "Gate[" + i + "]";
/* 169 */       Gate gate = this.gates[i];
/* 170 */       if (gate != null) {
/* 171 */         NBTTagCompound gateNBT = new NBTTagCompound();
/* 172 */         gate.writeToNBT(gateNBT);
/* 173 */         data.func_74782_a(key, (NBTBase)gateNBT);
/*     */       } else {
/* 175 */         data.func_82580_o(key);
/*     */       } 
/*     */     } 
/*     */     
/* 179 */     for (i = 0; i < 4; i++) {
/* 180 */       data.func_74757_a("wireSet[" + i + "]", this.wireSet[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound data) {
/* 185 */     this.transport.readFromNBT(data);
/*     */     int i;
/* 187 */     for (i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/* 188 */       String key = "Gate[" + i + "]";
/* 189 */       this.gates[i] = data.func_74764_b(key) ? GateFactory.makeGate(this, data.func_74775_l(key)) : null;
/*     */     } 
/*     */ 
/*     */     
/* 193 */     if (data.func_74764_b("Gate")) {
/* 194 */       ((PipeTransport)this.transport).container.setGate(GateFactory.makeGate(this, data.func_74775_l("Gate")), 0);
/*     */       
/* 196 */       data.func_82580_o("Gate");
/*     */     } 
/*     */     
/* 199 */     for (i = 0; i < 4; i++) {
/* 200 */       this.wireSet[i] = data.func_74767_n("wireSet[" + i + "]");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isInitialized() {
/* 205 */     return this.initialized;
/*     */   }
/*     */   
/*     */   public void initialize() {
/* 209 */     this.transport.initialize();
/* 210 */     updateSignalState();
/* 211 */     this.initialized = true;
/*     */   }
/*     */   
/*     */   public void updateSignalState() {
/* 215 */     for (PipeWire c : PipeWire.values()) {
/* 216 */       if (this.wireSet[c.ordinal()]) {
/* 217 */         updateSignalState(c);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateSignalState(PipeWire c) {
/* 223 */     int prevStrength = this.wireSignalStrength[c.ordinal()];
/* 224 */     boolean isBroadcast = false;
/*     */     
/* 226 */     for (Gate g : this.gates) {
/* 227 */       if (g != null && (g.broadcastSignal & 1 << c.ordinal()) != 0) {
/* 228 */         isBroadcast = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 233 */     if (isBroadcast) {
/* 234 */       if (prevStrength < 255) {
/* 235 */         propagateSignalState(c, 255);
/*     */       }
/*     */     } else {
/* 238 */       List<Pipe<?>> pipes = new ArrayList<Pipe<?>>();
/* 239 */       int maxStrength = 0;
/*     */       
/* 241 */       for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/* 242 */         TileEntity tile = this.container.getTile(dir);
/* 243 */         if (tile instanceof IPipeTile) {
/* 244 */           Pipe<?> pipe = (Pipe)((IPipeTile)tile).getPipe();
/* 245 */           if (isWireConnectedTo(tile, pipe, c, dir)) {
/* 246 */             pipes.add(pipe);
/* 247 */             int pipeStrength = pipe.wireSignalStrength[c.ordinal()];
/* 248 */             if (pipeStrength > maxStrength) {
/* 249 */               maxStrength = pipeStrength;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 255 */       if (maxStrength > prevStrength && maxStrength > 1) {
/* 256 */         this.wireSignalStrength[c.ordinal()] = maxStrength - 1;
/*     */       } else {
/* 258 */         this.wireSignalStrength[c.ordinal()] = 0;
/*     */       } 
/*     */       
/* 261 */       if (prevStrength != this.wireSignalStrength[c.ordinal()]) {
/* 262 */         this.container.scheduleRenderUpdate();
/*     */       }
/*     */       
/* 265 */       if (this.wireSignalStrength[c.ordinal()] == 0) {
/* 266 */         for (Pipe<?> p : pipes) {
/* 267 */           if (p.wireSignalStrength[c.ordinal()] > 0) {
/* 268 */             p.updateSignalState(c);
/*     */           }
/*     */         } 
/*     */       } else {
/* 272 */         for (Pipe<?> p : pipes) {
/* 273 */           if (p.wireSignalStrength[c.ordinal()] < this.wireSignalStrength[c.ordinal()] - 1) {
/* 274 */             p.updateSignalState(c);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void propagateSignalState(PipeWire c, int s) {
/* 282 */     this.wireSignalStrength[c.ordinal()] = s;
/* 283 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/* 284 */       TileEntity tile = this.container.getTile(dir);
/* 285 */       if (tile instanceof IPipeTile) {
/* 286 */         Pipe<?> pipe = (Pipe)((IPipeTile)tile).getPipe();
/* 287 */         if (isWireConnectedTo(tile, pipe, c, dir)) {
/* 288 */           if (s == 0) {
/* 289 */             if (pipe.wireSignalStrength[c.ordinal()] > 0) {
/* 290 */               pipe.updateSignalState(c);
/*     */             }
/*     */           }
/* 293 */           else if (pipe.wireSignalStrength[c.ordinal()] < s) {
/* 294 */             pipe.updateSignalState(c);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputOpen(ForgeDirection from) {
/* 303 */     return this.transport.inputOpen(from);
/*     */   }
/*     */   
/*     */   public boolean outputOpen(ForgeDirection to) {
/* 307 */     return this.transport.outputOpen(to);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(Entity entity) {}
/*     */   
/*     */   public boolean canConnectRedstone() {
/* 314 */     for (Gate gate : this.gates) {
/* 315 */       if (gate != null) {
/* 316 */         return true;
/*     */       }
/*     */     } 
/* 319 */     return false;
/*     */   }
/*     */   
/*     */   public int getMaxRedstoneOutput(ForgeDirection dir) {
/* 323 */     int output = 0;
/*     */     
/* 325 */     for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 326 */       output = Math.max(output, getRedstoneOutput(side));
/* 327 */       if (side == dir) {
/* 328 */         output = Math.max(output, getRedstoneOutputSide(side));
/*     */       }
/*     */     } 
/*     */     
/* 332 */     return output;
/*     */   }
/*     */   
/*     */   private int getRedstoneOutput(ForgeDirection dir) {
/* 336 */     Gate gate = this.gates[dir.ordinal()];
/*     */     
/* 338 */     return (gate != null) ? gate.getRedstoneOutput() : 0;
/*     */   }
/*     */   
/*     */   private int getRedstoneOutputSide(ForgeDirection dir) {
/* 342 */     Gate gate = this.gates[dir.ordinal()];
/*     */     
/* 344 */     return (gate != null) ? gate.getSidedRedstoneOutput() : 0;
/*     */   }
/*     */   
/*     */   public int isPoweringTo(int side) {
/* 348 */     ForgeDirection o = ForgeDirection.getOrientation(side).getOpposite();
/*     */     
/* 350 */     TileEntity tile = this.container.getTile(o);
/*     */     
/* 352 */     if (tile instanceof IPipeTile && this.container.isPipeConnected(o)) {
/* 353 */       return 0;
/*     */     }
/* 355 */     return getMaxRedstoneOutput(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int isIndirectlyPoweringTo(int l) {
/* 360 */     return isPoweringTo(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(Random random) {}
/*     */ 
/*     */   
/*     */   public boolean isWired(PipeWire color) {
/* 368 */     return this.wireSet[color.ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWireActive(PipeWire color) {
/* 373 */     return (this.wireSignalStrength[color.ordinal()] > 0);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public boolean hasGate() {
/* 378 */     for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
/* 379 */       if (hasGate(direction)) {
/* 380 */         return true;
/*     */       }
/*     */     } 
/* 383 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasGate(ForgeDirection side) {
/* 387 */     return this.container.hasGate(side);
/*     */   }
/*     */   
/*     */   protected void notifyBlocksOfNeighborChange(ForgeDirection side) {
/* 391 */     this.container.func_145831_w().func_147459_d(this.container.field_145851_c + side.offsetX, this.container.field_145848_d + side.offsetY, this.container.field_145849_e + side.offsetZ, (Block)BuildCraftTransport.genericPipeBlock);
/*     */   }
/*     */   
/*     */   protected void updateNeighbors(boolean needSelf) {
/* 395 */     if (needSelf) {
/* 396 */       this.container.func_145831_w().func_147459_d(this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e, (Block)BuildCraftTransport.genericPipeBlock);
/*     */     }
/* 398 */     for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 399 */       notifyBlocksOfNeighborChange(side);
/*     */     }
/*     */   }
/*     */   
/*     */   public void dropItem(ItemStack stack) {
/* 404 */     InvUtils.dropItems(this.container.func_145831_w(), stack, this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e);
/*     */   }
/*     */   
/*     */   public ArrayList<ItemStack> computeItemDrop() {
/* 408 */     ArrayList<ItemStack> result = new ArrayList<ItemStack>();
/*     */     
/* 410 */     for (PipeWire pipeWire : PipeWire.VALUES) {
/* 411 */       if (this.wireSet[pipeWire.ordinal()]) {
/* 412 */         result.add(pipeWire.getStack());
/*     */       }
/*     */     } 
/*     */     
/* 416 */     for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
/* 417 */       if (this.container.hasPipePluggable(direction)) {
/* 418 */         Collections.addAll(result, this.container.getPipePluggable(direction).getDropItems(this.container));
/*     */       }
/*     */     } 
/*     */     
/* 422 */     return result;
/*     */   }
/*     */   
/*     */   public LinkedList<IActionInternal> getActions() {
/* 426 */     LinkedList<IActionInternal> result = new LinkedList<IActionInternal>();
/*     */     
/* 428 */     for (ActionValve.ValveState state : ActionValve.ValveState.VALUES) {
/* 429 */       result.add(BuildCraftTransport.actionValve[state.ordinal()]);
/*     */     }
/*     */     
/* 432 */     return result;
/*     */   }
/*     */   
/*     */   public void resetGates() {
/* 436 */     for (int i = 0; i < this.gates.length; i++) {
/* 437 */       Gate gate = this.gates[i];
/* 438 */       if (gate != null) {
/* 439 */         gate.resetGate();
/*     */       }
/* 441 */       this.gates[i] = null;
/*     */     } 
/*     */     
/* 444 */     this.scheduleWireUpdate = true;
/* 445 */     this.container.scheduleRenderUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionsActivated(Collection<StatementSlot> actions) {}
/*     */   
/*     */   public TileGenericPipe getContainer() {
/* 452 */     return this.container;
/*     */   }
/*     */   
/*     */   public boolean isWireConnectedTo(TileEntity tile, PipeWire color, ForgeDirection direction) {
/* 456 */     if (!(tile instanceof IPipeTile)) {
/* 457 */       return false;
/*     */     }
/*     */     
/* 460 */     return isWireConnectedTo(tile, (Pipe)((IPipeTile)tile).getPipe(), color, direction);
/*     */   }
/*     */   
/*     */   public boolean isWireConnectedTo(TileEntity tile, Pipe<?> pipe, PipeWire color, ForgeDirection dir) {
/* 464 */     if (!BlockGenericPipe.isFullyDefined(pipe)) {
/* 465 */       return false;
/*     */     }
/*     */     
/* 468 */     if (!pipe.wireSet[color.ordinal()]) {
/* 469 */       return false;
/*     */     }
/*     */     
/* 472 */     if (this.container.hasBlockingPluggable(dir) || pipe.container.hasBlockingPluggable(dir.getOpposite())) {
/* 473 */       return false;
/*     */     }
/*     */     
/* 476 */     return (pipe.transport instanceof PipeTransportStructure || this.transport instanceof PipeTransportStructure || 
/* 477 */       Utils.checkPipesConnections(this.container, tile));
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropContents() {
/* 482 */     this.transport.dropContents();
/*     */   }
/*     */   
/*     */   public List<ItemStack> getDroppedItems() {
/* 486 */     return this.transport.getDroppedItems();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ForgeDirection getOpenOrientation() {
/* 493 */     int connectionsNum = 0;
/*     */     
/* 495 */     ForgeDirection targetOrientation = ForgeDirection.UNKNOWN;
/*     */     
/* 497 */     for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {
/* 498 */       if (this.container.isPipeConnected(o)) {
/*     */         
/* 500 */         connectionsNum++;
/*     */         
/* 502 */         if (connectionsNum == 1) {
/* 503 */           targetOrientation = o;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 508 */     if (connectionsNum > 1 || connectionsNum == 0) {
/* 509 */       return ForgeDirection.UNKNOWN;
/*     */     }
/*     */     
/* 512 */     return targetOrientation.getOpposite();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doDrop() {
/* 517 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChunkUnload() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 539 */     return this.container.func_145831_w();
/*     */   }
/*     */ 
/*     */   
/*     */   public IPipeTile getTile() {
/* 544 */     return this.container;
/*     */   }
/*     */ 
/*     */   
/*     */   public IGate getGate(ForgeDirection side) {
/* 549 */     if (side == ForgeDirection.UNKNOWN) {
/* 550 */       return null;
/*     */     }
/*     */     
/* 553 */     return this.gates[side.ordinal()];
/*     */   }
/*     */   
/*     */   private void pushActionState(ActionState state) {
/* 557 */     this.actionStates.add(state);
/*     */   }
/*     */   
/*     */   private Collection<ActionState> getActionStates() {
/* 561 */     return this.actionStates;
/*     */   }
/*     */   
/*     */   public void scheduleWireUpdate() {
/* 565 */     this.scheduleWireUpdate = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\Pipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */