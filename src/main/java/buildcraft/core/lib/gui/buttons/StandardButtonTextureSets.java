/*    */ package buildcraft.core.lib.gui.buttons;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum StandardButtonTextureSets
/*    */   implements IButtonTextureSet
/*    */ {
/* 14 */   LARGE_BUTTON(0, 0, 20, 200),
/* 15 */   SMALL_BUTTON(0, 80, 15, 200),
/* 16 */   LEFT_BUTTON(204, 0, 16, 10),
/* 17 */   RIGHT_BUTTON(214, 0, 16, 10); public static final ResourceLocation BUTTON_TEXTURES; private final int x; static {
/* 18 */     BUTTON_TEXTURES = new ResourceLocation("buildcraftcore:textures/gui/buttons.png");
/*    */   }
/*    */   private final int y; private final int height; private final int width;
/*    */   StandardButtonTextureSets(int x, int y, int height, int width) {
/* 22 */     this.x = x;
/* 23 */     this.y = y;
/* 24 */     this.height = height;
/* 25 */     this.width = width;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getX() {
/* 30 */     return this.x;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getY() {
/* 35 */     return this.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 40 */     return this.height;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 45 */     return this.width;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTexture() {
/* 50 */     return BUTTON_TEXTURES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\buttons\StandardButtonTextureSets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */