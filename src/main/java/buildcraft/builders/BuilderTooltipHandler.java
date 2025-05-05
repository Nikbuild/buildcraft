/*    */ package buildcraft.builders;
/*    */ 
/*    */ import buildcraft.builders.gui.ContainerBuilder;
/*    */ import buildcraft.core.blueprints.RequirementItemStack;
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraftforge.event.entity.player.ItemTooltipEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderTooltipHandler
/*    */ {
/*    */   @SubscribeEvent
/*    */   public void itemTooltipEvent(ItemTooltipEvent event) {
/* 19 */     if (event.itemStack != null && event.entityPlayer != null && event.entityPlayer.field_71070_bA != null && event.entityPlayer.field_71070_bA instanceof ContainerBuilder) {
/*    */       
/* 21 */       ContainerBuilder containerBuilder = (ContainerBuilder)event.entityPlayer.field_71070_bA;
/* 22 */       TileBuilder builder = containerBuilder.getBuilder();
/* 23 */       if (builder != null) {
/* 24 */         List<RequirementItemStack> needs = builder.getNeededItems();
/* 25 */         if (needs != null)
/* 26 */           for (RequirementItemStack ris : needs) {
/* 27 */             if (ris.stack == event.itemStack)
/* 28 */               event.toolTip.add(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "Needed: " + ris.size); 
/*    */           }  
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\BuilderTooltipHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */