/*     */ package buildcraft.energy;
/*     */ 
/*     */ import buildcraft.BuildCraftEnergy;
/*     */ import buildcraft.core.lib.engines.TileEngineWithInventory;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.lib.utils.MathUtils;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
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
/*     */ 
/*     */ public class TileEngineStone
/*     */   extends TileEngineWithInventory
/*     */ {
/*     */   static final float MAX_OUTPUT = 10.0F;
/*     */   static final float MIN_OUTPUT = 3.3333333F;
/*     */   static final float TARGET_OUTPUT = 0.375F;
/*  32 */   final float kp = 1.0F;
/*  33 */   final float ki = 0.05F;
/*  34 */   final double eLimit = 133.33334350585938D;
/*  35 */   int burnTime = 0;
/*  36 */   int totalBurnTime = 0;
/*     */   ItemStack burnItem;
/*  38 */   double esum = 0.0D;
/*     */   
/*     */   public TileEngineStone() {
/*  41 */     super(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentOutputLimit() {
/*  46 */     return (int)Math.floor((getIdealOutput() * this.heat / 100.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(EntityPlayer player, ForgeDirection side) {
/*  51 */     if (super.onBlockActivated(player, side)) {
/*  52 */       return true;
/*     */     }
/*  54 */     if (!this.field_145850_b.field_72995_K) {
/*  55 */       player.openGui(BuildCraftEnergy.instance, 21, this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/*  62 */     return (this.burnTime > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void overheat() {
/*  67 */     super.overheat();
/*  68 */     this.burnTime = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void burn() {
/*  73 */     if (this.burnTime > 0) {
/*  74 */       this.burnTime--;
/*  75 */       if (this.isRedstonePowered) {
/*  76 */         this.currentOutput = getIdealOutput();
/*  77 */         addEnergy(this.currentOutput);
/*     */       } 
/*     */     } else {
/*  80 */       this.currentOutput = 0;
/*     */     } 
/*     */     
/*  83 */     if (this.burnTime == 0 && this.isRedstonePowered) {
/*  84 */       this.burnTime = this.totalBurnTime = getItemBurnTime(func_70301_a(0));
/*     */       
/*  86 */       if (this.burnTime > 0) {
/*  87 */         this.burnItem = func_70301_a(0);
/*  88 */         func_70299_a(0, InvUtils.consumeItem(func_70301_a(0)));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getScaledBurnTime(int i) {
/*  94 */     return (int)(this.burnTime / this.totalBurnTime * i);
/*     */   }
/*     */   
/*     */   private int getItemBurnTime(ItemStack itemstack) {
/*  98 */     if (itemstack == null)
/*  99 */       return 0; 
/* 100 */     if (itemstack.func_77973_b() == Items.field_151121_aF) {
/* 101 */       return 400;
/*     */     }
/* 103 */     return TileEntityFurnace.func_145952_a(itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound data) {
/* 110 */     super.func_145839_a(data);
/* 111 */     this.burnTime = data.func_74762_e("burnTime");
/* 112 */     this.totalBurnTime = data.func_74762_e("totalBurnTime");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound data) {
/* 117 */     super.func_145841_b(data);
/* 118 */     data.func_74768_a("burnTime", this.burnTime);
/* 119 */     data.func_74768_a("totalBurnTime", this.totalBurnTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public void getGUINetworkData(int id, int value) {
/* 124 */     super.getGUINetworkData(id, value);
/* 125 */     switch (id) {
/*     */       case 15:
/* 127 */         this.burnTime = value;
/*     */         break;
/*     */       case 16:
/* 130 */         this.totalBurnTime = value;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendGUINetworkData(Container containerEngine, ICrafting iCrafting) {
/* 137 */     super.sendGUINetworkData(containerEngine, iCrafting);
/* 138 */     iCrafting.func_71112_a(containerEngine, 15, this.burnTime);
/* 139 */     iCrafting.func_71112_a(containerEngine, 16, this.totalBurnTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergy() {
/* 144 */     return 10000;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIdealOutput() {
/* 149 */     if (this.burnItem != null && this.burnItem.func_77973_b() == Items.field_151121_aF) {
/* 150 */       return 1;
/*     */     }
/*     */     
/* 153 */     double e = (0.375F * getMaxEnergy() - this.energy);
/* 154 */     this.esum = MathUtils.clamp(this.esum + e, -133.33334350585938D, 133.33334350585938D);
/* 155 */     return (int)Math.round(MathUtils.clamp(e * 1.0D + this.esum * 0.05000000074505806D, 3.3333332538604736D, 10.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 160 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\TileEngineStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */