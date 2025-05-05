/*    */ package buildcraft.api.recipes;
/*    */ 
/*    */ import buildcraft.api.core.IStackFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class StackDefinition
/*    */ {
/*    */   public final IStackFilter filter;
/*    */   public final int count;
/*    */   
/*    */   public StackDefinition(IStackFilter filter, int count) {
/* 13 */     this.filter = filter;
/* 14 */     this.count = count;
/*    */   }
/*    */   
/*    */   public StackDefinition(IStackFilter filter) {
/* 18 */     this(filter, 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\recipes\StackDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */