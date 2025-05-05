/*    */ package buildcraft.core.properties;
/*    */ 
/*    */ import java.util.BitSet;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkProperty
/*    */ {
/*    */   private BitSet property;
/*    */   private int worldHeight;
/*    */   private int xPosition;
/*    */   private int zPosition;
/*    */   private IBlockAccess blockAccess;
/*    */   
/*    */   public ChunkProperty(IBlockAccess iBlockAccess, int iWorldHeight, int iXPosition, int iZPosition) {
/* 23 */     this.worldHeight = iWorldHeight;
/* 24 */     this.property = new BitSet(256 * this.worldHeight / 8);
/* 25 */     this.xPosition = iXPosition;
/* 26 */     this.zPosition = iZPosition;
/* 27 */     this.blockAccess = iBlockAccess;
/*    */   }
/*    */   
/*    */   public boolean get(int xChunk, int y, int zChunk) {
/* 31 */     return this.property.get(xChunk * this.worldHeight * 16 + y * 16 + zChunk);
/*    */   }
/*    */   
/*    */   public void set(int xChunk, int y, int zChunk, boolean value) {
/* 35 */     this.property.set(xChunk * this.worldHeight * 16 + y * 16 + zChunk, value);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\properties\ChunkProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */