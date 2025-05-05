/*   */ package buildcraft.api.items;
/*   */ 
/*   */ public interface IBlueprintItem extends INamedItem {
/*   */   Type getType(ItemStack paramItemStack);
/*   */   
/*   */   public enum Type {
/* 7 */     TEMPLATE, BLUEPRINT;
/*   */   }
/*   */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\items\IBlueprintItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */