/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.IFluidBlock;
/*    */ import net.minecraftforge.fluids.IFluidContainerItem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FluidUtils
/*    */ {
/*    */   public static FluidStack getFluidStackFromBlock(Block b) {
/* 19 */     if (b != null) {
/* 20 */       if (b instanceof IFluidBlock && ((IFluidBlock)b).getFluid() != null) {
/* 21 */         return new FluidStack(((IFluidBlock)b).getFluid(), 1000);
/*    */       }
/* 23 */       Fluid f = FluidRegistry.lookupFluidForBlock(b);
/* 24 */       if (f != null && FluidRegistry.isFluidRegistered(f)) {
/* 25 */         return new FluidStack(f, 1000);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 30 */     return null;
/*    */   }
/*    */   
/*    */   public static FluidStack getFluidStackFromItemStack(ItemStack stack) {
/* 34 */     if (stack != null) {
/* 35 */       if (stack.func_77973_b() instanceof IFluidContainerItem) {
/* 36 */         IFluidContainerItem ctr = (IFluidContainerItem)stack.func_77973_b();
/* 37 */         return ctr.getFluid(stack);
/* 38 */       }  if (FluidContainerRegistry.isFilledContainer(stack))
/* 39 */         return FluidContainerRegistry.getFluidForFilledItem(stack); 
/* 40 */       if (stack.func_77973_b() instanceof net.minecraft.item.ItemBlock) {
/* 41 */         Block b = Block.func_149634_a(stack.func_77973_b());
/* 42 */         if (b != null) {
/* 43 */           return getFluidStackFromBlock(b);
/*    */         }
/*    */       } 
/*    */     } 
/* 47 */     return null;
/*    */   }
/*    */   
/*    */   public static Fluid getFluidFromItemStack(ItemStack stack) {
/* 51 */     FluidStack fluidStack = getFluidStackFromItemStack(stack);
/* 52 */     return (fluidStack != null) ? fluidStack.getFluid() : null;
/*    */   }
/*    */   
/*    */   public static boolean isFluidContainer(ItemStack stack) {
/* 56 */     return (stack != null && stack.func_77973_b() != null && (stack.func_77973_b() instanceof IFluidContainerItem || FluidContainerRegistry.isFilledContainer(stack)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\FluidUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */