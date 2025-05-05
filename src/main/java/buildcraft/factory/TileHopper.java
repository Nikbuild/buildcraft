/*     */ package buildcraft.factory;
/*     */ 
/*     */ import buildcraft.api.power.IRedstoneEngineReceiver;
/*     */ import buildcraft.api.transport.IInjectable;
/*     */ import buildcraft.core.lib.RFBattery;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.inventory.ITransactor;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.inventory.Transactor;
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ public class TileHopper
/*     */   extends TileBuildCraft
/*     */   implements IInventory, IEnergyHandler, IRedstoneEngineReceiver
/*     */ {
/*  31 */   private final SimpleInventory inventory = new SimpleInventory(4, "Hopper", 64);
/*     */   
/*     */   private boolean isEmpty;
/*     */   
/*     */   public void initialize() {
/*  36 */     setBattery(new RFBattery(10, 10, 0));
/*  37 */     this.inventory.addListener((TileEntity)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbtTagCompound) {
/*  42 */     super.func_145839_a(nbtTagCompound);
/*     */     
/*  44 */     NBTTagCompound p = nbtTagCompound;
/*     */     
/*  46 */     if (nbtTagCompound.func_74764_b("inventory"))
/*     */     {
/*  48 */       p = nbtTagCompound.func_74775_l("inventory");
/*     */     }
/*     */     
/*  51 */     this.inventory.readFromNBT(p);
/*  52 */     this.inventory.func_70296_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbtTagCompound) {
/*  57 */     super.func_145841_b(nbtTagCompound);
/*     */     
/*  59 */     this.inventory.writeToNBT(nbtTagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  64 */     super.func_145845_h();
/*  65 */     if (this.field_145850_b.field_72995_K || this.isEmpty || this.field_145850_b
/*  66 */       .func_82737_E() % 2L != 0L) {
/*     */       return;
/*     */     }
/*     */     
/*  70 */     TileEntity outputTile = getTile(ForgeDirection.DOWN);
/*     */     
/*  72 */     ITransactor transactor = Transactor.getTransactorFor(outputTile);
/*     */     
/*  74 */     if (transactor == null) {
/*  75 */       if (outputTile instanceof IInjectable && getBattery().getEnergyStored() >= 10) {
/*  76 */         ItemStack stackToOutput = null;
/*  77 */         int i = 0;
/*     */         
/*  79 */         getBattery().useEnergy(10, 10, false);
/*     */         
/*  81 */         while (i < this.inventory.func_70302_i_()) {
/*  82 */           ItemStack stackInSlot = this.inventory.func_70301_a(i);
/*  83 */           if (stackInSlot == null || stackInSlot.field_77994_a == 0) {
/*     */             i++; continue;
/*     */           } 
/*  86 */           stackToOutput = stackInSlot.func_77946_l();
/*  87 */           stackToOutput.field_77994_a = 1;
/*     */         } 
/*     */ 
/*     */         
/*  91 */         if (stackToOutput != null) {
/*  92 */           int used = ((IInjectable)outputTile).injectItem(stackToOutput, true, ForgeDirection.UP, null);
/*  93 */           if (used > 0) {
/*  94 */             func_70298_a(i, 1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 102 */     for (int internalSlot = 0; internalSlot < this.inventory.func_70302_i_(); internalSlot++) {
/* 103 */       ItemStack stackInSlot = this.inventory.func_70301_a(internalSlot);
/* 104 */       if (stackInSlot != null && stackInSlot.field_77994_a != 0) {
/*     */ 
/*     */ 
/*     */         
/* 108 */         ItemStack clonedStack = stackInSlot.func_77946_l().func_77979_a(1);
/* 109 */         if ((transactor.add(clonedStack, ForgeDirection.UP, true)).field_77994_a > 0) {
/* 110 */           this.inventory.func_70298_a(internalSlot, 1);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_70296_d() {
/* 118 */     super.func_70296_d();
/* 119 */     this.isEmpty = true;
/*     */     
/* 121 */     for (int internalSlot = 0; internalSlot < this.inventory.func_70302_i_(); internalSlot++) {
/* 122 */       ItemStack stackInSlot = this.inventory.func_70301_a(internalSlot);
/* 123 */       if (stackInSlot != null && stackInSlot.field_77994_a > 0) {
/* 124 */         this.isEmpty = false;
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 135 */     return this.inventory.func_70302_i_();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slotId) {
/* 140 */     return this.inventory.func_70301_a(slotId);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slotId, int count) {
/* 145 */     ItemStack output = this.inventory.func_70298_a(slotId, count);
/* 146 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slotId) {
/* 151 */     ItemStack output = this.inventory.func_70304_b(slotId);
/* 152 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slotId, ItemStack itemStack) {
/* 157 */     this.inventory.func_70299_a(slotId, itemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 162 */     return this.inventory.func_145825_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 167 */     return this.inventory.func_70297_j_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityPlayer) {
/* 172 */     return (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this && entityPlayer.func_70092_e(this.field_145851_c + 0.5D, this.field_145848_d + 0.5D, this.field_145849_e + 0.5D) <= 64.0D);
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
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 190 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstoneEngine(ForgeDirection side) {
/* 196 */     return (side.ordinal() >= 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOwner() {
/* 201 */     return super.getOwner();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection side) {
/* 207 */     return (side.ordinal() >= 2 && !(getTile(side) instanceof buildcraft.api.transport.IPipeTile));
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\TileHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */