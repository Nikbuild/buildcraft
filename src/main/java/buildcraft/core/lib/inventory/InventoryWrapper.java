/*    */ package buildcraft.core.lib.inventory;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.ISidedInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class InventoryWrapper
/*    */   implements ISidedInventory
/*    */ {
/*    */   IInventory inventory;
/*    */   
/*    */   public InventoryWrapper(IInventory inventory) {
/* 21 */     this.inventory = inventory;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int func_70302_i_() {
/* 27 */     return this.inventory.func_70302_i_();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_70301_a(int slotIndex) {
/* 32 */     return this.inventory.func_70301_a(slotIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_70298_a(int slotIndex, int amount) {
/* 37 */     return this.inventory.func_70298_a(slotIndex, amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_70304_b(int slotIndex) {
/* 42 */     return this.inventory.func_70304_b(slotIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_70299_a(int slotIndex, ItemStack itemstack) {
/* 47 */     this.inventory.func_70299_a(slotIndex, itemstack);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_145825_b() {
/* 52 */     return this.inventory.func_145825_b();
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_70297_j_() {
/* 57 */     return this.inventory.func_70297_j_();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_70296_d() {
/* 62 */     this.inventory.func_70296_d();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_70300_a(EntityPlayer entityplayer) {
/* 67 */     return this.inventory.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_70295_k_() {
/* 72 */     this.inventory.func_70295_k_();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_70305_f() {
/* 77 */     this.inventory.func_70305_f();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_94041_b(int slotIndex, ItemStack itemstack) {
/* 82 */     return this.inventory.func_94041_b(slotIndex, itemstack);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_145818_k_() {
/* 87 */     return this.inventory.func_145818_k_();
/*    */   }
/*    */ 
/*    */   
/*    */   public static ISidedInventory getWrappedInventory(Object inventory) {
/* 92 */     if (inventory instanceof ISidedInventory)
/* 93 */       return (ISidedInventory)inventory; 
/* 94 */     if (inventory instanceof IInventory) {
/* 95 */       return new InventoryWrapperSimple(InvUtils.getInventory((IInventory)inventory));
/*    */     }
/* 97 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\InventoryWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */