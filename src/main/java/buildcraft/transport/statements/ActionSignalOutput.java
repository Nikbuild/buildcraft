/*    */ package buildcraft.transport.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.transport.PipeWire;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import buildcraft.transport.Gate;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionSignalOutput
/*    */   extends BCStatement
/*    */   implements IActionInternal
/*    */ {
/*    */   public final PipeWire color;
/*    */   
/*    */   public ActionSignalOutput(PipeWire color) {
/* 28 */     super(new String[] { "buildcraft:pipe.wire.output." + color.name().toLowerCase(Locale.ENGLISH), "buildcraft.pipe.wire.output." + color.name().toLowerCase(Locale.ENGLISH) });
/*    */     
/* 30 */     this.color = color;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 35 */     return String.format(StringUtils.localize("gate.action.pipe.wire"), new Object[] { StringUtils.localize("color." + this.color.name().toLowerCase(Locale.ENGLISH)) });
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 40 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 45 */     return new ActionParameterSignal();
/*    */   }
/*    */ 
/*    */   
/*    */   public void actionActivate(IStatementContainer container, IStatementParameter[] parameters) {
/* 50 */     Gate gate = (Gate)container;
/*    */     
/* 52 */     gate.broadcastSignal(this.color);
/*    */     
/* 54 */     for (IStatementParameter param : parameters) {
/* 55 */       if (param != null && param instanceof ActionParameterSignal) {
/* 56 */         ActionParameterSignal signal = (ActionParameterSignal)param;
/*    */         
/* 58 */         if (signal.color != null) {
/* 59 */           gate.broadcastSignal(signal.color);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister register) {
/* 67 */     this.icon = register.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_" + this.color.name().toLowerCase() + "_active");
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\ActionSignalOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */