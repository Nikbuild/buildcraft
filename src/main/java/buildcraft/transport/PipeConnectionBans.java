/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.transport.pipes.PipeFluidsCobblestone;
/*    */ import buildcraft.transport.pipes.PipeFluidsEmerald;
/*    */ import buildcraft.transport.pipes.PipeFluidsQuartz;
/*    */ import buildcraft.transport.pipes.PipeFluidsStone;
/*    */ import buildcraft.transport.pipes.PipeFluidsWood;
/*    */ import buildcraft.transport.pipes.PipeItemsCobblestone;
/*    */ import buildcraft.transport.pipes.PipeItemsEmerald;
/*    */ import buildcraft.transport.pipes.PipeItemsEmzuli;
/*    */ import buildcraft.transport.pipes.PipeItemsObsidian;
/*    */ import buildcraft.transport.pipes.PipeItemsQuartz;
/*    */ import buildcraft.transport.pipes.PipeItemsStone;
/*    */ import buildcraft.transport.pipes.PipeItemsWood;
/*    */ import buildcraft.transport.pipes.PipePowerEmerald;
/*    */ import buildcraft.transport.pipes.PipePowerWood;
/*    */ import com.google.common.collect.HashMultimap;
/*    */ import com.google.common.collect.SetMultimap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PipeConnectionBans
/*    */ {
/* 34 */   private static final SetMultimap<Class<? extends Pipe<?>>, Class<? extends Pipe<?>>> connectionBans = (SetMultimap<Class<? extends Pipe<?>>, Class<? extends Pipe<?>>>)HashMultimap.create();
/*    */ 
/*    */   
/*    */   static {
/* 38 */     banConnection((Class<? extends Pipe<?>>[])new Class[] { PipeFluidsStone.class, PipeFluidsCobblestone.class, PipeFluidsQuartz.class });
/* 39 */     banConnection((Class<? extends Pipe<?>>[])new Class[] { PipeFluidsWood.class });
/* 40 */     banConnection((Class<? extends Pipe<?>>[])new Class[] { PipeFluidsEmerald.class });
/* 41 */     banConnection((Class<? extends Pipe<?>>[])new Class[] { PipeFluidsWood.class, PipeFluidsEmerald.class });
/*    */ 
/*    */     
/* 44 */     banConnection((Class<? extends Pipe<?>>[])new Class[] { PipeItemsStone.class, PipeItemsCobblestone.class, PipeItemsQuartz.class });
/* 45 */     banConnection((Class<? extends Pipe<?>>[])new Class[] { PipeItemsWood.class });
/* 46 */     banConnection((Class<? extends Pipe<?>>[])new Class[] { PipeItemsEmerald.class });
/* 47 */     banConnection((Class<? extends Pipe<?>>[])new Class[] { PipeItemsEmzuli.class });
/* 48 */     banConnection((Class<? extends Pipe<?>>[])new Class[] { PipeItemsWood.class, PipeItemsEmerald.class, PipeItemsEmzuli.class });
/* 49 */     banConnection((Class<? extends Pipe<?>>[])new Class[] { PipeItemsObsidian.class });
/*    */ 
/*    */     
/* 52 */     banConnection((Class<? extends Pipe<?>>[])new Class[] { PipePowerWood.class });
/* 53 */     banConnection((Class<? extends Pipe<?>>[])new Class[] { PipePowerEmerald.class });
/* 54 */     banConnection((Class<? extends Pipe<?>>[])new Class[] { PipePowerWood.class, PipePowerEmerald.class });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void banConnection(Class<? extends Pipe<?>>... types) {
/* 69 */     if (types.length == 0) {
/*    */       return;
/*    */     }
/* 72 */     if (types.length == 1) {
/* 73 */       connectionBans.put(types[0], types[0]);
/*    */       return;
/*    */     } 
/* 76 */     for (int i = 0; i < types.length; i++) {
/* 77 */       for (int j = 0; j < types.length; j++) {
/* 78 */         if (i != j)
/*    */         {
/*    */           
/* 81 */           connectionBans.put(types[i], types[j]); } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean canPipesConnect(Class<? extends Pipe> type1, Class<? extends Pipe> type2) {
/* 87 */     return !connectionBans.containsEntry(type1, type2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipeConnectionBans.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */