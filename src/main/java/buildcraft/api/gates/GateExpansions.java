/*    */ package buildcraft.api.gates;
/*    */ 
/*    */ import com.google.common.collect.HashBiMap;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ public final class GateExpansions
/*    */ {
/* 22 */   private static final Map<String, IGateExpansion> expansions = new HashMap<String, IGateExpansion>();
/* 23 */   private static final ArrayList<IGateExpansion> expansionIDs = new ArrayList<IGateExpansion>();
/* 24 */   private static final Map<IGateExpansion, ItemStack> recipes = (Map<IGateExpansion, ItemStack>)HashBiMap.create();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void registerExpansion(IGateExpansion expansion) {
/* 30 */     registerExpansion(expansion.getUniqueIdentifier(), expansion);
/*    */   }
/*    */   
/*    */   public static void registerExpansion(String identifier, IGateExpansion expansion) {
/* 34 */     expansions.put(identifier, expansion);
/* 35 */     expansionIDs.add(expansion);
/*    */   }
/*    */   
/*    */   public static void registerExpansion(IGateExpansion expansion, ItemStack addedRecipe) {
/* 39 */     registerExpansion(expansion.getUniqueIdentifier(), expansion);
/* 40 */     recipes.put(expansion, addedRecipe);
/*    */   }
/*    */   
/*    */   public static IGateExpansion getExpansion(String identifier) {
/* 44 */     return expansions.get(identifier);
/*    */   }
/*    */   
/*    */   public static Set<IGateExpansion> getExpansions() {
/* 48 */     Set<IGateExpansion> set = new HashSet<IGateExpansion>();
/* 49 */     set.addAll(expansionIDs);
/* 50 */     return set;
/*    */   }
/*    */   
/*    */   public static Map<IGateExpansion, ItemStack> getRecipesForPostInit() {
/* 54 */     return recipes;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static IGateExpansion getExpansionByID(int id) {
/* 60 */     return expansionIDs.get(id);
/*    */   }
/*    */   
/*    */   public static int getExpansionID(IGateExpansion expansion) {
/* 64 */     return expansionIDs.indexOf(expansion);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\gates\GateExpansions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */