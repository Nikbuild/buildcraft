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
/*     */ 
/*     */ public class InventoryCopy
/*     */   implements IInventory
/*     */ {
/*     */   private IInventory orignal;
/*     */   private ItemStack[] contents;
/*     */   
/*     */   public InventoryCopy(IInventory orignal) {
/*  27 */     this.orignal = orignal;
/*  28 */     this.contents = new ItemStack[orignal.func_70302_i_()];
/*  29 */     for (int i = 0; i < this.contents.length; i++) {
/*  30 */       ItemStack stack = orignal.func_70301_a(i);
/*  31 */       if (stack != null) {
/*  32 */         this.contents[i] = stack.func_77946_l();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  39 */     return this.contents.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/*  44 */     return this.contents[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int j) {
/*  49 */     if (this.contents[i] != null) {
/*  50 */       if ((this.contents[i]).field_77994_a <= j) {
/*  51 */         ItemStack itemstack = this.contents[i];
/*  52 */         this.contents[i] = null;
/*  53 */         return itemstack;
/*     */       } 
/*  55 */       ItemStack itemstack1 = this.contents[i].func_77979_a(j);
/*  56 */       if ((this.contents[i]).field_77994_a <= 0) {
/*  57 */         this.contents[i] = null;
/*     */       }
/*  59 */       return itemstack1;
/*     */     } 
/*  61 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/*  67 */     this.contents[i] = itemstack;
/*  68 */     if (itemstack != null && itemstack.field_77994_a > func_70297_j_()) {
/*  69 */       itemstack.field_77994_a = func_70297_j_();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  75 */     return this.orignal.func_145825_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/*  80 */     return this.orignal.func_70297_j_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/*  85 */     return true;
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
/*     */   public ItemStack func_70304_b(int slot) {
/*  98 */     return this.orignal.func_70304_b(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int slot, ItemStack stack) {
/* 103 */     return this.orignal.func_94041_b(slot, stack);
/*     */   }
/*     */   
/*     */   public ItemStack[] getItemStacks() {
/* 107 */     return this.contents;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 112 */     return false;
/*     */   }
/*     */   
/*     */   public void func_70296_d() {}
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\InventoryCopy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */