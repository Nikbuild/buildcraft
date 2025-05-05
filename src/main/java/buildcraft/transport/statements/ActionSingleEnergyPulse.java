/*    */ package buildcraft.transport.statements;
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
/*    */ public class ActionSingleEnergyPulse
/*    */   extends BCStatement
/*    */   implements IActionInternal
/*    */ {
/*    */   public ActionSingleEnergyPulse() {
/* 22 */     super(new String[] { "buildcraft:pulsar.single", "buildcraft.pulser.single" });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 27 */     return StringUtils.localize("gate.action.pulsar.single");
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 32 */     this.icon = iconRegister.func_94245_a("buildcrafttransport:triggers/action_single_pulsar");
/*    */   }
/*    */   
/*    */   public void actionActivate(IStatementContainer source, IStatementParameter[] parameters) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\ActionSingleEnergyPulse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */