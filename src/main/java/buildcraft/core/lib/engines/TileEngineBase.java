/*     */ package buildcraft.core.lib.engines;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.power.IEngine;
/*     */ import buildcraft.api.tiles.IHeatable;
/*     */ import buildcraft.api.tools.IToolWrench;
/*     */ import buildcraft.api.transport.IPipeConnection;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.CompatHooks;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.utils.MathUtils;
/*     */ import buildcraft.core.lib.utils.ResourceUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import cofh.api.energy.IEnergyConnection;
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import cofh.api.energy.IEnergyReceiver;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ public abstract class TileEngineBase
/*     */   extends TileBuildCraft
/*     */   implements IPipeConnection, IEnergyHandler, IEngine, IHeatable
/*     */ {
/*  39 */   public static final ResourceLocation TRUNK_BLUE_TEXTURE = new ResourceLocation("buildcraftcore:textures/blocks/engine/trunk_blue.png");
/*  40 */   public static final ResourceLocation TRUNK_GREEN_TEXTURE = new ResourceLocation("buildcraftcore:textures/blocks/engine/trunk_green.png");
/*  41 */   public static final ResourceLocation TRUNK_YELLOW_TEXTURE = new ResourceLocation("buildcraftcore:textures/blocks/engine/trunk_yellow.png");
/*  42 */   public static final ResourceLocation TRUNK_RED_TEXTURE = new ResourceLocation("buildcraftcore:textures/blocks/engine/trunk_red.png"); public static final float MIN_HEAT = 20.0F; public static final float IDEAL_HEAT = 100.0F;
/*  43 */   public static final ResourceLocation TRUNK_OVERHEAT_TEXTURE = new ResourceLocation("buildcraftcore:textures/blocks/engine/trunk_overheat.png");
/*     */   public static final float MAX_HEAT = 250.0F;
/*     */   
/*  46 */   public enum EnergyStage { BLUE, GREEN, YELLOW, RED, OVERHEAT;
/*  47 */     public static final EnergyStage[] VALUES = values();
/*     */     
/*     */     static {
/*     */     
/*     */     } }
/*     */   
/*  53 */   public int currentOutput = 0;
/*     */   public boolean isRedstonePowered = false;
/*     */   public float progress;
/*     */   public int energy;
/*  57 */   public float heat = 20.0F;
/*  58 */   public EnergyStage energyStage = EnergyStage.BLUE;
/*  59 */   public ForgeDirection orientation = ForgeDirection.UP;
/*     */   
/*  61 */   protected int progressPart = 0;
/*     */ 
/*     */   
/*     */   private boolean checkOrientation = false;
/*     */ 
/*     */   
/*     */   private boolean isPumping = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  72 */     if (!this.field_145850_b.field_72995_K) {
/*  73 */       checkRedstonePower();
/*     */     }
/*     */   }
/*     */   
/*     */   private String getTexturePrefix() {
/*  78 */     if (!(this.field_145854_h instanceof BlockEngineBase)) {
/*  79 */       Block engineBase = this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  80 */       if (engineBase instanceof BlockEngineBase) {
/*  81 */         this.field_145854_h = engineBase;
/*  82 */         func_145832_p();
/*     */       } else {
/*  84 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     return ((BlockEngineBase)this.field_145854_h).getTexturePrefix(func_145832_p(), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getBaseTexture() {
/*  93 */     if (getTexturePrefix() != null) {
/*  94 */       return new ResourceLocation(getTexturePrefix() + "/base.png");
/*     */     }
/*  96 */     return new ResourceLocation("missingno");
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getChamberTexture() {
/* 101 */     if (getTexturePrefix() != null) {
/* 102 */       return new ResourceLocation(getTexturePrefix() + "/chamber.png");
/*     */     }
/* 104 */     return new ResourceLocation("missingno");
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTrunkTexture(EnergyStage stage) {
/* 109 */     if (getTexturePrefix() == null) {
/* 110 */       return TRUNK_OVERHEAT_TEXTURE;
/*     */     }
/*     */     
/* 113 */     if (ResourceUtils.resourceExists(getTexturePrefix() + "/trunk.png")) {
/* 114 */       return new ResourceLocation(getTexturePrefix() + "/trunk.png");
/*     */     }
/*     */     
/* 117 */     switch (stage) {
/*     */       case BLUE:
/* 119 */         return TRUNK_BLUE_TEXTURE;
/*     */       case GREEN:
/* 121 */         return TRUNK_GREEN_TEXTURE;
/*     */       case YELLOW:
/* 123 */         return TRUNK_YELLOW_TEXTURE;
/*     */       case RED:
/* 125 */         return TRUNK_RED_TEXTURE;
/*     */       case OVERHEAT:
/* 127 */         return TRUNK_OVERHEAT_TEXTURE;
/*     */     } 
/* 129 */     return TRUNK_RED_TEXTURE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(EntityPlayer player, ForgeDirection side) {
/* 134 */     if (!player.field_70170_p.field_72995_K && player.func_71045_bC() != null && player
/* 135 */       .func_71045_bC().func_77973_b() instanceof IToolWrench) {
/* 136 */       IToolWrench wrench = (IToolWrench)player.func_71045_bC().func_77973_b();
/* 137 */       if (wrench.canWrench(player, this.field_145851_c, this.field_145848_d, this.field_145849_e)) {
/* 138 */         if (getEnergyStage() == EnergyStage.OVERHEAT && !Utils.isFakePlayer(player)) {
/* 139 */           this.energyStage = computeEnergyStage();
/* 140 */           sendNetworkUpdate();
/*     */         } 
/* 142 */         this.checkOrientation = true;
/*     */         
/* 144 */         wrench.wrenchUsed(player, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 145 */         return true;
/*     */       } 
/*     */     } 
/* 148 */     return false;
/*     */   }
/*     */   
/*     */   public double getEnergyLevel() {
/* 152 */     return this.energy / getMaxEnergy();
/*     */   }
/*     */   
/*     */   protected EnergyStage computeEnergyStage() {
/* 156 */     float energyLevel = getHeatLevel();
/* 157 */     if (energyLevel < 0.25F)
/* 158 */       return EnergyStage.BLUE; 
/* 159 */     if (energyLevel < 0.5F)
/* 160 */       return EnergyStage.GREEN; 
/* 161 */     if (energyLevel < 0.75F)
/* 162 */       return EnergyStage.YELLOW; 
/* 163 */     if (energyLevel < 1.0F) {
/* 164 */       return EnergyStage.RED;
/*     */     }
/* 166 */     return EnergyStage.OVERHEAT;
/*     */   }
/*     */ 
/*     */   
/*     */   public final EnergyStage getEnergyStage() {
/* 171 */     if (!this.field_145850_b.field_72995_K) {
/* 172 */       if (this.energyStage == EnergyStage.OVERHEAT) {
/* 173 */         return this.energyStage;
/*     */       }
/*     */       
/* 176 */       EnergyStage newStage = computeEnergyStage();
/*     */       
/* 178 */       if (this.energyStage != newStage) {
/* 179 */         this.energyStage = newStage;
/* 180 */         if (this.energyStage == EnergyStage.OVERHEAT) {
/* 181 */           overheat();
/*     */         }
/* 183 */         sendNetworkUpdate();
/*     */       } 
/*     */     } 
/*     */     
/* 187 */     return this.energyStage;
/*     */   }
/*     */   
/*     */   public void overheat() {
/* 191 */     this.isPumping = false;
/* 192 */     if (BuildCraftCore.canEnginesExplode) {
/* 193 */       this.field_145850_b.func_72876_a(null, this.field_145851_c, this.field_145848_d, this.field_145849_e, 3.0F, true);
/* 194 */       this.field_145850_b.func_147468_f(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateHeat() {
/* 199 */     this.heat = (float)(230.0D * getEnergyLevel()) + 20.0F;
/*     */   }
/*     */   
/*     */   public float getHeatLevel() {
/* 203 */     return (this.heat - 20.0F) / 230.0F;
/*     */   }
/*     */   
/*     */   public float getPistonSpeed() {
/* 207 */     if (!this.field_145850_b.field_72995_K) {
/* 208 */       return Math.max(0.16F * getHeatLevel(), 0.01F);
/*     */     }
/*     */     
/* 211 */     switch (getEnergyStage()) {
/*     */       case BLUE:
/* 213 */         return 0.02F;
/*     */       case GREEN:
/* 215 */         return 0.04F;
/*     */       case YELLOW:
/* 217 */         return 0.08F;
/*     */       case RED:
/* 219 */         return 0.16F;
/*     */     } 
/* 221 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 227 */     super.func_145845_h();
/*     */     
/* 229 */     if (this.field_145850_b.field_72995_K) {
/* 230 */       if (this.progressPart != 0) {
/* 231 */         this.progress += getPistonSpeed();
/*     */         
/* 233 */         if (this.progress > 1.0F) {
/* 234 */           this.progressPart = 0;
/* 235 */           this.progress = 0.0F;
/*     */         } 
/* 237 */       } else if (this.isPumping) {
/* 238 */         this.progressPart = 1;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 244 */     if (this.checkOrientation) {
/* 245 */       this.checkOrientation = false;
/*     */       
/* 247 */       if (!isOrientationValid()) {
/* 248 */         switchOrientation(true);
/*     */       }
/*     */     } 
/*     */     
/* 252 */     updateHeat();
/* 253 */     getEnergyStage();
/*     */     
/* 255 */     if (getEnergyStage() == EnergyStage.OVERHEAT) {
/* 256 */       this.energy = Math.max(this.energy - 50, 0);
/*     */       
/*     */       return;
/*     */     } 
/* 260 */     engineUpdate();
/*     */     
/* 262 */     Object tile = getEnergyProvider(this.orientation);
/*     */     
/* 264 */     if (this.progressPart != 0) {
/* 265 */       this.progress += getPistonSpeed();
/*     */       
/* 267 */       if (this.progress > 0.5D && this.progressPart == 1) {
/* 268 */         this.progressPart = 2;
/* 269 */       } else if (this.progress >= 1.0F) {
/* 270 */         this.progress = 0.0F;
/* 271 */         this.progressPart = 0;
/*     */       } 
/* 273 */     } else if (this.isRedstonePowered && isActive()) {
/* 274 */       if (isPoweredTile(tile, this.orientation)) {
/* 275 */         this.progressPart = 1;
/* 276 */         setPumping(true);
/* 277 */         if (getPowerToExtract() > 0) {
/* 278 */           this.progressPart = 1;
/* 279 */           setPumping(true);
/*     */         } else {
/* 281 */           setPumping(false);
/*     */         } 
/*     */       } else {
/* 284 */         setPumping(false);
/*     */       } 
/*     */     } else {
/* 287 */       setPumping(false);
/*     */     } 
/*     */     
/* 290 */     burn();
/*     */     
/* 292 */     if (!this.isRedstonePowered) {
/* 293 */       this.currentOutput = 0;
/* 294 */     } else if (this.isRedstonePowered && isActive()) {
/* 295 */       sendPower();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getEnergyProvider(ForgeDirection orientation) {
/* 300 */     return CompatHooks.INSTANCE.getEnergyProvider(getTile(orientation));
/*     */   }
/*     */   
/*     */   private int getPowerToExtract() {
/* 304 */     Object tile = getEnergyProvider(this.orientation);
/*     */     
/* 306 */     if (tile instanceof IEngine) {
/* 307 */       IEngine engine = (IEngine)tile;
/*     */       
/* 309 */       int maxEnergy = engine.receiveEnergyFromEngine(this.orientation
/* 310 */           .getOpposite(), this.energy, true);
/*     */       
/* 312 */       return extractEnergy(maxEnergy, false);
/* 313 */     }  if (tile instanceof IEnergyHandler) {
/* 314 */       IEnergyHandler handler = (IEnergyHandler)tile;
/*     */       
/* 316 */       int maxEnergy = handler.receiveEnergy(this.orientation
/* 317 */           .getOpposite(), this.energy, true);
/*     */       
/* 319 */       return extractEnergy(maxEnergy, false);
/* 320 */     }  if (tile instanceof IEnergyReceiver) {
/* 321 */       IEnergyReceiver handler = (IEnergyReceiver)tile;
/*     */       
/* 323 */       int maxEnergy = handler.receiveEnergy(this.orientation
/* 324 */           .getOpposite(), this.energy, true);
/*     */       
/* 326 */       return extractEnergy(maxEnergy, false);
/*     */     } 
/* 328 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendPower() {
/* 333 */     Object tile = getEnergyProvider(this.orientation);
/* 334 */     if (isPoweredTile(tile, this.orientation)) {
/* 335 */       int extracted = getPowerToExtract();
/* 336 */       if (extracted <= 0) {
/* 337 */         setPumping(false);
/*     */         
/*     */         return;
/*     */       } 
/* 341 */       setPumping(true);
/*     */       
/* 343 */       if (tile instanceof IEngine) {
/* 344 */         IEngine engine = (IEngine)tile;
/* 345 */         int neededRF = engine.receiveEnergyFromEngine(this.orientation
/* 346 */             .getOpposite(), extracted, false);
/*     */ 
/*     */         
/* 349 */         extractEnergy(neededRF, true);
/* 350 */       } else if (tile instanceof IEnergyHandler) {
/* 351 */         IEnergyHandler handler = (IEnergyHandler)tile;
/* 352 */         int neededRF = handler.receiveEnergy(this.orientation
/* 353 */             .getOpposite(), extracted, false);
/*     */ 
/*     */         
/* 356 */         extractEnergy(neededRF, true);
/* 357 */       } else if (tile instanceof IEnergyReceiver) {
/* 358 */         IEnergyReceiver handler = (IEnergyReceiver)tile;
/* 359 */         int neededRF = handler.receiveEnergy(this.orientation
/* 360 */             .getOpposite(), extracted, false);
/*     */ 
/*     */         
/* 363 */         extractEnergy(neededRF, true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void burn() {}
/*     */   
/*     */   protected void engineUpdate() {
/* 372 */     if (!this.isRedstonePowered) {
/* 373 */       if (this.energy >= 10) {
/* 374 */         this.energy -= 10;
/* 375 */       } else if (this.energy < 10) {
/* 376 */         this.energy = 0;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 382 */     return true;
/*     */   }
/*     */   
/*     */   protected final void setPumping(boolean isActive) {
/* 386 */     if (this.isPumping == isActive) {
/*     */       return;
/*     */     }
/*     */     
/* 390 */     this.isPumping = isActive;
/* 391 */     sendNetworkUpdate();
/*     */   }
/*     */   
/*     */   public boolean isOrientationValid() {
/* 395 */     Object tile = getEnergyProvider(this.orientation);
/*     */     
/* 397 */     return isPoweredTile(tile, this.orientation);
/*     */   }
/*     */   
/*     */   public boolean switchOrientation(boolean preferPipe) {
/* 401 */     if (preferPipe && switchOrientationDo(true)) {
/* 402 */       return true;
/*     */     }
/* 404 */     return switchOrientationDo(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean switchOrientationDo(boolean pipesOnly) {
/* 409 */     for (int i = this.orientation.ordinal() + 1; i <= this.orientation.ordinal() + 6; i++) {
/* 410 */       ForgeDirection o = ForgeDirection.VALID_DIRECTIONS[i % 6];
/*     */       
/* 412 */       Object tile = getEnergyProvider(o);
/*     */       
/* 414 */       if ((!pipesOnly || tile instanceof IPipeTile) && isPoweredTile(tile, o)) {
/* 415 */         this.orientation = o;
/* 416 */         this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 417 */         this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*     */         
/* 419 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 423 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 428 */     super.func_145843_s();
/* 429 */     this.checkOrientation = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145829_t() {
/* 434 */     super.func_145829_t();
/* 435 */     this.checkOrientation = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound data) {
/* 440 */     super.func_145839_a(data);
/*     */     
/* 442 */     this.orientation = ForgeDirection.getOrientation(data.func_74771_c("orientation"));
/* 443 */     this.progress = data.func_74760_g("progress");
/* 444 */     this.energy = data.func_74762_e("energy");
/* 445 */     this.heat = data.func_74760_g("heat");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound data) {
/* 450 */     super.func_145841_b(data);
/*     */     
/* 452 */     data.func_74774_a("orientation", (byte)this.orientation.ordinal());
/* 453 */     data.func_74776_a("progress", this.progress);
/* 454 */     data.func_74768_a("energy", this.energy);
/* 455 */     data.func_74776_a("heat", this.heat);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 460 */     int flags = stream.readUnsignedByte();
/* 461 */     this.energyStage = EnergyStage.values()[flags & 0x7];
/* 462 */     this.isPumping = ((flags & 0x8) != 0);
/* 463 */     this.orientation = ForgeDirection.getOrientation(stream.readByte());
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 468 */     stream.writeByte(this.energyStage.ordinal() | (this.isPumping ? 8 : 0));
/* 469 */     stream.writeByte(this.orientation.ordinal());
/*     */   }
/*     */   
/*     */   public void getGUINetworkData(int id, int value) {
/* 473 */     switch (id) {
/*     */       case 0:
/* 475 */         this.energy = this.energy & 0xFFFF0000 | value & 0xFFFF;
/*     */         break;
/*     */       case 1:
/* 478 */         this.energy = this.energy & 0xFFFF | (value & 0xFFFF) << 16;
/*     */         break;
/*     */       case 2:
/* 481 */         this.currentOutput = value;
/*     */         break;
/*     */       case 3:
/* 484 */         this.heat = value / 100.0F;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendGUINetworkData(Container container, ICrafting iCrafting) {
/* 490 */     iCrafting.func_71112_a(container, 0, this.energy & 0xFFFF);
/* 491 */     iCrafting.func_71112_a(container, 1, (this.energy & 0xFFFF0000) >> 16);
/* 492 */     iCrafting.func_71112_a(container, 2, this.currentOutput);
/* 493 */     iCrafting.func_71112_a(container, 3, Math.round(this.heat * 100.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract boolean isBurning();
/*     */   
/*     */   public void addEnergy(int addition) {
/* 500 */     if (getEnergyStage() == EnergyStage.OVERHEAT) {
/*     */       return;
/*     */     }
/*     */     
/* 504 */     this.energy += addition;
/*     */     
/* 506 */     if (this.energy > getMaxEnergy()) {
/* 507 */       this.energy = getMaxEnergy();
/*     */     }
/*     */   }
/*     */   
/*     */   public int extractEnergy(int energyMax, boolean doExtract) {
/* 512 */     int extracted, max = Math.min(energyMax, getCurrentOutputLimit());
/*     */ 
/*     */ 
/*     */     
/* 516 */     if (this.energy >= max) {
/* 517 */       extracted = max;
/*     */       
/* 519 */       if (doExtract) {
/* 520 */         this.energy -= max;
/*     */       }
/*     */     } else {
/* 523 */       extracted = this.energy;
/*     */       
/* 525 */       if (doExtract) {
/* 526 */         this.energy = 0;
/*     */       }
/*     */     } 
/*     */     
/* 530 */     return extracted;
/*     */   }
/*     */   
/*     */   public boolean isPoweredTile(Object tile, ForgeDirection side) {
/* 534 */     if (tile == null)
/* 535 */       return false; 
/* 536 */     if (tile instanceof IEngine)
/* 537 */       return ((IEngine)tile).canReceiveFromEngine(side.getOpposite()); 
/* 538 */     if (tile instanceof IEnergyHandler || tile instanceof IEnergyReceiver) {
/* 539 */       return ((IEnergyConnection)tile).canConnectEnergy(side.getOpposite());
/*     */     }
/* 541 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract int getMaxEnergy();
/*     */   
/*     */   public int getEnergyStored() {
/* 548 */     return this.energy;
/*     */   }
/*     */   
/*     */   public abstract int getIdealOutput();
/*     */   
/*     */   public int getCurrentOutputLimit() {
/* 554 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public IPipeConnection.ConnectOverride overridePipeConnection(IPipeTile.PipeType type, ForgeDirection with) {
/* 559 */     if (type == IPipeTile.PipeType.POWER)
/* 560 */       return IPipeConnection.ConnectOverride.DEFAULT; 
/* 561 */     if (with == this.orientation) {
/* 562 */       return IPipeConnection.ConnectOverride.DISCONNECT;
/*     */     }
/* 564 */     return IPipeConnection.ConnectOverride.DEFAULT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkRedstonePower() {
/* 569 */     this.isRedstonePowered = this.field_145850_b.func_72864_z(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */   }
/*     */   
/*     */   public void onNeighborUpdate() {
/* 573 */     checkRedstonePower();
/* 574 */     this.checkOrientation = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
/* 581 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyStored(ForgeDirection from) {
/* 586 */     if (from != this.orientation) {
/* 587 */       return 0;
/*     */     }
/*     */     
/* 590 */     return this.energy;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergyStored(ForgeDirection from) {
/* 595 */     return getMaxEnergy();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection from) {
/* 600 */     return (from == this.orientation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canReceiveFromEngine(ForgeDirection side) {
/* 607 */     return (side == this.orientation.getOpposite());
/*     */   }
/*     */ 
/*     */   
/*     */   public int receiveEnergyFromEngine(ForgeDirection side, int amount, boolean simulate) {
/* 612 */     if (canReceiveFromEngine(side)) {
/* 613 */       int targetEnergy = Math.min(getMaxEnergy() - this.energy, amount);
/* 614 */       if (!simulate) {
/* 615 */         this.energy += targetEnergy;
/*     */       }
/* 617 */       return targetEnergy;
/*     */     } 
/* 619 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMinHeatValue() {
/* 627 */     return 20.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getIdealHeatValue() {
/* 632 */     return 100.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxHeatValue() {
/* 637 */     return 250.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCurrentHeatValue() {
/* 642 */     return this.heat;
/*     */   }
/*     */ 
/*     */   
/*     */   public double setHeatValue(double value) {
/* 647 */     this.heat = (float)MathUtils.clamp(value, 20.0D, 250.0D);
/* 648 */     return this.heat;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\engines\TileEngineBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */