/*    */ package buildcraft.api.schematics;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicEntityContext
/*    */ {
/*    */   @Nonnull
/*    */   public final World world;
/*    */   @Nonnull
/*    */   public final BlockPos basePos;
/*    */   @Nonnull
/*    */   public final Entity entity;
/*    */   
/*    */   public SchematicEntityContext(@Nonnull World world, @Nonnull BlockPos basePos, @Nonnull Entity entity) {
/* 20 */     this.world = world;
/* 21 */     this.basePos = basePos;
/* 22 */     this.entity = entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\schematics\SchematicEntityContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */