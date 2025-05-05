/*    */ package buildcraft.transport.statements;
/*    */ 
/*    */ import buildcraft.api.gates.IGate;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.api.transport.PipeWire;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import buildcraft.transport.Pipe;
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
/*    */ public class TriggerPipeSignal
/*    */   extends BCStatement
/*    */   implements ITriggerInternal
/*    */ {
/*    */   boolean active;
/*    */   PipeWire color;
/*    */   
/*    */   public TriggerPipeSignal(boolean active, PipeWire color) {
/* 30 */     super(new String[] { "buildcraft:pipe.wire.input." + color.name().toLowerCase(Locale.ENGLISH) + (active ? ".active" : ".inactive"), "buildcraft.pipe.wire.input." + color
/* 31 */           .name().toLowerCase(Locale.ENGLISH) + (active ? ".active" : ".inactive") });
/*    */     
/* 33 */     this.active = active;
/* 34 */     this.color = color;
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 39 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 44 */     return String.format(StringUtils.localize("gate.trigger.pipe.wire." + (this.active ? "active" : "inactive")), new Object[] { StringUtils.localize("color." + this.color.name().toLowerCase(Locale.ENGLISH)) });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTriggerActive(IStatementContainer container, IStatementParameter[] parameters) {
/* 49 */     if (!(container instanceof IGate)) {
/* 50 */       return false;
/*    */     }
/*    */     
/* 53 */     Pipe<?> pipe = (Pipe)((IGate)container).getPipe();
/*    */     
/* 55 */     if (this.active) {
/* 56 */       if (pipe.wireSignalStrength[this.color.ordinal()] == 0) {
/* 57 */         return false;
/*    */       }
/*    */     }
/* 60 */     else if (pipe.wireSignalStrength[this.color.ordinal()] > 0) {
/* 61 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 65 */     for (IStatementParameter param : parameters) {
/* 66 */       if (param != null && param instanceof TriggerParameterSignal) {
/* 67 */         TriggerParameterSignal signal = (TriggerParameterSignal)param;
/*    */         
/* 69 */         if (signal.color != null) {
/* 70 */           if (signal.active) {
/* 71 */             if (pipe.wireSignalStrength[signal.color.ordinal()] == 0) {
/* 72 */               return false;
/*    */             }
/*    */           }
/* 75 */           else if (pipe.wireSignalStrength[signal.color.ordinal()] > 0) {
/* 76 */             return false;
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 83 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister register) {
/* 88 */     this.icon = register.func_94245_a("buildcrafttransport:triggers/trigger_pipesignal_" + this.color.name().toLowerCase() + "_" + (this.active ? "active" : "inactive"));
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 93 */     return new TriggerParameterSignal();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\TriggerPipeSignal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */