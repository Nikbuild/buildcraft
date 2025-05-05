/*    */ package buildcraft.core.list;
/*    */ 
/*    */ import buildcraft.api.items.IList;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraftforge.event.entity.player.ItemTooltipEvent;
/*    */ 
/*    */ 
/*    */ public class ListTooltipHandler
/*    */ {
/*    */   @SubscribeEvent
/*    */   public void itemTooltipEvent(ItemTooltipEvent event) {
/* 15 */     if (event.itemStack != null && event.entityPlayer != null && event.entityPlayer.field_71070_bA != null && event.entityPlayer.field_71070_bA instanceof ContainerListNew) {
/*    */       
/* 17 */       ItemStack list = event.entityPlayer.func_71045_bC();
/* 18 */       if (list != null && list.func_77973_b() instanceof IList && (
/* 19 */         (IList)list.func_77973_b()).matches(list, event.itemStack))
/* 20 */         event.toolTip.add(EnumChatFormatting.GREEN + StringUtils.localize("tip.list.matches")); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\list\ListTooltipHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */