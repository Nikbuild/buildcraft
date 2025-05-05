/*     */ package buildcraft.api.core;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public enum EnumColor
/*     */   implements IStringSerializable
/*     */ {
/*     */   public static final EnumColor[] VALUES;
/*     */   public static final String[] DYES;
/*     */   public static final String[] NAMES;
/*  22 */   BLACK,
/*  23 */   RED,
/*  24 */   GREEN,
/*  25 */   BROWN,
/*  26 */   BLUE,
/*  27 */   PURPLE,
/*  28 */   CYAN,
/*  29 */   LIGHT_GRAY,
/*  30 */   GRAY,
/*  31 */   PINK,
/*  32 */   LIME,
/*  33 */   YELLOW,
/*  34 */   LIGHT_BLUE,
/*  35 */   MAGENTA,
/*  36 */   ORANGE,
/*  37 */   WHITE;
/*     */   static {
/*  39 */     VALUES = values();
/*  40 */     DYES = new String[] { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };
/*     */     
/*  42 */     NAMES = new String[] { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };
/*     */     
/*  44 */     DARK_HEX = new int[] { 2960685, 10696757, 3755038, 6044196, 3424674, 8667071, 3571870, 8947848, 4473924, 15041952, 4172342, 13615665, 8362705, 16737535, 16738816, 16777215 };
/*     */     
/*  46 */     LIGHT_HEX = new int[] { 1578004, 12462887, 32526, 8998957, 2437523, 8271039, 2725785, 10528679, 8026746, 14250393, 3790126, 16767260, 6728447, 14238662, 15366197, 15000804 };
/*     */   }
/*     */   public static final int[] DARK_HEX;
/*     */   public static final int[] LIGHT_HEX;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private static ResourceLocation iconSheet;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private static TextureAtlasSprite[] brushSprites;
/*     */   
/*     */   public int getDarkHex() {
/*  56 */     return DARK_HEX[ordinal()];
/*     */   }
/*     */   
/*     */   public int getLightHex() {
/*  60 */     return LIGHT_HEX[ordinal()];
/*     */   }
/*     */   
/*     */   public static EnumColor fromId(int id) {
/*  64 */     if (id < 0 || id >= VALUES.length) {
/*  65 */       return WHITE;
/*     */     }
/*  67 */     return VALUES[id];
/*     */   }
/*     */   
/*     */   public static EnumColor fromDye(String dyeTag) {
/*  71 */     for (int id = 0; id < DYES.length; id++) {
/*  72 */       if (DYES[id].equals(dyeTag)) {
/*  73 */         return VALUES[id];
/*     */       }
/*     */     } 
/*  76 */     return null;
/*     */   }
/*     */   
/*     */   public static EnumColor fromName(String name) {
/*  80 */     for (int id = 0; id < NAMES.length; id++) {
/*  81 */       if (NAMES[id].equals(name)) {
/*  82 */         return VALUES[id];
/*     */       }
/*     */     } 
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   public static EnumColor getRand() {
/*  89 */     return VALUES[(new Random()).nextInt(VALUES.length)];
/*     */   }
/*     */   
/*     */   public EnumColor getNext() {
/*  93 */     EnumColor next = VALUES[(ordinal() + 1) % VALUES.length];
/*  94 */     return next;
/*     */   }
/*     */   
/*     */   public EnumColor getPrevious() {
/*  98 */     EnumColor previous = VALUES[(ordinal() + VALUES.length - 1) % VALUES.length];
/*  99 */     return previous;
/*     */   }
/*     */   
/*     */   public EnumColor inverse() {
/* 103 */     return VALUES[15 - ordinal()];
/*     */   }
/*     */   
/*     */   public String getTag() {
/* 107 */     return "color." + name().replace("_", ".").toLowerCase(Locale.ENGLISH);
/*     */   }
/*     */   
/*     */   public String getBasicTag() {
/* 111 */     return name().replace("_", ".").toLowerCase(Locale.ENGLISH);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_176610_l() {
/* 116 */     return NAMES[ordinal()];
/*     */   }
/*     */   
/*     */   public String getLocalizedName() {
/* 120 */     return I18n.func_74838_a(getTag());
/*     */   }
/*     */   
/*     */   public String getDye() {
/* 124 */     return DYES[ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 129 */     String s = name().replace("_", " ");
/* 130 */     String[] words = s.split(" ");
/* 131 */     StringBuilder b = new StringBuilder();
/* 132 */     for (String word : words) {
/* 133 */       b.append(word.charAt(0)).append(word.substring(1).toLowerCase(Locale.ENGLISH)).append(" ");
/*     */     }
/* 135 */     return b.toString().trim();
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void registerSprites(TextureAtlasSprite[] sprites) {
/* 140 */     brushSprites = sprites;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public TextureAtlasSprite getSprite() {
/* 145 */     return brushSprites[ordinal()];
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\EnumColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */