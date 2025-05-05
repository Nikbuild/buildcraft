/*    */ package buildcraft.api.transport;
/*    */ 
/*    */ import buildcraft.api.transport.pluggable.PipePluggable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.world.World;
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
/*    */ public abstract class PipeManager
/*    */ {
/* 24 */   public static List<IStripesHandler> stripesHandlers = new ArrayList<IStripesHandler>();
/* 25 */   public static ArrayList<Class<? extends PipePluggable>> pipePluggables = new ArrayList<Class<? extends PipePluggable>>();
/* 26 */   private static Map<String, Class<? extends PipePluggable>> pipePluggableNames = new HashMap<String, Class<? extends PipePluggable>>();
/*    */   
/* 28 */   private static Map<Class<? extends PipePluggable>, String> pipePluggableByNames = new HashMap<Class<? extends PipePluggable>, String>();
/*    */   
/* 30 */   private static Map<IStripesHandler, Integer> stripesHandlerPriorities = new HashMap<IStripesHandler, Integer>();
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static boolean canExtractItems(Object extractor, World world, int i, int j, int k) {
/* 35 */     return true;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static boolean canExtractFluids(Object extractor, World world, int i, int j, int k) {
/* 40 */     return true;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static void registerStripesHandler(IStripesHandler handler) {
/* 45 */     registerStripesHandler(handler, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void registerStripesHandler(IStripesHandler handler, int priority) {
/* 54 */     stripesHandlers.add(handler);
/* 55 */     stripesHandlerPriorities.put(handler, Integer.valueOf(priority));
/*    */     
/* 57 */     Collections.sort(stripesHandlers, new Comparator<IStripesHandler>()
/*    */         {
/*    */           public int compare(IStripesHandler o1, IStripesHandler o2) {
/* 60 */             return ((Integer)PipeManager.stripesHandlerPriorities.get(o2)).intValue() - ((Integer)PipeManager.stripesHandlerPriorities.get(o1)).intValue();
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public static void registerPipePluggable(Class<? extends PipePluggable> pluggable, String name) {
/* 66 */     pipePluggables.add(pluggable);
/* 67 */     pipePluggableNames.put(name, pluggable);
/* 68 */     pipePluggableByNames.put(pluggable, name);
/*    */   }
/*    */   
/*    */   public static Class<?> getPluggableByName(String pluggableName) {
/* 72 */     return pipePluggableNames.get(pluggableName);
/*    */   }
/*    */   
/*    */   public static String getPluggableName(Class<? extends PipePluggable> aClass) {
/* 76 */     return pipePluggableByNames.get(aClass);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\transport\PipeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */