/*    */ package buildcraft.api.fuels;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ICoolantManager
/*    */ {
/*    */   ICoolant addCoolant(ICoolant paramICoolant);
/*    */   
/*    */   ICoolant addCoolant(FluidStack paramFluidStack, float paramFloat);
/*    */   
/*    */   default ICoolant addCoolant(Fluid fluid, float degreesCoolingPerMb) {
/* 20 */     return addCoolant(new FluidStack(fluid, 1), degreesCoolingPerMb);
/*    */   }
/*    */   
/*    */   ISolidCoolant addSolidCoolant(ISolidCoolant paramISolidCoolant);
/*    */   
/*    */   ISolidCoolant addSolidCoolant(ItemStack paramItemStack, FluidStack paramFluidStack, float paramFloat);
/*    */   
/*    */   Collection<ICoolant> getCoolants();
/*    */   
/*    */   Collection<ISolidCoolant> getSolidCoolants();
/*    */   
/*    */   ICoolant getCoolant(FluidStack paramFluidStack);
/*    */   
/*    */   float getDegreesPerMb(FluidStack paramFluidStack, float paramFloat);
/*    */   
/*    */   ISolidCoolant getSolidCoolant(ItemStack paramItemStack);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\fuels\ICoolantManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */