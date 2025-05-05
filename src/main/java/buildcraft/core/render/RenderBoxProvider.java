/*    */ package buildcraft.core.render;
/*    */ 
/*    */ import buildcraft.core.Box;
/*    */ import buildcraft.core.internal.IBoxProvider;
/*    */ import buildcraft.core.internal.IBoxesProvider;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderBoxProvider
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/* 23 */   private static final ResourceLocation LASER_RED = new ResourceLocation("buildcraftcore:textures/laserBeams/laser_1.png");
/* 24 */   private static final ResourceLocation LASER_YELLOW = new ResourceLocation("buildcraftcore:textures/laserBeams/laser_2.png");
/* 25 */   private static final ResourceLocation LASER_GREEN = new ResourceLocation("buildcraftcore:textures/laserBeams/laser_3.png");
/* 26 */   private static final ResourceLocation LASER_BLUE = new ResourceLocation("buildcraftcore:textures/laserBeams/laser_4.png");
/* 27 */   private static final ResourceLocation STRIPES = new ResourceLocation("buildcraftcore:textures/laserBeams/stripes.png");
/* 28 */   private static final ResourceLocation BLUE_STRIPES = new ResourceLocation("buildcraftcore:textures/laserBeams/blue_stripes.png");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_147500_a(TileEntity tileentity, double x, double y, double z, float f) {
/* 35 */     GL11.glPushMatrix();
/* 36 */     GL11.glPushAttrib(8192);
/* 37 */     GL11.glEnable(2884);
/* 38 */     GL11.glDisable(2896);
/* 39 */     GL11.glEnable(3042);
/* 40 */     GL11.glBlendFunc(770, 771);
/* 41 */     GL11.glTranslated(-tileentity.field_145851_c, -tileentity.field_145848_d, -tileentity.field_145849_e);
/* 42 */     GL11.glTranslated(x, y, z);
/*    */     
/* 44 */     if (tileentity instanceof IBoxesProvider) {
/* 45 */       for (Box b : ((IBoxesProvider)tileentity).getBoxes()) {
/* 46 */         if (b.isVisible) {
/* 47 */           RenderBox.doRender(TileEntityRendererDispatcher.field_147556_a.field_147553_e, 
/*    */               
/* 49 */               getTexture(b.kind), b);
/*    */         }
/*    */       } 
/* 52 */     } else if (tileentity instanceof IBoxProvider) {
/* 53 */       Box b = ((IBoxProvider)tileentity).getBox();
/*    */       
/* 55 */       if (b.isVisible) {
/* 56 */         RenderBox.doRender(TileEntityRendererDispatcher.field_147556_a.field_147553_e, 
/*    */             
/* 58 */             getTexture(b.kind), b);
/*    */       }
/*    */     } 
/*    */     
/* 62 */     GL11.glPopAttrib();
/* 63 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   private ResourceLocation getTexture(Box.Kind kind) {
/* 67 */     switch (kind) {
/*    */       case LASER_RED:
/* 69 */         return LASER_RED;
/*    */       case LASER_YELLOW:
/* 71 */         return LASER_YELLOW;
/*    */       case LASER_GREEN:
/* 73 */         return LASER_GREEN;
/*    */       case LASER_BLUE:
/* 75 */         return LASER_BLUE;
/*    */       case STRIPES:
/* 77 */         return STRIPES;
/*    */       case BLUE_STRIPES:
/* 79 */         return BLUE_STRIPES;
/*    */     } 
/*    */     
/* 82 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\render\RenderBoxProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */