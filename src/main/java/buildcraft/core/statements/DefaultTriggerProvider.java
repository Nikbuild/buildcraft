/*    */ package buildcraft.core.statements;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.ITriggerExternal;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.api.statements.ITriggerProvider;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.ISidedInventory;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ import net.minecraftforge.fluids.FluidTankInfo;
/*    */ import net.minecraftforge.fluids.IFluidHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultTriggerProvider
/*    */   implements ITriggerProvider
/*    */ {
/*    */   public LinkedList<ITriggerExternal> getExternalTriggers(ForgeDirection side, TileEntity tile) {
/* 33 */     LinkedList<ITriggerExternal> res = new LinkedList<ITriggerExternal>();
/*    */     
/* 35 */     boolean blockInventoryTriggers = false;
/* 36 */     boolean blockFluidHandlerTriggers = false;
/*    */     
/* 38 */     if (tile instanceof IBlockDefaultTriggers) {
/* 39 */       blockInventoryTriggers = ((IBlockDefaultTriggers)tile).blockInventoryTriggers(side);
/* 40 */       blockFluidHandlerTriggers = ((IBlockDefaultTriggers)tile).blockFluidHandlerTriggers(side);
/*    */     } 
/*    */     
/* 43 */     if (!blockInventoryTriggers && tile instanceof IInventory) {
/* 44 */       boolean isSided = tile instanceof ISidedInventory;
/* 45 */       boolean addTriggers = false;
/*    */       
/* 47 */       if (isSided) {
/* 48 */         int[] accessibleSlots = ((ISidedInventory)tile).func_94128_d(side.getOpposite().ordinal());
/* 49 */         addTriggers = (accessibleSlots != null && accessibleSlots.length > 0);
/*    */       } 
/*    */       
/* 52 */       if (addTriggers || (!isSided && ((IInventory)tile).func_70302_i_() > 0)) {
/* 53 */         res.add(BuildCraftCore.triggerEmptyInventory);
/* 54 */         res.add(BuildCraftCore.triggerContainsInventory);
/* 55 */         res.add(BuildCraftCore.triggerSpaceInventory);
/* 56 */         res.add(BuildCraftCore.triggerFullInventory);
/* 57 */         res.add(BuildCraftCore.triggerInventoryBelow25);
/* 58 */         res.add(BuildCraftCore.triggerInventoryBelow50);
/* 59 */         res.add(BuildCraftCore.triggerInventoryBelow75);
/*    */       } 
/*    */     } 
/*    */     
/* 63 */     if (!blockFluidHandlerTriggers && tile instanceof IFluidHandler) {
/* 64 */       FluidTankInfo[] tanks = ((IFluidHandler)tile).getTankInfo(side.getOpposite());
/* 65 */       if (tanks != null && tanks.length > 0) {
/* 66 */         res.add(BuildCraftCore.triggerEmptyFluid);
/* 67 */         res.add(BuildCraftCore.triggerContainsFluid);
/* 68 */         res.add(BuildCraftCore.triggerSpaceFluid);
/* 69 */         res.add(BuildCraftCore.triggerFullFluid);
/* 70 */         res.add(BuildCraftCore.triggerFluidContainerBelow25);
/* 71 */         res.add(BuildCraftCore.triggerFluidContainerBelow50);
/* 72 */         res.add(BuildCraftCore.triggerFluidContainerBelow75);
/*    */       } 
/*    */     } 
/*    */     
/* 76 */     if (tile instanceof buildcraft.api.tiles.IHasWork) {
/* 77 */       res.add(BuildCraftCore.triggerMachineActive);
/* 78 */       res.add(BuildCraftCore.triggerMachineInactive);
/*    */     } 
/*    */     
/* 81 */     return res;
/*    */   }
/*    */ 
/*    */   
/*    */   public LinkedList<ITriggerInternal> getInternalTriggers(IStatementContainer container) {
/* 86 */     LinkedList<ITriggerInternal> res = new LinkedList<ITriggerInternal>();
/*    */     
/* 88 */     if (container instanceof buildcraft.api.statements.containers.IRedstoneStatementContainer) {
/* 89 */       res.add(BuildCraftCore.triggerRedstoneActive);
/* 90 */       res.add(BuildCraftCore.triggerRedstoneInactive);
/*    */     } 
/*    */     
/* 93 */     if (TriggerEnergy.isTriggeringPipe(container.getTile()) || TriggerEnergy.getTriggeringNeighbor(container.getTile()) != null) {
/* 94 */       res.add((ITriggerInternal)BuildCraftCore.triggerEnergyHigh);
/* 95 */       res.add((ITriggerInternal)BuildCraftCore.triggerEnergyLow);
/*    */     } 
/*    */     
/* 98 */     return res;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\DefaultTriggerProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */