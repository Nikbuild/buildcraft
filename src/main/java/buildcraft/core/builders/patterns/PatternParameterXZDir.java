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
/*    */ public class PatternParameterXZDir
/*    */   implements IStatementParameter {
/* 15 */   private static final String[] names = new String[] { "west", "east", "north", "south" };
/*    */ 
/*    */   
/* 18 */   private static final int[] shiftLeft = new int[] { 3, 2, 0, 1 };
/* 19 */   private static final int[] shiftRight = new int[] { 2, 3, 1, 0 };
/*    */   
/*    */   private static IIcon[] icons;
/*    */   
/*    */   private int direction;
/*    */   
/*    */   public PatternParameterXZDir() {}
/*    */   
/*    */   public PatternParameterXZDir(int direction) {
/* 28 */     this();
/* 29 */     this.direction = direction;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUniqueTag() {
/* 34 */     return "buildcraft:fillerParameterXZDir";
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon getIcon() {
/* 39 */     return icons[this.direction & 0x3];
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 53 */     icons = new IIcon[] { iconRegister.func_94245_a("buildcraftcore:fillerParameters/arrow_left"), iconRegister.func_94245_a("buildcraftcore:fillerParameters/arrow_right"), iconRegister.func_94245_a("buildcraftcore:fillerParameters/arrow_up"), iconRegister.func_94245_a("buildcraftcore:fillerParameters/arrow_down") };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 59 */     return StringUtils.localize("direction." + names[this.direction & 0x3]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(IStatementContainer source, IStatement stmt, ItemStack stack, StatementMouseClick mouse) {
/* 64 */     this.direction = shiftRight[this.direction & 0x3];
/*    */   }
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 69 */     this.direction = compound.func_74771_c("dir");
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNBT(NBTTagCompound compound) {
/* 74 */     compound.func_74774_a("dir", (byte)this.direction);
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter rotateLeft() {
/* 79 */     return new PatternParameterXZDir(shiftLeft[this.direction & 0x3]);
/*    */   }
/*    */   
/*    */   public int getDirection() {
/* 83 */     return this.direction;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\PatternParameterXZDir.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */