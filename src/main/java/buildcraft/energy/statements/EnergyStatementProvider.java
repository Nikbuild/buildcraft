/*    */ package buildcraft.energy.statements;
/*    */ 
/*    */ import buildcraft.BuildCraftEnergy;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.ITriggerExternal;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.api.statements.ITriggerProvider;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
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
/*    */ 
/*    */ public class EnergyStatementProvider
/*    */   implements ITriggerProvider
/*    */ {
/*    */   public Collection<ITriggerInternal> getInternalTriggers(IStatementContainer container) {
/* 30 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<ITriggerExternal> getExternalTriggers(ForgeDirection side, TileEntity tile) {
/* 35 */     LinkedList<ITriggerExternal> triggers = new LinkedList<ITriggerExternal>();
/*    */     
/* 37 */     if (tile instanceof buildcraft.core.lib.engines.TileEngineBase) {
/* 38 */       triggers.add(BuildCraftEnergy.triggerBlueEngineHeat);
/* 39 */       triggers.add(BuildCraftEnergy.triggerGreenEngineHeat);
/* 40 */       triggers.add(BuildCraftEnergy.triggerYellowEngineHeat);
/* 41 */       triggers.add(BuildCraftEnergy.triggerRedEngineHeat);
/* 42 */       triggers.add(BuildCraftEnergy.triggerEngineOverheat);
/*    */     } 
/*    */     
/* 45 */     if (tile instanceof buildcraft.energy.TileEngineIron) {
/* 46 */       triggers.add(BuildCraftEnergy.triggerCoolantBelow25);
/* 47 */       triggers.add(BuildCraftEnergy.triggerCoolantBelow50);
/*    */       
/* 49 */       triggers.add(BuildCraftEnergy.triggerFuelBelow25);
/* 50 */       triggers.add(BuildCraftEnergy.triggerFuelBelow50);
/*    */     } 
/*    */     
/* 53 */     return triggers;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\statements\EnergyStatementProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */