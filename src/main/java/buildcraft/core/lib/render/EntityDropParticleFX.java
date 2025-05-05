/*     */ package buildcraft.core.lib.render;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.IFluidBlock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class EntityDropParticleFX
/*     */   extends EntityFX
/*     */ {
/*     */   private int bobTimer;
/*     */   
/*     */   public EntityDropParticleFX(World world, double x, double y, double z, float particleRed, float particleGreen, float particleBlue) {
/*  30 */     super(world, x, y, z, 0.0D, 0.0D, 0.0D);
/*  31 */     this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  45 */     this.field_70552_h = particleRed;
/*  46 */     this.field_70553_i = particleGreen;
/*  47 */     this.field_70551_j = particleBlue;
/*     */     
/*  49 */     func_70536_a(113);
/*  50 */     func_70105_a(0.01F, 0.01F);
/*  51 */     this.field_70545_g = 0.06F;
/*  52 */     this.bobTimer = 40;
/*  53 */     this.field_70547_e = (int)(64.0D / (Math.random() * 0.8D + 0.2D));
/*  54 */     this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  62 */     this.field_70169_q = this.field_70165_t;
/*  63 */     this.field_70167_r = this.field_70163_u;
/*  64 */     this.field_70166_s = this.field_70161_v;
/*     */     
/*  66 */     this.field_70181_x -= this.field_70545_g;
/*     */     
/*  68 */     if (this.bobTimer-- > 0) {
/*  69 */       this.field_70159_w *= 0.02D;
/*  70 */       this.field_70181_x *= 0.02D;
/*  71 */       this.field_70179_y *= 0.02D;
/*  72 */       func_70536_a(113);
/*     */     } else {
/*  74 */       func_70536_a(112);
/*     */     } 
/*     */     
/*  77 */     func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*  78 */     this.field_70159_w *= 0.9800000190734863D;
/*  79 */     this.field_70181_x *= 0.9800000190734863D;
/*  80 */     this.field_70179_y *= 0.9800000190734863D;
/*     */     
/*  82 */     if (this.field_70547_e-- <= 0) {
/*  83 */       func_70106_y();
/*     */     }
/*     */     
/*  86 */     if (this.field_70122_E) {
/*  87 */       func_70536_a(114);
/*     */       
/*  89 */       this.field_70159_w *= 0.699999988079071D;
/*  90 */       this.field_70179_y *= 0.699999988079071D;
/*     */     } 
/*     */     
/*  93 */     int x = MathHelper.func_76128_c(this.field_70165_t);
/*  94 */     int y = MathHelper.func_76128_c(this.field_70163_u);
/*  95 */     int z = MathHelper.func_76128_c(this.field_70161_v);
/*  96 */     Block block = this.field_70170_p.func_147439_a(x, y, z);
/*     */     
/*  98 */     Material material = block.func_149688_o();
/*     */     
/* 100 */     if ((material.func_76224_d() || material.func_76220_a()) && block instanceof IFluidBlock) {
/*     */       
/* 102 */       double d0 = ((MathHelper.func_76128_c(this.field_70163_u) + 1) - ((IFluidBlock)block).getFilledPercentage(this.field_70170_p, x, y, z));
/*     */       
/* 104 */       if (this.field_70163_u < d0)
/* 105 */         func_70106_y(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\render\EntityDropParticleFX.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */