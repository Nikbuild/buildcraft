/*    */ package buildcraft.core.statements;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.api.core.BCLog;
/*    */ import buildcraft.api.statements.IActionExternal;
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IActionProvider;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.tiles.IControllable;
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
/*    */ public class DefaultActionProvider
/*    */   implements IActionProvider
/*    */ {
/*    */   public Collection<IActionInternal> getInternalActions(IStatementContainer container) {
/* 31 */     LinkedList<IActionInternal> res = new LinkedList<IActionInternal>();
/*    */     
/* 33 */     if (container instanceof buildcraft.api.statements.containers.IRedstoneStatementContainer) {
/* 34 */       res.add(BuildCraftCore.actionRedstone);
/*    */     }
/*    */     
/* 37 */     return res;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<IActionExternal> getExternalActions(ForgeDirection side, TileEntity tile) {
/* 42 */     LinkedList<IActionExternal> res = new LinkedList<IActionExternal>();
/*    */     
/*    */     try {
/* 45 */       if (tile instanceof IControllable) {
/* 46 */         for (IControllable.Mode mode : IControllable.Mode.values()) {
/* 47 */           if (mode != IControllable.Mode.Unknown && ((IControllable)tile)
/* 48 */             .acceptsControlMode(mode)) {
/* 49 */             res.add(BuildCraftCore.actionControl[mode.ordinal()]);
/*    */           }
/*    */         } 
/*    */       }
/* 53 */     } catch (Throwable error) {
/* 54 */       BCLog.logger.error("Outdated API detected, please update your mods!");
/*    */     } 
/*    */     
/* 57 */     return res;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\DefaultActionProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */