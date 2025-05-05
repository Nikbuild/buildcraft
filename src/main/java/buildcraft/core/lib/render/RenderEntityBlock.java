/*     */ package buildcraft.core.lib.render;
/*     */ 
/*     */ import buildcraft.core.lib.EntityBlock;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
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
/*     */ public final class RenderEntityBlock
/*     */   extends Render
/*     */ {
/*  32 */   public static RenderEntityBlock INSTANCE = new RenderEntityBlock();
/*     */ 
/*     */ 
/*     */   
/*  36 */   protected RenderBlocks renderBlocks = this.field_147909_c;
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation func_110775_a(Entity entity) {
/*  41 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   
/*     */   public static class RenderInfo
/*     */   {
/*  46 */     public double minX = 0.0D;
/*  47 */     public double minY = 0.0D;
/*  48 */     public double minZ = 0.0D;
/*  49 */     public double maxX = 1.0D;
/*  50 */     public double maxY = 1.0D;
/*  51 */     public double maxZ = 1.0D;
/*  52 */     public Block baseBlock = (Block)Blocks.field_150354_m;
/*  53 */     public IIcon texture = null;
/*  54 */     public IIcon[] textureArray = null;
/*  55 */     public boolean[] renderSide = new boolean[] { true, true, true, true, true, true };
/*  56 */     public int light = -1;
/*  57 */     public int brightness = -1;
/*     */ 
/*     */     
/*     */     public RenderInfo() {}
/*     */     
/*     */     public RenderInfo(Block template, IIcon[] texture) {
/*  63 */       this();
/*  64 */       this.baseBlock = template;
/*  65 */       this.textureArray = texture;
/*     */     }
/*     */     
/*     */     public RenderInfo(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
/*  69 */       this();
/*  70 */       setBounds(minX, minY, minZ, maxX, maxY, maxZ);
/*     */     }
/*     */     
/*     */     public void setSkyBlockLight(World world, int x, int y, int z, int light) {
/*  74 */       this.brightness = world.func_72925_a(EnumSkyBlock.Sky, x, y, z) << 16 | light;
/*     */     }
/*     */     
/*     */     public float getBlockBrightness(IBlockAccess iblockaccess, int i, int j, int k) {
/*  78 */       return this.baseBlock.func_149677_c(iblockaccess, i, j, k);
/*     */     }
/*     */     
/*     */     public final void setBounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
/*  82 */       this.minX = minX;
/*  83 */       this.minY = minY;
/*  84 */       this.minZ = minZ;
/*  85 */       this.maxX = maxX;
/*  86 */       this.maxY = maxY;
/*  87 */       this.maxZ = maxZ;
/*     */     }
/*     */     
/*     */     public final void setRenderSingleSide(int side) {
/*  91 */       Arrays.fill(this.renderSide, false);
/*  92 */       this.renderSide[side] = true;
/*     */     }
/*     */     
/*     */     public final void setRenderAllSides() {
/*  96 */       Arrays.fill(this.renderSide, true);
/*     */     }
/*     */     
/*     */     public void rotate() {
/* 100 */       double temp = this.minX;
/* 101 */       this.minX = this.minZ;
/* 102 */       this.minZ = temp;
/*     */       
/* 104 */       temp = this.maxX;
/* 105 */       this.maxX = this.maxZ;
/* 106 */       this.maxZ = temp;
/*     */     }
/*     */     
/*     */     public void reverseX() {
/* 110 */       double temp = this.minX;
/* 111 */       this.minX = 1.0D - this.maxX;
/* 112 */       this.maxX = 1.0D - temp;
/*     */     }
/*     */     
/*     */     public void reverseZ() {
/* 116 */       double temp = this.minZ;
/* 117 */       this.minZ = 1.0D - this.maxZ;
/* 118 */       this.maxZ = 1.0D - temp;
/*     */     }
/*     */     
/*     */     public IIcon getBlockTextureFromSide(int i) {
/* 122 */       if (this.texture != null) {
/* 123 */         return this.texture;
/*     */       }
/*     */       
/* 126 */       int index = i;
/*     */       
/* 128 */       if (this.textureArray == null || this.textureArray.length == 0) {
/* 129 */         return this.baseBlock.func_149733_h(index);
/*     */       }
/* 131 */       if (index >= this.textureArray.length) {
/* 132 */         index = 0;
/*     */       }
/*     */       
/* 135 */       return this.textureArray[index];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_76986_a(Entity entity, double x, double y, double z, float f, float f1) {
/* 142 */     doRenderBlock((EntityBlock)entity, x, y, z);
/*     */   }
/*     */   
/*     */   public void doRenderBlock(EntityBlock entity, double x, double y, double z) {
/* 146 */     if (entity.field_70128_L) {
/*     */       return;
/*     */     }
/*     */     
/* 150 */     this.field_76989_e = entity.shadowSize;
/* 151 */     RenderInfo util = new RenderInfo();
/* 152 */     util.textureArray = entity.texture;
/* 153 */     func_110776_a(TextureMap.field_110575_b);
/*     */     
/* 155 */     int iMax = (int)Math.ceil(entity.iSize) - 1;
/* 156 */     int jMax = (int)Math.ceil(entity.jSize) - 1;
/* 157 */     int kMax = (int)Math.ceil(entity.kSize) - 1;
/*     */     
/* 159 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/*     */     
/* 161 */     for (int iBase = 0; iBase <= iMax; iBase++) {
/* 162 */       for (int jBase = 0; jBase <= jMax; jBase++) {
/* 163 */         for (int kBase = 0; kBase <= kMax; kBase++) {
/* 164 */           util.renderSide[0] = (jBase == 0);
/* 165 */           util.renderSide[1] = (jBase == jMax);
/* 166 */           util.renderSide[2] = (kBase == 0);
/* 167 */           util.renderSide[3] = (kBase == kMax);
/* 168 */           util.renderSide[4] = (iBase == 0);
/* 169 */           util.renderSide[5] = (iBase == iMax);
/*     */           
/* 171 */           if (util.renderSide[0] || util.renderSide[1] || util.renderSide[2] || util.renderSide[3] || util.renderSide[4] || util.renderSide[5]) {
/*     */             
/* 173 */             util.minX = 0.0D;
/* 174 */             util.minY = 0.0D;
/* 175 */             util.minZ = 0.0D;
/*     */             
/* 177 */             double remainX = entity.iSize - iBase;
/* 178 */             double remainY = entity.jSize - jBase;
/* 179 */             double remainZ = entity.kSize - kBase;
/*     */             
/* 181 */             util.maxX = (remainX > 1.0D) ? 1.0D : remainX;
/* 182 */             util.maxY = (remainY > 1.0D) ? 1.0D : remainY;
/* 183 */             util.maxZ = (remainZ > 1.0D) ? 1.0D : remainZ;
/*     */             
/* 185 */             GL11.glPushMatrix();
/* 186 */             GL11.glRotatef(entity.rotationX, 1.0F, 0.0F, 0.0F);
/* 187 */             GL11.glRotatef(entity.rotationY, 0.0F, 1.0F, 0.0F);
/* 188 */             GL11.glRotatef(entity.rotationZ, 0.0F, 0.0F, 1.0F);
/* 189 */             GL11.glTranslatef(iBase, jBase, kBase);
/*     */             
/* 191 */             renderBlock(util);
/* 192 */             GL11.glPopMatrix();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     GL11.glTranslatef((float)-x, (float)-y, (float)-z);
/*     */   }
/*     */   
/*     */   public void renderBlock(RenderInfo info) {
/* 202 */     Tessellator tessellator = Tessellator.field_78398_a;
/* 203 */     tessellator.func_78382_b();
/*     */     
/* 205 */     this.renderBlocks.func_147782_a(info.minX, info.minY, info.minZ, info.maxX, info.maxY, info.maxZ);
/*     */     
/* 207 */     if (info.light != -1) {
/* 208 */       tessellator.func_78380_c(info.light << 20 | info.light << 4);
/* 209 */     } else if (info.brightness != -1) {
/* 210 */       tessellator.func_78380_c(info.brightness << 4);
/*     */     } 
/*     */     
/* 213 */     if (info.renderSide[0]) {
/* 214 */       tessellator.func_78375_b(0.0F, -1.0F, 0.0F);
/* 215 */       this.renderBlocks.func_147768_a(info.baseBlock, 0.0D, 0.0D, 0.0D, info.getBlockTextureFromSide(0));
/*     */     } 
/* 217 */     if (info.renderSide[1]) {
/* 218 */       tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
/* 219 */       this.renderBlocks.func_147806_b(info.baseBlock, 0.0D, 0.0D, 0.0D, info.getBlockTextureFromSide(1));
/*     */     } 
/* 221 */     if (info.renderSide[2]) {
/* 222 */       tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
/* 223 */       this.renderBlocks.func_147761_c(info.baseBlock, 0.0D, 0.0D, 0.0D, info.getBlockTextureFromSide(2));
/*     */     } 
/* 225 */     if (info.renderSide[3]) {
/* 226 */       tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/* 227 */       this.renderBlocks.func_147734_d(info.baseBlock, 0.0D, 0.0D, 0.0D, info.getBlockTextureFromSide(3));
/*     */     } 
/* 229 */     if (info.renderSide[4]) {
/* 230 */       tessellator.func_78375_b(-1.0F, 0.0F, 0.0F);
/* 231 */       this.renderBlocks.func_147798_e(info.baseBlock, 0.0D, 0.0D, 0.0D, info.getBlockTextureFromSide(4));
/*     */     } 
/* 233 */     if (info.renderSide[5]) {
/* 234 */       tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
/* 235 */       this.renderBlocks.func_147764_f(info.baseBlock, 0.0D, 0.0D, 0.0D, info.getBlockTextureFromSide(5));
/*     */     } 
/* 237 */     tessellator.func_78381_a();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\render\RenderEntityBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */