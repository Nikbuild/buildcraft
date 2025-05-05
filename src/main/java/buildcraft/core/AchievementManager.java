/*    */ package buildcraft.core;
/*    */ 
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import cpw.mods.fml.common.gameevent.PlayerEvent;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.stats.Achievement;
/*    */ import net.minecraft.stats.StatBase;
/*    */ import net.minecraftforge.common.AchievementPage;
/*    */ 
/*    */ public class AchievementManager {
/*    */   public final AchievementPage page;
/*    */   
/*    */   public AchievementManager(String name) {
/* 14 */     this.page = new AchievementPage(name, new Achievement[0]);
/* 15 */     AchievementPage.registerAchievementPage(this.page);
/*    */   }
/*    */   
/*    */   public Achievement registerAchievement(Achievement a) {
/* 19 */     if (a.field_75990_d != null && a.field_75990_d.func_77973_b() != null) {
/* 20 */       this.page.getAchievements().add(a.func_75971_g());
/*    */     }
/* 22 */     return a;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onCrafting(PlayerEvent.ItemCraftedEvent event) {
/* 27 */     Item item = event.crafting.func_77973_b();
/* 28 */     int damage = event.crafting.func_77960_j();
/*    */     
/* 30 */     for (Achievement a : this.page.getAchievements()) {
/* 31 */       if (item.equals(a.field_75990_d.func_77973_b()) && damage == a.field_75990_d.func_77960_j())
/* 32 */         event.player.func_71064_a((StatBase)a, 1); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\AchievementManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */