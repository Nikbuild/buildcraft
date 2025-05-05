/*    */ package buildcraft.factory;
/*    */ 
/*    */ import buildcraft.factory.gui.ContainerAutoWorkbench;
/*    */ import buildcraft.factory.gui.ContainerHopper;
/*    */ import buildcraft.factory.gui.ContainerRefinery;
/*    */ import buildcraft.factory.gui.GuiAutoCrafting;
/*    */ import buildcraft.factory.gui.GuiHopper;
/*    */ import buildcraft.factory.gui.GuiRefinery;
/*    */ import cpw.mods.fml.common.network.IGuiHandler;
/*    */ import net.minecraft.entity.player.EntityPlayer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FactoryGuiHandler
/*    */   implements IGuiHandler
/*    */ {
/*    */   public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/* 30 */     if (!world.func_72899_e(x, y, z)) {
/* 31 */       return null;
/*    */     }
/*    */     
/* 34 */     TileEntity tile = world.func_147438_o(x, y, z);
/*    */     
/* 36 */     switch (id) {
/*    */       
/*    */       case 30:
/* 39 */         if (!(tile instanceof TileAutoWorkbench)) {
/* 40 */           return null;
/*    */         }
/* 42 */         return new GuiAutoCrafting(player.field_71071_by, world, (TileAutoWorkbench)tile);
/*    */ 
/*    */       
/*    */       case 31:
/* 46 */         if (!(tile instanceof TileRefinery)) {
/* 47 */           return null;
/*    */         }
/* 49 */         return new GuiRefinery(player.field_71071_by, (TileRefinery)tile);
/*    */ 
/*    */       
/*    */       case 32:
/* 53 */         if (!(tile instanceof TileHopper)) {
/* 54 */           return null;
/*    */         }
/* 56 */         return new GuiHopper(player.field_71071_by, (TileHopper)tile);
/*    */     } 
/*    */ 
/*    */     
/* 60 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/* 67 */     if (!world.func_72899_e(x, y, z)) {
/* 68 */       return null;
/*    */     }
/*    */     
/* 71 */     TileEntity tile = world.func_147438_o(x, y, z);
/*    */     
/* 73 */     switch (id) {
/*    */       
/*    */       case 30:
/* 76 */         if (!(tile instanceof TileAutoWorkbench)) {
/* 77 */           return null;
/*    */         }
/* 79 */         return new ContainerAutoWorkbench(player.field_71071_by, (TileAutoWorkbench)tile);
/*    */ 
/*    */       
/*    */       case 31:
/* 83 */         if (!(tile instanceof TileRefinery)) {
/* 84 */           return null;
/*    */         }
/* 86 */         return new ContainerRefinery(player.field_71071_by, (TileRefinery)tile);
/*    */ 
/*    */       
/*    */       case 32:
/* 90 */         if (!(tile instanceof TileHopper)) {
/* 91 */           return null;
/*    */         }
/* 93 */         return new ContainerHopper(player.field_71071_by, (TileHopper)tile);
/*    */     } 
/*    */ 
/*    */     
/* 97 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\FactoryGuiHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */