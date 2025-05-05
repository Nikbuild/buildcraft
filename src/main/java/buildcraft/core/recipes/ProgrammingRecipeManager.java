/*    */ package buildcraft.core.recipes;
/*    */ 
/*    */ import buildcraft.api.core.BCLog;
/*    */ import buildcraft.api.recipes.IProgrammingRecipe;
/*    */ import buildcraft.api.recipes.IProgrammingRecipeManager;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class ProgrammingRecipeManager
/*    */   implements IProgrammingRecipeManager {
/* 12 */   public static final ProgrammingRecipeManager INSTANCE = new ProgrammingRecipeManager();
/* 13 */   private final HashMap<String, IProgrammingRecipe> recipes = new HashMap<String, IProgrammingRecipe>();
/*    */ 
/*    */   
/*    */   public void addRecipe(IProgrammingRecipe recipe) {
/* 17 */     if (recipe == null || recipe.getId() == null) {
/*    */       return;
/*    */     }
/*    */     
/* 21 */     if (!this.recipes.containsKey(recipe.getId())) {
/* 22 */       this.recipes.put(recipe.getId(), recipe);
/*    */     } else {
/* 24 */       BCLog.logger.warn("Programming Table Recipe '" + recipe.getId() + "' seems to be duplicated! This is a bug!");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeRecipe(String id) {
/* 30 */     this.recipes.remove(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeRecipe(IProgrammingRecipe recipe) {
/* 35 */     if (recipe == null || recipe.getId() == null) {
/*    */       return;
/*    */     }
/*    */     
/* 39 */     this.recipes.remove(recipe.getId());
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<IProgrammingRecipe> getRecipes() {
/* 44 */     return Collections.unmodifiableCollection(this.recipes.values());
/*    */   }
/*    */ 
/*    */   
/*    */   public IProgrammingRecipe getRecipe(String id) {
/* 49 */     return this.recipes.get(id);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\recipes\ProgrammingRecipeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */