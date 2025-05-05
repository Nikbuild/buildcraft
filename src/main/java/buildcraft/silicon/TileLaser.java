/*     */ package buildcraft.silicon;
/*     */ 
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.api.core.SafeTimeTracker;
/*     */ import buildcraft.api.power.ILaserTarget;
/*     */ import buildcraft.api.tiles.IControllable;
/*     */ import buildcraft.api.tiles.IHasWork;
/*     */ import buildcraft.core.Box;
/*     */ import buildcraft.core.EntityLaser;
/*     */ import buildcraft.core.LaserData;
/*     */ import buildcraft.core.lib.RFBattery;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
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
/*     */ public class TileLaser
/*     */   extends TileBuildCraft
/*     */   implements IHasWork, IControllable
/*     */ {
/*     */   private static final float LASER_OFFSET = 0.125F;
/*     */   private static final short POWER_AVERAGING = 100;
/*  41 */   public LaserData laser = new LaserData();
/*     */   
/*  43 */   private final SafeTimeTracker laserTickTracker = new SafeTimeTracker(10L);
/*  44 */   private final SafeTimeTracker searchTracker = new SafeTimeTracker(100L, 100L);
/*  45 */   private final SafeTimeTracker networkTracker = new SafeTimeTracker(20L, 3L);
/*     */   private ILaserTarget laserTarget;
/*  47 */   private int powerIndex = 0;
/*     */   
/*  49 */   private short powerAverage = 0;
/*  50 */   private final short[] power = new short[100];
/*     */ 
/*     */   
/*     */   public TileLaser() {
/*  54 */     setBattery(new RFBattery(10000, 250, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  59 */     super.initialize();
/*     */     
/*  61 */     if (this.laser == null) {
/*  62 */       this.laser = new LaserData();
/*     */     }
/*     */     
/*  65 */     this.laser.isVisible = false;
/*  66 */     this.laser.head = new Position(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  67 */     this.laser.tail = new Position(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  68 */     this.laser.isGlowing = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  73 */     super.func_145845_h();
/*     */     
/*  75 */     this.laser.iterateTexture();
/*     */     
/*  77 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  82 */     if (this.mode == IControllable.Mode.Off) {
/*  83 */       removeLaser();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  88 */     if (canFindTable()) {
/*  89 */       findTable();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  94 */     if (!isValidTable()) {
/*  95 */       removeLaser();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 100 */     if (getBattery().getEnergyStored() == 0) {
/* 101 */       removeLaser();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 106 */     if (this.laser != null) {
/*     */ 
/*     */       
/* 109 */       this.laser.isVisible = true;
/*     */ 
/*     */       
/* 112 */       if (canUpdateLaser()) {
/* 113 */         updateLaser();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 118 */     int localPower = getBattery().useEnergy(0, getMaxPowerSent(), false);
/* 119 */     this.laserTarget.receiveLaserEnergy(localPower);
/*     */     
/* 121 */     if (this.laser != null) {
/* 122 */       pushPower(localPower);
/*     */     }
/*     */     
/* 125 */     onPowerSent(localPower);
/*     */     
/* 127 */     sendNetworkUpdate();
/*     */   }
/*     */   
/*     */   protected int getMaxPowerSent() {
/* 131 */     return 40;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPowerSent(int power) {}
/*     */   
/*     */   protected boolean canFindTable() {
/* 138 */     return this.searchTracker.markTimeIfDelay(this.field_145850_b);
/*     */   }
/*     */   
/*     */   protected boolean canUpdateLaser() {
/* 142 */     return this.laserTickTracker.markTimeIfDelay(this.field_145850_b);
/*     */   }
/*     */   
/*     */   protected boolean isValidTable() {
/* 146 */     if (this.laserTarget == null || this.laserTarget.isInvalidTarget() || !this.laserTarget.requiresLaserEnergy()) {
/* 147 */       return false;
/*     */     }
/*     */     
/* 150 */     return true;
/*     */   }
/*     */   
/*     */   protected void findTable() {
/* 154 */     int meta = func_145832_p();
/*     */     
/* 156 */     int minX = this.field_145851_c - 5;
/* 157 */     int minY = this.field_145848_d - 5;
/* 158 */     int minZ = this.field_145849_e - 5;
/* 159 */     int maxX = this.field_145851_c + 5;
/* 160 */     int maxY = this.field_145848_d + 5;
/* 161 */     int maxZ = this.field_145849_e + 5;
/*     */     
/* 163 */     switch (ForgeDirection.getOrientation(meta)) {
/*     */       case WEST:
/* 165 */         maxX = this.field_145851_c;
/*     */         break;
/*     */       case EAST:
/* 168 */         minX = this.field_145851_c;
/*     */         break;
/*     */       case DOWN:
/* 171 */         maxY = this.field_145848_d;
/*     */         break;
/*     */       case UP:
/* 174 */         minY = this.field_145848_d;
/*     */         break;
/*     */       case NORTH:
/* 177 */         maxZ = this.field_145849_e;
/*     */         break;
/*     */       
/*     */       default:
/* 181 */         minZ = this.field_145849_e;
/*     */         break;
/*     */     } 
/*     */     
/* 185 */     List<ILaserTarget> targets = new LinkedList<ILaserTarget>();
/*     */     
/* 187 */     if (minY < 0) {
/* 188 */       minY = 0;
/*     */     }
/* 190 */     if (maxY > 255) {
/* 191 */       maxY = 255;
/*     */     }
/*     */     
/* 194 */     for (int y = minY; y <= maxY; y++) {
/* 195 */       for (int x = minX; x <= maxX; x++) {
/* 196 */         for (int z = minZ; z <= maxZ; z++) {
/* 197 */           if (BlockUtils.getBlock(this.field_145850_b, x, y, z) instanceof buildcraft.api.power.ILaserTargetBlock) {
/* 198 */             TileEntity tile = BlockUtils.getTileEntity(this.field_145850_b, x, y, z);
/*     */             
/* 200 */             if (tile instanceof ILaserTarget) {
/* 201 */               ILaserTarget table = (ILaserTarget)tile;
/*     */               
/* 203 */               if (table.requiresLaserEnergy()) {
/* 204 */                 targets.add(table);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 212 */     if (targets.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 216 */     this.laserTarget = targets.get(this.field_145850_b.field_73012_v.nextInt(targets.size()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateLaser() {
/* 221 */     int meta = func_145832_p();
/* 222 */     double px = 0.0D, py = 0.0D, pz = 0.0D;
/*     */     
/* 224 */     switch (ForgeDirection.getOrientation(meta)) {
/*     */       
/*     */       case WEST:
/* 227 */         px = -0.125D;
/*     */         break;
/*     */       case EAST:
/* 230 */         px = 0.125D;
/*     */         break;
/*     */       case DOWN:
/* 233 */         py = -0.125D;
/*     */         break;
/*     */       case UP:
/* 236 */         py = 0.125D;
/*     */         break;
/*     */       case NORTH:
/* 239 */         pz = -0.125D;
/*     */         break;
/*     */       
/*     */       default:
/* 243 */         pz = 0.125D;
/*     */         break;
/*     */     } 
/*     */     
/* 247 */     Position head = new Position(this.field_145851_c + 0.5D + px, this.field_145848_d + 0.5D + py, this.field_145849_e + 0.5D + pz);
/*     */     
/* 249 */     Position tail = new Position(this.laserTarget.getXCoord() + 0.475D + (this.field_145850_b.field_73012_v.nextFloat() - 0.5D) / 5.0D, this.laserTarget.getYCoord() + 0.5625D, this.laserTarget.getZCoord() + 0.475D + (this.field_145850_b.field_73012_v.nextFloat() - 0.5D) / 5.0D);
/*     */     
/* 251 */     this.laser.head = head;
/* 252 */     this.laser.tail = tail;
/*     */     
/* 254 */     if (!this.laser.isVisible) {
/* 255 */       this.laser.isVisible = true;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void removeLaser() {
/* 260 */     if (this.powerAverage > 0) {
/* 261 */       pushPower(0);
/*     */     }
/* 263 */     if (this.laser.isVisible) {
/* 264 */       this.laser.isVisible = false;
/*     */ 
/*     */       
/* 267 */       super.sendNetworkUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendNetworkUpdate() {
/* 273 */     if (this.networkTracker.markTimeIfDelay(this.field_145850_b)) {
/* 274 */       super.sendNetworkUpdate();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 280 */     super.func_145839_a(nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 285 */     super.func_145841_b(nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 290 */     this.laser = new LaserData();
/* 291 */     this.laser.readData(stream);
/* 292 */     this.powerAverage = stream.readShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 297 */     this.laser.writeData(stream);
/* 298 */     stream.writeShort(this.powerAverage);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 303 */     super.func_145843_s();
/* 304 */     removeLaser();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWork() {
/* 309 */     return isValidTable();
/*     */   }
/*     */   
/*     */   private void pushPower(int received) {
/* 313 */     this.powerAverage = (short)(this.powerAverage - this.power[this.powerIndex]);
/* 314 */     this.powerAverage = (short)(this.powerAverage + received);
/* 315 */     this.power[this.powerIndex] = (short)received;
/* 316 */     this.powerIndex++;
/*     */     
/* 318 */     if (this.powerIndex == this.power.length) {
/* 319 */       this.powerIndex = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public ResourceLocation getTexture() {
/* 324 */     double avg = (this.powerAverage / 100);
/*     */     
/* 326 */     if (avg <= 10.0D)
/* 327 */       return EntityLaser.LASER_TEXTURES[0]; 
/* 328 */     if (avg <= 20.0D)
/* 329 */       return EntityLaser.LASER_TEXTURES[1]; 
/* 330 */     if (avg <= 30.0D) {
/* 331 */       return EntityLaser.LASER_TEXTURES[2];
/*     */     }
/* 333 */     return EntityLaser.LASER_TEXTURES[3];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getRenderBoundingBox() {
/* 339 */     return (new Box((TileEntity)this)).extendToEncompass(this.laser.tail).getBoundingBox();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsControlMode(IControllable.Mode mode) {
/* 344 */     return (mode == IControllable.Mode.On || mode == IControllable.Mode.Off);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\TileLaser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */