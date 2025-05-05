/*    */ package buildcraft.core.recipes;
/*    */ 
/*    */ import buildcraft.api.recipes.IFlexibleCrafter;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FakeFlexibleCrafter
/*    */   implements IFlexibleCrafter
/*    */ {
/*    */   private final IFlexibleCrafter original;
/*    */   private int[] usedItems;
/*    */   private int[] usedFluids;
/*    */   
/*    */   public FakeFlexibleCrafter(IFlexibleCrafter original) {
/* 17 */     this.original = original;
/* 18 */     this.usedFluids = new int[original.getCraftingFluidStackSize()];
/* 19 */     this.usedItems = new int[original.getCraftingItemStackSize()];
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getCraftingItemStack(int slotId) {
/* 24 */     ItemStack output = this.original.getCraftingItemStack(slotId);
/* 25 */     if (this.usedItems[slotId] == 0)
/* 26 */       return output; 
/* 27 */     if (output.field_77994_a <= this.usedItems[slotId]) {
/* 28 */       return null;
/*    */     }
/* 30 */     output = output.func_77946_l();
/* 31 */     output.field_77994_a -= this.usedItems[slotId];
/* 32 */     return output;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack decrCraftingItemStack(int slotId, int amount) {
/* 37 */     ItemStack output = this.original.getCraftingItemStack(slotId);
/* 38 */     int result = Math.min(output.field_77994_a - this.usedItems[slotId], amount);
/* 39 */     this.usedItems[slotId] = this.usedItems[slotId] + result;
/*    */     
/* 41 */     if (result == 0) {
/* 42 */       return null;
/*    */     }
/* 44 */     ItemStack decrOut = output.func_77946_l();
/* 45 */     decrOut.field_77994_a = result;
/* 46 */     return decrOut;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCraftingItemStackSize() {
/* 51 */     return this.usedItems.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidStack getCraftingFluidStack(int slotId) {
/* 56 */     FluidStack output = this.original.getCraftingFluidStack(slotId);
/* 57 */     if (this.usedFluids[slotId] == 0)
/* 58 */       return output; 
/* 59 */     if (output.amount <= this.usedFluids[slotId]) {
/* 60 */       return null;
/*    */     }
/* 62 */     output = output.copy();
/* 63 */     output.amount -= this.usedFluids[slotId];
/* 64 */     return output;
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidStack decrCraftingFluidStack(int slotId, int amount) {
/* 69 */     FluidStack output = this.original.getCraftingFluidStack(slotId);
/* 70 */     int result = Math.min(output.amount - this.usedFluids[slotId], amount);
/* 71 */     this.usedFluids[slotId] = this.usedFluids[slotId] + result;
/*    */     
/* 73 */     if (result == 0) {
/* 74 */       return null;
/*    */     }
/* 76 */     FluidStack decrOut = output.copy();
/* 77 */     decrOut.amount = result;
/* 78 */     return decrOut;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCraftingFluidStackSize() {
/* 83 */     return this.usedFluids.length;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\recipes\FakeFlexibleCrafter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */