/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import buildcraft.api.core.BlockIndex;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class BlockScannerExpanding
/*    */   implements Iterable<BlockIndex>
/*    */ {
/*    */   private int searchRadius;
/*    */   private int searchX;
/*    */   private int searchY;
/*    */   private int searchZ;
/*    */   
/*    */   class BlockIt
/*    */     implements Iterator<BlockIndex>
/*    */   {
/*    */     public boolean hasNext() {
/* 18 */       return (BlockScannerExpanding.this.searchRadius < 64);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public BlockIndex next() {
/* 26 */       BlockIndex next = new BlockIndex(BlockScannerExpanding.this.searchX, BlockScannerExpanding.this.searchY, BlockScannerExpanding.this.searchZ);
/*    */ 
/*    */       
/* 29 */       if (Math.abs(BlockScannerExpanding.this.searchX) == BlockScannerExpanding.this.searchRadius || Math.abs(BlockScannerExpanding.this.searchZ) == BlockScannerExpanding.this.searchRadius) {
/* 30 */         BlockScannerExpanding.this.searchY = BlockScannerExpanding.this.searchY + 1;
/*    */       } else {
/* 32 */         BlockScannerExpanding.this.searchY = BlockScannerExpanding.this.searchY + BlockScannerExpanding.this.searchRadius * 2;
/*    */       } 
/*    */       
/* 35 */       if (BlockScannerExpanding.this.searchY > BlockScannerExpanding.this.searchRadius) {
/*    */         
/* 37 */         BlockScannerExpanding.this.searchY = -BlockScannerExpanding.this.searchRadius;
/* 38 */         BlockScannerExpanding.this.searchZ = BlockScannerExpanding.this.searchZ + 1;
/*    */         
/* 40 */         if (BlockScannerExpanding.this.searchZ > BlockScannerExpanding.this.searchRadius) {
/*    */           
/* 42 */           BlockScannerExpanding.this.searchZ = -BlockScannerExpanding.this.searchRadius;
/* 43 */           BlockScannerExpanding.this.searchX = BlockScannerExpanding.this.searchX + 1;
/*    */           
/* 45 */           if (BlockScannerExpanding.this.searchX > BlockScannerExpanding.this.searchRadius) {
/*    */             
/* 47 */             BlockScannerExpanding.this.searchRadius = BlockScannerExpanding.this.searchRadius + 1;
/* 48 */             BlockScannerExpanding.this.searchX = -BlockScannerExpanding.this.searchRadius;
/* 49 */             BlockScannerExpanding.this.searchY = -BlockScannerExpanding.this.searchRadius;
/* 50 */             BlockScannerExpanding.this.searchZ = -BlockScannerExpanding.this.searchRadius;
/*    */           } 
/*    */         } 
/*    */       } 
/* 54 */       return next;
/*    */     }
/*    */ 
/*    */     
/*    */     public void remove() {}
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockScannerExpanding() {
/* 63 */     this.searchRadius = 1;
/* 64 */     this.searchX = -1;
/* 65 */     this.searchY = -1;
/* 66 */     this.searchZ = -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<BlockIndex> iterator() {
/* 71 */     return new BlockIt();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\BlockScannerExpanding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */