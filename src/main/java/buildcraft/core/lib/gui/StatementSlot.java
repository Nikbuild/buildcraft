/*    */ package buildcraft.core.lib.gui;
/*    */ 
/*    */ import buildcraft.api.statements.IStatement;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class StatementSlot
/*    */   extends AdvancedSlot
/*    */ {
/*    */   public int slot;
/* 17 */   public ArrayList<StatementParameterSlot> parameters = new ArrayList<StatementParameterSlot>();
/*    */   
/*    */   public StatementSlot(GuiAdvancedInterface gui, int x, int y, int slot) {
/* 20 */     super(gui, x, y);
/*    */     
/* 22 */     this.slot = slot;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 27 */     IStatement stmt = getStatement();
/*    */     
/* 29 */     if (stmt != null) {
/* 30 */       return stmt.getDescription();
/*    */     }
/* 32 */     return "";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon getIcon() {
/* 39 */     IStatement stmt = getStatement();
/*    */     
/* 41 */     if (stmt != null) {
/* 42 */       return stmt.getIcon();
/*    */     }
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDefined() {
/* 50 */     return (getStatement() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract IStatement getStatement();
/*    */   
/*    */   public boolean shouldDrawHighlight() {
/* 57 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\StatementSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */