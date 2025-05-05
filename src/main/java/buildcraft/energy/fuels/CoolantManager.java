/*     */ package buildcraft.energy.fuels;
/*     */ 
/*     */ import buildcraft.api.core.StackKey;
/*     */ import buildcraft.api.fuels.ICoolant;
/*     */ import buildcraft.api.fuels.ICoolantManager;
/*     */ import buildcraft.api.fuels.ISolidCoolant;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ public final class CoolantManager
/*     */   implements ICoolantManager
/*     */ {
/*  26 */   public static final CoolantManager INSTANCE = new CoolantManager();
/*     */   
/*  28 */   private final List<ICoolant> coolants = new LinkedList<ICoolant>();
/*  29 */   private final List<ISolidCoolant> solidCoolants = new LinkedList<ISolidCoolant>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ICoolant addCoolant(ICoolant coolant) {
/*  36 */     this.coolants.add(coolant);
/*  37 */     return coolant;
/*     */   }
/*     */ 
/*     */   
/*     */   public ICoolant addCoolant(Fluid fluid, float degreesCoolingPerMB) {
/*  42 */     return addCoolant(new BCCoolant(fluid, degreesCoolingPerMB));
/*     */   }
/*     */ 
/*     */   
/*     */   public ISolidCoolant addSolidCoolant(ISolidCoolant solidCoolant) {
/*  47 */     this.solidCoolants.add(solidCoolant);
/*  48 */     return solidCoolant;
/*     */   }
/*     */ 
/*     */   
/*     */   public ISolidCoolant addSolidCoolant(StackKey solid, StackKey liquid, float multiplier) {
/*  53 */     assert solid.stack != null && solid.fluidStack == null;
/*  54 */     assert liquid.stack == null && liquid.fluidStack != null;
/*  55 */     return addSolidCoolant(new BCSolidCoolant(solid, liquid, multiplier));
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ICoolant> getCoolants() {
/*  60 */     return this.coolants;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ISolidCoolant> getSolidCoolants() {
/*  65 */     return this.solidCoolants;
/*     */   }
/*     */ 
/*     */   
/*     */   public ICoolant getCoolant(Fluid fluid) {
/*  70 */     for (ICoolant coolant : this.coolants) {
/*  71 */       if (coolant.getFluid() == fluid) {
/*  72 */         return coolant;
/*     */       }
/*     */     } 
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ISolidCoolant getSolidCoolant(StackKey solid) {
/*  80 */     assert solid.stack != null && solid.fluidStack == null;
/*  81 */     for (ISolidCoolant solidCoolant : this.solidCoolants) {
/*  82 */       if (solidCoolant.getFluidFromSolidCoolant(solid.stack) != null) {
/*  83 */         return solidCoolant;
/*     */       }
/*     */     } 
/*  86 */     return null;
/*     */   }
/*     */   
/*     */   private static final class BCCoolant implements ICoolant {
/*     */     private final Fluid fluid;
/*     */     private final float degreesCoolingPerMB;
/*     */     
/*     */     public BCCoolant(Fluid fluid, float degreesCoolingPerMB) {
/*  94 */       this.fluid = fluid;
/*  95 */       this.degreesCoolingPerMB = degreesCoolingPerMB;
/*     */     }
/*     */ 
/*     */     
/*     */     public Fluid getFluid() {
/* 100 */       return this.fluid;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getDegreesCoolingPerMB(float heat) {
/* 105 */       return this.degreesCoolingPerMB;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class BCSolidCoolant implements ISolidCoolant {
/*     */     private final StackKey solid;
/*     */     private final StackKey liquid;
/*     */     private final float multiplier;
/*     */     
/*     */     public BCSolidCoolant(StackKey solid, StackKey liquid, float multiplier) {
/* 115 */       this.solid = solid;
/* 116 */       this.liquid = liquid;
/* 117 */       this.multiplier = multiplier;
/*     */     }
/*     */ 
/*     */     
/*     */     public FluidStack getFluidFromSolidCoolant(ItemStack stack) {
/* 122 */       if (stack == null || !stack.func_77969_a(this.solid.stack)) {
/* 123 */         return null;
/*     */       }
/* 125 */       int liquidAmount = (int)((stack.field_77994_a * this.liquid.fluidStack.amount) * this.multiplier / this.solid.stack.field_77994_a);
/* 126 */       return new FluidStack(this.liquid.fluidStack.getFluid(), liquidAmount);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\fuels\CoolantManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */