/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.StatementParameterItemStack;
/*    */ import buildcraft.api.statements.StatementSlot;
/*    */ import buildcraft.core.lib.inventory.filters.StatementParameterStackFilter;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionStationProvideItems
/*    */   extends BCStatement
/*    */   implements IActionInternal
/*    */ {
/*    */   public ActionStationProvideItems() {
/* 27 */     super(new String[] { "buildcraft:station.provide_items" });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 32 */     return StringUtils.localize("gate.action.station.provide_items");
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 37 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:triggers/action_station_provide_items");
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 42 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 47 */     return (IStatementParameter)new StatementParameterItemStack();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void actionActivate(IStatementContainer source, IStatementParameter[] parameters) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean canExtractItem(DockingStation station, ItemStack stack) {
/* 57 */     boolean hasFilter = false;
/*    */     
/* 59 */     for (StatementSlot s : station.getActiveActions()) {
/* 60 */       if (s.statement instanceof ActionStationProvideItems) {
/* 61 */         StatementParameterStackFilter param = new StatementParameterStackFilter(s.parameters);
/*    */         
/* 63 */         if (param.hasFilter()) {
/* 64 */           hasFilter = true;
/*    */           
/* 66 */           if (param.matches(stack)) {
/* 67 */             return true;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 73 */     return !hasFilter;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\ActionStationProvideItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */