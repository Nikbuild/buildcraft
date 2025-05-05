/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XorShift128Random
/*    */ {
/* 10 */   private static final Random seed = new Random();
/*    */   private static final double DOUBLE_UNIT = 1.1102230246251565E-16D;
/* 12 */   private final long[] s = new long[2];
/*    */   
/*    */   public XorShift128Random() {
/* 15 */     this.s[0] = seed.nextLong();
/* 16 */     this.s[1] = seed.nextLong();
/*    */   }
/*    */   
/*    */   public long nextLong() {
/* 20 */     long s1 = this.s[0];
/* 21 */     long s0 = this.s[1];
/* 22 */     this.s[0] = s0;
/* 23 */     s1 ^= s1 << 23L;
/* 24 */     this.s[1] = (s1 ^ s0 ^ s1 >> 17L ^ s0 >> 26L) + s0;
/* 25 */     return this.s[1];
/*    */   }
/*    */   
/*    */   public int nextInt() {
/* 29 */     return (int)nextLong();
/*    */   }
/*    */   
/*    */   public boolean nextBoolean() {
/* 33 */     return ((nextLong() & 0x1L) != 0L);
/*    */   }
/*    */   
/*    */   public int nextInt(int size) {
/* 37 */     int nl = (int)nextLong();
/* 38 */     return (nl < 0) ? ((nl + Integer.MIN_VALUE) % size) : (nl % size);
/*    */   }
/*    */   
/*    */   public double nextDouble() {
/* 42 */     return (nextLong() & 0x1FFFFFFFFFFFFFL) * 1.1102230246251565E-16D;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\XorShift128Random.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */