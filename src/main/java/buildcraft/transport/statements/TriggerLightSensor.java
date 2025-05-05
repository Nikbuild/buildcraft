/*    */ package buildcraft.transport.statements;
/*    */ 
/*    */ import buildcraft.api.core.Position;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import buildcraft.api.statements.containers.ISidedStatementContainer;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ public class TriggerLightSensor
/*    */   extends BCStatement
/*    */   implements ITriggerInternal {
/*    */   private final boolean bright;
/*    */   
/*    */   public TriggerLightSensor(boolean bright) {
/* 21 */     super(new String[] { "buildcraft:light_" + (bright ? "bright" : "dark") });
/* 22 */     this.bright = bright;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 27 */     return StringUtils.localize("gate.trigger.light." + (this.bright ? "bright" : "dark"));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTriggerActive(IStatementContainer source, IStatementParameter[] parameters) {
/* 32 */     TileEntity tile = source.getTile();
/* 33 */     Position pos = new Position(tile);
/* 34 */     pos.orientation = ((ISidedStatementContainer)source).getSide();
/* 35 */     pos.moveForwards(1.0D);
/*    */     
/* 37 */     int lightLevel = tile.func_145831_w().func_72957_l((int)pos.x, (int)pos.y, (int)pos.z);
/*    */     
/* 39 */     return ((lightLevel < 8)) ^ this.bright;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 46 */     this.icon = iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_light_" + (this.bright ? "bright" : "dark"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\TriggerLightSensor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */