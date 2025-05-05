/*     */ package buildcraft.silicon;
/*     */ 
/*     */ import buildcraft.api.recipes.BuildcraftRecipeRegistry;
/*     */ import buildcraft.api.recipes.IIntegrationRecipe;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
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
/*     */ public class TileIntegrationTable
/*     */   extends TileLaserTableBase
/*     */   implements ISidedInventory
/*     */ {
/*     */   public static final int SLOT_OUTPUT = 9;
/*     */   private static final int CYCLE_LENGTH = 16;
/*  32 */   private static final int[] SLOTS = Utils.createSlotArray(0, 10);
/*     */   
/*  34 */   public final IInventory clientOutputInv = (IInventory)new SimpleInventory(1, "Preview", 64);
/*     */   
/*  36 */   private int tick = 0;
/*     */   
/*     */   private IIntegrationRecipe activeRecipe;
/*     */   private boolean activeRecipeValid = false;
/*     */   private int maxExpCountClient;
/*     */   
/*     */   public void initialize() {
/*  43 */     super.initialize();
/*     */     
/*  45 */     updateRecipe();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  50 */     super.func_145845_h();
/*     */     
/*  52 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  56 */     if (this.activeRecipe == null || !this.activeRecipeValid) {
/*  57 */       setEnergy(0);
/*     */       
/*     */       return;
/*     */     } 
/*  61 */     this.tick++;
/*  62 */     if (this.tick % 16 != 0) {
/*     */       return;
/*     */     }
/*     */     
/*  66 */     updateRecipeOutput();
/*     */     
/*  68 */     ItemStack output = this.clientOutputInv.func_70301_a(0);
/*  69 */     if (!isRoomForOutput(output)) {
/*  70 */       setEnergy(0);
/*     */       
/*     */       return;
/*     */     } 
/*  74 */     if (getEnergy() >= this.activeRecipe.getEnergyCost()) {
/*  75 */       setEnergy(0);
/*     */       
/*  77 */       output = this.activeRecipe.craft(func_70301_a(0), getExpansions(), false);
/*     */       
/*  79 */       if (output != null) {
/*  80 */         ItemStack input = func_70301_a(0);
/*     */         
/*  82 */         if (input.field_77994_a > output.field_77994_a) {
/*  83 */           input.field_77994_a -= output.field_77994_a;
/*     */         } else {
/*  85 */           func_70299_a(0, (ItemStack)null);
/*     */         } 
/*     */         
/*  88 */         outputStack(output, this, 9, false);
/*     */         
/*  90 */         for (int i = 1; i < 9; i++) {
/*  91 */           if (func_70301_a(i) != null && (func_70301_a(i)).field_77994_a == 0) {
/*  92 */             func_70299_a(i, (ItemStack)null);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<ItemStack> getExpansions() {
/* 100 */     List<ItemStack> expansions = new ArrayList<ItemStack>();
/* 101 */     for (int i = 1; i < 9; i++) {
/* 102 */       if (func_70301_a(i) != null) {
/* 103 */         expansions.add(func_70301_a(i));
/*     */       }
/*     */     } 
/* 106 */     return expansions;
/*     */   }
/*     */   
/*     */   private void updateRecipeOutput() {
/* 110 */     ItemStack oldClientOutput = this.clientOutputInv.func_70301_a(0);
/*     */     
/* 112 */     this.activeRecipeValid = false;
/* 113 */     this.clientOutputInv.func_70299_a(0, null);
/*     */     
/* 115 */     if (this.activeRecipe != null) {
/* 116 */       List<ItemStack> expansions = getExpansions();
/*     */       
/* 118 */       if (expansions.size() > 0) {
/* 119 */         this.clientOutputInv.func_70299_a(0, this.activeRecipe.craft(func_70301_a(0), expansions, true));
/*     */       }
/*     */     } 
/*     */     
/* 123 */     this.activeRecipeValid = (this.clientOutputInv.func_70301_a(0) != null);
/*     */     
/* 125 */     if (!StackHelper.isEqualItem(this.clientOutputInv.func_70301_a(0), oldClientOutput)) {
/* 126 */       sendNetworkUpdate();
/*     */     }
/*     */   }
/*     */   
/*     */   private void setNewActiveRecipe() {
/* 131 */     ItemStack input = func_70301_a(0);
/* 132 */     if ((input != null && this.activeRecipe != null && this.activeRecipe.isValidInput(input)) || (input == null && this.activeRecipe == null)) {
/*     */       return;
/*     */     }
/*     */     
/* 136 */     this.activeRecipe = null;
/* 137 */     this.activeRecipeValid = false;
/*     */     
/* 139 */     if (input != null && input.func_77973_b() != null) {
/* 140 */       for (IIntegrationRecipe recipe : BuildcraftRecipeRegistry.integrationTable.getRecipes()) {
/* 141 */         if (recipe.isValidInput(input)) {
/* 142 */           this.activeRecipe = recipe;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 148 */     sendNetworkUpdate();
/*     */   }
/*     */   
/*     */   private boolean isRoomForOutput(ItemStack output) {
/* 152 */     ItemStack existingOutput = this.inv.func_70301_a(9);
/* 153 */     if (existingOutput == null) {
/* 154 */       return true;
/*     */     }
/* 156 */     if (StackHelper.canStacksMerge(output, existingOutput) && output.field_77994_a + existingOutput.field_77994_a <= output.func_77976_d()) {
/* 157 */       return true;
/*     */     }
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf buf) {
/* 164 */     buf.writeByte((byte)getMaxExpansionCount());
/* 165 */     NetworkUtils.writeStack(buf, this.clientOutputInv.func_70301_a(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf buf) {
/* 170 */     this.maxExpCountClient = buf.readByte();
/* 171 */     this.clientOutputInv.func_70299_a(0, NetworkUtils.readStack(buf));
/*     */   }
/*     */   
/*     */   public int getMaxExpansionCount() {
/* 175 */     return this.field_145850_b.field_72995_K ? this.maxExpCountClient : ((this.activeRecipe != null) ? this.activeRecipe.getMaximumExpansionCount(func_70301_a(0)) : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRequiredEnergy() {
/* 180 */     return hasWork() ? this.activeRecipe.getEnergyCost() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCraft() {
/* 185 */     return hasWork();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int slot, ItemStack stack) {
/* 190 */     if (slot == 0)
/* 191 */       return true; 
/* 192 */     if (this.activeRecipe == null)
/* 193 */       return false; 
/* 194 */     if (slot < 9) {
/* 195 */       int expansionCount = this.activeRecipe.getMaximumExpansionCount(func_70301_a(0));
/* 196 */       if (expansionCount > 0 && slot > expansionCount) {
/* 197 */         return false;
/*     */       }
/* 199 */       return this.activeRecipe.isValidExpansion(func_70301_a(0), stack);
/*     */     } 
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 207 */     return 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 212 */     return StringUtils.localize("tile.integrationTableBlock.name");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 217 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWork() {
/* 222 */     return this.activeRecipeValid;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slot, ItemStack stack) {
/* 227 */     super.func_70299_a(slot, stack);
/*     */     
/* 229 */     if (slot == 0) {
/* 230 */       updateRecipe();
/*     */     }
/* 232 */     if (slot < 9) {
/* 233 */       updateRecipeOutput();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slot, int amount) {
/* 239 */     ItemStack result = super.func_70298_a(slot, amount);
/*     */     
/* 241 */     if (slot == 0) {
/* 242 */       updateRecipe();
/*     */     }
/* 244 */     if (slot < 9) {
/* 245 */       updateRecipeOutput();
/*     */     }
/*     */     
/* 248 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/* 253 */     super.func_70296_d();
/* 254 */     updateRecipeOutput();
/*     */   }
/*     */   
/*     */   private void updateRecipe() {
/* 258 */     setNewActiveRecipe();
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int side) {
/* 263 */     return SLOTS;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int slot, ItemStack stack, int side) {
/* 268 */     if (slot == 0)
/* 269 */       return true; 
/* 270 */     if (this.activeRecipe == null)
/* 271 */       return false; 
/* 272 */     if (slot < 9) {
/* 273 */       int expansionCount = this.activeRecipe.getMaximumExpansionCount(func_70301_a(0));
/* 274 */       if (expansionCount > 0 && slot > expansionCount) {
/* 275 */         return false;
/*     */       }
/* 277 */       return true;
/*     */     } 
/* 279 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int slot, ItemStack stack, int side) {
/* 285 */     return (slot == 9);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\TileIntegrationTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */