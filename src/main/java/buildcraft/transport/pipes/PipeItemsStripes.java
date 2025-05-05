/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.api.transport.IStripesActivator;
/*     */ import buildcraft.api.transport.IStripesHandler;
/*     */ import buildcraft.api.transport.IStripesPipe;
/*     */ import buildcraft.api.transport.PipeManager;
/*     */ import buildcraft.core.lib.RFBattery;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeTransport;
/*     */ import buildcraft.transport.PipeTransportItems;
/*     */ import buildcraft.transport.TravelingItem;
/*     */ import buildcraft.transport.pipes.events.PipeEventItem;
/*     */ import buildcraft.transport.statements.ActionPipeDirection;
/*     */ import buildcraft.transport.utils.TransportUtils;
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.WorldServer;
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
/*     */ public class PipeItemsStripes
/*     */   extends Pipe<PipeTransportItems>
/*     */   implements IEnergyHandler, IStripesPipe
/*     */ {
/*  50 */   private RFBattery battery = new RFBattery(16000, 640, 0);
/*  51 */   private ForgeDirection actionDir = ForgeDirection.UNKNOWN;
/*     */   
/*     */   public PipeItemsStripes(Item item) {
/*  54 */     super((PipeTransport)new PipeTransportItems(), item);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEntity() {
/*  59 */     super.updateEntity();
/*     */     
/*  61 */     if ((this.container.func_145831_w()).field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  65 */     if (this.battery.getEnergyStored() >= 10) {
/*  66 */       ForgeDirection o = this.actionDir;
/*  67 */       if (o == ForgeDirection.UNKNOWN) {
/*  68 */         o = getOpenOrientation();
/*     */       }
/*     */       
/*  71 */       if (o != ForgeDirection.UNKNOWN) {
/*  72 */         Position p = new Position(this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e, o);
/*     */         
/*  74 */         p.moveForwards(1.0D);
/*     */         
/*  76 */         if (!BlockUtils.isUnbreakableBlock(getWorld(), (int)p.x, (int)p.y, (int)p.z)) {
/*  77 */           Block block = getWorld().func_147439_a((int)p.x, (int)p.y, (int)p.z);
/*  78 */           int metadata = getWorld().func_72805_g((int)p.x, (int)p.y, (int)p.z);
/*     */           
/*  80 */           if (block instanceof net.minecraft.block.BlockLiquid || block instanceof net.minecraftforge.fluids.IFluidBlock) {
/*     */             return;
/*     */           }
/*     */           
/*  84 */           ItemStack stack = new ItemStack(block, 1, metadata);
/*     */           
/*  86 */           EntityPlayer player = CoreProxy.proxy.getBuildCraftPlayer((WorldServer)getWorld(), (int)p.x, (int)p.y, (int)p.z).get();
/*     */           
/*  88 */           if (this.battery.useEnergy(10, 10, false) != 10) {
/*     */             return;
/*     */           }
/*     */           
/*  92 */           for (IStripesHandler handler : PipeManager.stripesHandlers) {
/*  93 */             if (handler.getType() == IStripesHandler.StripesHandlerType.BLOCK_BREAK && handler
/*  94 */               .shouldHandle(stack) && 
/*  95 */               handler.handle(getWorld(), (int)p.x, (int)p.y, (int)p.z, o, stack, player, (IStripesActivator)this)) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 102 */           ArrayList<ItemStack> stacks = block.getDrops(
/* 103 */               getWorld(), (int)p.x, (int)p.y, (int)p.z, metadata, 0);
/*     */ 
/*     */ 
/*     */           
/* 107 */           if (stacks != null) {
/* 108 */             for (ItemStack s : stacks) {
/* 109 */               if (s != null) {
/* 110 */                 sendItem(s, o.getOpposite());
/*     */               }
/*     */             } 
/*     */           }
/*     */           
/* 115 */           getWorld().func_147468_f((int)p.x, (int)p.y, (int)p.z);
/*     */         } 
/*     */       } 
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void eventHandler(PipeEventItem.DropItem event) {
/* 124 */     if ((this.container.func_145831_w()).field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 128 */     ForgeDirection direction = this.actionDir;
/* 129 */     if (direction == ForgeDirection.UNKNOWN) {
/* 130 */       direction = event.direction;
/*     */     }
/*     */     
/* 133 */     Position p = new Position(this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e, direction);
/*     */     
/* 135 */     p.moveForwards(1.0D);
/*     */     
/* 137 */     ItemStack stack = event.entity.func_92059_d();
/*     */     
/* 139 */     EntityPlayer player = CoreProxy.proxy.getBuildCraftPlayer((WorldServer)getWorld(), (int)p.x, (int)p.y, (int)p.z).get();
/*     */     
/* 141 */     switch (direction) {
/*     */       case DOWN:
/* 143 */         player.field_70125_A = 90.0F;
/* 144 */         player.field_70177_z = 0.0F;
/*     */         break;
/*     */       case UP:
/* 147 */         player.field_70125_A = 270.0F;
/* 148 */         player.field_70177_z = 0.0F;
/*     */         break;
/*     */       case NORTH:
/* 151 */         player.field_70125_A = 0.0F;
/* 152 */         player.field_70177_z = 180.0F;
/*     */         break;
/*     */       case SOUTH:
/* 155 */         player.field_70125_A = 0.0F;
/* 156 */         player.field_70177_z = 0.0F;
/*     */         break;
/*     */       case WEST:
/* 159 */         player.field_70125_A = 0.0F;
/* 160 */         player.field_70177_z = 90.0F;
/*     */         break;
/*     */       case EAST:
/* 163 */         player.field_70125_A = 0.0F;
/* 164 */         player.field_70177_z = 270.0F;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     for (IStripesHandler handler : PipeManager.stripesHandlers) {
/* 174 */       if (handler.getType() == IStripesHandler.StripesHandlerType.ITEM_USE && handler
/* 175 */         .shouldHandle(stack) && 
/* 176 */         handler.handle(getWorld(), (int)p.x, (int)p.y, (int)p.z, direction, stack, player, (IStripesActivator)this)) {
/*     */         
/* 178 */         event.entity = null;
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropItem(ItemStack itemStack, ForgeDirection direction) {
/* 187 */     Position p = new Position(this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e, direction);
/*     */     
/* 189 */     p.moveForwards(1.0D);
/*     */     
/* 191 */     InvUtils.dropItems(getWorld(), itemStack, (int)p.x, (int)p.y, (int)p.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkedList<IActionInternal> getActions() {
/* 196 */     LinkedList<IActionInternal> action = super.getActions();
/* 197 */     for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
/* 198 */       if (!this.container.isPipeConnected(direction)) {
/* 199 */         action.add(BuildCraftTransport.actionPipeDirection[direction.ordinal()]);
/*     */       }
/*     */     } 
/* 202 */     return action;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionsActivated(Collection<StatementSlot> actions) {
/* 207 */     super.actionsActivated(actions);
/*     */     
/* 209 */     this.actionDir = ForgeDirection.UNKNOWN;
/*     */     
/* 211 */     for (StatementSlot action : actions) {
/* 212 */       if (action.statement instanceof ActionPipeDirection) {
/* 213 */         this.actionDir = ((ActionPipeDirection)action.statement).direction;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendItem(ItemStack itemStack, ForgeDirection direction) {
/* 222 */     Position pos = new Position(this.container.field_145851_c + 0.5D, (this.container.field_145848_d + TransportUtils.getPipeFloorOf(itemStack)), this.container.field_145849_e + 0.5D, direction);
/*     */     
/* 224 */     pos.moveBackwards(0.25D);
/*     */     
/* 226 */     TravelingItem newItem = TravelingItem.make(pos.x, pos.y, pos.z, itemStack);
/*     */     
/* 228 */     ((PipeTransportItems)this.transport).injectItem(newItem, direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public IIconProvider getIconProvider() {
/* 233 */     return BuildCraftTransport.instance.pipeIconProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconIndex(ForgeDirection direction) {
/* 238 */     return PipeIconProvider.TYPE.Stripes.ordinal();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPipeConnect(TileEntity tile, ForgeDirection side) {
/* 243 */     if (tile instanceof IPipeTile) {
/* 244 */       IPipeTile tilePipe = (IPipeTile)tile;
/*     */       
/* 246 */       if (tilePipe.getPipe() instanceof PipeItemsStripes) {
/* 247 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 251 */     return super.canPipeConnect(tile, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection from) {
/* 256 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
/* 262 */     return this.battery.receiveEnergy(maxReceive, simulate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
/* 268 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyStored(ForgeDirection from) {
/* 273 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergyStored(ForgeDirection from) {
/* 278 */     return 10;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsStripes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */