/*     */ package buildcraft.transport.render;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.transport.BlockGenericPipe;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import net.minecraft.block.Block;
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
/*     */ public class PlugItemRenderer
/*     */   implements IItemRenderer
/*     */ {
/*     */   private void renderPlugItem(RenderBlocks render, ItemStack item, float translateX, float translateY, float translateZ) {
/*  28 */     BlockGenericPipe blockGenericPipe = BuildCraftTransport.genericPipeBlock;
/*  29 */     Tessellator tessellator = Tessellator.field_78398_a;
/*  30 */     IIcon textureID = BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.PipeStructureCobblestone.ordinal());
/*     */     
/*  32 */     blockGenericPipe.func_149676_a(0.25F, 0.25F, 0.25F, 0.75F, 0.375F, 0.75F);
/*  33 */     blockGenericPipe.func_149683_g();
/*  34 */     render.func_147775_a((Block)blockGenericPipe);
/*  35 */     GL11.glTranslatef(translateX, translateY, translateZ + 0.25F);
/*     */     
/*  37 */     tessellator.func_78382_b();
/*  38 */     tessellator.func_78375_b(0.0F, -0.0F, 0.0F);
/*  39 */     render.func_147768_a((Block)blockGenericPipe, 0.0D, 0.0D, 0.0D, textureID);
/*  40 */     tessellator.func_78381_a();
/*     */     
/*  42 */     tessellator.func_78382_b();
/*  43 */     tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
/*  44 */     render.func_147806_b((Block)blockGenericPipe, 0.0D, 0.0D, 0.0D, textureID);
/*  45 */     tessellator.func_78381_a();
/*     */     
/*  47 */     tessellator.func_78382_b();
/*  48 */     tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
/*  49 */     render.func_147761_c((Block)blockGenericPipe, 0.0D, 0.0D, 0.0D, textureID);
/*  50 */     tessellator.func_78381_a();
/*     */     
/*  52 */     tessellator.func_78382_b();
/*  53 */     tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
/*  54 */     render.func_147734_d((Block)blockGenericPipe, 0.0D, 0.0D, 0.0D, textureID);
/*  55 */     tessellator.func_78381_a();
/*     */     
/*  57 */     tessellator.func_78382_b();
/*  58 */     tessellator.func_78375_b(-1.0F, 0.0F, 0.0F);
/*  59 */     render.func_147798_e((Block)blockGenericPipe, 0.0D, 0.0D, 0.0D, textureID);
/*  60 */     tessellator.func_78381_a();
/*     */     
/*  62 */     tessellator.func_78382_b();
/*  63 */     tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
/*  64 */     render.func_147764_f((Block)blockGenericPipe, 0.0D, 0.0D, 0.0D, textureID);
/*  65 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/*  70 */     switch (type) {
/*     */       case ENTITY:
/*  72 */         return true;
/*     */       case EQUIPPED:
/*  74 */         return true;
/*     */       case EQUIPPED_FIRST_PERSON:
/*  76 */         return true;
/*     */       case INVENTORY:
/*  78 */         return true;
/*     */     } 
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/*  86 */     return (helper != IItemRenderer.ItemRendererHelper.BLOCK_3D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/*  91 */     switch (type) {
/*     */       case ENTITY:
/*  93 */         GL11.glScalef(0.5F, 0.5F, 0.5F);
/*  94 */         renderPlugItem((RenderBlocks)data[0], item, -0.6F, 0.0F, -0.6F);
/*     */         break;
/*     */       case EQUIPPED:
/*     */       case EQUIPPED_FIRST_PERSON:
/*  98 */         GL11.glRotatef(70.0F, 0.0F, 0.0F, 1.0F);
/*  99 */         GL11.glRotatef(-55.0F, 1.0F, 0.0F, 0.0F);
/* 100 */         GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 101 */         GL11.glTranslatef(0.0F, -0.6F, -0.4F);
/* 102 */         renderPlugItem((RenderBlocks)data[0], item, 0.0F, 0.0F, 0.0F);
/*     */         break;
/*     */       case INVENTORY:
/* 105 */         GL11.glScalef(1.1F, 1.1F, 1.1F);
/* 106 */         renderPlugItem((RenderBlocks)data[0], item, -0.3F, -0.35F, -0.7F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\render\PlugItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */