/*     */ package buildcraft.core.list;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.core.lib.gui.AdvancedSlot;
/*     */ import buildcraft.core.lib.gui.GuiAdvancedInterface;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiListOld
/*     */   extends GuiAdvancedInterface
/*     */ {
/*  23 */   private static final ResourceLocation TEXTURE_BASE = new ResourceLocation("buildcraftcore:textures/gui/list.png");
/*     */   private GuiTextField textField;
/*     */   private EntityPlayer player;
/*     */   
/*     */   private static class MainSlot
/*     */     extends AdvancedSlot
/*     */   {
/*     */     public int lineIndex;
/*     */     
/*     */     public MainSlot(GuiAdvancedInterface gui, int x, int y, int iLineIndex) {
/*  33 */       super(gui, x, y);
/*     */       
/*  35 */       this.lineIndex = iLineIndex;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getItemStack() {
/*  40 */       ContainerListOld container = (ContainerListOld)this.gui.getContainer();
/*     */       
/*  42 */       return container.lines[this.lineIndex].getStack(0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SecondarySlot extends AdvancedSlot {
/*     */     public int lineIndex;
/*     */     public int slotIndex;
/*     */     
/*     */     public SecondarySlot(GuiAdvancedInterface gui, int x, int y, int iLineIndex, int iSlotIndex) {
/*  51 */       super(gui, x, y);
/*     */       
/*  53 */       this.lineIndex = iLineIndex;
/*  54 */       this.slotIndex = iSlotIndex;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getItemStack() {
/*  59 */       ContainerListOld container = (ContainerListOld)this.gui.getContainer();
/*     */       
/*  61 */       if (this.slotIndex == 6 && container.lines[this.lineIndex].getStack(7) != null) {
/*  62 */         return null;
/*     */       }
/*     */       
/*  65 */       return container.lines[this.lineIndex].getStack(this.slotIndex);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Button
/*     */     extends AdvancedSlot {
/*     */     public int line;
/*     */     public int kind;
/*     */     private String desc;
/*     */     
/*     */     public Button(GuiAdvancedInterface gui, int x, int y, int iLine, int iKind, String iDesc) {
/*  76 */       super(gui, x, y);
/*     */       
/*  78 */       this.line = iLine;
/*  79 */       this.kind = iKind;
/*  80 */       this.desc = iDesc;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDescription() {
/*  85 */       return this.desc;
/*     */     }
/*     */   }
/*     */   
/*     */   public GuiListOld(EntityPlayer iPlayer) {
/*  90 */     super(new ContainerListOld(iPlayer), (IInventory)iPlayer.field_71071_by, TEXTURE_BASE);
/*     */     
/*  92 */     this.field_146999_f = 176;
/*  93 */     this.field_147000_g = 241;
/*     */     
/*  95 */     for (int sy = 0; sy < 6; sy++) {
/*  96 */       this.slots.add(new MainSlot(this, 44, 31 + sy * 18, sy));
/*     */       
/*  98 */       for (int sx = 1; sx < 7; sx++) {
/*  99 */         this.slots.add(new SecondarySlot(this, 44 + sx * 18, 31 + sy * 18, sy, sx));
/*     */       }
/*     */       
/* 102 */       this.slots.add(new Button(this, 8, 31 + sy * 18, sy, 0, "gui.list.metadata"));
/* 103 */       this.slots.add(new Button(this, 26, 31 + sy * 18, sy, 1, "gui.list.oredict"));
/*     */     } 
/*     */     
/* 106 */     this.player = iPlayer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/* 111 */     super.func_73866_w_();
/*     */     
/* 113 */     this.textField = new GuiTextField(this.field_146289_q, 10, 10, 156, 12);
/* 114 */     this.textField.func_146203_f(32);
/* 115 */     this.textField.func_146180_a(BuildCraftCore.listItem.getLabel(this.player.func_71045_bC()));
/* 116 */     this.textField.func_146195_b(false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/* 121 */     super.func_146976_a(f, x, y);
/*     */     
/* 123 */     ContainerListOld container = (ContainerListOld)getContainer();
/*     */     
/* 125 */     bindTexture(TEXTURE_BASE);
/*     */     int i;
/* 127 */     for (i = 0; i < 6; i++) {
/* 128 */       if ((container.lines[i]).subitemsWildcard) {
/* 129 */         func_73729_b(this.field_147003_i + 7, this.field_147009_r + 30 + 18 * i, 194, 18, 18, 18);
/*     */       } else {
/* 131 */         func_73729_b(this.field_147003_i + 7, this.field_147009_r + 30 + 18 * i, 194, 0, 18, 18);
/*     */       } 
/*     */       
/* 134 */       if ((container.lines[i]).isOre) {
/* 135 */         if ((container.lines[i]).oreWildcard) {
/* 136 */           func_73729_b(this.field_147003_i + 25, this.field_147009_r + 30 + 18 * i, 176, 18, 18, 18);
/*     */         } else {
/* 138 */           func_73729_b(this.field_147003_i + 25, this.field_147009_r + 30 + 18 * i, 176, 0, 18, 18);
/*     */         } 
/*     */       }
/*     */       
/* 142 */       if ((container.lines[i]).subitemsWildcard || (container.lines[i]).oreWildcard) {
/* 143 */         for (int j = 0; j < 6; j++) {
/* 144 */           func_73729_b(this.field_147003_i + 62 + 18 * j, this.field_147009_r + 31 + 18 * i, 195, 37, 16, 16);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 149 */     drawBackgroundSlots(x, y);
/*     */     
/* 151 */     bindTexture(TEXTURE_BASE);
/*     */     
/* 153 */     for (i = 0; i < 6; i++) {
/* 154 */       if (container.lines[i].getStack(7) != null) {
/* 155 */         func_73729_b(this.field_147003_i + 152, this.field_147009_r + 31 + 18 * i, 177, 37, 16, 16);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/* 162 */     super.func_146979_b(par1, par2);
/*     */     
/* 164 */     this.textField.func_146194_f();
/*     */     
/* 166 */     drawTooltipForSlotAt(par1, par2);
/*     */   }
/*     */   
/*     */   private boolean isCarryingList() {
/* 170 */     ItemStack stack = this.field_146297_k.field_71439_g.field_71071_by.func_70445_o();
/* 171 */     return (stack != null && stack.func_77973_b() instanceof buildcraft.core.ItemList);
/*     */   }
/*     */   
/*     */   private boolean hasListEquipped() {
/* 175 */     return (this.field_146297_k.field_71439_g.func_71045_bC() != null && this.field_146297_k.field_71439_g.func_71045_bC().func_77973_b() instanceof buildcraft.core.ItemList);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int x, int y, int b) {
/* 180 */     super.func_73864_a(x, y, b);
/*     */     
/* 182 */     if (isCarryingList() || !hasListEquipped()) {
/*     */       return;
/*     */     }
/*     */     
/* 186 */     AdvancedSlot slot = getSlotAtLocation(x, y);
/* 187 */     ContainerListOld container = (ContainerListOld)getContainer();
/*     */     
/* 189 */     if (slot instanceof MainSlot) {
/* 190 */       container.setStack(((MainSlot)slot).lineIndex, 0, this.field_146297_k.field_71439_g.field_71071_by.func_70445_o());
/* 191 */     } else if (slot instanceof SecondarySlot) {
/* 192 */       container.setStack(((SecondarySlot)slot).lineIndex, ((SecondarySlot)slot).slotIndex, this.field_146297_k.field_71439_g.field_71071_by
/* 193 */           .func_70445_o());
/* 194 */     } else if (slot instanceof Button) {
/* 195 */       Button button = (Button)slot;
/*     */       
/* 197 */       container.switchButton(button.line, button.kind);
/*     */     } 
/*     */     
/* 200 */     this.textField.func_146192_a(x - this.field_147003_i, y - this.field_147009_r, b);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char c, int i) {
/* 205 */     if (this.textField.func_146206_l()) {
/* 206 */       if (c == '\r' || c == '\033') {
/* 207 */         this.textField.func_146195_b(false);
/*     */       } else {
/* 209 */         this.textField.func_146201_a(c, i);
/* 210 */         ((ContainerListOld)this.container).setLabel(this.textField.func_146179_b());
/*     */       } 
/*     */     } else {
/* 213 */       super.func_73869_a(c, i);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\list\GuiListOld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */