/*    */ package buildcraft.core.recipes;
/*    */ 
/*    */ import buildcraft.api.core.BCLog;
/*    */ import buildcraft.api.recipes.IFlexibleRecipe;
/*    */ import buildcraft.api.recipes.IRefineryRecipeManager;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import net.minecraftforge.fluids.FluidStack;
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
/*    */ public final class RefineryRecipeManager
/*    */   implements IRefineryRecipeManager
/*    */ {
/* 24 */   public static final RefineryRecipeManager INSTANCE = new RefineryRecipeManager();
/* 25 */   private HashMap<String, IFlexibleRecipe<FluidStack>> recipes = new HashMap<String, IFlexibleRecipe<FluidStack>>();
/* 26 */   private ArrayList<FluidStack> validFluids1 = new ArrayList<FluidStack>();
/* 27 */   private ArrayList<FluidStack> validFluids2 = new ArrayList<FluidStack>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addRecipe(String id, FluidStack ingredient, FluidStack result, int energy, int delay) {
/* 34 */     FlexibleRecipe<FluidStack> recipe = new FlexibleRecipe<FluidStack>(id, result, energy, delay, new Object[] { ingredient });
/* 35 */     this.recipes.put(id, recipe);
/* 36 */     this.validFluids1.add(ingredient);
/* 37 */     this.validFluids2.add(ingredient);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addRecipe(String id, FluidStack ingredient1, FluidStack ingredient2, FluidStack result, int energy, int delay) {
/* 44 */     if (ingredient1 == null || ingredient2 == null || result == null) {
/* 45 */       BCLog.logger.warn("Rejected refinery recipe " + id + " due to a null FluidStack!");
/*    */     }
/*    */     
/* 48 */     FlexibleRecipe<FluidStack> recipe = new FlexibleRecipe<FluidStack>(id, result, energy, delay, new Object[] { ingredient1, ingredient2 });
/*    */     
/* 50 */     this.recipes.put(id, recipe);
/* 51 */     this.validFluids1.add(ingredient1);
/* 52 */     this.validFluids2.add(ingredient2);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<IFlexibleRecipe<FluidStack>> getRecipes() {
/* 57 */     return Collections.unmodifiableCollection(this.recipes.values());
/*    */   }
/*    */ 
/*    */   
/*    */   public IFlexibleRecipe<FluidStack> getRecipe(String id) {
/* 62 */     return this.recipes.get(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeRecipe(IFlexibleRecipe<FluidStack> recipe) {
/* 67 */     removeRecipe(recipe.getId());
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeRecipe(String id) {
/* 72 */     this.recipes.remove(id);
/*    */   }
/*    */   
/*    */   public ArrayList<FluidStack> getValidFluidStacks1() {
/* 76 */     return this.validFluids1;
/*    */   }
/*    */   
/*    */   public ArrayList<FluidStack> getValidFluidStacks2() {
/* 80 */     return this.validFluids2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\recipes\RefineryRecipeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */