/*    */ package buildcraft.transport.pipes.events;
/*    */ 
/*    */ import buildcraft.transport.Pipe;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PipeEventPower
/*    */   extends PipeEvent
/*    */ {
/*    */   public final ForgeDirection from;
/*    */   public int power;
/*    */   
/*    */   public PipeEventPower(Pipe<?> pipe, ForgeDirection from, int power) {
/* 15 */     super(pipe);
/* 16 */     this.from = from;
/* 17 */     this.power = power;
/*    */   }
/*    */   
/*    */   public static class Request extends PipeEventPower {
/*    */     public Request(Pipe<?> pipe, ForgeDirection from, int power) {
/* 22 */       super(pipe, from, power);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Receive extends PipeEventPower {
/*    */     public boolean override;
/*    */     
/*    */     public Receive(Pipe<?> pipe, ForgeDirection from, int power) {
/* 30 */       super(pipe, from, power);
/* 31 */       this.override = false;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\events\PipeEventPower.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */