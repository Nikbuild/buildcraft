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
/*     */ public class TriggerParameterSignal
/*     */   implements IStatementParameter
/*     */ {
/*     */   private static IIcon[] icons;
/*     */   public boolean active = false;
/*  31 */   public PipeWire color = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack() {
/*  39 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon getIcon() {
/*  44 */     if (this.color == null) {
/*  45 */       return null;
/*     */     }
/*     */     
/*  48 */     return icons[this.color.ordinal() + (this.active ? 4 : 0)];
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClick(IStatementContainer source, IStatement stmt, ItemStack stack, StatementMouseClick mouse) {
/*  53 */     int maxColor = 4;
/*  54 */     if (source instanceof Gate) {
/*  55 */       maxColor = ((Gate)source).material.maxWireColor;
/*     */     }
/*     */     
/*  58 */     if (mouse.getButton() == 0) {
/*  59 */       if (this.color == null) {
/*  60 */         this.active = true;
/*  61 */         this.color = PipeWire.RED;
/*  62 */       } else if (this.active) {
/*  63 */         this.active = false;
/*  64 */       } else if (this.color == PipeWire.values()[maxColor - 1]) {
/*  65 */         this.color = null;
/*     */       } else {
/*     */         while (true) {
/*  68 */           this.color = PipeWire.values()[this.color.ordinal() + 1 & 0x3];
/*  69 */           if (this.color.ordinal() < maxColor)
/*  70 */           { this.active = true; return; } 
/*     */         } 
/*     */       } 
/*  73 */     } else if (this.color == null) {
/*  74 */       this.active = false;
/*  75 */       this.color = PipeWire.values()[maxColor - 1];
/*  76 */     } else if (!this.active) {
/*  77 */       this.active = true;
/*  78 */     } else if (this.color == PipeWire.RED) {
/*  79 */       this.color = null;
/*     */     } else {
/*     */       while (true) {
/*  82 */         this.color = PipeWire.values()[this.color.ordinal() - 1 & 0x3];
/*  83 */         if (this.color.ordinal() < maxColor) {
/*  84 */           this.active = false;
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*  91 */     nbt.func_74757_a("active", this.active);
/*     */     
/*  93 */     if (this.color != null) {
/*  94 */       nbt.func_74774_a("color", (byte)this.color.ordinal());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 101 */     this.active = nbt.func_74767_n("active");
/*     */     
/* 103 */     if (nbt.func_74764_b("color")) {
/* 104 */       this.color = PipeWire.values()[nbt.func_74771_c("color")];
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 110 */     if (this.color == null) {
/* 111 */       return null;
/*     */     }
/* 113 */     return String.format(StringUtils.localize("gate.trigger.pipe.wire." + (this.active ? "active" : "inactive")), new Object[] { StringUtils.localize("color." + this.color.name().toLowerCase(Locale.ENGLISH)) });
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUniqueTag() {
/* 118 */     return "buildcraft:pipeWireTrigger";
/*     */   }
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
/*     */   public void registerIcons(IIconRegister iconRegister) {
/* 131 */     icons = new IIcon[] { iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_red_inactive"), iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_blue_inactive"), iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_green_inactive"), iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_yellow_inactive"), iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_red_active"), iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_blue_active"), iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_green_active"), iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_yellow_active") };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IStatementParameter rotateLeft() {
/* 137 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\TriggerParameterSignal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */