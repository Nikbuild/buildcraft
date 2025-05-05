/*     */ package buildcraft.silicon;
/*     */ 
/*     */ import buildcraft.api.tiles.IHasWork;
/*     */ import buildcraft.core.lib.gui.ContainerDummy;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.utils.CraftingUtils;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import java.lang.ref.WeakReference;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.inventory.SlotCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class TileStampingTable extends TileLaserTableBase implements IHasWork, ISidedInventory {
/*     */   private class LocalInventoryCrafting extends InventoryCrafting {
/*     */     public LocalInventoryCrafting() {
/*  31 */       super((Container)new ContainerDummy(), 3, 3);
/*     */     }
/*     */     
/*     */     private IRecipe findRecipe() {
/*  35 */       return CraftingUtils.findMatchingRecipe(this, TileStampingTable.this.field_145850_b);
/*     */     }
/*     */   }
/*     */   
/*  39 */   private static final int[] SLOTS = Utils.createSlotArray(0, 5);
/*     */   private SlotCrafting craftSlot;
/*  41 */   private final LocalInventoryCrafting crafting = new LocalInventoryCrafting();
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/*  45 */     return !FMLCommonHandler.instance().getEffectiveSide().isClient();
/*     */   }
/*     */   
/*     */   public WeakReference<EntityPlayer> getInternalPlayer() {
/*  49 */     return CoreProxy.proxy.getBuildCraftPlayer((WorldServer)this.field_145850_b, this.field_145851_c, this.field_145848_d + 1, this.field_145849_e);
/*     */   }
/*     */   
/*     */   private void handleLeftoverItems(IInventory items) {
/*  53 */     for (int i = 0; i < items.func_70302_i_(); i++) {
/*  54 */       if (items.func_70301_a(i) != null) {
/*  55 */         ItemStack output = items.func_70301_a(i);
/*     */         
/*  57 */         if (output.field_77994_a <= 0) {
/*  58 */           items.func_70299_a(i, null);
/*     */         }
/*     */         else {
/*     */           
/*  62 */           boolean inserted = false;
/*     */           
/*  64 */           for (int j = 2; j <= 4; j++) {
/*  65 */             ItemStack target = func_70301_a(j);
/*     */             
/*  67 */             if (target == null || target.field_77994_a <= 0) {
/*  68 */               func_70299_a(j, output);
/*  69 */               inserted = true;
/*     */               break;
/*     */             } 
/*  72 */             output.field_77994_a -= StackHelper.mergeStacks(output, target, true);
/*  73 */             if (output.field_77994_a == 0) {
/*  74 */               inserted = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */           
/*  80 */           if (!inserted && 
/*  81 */             output.field_77994_a > 0) {
/*  82 */             output.field_77994_a -= Utils.addToRandomInventoryAround(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, output);
/*     */             
/*  84 */             if (output.field_77994_a > 0) {
/*  85 */               InvUtils.dropItems(this.field_145850_b, output, this.field_145851_c, this.field_145848_d + 1, this.field_145849_e);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/*  90 */           items.func_70299_a(i, null);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_145845_h() {
/*  97 */     super.func_145845_h();
/*     */     
/*  99 */     if (getEnergy() >= getRequiredEnergy() && getEnergy() > 0) {
/* 100 */       ItemStack input = func_70301_a(0);
/*     */       
/* 102 */       if (input == null) {
/*     */         return;
/*     */       }
/*     */       
/* 106 */       EntityPlayer internalPlayer = getInternalPlayer().get();
/*     */       
/* 108 */       if (this.craftSlot == null) {
/* 109 */         this.craftSlot = new SlotCrafting(internalPlayer, (IInventory)this.crafting, this, 1, 0, 0);
/*     */       }
/*     */       
/* 112 */       if (input.func_77973_b() instanceof ItemPackage) {
/*     */         
/* 114 */         NBTTagCompound tag = NBTUtils.getItemData(input);
/* 115 */         for (int i = 0; i < 9; i++) {
/* 116 */           if (tag.func_74764_b("item" + i)) {
/* 117 */             ItemStack is = ItemStack.func_77949_a(tag.func_74775_l("item" + i));
/* 118 */             if (is != null) {
/* 119 */               this.crafting.func_70299_a(i, is);
/*     */             } else {
/*     */               return;
/*     */             } 
/*     */           } else {
/* 124 */             this.crafting.func_70299_a(i, null);
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 129 */         ItemStack input2 = input.func_77946_l();
/* 130 */         input2.field_77994_a = 1;
/* 131 */         this.crafting.func_70299_a(0, input2);
/* 132 */         for (int i = 1; i < 9; i++) {
/* 133 */           this.crafting.func_70299_a(i, null);
/*     */         }
/*     */       } 
/*     */       
/* 137 */       IRecipe recipe = this.crafting.findRecipe();
/* 138 */       ItemStack result = (recipe != null) ? recipe.func_77572_b(this.crafting).func_77946_l() : null;
/*     */       
/* 140 */       addEnergy(-getRequiredEnergy());
/*     */       
/* 142 */       if (result != null) {
/* 143 */         this.craftSlot.func_82870_a(internalPlayer, result);
/* 144 */         handleLeftoverItems((IInventory)this.crafting);
/* 145 */         handleLeftoverItems((IInventory)internalPlayer.field_71071_by);
/*     */         
/* 147 */         for (int i = 1; i <= 4; i++) {
/* 148 */           ItemStack inside = this.inv.func_70301_a(i);
/*     */           
/* 150 */           if (inside == null || inside.field_77994_a <= 0) {
/* 151 */             this.inv.func_70299_a(i, result.func_77946_l());
/* 152 */             result.field_77994_a = 0; break;
/*     */           } 
/* 154 */           if (StackHelper.canStacksMerge(inside, result)) {
/* 155 */             result.field_77994_a -= StackHelper.mergeStacks(result, inside, true);
/*     */             
/* 157 */             if (result.field_77994_a == 0) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 163 */         if (result.field_77994_a > 0) {
/*     */           
/* 165 */           EntityItem entityitem = new EntityItem(this.field_145850_b, this.field_145851_c + 0.5D, this.field_145848_d + 0.7D, this.field_145849_e + 0.5D, result.func_77946_l());
/*     */           
/* 167 */           this.field_145850_b.func_72838_d((Entity)entityitem);
/* 168 */           result.field_77994_a = 0;
/*     */         } 
/* 170 */         func_70298_a(0, 1);
/*     */       } else {
/* 172 */         ItemStack outputSlot = func_70301_a(1);
/* 173 */         if (outputSlot == null) {
/* 174 */           func_70299_a(1, func_70301_a(0));
/* 175 */           func_70299_a(0, (ItemStack)null);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRequiredEnergy() {
/* 183 */     ItemStack stack = func_70301_a(0);
/* 184 */     ItemStack output = func_70301_a(1);
/* 185 */     if (output != null && output.field_77994_a == output.func_77976_d()) {
/* 186 */       return 0;
/*     */     }
/* 188 */     if (stack != null && stack.func_77973_b() != null) {
/* 189 */       if (stack.func_77973_b() instanceof ItemPackage)
/*     */       {
/* 191 */         return 400 * NBTUtils.getItemData(stack).func_150296_c().size();
/*     */       }
/* 193 */       return 400;
/*     */     } 
/*     */ 
/*     */     
/* 197 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWork() {
/* 202 */     return (getRequiredEnergy() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCraft() {
/* 207 */     return hasWork();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 212 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 217 */     return StringUtils.localize("tile.stampingTableBlock.name");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 222 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int slot, ItemStack stack) {
/* 227 */     return (slot == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int side) {
/* 232 */     return SLOTS;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int slot, ItemStack stack, int side) {
/* 237 */     return (slot == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int slot, ItemStack stack, int side) {
/* 242 */     return (slot >= 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\TileStampingTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */