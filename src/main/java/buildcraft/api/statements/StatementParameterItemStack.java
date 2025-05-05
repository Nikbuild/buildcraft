/*     */ package buildcraft.api.statements;
/*     */ 
/*     */ import buildcraft.api.core.render.ISprite;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatementParameterItemStack
/*     */   implements IStatementParameter
/*     */ {
/*     */   @Nonnull
/*     */   private static final ItemStack EMPTY_STACK;
/*     */   @Nonnull
/*     */   protected final ItemStack stack;
/*     */   
/*     */   static {
/*  30 */     ItemStack stack = ItemStack.field_190927_a;
/*  31 */     if (stack == null) throw new Error("Somehow ItemStack.EMPTY was null!"); 
/*  32 */     EMPTY_STACK = stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatementParameterItemStack() {
/*  39 */     this.stack = EMPTY_STACK;
/*     */   }
/*     */   
/*     */   public StatementParameterItemStack(@Nonnull ItemStack stack) {
/*  43 */     this.stack = stack;
/*     */   }
/*     */   
/*     */   public StatementParameterItemStack(NBTTagCompound nbt) {
/*  47 */     ItemStack read = new ItemStack(nbt.func_74775_l("stack"));
/*  48 */     if (read.func_190926_b()) {
/*  49 */       this.stack = EMPTY_STACK;
/*     */     } else {
/*  51 */       this.stack = read;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNbt(NBTTagCompound compound) {
/*  57 */     if (!this.stack.func_190926_b()) {
/*  58 */       NBTTagCompound tagCompound = new NBTTagCompound();
/*  59 */       this.stack.func_77955_b(tagCompound);
/*  60 */       compound.func_74782_a("stack", (NBTBase)tagCompound);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ISprite getSprite() {
/*  66 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemStack getItemStack() {
/*  72 */     return this.stack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StatementParameterItemStack onClick(IStatementContainer source, IStatement stmt, ItemStack stack, StatementMouseClick mouse) {
/*  78 */     if (stack.func_190926_b()) {
/*  79 */       return new StatementParameterItemStack();
/*     */     }
/*  81 */     ItemStack newStack = stack.func_77946_l();
/*  82 */     newStack.func_190920_e(1);
/*  83 */     return new StatementParameterItemStack(newStack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/*  89 */     if (object instanceof StatementParameterItemStack) {
/*  90 */       StatementParameterItemStack param = (StatementParameterItemStack)object;
/*     */       
/*  92 */       return (ItemStack.func_77989_b(this.stack, param.stack) && 
/*  93 */         ItemStack.func_77970_a(this.stack, param.stack));
/*     */     } 
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return Objects.hashCode(this.stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 106 */     if (this.stack.func_190926_b()) {
/* 107 */       return "";
/*     */     }
/* 109 */     return this.stack.func_82833_r();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public List<String> getTooltip() {
/* 116 */     if (this.stack.func_190926_b()) {
/* 117 */       return (List<String>)ImmutableList.of();
/*     */     }
/* 119 */     List<String> tooltip = this.stack.func_82840_a((EntityPlayer)(Minecraft.func_71410_x()).field_71439_g, false);
/* 120 */     if (!tooltip.isEmpty()) {
/* 121 */       tooltip.set(0, (this.stack.func_77953_t()).field_77937_e + (String)tooltip.get(0));
/* 122 */       for (int i = 1; i < tooltip.size(); i++) {
/* 123 */         tooltip.set(i, TextFormatting.GRAY + (String)tooltip.get(i));
/*     */       }
/*     */     } 
/* 126 */     return tooltip;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUniqueTag() {
/* 131 */     return "buildcraft:stack";
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter rotateLeft() {
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter[] getPossible(IStatementContainer source) {
/* 141 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\StatementParameterItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */