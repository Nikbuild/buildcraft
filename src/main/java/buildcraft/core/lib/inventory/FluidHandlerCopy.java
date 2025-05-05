/*    */ package buildcraft.core.lib.inventory;
/*    */ 
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.FluidTankInfo;
/*    */ import net.minecraftforge.fluids.IFluidHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FluidHandlerCopy
/*    */   implements IFluidHandler
/*    */ {
/*    */   private IFluidHandler orignal;
/*    */   private FluidTankInfo[] contents;
/*    */   
/*    */   public FluidHandlerCopy(IFluidHandler orignal) {
/* 23 */     this.orignal = orignal;
/*    */     
/* 25 */     FluidTankInfo[] originalInfo = orignal.getTankInfo(ForgeDirection.UNKNOWN);
/*    */     
/* 27 */     this.contents = new FluidTankInfo[originalInfo.length];
/*    */     
/* 29 */     for (int i = 0; i < this.contents.length; i++) {
/* 30 */       if (originalInfo[i] != null) {
/* 31 */         if ((originalInfo[i]).fluid != null) {
/* 32 */           this.contents[i] = new FluidTankInfo((originalInfo[i]).fluid.copy(), (originalInfo[i]).capacity);
/*    */         } else {
/* 34 */           this.contents[i] = new FluidTankInfo(null, (originalInfo[i]).capacity);
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/* 42 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/* 47 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/* 52 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/* 57 */     return this.orignal.canFill(from, fluid);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 62 */     return this.orignal.canDrain(from, fluid);
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/* 67 */     return this.contents;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\FluidHandlerCopy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */