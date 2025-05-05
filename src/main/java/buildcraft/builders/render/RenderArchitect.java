/*    */ package buildcraft.builders.render;
/*    */ 
/*    */ import buildcraft.builders.TileArchitect;
/*    */ import buildcraft.core.EntityLaser;
/*    */ import buildcraft.core.LaserData;
/*    */ import buildcraft.core.render.RenderBoxProvider;
/*    */ import buildcraft.core.render.RenderLaser;
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
/*    */ 
/*    */ 
/*    */ public class RenderArchitect
/*    */   extends RenderBoxProvider
/*    */ {
/*    */   public void func_147500_a(TileEntity tileentity, double x, double y, double z, float f) {
/* 26 */     super.func_147500_a(tileentity, x, y, z, f);
/*    */     
/* 28 */     TileArchitect architect = (TileArchitect)tileentity;
/*    */     
/* 30 */     GL11.glPushMatrix();
/* 31 */     GL11.glPushAttrib(8192);
/* 32 */     GL11.glEnable(2884);
/* 33 */     GL11.glEnable(2896);
/* 34 */     GL11.glEnable(3042);
/* 35 */     GL11.glBlendFunc(770, 771);
/*    */     
/* 37 */     GL11.glTranslated(x, y, z);
/* 38 */     GL11.glTranslated(-tileentity.field_145851_c, -tileentity.field_145848_d, -tileentity.field_145849_e);
/*    */     
/* 40 */     for (LaserData laser : architect.subLasers) {
/* 41 */       if (laser != null) {
/* 42 */         GL11.glPushMatrix();
/*    */         
/* 44 */         RenderLaser.doRenderLaserWave(TileEntityRendererDispatcher.field_147556_a.field_147553_e, laser, EntityLaser.LASER_TEXTURES[3]);
/*    */ 
/*    */ 
/*    */         
/* 48 */         GL11.glPopMatrix();
/*    */       } 
/*    */     } 
/*    */     
/* 52 */     GL11.glPopAttrib();
/* 53 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\render\RenderArchitect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */