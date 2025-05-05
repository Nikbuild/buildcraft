/*    */ package buildcraft.energy.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerExternal;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import buildcraft.energy.TileEngineIron;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
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
/*    */ public class TriggerCoolantBelowThreshold
/*    */   extends BCStatement
/*    */   implements ITriggerExternal
/*    */ {
/*    */   private float threshold;
/*    */   
/*    */   public TriggerCoolantBelowThreshold(float threshold) {
/* 31 */     super(new String[] { "buildcraft:trigger.coolantLevelBelow." + (int)(threshold * 100.0F) });
/* 32 */     this.threshold = threshold;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 37 */     return String.format(StringUtils.localize("gate.trigger.coolantLevelBelow"), new Object[] { Integer.valueOf((int)(this.threshold * 100.0F)) });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTriggerActive(TileEntity target, ForgeDirection side, IStatementContainer source, IStatementParameter[] parameters) {
/* 42 */     if (!(target instanceof TileEngineIron)) {
/* 43 */       return false;
/*    */     }
/*    */     
/* 46 */     return ((TileEngineIron)target).hasCoolantBelowThreshold(this.threshold);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 52 */     this.icon = iconRegister.func_94245_a("buildcraftenergy:triggers/trigger_coolant_below_threshold");
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\statements\TriggerCoolantBelowThreshold.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */