/*    */ package buildcraft.core.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IStatement;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.StatementManager;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BCStatement
/*    */   implements IStatement
/*    */ {
/*    */   protected final String uniqueTag;
/*    */   protected IIcon icon;
/*    */   
/*    */   public BCStatement(String... uniqueTag) {
/* 35 */     this.uniqueTag = uniqueTag[0];
/* 36 */     for (String tag : uniqueTag) {
/* 37 */       StatementManager.statements.put(tag, this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUniqueTag() {
/* 43 */     return this.uniqueTag;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon getIcon() {
/* 49 */     return this.icon;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IIconRegister iconRegister) {}
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 59 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int minParameters() {
/* 64 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 69 */     return "";
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatement rotateLeft() {
/* 74 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 79 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\BCStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */