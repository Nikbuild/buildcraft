/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.api.recipes.BuildcraftRecipeRegistry;
/*    */ import buildcraft.core.lib.utils.Utils;
/*    */ import cpw.mods.fml.common.Optional.Method;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CoreSiliconRecipes
/*    */ {
/*    */   @Method(modid = "BuildCraft|Silicon")
/*    */   public static void loadSiliconRecipes() {
/* 20 */     if (Utils.isRegistered((Item)BuildCraftCore.listItem))
/* 21 */       BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:list", 20000, new ItemStack((Item)BuildCraftCore.listItem, 1, 1), new Object[] { "dyeGreen", "dustRedstone", new ItemStack(Items.field_151121_aF, 8) }); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\CoreSiliconRecipes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */