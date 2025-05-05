/*    */ package buildcraft.core.list;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraftforge.oredict.OreDictionary;
/*    */ 
/*    */ public final class ListOreDictionaryCache
/*    */ {
/* 11 */   public static final ListOreDictionaryCache INSTANCE = new ListOreDictionaryCache();
/* 12 */   private static final String[] TYPE_KEYWORDS = new String[] { "Tiny", "Dense", "Small" };
/*    */ 
/*    */   
/* 15 */   private final Map<String, Set<Integer>> namingCache = new HashMap<String, Set<Integer>>();
/* 16 */   private final Set<String> registeredNames = new HashSet<String>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<Integer> getListOfPartialMatches(String part) {
/* 23 */     return this.namingCache.get(part);
/*    */   }
/*    */   
/*    */   private void addToNamingCache(String s, int id) {
/* 27 */     if (s == null) {
/*    */       return;
/*    */     }
/*    */     
/* 31 */     Set<Integer> ll = this.namingCache.get(s);
/*    */     
/* 33 */     if (ll == null) {
/* 34 */       ll = new HashSet<Integer>();
/* 35 */       ll.add(Integer.valueOf(id));
/* 36 */       this.namingCache.put(s, ll);
/*    */     } else {
/* 38 */       ll.add(Integer.valueOf(id));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getType(String name) {
/* 45 */     int splitLocation = name.length() - 1;
/* 46 */     while (splitLocation >= 0 && 
/* 47 */       !Character.isUpperCase(name.codePointAt(splitLocation)))
/*    */     {
/*    */       
/* 50 */       splitLocation--;
/*    */     }
/*    */     
/* 53 */     return (splitLocation >= 0) ? name.substring(0, splitLocation) : name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getMaterial(String name) {
/* 61 */     int splitLocation = 0;
/* 62 */     String t = null;
/* 63 */     while (splitLocation < name.length()) {
/* 64 */       if (!Character.isUpperCase(name.codePointAt(splitLocation))) {
/* 65 */         splitLocation++; continue;
/*    */       } 
/* 67 */       t = name.substring(splitLocation);
/* 68 */       for (String s : TYPE_KEYWORDS) {
/* 69 */         if (t.startsWith(s)) {
/* 70 */           t = null;
/*    */           break;
/*    */         } 
/*    */       } 
/* 74 */       if (t != null) {
/*    */         break;
/*    */       }
/* 77 */       splitLocation++;
/*    */     } 
/*    */ 
/*    */     
/* 81 */     return (splitLocation < name.length()) ? t : null;
/*    */   }
/*    */   
/*    */   public void registerName(String name) {
/* 85 */     if (this.registeredNames.contains(name)) {
/*    */       return;
/*    */     }
/*    */     
/* 89 */     int oreID = OreDictionary.getOreID(name);
/*    */     
/* 91 */     addToNamingCache(getType(name), oreID);
/* 92 */     addToNamingCache(getMaterial(name), oreID);
/*    */     
/* 94 */     this.registeredNames.add(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\list\ListOreDictionaryCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */