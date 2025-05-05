/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.inventory.InventoryCrafting;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.CraftingManager;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ import net.minecraft.item.crafting.ShapelessRecipes;
/*    */ import net.minecraft.world.World;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CraftingUtils
/*    */ {
/*    */   public static IRecipe findMatchingRecipe(InventoryCrafting par1InventoryCrafting, World par2World) {
/* 34 */     int itemNum = 0;
/* 35 */     ItemStack item1 = null;
/* 36 */     ItemStack item2 = null;
/*    */ 
/*    */     
/* 39 */     for (int slot = 0; slot < par1InventoryCrafting.func_70302_i_(); slot++) {
/* 40 */       ItemStack itemInSlot = par1InventoryCrafting.func_70301_a(slot);
/*    */       
/* 42 */       if (itemInSlot != null) {
/* 43 */         if (itemNum == 0) {
/* 44 */           item1 = itemInSlot;
/*    */         }
/*    */         
/* 47 */         if (itemNum == 1) {
/* 48 */           item2 = itemInSlot;
/*    */         }
/*    */         
/* 51 */         itemNum++;
/*    */       } 
/*    */     } 
/*    */     
/* 55 */     if (itemNum == 2 && item1 != null && item2 != null && item1
/* 56 */       .func_77973_b() == item2.func_77973_b() && item1.field_77994_a == 1 && item2.field_77994_a == 1 && item1
/*    */       
/* 58 */       .func_77973_b().isRepairable()) {
/* 59 */       Item itemBase = item1.func_77973_b();
/* 60 */       int item1Durability = itemBase.func_77612_l() - item1.func_77952_i();
/* 61 */       int item2Durability = itemBase.func_77612_l() - item2.func_77952_i();
/* 62 */       int repairAmt = item1Durability + item2Durability + itemBase.func_77612_l() * 5 / 100;
/* 63 */       int newDamage = itemBase.func_77612_l() - repairAmt;
/*    */       
/* 65 */       if (newDamage < 0) {
/* 66 */         newDamage = 0;
/*    */       }
/*    */       
/* 69 */       ArrayList<ItemStack> ingredients = new ArrayList(2);
/* 70 */       ingredients.add(item1);
/* 71 */       ingredients.add(item2);
/*    */       
/* 73 */       return (IRecipe)new ShapelessRecipes(new ItemStack(item1.func_77973_b(), 1, newDamage), ingredients);
/* 74 */     }  if (itemNum > 0) {
/*    */ 
/*    */       
/* 77 */       List recipes = CraftingManager.func_77594_a().func_77592_b();
/* 78 */       for (Object recipe : recipes) {
/* 79 */         IRecipe currentRecipe = (IRecipe)recipe;
/*    */         
/* 81 */         if (currentRecipe.func_77569_a(par1InventoryCrafting, par2World)) {
/* 82 */           return currentRecipe;
/*    */         }
/*    */       } 
/*    */       
/* 86 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 90 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\CraftingUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */