/*    */ package buildcraft.core.builders.patterns;
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
/*    */ public class PatternParameterYDir
/*    */   implements IStatementParameter
/*    */ {
/*    */   private static IIcon iconUp;
/*    */   private static IIcon iconDown;
/*    */   public boolean up = false;
/*    */   
/*    */   public PatternParameterYDir() {}
/*    */   
/*    */   public PatternParameterYDir(boolean up) {
/* 24 */     this();
/* 25 */     this.up = up;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUniqueTag() {
/* 30 */     return "buildcraft:fillerParameterYDir";
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon getIcon() {
/* 35 */     return this.up ? iconUp : iconDown;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 45 */     iconUp = iconRegister.func_94245_a("buildcraftcore:fillerParameters/stairs_ascend");
/* 46 */     iconDown = iconRegister.func_94245_a("buildcraftcore:fillerParameters/stairs_descend");
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 51 */     return StringUtils.localize("direction." + (this.up ? "up" : "down"));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(IStatementContainer source, IStatement stmt, ItemStack stack, StatementMouseClick mouse) {
/* 56 */     this.up = !this.up;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 61 */     this.up = compound.func_74767_n("up");
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNBT(NBTTagCompound compound) {
/* 66 */     compound.func_74757_a("up", this.up);
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter rotateLeft() {
/* 71 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\PatternParameterYDir.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */