/*    */ package buildcraft.api.lists;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public final class ListRegistry
/*    */ {
/* 10 */   public static final List<Class<? extends Item>> itemClassAsType = new ArrayList<>();
/* 11 */   private static final List<ListMatchHandler> handlers = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void registerHandler(ListMatchHandler h) {
/* 18 */     if (h != null) {
/* 19 */       handlers.add(h);
/*    */     }
/*    */   }
/*    */   
/*    */   public static List<ListMatchHandler> getHandlers() {
/* 24 */     return Collections.unmodifiableList(handlers);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\lists\ListRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */