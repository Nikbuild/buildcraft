/*     */ package buildcraft.transport.statements;
/*     */ 
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.StatementMouseClick;
/*     */ import buildcraft.api.transport.PipeWire;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.transport.Gate;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IIcon;
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
/*     */ public class ActionParameterSignal
/*     */   implements IStatementParameter
/*     */ {
/*     */   private static IIcon[] icons;
/*  30 */   public PipeWire color = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getIcon() {
/*  38 */     if (this.color == null) {
/*  39 */       return null;
/*     */     }
/*  41 */     return icons[this.color.ordinal()];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClick(IStatementContainer source, IStatement stmt, ItemStack stack, StatementMouseClick mouse) {
/*  47 */     int maxColor = 4;
/*  48 */     if (source instanceof Gate) {
/*  49 */       maxColor = ((Gate)source).material.maxWireColor;
/*     */     }
/*     */     
/*  52 */     if (this.color == null) {
/*  53 */       this.color = (mouse.getButton() == 0) ? PipeWire.RED : PipeWire.values()[maxColor - 1];
/*  54 */     } else if (this.color == ((mouse.getButton() == 0) ? PipeWire.values()[maxColor - 1] : PipeWire.RED)) {
/*  55 */       this.color = null;
/*     */     } else {
/*     */       do {
/*  58 */         this.color = PipeWire.values()[((mouse.getButton() == 0) ? (this.color.ordinal() + 1) : (this.color.ordinal() - 1)) & 0x3];
/*  59 */       } while (this.color.ordinal() >= maxColor);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*  65 */     if (this.color != null) {
/*  66 */       nbt.func_74774_a("color", (byte)this.color.ordinal());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  72 */     if (nbt.func_74764_b("color")) {
/*  73 */       this.color = PipeWire.values()[nbt.func_74771_c("color")];
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/*  79 */     if (object instanceof ActionParameterSignal) {
/*  80 */       ActionParameterSignal param = (ActionParameterSignal)object;
/*     */       
/*  82 */       return (param.color == this.color);
/*     */     } 
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  90 */     if (this.color == null) {
/*  91 */       return null;
/*     */     }
/*  93 */     return String.format(StringUtils.localize("gate.action.pipe.wire"), new Object[] { StringUtils.localize("color." + this.color.name().toLowerCase(Locale.ENGLISH)) });
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUniqueTag() {
/*  98 */     return "buildcraft:pipeWireAction";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerIcons(IIconRegister iconRegister) {
/* 107 */     icons = new IIcon[] { iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_red_active"), iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_blue_active"), iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_green_active"), iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_yellow_active") };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IStatementParameter rotateLeft() {
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack() {
/* 119 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\ActionParameterSignal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */