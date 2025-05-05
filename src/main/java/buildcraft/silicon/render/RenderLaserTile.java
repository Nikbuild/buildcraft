/*    */ package buildcraft.silicon.render;
/*    */ 
/*    */ import buildcraft.core.render.RenderLaser;
/*    */ import buildcraft.silicon.TileLaser;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
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
/*    */ 
/*    */ 
/*    */ public class RenderLaserTile
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/*    */   public void func_147500_a(TileEntity tileentity, double x, double y, double z, float f) {
/* 24 */     TileLaser laser = (TileLaser)tileentity;
/*    */     
/* 26 */     if (laser != null) {
/* 27 */       GL11.glPushMatrix();
/* 28 */       GL11.glTranslated(x, y, z);
/* 29 */       GL11.glTranslated(-tileentity.field_145851_c, -tileentity.field_145848_d, -tileentity.field_145849_e);
/*    */       
/* 31 */       GL11.glPushMatrix();
/* 32 */       RenderLaser.doRenderLaser(TileEntityRendererDispatcher.field_147556_a.field_147553_e, laser.laser, laser.getTexture());
/* 33 */       GL11.glPopMatrix();
/*    */       
/* 35 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\render\RenderLaserTile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */