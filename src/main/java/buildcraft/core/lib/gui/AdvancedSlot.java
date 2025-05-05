/*     */ package buildcraft.core.lib.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
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
/*     */ 
/*     */ public abstract class AdvancedSlot
/*     */ {
/*  22 */   private static final ResourceLocation TEXTURE_SLOT = new ResourceLocation("buildcraftcore:textures/gui/slot.png");
/*     */   
/*     */   public int x;
/*     */   public int y;
/*     */   public GuiAdvancedInterface gui;
/*     */   public boolean drawBackround = false;
/*     */   
/*     */   public AdvancedSlot(GuiAdvancedInterface gui, int x, int y) {
/*  30 */     this.x = x;
/*  31 */     this.y = y;
/*  32 */     this.gui = gui;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  36 */     return null;
/*     */   }
/*     */   
/*     */   public final void drawTooltip(GuiAdvancedInterface gui, int x, int y) {
/*  40 */     String s = StatCollector.func_74838_a(getDescription());
/*     */     
/*  42 */     if (s != null) {
/*  43 */       gui.drawTooltip(s, x, y);
/*     */     } else {
/*  45 */       ItemStack stack = getItemStack();
/*     */       
/*  47 */       if (stack != null) {
/*  48 */         int cornerX = (gui.field_146294_l - gui.getXSize()) / 2;
/*  49 */         int cornerY = (gui.field_146295_m - gui.getYSize()) / 2;
/*     */         
/*  51 */         int xS = x - cornerX;
/*  52 */         int yS = y - cornerY;
/*     */         
/*  54 */         gui.func_146285_a(stack, xS, yS);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public IIcon getIcon() {
/*  60 */     return null;
/*     */   }
/*     */   
/*     */   public ResourceLocation getTexture() {
/*  64 */     return TextureMap.field_110576_c;
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack() {
/*  68 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isDefined() {
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   public void drawSprite(int cornerX, int cornerY) {
/*  76 */     Minecraft mc = Minecraft.func_71410_x();
/*     */     
/*  78 */     if (this.drawBackround) {
/*  79 */       mc.field_71446_o.func_110577_a(TEXTURE_SLOT);
/*  80 */       this.gui.func_73729_b(cornerX + this.x - 1, cornerY + this.y - 1, 0, 0, 18, 18);
/*     */     } 
/*     */     
/*  83 */     if (!isDefined()) {
/*     */       return;
/*     */     }
/*     */     
/*  87 */     if (getItemStack() != null) {
/*  88 */       drawStack(getItemStack());
/*  89 */     } else if (getIcon() != null) {
/*  90 */       mc.field_71446_o.func_110577_a(getTexture());
/*     */ 
/*     */       
/*  93 */       GL11.glPushAttrib(16448);
/*     */       
/*  95 */       GL11.glDisable(2896);
/*  96 */       GL11.glEnable(3008);
/*  97 */       GL11.glEnable(3042);
/*     */       
/*  99 */       this.gui.func_94065_a(cornerX + this.x, cornerY + this.y, getIcon(), 16, 16);
/*     */       
/* 101 */       GL11.glPopAttrib();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawStack(ItemStack item) {
/* 107 */     int cornerX = (this.gui.field_146294_l - this.gui.getXSize()) / 2;
/* 108 */     int cornerY = (this.gui.field_146295_m - this.gui.getYSize()) / 2;
/*     */     
/* 110 */     this.gui.drawStack(item, cornerX + this.x, cornerY + this.y);
/* 111 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean shouldDrawHighlight() {
/* 115 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\AdvancedSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */