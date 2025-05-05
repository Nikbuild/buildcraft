/*     */ package buildcraft.core.lib.engines;
/*     */ 
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TileEngineWithInventory
/*     */   extends TileEngineBase
/*     */   implements IInventory, ISidedInventory
/*     */ {
/*     */   private final SimpleInventory inv;
/*     */   private final int[] defaultSlotArray;
/*     */   
/*     */   public TileEngineWithInventory(int invSize) {
/*  26 */     this.inv = new SimpleInventory(invSize, "Engine", 64);
/*  27 */     this.defaultSlotArray = Utils.createSlotArray(0, invSize);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  33 */     return this.inv.func_70302_i_();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slot) {
/*  38 */     return this.inv.func_70301_a(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slot, int amount) {
/*  43 */     return this.inv.func_70298_a(slot, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slot) {
/*  48 */     return this.inv.func_70304_b(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slot, ItemStack itemstack) {
/*  53 */     this.inv.func_70299_a(slot, itemstack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/*  58 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  63 */     return "Engine";
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/*  68 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/*  73 */     return (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this);
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
/*     */   public void func_145839_a(NBTTagCompound data) {
/*  86 */     super.func_145839_a(data);
/*  87 */     this.inv.readFromNBT(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound data) {
/*  92 */     super.func_145841_b(data);
/*  93 */     this.inv.writeToNBT(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int side) {
/* 100 */     if (side == this.orientation.ordinal()) {
/* 101 */       return new int[0];
/*     */     }
/* 103 */     return this.defaultSlotArray;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int slot, ItemStack stack, int side) {
/* 109 */     return (side != this.orientation.ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int slot, ItemStack stack, int side) {
/* 114 */     return (side != this.orientation.ordinal());
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\engines\TileEngineWithInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */