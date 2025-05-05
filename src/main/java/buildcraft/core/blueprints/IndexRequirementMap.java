/*    */ package buildcraft.core.blueprints;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicBlock;
/*    */ import buildcraft.api.core.BlockIndex;
/*    */ import buildcraft.api.core.Position;
/*    */ import buildcraft.core.builders.BuildingSlotBlock;
/*    */ import com.google.common.collect.HashMultimap;
/*    */ import com.google.common.collect.Multimap;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class IndexRequirementMap
/*    */ {
/* 15 */   private final Multimap<BlockIndex, BlockIndex> requirements = (Multimap<BlockIndex, BlockIndex>)HashMultimap.create();
/* 16 */   private final Multimap<BlockIndex, BlockIndex> requirementsInv = (Multimap<BlockIndex, BlockIndex>)HashMultimap.create();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(BuildingSlotBlock b, IBuilderContext context) {
/* 23 */     if (b.schematic instanceof SchematicBlock) {
/* 24 */       BlockIndex index = new BlockIndex(b.x, b.y, b.z);
/* 25 */       Set<BlockIndex> prereqs = ((SchematicBlock)b.schematic).getPrerequisiteBlocks(context);
/*    */       
/* 27 */       if (prereqs != null && prereqs.size() > 0) {
/* 28 */         Position min = context.surroundingBox().pMin();
/* 29 */         Position max = context.surroundingBox().pMax();
/* 30 */         for (BlockIndex i : prereqs) {
/* 31 */           BlockIndex ia = new BlockIndex(i.x + index.x, i.y + index.y, i.z + index.z);
/* 32 */           if (ia.equals(index) || ia.x < min.x || ia.y < min.y || ia.z < min.z || ia.x > max.x || ia.y > max.y || ia.z > max.z) {
/*    */             continue;
/*    */           }
/* 35 */           this.requirements.put(index, ia);
/* 36 */           this.requirementsInv.put(ia, index);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean contains(BlockIndex index) {
/* 43 */     return this.requirements.containsKey(index);
/*    */   }
/*    */   
/*    */   public void remove(BuildingSlotBlock b) {
/* 47 */     BlockIndex index = new BlockIndex(b.x, b.y, b.z);
/* 48 */     remove(index);
/*    */   }
/*    */   
/*    */   public void remove(BlockIndex index) {
/* 52 */     for (BlockIndex reqingIndex : this.requirementsInv.get(index)) {
/* 53 */       this.requirements.remove(reqingIndex, index);
/*    */     }
/* 55 */     this.requirementsInv.removeAll(index);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\IndexRequirementMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */