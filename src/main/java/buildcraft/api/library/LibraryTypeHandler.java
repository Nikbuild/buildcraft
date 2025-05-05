/*    */ package buildcraft.api.library;
/*    */ 
/*    */ @Deprecated
/*    */ public abstract class LibraryTypeHandler {
/*    */   private final String extension;
/*    */   
/*    */   public enum HandlerType {
/*  8 */     LOAD,
/*  9 */     STORE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LibraryTypeHandler(String extension) {
/* 15 */     this.extension = extension;
/*    */   }
/*    */   
/*    */   public abstract boolean isHandler(ItemStack paramItemStack, HandlerType paramHandlerType);
/*    */   
/*    */   public boolean isInputExtension(String ext) {
/* 21 */     return this.extension.equals(ext);
/*    */   }
/*    */   
/*    */   public String getOutputExtension() {
/* 25 */     return this.extension;
/*    */   }
/*    */   
/*    */   public abstract int getTextColor();
/*    */   
/*    */   public abstract String getName(ItemStack paramItemStack);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\library\LibraryTypeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */