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
/*    */ public class PatternParameterHollow
/*    */   implements IStatementParameter
/*    */ {
/*    */   private static IIcon iconHollow;
/*    */   private static IIcon iconFilled;
/*    */   public boolean filled = false;
/*    */   
/*    */   public PatternParameterHollow() {}
/*    */   
/*    */   public PatternParameterHollow(boolean hollow) {
/* 24 */     this();
/* 25 */     this.filled = !hollow;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUniqueTag() {
/* 30 */     return "buildcraft:fillerParameterHollow";
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon getIcon() {
/* 35 */     return this.filled ? iconFilled : iconHollow;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 45 */     iconFilled = iconRegister.func_94245_a("buildcraftcore:fillerParameters/filled");
/* 46 */     iconHollow = iconRegister.func_94245_a("buildcraftcore:fillerParameters/hollow");
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 51 */     return StringUtils.localize("fillerpattern.parameter." + (this.filled ? "filled" : "hollow"));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(IStatementContainer source, IStatement stmt, ItemStack stack, StatementMouseClick mouse) {
/* 56 */     this.filled = !this.filled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 61 */     this.filled = compound.func_74767_n("filled");
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNBT(NBTTagCompound compound) {
/* 66 */     compound.func_74757_a("filled", this.filled);
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter rotateLeft() {
/* 71 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\PatternParameterHollow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */