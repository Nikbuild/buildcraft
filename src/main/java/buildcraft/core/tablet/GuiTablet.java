/*     */ package buildcraft.core.tablet;
/*     */ 
/*     */ import buildcraft.api.tablet.TabletBitmap;
/*     */ import buildcraft.core.lib.render.DynamicTextureBC;
/*     */ import buildcraft.core.tablet.manager.TabletManagerClient;
/*     */ import buildcraft.core.tablet.manager.TabletThread;
/*     */ import java.util.Date;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiTablet
/*     */   extends GuiScreen
/*     */ {
/*     */   private static final boolean ENABLE_HIGHLIGHT = false;
/*  22 */   private static final int[] PALETTE = new int[] { 0, 469762048, 805306368, 1207959552, 1610612736, 2013265920, -1711276032, -1140850688 };
/*     */ 
/*     */ 
/*     */   
/*  26 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftcore", "textures/gui/tablet.png");
/*     */   
/*     */   private static final int X_SIZE = 142;
/*     */   
/*     */   private static final int Y_SIZE = 180;
/*     */   
/*     */   private final DynamicTextureBC display;
/*     */   private final TabletThread tabletThread;
/*  34 */   private float glScale = 1.0F; private final TabletClient tablet; private int guiLeft; private int guiTop; private long lastDate;
/*  35 */   private int buttonState = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiTablet(EntityPlayer player) {
/*  40 */     this.tabletThread = TabletManagerClient.INSTANCE.get();
/*  41 */     this.tablet = (TabletClient)this.tabletThread.getTablet();
/*  42 */     this.lastDate = (new Date()).getTime();
/*  43 */     this.display = new DynamicTextureBC(this.tablet.getScreenWidth(), this.tablet.getScreenHeight());
/*     */     
/*  45 */     this.tablet.updateGui(0.0F, this, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  55 */     super.func_73866_w_();
/*     */     
/*  57 */     int oldScale = this.field_146297_k.field_71474_y.field_74335_Z;
/*  58 */     ScaledResolution realRes = new ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
/*  59 */     this.field_146297_k.field_71474_y.field_74335_Z = (realRes.func_78325_e() == 1) ? 2 : (realRes.func_78325_e() & 0xFFFFFFFE);
/*  60 */     ScaledResolution currentRes = new ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
/*  61 */     this.field_146297_k.field_71474_y.field_74335_Z = oldScale;
/*     */     
/*  63 */     this.glScale = (float)(currentRes.func_78327_c() / realRes.func_78327_c());
/*     */     
/*  65 */     this.guiLeft = (currentRes.func_78326_a() - 142) / 2;
/*  66 */     this.guiTop = (currentRes.func_78328_b() - 180) / 2;
/*     */   }
/*     */   
/*     */   public void bindTexture(ResourceLocation texture) {
/*  70 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(texture);
/*     */   }
/*     */   
/*     */   public void copyDisplay(TabletBitmap display) {
/*  74 */     for (int j = 0; j < display.height; j++) {
/*  75 */       for (int i = 0; i < display.width; i++) {
/*  76 */         this.display.setColor(i, j, PALETTE[display.get(i, j) & 0x7]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/*  83 */     long date = (new Date()).getTime();
/*  84 */     float time = (float)(date - this.lastDate) / 1000.0F;
/*  85 */     this.tabletThread.tick(time);
/*  86 */     this.lastDate = date;
/*  87 */     this.tablet.updateGui(time, this, false);
/*     */   }
/*     */   
/*     */   private boolean isButton(int mx, int my) {
/*  91 */     return (mx >= this.guiLeft + 65 && my >= this.guiTop + 167 && mx < this.guiLeft + 65 + 18 && my < this.guiTop + 167 + 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146274_d() {
/*  96 */     int x = (int)((Mouse.getEventX() * this.field_146294_l / this.field_146297_k.field_71443_c) * this.glScale);
/*  97 */     int y = (int)((this.field_146295_m - Mouse.getEventY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1) * this.glScale);
/*  98 */     int k = Mouse.getEventButton();
/*     */     
/* 100 */     if (k == 0) {
/* 101 */       if (Mouse.getEventButtonState()) {
/* 102 */         if (isButton(x, y)) {
/* 103 */           this.buttonState = 2;
/*     */         }
/* 105 */       } else if (this.buttonState == 2) {
/* 106 */         if (isButton(x, y)) {
/* 107 */           this.buttonState = 1;
/*     */         } else {
/* 109 */           this.buttonState = 1;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int fmx, int fmy, float p) {
/* 123 */     func_146276_q_();
/*     */     
/* 125 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 126 */     GL11.glPushMatrix();
/* 127 */     GL11.glScalef(1.0F / this.glScale, 1.0F / this.glScale, 1.0F / this.glScale);
/*     */     
/* 129 */     bindTexture(TEXTURE);
/* 130 */     func_73729_b(this.guiLeft, this.guiTop, 0, 0, 142, 180);
/* 131 */     func_73729_b(this.guiLeft + 65, this.guiTop + 167, 142, 147 + this.buttonState * 10, 18, 8);
/*     */     
/* 133 */     GL11.glPushAttrib(8192);
/* 134 */     GL11.glEnable(3042);
/* 135 */     GL11.glTexParameteri(3553, 10241, 9729);
/*     */     
/* 137 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 138 */     this.display.draw((this.guiLeft + 10 + 1) * 2, (this.guiTop + 8 + 1) * 2, this.field_73735_i);
/*     */     
/* 140 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 141 */     GL11.glDisable(3042);
/* 142 */     GL11.glPopAttrib();
/*     */     
/* 144 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\tablet\GuiTablet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */