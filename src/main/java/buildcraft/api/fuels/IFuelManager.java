/*    */ package buildcraft.api.fuels;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IFuelManager
/*    */ {
/*    */   <F extends IFuel> F addFuel(F paramF);
/*    */   
/*    */   IFuel addFuel(FluidStack paramFluidStack, long paramLong, int paramInt);
/*    */   
/*    */   default IFuel addFuel(Fluid fluid, long powerPerCycle, int totalBurningTime) {
/* 18 */     return addFuel(new FluidStack(fluid, 1), powerPerCycle, totalBurningTime);
/*    */   }
/*    */ 
/*    */   
/*    */   IDirtyFuel addDirtyFuel(FluidStack paramFluidStack1, long paramLong, int paramInt, FluidStack paramFluidStack2);
/*    */ 
/*    */   
/*    */   default IDirtyFuel addDirtyFuel(Fluid fuel, long powerPerCycle, int totalBurningTime, FluidStack residue) {
/* 26 */     return addDirtyFuel(new FluidStack(fuel, 1), powerPerCycle, totalBurningTime, residue);
/*    */   }
/*    */   
/*    */   Collection<IFuel> getFuels();
/*    */   
/*    */   IFuel getFuel(FluidStack paramFluidStack);
/*    */   
/*    */   public static interface IDirtyFuel extends IFuel {
/*    */     FluidStack getResidue();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\fuels\IFuelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */