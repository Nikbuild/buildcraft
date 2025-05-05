/*    */ package buildcraft.api.statements;
/*    */ 
/*    */ import buildcraft.api.core.EnumPipePart;
/*    */ import java.util.Arrays;
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatementSlot
/*    */ {
/*    */   public IStatement statement;
/*    */   public IStatementParameter[] parameters;
/* 15 */   public EnumPipePart part = EnumPipePart.CENTER;
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 19 */     if (!(o instanceof StatementSlot)) {
/* 20 */       return false;
/*    */     }
/* 22 */     StatementSlot s = (StatementSlot)o;
/* 23 */     if (s.statement != this.statement || this.parameters.length != s.parameters.length) {
/* 24 */       return false;
/*    */     }
/* 26 */     for (int i = 0; i < this.parameters.length; i++) {
/* 27 */       IStatementParameter p1 = this.parameters[i];
/* 28 */       IStatementParameter p2 = s.parameters[i];
/* 29 */       if (p1 == null) {
/* 30 */         if (p2 != null) return false;
/*    */       
/*    */       } else {
/* 33 */         if (p2 == null) return false; 
/* 34 */         if (!p1.equals(p2))
/* 35 */           return false; 
/*    */       } 
/*    */     } 
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 43 */     return Objects.hash(new Object[] { this.statement, Integer.valueOf(Arrays.deepHashCode((Object[])this.parameters)) });
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\StatementSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */