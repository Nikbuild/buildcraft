/*    */ package buildcraft.core.lib.render;
/*    */ 
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ public class SubIcon
/*    */   implements IIcon {
/*    */   private final IIcon icon;
/*    */   private float u;
/*    */   private float v;
/*    */   private final int w;
/*    */   
/*    */   public SubIcon(IIcon icon, int u, int v, int size) {
/* 13 */     this(icon, u, v, 16, 16, size);
/*    */   }
/*    */   private final int h; private float uScale; private float vScale; private int iw; private int ih;
/*    */   public SubIcon(IIcon icon, int u, int v, int w, int h, int size) {
/* 17 */     this.iw = size;
/* 18 */     this.ih = size;
/* 19 */     this.icon = icon;
/* 20 */     this.uScale = icon.func_94212_f() - icon.func_94209_e();
/* 21 */     this.vScale = icon.func_94210_h() - icon.func_94206_g();
/* 22 */     this.u = icon.func_94209_e() + this.uScale * u / this.iw;
/* 23 */     this.v = icon.func_94206_g() + this.vScale * v / this.ih;
/* 24 */     this.w = w;
/* 25 */     this.h = h;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_94211_a() {
/* 30 */     return this.w;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_94216_b() {
/* 35 */     return this.h;
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_94209_e() {
/* 40 */     return this.u;
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_94212_f() {
/* 45 */     return this.u + this.uScale * this.w / this.iw;
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_94214_a(double uu) {
/* 50 */     return this.u + this.uScale * (float)uu / this.iw;
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_94206_g() {
/* 55 */     return this.v;
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_94210_h() {
/* 60 */     return this.v + this.vScale * this.h / this.ih;
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_94207_b(double vv) {
/* 65 */     return this.v + this.vScale * (float)vv / this.ih;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_94215_i() {
/* 70 */     return this.icon.func_94215_i();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\render\SubIcon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */