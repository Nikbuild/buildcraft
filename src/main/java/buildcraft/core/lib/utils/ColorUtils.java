/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.oredict.OreDictionary;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ColorUtils
/*    */ {
/* 18 */   private static final int[] WOOL_TO_RGB = new int[] { 16448250, 14188339, 11685080, 6724056, 15066419, 8375321, 15892389, 5000268, 10066329, 5013401, 8339378, 3361970, 6704179, 6717235, 10040115, 1644825 };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   private static final String[] WOOL_TO_NAME = new String[] { "white", "orange", "magenta", "light.blue", "yellow", "lime", "pink", "gray", "light.gray", "cyan", "purple", "blue", "brown", "green", "red", "black" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   private static final String[] OREDICT_DYE_NAMES = new String[] { "dyeWhite", "dyeOrange", "dyeMagenta", "dyeLightBlue", "dyeYellow", "dyeLime", "dyePink", "dyeGray", "dyeLightGray", "dyeCyan", "dyePurple", "dyeBlue", "dyeBrown", "dyeGreen", "dyeRed", "dyeBlack" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   private static final int[] OREDICT_DYE_IDS = new int[16];
/*    */   
/* 41 */   private static final char[] WOOL_TO_CHAT = new char[] { 'f', '6', 'd', '9', 'e', 'a', 'd', '8', '7', '3', '5', '1', '6', '2', '4', '0' };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void initialize() {
/* 51 */     for (int i = 0; i < 16; i++) {
/* 52 */       OREDICT_DYE_IDS[i] = OreDictionary.getOreID(OREDICT_DYE_NAMES[i]);
/*    */     }
/*    */   }
/*    */   
/*    */   public static int getColorIDFromDye(ItemStack stack) {
/* 57 */     if (stack == null || stack.func_77973_b() == null) {
/* 58 */       return -1;
/*    */     }
/*    */     
/* 61 */     if (stack.func_77973_b() == Items.field_151100_aR) {
/* 62 */       return 15 - stack.func_77960_j();
/*    */     }
/*    */     
/* 65 */     int[] itemOreIDs = OreDictionary.getOreIDs(stack);
/* 66 */     for (int i = 0; i < 16; i++) {
/* 67 */       for (int id : itemOreIDs) {
/* 68 */         if (i == id) {
/* 69 */           return i;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 74 */     return -1;
/*    */   }
/*    */   
/*    */   public static boolean isDye(ItemStack stack) {
/* 78 */     return (getColorIDFromDye(stack) >= 0);
/*    */   }
/*    */   
/*    */   public static int getRGBColor(int wool) {
/* 82 */     return WOOL_TO_RGB[wool & 0xF];
/*    */   }
/*    */   
/*    */   public static String getName(int wool) {
/* 86 */     return WOOL_TO_NAME[wool & 0xF];
/*    */   }
/*    */   
/*    */   public static String getOreDictionaryName(int wool) {
/* 90 */     return OREDICT_DYE_NAMES[wool & 0xF];
/*    */   }
/*    */   
/*    */   public static String getFormatting(int wool) {
/* 94 */     return "§" + WOOL_TO_CHAT[wool & 0xF];
/*    */   }
/*    */   
/*    */   public static String getFormattingTooltip(int wool) {
/* 98 */     return "§" + ((WOOL_TO_CHAT[wool & 0xF] == '0') ? 56 : WOOL_TO_CHAT[wool & 0xF]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\ColorUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */