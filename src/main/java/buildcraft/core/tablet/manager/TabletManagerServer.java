/*    */ package buildcraft.core.tablet.manager;
/*    */ 
/*    */ import buildcraft.core.tablet.TabletBase;
/*    */ import buildcraft.core.tablet.TabletServer;
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import cpw.mods.fml.common.gameevent.PlayerEvent;
/*    */ import cpw.mods.fml.common.gameevent.TickEvent;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ public class TabletManagerServer
/*    */ {
/* 14 */   public static final TabletManagerServer INSTANCE = new TabletManagerServer();
/*    */   
/* 16 */   private HashMap<EntityPlayer, TabletThread> threads = new HashMap<EntityPlayer, TabletThread>();
/*    */   
/*    */   public TabletServer get(EntityPlayer player) {
/* 19 */     if (!this.threads.containsKey(player)) {
/* 20 */       TabletServer tablet = new TabletServer(player);
/* 21 */       TabletThread thread = new TabletThread((TabletBase)tablet);
/* 22 */       this.threads.put(player, thread);
/* 23 */       (new Thread(thread)).start();
/*    */     } 
/* 25 */     return (TabletServer)((TabletThread)this.threads.get(player)).getTablet();
/*    */   }
/*    */   
/*    */   public void onServerStopping() {
/* 29 */     for (TabletThread thread : this.threads.values()) {
/* 30 */       thread.stop();
/*    */     }
/* 32 */     this.threads.clear();
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void serverTick(TickEvent.ServerTickEvent event) {
/* 37 */     for (TabletThread thread : this.threads.values()) {
/* 38 */       thread.tick(0.05F);
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void playerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
/* 44 */     TabletThread thread = this.threads.get(event.player);
/* 45 */     if (thread != null) {
/* 46 */       thread.stop();
/* 47 */       this.threads.remove(event.player);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\tablet\manager\TabletManagerServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */