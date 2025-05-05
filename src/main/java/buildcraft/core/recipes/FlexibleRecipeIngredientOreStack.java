/*    */ package buildcraft.core.recipes;
/*    */ 
/*    */ import buildcraft.api.recipes.IFlexibleRecipeIngredient;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.oredict.OreDictionary;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FlexibleRecipeIngredientOreStack
/*    */   implements IFlexibleRecipeIngredient
/*    */ {
/*    */   private final String oreName;
/*    */   private final int stackSize;
/*    */   
/*    */   public FlexibleRecipeIngredientOreStack(String oreName, int stackSize) {
/* 24 */     this.oreName = oreName;
/* 25 */     this.stackSize = stackSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getIngredient() {
/* 30 */     List<ItemStack> stacks = OreDictionary.getOres(this.oreName);
/* 31 */     List<ItemStack> result = new ArrayList<ItemStack>();
/*    */     
/* 33 */     if (stacks != null) {
/* 34 */       for (ItemStack stack : stacks) {
/* 35 */         ItemStack res = stack.func_77946_l();
/* 36 */         res.field_77994_a = this.stackSize;
/* 37 */         result.add(res);
/*    */       } 
/*    */     }
/*    */     
/* 41 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\recipes\FlexibleRecipeIngredientOreStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */