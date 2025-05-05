/*    */ package buildcraft.core.lib.inventory;
/*    */ 
/*    */ import buildcraft.api.recipes.IFlexibleCrafter;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CrafterCopy
/*    */   implements IFlexibleCrafter
/*    */ {
/*    */   private ItemStack[] items;
/*    */   private FluidStack[] fluids;
/*    */   
/*    */   public CrafterCopy(IFlexibleCrafter origin) {
/* 23 */     this.items = new ItemStack[origin.getCraftingItemStackSize()];
/*    */     int i;
/* 25 */     for (i = 0; i < this.items.length; i++) {
/* 26 */       ItemStack s = origin.getCraftingItemStack(i);
/*    */       
/* 28 */       if (s != null) {
/* 29 */         this.items[i] = s.func_77946_l();
/*    */       } else {
/* 31 */         this.items[i] = null;
/*    */       } 
/*    */     } 
/*    */     
/* 35 */     this.fluids = new FluidStack[origin.getCraftingFluidStackSize()];
/*    */     
/* 37 */     for (i = 0; i < this.fluids.length; i++) {
/* 38 */       FluidStack f = origin.getCraftingFluidStack(i);
/*    */       
/* 40 */       if (f != null) {
/* 41 */         this.fluids[i] = origin.getCraftingFluidStack(i).copy();
/*    */       } else {
/* 43 */         this.fluids[i] = null;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCraftingItemStackSize() {
/* 50 */     return this.items.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getCraftingItemStack(int slotid) {
/* 55 */     return this.items[slotid];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack decrCraftingItemStack(int slotid, int val) {
/*    */     ItemStack result;
/* 62 */     if (val >= (this.items[slotid]).field_77994_a) {
/* 63 */       result = this.items[slotid];
/* 64 */       this.items[slotid] = null;
/*    */     } else {
/* 66 */       result = this.items[slotid].func_77946_l();
/* 67 */       result.field_77994_a = val;
/* 68 */       (this.items[slotid]).field_77994_a -= val;
/*    */     } 
/*    */     
/* 71 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidStack getCraftingFluidStack(int tankid) {
/* 76 */     return this.fluids[tankid];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public FluidStack decrCraftingFluidStack(int tankid, int val) {
/*    */     FluidStack result;
/* 83 */     if (val >= (this.fluids[tankid]).amount) {
/* 84 */       result = this.fluids[tankid];
/* 85 */       this.fluids[tankid] = null;
/*    */     } else {
/* 87 */       result = this.fluids[tankid].copy();
/* 88 */       result.amount = val;
/* 89 */       (this.fluids[tankid]).amount -= val;
/*    */     } 
/*    */     
/* 92 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCraftingFluidStackSize() {
/* 97 */     return this.fluids.length;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\CrafterCopy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */