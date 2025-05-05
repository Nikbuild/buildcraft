/*    */ package buildcraft.core.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.BuildingPermission;
/*    */ import buildcraft.api.blueprints.SchematicBlock;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicBlockCreative
/*    */   extends SchematicBlock
/*    */ {
/*    */   public BuildingPermission getBuildingPermission() {
/* 18 */     return BuildingPermission.CREATIVE_ONLY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\schematics\SchematicBlockCreative.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */