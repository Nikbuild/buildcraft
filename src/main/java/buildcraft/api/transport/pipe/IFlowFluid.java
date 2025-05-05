/*    */ package buildcraft.api.transport.pipe;
/*    */ 
/*    */ import buildcraft.api.core.IFluidFilter;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IFlowFluid
/*    */ {
/*    */   @Nullable
/*    */   @Deprecated
/*    */   default FluidStack tryExtractFluid(int millibuckets, EnumFacing from, FluidStack filter) {
/* 21 */     return tryExtractFluid(millibuckets, from, filter, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   FluidStack tryExtractFluid(int paramInt, EnumFacing paramEnumFacing, FluidStack paramFluidStack, boolean paramBoolean);
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default ActionResult<FluidStack> tryExtractFluidAdv(int millibuckets, EnumFacing from, IFluidFilter filter) {
/* 34 */     return tryExtractFluidAdv(millibuckets, from, filter, false);
/*    */   }
/*    */   
/*    */   ActionResult<FluidStack> tryExtractFluidAdv(int paramInt, EnumFacing paramEnumFacing, IFluidFilter paramIFluidFilter, boolean paramBoolean);
/*    */   
/*    */   int insertFluidsForce(FluidStack paramFluidStack, @Nullable EnumFacing paramEnumFacing, boolean paramBoolean);
/*    */   
/*    */   @Nullable
/*    */   FluidStack extractFluidsForce(int paramInt1, int paramInt2, @Nullable EnumFacing paramEnumFacing, boolean paramBoolean);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\IFlowFluid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */