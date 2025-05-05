/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.transport.gui.ContainerDiamondPipe;
/*     */ import buildcraft.transport.gui.ContainerEmeraldFluidPipe;
/*     */ import buildcraft.transport.gui.ContainerEmeraldPipe;
/*     */ import buildcraft.transport.gui.ContainerEmzuliPipe;
/*     */ import buildcraft.transport.gui.ContainerFilteredBuffer;
/*     */ import buildcraft.transport.gui.ContainerGateInterface;
/*     */ import buildcraft.transport.gui.GuiDiamondPipe;
/*     */ import buildcraft.transport.gui.GuiEmeraldFluidPipe;
/*     */ import buildcraft.transport.gui.GuiEmeraldPipe;
/*     */ import buildcraft.transport.gui.GuiEmzuliPipe;
/*     */ import buildcraft.transport.gui.GuiFilteredBuffer;
/*     */ import buildcraft.transport.gui.GuiGateInterface;
/*     */ import buildcraft.transport.pipes.PipeFluidsEmerald;
/*     */ import buildcraft.transport.pipes.PipeItemsEmerald;
/*     */ import buildcraft.transport.pipes.PipeItemsEmzuli;
/*     */ import cpw.mods.fml.common.network.IGuiHandler;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.Level;
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
/*     */ 
/*     */ public class TransportGuiHandler
/*     */   implements IGuiHandler
/*     */ {
/*     */   public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/*     */     try {
/*  43 */       if (!world.func_72899_e(x, y, z)) {
/*  44 */         return null;
/*     */       }
/*     */       
/*  47 */       TileEntity tile = world.func_147438_o(x, y, z);
/*     */       
/*  49 */       if (tile instanceof TileFilteredBuffer) {
/*  50 */         TileFilteredBuffer filteredBuffer = (TileFilteredBuffer)tile;
/*  51 */         return new ContainerFilteredBuffer(player.field_71071_by, filteredBuffer);
/*     */       } 
/*     */       
/*  54 */       if (!(tile instanceof IPipeTile)) {
/*  55 */         return null;
/*     */       }
/*     */       
/*  58 */       IPipeTile pipe = (IPipeTile)tile;
/*     */       
/*  60 */       if (pipe.getPipe() == null) {
/*  61 */         return null;
/*     */       }
/*     */       
/*  64 */       switch (id) {
/*     */         case 50:
/*  66 */           return new ContainerDiamondPipe((IInventory)player.field_71071_by, (IDiamondPipe)pipe.getPipe());
/*     */         
/*     */         case 52:
/*  69 */           return new ContainerEmeraldPipe((IInventory)player.field_71071_by, (PipeItemsEmerald)pipe.getPipe());
/*     */         
/*     */         case 53:
/*  72 */           return new ContainerEmzuliPipe((IInventory)player.field_71071_by, (PipeItemsEmzuli)pipe.getPipe());
/*     */         
/*     */         case 54:
/*  75 */           return new ContainerEmeraldFluidPipe((IInventory)player.field_71071_by, (PipeFluidsEmerald)pipe.getPipe());
/*     */         
/*     */         case 51:
/*  78 */           return new ContainerGateInterface((IInventory)player.field_71071_by, pipe.getPipe());
/*     */       } 
/*     */       
/*  81 */       return null;
/*     */     }
/*  83 */     catch (Exception ex) {
/*  84 */       BCLog.logger.log(Level.ERROR, "Failed to open GUI", ex);
/*     */       
/*  86 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/*     */     try {
/*  92 */       if (!world.func_72899_e(x, y, z)) {
/*  93 */         return null;
/*     */       }
/*     */       
/*  96 */       TileEntity tile = world.func_147438_o(x, y, z);
/*     */       
/*  98 */       if (tile instanceof TileFilteredBuffer) {
/*  99 */         TileFilteredBuffer filteredBuffer = (TileFilteredBuffer)tile;
/* 100 */         return new GuiFilteredBuffer(player.field_71071_by, filteredBuffer);
/*     */       } 
/*     */       
/* 103 */       if (!(tile instanceof IPipeTile)) {
/* 104 */         return null;
/*     */       }
/*     */       
/* 107 */       IPipeTile pipe = (IPipeTile)tile;
/*     */       
/* 109 */       if (pipe.getPipe() == null) {
/* 110 */         return null;
/*     */       }
/*     */       
/* 113 */       switch (id) {
/*     */         case 50:
/* 115 */           return new GuiDiamondPipe((IInventory)player.field_71071_by, (IDiamondPipe)pipe.getPipe());
/*     */         
/*     */         case 52:
/* 118 */           return new GuiEmeraldPipe((IInventory)player.field_71071_by, (PipeItemsEmerald)pipe.getPipe());
/*     */         
/*     */         case 53:
/* 121 */           return new GuiEmzuliPipe((IInventory)player.field_71071_by, (PipeItemsEmzuli)pipe.getPipe());
/*     */         
/*     */         case 54:
/* 124 */           return new GuiEmeraldFluidPipe((IInventory)player.field_71071_by, (PipeFluidsEmerald)pipe.getPipe());
/*     */         
/*     */         case 51:
/* 127 */           return new GuiGateInterface((IInventory)player.field_71071_by, pipe.getPipe());
/*     */       } 
/*     */       
/* 130 */       return null;
/*     */     }
/* 132 */     catch (Exception ex) {
/* 133 */       BCLog.logger.log(Level.ERROR, "Failed to open GUI", ex);
/*     */       
/* 135 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\TransportGuiHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */