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
/*    */ public class ItemBlueprintStandard
/*    */   extends ItemBlueprint
/*    */ {
/*    */   public String getIconType() {
/* 20 */     return "blueprint";
/*    */   }
/*    */ 
/*    */   
/*    */   public IBlueprintItem.Type getType(ItemStack stack) {
/* 25 */     return IBlueprintItem.Type.BLUEPRINT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\ItemBlueprintStandard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */