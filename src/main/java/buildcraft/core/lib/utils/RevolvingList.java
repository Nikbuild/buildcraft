/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import com.google.common.collect.ForwardingCollection;
/*    */ import java.util.Collection;
/*    */ import java.util.Deque;
/*    */ import java.util.LinkedList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RevolvingList<T>
/*    */   extends ForwardingCollection<T>
/*    */ {
/* 19 */   private Deque<T> list = new LinkedList<T>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RevolvingList(Collection<? extends T> collection) {
/* 25 */     this.list.addAll(collection);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Collection<T> delegate() {
/* 30 */     return this.list;
/*    */   }
/*    */   
/*    */   public void rotateLeft() {
/* 34 */     if (this.list.isEmpty()) {
/*    */       return;
/*    */     }
/* 37 */     this.list.addFirst(this.list.removeLast());
/*    */   }
/*    */ 
/*    */   
/*    */   public void rotateRight() {
/* 42 */     if (this.list.isEmpty()) {
/*    */       return;
/*    */     }
/* 45 */     this.list.addLast(this.list.removeFirst());
/*    */   }
/*    */ 
/*    */   
/*    */   public T getCurrent() {
/* 50 */     if (this.list.isEmpty()) {
/* 51 */       return null;
/*    */     }
/* 53 */     return this.list.getFirst();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCurrent(T e) {
/* 58 */     if (!contains(e))
/*    */       return; 
/* 60 */     if (e == null) {
/* 61 */       while (getCurrent() != null) {
/* 62 */         rotateRight();
/*    */       }
/*    */     } else {
/* 65 */       while (getCurrent() == null || !getCurrent().equals(e))
/* 66 */         rotateRight(); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public RevolvingList() {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\RevolvingList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */