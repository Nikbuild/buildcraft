/*     */ package buildcraft.transport.gates;
/*     */ 
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GateDefinition
/*     */ {
/*     */   public static String getLocalizedName(GateMaterial material, GateLogic logic) {
/*  28 */     if (material == GateMaterial.REDSTONE) {
/*  29 */       return StringUtils.localize("gate.name.basic");
/*     */     }
/*  31 */     return String.format(StringUtils.localize("gate.name"), new Object[] { StringUtils.localize("gate.material." + material.getTag()), 
/*  32 */           StringUtils.localize("gate.logic." + logic.getTag()) });
/*     */   }
/*     */ 
/*     */   
/*     */   public enum GateMaterial
/*     */   {
/*  38 */     REDSTONE("gate_interface_1.png", 146, 1, 0, 0, 1),
/*  39 */     IRON("gate_interface_2.png", 164, 2, 0, 0, 2),
/*  40 */     GOLD("gate_interface_3.png", 200, 4, 1, 0, 3),
/*  41 */     DIAMOND("gate_interface_4.png", 200, 8, 1, 0, 4),
/*  42 */     EMERALD("gate_interface_5.png", 200, 4, 3, 3, 4),
/*  43 */     QUARTZ("gate_interface_6.png", 164, 2, 1, 1, 3);
/*     */     
/*  45 */     public static final GateMaterial[] VALUES = values();
/*     */ 
/*     */     
/*     */     public final ResourceLocation guiFile;
/*     */ 
/*     */     
/*     */     public final int guiHeight;
/*     */     
/*     */     public final int numSlots;
/*     */     
/*     */     public final int numTriggerParameters;
/*     */ 
/*     */     
/*     */     GateMaterial(String guiFile, int guiHeight, int numSlots, int triggerParameterSlots, int actionParameterSlots, int maxWireColor) {
/*  59 */       this.guiFile = new ResourceLocation("buildcrafttransport:textures/gui/" + guiFile);
/*  60 */       this.guiHeight = guiHeight;
/*  61 */       this.numSlots = numSlots;
/*  62 */       this.numTriggerParameters = triggerParameterSlots;
/*  63 */       this.numActionParameters = actionParameterSlots;
/*  64 */       this.maxWireColor = maxWireColor;
/*     */     } public final int numActionParameters; public final int maxWireColor; @SideOnly(Side.CLIENT)
/*     */     private IIcon iconBlock; @SideOnly(Side.CLIENT)
/*     */     private IIcon iconItem; static {  } @SideOnly(Side.CLIENT)
/*     */     public IIcon getIconBlock() {
/*  69 */       return this.iconBlock;
/*     */     }
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public IIcon getIconItem() {
/*  74 */       return this.iconItem;
/*     */     }
/*     */     
/*     */     public String getTag() {
/*  78 */       return name().toLowerCase(Locale.ENGLISH);
/*     */     }
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void registerBlockIcon(IIconRegister iconRegister) {
/*  83 */       if (this != REDSTONE) {
/*  84 */         this.iconBlock = iconRegister.func_94245_a("buildcrafttransport:gates/gate_material_" + getTag());
/*     */       }
/*     */     }
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void registerItemIcon(IIconRegister iconRegister) {
/*  90 */       if (this != REDSTONE) {
/*  91 */         this.iconItem = iconRegister.func_94245_a("buildcrafttransport:gates/gate_material_" + getTag());
/*     */       }
/*     */     }
/*     */     
/*     */     public static GateMaterial fromOrdinal(int ordinal) {
/*  96 */       if (ordinal < 0 || ordinal >= VALUES.length) {
/*  97 */         return REDSTONE;
/*     */       }
/*  99 */       return VALUES[ordinal];
/*     */     }
/*     */   }
/*     */   
/*     */   public enum GateLogic
/*     */   {
/* 105 */     AND, OR; @SideOnly(Side.CLIENT)
/* 106 */     private IIcon iconGate; public static final GateLogic[] VALUES = values();
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     private IIcon iconItem;
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     private IIcon iconDark;
/*     */     @SideOnly(Side.CLIENT)
/*     */     private IIcon iconLit;
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public IIcon getIconLit() {
/* 122 */       return this.iconLit;
/*     */     }
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public IIcon getIconDark() {
/* 127 */       return this.iconDark;
/*     */     }
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public IIcon getGateIcon() {
/* 132 */       return this.iconGate;
/*     */     }
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public IIcon getIconItem() {
/* 137 */       return this.iconItem;
/*     */     }
/*     */     
/*     */     public String getTag() {
/* 141 */       return name().toLowerCase(Locale.ENGLISH);
/*     */     }
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void registerBlockIcon(IIconRegister iconRegister) {
/* 146 */       this.iconLit = iconRegister.func_94245_a("buildcrafttransport:gates/gate_" + getTag() + "_lit");
/* 147 */       this.iconDark = iconRegister.func_94245_a("buildcrafttransport:gates/gate_" + getTag() + "_dark");
/*     */       
/* 149 */       this.iconGate = iconRegister.func_94245_a("buildcrafttransport:gates/gate_" + getTag());
/*     */     }
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void registerItemIcon(IIconRegister iconRegister) {
/* 154 */       this.iconItem = iconRegister.func_94245_a("buildcrafttransport:gates/gate_logic_" + getTag());
/*     */     }
/*     */     
/*     */     public static GateLogic fromOrdinal(int ordinal) {
/* 158 */       if (ordinal < 0 || ordinal >= VALUES.length) {
/* 159 */         return AND;
/*     */       }
/* 161 */       return VALUES[ordinal];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gates\GateDefinition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */