/*     */ package buildcraft.core.lib.inventory;
/*     */ 
/*     */ import buildcraft.api.core.INBTStoreable;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ public class SimpleInventory
/*     */   implements IInventory, INBTStoreable
/*     */ {
/*     */   private final ItemStack[] contents;
/*     */   private final String name;
/*     */   private final int stackLimit;
/*  29 */   private final LinkedList<TileEntity> listener = new LinkedList<TileEntity>();
/*     */   
/*     */   public SimpleInventory(int size, String invName, int invStackLimit) {
/*  32 */     this.contents = new ItemStack[size];
/*  33 */     this.name = invName;
/*  34 */     this.stackLimit = invStackLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  39 */     return this.contents.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slotId) {
/*  44 */     return this.contents[slotId];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slotId, int count) {
/*  49 */     if (slotId < this.contents.length && this.contents[slotId] != null) {
/*  50 */       if ((this.contents[slotId]).field_77994_a > count) {
/*  51 */         ItemStack result = this.contents[slotId].func_77979_a(count);
/*  52 */         func_70296_d();
/*  53 */         return result;
/*     */       } 
/*  55 */       if ((this.contents[slotId]).field_77994_a < count) {
/*  56 */         return null;
/*     */       }
/*  58 */       ItemStack stack = this.contents[slotId];
/*  59 */       func_70299_a(slotId, null);
/*  60 */       return stack;
/*     */     } 
/*  62 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slotId, ItemStack itemstack) {
/*  67 */     if (slotId >= this.contents.length) {
/*     */       return;
/*     */     }
/*  70 */     this.contents[slotId] = itemstack;
/*     */     
/*  72 */     if (itemstack != null && itemstack.field_77994_a > func_70297_j_()) {
/*  73 */       itemstack.field_77994_a = func_70297_j_();
/*     */     }
/*  75 */     func_70296_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  80 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/*  85 */     return this.stackLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70305_f() {}
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound data) {
/* 103 */     if (data.func_74764_b("items")) {
/*     */ 
/*     */       
/* 106 */       readFromNBT(data, "items");
/*     */     } else {
/* 108 */       readFromNBT(data, "Items");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound data, String tag) {
/* 113 */     NBTTagList nbttaglist = data.func_150295_c(tag, 10);
/*     */     
/* 115 */     for (int j = 0; j < nbttaglist.func_74745_c(); j++) {
/* 116 */       int index; NBTTagCompound slot = nbttaglist.func_150305_b(j);
/*     */       
/* 118 */       if (slot.func_74764_b("index")) {
/* 119 */         index = slot.func_74762_e("index");
/*     */       } else {
/* 121 */         index = slot.func_74771_c("Slot");
/*     */       } 
/* 123 */       if (index >= 0 && index < this.contents.length) {
/* 124 */         func_70299_a(index, ItemStack.func_77949_a(slot));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound data) {
/* 131 */     writeToNBT(data, "Items");
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound data, String tag) {
/* 135 */     NBTTagList slots = new NBTTagList(); byte index;
/* 136 */     for (index = 0; index < this.contents.length; index = (byte)(index + 1)) {
/* 137 */       if (this.contents[index] != null && (this.contents[index]).field_77994_a > 0) {
/* 138 */         NBTTagCompound slot = new NBTTagCompound();
/* 139 */         slots.func_74742_a((NBTBase)slot);
/* 140 */         slot.func_74774_a("Slot", index);
/* 141 */         this.contents[index].func_77955_b(slot);
/*     */       } 
/*     */     } 
/* 144 */     data.func_74782_a(tag, (NBTBase)slots);
/*     */   }
/*     */   
/*     */   public void addListener(TileEntity listner) {
/* 148 */     this.listener.add(listner);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slotId) {
/* 153 */     if (this.contents[slotId] == null) {
/* 154 */       return null;
/*     */     }
/*     */     
/* 157 */     ItemStack stackToTake = this.contents[slotId];
/* 158 */     func_70299_a(slotId, null);
/* 159 */     return stackToTake;
/*     */   }
/*     */   
/*     */   public ItemStack[] getItemStacks() {
/* 163 */     return this.contents;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 168 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 173 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/* 178 */     for (TileEntity handler : this.listener)
/* 179 */       handler.func_70296_d(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\SimpleInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */