/*    */ package buildcraft.core.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IStatement;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.StatementMouseClick;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatementParameterRedstoneGateSideOnly
/*    */   implements IStatementParameter
/*    */ {
/*    */   private static IIcon icon;
/*    */   public boolean isOn = false;
/*    */   
/*    */   public ItemStack getItemStack() {
/* 27 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon getIcon() {
/* 32 */     if (!this.isOn) {
/* 33 */       return null;
/*    */     }
/* 35 */     return icon;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onClick(IStatementContainer source, IStatement stmt, ItemStack stack, StatementMouseClick mouse) {
/* 41 */     this.isOn = !this.isOn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNBT(NBTTagCompound compound) {
/* 46 */     compound.func_74774_a("isOn", this.isOn ? 1 : 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 51 */     if (compound.func_74764_b("isOn")) {
/* 52 */       this.isOn = (compound.func_74771_c("isOn") == 1);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 58 */     return this.isOn ? StringUtils.localize("gate.parameter.redstone.gateSideOnly") : "";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUniqueTag() {
/* 63 */     return "buildcraft:redstoneGateSideOnly";
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 68 */     icon = iconRegister.func_94245_a("buildcraftcore:triggers/redstone_gate_side_only");
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter rotateLeft() {
/* 73 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\StatementParameterRedstoneGateSideOnly.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */