/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.core.builders.schematics.SchematicRotateMeta;
/*    */ 
/*    */ public class SchematicBuilderLike extends SchematicRotateMeta {
/*    */   public SchematicBuilderLike() {
/*  7 */     super(new int[] { 2, 5, 3, 4 }, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onNBTLoaded() {
/* 12 */     if (this.tileNBT != null) {
/* 13 */       this.tileNBT.func_82580_o("box");
/* 14 */       this.tileNBT.func_82580_o("bpt");
/* 15 */       this.tileNBT.func_82580_o("bptBuilder");
/* 16 */       this.tileNBT.func_82580_o("builderState");
/* 17 */       this.tileNBT.func_82580_o("done");
/* 18 */       this.tileNBT.func_82580_o("iterator");
/* 19 */       this.tileNBT.func_82580_o("path");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicBuilderLike.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */