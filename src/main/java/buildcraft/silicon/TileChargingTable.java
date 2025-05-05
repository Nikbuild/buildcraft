/*    */ package buildcraft.silicon;
/*    */ 
/*    */ import buildcraft.api.tiles.IHasWork;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import cofh.api.energy.IEnergyContainerItem;
/*    */ import cpw.mods.fml.common.FMLCommonHandler;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class TileChargingTable
/*    */   extends TileLaserTableBase
/*    */   implements IHasWork
/*    */ {
/*    */   public boolean canUpdate() {
/* 14 */     return !FMLCommonHandler.instance().getEffectiveSide().isClient();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_145845_h() {
/* 20 */     super.func_145845_h();
/*    */     
/* 22 */     if (getEnergy() > 0) {
/* 23 */       if (getRequiredEnergy() > 0) {
/* 24 */         ItemStack stack = func_70301_a(0);
/* 25 */         IEnergyContainerItem containerItem = (IEnergyContainerItem)stack.func_77973_b();
/* 26 */         addEnergy(0 - containerItem.receiveEnergy(stack, getEnergy(), false));
/* 27 */         func_70299_a(0, stack);
/*    */       } else {
/* 29 */         subtractEnergy(Math.min(getEnergy(), 10));
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRequiredEnergy() {
/* 36 */     ItemStack stack = func_70301_a(0);
/* 37 */     if (stack != null && stack.func_77973_b() != null && stack.func_77973_b() instanceof IEnergyContainerItem) {
/* 38 */       IEnergyContainerItem containerItem = (IEnergyContainerItem)stack.func_77973_b();
/* 39 */       return containerItem.getMaxEnergyStored(stack) - containerItem.getEnergyStored(stack);
/*    */     } 
/*    */     
/* 42 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasWork() {
/* 47 */     return (getRequiredEnergy() > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCraft() {
/* 52 */     return hasWork();
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_70302_i_() {
/* 57 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_145825_b() {
/* 62 */     return StringUtils.localize("tile.chargingTableBlock.name");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_145818_k_() {
/* 67 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_94041_b(int slot, ItemStack stack) {
/* 72 */     return (slot == 0 && stack != null && stack.func_77973_b() != null && stack.func_77973_b() instanceof IEnergyContainerItem);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\TileChargingTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */