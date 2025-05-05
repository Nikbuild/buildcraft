/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import com.google.common.base.Splitter;
/*    */ import net.minecraft.util.StatCollector;
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
/*    */ public final class StringUtils
/*    */ {
/* 17 */   public static final Splitter newLineSplitter = Splitter.on("\\n");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String localize(String key) {
/* 26 */     return StatCollector.func_74838_a(key);
/*    */   }
/*    */   
/*    */   public static boolean canLocalize(String key) {
/* 30 */     return StatCollector.func_94522_b(key);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */