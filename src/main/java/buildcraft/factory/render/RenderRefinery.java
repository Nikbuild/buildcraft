/*     */ package buildcraft.factory.render;
/*     */ 
/*     */ import buildcraft.core.lib.fluids.Tank;
/*     */ import buildcraft.core.lib.render.FluidRenderer;
/*     */ import buildcraft.core.lib.render.IInventoryRenderer;
/*     */ import buildcraft.core.lib.render.RenderUtils;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import buildcraft.factory.TileRefinery;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ public class RenderRefinery
/*     */   extends TileEntitySpecialRenderer
/*     */   implements IInventoryRenderer
/*     */ {
/*  32 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftfactory:textures/blocks/refineryBlock/refinery.png");
/*     */   private static final float pixel = 0.0625F;
/*     */   private final ModelRenderer tank;
/*  35 */   private final ModelRenderer[] magnet = new ModelRenderer[4];
/*  36 */   private final ModelBase model = new ModelBase()
/*     */     {
/*     */     
/*     */     };
/*     */   
/*     */   public RenderRefinery() {
/*  42 */     this.tank = new ModelRenderer(this.model, 0, 0);
/*  43 */     this.tank.func_78789_a(-4.0F, -8.0F, -4.0F, 8, 16, 8);
/*  44 */     this.tank.field_78800_c = 8.0F;
/*  45 */     this.tank.field_78797_d = 8.0F;
/*  46 */     this.tank.field_78798_e = 8.0F;
/*     */ 
/*     */ 
/*     */     
/*  50 */     for (int i = 0; i < 4; i++) {
/*  51 */       this.magnet[i] = new ModelRenderer(this.model, 32, i * 8);
/*  52 */       this.magnet[i].func_78789_a(0.0F, -8.0F, -8.0F, 8, 4, 4);
/*  53 */       (this.magnet[i]).field_78800_c = 8.0F;
/*  54 */       (this.magnet[i]).field_78797_d = 8.0F;
/*  55 */       (this.magnet[i]).field_78798_e = 8.0F;
/*     */     } 
/*     */ 
/*     */     
/*  59 */     this.field_147501_a = TileEntityRendererDispatcher.field_147556_a;
/*     */   }
/*     */   
/*     */   public RenderRefinery(String baseTexture) {
/*  63 */     this();
/*     */   }
/*     */ 
/*     */   
/*     */   public void inventoryRender(double x, double y, double z, float f, float f1) {
/*  68 */     render((TileRefinery)null, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_147500_a(TileEntity tileentity, double x, double y, double z, float f) {
/*  74 */     render((TileRefinery)CoreProxy.proxy.getServerTile(tileentity), x, y, z);
/*     */   }
/*     */   private void render(TileRefinery tile, double x, double y, double z) {
/*     */     float trans1, trans2;
/*  78 */     FluidStack liquid1 = null, liquid2 = null, liquidResult = null;
/*  79 */     int color1 = 16777215, color2 = 16777215, colorResult = 16777215;
/*     */     
/*  81 */     float anim = 0.0F;
/*  82 */     int angle = 0;
/*  83 */     ModelRenderer theMagnet = this.magnet[0];
/*  84 */     if (tile != null) {
/*  85 */       if (tile.tanks[0].getFluid() != null) {
/*  86 */         liquid1 = tile.tanks[0].getFluid();
/*  87 */         color1 = (tile.tanks[0]).colorRenderCache;
/*     */       } 
/*     */       
/*  90 */       if (tile.tanks[1].getFluid() != null) {
/*  91 */         liquid2 = tile.tanks[1].getFluid();
/*  92 */         color2 = (tile.tanks[1]).colorRenderCache;
/*     */       } 
/*     */       
/*  95 */       if (tile.result.getFluid() != null) {
/*  96 */         liquidResult = tile.result.getFluid();
/*  97 */         colorResult = tile.result.colorRenderCache;
/*     */       } 
/*     */       
/* 100 */       anim = tile.getAnimationStage();
/*     */       
/* 102 */       angle = 0;
/* 103 */       switch (tile.func_145831_w().func_72805_g(tile.field_145851_c, tile.field_145848_d, tile.field_145849_e)) {
/*     */         case 2:
/* 105 */           angle = 90;
/*     */           break;
/*     */         case 3:
/* 108 */           angle = 270;
/*     */           break;
/*     */         case 4:
/* 111 */           angle = 180;
/*     */           break;
/*     */         case 5:
/* 114 */           angle = 0;
/*     */           break;
/*     */       } 
/*     */       
/* 118 */       if (tile.animationSpeed <= 1.0F) {
/* 119 */         theMagnet = this.magnet[0];
/* 120 */       } else if (tile.animationSpeed <= 2.5D) {
/* 121 */         theMagnet = this.magnet[1];
/* 122 */       } else if (tile.animationSpeed <= 4.5D) {
/* 123 */         theMagnet = this.magnet[2];
/*     */       } else {
/* 125 */         theMagnet = this.magnet[3];
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     GL11.glPushMatrix();
/* 130 */     GL11.glPushAttrib(8192);
/* 131 */     GL11.glEnable(2896);
/* 132 */     GL11.glDisable(2884);
/*     */     
/* 134 */     GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
/* 135 */     GL11.glScalef(0.99F, 0.99F, 0.99F);
/*     */     
/* 137 */     GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
/*     */     
/* 139 */     func_147499_a(TEXTURE);
/*     */     
/* 141 */     GL11.glPushMatrix();
/* 142 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 143 */     GL11.glTranslatef(-0.25F, 0.0F, -0.25F);
/* 144 */     this.tank.func_78785_a(0.0625F);
/* 145 */     GL11.glTranslatef(0.25F, 0.0F, 0.25F);
/*     */     
/* 147 */     GL11.glTranslatef(-0.25F, 0.0F, 0.25F);
/* 148 */     this.tank.func_78785_a(0.0625F);
/* 149 */     GL11.glTranslatef(0.25F, 0.0F, -0.25F);
/*     */     
/* 151 */     GL11.glTranslatef(0.25F, 0.0F, 0.0F);
/* 152 */     this.tank.func_78785_a(0.0625F);
/* 153 */     GL11.glTranslatef(-0.25F, 0.0F, 0.0F);
/* 154 */     GL11.glPopMatrix();
/*     */ 
/*     */ 
/*     */     
/* 158 */     if (anim <= 100.0F) {
/* 159 */       trans1 = 0.75F * anim / 100.0F;
/* 160 */       trans2 = 0.0F;
/* 161 */     } else if (anim <= 200.0F) {
/* 162 */       trans1 = 0.75F - 0.75F * (anim - 100.0F) / 100.0F;
/* 163 */       trans2 = 0.75F * (anim - 100.0F) / 100.0F;
/*     */     } else {
/* 165 */       trans1 = 0.75F * (anim - 200.0F) / 100.0F;
/* 166 */       trans2 = 0.75F - 0.75F * (anim - 200.0F) / 100.0F;
/*     */     } 
/*     */     
/* 169 */     GL11.glPushMatrix();
/* 170 */     GL11.glScalef(0.99F, 0.99F, 0.99F);
/* 171 */     GL11.glTranslatef(-0.51F, trans1 - 0.5F, -0.5F);
/* 172 */     theMagnet.func_78785_a(0.0625F);
/* 173 */     GL11.glPopMatrix();
/*     */     
/* 175 */     GL11.glPushMatrix();
/* 176 */     GL11.glScalef(0.99F, 0.99F, 0.99F);
/* 177 */     GL11.glTranslatef(-0.51F, trans2 - 0.5F, 0.25F);
/* 178 */     theMagnet.func_78785_a(0.0625F);
/* 179 */     GL11.glPopMatrix();
/*     */     
/* 181 */     if (tile != null) {
/* 182 */       GL11.glPushAttrib(8192);
/* 183 */       GL11.glEnable(2884);
/* 184 */       GL11.glDisable(2896);
/* 185 */       GL11.glEnable(3042);
/* 186 */       GL11.glBlendFunc(770, 771);
/*     */       
/* 188 */       GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 189 */       GL11.glScalef(0.5F, 1.0F, 0.5F);
/*     */       
/* 191 */       func_147499_a(TextureMap.field_110575_b);
/*     */       
/* 193 */       if (liquid1 != null && liquid1.amount > 0) {
/* 194 */         int[] list1 = FluidRenderer.getFluidDisplayLists(liquid1, tile.func_145831_w(), false);
/*     */         
/* 196 */         if (list1 != null) {
/* 197 */           RenderUtils.setGLColorFromInt(color1);
/* 198 */           GL11.glCallList(list1[getDisplayListIndex((Tank)tile.tanks[0])]);
/*     */         } 
/*     */       } 
/*     */       
/* 202 */       if (liquid2 != null && liquid2.amount > 0) {
/* 203 */         int[] list2 = FluidRenderer.getFluidDisplayLists(liquid2, tile.func_145831_w(), false);
/*     */         
/* 205 */         if (list2 != null) {
/* 206 */           GL11.glPushMatrix();
/* 207 */           GL11.glTranslatef(0.0F, 0.0F, 1.0F);
/* 208 */           RenderUtils.setGLColorFromInt(color2);
/* 209 */           GL11.glCallList(list2[getDisplayListIndex((Tank)tile.tanks[1])]);
/* 210 */           GL11.glPopMatrix();
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 215 */       if (liquidResult != null && liquidResult.amount > 0) {
/* 216 */         int[] list3 = FluidRenderer.getFluidDisplayLists(liquidResult, tile.func_145831_w(), false);
/*     */         
/* 218 */         if (list3 != null) {
/* 219 */           GL11.glPushMatrix();
/* 220 */           GL11.glTranslatef(1.0F, 0.0F, 0.5F);
/* 221 */           RenderUtils.setGLColorFromInt(colorResult);
/* 222 */           GL11.glCallList(list3[getDisplayListIndex((Tank)tile.result)]);
/* 223 */           GL11.glPopMatrix();
/*     */         } 
/*     */       } 
/* 226 */       GL11.glPopAttrib();
/*     */     } 
/*     */     
/* 229 */     GL11.glPopAttrib();
/* 230 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private int getDisplayListIndex(Tank tank) {
/* 234 */     return Math.min((int)(tank.getFluidAmount() / tank.getCapacity() * 99.0F), 99);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\render\RenderRefinery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */