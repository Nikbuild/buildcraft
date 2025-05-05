/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
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
/*     */ public class PipeIconProvider
/*     */   implements IIconProvider
/*     */ {
/*     */   public enum TYPE
/*     */   {
/*  24 */     PipeStructureCobblestone("pipeStructureCobblestone"),
/*     */     
/*  26 */     PipeItemsCobbleStone("pipeItemsCobblestone"),
/*     */     
/*  28 */     PipeItemsDiamond_Item("pipeItemsDiamond_item"),
/*  29 */     PipeItemsDiamond_Center("pipeItemsDiamond_center"),
/*  30 */     PipeItemsDiamond_Down("pipeItemsDiamond_down"),
/*  31 */     PipeItemsDiamond_Up("pipeItemsDiamond_up"),
/*  32 */     PipeItemsDiamond_North("pipeItemsDiamond_north"),
/*  33 */     PipeItemsDiamond_South("pipeItemsDiamond_south"),
/*  34 */     PipeItemsDiamond_West("pipeItemsDiamond_west", "pipeItemsDiamond_west_cb"),
/*  35 */     PipeItemsDiamond_East("pipeItemsDiamond_east"),
/*     */     
/*  37 */     PipeItemsLapis_Black("pipeItemsLapis_black"),
/*  38 */     PipeItemsLapis_Red("pipeItemsLapis_red"),
/*  39 */     PipeItemsLapis_Green("pipeItemsLapis_green"),
/*  40 */     PipeItemsLapis_Brown("pipeItemsLapis_brown"),
/*  41 */     PipeItemsLapis_Blue("pipeItemsLapis_blue"),
/*  42 */     PipeItemsLapis_Purple("pipeItemsLapis_purple"),
/*  43 */     PipeItemsLapis_Cyan("pipeItemsLapis_cyan"),
/*  44 */     PipeItemsLapis_LightGray("pipeItemsLapis_lightgray"),
/*  45 */     PipeItemsLapis_Gray("pipeItemsLapis_gray"),
/*  46 */     PipeItemsLapis_Pink("pipeItemsLapis_pink"),
/*  47 */     PipeItemsLapis_Lime("pipeItemsLapis_lime"),
/*  48 */     PipeItemsLapis_Yellow("pipeItemsLapis_yellow"),
/*  49 */     PipeItemsLapis_LightBlue("pipeItemsLapis_lightblue"),
/*  50 */     PipeItemsLapis_Magenta("pipeItemsLapis_magenta"),
/*  51 */     PipeItemsLapis_Orange("pipeItemsLapis_orange"),
/*  52 */     PipeItemsLapis_White("pipeItemsLapis_white"),
/*     */     
/*  54 */     PipeItemsDaizuli_Black("pipeItemsDaizuli_black"),
/*  55 */     PipeItemsDaizuli_Red("pipeItemsDaizuli_red"),
/*  56 */     PipeItemsDaizuli_Green("pipeItemsDaizuli_green"),
/*  57 */     PipeItemsDaizuli_Brown("pipeItemsDaizuli_brown"),
/*  58 */     PipeItemsDaizuli_Blue("pipeItemsDaizuli_blue"),
/*  59 */     PipeItemsDaizuli_Purple("pipeItemsDaizuli_purple"),
/*  60 */     PipeItemsDaizuli_Cyan("pipeItemsDaizuli_cyan"),
/*  61 */     PipeItemsDaizuli_LightGray("pipeItemsDaizuli_lightgray"),
/*  62 */     PipeItemsDaizuli_Gray("pipeItemsDaizuli_gray"),
/*  63 */     PipeItemsDaizuli_Pink("pipeItemsDaizuli_pink"),
/*  64 */     PipeItemsDaizuli_Lime("pipeItemsDaizuli_lime"),
/*  65 */     PipeItemsDaizuli_Yellow("pipeItemsDaizuli_yellow"),
/*  66 */     PipeItemsDaizuli_LightBlue("pipeItemsDaizuli_lightblue"),
/*  67 */     PipeItemsDaizuli_Magenta("pipeItemsDaizuli_magenta"),
/*  68 */     PipeItemsDaizuli_Orange("pipeItemsDaizuli_orange"),
/*  69 */     PipeItemsDaizuli_White("pipeItemsDaizuli_white"),
/*  70 */     PipeAllDaizuli_Solid("pipeAllDaizuli_solid"),
/*     */     
/*  72 */     PipeItemsWood_Standard("pipeItemsWood_standard"),
/*  73 */     PipeAllWood_Solid("pipeAllWood_solid"),
/*     */     
/*  75 */     PipeItemsEmerald_Standard("pipeItemsEmerald_standard"),
/*  76 */     PipeAllEmerald_Solid("pipeAllEmerald_solid"),
/*     */     
/*  78 */     PipeItemsEmzuli_Standard("pipeItemsEmzuli_standard"),
/*  79 */     PipeAllEmzuli_Solid("pipeAllEmzuli_solid"),
/*     */     
/*  81 */     PipeItemsGold("pipeItemsGold"),
/*     */     
/*  83 */     PipeItemsIron_Standard("pipeItemsIron_standard"),
/*  84 */     PipeAllIron_Solid("pipeAllIron_solid"),
/*     */     
/*  86 */     PipeItemsObsidian("pipeItemsObsidian"),
/*  87 */     PipeItemsSandstone("pipeItemsSandstone"),
/*  88 */     PipeItemsStone("pipeItemsStone"),
/*  89 */     PipeItemsQuartz("pipeItemsQuartz"),
/*  90 */     PipeItemsClay("pipeItemsClay"),
/*  91 */     PipeItemsVoid("pipeItemsVoid"),
/*     */     
/*  93 */     PipeFluidsCobblestone("pipeFluidsCobblestone"),
/*  94 */     PipeFluidsWood_Standard("pipeFluidsWood_standard"),
/*  95 */     PipeFluidsEmerald_Standard("pipeFluidsEmerald_standard"),
/*  96 */     PipeFluidsQuartz("pipeFluidsQuartz"),
/*  97 */     PipeFluidsGold("pipeFluidsGold"),
/*  98 */     PipeFluidsIron_Standard("pipeFluidsIron_standard"),
/*  99 */     PipeFluidsSandstone("pipeFluidsSandstone"),
/* 100 */     PipeFluidsStone("pipeFluidsStone"),
/* 101 */     PipeFluidsVoid("pipeFluidsVoid"),
/* 102 */     PipeFluidsClay("pipeFluidsClay"),
/*     */     
/* 104 */     PipeFluidsDiamond_Item("pipeFluidsDiamond_item"),
/* 105 */     PipeFluidsDiamond_Center("pipeFluidsDiamond_center"),
/* 106 */     PipeFluidsDiamond_Down("pipeFluidsDiamond_down"),
/* 107 */     PipeFluidsDiamond_Up("pipeFluidsDiamond_up"),
/* 108 */     PipeFluidsDiamond_North("pipeFluidsDiamond_north"),
/* 109 */     PipeFluidsDiamond_South("pipeFluidsDiamond_south"),
/* 110 */     PipeFluidsDiamond_West("pipeFluidsDiamond_west", "pipeFluidsDiamond_west_cb"),
/* 111 */     PipeFluidsDiamond_East("pipeFluidsDiamond_east"),
/*     */     
/* 113 */     PipePowerDiamond("pipePowerDiamond"),
/* 114 */     PipePowerGold("pipePowerGold"),
/* 115 */     PipePowerQuartz("pipePowerQuartz"),
/* 116 */     PipePowerStone("pipePowerStone"),
/* 117 */     PipePowerSandstone("pipePowerSandstone"),
/* 118 */     PipePowerCobblestone("pipePowerCobblestone"),
/* 119 */     PipePowerWood_Standard("pipePowerWood_standard"),
/* 120 */     PipePowerEmerald_Standard("pipePowerEmerald_standard"),
/*     */     
/* 122 */     PipePowerIronM2("pipePowerIronM2"),
/* 123 */     PipePowerIronM4("pipePowerIronM4"),
/* 124 */     PipePowerIronM8("pipePowerIronM8"),
/* 125 */     PipePowerIronM16("pipePowerIronM16"),
/* 126 */     PipePowerIronM32("pipePowerIronM32"),
/* 127 */     PipePowerIronM64("pipePowerIronM64"),
/* 128 */     PipePowerIronM128("pipePowerIronM128"),
/*     */     
/* 130 */     PipeRobotStation("pipeRobotStation", "pipeRobotStation_cb"),
/* 131 */     PipeRobotStationReserved("pipeRobotStationReserved", "pipeRobotStationReserved_cb"),
/* 132 */     PipeRobotStationLinked("pipeRobotStationLinked", "pipeRobotStationLinked_cb"),
/*     */     
/* 134 */     Power_Normal("core:misc/texture_cyan"),
/* 135 */     Power_Overload("core:misc/texture_red_lit"),
/* 136 */     Stripes("pipeStripes"),
/*     */     
/* 138 */     PipeStainedOverlay("pipeStainedOverlay"),
/* 139 */     PipeLens("pipeLens"),
/* 140 */     PipeFilter("pipeFilter"),
/* 141 */     PipeLensClearOverlay("pipeLensClearOverlay"),
/* 142 */     PipeLensOverlay("pipeLensOverlay"),
/* 143 */     PipePlug("pipePlug"),
/*     */     
/* 145 */     TransparentFacade("transparent_facade"),
/* 146 */     Transparent("core:misc/transparent"),
/*     */     
/* 148 */     PipePowerAdapterSide("pipePowerAdapterSide"),
/* 149 */     PipePowerAdapterBottom("pipePowerAdapterBottom"),
/* 150 */     PipePowerAdapterTop("pipePowerAdapterTop"),
/*     */     
/* 152 */     ItemBox("itemBox");
/* 153 */     public static final TYPE[] VALUES = values();
/*     */     
/*     */     private final String iconTag;
/*     */     private final String iconTagColorBlind;
/*     */     
/*     */     TYPE(String iconTag, String iconTagColorBlind) {
/* 159 */       this.iconTag = iconTag;
/* 160 */       this.iconTagColorBlind = iconTagColorBlind;
/*     */     }
/*     */ 
/*     */     
/*     */     private IIcon icon;
/*     */ 
/*     */     
/*     */     private void registerIcon(IIconRegister iconRegister) {
/* 168 */       String name = BuildCraftCore.colorBlindMode ? this.iconTagColorBlind : this.iconTag;
/* 169 */       if (!name.contains(":")) {
/* 170 */         name = "transport:pipes/" + name;
/*     */       }
/* 172 */       this.icon = iconRegister.func_94245_a("buildcraft" + name);
/*     */     } static {
/*     */     
/*     */     } public IIcon getIcon() {
/* 176 */       return this.icon;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getIcon(int pipeIconIndex) {
/* 183 */     if (pipeIconIndex == -1) {
/* 184 */       return null;
/*     */     }
/* 186 */     return (TYPE.VALUES[pipeIconIndex]).icon;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerIcons(IIconRegister iconRegister) {
/* 192 */     for (TYPE type : TYPE.VALUES)
/* 193 */       type.registerIcon(iconRegister); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipeIconProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */