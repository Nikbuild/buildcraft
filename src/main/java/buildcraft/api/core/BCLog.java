/*    */ package buildcraft.api.core;
/*    */ 
/*    */ import org.apache.logging.log4j.Level;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BCLog
/*    */ {
/* 12 */   public static final Logger logger = LogManager.getLogger("BuildCraft");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static void logErrorAPI(String mod, Throwable error, Class<?> classFile) {
/* 19 */     logErrorAPI(error, classFile);
/*    */   }
/*    */   
/*    */   public static void logErrorAPI(Throwable error, Class<?> classFile) {
/* 23 */     StringBuilder msg = new StringBuilder("API error! Please update your mods. Error: ");
/* 24 */     msg.append(error);
/* 25 */     StackTraceElement[] stackTrace = error.getStackTrace();
/* 26 */     if (stackTrace.length > 0) {
/* 27 */       msg.append(", ").append(stackTrace[0]);
/*    */     }
/*    */     
/* 30 */     logger.log(Level.ERROR, msg.toString());
/*    */     
/* 32 */     if (classFile != null) {
/* 33 */       msg.append("API error: ").append(classFile.getSimpleName()).append(" is loaded from ").append(classFile.getProtectionDomain()
/* 34 */           .getCodeSource().getLocation());
/* 35 */       logger.log(Level.ERROR, msg.toString());
/*    */     } 
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static String getVersion() {
/* 41 */     return BuildCraftAPI.getVersion();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\BCLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */