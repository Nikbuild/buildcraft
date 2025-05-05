/*    */ package buildcraft.core.lib.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.buttons.GuiBetterButton;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.FontRenderer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class GuiTools
/*    */ {
/*    */   public static void drawCenteredString(FontRenderer fr, String s, int y) {
/* 26 */     drawCenteredString(fr, s, y, 176);
/*    */   }
/*    */   
/*    */   public static void drawCenteredString(FontRenderer fr, String s, int y, int guiWidth) {
/* 30 */     drawCenteredString(fr, s, y, guiWidth, 4210752, false);
/*    */   }
/*    */   
/*    */   public static void drawCenteredString(FontRenderer fr, String s, int y, int guiWidth, int color, boolean shadow) {
/* 34 */     int sWidth = fr.func_78256_a(s);
/* 35 */     int sPos = guiWidth / 2 - sWidth / 2;
/* 36 */     fr.func_85187_a(s, sPos, y, color, shadow);
/*    */   }
/*    */   
/*    */   public static void newButtonRowAuto(List<GuiBetterButton> buttonList, int xStart, int xSize, List<? extends GuiBetterButton> buttons) {
/* 40 */     int buttonWidth = 0;
/* 41 */     for (GuiBetterButton b : buttons) {
/* 42 */       buttonWidth += b.getWidth();
/*    */     }
/* 44 */     int remaining = xSize - buttonWidth;
/* 45 */     int spacing = remaining / (buttons.size() + 1);
/* 46 */     int pointer = 0;
/* 47 */     for (GuiBetterButton b : buttons) {
/* 48 */       pointer += spacing;
/* 49 */       b.field_146128_h = xStart + pointer;
/* 50 */       pointer += b.getWidth();
/* 51 */       buttonList.add(b);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void newButtonRow(List<GuiBetterButton> buttonList, int xStart, int spacing, List<? extends GuiBetterButton> buttons) {
/* 56 */     int pointer = 0;
/* 57 */     for (GuiBetterButton b : buttons) {
/* 58 */       b.field_146128_h = xStart + pointer;
/* 59 */       pointer += b.getWidth() + spacing;
/* 60 */       buttonList.add(b);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\GuiTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */