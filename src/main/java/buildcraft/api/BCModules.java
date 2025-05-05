/*    */ package buildcraft.api;
/*    */ 
/*    */ import buildcraft.api.core.BCLog;
/*    */ import java.util.Locale;
/*    */ import net.minecraftforge.fml.common.Loader;
/*    */ import net.minecraftforge.fml.common.LoaderState;
/*    */ 
/*    */ 
/*    */ public enum BCModules
/*    */ {
/* 11 */   LIB,
/*    */   
/* 13 */   CORE,
/*    */   
/* 15 */   BUILDERS,
/* 16 */   ENERGY,
/* 17 */   FACTORY,
/* 18 */   ROBOTICS,
/* 19 */   SILICON,
/* 20 */   TRANSPORT,
/*    */   
/* 22 */   COMPAT;
/*    */   static {
/* 24 */     VALUES = values();
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
/* 56 */     if (!Loader.instance().hasReachedState(LoaderState.CONSTRUCTING)) {
/* 57 */       throw new RuntimeException("Accessed BC modules too early! You can only use them from construction onwards!");
/*    */     }
/* 59 */     for (BCModules module : values()) {
/* 60 */       if (module.isLoaded()) {
/* 61 */         BCLog.logger.info("[api.modules] Module " + module.name().toLowerCase(Locale.ROOT) + " is loaded!");
/*    */       } else {
/* 63 */         BCLog.logger.warn("[api.modules] Module " + module.name().toLowerCase(Locale.ROOT) + " is NOT loaded!");
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static final BCModules[] VALUES;
/*    */   private static final String MODID_START = "buildcraft";
/*    */   private final String modid;
/*    */   private final String part;
/*    */   
/*    */   BCModules() {
/*    */     this.part = name().toLowerCase(Locale.ROOT);
/*    */     this.modid = "buildcraft" + this.part;
/*    */   }
/*    */   
/*    */   public static void fmlPreInit() {}
/*    */   
/*    */   public static boolean isBcMod(String modid) {
/*    */     if (!modid.startsWith("buildcraft"))
/*    */       return false; 
/*    */     String post = modid.substring("buildcraft".length());
/*    */     for (BCModules module : VALUES) {
/*    */       if (post.equals(module.part))
/*    */         return true; 
/*    */     } 
/*    */     return false;
/*    */   }
/*    */   
/*    */   public boolean isLoaded() {
/*    */     return Loader.isModLoaded(this.modid);
/*    */   }
/*    */   
/*    */   public String getModId() {
/*    */     return this.modid;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\BCModules.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */