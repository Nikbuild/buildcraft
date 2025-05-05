/*    */ package buildcraft.api.tablet;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public final class TabletAPI {
/*  7 */   private static final Map<String, TabletProgramFactory> programs = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void registerProgram(TabletProgramFactory factory) {
/* 14 */     programs.put(factory.getName(), factory);
/*    */   }
/*    */   
/*    */   public static TabletProgramFactory getProgram(String name) {
/* 18 */     return programs.get(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\tablet\TabletAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */