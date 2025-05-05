/*    */ package buildcraft.core.tablet;
/*    */ 
/*    */ import buildcraft.api.tablet.ITablet;
/*    */ import buildcraft.api.tablet.TabletBitmap;
/*    */ import buildcraft.api.tablet.TabletProgram;
/*    */ import buildcraft.core.tablet.utils.TabletDrawUtils;
/*    */ import buildcraft.core.tablet.utils.TabletFont;
/*    */ import buildcraft.core.tablet.utils.TabletFontManager;
/*    */ import buildcraft.core.tablet.utils.TabletTextUtils;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ 
/*    */ public class TabletProgramMenu
/*    */   extends TabletProgram {
/*    */   private final ITablet tablet;
/*    */   private boolean init = false;
/* 16 */   private float t = 0.0F;
/*    */   
/*    */   public TabletProgramMenu(ITablet tablet) {
/* 19 */     this.tablet = tablet;
/*    */   }
/*    */   
/*    */   public void tick(float time) {
/* 23 */     this.t += time;
/* 24 */     if (!this.init && this.t > 2.0F && this.tablet.getSide() == Side.CLIENT) {
/* 25 */       TabletBitmap bitmap = new TabletBitmap(244, 306);
/*    */       try {
/* 27 */         TabletFont font = TabletFontManager.INSTANCE.register("DejaVu11", TabletProgramMenu.class.getClassLoader().getResourceAsStream("assets/buildcraftcore/tablet/11.pf2"));
/* 28 */         String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque condimentum, nulla in tincidunt finibus, dolor enim condimentum felis, vitae vulputate lorem turpis nec purus. Nulla iaculis sed turpis in lacinia. Aliquam consectetur leo sit amet placerat blandit. Maecenas euismod magna eros, quis efficitur quam luctus mollis. Nulla facilisi. Quisque tempor turpis ipsum, ut auctor diam hendrerit dictum. Phasellus id viverra purus. Ut sagittis felis eu odio sagittis, vitae mollis felis feugiat. Morbi mi elit, varius id fringilla vel, vehicula ac risus. Curabitur aliquam orci at mollis posuere.";
/* 29 */         TabletDrawUtils.drawRect(bitmap, 4, 4, 236, 280, 7);
/* 30 */         int hxo = (244 - font.getStringWidth("Page 1")) / 2;
/* 31 */         font.draw(bitmap, "Page 2/4", hxo, 287, 5);
/* 32 */         int xo = 8;
/* 33 */         int y = 8;
/* 34 */         int w = 228;
/* 35 */         String[] lines = TabletTextUtils.split(lorem, font, w, false);
/* 36 */         for (int i = 0; i < lines.length; i++) {
/* 37 */           String line = lines[i];
/* 38 */           String[] words = line.split(" ");
/* 39 */           float justifyValue = 0.0F;
/* 40 */           if (i < lines.length - 1) {
/* 41 */             int widthNoSpaces = 0;
/* 42 */             for (String s : words) {
/* 43 */               widthNoSpaces += font.getStringWidth(s);
/*    */             }
/* 45 */             justifyValue = (w - widthNoSpaces) / (words.length - 1);
/*    */           } 
/* 47 */           float x = xo;
/* 48 */           for (String s : words) {
/* 49 */             x += font.draw(bitmap, s, (int)x, y, 7);
/* 50 */             x += justifyValue;
/*    */           } 
/* 52 */           y += font.getHeight() + 1;
/*    */         } 
/* 54 */       } catch (Exception e) {
/* 55 */         e.printStackTrace();
/*    */       } 
/* 57 */       this.tablet.refreshScreen(bitmap);
/*    */       
/* 59 */       this.init = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\tablet\TabletProgramMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */