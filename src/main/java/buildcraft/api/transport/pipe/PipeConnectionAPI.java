/*    */ package buildcraft.api.transport.pipe;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public final class PipeConnectionAPI
/*    */ {
/* 13 */   private static final Map<Block, ICustomPipeConnection> connections = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */   
/*    */   private static final ICustomPipeConnection NOTHING = (world, pos, face, state) -> 0.0F;
/*    */ 
/*    */ 
/*    */   
/*    */   public static void registerConnection(Block block, ICustomPipeConnection connection) {
/* 22 */     connections.put(block, connection);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void registerConnectionAsNothing(Block block) {
/* 28 */     connections.put(block, NOTHING);
/*    */   }
/*    */ 
/*    */   
/*    */   public static ICustomPipeConnection getCustomConnection(Block block) {
/* 33 */     if (block instanceof ICustomPipeConnection) {
/* 34 */       return (ICustomPipeConnection)block;
/*    */     }
/* 36 */     ICustomPipeConnection connection = connections.get(block);
/* 37 */     if (connection != null) {
/* 38 */       return connection;
/*    */     }
/* 40 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeConnectionAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */