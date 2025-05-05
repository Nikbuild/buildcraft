/*    */ package buildcraft.builders.statements;
/*    */ 
/*    */ import buildcraft.api.filler.FillerManager;
/*    */ import buildcraft.api.filler.IFillerPattern;
/*    */ import buildcraft.api.statements.IActionExternal;
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IActionProvider;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.core.builders.patterns.FillerPattern;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
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
/*    */ public class BuildersActionProvider
/*    */   implements IActionProvider
/*    */ {
/* 29 */   private final HashMap<String, ActionFiller> actionMap = new HashMap<String, ActionFiller>();
/*    */ 
/*    */   
/*    */   public Collection<IActionInternal> getInternalActions(IStatementContainer container) {
/* 33 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<IActionExternal> getExternalActions(ForgeDirection side, TileEntity tile) {
/* 38 */     LinkedList<IActionExternal> actions = new LinkedList<IActionExternal>();
/* 39 */     if (tile instanceof buildcraft.builders.TileFiller) {
/* 40 */       for (IFillerPattern p : FillerManager.registry.getPatterns()) {
/* 41 */         if (p instanceof FillerPattern) {
/* 42 */           if (!this.actionMap.containsKey(p.getUniqueTag())) {
/* 43 */             this.actionMap.put(p.getUniqueTag(), new ActionFiller((FillerPattern)p));
/*    */           }
/* 45 */           actions.add(this.actionMap.get(p.getUniqueTag()));
/*    */         } 
/*    */       } 
/*    */     }
/* 49 */     return actions;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\statements\BuildersActionProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */