/*     */ package buildcraft.core.list;
/*     */ 
/*     */ import buildcraft.api.lists.ListMatchHandler;
/*     */ import buildcraft.api.lists.ListRegistry;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ListHandlerNew
/*     */ {
/*     */   public static final int WIDTH = 9;
/*     */   public static final int HEIGHT = 2;
/*     */   
/*     */   public static class Line
/*     */   {
/*  27 */     public final ItemStack[] stacks = new ItemStack[9];
/*     */     public boolean precise;
/*     */     
/*     */     public boolean isOneStackMode() {
/*  31 */       return (this.byType || this.byMaterial);
/*     */     }
/*     */     public boolean byType; public boolean byMaterial;
/*     */     public boolean getOption(int id) {
/*  35 */       return (id == 0) ? this.precise : ((id == 1) ? this.byType : this.byMaterial);
/*     */     }
/*     */     
/*     */     public void toggleOption(int id) {
/*  39 */       if (!this.byType && !this.byMaterial && (id == 1 || id == 2)) {
/*  40 */         for (int i = 1; i < this.stacks.length; i++) {
/*  41 */           this.stacks[i] = null;
/*     */         }
/*     */       }
/*  44 */       switch (id) {
/*     */         case 0:
/*  46 */           this.precise = !this.precise;
/*     */           break;
/*     */         case 1:
/*  49 */           this.byType = !this.byType;
/*     */           break;
/*     */         case 2:
/*  52 */           this.byMaterial = !this.byMaterial;
/*     */           break;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean matches(ItemStack target) {
/*  58 */       if (this.byType || this.byMaterial) {
/*  59 */         if (this.stacks[0] == null) {
/*  60 */           return false;
/*     */         }
/*     */         
/*  63 */         List<ListMatchHandler> handlers = ListRegistry.getHandlers();
/*  64 */         ListMatchHandler.Type type = getSortingType();
/*  65 */         for (ListMatchHandler h : handlers) {
/*  66 */           if (h.matches(type, this.stacks[0], target, this.precise)) {
/*  67 */             return true;
/*     */           }
/*     */         } 
/*     */       } else {
/*  71 */         for (ItemStack s : this.stacks) {
/*  72 */           if (s != null && StackHelper.isMatchingItem(s, target, true, this.precise))
/*     */           {
/*  74 */             if (!this.precise || s.func_77960_j() == target.func_77960_j()) {
/*  75 */               return true;
/*     */             }
/*     */           }
/*     */         } 
/*     */       } 
/*  80 */       return false;
/*     */     }
/*     */     
/*     */     public ListMatchHandler.Type getSortingType() {
/*  84 */       return this.byType ? (this.byMaterial ? ListMatchHandler.Type.CLASS : ListMatchHandler.Type.TYPE) : ListMatchHandler.Type.MATERIAL;
/*     */     }
/*     */     
/*     */     public static Line fromNBT(NBTTagCompound data) {
/*  88 */       Line line = new Line();
/*     */       
/*  90 */       if (data != null && data.func_74764_b("st")) {
/*  91 */         NBTTagList l = data.func_150295_c("st", 10);
/*  92 */         for (int i = 0; i < l.func_74745_c(); i++) {
/*  93 */           line.stacks[i] = ItemStack.func_77949_a(l.func_150305_b(i));
/*     */         }
/*     */         
/*  96 */         line.precise = data.func_74767_n("Fp");
/*  97 */         line.byType = data.func_74767_n("Ft");
/*  98 */         line.byMaterial = data.func_74767_n("Fm");
/*     */       } 
/*     */       
/* 101 */       return line;
/*     */     }
/*     */     
/*     */     public NBTTagCompound toNBT() {
/* 105 */       NBTTagCompound data = new NBTTagCompound();
/* 106 */       NBTTagList stackList = new NBTTagList();
/* 107 */       for (ItemStack stack1 : this.stacks) {
/* 108 */         NBTTagCompound stack = new NBTTagCompound();
/* 109 */         if (stack1 != null) {
/* 110 */           stack1.func_77955_b(stack);
/*     */         }
/* 112 */         stackList.func_74742_a((NBTBase)stack);
/*     */       } 
/* 114 */       data.func_74782_a("st", (NBTBase)stackList);
/* 115 */       data.func_74757_a("Fp", this.precise);
/* 116 */       data.func_74757_a("Ft", this.byType);
/* 117 */       data.func_74757_a("Fm", this.byMaterial);
/* 118 */       return data;
/*     */     }
/*     */     
/*     */     public void setStack(int slotIndex, ItemStack stack) {
/* 122 */       if (slotIndex == 0 || (!this.byType && !this.byMaterial)) {
/* 123 */         if (stack != null && stack.func_77973_b() != null) {
/* 124 */           this.stacks[slotIndex] = stack.func_77946_l();
/* 125 */           (this.stacks[slotIndex]).field_77994_a = 1;
/*     */         } else {
/* 127 */           this.stacks[slotIndex] = null;
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     public ItemStack getStack(int i) {
/* 133 */       return (i >= 0 && i < this.stacks.length) ? this.stacks[i] : null;
/*     */     }
/*     */     
/*     */     public List<ItemStack> getExamples() {
/* 137 */       List<ItemStack> stackList = new ArrayList<ItemStack>();
/* 138 */       if (this.stacks[0] != null) {
/* 139 */         List<ListMatchHandler> handlers = ListRegistry.getHandlers();
/* 140 */         List<ListMatchHandler> handlersCustom = new ArrayList<ListMatchHandler>();
/* 141 */         ListMatchHandler.Type type = getSortingType();
/* 142 */         for (ListMatchHandler h : handlers) {
/* 143 */           if (h.isValidSource(type, this.stacks[0])) {
/* 144 */             List<ItemStack> examples = h.getClientExamples(type, this.stacks[0]);
/* 145 */             if (examples != null) {
/* 146 */               stackList.addAll(examples); continue;
/*     */             } 
/* 148 */             handlersCustom.add(h);
/*     */           } 
/*     */         } 
/*     */         
/* 152 */         if (handlersCustom.size() > 0) {
/* 153 */           for (Object o : Item.field_150901_e) {
/* 154 */             if (o != null && o instanceof Item) {
/* 155 */               Item i = (Item)o;
/* 156 */               List<ItemStack> examples = new ArrayList<ItemStack>();
/* 157 */               i.func_150895_a(i, CreativeTabs.field_78026_f, examples);
/* 158 */               for (ItemStack s : examples) {
/* 159 */                 for (ListMatchHandler mh : handlersCustom) {
/* 160 */                   if (mh.matches(type, this.stacks[0], s, false)) {
/* 161 */                     stackList.add(s);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 170 */         Collections.shuffle(stackList);
/*     */       } 
/* 172 */       return stackList;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Line[] getLines(ItemStack item) {
/* 181 */     NBTTagCompound data = NBTUtils.getItemData(item);
/* 182 */     if (data.func_74764_b("written") && data.func_74764_b("lines")) {
/* 183 */       NBTTagList list = data.func_150295_c("lines", 10);
/* 184 */       Line[] arrayOfLine = new Line[list.func_74745_c()];
/* 185 */       for (int j = 0; j < arrayOfLine.length; j++) {
/* 186 */         arrayOfLine[j] = Line.fromNBT(list.func_150305_b(j));
/*     */       }
/* 188 */       return arrayOfLine;
/*     */     } 
/* 190 */     Line[] lines = new Line[2];
/* 191 */     for (int i = 0; i < lines.length; i++) {
/* 192 */       lines[i] = new Line();
/*     */     }
/* 194 */     return lines;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveLines(ItemStack stackList, Line[] lines) {
/* 199 */     NBTTagCompound data = NBTUtils.getItemData(stackList);
/* 200 */     data.func_74757_a("written", true);
/* 201 */     NBTTagList lineList = new NBTTagList();
/* 202 */     for (Line l : lines) {
/* 203 */       lineList.func_74742_a((NBTBase)l.toNBT());
/*     */     }
/* 205 */     data.func_74782_a("lines", (NBTBase)lineList);
/*     */   }
/*     */   
/*     */   public static boolean matches(ItemStack stackList, ItemStack item) {
/* 209 */     NBTTagCompound data = NBTUtils.getItemData(stackList);
/* 210 */     if (data.func_74764_b("written") && data.func_74764_b("lines")) {
/* 211 */       NBTTagList list = data.func_150295_c("lines", 10);
/* 212 */       for (int i = 0; i < list.func_74745_c(); i++) {
/* 213 */         Line line = Line.fromNBT(list.func_150305_b(i));
/* 214 */         if (line.matches(item)) {
/* 215 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\list\ListHandlerNew.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */