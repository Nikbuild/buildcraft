/*     */ package buildcraft.core.lib.gui;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.core.lib.gui.slots.IPhantomSlot;
/*     */ import buildcraft.core.lib.gui.slots.SlotBase;
/*     */ import buildcraft.core.lib.gui.widgets.Widget;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.PacketGuiWidget;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ public abstract class BuildCraftContainer
/*     */   extends Container
/*     */ {
/*  36 */   private List<Widget> widgets = new ArrayList<Widget>();
/*     */   private int inventorySize;
/*     */   
/*     */   public BuildCraftContainer(int inventorySize) {
/*  40 */     this.inventorySize = inventorySize;
/*     */   }
/*     */   
/*     */   public List<Widget> getWidgets() {
/*  44 */     return this.widgets;
/*     */   }
/*     */   
/*     */   public void addSlot(Slot slot) {
/*  48 */     func_75146_a(slot);
/*     */   }
/*     */   
/*     */   public void addWidget(Widget widget) {
/*  52 */     widget.addToContainer(this);
/*  53 */     this.widgets.add(widget);
/*     */   }
/*     */   
/*     */   public void sendWidgetDataToClient(Widget widget, ICrafting player, byte[] data) {
/*  57 */     PacketGuiWidget pkt = new PacketGuiWidget(this.field_75152_c, this.widgets.indexOf(widget), data);
/*  58 */     BuildCraftCore.instance.sendToPlayer((EntityPlayer)player, (Packet)pkt);
/*     */   }
/*     */   
/*     */   public void handleWidgetClientData(int widgetId, ByteBuf data) {
/*  62 */     ByteBufInputStream byteBufInputStream = new ByteBufInputStream(data);
/*  63 */     DataInputStream stream = new DataInputStream((InputStream)byteBufInputStream);
/*     */     
/*     */     try {
/*  66 */       ((Widget)this.widgets.get(widgetId)).handleClientPacketData(stream);
/*  67 */     } catch (IOException e) {
/*  68 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75132_a(ICrafting player) {
/*  74 */     super.func_75132_a(player);
/*  75 */     for (Widget widget : this.widgets) {
/*  76 */       widget.initWidget(player);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75142_b() {
/*  82 */     super.func_75142_b();
/*  83 */     for (Widget widget : this.widgets) {
/*  84 */       for (ICrafting player : this.field_75149_d) {
/*  85 */         widget.updateWidget(player);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_75144_a(int slotNum, int mouseButton, int modifier, EntityPlayer player) {
/*  92 */     Slot slot = (slotNum < 0) ? null : this.field_75151_b.get(slotNum);
/*  93 */     if (slot instanceof IPhantomSlot) {
/*  94 */       return slotClickPhantom(slot, mouseButton, modifier, player);
/*     */     }
/*  96 */     return super.func_75144_a(slotNum, mouseButton, modifier, player);
/*     */   }
/*     */   
/*     */   private ItemStack slotClickPhantom(Slot slot, int mouseButton, int modifier, EntityPlayer player) {
/* 100 */     ItemStack stack = null;
/*     */     
/* 102 */     if (mouseButton == 2) {
/* 103 */       if (((IPhantomSlot)slot).canAdjust()) {
/* 104 */         slot.func_75215_d(null);
/*     */       }
/* 106 */     } else if (mouseButton == 0 || mouseButton == 1) {
/* 107 */       InventoryPlayer playerInv = player.field_71071_by;
/* 108 */       slot.func_75218_e();
/* 109 */       ItemStack stackSlot = slot.func_75211_c();
/* 110 */       ItemStack stackHeld = playerInv.func_70445_o();
/*     */       
/* 112 */       if (stackSlot != null) {
/* 113 */         stack = stackSlot.func_77946_l();
/*     */       }
/*     */       
/* 116 */       if (stackSlot == null) {
/* 117 */         if (stackHeld != null && slot.func_75214_a(stackHeld)) {
/* 118 */           fillPhantomSlot(slot, stackHeld, mouseButton, modifier);
/*     */         }
/* 120 */       } else if (stackHeld == null) {
/* 121 */         adjustPhantomSlot(slot, mouseButton, modifier);
/* 122 */         slot.func_82870_a(player, playerInv.func_70445_o());
/* 123 */       } else if (slot.func_75214_a(stackHeld)) {
/* 124 */         if (StackHelper.canStacksMerge(stackSlot, stackHeld)) {
/* 125 */           adjustPhantomSlot(slot, mouseButton, modifier);
/*     */         } else {
/* 127 */           fillPhantomSlot(slot, stackHeld, mouseButton, modifier);
/*     */         } 
/*     */       } 
/*     */     } 
/* 131 */     return stack;
/*     */   }
/*     */   protected void adjustPhantomSlot(Slot slot, int mouseButton, int modifier) {
/*     */     int stackSize;
/* 135 */     if (!((IPhantomSlot)slot).canAdjust()) {
/*     */       return;
/*     */     }
/* 138 */     ItemStack stackSlot = slot.func_75211_c();
/*     */     
/* 140 */     if (modifier == 1) {
/* 141 */       stackSize = (mouseButton == 0) ? ((stackSlot.field_77994_a + 1) / 2) : (stackSlot.field_77994_a * 2);
/*     */     } else {
/* 143 */       stackSize = (mouseButton == 0) ? (stackSlot.field_77994_a - 1) : (stackSlot.field_77994_a + 1);
/*     */     } 
/*     */     
/* 146 */     if (stackSize > slot.func_75219_a()) {
/* 147 */       stackSize = slot.func_75219_a();
/*     */     }
/*     */     
/* 150 */     stackSlot.field_77994_a = stackSize;
/*     */     
/* 152 */     if (stackSlot.field_77994_a <= 0) {
/* 153 */       slot.func_75215_d(null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fillPhantomSlot(Slot slot, ItemStack stackHeld, int mouseButton, int modifier) {
/* 158 */     if (!((IPhantomSlot)slot).canAdjust()) {
/*     */       return;
/*     */     }
/* 161 */     int stackSize = (mouseButton == 0) ? stackHeld.field_77994_a : 1;
/* 162 */     if (stackSize > slot.func_75219_a()) {
/* 163 */       stackSize = slot.func_75219_a();
/*     */     }
/* 165 */     ItemStack phantomStack = stackHeld.func_77946_l();
/* 166 */     phantomStack.field_77994_a = stackSize;
/*     */     
/* 168 */     slot.func_75215_d(phantomStack);
/*     */   }
/*     */   
/*     */   protected boolean shiftItemStack(ItemStack stackToShift, int start, int end) {
/* 172 */     boolean changed = false;
/* 173 */     if (stackToShift.func_77985_e()) {
/* 174 */       for (int slotIndex = start; stackToShift.field_77994_a > 0 && slotIndex < end; slotIndex++) {
/* 175 */         Slot slot = this.field_75151_b.get(slotIndex);
/* 176 */         ItemStack stackInSlot = slot.func_75211_c();
/* 177 */         if (stackInSlot != null && StackHelper.canStacksMerge(stackInSlot, stackToShift)) {
/* 178 */           int resultingStackSize = stackInSlot.field_77994_a + stackToShift.field_77994_a;
/* 179 */           int max = Math.min(stackToShift.func_77976_d(), slot.func_75219_a());
/* 180 */           if (resultingStackSize <= max) {
/* 181 */             stackToShift.field_77994_a = 0;
/* 182 */             stackInSlot.field_77994_a = resultingStackSize;
/* 183 */             slot.func_75218_e();
/* 184 */             changed = true;
/* 185 */           } else if (stackInSlot.field_77994_a < max) {
/* 186 */             stackToShift.field_77994_a -= max - stackInSlot.field_77994_a;
/* 187 */             stackInSlot.field_77994_a = max;
/* 188 */             slot.func_75218_e();
/* 189 */             changed = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 194 */     if (stackToShift.field_77994_a > 0) {
/* 195 */       for (int slotIndex = start; stackToShift.field_77994_a > 0 && slotIndex < end; slotIndex++) {
/* 196 */         Slot slot = this.field_75151_b.get(slotIndex);
/* 197 */         ItemStack stackInSlot = slot.func_75211_c();
/* 198 */         if (stackInSlot == null) {
/* 199 */           int max = Math.min(stackToShift.func_77976_d(), slot.func_75219_a());
/* 200 */           stackInSlot = stackToShift.func_77946_l();
/* 201 */           stackInSlot.field_77994_a = Math.min(stackToShift.field_77994_a, max);
/* 202 */           stackToShift.field_77994_a -= stackInSlot.field_77994_a;
/* 203 */           slot.func_75215_d(stackInSlot);
/* 204 */           slot.func_75218_e();
/* 205 */           changed = true;
/*     */         } 
/*     */       } 
/*     */     }
/* 209 */     return changed;
/*     */   }
/*     */   
/*     */   private boolean tryShiftItem(ItemStack stackToShift, int numSlots) {
/* 213 */     for (int machineIndex = 0; machineIndex < numSlots - 36; machineIndex++) {
/* 214 */       Slot slot = this.field_75151_b.get(machineIndex);
/* 215 */       if (!(slot instanceof SlotBase) || ((SlotBase)slot).canShift())
/*     */       {
/*     */         
/* 218 */         if (!(slot instanceof IPhantomSlot))
/*     */         {
/*     */           
/* 221 */           if (slot.func_75214_a(stackToShift))
/*     */           {
/*     */             
/* 224 */             if (shiftItemStack(stackToShift, machineIndex, machineIndex + 1))
/* 225 */               return true;  }  } 
/*     */       }
/*     */     } 
/* 228 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_82846_b(EntityPlayer player, int slotIndex) {
/* 233 */     ItemStack originalStack = null;
/* 234 */     Slot slot = this.field_75151_b.get(slotIndex);
/* 235 */     int numSlots = this.field_75151_b.size();
/* 236 */     if (slot != null && slot.func_75216_d()) {
/* 237 */       ItemStack stackInSlot = slot.func_75211_c();
/* 238 */       originalStack = stackInSlot.func_77946_l();
/* 239 */       if (slotIndex < numSlots - 36 || !tryShiftItem(stackInSlot, numSlots))
/*     */       {
/* 241 */         if (slotIndex >= numSlots - 36 && slotIndex < numSlots - 9) {
/* 242 */           if (!shiftItemStack(stackInSlot, numSlots - 9, numSlots)) {
/* 243 */             return null;
/*     */           }
/* 245 */         } else if (slotIndex >= numSlots - 9 && slotIndex < numSlots) {
/* 246 */           if (!shiftItemStack(stackInSlot, numSlots - 36, numSlots - 9)) {
/* 247 */             return null;
/*     */           }
/* 249 */         } else if (!shiftItemStack(stackInSlot, numSlots - 36, numSlots)) {
/* 250 */           return null;
/*     */         }  } 
/* 252 */       slot.func_75220_a(stackInSlot, originalStack);
/* 253 */       if (stackInSlot.field_77994_a <= 0) {
/* 254 */         slot.func_75215_d(null);
/*     */       } else {
/* 256 */         slot.func_75218_e();
/*     */       } 
/* 258 */       if (stackInSlot.field_77994_a == originalStack.field_77994_a) {
/* 259 */         return null;
/*     */       }
/* 261 */       slot.func_82870_a(player, stackInSlot);
/*     */     } 
/* 263 */     return originalStack;
/*     */   }
/*     */   
/*     */   public int getInventorySize() {
/* 267 */     return this.inventorySize;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\BuildCraftContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */