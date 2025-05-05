/*    */ package buildcraft.silicon.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicTile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicLaserTableBase
/*    */   extends SchematicTile
/*    */ {
/*    */   public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
/* 18 */     super.initializeFromObjectAt(context, x, y, z);
/*    */     
/* 20 */     this.tileNBT.func_82580_o("energy");
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\schematics\SchematicLaserTableBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */