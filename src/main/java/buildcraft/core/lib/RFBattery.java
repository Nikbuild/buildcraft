/*    */ package buildcraft.core.lib;
/*    */ 
/*    */ import cofh.api.energy.IEnergyStorage;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public class RFBattery implements IEnergyStorage {
/*    */   private int energy;
/*    */   private int maxEnergy;
/*    */   
/*    */   public RFBattery(int maxEnergy, int maxReceive, int maxExtract) {
/* 11 */     this.maxEnergy = maxEnergy;
/* 12 */     this.maxReceive = maxReceive;
/* 13 */     this.maxExtract = maxExtract;
/*    */   }
/*    */   private int maxReceive; private int maxExtract;
/*    */   public void readFromNBT(NBTTagCompound tag) {
/* 17 */     if (tag.func_74764_b("energy") && tag.func_74764_b("maxEnergy") && tag.func_74764_b("maxReceive") && tag.func_74764_b("maxExtract") && tag
/* 18 */       .func_74762_e("maxEnergy") > 0) {
/* 19 */       this.energy = tag.func_74762_e("energy");
/* 20 */       this.maxEnergy = tag.func_74762_e("maxEnergy");
/* 21 */       this.maxReceive = tag.func_74762_e("maxReceive");
/* 22 */       this.maxExtract = tag.func_74762_e("maxExtract");
/*    */     } 
/*    */   }
/*    */   
/*    */   public void writeToNBT(NBTTagCompound tag) {
/* 27 */     tag.func_74768_a("energy", this.energy);
/* 28 */     tag.func_74768_a("maxEnergy", this.maxEnergy);
/* 29 */     tag.func_74768_a("maxReceive", this.maxReceive);
/* 30 */     tag.func_74768_a("maxExtract", this.maxExtract);
/*    */   }
/*    */   
/*    */   public int addEnergy(int minReceive, int maxReceive, boolean simulate) {
/* 34 */     int amountReceived = Math.min(maxReceive, this.maxEnergy - this.energy);
/*    */     
/* 36 */     if (amountReceived < minReceive) {
/* 37 */       return 0;
/*    */     }
/*    */     
/* 40 */     if (!simulate) {
/* 41 */       this.energy += amountReceived;
/*    */     }
/*    */     
/* 44 */     return amountReceived;
/*    */   }
/*    */   
/*    */   public int useEnergy(int minExtract, int maxExtract, boolean simulate) {
/* 48 */     int amountExtracted = Math.min(maxExtract, this.energy);
/*    */     
/* 50 */     if (amountExtracted < minExtract) {
/* 51 */       return 0;
/*    */     }
/*    */     
/* 54 */     if (!simulate) {
/* 55 */       this.energy -= amountExtracted;
/*    */     }
/*    */     
/* 58 */     return amountExtracted;
/*    */   }
/*    */ 
/*    */   
/*    */   public int receiveEnergy(int maxReceive, boolean simulate) {
/* 63 */     return addEnergy(0, Math.min(maxReceive, this.maxReceive), simulate);
/*    */   }
/*    */ 
/*    */   
/*    */   public int extractEnergy(int maxExtract, boolean simulate) {
/* 68 */     return useEnergy(0, Math.min(maxExtract, this.maxExtract), simulate);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyStored() {
/* 73 */     return this.energy;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxEnergyStored() {
/* 78 */     return this.maxEnergy;
/*    */   }
/*    */   
/*    */   public int getMaxEnergyReceive() {
/* 82 */     return this.maxReceive;
/*    */   }
/*    */   
/*    */   public int getMaxEnergyExtract() {
/* 86 */     return this.maxExtract;
/*    */   }
/*    */   
/*    */   public void setEnergy(int iEnergy) {
/* 90 */     this.energy = iEnergy;
/*    */     
/* 92 */     if (this.energy < 0) {
/* 93 */       this.energy = 0;
/* 94 */     } else if (this.energy > this.maxEnergy) {
/* 95 */       this.energy = this.maxEnergy;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\RFBattery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */