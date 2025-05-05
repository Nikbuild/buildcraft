/*    */ package buildcraft.transport.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.transport.IPipe;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import buildcraft.core.statements.StatementParameterDirection;
/*    */ import buildcraft.transport.Gate;
/*    */ import buildcraft.transport.Pipe;
/*    */ import buildcraft.transport.PipeTransport;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionValve
/*    */   extends BCStatement
/*    */   implements IActionInternal
/*    */ {
/*    */   public final ValveState state;
/*    */   
/*    */   public enum ValveState
/*    */   {
/* 31 */     OPEN(true, true),
/* 32 */     INPUT_ONLY(true, false),
/* 33 */     OUTPUT_ONLY(false, true),
/* 34 */     CLOSED(false, false);
/*    */     
/* 36 */     public static final ValveState[] VALUES = values();
/*    */     
/*    */     public final boolean inputOpen;
/*    */     
/*    */     ValveState(boolean in, boolean out) {
/* 41 */       this.inputOpen = in;
/* 42 */       this.outputOpen = out;
/*    */     }
/*    */     public final boolean outputOpen;
/*    */     static {
/*    */     
/*    */     } }
/*    */   
/*    */   public ActionValve(ValveState valveState) {
/* 50 */     super(new String[] { "buildcraft:pipe.valve." + valveState.name().toLowerCase(Locale.ENGLISH) });
/* 51 */     this.state = valveState;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 56 */     return StringUtils.localize("gate.action.pipe.valve." + this.state.name().toLowerCase(Locale.ENGLISH));
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 61 */     this.icon = iconRegister.func_94245_a("buildcrafttransport:triggers/action_valve_" + this.state.name().toLowerCase(Locale.ENGLISH));
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 66 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int minParameters() {
/* 71 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 76 */     return (IStatementParameter)new StatementParameterDirection();
/*    */   }
/*    */ 
/*    */   
/*    */   public void actionActivate(IStatementContainer container, IStatementParameter[] parameters) {
/* 81 */     IPipe pipe = ((Gate)container).getPipe();
/*    */     
/* 83 */     if (pipe != null && pipe instanceof Pipe) {
/* 84 */       PipeTransport transport = ((Pipe)pipe).transport;
/* 85 */       if (parameters[0] != null && parameters[0] instanceof StatementParameterDirection) {
/* 86 */         ForgeDirection side = ((StatementParameterDirection)parameters[0]).direction;
/*    */         
/* 88 */         if (side != ForgeDirection.UNKNOWN) {
/* 89 */           transport.allowInput(side, this.state.inputOpen);
/* 90 */           transport.allowOutput(side, this.state.outputOpen);
/*    */         } 
/*    */       } else {
/* 93 */         for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 94 */           transport.allowInput(side, this.state.inputOpen);
/* 95 */           transport.allowOutput(side, this.state.outputOpen);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\ActionValve.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */