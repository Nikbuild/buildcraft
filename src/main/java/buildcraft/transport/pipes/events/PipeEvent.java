/*    */ package buildcraft.transport.pipes.events;
/*    */ 
/*    */ import buildcraft.transport.Pipe;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PipeEvent
/*    */ {
/*    */   public final Pipe<?> pipe;
/*    */   
/*    */   public PipeEvent(Pipe<?> pipe) {
/* 17 */     this.pipe = pipe;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\events\PipeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */