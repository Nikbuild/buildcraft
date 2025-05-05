/*    */ package buildcraft.core.tablet;
/*    */ 
/*    */ import buildcraft.api.tablet.ITablet;
/*    */ import buildcraft.api.tablet.TabletBitmap;
/*    */ import buildcraft.api.tablet.TabletProgram;
/*    */ import buildcraft.api.tablet.TabletProgramFactory;
/*    */ 
/*    */ public class TabletProgramMenuFactory
/*    */   extends TabletProgramFactory {
/*    */   public TabletProgram create(ITablet tablet) {
/* 11 */     return new TabletProgramMenu(tablet);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 16 */     return "menu";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TabletBitmap getIcon() {
/* 22 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\tablet\TabletProgramMenuFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */