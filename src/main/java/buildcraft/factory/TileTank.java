/*     */ package buildcraft.factory;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.SafeTimeTracker;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.fluids.Tank;
/*     */ import buildcraft.core.lib.fluids.TankManager;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidEvent;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.FluidTankInfo;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
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
/*     */ public class TileTank
/*     */   extends TileBuildCraft
/*     */   implements IFluidHandler
/*     */ {
/*  34 */   public final Tank tank = new Tank("tank", 16000, (TileEntity)this);
/*  35 */   public final TankManager<Tank> tankManager = new TankManager(new Tank[] { this.tank });
/*     */   public boolean hasUpdate = false;
/*     */   public boolean hasNetworkUpdate = false;
/*  38 */   public SafeTimeTracker tracker = new SafeTimeTracker((2 * BuildCraftCore.updateFactor));
/*  39 */   private int prevLightValue = 0;
/*  40 */   private int cachedComparatorOverride = 0;
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  44 */     super.initialize();
/*  45 */     updateComparators();
/*     */   }
/*     */   
/*     */   protected void updateComparators() {
/*  49 */     int co = calculateComparatorInputOverride();
/*  50 */     TileTank uTank = getBottomTank();
/*  51 */     while (uTank != null) {
/*  52 */       uTank.cachedComparatorOverride = co;
/*  53 */       uTank.hasUpdate = true;
/*  54 */       uTank = getTankAbove(uTank);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onBlockBreak() {
/*  59 */     if (!this.tank.isEmpty()) {
/*  60 */       FluidEvent.fireEvent((FluidEvent)new FluidEvent.FluidSpilledEvent(this.tank
/*  61 */             .getFluid(), this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  70 */     super.func_145845_h();
/*     */     
/*  72 */     if (this.field_145850_b.field_72995_K) {
/*  73 */       int lightValue = getFluidLightLevel();
/*  74 */       if (this.prevLightValue != lightValue) {
/*  75 */         this.prevLightValue = lightValue;
/*  76 */         this.field_145850_b.func_147463_c(EnumSkyBlock.Block, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  82 */     if (this.tank.getFluid() != null) {
/*  83 */       moveFluidBelow();
/*     */     }
/*     */     
/*  86 */     if (this.hasUpdate) {
/*  87 */       BlockUtils.onComparatorUpdate(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, func_145838_q());
/*  88 */       this.hasUpdate = false;
/*     */     } 
/*     */     
/*  91 */     if (this.hasNetworkUpdate && this.tracker.markTimeIfDelay(this.field_145850_b)) {
/*  92 */       sendNetworkUpdate();
/*  93 */       this.hasNetworkUpdate = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/* 100 */     this.tankManager.writeData(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 105 */     this.tankManager.readData(stream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound data) {
/* 111 */     super.func_145839_a(data);
/* 112 */     this.tankManager.readFromNBT(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound data) {
/* 117 */     super.func_145841_b(data);
/* 118 */     this.tankManager.writeToNBT(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileTank getBottomTank() {
/* 128 */     TileTank lastTank = this;
/*     */     
/*     */     while (true) {
/* 131 */       TileTank below = getTankBelow(lastTank);
/* 132 */       if (below != null) {
/* 133 */         lastTank = below;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 139 */     return lastTank;
/*     */   }
/*     */   
/*     */   public TileTank getTopTank() {
/* 143 */     TileTank lastTank = this;
/*     */     
/*     */     while (true) {
/* 146 */       TileTank above = getTankAbove(lastTank);
/* 147 */       if (above != null) {
/* 148 */         lastTank = above;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 154 */     return lastTank;
/*     */   }
/*     */   
/*     */   public static TileTank getTankBelow(TileTank tile) {
/* 158 */     TileEntity below = tile.getTile(ForgeDirection.DOWN);
/* 159 */     if (below instanceof TileTank) {
/* 160 */       return (TileTank)below;
/*     */     }
/* 162 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static TileTank getTankAbove(TileTank tile) {
/* 167 */     TileEntity above = tile.getTile(ForgeDirection.UP);
/* 168 */     if (above instanceof TileTank) {
/* 169 */       return (TileTank)above;
/*     */     }
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveFluidBelow() {
/* 176 */     TileTank below = getTankBelow(this);
/* 177 */     if (below == null) {
/*     */       return;
/*     */     }
/*     */     
/* 181 */     int oldComparator = getComparatorInputOverride();
/* 182 */     int used = below.tank.fill(this.tank.getFluid(), true);
/*     */     
/* 184 */     if (used > 0) {
/* 185 */       this.hasNetworkUpdate = true;
/* 186 */       below.hasNetworkUpdate = true;
/*     */       
/* 188 */       if (oldComparator != calculateComparatorInputOverride()) {
/* 189 */         updateComparators();
/*     */       }
/*     */       
/* 192 */       this.tank.drain(used, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/* 199 */     if (resource == null) {
/* 200 */       return 0;
/*     */     }
/*     */     
/* 203 */     FluidStack resourceCopy = resource.copy();
/* 204 */     int totalUsed = 0;
/* 205 */     TileTank tankToFill = getBottomTank();
/*     */     
/* 207 */     FluidStack liquid = tankToFill.tank.getFluid();
/* 208 */     if (liquid != null && liquid.amount > 0 && !liquid.isFluidEqual(resourceCopy)) {
/* 209 */       return 0;
/*     */     }
/*     */     
/* 212 */     int oldComparator = getComparatorInputOverride();
/*     */     
/* 214 */     while (tankToFill != null && resourceCopy.amount > 0) {
/* 215 */       int used = tankToFill.tank.fill(resourceCopy, doFill);
/* 216 */       resourceCopy.amount -= used;
/* 217 */       if (used > 0) {
/* 218 */         tankToFill.hasNetworkUpdate = true;
/*     */       }
/*     */       
/* 221 */       totalUsed += used;
/* 222 */       tankToFill = getTankAbove(tankToFill);
/*     */     } 
/*     */     
/* 225 */     if (oldComparator != calculateComparatorInputOverride()) {
/* 226 */       updateComparators();
/*     */     }
/*     */     
/* 229 */     return totalUsed;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, int maxEmpty, boolean doDrain) {
/* 234 */     TileTank bottom = getBottomTank();
/* 235 */     bottom.hasNetworkUpdate = true;
/* 236 */     int oldComparator = getComparatorInputOverride();
/* 237 */     FluidStack output = bottom.tank.drain(maxEmpty, doDrain);
/*     */     
/* 239 */     if (oldComparator != calculateComparatorInputOverride()) {
/* 240 */       updateComparators();
/*     */     }
/*     */     
/* 243 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/* 248 */     if (resource == null) {
/* 249 */       return null;
/*     */     }
/* 251 */     TileTank bottom = getBottomTank();
/* 252 */     if (!resource.isFluidEqual(bottom.tank.getFluid())) {
/* 253 */       return null;
/*     */     }
/* 255 */     return drain(from, resource.amount, doDrain);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidTankInfo[] getTankInfo(ForgeDirection direction) {
/* 260 */     FluidTank compositeTank = new FluidTank(this.tank.getCapacity());
/*     */     
/* 262 */     TileTank tile = getBottomTank();
/*     */     
/* 264 */     if (tile != null && tile.tank.getFluid() != null) {
/* 265 */       compositeTank.setFluid(tile.tank.getFluid().copy());
/*     */     } else {
/* 267 */       return new FluidTankInfo[] { compositeTank.getInfo() };
/*     */     } 
/*     */     
/* 270 */     int capacity = tile.tank.getCapacity();
/* 271 */     tile = getTankAbove(tile);
/*     */     
/* 273 */     while (tile != null) {
/* 274 */       FluidStack liquid = tile.tank.getFluid();
/* 275 */       if (liquid != null && liquid.amount != 0) {
/*     */         
/* 277 */         if (!compositeTank.getFluid().isFluidEqual(liquid)) {
/*     */           break;
/*     */         }
/* 280 */         (compositeTank.getFluid()).amount += liquid.amount;
/*     */       } 
/*     */       
/* 283 */       capacity += tile.tank.getCapacity();
/* 284 */       tile = getTankAbove(tile);
/*     */     } 
/*     */     
/* 287 */     compositeTank.setCapacity(capacity);
/* 288 */     return new FluidTankInfo[] { compositeTank.getInfo() };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/* 293 */     Fluid tankFluid = (getBottomTank()).tank.getFluidType();
/* 294 */     return (tankFluid == null || tankFluid == fluid);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 299 */     Fluid tankFluid = (getBottomTank()).tank.getFluidType();
/* 300 */     return (tankFluid != null && tankFluid == fluid);
/*     */   }
/*     */   
/*     */   public int getFluidLightLevel() {
/* 304 */     FluidStack tankFluid = this.tank.getFluid();
/* 305 */     return (tankFluid == null || tankFluid.amount == 0) ? 0 : tankFluid.getFluid().getLuminosity(tankFluid);
/*     */   }
/*     */   
/*     */   public int calculateComparatorInputOverride() {
/* 309 */     FluidTankInfo[] info = getTankInfo(ForgeDirection.UNKNOWN);
/* 310 */     if (info.length > 0 && info[0] != null && (info[0]).fluid != null) {
/* 311 */       return (info[0]).fluid.amount * 15 / (info[0]).capacity;
/*     */     }
/* 313 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride() {
/* 318 */     return this.cachedComparatorOverride;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\TileTank.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */