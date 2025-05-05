/*    */ package buildcraft.core.tablet;
/*    */ 
/*    */ import buildcraft.core.lib.items.ItemBuildCraft;
/*    */ import buildcraft.core.tablet.manager.TabletManagerServer;
/*    */ import cpw.mods.fml.common.FMLCommonHandler;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class ItemTablet
/*    */   extends ItemBuildCraft
/*    */ {
/*    */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/* 15 */     if (world.field_72995_K) {
/* 16 */       FMLCommonHandler.instance().showGuiScreen(new GuiTablet(player));
/*    */     } else {
/* 18 */       TabletManagerServer.INSTANCE.get(player);
/*    */     } 
/*    */     
/* 21 */     return stack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\tablet\ItemTablet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */