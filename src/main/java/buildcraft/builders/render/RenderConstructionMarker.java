/*     */ package buildcraft.builders.render;
/*     */ 
/*     */ import buildcraft.builders.TileConstructionMarker;
/*     */ import buildcraft.core.EntityLaser;
/*     */ import buildcraft.core.render.RenderBoxProvider;
/*     */ import buildcraft.core.render.RenderBuildingItems;
/*     */ import buildcraft.core.render.RenderLaser;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ public class RenderConstructionMarker
/*     */   extends RenderBoxProvider
/*     */ {
/*  29 */   private final RenderBuildingItems renderItems = new RenderBuildingItems();
/*     */   
/*  31 */   private final EntityItem dummyEntityItem = new EntityItem(null);
/*     */   
/*     */   private final RenderItem customRenderItem;
/*  34 */   private ModelBase model = new ModelBase() {
/*     */     
/*     */     };
/*     */   
/*     */   public RenderConstructionMarker() {
/*  39 */     this.box = new ModelRenderer(this.model, 0, 1);
/*  40 */     this.box.func_78789_a(-8.0F, -8.0F, -8.0F, 16, 4, 16);
/*  41 */     this.box.field_78800_c = 8.0F;
/*  42 */     this.box.field_78797_d = 8.0F;
/*  43 */     this.box.field_78798_e = 8.0F;
/*     */     
/*  45 */     this.customRenderItem = new RenderItem()
/*     */       {
/*     */         public boolean shouldBob() {
/*  48 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean shouldSpreadItems() {
/*  53 */           return false;
/*     */         }
/*     */       };
/*  56 */     this.customRenderItem.func_76976_a(RenderManager.field_78727_a);
/*     */   }
/*     */   private ModelRenderer box;
/*     */   
/*     */   public void func_147500_a(TileEntity tileentity, double x, double y, double z, float f) {
/*  61 */     super.func_147500_a(tileentity, x, y, z, f);
/*     */     
/*  63 */     TileConstructionMarker marker = (TileConstructionMarker)tileentity;
/*     */     
/*  65 */     GL11.glPushMatrix();
/*  66 */     GL11.glPushAttrib(8192);
/*  67 */     GL11.glEnable(2884);
/*  68 */     GL11.glEnable(2896);
/*  69 */     GL11.glEnable(3042);
/*  70 */     GL11.glBlendFunc(770, 771);
/*     */     
/*  72 */     GL11.glTranslated(x, y, z);
/*  73 */     GL11.glTranslated(-tileentity.field_145851_c, -tileentity.field_145848_d, -tileentity.field_145849_e);
/*     */     
/*  75 */     if (marker.laser != null) {
/*  76 */       GL11.glPushMatrix();
/*     */       
/*  78 */       RenderLaser.doRenderLaser(TileEntityRendererDispatcher.field_147556_a.field_147553_e, marker.laser, EntityLaser.LASER_TEXTURES[4]);
/*     */ 
/*     */       
/*  81 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/*  84 */     if (marker.itemBlueprint != null) {
/*  85 */       doRenderItem(marker.itemBlueprint, (marker.field_145851_c + 0.5F), (marker.field_145848_d + 0.2F), (marker.field_145849_e + 0.5F));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     GL11.glPopAttrib();
/*  93 */     GL11.glPopMatrix();
/*     */     
/*  95 */     this.renderItems.render(tileentity, x, y, z);
/*     */   }
/*     */   
/*     */   public void doRenderItem(ItemStack stack, double x, double y, double z) {
/*  99 */     if (stack == null) {
/*     */       return;
/*     */     }
/*     */     
/* 103 */     float renderScale = 1.5F;
/* 104 */     GL11.glPushMatrix();
/* 105 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/* 106 */     GL11.glTranslatef(0.0F, 0.25F, 0.0F);
/* 107 */     GL11.glScalef(renderScale, renderScale, renderScale);
/* 108 */     this.dummyEntityItem.func_92058_a(stack);
/* 109 */     this.customRenderItem.func_76986_a(this.dummyEntityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/*     */     
/* 111 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\render\RenderConstructionMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */