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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicWallSide
/*    */   extends SchematicBlock
/*    */ {
/*    */   public Set<BlockIndex> getPrerequisiteBlocks(IBuilderContext context) {
/* 24 */     int yPos = 0;
/* 25 */     int yNeg = 5;
/* 26 */     int xPos = 2;
/* 27 */     int xNeg = 1;
/* 28 */     int zPos = 4;
/* 29 */     int zNeg = 3;
/*    */     
/* 31 */     switch (this.meta & 0x7) {
/*    */       case 2:
/* 33 */         return Sets.newHashSet((Object[])new BlockIndex[] { RELATIVE_INDEXES[ForgeDirection.EAST.ordinal()] });
/*    */       case 1:
/* 35 */         return Sets.newHashSet((Object[])new BlockIndex[] { RELATIVE_INDEXES[ForgeDirection.WEST.ordinal()] });
/*    */       case 0:
/*    */       case 7:
/* 38 */         return Sets.newHashSet((Object[])new BlockIndex[] { RELATIVE_INDEXES[ForgeDirection.UP.ordinal()] });
/*    */       case 5:
/*    */       case 6:
/* 41 */         return Sets.newHashSet((Object[])new BlockIndex[] { RELATIVE_INDEXES[ForgeDirection.DOWN.ordinal()] });
/*    */       case 4:
/* 43 */         return Sets.newHashSet((Object[])new BlockIndex[] { RELATIVE_INDEXES[ForgeDirection.SOUTH.ordinal()] });
/*    */       case 3:
/* 45 */         return Sets.newHashSet((Object[])new BlockIndex[] { RELATIVE_INDEXES[ForgeDirection.NORTH.ordinal()] });
/*    */     } 
/* 47 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void rotateLeft(IBuilderContext context) {
/* 52 */     int xPos = 2;
/* 53 */     int xNeg = 1;
/* 54 */     int zPos = 4;
/* 55 */     int zNeg = 3;
/*    */     
/* 57 */     switch (this.meta & 0x7) {
/*    */       case 2:
/* 59 */         this.meta = this.meta & 0x8 | 0x4;
/*    */         break;
/*    */       case 3:
/* 62 */         this.meta = this.meta & 0x8 | 0x2;
/*    */         break;
/*    */       case 1:
/* 65 */         this.meta = this.meta & 0x8 | 0x3;
/*    */         break;
/*    */       case 4:
/* 68 */         this.meta = this.meta & 0x8 | 0x1;
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\schematics\SchematicWallSide.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */