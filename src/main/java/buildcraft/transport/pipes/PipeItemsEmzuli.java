/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.EnumColor;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.network.IGuiReturnHandler;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.TravelingItem;
/*     */ import buildcraft.transport.statements.ActionExtractionPreset;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.BitSet;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
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
/*     */ 
/*     */ 
/*     */ public class PipeItemsEmzuli
/*     */   extends PipeItemsWood
/*     */   implements IGuiReturnHandler
/*     */ {
/*  43 */   public final byte[] slotColors = new byte[4];
/*  44 */   private final SimpleInventory filters = new SimpleInventory(4, "Filters", 1);
/*  45 */   private final BitSet activeFlags = new BitSet(4);
/*  46 */   private final int filterCount = this.filters.func_70302_i_();
/*  47 */   private int currentFilter = 0;
/*     */   
/*     */   public PipeItemsEmzuli(Item item) {
/*  50 */     super(item);
/*     */     
/*  52 */     this.standardIconIndex = PipeIconProvider.TYPE.PipeItemsEmzuli_Standard.ordinal();
/*  53 */     this.solidIconIndex = PipeIconProvider.TYPE.PipeAllEmzuli_Solid.ordinal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPostTick() {
/*  61 */     super.onPostTick();
/*  62 */     incrementFilter();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean blockActivated(EntityPlayer entityplayer, ForgeDirection side) {
/*  67 */     if (entityplayer.func_71045_bC() != null && 
/*  68 */       Block.func_149634_a(entityplayer.func_71045_bC().func_77973_b()) instanceof buildcraft.transport.BlockGenericPipe) {
/*  69 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  73 */     if (super.blockActivated(entityplayer, side)) {
/*  74 */       return true;
/*     */     }
/*     */     
/*  77 */     if (!(this.container.func_145831_w()).field_72995_K) {
/*  78 */       entityplayer.openGui(BuildCraftTransport.instance, 53, this.container.func_145831_w(), this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e);
/*     */     }
/*     */     
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TravelingItem makeItem(double x, double y, double z, ItemStack stack) {
/*  86 */     TravelingItem item = super.makeItem(x, y, z, stack);
/*  87 */     int color = this.slotColors[this.currentFilter % this.filterCount];
/*  88 */     if (color > 0) {
/*  89 */       item.color = EnumColor.fromId(color - 1);
/*     */     }
/*  91 */     return item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] checkExtract(IInventory inventory, boolean doRemove, ForgeDirection from) {
/* 101 */     if (this.activeFlags.isEmpty()) {
/* 102 */       return null;
/*     */     }
/*     */     
/* 105 */     if (this.filters.func_70301_a(this.currentFilter % this.filterCount) == null || !this.activeFlags.get(this.currentFilter % this.filterCount)) {
/* 106 */       return null;
/*     */     }
/*     */     
/* 109 */     IInventory inv = InvUtils.getInventory(inventory);
/* 110 */     ItemStack result = checkExtractGeneric(inv, doRemove, from);
/*     */     
/* 112 */     if (result != null) {
/* 113 */       return new ItemStack[] { result };
/*     */     }
/*     */     
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack checkExtractGeneric(ISidedInventory inventory, boolean doRemove, ForgeDirection from) {
/* 121 */     if (inventory == null) {
/* 122 */       return null;
/*     */     }
/*     */     
/* 125 */     ItemStack filter = getCurrentFilter();
/* 126 */     if (filter == null)
/* 127 */       return null; 
/*     */     int arrayOfInt[], i;
/*     */     byte b;
/* 130 */     for (arrayOfInt = inventory.func_94128_d(from.ordinal()), i = arrayOfInt.length, b = 0; b < i; ) { int k = arrayOfInt[b];
/* 131 */       ItemStack slot = inventory.func_70301_a(k);
/*     */       
/* 133 */       if (slot == null || slot.field_77994_a <= 0 || !inventory.func_102008_b(k, slot, from.ordinal()) || 
/* 134 */         !StackHelper.isMatchingItemOrList(slot, filter)) {
/*     */         b++;
/*     */         continue;
/*     */       } 
/* 138 */       if (doRemove) {
/* 139 */         int maxStackSize = slot.field_77994_a;
/* 140 */         int stackSize = Math.min(maxStackSize, this.battery.getEnergyStored() / 10);
/* 141 */         int energyUsed = (int)((stackSize * 10) * this.speedMultiplier);
/* 142 */         this.battery.useEnergy(energyUsed, energyUsed, false);
/*     */         
/* 144 */         return inventory.func_70298_a(k, stackSize);
/*     */       } 
/* 146 */       return slot; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     return null;
/*     */   }
/*     */   
/*     */   public IInventory getFilters() {
/* 155 */     return (IInventory)this.filters;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionsActivated(Collection<StatementSlot> actions) {
/* 160 */     super.actionsActivated(actions);
/*     */     
/* 162 */     this.activeFlags.clear();
/*     */     
/* 164 */     for (StatementSlot action : actions) {
/* 165 */       if (action.statement instanceof ActionExtractionPreset) {
/* 166 */         setActivePreset(((ActionExtractionPreset)action.statement).color);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setActivePreset(EnumColor color) {
/* 172 */     switch (color) {
/*     */       case RED:
/* 174 */         this.activeFlags.set(0);
/*     */         break;
/*     */       case BLUE:
/* 177 */         this.activeFlags.set(1);
/*     */         break;
/*     */       case GREEN:
/* 180 */         this.activeFlags.set(2);
/*     */         break;
/*     */       case YELLOW:
/* 183 */         this.activeFlags.set(3);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkedList<IActionInternal> getActions() {
/* 192 */     LinkedList<IActionInternal> result = super.getActions();
/*     */     
/* 194 */     result.add(BuildCraftTransport.actionExtractionPresetRed);
/* 195 */     result.add(BuildCraftTransport.actionExtractionPresetBlue);
/* 196 */     result.add(BuildCraftTransport.actionExtractionPresetGreen);
/* 197 */     result.add(BuildCraftTransport.actionExtractionPresetYellow);
/*     */     
/* 199 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeGuiData(ByteBuf paramDataOutputStream) {}
/*     */ 
/*     */   
/*     */   public void readGuiData(ByteBuf data, EntityPlayer paramEntityPlayer) {
/* 208 */     byte slot = data.readByte();
/* 209 */     this.slotColors[slot] = data.readByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 214 */     super.readFromNBT(nbt);
/* 215 */     this.filters.readFromNBT(nbt);
/* 216 */     this.currentFilter = nbt.func_74762_e("currentFilter");
/* 217 */     for (int slot = 0; slot < this.slotColors.length; slot++) {
/* 218 */       this.slotColors[slot] = nbt.func_74771_c("slotColors[" + slot + "]");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 224 */     super.writeToNBT(nbt);
/* 225 */     this.filters.writeToNBT(nbt);
/* 226 */     nbt.func_74768_a("currentFilter", this.currentFilter);
/* 227 */     for (int slot = 0; slot < this.slotColors.length; slot++) {
/* 228 */       nbt.func_74774_a("slotColors[" + slot + "]", this.slotColors[slot]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void incrementFilter() {
/* 233 */     int count = 0;
/* 234 */     this.currentFilter++;
/*     */     
/* 236 */     while ((this.filters.func_70301_a(this.currentFilter % this.filterCount) == null || !this.activeFlags.get(this.currentFilter % this.filterCount)) && count < this.filterCount) {
/* 237 */       this.currentFilter++;
/* 238 */       count++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private ItemStack getCurrentFilter() {
/* 243 */     return this.filters.func_70301_a(this.currentFilter % this.filterCount);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsEmzuli.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */