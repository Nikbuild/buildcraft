/*    */ package buildcraft.core.lib.inventory.filters;
/*    */ 
/*    */ import net.minecraftforge.fluids.Fluid;
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
/*    */ public class PassThroughFluidFilter
/*    */   implements IFluidFilter
/*    */ {
/*    */   public boolean matches(Fluid fluid) {
/* 20 */     return (fluid != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\filters\PassThroughFluidFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */