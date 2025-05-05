/*     */ package buildcraft.api.robots;
/*     */ 
/*     */ import buildcraft.api.core.BCLog;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RobotManager
/*     */ {
/*     */   public static IRobotRegistryProvider registryProvider;
/*  15 */   public static ArrayList<Class<? extends AIRobot>> aiRobots = new ArrayList<>();
/*     */   
/*     */   private static Map<Class<? extends AIRobot>, String> aiRobotsNames;
/*  18 */   private static Map<String, Class<? extends AIRobot>> aiRobotsByNames = new HashMap<>(); static {
/*  19 */     aiRobotsNames = new HashMap<>();
/*  20 */     aiRobotsByLegacyClassNames = new HashMap<>();
/*  21 */     resourceIdNames = new HashMap<>();
/*  22 */     resourceIdByNames = new HashMap<>();
/*  23 */     resourceIdLegacyClassNames = new HashMap<>();
/*  24 */     dockingStationNames = new HashMap<>();
/*  25 */     dockingStationByNames = new HashMap<>();
/*     */     
/*  27 */     registerResourceId((Class)ResourceIdBlock.class, "resourceIdBlock", "buildcraft.core.robots.ResourceIdBlock");
/*  28 */     registerResourceId((Class)ResourceIdRequest.class, "resourceIdRequest", "buildcraft.core.robots.ResourceIdRequest");
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<String, Class<? extends AIRobot>> aiRobotsByLegacyClassNames;
/*     */   
/*     */   private static Map<Class<? extends ResourceId>, String> resourceIdNames;
/*     */   
/*     */   private static Map<String, Class<? extends ResourceId>> resourceIdByNames;
/*     */   
/*     */   private static Map<String, Class<? extends ResourceId>> resourceIdLegacyClassNames;
/*     */   private static Map<Class<? extends DockingStation>, String> dockingStationNames;
/*     */   private static Map<String, Class<? extends DockingStation>> dockingStationByNames;
/*     */   
/*     */   public static void registerAIRobot(Class<? extends AIRobot> aiRobot, String name) {
/*  43 */     registerAIRobot(aiRobot, name, null);
/*     */   }
/*     */   
/*     */   public static void registerAIRobot(Class<? extends AIRobot> aiRobot, String name, String legacyClassName) {
/*  47 */     if (aiRobotsByNames.containsKey(name)) {
/*  48 */       BCLog.logger.info("Overriding " + ((Class)aiRobotsByNames.get(name)).getName() + " with " + aiRobot.getName());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  53 */       aiRobot.getConstructor(new Class[] { EntityRobotBase.class });
/*  54 */     } catch (NoSuchMethodException e) {
/*  55 */       throw new RuntimeException("AI class " + aiRobot.getName() + " lacks NBT load constructor! This is a bug!");
/*     */     } 
/*     */     
/*  58 */     aiRobots.add(aiRobot);
/*  59 */     aiRobotsByNames.put(name, aiRobot);
/*  60 */     aiRobotsNames.put(aiRobot, name);
/*  61 */     if (legacyClassName != null) {
/*  62 */       aiRobotsByLegacyClassNames.put(legacyClassName, aiRobot);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Class<?> getAIRobotByName(String aiRobotName) {
/*  67 */     return aiRobotsByNames.get(aiRobotName);
/*     */   }
/*     */   
/*     */   public static String getAIRobotName(Class<? extends AIRobot> aiRobotClass) {
/*  71 */     return aiRobotsNames.get(aiRobotClass);
/*     */   }
/*     */   
/*     */   public static Class<?> getAIRobotByLegacyClassName(String aiRobotLegacyClassName) {
/*  75 */     return aiRobotsByLegacyClassNames.get(aiRobotLegacyClassName);
/*     */   }
/*     */   
/*     */   public static void registerResourceId(Class<? extends ResourceId> resourceId, String name) {
/*  79 */     registerResourceId(resourceId, name, null);
/*     */   }
/*     */   
/*     */   public static void registerResourceId(Class<? extends ResourceId> resourceId, String name, String legacyClassName) {
/*  83 */     resourceIdByNames.put(name, resourceId);
/*  84 */     resourceIdNames.put(resourceId, name);
/*  85 */     if (legacyClassName != null) {
/*  86 */       resourceIdLegacyClassNames.put(legacyClassName, resourceId);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Class<?> getResourceIdByName(String resourceIdName) {
/*  91 */     return resourceIdByNames.get(resourceIdName);
/*     */   }
/*     */   
/*     */   public static String getResourceIdName(Class<? extends ResourceId> resourceIdClass) {
/*  95 */     return resourceIdNames.get(resourceIdClass);
/*     */   }
/*     */   
/*     */   public static Class<?> getResourceIdByLegacyClassName(String resourceIdLegacyClassName) {
/*  99 */     return resourceIdLegacyClassNames.get(resourceIdLegacyClassName);
/*     */   }
/*     */   
/*     */   public static void registerDockingStation(Class<? extends DockingStation> dockingStation, String name) {
/* 103 */     dockingStationByNames.put(name, dockingStation);
/* 104 */     dockingStationNames.put(dockingStation, name);
/*     */   }
/*     */   
/*     */   public static Class<? extends DockingStation> getDockingStationByName(String dockingStationTypeName) {
/* 108 */     return dockingStationByNames.get(dockingStationTypeName);
/*     */   }
/*     */   
/*     */   public static String getDockingStationName(Class<? extends DockingStation> dockingStation) {
/* 112 */     return dockingStationNames.get(dockingStation);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\robots\RobotManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */