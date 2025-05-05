/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.api.gates.IGate;
/*    */ import buildcraft.api.statements.IActionExternal;
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IActionProvider;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PipeActionProvider
/*    */   implements IActionProvider
/*    */ {
/*    */   public Collection<IActionInternal> getInternalActions(IStatementContainer container) {
/* 20 */     LinkedList<IActionInternal> result = new LinkedList<IActionInternal>();
/* 21 */     Pipe<?> pipe = null;
/* 22 */     if (container instanceof IGate) {
/* 23 */       pipe = (Pipe)((IGate)container).getPipe();
/*    */       
/* 25 */       if (container instanceof Gate) {
/* 26 */         ((Gate)container).addActions(result);
/*    */       }
/*    */     } 
/*    */     
/* 30 */     if (pipe == null) {
/* 31 */       return result;
/*    */     }
/*    */     
/* 34 */     result.addAll(pipe.getActions());
/*    */     
/* 36 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<IActionExternal> getExternalActions(ForgeDirection side, TileEntity tile) {
/* 41 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipeActionProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */