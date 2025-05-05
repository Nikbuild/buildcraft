/*    */ package buildcraft.core.lib.gui;
/*    */ 
/*    */ import buildcraft.core.lib.render.FluidRenderer;
/*    */ import buildcraft.core.lib.render.RenderUtils;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.Fluid;
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
/*    */ public class FluidSlot
/*    */   extends AdvancedSlot
/*    */ {
/*    */   public Fluid fluid;
/*    */   public int colorRenderCache;
/*    */   
/*    */   public FluidSlot(GuiAdvancedInterface gui, int x, int y) {
/* 29 */     super(gui, x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawSprite(int cornerX, int cornerY) {
/* 34 */     if (this.fluid != null) {
/* 35 */       RenderUtils.setGLColorFromInt(this.colorRenderCache);
/*    */     }
/* 37 */     super.drawSprite(cornerX, cornerY);
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon getIcon() {
/* 42 */     return FluidRenderer.getFluidTexture(this.fluid, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTexture() {
/* 47 */     return TextureMap.field_110575_b;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\FluidSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */