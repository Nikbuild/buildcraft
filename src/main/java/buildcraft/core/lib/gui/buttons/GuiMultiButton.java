/*    */ package buildcraft.core.lib.gui.buttons;
/*    */ 
/*    */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import org.lwjgl.opengl.GL11;
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
/*    */ public class GuiMultiButton
/*    */   extends GuiBetterButton
/*    */ {
/*    */   private final MultiButtonController<?> control;
/*    */   
/*    */   public GuiMultiButton(int id, int x, int y, int width, MultiButtonController<?> control) {
/* 27 */     super(id, x, y, width, StandardButtonTextureSets.LARGE_BUTTON, "");
/* 28 */     this.control = control;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 33 */     return this.texture.getHeight();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_146112_a(Minecraft minecraft, int x, int y) {
/* 38 */     if (!this.field_146125_m) {
/*    */       return;
/*    */     }
/*    */     
/* 42 */     FontRenderer fontrenderer = minecraft.field_71466_p;
/* 43 */     bindButtonTextures(minecraft);
/* 44 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 45 */     IMultiButtonState state = (IMultiButtonState)this.control.getButtonState();
/* 46 */     IButtonTextureSet tex = state.getTextureSet();
/* 47 */     int xOffset = tex.getX();
/* 48 */     int yOffset = tex.getY();
/* 49 */     int h = tex.getHeight();
/* 50 */     int w = tex.getWidth();
/* 51 */     boolean flag = (x >= this.field_146128_h && y >= this.field_146129_i && x < this.field_146128_h + this.field_146120_f && y < this.field_146129_i + h);
/* 52 */     int hoverState = func_146114_a(flag);
/* 53 */     func_73729_b(this.field_146128_h, this.field_146129_i, xOffset, yOffset + hoverState * h, this.field_146120_f / 2, h);
/* 54 */     func_73729_b(this.field_146128_h + this.field_146120_f / 2, this.field_146129_i, xOffset + w - this.field_146120_f / 2, yOffset + hoverState * h, this.field_146120_f / 2, h);
/* 55 */     func_146119_b(minecraft, x, y);
/* 56 */     this.field_146126_j = state.getLabel();
/* 57 */     if (!"".equals(this.field_146126_j)) {
/* 58 */       if (!this.field_146124_l) {
/* 59 */         func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (h - 8) / 2, -6250336);
/* 60 */       } else if (flag) {
/* 61 */         func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (h - 8) / 2, 16777120);
/*    */       } else {
/* 63 */         func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (h - 8) / 2, 14737632);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_146116_c(Minecraft par1Minecraft, int par2, int par3) {
/* 70 */     boolean pressed = super.func_146116_c(par1Minecraft, par2, par3);
/* 71 */     if (pressed && this.field_146124_l) {
/* 72 */       this.control.incrementState();
/*    */     }
/* 74 */     return pressed;
/*    */   }
/*    */   
/*    */   public MultiButtonController<?> getController() {
/* 78 */     return this.control;
/*    */   }
/*    */ 
/*    */   
/*    */   public ToolTip getToolTip() {
/* 83 */     ToolTip tip = this.control.getButtonState().getToolTip();
/* 84 */     if (tip != null) {
/* 85 */       return tip;
/*    */     }
/* 87 */     return super.getToolTip();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\buttons\GuiMultiButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */