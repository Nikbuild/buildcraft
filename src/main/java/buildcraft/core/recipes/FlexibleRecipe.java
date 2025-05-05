/*     */ package buildcraft.core.recipes;
/*     */ 
/*     */ import buildcraft.api.recipes.CraftingResult;
/*     */ import buildcraft.api.recipes.IFlexibleCrafter;
/*     */ import buildcraft.api.recipes.IFlexibleRecipe;
/*     */ import buildcraft.api.recipes.IFlexibleRecipeIngredient;
/*     */ import buildcraft.api.recipes.IFlexibleRecipeViewable;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.inventory.filters.ArrayStackFilter;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ public class FlexibleRecipe<T>
/*     */   implements IFlexibleRecipe<T>, IFlexibleRecipeViewable
/*     */ {
/*     */   private class PreviewCrafter
/*     */     implements IFlexibleCrafter
/*     */   {
/*     */     private final SimpleInventory inventory;
/*     */     private final IFlexibleCrafter crafter;
/*     */     
/*     */     public PreviewCrafter(IFlexibleCrafter crafter) {
/*  40 */       this.crafter = crafter;
/*  41 */       this.inventory = new SimpleInventory(crafter.getCraftingItemStackSize(), "Preview", 64);
/*  42 */       for (int i = 0; i < this.inventory.func_70302_i_(); i++) {
/*  43 */         ItemStack s = crafter.getCraftingItemStack(i);
/*  44 */         if (s != null) {
/*  45 */           this.inventory.func_70299_a(i, s.func_77946_l());
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCraftingItemStackSize() {
/*  52 */       return this.inventory.func_70302_i_();
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getCraftingItemStack(int slotid) {
/*  57 */       return this.inventory.func_70301_a(slotid);
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack decrCraftingItemStack(int slotid, int val) {
/*  62 */       return this.inventory.func_70298_a(slotid, val);
/*     */     }
/*     */ 
/*     */     
/*     */     public FluidStack getCraftingFluidStack(int tankid) {
/*  67 */       return this.crafter.getCraftingFluidStack(tankid);
/*     */     }
/*     */ 
/*     */     
/*     */     public FluidStack decrCraftingFluidStack(int tankid, int val) {
/*  72 */       return this.crafter.decrCraftingFluidStack(tankid, val);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCraftingFluidStackSize() {
/*  77 */       return this.crafter.getCraftingFluidStackSize();
/*     */     }
/*     */   }
/*     */   
/*  81 */   public int energyCost = 0;
/*  82 */   public long craftingTime = 0L;
/*     */   
/*     */   public String id;
/*  85 */   public T output = null;
/*     */   
/*  87 */   public ArrayList<ItemStack> inputItems = new ArrayList<ItemStack>();
/*  88 */   public ArrayList<List<ItemStack>> inputItemsWithAlternatives = new ArrayList<List<ItemStack>>();
/*     */   
/*  90 */   public ArrayList<FluidStack> inputFluids = new ArrayList<FluidStack>();
/*     */ 
/*     */   
/*     */   public FlexibleRecipe() {}
/*     */ 
/*     */   
/*     */   public FlexibleRecipe(String id, T output, int iEnergyCost, long craftingTime, Object... input) {
/*  97 */     setContents(id, output, iEnergyCost, craftingTime, input);
/*     */   }
/*     */   
/*     */   public void setContents(String iid, Object iioutput, int iEnergyCost, long iCraftingTime, Object... input) {
/* 101 */     this.id = iid;
/*     */     
/* 103 */     Object ioutput = null;
/* 104 */     if (iioutput == null)
/* 105 */       throw new IllegalArgumentException("The output of FlexibleRecipe " + iid + " is null! Rejecting recipe."); 
/* 106 */     if (iioutput instanceof IFlexibleRecipeIngredient) {
/* 107 */       ioutput = ((IFlexibleRecipeIngredient)iioutput).getIngredient();
/*     */     } else {
/* 109 */       ioutput = iioutput;
/*     */     } 
/*     */     
/* 112 */     if (ioutput instanceof ItemStack) {
/* 113 */       this.output = (T)ioutput;
/* 114 */     } else if (ioutput instanceof Item) {
/* 115 */       this.output = (T)new ItemStack((Item)ioutput);
/* 116 */     } else if (ioutput instanceof Block) {
/* 117 */       this.output = (T)new ItemStack((Block)ioutput);
/* 118 */     } else if (ioutput instanceof FluidStack) {
/* 119 */       this.output = (T)ioutput;
/*     */     } else {
/* 121 */       throw new IllegalArgumentException("An unknown object passed to recipe " + iid + " as output! (" + ioutput.getClass() + ")");
/*     */     } 
/*     */     
/* 124 */     this.energyCost = iEnergyCost;
/* 125 */     this.craftingTime = iCraftingTime;
/*     */     
/* 127 */     for (int index = 0; index < input.length; index++) {
/* 128 */       Object i = null;
/* 129 */       Object ii = input[index];
/* 130 */       if (ii == null)
/* 131 */         throw new IllegalArgumentException("An input of FlexibleRecipe " + iid + " is null! Rejecting recipe."); 
/* 132 */       if (ii instanceof IFlexibleRecipeIngredient) {
/* 133 */         i = ((IFlexibleRecipeIngredient)ii).getIngredient();
/*     */       } else {
/* 135 */         i = ii;
/*     */       } 
/*     */       
/* 138 */       if (i instanceof ItemStack) {
/* 139 */         this.inputItems.add((ItemStack)i);
/* 140 */       } else if (i instanceof Item) {
/* 141 */         this.inputItems.add(new ItemStack((Item)i));
/* 142 */       } else if (i instanceof Block) {
/* 143 */         this.inputItems.add(new ItemStack((Block)i));
/* 144 */       } else if (i instanceof FluidStack) {
/* 145 */         this.inputFluids.add((FluidStack)i);
/* 146 */       } else if (i instanceof List) {
/* 147 */         this.inputItemsWithAlternatives.add((List<ItemStack>)i);
/* 148 */       } else if (i instanceof String) {
/* 149 */         if (index + 1 >= input.length) {
/* 150 */           this.inputItemsWithAlternatives.add(OreDictionary.getOres((String)i));
/* 151 */         } else if (input[index + 1] instanceof Integer) {
/* 152 */           index++;
/* 153 */           List<ItemStack> items = new ArrayList<ItemStack>();
/* 154 */           for (ItemStack stack : OreDictionary.getOres((String)i)) {
/* 155 */             stack.field_77994_a = ((Integer)input[index]).intValue();
/* 156 */             items.add(stack);
/*     */           } 
/* 158 */           this.inputItemsWithAlternatives.add(items);
/*     */         } else {
/* 160 */           this.inputItemsWithAlternatives.add(OreDictionary.getOres((String)i));
/*     */         } 
/*     */       } else {
/* 163 */         throw new IllegalArgumentException("An unknown object passed to recipe " + iid + " as input! (" + i.getClass() + ")");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCrafted(IFlexibleCrafter crafter) {
/* 171 */     return (craft(crafter, true) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftingResult<T> craft(IFlexibleCrafter baseCrafter, boolean preview) {
/* 176 */     if (this.output == null) {
/* 177 */       return null;
/*     */     }
/*     */     
/* 180 */     IFlexibleCrafter crafter = baseCrafter;
/* 181 */     if (preview) {
/* 182 */       crafter = new FakeFlexibleCrafter(baseCrafter);
/*     */     }
/*     */     
/* 185 */     CraftingResult<T> result = new CraftingResult();
/*     */     
/* 187 */     result.recipe = this;
/* 188 */     result.energyCost = this.energyCost;
/* 189 */     result.craftingTime = this.craftingTime;
/*     */     
/* 191 */     for (ItemStack requirement : this.inputItems) {
/* 192 */       ArrayStackFilter arrayStackFilter = new ArrayStackFilter(new ItemStack[] { requirement });
/* 193 */       int amount = requirement.field_77994_a;
/*     */       
/* 195 */       if (consumeItems(crafter, result, (IStackFilter)arrayStackFilter, amount) != 0) {
/* 196 */         return null;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 202 */     for (List<ItemStack> requirements : this.inputItemsWithAlternatives) {
/* 203 */       ArrayStackFilter arrayStackFilter = new ArrayStackFilter(requirements.<ItemStack>toArray(new ItemStack[requirements.size()]));
/* 204 */       int amount = ((ItemStack)requirements.get(0)).field_77994_a;
/*     */       
/* 206 */       if (consumeItems(crafter, result, (IStackFilter)arrayStackFilter, amount) != 0) {
/* 207 */         return null;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 213 */     for (FluidStack requirement : this.inputFluids) {
/* 214 */       int amount = requirement.amount;
/*     */       
/* 216 */       for (int tankid = 0; tankid < crafter.getCraftingFluidStackSize(); tankid++) {
/* 217 */         FluidStack fluid = crafter.getCraftingFluidStack(tankid);
/*     */         
/* 219 */         if (fluid != null && fluid.isFluidEqual(requirement)) {
/*     */           int amountUsed;
/*     */           
/* 222 */           if (fluid.amount > amount) {
/* 223 */             amountUsed = amount;
/*     */             
/* 225 */             if (!preview) {
/* 226 */               crafter.decrCraftingFluidStack(tankid, amount);
/*     */             }
/*     */             
/* 229 */             amount = 0;
/*     */           } else {
/* 231 */             amountUsed = fluid.amount;
/*     */             
/* 233 */             if (!preview) {
/* 234 */               crafter.decrCraftingFluidStack(tankid, fluid.amount);
/*     */             }
/*     */             
/* 237 */             amount -= fluid.amount;
/*     */           } 
/*     */           
/* 240 */           result.usedFluids.add(new FluidStack(requirement.getFluid(), amountUsed));
/*     */         } 
/*     */         
/* 243 */         if (amount == 0) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 248 */       if (amount != 0) {
/* 249 */         return null;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 255 */     result.crafted = this.output;
/*     */     
/* 257 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 262 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   private int consumeItems(IFlexibleCrafter crafter, CraftingResult<T> result, IStackFilter filter, int amount) {
/* 267 */     int expected = amount;
/*     */     
/* 269 */     for (int slotid = 0; slotid < crafter.getCraftingItemStackSize(); slotid++) {
/* 270 */       ItemStack stack = crafter.getCraftingItemStack(slotid);
/*     */       
/* 272 */       if (stack != null && filter.matches(stack)) {
/*     */         ItemStack removed;
/*     */         
/* 275 */         if (stack.field_77994_a >= expected) {
/* 276 */           removed = crafter.decrCraftingItemStack(slotid, expected);
/* 277 */           expected = 0;
/*     */         } else {
/* 279 */           removed = crafter.decrCraftingItemStack(slotid, stack.field_77994_a);
/* 280 */           expected -= removed.field_77994_a;
/*     */         } 
/*     */         
/* 283 */         result.usedItems.add(removed);
/*     */       } 
/*     */       
/* 286 */       if (expected == 0) {
/* 287 */         return 0;
/*     */       }
/*     */     } 
/*     */     
/* 291 */     return amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftingResult<T> canCraft(ItemStack expectedOutput) {
/* 296 */     if (this.output instanceof ItemStack && 
/* 297 */       StackHelper.isMatchingItem(expectedOutput, (ItemStack)this.output)) {
/* 298 */       CraftingResult<T> result = new CraftingResult();
/*     */       
/* 300 */       result.recipe = this;
/* 301 */       result.usedFluids = this.inputFluids;
/* 302 */       result.usedItems = this.inputItems;
/* 303 */       result.crafted = this.output;
/*     */       
/* 305 */       return result;
/*     */     } 
/* 307 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getOutput() {
/* 313 */     return this.output;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Object> getInputs() {
/* 318 */     ArrayList<Object> inputs = new ArrayList();
/*     */     
/* 320 */     inputs.addAll(this.inputItems);
/* 321 */     inputs.addAll(this.inputItemsWithAlternatives);
/* 322 */     inputs.addAll(this.inputFluids);
/*     */     
/* 324 */     return inputs;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyCost() {
/* 329 */     return this.energyCost;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getCraftingTime() {
/* 334 */     return this.craftingTime;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\recipes\FlexibleRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */