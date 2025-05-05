/*     */ package buildcraft.transport;
/*     */ 
/*     */ import com.google.common.collect.ForwardingSet;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public class TravelerSet
/*     */   extends ForwardingSet<TravelingItem>
/*     */ {
/*     */   public boolean iterating;
/*  22 */   private final Set<TravelingItem> items = new HashSet<TravelingItem>();
/*  23 */   private final Set<TravelingItem> toLoad = new HashSet<TravelingItem>();
/*  24 */   private final Set<TravelingItem> toAdd = new HashSet<TravelingItem>();
/*  25 */   private final Set<TravelingItem> toRemove = new HashSet<TravelingItem>();
/*  26 */   private int delay = 0;
/*     */   private final PipeTransportItems transport;
/*     */   
/*     */   public TravelerSet(PipeTransportItems transport) {
/*  30 */     this.transport = transport;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<TravelingItem> delegate() {
/*  35 */     return this.items;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(TravelingItem item) {
/*  40 */     if (this.iterating) {
/*  41 */       return this.toAdd.add(item);
/*     */     }
/*  43 */     item.setContainer(this.transport.container);
/*  44 */     this.items.add(item);
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends TravelingItem> collection) {
/*  50 */     if (this.iterating) {
/*  51 */       return this.toAdd.addAll(collection);
/*     */     }
/*  53 */     return standardAddAll(collection);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object object) {
/*  58 */     if (this.iterating) {
/*  59 */       return this.toRemove.add((TravelingItem)object);
/*     */     }
/*  61 */     return delegate().remove(object);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> collection) {
/*  66 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> collection) {
/*  71 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   void scheduleLoad(TravelingItem item) {
/*  75 */     this.delay = 10;
/*  76 */     this.toLoad.add(item);
/*     */   }
/*     */   
/*     */   private void loadScheduledItems() {
/*  80 */     if (this.delay > 0) {
/*  81 */       this.delay--;
/*     */       return;
/*     */     } 
/*  84 */     addAll(this.toLoad);
/*  85 */     this.toLoad.clear();
/*     */   }
/*     */   
/*     */   private void addScheduledItems() {
/*  89 */     addAll(this.toAdd);
/*  90 */     this.toAdd.clear();
/*     */   }
/*     */   
/*     */   public boolean scheduleRemoval(TravelingItem item) {
/*  94 */     return this.toRemove.add(item);
/*     */   }
/*     */   
/*     */   public boolean unscheduleRemoval(TravelingItem item) {
/*  98 */     return this.toRemove.remove(item);
/*     */   }
/*     */   
/*     */   void removeScheduledItems() {
/* 102 */     for (TravelingItem i : this.toRemove) {
/* 103 */       i.cleanup();
/* 104 */       this.items.remove(i);
/*     */     } 
/* 106 */     this.toRemove.clear();
/*     */   }
/*     */   
/*     */   void flush() {
/* 110 */     loadScheduledItems();
/* 111 */     addScheduledItems();
/* 112 */     removeScheduledItems();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<TravelingItem> iterator() {
/* 117 */     return this.items.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 122 */     if (this.iterating) {
/* 123 */       this.toRemove.addAll((Collection<? extends TravelingItem>)this);
/*     */     } else {
/* 125 */       this.items.clear();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\TravelerSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */