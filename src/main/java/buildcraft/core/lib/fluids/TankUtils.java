/*     */ package buildcraft.core.lib.fluids;
/*     */ 
/*     */ import buildcraft.core.lib.TileBuffer;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
/*     */ import net.minecraftforge.fluids.IFluidTank;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TankUtils
/*     */ {
/*     */   public static boolean handleRightClick(IFluidHandler tank, ForgeDirection side, EntityPlayer player, boolean fill, boolean drain) {
/*  37 */     if (player == null || tank == null) {
/*  38 */       return false;
/*     */     }
/*  40 */     ItemStack current = player.field_71071_by.func_70448_g();
/*  41 */     if (current != null) {
/*     */       
/*  43 */       FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(current);
/*     */       
/*  45 */       if (fill && liquid != null) {
/*  46 */         int used = tank.fill(side, liquid, true);
/*     */         
/*  48 */         if (used > 0) {
/*  49 */           if (!player.field_71075_bZ.field_75098_d) {
/*  50 */             player.field_71071_by.func_70299_a(player.field_71071_by.field_70461_c, InvUtils.consumeItem(current));
/*  51 */             player.field_71071_by.func_70296_d();
/*     */           } 
/*  53 */           return true;
/*     */         }
/*     */       
/*  56 */       } else if (drain) {
/*     */         
/*  58 */         FluidStack available = tank.drain(side, 2147483647, false);
/*  59 */         if (available != null) {
/*  60 */           ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, current);
/*     */           
/*  62 */           liquid = FluidContainerRegistry.getFluidForFilledItem(filled);
/*  63 */           if (liquid != null) {
/*     */             
/*  65 */             if (current.field_77994_a > 1) {
/*  66 */               if (!player.field_71071_by.func_70441_a(filled)) {
/*  67 */                 return false;
/*     */               }
/*  69 */               player.field_71071_by.func_70299_a(player.field_71071_by.field_70461_c, InvUtils.consumeItem(current));
/*  70 */               player.field_71071_by.func_70296_d();
/*     */             } else {
/*  72 */               player.field_71071_by.func_70299_a(player.field_71071_by.field_70461_c, InvUtils.consumeItem(current));
/*  73 */               player.field_71071_by.func_70299_a(player.field_71071_by.field_70461_c, filled);
/*  74 */               player.field_71071_by.func_70296_d();
/*     */             } 
/*     */             
/*  77 */             tank.drain(side, liquid.amount, true);
/*  78 */             return true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  83 */     return false;
/*     */   }
/*     */   
/*     */   public static Block getFluidBlock(Fluid fluid, boolean moving) {
/*  87 */     if (fluid == FluidRegistry.WATER) {
/*  88 */       return moving ? (Block)Blocks.field_150358_i : Blocks.field_150355_j;
/*     */     }
/*  90 */     if (fluid == FluidRegistry.LAVA) {
/*  91 */       return moving ? (Block)Blocks.field_150356_k : Blocks.field_150353_l;
/*     */     }
/*  93 */     return fluid.getBlock();
/*     */   }
/*     */   
/*     */   public static void pushFluidToConsumers(IFluidTank tank, int flowCap, TileBuffer[] tileBuffer) {
/*  97 */     int amountToPush = flowCap;
/*  98 */     for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/*  99 */       FluidStack fluidStack = tank.drain(amountToPush, false);
/* 100 */       if (fluidStack != null && fluidStack.amount > 0) {
/* 101 */         TileEntity tile = tileBuffer[side.ordinal()].getTile();
/* 102 */         if (tile instanceof IFluidHandler) {
/* 103 */           int used = ((IFluidHandler)tile).fill(side.getOpposite(), fluidStack, true);
/* 104 */           if (used > 0) {
/* 105 */             amountToPush -= used;
/* 106 */             tank.drain(used, true);
/* 107 */             if (amountToPush <= 0)
/*     */               return; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\fluids\TankUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */