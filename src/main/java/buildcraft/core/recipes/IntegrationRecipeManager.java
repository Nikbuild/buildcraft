/*    */ package buildcraft.core.recipes;
/*    */ 
/*    */ import buildcraft.api.recipes.IIntegrationRecipe;
/*    */ import buildcraft.api.recipes.IIntegrationRecipeManager;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntegrationRecipeManager
/*    */   implements IIntegrationRecipeManager
/*    */ {
/* 18 */   public static final IntegrationRecipeManager INSTANCE = new IntegrationRecipeManager();
/* 19 */   private List<IIntegrationRecipe> integrationRecipes = new LinkedList<IIntegrationRecipe>();
/*    */ 
/*    */   
/*    */   public void addRecipe(IIntegrationRecipe recipe) {
/* 23 */     this.integrationRecipes.add(recipe);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<? extends IIntegrationRecipe> getRecipes() {
/* 28 */     return this.integrationRecipes;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\recipes\IntegrationRecipeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */