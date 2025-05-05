/*     */ package buildcraft.silicon;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.recipes.CraftingResult;
/*     */ import buildcraft.api.recipes.IFlexibleCrafter;
/*     */ import buildcraft.api.recipes.IFlexibleRecipe;
/*     */ import buildcraft.api.recipes.IFlexibleRecipeViewable;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.core.recipes.AssemblyRecipeManager;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileAssemblyTable
/*     */   extends TileLaserTableBase
/*     */   implements IInventory, IFlexibleCrafter, ICommandReceiver
/*     */ {
/*  42 */   public String currentRecipeId = "";
/*     */   public IFlexibleRecipe<ItemStack> currentRecipe;
/*  44 */   public HashMap<String, CraftingResult<ItemStack>> plannedOutputIcons = new HashMap<String, CraftingResult<ItemStack>>();
/*  45 */   private HashSet<String> plannedOutput = new HashSet<String>();
/*     */   private boolean queuedNetworkUpdate = false;
/*     */   
/*     */   public List<CraftingResult<ItemStack>> getPotentialOutputs() {
/*  49 */     List<CraftingResult<ItemStack>> result = new LinkedList<CraftingResult<ItemStack>>();
/*     */     
/*  51 */     for (IFlexibleRecipe<ItemStack> recipe : (Iterable<IFlexibleRecipe<ItemStack>>)AssemblyRecipeManager.INSTANCE.getRecipes()) {
/*  52 */       CraftingResult<ItemStack> r = recipe.craft(this, true);
/*     */       
/*  54 */       if (r != null) {
/*  55 */         result.add(r);
/*     */       }
/*     */     } 
/*     */     
/*  59 */     return result;
/*     */   }
/*     */   
/*     */   private void queueNetworkUpdate() {
/*  63 */     this.queuedNetworkUpdate = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/*  68 */     return !FMLCommonHandler.instance().getEffectiveSide().isClient();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  73 */     super.func_145845_h();
/*     */     
/*  75 */     if (this.queuedNetworkUpdate) {
/*  76 */       sendNetworkUpdate();
/*  77 */       this.queuedNetworkUpdate = false;
/*     */     } 
/*     */     
/*  80 */     if (this.currentRecipe == null) {
/*     */       return;
/*     */     }
/*     */     
/*  84 */     if (!this.currentRecipe.canBeCrafted(this)) {
/*  85 */       setNextCurrentRecipe();
/*     */       
/*  87 */       if (this.currentRecipe == null) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/*  92 */     if (getEnergy() >= (this.currentRecipe.craft(this, true)).energyCost && 
/*  93 */       this.currentRecipe.canBeCrafted(this)) {
/*  94 */       CraftingResult<ItemStack> result = this.currentRecipe.craft(this, false);
/*  95 */       setEnergy(Math.max(0, getEnergy() - result.energyCost));
/*  96 */       outputStack(((ItemStack)result.crafted).func_77946_l(), true);
/*     */       
/*  98 */       setNextCurrentRecipe();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 106 */     return 12;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slot, ItemStack stack) {
/* 111 */     super.func_70299_a(slot, stack);
/*     */     
/* 113 */     if (this.currentRecipe == null) {
/* 114 */       setNextCurrentRecipe();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 120 */     return StringUtils.localize("tile.assemblyTableBlock.name");
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 125 */     super.readData(stream);
/* 126 */     this.currentRecipeId = NetworkUtils.readUTF(stream);
/* 127 */     this.plannedOutput.clear();
/* 128 */     int size = stream.readUnsignedByte();
/* 129 */     for (int i = 0; i < size; i++) {
/* 130 */       this.plannedOutput.add(NetworkUtils.readUTF(stream));
/*     */     }
/*     */ 
/*     */     
/* 134 */     generatePlannedOutputIcons();
/*     */     
/* 136 */     this.currentRecipe = AssemblyRecipeManager.INSTANCE.getRecipe(this.currentRecipeId);
/*     */   }
/*     */   
/*     */   private void generatePlannedOutputIcons() {
/* 140 */     for (String s : this.plannedOutput) {
/* 141 */       IFlexibleRecipe<ItemStack> recipe = AssemblyRecipeManager.INSTANCE.getRecipe(s);
/* 142 */       if (recipe != null) {
/* 143 */         CraftingResult<ItemStack> result = recipe.craft(this, true);
/* 144 */         if (result != null && result.usedItems != null && result.usedItems.size() > 0) {
/* 145 */           this.plannedOutputIcons.put(s, result); continue;
/* 146 */         }  if (recipe instanceof IFlexibleRecipeViewable) {
/*     */           
/* 148 */           Object out = ((IFlexibleRecipeViewable)recipe).getOutput();
/* 149 */           if (out instanceof ItemStack) {
/* 150 */             result = new CraftingResult();
/* 151 */             result.crafted = out;
/* 152 */             result.recipe = recipe;
/* 153 */             this.plannedOutputIcons.put(s, result);
/*     */           } 
/*     */         }  continue;
/*     */       } 
/* 157 */       this.plannedOutput.remove(s);
/*     */     } 
/*     */ 
/*     */     
/* 161 */     for (String s : (String[])this.plannedOutputIcons.keySet().toArray((Object[])new String[this.plannedOutputIcons.size()])) {
/* 162 */       if (!this.plannedOutput.contains(s)) {
/* 163 */         this.plannedOutputIcons.remove(s);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 170 */     super.writeData(stream);
/* 171 */     NetworkUtils.writeUTF(stream, this.currentRecipeId);
/* 172 */     stream.writeByte(this.plannedOutput.size());
/* 173 */     for (String s : this.plannedOutput) {
/* 174 */       NetworkUtils.writeUTF(stream, s);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 180 */     super.func_145839_a(nbt);
/*     */     
/* 182 */     NBTTagList list = nbt.func_150295_c("plannedIds", 8);
/*     */     
/* 184 */     for (int i = 0; i < list.func_74745_c(); i++) {
/* 185 */       IFlexibleRecipe<ItemStack> recipe = AssemblyRecipeManager.INSTANCE.getRecipe(list.func_150307_f(i));
/*     */       
/* 187 */       if (recipe != null) {
/* 188 */         this.plannedOutput.add(recipe.getId());
/*     */       }
/*     */     } 
/*     */     
/* 192 */     if (nbt.func_74764_b("recipeId")) {
/* 193 */       IFlexibleRecipe<ItemStack> recipe = AssemblyRecipeManager.INSTANCE.getRecipe(nbt.func_74779_i("recipeId"));
/*     */       
/* 195 */       if (recipe != null) {
/* 196 */         setCurrentRecipe(recipe);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 203 */     super.func_145841_b(nbt);
/*     */     
/* 205 */     NBTTagList list = new NBTTagList();
/*     */     
/* 207 */     for (String recipe : this.plannedOutput) {
/* 208 */       list.func_74742_a((NBTBase)new NBTTagString(recipe));
/*     */     }
/*     */     
/* 211 */     nbt.func_74782_a("plannedIds", (NBTBase)list);
/*     */     
/* 213 */     if (this.currentRecipe != null) {
/* 214 */       nbt.func_74778_a("recipeId", this.currentRecipe.getId());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isPlanned(IFlexibleRecipe<ItemStack> recipe) {
/* 219 */     if (recipe == null) {
/* 220 */       return false;
/*     */     }
/*     */     
/* 223 */     return this.plannedOutput.contains(recipe.getId());
/*     */   }
/*     */   
/*     */   public boolean isAssembling(IFlexibleRecipe<ItemStack> recipe) {
/* 227 */     return (recipe != null && recipe == this.currentRecipe);
/*     */   }
/*     */   
/*     */   private void setCurrentRecipe(IFlexibleRecipe<ItemStack> recipe) {
/* 231 */     this.currentRecipe = recipe;
/*     */     
/* 233 */     if (recipe != null) {
/* 234 */       this.currentRecipeId = recipe.getId();
/*     */     } else {
/* 236 */       this.currentRecipeId = "";
/*     */     } 
/*     */ 
/*     */     
/* 240 */     generatePlannedOutputIcons();
/*     */     
/* 242 */     if (this.field_145850_b != null && !this.field_145850_b.field_72995_K) {
/* 243 */       queueNetworkUpdate();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRequiredEnergy() {
/* 249 */     if (this.currentRecipe != null) {
/* 250 */       CraftingResult<ItemStack> result = this.currentRecipe.craft(this, true);
/*     */       
/* 252 */       if (result != null) {
/* 253 */         return result.energyCost;
/*     */       }
/* 255 */       return 0;
/*     */     } 
/*     */     
/* 258 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void planOutput(IFlexibleRecipe<ItemStack> recipe) {
/* 263 */     if (recipe != null && !isPlanned(recipe)) {
/* 264 */       this.plannedOutput.add(recipe.getId());
/*     */       
/* 266 */       if (!isAssembling(this.currentRecipe) || !isPlanned(this.currentRecipe)) {
/* 267 */         setCurrentRecipe(recipe);
/*     */       }
/*     */       
/* 270 */       queueNetworkUpdate();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cancelPlanOutput(IFlexibleRecipe<ItemStack> recipe) {
/* 275 */     if (isAssembling(recipe)) {
/* 276 */       setCurrentRecipe((IFlexibleRecipe<ItemStack>)null);
/*     */     }
/*     */     
/* 279 */     this.plannedOutput.remove(recipe.getId());
/*     */     
/* 281 */     if (!this.plannedOutput.isEmpty()) {
/* 282 */       setCurrentRecipe(AssemblyRecipeManager.INSTANCE.getRecipe(this.plannedOutput.iterator().next()));
/*     */     }
/*     */     
/* 285 */     queueNetworkUpdate();
/*     */   }
/*     */   
/*     */   public void setNextCurrentRecipe() {
/* 289 */     boolean takeNext = false;
/*     */     
/* 291 */     for (String recipeId : this.plannedOutput) {
/* 292 */       IFlexibleRecipe<ItemStack> recipe = AssemblyRecipeManager.INSTANCE.getRecipe(recipeId);
/*     */       
/* 294 */       if (recipe == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 298 */       if (recipe == this.currentRecipe) {
/* 299 */         takeNext = true; continue;
/* 300 */       }  if (takeNext && recipe.canBeCrafted(this)) {
/* 301 */         setCurrentRecipe(recipe);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 306 */     for (String recipeId : this.plannedOutput) {
/* 307 */       IFlexibleRecipe<ItemStack> recipe = AssemblyRecipeManager.INSTANCE.getRecipe(recipeId);
/*     */       
/* 309 */       if (recipe == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 313 */       if (recipe.canBeCrafted(this)) {
/* 314 */         setCurrentRecipe(recipe);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 319 */     setCurrentRecipe((IFlexibleRecipe<ItemStack>)null);
/*     */   }
/*     */   
/*     */   public void rpcSelectRecipe(final String id, final boolean select) {
/* 323 */     BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "select", new CommandWriter() {
/*     */             public void write(ByteBuf data) {
/* 325 */               NetworkUtils.writeUTF(data, id);
/* 326 */               data.writeBoolean(select);
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/* 333 */     if (side.isServer() && "select".equals(command)) {
/* 334 */       String id = NetworkUtils.readUTF(stream);
/* 335 */       boolean select = stream.readBoolean();
/*     */       
/* 337 */       IFlexibleRecipe<ItemStack> recipe = AssemblyRecipeManager.INSTANCE.getRecipe(id);
/*     */       
/* 339 */       if (recipe != null) {
/* 340 */         if (select) {
/* 341 */           planOutput(recipe);
/*     */         } else {
/* 343 */           cancelPlanOutput(recipe);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWork() {
/* 351 */     return (this.currentRecipe != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCraft() {
/* 356 */     return hasWork();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int slot, ItemStack stack) {
/* 361 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 366 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCraftingItemStackSize() {
/* 371 */     return func_70302_i_();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingItemStack(int slotid) {
/* 376 */     return func_70301_a(slotid);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack decrCraftingItemStack(int slotid, int val) {
/* 381 */     return func_70298_a(slotid, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack getCraftingFluidStack(int tankid) {
/* 386 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack decrCraftingFluidStack(int tankid, int val) {
/* 391 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCraftingFluidStackSize() {
/* 396 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\TileAssemblyTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */