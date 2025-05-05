/*    */ package buildcraft.core.tablet.manager;
/*    */ 
/*    */ import buildcraft.core.tablet.TabletBase;
/*    */ import buildcraft.core.tablet.TabletClient;
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import cpw.mods.fml.common.gameevent.PlayerEvent;
/*    */ 
/*    */ public class TabletManagerClient {
/*  9 */   public static final TabletManagerClient INSTANCE = new TabletManagerClient();
/*    */   
/*    */   private static TabletClient currentTablet;
/*    */   private static TabletThread currentTabletThread;
/*    */   
/*    */   public TabletThread get() {
/* 15 */     if (currentTablet == null) {
/* 16 */       currentTablet = new TabletClient();
/* 17 */       currentTabletThread = new TabletThread((TabletBase)currentTablet);
/* 18 */       (new Thread(currentTabletThread)).start();
/*    */     } 
/* 20 */     return currentTabletThread;
/*    */   }
/*    */   
/*    */   public void onServerStopping() {
/* 24 */     if (currentTablet != null) {
/* 25 */       currentTablet = null;
/* 26 */       currentTabletThread.stop();
/* 27 */       currentTabletThread = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void playerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
/* 33 */     if (currentTablet != null) {
/* 34 */       currentTablet = null;
/* 35 */       currentTabletThread.stop();
/* 36 */       currentTabletThread = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\tablet\manager\TabletManagerClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */