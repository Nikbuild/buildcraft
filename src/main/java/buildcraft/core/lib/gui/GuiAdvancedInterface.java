/*     */ package buildcraft.core.lib.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ public abstract class GuiAdvancedInterface
/*     */   extends GuiBuildCraft
/*     */ {
/*  26 */   public ArrayList<AdvancedSlot> slots = new ArrayList<AdvancedSlot>();
/*     */   
/*     */   public GuiAdvancedInterface(BuildCraftContainer container, IInventory inventory, ResourceLocation texture) {
/*  29 */     super(container, inventory, texture);
/*     */   }
/*     */   
/*     */   public int getSlotIndexAtLocation(int i, int j) {
/*  33 */     int x = i - this.field_147003_i;
/*  34 */     int y = j - this.field_147009_r;
/*     */     
/*  36 */     for (int position = 0; position < this.slots.size(); position++) {
/*  37 */       AdvancedSlot s = this.slots.get(position);
/*     */       
/*  39 */       if (s != null && x >= s.x && x <= s.x + 16 && y >= s.y && y <= s.y + 16) {
/*  40 */         return position;
/*     */       }
/*     */     } 
/*  43 */     return -1;
/*     */   }
/*     */   
/*     */   public AdvancedSlot getSlotAtLocation(int i, int j) {
/*  47 */     int id = getSlotIndexAtLocation(i, j);
/*     */     
/*  49 */     if (id != -1) {
/*  50 */       return this.slots.get(id);
/*     */     }
/*  52 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isMouseOverSlot(AdvancedSlot slot, int mouseX, int mouseY) {
/*  57 */     int realMouseX = mouseX - this.field_147003_i;
/*  58 */     int realMouseY = mouseY - this.field_147009_r;
/*  59 */     return (realMouseX >= slot.x - 1 && realMouseX < slot.x + 16 + 1 && realMouseY >= slot.y - 1 && realMouseY < slot.y + 16 + 1);
/*     */   }
/*     */   
/*     */   protected void drawSlotHighlight(AdvancedSlot slot, int mouseX, int mouseY) {
/*  63 */     if (isMouseOverSlot(slot, mouseX, mouseY) && slot.shouldDrawHighlight()) {
/*  64 */       GL11.glDisable(2896);
/*  65 */       GL11.glDisable(2929);
/*  66 */       GL11.glColorMask(true, true, true, false);
/*  67 */       func_73733_a(this.field_147003_i + slot.x, this.field_147009_r + slot.y, this.field_147003_i + slot.x + 16, this.field_147009_r + slot.y + 16, -2130706433, -2130706433);
/*  68 */       GL11.glColorMask(true, true, true, true);
/*  69 */       GL11.glEnable(2896);
/*  70 */       GL11.glEnable(2929);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawBackgroundSlots(int mouseX, int mouseY) {
/*  75 */     RenderHelper.func_74520_c();
/*  76 */     GL11.glPushMatrix();
/*  77 */     GL11.glPushAttrib(4096);
/*  78 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  79 */     GL11.glEnable(32826);
/*  80 */     int i1 = 240;
/*  81 */     int k1 = 240;
/*  82 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, i1 / 1.0F, k1 / 1.0F);
/*  83 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  85 */     if (this.slots != null) {
/*  86 */       for (AdvancedSlot slot : this.slots) {
/*  87 */         if (slot != null) {
/*  88 */           slot.drawSprite(this.field_147003_i, this.field_147009_r);
/*  89 */           drawSlotHighlight(slot, mouseX, mouseY);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  94 */     GL11.glPopAttrib();
/*  95 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void drawTooltipForSlotAt(int mouseX, int mouseY) {
/*  99 */     AdvancedSlot slot = getSlotAtLocation(mouseX, mouseY);
/*     */     
/* 101 */     if (slot != null) {
/* 102 */       slot.drawTooltip(this, mouseX, mouseY);
/* 103 */       RenderHelper.func_74520_c();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawTooltip(String caption, int mouseX, int mouseY) {
/* 108 */     if (caption.length() > 0) {
/* 109 */       int i2 = mouseX - this.field_147003_i;
/* 110 */       int k2 = mouseY - this.field_147009_r;
/* 111 */       func_146279_a(caption, i2, k2);
/* 112 */       RenderHelper.func_74520_c();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static RenderItem getItemRenderer() {
/* 117 */     return field_146296_j;
/*     */   }
/*     */   
/*     */   public int getXSize() {
/* 121 */     return this.field_146999_f;
/*     */   }
/*     */   
/*     */   public int getYSize() {
/* 125 */     return this.field_147000_g;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146285_a(ItemStack stack, int x, int y) {
/* 130 */     super.func_146285_a(stack, x, y);
/*     */   }
/*     */   
/*     */   public void drawStack(ItemStack item, int x, int y) {
/* 134 */     Minecraft mc = Minecraft.func_71410_x();
/*     */     
/* 136 */     if (item != null) {
/* 137 */       GL11.glEnable(2896);
/* 138 */       float prevZ = (getItemRenderer()).field_77023_b;
/* 139 */       (getItemRenderer()).field_77023_b = 200.0F;
/* 140 */       getItemRenderer().func_82406_b(getFontRenderer(), mc.field_71446_o, item, x, y);
/* 141 */       getItemRenderer().func_77021_b(getFontRenderer(), mc.field_71446_o, item, x, y);
/* 142 */       (getItemRenderer()).field_77023_b = prevZ;
/* 143 */       GL11.glDisable(2896);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
/* 149 */     super.func_73864_a(mouseX, mouseY, mouseButton);
/*     */     
/* 151 */     AdvancedSlot slot = getSlotAtLocation(mouseX, mouseY);
/*     */     
/* 153 */     if (slot != null && slot.isDefined()) {
/* 154 */       slotClicked(slot, mouseButton);
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetNullSlots(int size) {
/* 159 */     this.slots.clear();
/*     */     
/* 161 */     for (int i = 0; i < size; i++)
/* 162 */       this.slots.add(null); 
/*     */   }
/*     */   
/*     */   protected void slotClicked(AdvancedSlot slot, int mouseButton) {}
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\GuiAdvancedInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */