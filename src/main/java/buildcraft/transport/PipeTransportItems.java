/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.api.tiles.IDebuggable;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.DefaultProps;
/*     */ import buildcraft.core.lib.inventory.Transactor;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import buildcraft.core.lib.utils.MathUtils;
/*     */ import buildcraft.transport.network.PacketPipeTransportItemStackRequest;
/*     */ import buildcraft.transport.network.PacketPipeTransportTraveler;
/*     */ import buildcraft.transport.pipes.events.PipeEvent;
/*     */ import buildcraft.transport.pipes.events.PipeEventItem;
/*     */ import buildcraft.transport.utils.TransportUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import org.apache.logging.log4j.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PipeTransportItems
/*     */   extends PipeTransport
/*     */   implements IDebuggable
/*     */ {
/*     */   public static final int MAX_PIPE_STACKS = 64;
/*     */   public static final int MAX_PIPE_ITEMS = 1024;
/*     */   public boolean allowBouncing = false;
/*  50 */   public final TravelerSet items = new TravelerSet(this);
/*     */ 
/*     */   
/*     */   public IPipeTile.PipeType getPipeType() {
/*  54 */     return IPipeTile.PipeType.ITEM;
/*     */   }
/*     */   
/*     */   public void readjustSpeed(TravelingItem item) {
/*  58 */     PipeEventItem.AdjustSpeed event = new PipeEventItem.AdjustSpeed(this.container.pipe, item);
/*  59 */     this.container.pipe.eventBus.handleEvent((Class)PipeEventItem.AdjustSpeed.class, (PipeEvent)event);
/*  60 */     if (!event.handled) {
/*  61 */       defaultReadjustSpeed(item, event.slowdownAmount);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void defaultReadjustSpeed(TravelingItem item, float slowdownAmount) {
/*  66 */     float speed = item.getSpeed();
/*     */     
/*  68 */     if (speed > 0.15F) {
/*  69 */       speed = 0.15F;
/*     */     }
/*     */     
/*  72 */     if (speed > 0.01F) {
/*  73 */       speed -= slowdownAmount;
/*     */     }
/*     */     
/*  76 */     if (speed < 0.01F) {
/*  77 */       speed = 0.01F;
/*     */     }
/*     */     
/*  80 */     item.setSpeed(speed);
/*     */   }
/*     */   
/*     */   private void readjustPosition(TravelingItem item) {
/*  84 */     double x = MathUtils.clamp(item.xCoord, this.container.field_145851_c + 0.01D, this.container.field_145851_c + 0.99D);
/*  85 */     double y = MathUtils.clamp(item.yCoord, this.container.field_145848_d + 0.01D, this.container.field_145848_d + 0.99D);
/*  86 */     double z = MathUtils.clamp(item.zCoord, this.container.field_145849_e + 0.01D, this.container.field_145849_e + 0.99D);
/*     */     
/*  88 */     if (item.input != ForgeDirection.UP && item.input != ForgeDirection.DOWN) {
/*  89 */       y = (this.container.field_145848_d + TransportUtils.getPipeFloorOf(item.getItemStack()));
/*     */     }
/*     */     
/*  92 */     item.setPosition(x, y, z);
/*     */   }
/*     */   
/*     */   public void injectItem(TravelingItem item, ForgeDirection inputOrientation) {
/*  96 */     if (item.isCorrupted()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 102 */     item.reset();
/* 103 */     item.input = inputOrientation;
/*     */     
/* 105 */     readjustSpeed(item);
/* 106 */     readjustPosition(item);
/*     */     
/* 108 */     PipeEventItem.Entered event = new PipeEventItem.Entered(this.container.pipe, item);
/* 109 */     this.container.pipe.eventBus.handleEvent((Class)PipeEventItem.Entered.class, (PipeEvent)event);
/* 110 */     if (event.cancelled) {
/*     */       return;
/*     */     }
/*     */     
/* 114 */     if (!(this.container.func_145831_w()).field_72995_K) {
/* 115 */       item.output = resolveDestination(item);
/*     */     }
/*     */     
/* 118 */     this.items.add(item);
/*     */     
/* 120 */     if (!(this.container.func_145831_w()).field_72995_K) {
/* 121 */       sendTravelerPacket(item, false);
/*     */       
/* 123 */       int itemStackCount = getNumberOfStacks();
/*     */       
/* 125 */       if (itemStackCount >= 32) {
/* 126 */         groupEntities();
/* 127 */         itemStackCount = getNumberOfStacks();
/*     */       } 
/*     */       
/* 130 */       if (itemStackCount > 64) {
/* 131 */         BCLog.logger.log(Level.WARN, String.format("Pipe exploded at %d,%d,%d because it had too many stacks: %d", new Object[] { Integer.valueOf(this.container.field_145851_c), Integer.valueOf(this.container.field_145848_d), Integer.valueOf(this.container.field_145849_e), Integer.valueOf(this.items.size()) }));
/* 132 */         destroyPipe();
/*     */         
/*     */         return;
/*     */       } 
/* 136 */       int numItems = getNumberOfItems();
/*     */       
/* 138 */       if (numItems > 1024) {
/* 139 */         BCLog.logger.log(Level.WARN, String.format("Pipe exploded at %d,%d,%d because it had too many items: %d", new Object[] { Integer.valueOf(this.container.field_145851_c), Integer.valueOf(this.container.field_145848_d), Integer.valueOf(this.container.field_145849_e), Integer.valueOf(numItems) }));
/* 140 */         destroyPipe();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void destroyPipe() {
/* 146 */     BlockUtils.explodeBlock(this.container.func_145831_w(), this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e);
/* 147 */     this.container.func_145831_w().func_147468_f(this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void reverseItem(TravelingItem item) {
/* 156 */     if (item.isCorrupted()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 162 */     item.toCenter = true;
/* 163 */     item.input = item.output.getOpposite();
/*     */     
/* 165 */     readjustSpeed(item);
/* 166 */     readjustPosition(item);
/*     */     
/* 168 */     PipeEventItem.Entered event = new PipeEventItem.Entered(this.container.pipe, item);
/* 169 */     this.container.pipe.eventBus.handleEvent((Class)PipeEventItem.Entered.class, (PipeEvent)event);
/* 170 */     if (event.cancelled) {
/*     */       return;
/*     */     }
/*     */     
/* 174 */     if (!(this.container.func_145831_w()).field_72995_K) {
/* 175 */       item.output = resolveDestination(item);
/*     */     }
/*     */     
/* 178 */     this.items.unscheduleRemoval(item);
/*     */     
/* 180 */     if (!(this.container.func_145831_w()).field_72995_K) {
/* 181 */       sendTravelerPacket(item, true);
/*     */     }
/*     */   }
/*     */   
/*     */   public ForgeDirection resolveDestination(TravelingItem data) {
/* 186 */     List<ForgeDirection> validDestinations = getPossibleMovements(data);
/*     */     
/* 188 */     if (validDestinations.isEmpty()) {
/* 189 */       return ForgeDirection.UNKNOWN;
/*     */     }
/*     */     
/* 192 */     return validDestinations.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ForgeDirection> getPossibleMovements(TravelingItem item) {
/* 200 */     LinkedList<ForgeDirection> result = new LinkedList<ForgeDirection>();
/*     */     
/* 202 */     item.blacklist.add(item.input.getOpposite());
/*     */     
/* 204 */     EnumSet<ForgeDirection> sides = EnumSet.complementOf(item.blacklist);
/* 205 */     sides.remove(ForgeDirection.UNKNOWN);
/*     */     
/* 207 */     for (ForgeDirection o : sides) {
/* 208 */       if (this.container.pipe.outputOpen(o) && canReceivePipeObjects(o, item)) {
/* 209 */         result.add(o);
/*     */       }
/*     */     } 
/*     */     
/* 213 */     PipeEventItem.FindDest event = new PipeEventItem.FindDest(this.container.pipe, item, result);
/* 214 */     this.container.pipe.eventBus.handleEvent((Class)PipeEventItem.FindDest.class, (PipeEvent)event);
/*     */     
/* 216 */     if (this.allowBouncing && result.isEmpty() && 
/* 217 */       canReceivePipeObjects(item.input.getOpposite(), item)) {
/* 218 */       result.add(item.input.getOpposite());
/*     */     }
/*     */ 
/*     */     
/* 222 */     if (event.shuffle) {
/* 223 */       Collections.shuffle(result);
/*     */     }
/*     */     
/* 226 */     return result;
/*     */   }
/*     */   
/*     */   private boolean canReceivePipeObjects(ForgeDirection o, TravelingItem item) {
/* 230 */     TileEntity entity = this.container.getTile(o);
/*     */     
/* 232 */     if (!this.container.isPipeConnected(o)) {
/* 233 */       return false;
/*     */     }
/*     */     
/* 236 */     if (entity instanceof IPipeTile) {
/* 237 */       Pipe<?> pipe = (Pipe)((IPipeTile)entity).getPipe();
/*     */       
/* 239 */       if (pipe == null || pipe.transport == null) {
/* 240 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 244 */       return (pipe.inputOpen(o.getOpposite()) && pipe.transport instanceof PipeTransportItems);
/* 245 */     }  if (entity instanceof IInventory && item.getInsertionHandler().canInsertItem(item, (IInventory)entity) && 
/* 246 */       (Transactor.getTransactorFor(entity).add(item.getItemStack(), o.getOpposite(), false)).field_77994_a > 0) {
/* 247 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 251 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEntity() {
/* 256 */     moveSolids();
/*     */   }
/*     */   
/*     */   private void moveSolids() {
/* 260 */     this.items.flush();
/*     */     
/* 262 */     this.items.iterating = true;
/* 263 */     for (TravelingItem item : this.items) {
/* 264 */       if (item.getContainer() != this.container) {
/* 265 */         this.items.scheduleRemoval(item);
/*     */         
/*     */         continue;
/*     */       } 
/* 269 */       switch (item.toCenter ? item.input : item.output) {
/*     */         case DOWN:
/* 271 */           item.movePosition(0.0D, -item.getSpeed(), 0.0D);
/*     */           break;
/*     */         case UP:
/* 274 */           item.movePosition(0.0D, item.getSpeed(), 0.0D);
/*     */           break;
/*     */         case WEST:
/* 277 */           item.movePosition(-item.getSpeed(), 0.0D, 0.0D);
/*     */           break;
/*     */         case EAST:
/* 280 */           item.movePosition(item.getSpeed(), 0.0D, 0.0D);
/*     */           break;
/*     */         case NORTH:
/* 283 */           item.movePosition(0.0D, 0.0D, -item.getSpeed());
/*     */           break;
/*     */         case SOUTH:
/* 286 */           item.movePosition(0.0D, 0.0D, item.getSpeed());
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 292 */       if ((item.toCenter && middleReached(item)) || outOfBounds(item)) {
/* 293 */         if (item.isCorrupted()) {
/* 294 */           this.items.remove(item);
/*     */           
/*     */           continue;
/*     */         } 
/* 298 */         item.toCenter = false;
/*     */ 
/*     */         
/* 301 */         item.setPosition(this.container.field_145851_c + 0.5D, (this.container.field_145848_d + TransportUtils.getPipeFloorOf(item.getItemStack())), this.container.field_145849_e + 0.5D);
/*     */         
/* 303 */         if (item.output == ForgeDirection.UNKNOWN) {
/* 304 */           if (this.items.scheduleRemoval(item))
/* 305 */             dropItem(item); 
/*     */           continue;
/*     */         } 
/* 308 */         PipeEventItem.ReachedCenter event = new PipeEventItem.ReachedCenter(this.container.pipe, item);
/* 309 */         this.container.pipe.eventBus.handleEvent((Class)PipeEventItem.ReachedCenter.class, (PipeEvent)event);
/*     */         continue;
/*     */       } 
/* 312 */       if (!item.toCenter && endReached(item)) {
/* 313 */         if (item.isCorrupted()) {
/* 314 */           this.items.remove(item);
/*     */           
/*     */           continue;
/*     */         } 
/* 318 */         if (item.output == ForgeDirection.UNKNOWN) {
/*     */           
/* 320 */           this.items.scheduleRemoval(item);
/* 321 */           BCLog.logger.warn("Glitched item [Output direction UNKNOWN] removed from world @ " + this.container.x() + ", " + this.container.y() + ", " + this.container.z() + "!");
/*     */           
/*     */           continue;
/*     */         } 
/* 325 */         TileEntity tile = this.container.getTile(item.output, true);
/*     */         
/* 327 */         PipeEventItem.ReachedEnd event = new PipeEventItem.ReachedEnd(this.container.pipe, item, tile);
/* 328 */         this.container.pipe.eventBus.handleEvent((Class)PipeEventItem.ReachedEnd.class, (PipeEvent)event);
/* 329 */         boolean handleItem = !event.handled;
/*     */ 
/*     */         
/* 332 */         if (handleItem && this.items.scheduleRemoval(item)) {
/* 333 */           handleTileReached(item, tile);
/*     */         }
/*     */       } 
/*     */     } 
/* 337 */     this.items.iterating = false;
/* 338 */     this.items.flush();
/*     */   }
/*     */   
/*     */   private boolean passToNextPipe(TravelingItem item, TileEntity tile) {
/* 342 */     if (tile instanceof IPipeTile) {
/* 343 */       Pipe<?> pipe = (Pipe)((IPipeTile)tile).getPipe();
/* 344 */       if (BlockGenericPipe.isValid(pipe) && pipe.transport instanceof PipeTransportItems) {
/* 345 */         ((PipeTransportItems)pipe.transport).injectItem(item, item.output);
/* 346 */         return true;
/*     */       } 
/*     */     } 
/* 349 */     return false;
/*     */   }
/*     */   
/*     */   private void handleTileReached(TravelingItem item, TileEntity tile) {
/* 353 */     if (!passToNextPipe(item, tile))
/*     */     {
/* 355 */       if (tile instanceof IInventory) {
/* 356 */         if (!(this.container.func_145831_w()).field_72995_K) {
/* 357 */           if (item.getInsertionHandler().canInsertItem(item, (IInventory)tile)) {
/* 358 */             ItemStack added = Transactor.getTransactorFor(tile).add(item.getItemStack(), item.output.getOpposite(), true);
/* 359 */             (item.getItemStack()).field_77994_a -= added.field_77994_a;
/*     */           } 
/*     */           
/* 362 */           if ((item.getItemStack()).field_77994_a > 0) {
/* 363 */             reverseItem(item);
/*     */           }
/*     */         } 
/*     */       } else {
/* 367 */         dropItem(item);
/*     */       }  } 
/*     */   }
/*     */   
/*     */   private void dropItem(TravelingItem item) {
/* 372 */     if ((this.container.func_145831_w()).field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 376 */     PipeEventItem.DropItem event = new PipeEventItem.DropItem(this.container.pipe, item, item.toEntityItem());
/* 377 */     this.container.pipe.eventBus.handleEvent((Class)PipeEventItem.DropItem.class, (PipeEvent)event);
/*     */     
/* 379 */     if (event.entity == null) {
/*     */       return;
/*     */     }
/*     */     
/* 383 */     EntityItem entity = event.entity;
/* 384 */     ForgeDirection direction = item.input;
/* 385 */     entity.func_70107_b(entity.field_70165_t + direction.offsetX * 0.5D, entity.field_70163_u + direction.offsetY * 0.5D, entity.field_70161_v + direction.offsetZ * 0.5D);
/*     */ 
/*     */ 
/*     */     
/* 389 */     entity
/* 390 */       .field_70159_w = (direction.offsetX * item.speed * 5.0F) + (getWorld()).field_73012_v.nextGaussian() * 0.1D;
/* 391 */     entity
/* 392 */       .field_70181_x = (direction.offsetY * item.speed * 5.0F) + (getWorld()).field_73012_v.nextGaussian() * 0.1D;
/* 393 */     entity
/* 394 */       .field_70179_y = (direction.offsetZ * item.speed * 5.0F) + (getWorld()).field_73012_v.nextGaussian() * 0.1D;
/*     */     
/* 396 */     this.container.func_145831_w().func_72838_d((Entity)entity);
/*     */   }
/*     */   
/*     */   protected boolean middleReached(TravelingItem item) {
/* 400 */     float middleLimit = item.getSpeed() * 1.01F;
/* 401 */     return (Math.abs(this.container.field_145851_c + 0.5D - item.xCoord) < middleLimit && 
/* 402 */       Math.abs((this.container.field_145848_d + TransportUtils.getPipeFloorOf(item.getItemStack())) - item.yCoord) < middleLimit && 
/*     */       
/* 404 */       Math.abs(this.container.field_145849_e + 0.5D - item.zCoord) < middleLimit);
/*     */   }
/*     */   
/*     */   protected boolean endReached(TravelingItem item) {
/* 408 */     return (item.xCoord > (this.container.field_145851_c + 1) || item.xCoord < this.container.field_145851_c || item.yCoord > (this.container.field_145848_d + 1) || item.yCoord < this.container.field_145848_d || item.zCoord > (this.container.field_145849_e + 1) || item.zCoord < this.container.field_145849_e);
/*     */   }
/*     */   
/*     */   protected boolean outOfBounds(TravelingItem item) {
/* 412 */     return (item.xCoord > (this.container.field_145851_c + 2) || item.xCoord < (this.container.field_145851_c - 1) || item.yCoord > (this.container.field_145848_d + 2) || item.yCoord < (this.container.field_145848_d - 1) || item.zCoord > (this.container.field_145849_e + 2) || item.zCoord < (this.container.field_145849_e - 1));
/*     */   }
/*     */   
/*     */   public Position getPosition() {
/* 416 */     return new Position(this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 421 */     super.readFromNBT(nbt);
/*     */     
/* 423 */     NBTTagList nbttaglist = nbt.func_150295_c("travelingEntities", 10);
/*     */     
/* 425 */     for (int j = 0; j < nbttaglist.func_74745_c(); j++) {
/*     */       try {
/* 427 */         NBTTagCompound dataTag = nbttaglist.func_150305_b(j);
/*     */         
/* 429 */         TravelingItem item = TravelingItem.make(dataTag);
/*     */         
/* 431 */         if (!item.isCorrupted())
/*     */         {
/*     */ 
/*     */           
/* 435 */           this.items.scheduleLoad(item); } 
/* 436 */       } catch (Throwable throwable) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 445 */     super.writeToNBT(nbt);
/*     */     
/* 447 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 449 */     for (TravelingItem item : this.items) {
/* 450 */       NBTTagCompound dataTag = new NBTTagCompound();
/* 451 */       nbttaglist.func_74742_a((NBTBase)dataTag);
/* 452 */       item.writeToNBT(dataTag);
/*     */     } 
/*     */     
/* 455 */     nbt.func_74782_a("travelingEntities", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doWork() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleTravelerPacket(PacketPipeTransportTraveler packet) {
/* 467 */     TravelingItem item = TravelingItem.clientCache.get(packet.getTravelingEntityId());
/*     */     
/* 469 */     if (item == null) {
/* 470 */       item = TravelingItem.make(packet.getTravelingEntityId());
/*     */     }
/*     */     
/* 473 */     if (item.getContainer() != this.container) {
/* 474 */       this.items.add(item);
/*     */     }
/*     */     
/* 477 */     if (packet.forceStackRefresh() || item.getItemStack() == null) {
/* 478 */       BuildCraftTransport.instance.sendToServer((Packet)new PacketPipeTransportItemStackRequest(packet.getTravelingEntityId()));
/*     */     }
/*     */     
/* 481 */     item.setPosition(packet.getItemX(), packet.getItemY(), packet.getItemZ());
/*     */     
/* 483 */     item.setSpeed(packet.getSpeed());
/*     */     
/* 485 */     item.toCenter = true;
/* 486 */     item.input = packet.getInputOrientation();
/* 487 */     item.output = packet.getOutputOrientation();
/* 488 */     item.color = packet.getColor();
/*     */   }
/*     */   
/*     */   private void sendTravelerPacket(TravelingItem data, boolean forceStackRefresh) {
/* 492 */     PacketPipeTransportTraveler packet = new PacketPipeTransportTraveler(data, forceStackRefresh);
/* 493 */     BuildCraftTransport.instance.sendToPlayers((Packet)packet, this.container.func_145831_w(), this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e, DefaultProps.PIPE_CONTENTS_RENDER_DIST);
/*     */   }
/*     */   
/*     */   public int getNumberOfStacks() {
/* 497 */     int num = 0;
/* 498 */     for (TravelingItem item : this.items) {
/* 499 */       if (!item.ignoreWeight()) {
/* 500 */         num++;
/*     */       }
/*     */     } 
/* 503 */     return num;
/*     */   }
/*     */   
/*     */   public int getNumberOfItems() {
/* 507 */     int num = 0;
/* 508 */     for (TravelingItem item : this.items) {
/* 509 */       if (!item.ignoreWeight() && item.getItemStack() != null) {
/* 510 */         num += (item.getItemStack()).field_77994_a;
/*     */       }
/*     */     } 
/* 513 */     return num;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void neighborChange() {}
/*     */ 
/*     */   
/*     */   public boolean canPipeConnect(TileEntity tile, ForgeDirection side) {
/* 521 */     if (tile instanceof IPipeTile) {
/* 522 */       Pipe<?> pipe2 = (Pipe)((IPipeTile)tile).getPipe();
/* 523 */       if (BlockGenericPipe.isValid(pipe2) && !(pipe2.transport instanceof PipeTransportItems)) {
/* 524 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 528 */     if (tile instanceof ISidedInventory) {
/* 529 */       int[] slots = ((ISidedInventory)tile).func_94128_d(side.getOpposite().ordinal());
/* 530 */       return (slots != null && slots.length > 0);
/*     */     } 
/*     */     
/* 533 */     return (tile instanceof IPipeTile || (tile instanceof IInventory && ((IInventory)tile).func_70302_i_() > 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void groupEntities() {
/* 541 */     for (TravelingItem item : this.items) {
/* 542 */       if (item.isCorrupted()) {
/*     */         continue;
/*     */       }
/* 545 */       for (TravelingItem otherItem : this.items) {
/* 546 */         if (item.tryMergeInto(otherItem)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropContents() {
/* 555 */     groupEntities();
/*     */     
/* 557 */     for (TravelingItem item : this.items) {
/* 558 */       if (!item.isCorrupted()) {
/* 559 */         this.container.pipe.dropItem(item.getItemStack());
/*     */       }
/*     */     } 
/*     */     
/* 563 */     this.items.clear();
/*     */   }
/*     */   
/*     */   public List<ItemStack> getDroppedItems() {
/* 567 */     groupEntities();
/*     */     
/* 569 */     ArrayList<ItemStack> itemsDropped = new ArrayList<ItemStack>(this.items.size());
/*     */     
/* 571 */     for (TravelingItem item : this.items) {
/* 572 */       if (!item.isCorrupted()) {
/* 573 */         itemsDropped.add(item.getItemStack());
/*     */       }
/*     */     } 
/*     */     
/* 577 */     return itemsDropped;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean delveIntoUnloadedChunks() {
/* 582 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getDebugInfo(List<String> info, ForgeDirection side, ItemStack debugger, EntityPlayer player) {
/* 587 */     info.add("PipeTransportItems");
/* 588 */     info.add("- Items: " + getNumberOfStacks() + "/" + '@' + " (" + getNumberOfItems() + "/" + 'Ѐ' + ")");
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipeTransportItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */