/*     */ package buildcraft.core.lib.inventory;
/*     */ 
/*     */ import buildcraft.api.items.IList;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StackHelper
/*     */ {
/*     */   public static boolean canStacksMerge(ItemStack stack1, ItemStack stack2) {
/*  32 */     if (stack1 == null || stack2 == null) {
/*  33 */       return false;
/*     */     }
/*  35 */     if (!stack1.func_77969_a(stack2)) {
/*  36 */       return false;
/*     */     }
/*  38 */     if (!ItemStack.func_77970_a(stack1, stack2)) {
/*  39 */       return false;
/*     */     }
/*  41 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canStacksOrListsMerge(ItemStack stack1, ItemStack stack2) {
/*  46 */     if (stack1 == null || stack2 == null) {
/*  47 */       return false;
/*     */     }
/*     */     
/*  50 */     if (stack1.func_77973_b() instanceof IList) {
/*  51 */       IList list = (IList)stack1.func_77973_b();
/*  52 */       return list.matches(stack1, stack2);
/*  53 */     }  if (stack2.func_77973_b() instanceof IList) {
/*  54 */       IList list = (IList)stack2.func_77973_b();
/*  55 */       return list.matches(stack2, stack1);
/*     */     } 
/*     */     
/*  58 */     if (!stack1.func_77969_a(stack2)) {
/*  59 */       return false;
/*     */     }
/*  61 */     if (!ItemStack.func_77970_a(stack1, stack2)) {
/*  62 */       return false;
/*     */     }
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int mergeStacks(ItemStack mergeSource, ItemStack mergeTarget, boolean doMerge) {
/*  79 */     if (!canStacksMerge(mergeSource, mergeTarget)) {
/*  80 */       return 0;
/*     */     }
/*  82 */     int mergeCount = Math.min(mergeTarget.func_77976_d() - mergeTarget.field_77994_a, mergeSource.field_77994_a);
/*  83 */     if (mergeCount < 1) {
/*  84 */       return 0;
/*     */     }
/*  86 */     if (doMerge) {
/*  87 */       mergeTarget.field_77994_a += mergeCount;
/*     */     }
/*  89 */     return mergeCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCraftingEquivalent(ItemStack base, ItemStack comparison, boolean oreDictionary) {
/* 105 */     if (isMatchingItem(base, comparison, true, false)) {
/* 106 */       return true;
/*     */     }
/*     */     
/* 109 */     if (oreDictionary) {
/* 110 */       int[] idBase = OreDictionary.getOreIDs(base);
/* 111 */       if (idBase.length > 0) {
/* 112 */         for (int id : idBase) {
/* 113 */           for (ItemStack itemstack : OreDictionary.getOres(OreDictionary.getOreName(id))) {
/* 114 */             if (comparison.func_77973_b() == itemstack.func_77973_b() && (itemstack.func_77960_j() == 32767 || comparison.func_77960_j() == itemstack.func_77960_j())) {
/* 115 */               return true;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 122 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isCraftingEquivalent(int[] oreIDs, ItemStack comparison) {
/* 126 */     if (oreIDs.length > 0) {
/* 127 */       for (int id : oreIDs) {
/* 128 */         for (ItemStack itemstack : OreDictionary.getOres(OreDictionary.getOreName(id))) {
/* 129 */           if (comparison.func_77973_b() == itemstack.func_77973_b() && (itemstack.func_77960_j() == 32767 || comparison.func_77960_j() == itemstack.func_77960_j())) {
/* 130 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 136 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isMatchingItemOrList(ItemStack a, ItemStack b) {
/* 140 */     if (a == null || b == null) {
/* 141 */       return false;
/*     */     }
/*     */     
/* 144 */     if (a.func_77973_b() instanceof IList) {
/* 145 */       IList list = (IList)a.func_77973_b();
/* 146 */       return list.matches(a, b);
/* 147 */     }  if (b.func_77973_b() instanceof IList) {
/* 148 */       IList list = (IList)b.func_77973_b();
/* 149 */       return list.matches(b, a);
/*     */     } 
/*     */     
/* 152 */     return isMatchingItem(a, b, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMatchingItem(ItemStack base, ItemStack comparison) {
/* 164 */     return isMatchingItem(base, comparison, true, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEqualItem(ItemStack a, ItemStack b) {
/* 171 */     if (isMatchingItem(a, b, false, true)) {
/* 172 */       return (isWildcard(a) || isWildcard(b) || a.func_77960_j() == b.func_77960_j());
/*     */     }
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMatchingItem(ItemStack a, ItemStack b, boolean matchDamage, boolean matchNBT) {
/* 190 */     if (a == null || b == null) {
/* 191 */       return false;
/*     */     }
/*     */     
/* 194 */     if (a.func_77973_b() != b.func_77973_b()) {
/* 195 */       return false;
/*     */     }
/*     */     
/* 198 */     if (isWildcard(a) || isWildcard(b)) {
/* 199 */       return true;
/*     */     }
/*     */     
/* 202 */     if (matchDamage && a.func_77981_g() && 
/* 203 */       a.func_77960_j() != b.func_77960_j()) {
/* 204 */       return false;
/*     */     }
/*     */     
/* 207 */     if (matchNBT && 
/* 208 */       !ItemStack.func_77970_a(a, b)) {
/* 209 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 213 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isWildcard(ItemStack stack) {
/* 217 */     return isWildcard(stack.func_77960_j());
/*     */   }
/*     */   
/*     */   public static boolean isWildcard(int damage) {
/* 221 */     return (damage == -1 || damage == 32767);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\StackHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */