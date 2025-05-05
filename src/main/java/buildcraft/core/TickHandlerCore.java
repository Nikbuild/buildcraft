/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.core.proxy.CoreProxy;
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import cpw.mods.fml.common.gameevent.TickEvent;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TickHandlerCore
/*    */ {
/*    */   private boolean nagged;
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   @SubscribeEvent
/*    */   public void checkUpToDate(TickEvent.PlayerTickEvent evt) {
/* 27 */     if (this.nagged) {
/*    */       return;
/*    */     }
/*    */     
/* 31 */     EntityPlayer player = evt.player;
/*    */     
/* 33 */     if (Version.needsUpdateNoticeAndMarkAsSeen()) {
/* 34 */       player.func_145747_a((IChatComponent)new ChatComponentTranslation("bc_update.new_version", new Object[] {
/* 35 */               Version.getRecommendedVersion(), CoreProxy.proxy
/* 36 */               .getMinecraftVersion() }));
/* 37 */       player.func_145747_a((IChatComponent)new ChatComponentTranslation("bc_update.download", new Object[0]));
/* 38 */       player.func_145747_a((IChatComponent)new ChatComponentTranslation("bc_update.once", new Object[0]));
/* 39 */       player.func_145747_a((IChatComponent)new ChatComponentTranslation("bc_update.again", new Object[0]));
/* 40 */       player.func_145747_a((IChatComponent)new ChatComponentTranslation("bc_update.changelog", new Object[0]));
/*    */     } 
/*    */     
/* 43 */     this.nagged = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\TickHandlerCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */