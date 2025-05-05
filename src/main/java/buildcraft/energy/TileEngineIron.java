/*     */ package buildcraft.energy;
/*     */ 
/*     */ import buildcraft.BuildCraftEnergy;
/*     */ import buildcraft.api.core.StackKey;
/*     */ import buildcraft.api.fuels.BuildcraftFuelRegistry;
/*     */ import buildcraft.api.fuels.ICoolant;
/*     */ import buildcraft.api.fuels.IFuel;
/*     */ import buildcraft.api.fuels.ISolidCoolant;
/*     */ import buildcraft.core.lib.engines.TileEngineBase;
/*     */ import buildcraft.core.lib.engines.TileEngineWithInventory;
/*     */ import buildcraft.core.lib.fluids.Tank;
/*     */ import buildcraft.core.lib.fluids.TankManager;
/*     */ import buildcraft.core.lib.fluids.TankUtils;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.statements.IBlockDefaultTriggers;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ 
/*     */ public class TileEngineIron
/*     */   extends TileEngineWithInventory
/*     */   implements IFluidHandler, IBlockDefaultTriggers
/*     */ {
/*  45 */   public static int MAX_LIQUID = 10000;
/*  46 */   public static float HEAT_PER_RF = 2.3E-4F;
/*  47 */   public static float COOLDOWN_RATE = 0.05F;
/*  48 */   public static int MAX_COOLANT_PER_TICK = 40;
/*     */   
/*  50 */   public Tank tankFuel = new Tank("tankFuel", MAX_LIQUID, (TileEntity)this);
/*  51 */   public Tank tankCoolant = new Tank("tankCoolant", MAX_LIQUID, (TileEntity)this);
/*     */   
/*  53 */   private int burnTime = 0;
/*  54 */   private float coolingBuffer = 0.0F;
/*  55 */   private int tankFuelAmountCache = 0;
/*  56 */   private int tankCoolantAmountCache = 0;
/*     */   
/*  58 */   private TankManager<Tank> tankManager = new TankManager();
/*     */   private IFuel currentFuel;
/*  60 */   private int penaltyCooling = 0;
/*     */   private boolean lastPowered = false;
/*     */   private BiomeGenBase biomeCache;
/*     */   
/*     */   public TileEngineIron() {
/*  65 */     super(1);
/*  66 */     this.tankManager.add(this.tankFuel);
/*  67 */     this.tankManager.add(this.tankCoolant);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(EntityPlayer player, ForgeDirection side) {
/*  72 */     if (super.onBlockActivated(player, side)) {
/*  73 */       return true;
/*     */     }
/*     */     
/*  76 */     ItemStack current = player.func_71045_bC();
/*  77 */     if (current != null) {
/*  78 */       if (current.func_77973_b() instanceof buildcraft.api.transport.IItemPipe) {
/*  79 */         return false;
/*     */       }
/*  81 */       if (!this.field_145850_b.field_72995_K) {
/*  82 */         if (TankUtils.handleRightClick(this, side, player, true, true)) {
/*  83 */           return true;
/*     */         }
/*     */       }
/*  86 */       else if (FluidContainerRegistry.isContainer(current)) {
/*  87 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     if (!this.field_145850_b.field_72995_K) {
/*  92 */       player.openGui(BuildCraftEnergy.instance, 20, this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/*  94 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPistonSpeed() {
/*  99 */     if (!this.field_145850_b.field_72995_K) {
/* 100 */       return Math.max(0.07F * getHeatLevel(), 0.01F);
/*     */     }
/* 102 */     switch (getEnergyStage()) {
/*     */       case BLUE:
/* 104 */         return 0.04F;
/*     */       case GREEN:
/* 106 */         return 0.05F;
/*     */       case YELLOW:
/* 108 */         return 0.06F;
/*     */       case RED:
/* 110 */         return 0.07F;
/*     */     } 
/* 112 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasFuelBelowThreshold(float threshold) {
/* 117 */     FluidStack fuel = this.tankFuel.getFluid();
/*     */     
/* 119 */     if (fuel == null) {
/* 120 */       return true;
/*     */     }
/*     */     
/* 123 */     float percentage = fuel.amount / MAX_LIQUID;
/* 124 */     return (percentage < threshold);
/*     */   }
/*     */   
/*     */   public boolean hasCoolantBelowThreshold(float threshold) {
/* 128 */     FluidStack coolant = this.tankCoolant.getFluid();
/*     */     
/* 130 */     if (coolant == null) {
/* 131 */       return true;
/*     */     }
/*     */     
/* 134 */     float percentage = coolant.amount / MAX_LIQUID;
/* 135 */     return (percentage < threshold);
/*     */   }
/*     */   
/*     */   private float getBiomeTempScalar() {
/* 139 */     if (this.biomeCache == null) {
/* 140 */       this.biomeCache = this.field_145850_b.func_72807_a(this.field_145851_c, this.field_145849_e);
/*     */     }
/* 142 */     float tempScalar = this.biomeCache.field_76750_F - 1.0F;
/* 143 */     tempScalar *= 0.5F;
/* 144 */     tempScalar++;
/* 145 */     return tempScalar;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 150 */     super.func_145843_s();
/* 151 */     this.biomeCache = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 156 */     if (getEnergyStage() == TileEngineBase.EnergyStage.OVERHEAT) {
/* 157 */       return false;
/*     */     }
/*     */     
/* 160 */     FluidStack fuel = this.tankFuel.getFluid();
/* 161 */     return (fuel != null && fuel.amount > 0 && this.penaltyCooling == 0 && this.isRedstonePowered);
/*     */   }
/*     */ 
/*     */   
/*     */   public void overheat() {
/* 166 */     super.overheat();
/*     */     
/* 168 */     this.tankCoolant.setFluid(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void burn() {
/* 173 */     FluidStack fuel = this.tankFuel.getFluid();
/* 174 */     if (this.currentFuel == null && fuel != null) {
/* 175 */       this.currentFuel = BuildcraftFuelRegistry.fuel.getFuel(fuel.getFluid());
/*     */     }
/*     */     
/* 178 */     if (this.currentFuel == null) {
/*     */       return;
/*     */     }
/*     */     
/* 182 */     if (this.penaltyCooling <= 0 && this.isRedstonePowered) {
/*     */       
/* 184 */       this.lastPowered = true;
/*     */       
/* 186 */       if (this.burnTime > 0 || (fuel != null && fuel.amount > 0)) {
/* 187 */         if (this.burnTime > 0) {
/* 188 */           this.burnTime--;
/*     */         }
/* 190 */         if (this.burnTime <= 0) {
/* 191 */           if (fuel != null) {
/* 192 */             if (--fuel.amount <= 0) {
/* 193 */               this.tankFuel.setFluid(null);
/*     */             }
/* 195 */             this.burnTime = this.currentFuel.getTotalBurningTime() / 1000;
/*     */           } else {
/* 197 */             this.currentFuel = null;
/*     */             
/*     */             return;
/*     */           } 
/*     */         }
/* 202 */         this.currentOutput = this.currentFuel.getPowerPerCycle();
/*     */         
/* 204 */         addEnergy(this.currentFuel.getPowerPerCycle());
/* 205 */         this.heat += this.currentFuel.getPowerPerCycle() * HEAT_PER_RF * getBiomeTempScalar();
/*     */       } 
/* 207 */     } else if (this.penaltyCooling <= 0 && 
/* 208 */       this.lastPowered) {
/* 209 */       this.lastPowered = false;
/* 210 */       this.penaltyCooling = 10;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateHeat() {
/* 218 */     if (this.energyStage == TileEngineBase.EnergyStage.OVERHEAT && this.heat > 20.0F) {
/* 219 */       this.heat -= COOLDOWN_RATE;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void engineUpdate() {
/* 225 */     super.engineUpdate();
/*     */     
/* 227 */     ItemStack stack = func_70301_a(0);
/* 228 */     if (stack != null) {
/* 229 */       FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(stack);
/* 230 */       if (liquid == null && this.heat > 40.0F) {
/* 231 */         ItemStack stackOne = stack.func_77946_l();
/* 232 */         stackOne.field_77994_a = 1;
/* 233 */         ISolidCoolant coolant = BuildcraftFuelRegistry.coolant.getSolidCoolant(StackKey.stack(stackOne));
/* 234 */         if (coolant != null) {
/* 235 */           liquid = coolant.getFluidFromSolidCoolant(stackOne);
/*     */         }
/*     */       } 
/*     */       
/* 239 */       if (liquid != null && 
/* 240 */         fill(ForgeDirection.UNKNOWN, liquid, false) == liquid.amount) {
/* 241 */         fill(ForgeDirection.UNKNOWN, liquid, true);
/* 242 */         func_70299_a(0, InvUtils.consumeItem(stack));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 247 */     if (this.heat > 20.0F && (this.penaltyCooling > 0 || !this.isRedstonePowered)) {
/* 248 */       this.heat -= COOLDOWN_RATE;
/* 249 */       coolEngine(20.0F);
/* 250 */       getEnergyStage();
/* 251 */     } else if (this.heat > 100.0F) {
/* 252 */       coolEngine(100.0F);
/*     */     } 
/*     */     
/* 255 */     if (this.heat <= 20.0F && this.penaltyCooling > 0) {
/* 256 */       this.penaltyCooling--;
/*     */     }
/*     */     
/* 259 */     if (this.heat <= 20.0F) {
/* 260 */       this.heat = 20.0F;
/*     */     }
/*     */   }
/*     */   
/*     */   private void coolEngine(float idealHeat) {
/* 265 */     float extraHeat = this.heat - idealHeat;
/*     */     
/* 267 */     if (this.coolingBuffer < extraHeat) {
/* 268 */       fillCoolingBuffer();
/*     */     }
/*     */     
/* 271 */     if (this.coolingBuffer >= extraHeat) {
/* 272 */       this.coolingBuffer -= extraHeat;
/* 273 */       this.heat -= extraHeat;
/*     */       
/*     */       return;
/*     */     } 
/* 277 */     this.heat -= this.coolingBuffer;
/* 278 */     this.coolingBuffer = 0.0F;
/*     */   }
/*     */   
/*     */   private void fillCoolingBuffer() {
/* 282 */     FluidStack coolant = this.tankCoolant.getFluid();
/* 283 */     if (coolant == null) {
/*     */       return;
/*     */     }
/*     */     
/* 287 */     int coolantAmount = Math.min(MAX_COOLANT_PER_TICK, coolant.amount);
/* 288 */     ICoolant currentCoolant = BuildcraftFuelRegistry.coolant.getCoolant(coolant.getFluid());
/* 289 */     if (currentCoolant != null) {
/* 290 */       float cooling = currentCoolant.getDegreesCoolingPerMB(this.heat);
/* 291 */       cooling /= getBiomeTempScalar();
/* 292 */       this.coolingBuffer += coolantAmount * cooling;
/* 293 */       this.tankCoolant.drain(coolantAmount, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound data) {
/* 299 */     super.func_145839_a(data);
/* 300 */     this.tankManager.readFromNBT(data);
/*     */     
/* 302 */     this.burnTime = data.func_74762_e("burnTime");
/* 303 */     this.penaltyCooling = data.func_74762_e("penaltyCooling");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound data) {
/* 309 */     super.func_145841_b(data);
/* 310 */     this.tankManager.writeToNBT(data);
/*     */     
/* 312 */     data.func_74768_a("burnTime", this.burnTime);
/* 313 */     data.func_74768_a("penaltyCooling", this.penaltyCooling);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getGUINetworkData(int id, int value) {
/* 319 */     super.getGUINetworkData(id, value);
/* 320 */     switch (id) {
/*     */       
/*     */       case 15:
/* 323 */         if (FluidRegistry.getFluid(value) != null) {
/* 324 */           this.tankFuel.setFluid(new FluidStack(FluidRegistry.getFluid(value), this.tankFuelAmountCache)); break;
/*     */         } 
/* 326 */         this.tankFuel.setFluid(null);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 16:
/* 331 */         if (FluidRegistry.getFluid(value) != null) {
/* 332 */           this.tankCoolant.setFluid(new FluidStack(FluidRegistry.getFluid(value), this.tankCoolantAmountCache)); break;
/*     */         } 
/* 334 */         this.tankCoolant.setFluid(null);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 17:
/* 339 */         this.tankFuelAmountCache = value;
/* 340 */         if (this.tankFuel.getFluid() != null) {
/* 341 */           (this.tankFuel.getFluid()).amount = value;
/*     */         }
/*     */         break;
/*     */       
/*     */       case 18:
/* 346 */         this.tankCoolantAmountCache = value;
/* 347 */         if (this.tankCoolant.getFluid() != null) {
/* 348 */           (this.tankCoolant.getFluid()).amount = value;
/*     */         }
/*     */         break;
/*     */       
/*     */       case 19:
/* 353 */         this.tankFuel.colorRenderCache = value;
/*     */         break;
/*     */       
/*     */       case 20:
/* 357 */         this.tankCoolant.colorRenderCache = value;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendGUINetworkData(Container containerEngine, ICrafting iCrafting) {
/* 364 */     super.sendGUINetworkData(containerEngine, iCrafting);
/* 365 */     iCrafting.func_71112_a(containerEngine, 15, (this.tankFuel.getFluid() != null && this.tankFuel.getFluid().getFluid() != null) ? this.tankFuel.getFluid().getFluid().getID() : 0);
/* 366 */     iCrafting.func_71112_a(containerEngine, 16, (this.tankCoolant.getFluid() != null && this.tankCoolant.getFluid().getFluid() != null) ? this.tankCoolant.getFluid().getFluid().getID() : 0);
/* 367 */     iCrafting.func_71112_a(containerEngine, 17, (this.tankFuel.getFluid() != null) ? (this.tankFuel.getFluid()).amount : 0);
/* 368 */     iCrafting.func_71112_a(containerEngine, 18, (this.tankCoolant.getFluid() != null) ? (this.tankCoolant.getFluid()).amount : 0);
/* 369 */     iCrafting.func_71112_a(containerEngine, 19, this.tankFuel.colorRenderCache);
/* 370 */     iCrafting.func_71112_a(containerEngine, 20, this.tankCoolant.colorRenderCache);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 375 */     return (this.penaltyCooling <= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/* 381 */     return this.tankFuel.drain(maxDrain, doDrain);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/* 386 */     if (resource == null) {
/* 387 */       return null;
/*     */     }
/* 389 */     if (this.tankCoolant.getFluidType() == resource.getFluid()) {
/* 390 */       return this.tankCoolant.drain(resource.amount, doDrain);
/*     */     }
/* 392 */     if (this.tankFuel.getFluidType() == resource.getFluid()) {
/* 393 */       return this.tankFuel.drain(resource.amount, doDrain);
/*     */     }
/* 395 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 400 */     return (from != this.orientation);
/*     */   }
/*     */ 
/*     */   
/*     */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/* 405 */     if (resource == null || resource.getFluid() == null) {
/* 406 */       return 0;
/*     */     }
/*     */     
/* 409 */     if (BuildcraftFuelRegistry.coolant.getCoolant(resource.getFluid()) != null)
/* 410 */       return this.tankCoolant.fill(resource, doFill); 
/* 411 */     if (BuildcraftFuelRegistry.fuel.getFuel(resource.getFluid()) != null) {
/* 412 */       int filled = this.tankFuel.fill(resource, doFill);
/* 413 */       if (filled > 0 && this.tankFuel.getFluid() != null && this.tankFuel.getFluid().getFluid() != null && (this.currentFuel == null || this.tankFuel.getFluid().getFluid() != this.currentFuel.getFluid())) {
/* 414 */         this.currentFuel = BuildcraftFuelRegistry.fuel.getFuel(this.tankFuel.getFluid().getFluid());
/*     */       }
/* 416 */       return filled;
/*     */     } 
/* 418 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/* 424 */     return (from != this.orientation && fluid != null && (BuildcraftFuelRegistry.coolant
/* 425 */       .getCoolant(fluid) != null || BuildcraftFuelRegistry.fuel
/* 426 */       .getFuel(fluid) != null));
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidTankInfo[] getTankInfo(ForgeDirection direction) {
/* 431 */     return this.tankManager.getTankInfo(direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 436 */     if (itemstack == null)
/* 437 */       return false; 
/* 438 */     if (BuildcraftFuelRegistry.coolant.getSolidCoolant(StackKey.stack(itemstack)) != null) {
/* 439 */       return true;
/*     */     }
/* 441 */     FluidStack fluidStack = FluidContainerRegistry.getFluidForFilledItem(itemstack);
/* 442 */     return (fluidStack != null && canFill(ForgeDirection.UNKNOWN, fluidStack.getFluid()));
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack getFuel() {
/* 447 */     return this.tankFuel.getFluid();
/*     */   }
/*     */   
/*     */   public FluidStack getCoolant() {
/* 451 */     return this.tankCoolant.getFluid();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergy() {
/* 456 */     return 100000;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIdealOutput() {
/* 461 */     if (this.currentFuel == null) {
/* 462 */       return 0;
/*     */     }
/* 464 */     return this.currentFuel.getPowerPerCycle();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 470 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean blockInventoryTriggers(ForgeDirection side) {
/* 475 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean blockFluidHandlerTriggers(ForgeDirection side) {
/* 480 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\TileEngineIron.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */