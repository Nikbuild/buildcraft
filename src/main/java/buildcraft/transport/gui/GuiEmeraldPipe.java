/*     */ package buildcraft.transport.gui;
/*     */ 
/*     */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*     */ import buildcraft.core.lib.gui.buttons.GuiImageButton;
/*     */ import buildcraft.core.lib.gui.buttons.IButtonClickEventListener;
/*     */ import buildcraft.core.lib.gui.buttons.IButtonClickEventTrigger;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTipLine;
/*     */ import buildcraft.core.lib.network.IGuiReturnHandler;
/*     */ import buildcraft.core.lib.network.PacketGuiReturn;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.transport.pipes.PipeItemsEmerald;
/*     */ import net.minecraft.inventory.IInventory;
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
/*     */ public class GuiEmeraldPipe
/*     */   extends GuiBuildCraft
/*     */   implements IButtonClickEventListener
/*     */ {
/*  30 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcrafttransport:textures/gui/pipe_emerald.png");
/*  31 */   private static final ResourceLocation TEXTURE_BUTTON = new ResourceLocation("buildcrafttransport:textures/gui/pipe_emerald_button.png");
/*     */   
/*     */   private static final int WHITE_LIST_BUTTON_ID = 1;
/*     */   
/*     */   private static final int BLACK_LIST_BUTTON_ID = 2;
/*     */   private static final int ROUND_ROBIN_BUTTON_ID = 3;
/*     */   private GuiImageButton whiteListButton;
/*     */   private GuiImageButton blackListButton;
/*     */   private GuiImageButton roundRobinButton;
/*     */   private PipeItemsEmerald pipe;
/*     */   
/*     */   public GuiEmeraldPipe(IInventory playerInventory, PipeItemsEmerald pipe) {
/*  43 */     super(new ContainerEmeraldPipe(playerInventory, pipe), pipe.getFilters(), TEXTURE);
/*     */     
/*  45 */     this.pipe = pipe;
/*     */     
/*  47 */     this.field_146999_f = 175;
/*  48 */     this.field_147000_g = 161;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  53 */     super.func_73866_w_();
/*     */     
/*  55 */     this.field_146292_n.clear();
/*     */     
/*  57 */     this.whiteListButton = new GuiImageButton(1, this.field_147003_i + 7, this.field_147009_r + 41, 18, TEXTURE_BUTTON, 19, 19);
/*  58 */     this.whiteListButton.registerListener(this);
/*  59 */     this.whiteListButton.setToolTip(new ToolTip(500, new ToolTipLine[] { new ToolTipLine(StatCollector.func_74838_a("tip.PipeItemsEmerald.whitelist")) }));
/*  60 */     this.field_146292_n.add(this.whiteListButton);
/*     */     
/*  62 */     this.blackListButton = new GuiImageButton(2, this.field_147003_i + 7 + 18, this.field_147009_r + 41, 18, TEXTURE_BUTTON, 37, 19);
/*  63 */     this.blackListButton.registerListener(this);
/*  64 */     this.blackListButton.setToolTip(new ToolTip(500, new ToolTipLine[] { new ToolTipLine(StatCollector.func_74838_a("tip.PipeItemsEmerald.blacklist")) }));
/*  65 */     this.field_146292_n.add(this.blackListButton);
/*     */     
/*  67 */     this.roundRobinButton = new GuiImageButton(3, this.field_147003_i + 7 + 36, this.field_147009_r + 41, 18, TEXTURE_BUTTON, 55, 19);
/*  68 */     this.roundRobinButton.registerListener(this);
/*  69 */     this.roundRobinButton.setToolTip(new ToolTip(500, new ToolTipLine[] { new ToolTipLine(StatCollector.func_74838_a("tip.PipeItemsEmerald.roundrobin")) }));
/*  70 */     this.field_146292_n.add(this.roundRobinButton);
/*     */     
/*  72 */     switch (this.pipe.getSettings().getFilterMode()) {
/*     */       case WHITE_LIST:
/*  74 */         this.whiteListButton.activate();
/*     */         break;
/*     */       case BLACK_LIST:
/*  77 */         this.blackListButton.activate();
/*     */         break;
/*     */       case ROUND_ROBIN:
/*  80 */         this.roundRobinButton.activate();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleButtonClick(IButtonClickEventTrigger sender, int buttonId) {
/*  87 */     switch (buttonId) {
/*     */       case 1:
/*  89 */         this.whiteListButton.activate();
/*  90 */         this.blackListButton.deActivate();
/*  91 */         this.roundRobinButton.deActivate();
/*     */         
/*  93 */         this.pipe.getSettings().setFilterMode(PipeItemsEmerald.FilterMode.WHITE_LIST);
/*     */         break;
/*     */       case 2:
/*  96 */         this.whiteListButton.deActivate();
/*  97 */         this.blackListButton.activate();
/*  98 */         this.roundRobinButton.deActivate();
/*     */         
/* 100 */         this.pipe.getSettings().setFilterMode(PipeItemsEmerald.FilterMode.BLACK_LIST);
/*     */         break;
/*     */       case 3:
/* 103 */         this.whiteListButton.deActivate();
/* 104 */         this.blackListButton.deActivate();
/* 105 */         this.roundRobinButton.activate();
/*     */         
/* 107 */         this.pipe.getSettings().setFilterMode(PipeItemsEmerald.FilterMode.ROUND_ROBIN);
/*     */         break;
/*     */     } 
/*     */     
/* 111 */     if ((this.pipe.getWorld()).field_72995_K) {
/* 112 */       PacketGuiReturn pkt = new PacketGuiReturn((IGuiReturnHandler)this.pipe.getContainer());
/* 113 */       pkt.sendPacket();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/* 119 */     String title = StringUtils.localize("gui.pipes.emerald.title");
/*     */     
/* 121 */     this.field_146289_q.func_78276_b(title, (this.field_146999_f - this.field_146289_q.func_78256_a(title)) / 2, 6, 4210752);
/* 122 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 93, 4210752);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/* 127 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 128 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 129 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gui\GuiEmeraldPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */