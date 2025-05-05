/*    */ package buildcraft.transport.pipes.events;
/*    */ 
/*    */ import buildcraft.transport.Pipe;
/*    */ import com.google.common.collect.Multiset;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public abstract class PipeEventFluid
/*    */   extends PipeEvent
/*    */ {
/*    */   public final FluidStack fluidStack;
/*    */   
/*    */   public PipeEventFluid(Pipe<?> pipe, FluidStack fluidStack) {
/* 14 */     super(pipe);
/* 15 */     this.fluidStack = fluidStack;
/*    */   }
/*    */   
/*    */   public static class FindDest extends PipeEventFluid {
/*    */     public final Multiset<ForgeDirection> destinations;
/*    */     
/*    */     public FindDest(Pipe<?> pipe, FluidStack fluidStack, Multiset<ForgeDirection> destinations) {
/* 22 */       super(pipe, fluidStack);
/* 23 */       this.destinations = destinations;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\events\PipeEventFluid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */