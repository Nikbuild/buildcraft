/*     */ package buildcraft.transport.render;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.EnumColor;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.api.items.IItemCustomPipeRender;
/*     */ import buildcraft.core.lib.render.RenderEntityBlock;
/*     */ import buildcraft.core.lib.render.RenderUtils;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeTransportItems;
/*     */ import buildcraft.transport.TravelingItem;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class PipeTransportItemsRenderer
/*     */   extends PipeTransportRenderer<PipeTransportItems>
/*     */ {
/*  26 */   private static final EntityItem dummyEntityItem = new EntityItem(null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private static final RenderItem customRenderItem = new RenderItem()
/*     */     {
/*     */       public boolean shouldBob() {
/*  34 */         return false;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean shouldSpreadItems() {
/*  39 */         return false;
/*     */       }
/*     */     }; static {
/*  42 */     customRenderItem.func_76976_a(RenderManager.field_78727_a);
/*     */   }
/*     */   private static final int MAX_ITEMS_TO_RENDER = 10;
/*     */   private int getItemLightLevel(ItemStack stack) {
/*  46 */     if (stack.func_77973_b() instanceof net.minecraft.item.ItemBlock) {
/*  47 */       Block b = Block.func_149634_a(stack.func_77973_b());
/*  48 */       return b.func_149750_m();
/*     */     } 
/*  50 */     return 0;
/*     */   }
/*     */   
/*     */   public void doRenderItem(TravelingItem travellingItem, double x, double y, double z, float light, EnumColor color) {
/*  54 */     if (travellingItem == null || travellingItem.getItemStack() == null) {
/*     */       return;
/*     */     }
/*     */     
/*  58 */     float renderScale = 0.7F;
/*  59 */     ItemStack itemstack = travellingItem.getItemStack();
/*     */     
/*  61 */     GL11.glPushMatrix();
/*  62 */     GL11.glTranslatef((float)x, (float)y + 0.25F, (float)z);
/*  63 */     GL11.glEnable(3008);
/*     */ 
/*     */ 
/*     */     
/*  67 */     if (travellingItem.hasDisplayList) {
/*  68 */       GL11.glCallList(travellingItem.displayList);
/*     */     } else {
/*  70 */       travellingItem.displayList = GLAllocation.func_74526_a(1);
/*  71 */       travellingItem.hasDisplayList = true;
/*     */       
/*  73 */       GL11.glNewList(travellingItem.displayList, 4865);
/*  74 */       if (itemstack.func_77973_b() instanceof IItemCustomPipeRender) {
/*  75 */         IItemCustomPipeRender render = (IItemCustomPipeRender)itemstack.func_77973_b();
/*  76 */         float itemScale = render.getPipeRenderScale(itemstack);
/*  77 */         GL11.glScalef(renderScale * itemScale, renderScale * itemScale, renderScale * itemScale);
/*  78 */         itemScale = 1.0F / itemScale;
/*     */         
/*  80 */         if (!render.renderItemInPipe(itemstack, x, y, z)) {
/*  81 */           dummyEntityItem.func_92058_a(itemstack);
/*  82 */           customRenderItem.func_76986_a(dummyEntityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/*     */         } 
/*     */         
/*  85 */         GL11.glScalef(itemScale, itemScale, itemScale);
/*     */       } else {
/*  87 */         GL11.glScalef(renderScale, renderScale, renderScale);
/*  88 */         dummyEntityItem.func_92058_a(itemstack);
/*  89 */         customRenderItem.func_76986_a(dummyEntityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/*     */       } 
/*  91 */       GL11.glEndList();
/*     */     } 
/*     */     
/*  94 */     if (color != null) {
/*  95 */       bindTexture(TextureMap.field_110575_b);
/*  96 */       RenderEntityBlock.RenderInfo block = new RenderEntityBlock.RenderInfo();
/*     */       
/*  98 */       block.texture = BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.ItemBox.ordinal());
/*     */       
/* 100 */       float pix = 0.0625F;
/* 101 */       float min = -4.0F * pix;
/* 102 */       float max = 4.0F * pix;
/*     */       
/* 104 */       block.minY = min;
/* 105 */       block.maxY = max;
/*     */       
/* 107 */       block.minZ = min;
/* 108 */       block.maxZ = max;
/*     */       
/* 110 */       block.minX = min;
/* 111 */       block.maxX = max;
/*     */       
/* 113 */       RenderUtils.setGLColorFromInt(color.getLightHex());
/* 114 */       RenderEntityBlock.INSTANCE.renderBlock(block);
/*     */     } 
/*     */     
/* 117 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Pipe<PipeTransportItems> pipe, double x, double y, double z, float f) {
/* 122 */     GL11.glPushMatrix();
/*     */     
/* 124 */     int count = 0;
/* 125 */     for (TravelingItem item : ((PipeTransportItems)pipe.transport).items) {
/* 126 */       if (count >= 10) {
/*     */         break;
/*     */       }
/*     */       
/* 130 */       Position motion = new Position(0.0D, 0.0D, 0.0D, item.toCenter ? item.input : item.output);
/* 131 */       motion.moveForwards((item.getSpeed() * f));
/*     */       
/* 133 */       doRenderItem(item, x + item.xCoord - pipe.container.field_145851_c + motion.x, y + item.yCoord - pipe.container.field_145848_d + motion.y, z + item.zCoord - pipe.container.field_145849_e + motion.z, 0.0F, item.color);
/* 134 */       count++;
/*     */     } 
/*     */     
/* 137 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\render\PipeTransportItemsRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */