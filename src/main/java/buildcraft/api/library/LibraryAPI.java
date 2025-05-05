/*    */ package buildcraft.api.library;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ @Deprecated
/*    */ public final class LibraryAPI {
/*  8 */   private static final Set<LibraryTypeHandler> handlers = new HashSet<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Set<LibraryTypeHandler> getHandlerSet() {
/* 15 */     return handlers;
/*    */   }
/*    */   
/*    */   public static void registerHandler(LibraryTypeHandler handler) {
/* 19 */     handlers.add(handler);
/*    */   }
/*    */   
/*    */   public static LibraryTypeHandler getHandlerFor(String extension) {
/* 23 */     for (LibraryTypeHandler h : handlers) {
/* 24 */       if (h.isInputExtension(extension)) {
/* 25 */         return h;
/*    */       }
/*    */     } 
/* 28 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\library\LibraryAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */