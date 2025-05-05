/*    */ package buildcraft.core.lib.gui.widgets;
/*    */ 
/*    */ import buildcraft.core.lib.fluids.Tank;
/*    */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*    */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*    */ import buildcraft.core.lib.render.FluidRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraftforge.fluids.FluidStack;
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
/*    */ public class FluidGaugeWidget
/*    */   extends Widget
/*    */ {
/*    */   public final Tank tank;
/*    */   
/*    */   public FluidGaugeWidget(Tank tank, int x, int y, int u, int v, int w, int h) {
/* 26 */     super(x, y, u, v, w, h);
/* 27 */     this.tank = tank;
/*    */   }
/*    */ 
/*    */   
/*    */   public ToolTip getToolTip() {
/* 32 */     return this.tank.getToolTip();
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(GuiBuildCraft gui, int guiX, int guiY, int mouseX, int mouseY) {
/* 37 */     if (this.tank == null) {
/*    */       return;
/*    */     }
/* 40 */     FluidStack fluidStack = this.tank.getFluid();
/* 41 */     if (fluidStack == null || fluidStack.amount <= 0 || fluidStack.getFluid() == null) {
/*    */       return;
/*    */     }
/*    */     
/* 45 */     IIcon liquidIcon = FluidRenderer.getFluidTexture(fluidStack, false);
/*    */     
/* 47 */     if (liquidIcon == null) {
/*    */       return;
/*    */     }
/*    */     
/* 51 */     float scale = Math.min(fluidStack.amount, this.tank.getCapacity()) / this.tank.getCapacity();
/*    */     
/* 53 */     gui.bindTexture(TextureMap.field_110575_b);
/*    */     
/* 55 */     for (int col = 0; col < this.w / 16; col++) {
/* 56 */       for (int row = 0; row <= this.h / 16; row++) {
/* 57 */         gui.func_94065_a(guiX + this.x + col * 16, guiY + this.y + row * 16 - 1, liquidIcon, 16, 16);
/*    */       }
/*    */     } 
/*    */     
/* 61 */     gui.bindTexture(gui.texture);
/*    */     
/* 63 */     gui.func_73729_b(guiX + this.x, guiY + this.y - 1, this.x, this.y - 1, this.w, this.h - (int)Math.floor((this.h * scale)) + 1);
/* 64 */     gui.func_73729_b(guiX + this.x, guiY + this.y, this.u, this.v, this.w, this.h);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\widgets\FluidGaugeWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */