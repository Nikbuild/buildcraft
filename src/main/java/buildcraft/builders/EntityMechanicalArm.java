/*     */ package buildcraft.builders;
/*     */ 
/*     */ import buildcraft.core.lib.EntityBlock;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityMechanicalArm
/*     */   extends Entity
/*     */ {
/*     */   protected TileQuarry parent;
/*     */   private double armSizeX;
/*     */   private double armSizeZ;
/*     */   private double xRoot;
/*     */   private double yRoot;
/*     */   private double zRoot;
/*     */   private int headX;
/*     */   private int headY;
/*     */   private int headZ;
/*     */   private EntityBlock xArm;
/*     */   private EntityBlock yArm;
/*     */   private EntityBlock zArm;
/*     */   private EntityBlock head;
/*     */   
/*     */   public EntityMechanicalArm(World world) {
/*  32 */     super(world);
/*  33 */     makeParts(world);
/*  34 */     this.field_70145_X = true;
/*     */   }
/*     */   
/*     */   public EntityMechanicalArm(World world, double x, double y, double z, double width, double height, TileQuarry parent) {
/*  38 */     this(world);
/*  39 */     func_70080_a(parent.field_145851_c, parent.field_145848_d, parent.field_145849_e, 0.0F, 0.0F);
/*  40 */     this.xRoot = x;
/*  41 */     this.yRoot = y;
/*  42 */     this.zRoot = z;
/*  43 */     this.field_70159_w = 0.0D;
/*  44 */     this.field_70181_x = 0.0D;
/*  45 */     this.field_70179_y = 0.0D;
/*  46 */     setArmSize(width, height);
/*  47 */     setHead(x, y - 2.0D, z);
/*  48 */     this.parent = parent;
/*  49 */     parent.setArm(this);
/*  50 */     updatePosition();
/*     */   }
/*     */   
/*     */   void setHead(double x, double y, double z) {
/*  54 */     this.headX = (int)(x * 32.0D);
/*  55 */     this.headY = (int)(y * 32.0D);
/*  56 */     this.headZ = (int)(z * 32.0D);
/*     */   }
/*     */   
/*     */   private void setArmSize(double x, double z) {
/*  60 */     this.armSizeX = x;
/*  61 */     this.xArm.iSize = x;
/*  62 */     this.armSizeZ = z;
/*  63 */     this.zArm.kSize = z;
/*  64 */     updatePosition();
/*     */   }
/*     */   
/*     */   private void makeParts(World world) {
/*  68 */     this.xArm = BuilderProxy.proxy.newDrill(world, 0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D, true);
/*  69 */     this.yArm = BuilderProxy.proxy.newDrill(world, 0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D, false);
/*  70 */     this.zArm = BuilderProxy.proxy.newDrill(world, 0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 1.0D, true);
/*     */     
/*  72 */     this.head = BuilderProxy.proxy.newDrillHead(world, 0.0D, 0.0D, 0.0D, 0.2D, 1.0D, 0.2D);
/*  73 */     this.head.shadowSize = 1.0F;
/*     */     
/*  75 */     world.func_72838_d((Entity)this.xArm);
/*  76 */     world.func_72838_d((Entity)this.yArm);
/*  77 */     world.func_72838_d((Entity)this.zArm);
/*  78 */     world.func_72838_d((Entity)this.head);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {}
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound nbttagcompound) {
/*  87 */     this.xRoot = nbttagcompound.func_74769_h("xRoot");
/*  88 */     this.yRoot = nbttagcompound.func_74769_h("yRoot");
/*  89 */     this.zRoot = nbttagcompound.func_74769_h("zRoot");
/*  90 */     this.armSizeX = nbttagcompound.func_74769_h("armSizeX");
/*  91 */     this.armSizeZ = nbttagcompound.func_74769_h("armSizeZ");
/*  92 */     setArmSize(this.armSizeX, this.armSizeZ);
/*  93 */     updatePosition();
/*     */   }
/*     */   
/*     */   private void findAndJoinQuarry() {
/*  97 */     TileEntity te = this.field_70170_p.func_147438_o((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v);
/*  98 */     if (te instanceof TileQuarry) {
/*  99 */       this.parent = (TileQuarry)te;
/* 100 */       this.parent.setArm(this);
/*     */     } else {
/* 102 */       func_70106_y();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound nbttagcompound) {
/* 108 */     nbttagcompound.func_74780_a("xRoot", this.xRoot);
/* 109 */     nbttagcompound.func_74780_a("yRoot", this.yRoot);
/* 110 */     nbttagcompound.func_74780_a("zRoot", this.zRoot);
/* 111 */     nbttagcompound.func_74780_a("armSizeX", this.armSizeX);
/* 112 */     nbttagcompound.func_74780_a("armSizeZ", this.armSizeZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 117 */     super.func_70071_h_();
/* 118 */     updatePosition();
/* 119 */     if (this.parent == null) {
/* 120 */       findAndJoinQuarry();
/*     */     }
/*     */     
/* 123 */     if (this.parent == null) {
/* 124 */       func_70106_y();
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updatePosition() {
/* 130 */     double[] headT = getHead();
/* 131 */     this.xArm.func_70107_b(this.xRoot, this.yRoot, headT[2] + 0.25D);
/* 132 */     this.yArm.jSize = this.yRoot - headT[1] - 1.0D;
/* 133 */     this.yArm.func_70107_b(headT[0] + 0.25D, headT[1] + 1.0D, headT[2] + 0.25D);
/* 134 */     this.zArm.func_70107_b(headT[0] + 0.25D, this.yRoot, this.zRoot);
/* 135 */     this.head.func_70107_b(headT[0] + 0.4D, headT[1], headT[2] + 0.4D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 140 */     if (this.field_70170_p != null && this.field_70170_p.field_72995_K) {
/* 141 */       this.xArm.func_70106_y();
/* 142 */       this.yArm.func_70106_y();
/* 143 */       this.zArm.func_70106_y();
/* 144 */       this.head.func_70106_y();
/*     */     } 
/* 146 */     super.func_70106_y();
/*     */   }
/*     */   
/*     */   private double[] getHead() {
/* 150 */     return new double[] { this.headX / 32.0D, this.headY / 32.0D, this.headZ / 32.0D };
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\EntityMechanicalArm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */