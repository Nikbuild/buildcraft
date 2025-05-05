/*    */ package buildcraft.api.transport;
/*    */ 
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ public class WireNode
/*    */ {
/*    */   public final BlockPos pos;
/*    */   public final EnumWirePart part;
/*    */   private final int hash;
/*    */   
/*    */   public WireNode(BlockPos pos, EnumWirePart part) {
/* 16 */     this.pos = pos;
/* 17 */     this.part = part;
/* 18 */     this.hash = pos.hashCode() * 31 + part.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 23 */     return this.hash;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 28 */     if (this == obj) return true; 
/* 29 */     if (obj == null) return false; 
/* 30 */     if (getClass() != obj.getClass()) return false; 
/* 31 */     WireNode other = (WireNode)obj;
/* 32 */     return (this.part == other.part && this.pos
/* 33 */       .equals(other.pos));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 38 */     return "(" + this.pos.func_177958_n() + ", " + this.pos.func_177956_o() + ", " + this.pos.func_177952_p() + ", " + this.part + ")";
/*    */   }
/*    */   
/*    */   public WireNode offset(EnumFacing face) {
/* 42 */     int nx = ((this.part.x == EnumFacing.AxisDirection.POSITIVE) ? 1 : 0) + face.func_82601_c();
/* 43 */     int ny = ((this.part.y == EnumFacing.AxisDirection.POSITIVE) ? 1 : 0) + face.func_96559_d();
/* 44 */     int nz = ((this.part.z == EnumFacing.AxisDirection.POSITIVE) ? 1 : 0) + face.func_82599_e();
/* 45 */     EnumWirePart nPart = EnumWirePart.get(nx, ny, nz);
/* 46 */     if (nx < 0 || ny < 0 || nz < 0 || nx > 1 || ny > 1 || nz > 1) {
/* 47 */       return new WireNode(this.pos.func_177972_a(face), nPart);
/*    */     }
/* 49 */     return new WireNode(this.pos, nPart);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<EnumFacing, WireNode> getAllPossibleConnections() {
/* 54 */     Map<EnumFacing, WireNode> map = new EnumMap<>(EnumFacing.class);
/*    */     
/* 56 */     for (EnumFacing face : EnumFacing.field_82609_l) {
/* 57 */       map.put(face, offset(face));
/*    */     }
/* 59 */     return map;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\WireNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */