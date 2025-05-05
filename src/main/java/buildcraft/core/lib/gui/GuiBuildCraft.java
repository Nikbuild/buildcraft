/*     */ package buildcraft.core.lib.gui;
/*     */ 
/*     */ import buildcraft.core.lib.gui.tooltips.IToolTipProvider;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTipLine;
/*     */ import buildcraft.core.lib.gui.widgets.Widget;
/*     */ import buildcraft.core.lib.render.RenderUtils;
/*     */ import buildcraft.core.lib.utils.SessionVars;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ 
/*     */ 
/*     */ public abstract class GuiBuildCraft
/*     */   extends GuiContainer
/*     */ {
/*  42 */   public static final ResourceLocation LEDGER_TEXTURE = new ResourceLocation("buildcraftcore:textures/gui/ledger.png");
/*  43 */   public final LedgerManager ledgerManager = new LedgerManager(this);
/*     */   public final TileEntity tile;
/*     */   public final BuildCraftContainer container;
/*     */   public ResourceLocation texture;
/*     */   
/*     */   public GuiBuildCraft(BuildCraftContainer container, IInventory inventory, ResourceLocation texture) {
/*  49 */     super(container);
/*  50 */     this.container = container;
/*     */     
/*  52 */     this.texture = texture;
/*     */     
/*  54 */     if (inventory instanceof TileEntity) {
/*  55 */       this.tile = (TileEntity)inventory;
/*     */     } else {
/*  57 */       this.tile = null;
/*     */     } 
/*     */     
/*  60 */     initLedgers(inventory);
/*     */   }
/*     */   
/*     */   public FontRenderer getFontRenderer() {
/*  64 */     return this.field_146289_q;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initLedgers(IInventory inventory) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float par3) {
/*  75 */     super.func_73863_a(mouseX, mouseY, par3);
/*  76 */     int left = this.field_147003_i;
/*  77 */     int top = this.field_147009_r;
/*     */     
/*  79 */     GL11.glDisable(2896);
/*  80 */     GL11.glDisable(2929);
/*  81 */     GL11.glPushMatrix();
/*  82 */     GL11.glTranslatef(left, top, 0.0F);
/*  83 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  84 */     RenderHelper.func_74518_a();
/*     */     
/*  86 */     InventoryPlayer playerInv = this.field_146297_k.field_71439_g.field_71071_by;
/*     */     
/*  88 */     if (playerInv.func_70445_o() == null) {
/*  89 */       drawToolTips(this.container.getWidgets(), mouseX - left, mouseY - top, left, top);
/*  90 */       drawToolTips(this.field_146292_n, mouseX, mouseY, 0, 0);
/*  91 */       drawToolTips(this.field_147002_h.field_75151_b, mouseX, mouseY, 0, 0);
/*     */     } 
/*     */     
/*  94 */     GL11.glPopMatrix();
/*  95 */     GL11.glEnable(2896);
/*  96 */     GL11.glEnable(2929);
/*     */   }
/*     */   
/*     */   private void drawToolTips(Collection<?> objects, int mouseX, int mouseY, int offsetX, int offsetY) {
/* 100 */     for (Object obj : objects) {
/* 101 */       if (!(obj instanceof IToolTipProvider)) {
/*     */         continue;
/*     */       }
/* 104 */       IToolTipProvider provider = (IToolTipProvider)obj;
/* 105 */       if (!provider.isToolTipVisible()) {
/*     */         continue;
/*     */       }
/* 108 */       ToolTip tips = provider.getToolTip();
/* 109 */       if (tips == null) {
/*     */         continue;
/*     */       }
/* 112 */       boolean mouseOver = provider.isMouseOver(mouseX, mouseY);
/* 113 */       tips.onTick(mouseOver);
/* 114 */       if (mouseOver && tips.isReady()) {
/* 115 */         tips.refresh();
/* 116 */         drawToolTips(tips, mouseX + offsetX, mouseY + offsetY);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public void drawFluid(FluidStack fluid, int x, int y, int width, int height, int maxCapacity) {
/*     */     TextureAtlasSprite textureAtlasSprite;
/* 122 */     if (fluid == null || fluid.getFluid() == null) {
/*     */       return;
/*     */     }
/* 125 */     IIcon icon = fluid.getFluid().getIcon(fluid);
/*     */     
/* 127 */     if (icon == null) {
/* 128 */       textureAtlasSprite = ((TextureMap)Minecraft.func_71410_x().func_110434_K().func_110581_b(TextureMap.field_110575_b)).func_110572_b("missingno");
/*     */     }
/*     */     
/* 131 */     this.field_146297_k.field_71446_o.func_110577_a(TextureMap.field_110575_b);
/* 132 */     RenderUtils.setGLColorFromInt(fluid.getFluid().getColor(fluid));
/* 133 */     int fullX = width / 16;
/* 134 */     int fullY = height / 16;
/* 135 */     int lastX = width - fullX * 16;
/* 136 */     int lastY = height - fullY * 16;
/* 137 */     int level = fluid.amount * height / maxCapacity;
/* 138 */     int fullLvl = (height - level) / 16;
/* 139 */     int lastLvl = height - level - fullLvl * 16; int i;
/* 140 */     for (i = 0; i < fullX; i++) {
/* 141 */       for (int j = 0; j < fullY; j++) {
/* 142 */         if (j >= fullLvl) {
/* 143 */           drawCutIcon((IIcon)textureAtlasSprite, x + i * 16, y + j * 16, 16, 16, (j == fullLvl) ? lastLvl : 0);
/*     */         }
/*     */       } 
/*     */     } 
/* 147 */     for (i = 0; i < fullX; i++) {
/* 148 */       drawCutIcon((IIcon)textureAtlasSprite, x + i * 16, y + fullY * 16, 16, lastY, (fullLvl == fullY) ? lastLvl : 0);
/*     */     }
/* 150 */     for (i = 0; i < fullY; i++) {
/* 151 */       if (i >= fullLvl) {
/* 152 */         drawCutIcon((IIcon)textureAtlasSprite, x + fullX * 16, y + i * 16, lastX, 16, (i == fullLvl) ? lastLvl : 0);
/*     */       }
/*     */     } 
/* 155 */     drawCutIcon((IIcon)textureAtlasSprite, x + fullX * 16, y + fullY * 16, lastX, lastY, (fullLvl == fullY) ? lastLvl : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawCutIcon(IIcon icon, int x, int y, int width, int height, int cut) {
/* 160 */     Tessellator tess = Tessellator.field_78398_a;
/* 161 */     tess.func_78382_b();
/* 162 */     tess.func_78374_a(x, (y + height), this.field_73735_i, icon.func_94209_e(), icon.func_94207_b(height));
/* 163 */     tess.func_78374_a((x + width), (y + height), this.field_73735_i, icon.func_94214_a(width), icon.func_94207_b(height));
/* 164 */     tess.func_78374_a((x + width), (y + cut), this.field_73735_i, icon.func_94214_a(width), icon.func_94207_b(cut));
/* 165 */     tess.func_78374_a(x, (y + cut), this.field_73735_i, icon.func_94209_e(), icon.func_94207_b(cut));
/* 166 */     tess.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int mouseX, int mouseY) {
/* 171 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 172 */     bindTexture(this.texture);
/* 173 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*     */     
/* 175 */     int mX = mouseX - this.field_147003_i;
/* 176 */     int mY = mouseY - this.field_147009_r;
/*     */     
/* 178 */     drawWidgets(mX, mY);
/*     */   }
/*     */   
/*     */   protected void drawWidgets(int mX, int mY) {
/* 182 */     for (Widget widget : this.container.getWidgets()) {
/* 183 */       if (widget.hidden) {
/*     */         continue;
/*     */       }
/* 186 */       bindTexture(this.texture);
/* 187 */       widget.draw(this, this.field_147003_i, this.field_147009_r, mX, mY);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/* 193 */     drawLedgers(par1, par2);
/*     */   }
/*     */   
/*     */   protected void drawLedgers(int x, int y) {
/* 197 */     this.ledgerManager.drawLedgers(x, y);
/*     */   }
/*     */   
/*     */   public void drawCenteredString(String string, int xCenter, int yCenter, int textColor) {
/* 201 */     this.field_146289_q.func_78276_b(string, xCenter - this.field_146289_q.func_78256_a(string) / 2, yCenter - this.field_146289_q.field_78288_b / 2, textColor);
/*     */   }
/*     */   
/*     */   protected int getCenteredOffset(String string) {
/* 205 */     return getCenteredOffset(string, this.field_146999_f);
/*     */   }
/*     */   
/*     */   protected int getCenteredOffset(String string, int xWidth) {
/* 209 */     return (xWidth - this.field_146289_q.func_78256_a(string)) / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY) {
/* 216 */     int left = this.field_147003_i;
/* 217 */     int top = this.field_147009_r;
/* 218 */     int realMouseX = mouseX - left;
/* 219 */     int realMouseY = mouseY - top;
/* 220 */     return (realMouseX >= slot.field_75223_e - 1 && realMouseX < slot.field_75223_e + 16 + 1 && realMouseY >= slot.field_75221_f - 1 && realMouseY < slot.field_75221_f + 16 + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
/* 226 */     int mX = mouseX - this.field_147003_i;
/* 227 */     int mY = mouseY - this.field_147009_r;
/*     */     
/* 229 */     for (Widget widget : this.container.getWidgets()) {
/* 230 */       if (widget.hidden)
/*     */         continue; 
/* 232 */       if (!widget.isMouseOver(mX, mY))
/*     */         continue; 
/* 234 */       if (widget.handleMouseClick(mX, mY, mouseButton)) {
/*     */         return;
/*     */       }
/*     */     } 
/* 238 */     super.func_73864_a(mouseX, mouseY, mouseButton);
/*     */ 
/*     */     
/* 241 */     this.ledgerManager.handleMouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146273_a(int mouseX, int mouseY, int mouseButton, long time) {
/* 246 */     int mX = mouseX - this.field_147003_i;
/* 247 */     int mY = mouseY - this.field_147009_r;
/* 248 */     for (Widget widget : this.container.getWidgets()) {
/* 249 */       if (widget.hidden) {
/*     */         continue;
/*     */       }
/* 252 */       widget.handleMouseMove(mX, mY, mouseButton, time);
/*     */     } 
/*     */     
/* 255 */     Slot slot = func_146975_c(mouseX, mouseY);
/* 256 */     if (mouseButton == 1 && slot instanceof buildcraft.core.lib.gui.slots.IPhantomSlot) {
/*     */       return;
/*     */     }
/* 259 */     super.func_146273_a(mouseX, mouseY, mouseButton, time);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146286_b(int mouseX, int mouseY, int eventType) {
/* 264 */     super.func_146286_b(mouseX, mouseY, eventType);
/*     */     
/* 266 */     int mX = mouseX - this.field_147003_i;
/* 267 */     int mY = mouseY - this.field_147009_r;
/* 268 */     for (Widget widget : this.container.getWidgets()) {
/* 269 */       if (widget.hidden) {
/*     */         continue;
/*     */       }
/* 272 */       widget.handleMouseRelease(mX, mY, eventType);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Slot func_146975_c(int x, int y) {
/* 277 */     for (int slotIndex = 0; slotIndex < this.field_147002_h.field_75151_b.size(); slotIndex++) {
/* 278 */       Slot slot = this.field_147002_h.field_75151_b.get(slotIndex);
/* 279 */       if (isMouseOverSlot(slot, x, y)) {
/* 280 */         return slot;
/*     */       }
/*     */     } 
/* 283 */     return null;
/*     */   }
/*     */   
/*     */   public void bindTexture(ResourceLocation texture) {
/* 287 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(texture);
/*     */   }
/*     */   
/*     */   private void drawToolTips(ToolTip toolTips, int mouseX, int mouseY) {
/* 291 */     if (toolTips.size() > 0) {
/* 292 */       int left = this.field_147003_i;
/* 293 */       int top = this.field_147009_r;
/* 294 */       int length = 0;
/*     */ 
/*     */ 
/*     */       
/* 298 */       for (ToolTipLine tip : toolTips) {
/* 299 */         int i = this.field_146289_q.func_78256_a(tip.text);
/*     */         
/* 301 */         if (i > length) {
/* 302 */           length = i;
/*     */         }
/*     */       } 
/*     */       
/* 306 */       int x = mouseX - left + 12;
/* 307 */       int y = mouseY - top - 12;
/* 308 */       int var14 = 8;
/*     */       
/* 310 */       if (toolTips.size() > 1) {
/* 311 */         var14 += 2 + (toolTips.size() - 1) * 10;
/*     */       }
/*     */       
/* 314 */       this.field_73735_i = 300.0F;
/* 315 */       field_146296_j.field_77023_b = 300.0F;
/* 316 */       int var15 = -267386864;
/* 317 */       func_73733_a(x - 3, y - 4, x + length + 3, y - 3, var15, var15);
/* 318 */       func_73733_a(x - 3, y + var14 + 3, x + length + 3, y + var14 + 4, var15, var15);
/* 319 */       func_73733_a(x - 3, y - 3, x + length + 3, y + var14 + 3, var15, var15);
/* 320 */       func_73733_a(x - 4, y - 3, x - 3, y + var14 + 3, var15, var15);
/* 321 */       func_73733_a(x + length + 3, y - 3, x + length + 4, y + var14 + 3, var15, var15);
/* 322 */       int var16 = 1347420415;
/* 323 */       int var17 = (var16 & 0xFEFEFE) >> 1 | var16 & 0xFF000000;
/* 324 */       func_73733_a(x - 3, y - 3 + 1, x - 3 + 1, y + var14 + 3 - 1, var16, var17);
/* 325 */       func_73733_a(x + length + 2, y - 3 + 1, x + length + 3, y + var14 + 3 - 1, var16, var17);
/* 326 */       func_73733_a(x - 3, y - 3, x + length + 3, y - 3 + 1, var16, var16);
/* 327 */       func_73733_a(x - 3, y + var14 + 2, x + length + 3, y + var14 + 3, var17, var17);
/*     */       
/* 329 */       for (ToolTipLine tip : toolTips) {
/* 330 */         String line = tip.text;
/*     */         
/* 332 */         if (tip.color == -1) {
/* 333 */           line = "§7" + line;
/*     */         } else {
/* 335 */           line = "§" + Integer.toHexString(tip.color) + line;
/*     */         } 
/*     */         
/* 338 */         this.field_146289_q.func_78261_a(line, x, y, -1);
/*     */         
/* 340 */         y += 10 + tip.getSpacing();
/*     */       } 
/*     */       
/* 343 */       this.field_73735_i = 0.0F;
/* 344 */       field_146296_j.field_77023_b = 0.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected class LedgerManager
/*     */   {
/* 350 */     protected ArrayList<GuiBuildCraft.Ledger> ledgers = new ArrayList<GuiBuildCraft.Ledger>();
/*     */     private GuiBuildCraft gui;
/*     */     
/*     */     public LedgerManager(GuiBuildCraft gui) {
/* 354 */       this.gui = gui;
/*     */     }
/*     */     
/*     */     public void add(GuiBuildCraft.Ledger ledger) {
/* 358 */       this.ledgers.add(ledger);
/* 359 */       if (SessionVars.getOpenedLedger() != null && ledger.getClass().equals(SessionVars.getOpenedLedger())) {
/* 360 */         ledger.setFullyOpen();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void insert(GuiBuildCraft.Ledger ledger) {
/* 370 */       this.ledgers.add(this.ledgers.size() - 1, ledger);
/*     */     }
/*     */ 
/*     */     
/*     */     protected GuiBuildCraft.Ledger getAtPosition(int mX, int mY) {
/* 375 */       int xShift = (this.gui.field_146294_l - this.gui.field_146999_f) / 2 + this.gui.field_146999_f;
/* 376 */       int yShift = (this.gui.field_146295_m - this.gui.field_147000_g) / 2 + 8;
/*     */       
/* 378 */       for (GuiBuildCraft.Ledger ledger : this.ledgers) {
/* 379 */         if (!ledger.isVisible()) {
/*     */           continue;
/*     */         }
/*     */         
/* 383 */         ledger.currentShiftX = xShift;
/* 384 */         ledger.currentShiftY = yShift;
/* 385 */         if (ledger.intersectsWith(mX, mY, xShift, yShift)) {
/* 386 */           return ledger;
/*     */         }
/*     */         
/* 389 */         yShift += ledger.getHeight();
/*     */       } 
/*     */       
/* 392 */       return null;
/*     */     }
/*     */     
/*     */     protected void drawLedgers(int mouseX, int mouseY) {
/* 396 */       int yPos = 8;
/* 397 */       for (GuiBuildCraft.Ledger ledger1 : this.ledgers) {
/*     */         
/* 399 */         ledger1.update();
/* 400 */         if (!ledger1.isVisible()) {
/*     */           continue;
/*     */         }
/*     */         
/* 404 */         ledger1.draw(GuiBuildCraft.this.field_146999_f, yPos);
/* 405 */         yPos += ledger1.getHeight();
/*     */       } 
/*     */       
/* 408 */       GuiBuildCraft.Ledger ledger = getAtPosition(mouseX, mouseY);
/* 409 */       if (ledger != null) {
/* 410 */         int startX = mouseX - (this.gui.field_146294_l - this.gui.field_146999_f) / 2 + 12;
/* 411 */         int startY = mouseY - (this.gui.field_146295_m - this.gui.field_147000_g) / 2 - 12;
/*     */         
/* 413 */         String tooltip = ledger.getTooltip();
/* 414 */         int textWidth = GuiBuildCraft.this.field_146289_q.func_78256_a(tooltip);
/* 415 */         GuiBuildCraft.this.func_73733_a(startX - 3, startY - 3, startX + textWidth + 3, startY + 8 + 3, -1073741824, -1073741824);
/* 416 */         GuiBuildCraft.this.field_146289_q.func_78261_a(tooltip, startX, startY, -1);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void handleMouseClicked(int x, int y, int mouseButton) {
/* 422 */       if (mouseButton == 0) {
/*     */         
/* 424 */         GuiBuildCraft.Ledger ledger = getAtPosition(x, y);
/*     */ 
/*     */ 
/*     */         
/* 428 */         if (ledger != null && !ledger.handleMouseClicked(x, y, mouseButton)) {
/*     */           
/* 430 */           for (GuiBuildCraft.Ledger other : this.ledgers) {
/* 431 */             if (other != ledger && other.isOpen()) {
/* 432 */               other.toggleOpen();
/*     */             }
/*     */           } 
/* 435 */           ledger.toggleOpen();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract class Ledger
/*     */   {
/* 446 */     public int currentShiftX = 0;
/* 447 */     public int currentShiftY = 0;
/* 448 */     protected int overlayColor = 16777215;
/* 449 */     protected int maxWidth = 124;
/* 450 */     protected int minWidth = 24;
/* 451 */     protected int currentWidth = this.minWidth;
/* 452 */     protected int maxHeight = 24;
/* 453 */     protected int minHeight = 24;
/* 454 */     protected int currentHeight = this.minHeight;
/*     */     
/*     */     private boolean open;
/* 457 */     private long lastUpdateTime = -1L;
/*     */     
/*     */     public void update() {
/* 460 */       if (this.lastUpdateTime < 0L) {
/* 461 */         this.lastUpdateTime = (new Date()).getTime();
/*     */       }
/*     */       
/* 464 */       long updateTime = (new Date()).getTime();
/* 465 */       int updateVal = (int)Math.round((updateTime - this.lastUpdateTime) / 8.0D);
/*     */ 
/*     */       
/* 468 */       if (this.open && this.currentWidth < this.maxWidth) {
/* 469 */         this.currentWidth += updateVal;
/* 470 */         this.currentWidth = Math.min(this.maxWidth, this.currentWidth);
/* 471 */       } else if (!this.open && this.currentWidth > this.minWidth) {
/* 472 */         this.currentWidth -= updateVal;
/* 473 */         this.currentWidth = Math.max(this.minWidth, this.currentWidth);
/*     */       } 
/*     */ 
/*     */       
/* 477 */       if (this.open && this.currentHeight < this.maxHeight) {
/* 478 */         this.currentHeight += updateVal;
/* 479 */         this.currentHeight = Math.min(this.maxWidth, this.currentHeight);
/* 480 */       } else if (!this.open && this.currentHeight > this.minHeight) {
/* 481 */         this.currentHeight -= updateVal;
/* 482 */         this.currentHeight = Math.max(this.minHeight, this.currentHeight);
/*     */       } 
/*     */       
/* 485 */       this.lastUpdateTime = updateTime;
/*     */     }
/*     */     
/*     */     public int getHeight() {
/* 489 */       return this.currentHeight;
/*     */     }
/*     */     
/*     */     public abstract void draw(int param1Int1, int param1Int2);
/*     */     
/*     */     public abstract String getTooltip();
/*     */     
/*     */     public boolean handleMouseClicked(int x, int y, int mouseButton) {
/* 497 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean intersectsWith(int mouseX, int mouseY, int shiftX, int shiftY) {
/* 502 */       if (mouseX >= shiftX && mouseX <= shiftX + this.currentWidth && mouseY >= shiftY && mouseY <= shiftY + getHeight()) {
/* 503 */         return true;
/*     */       }
/*     */       
/* 506 */       return false;
/*     */     }
/*     */     
/*     */     public void setFullyOpen() {
/* 510 */       this.open = true;
/* 511 */       this.currentWidth = this.maxWidth;
/* 512 */       this.currentHeight = this.maxHeight;
/*     */     }
/*     */     
/*     */     public void toggleOpen() {
/* 516 */       if (this.open) {
/* 517 */         this.open = false;
/* 518 */         SessionVars.setOpenedLedger(null);
/*     */       } else {
/* 520 */         this.open = true;
/* 521 */         SessionVars.setOpenedLedger(getClass());
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isVisible() {
/* 526 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isOpen() {
/* 530 */       return this.open;
/*     */     }
/*     */     
/*     */     protected boolean isFullyOpened() {
/* 534 */       return (this.currentWidth >= this.maxWidth);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground(int x, int y) {
/* 539 */       RenderUtils.setGLColorFromInt(this.overlayColor);
/*     */       
/* 541 */       GuiBuildCraft.this.field_146297_k.field_71446_o.func_110577_a(GuiBuildCraft.LEDGER_TEXTURE);
/* 542 */       GuiBuildCraft.this.func_73729_b(x, y, 0, 256 - this.currentHeight, 4, this.currentHeight);
/* 543 */       GuiBuildCraft.this.func_73729_b(x + 4, y, 256 - this.currentWidth + 4, 0, this.currentWidth - 4, 4);
/*     */       
/* 545 */       GuiBuildCraft.this.func_73729_b(x, y, 0, 0, 4, 4);
/*     */       
/* 547 */       GuiBuildCraft.this.func_73729_b(x + 4, y + 4, 256 - this.currentWidth + 4, 256 - this.currentHeight + 4, this.currentWidth - 4, this.currentHeight - 4);
/*     */       
/* 549 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */     
/*     */     protected void drawIcon(IIcon icon, int x, int y) {
/* 553 */       GL11.glDisable(3042);
/* 554 */       GL11.glEnable(3008);
/* 555 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 556 */       GuiBuildCraft.this.func_94065_a(x, y, icon, 16, 16);
/*     */     }
/*     */   }
/*     */   
/*     */   public BuildCraftContainer getContainer() {
/* 561 */     return this.container;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\GuiBuildCraft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */