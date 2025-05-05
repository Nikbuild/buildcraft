/*    */ package buildcraft.builders;
/*    */ 
/*    */ import buildcraft.api.blueprints.SchematicBlock;
/*    */ import buildcraft.api.blueprints.SchematicFluid;
/*    */ import buildcraft.builders.schematics.SchematicStairs;
/*    */ import buildcraft.core.blueprints.SchematicRegistry;
/*    */ import buildcraft.core.builders.schematics.SchematicBlockFloored;
/*    */ import buildcraft.core.builders.schematics.SchematicIgnore;
/*    */ import buildcraft.core.builders.schematics.SchematicTileCreative;
/*    */ import buildcraft.core.builders.schematics.SchematicWallSide;
/*    */ import buildcraft.core.lib.utils.FluidUtils;
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraftforge.fluids.FluidStack;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class HeuristicBlockDetection
/*    */ {
/*    */   public static void start() {
/* 35 */     Iterator<Block> i = Block.field_149771_c.iterator();
/* 36 */     while (i.hasNext()) {
/* 37 */       Block block = i.next();
/* 38 */       if (block == null || block == Blocks.field_150350_a) {
/*    */         continue;
/*    */       }
/*    */       
/* 42 */       for (int meta = 0; meta < 16; meta++) {
/* 43 */         if (!SchematicRegistry.INSTANCE.isSupported(block, meta))
/*    */           try {
/* 45 */             if (block.hasTileEntity(meta)) {
/*    */ 
/*    */               
/* 48 */               SchematicRegistry.INSTANCE.registerSchematicBlock(block, meta, SchematicTileCreative.class, new Object[0]);
/*    */             } else {
/*    */ 
/*    */ 
/*    */               
/* 53 */               try { if (block instanceof net.minecraft.block.BlockDynamicLiquid) {
/*    */                   
/* 55 */                   SchematicRegistry.INSTANCE.registerSchematicBlock(block, meta, SchematicIgnore.class, new Object[0]);
/*    */                 }
/* 57 */                 else if (block instanceof net.minecraft.block.BlockBush || block instanceof net.minecraftforge.common.IPlantable || block instanceof net.minecraft.block.IGrowable || block instanceof net.minecraft.block.BlockBasePressurePlate) {
/* 58 */                   SchematicRegistry.INSTANCE.registerSchematicBlock(block, meta, SchematicBlockFloored.class, new Object[0]);
/* 59 */                 } else if (block instanceof net.minecraft.block.BlockLever || block instanceof net.minecraft.block.BlockTorch || block instanceof net.minecraft.block.BlockButton) {
/* 60 */                   SchematicRegistry.INSTANCE.registerSchematicBlock(block, meta, SchematicWallSide.class, new Object[0]);
/* 61 */                 } else if (block instanceof net.minecraft.block.BlockStairs) {
/* 62 */                   SchematicRegistry.INSTANCE.registerSchematicBlock(block, meta, SchematicStairs.class, new Object[0]);
/*    */                 } else {
/* 64 */                   FluidStack fstack = FluidUtils.getFluidStackFromBlock(block);
/* 65 */                   if (fstack != null) {
/* 66 */                     SchematicRegistry.INSTANCE.registerSchematicBlock(block, meta, SchematicFluid.class, new Object[] { fstack });
/*    */                   } else {
/* 68 */                     SchematicRegistry.INSTANCE.registerSchematicBlock(block, meta, SchematicBlock.class, new Object[0]);
/*    */                   }
/*    */                 
/*    */                 }  }
/* 72 */               catch (Exception e)
/* 73 */               { e.printStackTrace(); } 
/*    */             } 
/* 75 */           } catch (Exception exception) {} 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\HeuristicBlockDetection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */