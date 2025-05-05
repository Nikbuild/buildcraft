/*     */ package buildcraft.core.lib.gui.buttons;
/*     */ 
/*     */ import buildcraft.core.lib.gui.tooltips.IToolTipProvider;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ public class GuiImageButton
/*     */   extends GuiButton
/*     */   implements IButtonClickEventTrigger, IToolTipProvider
/*     */ {
/*     */   private final int size;
/*     */   private final int u;
/*     */   private final int v;
/*     */   private final int baseU;
/*     */   private final int baseV;
/*     */   private final ResourceLocation texture;
/*  32 */   private ArrayList<IButtonClickEventListener> listeners = new ArrayList<IButtonClickEventListener>();
/*     */   private boolean active = false;
/*     */   private ToolTip toolTip;
/*     */   
/*     */   public GuiImageButton(int id, int x, int y, int size, ResourceLocation texture, int u, int v) {
/*  37 */     this(id, x, y, size, texture, 0, 0, u, v);
/*     */   }
/*     */   
/*     */   public GuiImageButton(int id, int x, int y, int size, ResourceLocation texture, int baseU, int baseV, int u, int v) {
/*  41 */     super(id, x, y, size, size, "");
/*  42 */     this.size = size;
/*  43 */     this.u = u;
/*  44 */     this.v = v;
/*  45 */     this.baseU = baseU;
/*  46 */     this.baseV = baseV;
/*  47 */     this.texture = texture;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  51 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/*  55 */     return this.active;
/*     */   }
/*     */   
/*     */   public void activate() {
/*  59 */     this.active = true;
/*     */   }
/*     */   
/*     */   public void deActivate() {
/*  63 */     this.active = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146112_a(Minecraft minecraft, int x, int y) {
/*  68 */     if (!this.field_146125_m) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     minecraft.field_71446_o.func_110577_a(this.texture);
/*     */     
/*  74 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  75 */     GL11.glEnable(3008);
/*  76 */     GL11.glDisable(3042);
/*     */     
/*  78 */     int buttonState = getButtonState(x, y);
/*     */     
/*  80 */     func_73729_b(this.field_146128_h, this.field_146129_i, this.baseU + buttonState * this.size, this.baseV, this.size, this.size);
/*  81 */     func_73729_b(this.field_146128_h + 1, this.field_146129_i + 1, this.u, this.v, this.size - 2, this.size - 2);
/*     */     
/*  83 */     func_146119_b(minecraft, x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_146116_c(Minecraft par1Minecraft, int par2, int par3) {
/*  88 */     boolean pressed = super.func_146116_c(par1Minecraft, par2, par3);
/*     */     
/*  90 */     if (pressed) {
/*  91 */       this.active = !this.active;
/*  92 */       notifyAllListeners();
/*     */     } 
/*     */     
/*  95 */     return pressed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerListener(IButtonClickEventListener listener) {
/* 100 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeListener(IButtonClickEventListener listener) {
/* 105 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyAllListeners() {
/* 110 */     for (IButtonClickEventListener listener : this.listeners) {
/* 111 */       listener.handleButtonClick(this, this.field_146127_k);
/*     */     }
/*     */   }
/*     */   
/*     */   private int getButtonState(int mouseX, int mouseY) {
/* 116 */     if (!this.field_146124_l) {
/* 117 */       return 0;
/*     */     }
/*     */     
/* 120 */     if (isMouseOverButton(mouseX, mouseY)) {
/* 121 */       if (!this.active) {
/* 122 */         return 2;
/*     */       }
/* 124 */       return 4;
/*     */     } 
/*     */ 
/*     */     
/* 128 */     if (!this.active) {
/* 129 */       return 1;
/*     */     }
/* 131 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isMouseOverButton(int mouseX, int mouseY) {
/* 136 */     return (mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.size && mouseY < this.field_146129_i + this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public ToolTip getToolTip() {
/* 141 */     return this.toolTip;
/*     */   }
/*     */   
/*     */   public GuiImageButton setToolTip(ToolTip tips) {
/* 145 */     this.toolTip = tips;
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isToolTipVisible() {
/* 151 */     return this.field_146125_m;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseOver(int mouseX, int mouseY) {
/* 156 */     return isMouseOverButton(mouseX, mouseY);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\buttons\GuiImageButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */