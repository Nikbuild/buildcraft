/*    */ package buildcraft.core.builders.schematics;
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
/*    */ public class SchematicRotateMeta
/*    */   extends SchematicTile
/*    */ {
/*    */   int[] rot;
/*    */   boolean rotateForward;
/* 19 */   int infoMask = 0;
/*    */   
/*    */   public SchematicRotateMeta(int[] rotations, boolean rotateForward) {
/* 22 */     this.rot = rotations;
/*    */     
/* 24 */     for (int element : this.rot) {
/* 25 */       if (element < 4) {
/* 26 */         this.infoMask = (this.infoMask < 3) ? 3 : this.infoMask;
/* 27 */       } else if (element < 8) {
/* 28 */         this.infoMask = (this.infoMask < 7) ? 7 : this.infoMask;
/* 29 */       } else if (element < 16) {
/* 30 */         this.infoMask = (this.infoMask < 15) ? 15 : this.infoMask;
/*    */       } 
/*    */     } 
/*    */     
/* 34 */     this.rotateForward = rotateForward;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/* 39 */     return (this.block == context.world().func_147439_a(x, y, z));
/*    */   }
/*    */ 
/*    */   
/*    */   public void rotateLeft(IBuilderContext context) {
/* 44 */     int pos = this.meta & this.infoMask;
/* 45 */     int others = this.meta - pos;
/*    */     
/* 47 */     if (this.rotateForward) {
/* 48 */       if (pos == this.rot[0]) {
/* 49 */         pos = this.rot[1];
/* 50 */       } else if (pos == this.rot[1]) {
/* 51 */         pos = this.rot[2];
/* 52 */       } else if (pos == this.rot[2]) {
/* 53 */         pos = this.rot[3];
/* 54 */       } else if (pos == this.rot[3]) {
/* 55 */         pos = this.rot[0];
/*    */       }
/*    */     
/* 58 */     } else if (pos == this.rot[0]) {
/* 59 */       pos = this.rot[3];
/* 60 */     } else if (pos == this.rot[1]) {
/* 61 */       pos = this.rot[2];
/* 62 */     } else if (pos == this.rot[2]) {
/* 63 */       pos = this.rot[0];
/* 64 */     } else if (pos == this.rot[3]) {
/* 65 */       pos = this.rot[1];
/*    */     } 
/*    */ 
/*    */     
/* 69 */     this.meta = pos + others;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\schematics\SchematicRotateMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */