/*     */ package buildcraft.silicon;
/*     */ 
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import buildcraft.api.power.ILaserTarget;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.lib.inventory.InventoryCopy;
/*     */ import buildcraft.core.lib.inventory.InventoryIterator;
/*     */ import buildcraft.core.lib.inventory.InventoryMapper;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.inventory.Transactor;
/*     */ import buildcraft.core.lib.inventory.filters.CraftingFilter;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.core.lib.utils.CraftingUtils;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
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
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.oredict.OreDictionary;
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
/*     */ public class TileAdvancedCraftingTable
/*     */   extends TileLaserTableBase
/*     */   implements IInventory, ILaserTarget, ISidedInventory
/*     */ {
/*  52 */   private static final int[] SLOTS = Utils.createSlotArray(0, 24);
/*  53 */   private static final EnumSet<ForgeDirection> SEARCH_SIDES = EnumSet.of(ForgeDirection.DOWN, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST);
/*     */   private static final int REQUIRED_POWER = 5000;
/*     */   private final CraftingGrid craftingSlots;
/*     */   private final InventoryMapper invInput;
/*     */   private final InventoryMapper invOutput;
/*     */   private SlotCrafting craftSlot;
/*     */   private boolean craftable;
/*     */   private boolean justCrafted;
/*     */   private IRecipe currentRecipe;
/*     */   private InventoryCraftResult craftResult;
/*     */   private InternalInventoryCrafting internalInventoryCrafting;
/*     */   
/*     */   private final class InternalInventoryCraftingContainer
/*     */     extends Container {
/*     */     private InternalInventoryCraftingContainer() {}
/*     */     
/*     */     public boolean func_75145_c(EntityPlayer var1) {
/*  70 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class CraftingGrid
/*     */     extends SimpleInventory {
/*  76 */     public int[][] oreIDs = new int[9][];
/*     */     
/*     */     public CraftingGrid() {
/*  79 */       super(9, "CraftingSlots", 1);
/*  80 */       Arrays.fill((Object[])this.oreIDs, new int[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70299_a(int slotId, ItemStack itemstack) {
/*  85 */       super.func_70299_a(slotId, itemstack);
/*     */       
/*  87 */       if (TileAdvancedCraftingTable.this.func_145831_w() == null || !(TileAdvancedCraftingTable.this.func_145831_w()).field_72995_K) {
/*  88 */         int[] id = new int[0];
/*  89 */         if (itemstack != null) {
/*  90 */           int[] ids = OreDictionary.getOreIDs(itemstack);
/*  91 */           if (ids.length > 0) {
/*  92 */             id = ids;
/*     */           }
/*     */         } 
/*  95 */         this.oreIDs[slotId] = id;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private final class InternalInventoryCrafting
/*     */     extends InventoryCrafting {
/*     */     public int[] hitCount;
/* 103 */     private int[] bindings = new int[9];
/*     */     private ItemStack[] tempStacks;
/*     */     private boolean useRecipeStack;
/*     */     
/*     */     private InternalInventoryCrafting() {
/* 108 */       super(new TileAdvancedCraftingTable.InternalInventoryCraftingContainer(TileAdvancedCraftingTable.this, null), 3, 3);
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_70301_a(int slot) {
/* 113 */       if (slot >= 0 && slot < 9) {
/* 114 */         if (this.useRecipeStack || this.tempStacks == null) {
/* 115 */           return TileAdvancedCraftingTable.this.craftingSlots.func_70301_a(slot);
/*     */         }
/* 117 */         if (this.bindings[slot] >= 0) {
/* 118 */           return this.tempStacks[this.bindings[slot]];
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 124 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70299_a(int slot, ItemStack par2ItemStack) {
/* 129 */       if (this.tempStacks != null && slot >= 0 && slot < 9 && this.bindings[slot] >= 0) {
/* 130 */         this.tempStacks[this.bindings[slot]] = par2ItemStack;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_70298_a(int slot, int amount) {
/* 136 */       if (this.tempStacks != null && slot >= 0 && slot < 9 && this.bindings[slot] >= 0) {
/* 137 */         if ((this.tempStacks[this.bindings[slot]]).field_77994_a <= amount) {
/* 138 */           ItemStack itemStack = this.tempStacks[this.bindings[slot]];
/* 139 */           this.tempStacks[this.bindings[slot]] = null;
/* 140 */           return itemStack;
/*     */         } 
/* 142 */         ItemStack result = this.tempStacks[this.bindings[slot]].func_77979_a(amount);
/*     */         
/* 144 */         if ((this.tempStacks[this.bindings[slot]]).field_77994_a <= 0) {
/* 145 */           this.tempStacks[this.bindings[slot]] = null;
/*     */         }
/*     */         
/* 148 */         return result;
/*     */       } 
/*     */       
/* 151 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void recipeUpdate(boolean flag) {
/* 156 */       this.useRecipeStack = flag;
/*     */     }
/*     */   }
/*     */   
/*     */   public TileAdvancedCraftingTable() {
/* 161 */     this.craftingSlots = new CraftingGrid();
/* 162 */     this.inv.addListener((TileEntity)this);
/* 163 */     this.invInput = new InventoryMapper((IInventory)this.inv, 0, 15);
/* 164 */     this.invOutput = new InventoryMapper((IInventory)this.inv, 15, 9);
/* 165 */     this.craftResult = new InventoryCraftResult();
/*     */   }
/*     */   
/*     */   public WeakReference<EntityPlayer> getInternalPlayer() {
/* 169 */     return CoreProxy.proxy.getBuildCraftPlayer((WorldServer)this.field_145850_b, this.field_145851_c, this.field_145848_d + 1, this.field_145849_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound data) {
/* 174 */     super.func_145841_b(data);
/* 175 */     this.craftingSlots.writeToNBT(data, "craftingSlots");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound data) {
/* 180 */     super.func_145839_a(data);
/* 181 */     if (data.func_74764_b("StorageSlots")) {
/* 182 */       this.inv.readFromNBT(data, "StorageSlots");
/*     */     }
/*     */     
/* 185 */     if (data.func_74764_b("items")) {
/* 186 */       this.craftingSlots.readFromNBT(data);
/*     */     } else {
/* 188 */       this.craftingSlots.readFromNBT(data, "craftingSlots");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 194 */     return 24;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 199 */     return StringUtils.localize("tile.assemblyWorkbenchBlock.name");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/* 204 */     super.func_70296_d();
/* 205 */     this.craftable = (this.craftResult.func_70301_a(0) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRequiredEnergy() {
/* 210 */     return (this.craftResult.func_70301_a(0) != null) ? 5000 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getProgressScaled(int i) {
/* 215 */     return getEnergy() * i / 5000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 220 */     super.func_145845_h();
/*     */     
/* 222 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 226 */     if (this.internalInventoryCrafting == null) {
/* 227 */       this.internalInventoryCrafting = new InternalInventoryCrafting();
/* 228 */       this.craftSlot = new SlotCrafting(getInternalPlayer().get(), (IInventory)this.internalInventoryCrafting, (IInventory)this.craftResult, 0, 0, 0);
/* 229 */       updateRecipe();
/*     */     } 
/* 231 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/* 234 */     updateRecipe();
/* 235 */     searchNeighborsForIngredients();
/* 236 */     locateAndBindIngredients();
/* 237 */     updateRecipeOutputDisplay();
/* 238 */     this.justCrafted = false;
/* 239 */     if (canCraftAndOutput()) {
/* 240 */       if (getEnergy() >= getRequiredEnergy()) {
/* 241 */         craftItem();
/* 242 */         this.justCrafted = true;
/*     */       } 
/*     */     } else {
/* 245 */       this.craftable = false;
/* 246 */       this.internalInventoryCrafting.tempStacks = null;
/* 247 */       this.internalInventoryCrafting.hitCount = null;
/* 248 */       setEnergy(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean canCraftAndOutput() {
/* 253 */     if (!hasIngredients()) {
/* 254 */       return false;
/*     */     }
/* 256 */     ItemStack output = getRecipeOutput();
/* 257 */     if (output == null) {
/* 258 */       return false;
/*     */     }
/* 260 */     return InvUtils.isRoomForStack(output, ForgeDirection.UP, (IInventory)this.invOutput);
/*     */   }
/*     */   
/*     */   private void locateAndBindIngredients() {
/* 264 */     this.internalInventoryCrafting.tempStacks = (new InventoryCopy((IInventory)this.inv)).getItemStacks();
/* 265 */     this.internalInventoryCrafting.hitCount = new int[this.internalInventoryCrafting.tempStacks.length];
/* 266 */     ItemStack[] inputSlots = this.internalInventoryCrafting.tempStacks;
/*     */     
/* 268 */     for (int gridSlot = 0; gridSlot < this.craftingSlots.func_70302_i_(); gridSlot++) {
/* 269 */       this.internalInventoryCrafting.bindings[gridSlot] = -1;
/*     */       
/* 271 */       if (this.craftingSlots.func_70301_a(gridSlot) != null) {
/*     */ 
/*     */ 
/*     */         
/* 275 */         boolean foundMatch = false;
/*     */         
/* 277 */         for (int inputSlot = 0; inputSlot < inputSlots.length; inputSlot++) {
/* 278 */           if (isMatchingIngredient(gridSlot, inputSlot))
/*     */           {
/*     */ 
/*     */             
/* 282 */             if (this.internalInventoryCrafting.hitCount[inputSlot] < (inputSlots[inputSlot]).field_77994_a && this.internalInventoryCrafting.hitCount[inputSlot] < inputSlots[inputSlot]
/* 283 */               .func_77976_d()) {
/* 284 */               this.internalInventoryCrafting.bindings[gridSlot] = inputSlot;
/* 285 */               this.internalInventoryCrafting.hitCount[inputSlot] = this.internalInventoryCrafting.hitCount[inputSlot] + 1;
/* 286 */               foundMatch = true;
/*     */               break;
/*     */             } 
/*     */           }
/*     */         } 
/* 291 */         if (!foundMatch)
/*     */           return; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isMatchingIngredient(int gridSlot, int inputSlot) {
/* 298 */     ItemStack inputStack = this.internalInventoryCrafting.tempStacks[inputSlot];
/*     */     
/* 300 */     if (inputStack == null)
/* 301 */       return false; 
/* 302 */     if (StackHelper.isMatchingItem(this.craftingSlots.func_70301_a(gridSlot), inputStack, true, false)) {
/* 303 */       return true;
/*     */     }
/* 305 */     return StackHelper.isCraftingEquivalent(this.craftingSlots.oreIDs[gridSlot], inputStack);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasIngredients() {
/* 310 */     return (this.currentRecipe != null && this.currentRecipe.func_77569_a(this.internalInventoryCrafting, this.field_145850_b));
/*     */   }
/*     */   
/*     */   private void craftItem() {
/* 314 */     EntityPlayer internalPlayer = getInternalPlayer().get();
/* 315 */     ItemStack recipeOutput = getRecipeOutput();
/* 316 */     this.craftSlot.func_82870_a(internalPlayer, recipeOutput);
/* 317 */     ItemStack[] tempStorage = this.internalInventoryCrafting.tempStacks;
/*     */     
/* 319 */     for (int i = 0; i < tempStorage.length; i++) {
/* 320 */       if (tempStorage[i] != null && (tempStorage[i]).field_77994_a <= 0) {
/* 321 */         tempStorage[i] = null;
/*     */       }
/*     */       
/* 324 */       this.inv.getItemStacks()[i] = tempStorage[i];
/*     */     } 
/*     */     
/* 327 */     subtractEnergy(getRequiredEnergy());
/* 328 */     List<ItemStack> outputs = Lists.newArrayList((Object[])new ItemStack[] { recipeOutput.func_77946_l() });
/*     */     
/* 330 */     for (int j = 0; j < internalPlayer.field_71071_by.field_70462_a.length; j++) {
/* 331 */       if (internalPlayer.field_71071_by.field_70462_a[j] != null) {
/* 332 */         outputs.add(internalPlayer.field_71071_by.field_70462_a[j]);
/* 333 */         internalPlayer.field_71071_by.field_70462_a[j] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 337 */     for (ItemStack output : outputs) {
/* 338 */       output.field_77994_a -= (Transactor.getTransactorFor(this.invOutput).add(output, ForgeDirection.UP, true)).field_77994_a;
/*     */       
/* 340 */       if (output.field_77994_a > 0) {
/* 341 */         output.field_77994_a -= Utils.addToRandomInventoryAround(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, output);
/*     */       }
/*     */       
/* 344 */       if (output.field_77994_a > 0) {
/* 345 */         InvUtils.dropItems(this.field_145850_b, output, this.field_145851_c, this.field_145848_d + 1, this.field_145849_e);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void searchNeighborsForIngredients() {
/* 351 */     for (IInvSlot slot : InventoryIterator.getIterable((IInventory)this.craftingSlots, ForgeDirection.UP)) {
/* 352 */       ItemStack ingred = slot.getStackInSlot();
/*     */       
/* 354 */       if (ingred == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 358 */       CraftingFilter craftingFilter = new CraftingFilter(new ItemStack[] { ingred });
/*     */       
/* 360 */       if (InvUtils.countItems((IInventory)this.invInput, ForgeDirection.UP, (IStackFilter)craftingFilter) < InvUtils.countItems((IInventory)this.craftingSlots, ForgeDirection.UP, (IStackFilter)craftingFilter)) {
/* 361 */         for (ForgeDirection side : SEARCH_SIDES) {
/* 362 */           TileEntity tile = getTile(side);
/*     */           
/* 364 */           if (tile instanceof IInventory) {
/* 365 */             IInventory inv = InvUtils.getInventory((IInventory)tile);
/* 366 */             ItemStack result = InvUtils.moveOneItem(inv, side.getOpposite(), (IInventory)this.invInput, side, (IStackFilter)craftingFilter);
/*     */             
/* 368 */             if (result != null) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateRecipe() {
/* 378 */     if (this.internalInventoryCrafting == null) {
/*     */       return;
/*     */     }
/*     */     
/* 382 */     this.internalInventoryCrafting.recipeUpdate(true);
/*     */     
/* 384 */     if (this.currentRecipe == null || !this.currentRecipe.func_77569_a(this.internalInventoryCrafting, this.field_145850_b)) {
/* 385 */       this.currentRecipe = CraftingUtils.findMatchingRecipe(this.internalInventoryCrafting, this.field_145850_b);
/*     */     }
/*     */     
/* 388 */     this.internalInventoryCrafting.recipeUpdate(false);
/* 389 */     func_70296_d();
/*     */   }
/*     */   
/*     */   private void updateRecipeOutputDisplay() {
/* 393 */     if (this.internalInventoryCrafting == null || this.currentRecipe == null) {
/* 394 */       this.craftResult.func_70299_a(0, null);
/*     */       
/*     */       return;
/*     */     } 
/* 398 */     ItemStack resultStack = getRecipeOutput();
/*     */     
/* 400 */     if (resultStack == null) {
/* 401 */       this.internalInventoryCrafting.recipeUpdate(true);
/* 402 */       resultStack = getRecipeOutput();
/* 403 */       this.internalInventoryCrafting.recipeUpdate(false);
/*     */     } 
/*     */     
/* 406 */     this.craftResult.func_70299_a(0, resultStack);
/* 407 */     func_70296_d();
/*     */   }
/*     */   
/*     */   private ItemStack getRecipeOutput() {
/* 411 */     if (this.internalInventoryCrafting == null || this.currentRecipe == null) {
/* 412 */       return null;
/*     */     }
/* 414 */     return this.currentRecipe.func_77572_b(this.internalInventoryCrafting);
/*     */   }
/*     */ 
/*     */   
/*     */   public IInventory getCraftingSlots() {
/* 419 */     return (IInventory)this.craftingSlots;
/*     */   }
/*     */   
/*     */   public IInventory getOutputSlot() {
/* 423 */     return (IInventory)this.craftResult;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCraft() {
/* 428 */     return (this.craftable && !this.justCrafted);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWork() {
/* 433 */     return requiresLaserEnergy();
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int side) {
/* 438 */     return SLOTS;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int slot, ItemStack stack, int side) {
/* 443 */     return func_94041_b(slot, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int slot, ItemStack stack, int side) {
/* 448 */     return (slot >= 15);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int slot, ItemStack stack) {
/* 453 */     return (slot < 15);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 458 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\TileAdvancedCraftingTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */