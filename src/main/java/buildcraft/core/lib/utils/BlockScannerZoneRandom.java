/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import buildcraft.api.core.BlockIndex;
/*    */ import buildcraft.api.core.IZone;
/*    */ import java.util.Iterator;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class BlockScannerZoneRandom
/*    */   implements Iterable<BlockIndex>
/*    */ {
/*    */   private Random rand;
/*    */   private IZone zone;
/*    */   private int x;
/*    */   private int y;
/*    */   private int z;
/*    */   
/*    */   class BlockIt
/*    */     implements Iterator<BlockIndex>
/*    */   {
/*    */     public boolean hasNext() {
/* 21 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public BlockIndex next() {
/* 26 */       BlockIndex block = BlockScannerZoneRandom.this.zone.getRandomBlockIndex(BlockScannerZoneRandom.this.rand);
/* 27 */       return new BlockIndex(block.x - BlockScannerZoneRandom.this.x, block.y - BlockScannerZoneRandom.this.y, block.z - BlockScannerZoneRandom.this.z);
/*    */     }
/*    */ 
/*    */     
/*    */     public void remove() {}
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockScannerZoneRandom(int iX, int iY, int iZ, Random iRand, IZone iZone) {
/* 36 */     this.x = iX;
/* 37 */     this.y = iY;
/* 38 */     this.z = iZ;
/* 39 */     this.rand = iRand;
/* 40 */     this.zone = iZone;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<BlockIndex> iterator() {
/* 45 */     return new BlockIt();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\BlockScannerZoneRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */