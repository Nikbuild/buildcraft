/*    */ package buildcraft.core.lib.gui.buttons;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ButtonTextureSet
/*    */   implements IButtonTextureSet
/*    */ {
/*    */   private final ResourceLocation texture;
/*    */   private final int x;
/*    */   private final int y;
/*    */   private final int height;
/*    */   private final int width;
/*    */   
/*    */   public ButtonTextureSet(int x, int y, int height, int width) {
/* 18 */     this(x, y, height, width, StandardButtonTextureSets.BUTTON_TEXTURES);
/*    */   }
/*    */   
/*    */   public ButtonTextureSet(int x, int y, int height, int width, ResourceLocation texture) {
/* 22 */     this.x = x;
/* 23 */     this.y = y;
/* 24 */     this.height = height;
/* 25 */     this.width = width;
/* 26 */     this.texture = texture;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getX() {
/* 31 */     return this.x;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getY() {
/* 36 */     return this.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 41 */     return this.height;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 46 */     return this.width;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTexture() {
/* 51 */     return this.texture;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\buttons\ButtonTextureSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */