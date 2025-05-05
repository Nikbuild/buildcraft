/*     */ package buildcraft.robotics.gui;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.EnumColor;
/*     */ import buildcraft.core.lib.gui.AdvancedSlot;
/*     */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*     */ import buildcraft.core.lib.gui.GuiAdvancedInterface;
/*     */ import buildcraft.core.lib.gui.buttons.GuiBetterButton;
/*     */ import buildcraft.core.lib.gui.buttons.IButtonTextureSet;
/*     */ import buildcraft.core.lib.gui.buttons.StandardButtonTextureSets;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTipLine;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.render.DynamicTextureBC;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.robotics.TileZonePlan;
/*     */ import buildcraft.robotics.ZonePlan;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ public class GuiZonePlan
/*     */   extends GuiAdvancedInterface
/*     */ {
/*     */   public static final int WINDOWED_MAP_WIDTH = 213;
/*     */   public static final int WINDOWED_MAP_HEIGHT = 100;
/*  47 */   private static final ResourceLocation TMP_TEXTURE = new ResourceLocation("buildcraftrobotics:textures/gui/zone_planner_gui.png");
/*     */   
/*  49 */   private int mapWidth = 213;
/*  50 */   private int mapHeight = 100;
/*     */   
/*     */   private TileZonePlan zonePlan;
/*     */   
/*     */   private DynamicTextureBC newSelection;
/*  55 */   private int selX1 = 0;
/*  56 */   private int selX2 = 0;
/*  57 */   private int selY1 = 0;
/*  58 */   private int selY2 = 0;
/*     */   
/*     */   private boolean inSelection = false;
/*     */   
/*     */   private DynamicTextureBC currentSelection;
/*     */   
/*  64 */   private int mapXMin = 0;
/*  65 */   private int mapYMin = 0;
/*     */   
/*  67 */   private float blocksPerPixel = 1.0F;
/*     */   
/*     */   private int cx;
/*     */   private int cz;
/*  71 */   private AreaSlot colorSelected = null;
/*     */   
/*  73 */   private float alpha = 0.8F;
/*     */   
/*     */   private GuiBetterButton tool;
/*     */   private GuiBetterButton fsButton;
/*     */   private List<GuiBetterButton> savedButtonList;
/*     */   private GuiTextField textField;
/*     */   
/*     */   private static class AreaSlot
/*     */     extends AdvancedSlot
/*     */   {
/*     */     public EnumColor color;
/*     */     
/*     */     public AreaSlot(GuiAdvancedInterface gui, int x, int y, EnumColor iColor) {
/*  86 */       super(gui, x, y);
/*     */       
/*  88 */       this.color = iColor;
/*     */     }
/*     */ 
/*     */     
/*     */     public IIcon getIcon() {
/*  93 */       return this.color.getIcon();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDescription() {
/*  98 */       return this.color.getLocalizedName();
/*     */     }
/*     */   }
/*     */   
/*     */   public GuiZonePlan(IInventory inventory, TileZonePlan iZonePlan) {
/* 103 */     super(new ContainerZonePlan(inventory, iZonePlan), inventory, TMP_TEXTURE);
/*     */     
/* 105 */     (getContainer()).gui = this;
/*     */     
/* 107 */     this.field_146999_f = 256;
/* 108 */     this.field_147000_g = 228;
/*     */     
/* 110 */     this.zonePlan = iZonePlan;
/*     */     
/* 112 */     (getContainer()).mapTexture = new DynamicTextureBC(this.mapWidth, this.mapHeight);
/* 113 */     this.currentSelection = new DynamicTextureBC(this.mapWidth, this.mapHeight);
/* 114 */     this.newSelection = new DynamicTextureBC(1, 1);
/*     */     
/* 116 */     (getContainer()).currentAreaSelection = new ZonePlan();
/*     */     
/* 118 */     this.cx = this.zonePlan.field_145851_c;
/* 119 */     this.cz = this.zonePlan.field_145849_e;
/*     */     
/* 121 */     resetNullSlots(16);
/*     */     
/* 123 */     for (int i = 0; i < 4; i++) {
/* 124 */       for (int j = 0; j < 4; j++) {
/* 125 */         this.slots.set(i * 4 + j, new AreaSlot(this, 8 + 18 * i, 146 + 18 * j, EnumColor.values()[i * 4 + j]));
/*     */       }
/*     */     } 
/*     */     
/* 129 */     this.colorSelected = this.slots.get(0);
/*     */     
/* 131 */     this.newSelection.setColor(0, 0, this.colorSelected.color.getDarkHex(), this.alpha);
/*     */     
/* 133 */     uploadMap();
/* 134 */     getContainer().loadArea(this.colorSelected.color.ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/* 140 */     super.func_73866_w_();
/*     */     
/* 142 */     this.tool = new GuiBetterButton(0, this.field_147003_i + 27, this.field_147009_r + 111, 15, (IButtonTextureSet)StandardButtonTextureSets.SMALL_BUTTON, "+");
/* 143 */     this.tool.setToolTip(new ToolTip(new ToolTipLine[] { new ToolTipLine(StringUtils.localize("tip.tool.add")) }));
/* 144 */     this.field_146292_n.add(this.tool);
/* 145 */     this.fsButton = new GuiBetterButton(1, this.field_147003_i + 44, this.field_147009_r + 111, 20, (IButtonTextureSet)StandardButtonTextureSets.SMALL_BUTTON, "FS");
/* 146 */     this.fsButton.setToolTip(new ToolTip(new ToolTipLine[] { new ToolTipLine(StringUtils.localize("tip.tool.fullscreen")) }));
/* 147 */     this.field_146292_n.add(this.fsButton);
/*     */     
/* 149 */     this.savedButtonList = this.field_146292_n;
/*     */     
/* 151 */     this.textField = new GuiTextField(this.field_146289_q, 28, 129, 156, 12);
/* 152 */     this.textField.func_146203_f(32);
/* 153 */     this.textField.func_146180_a(this.zonePlan.mapName);
/* 154 */     this.textField.func_146195_b(true);
/*     */   }
/*     */   
/*     */   private void uploadMap() {
/* 158 */     BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(getContainer(), "computeMap", new CommandWriter() {
/*     */             public void write(ByteBuf data) {
/* 160 */               data.writeInt(GuiZonePlan.this.cx);
/* 161 */               data.writeInt(GuiZonePlan.this.cz);
/* 162 */               data.writeShort((GuiZonePlan.this.getContainer()).mapTexture.width);
/* 163 */               data.writeShort((GuiZonePlan.this.getContainer()).mapTexture.height);
/* 164 */               data.writeFloat(GuiZonePlan.this.blocksPerPixel);
/*     */             }
/*     */           }));
/*     */   }
/*     */   
/*     */   private boolean isFullscreen() {
/* 170 */     return ((getContainer()).mapTexture.height > 100);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/* 175 */     super.func_146976_a(f, x, y);
/*     */     
/* 177 */     if ((getContainer()).mapTexture.width <= 213) {
/* 178 */       this.mapXMin = this.field_147003_i + 8 + (213 - (getContainer()).mapTexture.width) / 2;
/*     */     } else {
/* 180 */       this.mapXMin = (this.field_146294_l - (getContainer()).mapTexture.width) / 2;
/*     */     } 
/*     */     
/* 183 */     if ((getContainer()).mapTexture.height <= 100) {
/* 184 */       this.mapYMin = this.field_147009_r + 9 + (100 - (getContainer()).mapTexture.height) / 2;
/*     */     } else {
/* 186 */       this.mapYMin = (this.field_146295_m - (getContainer()).mapTexture.height) / 2;
/*     */     } 
/*     */     
/* 189 */     (getContainer()).mapTexture.draw(this.mapXMin, this.mapYMin, this.field_73735_i);
/*     */     
/* 191 */     GL11.glPushAttrib(8192);
/* 192 */     GL11.glEnable(3042);
/*     */     
/* 194 */     this.currentSelection.draw(this.mapXMin, this.mapYMin, this.field_73735_i);
/*     */     
/* 196 */     GL11.glPopAttrib();
/* 197 */     GL11.glDisable(3042);
/*     */     
/* 199 */     this.newSelection.updateTexture();
/*     */     
/* 201 */     if (this.inSelection && this.selX2 != 0) {
/* 202 */       GL11.glPushAttrib(8192);
/* 203 */       GL11.glEnable(3042);
/*     */       
/* 205 */       int x1 = (this.selX1 < this.selX2) ? this.selX1 : this.selX2;
/* 206 */       int x2 = (this.selX1 < this.selX2) ? this.selX2 : this.selX1;
/* 207 */       int y1 = (this.selY1 < this.selY2) ? this.selY1 : this.selY2;
/* 208 */       int y2 = (this.selY1 < this.selY2) ? this.selY2 : this.selY1;
/*     */       
/* 210 */       func_73729_b(x1, y1, 0, 0, x2 - x1 + 1, y2 - y1 + 1);
/* 211 */       GL11.glPopAttrib();
/* 212 */       GL11.glDisable(3042);
/*     */     } 
/*     */     
/* 215 */     if (!isFullscreen()) {
/* 216 */       drawBackgroundSlots(x, y);
/*     */       
/* 218 */       bindTexture(this.texture);
/*     */       
/* 220 */       GL11.glEnable(3008);
/* 221 */       func_73729_b(this.field_147003_i + this.colorSelected.x, this.field_147009_r + this.colorSelected.y, 0, 228, 16, 16);
/* 222 */       func_73729_b(this.field_147003_i + 236, this.field_147009_r + 27, 16, 228, 8, (int)(this.zonePlan.progress / 120.0F * 27.0F));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/* 229 */     super.func_146979_b(par1, par2);
/* 230 */     if (!isFullscreen()) {
/* 231 */       this.textField.func_146194_f();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
/* 237 */     int blocksX = Math.round((mouseX - this.mapXMin) * this.blocksPerPixel);
/* 238 */     int blocksZ = Math.round((mouseY - this.mapYMin) * this.blocksPerPixel);
/*     */     
/* 240 */     int blockStartX = Math.round(this.cx - this.mapWidth * this.blocksPerPixel / 2.0F);
/* 241 */     int blockStartZ = Math.round(this.cz - this.mapHeight * this.blocksPerPixel / 2.0F);
/*     */ 
/*     */ 
/*     */     
/* 245 */     boolean clickOnMap = (mouseX >= this.mapXMin && mouseX <= this.mapXMin + (getContainer()).mapTexture.width && mouseY >= this.mapYMin && mouseY <= this.mapYMin + (getContainer()).mapTexture.height);
/*     */     
/* 247 */     if (clickOnMap) {
/* 248 */       if (mouseButton == 1) {
/* 249 */         this.cx = blockStartX + blocksX;
/* 250 */         this.cz = blockStartZ + blocksZ;
/*     */         
/* 252 */         uploadMap();
/* 253 */         refreshSelectedArea();
/*     */         return;
/*     */       } 
/* 256 */       this.inSelection = true;
/* 257 */       this.selX1 = mouseX;
/* 258 */       this.selY1 = mouseY;
/* 259 */       this.selX2 = 0;
/* 260 */       this.selY2 = 0;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 265 */     super.func_73864_a(mouseX, mouseY, mouseButton);
/* 266 */     this.textField.func_146192_a(mouseX - this.field_147003_i, mouseY - this.field_147009_r, mouseButton);
/*     */     
/* 268 */     AdvancedSlot slot = getSlotAtLocation(mouseX, mouseY);
/*     */     
/* 270 */     if (slot instanceof AreaSlot) {
/* 271 */       this.colorSelected = (AreaSlot)slot;
/*     */       
/* 273 */       this.newSelection.setColor(0, 0, this.colorSelected.color.getDarkHex(), this.alpha);
/* 274 */       getContainer().loadArea(this.colorSelected.color.ordinal());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146273_a(int mouseX, int mouseY, int lastButtonBlicked, long time) {
/* 280 */     super.func_146273_a(mouseX, mouseY, lastButtonBlicked, time);
/*     */     
/* 282 */     if (this.inSelection && mouseX >= this.mapXMin && mouseX <= this.mapXMin + 
/* 283 */       (getContainer()).mapTexture.width && mouseY >= this.mapYMin && mouseY <= this.mapYMin + 
/* 284 */       (getContainer()).mapTexture.height) {
/*     */       
/* 286 */       this.selX2 = mouseX;
/* 287 */       this.selY2 = mouseY;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146286_b(int mouseX, int mouseY, int eventType) {
/* 293 */     super.func_146286_b(mouseX, mouseY, eventType);
/*     */     
/* 295 */     if (eventType != -1 && this.inSelection) {
/* 296 */       boolean val = this.tool.field_146126_j.equals("+");
/* 297 */       int blockStartX = Math.round(this.cx - this.mapWidth * this.blocksPerPixel / 2.0F);
/* 298 */       int blockStartZ = Math.round(this.cz - this.mapHeight * this.blocksPerPixel / 2.0F);
/*     */       
/* 300 */       int x1 = (this.selX1 < this.selX2) ? this.selX1 : this.selX2;
/* 301 */       int x2 = (this.selX1 < this.selX2) ? this.selX2 : this.selX1;
/* 302 */       int y1 = (this.selY1 < this.selY2) ? this.selY1 : this.selY2;
/* 303 */       int y2 = (this.selY1 < this.selY2) ? this.selY2 : this.selY1;
/*     */       
/* 305 */       int lengthX = Math.round((x2 - x1) * this.blocksPerPixel);
/* 306 */       int lengthY = Math.round((y2 - y1) * this.blocksPerPixel);
/*     */       
/* 308 */       for (int i = 0; i <= lengthX; i++) {
/* 309 */         for (int j = 0; j <= lengthY; j++) {
/* 310 */           int x = Math.round(blockStartX + (x1 - this.mapXMin) * this.blocksPerPixel) + i;
/* 311 */           int z = Math.round(blockStartZ + (y1 - this.mapYMin) * this.blocksPerPixel) + j;
/*     */           
/* 313 */           (getContainer()).currentAreaSelection.set(x, z, val);
/*     */         } 
/*     */       } 
/*     */       
/* 317 */       this.inSelection = false;
/* 318 */       getContainer().saveArea(this.colorSelected.color.ordinal());
/* 319 */       refreshSelectedArea();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void toFullscreen() {
/* 324 */     if (isFullscreen()) {
/*     */       return;
/*     */     }
/*     */     
/* 328 */     if (this.blocksPerPixel > 4.0F) {
/* 329 */       this.blocksPerPixel = 4.0F;
/*     */     }
/*     */     
/* 332 */     this.mapWidth = this.field_146297_k.field_71443_c;
/* 333 */     this.mapHeight = this.field_146297_k.field_71440_d;
/*     */     
/* 335 */     (getContainer()).mapTexture = new DynamicTextureBC(this.mapWidth, this.mapHeight);
/* 336 */     this.currentSelection = new DynamicTextureBC(this.mapWidth, this.mapHeight);
/*     */     
/* 338 */     for (Slot s : this.container.field_75151_b) {
/* 339 */       s.field_75223_e += 1048576;
/* 340 */       s.field_75221_f += 1048576;
/*     */     } 
/*     */     
/* 343 */     uploadMap();
/* 344 */     refreshSelectedArea();
/*     */     
/* 346 */     this.field_146292_n = new LinkedList();
/*     */   }
/*     */   
/*     */   private void toWindowed() {
/* 350 */     if (!isFullscreen()) {
/*     */       return;
/*     */     }
/*     */     
/* 354 */     this.mapWidth = 213;
/* 355 */     this.mapHeight = 100;
/*     */     
/* 357 */     (getContainer()).mapTexture = new DynamicTextureBC(this.mapWidth, this.mapHeight);
/* 358 */     this.currentSelection = new DynamicTextureBC(this.mapWidth, this.mapHeight);
/*     */     
/* 360 */     for (Slot s : this.container.field_75151_b) {
/* 361 */       s.field_75223_e -= 1048576;
/* 362 */       s.field_75221_f -= 1048576;
/*     */     } 
/*     */     
/* 365 */     uploadMap();
/* 366 */     refreshSelectedArea();
/*     */     
/* 368 */     this.field_146292_n = this.savedButtonList;
/*     */   }
/*     */   
/*     */   private boolean incBlocksPerPixel() {
/* 372 */     if (this.blocksPerPixel > 0.125F) {
/* 373 */       if (this.blocksPerPixel <= 1.0F) {
/* 374 */         this.blocksPerPixel /= 2.0F;
/*     */       } else {
/* 376 */         this.blocksPerPixel--;
/*     */       } 
/* 378 */       return true;
/*     */     } 
/* 380 */     return false;
/*     */   }
/*     */   
/*     */   private boolean decBlocksPerPixel() {
/* 384 */     if ((isFullscreen() && this.blocksPerPixel < 4.0F) || (!isFullscreen() && this.blocksPerPixel < 8.0F)) {
/* 385 */       if (this.blocksPerPixel >= 1.0F) {
/* 386 */         this.blocksPerPixel++;
/*     */       } else {
/* 388 */         this.blocksPerPixel *= 2.0F;
/*     */       } 
/* 390 */       return true;
/*     */     } 
/* 392 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char carac, int val) {
/* 397 */     if (!isFullscreen() && this.textField.func_146206_l()) {
/* 398 */       if (carac == '\r' || carac == '\033') {
/* 399 */         this.textField.func_146195_b(false);
/*     */       } else {
/* 401 */         this.textField.func_146201_a(carac, val);
/* 402 */         final String text = this.textField.func_146179_b();
/* 403 */         BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(getContainer(), "setName", new CommandWriter() {
/*     */                 public void write(ByteBuf data) {
/* 405 */                   NetworkUtils.writeUTF(data, text); }
/*     */               }));
/*     */       } 
/*     */       return;
/*     */     } 
/* 410 */     if (val == 63) {
/* 411 */       uploadMap();
/* 412 */       refreshSelectedArea();
/* 413 */     } else if (carac == '+' && incBlocksPerPixel()) {
/* 414 */       uploadMap();
/* 415 */       refreshSelectedArea();
/* 416 */     } else if (carac == '-' && decBlocksPerPixel()) {
/* 417 */       uploadMap();
/* 418 */       refreshSelectedArea();
/* 419 */     } else if (carac == 'm' || (carac == '\033' && isFullscreen())) {
/* 420 */       toWindowed();
/* 421 */     } else if (carac == 'M') {
/* 422 */       toFullscreen();
/*     */     } else {
/* 424 */       super.func_73869_a(carac, val);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void refreshSelectedArea() {
/* 429 */     int color = this.colorSelected.color.getDarkHex();
/*     */     
/* 431 */     int rAdd = color >> 16 & 0xFF;
/* 432 */     int gAdd = color >> 8 & 0xFF;
/* 433 */     int bAdd = color & 0xFF;
/*     */     
/* 435 */     for (int i = 0; i < this.currentSelection.width; i++) {
/* 436 */       for (int j = 0; j < this.currentSelection.height; j++) {
/* 437 */         int blockStartX = Math.round(this.cx - this.mapWidth * this.blocksPerPixel / 2.0F);
/* 438 */         int blockStartZ = Math.round(this.cz - this.mapHeight * this.blocksPerPixel / 2.0F);
/* 439 */         int c = (int)Math.ceil(this.blocksPerPixel);
/*     */         
/* 441 */         double r = 0.0D;
/* 442 */         double g = 0.0D;
/* 443 */         double b = 0.0D;
/*     */         
/* 445 */         for (int stepi = 0; stepi < c; stepi++) {
/* 446 */           for (int stepj = 0; stepj < c; stepj++) {
/* 447 */             int x = Math.round(blockStartX + i * this.blocksPerPixel) + stepi;
/* 448 */             int z = Math.round(blockStartZ + j * this.blocksPerPixel) + stepj;
/*     */             
/* 450 */             if ((getContainer()).currentAreaSelection.get(x, z)) {
/* 451 */               r += rAdd;
/* 452 */               g += gAdd;
/* 453 */               b += bAdd;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 458 */         r /= (c * c);
/* 459 */         g /= (c * c);
/* 460 */         b /= (c * c);
/*     */         
/* 462 */         if (r != 0.0D) {
/* 463 */           this.currentSelection.setColori(i, j, (int)r, (int)g, (int)b, (int)(this.alpha * 255.0F));
/*     */         } else {
/* 465 */           this.currentSelection.setColori(i, j, 0, 0, 0, 0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerZonePlan getContainer() {
/* 473 */     return (ContainerZonePlan)super.getContainer();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) {
/* 478 */     if (button == this.tool) {
/* 479 */       if (this.tool.field_146126_j.equals("+")) {
/* 480 */         this.tool.field_146126_j = "-";
/* 481 */         this.tool.getToolTip().remove(0);
/* 482 */         this.tool.getToolTip().add(new ToolTipLine(StringUtils.localize("tip.tool.remove")));
/*     */       } else {
/* 484 */         this.tool.field_146126_j = "+";
/* 485 */         this.tool.getToolTip().remove(0);
/* 486 */         this.tool.getToolTip().add(new ToolTipLine(StringUtils.localize("tip.tool.add")));
/*     */       } 
/* 488 */     } else if (button == this.fsButton) {
/* 489 */       toFullscreen();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146274_d() {
/* 495 */     super.func_146274_d();
/*     */     
/* 497 */     int mouseX = Mouse.getEventX() * this.field_146294_l / this.field_146297_k.field_71443_c;
/* 498 */     int mouseY = this.field_146295_m - Mouse.getEventY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1;
/*     */     
/* 500 */     if (mouseX >= this.mapXMin && mouseX <= this.mapXMin + (getContainer()).mapTexture.width && mouseY >= this.mapYMin && mouseY <= this.mapYMin + 
/* 501 */       (getContainer()).mapTexture.height) {
/* 502 */       int wheel = Mouse.getEventDWheel();
/* 503 */       if (wheel != 0)
/* 504 */         if (wheel > 0 && decBlocksPerPixel()) {
/* 505 */           uploadMap();
/* 506 */           refreshSelectedArea();
/* 507 */         } else if (wheel < 0 && incBlocksPerPixel()) {
/* 508 */           uploadMap();
/* 509 */           refreshSelectedArea();
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\gui\GuiZonePlan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */