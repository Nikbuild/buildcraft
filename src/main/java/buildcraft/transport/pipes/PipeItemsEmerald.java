/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.ISerializable;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.lib.inventory.InventoryWrapper;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.network.IGuiReturnHandler;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ public class PipeItemsEmerald
/*     */   extends PipeItemsWood
/*     */   implements ISerializable, IGuiReturnHandler
/*     */ {
/*     */   public enum FilterMode
/*     */   {
/*  38 */     WHITE_LIST, BLACK_LIST, ROUND_ROBIN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class EmeraldPipeSettings
/*     */   {
/*  46 */     private PipeItemsEmerald.FilterMode filterMode = PipeItemsEmerald.FilterMode.WHITE_LIST;
/*     */ 
/*     */     
/*     */     public PipeItemsEmerald.FilterMode getFilterMode() {
/*  50 */       return this.filterMode;
/*     */     }
/*     */     
/*     */     public void setFilterMode(PipeItemsEmerald.FilterMode mode) {
/*  54 */       this.filterMode = mode;
/*     */     }
/*     */     
/*     */     public void readFromNBT(NBTTagCompound nbt) {
/*  58 */       this.filterMode = PipeItemsEmerald.FilterMode.values()[nbt.func_74771_c("filterMode")];
/*     */     }
/*     */     
/*     */     public void writeToNBT(NBTTagCompound nbt) {
/*  62 */       nbt.func_74774_a("filterMode", (byte)this.filterMode.ordinal());
/*     */     }
/*     */   }
/*     */   
/*  66 */   private EmeraldPipeSettings settings = new EmeraldPipeSettings();
/*     */   
/*  68 */   private final SimpleInventory filters = new SimpleInventory(9, "Filters", 1);
/*     */   
/*  70 */   private int currentFilter = 0;
/*     */   
/*     */   public PipeItemsEmerald(Item item) {
/*  73 */     super(item);
/*     */     
/*  75 */     this.standardIconIndex = PipeIconProvider.TYPE.PipeItemsEmerald_Standard.ordinal();
/*  76 */     this.solidIconIndex = PipeIconProvider.TYPE.PipeAllEmerald_Solid.ordinal();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean blockActivated(EntityPlayer entityplayer, ForgeDirection side) {
/*  81 */     if (entityplayer.func_71045_bC() != null && 
/*  82 */       Block.func_149634_a(entityplayer.func_71045_bC().func_77973_b()) instanceof buildcraft.transport.BlockGenericPipe) {
/*  83 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  87 */     if (super.blockActivated(entityplayer, side)) {
/*  88 */       return true;
/*     */     }
/*     */     
/*  91 */     if (!(this.container.func_145831_w()).field_72995_K) {
/*  92 */       entityplayer.openGui(BuildCraftTransport.instance, 52, this.container.func_145831_w(), this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e);
/*     */     }
/*     */     
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack[] checkExtract(IInventory inventory, boolean doRemove, ForgeDirection from) {
/* 100 */     if (inventory == null) {
/* 101 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 105 */     ISidedInventory sidedInventory = InventoryWrapper.getWrappedInventory(InvUtils.getInventory(inventory));
/*     */     
/* 107 */     if (this.settings.getFilterMode() == FilterMode.ROUND_ROBIN) {
/* 108 */       return checkExtractRoundRobin(sidedInventory, doRemove, from);
/*     */     }
/*     */     
/* 111 */     return checkExtractFiltered(sidedInventory, doRemove, from);
/*     */   }
/*     */   
/*     */   private ItemStack[] checkExtractFiltered(ISidedInventory inventory, boolean doRemove, ForgeDirection from) {
/* 115 */     for (int k : inventory.func_94128_d(from.ordinal())) {
/* 116 */       ItemStack stack = inventory.func_70301_a(k);
/*     */       
/* 118 */       if (stack != null && stack.field_77994_a > 0)
/*     */       {
/*     */ 
/*     */         
/* 122 */         if (inventory.func_102008_b(k, stack, from.ordinal())) {
/*     */ 
/*     */ 
/*     */           
/* 126 */           boolean matches = isFiltered(stack);
/* 127 */           boolean isBlackList = (this.settings.getFilterMode() == FilterMode.BLACK_LIST);
/*     */           
/* 129 */           if ((!isBlackList || !matches) && (isBlackList || matches)) {
/*     */ 
/*     */ 
/*     */             
/* 133 */             if (doRemove) {
/* 134 */               int maxStackSize = stack.field_77994_a;
/* 135 */               int stackSize = Math.min(maxStackSize, this.battery.getEnergyStored() / 10);
/* 136 */               if (stackSize > 0) {
/* 137 */                 this.speedMultiplier = Math.min(4.0F, (this.battery.getEnergyStored() * 10 / stackSize));
/* 138 */                 int energyUsed = (int)((stackSize * 10) * this.speedMultiplier);
/* 139 */                 this.battery.useEnergy(energyUsed, energyUsed, false);
/*     */                 
/* 141 */                 stack = inventory.func_70298_a(k, stackSize);
/*     */               } else {
/* 143 */                 return null;
/*     */               } 
/*     */             } 
/*     */             
/* 147 */             return new ItemStack[] { stack };
/*     */           } 
/*     */         }  } 
/* 150 */     }  return null;
/*     */   }
/*     */   
/*     */   private ItemStack[] checkExtractRoundRobin(ISidedInventory inventory, boolean doRemove, ForgeDirection from) {
/* 154 */     for (int i : inventory.func_94128_d(from.ordinal())) {
/* 155 */       ItemStack stack = inventory.func_70301_a(i);
/*     */       
/* 157 */       if (stack != null && stack.field_77994_a > 0) {
/* 158 */         ItemStack filter = getCurrentFilter();
/*     */         
/* 160 */         if (filter == null) {
/* 161 */           return null;
/*     */         }
/*     */         
/* 164 */         if (StackHelper.isMatchingItemOrList(filter, stack))
/*     */         {
/*     */ 
/*     */           
/* 168 */           if (inventory.func_102008_b(i, stack, from.ordinal())) {
/*     */ 
/*     */ 
/*     */             
/* 172 */             if (doRemove) {
/*     */               
/* 174 */               stack = inventory.func_70298_a(i, 1);
/* 175 */               incrementFilter();
/*     */             } else {
/* 177 */               stack = stack.func_77946_l();
/* 178 */               stack.field_77994_a = 1;
/*     */             } 
/*     */             
/* 181 */             return new ItemStack[] { stack };
/*     */           }  } 
/*     */       } 
/*     */     } 
/* 185 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isFiltered(ItemStack stack) {
/* 189 */     for (int i = 0; i < this.filters.func_70302_i_(); i++) {
/* 190 */       ItemStack filter = this.filters.func_70301_a(i);
/*     */       
/* 192 */       if (filter == null) {
/* 193 */         return false;
/*     */       }
/*     */       
/* 196 */       if (StackHelper.isMatchingItemOrList(filter, stack)) {
/* 197 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 201 */     return false;
/*     */   }
/*     */   
/*     */   private void incrementFilter() {
/* 205 */     this.currentFilter = (this.currentFilter + 1) % this.filters.func_70302_i_();
/* 206 */     int count = 0;
/* 207 */     while (this.filters.func_70301_a(this.currentFilter) == null && count < this.filters.func_70302_i_()) {
/* 208 */       this.currentFilter = (this.currentFilter + 1) % this.filters.func_70302_i_();
/* 209 */       count++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private ItemStack getCurrentFilter() {
/* 214 */     ItemStack filter = this.filters.func_70301_a(this.currentFilter);
/* 215 */     if (filter == null) {
/* 216 */       incrementFilter();
/*     */     }
/* 218 */     return this.filters.func_70301_a(this.currentFilter);
/*     */   }
/*     */   
/*     */   public IInventory getFilters() {
/* 222 */     return (IInventory)this.filters;
/*     */   }
/*     */   
/*     */   public EmeraldPipeSettings getSettings() {
/* 226 */     return this.settings;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/* 231 */     NBTTagCompound nbt = new NBTTagCompound();
/* 232 */     this.filters.writeToNBT(nbt);
/* 233 */     this.settings.writeToNBT(nbt);
/* 234 */     NetworkUtils.writeNBT(data, nbt);
/* 235 */     data.writeByte(this.currentFilter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/* 240 */     NBTTagCompound nbt = NetworkUtils.readNBT(data);
/* 241 */     this.filters.readFromNBT(nbt);
/* 242 */     this.settings.readFromNBT(nbt);
/* 243 */     this.currentFilter = data.readUnsignedByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 248 */     super.readFromNBT(nbt);
/*     */     
/* 250 */     this.filters.readFromNBT(nbt);
/* 251 */     this.settings.readFromNBT(nbt);
/*     */     
/* 253 */     this.currentFilter = nbt.func_74762_e("currentFilter") % this.filters.func_70302_i_();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 258 */     super.writeToNBT(nbt);
/*     */     
/* 260 */     this.filters.writeToNBT(nbt);
/* 261 */     this.settings.writeToNBT(nbt);
/*     */     
/* 263 */     nbt.func_74768_a("currentFilter", this.currentFilter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeGuiData(ByteBuf data) {
/* 268 */     data.writeByte((byte)this.settings.getFilterMode().ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readGuiData(ByteBuf data, EntityPlayer sender) {
/* 273 */     this.settings.setFilterMode(FilterMode.values()[data.readByte()]);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsEmerald.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */