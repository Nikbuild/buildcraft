/*     */ package buildcraft.factory;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.SafeTimeTracker;
/*     */ import buildcraft.api.recipes.CraftingResult;
/*     */ import buildcraft.api.recipes.IFlexibleCrafter;
/*     */ import buildcraft.api.recipes.IFlexibleRecipe;
/*     */ import buildcraft.api.tiles.IHasWork;
/*     */ import buildcraft.core.lib.RFBattery;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.fluids.SingleUseTank;
/*     */ import buildcraft.core.lib.fluids.Tank;
/*     */ import buildcraft.core.lib.fluids.TankManager;
/*     */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.core.recipes.RefineryRecipeManager;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
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
/*     */ public class TileRefinery
/*     */   extends TileBuildCraft
/*     */   implements IFluidHandler, IHasWork, IFlexibleCrafter, ICommandReceiver
/*     */ {
/*  43 */   public static int LIQUID_PER_SLOT = 4000;
/*     */   
/*     */   public IFlexibleRecipe<FluidStack> currentRecipe;
/*     */   
/*     */   public CraftingResult<FluidStack> craftingResult;
/*  48 */   public SingleUseTank[] tanks = new SingleUseTank[] { new SingleUseTank("tank1", LIQUID_PER_SLOT, (TileEntity)this), new SingleUseTank("tank2", LIQUID_PER_SLOT, (TileEntity)this) };
/*     */ 
/*     */   
/*  51 */   public SingleUseTank result = new SingleUseTank("result", LIQUID_PER_SLOT, (TileEntity)this);
/*  52 */   public TankManager<SingleUseTank> tankManager = new TankManager((Tank[])new SingleUseTank[] { this.tanks[0], this.tanks[1], this.result });
/*  53 */   public float animationSpeed = 1.0F;
/*  54 */   private short animationStage = 0;
/*  55 */   private SafeTimeTracker time = new SafeTimeTracker();
/*     */   
/*  57 */   private SafeTimeTracker updateNetworkTime = new SafeTimeTracker(BuildCraftCore.updateFactor);
/*     */   
/*     */   private boolean isActive;
/*  60 */   private String currentRecipeId = "";
/*     */ 
/*     */   
/*     */   public TileRefinery() {
/*  64 */     setBattery(new RFBattery(10000, 1500, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  69 */     super.func_145845_h();
/*     */     
/*  71 */     if (this.field_145850_b.field_72995_K) {
/*  72 */       simpleAnimationIterate();
/*     */       
/*     */       return;
/*     */     } 
/*  76 */     if (this.updateNetworkTime.markTimeIfDelay(this.field_145850_b)) {
/*  77 */       sendNetworkUpdate();
/*     */     }
/*     */     
/*  80 */     this.isActive = false;
/*     */     
/*  82 */     if (this.currentRecipe == null) {
/*  83 */       decreaseAnimation();
/*     */       
/*     */       return;
/*     */     } 
/*  87 */     if (this.result.fill(((FluidStack)this.craftingResult.crafted).copy(), false) != ((FluidStack)this.craftingResult.crafted).amount) {
/*  88 */       decreaseAnimation();
/*     */       
/*     */       return;
/*     */     } 
/*  92 */     this.isActive = true;
/*     */     
/*  94 */     if (getBattery().getEnergyStored() >= this.craftingResult.energyCost) {
/*  95 */       increaseAnimation();
/*     */     } else {
/*  97 */       decreaseAnimation();
/*     */     } 
/*     */     
/* 100 */     if (!this.time.markTimeIfDelay(this.field_145850_b, this.craftingResult.craftingTime)) {
/*     */       return;
/*     */     }
/*     */     
/* 104 */     if (getBattery().useEnergy(this.craftingResult.energyCost, this.craftingResult.energyCost, true) > 0) {
/* 105 */       CraftingResult<FluidStack> r = this.currentRecipe.craft(this, true);
/* 106 */       if (r != null && r.crafted != null) {
/* 107 */         getBattery().useEnergy(this.craftingResult.energyCost, this.craftingResult.energyCost, false);
/* 108 */         r = this.currentRecipe.craft(this, false);
/* 109 */         if (r != null && r.crafted != null)
/*     */         {
/* 111 */           this.result.fill(((FluidStack)r.crafted).copy(), true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWork() {
/* 119 */     return this.isActive;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound data) {
/* 124 */     super.func_145839_a(data);
/*     */     
/* 126 */     this.tankManager.readFromNBT(data);
/*     */     
/* 128 */     this.animationStage = data.func_74765_d("animationStage");
/* 129 */     this.animationSpeed = data.func_74760_g("animationSpeed");
/*     */     
/* 131 */     updateRecipe();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound data) {
/* 136 */     super.func_145841_b(data);
/*     */     
/* 138 */     this.tankManager.writeToNBT(data);
/*     */     
/* 140 */     data.func_74777_a("animationStage", this.animationStage);
/* 141 */     data.func_74776_a("animationSpeed", this.animationSpeed);
/*     */   }
/*     */   
/*     */   public int getAnimationStage() {
/* 145 */     return this.animationStage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void simpleAnimationIterate() {
/* 152 */     if (this.animationSpeed > 1.0F) {
/* 153 */       this.animationStage = (short)(int)(this.animationStage + this.animationSpeed);
/*     */       
/* 155 */       if (this.animationStage > 300) {
/* 156 */         this.animationStage = 100;
/*     */       }
/* 158 */     } else if (this.animationStage > 0) {
/* 159 */       this.animationStage = (short)(this.animationStage - 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void increaseAnimation() {
/* 164 */     if (this.animationSpeed < 2.0F) {
/* 165 */       this.animationSpeed = 2.0F;
/* 166 */     } else if (this.animationSpeed <= 5.0F) {
/* 167 */       this.animationSpeed = (float)(this.animationSpeed + 0.1D);
/*     */     } 
/*     */     
/* 170 */     this.animationStage = (short)(int)(this.animationStage + this.animationSpeed);
/*     */     
/* 172 */     if (this.animationStage > 300) {
/* 173 */       this.animationStage = 100;
/*     */     }
/*     */   }
/*     */   
/*     */   public void decreaseAnimation() {
/* 178 */     if (this.animationSpeed >= 1.0F) {
/* 179 */       this.animationSpeed = (float)(this.animationSpeed - 0.1D);
/*     */       
/* 181 */       this.animationStage = (short)(int)(this.animationStage + this.animationSpeed);
/*     */       
/* 183 */       if (this.animationStage > 300) {
/* 184 */         this.animationStage = 100;
/*     */       }
/*     */     }
/* 187 */     else if (this.animationStage > 0) {
/* 188 */       this.animationStage = (short)(this.animationStage - 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetFilters() {
/* 194 */     for (SingleUseTank tank : this.tankManager) {
/* 195 */       tank.setAcceptedFluid(null);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setFilter(int number, Fluid fluid) {
/* 200 */     ((SingleUseTank)this.tankManager.get(number)).setAcceptedFluid(fluid);
/*     */   }
/*     */   
/*     */   public Fluid getFilter(int number) {
/* 204 */     return ((SingleUseTank)this.tankManager.get(number)).getAcceptedFluid();
/*     */   }
/*     */ 
/*     */   
/*     */   public void getGUINetworkData(int id, int data) {
/* 209 */     switch (id) {
/*     */       case 0:
/* 211 */         setFilter(0, FluidRegistry.getFluid(data));
/*     */         break;
/*     */       case 1:
/* 214 */         setFilter(1, FluidRegistry.getFluid(data));
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendGUINetworkData(Container container, ICrafting iCrafting) {
/* 220 */     if (getFilter(0) != null) {
/* 221 */       iCrafting.func_71112_a(container, 0, getFilter(0).getID());
/*     */     }
/* 223 */     if (getFilter(1) != null) {
/* 224 */       iCrafting.func_71112_a(container, 1, getFilter(1).getID());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/* 231 */     int used = 0;
/* 232 */     FluidStack resourceUsing = resource.copy();
/*     */     
/* 234 */     if (RefineryRecipeManager.INSTANCE.getValidFluidStacks1().contains(resource)) {
/* 235 */       used += this.tanks[0].fill(resourceUsing, doFill);
/* 236 */       resourceUsing.amount -= used;
/*     */     } 
/* 238 */     if (RefineryRecipeManager.INSTANCE.getValidFluidStacks2().contains(resource)) {
/* 239 */       used += this.tanks[1].fill(resourceUsing, doFill);
/* 240 */       resourceUsing.amount -= used;
/*     */     } 
/* 242 */     updateRecipe();
/*     */     
/* 244 */     return used;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, int maxEmpty, boolean doDrain) {
/* 249 */     FluidStack r = this.result.drain(maxEmpty, doDrain);
/*     */     
/* 251 */     updateRecipe();
/*     */     
/* 253 */     return r;
/*     */   }
/*     */   
/*     */   private void updateRecipe() {
/* 257 */     this.currentRecipe = null;
/* 258 */     this.craftingResult = null;
/*     */     
/* 260 */     for (IFlexibleRecipe<FluidStack> recipe : (Iterable<IFlexibleRecipe<FluidStack>>)RefineryRecipeManager.INSTANCE.getRecipes()) {
/* 261 */       this.craftingResult = recipe.craft(this, true);
/*     */       
/* 263 */       if (this.craftingResult != null) {
/* 264 */         this.currentRecipe = recipe;
/* 265 */         this.currentRecipeId = this.currentRecipe.getId();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/* 273 */     if (resource == null || !resource.isFluidEqual(this.result.getFluid())) {
/* 274 */       return null;
/*     */     }
/* 276 */     return drain(from, resource.amount, doDrain);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidTankInfo[] getTankInfo(ForgeDirection direction) {
/* 281 */     return this.tankManager.getTankInfo(direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 286 */     stream.writeFloat(this.animationSpeed);
/* 287 */     NetworkUtils.writeUTF(stream, this.currentRecipeId);
/* 288 */     this.tankManager.writeData(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 293 */     this.animationSpeed = stream.readFloat();
/* 294 */     this.currentRecipeId = NetworkUtils.readUTF(stream);
/* 295 */     this.tankManager.readData(stream);
/*     */     
/* 297 */     this.currentRecipe = RefineryRecipeManager.INSTANCE.getRecipe(this.currentRecipeId);
/*     */     
/* 299 */     if (this.currentRecipe != null) {
/* 300 */       this.craftingResult = this.currentRecipe.craft(this, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/* 306 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 311 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCraftingItemStackSize() {
/* 316 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingItemStack(int slotid) {
/* 321 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack decrCraftingItemStack(int slotid, int val) {
/* 326 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack getCraftingFluidStack(int tankid) {
/* 331 */     return this.tanks[tankid].getFluid();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidStack decrCraftingFluidStack(int tankid, int val) {
/*     */     FluidStack resultF;
/* 338 */     if (val >= (this.tanks[tankid].getFluid()).amount) {
/* 339 */       resultF = this.tanks[tankid].getFluid();
/* 340 */       this.tanks[tankid].setFluid(null);
/*     */     } else {
/* 342 */       resultF = this.tanks[tankid].getFluid().copy();
/* 343 */       resultF.amount = val;
/* 344 */       (this.tanks[tankid].getFluid()).amount -= val;
/*     */     } 
/*     */     
/* 347 */     updateRecipe();
/*     */     
/* 349 */     return resultF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCraftingFluidStackSize() {
/* 354 */     return this.tanks.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/* 359 */     if (side.isServer() && "setFilter".equals(command))
/* 360 */       setFilter(stream.readByte(), FluidRegistry.getFluid(stream.readShort())); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\TileRefinery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */