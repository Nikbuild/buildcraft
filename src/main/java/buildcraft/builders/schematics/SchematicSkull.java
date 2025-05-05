/*    */ package buildcraft.builders.schematics;
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
/*    */ public class SchematicSkull
/*    */   extends SchematicTile
/*    */ {
/*    */   public void rotateLeft(IBuilderContext context) {
/* 18 */     int rot = this.tileNBT.func_74771_c("Rot");
/*    */     
/* 20 */     rot = (rot + 4) % 16;
/*    */     
/* 22 */     this.tileNBT.func_74774_a("Rot", (byte)rot);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicSkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */