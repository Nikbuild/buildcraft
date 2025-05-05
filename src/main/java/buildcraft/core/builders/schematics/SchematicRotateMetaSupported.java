/*    */ package buildcraft.core.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.core.BlockIndex;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ public class SchematicRotateMetaSupported
/*    */   extends SchematicRotateMeta
/*    */ {
/*    */   public SchematicRotateMetaSupported(int[] rotations, boolean rotateForward) {
/* 14 */     super(rotations, rotateForward);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<BlockIndex> getPrerequisiteBlocks(IBuilderContext context) {
/* 19 */     int pos = this.meta & this.infoMask;
/* 20 */     if (pos == this.rot[0])
/* 21 */       return Sets.newHashSet((Object[])new BlockIndex[] { RELATIVE_INDEXES[ForgeDirection.NORTH.ordinal()] }); 
/* 22 */     if (pos == this.rot[1])
/* 23 */       return Sets.newHashSet((Object[])new BlockIndex[] { RELATIVE_INDEXES[ForgeDirection.EAST.ordinal()] }); 
/* 24 */     if (pos == this.rot[2])
/* 25 */       return Sets.newHashSet((Object[])new BlockIndex[] { RELATIVE_INDEXES[ForgeDirection.SOUTH.ordinal()] }); 
/* 26 */     if (pos == this.rot[3]) {
/* 27 */       return Sets.newHashSet((Object[])new BlockIndex[] { RELATIVE_INDEXES[ForgeDirection.WEST.ordinal()] });
/*    */     }
/* 29 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\schematics\SchematicRotateMetaSupported.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */