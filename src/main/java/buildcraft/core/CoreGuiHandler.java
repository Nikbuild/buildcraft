/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.core.list.ContainerListNew;
/*    */ import buildcraft.core.list.ContainerListOld;
/*    */ import buildcraft.core.list.GuiListNew;
/*    */ import buildcraft.core.list.GuiListOld;
/*    */ import cpw.mods.fml.common.network.IGuiHandler;
/*    */ import net.minecraft.entity.player.EntityPlayer;
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
/*    */ public class CoreGuiHandler
/*    */   implements IGuiHandler
/*    */ {
/*    */   public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/* 25 */     if (id == 17)
/* 26 */       return new GuiListOld(player); 
/* 27 */     if (id == 19) {
/* 28 */       return new GuiListNew(player);
/*    */     }
/* 30 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/* 35 */     if (id == 17)
/* 36 */       return new ContainerListOld(player); 
/* 37 */     if (id == 19) {
/* 38 */       return new ContainerListNew(player);
/*    */     }
/* 40 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\CoreGuiHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */