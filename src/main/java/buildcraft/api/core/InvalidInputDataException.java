/*    */ package buildcraft.api.core;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvalidInputDataException
/*    */   extends IOException
/*    */ {
/*    */   public InvalidInputDataException() {}
/*    */   
/*    */   public InvalidInputDataException(String message) {
/* 19 */     super(message);
/*    */   }
/*    */   
/*    */   public InvalidInputDataException(Throwable cause) {
/* 23 */     super(cause);
/*    */   }
/*    */   
/*    */   public InvalidInputDataException(String message, Throwable cause) {
/* 27 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\InvalidInputDataException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */