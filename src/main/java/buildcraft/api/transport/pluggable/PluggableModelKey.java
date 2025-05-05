/*    */ package buildcraft.api.transport.pluggable;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import net.minecraft.util.BlockRenderLayer;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public abstract class PluggableModelKey
/*    */ {
/*    */   public final BlockRenderLayer layer;
/*    */   public final EnumFacing side;
/*    */   private final int hash;
/*    */   
/*    */   public PluggableModelKey(BlockRenderLayer layer, EnumFacing side) {
/* 14 */     if (layer != BlockRenderLayer.CUTOUT && layer != BlockRenderLayer.TRANSLUCENT) {
/* 15 */       throw new IllegalArgumentException("Can only use CUTOUT or TRANSLUCENT at the moment (was " + layer + ")");
/*    */     }
/* 17 */     if (side == null) throw new NullPointerException("side"); 
/* 18 */     this.layer = layer;
/* 19 */     this.side = side;
/* 20 */     this.hash = Objects.hash(new Object[] { layer, side });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 25 */     if (obj == this) return true; 
/* 26 */     if (obj == null) return false; 
/* 27 */     if (getClass() != obj.getClass()) return false; 
/* 28 */     PluggableModelKey other = (PluggableModelKey)obj;
/* 29 */     if (this.layer != other.layer) return false; 
/* 30 */     if (this.side != other.side) return false; 
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 36 */     return this.hash;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pluggable\PluggableModelKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */