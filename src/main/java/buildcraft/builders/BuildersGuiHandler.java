/*     */ package buildcraft.builders;
/*     */ 
/*     */ import buildcraft.builders.gui.ContainerArchitect;
/*     */ import buildcraft.builders.gui.ContainerBlueprintLibrary;
/*     */ import buildcraft.builders.gui.ContainerBuilder;
/*     */ import buildcraft.builders.gui.ContainerFiller;
/*     */ import buildcraft.builders.gui.GuiArchitect;
/*     */ import buildcraft.builders.gui.GuiBlueprintLibrary;
/*     */ import buildcraft.builders.gui.GuiBuilder;
/*     */ import buildcraft.builders.gui.GuiFiller;
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
/*     */ 
/*     */ public class BuildersGuiHandler
/*     */   implements IGuiHandler
/*     */ {
/*     */   public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/*  31 */     if (!world.func_72899_e(x, y, z)) {
/*  32 */       return null;
/*     */     }
/*     */     
/*  35 */     TileEntity tile = world.func_147438_o(x, y, z);
/*     */     
/*  37 */     switch (id) {
/*     */       
/*     */       case 10:
/*  40 */         if (!(tile instanceof TileArchitect)) {
/*  41 */           return null;
/*     */         }
/*  43 */         return new GuiArchitect(player, (TileArchitect)tile);
/*     */       
/*     */       case 13:
/*  46 */         if (!(tile instanceof TileBlueprintLibrary)) {
/*  47 */           return null;
/*     */         }
/*  49 */         return new GuiBlueprintLibrary(player, (TileBlueprintLibrary)tile);
/*     */       
/*     */       case 11:
/*  52 */         if (!(tile instanceof TileBuilder)) {
/*  53 */           return null;
/*     */         }
/*  55 */         return new GuiBuilder((IInventory)player.field_71071_by, (TileBuilder)tile);
/*     */       
/*     */       case 12:
/*  58 */         if (!(tile instanceof TileFiller)) {
/*  59 */           return null;
/*     */         }
/*  61 */         return new GuiFiller((IInventory)player.field_71071_by, (TileFiller)tile);
/*     */     } 
/*     */     
/*  64 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/*  72 */     if (!world.func_72899_e(x, y, z)) {
/*  73 */       return null;
/*     */     }
/*     */     
/*  76 */     TileEntity tile = world.func_147438_o(x, y, z);
/*     */     
/*  78 */     switch (id) {
/*     */       
/*     */       case 10:
/*  81 */         if (!(tile instanceof TileArchitect)) {
/*  82 */           return null;
/*     */         }
/*  84 */         return new ContainerArchitect(player, (TileArchitect)tile);
/*     */       
/*     */       case 13:
/*  87 */         if (!(tile instanceof TileBlueprintLibrary)) {
/*  88 */           return null;
/*     */         }
/*  90 */         return new ContainerBlueprintLibrary(player, (TileBlueprintLibrary)tile);
/*     */       
/*     */       case 11:
/*  93 */         if (!(tile instanceof TileBuilder)) {
/*  94 */           return null;
/*     */         }
/*  96 */         return new ContainerBuilder((IInventory)player.field_71071_by, (TileBuilder)tile);
/*     */       
/*     */       case 12:
/*  99 */         if (!(tile instanceof TileFiller)) {
/* 100 */           return null;
/*     */         }
/* 102 */         return new ContainerFiller((IInventory)player.field_71071_by, (TileFiller)tile);
/*     */     } 
/*     */     
/* 105 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\BuildersGuiHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */