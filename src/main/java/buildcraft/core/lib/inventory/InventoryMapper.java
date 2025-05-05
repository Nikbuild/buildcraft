/*     */ package buildcraft.core.lib.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ public class InventoryMapper
/*     */   implements IInventory
/*     */ {
/*     */   private final IInventory inv;
/*     */   private final int start;
/*     */   private final int size;
/*  25 */   private int stackSizeLimit = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkItems = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InventoryMapper(IInventory inv, int start, int size) {
/*  37 */     this(inv, start, size, true);
/*     */   }
/*     */   
/*     */   public InventoryMapper(IInventory inv, int start, int size, boolean checkItems) {
/*  41 */     this.inv = inv;
/*  42 */     this.start = start;
/*  43 */     this.size = size;
/*  44 */     this.checkItems = checkItems;
/*     */   }
/*     */   
/*     */   public IInventory getBaseInventory() {
/*  48 */     return this.inv;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  53 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slot) {
/*  58 */     return this.inv.func_70301_a(this.start + slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slot, int amount) {
/*  63 */     return this.inv.func_70298_a(this.start + slot, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slot, ItemStack itemstack) {
/*  68 */     this.inv.func_70299_a(this.start + slot, itemstack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  73 */     return this.inv.func_145825_b();
/*     */   }
/*     */   
/*     */   public void setStackSizeLimit(int limit) {
/*  77 */     this.stackSizeLimit = limit;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/*  82 */     return (this.stackSizeLimit > 0) ? this.stackSizeLimit : this.inv.func_70297_j_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/*  87 */     return this.inv.func_70300_a(entityplayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {
/*  92 */     this.inv.func_70295_k_();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70305_f() {
/*  97 */     this.inv.func_70305_f();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slot) {
/* 102 */     return this.inv.func_70304_b(this.start + slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int slot, ItemStack stack) {
/* 107 */     if (this.checkItems) {
/* 108 */       return this.inv.func_94041_b(this.start + slot, stack);
/*     */     }
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 115 */     return this.inv.func_145818_k_();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/* 120 */     this.inv.func_70296_d();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\InventoryMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */