/*    */ package buildcraft.api.statements;
/*    */ 
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public interface ITriggerExternalOverride
/*    */ {
/*    */   Result override(EnumFacing paramEnumFacing, IStatementContainer paramIStatementContainer, ITriggerExternal paramITriggerExternal, IStatementParameter[] paramArrayOfIStatementParameter);
/*    */   
/*    */   public enum Result {
/* 10 */     TRUE,
/* 11 */     FALSE,
/* 12 */     IGNORE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\ITriggerExternalOverride.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */