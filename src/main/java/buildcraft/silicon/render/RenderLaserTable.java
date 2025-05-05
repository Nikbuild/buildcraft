/*     */ package buildcraft.silicon.render;
/*     */ 
/*     */ import buildcraft.core.lib.block.BlockBuildCraft;
/*     */ import buildcraft.core.lib.render.FakeBlock;
/*     */ import buildcraft.core.lib.render.RenderUtils;
/*     */ import buildcraft.core.lib.render.SubIcon;
/*     */ import buildcraft.core.render.BCSimpleBlockRenderingHandler;
/*     */ import buildcraft.silicon.SiliconProxy;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderLaserTable
/*     */   extends BCSimpleBlockRenderingHandler
/*     */ {
/*     */   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
/*  24 */     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/*  25 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/*  26 */     BlockBuildCraft bcBlock = (BlockBuildCraft)block;
/*  27 */     switch (metadata) {
/*     */       case 0:
/*  29 */         renderAssemblyTable(renderer, true, 0, 0, 0, bcBlock);
/*     */         break;
/*     */       case 1:
/*  32 */         renderAdvancedCraftingTable(renderer, true, 0, 0, 0, bcBlock);
/*     */         break;
/*     */       case 2:
/*  35 */         renderIntegrationTable(renderer, true, 0, 0, 0, bcBlock);
/*     */         break;
/*     */       case 3:
/*  38 */         renderChargingTable(renderer, true, 0, 0, 0, bcBlock);
/*     */         break;
/*     */       case 4:
/*  41 */         renderProgrammingTable(renderer, true, 0, 0, 0, bcBlock);
/*     */         break;
/*     */       case 5:
/*  44 */         renderStampingTable(renderer, true, 0, 0, 0, bcBlock);
/*     */         break;
/*     */     } 
/*  47 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
/*  52 */     BlockBuildCraft bcBlock = (BlockBuildCraft)block;
/*  53 */     switch (world.func_72805_g(x, y, z))
/*     */     { case 0:
/*  55 */         renderAssemblyTable(renderer, false, x, y, z, bcBlock);
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
/*     */ 
/*     */ 
/*     */         
/*  76 */         return true;case 1: renderAdvancedCraftingTable(renderer, false, x, y, z, bcBlock); return true;case 2: renderIntegrationTable(renderer, false, x, y, z, bcBlock); return true;case 3: renderChargingTable(renderer, false, x, y, z, bcBlock); return true;case 4: renderProgrammingTable(renderer, false, x, y, z, bcBlock); return true;case 5: renderStampingTable(renderer, false, x, y, z, bcBlock); return true; }  fixEmptyAlphaPass(x, y, z); return true;
/*     */   }
/*     */   
/*     */   private void renderCube(RenderBlocks renderer, boolean isInventory, int xPos, int yPos, int zPos, float xB, float yB, float zB, int w, int h, int d, int topX, int topY, IIcon base, int mask) {
/*  80 */     int xI = (int)(xB * 16.0F);
/*  81 */     int yI = 16 - (int)(yB * 16.0F) - h;
/*  82 */     int zI = (int)(zB * 16.0F);
/*     */     
/*  84 */     FakeBlock block = FakeBlock.INSTANCE;
/*  85 */     block.setRenderMask(mask);
/*  86 */     block.setColor(16777215);
/*     */     
/*  88 */     IIcon[] icons = block.getTextureState().popArray();
/*  89 */     icons[0] = (IIcon)new SubIcon(base, topX + w - xI, topY - zI, 16, 16, 64);
/*  90 */     icons[1] = (IIcon)new SubIcon(base, topX - xI, topY - zI, 16, 16, 64);
/*  91 */     icons[2] = (IIcon)new SubIcon(base, topX - xI, topY + d - yI, 16, 16, 64);
/*  92 */     icons[3] = (IIcon)new SubIcon(base, topX + w + d - xI, topY + d - yI, 16, 16, 64);
/*  93 */     icons[4] = (IIcon)new SubIcon(base, topX - d - zI, topY + d - yI, 16, 16, 64);
/*  94 */     icons[5] = (IIcon)new SubIcon(base, topX + w - zI, topY + d - yI, 16, 16, 64);
/*     */     
/*  96 */     renderer.func_147782_a(xB, yB, zB, (xB + w / 16.0F), (yB + h / 16.0F), (zB + d / 16.0F));
/*  97 */     if (isInventory) {
/*  98 */       RenderUtils.drawBlockItem(renderer, Tessellator.field_78398_a, (Block)block, 0);
/*     */     } else {
/* 100 */       renderer.func_147736_d((Block)block, xPos, yPos, zPos, 1.0F, 1.0F, 1.0F);
/*     */     } 
/* 102 */     block.getTextureState().pushArray();
/* 103 */     block.setRenderMask(63);
/*     */   }
/*     */   
/*     */   private void renderAssemblyTable(RenderBlocks renderer, boolean isInv, int x, int y, int z, BlockBuildCraft block) {
/* 107 */     if (!isInv && block.getCurrentRenderPass() != 0) {
/* 108 */       fixEmptyAlphaPass(x, y, z);
/*     */       return;
/*     */     } 
/* 111 */     IIcon base = block.func_149691_a(0, 0);
/* 112 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.0F, 0.0F, 16, 2, 16, 16, 21, base, 63);
/* 113 */     renderCube(renderer, isInv, x, y, z, 0.0625F, 0.125F, 0.0625F, 14, 1, 14, 18, 39, base, 60);
/* 114 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.1875F, 0.0F, 16, 5, 16, 16, 0, base, 63);
/*     */   }
/*     */   
/*     */   private void renderChargingTable(RenderBlocks renderer, boolean isInv, int x, int y, int z, BlockBuildCraft block) {
/* 118 */     if (!isInv && block.getCurrentRenderPass() != 0) {
/* 119 */       fixEmptyAlphaPass(x, y, z);
/*     */       return;
/*     */     } 
/* 122 */     IIcon base = block.func_149691_a(0, 3);
/* 123 */     renderCube(renderer, isInv, x, y, z, 0.0625F, 0.0F, 0.0625F, 14, 5, 14, 14, 19, base, 61);
/*     */ 
/*     */     
/* 126 */     renderCube(renderer, isInv, x, y, z, 0.8125F, 0.0F, 0.0F, 3, 5, 3, 3, 6, base, 61);
/* 127 */     renderCube(renderer, isInv, x, y, z, 0.8125F, 0.0F, 0.8125F, 3, 5, 3, 3, 6, base, 61);
/* 128 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.0F, 0.8125F, 3, 5, 3, 3, 6, base, 61);
/* 129 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.0F, 0.0F, 3, 5, 3, 3, 6, base, 61);
/*     */     
/* 131 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.3125F, 0.0F, 16, 3, 16, 16, 0, base, 63);
/*     */   }
/*     */   
/*     */   private void renderProgrammingTable(RenderBlocks renderer, boolean isInv, int x, int y, int z, BlockBuildCraft block) {
/* 135 */     IIcon base = block.func_149691_a(0, 4);
/* 136 */     if (block.getCurrentRenderPass() != 0) {
/* 137 */       renderCube(renderer, isInv, x, y, z, 0.25F, 0.375F, 0.25F, 8, 2, 8, 8, 48, base, 2);
/* 138 */       if (!isInv) {
/*     */         return;
/*     */       }
/*     */     } 
/* 142 */     renderCube(renderer, isInv, x, y, z, 0.0625F, 0.0F, 0.0625F, 14, 3, 14, 14, 23, base, 63);
/*     */ 
/*     */     
/* 145 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.0F, 0.0F, 4, 3, 4, 4, 0, base, 61);
/* 146 */     renderCube(renderer, isInv, x, y, z, 0.75F, 0.0F, 0.0F, 4, 3, 4, 4, 0, base, 61);
/* 147 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.0F, 0.75F, 4, 3, 4, 4, 0, base, 61);
/* 148 */     renderCube(renderer, isInv, x, y, z, 0.75F, 0.0F, 0.75F, 4, 3, 4, 4, 0, base, 61);
/*     */ 
/*     */     
/* 151 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.1875F, 0.0F, 4, 5, 16, 16, 2, base, 62);
/* 152 */     renderCube(renderer, isInv, x, y, z, 0.25F, 0.1875F, 0.75F, 8, 5, 4, 28, 9, base, 14);
/*     */     
/* 154 */     renderCube(renderer, isInv, x, y, z, 0.3125F, 0.1875F, 0.3125F, 6, 2, 6, 6, 40, base, 62);
/*     */     
/* 156 */     renderCube(renderer, isInv, x, y, z, 0.25F, 0.1875F, 0.0F, 8, 5, 4, 28, 0, base, 14);
/* 157 */     renderCube(renderer, isInv, x, y, z, 0.75F, 0.1875F, 0.0F, 4, 5, 16, 40, 43, base, 62);
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderIntegrationTable(RenderBlocks renderer, boolean isInv, int x, int y, int z, BlockBuildCraft block) {
/* 162 */     IIcon base = block.func_149691_a(0, 2);
/* 163 */     if (!isInv && block.getCurrentRenderPass() != 0) {
/* 164 */       fixEmptyAlphaPass(x, y, z);
/*     */       return;
/*     */     } 
/* 167 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.0F, 0.0F, 16, 1, 16, 16, 21, base, 63);
/*     */ 
/*     */     
/* 170 */     renderCube(renderer, isInv, x, y, z, 0.0625F, 0.0625F, 0.0625F, 4, 2, 4, 4, 0, base, 60);
/* 171 */     renderCube(renderer, isInv, x, y, z, 0.6875F, 0.0625F, 0.0625F, 4, 2, 4, 4, 0, base, 60);
/* 172 */     renderCube(renderer, isInv, x, y, z, 0.0625F, 0.0625F, 0.6875F, 4, 2, 4, 4, 0, base, 60);
/* 173 */     renderCube(renderer, isInv, x, y, z, 0.6875F, 0.0625F, 0.6875F, 4, 2, 4, 4, 0, base, 60);
/*     */ 
/*     */     
/* 176 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.1875F, 0.0F, 5, 5, 16, 16, 0, base, 63);
/* 177 */     renderCube(renderer, isInv, x, y, z, 0.3125F, 0.1875F, 0.6875F, 6, 5, 5, 47, 10, base, 15);
/*     */     
/* 179 */     renderCube(renderer, isInv, x, y, z, 0.3125F, 0.1875F, 0.3125F, 6, 4, 6, 6, 38, base, 63);
/*     */     
/* 181 */     renderCube(renderer, isInv, x, y, z, 0.3125F, 0.1875F, 0.0F, 6, 5, 5, 47, 0, base, 15);
/* 182 */     renderCube(renderer, isInv, x, y, z, 0.6875F, 0.1875F, 0.0F, 5, 5, 16, 38, 43, base, 63);
/*     */   }
/*     */   
/*     */   private void renderAdvancedCraftingTable(RenderBlocks renderer, boolean isInv, int x, int y, int z, BlockBuildCraft block) {
/* 186 */     if (!isInv && block.getCurrentRenderPass() != 0) {
/* 187 */       fixEmptyAlphaPass(x, y, z);
/*     */       return;
/*     */     } 
/* 190 */     IIcon base = block.func_149691_a(0, 1);
/* 191 */     renderCube(renderer, isInv, x, y, z, 0.125F, 0.0F, 0.125F, 12, 3, 12, 12, 21, base, 61);
/*     */ 
/*     */     
/* 194 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.0F, 0.0F, 3, 3, 3, 3, 0, base, 61);
/* 195 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.0F, 0.8125F, 3, 3, 3, 3, 0, base, 61);
/* 196 */     renderCube(renderer, isInv, x, y, z, 0.8125F, 0.0F, 0.0F, 3, 3, 3, 3, 0, base, 61);
/* 197 */     renderCube(renderer, isInv, x, y, z, 0.8125F, 0.0F, 0.8125F, 3, 3, 3, 3, 0, base, 61);
/*     */     
/* 199 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.1875F, 0.0F, 16, 5, 16, 16, 0, base, 63);
/*     */   }
/*     */   
/*     */   private void renderStampingTable(RenderBlocks renderer, boolean isInv, int x, int y, int z, BlockBuildCraft block) {
/* 203 */     if (!isInv && block.getCurrentRenderPass() != 0) {
/* 204 */       fixEmptyAlphaPass(x, y, z);
/*     */       return;
/*     */     } 
/* 207 */     IIcon base = block.func_149691_a(0, 5);
/* 208 */     renderCube(renderer, isInv, x, y, z, 0.125F, 0.0F, 0.125F, 12, 3, 12, 12, 21, base, 61);
/*     */ 
/*     */     
/* 211 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.0F, 0.0F, 3, 3, 3, 3, 0, base, 61);
/* 212 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.0F, 0.8125F, 3, 3, 3, 3, 0, base, 61);
/* 213 */     renderCube(renderer, isInv, x, y, z, 0.8125F, 0.0F, 0.0F, 3, 3, 3, 3, 0, base, 61);
/* 214 */     renderCube(renderer, isInv, x, y, z, 0.8125F, 0.0F, 0.8125F, 3, 3, 3, 3, 0, base, 61);
/*     */     
/* 216 */     renderCube(renderer, isInv, x, y, z, 0.0F, 0.1875F, 0.0F, 16, 5, 16, 16, 0, base, 63);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender3DInInventory(int modelId) {
/* 221 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderId() {
/* 226 */     return SiliconProxy.laserTableModel;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\render\RenderLaserTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */