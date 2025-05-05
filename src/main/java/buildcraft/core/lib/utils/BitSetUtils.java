/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import java.util.BitSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BitSetUtils
/*    */ {
/*    */   public static BitSet fromByteArray(byte[] bytes) {
/* 11 */     BitSet bits = new BitSet(bytes.length * 8);
/* 12 */     for (int i = 0; i < bytes.length * 8; i++) {
/* 13 */       if ((bytes[i / 8] & 1 << i % 8) != 0) {
/* 14 */         bits.set(i);
/*    */       }
/*    */     } 
/* 17 */     return bits;
/*    */   }
/*    */   
/*    */   public static byte[] toByteArray(BitSet bits) {
/* 21 */     return toByteArray(bits, bits.size() + 7 >> 3);
/*    */   }
/*    */   
/*    */   public static byte[] toByteArray(BitSet bits, int sizeInBytes) {
/* 25 */     byte[] bytes = new byte[sizeInBytes];
/* 26 */     for (int i = 0; i < bits.length(); i++) {
/* 27 */       if (bits.get(i)) {
/* 28 */         bytes[i / 8] = (byte)(bytes[i / 8] | 1 << i % 8);
/*    */       }
/*    */     } 
/* 31 */     return bytes;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\BitSetUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */