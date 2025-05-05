/*     */ package buildcraft.api.core;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StackKey
/*     */ {
/*     */   public final ItemStack stack;
/*     */   public final FluidStack fluidStack;
/*     */   
/*     */   public StackKey(FluidStack fluidStack) {
/*  20 */     this(null, fluidStack);
/*     */   }
/*     */   
/*     */   public StackKey(ItemStack stack) {
/*  24 */     this(stack, null);
/*     */   }
/*     */   
/*     */   public StackKey(ItemStack stack, FluidStack fluidStack) {
/*  28 */     this.stack = stack;
/*  29 */     this.fluidStack = fluidStack;
/*     */   }
/*     */   
/*     */   public static StackKey stack(Item item, int amount, int damage) {
/*  33 */     return new StackKey(new ItemStack(item, amount, damage));
/*     */   }
/*     */   
/*     */   public static StackKey stack(Block block, int amount, int damage) {
/*  37 */     return new StackKey(new ItemStack(block, amount, damage));
/*     */   }
/*     */   
/*     */   public static StackKey stack(Item item) {
/*  41 */     return new StackKey(new ItemStack(item, 1, 0));
/*     */   }
/*     */   
/*     */   public static StackKey stack(Block block) {
/*  45 */     return new StackKey(new ItemStack(block, 1, 0));
/*     */   }
/*     */   
/*     */   public static StackKey stack(ItemStack itemStack) {
/*  49 */     return new StackKey(itemStack);
/*     */   }
/*     */   
/*     */   public static StackKey fluid(Fluid fluid, int amount) {
/*  53 */     return new StackKey(new FluidStack(fluid, amount));
/*     */   }
/*     */   
/*     */   public static StackKey fluid(Fluid fluid) {
/*  57 */     return new StackKey(new FluidStack(fluid, 1000));
/*     */   }
/*     */   
/*     */   public static StackKey fluid(FluidStack fluidStack) {
/*  61 */     return new StackKey(fluidStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  66 */     if (this == o) {
/*  67 */       return true;
/*     */     }
/*  69 */     if (o == null || o.getClass() != StackKey.class) {
/*  70 */       return false;
/*     */     }
/*  72 */     StackKey k = (StackKey)o;
/*  73 */     if ((((this.stack == null) ? 1 : 0) ^ ((k.stack == null) ? 1 : 0)) == 0) if ((((this.fluidStack == null) ? 1 : 0) ^ ((k.fluidStack == null) ? 1 : 0)) == 0) {
/*     */ 
/*     */         
/*  76 */         if (this.stack != null && (
/*  77 */           this.stack.func_77973_b() != k.stack.func_77973_b() || (this.stack.func_77981_g() && this.stack.func_77952_i() != k.stack.func_77952_i()) || !objectsEqual(this.stack
/*  78 */             .func_77978_p(), k.stack.func_77978_p()))) {
/*  79 */           return false;
/*     */         }
/*     */         
/*  82 */         if (this.fluidStack != null && (
/*  83 */           !this.fluidStack.isFluidEqual(k.fluidStack) || this.fluidStack.amount != k.fluidStack.amount)) {
/*  84 */           return false;
/*     */         }
/*     */         
/*  87 */         return true;
/*     */       }  
/*     */     return false;
/*     */   }
/*     */   public int hashCode() {
/*  92 */     int result = 7;
/*  93 */     if (this.stack != null) {
/*  94 */       result = 31 * result + this.stack.func_77973_b().hashCode();
/*  95 */       result = 31 * result + this.stack.func_77952_i();
/*  96 */       result = 31 * result + objectHashCode(this.stack.func_77978_p());
/*     */     } 
/*  98 */     result = 31 * result + 7;
/*  99 */     if (this.fluidStack != null) {
/* 100 */       result = 31 * result + this.fluidStack.getFluid().getName().hashCode();
/* 101 */       result = 31 * result + this.fluidStack.amount;
/* 102 */       result = 31 * result + objectHashCode(this.fluidStack.tag);
/*     */     } 
/* 104 */     return result;
/*     */   }
/*     */   
/*     */   private boolean objectsEqual(Object o1, Object o2) {
/* 108 */     if (o1 == null && o2 == null)
/* 109 */       return true; 
/* 110 */     if (o1 == null || o2 == null) {
/* 111 */       return false;
/*     */     }
/* 113 */     return o1.equals(o2);
/*     */   }
/*     */ 
/*     */   
/*     */   private int objectHashCode(Object o) {
/* 118 */     return (o != null) ? o.hashCode() : 0;
/*     */   }
/*     */   
/*     */   public StackKey copy() {
/* 122 */     return new StackKey((this.stack != null) ? this.stack.func_77946_l() : null, (this.fluidStack != null) ? this.fluidStack.copy() : null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\StackKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */