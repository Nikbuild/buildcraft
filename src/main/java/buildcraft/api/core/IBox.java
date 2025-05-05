/*    */ package buildcraft.api.core;
/*    */ 
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IBox
/*    */   extends IZone
/*    */ {
/*    */   IBox expand(int paramInt);
/*    */   
/*    */   IBox contract(int paramInt);
/*    */   
/*    */   BlockPos min();
/*    */   
/*    */   BlockPos max();
/*    */   
/*    */   default BlockPos size() {
/* 21 */     return max().func_177973_b((Vec3i)min()).func_177982_a(1, 1, 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\IBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */