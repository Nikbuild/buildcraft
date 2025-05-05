/*    */ package buildcraft.core.builders;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuildingSlotIterator
/*    */ {
/* 15 */   private static int ITERATIONS_MAX = 500;
/*    */ 
/*    */   
/*    */   private final LinkedList<BuildingSlotBlock> buildList;
/*    */   
/*    */   private Iterator<BuildingSlotBlock> current;
/*    */   
/*    */   private int nbIterations;
/*    */ 
/*    */   
/*    */   public BuildingSlotIterator(LinkedList<BuildingSlotBlock> buildList) {
/* 26 */     this.buildList = buildList;
/*    */   }
/*    */   
/*    */   public void startIteration() {
/* 30 */     if (this.current == null || !this.current.hasNext()) {
/* 31 */       this.current = this.buildList.iterator();
/*    */     }
/*    */     
/* 34 */     this.nbIterations = 0;
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 38 */     return (this.current.hasNext() && this.nbIterations < ITERATIONS_MAX);
/*    */   }
/*    */   
/*    */   public BuildingSlotBlock next() {
/* 42 */     BuildingSlotBlock next = this.current.next();
/*    */     
/* 44 */     if (next == null)
/*    */     {
/*    */ 
/*    */       
/* 48 */       if (this.nbIterations == 0) {
/* 49 */         this.current.remove();
/*    */       }
/*    */     }
/*    */     
/* 53 */     this.nbIterations++;
/* 54 */     return next;
/*    */   }
/*    */   
/*    */   public void remove() {
/* 58 */     this.current.remove();
/*    */   }
/*    */   
/*    */   public void reset() {
/* 62 */     this.current = this.buildList.iterator();
/* 63 */     this.nbIterations = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\BuildingSlotIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */