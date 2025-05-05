/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.StatementParameterItemStack;
/*    */ import buildcraft.api.statements.StatementSlot;
/*    */ import buildcraft.core.lib.inventory.filters.ArrayStackOrListFilter;
/*    */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*    */ import buildcraft.core.lib.inventory.filters.PassThroughStackFilter;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
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
/*    */ 
/*    */ public class ActionRobotFilterTool
/*    */   extends BCStatement
/*    */   implements IActionInternal
/*    */ {
/*    */   public ActionRobotFilterTool() {
/* 32 */     super(new String[] { "buildcraft:robot.work_filter_tool" });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 37 */     return StringUtils.localize("gate.action.robot.filter_tool");
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 42 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:triggers/action_robot_filter_tool");
/*    */   }
/*    */ 
/*    */   
/*    */   public int minParameters() {
/* 47 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 52 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 57 */     return (IStatementParameter)new StatementParameterItemStack();
/*    */   }
/*    */   
/*    */   public static Collection<ItemStack> getGateFilterStacks(DockingStation station) {
/* 61 */     ArrayList<ItemStack> result = new ArrayList<ItemStack>();
/*    */     
/* 63 */     for (StatementSlot slot : station.getActiveActions()) {
/* 64 */       if (slot.statement instanceof ActionRobotFilterTool) {
/* 65 */         for (IStatementParameter p : slot.parameters) {
/* 66 */           if (p != null && p instanceof StatementParameterItemStack) {
/* 67 */             StatementParameterItemStack param = (StatementParameterItemStack)p;
/* 68 */             ItemStack stack = param.getItemStack();
/*    */             
/* 70 */             if (stack != null) {
/* 71 */               result.add(stack);
/*    */             }
/*    */           } 
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 78 */     return result;
/*    */   }
/*    */   
/*    */   public static IStackFilter getGateFilter(DockingStation station) {
/* 82 */     Collection<ItemStack> stacks = getGateFilterStacks(station);
/*    */     
/* 84 */     if (stacks.size() == 0) {
/* 85 */       return (IStackFilter)new PassThroughStackFilter();
/*    */     }
/* 87 */     return (IStackFilter)new ArrayStackOrListFilter(stacks.<ItemStack>toArray(new ItemStack[stacks.size()]));
/*    */   }
/*    */   
/*    */   public void actionActivate(IStatementContainer source, IStatementParameter[] parameters) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\ActionRobotFilterTool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */