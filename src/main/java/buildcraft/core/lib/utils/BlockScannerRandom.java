/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import buildcraft.api.core.BlockIndex;
/*    */ import java.util.Iterator;
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ public class BlockScannerRandom
/*    */   implements Iterable<BlockIndex>
/*    */ {
/*    */   private Random rand;
/*    */   private int maxDistance;
/*    */   
/*    */   class BlockIt
/*    */     implements Iterator<BlockIndex>
/*    */   {
/*    */     public boolean hasNext() {
/* 19 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public BlockIndex next() {
/* 24 */       double radius = (BlockScannerRandom.this.rand.nextFloat() * BlockScannerRandom.this.maxDistance);
/* 25 */       float polarAngle = BlockScannerRandom.this.rand.nextFloat() * 2.0F * 3.1415927F;
/* 26 */       float azimuthAngle = BlockScannerRandom.this.rand.nextFloat() * 3.1415927F;
/*    */       
/* 28 */       int searchX = (int)(radius * MathHelper.func_76134_b(polarAngle) * MathHelper.func_76126_a(azimuthAngle));
/* 29 */       int searchY = (int)(radius * MathHelper.func_76134_b(azimuthAngle));
/* 30 */       int searchZ = (int)(radius * MathHelper.func_76126_a(polarAngle) * MathHelper.func_76126_a(azimuthAngle));
/*    */       
/* 32 */       return new BlockIndex(searchX, searchY, searchZ);
/*    */     }
/*    */ 
/*    */     
/*    */     public void remove() {}
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockScannerRandom(Random iRand, int iMaxDistance) {
/* 41 */     this.rand = iRand;
/* 42 */     this.maxDistance = iMaxDistance;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<BlockIndex> iterator() {
/* 47 */     return new BlockIt();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\BlockScannerRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */