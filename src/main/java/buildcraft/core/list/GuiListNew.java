/*     */ package buildcraft.core.list;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.lists.ListMatchHandler;
/*     */ import buildcraft.core.lib.gui.AdvancedSlot;
/*     */ import buildcraft.core.lib.gui.GuiAdvancedInterface;
/*     */ import buildcraft.core.lib.gui.buttons.GuiImageButton;
/*     */ import buildcraft.core.lib.gui.buttons.IButtonClickEventListener;
/*     */ import buildcraft.core.lib.gui.buttons.IButtonClickEventTrigger;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
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
/*     */ public class GuiListNew
/*     */   extends GuiAdvancedInterface
/*     */   implements IButtonClickEventListener
/*     */ {
/*  34 */   private static final ResourceLocation TEXTURE_BASE = new ResourceLocation("buildcraftcore:textures/gui/list_new.png");
/*     */   
/*     */   private static final int BUTTON_COUNT = 3;
/*     */   
/*  38 */   private final Map<Integer, Map<ListMatchHandler.Type, List<ItemStack>>> exampleCache = new HashMap<Integer, Map<ListMatchHandler.Type, List<ItemStack>>>();
/*     */   private GuiTextField textField;
/*     */   private EntityPlayer player;
/*     */   
/*     */   private static class ListSlot extends AdvancedSlot {
/*     */     public int lineIndex;
/*     */     public int slotIndex;
/*     */     
/*     */     public ListSlot(GuiAdvancedInterface gui, int x, int y, int iLineIndex, int iSlotIndex) {
/*  47 */       super(gui, x, y);
/*     */       
/*  49 */       this.lineIndex = iLineIndex;
/*  50 */       this.slotIndex = iSlotIndex;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getItemStack() {
/*  55 */       ContainerListNew container = (ContainerListNew)this.gui.getContainer();
/*  56 */       if (this.slotIndex == 0 || !container.lines[this.lineIndex].isOneStackMode()) {
/*  57 */         return container.lines[this.lineIndex].getStack(this.slotIndex);
/*     */       }
/*  59 */       List<ItemStack> data = ((GuiListNew)this.gui).getExamplesList(this.lineIndex, container.lines[this.lineIndex].getSortingType());
/*  60 */       if (data.size() >= this.slotIndex) {
/*  61 */         return data.get(this.slotIndex - 1);
/*     */       }
/*  63 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void drawSprite(int cornerX, int cornerY) {
/*  70 */       if (!shouldDrawHighlight()) {
/*  71 */         (Minecraft.func_71410_x()).field_71446_o.func_110577_a(GuiListNew.TEXTURE_BASE);
/*  72 */         this.gui.func_73729_b(cornerX + this.x, cornerY + this.y, 176, 0, 16, 16);
/*     */       } 
/*     */       
/*  75 */       super.drawSprite(cornerX, cornerY);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldDrawHighlight() {
/*  80 */       ContainerListNew container = (ContainerListNew)this.gui.getContainer();
/*  81 */       return (this.slotIndex == 0 || !container.lines[this.lineIndex].isOneStackMode());
/*     */     }
/*     */   }
/*     */   
/*     */   public GuiListNew(EntityPlayer iPlayer) {
/*  86 */     super(new ContainerListNew(iPlayer), (IInventory)iPlayer.field_71071_by, TEXTURE_BASE);
/*     */     
/*  88 */     this.field_146999_f = 176;
/*  89 */     this.field_147000_g = 191;
/*     */     
/*  91 */     this.player = iPlayer;
/*     */   }
/*     */   
/*     */   private void clearExamplesCache(int lineId) {
/*  95 */     Map<ListMatchHandler.Type, List<ItemStack>> exampleList = this.exampleCache.get(Integer.valueOf(lineId));
/*  96 */     if (exampleList != null) {
/*  97 */       exampleList.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   private List<ItemStack> getExamplesList(int lineId, ListMatchHandler.Type type) {
/* 102 */     Map<ListMatchHandler.Type, List<ItemStack>> exampleList = this.exampleCache.get(Integer.valueOf(lineId));
/* 103 */     if (exampleList == null) {
/* 104 */       exampleList = new EnumMap<ListMatchHandler.Type, List<ItemStack>>(ListMatchHandler.Type.class);
/* 105 */       this.exampleCache.put(Integer.valueOf(lineId), exampleList);
/*     */     } 
/*     */     
/* 108 */     ContainerListNew container = (ContainerListNew)getContainer();
/*     */     
/* 110 */     if (!exampleList.containsKey(type)) {
/* 111 */       List<ItemStack> examples = container.lines[lineId].getExamples();
/* 112 */       ItemStack input = (container.lines[lineId]).stacks[0];
/* 113 */       if (input != null) {
/* 114 */         List<ItemStack> repetitions = new ArrayList<ItemStack>();
/* 115 */         for (ItemStack is : examples) {
/* 116 */           if (StackHelper.isMatchingItem(input, is, true, false)) {
/* 117 */             repetitions.add(is);
/*     */           }
/*     */         } 
/* 120 */         examples.removeAll(repetitions);
/*     */       } 
/* 122 */       exampleList.put(type, examples);
/*     */     } 
/* 124 */     return exampleList.get(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/* 129 */     super.func_73866_w_();
/*     */     
/* 131 */     this.exampleCache.clear();
/* 132 */     this.slots.clear();
/* 133 */     this.field_146292_n.clear();
/*     */     
/* 135 */     for (int sy = 0; sy < 2; sy++) {
/* 136 */       for (int sx = 0; sx < 9; sx++) {
/* 137 */         this.slots.add(new ListSlot(this, 8 + sx * 18, 32 + sy * 33, sy, sx));
/*     */       }
/* 139 */       int bOff = sy * 3;
/* 140 */       int bOffX = this.field_147003_i + 8 + 162 - 33;
/* 141 */       int bOffY = this.field_147009_r + 32 + sy * 33 + 18;
/*     */       
/* 143 */       this.field_146292_n.add(new GuiImageButton(bOff + 0, bOffX, bOffY, 11, TEXTURE_BASE, 176, 16, 176, 28));
/* 144 */       this.field_146292_n.add(new GuiImageButton(bOff + 1, bOffX + 11, bOffY, 11, TEXTURE_BASE, 176, 16, 185, 28));
/* 145 */       this.field_146292_n.add(new GuiImageButton(bOff + 2, bOffX + 22, bOffY, 11, TEXTURE_BASE, 176, 16, 194, 28));
/*     */     } 
/*     */     
/* 148 */     for (Object o : this.field_146292_n) {
/* 149 */       GuiImageButton b = (GuiImageButton)o;
/* 150 */       int lineId = b.field_146127_k / 3;
/* 151 */       int buttonId = b.field_146127_k % 3;
/* 152 */       if (((ContainerListNew)getContainer()).lines[lineId].getOption(buttonId)) {
/* 153 */         b.activate();
/*     */       }
/*     */       
/* 156 */       b.registerListener(this);
/*     */     } 
/*     */     
/* 159 */     this.textField = new GuiTextField(this.field_146289_q, 10, 10, 156, 12);
/* 160 */     this.textField.func_146203_f(32);
/* 161 */     this.textField.func_146180_a(BuildCraftCore.listItem.getLabel(this.player.func_71045_bC()));
/* 162 */     this.textField.func_146195_b(false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/* 167 */     super.func_146976_a(f, x, y);
/*     */     
/* 169 */     ContainerListNew containerL = (ContainerListNew)getContainer();
/* 170 */     for (int i = 0; i < 2; i++) {
/* 171 */       if (containerL.lines[i].isOneStackMode()) {
/* 172 */         func_73729_b(this.field_147003_i + 6, this.field_147009_r + 30 + i * 33, 0, this.field_147000_g, 20, 20);
/*     */       }
/*     */     } 
/*     */     
/* 176 */     drawBackgroundSlots(x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/* 181 */     super.func_146979_b(par1, par2);
/*     */     
/* 183 */     this.textField.func_146194_f();
/* 184 */     drawTooltipForSlotAt(par1, par2);
/*     */   }
/*     */   
/*     */   private boolean isCarryingNonEmptyList() {
/* 188 */     ItemStack stack = this.field_146297_k.field_71439_g.field_71071_by.func_70445_o();
/* 189 */     return (stack != null && stack.func_77973_b() instanceof buildcraft.core.ItemList && stack.func_77978_p() != null);
/*     */   }
/*     */   
/*     */   private boolean hasListEquipped() {
/* 193 */     return (this.field_146297_k.field_71439_g.func_71045_bC() != null && this.field_146297_k.field_71439_g.func_71045_bC().func_77973_b() instanceof buildcraft.core.ItemList);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int x, int y, int b) {
/* 198 */     super.func_73864_a(x, y, b);
/*     */     
/* 200 */     if (isCarryingNonEmptyList() || !hasListEquipped()) {
/*     */       return;
/*     */     }
/*     */     
/* 204 */     AdvancedSlot slot = getSlotAtLocation(x, y);
/* 205 */     ContainerListNew container = (ContainerListNew)getContainer();
/*     */     
/* 207 */     if (slot instanceof ListSlot) {
/* 208 */       container.setStack(((ListSlot)slot).lineIndex, ((ListSlot)slot).slotIndex, this.field_146297_k.field_71439_g.field_71071_by.func_70445_o());
/* 209 */       clearExamplesCache(((ListSlot)slot).lineIndex);
/*     */     } 
/*     */     
/* 212 */     this.textField.func_146192_a(x - this.field_147003_i, y - this.field_147009_r, b);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleButtonClick(IButtonClickEventTrigger sender, int id) {
/* 217 */     int buttonId = id % 3;
/* 218 */     int lineId = id / 3;
/*     */     
/* 220 */     ContainerListNew container = (ContainerListNew)getContainer();
/* 221 */     container.switchButton(lineId, buttonId);
/* 222 */     clearExamplesCache(lineId);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char c, int i) {
/* 227 */     if (this.textField.func_146206_l()) {
/* 228 */       if (c == '\r' || c == '\033') {
/* 229 */         this.textField.func_146195_b(false);
/*     */       } else {
/* 231 */         this.textField.func_146201_a(c, i);
/* 232 */         ((ContainerListNew)this.container).setLabel(this.textField.func_146179_b());
/*     */       } 
/*     */     } else {
/* 235 */       super.func_73869_a(c, i);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\list\GuiListNew.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */