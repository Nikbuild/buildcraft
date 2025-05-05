/*    */ package buildcraft.core.lib.gui.tooltips;
/*    */ 
/*    */ import com.google.common.collect.ForwardingList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ToolTip
/*    */   extends ForwardingList<ToolTipLine>
/*    */ {
/* 19 */   private final List<ToolTipLine> delegate = new ArrayList<ToolTipLine>();
/*    */   private final long delay;
/*    */   private long mouseOverStart;
/*    */   
/*    */   public ToolTip(ToolTipLine... lines) {
/* 24 */     this.delay = 0L;
/* 25 */     Collections.addAll(this.delegate, lines);
/*    */   }
/*    */   
/*    */   public ToolTip(int delay, ToolTipLine... lines) {
/* 29 */     this.delay = delay;
/* 30 */     Collections.addAll(this.delegate, lines);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final List<ToolTipLine> delegate() {
/* 35 */     return this.delegate;
/*    */   }
/*    */   
/*    */   public void onTick(boolean mouseOver) {
/* 39 */     if (this.delay == 0L) {
/*    */       return;
/*    */     }
/* 42 */     if (mouseOver) {
/* 43 */       if (this.mouseOverStart == 0L) {
/* 44 */         this.mouseOverStart = System.currentTimeMillis();
/*    */       }
/*    */     } else {
/* 47 */       this.mouseOverStart = 0L;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isReady() {
/* 52 */     if (this.delay == 0L) {
/* 53 */       return true;
/*    */     }
/* 55 */     if (this.mouseOverStart == 0L) {
/* 56 */       return false;
/*    */     }
/* 58 */     return (System.currentTimeMillis() - this.mouseOverStart >= this.delay);
/*    */   }
/*    */   
/*    */   public void refresh() {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\tooltips\ToolTip.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */