/*     */ package buildcraft.api.core;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BCDebugging
/*     */ {
/*     */   private static final DebugStatus DEBUG_STATUS;
/*     */   
/*     */   public enum DebugStatus
/*     */   {
/*  17 */     NONE,
/*  18 */     ENABLE,
/*  19 */     LOGGING_ONLY,
/*  20 */     ALL;
/*     */   }
/*     */   
/*     */   enum DebugLevel {
/*  24 */     LOG,
/*  25 */     COMPLEX;
/*     */     DebugLevel() {
/*  27 */       this.name = name().toLowerCase(Locale.ROOT);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final String name;
/*     */ 
/*     */     
/*     */     boolean isAllOn;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     boolean isDev;
/*     */     try {
/*  44 */       Method getTileEntity = World.class.getDeclaredMethod("getTileEntity", new Class[] { BlockPos.class });
/*  45 */       BCLog.logger.info("[debugger] Method found: World.getTileEntity = " + getTileEntity);
/*  46 */       isDev = true;
/*  47 */     } catch (Throwable ignored) {
/*     */       
/*  49 */       isDev = false;
/*     */     } 
/*  51 */     BCLog.logger.info("[debugger] Not a dev environment!");
/*     */     
/*  53 */     String value = System.getProperty("buildcraft.debug");
/*  54 */     if ("enable".equals(value)) { DEBUG_STATUS = DebugStatus.ENABLE; }
/*  55 */     else if ("all".equals(value)) { DEBUG_STATUS = DebugStatus.ALL; }
/*  56 */     else if ("disable".equals(value))
/*     */     
/*  58 */     { DEBUG_STATUS = DebugStatus.NONE; }
/*  59 */     else if ("log".equals(value))
/*     */     
/*  61 */     { DEBUG_STATUS = DebugStatus.LOGGING_ONLY; }
/*     */     
/*  63 */     else if (isDev)
/*  64 */     { DEBUG_STATUS = DebugStatus.ENABLE; }
/*     */     else
/*     */     
/*  67 */     { DEBUG_STATUS = DebugStatus.NONE; }
/*     */ 
/*     */ 
/*     */     
/*  71 */     if (DEBUG_STATUS == DebugStatus.ALL) {
/*  72 */       BCLog.logger.info("[debugger] Debugging automatically enabled for ALL of buildcraft. Prepare for log spam.");
/*  73 */     } else if (DEBUG_STATUS == DebugStatus.LOGGING_ONLY) {
/*  74 */       BCLog.logger.info("[debugger] Debugging automatically enabled for some non-spammy parts of buildcraft.");
/*  75 */     } else if (DEBUG_STATUS == DebugStatus.ENABLE) {
/*  76 */       BCLog.logger.info("[debugger] Debugging not automatically enabled for all of buildcraft. Logging all possible debug options.");
/*  77 */       BCLog.logger.info("              To enable it for only logging messages add \"-Dbuildcraft.debug=log\" to your launch VM arguments");
/*  78 */       BCLog.logger.info("              To enable it for ALL debugging \"-Dbuildcraft.debug=all\" to your launch VM arguments");
/*  79 */       BCLog.logger.info("              To remove this message and all future ones add \"-Dbuildcraft.debug=disable\" to your launch VM arguments");
/*     */     } 
/*     */     
/*  82 */     DebugLevel.COMPLEX.isAllOn = (DEBUG_STATUS == DebugStatus.ALL);
/*  83 */     DebugLevel.LOG.isAllOn = (DEBUG_STATUS == DebugStatus.ALL || DEBUG_STATUS == DebugStatus.LOGGING_ONLY);
/*     */   }
/*     */   
/*     */   public static boolean shouldDebugComplex(String string) {
/*  87 */     return shouldDebug(string, DebugLevel.COMPLEX);
/*     */   }
/*     */   
/*     */   public static boolean shouldDebugLog(String string) {
/*  91 */     return shouldDebug(string, DebugLevel.LOG);
/*     */   }
/*     */   
/*     */   private static boolean shouldDebug(String option, DebugLevel type) {
/*  95 */     String prop = getProp(option);
/*  96 */     String actual = System.getProperty(prop);
/*  97 */     if ("false".equals(actual)) {
/*  98 */       BCLog.logger.info("[debugger] Debugging manually disabled for \"" + option + "\" (" + type + ").");
/*  99 */       return false;
/* 100 */     }  if ("true".equals(actual)) {
/* 101 */       BCLog.logger.info("[debugger] Debugging enabled for \"" + option + "\" (" + type + ").");
/* 102 */       return true;
/*     */     } 
/* 104 */     if (type.isAllOn) {
/* 105 */       BCLog.logger.info("[debugger] Debugging automatically enabled for \"" + option + "\" (" + type + ").");
/* 106 */       return true;
/*     */     } 
/* 108 */     if ("complex".equals(actual) || type.name.equals(actual)) {
/* 109 */       BCLog.logger.info("[debugger] Debugging enabled for \"" + option + "\" (" + type + ").");
/* 110 */       return true;
/* 111 */     }  if (DEBUG_STATUS != DebugStatus.NONE) {
/* 112 */       StringBuilder log = new StringBuilder();
/* 113 */       log.append("[debugger] To enable debugging for ");
/* 114 */       log.append(option);
/* 115 */       log.append(" add the option \"-D");
/* 116 */       log.append(prop);
/* 117 */       log.append("=true\" to your launch config as a VM argument (").append(type).append(").");
/* 118 */       BCLog.logger.info(log);
/*     */     } 
/* 120 */     return false;
/*     */   }
/*     */   
/*     */   private static String getProp(String string) {
/* 124 */     return "buildcraft." + string + ".debug";
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\BCDebugging.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */