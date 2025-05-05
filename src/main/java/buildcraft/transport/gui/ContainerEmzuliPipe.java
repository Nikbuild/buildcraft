/*     */ package buildcraft.transport.gui;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.EnumColor;
/*     */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*     */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*     */ import buildcraft.core.lib.gui.slots.SlotPhantom;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*     */ import buildcraft.core.lib.gui.tooltips.ToolTipLine;
/*     */ import buildcraft.core.lib.gui.widgets.ButtonWidget;
/*     */ import buildcraft.core.lib.gui.widgets.Widget;
/*     */ import buildcraft.core.lib.network.IGuiReturnHandler;
/*     */ import buildcraft.core.lib.network.PacketGuiReturn;
/*     */ import buildcraft.core.lib.utils.RevolvingList;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.transport.pipes.PipeItemsEmzuli;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerEmzuliPipe
/*     */   extends BuildCraftContainer
/*     */ {
/*     */   private final PipeItemsEmzuli pipe;
/*     */   private final IInventory filterInv;
/*  42 */   private final byte[] prevSlotColors = new byte[4];
/*  43 */   private final PaintWidget[] paintWidgets = new PaintWidget[4];
/*     */   
/*     */   public ContainerEmzuliPipe(IInventory playerInventory, PipeItemsEmzuli pipe) {
/*  46 */     super(pipe.getFilters().func_70302_i_());
/*     */     
/*  48 */     this.pipe = pipe;
/*  49 */     this.filterInv = pipe.getFilters();
/*     */     
/*  51 */     addWidget((Widget)(this.paintWidgets[0] = new PaintWidget(0, 51, 19)));
/*  52 */     addWidget((Widget)(this.paintWidgets[1] = new PaintWidget(1, 104, 19)));
/*  53 */     addWidget((Widget)(this.paintWidgets[2] = new PaintWidget(2, 51, 47)));
/*  54 */     addWidget((Widget)(this.paintWidgets[3] = new PaintWidget(3, 104, 47)));
/*     */     
/*  56 */     addSlot((Slot)new SlotPhantom(this.filterInv, 0, 25, 21));
/*  57 */     addSlot((Slot)new SlotPhantom(this.filterInv, 1, 134, 21));
/*  58 */     addSlot((Slot)new SlotPhantom(this.filterInv, 2, 25, 49));
/*  59 */     addSlot((Slot)new SlotPhantom(this.filterInv, 3, 134, 49));
/*     */     
/*  61 */     for (int l = 0; l < 3; l++) {
/*  62 */       for (int k1 = 0; k1 < 9; k1++) {
/*  63 */         func_75146_a(new Slot(playerInventory, k1 + l * 9 + 9, 8 + k1 * 18, 84 + l * 18));
/*     */       }
/*     */     } 
/*     */     
/*  67 */     for (int i1 = 0; i1 < 9; i1++) {
/*  68 */       func_75146_a(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75132_a(ICrafting player) {
/*  74 */     super.func_75132_a(player);
/*  75 */     for (int slot = 0; slot < this.pipe.slotColors.length; slot++) {
/*  76 */       this.prevSlotColors[slot] = this.pipe.slotColors[slot];
/*  77 */       player.func_71112_a((Container)this, slot, this.pipe.slotColors[slot]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_75142_b() {
/*  86 */     super.func_75142_b();
/*     */     
/*  88 */     for (Object crafter : this.field_75149_d) {
/*  89 */       ICrafting player = (ICrafting)crafter;
/*     */       
/*  91 */       for (int slot = 0; slot < this.pipe.slotColors.length; slot++) {
/*  92 */         if (this.prevSlotColors[slot] != this.pipe.slotColors[slot]) {
/*  93 */           player.func_71112_a((Container)this, slot, this.pipe.slotColors[slot]);
/*     */         }
/*     */       } 
/*     */     } 
/*  97 */     System.arraycopy(this.pipe.slotColors, 0, this.prevSlotColors, 0, this.pipe.slotColors.length);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_75137_b(int id, int data) {
/* 103 */     (this.paintWidgets[id]).colors.setCurrent((data == 0) ? null : EnumColor.fromId(data - 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 108 */     return this.pipe.container.isUseableByPlayer(entityplayer);
/*     */   }
/*     */   
/*     */   private class PaintWidget
/*     */     extends ButtonWidget {
/*     */     private final int slot;
/* 114 */     private RevolvingList<EnumColor> colors = new RevolvingList();
/*     */     
/* 116 */     private ToolTip toolTip = new ToolTip(500, new ToolTipLine[0])
/*     */       {
/*     */         public void refresh() {
/* 119 */           ContainerEmzuliPipe.PaintWidget.this.toolTip.clear();
/* 120 */           EnumColor color = (EnumColor)ContainerEmzuliPipe.PaintWidget.this.colors.getCurrent();
/* 121 */           if (color != null) {
/* 122 */             ContainerEmzuliPipe.PaintWidget.this.toolTip.add(new ToolTipLine(String.format(StringUtils.localize("gui.pipes.emzuli.paint"), new Object[] { color.getLocalizedName() })));
/*     */           } else {
/* 124 */             ContainerEmzuliPipe.PaintWidget.this.toolTip.add(new ToolTipLine(StringUtils.localize("gui.pipes.emzuli.nopaint")));
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/*     */     public PaintWidget(int slot, int x, int y) {
/* 130 */       super(x, y, 176, 0, 20, 20);
/* 131 */       this.slot = slot;
/* 132 */       this.colors.add(null);
/* 133 */       this.colors.addAll(Arrays.asList(EnumColor.VALUES));
/*     */     }
/*     */ 
/*     */     
/*     */     public void draw(GuiBuildCraft gui, int guiX, int guiY, int mouseX, int mouseY) {
/* 138 */       super.draw(gui, guiX, guiY, mouseX, mouseY);
/* 139 */       EnumColor color = (EnumColor)this.colors.getCurrent();
/* 140 */       if (color != null) {
/* 141 */         gui.bindTexture(TextureMap.field_110576_c);
/* 142 */         gui.func_94065_a(guiX + this.x + 2, guiY + this.y + 2, BuildCraftTransport.actionPipeColor[color.ordinal()].getIcon(), 16, 16);
/*     */       } else {
/* 144 */         gui.func_73729_b(guiX + this.x + 2, guiY + this.y + 2, this.u, this.v + this.h + this.h, 16, 16);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onRelease(int mouseButton) {
/* 150 */       switch (mouseButton) {
/*     */         case 0:
/* 152 */           this.colors.rotateLeft();
/*     */           break;
/*     */         case 1:
/* 155 */           this.colors.rotateRight();
/*     */           break;
/*     */         case 2:
/* 158 */           this.colors.setCurrent(null);
/*     */           break;
/*     */       } 
/*     */       try {
/* 162 */         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
/* 163 */         DataOutputStream data = new DataOutputStream(bytes);
/* 164 */         data.writeByte(this.slot);
/* 165 */         EnumColor color = (EnumColor)this.colors.getCurrent();
/* 166 */         data.writeByte((color == null) ? 0 : (color.ordinal() + 1));
/* 167 */         PacketGuiReturn pkt = new PacketGuiReturn((IGuiReturnHandler)ContainerEmzuliPipe.this.pipe.getContainer(), bytes.toByteArray());
/* 168 */         pkt.sendPacket();
/* 169 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ToolTip getToolTip() {
/* 175 */       return this.toolTip;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gui\ContainerEmzuliPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */