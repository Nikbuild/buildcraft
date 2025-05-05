/*    */ package buildcraft.robotics;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.particle.EntityFX;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class EntityRobotEnergyParticle
/*    */   extends EntityFX
/*    */ {
/*    */   private float smokeParticleScale;
/*    */   
/*    */   public EntityRobotEnergyParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
/* 25 */     this(world, x, y, z, vx, vy, vz, 1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityRobotEnergyParticle(World world, double x, double y, double z, double vx, double vy, double vz, float size) {
/* 31 */     super(world, x, y, z, vx, vy, vz);
/* 32 */     this.field_70159_w *= 0.10000000149011612D;
/* 33 */     this.field_70181_x *= 0.10000000149011612D;
/* 34 */     this.field_70179_y *= 0.10000000149011612D;
/* 35 */     this.field_70159_w += vx;
/* 36 */     this.field_70181_x += vy;
/* 37 */     this.field_70179_y += vz;
/* 38 */     this.field_70552_h = (float)(Math.random() * 0.6D);
/* 39 */     this.field_70553_i = 0.0F;
/* 40 */     this.field_70551_j = 0.0F;
/* 41 */     this.field_70544_f *= 0.75F;
/* 42 */     this.field_70544_f *= size;
/* 43 */     this.smokeParticleScale = this.field_70544_f;
/* 44 */     this.field_70547_e = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
/* 45 */     this.field_70547_e = (int)(this.field_70547_e * size);
/* 46 */     this.field_70145_X = false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70539_a(Tessellator tesslator, float p2, float p3, float p4, float p5, float p6, float p7) {
/* 52 */     float f6 = (this.field_70546_d + p2) / this.field_70547_e * 32.0F;
/*    */     
/* 54 */     if (f6 < 0.0F) {
/* 55 */       f6 = 0.0F;
/*    */     }
/*    */     
/* 58 */     if (f6 > 1.0F) {
/* 59 */       f6 = 1.0F;
/*    */     }
/*    */     
/* 62 */     this.field_70544_f = this.smokeParticleScale * f6;
/* 63 */     super.func_70539_a(tesslator, p2, p3, p4, p5, p6, p7);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70071_h_() {
/* 71 */     this.field_70169_q = this.field_70165_t;
/* 72 */     this.field_70167_r = this.field_70163_u;
/* 73 */     this.field_70166_s = this.field_70161_v;
/*    */     
/* 75 */     if (this.field_70546_d++ >= this.field_70547_e) {
/* 76 */       func_70106_y();
/*    */     }
/*    */     
/* 79 */     func_70536_a(7 - this.field_70546_d * 8 / this.field_70547_e);
/* 80 */     func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*    */     
/* 82 */     this.field_70159_w *= 0.98D;
/* 83 */     this.field_70181_x += 5.0E-4D;
/* 84 */     this.field_70179_y *= 0.98D;
/*    */     
/* 86 */     if (this.field_70122_E) {
/* 87 */       this.field_70159_w *= 0.699999988079071D;
/* 88 */       this.field_70179_y *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\EntityRobotEnergyParticle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */