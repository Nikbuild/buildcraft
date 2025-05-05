/*    */ package buildcraft.core.lib.render;
/*    */ 
/*    */ import buildcraft.api.core.render.ITextureStateManager;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.util.IIcon;
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
/*    */ @SideOnly(Side.CLIENT)
/*    */ public final class TextureStateManager
/*    */   implements ITextureStateManager
/*    */ {
/*    */   private IIcon currentTexture;
/*    */   private IIcon[] textureArray;
/*    */   private IIcon[] textureArrayCache;
/*    */   
/*    */   public TextureStateManager(IIcon placeholder) {
/* 31 */     this.currentTexture = placeholder;
/* 32 */     this.textureArrayCache = new IIcon[6];
/*    */   }
/*    */   
/*    */   public IIcon[] popArray() {
/* 36 */     this.textureArray = this.textureArrayCache;
/* 37 */     return this.textureArrayCache;
/*    */   }
/*    */   
/*    */   public void pushArray() {
/* 41 */     this.textureArray = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon getTexture() {
/* 46 */     return this.currentTexture;
/*    */   }
/*    */   
/*    */   public IIcon[] getTextureArray() {
/* 50 */     return this.textureArray;
/*    */   }
/*    */   
/*    */   public boolean isSided() {
/* 54 */     return (this.textureArray != null);
/*    */   }
/*    */   
/*    */   public void set(IIcon icon) {
/* 58 */     this.currentTexture = icon;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\render\TextureStateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */