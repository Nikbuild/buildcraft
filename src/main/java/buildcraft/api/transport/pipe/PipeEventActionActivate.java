/*    */ package buildcraft.api.transport.pipe;
/*    */ 
/*    */ import buildcraft.api.core.EnumPipePart;
/*    */ import buildcraft.api.statements.IAction;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ 
/*    */ public class PipeEventActionActivate extends PipeEvent {
/*    */   public final IAction action;
/*    */   public final IStatementParameter[] params;
/*    */   public final EnumPipePart part;
/*    */   
/*    */   public PipeEventActionActivate(IPipeHolder holder, IAction action, IStatementParameter[] params, EnumPipePart part) {
/* 13 */     super(holder);
/* 14 */     this.action = action;
/* 15 */     this.params = params;
/* 16 */     this.part = part;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeEventActionActivate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */