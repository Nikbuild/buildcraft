/*    */ package buildcraft.api.recipes;
/*    */ 
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
/*    */ 
/*    */ public interface IIntegrationRecipeProvider
/*    */ {
/*    */   @Nullable
/*    */   IntegrationRecipe getRecipeFor(@Nonnull ItemStack paramItemStack, @Nonnull NonNullList<ItemStack> paramNonNullList);
/*    */   
/*    */   default Optional<IntegrationRecipe> getRecipe(@Nonnull ResourceLocation name, @Nullable NBTTagCompound recipeTag) {
/* 30 */     return Optional.empty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\recipes\IIntegrationRecipeProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */