/*     */ package buildcraft.transport.render;
/*     */ 
/*     */ import buildcraft.core.lib.render.RenderEntityBlock;
/*     */ import buildcraft.core.lib.render.RenderUtils;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeTransportFluids;
/*     */ import buildcraft.transport.utils.FluidRenderData;
/*     */ import gnu.trove.iterator.TIntIterator;
/*     */ import gnu.trove.set.hash.TIntHashSet;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.IntHashMap;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PipeTransportFluidsRenderer
/*     */   extends PipeTransportRenderer<PipeTransportFluids>
/*     */ {
/*  26 */   private static final IntHashMap displayFluidLists = new IntHashMap();
/*  27 */   private static final TIntHashSet displayFluidListsSet = new TIntHashSet();
/*     */   
/*     */   private static final int LIQUID_STAGES = 40;
/*  30 */   private static final int[] angleY = new int[] { 0, 0, 270, 90, 0, 180 };
/*  31 */   private static final int[] angleZ = new int[] { 90, 270, 0, 0, 0, 0 };
/*     */   
/*     */   private class DisplayFluidList {
/*  34 */     public int[] sideHorizontal = new int[40]; private DisplayFluidList() {}
/*  35 */     public int[] sideVertical = new int[40];
/*  36 */     public int[] centerHorizontal = new int[40];
/*  37 */     public int[] centerVertical = new int[40];
/*     */   }
/*     */   
/*     */   protected static void clear() {
/*  41 */     TIntIterator i = displayFluidListsSet.iterator();
/*     */     
/*  43 */     while (i.hasNext()) {
/*  44 */       GL11.glDeleteLists(i.next(), 1);
/*     */     }
/*     */     
/*  47 */     displayFluidListsSet.clear();
/*  48 */     displayFluidLists.func_76046_c();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useServerTileIfPresent() {
/*  53 */     return false;
/*     */   }
/*     */   
/*     */   private DisplayFluidList getDisplayFluidLists(int liquidId, int skylight, int blocklight, int flags, World world) {
/*  57 */     int finalBlockLight = Math.max(flags & 0x1F, blocklight);
/*  58 */     int listId = (liquidId & 0x3FFFF) << 13 | (flags & 0xE0 | finalBlockLight) << 5 | skylight & 0x1F;
/*     */     
/*  60 */     if (displayFluidLists.func_76037_b(listId)) {
/*  61 */       return (DisplayFluidList)displayFluidLists.func_76041_a(listId);
/*     */     }
/*     */     
/*  64 */     Fluid fluid = FluidRegistry.getFluid(liquidId);
/*     */     
/*  66 */     if (fluid == null) {
/*  67 */       return null;
/*     */     }
/*     */     
/*  70 */     DisplayFluidList d = new DisplayFluidList();
/*  71 */     displayFluidLists.func_76038_a(listId, d);
/*  72 */     displayFluidListsSet.add(listId);
/*     */     
/*  74 */     RenderEntityBlock.RenderInfo block = new RenderEntityBlock.RenderInfo();
/*     */     
/*  76 */     if (fluid.getBlock() != null) {
/*  77 */       block.baseBlock = fluid.getBlock();
/*     */     } else {
/*  79 */       block.baseBlock = Blocks.field_150355_j;
/*     */     } 
/*     */     
/*  82 */     block.texture = fluid.getStillIcon();
/*  83 */     block.brightness = skylight << 16 | finalBlockLight;
/*     */     
/*  85 */     float size = 0.5F;
/*     */ 
/*     */ 
/*     */     
/*  89 */     for (int s = 0; s < 40; s++) {
/*  90 */       float ratio = s / 40.0F;
/*     */ 
/*     */ 
/*     */       
/*  94 */       d.sideHorizontal[s] = GLAllocation.func_74526_a(1);
/*  95 */       GL11.glNewList(d.sideHorizontal[s], 4864);
/*     */       
/*  97 */       block.minX = 0.0D;
/*  98 */       block.minZ = 0.25999999046325684D;
/*     */       
/* 100 */       block.maxX = block.minX + (size / 2.0F) + 0.009999999776482582D;
/* 101 */       block.maxZ = block.minZ + size - 0.019999999552965164D;
/*     */       
/* 103 */       block.minY = 0.25999999046325684D;
/* 104 */       block.maxY = block.minY + ((size - 0.02F) * ratio);
/*     */       
/* 106 */       RenderEntityBlock.INSTANCE.renderBlock(block);
/*     */       
/* 108 */       GL11.glEndList();
/*     */ 
/*     */ 
/*     */       
/* 112 */       d.sideVertical[s] = GLAllocation.func_74526_a(1);
/* 113 */       GL11.glNewList(d.sideVertical[s], 4864);
/*     */       
/* 115 */       block.minY = 0.74D;
/* 116 */       block.maxY = 1.0D;
/*     */       
/* 118 */       block.minX = 0.5D - ((size / 2.0F) - 0.01D) * ratio;
/* 119 */       block.maxX = 0.5D + ((size / 2.0F) - 0.01D) * ratio;
/*     */       
/* 121 */       block.minZ = 0.5D - ((size / 2.0F) - 0.01D) * ratio;
/* 122 */       block.maxZ = 0.5D + ((size / 2.0F) - 0.01D) * ratio;
/*     */       
/* 124 */       RenderEntityBlock.INSTANCE.renderBlock(block);
/*     */       
/* 126 */       GL11.glEndList();
/*     */ 
/*     */ 
/*     */       
/* 130 */       d.centerHorizontal[s] = GLAllocation.func_74526_a(1);
/* 131 */       GL11.glNewList(d.centerHorizontal[s], 4864);
/*     */       
/* 133 */       block.minX = 0.26D;
/* 134 */       block.minZ = 0.26D;
/*     */       
/* 136 */       block.maxX = block.minX + size - 0.02D;
/* 137 */       block.maxZ = block.minZ + size - 0.02D;
/*     */       
/* 139 */       block.minY = 0.26D;
/* 140 */       block.maxY = block.minY + ((size - 0.02F) * ratio);
/*     */       
/* 142 */       RenderEntityBlock.INSTANCE.renderBlock(block);
/*     */       
/* 144 */       GL11.glEndList();
/*     */ 
/*     */ 
/*     */       
/* 148 */       d.centerVertical[s] = GLAllocation.func_74526_a(1);
/* 149 */       GL11.glNewList(d.centerVertical[s], 4864);
/*     */       
/* 151 */       block.minY = 0.26D;
/* 152 */       block.maxY = 0.74D;
/*     */       
/* 154 */       block.minX = 0.5D - ((size / 2.0F) - 0.02D) * ratio;
/* 155 */       block.maxX = 0.5D + ((size / 2.0F) - 0.02D) * ratio;
/*     */       
/* 157 */       block.minZ = 0.5D - ((size / 2.0F) - 0.02D) * ratio;
/* 158 */       block.maxZ = 0.5D + ((size / 2.0F) - 0.02D) * ratio;
/*     */       
/* 160 */       RenderEntityBlock.INSTANCE.renderBlock(block);
/*     */       
/* 162 */       GL11.glEndList();
/*     */     } 
/*     */ 
/*     */     
/* 166 */     return d;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Pipe<PipeTransportFluids> pipe, double x, double y, double z, float f) {
/* 171 */     PipeTransportFluids trans = (PipeTransportFluids)pipe.transport;
/*     */     
/* 173 */     boolean needsRender = false;
/* 174 */     FluidRenderData renderData = trans.renderCache;
/*     */     
/* 176 */     for (int i = 0; i < 7; i++) {
/* 177 */       if (renderData.amount[i] > 0) {
/* 178 */         needsRender = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 183 */     if (!needsRender) {
/*     */       return;
/*     */     }
/*     */     
/* 187 */     if (pipe.container == null) {
/*     */       return;
/*     */     }
/*     */     
/* 191 */     GL11.glPushMatrix();
/* 192 */     GL11.glPushAttrib(8192);
/* 193 */     GL11.glEnable(2884);
/* 194 */     GL11.glDisable(2896);
/* 195 */     GL11.glEnable(3042);
/* 196 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 198 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/*     */     
/* 200 */     int skylight = pipe.container.getWorld().func_72925_a(EnumSkyBlock.Sky, pipe.container.x(), pipe.container.y(), pipe.container.z());
/* 201 */     int blocklight = pipe.container.getWorld().func_72925_a(EnumSkyBlock.Block, pipe.container.x(), pipe.container.y(), pipe.container.z());
/*     */     
/* 203 */     boolean sides = false, above = false;
/*     */     
/* 205 */     for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 206 */       int j = side.ordinal();
/*     */       
/* 208 */       if (renderData.amount[j] > 0)
/*     */       {
/*     */ 
/*     */         
/* 212 */         if (pipe.container.isPipeConnected(side)) {
/*     */ 
/*     */ 
/*     */           
/* 216 */           DisplayFluidList d = getDisplayFluidLists(renderData.fluidID, skylight, blocklight, renderData.flags, pipe.container
/* 217 */               .func_145831_w());
/*     */           
/* 219 */           if (d != null) {
/*     */ 
/*     */ 
/*     */             
/* 223 */             int stage = (int)(renderData.amount[j] / trans.getCapacity() * 39.0F);
/*     */             
/* 225 */             GL11.glPushMatrix();
/* 226 */             int list = 0;
/*     */             
/* 228 */             switch (ForgeDirection.VALID_DIRECTIONS[j]) {
/*     */               case UP:
/* 230 */                 above = true;
/* 231 */                 list = d.sideVertical[stage];
/*     */                 break;
/*     */               case DOWN:
/* 234 */                 GL11.glTranslatef(0.0F, -0.75F, 0.0F);
/* 235 */                 list = d.sideVertical[stage];
/*     */                 break;
/*     */               case EAST:
/*     */               case WEST:
/*     */               case SOUTH:
/*     */               case NORTH:
/* 241 */                 sides = true;
/*     */                 
/* 243 */                 GL11.glTranslatef(0.5F, 0.0F, 0.5F);
/* 244 */                 GL11.glRotatef(angleY[j], 0.0F, 1.0F, 0.0F);
/* 245 */                 GL11.glRotatef(angleZ[j], 0.0F, 0.0F, 1.0F);
/* 246 */                 GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
/* 247 */                 list = d.sideHorizontal[stage];
/*     */                 break;
/*     */             } 
/*     */             
/* 251 */             bindTexture(TextureMap.field_110575_b);
/* 252 */             RenderUtils.setGLColorFromInt(renderData.color);
/* 253 */             GL11.glCallList(list);
/* 254 */             GL11.glPopMatrix();
/*     */           } 
/*     */         }  } 
/* 257 */     }  if (renderData.amount[6] > 0) {
/* 258 */       DisplayFluidList d = getDisplayFluidLists(renderData.fluidID, skylight, blocklight, renderData.flags, pipe.container
/* 259 */           .func_145831_w());
/*     */       
/* 261 */       if (d != null) {
/* 262 */         int stage = (int)(renderData.amount[6] / trans.getCapacity() * 39.0F);
/*     */         
/* 264 */         bindTexture(TextureMap.field_110575_b);
/* 265 */         RenderUtils.setGLColorFromInt(renderData.color);
/*     */         
/* 267 */         if (above) {
/* 268 */           GL11.glCallList(d.centerVertical[stage]);
/*     */         }
/*     */         
/* 271 */         if (!above || sides) {
/* 272 */           GL11.glCallList(d.centerHorizontal[stage]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 278 */     GL11.glPopAttrib();
/* 279 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\render\PipeTransportFluidsRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */