/*     */ package buildcraft.builders.gui;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.filler.FillerManager;
/*     */ import buildcraft.api.filler.IFillerPattern;
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.StatementMouseClick;
/*     */ import buildcraft.builders.TileFiller;
/*     */ import buildcraft.core.builders.patterns.FillerPattern;
/*     */ import buildcraft.core.lib.gui.AdvancedSlot;
/*     */ import buildcraft.core.lib.gui.GuiAdvancedInterface;
/*     */ import buildcraft.core.lib.gui.GuiTools;
/*     */ import buildcraft.core.lib.gui.StatementParameterSlot;
/*     */ import buildcraft.core.lib.gui.StatementSlot;
/*     */ import buildcraft.core.lib.gui.buttons.ButtonTextureSet;
/*     */ import buildcraft.core.lib.gui.buttons.GuiBetterButton;
/*     */ import buildcraft.core.lib.gui.buttons.IButtonTextureSet;
/*     */ import buildcraft.core.lib.gui.buttons.StandardButtonTextureSets;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTipLine;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiFiller
/*     */   extends GuiAdvancedInterface
/*     */ {
/*     */   class FillerParameterSlot
/*     */     extends StatementParameterSlot
/*     */   {
/*     */     public FillerParameterSlot(int x, int y, int slot) {
/*  43 */       super(GuiFiller.this.instance, x, y, slot, GuiFiller.this.fakeStatementSlot);
/*     */     }
/*     */ 
/*     */     
/*     */     public IStatementParameter getParameter() {
/*  48 */       if (GuiFiller.this.instance.filler.patternParameters == null || this.slot >= GuiFiller.this.instance.filler.patternParameters.length) {
/*  49 */         return null;
/*     */       }
/*  51 */       return GuiFiller.this.instance.filler.patternParameters[this.slot];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setParameter(IStatementParameter param, boolean notifyServer) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  61 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftbuilders:textures/gui/filler.png");
/*  62 */   private static final IButtonTextureSet EXCAVATE_OFF = (IButtonTextureSet)new ButtonTextureSet(240, -16, 16, 16, TEXTURE);
/*  63 */   private static final IButtonTextureSet EXCAVATE_ON = (IButtonTextureSet)new ButtonTextureSet(224, -16, 16, 16, TEXTURE);
/*     */   private final TileFiller filler;
/*     */   private final GuiFiller instance;
/*     */   private final StatementSlot fakeStatementSlot;
/*     */   
/*     */   public GuiFiller(IInventory playerInventory, TileFiller filler) {
/*  69 */     super(new ContainerFiller(playerInventory, filler), (IInventory)filler, TEXTURE);
/*  70 */     this.filler = filler;
/*  71 */     this.instance = this;
/*  72 */     this.fakeStatementSlot = new StatementSlot(this.instance, -1, -1, 0)
/*     */       {
/*     */         public IStatement getStatement() {
/*  75 */           return (IStatement)GuiFiller.this.instance.filler.currentPattern;
/*     */         }
/*     */       };
/*  78 */     this.field_146999_f = 175;
/*  79 */     this.field_147000_g = 240;
/*     */   }
/*     */   
/*     */   private IButtonTextureSet getExcavateTexture() {
/*  83 */     return this.filler.isExcavate() ? EXCAVATE_ON : EXCAVATE_OFF;
/*     */   }
/*     */   
/*     */   private GuiBetterButton getExcavateButton() {
/*  87 */     return (new GuiBetterButton(2, this.field_147003_i + 150, this.field_147009_r + 30, 16, getExcavateTexture(), ""))
/*  88 */       .setToolTip(new ToolTip(500, new ToolTipLine[] {
/*  89 */             new ToolTipLine(StatCollector.func_74838_a("tip.filler.excavate." + (this.filler.isExcavate() ? "on" : "off")))
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  95 */     super.func_73866_w_();
/*  96 */     this.field_146292_n.clear();
/*     */     
/*  98 */     this.field_146292_n.add(new GuiBetterButton(0, this.field_147003_i + 38 - 18, this.field_147009_r + 30, 10, (IButtonTextureSet)StandardButtonTextureSets.LEFT_BUTTON, ""));
/*     */     
/* 100 */     this.field_146292_n.add(new GuiBetterButton(1, this.field_147003_i + 38 + 16 + 8, this.field_147009_r + 30, 10, (IButtonTextureSet)StandardButtonTextureSets.RIGHT_BUTTON, ""));
/*     */     
/* 102 */     this.field_146292_n.add(getExcavateButton());
/*     */     
/* 104 */     this.slots.clear();
/* 105 */     for (int i = 0; i < 4; i++) {
/* 106 */       this.slots.add(new FillerParameterSlot(77 + i * 18, 30, i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) {
/* 112 */     super.func_146284_a(button);
/*     */     
/* 114 */     if (button.field_146127_k == 0) {
/* 115 */       this.filler.currentPattern = (FillerPattern)FillerManager.registry.getPreviousPattern((IFillerPattern)this.filler.currentPattern);
/* 116 */     } else if (button.field_146127_k == 1) {
/* 117 */       this.filler.currentPattern = (FillerPattern)FillerManager.registry.getNextPattern((IFillerPattern)this.filler.currentPattern);
/* 118 */     } else if (button.field_146127_k == 2) {
/* 119 */       this.filler.setExcavate(!this.filler.isExcavate());
/*     */       
/* 121 */       this.field_146292_n.set(2, getExcavateButton());
/*     */       
/* 123 */       BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this.filler, "setFlags", new CommandWriter() {
/*     */               public void write(ByteBuf data) {
/* 125 */                 data.writeBoolean(GuiFiller.this.filler.isExcavate());
/*     */               }
/*     */             }));
/*     */     } 
/*     */     
/* 130 */     this.filler.rpcSetPatternFromString(this.filler.currentPattern.getUniqueTag());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int x, int y, int k) {
/* 135 */     super.func_73864_a(x, y, k);
/*     */     
/* 137 */     AdvancedSlot slot = getSlotAtLocation(x, y);
/*     */     
/* 139 */     if (slot != null) {
/* 140 */       int i = ((FillerParameterSlot)slot).slot;
/* 141 */       if (i < this.filler.patternParameters.length) {
/* 142 */         if (this.filler.patternParameters[i] != null) {
/* 143 */           this.filler.patternParameters[i].onClick((IStatementContainer)this.filler, (IStatement)this.filler.currentPattern, this.field_146297_k.field_71439_g.field_71071_by.func_70445_o(), new StatementMouseClick(k, 
/* 144 */                 func_146272_n()));
/*     */         } else {
/* 146 */           this.filler.patternParameters[i] = this.filler.currentPattern.createParameter(i);
/*     */         } 
/* 148 */         this.filler.rpcSetParameter(i, this.filler.patternParameters[i]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int mx, int my) {
/* 155 */     super.func_146976_a(f, mx, my);
/* 156 */     drawBackgroundSlots(mx, my);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int mx, int my) {
/* 161 */     super.func_146979_b(mx, my);
/*     */     
/* 163 */     String title = StringUtils.localize("tile.fillerBlock.name");
/* 164 */     this.field_146289_q.func_78276_b(title, getCenteredOffset(title), 6, 4210752);
/* 165 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.filling.resources"), 8, 74, 4210752);
/* 166 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, 142, 4210752);
/* 167 */     GuiTools.drawCenteredString(this.field_146289_q, this.filler.currentPattern.getDescription(), 56);
/*     */     
/* 169 */     drawTooltipForSlotAt(mx, my);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\gui\GuiFiller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */