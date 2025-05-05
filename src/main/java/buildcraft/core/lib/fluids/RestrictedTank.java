/*    */ package buildcraft.core.lib.fluids;
/*    */ 
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ 
/*    */ public class RestrictedTank
/*    */   extends Tank
/*    */ {
/*    */   private final Fluid[] acceptedFluids;
/*    */   
/*    */   public RestrictedTank(String name, int capacity, TileEntity tile, Fluid... acceptedFluids) {
/* 21 */     super(name, capacity, tile);
/* 22 */     this.acceptedFluids = acceptedFluids;
/*    */   }
/*    */ 
/*    */   
/*    */   public int fill(FluidStack resource, boolean doFill) {
/* 27 */     if (!acceptsFluid(resource.getFluid())) {
/* 28 */       return 0;
/*    */     }
/* 30 */     return super.fill(resource, doFill);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean acceptsFluid(Fluid fluid) {
/* 35 */     for (Fluid accepted : this.acceptedFluids) {
/* 36 */       if (accepted.equals(fluid)) {
/* 37 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 41 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\fluids\RestrictedTank.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */