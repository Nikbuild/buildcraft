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
/*    */ public class PatternParameterCenter
/*    */   implements IStatementParameter {
/* 15 */   private static final int[] shiftLeft = new int[] { 6, 3, 0, 7, 4, 1, 8, 5, 2 };
/*    */   
/*    */   private static IIcon[] icons;
/*    */   
/*    */   private int direction;
/*    */   
/*    */   public PatternParameterCenter() {}
/*    */   
/*    */   public PatternParameterCenter(int direction) {
/* 24 */     this();
/* 25 */     this.direction = direction;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUniqueTag() {
/* 30 */     return "buildcraft:fillerParameterCenter";
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon getIcon() {
/* 35 */     return icons[this.direction % 9];
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 45 */     icons = new IIcon[9];
/* 46 */     for (int i = 0; i < 9; i++) {
/* 47 */       icons[i] = iconRegister.func_94245_a("buildcraftcore:fillerParameters/center_" + i);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 53 */     return StringUtils.localize("direction.center." + this.direction);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(IStatementContainer source, IStatement stmt, ItemStack stack, StatementMouseClick mouse) {
/* 58 */     this.direction = (this.direction + 1) % 9;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 63 */     this.direction = compound.func_74771_c("dir");
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNBT(NBTTagCompound compound) {
/* 68 */     compound.func_74774_a("dir", (byte)this.direction);
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter rotateLeft() {
/* 73 */     return new PatternParameterCenter(shiftLeft[this.direction % 9]);
/*    */   }
/*    */   
/*    */   public int getDirection() {
/* 77 */     return this.direction;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\PatternParameterCenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */