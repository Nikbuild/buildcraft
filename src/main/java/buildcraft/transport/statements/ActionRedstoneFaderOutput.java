/*    */ package buildcraft.transport.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.ActionRedstoneOutput;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionRedstoneFaderOutput
/*    */   extends ActionRedstoneOutput
/*    */   implements IActionInternal
/*    */ {
/*    */   public final int level;
/*    */   
/*    */   public ActionRedstoneFaderOutput(int level) {
/* 26 */     super(String.format("buildcraft:redstone.output.%02d", new Object[] { Integer.valueOf(level) }));
/*    */     
/* 28 */     this.level = level;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 33 */     return String.format(StringUtils.localize("gate.trigger.redstone.input.level"), new Object[] { Integer.valueOf(this.level) });
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon getIcon() {
/* 39 */     return this.icon;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 45 */     this.icon = iconRegister.func_94245_a(String.format("buildcrafttransport:triggers/redstone_%02d", new Object[] { Integer.valueOf(this.level) }));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSignalLevel() {
/* 50 */     return this.level;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\ActionRedstoneFaderOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */