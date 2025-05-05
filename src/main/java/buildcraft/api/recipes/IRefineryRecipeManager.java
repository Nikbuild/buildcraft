/*    */ package buildcraft.api.recipes;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ public interface IRefineryRecipeManager
/*    */ {
/*    */   IHeatableRecipe createHeatingRecipe(FluidStack paramFluidStack1, FluidStack paramFluidStack2, int paramInt1, int paramInt2);
/*    */   
/*    */   default IHeatableRecipe addHeatableRecipe(FluidStack in, FluidStack out, int heatFrom, int heatTo) {
/* 15 */     return getHeatableRegistry().addRecipe(createHeatingRecipe(in, out, heatFrom, heatTo));
/*    */   }
/*    */   
/*    */   ICoolableRecipe createCoolableRecipe(FluidStack paramFluidStack1, FluidStack paramFluidStack2, int paramInt1, int paramInt2);
/*    */   
/*    */   default ICoolableRecipe addCoolableRecipe(FluidStack in, FluidStack out, int heatFrom, int heatTo) {
/* 21 */     return getCoolableRegistry().addRecipe(createCoolableRecipe(in, out, heatFrom, heatTo));
/*    */   }
/*    */   
/*    */   IDistillationRecipe createDistillationRecipe(FluidStack paramFluidStack1, FluidStack paramFluidStack2, FluidStack paramFluidStack3, long paramLong);
/*    */   
/*    */   default IDistillationRecipe addDistillationRecipe(FluidStack in, FluidStack outGas, FluidStack outLiquid, long powerRequired) {
/* 27 */     return getDistillationRegistry().addRecipe(createDistillationRecipe(in, outGas, outLiquid, powerRequired));
/*    */   }
/*    */   
/*    */   IRefineryRegistry<IHeatableRecipe> getHeatableRegistry();
/*    */   
/*    */   IRefineryRegistry<ICoolableRecipe> getCoolableRegistry();
/*    */   
/*    */   IRefineryRegistry<IDistillationRecipe> getDistillationRegistry();
/*    */   
/*    */   public static interface IDistillationRecipe extends IRefineryRecipe {
/*    */     long powerRequired();
/*    */     
/*    */     FluidStack outGas();
/*    */     
/*    */     FluidStack outLiquid();
/*    */   }
/*    */   
/*    */   public static interface ICoolableRecipe extends IHeatExchangerRecipe {}
/*    */   
/*    */   public static interface IHeatableRecipe extends IHeatExchangerRecipe {}
/*    */   
/*    */   public static interface IHeatExchangerRecipe extends IRefineryRecipe {
/*    */     @Nullable
/*    */     FluidStack out();
/*    */     
/*    */     int heatFrom();
/*    */     
/*    */     int heatTo();
/*    */   }
/*    */   
/*    */   public static interface IRefineryRecipe {
/*    */     FluidStack in();
/*    */   }
/*    */   
/*    */   public static interface IRefineryRegistry<R extends IRefineryRecipe> {
/*    */     Stream<R> getRecipes(Predicate<R> param1Predicate);
/*    */     
/*    */     Collection<R> getAllRecipes();
/*    */     
/*    */     @Nullable
/*    */     R getRecipeForInput(@Nullable FluidStack param1FluidStack);
/*    */     
/*    */     Collection<R> removeRecipes(Predicate<R> param1Predicate);
/*    */     
/*    */     R addRecipe(R param1R);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\recipes\IRefineryRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */