/*    */ package buildcraft.core.render;
/*    */ 
/*    */ import buildcraft.core.EntityLaser;
/*    */ import buildcraft.core.LaserData;
/*    */ import buildcraft.core.builders.TileAbstractBuilder;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ public class RenderBuilder
/*    */   extends RenderBoxProvider
/*    */ {
/* 21 */   private static final RenderBuildingItems renderItems = new RenderBuildingItems();
/*    */ 
/*    */   
/*    */   public void func_147500_a(TileEntity tileentity, double x, double y, double z, float f) {
/* 25 */     super.func_147500_a(tileentity, x, y, z, f);
/*    */     
/* 27 */     TileAbstractBuilder builder = (TileAbstractBuilder)tileentity;
/*    */     
/* 29 */     GL11.glPushMatrix();
/* 30 */     GL11.glPushAttrib(8192);
/* 31 */     GL11.glEnable(2884);
/* 32 */     GL11.glEnable(2896);
/* 33 */     GL11.glEnable(3042);
/* 34 */     GL11.glBlendFunc(770, 771);
/*    */     
/* 36 */     GL11.glTranslated(x, y, z);
/* 37 */     GL11.glTranslated(-tileentity.field_145851_c, -tileentity.field_145848_d, -tileentity.field_145849_e);
/*    */     
/* 39 */     if (builder.getPathLaser() != null) {
/* 40 */       for (LaserData laser : builder.getPathLaser()) {
/* 41 */         if (laser != null) {
/* 42 */           GL11.glPushMatrix();
/*    */           
/* 44 */           RenderLaser.doRenderLaser(TileEntityRendererDispatcher.field_147556_a.field_147553_e, laser, EntityLaser.LASER_TEXTURES[4]);
/*    */ 
/*    */           
/* 47 */           GL11.glPopMatrix();
/*    */         } 
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/* 53 */     GL11.glPopAttrib();
/* 54 */     GL11.glPopMatrix();
/*    */     
/* 56 */     renderItems.render(tileentity, x, y, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\render\RenderBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */