/*    */ package buildcraft.transport.utils;
/*    */ 
/*    */ import java.util.BitSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BitSetCodec
/*    */ {
/*    */   public byte encode(BitSet set) {
/* 15 */     byte result = 0;
/* 16 */     for (byte i = 0; i < 8; i = (byte)(i + 1)) {
/* 17 */       result = (byte)(result | (set.get(i) ? (1 << i) : 0));
/*    */     }
/* 19 */     return result;
/*    */   }
/*    */   
/*    */   public void decode(byte data, BitSet target) {
/* 23 */     byte localData = data;
/* 24 */     int t = 1;
/*    */     
/* 26 */     target.clear();
/*    */     byte i;
/* 28 */     for (i = 0; i < 8; i = (byte)(i + 1)) {
/* 29 */       target.set(i, ((localData & t) != 0));
/* 30 */       t <<= 1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transpor\\utils\BitSetCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */