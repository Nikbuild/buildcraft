/*    */ package buildcraft.builders;
/*    */ 
/*    */ import buildcraft.api.items.IBlueprintItem;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ public class ItemBlueprintTemplate
/*    */   extends ItemBlueprint
/*    */ {
/*    */   public String getIconType() {
/* 20 */     return "template";
/*    */   }
/*    */ 
/*    */   
/*    */   public IBlueprintItem.Type getType(ItemStack stack) {
/* 25 */     return IBlueprintItem.Type.TEMPLATE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\ItemBlueprintTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */