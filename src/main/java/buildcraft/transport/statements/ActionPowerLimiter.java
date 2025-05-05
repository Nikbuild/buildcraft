/*    */ package buildcraft.transport.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.core.PowerMode;
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
/*    */ 
/*    */ 
/*    */ public class ActionPowerLimiter
/*    */   extends BCStatement
/*    */   implements IActionInternal
/*    */ {
/*    */   public final PowerMode limit;
/*    */   
/*    */   public ActionPowerLimiter(PowerMode limit) {
/* 26 */     super(new String[] { "buildcraft:power.limiter." + limit.name().toLowerCase(Locale.ENGLISH), "buildcraft.power.limiter." + limit.name().toLowerCase(Locale.ENGLISH) });
/*    */     
/* 28 */     this.limit = limit;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 33 */     return this.limit.maxPower + " RF/t Limit";
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 38 */     this.icon = iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_limiter_" + this.limit.name().toLowerCase(Locale.ENGLISH));
/*    */   }
/*    */   
/*    */   public void actionActivate(IStatementContainer source, IStatementParameter[] parameters) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\ActionPowerLimiter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */