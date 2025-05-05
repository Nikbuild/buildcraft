/*     */ package buildcraft.builders.gui;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.builders.TileArchitect;
/*     */ import buildcraft.core.blueprints.BlueprintReadConfiguration;
/*     */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*     */ import buildcraft.core.lib.gui.buttons.GuiBetterButton;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTipLine;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Keyboard;
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
/*     */ public class GuiArchitect
/*     */   extends GuiBuildCraft
/*     */ {
/*     */   private static final int TEXT_X = 90;
/*     */   private static final int TEXT_Y = 62;
/*     */   private static final int TEXT_WIDTH = 156;
/*     */   private static final int TEXT_HEIGHT = 12;
/*  40 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftbuilders:textures/gui/architect_gui.png");
/*     */   
/*     */   private TileArchitect architect;
/*     */   
/*     */   private GuiBetterButton optionRotate;
/*     */   
/*     */   private GuiBetterButton optionExcavate;
/*     */   
/*     */   private GuiBetterButton optionAllowCreative;
/*     */   private GuiTextField textField;
/*     */   
/*     */   public GuiArchitect(EntityPlayer player, TileArchitect architect) {
/*  52 */     super(new ContainerArchitect(player, architect), (IInventory)architect, TEXTURE);
/*  53 */     this.architect = architect;
/*  54 */     this.field_146999_f = 256;
/*  55 */     this.field_147000_g = 166;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  61 */     super.func_73866_w_();
/*     */     
/*  63 */     Keyboard.enableRepeatEvents(true);
/*     */     
/*  65 */     this.optionRotate = new GuiBetterButton(0, this.field_147003_i + 5, this.field_147009_r + 30, 79, "");
/*  66 */     this.field_146292_n.add(this.optionRotate);
/*     */     
/*  68 */     this.optionExcavate = new GuiBetterButton(1, this.field_147003_i + 5, this.field_147009_r + 55, 79, "");
/*  69 */     this.field_146292_n.add(this.optionExcavate);
/*     */     
/*  71 */     this.optionAllowCreative = new GuiBetterButton(2, this.field_147003_i + 5, this.field_147009_r + 80, 79, "");
/*  72 */     this.optionAllowCreative.setToolTip(new ToolTip(500, new ToolTipLine[] { new ToolTipLine(
/*  73 */               StringUtils.localize("tile.architect.tooltip.allowCreative.1")), new ToolTipLine(
/*  74 */               StringUtils.localize("tile.architect.tooltip.allowCreative.2")) }));
/*     */     
/*  76 */     this.field_146292_n.add(this.optionAllowCreative);
/*     */     
/*  78 */     this.textField = new GuiTextField(this.field_146289_q, 90, 62, 156, 12);
/*  79 */     this.textField.func_146203_f(32);
/*  80 */     this.textField.func_146180_a(this.architect.name);
/*  81 */     this.textField.func_146195_b(true);
/*     */     
/*  83 */     updateButtons();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146281_b() {
/*  88 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) {
/*  93 */     BlueprintReadConfiguration conf = this.architect.readConfiguration;
/*     */     
/*  95 */     if (button == this.optionRotate) {
/*  96 */       conf.rotate = !conf.rotate;
/*  97 */     } else if (button == this.optionExcavate) {
/*  98 */       conf.excavate = !conf.excavate;
/*  99 */     } else if (button == this.optionAllowCreative) {
/* 100 */       conf.allowCreative = !conf.allowCreative;
/*     */     } 
/*     */     
/* 103 */     this.architect.rpcSetConfiguration(conf);
/*     */     
/* 105 */     updateButtons();
/*     */   }
/*     */   
/*     */   private void updateButtons() {
/* 109 */     BlueprintReadConfiguration conf = this.architect.readConfiguration;
/*     */     
/* 111 */     if (conf.rotate) {
/* 112 */       this.optionRotate.field_146126_j = StringUtils.localize("tile.architect.rotate");
/*     */     } else {
/* 114 */       this.optionRotate.field_146126_j = StringUtils.localize("tile.architect.norotate");
/*     */     } 
/*     */     
/* 117 */     if (conf.excavate) {
/* 118 */       this.optionExcavate.field_146126_j = StringUtils.localize("tile.architect.excavate");
/*     */     } else {
/* 120 */       this.optionExcavate.field_146126_j = StringUtils.localize("tile.architect.noexcavate");
/*     */     } 
/*     */     
/* 123 */     if (conf.allowCreative) {
/* 124 */       this.optionAllowCreative.field_146126_j = StringUtils.localize("tile.architect.allowCreative");
/*     */     } else {
/* 126 */       this.optionAllowCreative.field_146126_j = StringUtils.localize("tile.architect.noallowCreative");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/* 132 */     String title = StringUtils.localize("tile.architectBlock.name");
/* 133 */     this.field_146289_q.func_78276_b(title, getCenteredOffset(title), 6, 4210752);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     this.textField.func_146194_f();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/* 147 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 148 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 149 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/* 150 */     int i1 = ((ContainerArchitect)this.container).computingTime;
/* 151 */     func_73729_b(this.field_147003_i + 159, this.field_147009_r + 34, 0, 166, i1 + 1, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int i, int j, int k) {
/* 156 */     super.func_73864_a(i, j, k);
/*     */     
/* 158 */     this.textField.func_146192_a(i - this.field_147003_i, j - this.field_147009_r, k);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char c, int i) {
/* 163 */     if (this.textField.func_146206_l()) {
/* 164 */       if (c == '\r' || c == '\033') {
/* 165 */         this.textField.func_146195_b(false);
/*     */       } else {
/* 167 */         this.textField.func_146201_a(c, i);
/* 168 */         final String text = this.textField.func_146179_b();
/* 169 */         BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this.architect, "setName", new CommandWriter() {
/*     */                 public void write(ByteBuf data) {
/* 171 */                   NetworkUtils.writeUTF(data, text);
/*     */                 }
/*     */               }));
/*     */       } 
/*     */     } else {
/* 176 */       super.func_73869_a(c, i);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\gui\GuiArchitect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */