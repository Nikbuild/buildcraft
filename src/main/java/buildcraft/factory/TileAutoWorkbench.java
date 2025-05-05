/*     */ package buildcraft.factory;
/*     */ 
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import buildcraft.api.power.IRedstoneEngineReceiver;
/*     */ import buildcraft.api.tiles.IDebuggable;
/*     */ import buildcraft.api.tiles.IHasWork;
/*     */ import buildcraft.core.lib.RFBattery;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.gui.ContainerDummy;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.lib.inventory.InventoryConcatenator;
/*     */ import buildcraft.core.lib.inventory.InventoryIterator;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.utils.CraftingUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.inventory.InventoryCraftResult;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.inventory.SlotCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
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
/*     */ public class TileAutoWorkbench
/*     */   extends TileBuildCraft
/*     */   implements ISidedInventory, IHasWork, IRedstoneEngineReceiver, IDebuggable
/*     */ {
/*     */   public static final int SLOT_RESULT = 9;
/*     */   public static final int CRAFT_TIME = 256;
/*     */   public static final int UPDATE_TIME = 16;
/*  50 */   private static final int[] SLOTS = Utils.createSlotArray(0, 10);
/*     */   
/*  52 */   public int progress = 0;
/*  53 */   public LocalInventoryCrafting craftMatrix = new LocalInventoryCrafting();
/*     */   
/*  55 */   private SimpleInventory resultInv = new SimpleInventory(1, "Auto Workbench", 64);
/*  56 */   private SimpleInventory inputInv = new SimpleInventory(9, "Auto Workbench", 64)
/*     */     {
/*     */       public void func_70299_a(int slotId, ItemStack itemstack) {
/*  59 */         super.func_70299_a(slotId, itemstack);
/*  60 */         if (TileAutoWorkbench.this.craftMatrix.isInputMissing && func_70301_a(slotId) != null) {
/*  61 */           TileAutoWorkbench.this.craftMatrix.isInputMissing = false;
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public void func_70296_d() {
/*  67 */         super.func_70296_d();
/*  68 */         TileAutoWorkbench.this.craftMatrix.isInputMissing = false;
/*     */       }
/*     */     };
/*     */   
/*  72 */   private IInventory inv = (IInventory)InventoryConcatenator.make().add((IInventory)this.inputInv).add((IInventory)this.resultInv).add((IInventory)this.craftMatrix);
/*     */   
/*     */   private SlotCrafting craftSlot;
/*  75 */   private InventoryCraftResult craftResult = new InventoryCraftResult();
/*     */   
/*  77 */   private int[] bindings = new int[9];
/*  78 */   private int[] bindingCounts = new int[9];
/*     */   
/*  80 */   private int update = Utils.RANDOM.nextInt();
/*     */   
/*     */   private boolean hasWork = false;
/*     */   
/*     */   private boolean scheduledCacheRebuild = false;
/*     */   
/*     */   public TileAutoWorkbench() {
/*  87 */     setBattery(new RFBattery(16, 16, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWork() {
/*  92 */     return this.hasWork;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstoneEngine(ForgeDirection side) {
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection side) {
/* 102 */     TileEntity tile = this.field_145850_b.func_147438_o(this.field_145851_c + side.offsetX, this.field_145848_d + side.offsetY, this.field_145849_e + side.offsetZ);
/* 103 */     return tile instanceof buildcraft.api.power.IRedstoneEngine;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getDebugInfo(List<String> info, ForgeDirection side, ItemStack debugger, EntityPlayer player) {
/* 108 */     info.add("isInputMissing = " + this.craftMatrix.isInputMissing);
/* 109 */     info.add("isOutputJammed = " + this.craftMatrix.isOutputJammed);
/*     */   }
/*     */   
/*     */   public class LocalInventoryCrafting extends InventoryCrafting {
/*     */     public IRecipe currentRecipe;
/*     */     public boolean useBindings;
/*     */     
/*     */     public LocalInventoryCrafting() {
/* 117 */       super((Container)new ContainerDummy(), 3, 3);
/*     */     }
/*     */     public boolean isOutputJammed; public boolean isInputMissing;
/*     */     
/*     */     public ItemStack func_70301_a(int slot) {
/* 122 */       if (this.useBindings) {
/* 123 */         if (slot >= 0 && slot < 9 && TileAutoWorkbench.this.bindings[slot] >= 0) {
/* 124 */           return TileAutoWorkbench.this.inputInv.func_70301_a(TileAutoWorkbench.this.bindings[slot]);
/*     */         }
/* 126 */         return null;
/*     */       } 
/*     */       
/* 129 */       return super.func_70301_a(slot);
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getRecipeOutput() {
/* 134 */       this.currentRecipe = findRecipe();
/* 135 */       if (this.currentRecipe == null) {
/* 136 */         return null;
/*     */       }
/* 138 */       ItemStack result = this.currentRecipe.func_77572_b(this);
/* 139 */       if (result != null) {
/* 140 */         result = result.func_77946_l();
/*     */       }
/* 142 */       return result;
/*     */     }
/*     */     
/*     */     private IRecipe findRecipe() {
/* 146 */       for (IInvSlot slot : InventoryIterator.getIterable((IInventory)this, ForgeDirection.UP)) {
/* 147 */         ItemStack stack = slot.getStackInSlot();
/* 148 */         if (stack == null) {
/*     */           continue;
/*     */         }
/* 151 */         if (stack.func_77973_b().hasContainerItem(stack)) {
/* 152 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 156 */       return CraftingUtils.findMatchingRecipe(TileAutoWorkbench.this.craftMatrix, TileAutoWorkbench.this.field_145850_b);
/*     */     }
/*     */     
/*     */     public void rebuildCache() {
/* 160 */       this.currentRecipe = findRecipe();
/* 161 */       TileAutoWorkbench.this.hasWork = (this.currentRecipe != null && this.currentRecipe.func_77571_b() != null);
/*     */       
/* 163 */       ItemStack result = getRecipeOutput();
/* 164 */       ItemStack resultInto = TileAutoWorkbench.this.resultInv.func_70301_a(0);
/*     */       
/* 166 */       if (resultInto != null && (
/* 167 */         !StackHelper.canStacksMerge(resultInto, result) || resultInto.field_77994_a + result.field_77994_a > resultInto
/* 168 */         .func_77976_d())) {
/*     */         
/* 170 */         this.isOutputJammed = true;
/*     */       } else {
/* 172 */         this.isOutputJammed = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70299_a(int slot, ItemStack stack) {
/* 178 */       if (this.useBindings) {
/* 179 */         if (slot >= 0 && slot < 9 && TileAutoWorkbench.this.bindings[slot] >= 0) {
/* 180 */           TileAutoWorkbench.this.inputInv.func_70299_a(TileAutoWorkbench.this.bindings[slot], stack);
/*     */         }
/*     */         return;
/*     */       } 
/* 184 */       super.func_70299_a(slot, stack);
/* 185 */       TileAutoWorkbench.this.scheduledCacheRebuild = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70296_d() {
/* 190 */       super.func_70296_d();
/* 191 */       TileAutoWorkbench.this.scheduledCacheRebuild = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_70298_a(int slot, int amount) {
/* 196 */       if (this.useBindings) {
/* 197 */         if (slot >= 0 && slot < 9 && TileAutoWorkbench.this.bindings[slot] >= 0) {
/* 198 */           return TileAutoWorkbench.this.inputInv.func_70298_a(TileAutoWorkbench.this.bindings[slot], amount);
/*     */         }
/* 200 */         return null;
/*     */       } 
/*     */       
/* 203 */       TileAutoWorkbench.this.scheduledCacheRebuild = true;
/* 204 */       return func_70298_a(slot, amount);
/*     */     }
/*     */     
/*     */     public void setUseBindings(boolean use) {
/* 208 */       this.useBindings = use;
/*     */     }
/*     */   }
/*     */   
/*     */   public WeakReference<EntityPlayer> getInternalPlayer() {
/* 213 */     return CoreProxy.proxy.getBuildCraftPlayer((WorldServer)this.field_145850_b, this.field_145851_c, this.field_145848_d + 1, this.field_145849_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/* 218 */     super.func_70296_d();
/* 219 */     this.inv.func_70296_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 224 */     return 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slot) {
/* 229 */     return this.inv.func_70301_a(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slot, int count) {
/* 234 */     return this.inv.func_70298_a(slot, count);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slot, ItemStack stack) {
/* 239 */     this.inv.func_70299_a(slot, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slot) {
/* 244 */     return this.inv.func_70304_b(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 249 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 254 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 259 */     return (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this && player.func_70092_e(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.5D) <= 64.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound data) {
/* 264 */     super.func_145839_a(data);
/* 265 */     this.resultInv.readFromNBT(data);
/* 266 */     if (data.func_74764_b("input")) {
/* 267 */       InvUtils.readInvFromNBT((IInventory)this.inputInv, "input", data);
/* 268 */       InvUtils.readInvFromNBT((IInventory)this.craftMatrix, "matrix", data);
/*     */     } else {
/* 270 */       InvUtils.readInvFromNBT((IInventory)this.inputInv, "matrix", data);
/* 271 */       for (int i = 0; i < 9; i++) {
/* 272 */         ItemStack inputStack = this.inputInv.func_70301_a(i);
/* 273 */         if (inputStack != null) {
/* 274 */           ItemStack matrixStack = inputStack.func_77946_l();
/* 275 */           matrixStack.field_77994_a = 1;
/* 276 */           this.craftMatrix.func_70299_a(i, matrixStack);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 281 */     this.craftMatrix.rebuildCache();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound data) {
/* 286 */     super.func_145841_b(data);
/* 287 */     this.resultInv.writeToNBT(data);
/* 288 */     InvUtils.writeInvToNBT((IInventory)this.inputInv, "input", data);
/* 289 */     InvUtils.writeInvToNBT((IInventory)this.craftMatrix, "matrix", data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/* 295 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 300 */     super.func_145845_h();
/*     */     
/* 302 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 306 */     if (this.scheduledCacheRebuild) {
/* 307 */       this.craftMatrix.rebuildCache();
/* 308 */       this.scheduledCacheRebuild = false;
/*     */     } 
/*     */     
/* 311 */     if (this.craftMatrix.isOutputJammed || this.craftMatrix.isInputMissing || this.craftMatrix.currentRecipe == null) {
/* 312 */       this.progress = 0;
/*     */       
/*     */       return;
/*     */     } 
/* 316 */     if (this.craftSlot == null) {
/* 317 */       this.craftSlot = new SlotCrafting(getInternalPlayer().get(), (IInventory)this.craftMatrix, (IInventory)this.craftResult, 0, 0, 0);
/*     */     }
/*     */     
/* 320 */     if (!this.hasWork) {
/*     */       return;
/*     */     }
/*     */     
/* 324 */     int updateNext = this.update + getBattery().getEnergyStored() + 1;
/* 325 */     int updateThreshold = (this.update & 0xFFFFFFF0) + 16;
/* 326 */     this.update = Math.min(updateThreshold, updateNext);
/* 327 */     if (this.update % 16 == 0) {
/* 328 */       updateCrafting();
/*     */     }
/* 330 */     getBattery().setEnergy(0);
/*     */   }
/*     */   
/*     */   public int getProgressScaled(int i) {
/* 334 */     return this.progress * i / 256;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateCrafting() {
/* 341 */     this.progress += 16;
/*     */     int i;
/* 343 */     for (i = 0; i < 9; i++) {
/* 344 */       this.bindingCounts[i] = 0;
/*     */     }
/* 346 */     for (i = 0; i < 9; i++) {
/* 347 */       ItemStack comparedStack = this.craftMatrix.func_70301_a(i);
/* 348 */       if (comparedStack == null || comparedStack.func_77973_b() == null) {
/* 349 */         this.bindings[i] = -1;
/*     */ 
/*     */       
/*     */       }
/* 353 */       else if (this.bindings[i] == -1 || !StackHelper.isMatchingItem(this.inputInv.func_70301_a(this.bindings[i]), comparedStack, true, true)) {
/* 354 */         boolean found = false;
/* 355 */         for (int j = 0; j < 9; j++) {
/* 356 */           if (j != this.bindings[i]) {
/*     */ 
/*     */ 
/*     */             
/* 360 */             ItemStack inputInvStack = this.inputInv.func_70301_a(j);
/*     */             
/* 362 */             if (StackHelper.isMatchingItem(inputInvStack, comparedStack, true, false) && inputInvStack.field_77994_a > this.bindingCounts[j]) {
/*     */               
/* 364 */               found = true;
/* 365 */               this.bindings[i] = j;
/* 366 */               this.bindingCounts[j] = this.bindingCounts[j] + 1; break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 370 */         if (!found) {
/* 371 */           this.craftMatrix.isInputMissing = true;
/* 372 */           this.progress = 0;
/*     */           return;
/*     */         } 
/*     */       } else {
/* 376 */         this.bindingCounts[this.bindings[i]] = this.bindingCounts[this.bindings[i]] + 1;
/*     */       } 
/*     */     } 
/*     */     
/* 380 */     for (i = 0; i < 9; i++) {
/* 381 */       if (this.bindingCounts[i] > 0) {
/* 382 */         ItemStack stack = this.inputInv.func_70301_a(i);
/* 383 */         if (stack != null && stack.field_77994_a < this.bindingCounts[i]) {
/*     */ 
/*     */           
/* 386 */           for (int j = 0; j < 9; j++) {
/* 387 */             if (this.bindings[j] == i) {
/* 388 */               this.bindings[j] = -1;
/*     */             }
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 396 */     if (this.progress < 256) {
/*     */       return;
/*     */     }
/*     */     
/* 400 */     this.progress = 0;
/*     */     
/* 402 */     this.craftMatrix.setUseBindings(true);
/* 403 */     ItemStack result = this.craftMatrix.getRecipeOutput();
/*     */     
/* 405 */     if (result != null && result.field_77994_a > 0) {
/* 406 */       ItemStack resultInto = this.resultInv.func_70301_a(0);
/*     */       
/* 408 */       this.craftSlot.func_82870_a(getInternalPlayer().get(), result);
/*     */       
/* 410 */       if (resultInto == null) {
/* 411 */         this.resultInv.func_70299_a(0, result);
/*     */       } else {
/* 413 */         resultInto.field_77994_a += result.field_77994_a;
/*     */       } 
/*     */     } 
/*     */     
/* 417 */     this.craftMatrix.setUseBindings(false);
/* 418 */     this.craftMatrix.rebuildCache();
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
/*     */   public boolean func_94041_b(int slot, ItemStack stack) {
/* 431 */     if (slot == 9) {
/* 432 */       return false;
/*     */     }
/* 434 */     if (stack.func_77973_b().hasContainerItem(stack)) {
/* 435 */       return false;
/*     */     }
/* 437 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 442 */     return SLOTS;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int slot, ItemStack stack, int side) {
/* 447 */     if (slot >= 9) {
/* 448 */       return false;
/*     */     }
/* 450 */     ItemStack slotStack = this.inv.func_70301_a(slot);
/* 451 */     if (StackHelper.canStacksMerge(stack, slotStack)) {
/* 452 */       return true;
/*     */     }
/* 454 */     for (int i = 0; i < 9; i++) {
/* 455 */       ItemStack inputStack = this.craftMatrix.func_70301_a(i);
/* 456 */       if (inputStack != null && StackHelper.isMatchingItem(inputStack, stack, true, false)) {
/* 457 */         return true;
/*     */       }
/*     */     } 
/* 460 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int slot, ItemStack stack, int side) {
/* 465 */     return (slot == 9);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 470 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\TileAutoWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */