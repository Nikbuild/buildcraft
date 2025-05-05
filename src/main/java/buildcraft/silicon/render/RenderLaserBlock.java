/*     */ package buildcraft.silicon.render;
/*     */ 
/*     */ import buildcraft.core.lib.render.RenderUtils;
/*     */ import buildcraft.core.render.BCSimpleBlockRenderingHandler;
/*     */ import buildcraft.silicon.SiliconProxy;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public class RenderLaserBlock
/*     */   extends BCSimpleBlockRenderingHandler
/*     */ {
/*     */   public int getRenderId() {
/*  28 */     return SiliconProxy.laserBlockModel;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender3DInInventory(int modelId) {
/*  33 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderWorldBlock(IBlockAccess iblockaccess, int x, int y, int z, Block block, int l, RenderBlocks renderblocks) {
/*  38 */     int meta = iblockaccess.func_72805_g(x, y, z);
/*     */     
/*  40 */     if (meta == ForgeDirection.EAST.ordinal()) {
/*  41 */       renderblocks.field_147875_q = 2;
/*  42 */       renderblocks.field_147873_r = 1;
/*  43 */       renderblocks.field_147867_u = 1;
/*  44 */       renderblocks.field_147865_v = 2;
/*     */       
/*  46 */       renderblocks.func_147782_a(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);
/*  47 */       renderblocks.func_147784_q(block, x, y, z);
/*     */       
/*  49 */       renderblocks.func_147782_a(0.25D, 0.3125D, 0.3125D, 0.8125D, 0.6875D, 0.6875D);
/*  50 */       renderblocks.func_147784_q(block, x, y, z);
/*  51 */     } else if (meta == ForgeDirection.WEST.ordinal()) {
/*  52 */       renderblocks.field_147875_q = 1;
/*  53 */       renderblocks.field_147873_r = 2;
/*  54 */       renderblocks.field_147867_u = 2;
/*  55 */       renderblocks.field_147865_v = 1;
/*     */       
/*  57 */       renderblocks.func_147782_a(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*  58 */       renderblocks.func_147784_q(block, x, y, z);
/*     */       
/*  60 */       renderblocks.func_147782_a(0.1875D, 0.3125D, 0.3125D, 0.75D, 0.6875D, 0.6875D);
/*  61 */       renderblocks.func_147784_q(block, x, y, z);
/*  62 */     } else if (meta == ForgeDirection.NORTH.ordinal()) {
/*  63 */       renderblocks.field_147871_s = 1;
/*  64 */       renderblocks.field_147869_t = 2;
/*     */       
/*  66 */       renderblocks.func_147782_a(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);
/*  67 */       renderblocks.func_147784_q(block, x, y, z);
/*     */       
/*  69 */       renderblocks.func_147782_a(0.3125D, 0.3125D, 0.1875D, 0.6875D, 0.6875D, 0.75D);
/*  70 */       renderblocks.func_147784_q(block, x, y, z);
/*  71 */     } else if (meta == ForgeDirection.SOUTH.ordinal()) {
/*  72 */       renderblocks.field_147871_s = 2;
/*  73 */       renderblocks.field_147869_t = 1;
/*  74 */       renderblocks.field_147867_u = 3;
/*  75 */       renderblocks.field_147865_v = 3;
/*     */       
/*  77 */       renderblocks.func_147782_a(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
/*  78 */       renderblocks.func_147784_q(block, x, y, z);
/*     */       
/*  80 */       renderblocks.func_147782_a(0.3125D, 0.3125D, 0.25D, 0.6875D, 0.6875D, 0.8125D);
/*  81 */       renderblocks.func_147784_q(block, x, y, z);
/*  82 */     } else if (meta == ForgeDirection.DOWN.ordinal()) {
/*  83 */       renderblocks.field_147875_q = 3;
/*  84 */       renderblocks.field_147873_r = 3;
/*  85 */       renderblocks.field_147871_s = 3;
/*  86 */       renderblocks.field_147869_t = 3;
/*     */       
/*  88 */       renderblocks.func_147782_a(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);
/*  89 */       renderblocks.func_147784_q(block, x, y, z);
/*     */       
/*  91 */       renderblocks.func_147782_a(0.3125D, 0.1875D, 0.3125D, 0.6875D, 0.75D, 0.6875D);
/*  92 */       renderblocks.func_147784_q(block, x, y, z);
/*  93 */     } else if (meta == ForgeDirection.UP.ordinal()) {
/*  94 */       renderblocks.func_147782_a(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
/*  95 */       renderblocks.func_147784_q(block, x, y, z);
/*     */       
/*  97 */       renderblocks.func_147782_a(0.3125D, 0.25D, 0.3125D, 0.6875D, 0.8125D, 0.6875D);
/*  98 */       renderblocks.func_147784_q(block, x, y, z);
/*     */     } 
/*     */     
/* 101 */     renderblocks.field_147875_q = 0;
/* 102 */     renderblocks.field_147873_r = 0;
/* 103 */     renderblocks.field_147871_s = 0;
/* 104 */     renderblocks.field_147869_t = 0;
/* 105 */     renderblocks.field_147867_u = 0;
/* 106 */     renderblocks.field_147865_v = 0;
/*     */     
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderInventoryBlock(Block block, int i, int j, RenderBlocks renderblocks) {
/* 113 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/*     */     
/* 115 */     renderblocks.func_147782_a(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
/* 116 */     RenderUtils.drawBlockItem(renderblocks, Tessellator.field_78398_a, block, 1);
/*     */     
/* 118 */     renderblocks.func_147782_a(0.3125D, 0.25D, 0.3125D, 0.6875D, 0.8125D, 0.6875D);
/* 119 */     RenderUtils.drawBlockItem(renderblocks, Tessellator.field_78398_a, block, 1);
/*     */     
/* 121 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\render\RenderLaserBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */