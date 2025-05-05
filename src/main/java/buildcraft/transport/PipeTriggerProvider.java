/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.ITriggerExternal;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.api.statements.ITriggerProvider;
/*    */ import buildcraft.api.transport.IPipeTile;
/*    */ import buildcraft.transport.statements.TriggerPipeContents;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PipeTriggerProvider
/*    */   implements ITriggerProvider
/*    */ {
/*    */   public LinkedList<ITriggerInternal> getInternalTriggers(IStatementContainer container) {
/* 26 */     LinkedList<ITriggerInternal> result = new LinkedList<ITriggerInternal>();
/* 27 */     Pipe<?> pipe = null;
/* 28 */     TileEntity tile = container.getTile();
/*    */     
/* 30 */     if (tile instanceof TileGenericPipe) {
/* 31 */       pipe = ((TileGenericPipe)tile).pipe;
/*    */     }
/*    */     
/* 34 */     if (pipe == null) {
/* 35 */       return result;
/*    */     }
/*    */     
/* 38 */     if (container instanceof Gate) {
/* 39 */       ((Gate)container).addTriggers(result);
/*    */     }
/*    */     
/* 42 */     switch (((TileGenericPipe)tile).getPipeType()) {
/*    */       case ITEM:
/* 44 */         result.add(TriggerPipeContents.PipeContents.empty.trigger);
/* 45 */         result.add(TriggerPipeContents.PipeContents.containsItems.trigger);
/*    */         break;
/*    */       case FLUID:
/* 48 */         result.add(TriggerPipeContents.PipeContents.empty.trigger);
/* 49 */         result.add(TriggerPipeContents.PipeContents.containsFluids.trigger);
/*    */         break;
/*    */       case POWER:
/* 52 */         result.add(TriggerPipeContents.PipeContents.empty.trigger);
/* 53 */         result.add(TriggerPipeContents.PipeContents.containsEnergy.trigger);
/* 54 */         result.add(TriggerPipeContents.PipeContents.tooMuchEnergy.trigger);
/* 55 */         result.add(TriggerPipeContents.PipeContents.requestsEnergy.trigger);
/*    */         break;
/*    */     } 
/*    */ 
/*    */     
/* 60 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public LinkedList<ITriggerExternal> getExternalTriggers(ForgeDirection side, TileEntity tile) {
/* 65 */     LinkedList<ITriggerExternal> result = new LinkedList<ITriggerExternal>();
/*    */     
/* 67 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipeTriggerProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */