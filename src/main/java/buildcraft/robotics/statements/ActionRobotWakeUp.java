/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionRobotWakeUp
/*    */   extends BCStatement
/*    */   implements IActionInternal
/*    */ {
/*    */   public ActionRobotWakeUp() {
/* 22 */     super(new String[] { "buildcraft:robot.wakeup" });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 27 */     return StringUtils.localize("gate.action.robot.wakeup");
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 32 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:triggers/action_robot_wakeup");
/*    */   }
/*    */   
/*    */   public void actionActivate(IStatementContainer source, IStatementParameter[] parameters) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\ActionRobotWakeUp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */