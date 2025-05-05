/*    */ package buildcraft.core;
/*    */ 
/*    */ public enum PowerMode
/*    */ {
/*  5 */   M2(20), M4(40), M8(80), M16(160), M32(320), M64(640), M128(1280); public final int maxPower; static {
/*  6 */     VALUES = values();
/*    */   }
/*    */   public static final PowerMode[] VALUES;
/*    */   PowerMode(int max) {
/* 10 */     this.maxPower = max;
/*    */   }
/*    */   
/*    */   public PowerMode getNext() {
/* 14 */     PowerMode next = VALUES[(ordinal() + 1) % VALUES.length];
/* 15 */     return next;
/*    */   }
/*    */   
/*    */   public PowerMode getPrevious() {
/* 19 */     PowerMode previous = VALUES[(ordinal() + VALUES.length - 1) % VALUES.length];
/* 20 */     return previous;
/*    */   }
/*    */   
/*    */   public static PowerMode fromId(int id) {
/* 24 */     if (id < 0 || id >= VALUES.length) {
/* 25 */       return M128;
/*    */     }
/* 27 */     return VALUES[id];
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\PowerMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */