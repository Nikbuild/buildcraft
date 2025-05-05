/*     */ package buildcraft.core.lib;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityBlock
/*     */   extends Entity
/*     */ {
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon[] texture;
/*  23 */   public float shadowSize = 0.0F;
/*  24 */   public float rotationX = 0.0F;
/*  25 */   public float rotationY = 0.0F;
/*  26 */   public float rotationZ = 0.0F;
/*     */   public double iSize;
/*  28 */   private int brightness = -1; public double jSize; public double kSize;
/*     */   
/*     */   public EntityBlock(World world) {
/*  31 */     super(world);
/*  32 */     this.field_70156_m = false;
/*  33 */     this.field_70145_X = true;
/*  34 */     this.field_70178_ae = true;
/*     */   }
/*     */   
/*     */   public EntityBlock(World world, double xPos, double yPos, double zPos) {
/*  38 */     super(world);
/*  39 */     func_70080_a(xPos, yPos, zPos, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public EntityBlock(World world, double i, double j, double k, double iSize, double jSize, double kSize) {
/*  43 */     this(world);
/*  44 */     this.iSize = iSize;
/*  45 */     this.jSize = jSize;
/*  46 */     this.kSize = kSize;
/*  47 */     func_70080_a(i, j, k, 0.0F, 0.0F);
/*  48 */     this.field_70159_w = 0.0D;
/*  49 */     this.field_70181_x = 0.0D;
/*  50 */     this.field_70179_y = 0.0D;
/*     */   }
/*     */   
/*     */   public void setTexture(IIcon icon) {
/*  54 */     if (this.texture == null) {
/*  55 */       this.texture = new IIcon[6];
/*     */     }
/*  57 */     for (int i = 0; i < 6; i++) {
/*  58 */       this.texture[i] = icon;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70107_b(double d, double d1, double d2) {
/*  64 */     super.func_70107_b(d, d1, d2);
/*  65 */     this.field_70121_D.field_72340_a = this.field_70165_t;
/*  66 */     this.field_70121_D.field_72338_b = this.field_70163_u;
/*  67 */     this.field_70121_D.field_72339_c = this.field_70161_v;
/*     */     
/*  69 */     this.field_70121_D.field_72336_d = this.field_70165_t + this.iSize;
/*  70 */     this.field_70121_D.field_72337_e = this.field_70163_u + this.jSize;
/*  71 */     this.field_70121_D.field_72334_f = this.field_70161_v + this.kSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70091_d(double d, double d1, double d2) {
/*  76 */     func_70107_b(this.field_70165_t + d, this.field_70163_u + d1, this.field_70161_v + d2);
/*     */   }
/*     */   
/*     */   public void setBrightness(int brightness) {
/*  80 */     this.brightness = brightness;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound data) {
/*  90 */     this.iSize = data.func_74769_h("iSize");
/*  91 */     this.jSize = data.func_74769_h("jSize");
/*  92 */     this.kSize = data.func_74769_h("kSize");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound data) {
/*  97 */     data.func_74780_a("iSize", this.iSize);
/*  98 */     data.func_74780_a("jSize", this.jSize);
/*  99 */     data.func_74780_a("kSize", this.kSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70070_b(float par1) {
/* 104 */     return (this.brightness > 0) ? this.brightness : super.func_70070_b(par1);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_70112_a(double distance) {
/* 109 */     return (distance < 50000.0D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\EntityBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */