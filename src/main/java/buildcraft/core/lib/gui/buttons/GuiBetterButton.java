/*     */ package buildcraft.core.lib.gui.buttons;
/*     */ 
/*     */ import buildcraft.core.lib.gui.tooltips.IToolTipProvider;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GuiBetterButton
/*     */   extends GuiButton
/*     */   implements IToolTipProvider
/*     */ {
/*     */   protected final IButtonTextureSet texture;
/*     */   private ToolTip toolTip;
/*     */   
/*     */   public GuiBetterButton(int id, int x, int y, String label) {
/*  29 */     this(id, x, y, 200, StandardButtonTextureSets.LARGE_BUTTON, label);
/*     */   }
/*     */   
/*     */   public GuiBetterButton(int id, int x, int y, int width, String label) {
/*  33 */     this(id, x, y, width, StandardButtonTextureSets.LARGE_BUTTON, label);
/*     */   }
/*     */   
/*     */   public GuiBetterButton(int id, int x, int y, int width, IButtonTextureSet texture, String label) {
/*  37 */     super(id, x, y, width, texture.getHeight(), label);
/*  38 */     this.texture = texture;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  42 */     return this.field_146120_f;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/*  46 */     return this.texture.getHeight();
/*     */   }
/*     */   
/*     */   public int getTextColor(boolean mouseOver) {
/*  50 */     if (!this.field_146124_l)
/*  51 */       return -6250336; 
/*  52 */     if (mouseOver) {
/*  53 */       return 16777120;
/*     */     }
/*  55 */     return 14737632;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseOverButton(int mouseX, int mouseY) {
/*  60 */     return (mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + getHeight());
/*     */   }
/*     */   
/*     */   protected void bindButtonTextures(Minecraft minecraft) {
/*  64 */     minecraft.field_71446_o.func_110577_a(this.texture.getTexture());
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146112_a(Minecraft minecraft, int mouseX, int mouseY) {
/*  69 */     if (!this.field_146125_m) {
/*     */       return;
/*     */     }
/*     */     
/*  73 */     FontRenderer fontrenderer = minecraft.field_71466_p;
/*  74 */     bindButtonTextures(minecraft);
/*  75 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  76 */     int xOffset = this.texture.getX();
/*  77 */     int yOffset = this.texture.getY();
/*  78 */     int h = this.texture.getHeight();
/*  79 */     int w = this.texture.getWidth();
/*  80 */     boolean mouseOver = isMouseOverButton(mouseX, mouseY);
/*  81 */     int hoverState = func_146114_a(mouseOver);
/*  82 */     func_73729_b(this.field_146128_h, this.field_146129_i, xOffset, yOffset + hoverState * h, this.field_146120_f / 2, h);
/*  83 */     func_73729_b(this.field_146128_h + this.field_146120_f / 2, this.field_146129_i, xOffset + w - this.field_146120_f / 2, yOffset + hoverState * h, this.field_146120_f / 2, h);
/*  84 */     func_146119_b(minecraft, mouseX, mouseY);
/*  85 */     func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (h - 8) / 2, getTextColor(mouseOver));
/*     */   }
/*     */ 
/*     */   
/*     */   public ToolTip getToolTip() {
/*  90 */     return this.toolTip;
/*     */   }
/*     */   
/*     */   public GuiBetterButton setToolTip(ToolTip tips) {
/*  94 */     this.toolTip = tips;
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isToolTipVisible() {
/* 100 */     return this.field_146125_m;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseOver(int mouseX, int mouseY) {
/* 105 */     return isMouseOverButton(mouseX, mouseY);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\buttons\GuiBetterButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */