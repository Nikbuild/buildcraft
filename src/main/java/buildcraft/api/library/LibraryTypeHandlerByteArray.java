/*   */ package buildcraft.api.library;
/*   */ 
/*   */ import net.minecraft.item.ItemStack;
/*   */ 
/*   */ public abstract class LibraryTypeHandlerByteArray extends LibraryTypeHandler {
/*   */   public LibraryTypeHandlerByteArray(String extension) {
/* 7 */     super(extension);
/*   */   }
/*   */   
/*   */   public abstract ItemStack load(ItemStack paramItemStack, byte[] paramArrayOfbyte);
/*   */   
/*   */   public abstract byte[] store(ItemStack paramItemStack);
/*   */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\library\LibraryTypeHandlerByteArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */