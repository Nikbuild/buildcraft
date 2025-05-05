/*    */ package buildcraft.api.recipes;
/*    */ 
/*    */ import buildcraft.api.core.BuildCraftAPI;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
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
/*    */ public final class AssemblyRecipe
/*    */   implements Comparable<AssemblyRecipe>
/*    */ {
/*    */   public final ResourceLocation name;
/*    */   public final long requiredMicroJoules;
/*    */   public final ImmutableSet<StackDefinition> requiredStacks;
/*    */   @Nonnull
/*    */   public final ItemStack output;
/*    */   @Nullable
/*    */   public final NBTTagCompound recipeTag;
/*    */   
/*    */   public AssemblyRecipe(ResourceLocation name, long requiredMicroJoules, Set<StackDefinition> requiredStacks, @Nonnull ItemStack output, @Nullable NBTTagCompound recipeTag) {
/* 30 */     this.name = name;
/* 31 */     this.requiredMicroJoules = requiredMicroJoules;
/* 32 */     this.requiredStacks = ImmutableSet.copyOf(requiredStacks);
/* 33 */     this.output = output;
/* 34 */     this.recipeTag = recipeTag;
/*    */   }
/*    */   
/*    */   public AssemblyRecipe(String name, long requiredMicroJoules, Set<StackDefinition> requiredStacks, @Nonnull ItemStack output, @Nullable NBTTagCompound recipeTag) {
/* 38 */     this(BuildCraftAPI.nameToResourceLocation(name), requiredMicroJoules, requiredStacks, output, recipeTag);
/*    */   }
/*    */   
/*    */   public AssemblyRecipe(ResourceLocation name, long requiredMicroJoules, Set<StackDefinition> requiredStacks, @Nonnull ItemStack output) {
/* 42 */     this(name, requiredMicroJoules, requiredStacks, output, (NBTTagCompound)null);
/*    */   }
/*    */   
/*    */   public AssemblyRecipe(String name, long requiredMicroJoules, Set<StackDefinition> requiredStacks, @Nonnull ItemStack output) {
/* 46 */     this(name, requiredMicroJoules, requiredStacks, output, (NBTTagCompound)null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 51 */     if (this == o) {
/* 52 */       return true;
/*    */     }
/* 54 */     if (o == null || getClass() != o.getClass()) {
/* 55 */       return false;
/*    */     }
/*    */     
/* 58 */     AssemblyRecipe that = (AssemblyRecipe)o;
/*    */     
/* 60 */     return this.name.equals(that.name);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 65 */     return this.name.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(AssemblyRecipe o) {
/* 70 */     return this.name.toString().compareTo(o.name.toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\recipes\AssemblyRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */