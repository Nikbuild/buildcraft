/*    */ package buildcraft.builders.statements;
/*    */ 
/*    */ import buildcraft.api.statements.IActionExternal;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.builders.TileFiller;
/*    */ import buildcraft.core.builders.patterns.FillerPattern;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.IIcon;
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
/*    */ public class ActionFiller
/*    */   extends BCStatement
/*    */   implements IActionExternal
/*    */ {
/*    */   public final FillerPattern pattern;
/*    */   
/*    */   public ActionFiller(FillerPattern pattern) {
/* 28 */     super(new String[] { "filler:" + pattern.getUniqueTag() });
/* 29 */     this.pattern = pattern;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 34 */     return "Pattern: " + this.pattern.getDescription();
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon getIcon() {
/* 39 */     return this.pattern.getIcon();
/*    */   }
/*    */ 
/*    */   
/*    */   public int minParameters() {
/* 44 */     return this.pattern.minParameters();
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 49 */     return this.pattern.maxParameters();
/*    */   }
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int index) {
/* 54 */     return this.pattern.createParameter(index);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void actionActivate(TileEntity target, ForgeDirection side, IStatementContainer source, IStatementParameter[] parameters) {
/* 60 */     if (target instanceof TileFiller) {
/* 61 */       ((TileFiller)target).setPattern(this.pattern);
/* 62 */       ((TileFiller)target).patternParameters = parameters;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\statements\ActionFiller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */