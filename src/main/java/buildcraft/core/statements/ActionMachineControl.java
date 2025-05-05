/*    */ package buildcraft.core.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IActionExternal;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.tiles.IControllable;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.Locale;
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
/*    */ 
/*    */ public class ActionMachineControl
/*    */   extends BCStatement
/*    */   implements IActionExternal
/*    */ {
/*    */   public final IControllable.Mode mode;
/*    */   
/*    */   public ActionMachineControl(IControllable.Mode mode) {
/* 31 */     super(new String[] { "buildcraft:machine." + mode.name().toLowerCase(Locale.ENGLISH), "buildcraft.machine." + mode.name().toLowerCase(Locale.ENGLISH) });
/*    */     
/* 33 */     this.mode = mode;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 38 */     return StringUtils.localize("gate.action.machine." + this.mode.name().toLowerCase(Locale.ENGLISH));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void actionActivate(TileEntity target, ForgeDirection side, IStatementContainer source, IStatementParameter[] parameters) {
/* 44 */     if (target instanceof IControllable) {
/* 45 */       ((IControllable)target).setControlMode(this.mode);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IIconRegister register) {
/* 52 */     this.icon = register.func_94245_a("buildcraftcore:triggers/action_machinecontrol_" + this.mode.name().toLowerCase());
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\ActionMachineControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */