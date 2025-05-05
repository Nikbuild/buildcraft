/*     */ package buildcraft.silicon;
/*     */ 
/*     */ import buildcraft.silicon.gui.ContainerAdvancedCraftingTable;
/*     */ import buildcraft.silicon.gui.ContainerAssemblyTable;
/*     */ import buildcraft.silicon.gui.ContainerChargingTable;
/*     */ import buildcraft.silicon.gui.ContainerIntegrationTable;
/*     */ import buildcraft.silicon.gui.ContainerPackager;
/*     */ import buildcraft.silicon.gui.ContainerProgrammingTable;
/*     */ import buildcraft.silicon.gui.ContainerStampingTable;
/*     */ import buildcraft.silicon.gui.GuiAdvancedCraftingTable;
/*     */ import buildcraft.silicon.gui.GuiAssemblyTable;
/*     */ import buildcraft.silicon.gui.GuiChargingTable;
/*     */ import buildcraft.silicon.gui.GuiIntegrationTable;
/*     */ import buildcraft.silicon.gui.GuiPackager;
/*     */ import buildcraft.silicon.gui.GuiProgrammingTable;
/*     */ import buildcraft.silicon.gui.GuiStampingTable;
/*     */ import cpw.mods.fml.common.network.IGuiHandler;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SiliconGuiHandler
/*     */   implements IGuiHandler
/*     */ {
/*     */   public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/*  36 */     if (!world.func_72899_e(x, y, z)) {
/*  37 */       return null;
/*     */     }
/*     */     
/*  40 */     TileEntity tile = world.func_147438_o(x, y, z);
/*     */     
/*  42 */     switch (id) {
/*     */       
/*     */       case 0:
/*  45 */         if (!(tile instanceof TileAssemblyTable)) {
/*  46 */           return null;
/*     */         }
/*  48 */         return new GuiAssemblyTable((IInventory)player.field_71071_by, (TileAssemblyTable)tile);
/*     */ 
/*     */       
/*     */       case 1:
/*  52 */         if (!(tile instanceof TileAdvancedCraftingTable)) {
/*  53 */           return null;
/*     */         }
/*  55 */         return new GuiAdvancedCraftingTable(player.field_71071_by, (TileAdvancedCraftingTable)tile);
/*     */ 
/*     */       
/*     */       case 2:
/*  59 */         if (!(tile instanceof TileIntegrationTable)) {
/*  60 */           return null;
/*     */         }
/*  62 */         return new GuiIntegrationTable(player.field_71071_by, (TileIntegrationTable)tile);
/*     */ 
/*     */       
/*     */       case 3:
/*  66 */         if (!(tile instanceof TileChargingTable)) {
/*  67 */           return null;
/*     */         }
/*  69 */         return new GuiChargingTable(player.field_71071_by, (TileChargingTable)tile);
/*     */ 
/*     */       
/*     */       case 4:
/*  73 */         if (!(tile instanceof TileProgrammingTable)) {
/*  74 */           return null;
/*     */         }
/*  76 */         return new GuiProgrammingTable((IInventory)player.field_71071_by, (TileProgrammingTable)tile);
/*     */ 
/*     */       
/*     */       case 5:
/*  80 */         if (!(tile instanceof TileStampingTable)) {
/*  81 */           return null;
/*     */         }
/*  83 */         return new GuiStampingTable(player.field_71071_by, (TileStampingTable)tile);
/*     */ 
/*     */       
/*     */       case 10:
/*  87 */         if (!(tile instanceof TilePackager)) {
/*  88 */           return null;
/*     */         }
/*  90 */         return new GuiPackager(player.field_71071_by, (TilePackager)tile);
/*     */     } 
/*     */ 
/*     */     
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/* 100 */     if (!world.func_72899_e(x, y, z)) {
/* 101 */       return null;
/*     */     }
/*     */     
/* 104 */     TileEntity tile = world.func_147438_o(x, y, z);
/*     */     
/* 106 */     switch (id) {
/*     */       
/*     */       case 0:
/* 109 */         if (!(tile instanceof TileAssemblyTable)) {
/* 110 */           return null;
/*     */         }
/* 112 */         return new ContainerAssemblyTable((IInventory)player.field_71071_by, (TileAssemblyTable)tile);
/*     */ 
/*     */       
/*     */       case 1:
/* 116 */         if (!(tile instanceof TileAdvancedCraftingTable)) {
/* 117 */           return null;
/*     */         }
/* 119 */         return new ContainerAdvancedCraftingTable(player.field_71071_by, (TileAdvancedCraftingTable)tile);
/*     */ 
/*     */       
/*     */       case 2:
/* 123 */         if (!(tile instanceof TileIntegrationTable)) {
/* 124 */           return null;
/*     */         }
/* 126 */         return new ContainerIntegrationTable(player.field_71071_by, (TileIntegrationTable)tile);
/*     */ 
/*     */       
/*     */       case 3:
/* 130 */         if (!(tile instanceof TileChargingTable)) {
/* 131 */           return null;
/*     */         }
/* 133 */         return new ContainerChargingTable(player.field_71071_by, (TileChargingTable)tile);
/*     */ 
/*     */       
/*     */       case 4:
/* 137 */         if (!(tile instanceof TileProgrammingTable)) {
/* 138 */           return null;
/*     */         }
/* 140 */         return new ContainerProgrammingTable((IInventory)player.field_71071_by, (TileProgrammingTable)tile);
/*     */ 
/*     */       
/*     */       case 5:
/* 144 */         if (!(tile instanceof TileStampingTable)) {
/* 145 */           return null;
/*     */         }
/* 147 */         return new ContainerStampingTable(player.field_71071_by, (TileStampingTable)tile);
/*     */ 
/*     */       
/*     */       case 10:
/* 151 */         if (!(tile instanceof TilePackager)) {
/* 152 */           return null;
/*     */         }
/* 154 */         return new ContainerPackager(player.field_71071_by, (TilePackager)tile);
/*     */     } 
/*     */ 
/*     */     
/* 158 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\SiliconGuiHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */