/*     */ package buildcraft.core.lib.inventory;
/*     */ 
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryLargeChest;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InvUtils
/*     */ {
/*     */   public static int countItems(IInventory inv, ForgeDirection side, IStackFilter filter) {
/*  39 */     int count = 0;
/*  40 */     for (IInvSlot slot : InventoryIterator.getIterable(inv, side)) {
/*  41 */       ItemStack stack = slot.getStackInSlot();
/*  42 */       if (stack != null && filter.matches(stack)) {
/*  43 */         count += stack.field_77994_a;
/*     */       }
/*     */     } 
/*  46 */     return count;
/*     */   }
/*     */   
/*     */   public static boolean containsItem(IInventory inv, ForgeDirection side, IStackFilter filter) {
/*  50 */     for (IInvSlot slot : InventoryIterator.getIterable(inv, side)) {
/*  51 */       ItemStack stack = slot.getStackInSlot();
/*  52 */       if (stack != null && filter.matches(stack)) {
/*  53 */         return true;
/*     */       }
/*     */     } 
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRoomForStack(ItemStack stack, ForgeDirection side, IInventory dest) {
/*  67 */     if (stack == null || dest == null) {
/*  68 */       return false;
/*     */     }
/*  70 */     ITransactor tran = Transactor.getTransactorFor(dest);
/*  71 */     return ((tran.add(stack, side, false)).field_77994_a > 0);
/*     */   }
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
/*     */   public static ItemStack moveOneItem(IInventory source, ForgeDirection output, IInventory dest, ForgeDirection intput, IStackFilter filter) {
/*  84 */     ITransactor imSource = Transactor.getTransactorFor(source);
/*  85 */     ItemStack stack = imSource.remove(filter, output, false);
/*  86 */     if (stack != null) {
/*  87 */       ITransactor imDest = Transactor.getTransactorFor(dest);
/*  88 */       int moved = (imDest.add(stack, intput, true)).field_77994_a;
/*  89 */       if (moved > 0) {
/*  90 */         imSource.remove(filter, output, true);
/*  91 */         return stack;
/*     */       } 
/*     */     } 
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dropItems(World world, ItemStack stack, int i, int j, int k) {
/*  99 */     if (stack == null || stack.field_77994_a <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 103 */     float f1 = 0.7F;
/* 104 */     double d = (world.field_73012_v.nextFloat() * f1) + (1.0F - f1) * 0.5D;
/* 105 */     double d1 = (world.field_73012_v.nextFloat() * f1) + (1.0F - f1) * 0.5D;
/* 106 */     double d2 = (world.field_73012_v.nextFloat() * f1) + (1.0F - f1) * 0.5D;
/* 107 */     EntityItem entityitem = new EntityItem(world, i + d, j + d1, k + d2, stack);
/* 108 */     entityitem.field_145804_b = 10;
/*     */     
/* 110 */     world.func_72838_d((Entity)entityitem);
/*     */   }
/*     */   
/*     */   public static void dropItems(World world, IInventory inv, int i, int j, int k) {
/* 114 */     for (int slot = 0; slot < inv.func_70302_i_(); slot++) {
/* 115 */       ItemStack items = inv.func_70301_a(slot);
/*     */       
/* 117 */       if (items != null && items.field_77994_a > 0) {
/* 118 */         dropItems(world, inv.func_70301_a(slot).func_77946_l(), i, j, k);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void wipeInventory(IInventory inv) {
/* 124 */     for (int slot = 0; slot < inv.func_70302_i_(); slot++) {
/* 125 */       inv.func_70299_a(slot, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public static NBTTagCompound getItemData(ItemStack stack) {
/* 130 */     NBTTagCompound nbt = stack.func_77978_p();
/* 131 */     if (nbt == null) {
/* 132 */       nbt = new NBTTagCompound();
/* 133 */       stack.func_77982_d(nbt);
/*     */     } 
/* 135 */     return nbt;
/*     */   }
/*     */   
/*     */   public static void addItemToolTip(ItemStack stack, String msg) {
/* 139 */     NBTTagCompound nbt = getItemData(stack);
/* 140 */     NBTTagCompound display = nbt.func_74775_l("display");
/* 141 */     nbt.func_74782_a("display", (NBTBase)display);
/* 142 */     NBTTagList lore = display.func_150295_c("Lore", 8);
/* 143 */     display.func_74782_a("Lore", (NBTBase)lore);
/* 144 */     lore.func_74742_a((NBTBase)new NBTTagString(msg));
/*     */   }
/*     */   
/*     */   public static void writeInvToNBT(IInventory inv, String tag, NBTTagCompound data) {
/* 148 */     NBTTagList list = new NBTTagList(); byte slot;
/* 149 */     for (slot = 0; slot < inv.func_70302_i_(); slot = (byte)(slot + 1)) {
/* 150 */       ItemStack stack = inv.func_70301_a(slot);
/* 151 */       if (stack != null) {
/* 152 */         NBTTagCompound itemTag = new NBTTagCompound();
/* 153 */         itemTag.func_74774_a("Slot", slot);
/* 154 */         stack.func_77955_b(itemTag);
/* 155 */         list.func_74742_a((NBTBase)itemTag);
/*     */       } 
/*     */     } 
/* 158 */     data.func_74782_a(tag, (NBTBase)list);
/*     */   }
/*     */   
/*     */   public static void readInvFromNBT(IInventory inv, String tag, NBTTagCompound data) {
/* 162 */     NBTTagList list = data.func_150295_c(tag, 10); byte entry;
/* 163 */     for (entry = 0; entry < list.func_74745_c(); entry = (byte)(entry + 1)) {
/* 164 */       NBTTagCompound itemTag = list.func_150305_b(entry);
/* 165 */       int slot = itemTag.func_74771_c("Slot");
/* 166 */       if (slot >= 0 && slot < inv.func_70302_i_()) {
/* 167 */         ItemStack stack = ItemStack.func_77949_a(itemTag);
/* 168 */         inv.func_70299_a(slot, stack);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void readStacksFromNBT(NBTTagCompound nbt, String name, ItemStack[] stacks) {
/* 174 */     NBTTagList nbttaglist = nbt.func_150295_c(name, 10);
/*     */     
/* 176 */     for (int i = 0; i < stacks.length; i++) {
/* 177 */       if (i < nbttaglist.func_74745_c()) {
/* 178 */         NBTTagCompound nbttagcompound2 = nbttaglist.func_150305_b(i);
/*     */         
/* 180 */         stacks[i] = ItemStack.func_77949_a(nbttagcompound2);
/*     */       } else {
/* 182 */         stacks[i] = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeStacksToNBT(NBTTagCompound nbt, String name, ItemStack[] stacks) {
/* 188 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 190 */     for (ItemStack stack : stacks) {
/* 191 */       NBTTagCompound cpt = new NBTTagCompound();
/* 192 */       nbttaglist.func_74742_a((NBTBase)cpt);
/* 193 */       if (stack != null) {
/* 194 */         stack.func_77955_b(cpt);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 199 */     nbt.func_74782_a(name, (NBTBase)nbttaglist);
/*     */   }
/*     */   
/*     */   public static ItemStack consumeItem(ItemStack stack) {
/* 203 */     if (stack.field_77994_a == 1) {
/* 204 */       if (stack.func_77973_b().hasContainerItem(stack)) {
/* 205 */         return stack.func_77973_b().getContainerItem(stack);
/*     */       }
/* 207 */       return null;
/*     */     } 
/*     */     
/* 210 */     stack.func_77979_a(1);
/*     */     
/* 212 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IInventory getInventory(IInventory inv) {
/* 224 */     if (inv instanceof TileEntityChest) {
/* 225 */       TileEntityChest adjacent = BlockUtils.getOtherDoubleChest((TileEntity)inv);
/* 226 */       if (adjacent != null) {
/* 227 */         return (IInventory)new InventoryLargeChest("", inv, (IInventory)adjacent);
/*     */       }
/* 229 */       return inv;
/*     */     } 
/* 231 */     return inv;
/*     */   }
/*     */   
/*     */   public static IInvSlot getItem(IInventory inv, IStackFilter filter) {
/* 235 */     for (IInvSlot s : InventoryIterator.getIterable(inv)) {
/* 236 */       if (s.getStackInSlot() != null && filter.matches(s.getStackInSlot())) {
/* 237 */         return s;
/*     */       }
/*     */     } 
/*     */     
/* 241 */     return null;
/*     */   }
/*     */   
/*     */   public static Iterable<IInvSlot> getItems(final IInventory inv, final IStackFilter filter) {
/* 245 */     return new Iterable<IInvSlot>()
/*     */       {
/*     */         public Iterator<IInvSlot> iterator() {
/* 248 */           return new Iterator<IInvSlot>() {
/* 249 */               private final Iterator<IInvSlot> parent = InventoryIterator.getIterable(inv).iterator();
/*     */               private boolean searched = false;
/*     */               private IInvSlot next;
/*     */               
/*     */               private void find() {
/* 254 */                 this.next = null;
/* 255 */                 this.searched = true;
/*     */                 
/* 257 */                 while (this.parent.hasNext()) {
/* 258 */                   IInvSlot s = this.parent.next();
/* 259 */                   if (s.getStackInSlot() != null && filter.matches(s.getStackInSlot())) {
/* 260 */                     this.next = s;
/*     */                     return;
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 268 */                 if (!this.searched) {
/* 269 */                   find();
/*     */                 }
/*     */                 
/* 272 */                 return (this.next != null);
/*     */               }
/*     */ 
/*     */               
/*     */               public IInvSlot next() {
/* 277 */                 if (!this.searched) {
/* 278 */                   find();
/*     */                 }
/*     */                 
/* 281 */                 IInvSlot current = this.next;
/* 282 */                 find();
/* 283 */                 return current;
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 288 */                 throw new UnsupportedOperationException("Remove not supported.");
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\InvUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */