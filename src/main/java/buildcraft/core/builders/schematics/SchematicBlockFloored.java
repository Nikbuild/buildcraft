/*    */ package buildcraft.core.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicBlock;
/*    */ import buildcraft.api.core.BlockIndex;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicBlockFloored
/*    */   extends SchematicBlock
/*    */ {
/*    */   public Set<BlockIndex> getPrerequisiteBlocks(IBuilderContext context) {
/* 16 */     return Sets.newHashSet((Object[])new BlockIndex[] { RELATIVE_INDEXES[ForgeDirection.DOWN.ordinal()] });
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\schematics\SchematicBlockFloored.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */