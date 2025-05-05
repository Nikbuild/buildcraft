/*    */ package buildcraft.transport.gates;
/*    */ 
/*    */ import buildcraft.api.statements.StatementSlot;
/*    */ import buildcraft.api.transport.IPipe;
/*    */ import buildcraft.transport.Gate;
/*    */ import java.util.Iterator;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionIterator
/*    */   implements Iterable<StatementSlot>
/*    */ {
/*    */   private final IPipe pipe;
/*    */   
/*    */   public ActionIterator(IPipe iPipe) {
/* 23 */     this.pipe = iPipe;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<StatementSlot> iterator() {
/* 28 */     return new It();
/*    */   }
/*    */   
/*    */   private class It
/*    */     implements Iterator<StatementSlot> {
/* 33 */     private ForgeDirection curDir = ForgeDirection.values()[0];
/* 34 */     private int index = 0;
/*    */     private StatementSlot next;
/*    */     
/*    */     public It() {
/* 38 */       while (!isValid() && 
/* 39 */         this.curDir != ForgeDirection.UNKNOWN) {
/*    */         
/* 41 */         if (ActionIterator.this.pipe.getGate(this.curDir) == null || this.index >= ActionIterator.this
/* 42 */           .pipe.getGate(this.curDir).getActiveActions().size() - 1) {
/* 43 */           this.index = 0;
/* 44 */           this.curDir = ForgeDirection.values()[this.curDir.ordinal() + 1]; continue;
/*    */         } 
/* 46 */         this.index++;
/*    */       } 
/*    */ 
/*    */       
/* 50 */       if (isValid()) {
/* 51 */         this.next = ActionIterator.this.pipe.getGate(this.curDir).getActiveActions().get(this.index);
/*    */       }
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean hasNext() {
/* 57 */       return (this.next != null);
/*    */     }
/*    */ 
/*    */     
/*    */     public StatementSlot next() {
/* 62 */       StatementSlot result = this.next;
/*    */       
/*    */       do {
/* 65 */         if (this.index < Gate.MAX_STATEMENTS - 1) {
/* 66 */           this.index++;
/* 67 */         } else if (this.curDir != ForgeDirection.UNKNOWN) {
/* 68 */           this.index = 0;
/* 69 */           this.curDir = ForgeDirection.values()[this.curDir.ordinal() + 1];
/*    */         } else {
/*    */           
/*    */           break;
/*    */         } 
/* 74 */       } while (!isValid());
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 79 */       if (isValid()) {
/* 80 */         this.next = ActionIterator.this.pipe.getGate(this.curDir).getActiveActions().get(this.index);
/*    */       } else {
/* 82 */         this.next = null;
/*    */       } 
/*    */       
/* 85 */       return result;
/*    */     }
/*    */ 
/*    */     
/*    */     public void remove() {
/* 90 */       throw new UnsupportedOperationException("Remove not supported.");
/*    */     }
/*    */     
/*    */     private boolean isValid() {
/* 94 */       return (this.curDir != ForgeDirection.UNKNOWN && ActionIterator.this
/* 95 */         .pipe.getGate(this.curDir) != null && this.index < ActionIterator.this
/* 96 */         .pipe.getGate(this.curDir).getActiveActions().size());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gates\ActionIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */