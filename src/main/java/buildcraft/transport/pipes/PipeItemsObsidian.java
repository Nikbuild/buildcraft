/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.core.lib.RFBattery;
/*     */ import buildcraft.core.lib.inventory.ITransactor;
/*     */ import buildcraft.core.lib.inventory.Transactor;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.core.lib.inventory.filters.StackFilter;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeTransport;
/*     */ import buildcraft.transport.PipeTransportItems;
/*     */ import buildcraft.transport.TransportProxy;
/*     */ import buildcraft.transport.TravelingItem;
/*     */ import buildcraft.transport.pipes.events.PipeEventItem;
/*     */ import buildcraft.transport.utils.TransportUtils;
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import java.util.WeakHashMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PipeItemsObsidian
/*     */   extends Pipe<PipeTransportItems>
/*     */   implements IEnergyHandler
/*     */ {
/*  46 */   private final RFBattery battery = new RFBattery(2560, 640, 0);
/*  47 */   private final WeakHashMap<Entity, Long> entityDropTime = new WeakHashMap<Entity, Long>();
/*     */   
/*     */   public PipeItemsObsidian(Item item) {
/*  50 */     super((PipeTransport)new PipeTransportItems(), item);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIconProvider getIconProvider() {
/*  56 */     return BuildCraftTransport.instance.pipeIconProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconIndex(ForgeDirection direction) {
/*  61 */     return PipeIconProvider.TYPE.PipeItemsObsidian.ordinal();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(Entity entity) {
/*  66 */     super.onEntityCollidedWithBlock(entity);
/*     */     
/*  68 */     if (entity.field_70128_L) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     if (canSuck(entity, 0)) {
/*  73 */       pullItemIntoPipe(entity, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private AxisAlignedBB getSuckingBox(ForgeDirection orientation, int distance) {
/*  78 */     if (orientation == ForgeDirection.UNKNOWN) {
/*  79 */       return null;
/*     */     }
/*  81 */     Position p1 = new Position(this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e, orientation);
/*  82 */     Position p2 = new Position(this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e, orientation);
/*     */     
/*  84 */     switch (orientation) {
/*     */       case EAST:
/*  86 */         p1.x += distance;
/*  87 */         p2.x += (1 + distance);
/*     */         break;
/*     */       case WEST:
/*  90 */         p1.x -= (distance - 1);
/*  91 */         p2.x -= distance;
/*     */         break;
/*     */       case UP:
/*     */       case DOWN:
/*  95 */         p1.x += (distance + 1);
/*  96 */         p2.x -= distance;
/*  97 */         p1.z += (distance + 1);
/*  98 */         p2.z -= distance;
/*     */         break;
/*     */       case SOUTH:
/* 101 */         p1.z += distance;
/* 102 */         p2.z += (distance + 1);
/*     */         break;
/*     */       
/*     */       default:
/* 106 */         p1.z -= (distance - 1);
/* 107 */         p2.z -= distance;
/*     */         break;
/*     */     } 
/*     */     
/* 111 */     switch (orientation)
/*     */     { case EAST:
/*     */       case WEST:
/* 114 */         p1.y += (distance + 1);
/* 115 */         p2.y -= distance;
/* 116 */         p1.z += (distance + 1);
/* 117 */         p2.z -= distance;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 137 */         min = p1.min(p2);
/* 138 */         max = p1.max(p2);
/*     */         
/* 140 */         return AxisAlignedBB.func_72330_a(min.x, min.y, min.z, max.x, max.y, max.z);case UP: p1.y += (distance + 1); p2.y += distance; min = p1.min(p2); max = p1.max(p2); return AxisAlignedBB.func_72330_a(min.x, min.y, min.z, max.x, max.y, max.z);case DOWN: p1.y -= (distance - 1); p2.y -= distance; min = p1.min(p2); max = p1.max(p2); return AxisAlignedBB.func_72330_a(min.x, min.y, min.z, max.x, max.y, max.z); }  p1.y += (distance + 1); p2.y -= distance; p1.x += (distance + 1); p2.x -= distance; Position min = p1.min(p2); Position max = p1.max(p2); return AxisAlignedBB.func_72330_a(min.x, min.y, min.z, max.x, max.y, max.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEntity() {
/* 145 */     super.updateEntity();
/*     */     
/* 147 */     if (this.battery.getEnergyStored() > 0) {
/* 148 */       for (int j = 1; j < 5; j++) {
/* 149 */         if (suckItem(j)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 154 */       this.battery.useEnergy(0, 5, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean suckItem(int distance) {
/* 159 */     AxisAlignedBB box = getSuckingBox(getOpenOrientation(), distance);
/*     */     
/* 161 */     if (box == null) {
/* 162 */       return false;
/*     */     }
/*     */     
/* 165 */     List<Entity> discoveredEntities = this.container.func_145831_w().func_72872_a(Entity.class, box);
/*     */     
/* 167 */     for (Entity entity : discoveredEntities) {
/* 168 */       if (canSuck(entity, distance)) {
/* 169 */         pullItemIntoPipe(entity, distance);
/* 170 */         return true;
/*     */       } 
/*     */       
/* 173 */       if (distance == 1 && entity instanceof EntityMinecart && entity instanceof net.minecraft.inventory.IInventory) {
/* 174 */         EntityMinecart cart = (EntityMinecart)entity;
/* 175 */         if (!cart.field_70128_L) {
/* 176 */           ITransactor trans = Transactor.getTransactorFor(cart);
/* 177 */           ForgeDirection openOrientation = getOpenOrientation();
/* 178 */           ItemStack stack = trans.remove((IStackFilter)StackFilter.ALL, openOrientation, false);
/*     */           
/* 180 */           if (stack != null && this.battery.useEnergy(10, 10, false) > 0) {
/* 181 */             stack = trans.remove((IStackFilter)StackFilter.ALL, openOrientation, true);
/* 182 */             if (stack != null) {
/* 183 */               TravelingItem item = TravelingItem.make(this.container.field_145851_c + 0.5D, (this.container.field_145848_d + TransportUtils.getPipeFloorOf(stack)), this.container.field_145849_e + 0.5D, stack);
/* 184 */               ((PipeTransportItems)this.transport).injectItem(item, openOrientation.getOpposite());
/*     */             } 
/* 186 */             return true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     return false;
/*     */   }
/*     */   
/*     */   public void pullItemIntoPipe(Entity entity, int distance) {
/* 196 */     if ((this.container.func_145831_w()).field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 200 */     ForgeDirection orientation = getOpenOrientation().getOpposite();
/*     */     
/* 202 */     if (orientation != ForgeDirection.UNKNOWN) {
/* 203 */       ItemStack stack; this.container.func_145831_w().func_72956_a(entity, "random.pop", 0.2F, (((this.container.func_145831_w()).field_73012_v.nextFloat() - (this.container.func_145831_w()).field_73012_v.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*     */ 
/*     */ 
/*     */       
/* 207 */       double speed = 0.009999999776482582D;
/*     */       
/* 209 */       if (entity instanceof EntityItem) {
/* 210 */         EntityItem entityItem = (EntityItem)entity;
/* 211 */         ItemStack contained = entityItem.func_92059_d();
/*     */         
/* 213 */         if (contained == null) {
/*     */           return;
/*     */         }
/*     */         
/* 217 */         TransportProxy.proxy.obsidianPipePickup(this.container.func_145831_w(), entityItem, (TileEntity)this.container);
/*     */         
/* 219 */         int energyUsed = Math.min(10 * contained.field_77994_a * distance, this.battery.getEnergyStored());
/*     */         
/* 221 */         if (distance == 0 || energyUsed / distance / 10 == contained.field_77994_a) {
/* 222 */           stack = contained;
/* 223 */           CoreProxy.proxy.removeEntity(entity);
/*     */         } else {
/* 225 */           stack = contained.func_77979_a(energyUsed / distance / 10);
/*     */         } 
/*     */         
/* 228 */         this.battery.useEnergy(energyUsed, energyUsed, false);
/*     */         
/* 230 */         speed = Math.sqrt(entityItem.field_70159_w * entityItem.field_70159_w + entityItem.field_70181_x * entityItem.field_70181_x + entityItem.field_70179_y * entityItem.field_70179_y);
/* 231 */         speed = speed / 2.0D - 0.05D;
/*     */         
/* 233 */         if (speed < 0.01D) {
/* 234 */           speed = 0.01D;
/*     */         }
/* 236 */       } else if (entity instanceof EntityArrow && this.battery.useEnergy(distance * 10, distance * 10, false) > 0) {
/* 237 */         stack = new ItemStack(Items.field_151032_g, 1);
/* 238 */         CoreProxy.proxy.removeEntity(entity);
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */       
/* 243 */       if (stack == null) {
/*     */         return;
/*     */       }
/*     */       
/* 247 */       TravelingItem item = TravelingItem.make(this.container.field_145851_c + 0.5D, (this.container.field_145848_d + TransportUtils.getPipeFloorOf(stack)), this.container.field_145849_e + 0.5D, stack);
/*     */       
/* 249 */       item.setSpeed((float)speed);
/*     */       
/* 251 */       ((PipeTransportItems)this.transport).injectItem(item, orientation);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void eventHandler(PipeEventItem.DropItem event) {
/* 256 */     this.entityDropTime.put(event.entity, Long.valueOf(event.entity.field_70170_p.func_82737_E() + 200L));
/*     */   }
/*     */   
/*     */   public boolean canSuck(Entity entity, int distance) {
/* 260 */     if (!entity.func_70089_S()) {
/* 261 */       return false;
/*     */     }
/* 263 */     if (entity instanceof EntityItem) {
/* 264 */       EntityItem item = (EntityItem)entity;
/*     */       
/* 266 */       if ((item.func_92059_d()).field_77994_a <= 0) {
/* 267 */         return false;
/*     */       }
/*     */       
/* 270 */       long wt = entity.field_70170_p.func_82737_E();
/* 271 */       if (this.entityDropTime.containsKey(entity) && ((Long)this.entityDropTime.get(entity)).longValue() >= wt) {
/* 272 */         return false;
/*     */       }
/*     */       
/* 275 */       return (this.battery.getEnergyStored() >= distance * 10);
/* 276 */     }  if (entity instanceof EntityArrow) {
/* 277 */       EntityArrow arrow = (EntityArrow)entity;
/* 278 */       return (arrow.field_70251_a == 1 && this.battery.getEnergyStored() >= distance * 10);
/*     */     } 
/* 280 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection from) {
/* 285 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
/* 291 */     return this.battery.receiveEnergy(maxReceive, simulate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
/* 297 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyStored(ForgeDirection from) {
/* 302 */     return this.battery.getEnergyStored();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergyStored(ForgeDirection from) {
/* 307 */     return this.battery.getMaxEnergyStored();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsObsidian.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */