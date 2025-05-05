/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileFilteredBuffer
/*     */   extends TileBuildCraft
/*     */   implements IInventory
/*     */ {
/*  21 */   private final SimpleInventory inventoryFilters = new SimpleInventory(9, "FilteredBufferFilters", 1);
/*  22 */   private final SimpleInventory inventoryStorage = new SimpleInventory(9, "FilteredBufferStorage", 64);
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  26 */     super.func_145845_h();
/*     */   }
/*     */   
/*     */   public IInventory getFilters() {
/*  30 */     return (IInventory)this.inventoryFilters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  37 */     return this.inventoryStorage.func_70302_i_();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slotId) {
/*  42 */     return this.inventoryStorage.func_70301_a(slotId);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slotId, int count) {
/*  47 */     return this.inventoryStorage.func_70298_a(slotId, count);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slotId) {
/*  52 */     return this.inventoryStorage.func_70304_b(slotId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slotId, ItemStack itemStack) {
/*  57 */     this.inventoryStorage.func_70299_a(slotId, itemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  62 */     return this.inventoryStorage.func_145825_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/*  67 */     return this.inventoryStorage.func_70297_j_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityPlayer) {
/*  72 */     return (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70305_f() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/*  86 */     ItemStack filterItemStack = this.inventoryFilters.func_70301_a(i);
/*     */     
/*  88 */     if (filterItemStack == null || filterItemStack.func_77973_b() != itemstack.func_77973_b()) {
/*  89 */       return false;
/*     */     }
/*     */     
/*  92 */     if (itemstack.func_77973_b().func_77645_m()) {
/*  93 */       return true;
/*     */     }
/*     */     
/*  96 */     if (filterItemStack.func_77960_j() == itemstack.func_77960_j()) {
/*  97 */       return true;
/*     */     }
/*     */     
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbtTagCompound) {
/* 105 */     super.func_145839_a(nbtTagCompound);
/*     */     
/* 107 */     NBTTagCompound inventoryStorageTag = nbtTagCompound;
/*     */     
/* 109 */     if (nbtTagCompound.func_74764_b("inventoryStorage"))
/*     */     {
/* 111 */       inventoryStorageTag = (NBTTagCompound)nbtTagCompound.func_74781_a("inventoryStorage");
/*     */     }
/*     */     
/* 114 */     this.inventoryStorage.readFromNBT(inventoryStorageTag);
/*     */     
/* 116 */     NBTTagCompound inventoryFiltersTag = (NBTTagCompound)nbtTagCompound.func_74781_a("inventoryFilters");
/* 117 */     this.inventoryFilters.readFromNBT(inventoryFiltersTag);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbtTagCompound) {
/* 122 */     super.func_145841_b(nbtTagCompound);
/*     */     
/* 124 */     this.inventoryStorage.writeToNBT(nbtTagCompound);
/*     */     
/* 126 */     NBTTagCompound inventoryFiltersTag = new NBTTagCompound();
/* 127 */     this.inventoryFilters.writeToNBT(inventoryFiltersTag);
/* 128 */     nbtTagCompound.func_74782_a("inventoryFilters", (NBTBase)inventoryFiltersTag);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 133 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\TileFilteredBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */