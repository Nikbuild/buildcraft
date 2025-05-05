/*     */ package buildcraft.core.lib.inventory;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public final class InventoryConcatenator
/*     */   implements IInventory
/*     */ {
/*  23 */   private final List<Integer> slotMap = new ArrayList<Integer>();
/*  24 */   private final List<IInventory> invMap = new ArrayList<IInventory>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InventoryConcatenator make() {
/*  30 */     return new InventoryConcatenator();
/*     */   }
/*     */   
/*     */   public InventoryConcatenator add(IInventory inv) {
/*  34 */     for (int slot = 0; slot < inv.func_70302_i_(); slot++) {
/*  35 */       this.slotMap.add(Integer.valueOf(slot));
/*  36 */       this.invMap.add(inv);
/*     */     } 
/*  38 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  43 */     return this.slotMap.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slot) {
/*  48 */     return ((IInventory)this.invMap.get(slot)).func_70301_a(((Integer)this.slotMap.get(slot)).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slot, int amount) {
/*  53 */     return ((IInventory)this.invMap.get(slot)).func_70298_a(((Integer)this.slotMap.get(slot)).intValue(), amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slot) {
/*  58 */     return ((IInventory)this.invMap.get(slot)).func_70304_b(((Integer)this.slotMap.get(slot)).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slot, ItemStack stack) {
/*  63 */     ((IInventory)this.invMap.get(slot)).func_70299_a(((Integer)this.slotMap.get(slot)).intValue(), stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  68 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/*  73 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/*  78 */     return true;
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
/*     */   public boolean func_94041_b(int slot, ItemStack stack) {
/*  91 */     return ((IInventory)this.invMap.get(slot)).func_94041_b(((Integer)this.slotMap.get(slot)).intValue(), stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/* 101 */     for (IInventory inv : this.invMap)
/* 102 */       inv.func_70296_d(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\InventoryConcatenator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */