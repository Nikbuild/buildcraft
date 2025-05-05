/*    */ package buildcraft.api.filler;
/*    */ 
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.containers.IFillerStatementContainer;
/*    */ import javax.annotation.Nullable;
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
/*    */ public interface IFillerPatternShape
/*    */   extends IFillerPattern
/*    */ {
/*    */   boolean fillTemplate(IFilledTemplate paramIFilledTemplate, IStatementParameter[] paramArrayOfIStatementParameter);
/*    */   
/*    */   @Nullable
/*    */   default IFilledTemplate createTemplate(IFillerStatementContainer filler, IStatementParameter[] params) {
/* 25 */     IFilledTemplate template = FillerManager.registry.createFilledTemplate(filler
/* 26 */         .getBox().min(), filler
/* 27 */         .getBox().size());
/*    */     
/* 29 */     if (!fillTemplate(template, params)) {
/* 30 */       return null;
/*    */     }
/* 32 */     return template;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\filler\IFillerPatternShape.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */