/*    */ package buildcraft.robotics;
/*    */ 
/*    */ import buildcraft.robotics.gui.ContainerRequester;
/*    */ import buildcraft.robotics.gui.ContainerZonePlan;
/*    */ import buildcraft.robotics.gui.GuiRequester;
/*    */ import buildcraft.robotics.gui.GuiZonePlan;
/*    */ import cpw.mods.fml.common.network.IGuiHandler;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ public class RoboticsGuiHandler
/*    */   implements IGuiHandler
/*    */ {
/*    */   public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/* 26 */     if (!world.func_72899_e(x, y, z)) {
/* 27 */       return null;
/*    */     }
/*    */     
/* 30 */     TileEntity tile = world.func_147438_o(x, y, z);
/*    */     
/* 32 */     switch (id) {
/*    */       case 15:
/* 34 */         if (!(tile instanceof TileZonePlan)) {
/* 35 */           return null;
/*    */         }
/* 37 */         return new GuiZonePlan((IInventory)player.field_71071_by, (TileZonePlan)tile);
/*    */       
/*    */       case 16:
/* 40 */         if (!(tile instanceof TileRequester)) {
/* 41 */           return null;
/*    */         }
/* 43 */         return new GuiRequester((IInventory)player.field_71071_by, (TileRequester)tile);
/*    */     } 
/*    */     
/* 46 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/* 54 */     if (!world.func_72899_e(x, y, z)) {
/* 55 */       return null;
/*    */     }
/*    */     
/* 58 */     TileEntity tile = world.func_147438_o(x, y, z);
/*    */     
/* 60 */     switch (id) {
/*    */       case 15:
/* 62 */         if (!(tile instanceof TileZonePlan)) {
/* 63 */           return null;
/*    */         }
/* 65 */         return new ContainerZonePlan((IInventory)player.field_71071_by, (TileZonePlan)tile);
/*    */ 
/*    */       
/*    */       case 16:
/* 69 */         if (!(tile instanceof TileRequester)) {
/* 70 */           return null;
/*    */         }
/* 72 */         return new ContainerRequester((IInventory)player.field_71071_by, (TileRequester)tile);
/*    */     } 
/*    */ 
/*    */     
/* 76 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\RoboticsGuiHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */