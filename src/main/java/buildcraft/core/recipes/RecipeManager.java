/*    */ package buildcraft.core.recipes;
/*    */ 
/*    */ import buildcraft.api.recipes.IFlexibleRecipe;
/*    */ import buildcraft.api.recipes.IRecipeManager;
/*    */ import com.google.common.collect.BiMap;
/*    */ import com.google.common.collect.HashBiMap;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipeManager<T>
/*    */   implements IRecipeManager<T>
/*    */ {
/* 21 */   private BiMap<String, IFlexibleRecipe<T>> recipes = (BiMap<String, IFlexibleRecipe<T>>)HashBiMap.create();
/*    */ 
/*    */   
/*    */   public void addRecipe(String id, int energyCost, T output, Object... input) {
/* 25 */     addRecipe(id, energyCost, 0, output, input);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addRecipe(String id, int energyCost, int craftingDelay, T output, Object... input) {
/* 30 */     this.recipes.put(id, new FlexibleRecipe<T>(id, output, energyCost, craftingDelay, input));
/*    */   }
/*    */ 
/*    */   
/*    */   public void addRecipe(IFlexibleRecipe<T> recipe) {
/* 35 */     this.recipes.put(recipe.getId(), recipe);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeRecipe(String id) {
/* 40 */     this.recipes.remove(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeRecipe(IFlexibleRecipe<T> recipe) {
/* 45 */     this.recipes.remove(this.recipes.inverse().get(recipe));
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<IFlexibleRecipe<T>> getRecipes() {
/* 50 */     return Collections.unmodifiableCollection(this.recipes.values());
/*    */   }
/*    */ 
/*    */   
/*    */   public IFlexibleRecipe<T> getRecipe(String id) {
/* 55 */     return (IFlexibleRecipe<T>)this.recipes.get(id);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\recipes\RecipeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */