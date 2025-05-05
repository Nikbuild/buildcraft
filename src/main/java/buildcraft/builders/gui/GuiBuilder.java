/*     */ package buildcraft.builders.gui;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.builders.TileBuilder;
/*     */ import buildcraft.core.blueprints.RequirementItemStack;
/*     */ import buildcraft.core.lib.fluids.Tank;
/*     */ import buildcraft.core.lib.gui.AdvancedSlot;
/*     */ import buildcraft.core.lib.gui.GuiAdvancedInterface;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.util.EnumChatFormatting;
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
/*     */ public class GuiBuilder
/*     */   extends GuiAdvancedInterface
/*     */ {
/*  35 */   private static final ResourceLocation REGULAR_TEXTURE = new ResourceLocation("buildcraftbuilders:textures/gui/builder.png");
/*  36 */   private static final ResourceLocation BLUEPRINT_TEXTURE = new ResourceLocation("buildcraftbuilders:textures/gui/builder_blueprint.png");
/*     */   private TileBuilder builder;
/*     */   private GuiButton selectedButton;
/*     */   
/*     */   public GuiBuilder(IInventory playerInventory, TileBuilder builder) {
/*  41 */     super(new ContainerBuilder(playerInventory, builder), (IInventory)builder, BLUEPRINT_TEXTURE);
/*  42 */     this.builder = builder;
/*  43 */     this.field_146999_f = 256;
/*  44 */     this.field_147000_g = 225;
/*     */     
/*  46 */     resetNullSlots(24);
/*     */     
/*  48 */     for (int i = 0; i < 6; i++) {
/*  49 */       for (int j = 0; j < 4; j++) {
/*  50 */         this.slots.set(i * 4 + j, new SlotBuilderRequirement(this, 179 + j * 18, 18 + i * 18));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private ContainerBuilder getContainerBuilder() {
/*  56 */     return (ContainerBuilder)getContainer();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/*  61 */     super.func_146979_b(par1, par2);
/*     */     
/*  63 */     drawCenteredString(StringUtils.localize("tile.builderBlock.name"), 89, 16, 4210752);
/*  64 */     if (this.builder.func_70301_a(0) != null) {
/*  65 */       this.field_146289_q.func_78276_b(StringUtils.localize("gui.building.resources"), 8, 60, 4210752);
/*  66 */       this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 97, 4210752);
/*  67 */       this.field_146289_q.func_78276_b(StringUtils.localize("gui.needed"), 178, 7, 4210752);
/*  68 */       this.field_146289_q.func_78276_b(StringUtils.localize("gui.building.fluids"), 178, 133, 4210752);
/*     */     } 
/*     */     
/*  71 */     drawTooltips(par1, par2);
/*     */   }
/*     */   
/*     */   private void drawTooltips(int par1, int par2) {
/*  75 */     int top = this.field_147009_r + 145;
/*  76 */     for (int i = 0; i < this.builder.fluidTanks.length; i++) {
/*  77 */       int left = this.field_147003_i + 179 + 18 * i;
/*  78 */       if (par1 >= left && par2 >= top && par1 < left + 16 && par2 < left + 47) {
/*  79 */         List<String> fluidTip = new ArrayList<String>();
/*  80 */         Tank tank = this.builder.fluidTanks[i];
/*  81 */         if (tank.getFluid() != null && (tank.getFluid()).amount > 0) {
/*  82 */           fluidTip.add(tank.getFluid().getLocalizedName());
/*  83 */           if (!BuildCraftCore.hideFluidNumbers) {
/*  84 */             fluidTip.add(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + (tank.getFluid()).amount + " mB");
/*     */           }
/*     */         } else {
/*  87 */           fluidTip.add(StatCollector.func_74838_a("gui.fluidtank.empty"));
/*     */         } 
/*  89 */         drawHoveringText(fluidTip, par1 - this.field_147003_i, par2 - this.field_147009_r, this.field_146289_q);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  94 */     drawTooltipForSlotAt(par1, par2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/* 101 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 102 */     boolean isBlueprint = (this.builder.func_70301_a(0) != null);
/*     */     
/* 104 */     this.field_146297_k.field_71446_o.func_110577_a(REGULAR_TEXTURE);
/* 105 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, 176, this.field_147000_g);
/* 106 */     this.field_146297_k.field_71446_o.func_110577_a(BLUEPRINT_TEXTURE);
/* 107 */     if (isBlueprint) {
/* 108 */       func_73729_b(this.field_147003_i + 169, this.field_147009_r, 169, 0, 87, this.field_147000_g);
/*     */     }
/*     */     
/* 111 */     List<RequirementItemStack> needs = this.builder.getNeededItems();
/*     */     
/* 113 */     if (needs != null) {
/* 114 */       if (needs.size() > this.slots.size()) {
/* 115 */         (getContainerBuilder()).scrollbarWidget.hidden = false;
/* 116 */         (getContainerBuilder()).scrollbarWidget.setLength((needs.size() - this.slots.size() + 3) / 4);
/*     */       } else {
/* 118 */         (getContainerBuilder()).scrollbarWidget.hidden = true;
/*     */       } 
/*     */       
/* 121 */       int offset = (getContainerBuilder()).scrollbarWidget.getPosition() * 4;
/* 122 */       for (int s = 0; s < this.slots.size(); s++) {
/* 123 */         int ts = offset + s;
/* 124 */         if (ts >= needs.size()) {
/* 125 */           ((SlotBuilderRequirement)this.slots.get(s)).stack = null;
/*     */         } else {
/* 127 */           ((SlotBuilderRequirement)this.slots.get(s)).stack = needs.get(ts);
/*     */         } 
/*     */       } 
/*     */       
/* 131 */       for (GuiButton b : this.field_146292_n) {
/* 132 */         b.field_146125_m = true;
/*     */       }
/*     */     } else {
/* 135 */       (getContainerBuilder()).scrollbarWidget.hidden = true;
/* 136 */       for (AdvancedSlot slot : this.slots) {
/* 137 */         ((SlotBuilderRequirement)slot).stack = null;
/*     */       }
/* 139 */       for (GuiButton b : this.field_146292_n) {
/* 140 */         b.field_146125_m = false;
/*     */       }
/*     */     } 
/*     */     
/* 144 */     drawWidgets(x, y);
/*     */     
/* 146 */     if (isBlueprint) {
/* 147 */       drawBackgroundSlots(x, y);
/*     */     }
/*     */     
/* 150 */     if (isBlueprint) {
/* 151 */       int i; for (i = 0; i < this.builder.fluidTanks.length; i++) {
/* 152 */         Tank tank = this.builder.fluidTanks[i];
/* 153 */         if (tank.getFluid() != null && (tank.getFluid()).amount > 0) {
/* 154 */           drawFluid(tank.getFluid(), this.field_147003_i + 179 + 18 * i, this.field_147009_r + 145, 16, 47, tank.getCapacity());
/*     */         }
/*     */       } 
/*     */       
/* 158 */       this.field_146297_k.field_71446_o.func_110577_a(BLUEPRINT_TEXTURE);
/*     */       
/* 160 */       for (i = 0; i < this.builder.fluidTanks.length; i++) {
/* 161 */         Tank tank = this.builder.fluidTanks[i];
/* 162 */         if (tank.getFluid() != null && (tank.getFluid()).amount > 0) {
/* 163 */           func_73729_b(this.field_147003_i + 179 + 18 * i, this.field_147009_r + 145, 0, 54, 16, 47);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/* 171 */     super.func_73866_w_();
/* 172 */     for (int i = 0; i < 4; i++) {
/* 173 */       this.field_146292_n.add(new BuilderEraseButton(i, this.field_147003_i + 178 + 18 * i, this.field_147009_r + 197, 18, 18));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146286_b(int mouseX, int mouseY, int eventType) {
/* 179 */     super.func_146286_b(mouseX, mouseY, eventType);
/* 180 */     if (this.selectedButton != null && eventType == 0) {
/* 181 */       this.selectedButton.func_146118_a(mouseX, mouseY);
/* 182 */       this.selectedButton = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private class BuilderEraseButton extends GuiButton {
/*     */     private boolean clicked;
/*     */     
/*     */     public BuilderEraseButton(int id, int x, int y, int width, int height) {
/* 190 */       super(id, x, y, width, height, null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_146116_c(Minecraft mc, int x, int y) {
/* 195 */       if (super.func_146116_c(mc, x, y)) {
/* 196 */         GuiBuilder.this.selectedButton = this;
/* 197 */         this.clicked = true;
/* 198 */         BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(GuiBuilder.this.builder, "eraseFluidTank", new CommandWriter() {
/*     */                 public void write(ByteBuf data) {
/* 200 */                   data.writeInt(GuiBuilder.BuilderEraseButton.this.field_146127_k);
/*     */                 }
/*     */               }));
/* 203 */         return true;
/*     */       } 
/* 205 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_146118_a(int x, int y) {
/* 211 */       super.func_146118_a(x, y);
/* 212 */       this.clicked = false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_146112_a(Minecraft mc, int x, int y) {
/* 217 */       if (!this.field_146125_m) {
/*     */         return;
/*     */       }
/*     */       
/* 221 */       this.field_146123_n = (x >= this.field_146128_h && y >= this.field_146129_i && x < this.field_146128_h + this.field_146120_f && y < this.field_146129_i + this.field_146121_g);
/*     */       
/* 223 */       mc.field_71446_o.func_110577_a(GuiBuilder.BLUEPRINT_TEXTURE);
/* 224 */       func_73729_b(this.field_146128_h, this.field_146129_i, 0, (this.clicked ? 1 : (this.field_146123_n ? 2 : 0)) * 18, 18, 18);
/* 225 */       func_146119_b(mc, x, y);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\gui\GuiBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */