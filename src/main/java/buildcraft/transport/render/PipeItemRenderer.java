/*     */ package buildcraft.transport.render;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.core.lib.render.FakeBlock;
/*     */ import buildcraft.core.lib.render.RenderUtils;
/*     */ import buildcraft.core.lib.utils.ColorUtils;
/*     */ import buildcraft.transport.BlockGenericPipe;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
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
/*     */ public class PipeItemRenderer
/*     */   implements IItemRenderer
/*     */ {
/*     */   private static final float zFightOffset = 2.4414062E-4F;
/*     */   
/*     */   private void renderPipeItem(RenderBlocks render, ItemStack item, float translateX, float translateY, float translateZ) {
/*     */     TextureAtlasSprite textureAtlasSprite;
/*  34 */     GL11.glPushAttrib(16384);
/*     */     
/*  36 */     GL11.glBlendFunc(770, 771);
/*  37 */     GL11.glEnable(3042);
/*     */ 
/*     */     
/*  40 */     Tessellator tessellator = Tessellator.field_78398_a;
/*     */     
/*  42 */     FakeBlock fakeBlock = FakeBlock.INSTANCE;
/*  43 */     IIcon icon = PipeIconProvider.TYPE.PipeStainedOverlay.getIcon();
/*     */     
/*  45 */     if (item.func_77960_j() >= 1) {
/*  46 */       GL11.glPushMatrix();
/*     */       
/*  48 */       int c = ColorUtils.getRGBColor(item.func_77960_j() - 1);
/*  49 */       GL11.glColor3ub((byte)(c >> 16), (byte)(c >> 8 & 0xFF), (byte)(c & 0xFF));
/*  50 */       fakeBlock.func_149676_a(0.25024414F, 2.4414062E-4F, 0.25024414F, 0.74975586F, 0.99975586F, 0.74975586F);
/*  51 */       fakeBlock.func_149683_g();
/*  52 */       render.func_147775_a((Block)fakeBlock);
/*     */       
/*  54 */       GL11.glTranslatef(translateX, translateY, translateZ);
/*  55 */       RenderUtils.drawBlockItem(render, tessellator, (Block)fakeBlock, icon);
/*  56 */       fakeBlock.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  57 */       GL11.glColor3ub((byte)-1, (byte)-1, (byte)-1);
/*  58 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/*  61 */     BlockGenericPipe blockGenericPipe = BuildCraftTransport.genericPipeBlock;
/*  62 */     icon = item.func_77973_b().func_77617_a(0);
/*     */     
/*  64 */     if (icon == null) {
/*  65 */       textureAtlasSprite = ((TextureMap)Minecraft.func_71410_x().func_110434_K().func_110581_b(TextureMap.field_110575_b)).func_110572_b("missingno");
/*     */     }
/*     */     
/*  68 */     blockGenericPipe.func_149676_a(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
/*  69 */     blockGenericPipe.func_149683_g();
/*  70 */     render.func_147775_a((Block)blockGenericPipe);
/*     */     
/*  72 */     GL11.glTranslatef(translateX, translateY, translateZ);
/*  73 */     RenderUtils.drawBlockItem(render, tessellator, (Block)blockGenericPipe, (IIcon)textureAtlasSprite);
/*  74 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*  75 */     blockGenericPipe.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  77 */     GL11.glPopAttrib();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/*  85 */     switch (type) {
/*     */       case ENTITY:
/*  87 */         return true;
/*     */       case EQUIPPED:
/*  89 */         return true;
/*     */       case EQUIPPED_FIRST_PERSON:
/*  91 */         return true;
/*     */       case INVENTORY:
/*  93 */         return true;
/*     */     } 
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/* 106 */     switch (type) {
/*     */       case ENTITY:
/* 108 */         renderPipeItem((RenderBlocks)data[0], item, -0.5F, -0.5F, -0.5F);
/*     */         break;
/*     */       case EQUIPPED:
/* 111 */         renderPipeItem((RenderBlocks)data[0], item, -0.4F, 0.5F, 0.35F);
/*     */         break;
/*     */       case EQUIPPED_FIRST_PERSON:
/* 114 */         renderPipeItem((RenderBlocks)data[0], item, -0.4F, 0.5F, 0.35F);
/*     */         break;
/*     */       case INVENTORY:
/* 117 */         renderPipeItem((RenderBlocks)data[0], item, -0.5F, -0.5F, -0.5F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\render\PipeItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */