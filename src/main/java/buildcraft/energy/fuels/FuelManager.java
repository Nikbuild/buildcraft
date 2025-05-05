/*    */ package buildcraft.energy.fuels;
/*    */ 
/*    */ import buildcraft.api.fuels.IFuel;
/*    */ import buildcraft.api.fuels.IFuelManager;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
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
/*    */ public final class FuelManager
/*    */   implements IFuelManager
/*    */ {
/* 21 */   public static final FuelManager INSTANCE = new FuelManager();
/*    */   
/* 23 */   private final List<IFuel> fuels = new LinkedList<IFuel>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IFuel addFuel(IFuel fuel) {
/* 30 */     this.fuels.add(fuel);
/* 31 */     return fuel;
/*    */   }
/*    */ 
/*    */   
/*    */   public IFuel addFuel(Fluid fluid, int powerPerCycle, int totalBurningTime) {
/* 36 */     return addFuel(new BCFuel(fluid, powerPerCycle, totalBurningTime));
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<IFuel> getFuels() {
/* 41 */     return this.fuels;
/*    */   }
/*    */ 
/*    */   
/*    */   public IFuel getFuel(Fluid fluid) {
/* 46 */     for (IFuel fuel : this.fuels) {
/* 47 */       if (fuel.getFluid() == fluid) {
/* 48 */         return fuel;
/*    */       }
/*    */     } 
/* 51 */     return null;
/*    */   }
/*    */   
/*    */   private static final class BCFuel implements IFuel {
/*    */     private final Fluid fluid;
/*    */     private final int powerPerCycle;
/*    */     private final int totalBurningTime;
/*    */     
/*    */     public BCFuel(Fluid fluid, int powerPerCycle, int totalBurningTime) {
/* 60 */       this.fluid = fluid;
/* 61 */       this.powerPerCycle = powerPerCycle;
/* 62 */       this.totalBurningTime = totalBurningTime;
/*    */     }
/*    */ 
/*    */     
/*    */     public Fluid getFluid() {
/* 67 */       return this.fluid;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getTotalBurningTime() {
/* 72 */       return this.totalBurningTime;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getPowerPerCycle() {
/* 77 */       return this.powerPerCycle;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\fuels\FuelManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */