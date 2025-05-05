/*    */ package buildcraft.core.blueprints;
/*    */ 
/*    */ import buildcraft.api.blueprints.ISchematicHelper;
/*    */ import buildcraft.core.lib.inventory.StackHelper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public final class SchematicHelper
/*    */   implements ISchematicHelper {
/*  9 */   public static final SchematicHelper INSTANCE = new SchematicHelper();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEqualItem(ItemStack a, ItemStack b) {
/* 17 */     return StackHelper.isEqualItem(a, b);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\SchematicHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */