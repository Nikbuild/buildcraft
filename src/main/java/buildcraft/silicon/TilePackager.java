/*     */ package buildcraft.silicon;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.BuildCraftSilicon;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.inventory.InventoryIterator;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import gnu.trove.map.hash.TObjectIntHashMap;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class TilePackager
/*     */   extends TileBuildCraft
/*     */   implements ISidedInventory {
/*     */   private class Requirement {
/*     */     public final IInventory location;
/*     */     public final int slot;
/*     */     
/*     */     public Requirement(IInventory location, int slot) {
/*  35 */       this.location = location;
/*  36 */       this.slot = slot;
/*     */     }
/*     */     
/*     */     public boolean isValid() {
/*  40 */       return (this.location.func_70302_i_() > this.slot && (!(this.location instanceof TileEntity) || !((TileEntity)this.location).func_145837_r()));
/*     */     }
/*     */     
/*     */     public ItemStack getStack() {
/*  44 */       return this.location.func_70301_a(this.slot);
/*     */     }
/*     */     
/*     */     public ItemStack decrStackSize(int amount) {
/*  48 */       return this.location.func_70298_a(this.slot, amount);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object other) {
/*  53 */       if (other == null || !(other instanceof Requirement)) {
/*  54 */         return false;
/*     */       }
/*     */       
/*  57 */       Requirement r = (Requirement)other;
/*     */       
/*  59 */       return (r.location.equals(this.location) && r.slot == this.slot);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  64 */       return this.location.hashCode() + this.slot * 17;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  69 */   private static final int[] SLOTS = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11 };
/*     */   
/*  71 */   public SimpleInventory inventoryPublic = new SimpleInventory(12, "Packager", 64);
/*  72 */   public SimpleInventory inventoryPattern = new SimpleInventory(9, "Packager", 64);
/*     */   
/*  74 */   private Requirement[] requirements = new Requirement[9];
/*     */   private int patternsSet;
/*  76 */   private int updateTime = BuildCraftCore.random.nextInt(5);
/*     */   
/*     */   public boolean isPatternSlotSet(int p) {
/*  79 */     return ((this.patternsSet & 1 << p) != 0);
/*     */   }
/*     */   
/*     */   public void setPatternSlot(int p, boolean v) {
/*  83 */     if (v) {
/*  84 */       this.patternsSet |= 1 << p;
/*     */     } else {
/*  86 */       this.patternsSet &= 1 << p ^ 0xFFFFFFFF;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  92 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  96 */     if (this.updateTime++ % 5 == 0 && this.patternsSet > 0) {
/*  97 */       attemptCrafting(this.inventoryPublic.func_70301_a(9));
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean validMissing(Requirement r, int missingCount) {
/* 102 */     ItemStack inputStack = r.getStack();
/* 103 */     if (inputStack != null && inputStack.field_77994_a >= missingCount) {
/*     */       
/* 105 */       for (int j = 0; j < 9; j++) {
/* 106 */         ItemStack comparedStack = this.inventoryPattern.func_70301_a(j);
/* 107 */         if (isPatternSlotSet(j) && comparedStack != null && StackHelper.isMatchingItem(inputStack, comparedStack, true, false)) {
/* 108 */           return false;
/*     */         }
/*     */       } 
/* 111 */       return true;
/*     */     } 
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean attemptCrafting(ItemStack input) {
/*     */     ItemStack pkg;
/* 120 */     if (this.inventoryPublic.func_70301_a(11) != null) {
/* 121 */       return false;
/*     */     }
/*     */     
/* 124 */     if (input == null || input.field_77994_a == 0 || (input.func_77973_b() != Items.field_151121_aF && !(input.func_77973_b() instanceof ItemPackage))) {
/* 125 */       return false;
/*     */     }
/*     */     
/* 128 */     if (input.func_77973_b() instanceof ItemPackage) {
/* 129 */       NBTTagCompound inputTag = NBTUtils.getItemData(input);
/*     */       
/* 131 */       for (int k = 0; k < 9; k++) {
/* 132 */         if (isPatternSlotSet(k) && inputTag.func_74764_b("item" + k)) {
/* 133 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     TObjectIntHashMap<Requirement> reqCounts = new TObjectIntHashMap(9);
/* 139 */     int missingCount = 0;
/*     */     
/* 141 */     int filteredReqsToFulfill = 0;
/*     */ 
/*     */ 
/*     */     
/* 145 */     int i = 0; while (true) { if (i < 9) {
/* 146 */         if (isPatternSlotSet(i))
/* 147 */         { ItemStack inputStack = this.inventoryPattern.func_70301_a(i);
/* 148 */           if (inputStack != null) {
/* 149 */             filteredReqsToFulfill++;
/*     */           } else {
/* 151 */             missingCount++;
/* 152 */             this.requirements[i] = null;
/*     */             
/*     */             i++;
/*     */           } 
/* 156 */           Requirement r = this.requirements[i];
/* 157 */           if (r != null)
/*     */           {
/*     */             
/* 160 */             if (!r.isValid()) {
/* 161 */               this.requirements[i] = null;
/*     */             
/*     */             }
/* 164 */             else if (r.getStack() == null) {
/* 165 */               this.requirements[i] = null;
/*     */             
/*     */             }
/* 168 */             else if (inputStack != null && 
/* 169 */               !StackHelper.isMatchingItem(inputStack, r.getStack(), true, false)) {
/* 170 */               this.requirements[i] = null;
/*     */             }
/*     */             else {
/*     */               
/* 174 */               reqCounts.adjustOrPutValue(this.requirements[i], 1, 1);
/* 175 */               filteredReqsToFulfill--;
/*     */             }  }  }
/* 177 */         else { this.requirements[i] = null; }
/*     */       
/*     */       } else {
/*     */         break;
/*     */       }  i++; }
/* 182 */      for (Requirement r : (Requirement[])reqCounts.keys((Object[])new Requirement[reqCounts.size()])) {
/* 183 */       if ((r.getStack()).field_77994_a < reqCounts.get(r)) {
/* 184 */         int allowedAmount = 0;
/* 185 */         for (int k = 0; k < 9; k++) {
/*     */           
/* 187 */           allowedAmount--;
/* 188 */           if (this.requirements[k] != null && this.requirements[k].equals(r) && allowedAmount < 0) {
/* 189 */             this.requirements[k] = null;
/* 190 */             filteredReqsToFulfill++;
/*     */           } 
/*     */         } 
/*     */         
/* 194 */         reqCounts.remove(r);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 200 */     if (filteredReqsToFulfill > 0) {
/* 201 */       for (i = 0; i < 9 && 
/* 202 */         filteredReqsToFulfill != 0; i++) {
/*     */ 
/*     */         
/* 205 */         if (isPatternSlotSet(i) && this.requirements[i] == null) {
/* 206 */           ItemStack inputStack = this.inventoryPattern.func_70301_a(i);
/* 207 */           if (inputStack != null) {
/* 208 */             for (int k = 0; k < 9; k++) {
/* 209 */               ItemStack comparedStack = this.inventoryPublic.func_70301_a(k);
/* 210 */               if (comparedStack != null) {
/*     */ 
/*     */                 
/* 213 */                 Requirement r = new Requirement((IInventory)this, k);
/* 214 */                 if (comparedStack.field_77994_a > reqCounts.get(r))
/*     */                 {
/*     */ 
/*     */                   
/* 218 */                   if (StackHelper.isMatchingItem(inputStack, comparedStack, true, false)) {
/* 219 */                     this.requirements[i] = r;
/* 220 */                     filteredReqsToFulfill--;
/* 221 */                     reqCounts.adjustOrPutValue(r, 1, 1);
/*     */                     break;
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/* 231 */     Map<ForgeDirection, IInventory> invs = new EnumMap<ForgeDirection, IInventory>(ForgeDirection.class);
/* 232 */     if (filteredReqsToFulfill > 0 || missingCount > 0) {
/* 233 */       for (int k = 2; k < 6; k++) {
/* 234 */         TileEntity neighbor = getTile(ForgeDirection.getOrientation(k));
/* 235 */         if (neighbor instanceof IInventory) {
/* 236 */           invs.put(ForgeDirection.getOrientation(k), (IInventory)neighbor);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 241 */     if (filteredReqsToFulfill > 0) {
/* 242 */       for (ForgeDirection dir : invs.keySet()) {
/* 243 */         if (filteredReqsToFulfill == 0) {
/*     */           break;
/*     */         }
/* 246 */         IInventory inv = invs.get(dir);
/* 247 */         Iterable<IInvSlot> iterator = InventoryIterator.getIterable(inv, dir.getOpposite());
/* 248 */         for (IInvSlot slot : iterator) {
/* 249 */           if (filteredReqsToFulfill == 0) {
/*     */             break;
/*     */           }
/* 252 */           ItemStack comparedStack = slot.getStackInSlot();
/* 253 */           if (comparedStack == null || !slot.canTakeStackFromSlot(comparedStack)) {
/*     */             continue;
/*     */           }
/* 256 */           Requirement r = new Requirement(inv, slot.getIndex());
/* 257 */           if (comparedStack.field_77994_a <= reqCounts.get(r)) {
/*     */             continue;
/*     */           }
/*     */           
/* 261 */           for (int k = 0; k < 9; k++) {
/* 262 */             ItemStack inputStack = this.inventoryPattern.func_70301_a(k);
/* 263 */             if (isPatternSlotSet(k) && this.requirements[k] == null && inputStack != null && 
/* 264 */               StackHelper.isMatchingItem(inputStack, comparedStack, true, false)) {
/* 265 */               filteredReqsToFulfill--;
/* 266 */               this.requirements[k] = r;
/* 267 */               reqCounts.adjustOrPutValue(r, 1, 1);
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 276 */     if (filteredReqsToFulfill > 0) {
/* 277 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 281 */     boolean foundMissing = false;
/*     */     
/* 283 */     if (missingCount > 0) {
/* 284 */       for (int k = 0; k < 9; k++) {
/* 285 */         Requirement r = new Requirement((IInventory)this, k);
/* 286 */         if (!reqCounts.contains(r))
/*     */         {
/*     */           
/* 289 */           if (validMissing(r, missingCount)) {
/* 290 */             foundMissing = true;
/* 291 */             for (int m = 0; m < 9; m++) {
/* 292 */               if (this.requirements[m] == null && isPatternSlotSet(m) && this.inventoryPattern.func_70301_a(m) == null) {
/* 293 */                 this.requirements[m] = r;
/*     */               }
/*     */             } 
/* 296 */             reqCounts.adjustOrPutValue(r, missingCount, missingCount);
/* 297 */             missingCount = 0;
/*     */             break;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 303 */     if (missingCount > 0) {
/* 304 */       for (ForgeDirection dir : invs.keySet()) {
/* 305 */         if (foundMissing) {
/*     */           break;
/*     */         }
/* 308 */         IInventory inv = invs.get(dir);
/* 309 */         Iterable<IInvSlot> iterator = InventoryIterator.getIterable(inv, dir.getOpposite());
/* 310 */         for (IInvSlot slot : iterator) {
/* 311 */           if (foundMissing) {
/*     */             break;
/*     */           }
/* 314 */           Requirement r = new Requirement(inv, slot.getIndex());
/* 315 */           if (reqCounts.contains(r)) {
/*     */             continue;
/*     */           }
/* 318 */           if (validMissing(r, missingCount)) {
/* 319 */             foundMissing = true;
/* 320 */             for (int k = 0; k < 9; k++) {
/* 321 */               if (this.requirements[k] == null && isPatternSlotSet(k) && this.inventoryPattern.func_70301_a(k) == null) {
/* 322 */                 this.requirements[k] = r;
/*     */               }
/*     */             } 
/* 325 */             reqCounts.adjustOrPutValue(r, missingCount, missingCount);
/* 326 */             missingCount = 0;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 333 */     if (missingCount > 0) {
/* 334 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 339 */     if (input.func_77973_b() instanceof ItemPackage) {
/* 340 */       pkg = input.func_77946_l();
/*     */     } else {
/* 342 */       pkg = new ItemStack((Item)BuildCraftSilicon.packageItem);
/*     */     } 
/* 344 */     NBTTagCompound pkgTag = NBTUtils.getItemData(pkg);
/*     */     
/* 346 */     boolean broken = false;
/*     */     
/* 348 */     for (int j = 0; j < 9; j++) {
/* 349 */       if (isPatternSlotSet(j))
/* 350 */         if (this.requirements[j] == null) {
/* 351 */           BCLog.logger.error("(Recipe Packager) At " + this.field_145851_c + ", " + this.field_145848_d + ", " + this.field_145849_e + " requirement " + j + " was null! THIS SHOULD NOT HAPPEN!");
/* 352 */           broken = true;
/*     */         } else {
/*     */           
/* 355 */           ItemStack usedStack = this.requirements[j].decrStackSize(1);
/* 356 */           if (usedStack == null) {
/* 357 */             BCLog.logger.error("(Recipe Packager) At " + this.field_145851_c + ", " + this.field_145848_d + ", " + this.field_145849_e + " stack at slot " + j + " was too small! THIS SHOULD NOT HAPPEN!");
/* 358 */             broken = true;
/*     */           } else {
/*     */             
/* 361 */             NBTTagCompound itemTag = new NBTTagCompound();
/* 362 */             usedStack.func_77955_b(itemTag);
/* 363 */             pkgTag.func_74782_a("item" + j, (NBTBase)itemTag);
/*     */           } 
/*     */         }  
/*     */     } 
/* 367 */     if (broken) {
/* 368 */       return false;
/*     */     }
/*     */     
/* 371 */     ItemPackage.update(pkg);
/*     */     
/* 373 */     func_70298_a(9, 1);
/* 374 */     func_70299_a(11, pkg);
/*     */     
/* 376 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 381 */     super.readData(stream);
/* 382 */     this.patternsSet = stream.readUnsignedShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 387 */     super.writeData(stream);
/* 388 */     stream.writeShort(this.patternsSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound cpd) {
/* 393 */     super.func_145839_a(cpd);
/* 394 */     this.inventoryPublic.readFromNBT(cpd, "items");
/* 395 */     this.inventoryPattern.readFromNBT(cpd, "pattern");
/* 396 */     this.patternsSet = cpd.func_74765_d("patternSet");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound cpd) {
/* 401 */     super.func_145841_b(cpd);
/* 402 */     this.inventoryPublic.writeToNBT(cpd, "items");
/* 403 */     this.inventoryPattern.writeToNBT(cpd, "pattern");
/* 404 */     cpd.func_74777_a("patternSet", (short)this.patternsSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 409 */     return this.inventoryPublic.func_70302_i_();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slot) {
/* 414 */     return this.inventoryPublic.func_70301_a(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slot, int amount) {
/* 419 */     return this.inventoryPublic.func_70298_a(slot, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slot) {
/* 424 */     return this.inventoryPublic.func_70304_b(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slot, ItemStack stack) {
/* 429 */     this.inventoryPublic.func_70299_a(slot, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 434 */     return "Packager";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 439 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 444 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 449 */     return this.inventoryPublic.func_70300_a(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {
/* 454 */     this.inventoryPublic.func_70295_k_();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70305_f() {
/* 459 */     this.inventoryPublic.func_70305_f();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int slot, ItemStack stack) {
/* 464 */     if (slot == 9) {
/* 465 */       return (stack == null || stack.func_77973_b() == Items.field_151121_aF || stack.func_77973_b() instanceof ItemPackage);
/*     */     }
/* 467 */     return this.inventoryPublic.func_94041_b(slot, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int side) {
/* 472 */     return SLOTS;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int slot, ItemStack stack, int side) {
/* 477 */     if (side >= 2) {
/* 478 */       if (slot >= 9) {
/* 479 */         return false;
/*     */       }
/* 481 */       ItemStack slotStack = this.inventoryPublic.func_70301_a(slot);
/* 482 */       if (StackHelper.canStacksMerge(stack, slotStack)) {
/* 483 */         return true;
/*     */       }
/*     */       
/* 486 */       for (int i = 0; i < 9; i++) {
/* 487 */         if (isPatternSlotSet(i)) {
/* 488 */           ItemStack inputStack = this.inventoryPattern.func_70301_a(i);
/* 489 */           if (inputStack == null) {
/* 490 */             return true;
/*     */           }
/* 492 */           if (StackHelper.isMatchingItem(inputStack, stack, true, false)) {
/* 493 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/* 497 */       return false;
/*     */     } 
/* 499 */     return (slot == 9);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int slot, ItemStack stack, int side) {
/* 505 */     return (slot == 11);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\TilePackager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */