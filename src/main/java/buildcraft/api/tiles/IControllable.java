/*    */ package buildcraft.api.tiles;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ public interface IControllable
/*    */ {
/*    */   Mode getControlMode();
/*    */   
/*    */   void setControlMode(Mode paramMode);
/*    */   
/*    */   public enum Mode
/*    */   {
/* 13 */     ON,
/* 14 */     OFF,
/* 15 */     LOOP; public static final Mode[] VALUES = values();
/*    */     public final String lowerCaseName;
/*    */     
/*    */     Mode() {
/* 19 */       this.lowerCaseName = name().toLowerCase(Locale.ROOT);
/*    */     }
/*    */     
/*    */     static {
/*    */     
/*    */     } }
/*    */   
/*    */   default boolean acceptsControlMode(Mode mode) {
/* 27 */     return (mode != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\tiles\IControllable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */