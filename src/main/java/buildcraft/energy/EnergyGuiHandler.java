/*    */ package buildcraft.energy;
/*    */ 
/*    */ import buildcraft.core.lib.engines.TileEngineWithInventory;
/*    */ import buildcraft.energy.gui.ContainerEngine;
/*    */ import buildcraft.energy.gui.GuiCombustionEngine;
/*    */ import buildcraft.energy.gui.GuiStoneEngine;
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
/*    */ public class EnergyGuiHandler
/*    */   implements IGuiHandler
/*    */ {
/*    */   public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/* 28 */     if (!world.func_72899_e(x, y, z)) {
/* 29 */       return null;
/*    */     }
/*    */     
/* 32 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 33 */     if (!(tile instanceof TileEngineWithInventory)) {
/* 34 */       return null;
/*    */     }
/*    */     
/* 37 */     TileEngineWithInventory engine = (TileEngineWithInventory)tile;
/*    */     
/* 39 */     switch (id) {
/*    */       
/*    */       case 20:
/* 42 */         return new GuiCombustionEngine(player.field_71071_by, (TileEngineIron)engine);
/*    */       
/*    */       case 21:
/* 45 */         return new GuiStoneEngine(player.field_71071_by, (TileEngineStone)engine);
/*    */     } 
/*    */     
/* 48 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/* 55 */     if (!world.func_72899_e(x, y, z)) {
/* 56 */       return null;
/*    */     }
/*    */     
/* 59 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 60 */     if (!(tile instanceof TileEngineWithInventory)) {
/* 61 */       return null;
/*    */     }
/*    */     
/* 64 */     TileEngineWithInventory engine = (TileEngineWithInventory)tile;
/*    */     
/* 66 */     switch (id) {
/*    */       
/*    */       case 20:
/* 69 */         return new ContainerEngine(player.field_71071_by, engine);
/*    */       
/*    */       case 21:
/* 72 */         return new ContainerEngine(player.field_71071_by, engine);
/*    */     } 
/*    */     
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\EnergyGuiHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */