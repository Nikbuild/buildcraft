/*    */ package buildcraft.core.lib.inventory.filters;
/*    */ 
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleFluidFilter
/*    */   implements IFluidFilter
/*    */ {
/*    */   private Fluid fluidChecked;
/*    */   
/*    */   public SimpleFluidFilter(FluidStack stack) {
/* 19 */     if (stack != null) {
/* 20 */       this.fluidChecked = stack.getFluid();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(Fluid fluid) {
/* 26 */     if (this.fluidChecked != null) {
/* 27 */       return (this.fluidChecked.getID() == fluid.getID());
/*    */     }
/* 29 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\filters\SimpleFluidFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */