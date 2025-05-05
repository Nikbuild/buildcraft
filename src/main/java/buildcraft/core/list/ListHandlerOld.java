/*     */ package buildcraft.core.list;
/*     */ 
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.WeakHashMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ 
/*     */ public final class ListHandlerOld
/*     */ {
/*  23 */   private static final WeakHashMap<ItemStack, StackLine[]> LINE_CACHE = (WeakHashMap)new WeakHashMap<ItemStack, StackLine>();
/*     */   
/*     */   public static class StackLine
/*     */   {
/*     */     public boolean oreWildcard = false;
/*     */     public boolean subitemsWildcard = false;
/*     */     public boolean isOre;
/*  30 */     private ItemStack[] stacks = new ItemStack[7];
/*  31 */     private ArrayList<ItemStack> ores = new ArrayList<ItemStack>();
/*  32 */     private ArrayList<ItemStack> relatedItems = new ArrayList<ItemStack>();
/*     */     
/*     */     public ItemStack getStack(int index) {
/*  35 */       if (index == 0 || (!this.oreWildcard && !this.subitemsWildcard)) {
/*  36 */         if (index < 7) {
/*  37 */           return this.stacks[index];
/*     */         }
/*  39 */         return null;
/*     */       } 
/*  41 */       if (this.oreWildcard) {
/*  42 */         if (this.ores.size() >= index) {
/*  43 */           return this.ores.get(index - 1);
/*     */         }
/*  45 */         return null;
/*     */       } 
/*     */       
/*  48 */       if (this.relatedItems.size() >= index) {
/*  49 */         return this.relatedItems.get(index - 1);
/*     */       }
/*  51 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setStack(int slot, ItemStack stack) {
/*  57 */       this.stacks[slot] = stack;
/*     */       
/*  59 */       if (stack != null) {
/*  60 */         this.stacks[slot] = this.stacks[slot].func_77946_l();
/*  61 */         (this.stacks[slot]).field_77994_a = 1;
/*     */       } 
/*     */       
/*  64 */       if (slot == 0) {
/*  65 */         this.relatedItems.clear();
/*  66 */         this.ores.clear();
/*     */         
/*  68 */         if (stack == null) {
/*  69 */           this.isOre = false;
/*     */         }
/*  71 */         else if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
/*  72 */           setClientPreviewLists();
/*     */         } else {
/*  74 */           this.isOre = ((OreDictionary.getOreIDs(this.stacks[0])).length > 0);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeToNBT(NBTTagCompound nbt) {
/*  81 */       nbt.func_74757_a("ore", this.oreWildcard);
/*  82 */       nbt.func_74757_a("sub", this.subitemsWildcard);
/*     */       
/*  84 */       for (int i = 0; i < 7; i++) {
/*  85 */         if (this.stacks[i] != null) {
/*  86 */           NBTTagCompound stackNBT = new NBTTagCompound();
/*  87 */           this.stacks[i].func_77955_b(stackNBT);
/*  88 */           nbt.func_74782_a("stacks[" + i + "]", (NBTBase)stackNBT);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void readFromNBT(NBTTagCompound nbt) {
/*  94 */       this.oreWildcard = nbt.func_74767_n("ore");
/*  95 */       this.subitemsWildcard = nbt.func_74767_n("sub");
/*     */       
/*  97 */       for (int i = 0; i < 7; i++) {
/*  98 */         if (nbt.func_74764_b("stacks[" + i + "]")) {
/*  99 */           setStack(i, ItemStack.func_77949_a(nbt.func_74775_l("stacks[" + i + "]")));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean classMatch(Item base, Item matched) {
/* 105 */       if (base.getClass() == Item.class)
/* 106 */         return (base == matched); 
/* 107 */       if (base.getClass() == matched.getClass()) {
/* 108 */         if (base instanceof ItemBlock) {
/* 109 */           Block baseBlock = ((ItemBlock)base).field_150939_a;
/* 110 */           Block matchedBlock = ((ItemBlock)matched).field_150939_a;
/*     */           
/* 112 */           if (baseBlock.getClass() == Block.class) {
/* 113 */             return (baseBlock == matchedBlock);
/*     */           }
/* 115 */           return baseBlock.equals(matchedBlock);
/*     */         } 
/*     */         
/* 118 */         return true;
/*     */       } 
/*     */       
/* 121 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean oreMatch(ItemStack base, ItemStack matched) {
/* 126 */       int[] oreIds = OreDictionary.getOreIDs(base);
/* 127 */       int[] matchesIds = OreDictionary.getOreIDs(matched);
/*     */       
/* 129 */       for (int stackId : oreIds) {
/* 130 */         for (int matchId : matchesIds) {
/* 131 */           if (stackId == matchId) {
/* 132 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 137 */       return false;
/*     */     }
/*     */     
/*     */     private void setClientPreviewLists() {
/* 141 */       Item baseItem = this.stacks[0].func_77973_b();
/*     */       
/* 143 */       int[] oreIds = OreDictionary.getOreIDs(this.stacks[0]);
/*     */       
/* 145 */       this.isOre = (oreIds.length > 0);
/*     */       
/* 147 */       for (Object o : Item.field_150901_e) {
/* 148 */         Item item = (Item)o;
/* 149 */         boolean classMatch = classMatch(baseItem, item);
/*     */         
/* 151 */         List list = new LinkedList();
/*     */         
/* 153 */         for (CreativeTabs tab : item.getCreativeTabs()) {
/* 154 */           item.func_150895_a(item, tab, list);
/*     */         }
/*     */         
/* 157 */         if (list.size() > 0) {
/* 158 */           for (Object ol : list) {
/* 159 */             ItemStack stack = (ItemStack)ol;
/*     */             
/* 161 */             if (classMatch && this.relatedItems.size() <= 7 && !StackHelper.isMatchingItemOrList(this.stacks[0], stack)) {
/* 162 */               this.relatedItems.add(stack);
/*     */             }
/*     */             
/* 165 */             if (this.isOre && this.ores.size() <= 7 && !StackHelper.isMatchingItemOrList(this.stacks[0], stack) && 
/* 166 */               oreMatch(this.stacks[0], stack)) {
/* 167 */               this.ores.add(stack);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean matches(ItemStack item) {
/* 175 */       if (this.subitemsWildcard) {
/* 176 */         if (this.stacks[0] == null) {
/* 177 */           return false;
/*     */         }
/*     */         
/* 180 */         return classMatch(this.stacks[0].func_77973_b(), item.func_77973_b());
/* 181 */       }  if (this.oreWildcard) {
/* 182 */         if (this.stacks[0] == null) {
/* 183 */           return false;
/*     */         }
/*     */         
/* 186 */         return oreMatch(this.stacks[0], item);
/*     */       } 
/* 188 */       for (ItemStack stack : this.stacks) {
/* 189 */         if (stack != null && StackHelper.isMatchingItem(stack, item, true, false)) {
/* 190 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 194 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveLine(ItemStack stack, StackLine line, int index) {
/* 204 */     NBTTagCompound nbt = NBTUtils.getItemData(stack);
/*     */     
/* 206 */     nbt.func_74757_a("written", true);
/*     */     
/* 208 */     NBTTagCompound lineNBT = new NBTTagCompound();
/* 209 */     line.writeToNBT(lineNBT);
/* 210 */     nbt.func_74782_a("line[" + index + "]", (NBTBase)lineNBT);
/*     */   }
/*     */   
/*     */   public static StackLine[] getLines(ItemStack stack) {
/* 214 */     if (LINE_CACHE.containsKey(stack)) {
/* 215 */       return LINE_CACHE.get(stack);
/*     */     }
/*     */     
/* 218 */     StackLine[] result = new StackLine[6];
/*     */     
/* 220 */     for (int i = 0; i < 6; i++) {
/* 221 */       result[i] = new StackLine();
/*     */     }
/*     */     
/* 224 */     NBTTagCompound nbt = NBTUtils.getItemData(stack);
/*     */     
/* 226 */     if (nbt.func_74764_b("written")) {
/* 227 */       for (int j = 0; j < 6; j++) {
/* 228 */         result[j].readFromNBT(nbt.func_74775_l("line[" + j + "]"));
/*     */       }
/*     */     }
/*     */     
/* 232 */     LINE_CACHE.put(stack, result);
/*     */     
/* 234 */     return result;
/*     */   }
/*     */   
/*     */   public static boolean matches(ItemStack stackList, ItemStack item) {
/* 238 */     StackLine[] lines = getLines(stackList);
/*     */     
/* 240 */     if (lines != null) {
/* 241 */       for (StackLine line : lines) {
/* 242 */         if (line != null && line.matches(item)) {
/* 243 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 248 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\list\ListHandlerOld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */