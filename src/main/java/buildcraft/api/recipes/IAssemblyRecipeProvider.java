/*    */ package buildcraft.api.recipes;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraft.util.ResourceLocation;
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
/*    */ 
/*    */ 
/*    */ public interface IAssemblyRecipeProvider
/*    */ {
/*    */   @Nonnull
/*    */   List<AssemblyRecipe> getRecipesFor(@Nonnull NonNullList<ItemStack> paramNonNullList);
/*    */   
/*    */   default Optional<AssemblyRecipe> getRecipe(@Nonnull ResourceLocation name, @Nullable NBTTagCompound recipeTag) {
/* 30 */     return Optional.empty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\recipes\IAssemblyRecipeProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */