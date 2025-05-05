/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicTile;
/*    */ 
/*    */ public class SchematicBrewingStand
/*    */   extends SchematicTile {
/*    */   public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
/*  9 */     super.initializeFromObjectAt(context, x, y, z);
/*    */     
/* 11 */     if (this.tileNBT != null)
/* 12 */       this.tileNBT.func_82580_o("BrewTime"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicBrewingStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */