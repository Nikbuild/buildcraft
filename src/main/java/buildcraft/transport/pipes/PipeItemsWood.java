/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.core.lib.RFBattery;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.lib.inventory.InventoryWrapper;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeTransport;
/*     */ import buildcraft.transport.PipeTransportItems;
/*     */ import buildcraft.transport.TravelingItem;
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ public class PipeItemsWood
/*     */   extends Pipe<PipeTransportItems>
/*     */   implements IEnergyHandler
/*     */ {
/*  37 */   protected RFBattery battery = new RFBattery(2560, 80, 0);
/*     */   
/*  39 */   protected int standardIconIndex = PipeIconProvider.TYPE.PipeItemsWood_Standard.ordinal();
/*  40 */   protected int solidIconIndex = PipeIconProvider.TYPE.PipeAllWood_Solid.ordinal();
/*  41 */   protected float speedMultiplier = 1.0F;
/*     */   
/*  43 */   private int ticksSincePull = 0;
/*     */   
/*  45 */   private PipeLogicWood logic = new PipeLogicWood(this)
/*     */     {
/*     */       protected boolean isValidConnectingTile(TileEntity tile) {
/*  48 */         if (tile instanceof buildcraft.api.transport.IPipeTile) {
/*  49 */           return false;
/*     */         }
/*  51 */         if (!(tile instanceof IInventory)) {
/*  52 */           return false;
/*     */         }
/*  54 */         return true;
/*     */       }
/*     */     };
/*     */   
/*     */   public PipeItemsWood(Item item) {
/*  59 */     super((PipeTransport)new PipeTransportItems(), item);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean blockActivated(EntityPlayer entityplayer, ForgeDirection side) {
/*  64 */     return this.logic.blockActivated(entityplayer, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(int blockId) {
/*  69 */     this.logic.onNeighborBlockChange();
/*  70 */     super.onNeighborBlockChange(blockId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  75 */     this.logic.initialize();
/*  76 */     super.initialize();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIconProvider getIconProvider() {
/*  82 */     return BuildCraftTransport.instance.pipeIconProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconIndex(ForgeDirection direction) {
/*  87 */     if (direction == ForgeDirection.UNKNOWN) {
/*  88 */       return this.standardIconIndex;
/*     */     }
/*  90 */     int metadata = this.container.func_145832_p();
/*     */     
/*  92 */     if (metadata == direction.ordinal()) {
/*  93 */       return this.solidIconIndex;
/*     */     }
/*  95 */     return this.standardIconIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateEntity() {
/* 102 */     super.updateEntity();
/*     */     
/* 104 */     if ((this.container.func_145831_w()).field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 108 */     this.ticksSincePull++;
/*     */     
/* 110 */     if (shouldTick()) {
/* 111 */       if (((PipeTransportItems)this.transport).getNumberOfStacks() < 64) {
/* 112 */         extractItems();
/*     */       }
/*     */       
/* 115 */       this.battery.setEnergy(0);
/* 116 */       this.ticksSincePull = 0;
/* 117 */       this.speedMultiplier = 1.0F;
/*     */       
/* 119 */       onPostTick();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPostTick() {}
/*     */ 
/*     */   
/*     */   private boolean shouldTick() {
/* 128 */     if (this.ticksSincePull < 8)
/* 129 */       return false; 
/* 130 */     if (this.ticksSincePull < 16) {
/*     */       
/* 132 */       int meta = this.container.func_145832_p();
/*     */       
/* 134 */       if (meta <= 5) {
/* 135 */         ForgeDirection side = ForgeDirection.getOrientation(meta);
/* 136 */         TileEntity tile = this.container.getTile(side);
/*     */         
/* 138 */         if (tile instanceof IInventory) {
/* 139 */           int stackSize = 0;
/* 140 */           IInventory inventory = (IInventory)tile;
/* 141 */           ItemStack[] extracted = checkExtract(inventory, false, side.getOpposite());
/* 142 */           if (extracted != null) {
/* 143 */             for (ItemStack s : extracted) {
/* 144 */               stackSize += s.field_77994_a;
/*     */             }
/*     */           }
/*     */           
/* 148 */           if (this.battery.getEnergyStored() >= stackSize * 10) {
/* 149 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 156 */     return (this.ticksSincePull >= 16 && this.battery.getEnergyStored() >= 10);
/*     */   }
/*     */   
/*     */   private void extractItems() {
/* 160 */     int meta = this.container.func_145832_p();
/*     */     
/* 162 */     if (meta > 5) {
/*     */       return;
/*     */     }
/*     */     
/* 166 */     ForgeDirection side = ForgeDirection.getOrientation(meta);
/* 167 */     TileEntity tile = this.container.getTile(side);
/*     */     
/* 169 */     if (tile instanceof IInventory) {
/* 170 */       IInventory inventory = (IInventory)tile;
/*     */       
/* 172 */       ItemStack[] extracted = checkExtract(inventory, true, side.getOpposite());
/* 173 */       if (extracted == null) {
/*     */         return;
/*     */       }
/*     */       
/* 177 */       tile.func_70296_d();
/*     */       
/* 179 */       for (ItemStack stack : extracted) {
/* 180 */         if (stack != null && stack.field_77994_a != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 186 */           Position entityPos = new Position(tile.field_145851_c + 0.5D, tile.field_145848_d + 0.5D, tile.field_145849_e + 0.5D, side.getOpposite());
/*     */           
/* 188 */           entityPos.moveForwards(0.6D);
/*     */           
/* 190 */           TravelingItem entity = makeItem(entityPos.x, entityPos.y, entityPos.z, stack);
/* 191 */           entity.setSpeed(0.01F);
/* 192 */           ((PipeTransportItems)this.transport).injectItem(entity, entityPos.orientation);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   protected TravelingItem makeItem(double x, double y, double z, ItemStack stack) {
/* 198 */     return TravelingItem.make(x, y, z, stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] checkExtract(IInventory inventory, boolean doRemove, ForgeDirection from) {
/* 207 */     IInventory inv = InvUtils.getInventory(inventory);
/* 208 */     ItemStack result = checkExtractGeneric(inv, doRemove, from);
/*     */     
/* 210 */     if (result != null) {
/* 211 */       return new ItemStack[] { result };
/*     */     }
/*     */     
/* 214 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack checkExtractGeneric(IInventory inventory, boolean doRemove, ForgeDirection from) {
/* 218 */     return checkExtractGeneric(InventoryWrapper.getWrappedInventory(inventory), doRemove, from);
/*     */   }
/*     */   
/*     */   public ItemStack checkExtractGeneric(ISidedInventory inventory, boolean doRemove, ForgeDirection from) {
/* 222 */     if (inventory == null) {
/* 223 */       return null;
/*     */     }
/*     */     
/* 226 */     for (int k : inventory.func_94128_d(from.ordinal())) {
/* 227 */       ItemStack slot = inventory.func_70301_a(k);
/*     */       
/* 229 */       if (slot != null && slot.field_77994_a > 0 && inventory.func_102008_b(k, slot, from.ordinal())) {
/* 230 */         if (doRemove) {
/* 231 */           int maxStackSize = slot.field_77994_a;
/* 232 */           int stackSize = Math.min(maxStackSize, this.battery.getEnergyStored() / 10);
/*     */ 
/*     */           
/* 235 */           int energyUsed = (int)((stackSize * 10) * this.speedMultiplier);
/* 236 */           this.battery.useEnergy(energyUsed, energyUsed, false);
/*     */           
/* 238 */           return inventory.func_70298_a(k, stackSize);
/*     */         } 
/* 240 */         return slot;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 245 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection from) {
/* 250 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
/* 256 */     return this.battery.receiveEnergy(maxReceive, simulate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
/* 262 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyStored(ForgeDirection from) {
/* 267 */     return this.battery.getEnergyStored();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergyStored(ForgeDirection from) {
/* 272 */     return this.battery.getMaxEnergyStored();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsWood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */