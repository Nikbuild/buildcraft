/*    */ package buildcraft.transport.utils;
/*    */ 
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class FluidRenderData {
/*    */   public int fluidID;
/*  7 */   public int[] amount = new int[7]; public int color; public int flags;
/*    */   
/*    */   public FluidRenderData duplicate() {
/* 10 */     FluidRenderData n = new FluidRenderData();
/* 11 */     n.fluidID = this.fluidID;
/* 12 */     n.color = this.color;
/* 13 */     n.flags = this.flags;
/* 14 */     System.arraycopy(this.amount, 0, n.amount, 0, 7);
/* 15 */     return n;
/*    */   }
/*    */   
/*    */   public static int getFlags(FluidStack s) {
/* 19 */     if (s == null) {
/* 20 */       return 0;
/*    */     }
/* 22 */     return s.getFluid().getLuminosity(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transpor\\utils\FluidRenderData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */