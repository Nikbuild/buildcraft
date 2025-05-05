/*    */ package buildcraft.api.schematics;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicBlockContext
/*    */ {
/*    */   @Nonnull
/*    */   public final World world;
/*    */   @Nonnull
/*    */   public final BlockPos basePos;
/*    */   @Nonnull
/*    */   public final BlockPos pos;
/*    */   @Nonnull
/*    */   public final IBlockState blockState;
/*    */   @Nonnull
/*    */   public final Block block;
/*    */   
/*    */   public SchematicBlockContext(@Nonnull World world, @Nonnull BlockPos basePos, @Nonnull BlockPos pos, @Nonnull IBlockState blockState, @Nonnull Block block) {
/* 27 */     this.world = world;
/* 28 */     this.basePos = basePos;
/* 29 */     this.pos = pos;
/* 30 */     this.blockState = blockState;
/* 31 */     this.block = block;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\schematics\SchematicBlockContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */