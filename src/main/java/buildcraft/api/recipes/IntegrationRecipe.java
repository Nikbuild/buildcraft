/*    */ package buildcraft.api.recipes;
/*    */ 
/*    */ import buildcraft.api.core.BuildCraftAPI;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class IntegrationRecipe
/*    */ {
/*    */   public final ResourceLocation name;
/*    */   public final long requiredMicroJoules;
/*    */   public final StackDefinition target;
/*    */   public final ImmutableList<StackDefinition> toIntegrate;
/*    */   @Nonnull
/*    */   public final ItemStack output;
/*    */   @Nullable
/*    */   public final NBTTagCompound recipeTag;
/*    */   
/*    */   public IntegrationRecipe(ResourceLocation name, long requiredMicroJoules, StackDefinition target, List<StackDefinition> toIntegrate, @Nonnull ItemStack output, @Nullable NBTTagCompound recipeTag) {
/* 30 */     this.name = name;
/* 31 */     this.requiredMicroJoules = requiredMicroJoules;
/* 32 */     this.target = target;
/* 33 */     this.toIntegrate = ImmutableList.copyOf(toIntegrate);
/* 34 */     this.output = output;
/* 35 */     this.recipeTag = recipeTag;
/*    */   }
/*    */   
/*    */   public IntegrationRecipe(String name, long requiredMicroJoules, StackDefinition target, List<StackDefinition> toIntegrate, @Nonnull ItemStack output, @Nullable NBTTagCompound recipeTag) {
/* 39 */     this(BuildCraftAPI.nameToResourceLocation(name), requiredMicroJoules, target, toIntegrate, output, recipeTag);
/*    */   }
/*    */   
/*    */   public IntegrationRecipe(ResourceLocation name, long requiredMicroJoules, StackDefinition target, List<StackDefinition> toIntegrate, @Nonnull ItemStack output) {
/* 43 */     this(name, requiredMicroJoules, target, toIntegrate, output, (NBTTagCompound)null);
/*    */   }
/*    */   
/*    */   public IntegrationRecipe(String name, long requiredMicroJoules, StackDefinition target, List<StackDefinition> toIntegrate, @Nonnull ItemStack output) {
/* 47 */     this(name, requiredMicroJoules, target, toIntegrate, output, (NBTTagCompound)null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 52 */     if (this == o) {
/* 53 */       return true;
/*    */     }
/* 55 */     if (o == null || getClass() != o.getClass()) {
/* 56 */       return false;
/*    */     }
/*    */     
/* 59 */     IntegrationRecipe that = (IntegrationRecipe)o;
/*    */     
/* 61 */     return this.name.equals(that.name);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 66 */     return this.name.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\recipes\IntegrationRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */