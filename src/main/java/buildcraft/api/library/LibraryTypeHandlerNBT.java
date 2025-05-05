/*   */ package buildcraft.api.library;
/*   */ 
/*   */ import net.minecraft.item.ItemStack;
/*   */ import net.minecraft.nbt.NBTTagCompound;
/*   */ 
/*   */ public abstract class LibraryTypeHandlerNBT extends LibraryTypeHandler {
/*   */   public LibraryTypeHandlerNBT(String extension) {
/* 8 */     super(extension);
/*   */   }
/*   */   
/*   */   public abstract ItemStack load(ItemStack paramItemStack, NBTTagCompound paramNBTTagCompound);
/*   */   
/*   */   public abstract boolean store(ItemStack paramItemStack, NBTTagCompound paramNBTTagCompound);
/*   */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\library\LibraryTypeHandlerNBT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */