/*    */ package buildcraft.core.recipes;
/*    */ 
/*    */ import buildcraft.api.recipes.IAssemblyRecipeManager;
/*    */ import buildcraft.api.recipes.IFlexibleRecipe;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class AssemblyRecipeManager
/*    */   implements IAssemblyRecipeManager
/*    */ {
/* 22 */   public static final AssemblyRecipeManager INSTANCE = new AssemblyRecipeManager();
/* 23 */   private Map<String, IFlexibleRecipe<ItemStack>> assemblyRecipes = new HashMap<String, IFlexibleRecipe<ItemStack>>();
/*    */ 
/*    */   
/*    */   public void addRecipe(String id, int energyCost, ItemStack output, Object... input) {
/* 27 */     addRecipe(id, new FlexibleRecipe<ItemStack>(id, output, energyCost, 0L, input));
/*    */   }
/*    */ 
/*    */   
/*    */   public void addRecipe(IFlexibleRecipe<ItemStack> recipe) {
/* 32 */     addRecipe(recipe.getId(), recipe);
/*    */   }
/*    */   
/*    */   private void addRecipe(String id, IFlexibleRecipe<ItemStack> recipe) {
/* 36 */     if (recipe == null) {
/* 37 */       throw new RuntimeException("Recipe \"" + id + "\" is null!");
/*    */     }
/*    */     
/* 40 */     if (this.assemblyRecipes.containsKey(id)) {
/* 41 */       throw new RuntimeException("Recipe \"" + id + "\" already registered");
/*    */     }
/*    */     
/* 44 */     this.assemblyRecipes.put(recipe.getId(), recipe);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<IFlexibleRecipe<ItemStack>> getRecipes() {
/* 49 */     return this.assemblyRecipes.values();
/*    */   }
/*    */   
/*    */   public IFlexibleRecipe<ItemStack> getRecipe(String id) {
/* 53 */     return this.assemblyRecipes.get(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeRecipe(IFlexibleRecipe<ItemStack> recipe) {
/* 58 */     removeRecipe(recipe.getId());
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeRecipe(String id) {
/* 63 */     this.assemblyRecipes.remove(id);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\recipes\AssemblyRecipeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */