/*    */ package buildcraft.transport.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TriggerClockTimer
/*    */   extends BCStatement
/*    */   implements ITriggerInternal
/*    */ {
/*    */   public final Time time;
/*    */   
/*    */   public enum Time
/*    */   {
/* 25 */     SHORT(5), MEDIUM(10), LONG(15); public final int delay;
/* 26 */     public static final Time[] VALUES = values();
/*    */ 
/*    */     
/*    */     Time(int delay) {
/* 30 */       this.delay = delay;
/*    */     }
/*    */     static {
/*    */     
/*    */     } }
/*    */   
/*    */   public TriggerClockTimer(Time time) {
/* 37 */     super(new String[] { "buildcraft:timer." + time.name().toLowerCase(Locale.ENGLISH) });
/*    */     
/* 39 */     this.time = time;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 44 */     return String.format(StringUtils.localize("gate.trigger.timer"), new Object[] { Integer.valueOf(this.time.delay) });
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 49 */     this.icon = iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_timer_" + this.time.name().toLowerCase(Locale.ENGLISH));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isTriggerActive(IStatementContainer source, IStatementParameter[] parameters) {
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\TriggerClockTimer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */