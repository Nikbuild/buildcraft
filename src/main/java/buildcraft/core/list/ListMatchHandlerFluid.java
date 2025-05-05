/*    */ package buildcraft.core.list;
/*    */ 
/*    */ import buildcraft.api.lists.ListMatchHandler;
/*    */ import buildcraft.core.lib.inventory.StackHelper;
/*    */ import buildcraft.core.lib.utils.FluidUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListMatchHandlerFluid
/*    */   extends ListMatchHandler
/*    */ {
/*    */   public boolean matches(ListMatchHandler.Type type, ItemStack stack, ItemStack target, boolean precise) {
/* 18 */     if (type == ListMatchHandler.Type.TYPE) {
/* 19 */       if (FluidContainerRegistry.isContainer(stack) && FluidContainerRegistry.isContainer(target)) {
/* 20 */         ItemStack emptyContainerStack = FluidContainerRegistry.drainFluidContainer(stack);
/* 21 */         ItemStack emptyContainerTarget = FluidContainerRegistry.drainFluidContainer(target);
/* 22 */         if (StackHelper.isMatchingItem(emptyContainerStack, emptyContainerTarget, true, true)) {
/* 23 */           return true;
/*    */         }
/*    */       } 
/* 26 */     } else if (type == ListMatchHandler.Type.MATERIAL) {
/* 27 */       FluidStack fStack = FluidUtils.getFluidStackFromItemStack(stack);
/* 28 */       FluidStack fTarget = FluidUtils.getFluidStackFromItemStack(target);
/* 29 */       if (fStack != null && fTarget != null) {
/* 30 */         return fStack.isFluidEqual(fTarget);
/*    */       }
/*    */     } 
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidSource(ListMatchHandler.Type type, ItemStack stack) {
/* 38 */     if (type == ListMatchHandler.Type.TYPE)
/* 39 */       return FluidContainerRegistry.isContainer(stack); 
/* 40 */     if (type == ListMatchHandler.Type.MATERIAL) {
/* 41 */       return (FluidUtils.getFluidStackFromItemStack(stack) != null);
/*    */     }
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getClientExamples(ListMatchHandler.Type type, ItemStack stack) {
/* 48 */     if (type == ListMatchHandler.Type.MATERIAL) {
/* 49 */       FluidStack fStack = FluidUtils.getFluidStackFromItemStack(stack);
/* 50 */       if (fStack != null) {
/* 51 */         List<ItemStack> examples = new ArrayList<ItemStack>();
/* 52 */         for (FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
/* 53 */           if (fStack.isFluidEqual(data.fluid)) {
/* 54 */             examples.add(data.filledContainer);
/*    */           }
/*    */         } 
/* 57 */         return examples;
/*    */       } 
/* 59 */     } else if (type == ListMatchHandler.Type.TYPE && 
/* 60 */       FluidContainerRegistry.isContainer(stack)) {
/* 61 */       List<ItemStack> examples = new ArrayList<ItemStack>();
/* 62 */       ItemStack emptyContainerStack = FluidContainerRegistry.drainFluidContainer(stack);
/* 63 */       examples.add(stack);
/* 64 */       examples.add(emptyContainerStack);
/* 65 */       for (FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
/* 66 */         if (StackHelper.isMatchingItem(data.emptyContainer, emptyContainerStack, true, true)) {
/* 67 */           examples.add(data.filledContainer);
/*    */         }
/*    */       } 
/* 70 */       return examples;
/*    */     } 
/*    */     
/* 73 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\list\ListMatchHandlerFluid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */