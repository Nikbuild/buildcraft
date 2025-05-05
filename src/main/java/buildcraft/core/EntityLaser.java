/*     */ package buildcraft.core;
/*     */ 
/*     */ import buildcraft.api.core.Position;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ public abstract class EntityLaser
/*     */   extends Entity
/*     */ {
/*  20 */   public static final ResourceLocation[] LASER_TEXTURES = new ResourceLocation[] { new ResourceLocation("buildcraftcore", "textures/laserBeams/laser_1.png"), new ResourceLocation("buildcraftcore", "textures/laserBeams/laser_2.png"), new ResourceLocation("buildcraftcore", "textures/laserBeams/laser_3.png"), new ResourceLocation("buildcraftcore", "textures/laserBeams/laser_4.png"), new ResourceLocation("buildcraftcore", "textures/laserBeams/stripes.png"), new ResourceLocation("buildcraftcore", "textures/laserBeams/blue_stripes.png") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   public LaserData data = new LaserData();
/*     */   
/*     */   protected boolean needsUpdate = true;
/*     */   
/*     */   public EntityLaser(World world) {
/*  33 */     super(world);
/*     */     
/*  35 */     this.data.head = new Position(0.0D, 0.0D, 0.0D);
/*  36 */     this.data.tail = new Position(0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLaser(World world, Position head, Position tail) {
/*  41 */     super(world);
/*     */     
/*  43 */     this.data.head = head;
/*  44 */     this.data.tail = tail;
/*     */     
/*  46 */     func_70080_a(head.x, head.y, head.z, 0.0F, 0.0F);
/*  47 */     func_70105_a(10.0F, 10.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  52 */     this.field_70156_m = false;
/*  53 */     this.field_70145_X = true;
/*  54 */     this.field_70178_ae = true;
/*     */     
/*  56 */     this.field_70180_af.func_75682_a(8, Integer.valueOf(0));
/*  57 */     this.field_70180_af.func_75682_a(9, Integer.valueOf(0));
/*  58 */     this.field_70180_af.func_75682_a(10, Integer.valueOf(0));
/*  59 */     this.field_70180_af.func_75682_a(11, Integer.valueOf(0));
/*  60 */     this.field_70180_af.func_75682_a(12, Integer.valueOf(0));
/*  61 */     this.field_70180_af.func_75682_a(13, Integer.valueOf(0));
/*     */     
/*  63 */     this.field_70180_af.func_75682_a(14, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  69 */     if (this.data.head == null || this.data.tail == null) {
/*     */       return;
/*     */     }
/*     */     
/*  73 */     if (!this.field_70170_p.field_72995_K && this.needsUpdate) {
/*  74 */       updateDataServer();
/*  75 */       this.needsUpdate = false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     this.field_70121_D.field_72340_a = Math.min(this.data.head.x, this.data.tail.x);
/*  83 */     this.field_70121_D.field_72338_b = Math.min(this.data.head.y, this.data.tail.y);
/*  84 */     this.field_70121_D.field_72339_c = Math.min(this.data.head.z, this.data.tail.z);
/*     */     
/*  86 */     this.field_70121_D.field_72336_d = Math.max(this.data.head.x, this.data.tail.x);
/*  87 */     this.field_70121_D.field_72337_e = Math.max(this.data.head.y, this.data.tail.y);
/*  88 */     this.field_70121_D.field_72334_f = Math.max(this.data.head.z, this.data.tail.z);
/*     */     
/*  90 */     this.field_70121_D.field_72340_a--;
/*  91 */     this.field_70121_D.field_72338_b--;
/*  92 */     this.field_70121_D.field_72339_c--;
/*     */     
/*  94 */     this.field_70121_D.field_72336_d++;
/*  95 */     this.field_70121_D.field_72337_e++;
/*  96 */     this.field_70121_D.field_72334_f++;
/*     */     
/*  98 */     this.data.update();
/*     */   }
/*     */   
/*     */   protected void updateDataClient() {
/* 102 */     this.data.head.x = decodeDouble(this.field_70180_af.func_75679_c(8));
/* 103 */     this.data.head.y = decodeDouble(this.field_70180_af.func_75679_c(9));
/* 104 */     this.data.head.z = decodeDouble(this.field_70180_af.func_75679_c(10));
/* 105 */     this.data.tail.x = decodeDouble(this.field_70180_af.func_75679_c(11));
/* 106 */     this.data.tail.y = decodeDouble(this.field_70180_af.func_75679_c(12));
/* 107 */     this.data.tail.z = decodeDouble(this.field_70180_af.func_75679_c(13));
/*     */     
/* 109 */     this.data.isVisible = (this.field_70180_af.func_75683_a(14) == 1);
/*     */   }
/*     */   
/*     */   protected void updateDataServer() {
/* 113 */     this.field_70180_af.func_75692_b(8, Integer.valueOf(encodeDouble(this.data.head.x)));
/* 114 */     this.field_70180_af.func_75692_b(9, Integer.valueOf(encodeDouble(this.data.head.y)));
/* 115 */     this.field_70180_af.func_75692_b(10, Integer.valueOf(encodeDouble(this.data.head.z)));
/* 116 */     this.field_70180_af.func_75692_b(11, Integer.valueOf(encodeDouble(this.data.tail.x)));
/* 117 */     this.field_70180_af.func_75692_b(12, Integer.valueOf(encodeDouble(this.data.tail.y)));
/* 118 */     this.field_70180_af.func_75692_b(13, Integer.valueOf(encodeDouble(this.data.tail.z)));
/*     */     
/* 120 */     this.field_70180_af.func_75692_b(14, Byte.valueOf((byte)(this.data.isVisible ? 1 : 0)));
/*     */   }
/*     */   
/*     */   public void setPositions(Position head, Position tail) {
/* 124 */     this.data.head = head;
/* 125 */     this.data.tail = tail;
/*     */     
/* 127 */     func_70080_a(head.x, head.y, head.z, 0.0F, 0.0F);
/*     */     
/* 129 */     this.needsUpdate = true;
/*     */   }
/*     */   
/*     */   public void show() {
/* 133 */     this.data.isVisible = true;
/* 134 */     this.needsUpdate = true;
/*     */   }
/*     */   
/*     */   public void hide() {
/* 138 */     this.data.isVisible = false;
/* 139 */     this.needsUpdate = true;
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 143 */     return this.data.isVisible;
/*     */   }
/*     */   
/*     */   public abstract ResourceLocation getTexture();
/*     */   
/*     */   protected int encodeDouble(double d) {
/* 149 */     return (int)(d * 8192.0D);
/*     */   }
/*     */   
/*     */   protected double decodeDouble(int i) {
/* 153 */     return i / 8192.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound nbt) {
/* 160 */     double headX = nbt.func_74769_h("headX");
/* 161 */     double headY = nbt.func_74769_h("headZ");
/* 162 */     double headZ = nbt.func_74769_h("headY");
/* 163 */     this.data.head = new Position(headX, headY, headZ);
/*     */     
/* 165 */     double tailX = nbt.func_74769_h("tailX");
/* 166 */     double tailY = nbt.func_74769_h("tailZ");
/* 167 */     double tailZ = nbt.func_74769_h("tailY");
/* 168 */     this.data.tail = new Position(tailX, tailY, tailZ);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound nbt) {
/* 174 */     nbt.func_74780_a("headX", this.data.head.x);
/* 175 */     nbt.func_74780_a("headY", this.data.head.y);
/* 176 */     nbt.func_74780_a("headZ", this.data.head.z);
/*     */     
/* 178 */     nbt.func_74780_a("tailX", this.data.tail.x);
/* 179 */     nbt.func_74780_a("tailY", this.data.tail.y);
/* 180 */     nbt.func_74780_a("tailZ", this.data.tail.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public Position renderOffset() {
/* 185 */     return new Position(this.data.head.x - this.field_70165_t, this.data.head.y - this.field_70163_u, this.data.head.z - this.field_70161_v);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70070_b(float par1) {
/* 190 */     return 210;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\EntityLaser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */