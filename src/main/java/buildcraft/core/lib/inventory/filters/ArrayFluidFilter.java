/*    */ package buildcraft.core.lib.inventory.filters;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidContainerRegistry;
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
/*    */ 
/*    */ 
/*    */ public class ArrayFluidFilter
/*    */   implements IFluidFilter
/*    */ {
/*    */   protected Fluid[] fluids;
/*    */   
/*    */   public ArrayFluidFilter(ItemStack... stacks) {
/* 25 */     this.fluids = new Fluid[stacks.length];
/*    */     
/* 27 */     for (int i = 0; i < stacks.length; i++) {
/* 28 */       FluidStack stack = FluidContainerRegistry.getFluidForFilledItem(stacks[i]);
/* 29 */       if (stack != null) {
/* 30 */         this.fluids[i] = stack.getFluid();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public ArrayFluidFilter(Fluid... iFluids) {
/* 36 */     this.fluids = iFluids;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasFilter() {
/* 41 */     for (Fluid filter : this.fluids) {
/* 42 */       if (filter != null) {
/* 43 */         return true;
/*    */       }
/*    */     } 
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(Fluid fluid) {
/* 51 */     for (Fluid filter : this.fluids) {
/* 52 */       if (filter != null && fluid.getID() == filter.getID()) {
/* 53 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 57 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\filters\ArrayFluidFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */