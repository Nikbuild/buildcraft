/*    */ package buildcraft.core.builders.patterns;
/*    */ 
/*    */ import buildcraft.api.filler.IFillerPattern;
/*    */ import buildcraft.api.filler.IFillerRegistry;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.TreeMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FillerRegistry
/*    */   implements IFillerRegistry
/*    */ {
/* 21 */   private TreeMap<String, IFillerPattern> patterns = new TreeMap<String, IFillerPattern>();
/*    */ 
/*    */   
/*    */   public void addPattern(IFillerPattern pattern) {
/* 25 */     this.patterns.put(pattern.getUniqueTag(), pattern);
/*    */   }
/*    */ 
/*    */   
/*    */   public IFillerPattern getPattern(String patternName) {
/* 30 */     return this.patterns.get(patternName);
/*    */   }
/*    */ 
/*    */   
/*    */   public IFillerPattern getNextPattern(IFillerPattern currentPattern) {
/* 35 */     Map.Entry<String, IFillerPattern> pattern = this.patterns.higherEntry(currentPattern.getUniqueTag());
/* 36 */     if (pattern == null) {
/* 37 */       pattern = this.patterns.firstEntry();
/*    */     }
/* 39 */     return pattern.getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public IFillerPattern getPreviousPattern(IFillerPattern currentPattern) {
/* 44 */     Map.Entry<String, IFillerPattern> pattern = this.patterns.lowerEntry(currentPattern.getUniqueTag());
/* 45 */     if (pattern == null) {
/* 46 */       pattern = this.patterns.lastEntry();
/*    */     }
/* 48 */     return pattern.getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<IFillerPattern> getPatterns() {
/* 53 */     return Collections.unmodifiableCollection(this.patterns.values());
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\FillerRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */