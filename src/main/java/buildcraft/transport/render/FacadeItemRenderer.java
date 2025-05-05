/*     */ package buildcraft.transport.render;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.facades.FacadeType;
/*     */ import buildcraft.api.facades.IFacadeItem;
/*     */ import buildcraft.core.lib.render.RenderUtils;
/*     */ import buildcraft.transport.BlockGenericPipe;
/*     */ import buildcraft.transport.ItemFacade;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraftforge.client.IItemRenderer;
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
/*     */ 
/*     */ public class FacadeItemRenderer
/*     */   implements IItemRenderer
/*     */ {
/*  34 */   private long lastTime = 0L;
/*     */   
/*  36 */   private int renderState = 0;
/*     */   
/*     */   private IIcon tryGetBlockIcon(Block block, int side, int decodedMeta) {
/*  39 */     IIcon icon = RenderUtils.tryGetBlockIcon(block, side, decodedMeta);
/*     */     
/*  41 */     if (icon == null) {
/*  42 */       icon = PipeIconProvider.TYPE.TransparentFacade.getIcon();
/*     */     }
/*     */     
/*  45 */     return icon;
/*     */   }
/*     */   
/*     */   private void drawHollowCube(Tessellator tessellator, RenderBlocks render, Block block, int decodedMeta) {
/*  49 */     IIcon icon0 = tryGetBlockIcon(block, 0, decodedMeta);
/*  50 */     IIcon icon1 = tryGetBlockIcon(block, 1, decodedMeta);
/*  51 */     IIcon icon2 = tryGetBlockIcon(block, 2, decodedMeta);
/*  52 */     IIcon icon3 = tryGetBlockIcon(block, 3, decodedMeta);
/*  53 */     IIcon icon4 = tryGetBlockIcon(block, 4, decodedMeta);
/*  54 */     IIcon icon5 = tryGetBlockIcon(block, 5, decodedMeta);
/*     */     
/*  56 */     float width = 0.875F;
/*  57 */     float cavity = 0.25F;
/*  58 */     double innerWidth = (1.0F - cavity);
/*     */     
/*  60 */     tessellator.func_78382_b();
/*  61 */     render.func_147782_a(0.0D, 0.0D, width, 1.0D, 1.0D, 1.0D);
/*     */ 
/*     */     
/*  64 */     tessellator.func_78375_b(0.0F, -1.0F, 0.0F);
/*  65 */     render.func_147768_a(block, 0.0D, 0.0D, 0.0D, icon0);
/*  66 */     tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
/*  67 */     render.func_147806_b(block, 0.0D, 0.0D, 0.0D, icon1);
/*  68 */     tessellator.func_78375_b(-1.0F, 0.0F, 0.0F);
/*  69 */     render.func_147798_e(block, 0.0D, 0.0D, 0.0D, icon4);
/*  70 */     tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
/*  71 */     render.func_147764_f(block, 0.0D, 0.0D, 0.0D, icon5);
/*     */ 
/*     */     
/*  74 */     tessellator.func_78375_b(0.0F, -1.0F, 0.0F);
/*  75 */     render.func_147768_a(block, 0.0D, innerWidth, 0.0D, icon0);
/*  76 */     tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
/*  77 */     render.func_147806_b(block, 0.0D, -innerWidth, 0.0D, icon1);
/*  78 */     tessellator.func_78375_b(-1.0F, 0.0F, 0.0F);
/*  79 */     render.func_147798_e(block, innerWidth, 0.0D, 0.0D, icon4);
/*  80 */     tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
/*  81 */     render.func_147764_f(block, -innerWidth, 0.0D, 0.0D, icon5);
/*     */ 
/*     */     
/*  84 */     render.field_152631_f = true;
/*  85 */     render.func_147782_a(0.0D, 0.0D, width, cavity, 1.0D, 1.0D);
/*  86 */     tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
/*  87 */     render.func_147761_c(block, 0.0D, 0.0D, 0.0D, icon2);
/*  88 */     tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/*  89 */     render.func_147734_d(block, 0.0D, 0.0D, 0.0D, icon3);
/*  90 */     render.func_147782_a(innerWidth, 0.0D, width, 1.0D, 1.0D, 1.0D);
/*  91 */     tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
/*  92 */     render.func_147761_c(block, 0.0D, 0.0D, 0.0D, icon2);
/*  93 */     tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/*  94 */     render.func_147734_d(block, 0.0D, 0.0D, 0.0D, icon3);
/*  95 */     render.field_152631_f = false;
/*     */     
/*  97 */     render.func_147782_a(cavity, 0.0D, width, innerWidth, cavity, 1.0D);
/*  98 */     tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
/*  99 */     render.func_147761_c(block, 0.0D, 0.0D, 0.0D, icon2);
/* 100 */     tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/* 101 */     render.func_147734_d(block, 0.0D, 0.0D, 0.0D, icon3);
/* 102 */     render.func_147782_a(cavity, innerWidth, width, innerWidth, 1.0D, 1.0D);
/* 103 */     tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
/* 104 */     render.func_147761_c(block, 0.0D, 0.0D, 0.0D, icon2);
/* 105 */     tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/* 106 */     render.func_147734_d(block, 0.0D, 0.0D, 0.0D, icon3);
/*     */     
/* 108 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   private void renderFacadeItem(RenderBlocks render, ItemStack item, float translateX, float translateY, float translateZ) {
/* 112 */     if (this.lastTime < System.currentTimeMillis()) {
/*     */       
/* 114 */       this.renderState = (this.renderState + 1) % 12;
/* 115 */       this.lastTime = System.currentTimeMillis() + 1000L;
/*     */     } 
/*     */     
/* 118 */     FacadeType type = ((IFacadeItem)item.func_77973_b()).getFacadeType(item);
/* 119 */     ItemFacade.FacadeState[] states = ItemFacade.getFacadeStates(item);
/* 120 */     ItemFacade.FacadeState activeState = null;
/*     */     
/* 122 */     if (states.length > 0)
/*     */     {
/* 124 */       if (type == FacadeType.Basic) {
/* 125 */         activeState = states[0];
/* 126 */       } else if (type == FacadeType.Phased) {
/* 127 */         activeState = states[this.renderState % states.length];
/*     */       } 
/*     */     }
/* 130 */     Block block = (activeState != null) ? activeState.block : null;
/* 131 */     int decodedMeta = (activeState != null) ? activeState.metadata : 0;
/* 132 */     boolean hollow = (activeState != null) ? activeState.hollow : false;
/*     */     
/* 134 */     Tessellator tessellator = Tessellator.field_78398_a;
/*     */     
/* 136 */     if (tryGetBlockIcon(block, 0, decodedMeta) == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 141 */     GL11.glPushMatrix();
/*     */ 
/*     */     
/* 144 */     if (block != null) {
/* 145 */       if (block.func_149701_w() > 0) {
/* 146 */         GL11.glAlphaFunc(516, 0.1F);
/* 147 */         GL11.glEnable(3042);
/* 148 */         OpenGlHelper.func_148821_a(770, 771, 1, 0);
/*     */       } 
/*     */       
/* 151 */       RenderUtils.setGLColorFromInt(block.func_149741_i(decodedMeta));
/*     */     } 
/*     */     
/* 154 */     if (hollow) {
/* 155 */       GL11.glTranslatef(translateX, translateY, translateZ);
/* 156 */       drawHollowCube(tessellator, render, block, decodedMeta);
/*     */     } else {
/* 158 */       render.func_147782_a(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
/* 159 */       GL11.glTranslatef(translateX, translateY, translateZ);
/* 160 */       RenderUtils.drawBlockItem(render, tessellator, block, decodedMeta);
/*     */     } 
/*     */ 
/*     */     
/* 164 */     if (block != null && block.func_149701_w() > 0) {
/* 165 */       GL11.glDisable(3042);
/*     */     }
/*     */     
/* 168 */     GL11.glPopMatrix();
/*     */     
/* 170 */     RenderUtils.setGLColorFromInt(16777215);
/*     */ 
/*     */     
/* 173 */     if (!hollow && block != null && (block.func_149688_o() == null || block.func_149688_o().func_76218_k())) {
/* 174 */       BlockGenericPipe blockGenericPipe = BuildCraftTransport.genericPipeBlock;
/* 175 */       IIcon textureID = BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.PipeStructureCobblestone.ordinal());
/*     */       
/* 177 */       render.func_147782_a(0.25D, 0.25D, 0.25D, 0.75D, 0.75D, 0.6875D);
/* 178 */       GL11.glTranslatef(translateX, translateY, translateZ + 0.25F);
/*     */       
/* 180 */       tessellator.func_78382_b();
/* 181 */       tessellator.func_78375_b(0.0F, -0.0F, 0.0F);
/* 182 */       render.func_147768_a((Block)blockGenericPipe, 0.0D, 0.0D, 0.0D, textureID);
/* 183 */       tessellator.func_78381_a();
/* 184 */       tessellator.func_78382_b();
/* 185 */       tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
/* 186 */       render.func_147806_b((Block)blockGenericPipe, 0.0D, 0.0D, 0.0D, textureID);
/* 187 */       tessellator.func_78381_a();
/* 188 */       tessellator.func_78382_b();
/* 189 */       tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
/* 190 */       render.func_147761_c((Block)blockGenericPipe, 0.0D, 0.0D, 0.0D, textureID);
/* 191 */       tessellator.func_78381_a();
/* 192 */       tessellator.func_78382_b();
/* 193 */       tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/* 194 */       render.func_147734_d((Block)blockGenericPipe, 0.0D, 0.0D, 0.0D, textureID);
/* 195 */       tessellator.func_78381_a();
/* 196 */       tessellator.func_78382_b();
/* 197 */       tessellator.func_78375_b(-1.0F, 0.0F, 0.0F);
/* 198 */       render.func_147798_e((Block)blockGenericPipe, 0.0D, 0.0D, 0.0D, textureID);
/* 199 */       tessellator.func_78381_a();
/* 200 */       tessellator.func_78382_b();
/* 201 */       tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
/* 202 */       render.func_147764_f((Block)blockGenericPipe, 0.0D, 0.0D, 0.0D, textureID);
/* 203 */       tessellator.func_78381_a();
/*     */     } 
/* 205 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/* 210 */     switch (type) {
/*     */       case ENTITY:
/* 212 */         return true;
/*     */       case EQUIPPED:
/* 214 */         return true;
/*     */       case EQUIPPED_FIRST_PERSON:
/* 216 */         return true;
/*     */       case INVENTORY:
/* 218 */         return true;
/*     */     } 
/* 220 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/* 226 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/* 232 */     switch (type) {
/*     */       case ENTITY:
/* 234 */         GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 235 */         renderFacadeItem((RenderBlocks)data[0], item, -0.6F, 0.0F, -0.6F);
/*     */         break;
/*     */       case EQUIPPED:
/*     */       case EQUIPPED_FIRST_PERSON:
/* 239 */         renderFacadeItem((RenderBlocks)data[0], item, 0.0F, 0.0F, 0.0F);
/*     */         break;
/*     */       case INVENTORY:
/* 242 */         GL11.glScalef(1.1F, 1.1F, 1.1F);
/* 243 */         renderFacadeItem((RenderBlocks)data[0], item, -0.3F, -0.35F, -0.7F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\render\FacadeItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */