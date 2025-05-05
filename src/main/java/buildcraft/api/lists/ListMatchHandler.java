/*    */ package buildcraft.api.lists;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public abstract class ListMatchHandler {
/*    */   public abstract boolean matches(Type paramType, @Nonnull ItemStack paramItemStack1, @Nonnull ItemStack paramItemStack2, boolean paramBoolean);
/*    */   
/*    */   public abstract boolean isValidSource(Type paramType, @Nonnull ItemStack paramItemStack);
/*    */   
/*    */   public enum Type {
/* 10 */     TYPE,
/* 11 */     MATERIAL,
/* 12 */     CLASS;
/*    */   }
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
/*    */   public NonNullList<ItemStack> getClientExamples(Type type, @Nonnull ItemStack stack) {
/* 26 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\lists\ListMatchHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */