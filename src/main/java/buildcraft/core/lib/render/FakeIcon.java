/*    */ package buildcraft.core.lib.render;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ public class FakeIcon implements IIcon {
/*    */   private final int w;
/*    */   private final int h;
/*    */   private final float minU;
/*    */   
/*    */   public FakeIcon(float minU, float maxU, float minV, float maxV, int w, int h) {
/* 10 */     this.minU = minU;
/* 11 */     this.minV = minV;
/* 12 */     this.maxU = maxU;
/* 13 */     this.maxV = maxV;
/* 14 */     this.w = w;
/* 15 */     this.h = h;
/*    */   }
/*    */   private final float maxU; private final float minV; private final float maxV;
/*    */   
/*    */   public int func_94211_a() {
/* 20 */     return this.w;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_94216_b() {
/* 25 */     return this.h;
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_94209_e() {
/* 30 */     return this.minU;
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_94212_f() {
/* 35 */     return this.maxU;
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_94214_a(double uu) {
/* 40 */     return (float)(this.minU + uu * (this.maxU - this.minU) / 16.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_94206_g() {
/* 45 */     return this.minV;
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_94210_h() {
/* 50 */     return this.maxV;
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_94207_b(double uu) {
/* 55 */     return (float)(this.minV + uu * (this.maxV - this.minV) / 16.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_94215_i() {
/* 60 */     return "FakeIcon";
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\render\FakeIcon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */