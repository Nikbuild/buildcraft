/*    */ package buildcraft.core.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerExternal;
/*    */ import buildcraft.api.tiles.IHasWork;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
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
/*    */ public class TriggerMachine
/*    */   extends BCStatement
/*    */   implements ITriggerExternal
/*    */ {
/*    */   boolean active;
/*    */   
/*    */   public TriggerMachine(boolean active) {
/* 27 */     super(new String[] { "buildcraft:work." + (active ? "scheduled" : "done"), "buildcraft.work." + (active ? "scheduled" : "done") });
/*    */     
/* 29 */     this.active = active;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 34 */     return StringUtils.localize("gate.trigger.machine." + (this.active ? "scheduled" : "done"));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTriggerActive(TileEntity tile, ForgeDirection side, IStatementContainer container, IStatementParameter[] parameters) {
/* 39 */     if (tile instanceof IHasWork) {
/* 40 */       IHasWork machine = (IHasWork)tile;
/*    */       
/* 42 */       if (this.active) {
/* 43 */         return machine.hasWork();
/*    */       }
/* 45 */       return !machine.hasWork();
/*    */     } 
/*    */ 
/*    */     
/* 49 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister register) {
/* 54 */     this.icon = register.func_94245_a("buildcraftcore:triggers/trigger_machine_" + (this.active ? "active" : "inactive"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\TriggerMachine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */