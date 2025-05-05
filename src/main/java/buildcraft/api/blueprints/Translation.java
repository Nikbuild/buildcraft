/*    */ package buildcraft.api.blueprints;
/*    */ 
/*    */ import buildcraft.api.core.Position;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Translation
/*    */ {
/* 15 */   public double x = 0.0D;
/* 16 */   public double y = 0.0D;
/* 17 */   public double z = 0.0D;
/*    */   
/*    */   public Position translate(Position p) {
/* 20 */     Position p2 = new Position(p);
/*    */     
/* 22 */     p.x += this.x;
/* 23 */     p.y += this.y;
/* 24 */     p.z += this.z;
/*    */     
/* 26 */     return p2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 31 */     return "{" + this.x + ", " + this.y + ", " + this.z + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\blueprints\Translation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */