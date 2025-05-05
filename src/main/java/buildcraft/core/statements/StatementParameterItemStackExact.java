/*     */ package buildcraft.core.statements;
/*     */ 
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.StatementMouseClick;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ public class StatementParameterItemStackExact implements IStatementParameter {
/*     */   protected ItemStack stack;
/*     */   private int availableSlots;
/*     */   
/*     */   public StatementParameterItemStackExact() {
/*  18 */     this(-1);
/*     */   }
/*     */   
/*     */   public StatementParameterItemStackExact(int availableSlots) {
/*  22 */     this.availableSlots = availableSlots;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon getIcon() {
/*  27 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack() {
/*  32 */     return this.stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClick(IStatementContainer source, IStatement stmt, ItemStack stack, StatementMouseClick mouse) {
/*  37 */     if (stack != null) {
/*  38 */       if (areItemsEqual(this.stack, stack)) {
/*  39 */         if (mouse.getButton() == 0) {
/*  40 */           this.stack.field_77994_a += mouse.isShift() ? 16 : 1;
/*     */           
/*  42 */           int maxSize = (this.availableSlots < 0) ? 64 : Math.min(64, this.stack.func_77976_d() * this.availableSlots);
/*  43 */           if (this.stack.field_77994_a > maxSize) {
/*  44 */             this.stack.field_77994_a = maxSize;
/*     */           }
/*     */         } else {
/*  47 */           this.stack.field_77994_a -= mouse.isShift() ? 16 : 1;
/*  48 */           if (this.stack.field_77994_a <= 0) {
/*  49 */             this.stack = null;
/*     */           }
/*     */         } 
/*     */       } else {
/*  53 */         this.stack = stack.func_77946_l();
/*     */       }
/*     */     
/*  56 */     } else if (this.stack != null) {
/*  57 */       if (mouse.getButton() == 0) {
/*  58 */         this.stack.field_77994_a += mouse.isShift() ? 16 : 1;
/*     */         
/*  60 */         int maxSize = (this.availableSlots < 0) ? 64 : Math.min(64, this.stack.func_77976_d() * this.availableSlots);
/*  61 */         if (this.stack.field_77994_a > maxSize) {
/*  62 */           this.stack.field_77994_a = maxSize;
/*     */         }
/*     */       } else {
/*  65 */         this.stack.field_77994_a -= mouse.isShift() ? 16 : 1;
/*  66 */         if (this.stack.field_77994_a <= 0) {
/*  67 */           this.stack = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  76 */     if (this.stack != null) {
/*  77 */       NBTTagCompound tagCompound = new NBTTagCompound();
/*  78 */       this.stack.func_77955_b(tagCompound);
/*  79 */       compound.func_74782_a("stack", (NBTBase)tagCompound);
/*     */     } 
/*     */     
/*  82 */     compound.func_74768_a("availableSlots", this.availableSlots);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  87 */     if (compound.func_74764_b("availableSlots")) {
/*  88 */       this.availableSlots = compound.func_74762_e("availableSlots");
/*     */     }
/*  90 */     this.stack = ItemStack.func_77949_a(compound.func_74775_l("stack"));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/*  95 */     if (object instanceof StatementParameterItemStackExact) {
/*  96 */       StatementParameterItemStackExact param = (StatementParameterItemStackExact)object;
/*     */       
/*  98 */       return areItemsEqual(this.stack, param.stack);
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
/* 105 */     if (stack1 != null) {
/* 106 */       return (stack2 != null && stack1.func_77969_a(stack2) && ItemStack.func_77970_a(stack1, stack2));
/*     */     }
/* 108 */     return (stack2 == null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 114 */     if (this.stack != null) {
/* 115 */       return this.stack.func_82833_r();
/*     */     }
/* 117 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueTag() {
/* 123 */     return "buildcraft:stackExact";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerIcons(IIconRegister iconRegister) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public IStatementParameter rotateLeft() {
/* 133 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\StatementParameterItemStackExact.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */