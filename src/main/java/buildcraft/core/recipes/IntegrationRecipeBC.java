/*    */ package buildcraft.core.recipes;
/*    */ 
/*    */ import buildcraft.api.recipes.IIntegrationRecipe;
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public abstract class IntegrationRecipeBC
/*    */   implements IIntegrationRecipe {
/*    */   private final int energyCost;
/*    */   private final int maxExpansionCount;
/*    */   private SoftReference<List<ItemStack>> exampleInputs;
/*    */   private SoftReference<List<ItemStack>> exampleOutputs;
/*    */   private SoftReference<List<List<ItemStack>>> exampleExpansions;
/*    */   
/*    */   public IntegrationRecipeBC(int energyCost) {
/* 17 */     this(energyCost, -1);
/*    */   }
/*    */   
/*    */   public IntegrationRecipeBC(int energyCost, int maxExpansionCount) {
/* 21 */     this.energyCost = energyCost;
/* 22 */     this.maxExpansionCount = maxExpansionCount;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract List<ItemStack> generateExampleInput();
/*    */   
/*    */   public abstract List<ItemStack> generateExampleOutput();
/*    */   
/*    */   public abstract List<List<ItemStack>> generateExampleExpansions();
/*    */   
/*    */   public int getEnergyCost() {
/* 33 */     return this.energyCost;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getExampleInput() {
/* 38 */     if (this.exampleInputs != null && this.exampleInputs.get() != null) {
/* 39 */       return this.exampleInputs.get();
/*    */     }
/* 41 */     this.exampleInputs = new SoftReference<List<ItemStack>>(generateExampleInput());
/* 42 */     return this.exampleInputs.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<List<ItemStack>> getExampleExpansions() {
/* 47 */     if (this.exampleExpansions != null && this.exampleExpansions.get() != null) {
/* 48 */       return this.exampleExpansions.get();
/*    */     }
/* 50 */     this.exampleExpansions = new SoftReference<List<List<ItemStack>>>(generateExampleExpansions());
/* 51 */     return this.exampleExpansions.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getExampleOutput() {
/* 56 */     if (this.exampleOutputs != null && this.exampleOutputs.get() != null) {
/* 57 */       return this.exampleOutputs.get();
/*    */     }
/* 59 */     this.exampleOutputs = new SoftReference<List<ItemStack>>(generateExampleOutput());
/* 60 */     return this.exampleOutputs.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaximumExpansionCount(ItemStack input) {
/* 65 */     return this.maxExpansionCount;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\recipes\IntegrationRecipeBC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */