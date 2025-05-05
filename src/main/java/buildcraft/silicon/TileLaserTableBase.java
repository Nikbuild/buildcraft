/*     */ package buildcraft.silicon;
/*     */ 
/*     */ import buildcraft.api.power.ILaserTarget;
/*     */ import buildcraft.api.tiles.IHasWork;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.utils.AverageInt;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TileLaserTableBase
/*     */   extends TileBuildCraft
/*     */   implements ILaserTarget, IInventory, IHasWork
/*     */ {
/*  30 */   public int clientRequiredEnergy = 0;
/*  31 */   protected SimpleInventory inv = new SimpleInventory(func_70302_i_(), "inv", 64);
/*  32 */   private int energy = 0;
/*     */   private int recentEnergyAverage;
/*  34 */   private AverageInt recentEnergyAverageUtil = new AverageInt(20);
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  38 */     super.func_145845_h();
/*  39 */     this.recentEnergyAverageUtil.tick();
/*     */   }
/*     */   
/*     */   public int getEnergy() {
/*  43 */     return this.energy;
/*     */   }
/*     */   
/*     */   public void setEnergy(int energy) {
/*  47 */     this.energy = energy;
/*     */   }
/*     */   
/*     */   public void addEnergy(int energy) {
/*  51 */     this.energy += energy;
/*     */   }
/*     */   
/*     */   public void subtractEnergy(int energy) {
/*  55 */     this.energy -= energy;
/*     */   }
/*     */   
/*     */   public abstract int getRequiredEnergy();
/*     */   
/*     */   public int getProgressScaled(int ratio) {
/*  61 */     if (this.clientRequiredEnergy == 0)
/*  62 */       return 0; 
/*  63 */     if (this.energy >= this.clientRequiredEnergy) {
/*  64 */       return ratio;
/*     */     }
/*  66 */     return (int)(this.energy / this.clientRequiredEnergy * ratio);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRecentEnergyAverage() {
/*  71 */     return this.recentEnergyAverage;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract boolean canCraft();
/*     */   
/*     */   public boolean requiresLaserEnergy() {
/*  78 */     return (canCraft() && this.energy < getRequiredEnergy() * 5.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveLaserEnergy(int energy) {
/*  83 */     this.energy += energy;
/*  84 */     this.recentEnergyAverageUtil.push(energy);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvalidTarget() {
/*  89 */     return func_145837_r();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getXCoord() {
/*  94 */     return this.field_145851_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getYCoord() {
/*  99 */     return this.field_145848_d;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZCoord() {
/* 104 */     return this.field_145849_e;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slot) {
/* 109 */     return this.inv.func_70301_a(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slot, int amount) {
/* 114 */     return this.inv.func_70298_a(slot, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slot) {
/* 119 */     return this.inv.func_70304_b(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slot, ItemStack stack) {
/* 124 */     this.inv.func_70299_a(slot, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 129 */     return this.inv.func_70297_j_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 134 */     return (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this && !func_145837_r());
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
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 147 */     super.func_145841_b(nbt);
/* 148 */     this.inv.writeToNBT(nbt, "inv");
/* 149 */     nbt.func_74768_a("energy", this.energy);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 154 */     super.func_145839_a(nbt);
/* 155 */     this.inv.readFromNBT(nbt, "inv");
/* 156 */     this.energy = nbt.func_74762_e("energy");
/*     */   }
/*     */   
/*     */   protected void outputStack(ItemStack remaining, boolean autoEject) {
/* 160 */     outputStack(remaining, (IInventory)null, 0, autoEject);
/*     */   }
/*     */   
/*     */   protected void outputStack(ItemStack remaining, IInventory inv, int slot, boolean autoEject) {
/* 164 */     if (autoEject) {
/* 165 */       if (remaining != null && remaining.field_77994_a > 0) {
/* 166 */         remaining.field_77994_a -= 
/* 167 */           Utils.addToRandomInventoryAround(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, remaining);
/*     */       }
/*     */       
/* 170 */       if (remaining != null && remaining.field_77994_a > 0) {
/* 171 */         remaining.field_77994_a -= Utils.addToRandomInjectableAround(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, ForgeDirection.UNKNOWN, remaining);
/*     */       }
/*     */     } 
/*     */     
/* 175 */     if (inv != null && remaining != null && remaining.field_77994_a > 0) {
/* 176 */       ItemStack inside = inv.func_70301_a(slot);
/*     */       
/* 178 */       if (inside == null || inside.field_77994_a <= 0) {
/* 179 */         inv.func_70299_a(slot, remaining); return;
/*     */       } 
/* 181 */       if (StackHelper.canStacksMerge(inside, remaining)) {
/* 182 */         remaining.field_77994_a -= StackHelper.mergeStacks(remaining, inside, true);
/*     */       }
/*     */     } 
/*     */     
/* 186 */     if (remaining != null && remaining.field_77994_a > 0) {
/* 187 */       EntityItem entityitem = new EntityItem(this.field_145850_b, this.field_145851_c + 0.5D, this.field_145848_d + 0.7D, this.field_145849_e + 0.5D, remaining);
/*     */ 
/*     */       
/* 190 */       this.field_145850_b.func_72838_d((Entity)entityitem);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void getGUINetworkData(int id, int data) {
/* 195 */     int currentStored = this.energy;
/* 196 */     int requiredEnergy = this.clientRequiredEnergy;
/*     */     
/* 198 */     switch (id) {
/*     */       case 0:
/* 200 */         requiredEnergy = requiredEnergy & 0xFFFF0000 | data & 0xFFFF;
/* 201 */         this.clientRequiredEnergy = requiredEnergy;
/*     */         break;
/*     */       case 1:
/* 204 */         currentStored = currentStored & 0xFFFF0000 | data & 0xFFFF;
/* 205 */         this.energy = currentStored;
/*     */         break;
/*     */       case 2:
/* 208 */         requiredEnergy = requiredEnergy & 0xFFFF | (data & 0xFFFF) << 16;
/* 209 */         this.clientRequiredEnergy = requiredEnergy;
/*     */         break;
/*     */       case 3:
/* 212 */         currentStored = currentStored & 0xFFFF | (data & 0xFFFF) << 16;
/* 213 */         this.energy = currentStored;
/*     */         break;
/*     */       case 4:
/* 216 */         this.recentEnergyAverage = this.recentEnergyAverage & 0xFFFF0000 | data & 0xFFFF;
/*     */         break;
/*     */       case 5:
/* 219 */         this.recentEnergyAverage = this.recentEnergyAverage & 0xFFFF | (data & 0xFFFF) << 16;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendGUINetworkData(Container container, ICrafting iCrafting) {
/* 225 */     int requiredEnergy = getRequiredEnergy();
/* 226 */     int currentStored = this.energy;
/* 227 */     int lRecentEnergy = (int)(this.recentEnergyAverageUtil.getAverage() * 100.0D);
/* 228 */     iCrafting.func_71112_a(container, 0, requiredEnergy & 0xFFFF);
/* 229 */     iCrafting.func_71112_a(container, 1, currentStored & 0xFFFF);
/* 230 */     iCrafting.func_71112_a(container, 2, requiredEnergy >>> 16 & 0xFFFF);
/* 231 */     iCrafting.func_71112_a(container, 3, currentStored >>> 16 & 0xFFFF);
/* 232 */     iCrafting.func_71112_a(container, 4, lRecentEnergy & 0xFFFF);
/* 233 */     iCrafting.func_71112_a(container, 5, lRecentEnergy >>> 16 & 0xFFFF);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\TileLaserTableBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */