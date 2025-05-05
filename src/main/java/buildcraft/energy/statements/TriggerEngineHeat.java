/*    */ package buildcraft.energy.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerExternal;
/*    */ import buildcraft.core.lib.engines.TileEngineBase;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
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
/*    */ 
/*    */ public class TriggerEngineHeat
/*    */   extends BCStatement
/*    */   implements ITriggerExternal
/*    */ {
/*    */   public TileEngineBase.EnergyStage stage;
/*    */   
/*    */   public TriggerEngineHeat(TileEngineBase.EnergyStage stage) {
/* 33 */     super(new String[] { "buildcraft:engine.stage." + stage.name().toLowerCase(Locale.ENGLISH), "buildcraft.engine.stage." + stage.name().toLowerCase(Locale.ENGLISH) });
/*    */     
/* 35 */     this.stage = stage;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 40 */     return StringUtils.localize("gate.trigger.engine." + this.stage.name().toLowerCase(Locale.ENGLISH));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTriggerActive(TileEntity tile, ForgeDirection side, IStatementContainer container, IStatementParameter[] parameters) {
/* 45 */     if (tile instanceof TileEngineBase) {
/* 46 */       TileEngineBase engine = (TileEngineBase)tile;
/*    */       
/* 48 */       return (engine.getEnergyStage() == this.stage);
/*    */     } 
/*    */     
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 57 */     this.icon = iconRegister.func_94245_a("buildcraftenergy:triggers/trigger_engineheat_" + this.stage.name().toLowerCase(Locale.ENGLISH));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\statements\TriggerEngineHeat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */