/*     */ package buildcraft.core.list;
/*     */ 
/*     */ import buildcraft.api.lists.ListMatchHandler;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ public class ListMatchHandlerOreDictionary
/*     */   extends ListMatchHandler
/*     */ {
/*     */   private int getUppercaseCount(String s) {
/*  17 */     int j = 0;
/*  18 */     for (int i = 0; i < s.length(); i++) {
/*  19 */       if (Character.isUpperCase(s.codePointAt(i))) {
/*  20 */         j++;
/*     */       }
/*     */     } 
/*  23 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(ListMatchHandler.Type type, ItemStack stack, ItemStack target, boolean precise) {
/*  28 */     int[] oreIds = OreDictionary.getOreIDs(stack);
/*     */     
/*  30 */     if (oreIds.length == 0) {
/*     */       
/*  32 */       if (type == ListMatchHandler.Type.TYPE) {
/*  33 */         return StackHelper.isMatchingItem(stack, target, false, false);
/*     */       }
/*  35 */       return false;
/*     */     } 
/*     */     
/*  38 */     int[] matchesIds = OreDictionary.getOreIDs(target);
/*     */     
/*  40 */     if (type == ListMatchHandler.Type.CLASS) {
/*  41 */       for (int i : oreIds) {
/*  42 */         for (int j : matchesIds) {
/*  43 */           if (i == j) {
/*  44 */             return true;
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  52 */       String s = getBestOreString(oreIds);
/*  53 */       if (s != null) {
/*  54 */         Set<Integer> stackIds = ListOreDictionaryCache.INSTANCE.getListOfPartialMatches((type == ListMatchHandler.Type.MATERIAL) ? 
/*  55 */             ListOreDictionaryCache.getMaterial(s) : ListOreDictionaryCache.getType(s));
/*     */         
/*  57 */         if (stackIds != null) {
/*  58 */           for (Iterator<Integer> iterator = stackIds.iterator(); iterator.hasNext(); ) { int j = ((Integer)iterator.next()).intValue();
/*  59 */             for (int k : matchesIds) {
/*  60 */               if (j == k) {
/*  61 */                 return true;
/*     */               }
/*     */             }  }
/*     */         
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidSource(ListMatchHandler.Type type, ItemStack stack) {
/*  74 */     if ((OreDictionary.getOreIDs(stack)).length > 0) {
/*  75 */       return true;
/*     */     }
/*  77 */     if (type == ListMatchHandler.Type.TYPE && stack.func_77981_g()) {
/*  78 */       return true;
/*     */     }
/*  80 */     return false;
/*     */   }
/*     */   
/*     */   private String getBestOreString(int[] oreIds) {
/*  84 */     String s = null;
/*  85 */     int suc = 0;
/*  86 */     for (int i : oreIds) {
/*  87 */       String st = OreDictionary.getOreName(i);
/*  88 */       int suct = getUppercaseCount(st);
/*  89 */       if (s == null || suct > suc) {
/*  90 */         s = st;
/*  91 */         suc = suct;
/*     */       } 
/*     */     } 
/*  94 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getClientExamples(ListMatchHandler.Type type, ItemStack stack) {
/*  99 */     int[] oreIds = OreDictionary.getOreIDs(stack);
/* 100 */     List<ItemStack> stacks = new ArrayList<ItemStack>();
/*     */     
/* 102 */     if (oreIds.length == 0) {
/*     */       
/* 104 */       if (type == ListMatchHandler.Type.TYPE) {
/* 105 */         List<ItemStack> tempStack = new ArrayList<ItemStack>();
/* 106 */         stack.func_77973_b().func_150895_a(stack.func_77973_b(), CreativeTabs.field_78026_f, tempStack);
/* 107 */         for (ItemStack is : tempStack) {
/* 108 */           if (is.func_77973_b() == stack.func_77973_b()) {
/* 109 */             stacks.add(is);
/*     */           }
/*     */         } 
/*     */       } 
/* 113 */       return stacks;
/*     */     } 
/*     */     
/* 116 */     if (type == ListMatchHandler.Type.CLASS) {
/* 117 */       for (int i : oreIds) {
/* 118 */         stacks.addAll(OreDictionary.getOres(Integer.valueOf(i)));
/*     */       }
/*     */     } else {
/* 121 */       String s = getBestOreString(oreIds);
/* 122 */       if (s != null) {
/* 123 */         Set<Integer> stackIds = ListOreDictionaryCache.INSTANCE.getListOfPartialMatches((type == ListMatchHandler.Type.MATERIAL) ? 
/* 124 */             ListOreDictionaryCache.getMaterial(s) : ListOreDictionaryCache.getType(s));
/*     */         
/* 126 */         if (stackIds != null) {
/* 127 */           for (Iterator<Integer> iterator = stackIds.iterator(); iterator.hasNext(); ) { int j = ((Integer)iterator.next()).intValue();
/* 128 */             stacks.addAll(OreDictionary.getOres(Integer.valueOf(j))); }
/*     */         
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     List<ItemStack> wildcard = new ArrayList<ItemStack>();
/*     */     
/* 136 */     for (ItemStack is : stacks) {
/* 137 */       if (is != null && is.func_77960_j() == 32767 && is.func_77981_g()) {
/* 138 */         wildcard.add(is);
/*     */       }
/*     */     } 
/* 141 */     for (ItemStack is : wildcard) {
/* 142 */       List<ItemStack> wll = new ArrayList<ItemStack>();
/* 143 */       is.func_77973_b().func_150895_a(is.func_77973_b(), CreativeTabs.field_78026_f, wll);
/* 144 */       if (wll.size() > 0) {
/* 145 */         stacks.remove(is);
/* 146 */         stacks.addAll(wll);
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     return stacks;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\list\ListMatchHandlerOreDictionary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */